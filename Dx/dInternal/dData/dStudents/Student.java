/**
*
* Title: Student $Revision: 1.4 $  $Date: 2005-02-04 16:20:11 $
* Description: Student is a class used as a data structure container.
*              It contains the student and their attributes.
*
*
* Copyright (c) 2001 by rgr.
* All rights reserved.
*
*
* This software is the confidential and proprietary information
* of rgr. ("Confidential Information").  You
* shall not disclose such Confidential Information and shall use
* it only in accordance with the terms of the license agreement
* you entered into with rgr.
*
* @version $Revision: 1.4 $
* @author  $Author: gonzrubi $
* @since JDK1.3
*/
package dInternal.dData.dStudents;


import java.util.StringTokenizer;

import dConstants.DConst;
import dInternal.DResource;
import dInternal.DValue;
import dInternal.dUtil.DXToolsMethods;


/**
 * @author syay1801
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Student extends DResource {

	/** 1= Male; 0=Female*/
	  private int _sex=1;
	  /** session of study*/
	  private int _session=0;
	  /**0=null; 1= bas; 2= moyen; 3= bon; 4= tres bon*/
	  private int _stage=0;
	  /**0= center; 1= north; 2= south; 3= west; 4= east; 5= foreigner*/
	  private int _area=0;
	  
	  /**the field to be write before name
	   * and after matricule in toWrite method*/
	  //private String _auxField="";
	  
	public Student(String studentName) {
		super(studentName, new SetOfStuCourses());		
	}
	
	/**
	 * add a student in the studentlist
	 * @param: coursesLine
	 * */
	public void addCourses(String coursesLine){
		StringTokenizer courses = new StringTokenizer(new String (coursesLine) );
		//StuCourse stuCourse= new StuCourse();
		while (courses.hasMoreTokens()){
			//student.addCourse(courses.nextToken());
			String course= courses.nextToken();
			String stateInGroup="0";
		    String courseDesc= course;
		    int theGroupDescNb= DXToolsMethods.countTokens(courseDesc,";");
		    if( theGroupDescNb>1){
		      course= DXToolsMethods.getToken(courseDesc,";",0);
		      stateInGroup=DXToolsMethods.getToken(courseDesc,";",1);
		    }// end if( DXToolsMethods.countTokens(courseToken,";")>1)
		    if (course.length()>= DConst.STUDENT_COURSE_LENGTH){
		    	StuCourse courseValue= new StuCourse();
		      //courseValue.setStringValue(course.substring(0,_COURSELENGTH));
		      if (course.length()>DConst.STUDENT_COURSE_LENGTH){
		        int group= Integer.parseInt(course.substring(
		        		DConst.STUDENT_COURSE_LENGTH,
						DConst.STUDENT_COURSE_LENGTH+2));
		        if(group>0){
		          courseValue.setIntValue(group);
		          courseValue.setBooleanValue(true);//(new Integer (stateInGroup)).intValue();
		        }
		        if( theGroupDescNb>1){
		          if((new Integer (stateInGroup)).intValue()==0)
		            courseValue.setBooleanValue(false);
		        }
		      }
		      SetOfStuCourses soCourses = (SetOfStuCourses)getAttach();
		      soCourses.addResource(new DResource(course.substring(0,
		      		DConst.STUDENT_COURSE_LENGTH),courseValue),1);
		    }
		}//while (courses.hasMoreTokens())
		
	}
	
	/**
	   * set the student sex
	   * 0= Male; 1=Female
	   * INPUT: sex, an integer
	   * */
	  public void setSex(int sex){
	    _sex= sex;
	  }

	  public String getAuxField(){
	    //return _auxField;
	  	return ((SetOfStuCourses)getAttach()).getAuxField();
	  }

	  /**
	   *set the aux field
	   * @param String the resource temporary message
	   * */
	  public void setAuxField(String message){
	    //_auxField = message;
	  	((SetOfStuCourses)getAttach()).setAuxField(message);
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
	  public long getSelectedField(int choice){
	    switch(choice){
	      case 0: return _area;
	      case 1: return _stage;
	      case 2: return _sex;
	      case 3: return _session;
	      case 4: return ((SetOfStuCourses)getAttach()).size();
	      case 5: return Long.parseLong(getAuxField());
	    }
	    return -1;
	  }

	  /**
	   * Builds the student external key
	   * @param str the key of the resource
	   * @param id the id of the resource
	   * @return
	   */
	  public String externalKey(String str, String id){
	    String temp="0000000"+ str;
	    temp= temp.substring(temp.length()-8,temp.length());
	    String nbCours="000"+ ((SetOfStuCourses)getAttach()).size();
	    nbCours= nbCours.substring(nbCours.length()-2,nbCours.length());
	    String idTemp= temp+ getAuxField()+id;
	    for(int i=idTemp.length(); i<30; i++)
	      idTemp+=" ";
	    return idTemp+nbCours;
	  }
	  
	  /**
	   * return true if the student is registered in the activity and the associate type
	   * @return boolean
	   */
	  public boolean isInActivity(String activity, int type){
	  	SetOfStuCourses courses = (SetOfStuCourses)getAttach();
	    if ( courses.getResource(activity+Integer.toString(type))!=null)
	      return true;
	    return false;
	  }

	  /**
	   * return true if the student is registered in the activity
	   * @return boolean
	   */
	  public boolean isInActivity(String activity){
	  	SetOfStuCourses courses = (SetOfStuCourses)getAttach();
	    if (courses.getResource(activity)!=null)
	      return true;
	    return false;
	  }



	 /**
	  *
	  * @param actyvityType
	  * @param group
	  * @return
	  */
	 public boolean isInGroup(String actyvityType, int group){
	   if (actyvityType.length()!= DConst.STUDENT_COURSE_LENGTH)
	     return false;
	   //else{
	   SetOfStuCourses courses = (SetOfStuCourses)getAttach();
	     DResource resource = courses.getResource(actyvityType);
	     if(resource!=null)
	       if (((DValue)resource.getAttach()).getIntValue()== group)
	         return true;
	  // }
	   return false;
	 }

	 /**
	   *
	   * @param course
	   * @param group
	   * @return
	   */
	  public boolean isFixedInGroup(String thecourse, int group){
	  	SetOfStuCourses courses = (SetOfStuCourses)getAttach();
	  	DResource courseValue;
	    group+=0;
	    courseValue = courses.getResource(thecourse.substring(0,
	    		DConst.STUDENT_COURSE_LENGTH));
	    if(courseValue!=null){
	      boolean b = ((DValue)courseValue.getAttach()).getBooleanValue();
	      return b;
	    }
	      return false;
	  }
	  
	  /**
	   *
	   * @param String course, the course the student must be assign in a group (ADM1111)
	   * @param int the group where the student must be assign
	   * @param boolean fixeInGroup. if true the student is fixed in this group, false
	   * otherwise
	   */
	  public void setInGroup(String course, int group, boolean fixeInGroup){
	  	SetOfStuCourses courses = (SetOfStuCourses)getAttach();
	  	DResource courseValue;
	    if (course.length()>=DConst.STUDENT_COURSE_LENGTH){
	      courseValue = courses.getResource(course.substring(0,DConst.STUDENT_COURSE_LENGTH));
	      if(courseValue!=null){
	        ((DValue)courseValue.getAttach()).setIntValue(group);
	        ((DValue)courseValue.getAttach()).setBooleanValue(fixeInGroup);
	      }// end if(courseValue!=null)
	    }else{// end if (course.length()>=_COURSELENGTH)
	      courseValue = courses.getResource(course);
	      if(courseValue!=null){
	        ((DValue)courseValue.getAttach()).setIntValue(group);
	        ((DValue)courseValue.getAttach()).setBooleanValue(fixeInGroup);
	      }// end if(courseValue!=null)

	    }
	  }
	  
	  /**
	  *
	  * @param actyvityType
	  * @return
	  */
	 public int getGroup(String actyvityType){
	 	SetOfStuCourses courses = (SetOfStuCourses)getAttach();
	   if (actyvityType.length()!= DConst.STUDENT_COURSE_LENGTH)
	     return -1;
	   //else{
	     DResource resource = courses.getResource(actyvityType);
	     if(resource!=null)
	       return((DValue)resource.getAttach()).getIntValue();
	   //}
	   return -1;
	 }
	 
	 public SetOfStuCourses getCoursesList(){
	 	return (SetOfStuCourses)this.getAttach();
	 }

}
