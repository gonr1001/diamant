/**
 *
 * Title: SetOfStudents $Revision: 1.46 $  $Date: 2004-12-01 17:16:46 $
 * Description: SetOfStudents is a class used as a data structure container.
 *              It contains the students and their attributes.
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
 * @version $Revision: 1.46 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */


package dInternal.dDataTxt;

import java.awt.Component;
import java.util.StringTokenizer;
import java.util.Vector;

import dConstants.DConst;
import dInterface.dUtil.DXTools;
import dInternal.dUtil.DXToolsMethods;
import dInternal.dUtil.DXValue;

public class SetOfStudents extends SetOfResources{
	
	private byte[] _dataloaded; //_st;// student in text format
	/** _ttWithStudents give the type of setOfStudents
	 * 0= timetable with students; 1= time table without students
	 */
	
	private String _error;
	private Vector _SOSListeners;
	/** Course length*/
	//public static int DConst.STUD_COURSE_LENGTH = 7;
	//public static int STUD_COURSE_GROUP_LENGTH = 9;
	
	/**
	 * INPUTS: byte[]  dataloaded (information from the student file in byte type)
	 * */
	public SetOfStudents(byte[] dataloaded) {
		super(1);
		_dataloaded = dataloaded;
		_error="";
		//_ttWithStudents=0;
		_SOSListeners = new Vector(1);
	}
	
	public SetOfStudents() {
		super(1);
		//_dataloaded = dataloaded;
		_error="";
		//_ttWithStudents=0;
		_SOSListeners = new Vector(1);
	}
	/**
	 *
	 * @param dataloaded
	 */
	public void setDataToLoad(byte[]  dataloaded){
		_dataloaded = dataloaded;
	}
	
	/**
	 * Analyse student data coming from a file (_dataloaded) by a finite-state machine.
	 * For a timetable with students
	 * @param beginPosition
	 * @return
	 */
	public boolean analyseTokens(int beginPosition){
		StringTokenizer st = new StringTokenizer(new String (_dataloaded), DConst.CR_LF );
		String token = st.nextToken().toString().trim();
		if (token.equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
			return analyseTokens1_6();
		} 
		return analyseTokens1_5(beginPosition);
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
	public void buildSetOfResources(int beginPosition){
		/*switch(_ttWithStudents){
		 case 0: buildSetOfResourcesWithStudents(beginPosition);
		 break;
		 case 1: //buildSetOfResourcesWithoutStudents(beginPosition);						
		 }*/
		StringTokenizer st = new StringTokenizer(new String (_dataloaded), DConst.CR_LF );
		String token = st.nextToken().toString().trim();
		if (token.equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
			buildSetOfRessources1_6();
		} else { 
			buildSetOfRessources1_5(beginPosition);
		}
	}
	
	/**
	 * @param beginPosition
	 */
	private void buildSetOfRessources1_5(int beginPosition) {
		String token;
		StringTokenizer st = new StringTokenizer(new String (_dataloaded),DConst.CR_LF );
		//int state=0;
		int position=beginPosition;
		StudentAttach student= new StudentAttach();
		String studentName="";
		/** the string beetween _ENDSTUDENTMATRICULE and _BEGINSTUDENTNAME*/
		String studentTemp="";
		long studentKey=0;
		
		while (st.hasMoreElements()){
			token = st.nextToken();
			
			switch (position){
			case 0:// number of students				
				position = 1;
				break;
			case 1:// student ID (matricule and name)
				student = new StudentAttach();
				studentName = token.substring(DConst.BEGIN_STUDENT_NAME, DConst.END_STUDENT_NAME).trim();
				studentKey = (new Integer (token.substring(DConst.BEGIN_STUDENT_MATRICULE,
						DConst.END_STUDENT_MATRICULE).trim())).longValue();
				studentTemp=token.substring(DConst.END_STUDENT_MATRICULE, DConst.BEGIN_STUDENT_NAME).trim();
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
	} //end buildSetOfRessources1_5
	
	/**
	 * 
	 */
	private void buildSetOfRessources1_6() {
		// TODO Auto-generated method stub
		
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
	private boolean analyseTokens1_5(int beginPosition){
		String token;
		StringTokenizer st = new StringTokenizer(new String (_dataloaded),DConst.CR_LF );
		//int state=0;
		int position=beginPosition;
		int line=0;
		int numberOfStudents=0;
		int countStudents=0;
		int numberOfCources=0;
		_error="";
		while (st.hasMoreElements()){
			token = st.nextToken();
			line++;
			if (!isErrorEmpty())
				return false;
			switch (position){
			case 0:// number of students
				numberOfStudents = analyseNumberOfStud(token, line);
				position = 1;
				break;
			case 1:// student ID (matricule and name)
				numberOfCources = analyseStuID (token, line);
				position = 2;
				countStudents++;
				break;
			case 2:// student courses choice
				analyseNumOfCourseChoice(token,numberOfCources,line);
				if (isErrorEmpty())
					analyseStuCourses(token, line);
				position = 1;
				break;
			}// end switch (position)
		}// end while (st.hasMoreElements())
		
		if (countStudents!=numberOfStudents){
			_error = DConst.STUD_TEXT6 +
			DConst.CR_LF + DConst.STUD_TEXT4;
			return false;
		}
		return true;
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
	private boolean analyseTokens1_6(){
		String token;
		StringTokenizer st = new StringTokenizer(new String (_dataloaded),DConst.CR_LF );
		//int state=0;
		st.nextToken();// jumping Diamant1.6
		//int position=beginPosition;
		int line=0;
		int numberOfStudents=0;
		//int countStudents=0;
		//int numberOfCources=0;
		_error="";
		while (st.hasMoreElements()){
			token = st.nextToken();
			line++;
			if (!isErrorEmpty())
				return false;
			String h=DXToolsMethods.getToken(token," ",0);
			if(h.equalsIgnoreCase(String.valueOf(DConst.LINE_DESCRIPTOR_S))){
				analyseStudentLine(token,line);
				numberOfStudents++;
			}
			if(h.equalsIgnoreCase(String.valueOf(DConst.LINE_DESCRIPTOR_C)))
				analyseCourseLine(token, line);	
			if(h.equalsIgnoreCase(String.valueOf(DConst.LINE_DESCRIPTOR_T)))
				analyseTotalLine(token, line, numberOfStudents);
			
		}// end while (st.hasMoreElements())
		if (!isErrorEmpty())
			return false;
		return true;
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
	/*private boolean analyseTokensorg(int beginPosition){
	 String token;
	 StringTokenizer st = new StringTokenizer(new String (_dataloaded),DConst.CR_LF);
	 //int state=0;
	  int position=beginPosition;
	  int line=0;
	  int numberOfStudents=0;
	  int countStudents=0;
	  int numberOfCources=0;
	  _error="";
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
	  (new Integer (token.substring(BEGINSTUDENTMATRICULE,
	  ENDSTUDENTMATRICULE).trim())).intValue();
	  }catch (NumberFormatException exc){
	  //System.out.println(exc+" --- "+token+ " *** line: "+line);//debug
	   _error = DConst.STUD_TEXT1+ line +  DConst.STUD_TEXT4  +
	   "\n" + DConst.STUD_TEXT5;
	   return false;
	   }
	   if (token.trim().length()!=ENDSTUDENTNUMBEROFCOURSE){
	   _error =DConst.STUD_TEXT2+line+  DConst.STUD_TEXT4  +
	   "\n" + DConst.STUD_TEXT5;
	   return false;
	   }
	   try{
	   numberOfCources= (new Integer (token.substring(ENDSTUDENTNAME,
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
	   if(courseToken.length()<COURSELENGTH){
	   _error = DConst.STUD_TEXT3+line+  DConst.STUD_TEXT4 +
	   "\n" + DConst.STUD_TEXT5;
	   return false;
	   }//else{// end if(courseToken.length()<_COURSELENGTH)
	   String stateInGroup="0";
	   String courseDesc= courseToken;
	   int theGroupDescNb= DXToolsMethods.countTokens(courseDesc,";");
	   if( theGroupDescNb>1){
	   courseToken= DXToolsMethods.getToken(courseDesc,";",0);
	   stateInGroup=DXToolsMethods.getToken(courseDesc,";",1);
	   }// end if( DXToolsMethods.countTokens(courseToken,";")>1)
	   if (courseToken.length()!=COURSELENGTH){
	   if(courseToken.length()<COURSEGROUPLENGTH){
	   _error = DConst.STUD_TEXT3+line+  DConst.STUD_TEXT4 +
	   "\n" + DConst.STUD_TEXT5;
	   return false;
	   }//else {// end if(courseToken.length()<_COURSEGROUPLENGTH)
	   try{
	   (new Integer (courseToken.substring(COURSELENGTH,
	   COURSEGROUPLENGTH).trim())).intValue();
	   if(theGroupDescNb>1)
	   (new Integer (stateInGroup)).intValue();
	   }catch (NumberFormatException exc){// end try
	   _error = DConst.STUD_TEXT3+line+  DConst.STUD_TEXT4 +
	   "\n" + DConst.STUD_TEXT5;
	   return false;
	   }// end catch (NumberFormatException exc)
	   //}// end else if(courseToken.length()<_COURSEGROUPLENGTH)
	    }// end if (courseToken.length()!=_COURSELENGTH)
	    //}// end else if(courseToken.length()<_COURSELENGTH)
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
	     }*/
	
	
	/**
	 * 
	 * @param str
	 * @param line
	 * @return the number of students
	 */
	private int analyseNumberOfStud(String str, int line) {
		int numberOfStudents=0;
		try{
			numberOfStudents += (new Integer (str.trim())).intValue();
		}catch(NumberFormatException exc){
			_error = DConst.STUD_TEXT6+line+ DConst.CR_LF + DConst.STUD_TEXT5;
		}
		return numberOfStudents;
	}  
	
	/**
	 * 
	 * @param str
	 * @param line
	 * @return the number of course
	 */
	private int analyseStuID(String str, int line){
		int numberOfCources= 0;
		try{
			(new Integer (str.substring(DConst.BEGIN_STUDENT_MATRICULE,
					DConst.END_STUDENT_MATRICULE).trim())).intValue();
		}catch (NumberFormatException exc){
			//System.out.println(exc+" --- "+token+ " *** line: "+line);//debug
			_error = DConst.STUD_TEXT1+ line +  DConst.STUD_TEXT4  +
			DConst.CR_LF + DConst.STUD_TEXT5;
			return numberOfCources;
		}
		if (str.trim().length()!=DConst.END_STUDENT_NUMBER_OF_COURSE){
			_error =DConst.STUD_TEXT2+line+  DConst.STUD_TEXT4  +
			DConst.CR_LF + DConst.STUD_TEXT5;
			return numberOfCources;
		}
		try{
			numberOfCources+= (new Integer (str.substring(DConst.END_STUDENT_NAME,
					str.length()).trim())).intValue();
		}catch (NumberFormatException exc){
			_error = DConst.STUD_TEXT7+ line +  DConst.STUD_TEXT4  +
			DConst.CR_LF+ DConst.STUD_TEXT5;
			return numberOfCources;
		}
		return numberOfCources;
	}
	
	/**
	 * 
	 * @param str
	 * @param line
	 * @return
	 */
	private int analyseStuCourses(String str, int line){
		StringTokenizer courses = new StringTokenizer(new String (str) );
		String courseToken;
		while (courses.hasMoreTokens()){
			courseToken=courses.nextToken();
			_error= analyseOneCourse(courseToken,line);
			if(!isErrorEmpty())
				return courses.countTokens();
		}//while (courses.hasMoreTokens())
		return courses.countTokens();
	}
	
	/**
	 * 
	 * @param str
	 * @param line
	 * @return the error
	 */
	private String analyseOneCourse(String str, int line){
		if(str.length()<DConst.STUD_COURSE_LENGTH){
			return DConst.STUD_TEXT3+line+  DConst.STUD_TEXT4 +
			DConst.CR_LF+ DConst.STUD_TEXT5;
		}//else{// end if(courseToken.length()<_COURSELENGTH)
		String stateInGroup="0";
		int theGroupDescNb= DXToolsMethods.countTokens(str,";");
		//String strTmp="";
		if( theGroupDescNb>1){
			stateInGroup=DXToolsMethods.getToken(str,";",1);
			str= DXToolsMethods.getToken(str,";",0);
		}// end if( DXToolsMethods.countTokens(courseToken,";")>1)
		if (str.length()!=DConst.STUD_COURSE_LENGTH){
			if(str.length()<DConst.STUD_COURSE_GROUP_LENGTH){
				return DConst.STUD_TEXT3 + line+  DConst.STUD_TEXT4 +
				DConst.CR_LF + DConst.STUD_TEXT5;
			}//else {// end if(courseToken.length()<_COURSEGROUPLENGTH)
			try{
				(new Integer (str.substring(DConst.STUD_COURSE_LENGTH,
						DConst.STUD_COURSE_GROUP_LENGTH).trim())).intValue();
				if(theGroupDescNb>1)
					(new Integer (stateInGroup)).intValue();
			}catch (NumberFormatException exc){// end try
				return DConst.STUD_TEXT3+ line+  DConst.STUD_TEXT4 +
				DConst.CR_LF + DConst.STUD_TEXT5;
			}// end catch (NumberFormatException exc)
		}// end if (courseToken.length()!=_COURSELENGTH)
		return "";
	}
	
	/**
	 * 
	 * @param str
	 * @param numberOfCources
	 * @param line
	 */
	private void analyseNumOfCourseChoice(String str, int numberOfCources, int line){
		StringTokenizer courses = new StringTokenizer(new String (str) );
		if(courses.countTokens()!= numberOfCources){
			_error = DConst.STUD_TEXT7+ line +  DConst.STUD_TEXT4  +
			DConst.CR_LF + DConst.STUD_TEXT5;
			
		}
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
	/*private boolean analyseTokensWithoutStudents(int beginPosition){
	 beginPosition+=0;
	 _error="";
	 return true;
	 }*/
	
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
	/*private void buildSetOfResourcesWithStudents(int beginPosition){
		String token;
		StringTokenizer st = new StringTokenizer(new String (_dataloaded),"\r\n" );
		//int state=0;
		int position=beginPosition;
		StudentAttach student= new StudentAttach();
		String studentName="";
		// the string beetween _ENDSTUDENTMATRICULE and _BEGINSTUDENTNAME
		String studentTemp="";
		long studentKey=0;
		//Resource resource;
		while (st.hasMoreElements()){
			token = st.nextToken();
			switch (position){
			case 0:// number of students
				position = 1;
				break;
			case 1:// student ID (matricule and name)
				student = new StudentAttach();
				studentName = token.substring(DConst.BEGIN_STUDENT_NAME, DConst.END_STUDENT_NAME).trim();
				studentKey = (new Integer (token.substring(DConst.BEGIN_STUDENT_MATRICULE,
						DConst.END_STUDENT_MATRICULE).trim())).longValue();
				studentTemp=token.substring(DConst.END_STUDENT_MATRICULE, DConst.BEGIN_STUDENT_NAME).trim();
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
	*/
	/**
	 * check if there is an error detected
	 * @return
	 */
	private boolean isErrorEmpty() {
		return _error.length()==0;
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
	/*private void buildSetOfResourcesWithoutStudents(int beginPosition){
	 
	 }*/
	
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
			Resource studResc= this.getResource(matricule);
			if(studResc==null){
				if (temp.length()==0)
					studentChoice.setAuxField("0000000000000");
				else
					studentChoice.setAuxField(temp);
				setCurrentKey(matricule);
				Resource resource = new Resource(name, studentChoice);
				return addResource(resource,0);
			} else if(studResc!=null){// else if(studResc==null)
				SetOfResources currentCourseList = ((StudentAttach)studResc.getAttach()).getCoursesList();
				SetOfResources newCourseList = studentChoice.getCoursesList();
				// remove exlude courses
				for (int i=0; i< currentCourseList.size(); i++){
					if(newCourseList.getIndexOfResource(currentCourseList.getResourceAt(i).getID())==-1)
						currentCourseList.removeResource(currentCourseList.getResourceAt(i).getID());
				}// end for (int i=0; i< currentCourseList.size(); i++)
				
				// add new courses
				for (int i=0; i< newCourseList.size(); i++){
					String group = DXTools.STIConvertGroup(((DXValue)newCourseList.getResourceAt(i).getAttach()).getIntValue());
					String course= newCourseList.getResourceAt(i).getID()+ group;
					((StudentAttach)studResc.getAttach()).addCourse(course);
				}// end for (int i=0; i< newCourseList.size(); i++)
				
			}// end else if(studResc==null)
		}// end if(this.getResource(matricule)!=null)
		return false;
	}
	
	/**
	 *
	 * @param listOfStudents a vector containing the list of students key
	 * @param course,  a string of 7 chars eg. GEI4421 or 9 chars
	 * (with group added) eg. GEI442101
	 * @return
	 */
	public boolean addActivityToStudents(Vector listOfStudents, String course){
		for (int i=0; i< listOfStudents.size(); i++){
			StudentAttach student= (StudentAttach)getResource(Long.parseLong(listOfStudents.get(i).toString())).getAttach();
			if(student==null)
				return false;
			student.addCourse(course);
		}// end for (int i=0; i< listOfStudents.size(); i++)
		return true;
	}
	
	/**
	 * Build a list of Resources's ID
	 * @return Vector It contents the Resources's ID plus the Resources's keys,
	 * separate by a blank space
	 * */
	/*
	 public Vector getStudentsByGroup(String activityID, String typeID, int group){
	 int IDLength = DConst.STUDENT_ID_LENGTH;
	 int keyLength = DConst.STUDENT_KEY_LENGTH;
	 int diff;
	 String ID, key;
	 Resource studentRes;
	 Vector list= new Vector();
	 for(int i=0; i< size(); i++){
	 studentRes = getResourceAt(i);
	 if(((StudentAttach)studentRes.getAttach()).isInGroup(activityID+typeID,group)){
	 ID = studentRes.getID();
	 diff = Math.abs(IDLength - ID.length());
	 for(int j = 0; j < diff; j++){
	 ID = ID+" ";
	 }
	 key = String.valueOf(studentRes.getKey());
	 diff = Math.abs(keyLength - key.length());
	 for(int j = 0; j < diff; j++){
	 key = "0"+ key;
	 }
	 list.add(ID + " " + key);
	 //list.add(studentRes.getID());
	  }//end if(((StudentAttach)studentRes.getAttach()).isInGroup(activityID+typeID,group))
	  }//end for(int i=0; i< size(); i++)
	  return list;
	  }
	  */
	
	/**
	 * @todo Cette methode n'est pas valide  22/10/03
	 * @param activityID
	 * @param typeID
	 * @param group
	 * @return
	 */
	public Vector getStudentsByGroup(String activityID, String typeID, int group){
		int IDLength = DConst.STUDENT_ID_LENGTH;
		int keyLength = DConst.STUDENT_KEY_LENGTH;
		int diff;
		String ID, key;
		Resource studentRes;
		Vector list= new Vector();
		for(int i=0; i< size(); i++){
			studentRes = getResourceAt(i);
			if(((StudentAttach)studentRes.getAttach()).isInGroup(activityID+typeID,group)){
				ID = studentRes.getID();
				diff = Math.abs(IDLength - ID.length());
				for(int j = 0; j < diff; j++){
					ID = ID+" ";
				}
				key = String.valueOf(studentRes.getKey());
				diff = Math.abs(keyLength - key.length());
				for(int j = 0; j < diff; j++){
					key = "0"+ key;
				}
				list.add(ID + " " + key);
				//list.add(studentRes.getID());
			}//end if(((StudentAttach)studentRes.getAttach()).isInGroup(activityID+typeID,group))
		}//end for(int i=0; i< size(); i++)
		return list;
	}
	
	
	
	/**
	 *
	 * @param activityID
	 * @param typeID
	 * @param group
	 * @param order
	 * @return
	 */
	public Vector getStudentsByGroup(String activityID, String typeID, int group, int order){
		int IDLength = DConst.STUDENT_ID_LENGTH;
		int keyLength = DConst.STUDENT_KEY_LENGTH;
		//int studentProgramLength = DConst.STUDENT_PROGRAM_LENGTH;
		int diff;
		String ID, key, studentProgram, str = null;
		Resource studentRes;
		Vector list= new Vector();
		if (order == 0)
			sortSetOfResourcesByID();
		if (order == 1)
			sortSetOfResourcesByKey();
		if (order == 2)
			sortSetOfResourcesBySelectedAttachField(5);//sort by _auxField
		//System.out.println("#############################################################");
		for(int i=0; i < size(); i++){
			studentRes = getResourceAt(i);
			/*System.out.println("### Student: "+studentRes.getID()+" -Activity: "+String.valueOf(activityID+typeID)+
			 " -StudentAttach Group: "+ ((StudentAttach)studentRes.getAttach()).getGroup(activityID+typeID));*/
			if(((StudentAttach)studentRes.getAttach()).isInGroup(activityID + typeID, group)){
				ID = studentRes.getID();
				diff = Math.abs(IDLength - ID.length());
				for(int j = 0; j < diff; j++)
					ID = ID+" ";
				key = String.valueOf(studentRes.getKey());
				diff = Math.abs(keyLength - key.length());
				for(int j = 0; j < diff; j++)
					key = "0"+ key;
				studentProgram = ((StudentAttach)studentRes.getAttach()).getAuxField();
				studentProgram = studentProgram.substring(0, 6);
				//System.out.println("studentProgram " + studentProgram);
				if (order == 0)
					str = ID + " " + key + " " + studentProgram;
				if (order == 1)
					str = key + " " + ID + " " + studentProgram;
				if (order == 2)
					str = studentProgram + " " + ID + " " + key;
				if(((StudentAttach)studentRes.getAttach()).isFixedInGroup(activityID+typeID,group))
					str = str + DConst.CHAR_FIXED_IN_GROUP;
				list.add(str);
				//list.add(studentRes.getID());
			}//end if(((StudentAttach)studentRes.getAttach()).isInGroup(activityID+typeID,group))
		}//end for(int i=0; i< size(); i++)
		return list;
	}
	
	
	/**
	 * 
	 * @param token
	 * @param line
	 */
	private void analyseStudentLine(String token, int line){
		if (DXToolsMethods.countTokens(token," ") <= DConst.T_IN_STUDENT_LINE){ 
			_error =  DConst.STUD_TEXT8+ line +  DConst.STUD_TEXT4  +
			DConst.CR_LF + DConst.STUD_TEXT5;
		}
		if (DXToolsMethods.countTokens(token,",") != DConst.T_IN_STUDENT_NAME){ 
			_error =  DConst.STUD_TEXT8+ line +  DConst.STUD_TEXT4  +
			DConst.CR_LF + DConst.STUD_TEXT5;
		}
		String str = DXToolsMethods.getToken(token, " " , 1);
		
		try{
			(new Integer (str.substring(DConst.BEGIN_STUDENT_MATRICULE,
					DConst.END_STUDENT_MATRICULE).trim())).intValue();
		}catch (NumberFormatException exc){
			_error = DConst.STUD_TEXT1+ line +  DConst.STUD_TEXT4  +
			DConst.CR_LF + DConst.STUD_TEXT5;			
		}
		
	}
	
	/**
	 * 
	 * @param token
	 * @param line
	 */
	private void analyseCourseLine(String token, int line){
		if (DXToolsMethods.countTokens(token," ") != DConst.T_IN_STUDENT_COURSE_LINE){ 
			_error = DConst.STUD_TEXT8+ line +  DConst.STUD_TEXT4  +
			DConst.CR_LF + DConst.STUD_TEXT5;
		}
		String str = DXToolsMethods.getToken(token," ",1);
		if (isErrorEmpty())
			_error = analyseOneCourse(str, line);
		str = DXToolsMethods.getToken(token," ",2);
		if (isErrorEmpty())
			analyseSiteCourse(str, line);
	}
	
	/**
	 * @param str
	 * @param line
	 * @return
	 */
	private void analyseSiteCourse(String str, int line) {
		if(str.length()!= DConst.STUD_SITE_LENGTH)
			_error = DConst.STUD_TEXT9+ line +  DConst.STUD_TEXT4  +
			DConst.CR_LF + DConst.STUD_TEXT5;
	}

	/**
	 * 
	 * @param token
	 * @param line
	 * @param numberofstudent
	 */
	private void analyseTotalLine(String token, int line, int numberofstudent){
		String str = DXToolsMethods.getToken(token," ",1);
		int nStudent= analyseNumberOfStud(str,line);
		if(nStudent!= numberofstudent)
			_error = DConst.STUD_TEXT6 +
				DConst.CR_LF + DConst.STUD_TEXT4;
	}
	
	/**
	 *
	 * @return
	 */
	public String getError() {
		return _error;
	}
	
	/**
	 *
	 * @param component
	 */
	public void sendEvent(Component component) {
		SetOfStudentsEvent event = new SetOfStudentsEvent(this);
		for (int i=0; i< _SOSListeners.size(); i++) {
			SetOfStudentsListener sosl = (SetOfStudentsListener) _SOSListeners.elementAt(i);
			sosl.changeInSetOfStudents(event, component);
			//System.out.println("SetOfStudents listener started: "+i);//debug
		}
	}
	
	/**
	 *
	 * @param dml
	 */
	public synchronized void addSetOfStudentsListener(SetOfStudentsListener sosl) {
		//System.out.println("SetOfStudents listener added: ");//debug
		if ((sosl==null) || (_SOSListeners.contains(sosl))){
			return;
		}
		_SOSListeners.addElement(sosl);
		//System.out.println("addSetOfStudents Listener ...");//debug
	}
	
}