/**
*
* Title: SetOfStuSites $Revision: 1.3 $  $Date: 2004-12-16 19:21:00 $
* Description: SetOfStuSites is a class used as a data structure container.
*              It contains the sites where students can take a
*              course and their attributes.
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
* @version $Revision: 1.3 $
* @author  $Author: gonzrubi $
* @since JDK1.3
*/
package dInternal.dData.dStudents;


import java.util.StringTokenizer;
//import java.util.Vector;

import dConstants.DConst;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DataExchange;
import dInternal.dData.ByteArrayMsg;
import dInternal.dUtil.DXToolsMethods;

public class SetOfStuSites extends DSetOfResources {

	private String _error;
	//private Vector _soSitesListeners;
	
	/**
	 * 
	 */
	public SetOfStuSites() {
		super();
		_error ="";
		//_soSitesListeners= new Vector();
	}
	
	/**
	 * Analyse student data coming from a file (_dataloaded) by a finite-state machine.
	 * For a timetable with students
	 * @param beginPosition
	 * @return
	 */
	public boolean analyseTokens(DataExchange de, int beginPosition) {

	    if (de.getHeader().equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
	    	return analyseTokens1_6(de.getContents().getBytes());
	    } //else if (token.equalsIgnoreCase(DConst.FILE_VER_NAME1_7)) {
	    return analyseTokens1_5(de.getContents().getBytes(), beginPosition);
		//return false;
	      
	} //end analyseTokens
	

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
	public void buildSetOfResources(DataExchange de, int beginPosition) {
	    if (de.getHeader().equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
	    	buildSetOfResources1_6(de.getContents().getBytes());
	    } else {
	    	buildSetOfResources1_5(de.getContents().getBytes(), beginPosition);
	    }
	      
	} //end buildSetOfResources
	
	/**
	   *
	   * @param component
	   */
	/*
	 public void sendEvent(Component component) {
	   SetOfStudentsEvent event = new SetOfStudentsEvent(this);
	   for (int i=0; i< _soSitesListeners.size(); i++) {
	   	SetOfStudentsListener sosl = (SetOfStudentsListener) _soSitesListeners.elementAt(i);
	     sosl.changeInSetOfStudents(event, component);
	   }
	  }
*/
	  /**
	   * 
	   * @param soStu
	   */
	/*
	  public synchronized void addSetOfSitesListener(SetOfStudentsListener soStu) {
	    if (_soSitesListeners.contains(soStu)){
	      return;
	    }
	    _soSitesListeners.addElement(soStu);
	  }

*/
	/**
	 * 
	 */
	public String getError() {
		return _error;
	}

	/**
	 * 
	 */
	public String toWrite() {
		
		return "";
	}

	/**
	 * 
	 */
	public long getSelectedField() {
		
		return 0;
	}
	
	/**
	 * @param dataloaded
	 * @param beginPosition
	 * @return
	 */
	private boolean analyseTokens1_5(byte[] dataloaded, int beginPosition) {
		String token;
		StringTokenizer st = new StringTokenizer(new String (dataloaded),DConst.CR_LF );
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
	 * @param dataloaded
	 * @return
	 */
	private boolean analyseTokens1_6(byte[] dataloaded) {
		String token;
		StringTokenizer st = new StringTokenizer(new String (dataloaded),DConst.CR_LF );
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
	 * @param beginPosition
	 */
	private void buildSetOfResources1_5(byte[]  dataloaded, int beginPosition) {
		String token;
		StringTokenizer st = new StringTokenizer(new String (dataloaded),DConst.CR_LF );
		int position=beginPosition;
		String nameLine="";
		String coursesLine="";
		SetOfStudents setOfStudent = new SetOfStudents();
		addResource(new DResource(DConst.STUDENT_STANDARD_SITE,
				setOfStudent),1);
		while (st.hasMoreElements()){
			token = st.nextToken();
			
			switch (position){
			case 0:// number of students				
				position = 1;
				break;
			case 1:// student ID (matricule and name)
				nameLine = token+DConst.CR_LF;
				position = 2;
				break;
			case 2:// student courses choice
				coursesLine= token;
	      		DataExchange dEx= new ByteArrayMsg(DConst.FILE_VER_NAME1_5, nameLine+coursesLine);
	      		setOfStudent.buildSetOfResources(dEx,0);
				position = 1;
				break;
			}// end switch (position)
		}// end while (st.hasMoreElements())
	} //end buildSetOfRessources1_5
	
	/**
	 * 
	 */
	private void buildSetOfResources1_6(byte[]  dataloaded) {
		String token;
		StringTokenizer st = new StringTokenizer(new String (dataloaded),DConst.CR_LF );
		//st.nextToken();
		//int position=beginPosition;
		String nameLine="";
		String coursesLine="";
		DataExchange dEx= new ByteArrayMsg(DConst.FILE_VER_NAME1_6, nameLine+coursesLine);
		//boolean needToBuild=false;
		//SetOfStudents setOfStudent = new SetOfStudents();
		/*addResource(new DResource(DConst.STUDENT_STANDARD_SITE,
				setOfStudent),1);*/
		while (st.hasMoreElements()){
			token = st.nextToken();
			String lineDesc= DXToolsMethods.getToken(token, DConst.STUDENT_TOKEN_SEPARATOR, 
					DConst.STUDENT_LINE_DESC_TOKEN).trim();
			char desc= lineDesc.toUpperCase().charAt(DConst.STUDENT_LINE_DESC_TOKEN);
			switch (desc){
			case 'A':// Version of the file				
				//position = 1;
				break;
			case DConst.LINE_DESCRIPTOR_S:// student ID (matricule and name)
				//if(needToBuild){
					
		      		//setOfStudent.buildSetOfResources(dEx,0);
		      		//coursesLine="";
		      		//needToBuild=false;
				//}
				nameLine = token+DConst.CR_LF;
				//position = 2;
				break;
			case DConst.LINE_DESCRIPTOR_C:// student courses choice
				String site = DXToolsMethods.getToken(token, DConst.STUDENT_TOKEN_SEPARATOR, 
						DConst.STUDENT_SITE_TOKEN).trim();
				DResource stResc = getResource(site);
				//SetOfStudents sos = (SetOfStudents)getResource(site).getAttach();
				if(stResc==null){
					stResc = new DResource(site,new SetOfStudents());
					//sos= new SetOfStudents();
					addResource(stResc,1);
				}
				
				SetOfStudents sos = (SetOfStudents)stResc.getAttach();
				dEx.setContents(nameLine+token);
				sos.buildSetOfResources(dEx,0);
				break;
			case DConst.LINE_DESCRIPTOR_T:// total number of students
	      		
				break;
			}// end switch (position) 
		}// end while (st.hasMoreElements())
		
	}
	
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
	 * check if there is an error detected
	 * @return
	 */
	private boolean isErrorEmpty() {
		return _error.length()==0;
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
	

	

}
