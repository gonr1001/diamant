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

  public InstructorsList extractInstructors(){
    byte[]  dataloaded = preLoad(_instructorFileName);
    if (dataloaded != null) {
      //StringTokenizer st = new StringTokenizer(new String (dataloaded),"\r\n" );
      //return analyseInstructorTokens (st);
     InstructorsList _instructorsList = new InstructorsList(dataloaded,5,14);
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


  private byte[] preLoad(String str) {
    FilterFile filter = new FilterFile();
    filter.appendToCharKnown("тк");
    if (filter.validFile(str)) {
      return filter.getByteArray();
    } else return null;

  }

  public StudentsList extractStudents(){
    byte[]  dataloaded = preLoad(_studentsFileName);
    if ( dataloaded!= null) {
  StringTokenizer st = new StringTokenizer(new String(dataloaded),"\r\n" );
  return null;//analyseStudentTokens (st);
} else {// (NullPointerException npe) {
  new FatalProblemDlg("npe.toString()" );
  System.exit(52);
}
return null;
  }

  private  Vector analyseInstructorTokens(StringTokenizer st) {
    return new Vector();
  }

  private  Vector analyseStudentTokens(StringTokenizer st) {
   return new Vector();
  }

  public static void main(String[] args) {
    String path ="D:"+File.separator+"Developpements"+File.separator+"Dx"+File.separator+"data"+File.separator+"filedata.sig";
    String pathSave ="D:"+File.separator+"Developpements"+File.separator+"Dx"+File.separator+"data"+File.separator+"SAVEInst.sig";
    System.out.println("PATH: "+ path);//debug
    LoadData ldata=  new LoadData(path);
    InstructorsList insList = ldata.extractInstructors();
    insList.sortResourceListByID();
    insList.sortResourceListByKey();
    FilterFile filter= new FilterFile(insList.toString().getBytes());
    filter.saveFile(pathSave);
  } // end main


}