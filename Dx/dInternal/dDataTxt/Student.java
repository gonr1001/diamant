package dInternal;

/**
 * <p>Title: DX</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @authors rgr, ysyam, alexander
 * @version 1.0
 */
import java.util.Vector;
import dResources.StudentChoice;
import dResources.DXObject;

public class Student extends DXObject{

/**
 * fields description
 * */

  /** 0= Male; 1=Female*/
  private int _sex=0;
  /** session of study*/
  private int _session=0;
  /**0=null; 1= bas; 2= moyen; 3= bon; 4= tres bon*/
  private int _stage=0;
  /**0= center; 1= north; 2= south; 3= west; 4= east; 5= foreigner*/
  private int _area=0;
  /**contains StudentChoice object*/
  private Vector _courses;

  /**
   * constructor
   * initialize the _courses vector
   * INPUT: course a string of 7 chars
   * */
  public  Student() {
    _courses= new Vector();
  }

  /**
   * set the student sex
   * 0= Male; 1=Female
   * INPUT: sex, an integer
   * */
  public void setSex(int sex){
    _sex= sex;
  }

  /**
   * set the student session
   * INPUT: session, an integer
   * */
  public void setSession(int session){
    _session= session;
  }

  /**
   * set the student stage of comprehension
   * 0=null; 1= bas; 2= moyen; 3= bon; 4= tres bon
   * INPUT: stage, an integer
   * */
  public void setStage(int stage){
    _stage= stage;
  }

  /**
   * set the student area
   * 0= center; 1= north; 2= south; 3= west; 4= east; 5= foreigner
   * INPUT: area, an integer
   * */
  public void setArea(int area){
    _area= area;
  }

  /**
   * add a course in the student courses choice
   * INPUT: course, a string
   * OUTPUT: a boolean. "true" if course has been added
   * and "false" otherwise (the course already exist or the course name size is
   * smaller than 7 )
   * */
  public boolean addCourse(String course){
    boolean addcourse;
    StudentChoice stchoice= new StudentChoice();
    if (this.getCourse(course)==null){
      if( stchoice.setCourse(course)){
        _courses.add(stchoice);
        return true;
      }
    }
    return false;
  }

  /**
   * set the student course choice
   * INPUT: courses, a studentchoice
   * OUTPUT: a boolean. "true" if course found and set and "false" otherwise
   * */
  public boolean setCourses(StudentChoice courses){
    for (int i=0; i< _courses.size(); i++){
      if (((StudentChoice)_courses.get(i)).getCourse().equalsIgnoreCase(courses.getCourse())){
        _courses.setElementAt(courses,i);
        return true;
      }
    }
    return false;
  }

  /**
   * set the student courses choice
   * INPUT: courses, a vector of studentchoice
   * */
  public void setCourses(Vector courses){
    _courses = (Vector)courses.clone();
  }

  /**
   * set a group of course to student
   * INPUTS: course, a string (the course name); group, an integer (the group)
   * OUTPUT: a boolean. "true" if course found and "false" otherwise
   * */
  public boolean setGroupInCourse(String course, int group){
    for (int i=0; i< _courses.size(); i++)
      if (((StudentChoice)_courses.get(i)).getCourse().equalsIgnoreCase(course)){
        ((StudentChoice)_courses.get(i)).setGroup(group);
        return true;
      }
    return false;
  }

  /**
   * set a state of the student in a group
   * INPUTS: course, a string (the course name); state, a boolean
   * OUTPUT: a boolean. "true" if course found and "false" otherwise
   * */
  public boolean setStateInCourse(String course, boolean state){
    for (int i=0; i< _courses.size(); i++)
      if (((StudentChoice)_courses.get(i)).getCourse().equalsIgnoreCase(course)){
        ((StudentChoice)_courses.get(i)).setState(state);
        return true;
      }
    return false;
  }

  /**
   * get a course where the student is
   * INPUT: course, a string (the course name)
   * OUTPUT: a studentChoice object. it return "null" if the course does'nt found
   * */
  public StudentChoice getCourse(String course){
    for (int i=0; i< _courses.size(); i++)
      if (((StudentChoice)_courses.get(i)).getCourse().equalsIgnoreCase(course))
        return (StudentChoice)_courses.get(i);
    return null;
  }

  /**
   * get _courses vector that contains all student choices
   * OUTPUT: _courses, a vector of StudentChoice
   * */
  public Vector getCourses(){
    return _courses;
  }

  /**
   * get a group where the student is in course
   * INPUT: course, a string (the course name)
   * OUTPUT: an integer. 0= if the course does'nt found and the group number otherwise
   * */
  public int getGroupInCourse(String course){
    for (int i=0; i< _courses.size(); i++)
      if (((StudentChoice)_courses.get(i)).getCourse().equalsIgnoreCase(course))
        return ((StudentChoice)_courses.get(i)).getGroup();
    return 0;
  }

  /**
   * get the state where the student is in course
   * INPUT: course, a string (the course name)
   * OUTPUT: an integer. false= if the course does'nt found or if the state is false
   * is realy false and the group number otherwise
   * */
  public boolean getStateInCourse(String course){
    for (int i=0; i< _courses.size(); i++)
      if (((StudentChoice)_courses.get(i)).getCourse().equalsIgnoreCase(course))
        return ((StudentChoice)_courses.get(i)).getState();
    return false;
  }


  /**
   * it return area of the student
   * OUTPUT: an integer, the area
   * */
  public int getArea(){
    return _area;
  }

  /**
   * it return sex of the student
   * OUTPUT: an integer, the sex
   * */
  public int getSex(){
    return _sex;
  }

  /**
   * it return the stage of comprehension of the student
   * OUTPUT: an integer, the stage
   * */
  public int getStage(){
    return _stage;
  }

  /**
   * it return the session of the student
   * OUTPUT: an integer, the session
   * */
  public int getSession(){
    return _session;
  }

  /**
   * return the value of the selected key
   * INPUT: choice, an integer. choice = 0 return _area; 1 return _stage
   * 2 return _sex; 3 return _session; 4 return number of courses
   * OUTPUT: an integer. it return -1 if choice is invalid
   * */
  public int getSelectedField(int choice){
    switch(choice){
      case 0: return _area;
      case 1: return _stage;
      case 2: return _sex;
      case 3: return _session;
      case 4: return _courses.size();
    }
    return -1;
  }

  /**
   * Print student courses choice information
   * OUTPUT: String of courses choice
   * */
  public String toString(){
    String choice="";
    for (int i=0; i< _courses.size()-1; i++)
      choice+=((StudentChoice)_courses.get(i)).getCourse()+" ";
    if(_courses.size()!=0)
      choice+=((StudentChoice)_courses.get(_courses.size()-1)).getCourse();
    return choice;
  }

}