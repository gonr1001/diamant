package dInternal;

/**
 * <p>Title: DX</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */

import java.util.StringTokenizer;
import java.util.Vector;
import com.iLib.gDialog.FatalProblemDlg;

public class StudentsList extends ResourceList{

  private byte[] _dataloaded; //_st;// student in text format
  private static final int _BEGINSTUDENTMATRICULE=0;
  private static final int _ENDSTUDENTMATRICULE=8;
  private static final int _BEGINSTUDENTNAME=21;
  private static final int _ENDSTUDENTNAME=30;
  private static final int _BEGINSTUDENTNUMBEROFCOURSE=30;
  private static final int _ENDSTUDENTNUMBEROFCOURSE=32;
  /** Course length*/
    private int _COURSELENGTH = 7;

  /**
   * INPUTS: byte[]  dataloaded (information from the student file in byte type)
   * */
  public StudentsList(byte[] dataloaded) {
    super(1);
    _dataloaded = dataloaded;
  }

  /**
   * Analyse student data coming from a file (_dataloaded) by a finite-state machine
   * @param int  beginPosition, an integer (start state of the finite-state machine)
   * @return  boolean. "true" if the analysis proceeded successfully and false otherwise
   *
   * <p>
   * Requires: _dataloaded must be initiaklized by the constructor.
   *
   * <p>
   * Modifies: nothing.
   *
   * <p>
   * Effect: the _dataloaded array is scanned looking for bad data,
   *         if some bad data is found the a FatalProblemDlg is displayed
   *         then the application exits
   * */
  public boolean analyseTokens(int beginPosition){
    String token;
    StringTokenizer st = new StringTokenizer(new String (_dataloaded),"\r\n" );
    int state=0;
    int position=beginPosition;
    int line=0;
    while (st.hasMoreElements()){
      token = st.nextToken();
      line++;
      switch (position){
        case 0:// number of students
          position = 1;
          break;
        case 1:// student ID (matricule and name)
          try{
            (new Integer (token.substring(_BEGINSTUDENTMATRICULE,
                _ENDSTUDENTMATRICULE).trim())).intValue();
          }catch (NumberFormatException exc){
            //System.out.println(exc+" --- "+token+ " *** line: "+line);//debug
            new FatalProblemDlg(
            "Wrong student matricule at line: "+ line +  "in the student file:" +
            "\n" + "I was in StudentList class and in analyseTokens method ");
            System.exit(1);
          }
          if (token.substring(_BEGINSTUDENTNAME, _ENDSTUDENTNAME).trim().length()==0){
            new FatalProblemDlg(
            "Wrong student name at line: "+line+  "in the student file:" +
            "\n" + "I was in StudentList class and in analyseTokens method ");
            System.exit(1);
          }
          position = 2;
          break;
        case 2:// student courses choice
          StringTokenizer courses = new StringTokenizer(new String (token) );
          String courseToken;
          while (courses.hasMoreTokens()){
            if(courses.nextToken().length()<_COURSELENGTH){
              new FatalProblemDlg(
                  "Wrong student course choice at line: "+line+  "in the student file:" +
                  "\n" + "I was in StudentList class and in analyseTokens method ");
              System.exit(1);
            }
          }//while (courses.hasMoreTokens())
          position = 1;
          break;
      }// end switch (position)
    }// end while (st.hasMoreElements())
    return true;
  }

  /**
   * build a ListOfStudent using
   * student data coming from a file (_dataloaed) by a finite-state machine
   * @param int  beginPosition, an integer (start state of the finite-state machine)
   * @return  boolean. "true" if the analysis proceeded successfully and false otherwise
   *
   * <p>
   * Requires: _dataloaded must be scanned by analyseTokens.
   *
   * <p>
   * Modifies: the associated ListOfResources is created and
   *           built.
   *
   * <p>
   * Effect: nothing.
   * */
  public void buildStudentList(int beginPosition){
    String token;
    StringTokenizer st = new StringTokenizer(new String (_dataloaded),"\r\n" );
    int state=0;
    int position=beginPosition;
    Student student= new Student();
    String studentName="";
    /** the string beetween _ENDSTUDENTMATRICULE and _BEGINSTUDENTNAME*/
    String studentTemp="";
    long studentKey=0;
    Resource resource;
    while (st.hasMoreElements()){
      token = st.nextToken();
      switch (position){
        case 0:// number of students
          position = 1;
          break;
        case 1:// student ID (matricule and name)
          student = new Student();
          studentName = token.substring(_BEGINSTUDENTNAME, _ENDSTUDENTNAME).trim();
          studentKey = (new Integer (token.substring(_BEGINSTUDENTMATRICULE,
                _ENDSTUDENTMATRICULE).trim())).longValue();
          studentTemp=token.substring(_ENDSTUDENTMATRICULE, _BEGINSTUDENTNAME).trim();
          //resource = new Resource();
          position = 2;
          break;
        case 2:// student courses choice
          StringTokenizer courses = new StringTokenizer(new String (token) );
          while (courses.hasMoreTokens()){
            student.addCourse(courses.nextToken());
          }//while (courses.hasMoreTokens())
          addStudent(studentKey, studentName, studentTemp, student);
          position = 1;
          break;
      }// end switch (position)
    }// end while (st.hasMoreElements())
  }

  /**
   * add a student in the studentlist
   * INPUT: matricule (an long int); studentName (a string); temp (a string of
   * 13 chars); studentChoice a student object
   * OUTPUT: boolean, "true" if student added and if studentChoice contains an element
   *   false otherwise
   * */
  public boolean addStudent(long matricule, String name, String temp, Student studentChoice){
    if (studentChoice.getCourses().size()!=0){
      //Student newStudent = new Student();
      Resource resource = new Resource(name, studentChoice);
      if (temp.length()==0)
        resource.setMessage("0000000000000");
      else
        resource.setMessage(temp);
      setCurrentKey(matricule);
      return addResource(resource,0);
    }// end if(this.getResource(matricule)!=null)
    return false;
  }

  /**
   * remove a student in the student list
   * INPUT: matricule (an long int)
   * OUTPUT: boolean, "true" if student removed succesfully and false if student
   * did'nt found
   * */
  private boolean removeStudent(long matricule){
    int index= getIndexOfResource(matricule);
    if(index!=-1){
      removeResource(matricule);
      return true;
    }
    return false;
  }

}