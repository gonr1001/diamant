package dInternal;

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

public class LoadData {
  Vector _v;
  String _instructorFileName;
  String _roomsFileName;
  String _activitiesFileName;
  String _studentsFileName;
  private static String _SEP= File.separator;
 // private InstructorsList _instructorsList;
  //private RoomsList _roomsList;
  //private StudentsList _studentList;

  private final int NUMBER_OF_TOKENS = 4;
  private final String CR_LF = "\r\n";
  /***
   *constructor
   */
  public LoadData(String args) {
    _v = new Vector(); // to eliminate
    verifyImportDataFile(args);
  }

  private void verifyImportDataFile(String str){
    FilterFile filter = new FilterFile();
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
  //args[0]: instructor
  //args[1]: student
  //args[2]:
  //args[3]:
  public void extractDatafromImport(String [] args){
   // _v.add(extractInstructors(args[0]));
   // _v.add(extractStudents(args[1]));
  }

  public InstructorsList extractInstructors(InstructorsList currentList, boolean merge){
    byte[]  dataloaded = preLoad(_instructorFileName);
    if (dataloaded != null) {
      //StringTokenizer st = new StringTokenizer(new String (dataloaded),"\r\n" );
      //return analyseInstructorTokens (st);
     InstructorsList _instructorsList = new InstructorsList(dataloaded,5,14);
     if (merge)
       if(currentList!=null)
         _instructorsList.setResourceList(currentList.getResourceList());

      if (_instructorsList.analyseTokens()){
        _instructorsList.buildInstructorsList();
        return _instructorsList;
      }
    } else {// (NullPointerException npe) {
      new FatalProblemDlg("I was in LoadData class and extractInstructors. preload failed!!!" );
      System.exit(52);
    }
    return null;
  }

  public RoomsList extractRooms(InstructorsList currentList, boolean merge){
   byte[]  dataloaded = preLoad(_roomsFileName);
   if (dataloaded != null) {
     //StringTokenizer st = new StringTokenizer(new String (dataloaded),"\r\n" );
     //return analyseInstructorTokens (st);
    RoomsList roomsList = new RoomsList(dataloaded,5,14);
    if (merge)
       if(currentList!=null)
         roomsList.setResourceList(currentList.getResourceList());

     if (roomsList.analyseTokens(0)){
       roomsList.buildRoomsList(0);
       return roomsList;
     }
   } else {// (NullPointerException npe) {
     new FatalProblemDlg("I was in LoadData class and extractRooms. preload failed!!!" );
     System.exit(52);
   }
   return null;
  }

  public ActivitiesList extractActivities(InstructorsList currentList, boolean merge){
   byte[]  dataloaded = preLoad(_activitiesFileName);

   return null;
  }

  private byte[] preLoad(String str) {
    FilterFile filter = new FilterFile();
    filter.appendToCharKnown("ิห้-',; ()๊.เ");
    if (filter.validFile(str)) {
      return filter.getByteArray();
    } else return null;

  }

  public StudentsList extractStudents(InstructorsList currentList, boolean merge){
    byte[]  dataloaded = preLoad(_studentsFileName);
   if (dataloaded != null) {
     //StringTokenizer st = new StringTokenizer(new String (dataloaded),"\r\n" );
     //return analyseInstructorTokens (st);
    StudentsList studentsList = new StudentsList(dataloaded);
    if (merge)
       if(currentList!=null)
         studentsList.setResourceList(currentList.getResourceList());

     if (studentsList.analyseTokens(0)){
       studentsList.buildStudentList(0);
       return studentsList;
     }
   } else {// (NullPointerException npe) {
     new FatalProblemDlg("I was in LoadData class and extractStudents. preload failed!!!" );
     System.exit(52);
   }
   return null;
  }



  public static void main(String[] args) {
    String path ="D:"+File.separator+"Developpements"+File.separator+"Dx"+File.separator+"data"+File.separator+"filedata.sig";
    String pathSave ="D:"+File.separator+"Developpements"+File.separator+"Dx"+File.separator+"data"+File.separator;
    System.out.println("PATH: "+ path);//debug
    FilterFile filter;
    LoadData ldata=  new LoadData(path);

    /**Irstructor test*/
    InstructorsList insList = ldata.extractInstructors(null,false);
    insList.sortResourceListByID();
    ldata=  new LoadData(pathSave+"filedata1.sig");
    ldata.extractInstructors(insList,true);
    //insList.sortResourceListByKey();

    /**Room test*/
    RoomsList roomlist = ldata.extractRooms(null, true);
    //roomlist.sortResourceListBySelectedObjectField(0);
    roomlist.sortResourceListByID();

    /** Student test*/
    StudentsList studentList = ldata.extractStudents(null, true);
    Student yan = new Student();
    yan.addCourse("GEI4421");
    yan.addCourse("GEI4501");
    studentList.addStudent(99872506,"YANNICK SYAM","",yan);
    //filter= new FilterFile(studentList.toString().getBytes());
    //filter.saveFile(pathSave+"SaveStudent.sig");

    yan = new Student();
    yan.addCourse("GEI4421");
    yan.addCourse("GEI4501");
    studentList.addStudent(99872506,"YANNICK SYAM","",yan);

    // add a student
    /*Student yan = new Student();
    yan.addCourse("GEI4421");
    yan.addCourse("GEI4501");
    Resource resource = new Resource("Yannick Sy",yan);
    resource.setMessage("2035030720003");
    studentList.setCurrentKey(99872506);
    studentList.addResource(resource);*/
    studentList.sortResourceListByID();
    //studentList.sortResourceListByKey();

    // debug activity
    //Resource activ
    /*Activity activity = new Activity();
    System.out.println(activity.addNature("1"));
    System.out.println(activity.addNature("2"));
    System.out.println(activity.addNature("2"));
    Resource activ= new Resource("GEI452", activity);*/
    ActivitiesList actList = new ActivitiesList(insList.toString().getBytes());
    System.out.println("activity added1?: "+actList.addActivity("GEI450"));
    System.out.println("activity added2?: "+actList.addActivity("GEI455"));
    Activity activ= (Activity)actList.getActivity("GEI4501").getObject();
    System.out.println(activ.addNature("1"));
    System.out.println(activ.addNature("2"));
    System.out.println(activ.addNature("2"));
    actList.removeActivity("GEI4551");
    System.out.println("activity added3?: "+actList.addActivity("GEI470"));
    //actList.addResource(activ,0);

    System.out.println(actList.toString());
    //
    filter= new FilterFile(insList.toString().getBytes());
    filter.saveFile(pathSave+"SaveInst.sig");
    filter= new FilterFile(roomlist.toString().getBytes());
    filter.saveFile(pathSave+"SaveRoom.sig");
    filter= new FilterFile(studentList.toString().getBytes());
    filter.saveFile(pathSave+"SaveStudent.sig");

  } // end main


}