package dInternal.dData;

/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr
 * @version 1.0
 */

import java.util.Vector;
import java.util.StringTokenizer;
import java.io.File;

import com.iLib.gDialog.FatalProblemDlg;
import com.iLib.gIO.FilterFile;


import dInternal.dTimeTable.TTStructure;
import dInternal.DModel;
import dInternal.Preferences;

public class LoadData {
  //Vector _v;
  String _instructorFileName;
  String _roomsFileName;
  String _activitiesFileName;
  String _studentsFileName;
  String _functionFileName;
  String _caractFileName;
  DModel _dm;
  RoomsAttributesInterpretor _roomsAttributesInterpretor = new RoomsAttributesInterpretor();
  private static String _SEP= File.separator;
  public final static String _saveSeparator="=================================";
 // private SetOfInstructors _instructorsList;
  //private SetOfRooms _roomsList;
  //private SetOfStudents _studentList;

  private final int NUMBER_OF_TOKENS = 4;
  private final String CR_LF = "\r\n";
  private boolean _load=true;
  private String _chars;
  /***
   *constructor
   */
  public LoadData() {
    initLoadData();

  }
  public LoadData(String args, DModel dm) {
    _dm = dm;
    if (_dm != null)
      _chars =_dm.getDDocument().getDMediator(
          ).getDApplication(
          ).getPreferences()._acceptedChars;
    initLoadData();
    _roomsAttributesInterpretor= extractRoomsAttributesInterpretor();
    verifyImportDataFile(args);
  }

  public LoadData(DModel dm) {
	initLoadData();
   _dm = dm;
/*   if (_dm != null)
      _chars =_dm.getDDocument().getDMediator(
      ).getDApplication(
      ).getPreferences()._acceptedChars;*/
  }
  private void initLoadData() {
    _dm = null;
    String path =System.getProperty("user.dir")+ File.separator+"pref"+File.separator;
    _functionFileName= path + "DXfunctions.sig";
    _caractFileName= path + "DXcaracteristics.sig";
    Preferences preferences = new Preferences(System.getProperty("user.dir")
          + File.separator +
          "pref"
          + File.separator +
          "pref.txt");
      _chars = preferences._acceptedChars;
  }

  private void verifyImportDataFile(String str){
    FilterFile filter = new FilterFile(_chars);
    if (filter.validFile(str)) {
      StringTokenizer st = new StringTokenizer(new String (filter.getByteArray()), CR_LF );
      if (st.countTokens() == NUMBER_OF_TOKENS){
        _instructorFileName = st.nextToken();
        _roomsFileName = st.nextToken();
        _activitiesFileName = st.nextToken();
        _studentsFileName = st.nextToken();
      } else {
        new FatalProblemDlg(
            "Wrong number of lines in the file:" +
            str +
            "\n" +
            "I was in DLoadData constructor ");
        System.exit(1);
      }
    } else {
      new FatalProblemDlg(
          "Unable to filter a file" +
          str +
          "\n" +
          "I was in DLoadData constructor "); //ys
      System.exit(1);
    }
  }

  public RoomsAttributesInterpretor extractRoomsAttributesInterpretor(){
    RoomsAttributesInterpretor attr = new RoomsAttributesInterpretor();
    byte[]  dataloaded = preLoad(_functionFileName);
    if (dataloaded != null)
      attr.loadSetOfFunctions(dataloaded);
    dataloaded = preLoad(_caractFileName);
    if (dataloaded != null)
      attr.loadSetOfCaracteristics(dataloaded);
    return attr;
  }

  public SetOfInstructors extractInstructors(SetOfInstructors currentList, boolean merge){
    byte[]  dataloaded = preLoad(_instructorFileName);
    SetOfInstructors _instructorsList= new SetOfInstructors(dataloaded,5,14);
    if (dataloaded != null) {
      //StringTokenizer st = new StringTokenizer(new String (dataloaded),"\r\n" );
      //return analyseInstructorTokens (st);
     if (merge)
       if(currentList!=null)
         _instructorsList.setSetOfResources(currentList.getSetOfResources());

      if (_instructorsList.analyseTokens(0)){
        _instructorsList.buildSetOfInstructors(0);
        return _instructorsList;
      }
    } else {// (NullPointerException npe) {
      new FatalProblemDlg("I was in LoadData class and extractInstructors. preload failed!!!" );
      //System.exit(52);
    }
    return _instructorsList;
  }

  public SetOfRooms extractRooms(SetOfRooms currentList, boolean merge){
   byte[]  dataloaded = preLoad(_roomsFileName);
   SetOfRooms roomsList = new SetOfRooms(dataloaded,5,14);
   if (dataloaded != null) {
     //StringTokenizer st = new StringTokenizer(new String (dataloaded),"\r\n" );
     //return analyseInstructorTokens (st);
    if (merge)
       if(currentList!=null)
         roomsList.setSetOfResources(currentList.getSetOfResources());

     if (roomsList.analyseTokens(0)){
       roomsList.buildSetOfRooms(0, _roomsAttributesInterpretor);
     }
   } else {// (NullPointerException npe) {
     new FatalProblemDlg("I was in LoadData class and extractRooms. preload failed!!!" );
     System.exit(52);
   }
   return roomsList;
  }


  private byte[] preLoad(String str) {
    FilterFile filter = new FilterFile();
    filter.setCharKnown("");
    filter.appendToCharKnown(_chars);
    if (filter.validFile(str)) {
      return filter.getByteArray();
    } else return null;

  }



  public SetOfStudents extractStudents(SetOfStudents currentList, boolean merge){
    byte[]  dataloaded = preLoad(_studentsFileName);
     SetOfStudents studentsList = new SetOfStudents(dataloaded);
     if (dataloaded != null) {
       //StringTokenizer st = new StringTokenizer(new String (dataloaded),"\r\n" );
       //return analyseInstructorTokens (st);

       if (merge)
         if(currentList!=null)
           studentsList.setSetOfResources(currentList.getSetOfResources());

       if (studentsList.analyseTokens(0)){
         studentsList.buildStudentList(0);
         //return studentsList;
       }
     } else {// (NullPointerException npe) {
       new FatalProblemDlg("I was in LoadData class and extractStudents. preload failed!!!" );
       System.exit(52);
     }
     return studentsList;
  }

  public SetOfActivities extractActivities(SetOfActivities currentList, boolean merge){
    byte[]  dataloaded = preLoad(_activitiesFileName);
    SetOfActivities activitiesList = new SetOfActivities(dataloaded,false);
   if (dataloaded != null) {
     //StringTokenizer st = new StringTokenizer(new String (dataloaded),"\r\n" );
     //return analyseInstructorTokens (st);
    if (merge)
       if(currentList!=null)
         activitiesList.setSetOfResources(currentList.getSetOfResources());

     if (activitiesList.analyseTokens(1)){
       activitiesList.buildSetOfActivities(1);
     }
   } else {// (NullPointerException npe) {
     new FatalProblemDlg("I was in LoadData class and extractActivities. preload failed!!!" );
     //System.exit(52);
   }
   return activitiesList;
  }

  /**
   *
   * */
  public Vector loadProject(String fileName){
    Vector extract= new Vector();
    String dataloaded= new String(preLoad(fileName));

     StringTokenizer project= new StringTokenizer(dataloaded,_saveSeparator);
     if(project.countTokens()==6){
       // extract version
       extract.add(project.nextToken().trim());
       //extract ttstructure
       TTStructure tts= new TTStructure();
       tts.loadTTStructure(project.nextToken().trim());
       extract.add(tts);
       // extract instructor
       SetOfInstructors instructorsList= new SetOfInstructors(project.nextToken().trim().getBytes(),
           tts.getNumberOfActiveDays(),tts.getCurrentCycle().getMaxNumberOfPeriodsADay());
       if (instructorsList.analyseTokens(0)){
        instructorsList.buildSetOfInstructors(0);
      }
      extract.add(instructorsList);

      // extract rooms
      SetOfRooms roomsList = new SetOfRooms(project.nextToken().trim().getBytes(),5,14);
      if (roomsList.analyseTokens(0)){
       roomsList.buildSetOfRooms(3, extractRoomsAttributesInterpretor());
     }
     extract.add(roomsList);

     // extract activities
     SetOfActivities activitiesList = new SetOfActivities(project.nextToken().trim().getBytes(),true);
     if (activitiesList.analyseTokens(1)){
       activitiesList.buildSetOfActivities(1);
     }
     extract.add(activitiesList);
     //extract students
     SetOfStudents studentsList = new SetOfStudents(project.nextToken().trim().getBytes());
     if (studentsList.analyseTokens(0)){
       studentsList.buildStudentList(0);
     }
      extract.add(studentsList);

     }else{
       new FatalProblemDlg("I was in"+getClass().toString()+" LoadData class and loadProject. extract failed!!!" );
       System.exit(1);
     }
     return extract;
  }


  public static void main(String[] args) {

  } // end main


}