/**
*
* Title: SetOfStudents $Revision: 1.6 $  $Date: 2005-02-08 16:24:46 $
* Description: SetOfStudents is a class used as a data structure container.
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
* @version $Revision: 1.6 $
* @author  $Author: gonzrubi $
* @since JDK1.3
*/
package dInternal.dData.dStudents;

//import java.util.Vector;

import java.awt.Component;
import java.util.Vector;

import dConstants.DConst;
//import dInterface.dUtil.DXTools;
//import dInternal.DResource;
//import dInternal.DResource;
//import dInternal.DResource;
//import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DataExchange;
//import dInternal.dData.ByteArrayMsg;
//import dInternal.dData.dRooms.SetOfRoomsEvent;
//mport dInternal.dData.dRooms.SetOfRoomsListener;
import dInternal.dUtil.DXToolsMethods;

/**
 * @author syay1801
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SetOfStudents extends DSetOfResources {
    /**
     * @associates SetOfStudentsListener 
     */
	private Vector _soStudentsListeners;
	/**
	 * 
	 */
	public SetOfStudents() {
		super();
		_soStudentsListeners =new Vector();
		// TODO Auto-generated constructor stub
	}

	public boolean analyseTokens(DataExchange de, int beginPosition){
	  	beginPosition+=0;
	  	de.toString();
	  	//dataloaded[0]+=0;
	    return false;
	  }
		
	/**
	 * 
	 * @param de
	 * @param beginPosition
	 */	
	public  void buildSetOfResources(DataExchange de, int beginPosition){
	  	beginPosition+=0;
	  	String nameLine= DXToolsMethods.getToken(de.getContents(), DConst.CR_LF,
	  			DConst.STUDENT_NAME_LINE).trim();
	  	String coursesLine=DXToolsMethods.getToken(de.getContents(), DConst.CR_LF,
	  			DConst.STUDENT_COURSE_LINE).trim();
	  	
	  	addCourses(de.getHeader(), nameLine, coursesLine);
	  }
	
	/**
	 * 
	 * @param version
	 * @param stuLine
	 * @param courses
	 */
	public void addCourses(String version, String stuLine, String courses){
		if (version.equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
			addCourses1_6(stuLine,courses);
		}else{
			addCourses1_5(stuLine,courses);
		}
	}
	
	/**
	 * 
	 * @param stuLine
	 * @param courses
	 */
	private void addCourses1_5(String stuLine, String courses){
		/*String coursesLine = DXToolsMethods.getToken(de.getContents(), DConst.CR_LF,
	  			DConst.STUDENT_COURSE_LINE).trim();*/
		
	  	String studentName = stuLine.substring(DConst.BEGIN_STUDENT_NAME, DConst.END_STUDENT_NAME).trim();
		long studentKey = (new Integer (stuLine.substring(DConst.BEGIN_STUDENT_MATRICULE,
				DConst.END_STUDENT_MATRICULE).trim())).longValue();
		String studentTemp=stuLine.substring(DConst.END_STUDENT_MATRICULE, DConst.BEGIN_STUDENT_NAME).trim();
		Student student= (Student) getResource(studentKey);
		if (student==null){
			student = new Student(studentName);
		  	student.setAuxField(studentTemp);
		  	setCurrentKey(studentKey);
		  	addResource(student,0);
		}
		student.addCourses(courses);
	}
	
	/**
	 * 
	 * @param stuLine
	 * @param courses
	 */
	private void addCourses1_6(String nameLine, String coursesLine){
		/*String coursesLine = DXToolsMethods.getToken(de.getContents(), DConst.CR_LF,
	  			DConst.STUDENT_COURSE_LINE).trim();*/
		
	  	//String studentName = stuLine.substring(DConst.BEGIN_STUDENT_NAME, DConst.END_STUDENT_NAME).trim();
		String stuInfo=buildLines1_6(nameLine,coursesLine);
		String stuLine= DXToolsMethods.getToken(stuInfo, DConst.CR_LF,
	  			DConst.STUDENT_NAME_LINE).trim();
		String studentName= stuLine.substring(DConst.BEGIN_STUDENT_NAME).trim();
		String courses= DXToolsMethods.getToken(stuInfo, DConst.CR_LF,
	  			DConst.STUDENT_COURSE_LINE).trim();
		long studentKey = (new Integer (stuLine.substring(DConst.BEGIN_STUDENT_MATRICULE,
				DConst.END_STUDENT_MATRICULE).trim())).longValue();
		String studentTemp=stuLine.substring(DConst.END_STUDENT_MATRICULE, DConst.BEGIN_STUDENT_NAME).trim();
		Student student= (Student) getResource(studentKey);
		if (student==null){
			student = new Student(studentName);
		  	student.setAuxField(studentTemp);
		  	setCurrentKey(studentKey);
		  	addResource(student,0);
		}
		student.addCourses(courses);
	}
		
	/**
	 * 
	 */	
	public  String getError(){
	    return "";
	  }
		
		public String toWrite(){
			return "";
		}

		/* (non-Javadoc)
		 * @see dInternal.DObject#getSelectedField()
		 */
		public long getSelectedField() {
			return 0;
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
				Student student= (Student)getResource(Long.parseLong(listOfStudents.get(i).toString()));
				if(student==null)
					return false;
				student.addCourses(course);
			}// end for (int i=0; i< listOfStudents.size(); i++)
			return true;
		}
		
		
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
			Student studentRes;
			Vector list= new Vector();
			for(int i=0; i< size(); i++){
				studentRes = (Student)getResourceAt(i);
				if(studentRes.isInGroup(activityID+typeID,group)){
					ID = studentRes.getID().trim();
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
			Student studentRes;
			Vector list= new Vector();
			if (order == 0)
				sortSetOfResourcesByID();
			if (order == 1)
				sortSetOfResourcesByKey();
			if (order == 2)
				sortSetOfResourcesBySelectedAttachField(5);//sort by _auxField
			//System.out.println("#############################################################");
			for(int i=0; i < size(); i++){
				studentRes = (Student)getResourceAt(i);
				if(studentRes.isInGroup(activityID + typeID, group)){
					ID = studentRes.getID();
					diff = Math.abs(IDLength - ID.length());
					for(int j = 0; j < diff; j++)
						ID = ID+" ";
					key = String.valueOf(studentRes.getKey());
					diff = Math.abs(keyLength - key.length());
					for(int j = 0; j < diff; j++)
						key = "0"+ key;
					studentProgram = studentRes.getAuxField();
					studentProgram = studentProgram.substring(0, 6);
					//System.out.println("studentProgram " + studentProgram);
					if (order == 0)
						str = ID + " " + key + " " + studentProgram;
					if (order == 1)
						str = key + " " + ID + " " + studentProgram;
					if (order == 2)
						str = studentProgram + " " + ID + " " + key;
					if(studentRes.isFixedInGroup(activityID+typeID,group))
						str = str + DConst.CHAR_FIXED_IN_GROUP;
					list.add(str);
					//list.add(studentRes.getID());
				}//end if(((StudentAttach)studentRes.getAttach()).isInGroup(activityID+typeID,group))
			}//end for(int i=0; i< size(); i++)
			return list;
		}
		
		/**
		 * 
		 */
		private String buildLines1_6(String nameLine, String coursesLine) {
			String matricule= DXToolsMethods.getToken(nameLine,
		  			DConst.STUDENT_TOKEN_SEPARATOR, DConst.STUDENT_MAT_TOKEN);
		  	int countToken = DXToolsMethods.countTokens(nameLine,DConst.STUDENT_TOKEN_SEPARATOR);
		  	String name="";
		  	for (int i=DConst.STUDENT_FIRST_NAME_TOKEN; i< countToken; i++){
		  		name += DXToolsMethods.getToken(nameLine,
			  			DConst.STUDENT_TOKEN_SEPARATOR, i)+DConst.STUDENT_TOKEN_SEPARATOR;
		  	}
		  	coursesLine = DXToolsMethods.getToken(coursesLine, DConst.STUDENT_TOKEN_SEPARATOR,
		  			DConst.STUDENT_COURSE_TOKEN).trim();
		  	return matricule+name+DConst.CR_LF+coursesLine;
		}
		
		
		/**
		   *
		   * @param dml
		   */
		  public synchronized void addSetOfStudentsListener(SetOfStudentsListener sosl) {
		    if (_soStudentsListeners.contains(sosl)){
		      return;
		    }
		    _soStudentsListeners.addElement(sosl);
		  }
		  /**
		   *
		   * @param component
		   */
		 public void sendEvent(Component component) {
		 	SetOfStudentsEvent event = new SetOfStudentsEvent(this);
		   for (int i=0; i< _soStudentsListeners.size(); i++) {
		     SetOfStudentsListener sosl = (SetOfStudentsListener) _soStudentsListeners.elementAt(i);
		     sosl.changeInSetOfStudents(event, component);
		   }
		  }
		

		 
		 public Student getStudent(String str){		 	
		 	return (Student) this.getResource(str);
		 }
		 
		 public Student getStudent(long mat){		 	
		 	return (Student) this.getResource(mat);
		 }

		/**
		 * @param id
		 * @return
		 */
		public String toWrite(String site) {
			//String reslist="";
			StringBuffer save= new StringBuffer();
			if(getSetOfResources().size()>0){
		    	//DResource siteRsc;
		    	Student stu;
		    	SetOfStuCourses stuCourses;
		        for (int i=0; i< getSetOfResources().size()-1; i++){
		        	stu = ((Student)getSetOfResources().get(i));
		        	stuCourses= (SetOfStuCourses)stu.getAttach();
		        	//reslist+= externalKey(stu)+DConst.CR_LF;
		        	save.append(externalKey(stu)+DConst.CR_LF);
		        	//reslist+= stuCourses.toWrite(site)+DConst.CR_LF;
		        	save.append(stuCourses.toWrite(site)+DConst.CR_LF);
		        }
		        stu = ((Student)getSetOfResources().get(getSetOfResources().size()-1));
		        stuCourses= (SetOfStuCourses)stu.getAttach();
	        	//reslist+= externalKey(stu)+DConst.CR_LF;
		        save.append(externalKey(stu)+DConst.CR_LF);
		        //reslist+= stuCourses.toWrite(site);
		        save.append(stuCourses.toWrite(site));
		      }	   
			return save.toString();
		}
		
		/**
		   * Builds the student external key
		   * @param str the key of the resource
		   * @param id the id of the resource
		   * @return
		   */
		  private String externalKey(Student stu){
		  	String temp="0000000"+ Long.toString(stu.getKey());
		    temp= temp.substring(temp.length()-8,temp.length());
		    String idTemp= DConst.LINE_DESCRIPTOR_S+ " "+temp
							+ stu.getAuxField()+ " "+stu.getID();
		    for(int i=idTemp.length(); i<30; i++)
		      idTemp+=" ";
		    return idTemp;
		  }
		 
}
