package dInternal.dData;

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
import dResources.DConst;
import dInternal.dUtil.DXToolsMethods;

public class SetOfStudents extends SetOfResources{

  private byte[] _dataloaded; //_st;// student in text format
  private static final int _BEGINSTUDENTMATRICULE=0;
  private static final int _ENDSTUDENTMATRICULE=8;
  private static final int _BEGINSTUDENTNAME=21;
  private static final int _ENDSTUDENTNAME=30;
  private static final int _BEGINSTUDENTNUMBEROFCOURSE=30;
  private static final int _ENDSTUDENTNUMBEROFCOURSE=32;
  private String _error="";
  /** Course length*/
  private int _COURSELENGTH = 7;
  private int _COURSEGROUPLENGTH = 9;

  /**
   * INPUTS: byte[]  dataloaded (information from the student file in byte type)
   * */
  public SetOfStudents(byte[] dataloaded) {
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
    int numberOfStudents=0;
    int countStudents=0;
    int numberOfCources=0;
    while (st.hasMoreElements()){
      token = st.nextToken();
      line++;
      switch (position){
        case 0:// number of students
          try{
            numberOfStudents= (new Integer (token.trim())).intValue();
          }catch(NumberFormatException exc){
            _error = DConst.STUD_TEXT6+ "\n" + DConst.STUD_TEXT5;
            return false;
          }
          position = 1;
          break;
        case 1:// student ID (matricule and name)
          try{
             (new Integer (token.substring(_BEGINSTUDENTMATRICULE,
                _ENDSTUDENTMATRICULE).trim())).intValue();
          }catch (NumberFormatException exc){
            //System.out.println(exc+" --- "+token+ " *** line: "+line);//debug
            _error = DConst.STUD_TEXT1+ line +  DConst.STUD_TEXT4  +
            "\n" + DConst.STUD_TEXT5;
            return false;
          }
          if (token.trim().length()!=_ENDSTUDENTNUMBEROFCOURSE){
            _error =DConst.STUD_TEXT2+line+  DConst.STUD_TEXT4  +
            "\n" + DConst.STUD_TEXT5;
            return false;
          }
          try{
             numberOfCources= (new Integer (token.substring(_ENDSTUDENTNAME,
                token.length()).trim())).intValue();
          }catch (NumberFormatException exc){
            _error = DConst.STUD_TEXT7+ line +  DConst.STUD_TEXT4  +
            "\n" + DConst.STUD_TEXT5;
            return false;
          }
          position = 2;
          countStudents++;
          break;
        case 2:// student courses choice
          StringTokenizer courses = new StringTokenizer(new String (token) );
          String courseToken;
          if(courses.countTokens()!= numberOfCources){
            _error = DConst.STUD_TEXT7+ line +  DConst.STUD_TEXT4  +
           "\n" + DConst.STUD_TEXT5;
            return false;
          }
          while (courses.hasMoreTokens()){
            courseToken=courses.nextToken();
            if(courseToken.length()<_COURSELENGTH){
              _error = DConst.STUD_TEXT3+line+  DConst.STUD_TEXT4 +
                  "\n" + DConst.STUD_TEXT5;
              return false;
            }else{
              if (courseToken.length()!=_COURSELENGTH){
                if(courseToken.length()<_COURSEGROUPLENGTH){
                  _error = DConst.STUD_TEXT3+line+  DConst.STUD_TEXT4 +
                           "\n" + DConst.STUD_TEXT5;
                  return false;
                }else{
                  try{
                    (new Integer (courseToken.substring(_COURSELENGTH,
                        _COURSEGROUPLENGTH).trim())).intValue();
                  }catch (NumberFormatException exc){
                    _error = DConst.STUD_TEXT3+line+  DConst.STUD_TEXT4 +
                             "\n" + DConst.STUD_TEXT5;
                    return false;
                  }
                }
              }
            }
          }//while (courses.hasMoreTokens())
          position = 1;
          break;
      }// end switch (position)
    }// end while (st.hasMoreElements())

    if (countStudents!=numberOfStudents){
      _error = DConst.STUD_TEXT6 +
            "\n" + DConst.STUD_TEXT4;
      return false;
    }
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
    StudentAttach student= new StudentAttach();
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
          student = new StudentAttach();
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
   * @param: matricule (an long int)
   * @param: studentName (a string)
   * @param: temp (a string of 13 chars); studentChoice a student object
   * @return: boolean, "true" if student added and if studentChoice contains an element
   *   false otherwise
   * */
  public boolean addStudent(long matricule, String name, String temp, StudentAttach studentChoice){
    if (studentChoice.getCoursesList().size()!=0){
      //StudentAttach newStudent = new StudentAttach();

      if (temp.length()==0)
        studentChoice.setAuxField("0000000000000");
      else
        studentChoice.setAuxField(temp);
      setCurrentKey(matricule);
      Resource resource = new Resource(name, studentChoice);
      return addResource(resource,0);
    }// end if(this.getResource(matricule)!=null)
    return false;
  }

  /**
  * Build a list of Resources's ID
  * @return Vector It contents the Resources's ID
  * */
  public Vector getStudentsByGroup(String activityID, String typeID, String groupID){
    Resource studentRes;
    Vector list= new Vector();
    if (DXToolsMethods.isIntValue(groupID)){
      for(int i=0; i< size(); i++){
        studentRes = getResourceAt(i);
        if(((StudentAttach)studentRes.getAttach()).isInGroup(activityID+typeID,Integer.parseInt(groupID)))
          list.add(studentRes.getID());
      }//end for(int i=0; i< size(); i++)
    }// end if (DXToolsMethods.isIntValue(groupID))

    return list;
  }

  /**
   *
   * @return
   */
  public String getError() {
    return _error;
  }

}