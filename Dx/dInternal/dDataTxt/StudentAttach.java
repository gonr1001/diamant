package dInternal.dData;

/**
 * <p>Title: DX</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @authors rgr, ysyam, alexander
 * @version 1.0
 */
import java.util.Vector;
import dInternal.dUtil.DXObject;
import dInternal.dUtil.DXValue;
import dInternal.dData.SetOfResources;

public class StudentAttach extends DXObject{

/**
 * fields description
 * */

  /** 1= Male; 0=Female*/
  private int _sex=1;
  /** session of study*/
  private int _session=0;
  /**0=null; 1= bas; 2= moyen; 3= bon; 4= tres bon*/
  private int _stage=0;
  /**0= center; 1= north; 2= south; 3= west; 4= east; 5= foreigner*/
  private int _area=0;
  /**contains DXValue object. the used field in this object are:
   * _intValue: it gives the group where the student is affect in this course
   * _booleanValue: it is "true" if the student is fixed in the group "false" otherwise
   */
  private SetOfResources _courses;
  /**the field to be write before name
   * and after matricule in toWrite method*/
  private String _auxField="";

  /**
   * constructor
   * initialize the _courses vector
   * INPUT: course a string of 7 chars
   * */
  public  StudentAttach() {
    _courses= new SetOfResources(1);
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
   *set the aux field
   * @param String the resource temporary message
   * */
  public void setAuxField(String message){
    _auxField = message;
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
   * INPUT: course, a string of 7 chars eg. GEI4421 or 9 chars
   * (with group added) eg. GEI442101
   * OUTPUT: a boolean. "true" if course has been added
   * and "false" otherwise (the course already exist or the course name size is
   * smaller than 7 )
   * */
  public boolean addCourse(String course){
    if (course.length()>=_COURSELENGTH){
      DXValue courseValue= new DXValue();
      //courseValue.setStringValue(course.substring(0,_COURSELENGTH));
      if (course.length()>_COURSELENGTH){
        courseValue.setIntValue(Integer.parseInt(course.substring(
            _COURSELENGTH,_COURSELENGTH+2)));
        courseValue.setBooleanValue(true);
      }
      return  _courses.addResource(new Resource(course.substring(0,_COURSELENGTH),courseValue),1);
    }
    return false;
  }

  /**
   *
   * */
  public SetOfResources getCoursesList(){
    return _courses;
  }

 /**
   * set the student courses choice
   * INPUT: courses, a vector of studentchoice
   * */
  public void setCoursesList(SetOfResources courses){
    _courses = courses;
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
   *
   * */
  public String externalKey(String str, String id){
    String temp="0000000"+ str;
    temp= temp.substring(temp.length()-8,temp.length());
    String nbCours="000"+_courses.size();
    nbCours= nbCours.substring(nbCours.length()-2,nbCours.length());
    String idTemp= temp+ _auxField+id;
    for(int i=idTemp.length(); i<30; i++)
      idTemp+=" ";
    return idTemp+nbCours;
  }

  /**
   * Print student courses choice information
   * OUTPUT: String of courses choice
   * */
  public String toWrite(){
    String str="";
    DXValue choice;
    for (int i=0; i< _courses.size(); i++){
      str+= _courses.getResourceAt(i).getID();
      choice = (DXValue)_courses.getResourceAt(i).getAttach();
      if (choice.getIntValue()>0){
        String group= "00"+Integer.toString(choice.getIntValue());
        str+= group.substring(group.length()-2,group.length());
      }
      if (i< _courses.size()-1)
        str+=" ";
    }
    return str;
  }

  /**
  * compare this resource with the specified resource
  * @param resource the specified resource
  * @return bolean true if this resource and the specified resource are equals
  * false if they are not equals
  * */
 public boolean isEquals(DXObject stud){
   StudentAttach studAttach = (StudentAttach)stud;
   if(_area!=studAttach._area)
     return false;
   if(!_auxField.equals(studAttach._auxField))
     return false;
   if(_session!=studAttach._session)
     return false;
   if(_sex!=studAttach._sex)
     return false;
   if(_stage!=studAttach._stage)
     return false;
   if(!_courses.isEquals(studAttach._courses))
     return false;
   return true;
 }

  /** Course length*/
  private int _COURSELENGTH = 7;

}