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

import com.iLib.gDialog.FatalProblemDlg;
import com.iLib.gIO.FilterFile;

public class LoadData {
  Vector _v;
  String _instructorFileName;
  String _roomsFileName;
  String _activitiesFileName;
  String _studentsFileName;

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
      /*new DisplayFatalProblem(
          "Unable to filter a file" +
          str +
          "\n" +
          "I was in DLoadData constructor ");*/ //ys
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

  private Vector extractInstructors(){
    byte[]  dataloaded = preLoad(_instructorFileName);
    if (dataloaded != null) {
      //StringTokenizer st = new StringTokenizer(new String (dataloaded),"\r\n" );
      //return analyseInstructorTokens (st);
      InstructorsList anInst = new InstructorsList(dataloaded,5,14);
      return anInst.analyseTokens();
    } else {// (NullPointerException npe) {
      new FatalProblemDlg("npe.toString()" );
      System.exit(52);
    }
    return null;
  }


  private byte[] preLoad(String str) {
    FilterFile filter = new FilterFile();
    if (filter.validFile(str)) {
      return filter.getByteArray();
    } else return null;

  }

  private Vector extractStudents(){
    byte[]  dataloaded = preLoad(_studentsFileName);
    if ( dataloaded!= null) {
  StringTokenizer st = new StringTokenizer(new String(dataloaded),"\r\n" );
  return analyseStudentTokens (st);
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



}