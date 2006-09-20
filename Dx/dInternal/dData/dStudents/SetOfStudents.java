/**
 *
 * Title: SetOfStudents $Revision: 1.15 $  $Date: 2006-09-20 03:09:04 $
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
 * @version $Revision: 1.15 $
 * @author  $Author: hara2602 $
 * @since JDK1.3
 */
package dInternal.dData.dStudents;

import java.util.Vector;

import dConstants.DConst;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DValue;
import dInternal.DataExchange;
import dInternal.dData.StandardCollection;
import dInternal.dUtil.DXToolsMethods;
import eLib.exit.dialog.DxExceptionDlg;
import eLib.exit.exception.DxException;

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
	//private Vector _soStudentsListeners;
	/**
	 * 
	 */
	public SetOfStudents() {
		super();
	}

	public boolean analyseTokens(DataExchange de, int beginPosition) {
		beginPosition += 0;
		de.toString();
		return false;
	}

	/**
	 * 
	 * @param de
	 * @param beginPosition
	 */
	public void buildSetOfResources(DataExchange de, int beginPosition) {
		String nameLine = DXToolsMethods.getToken(de.getContents(),
				DConst.CR_LF, DConst.STUDENT_NAME_LINE).trim();
		String coursesLine = DXToolsMethods.getToken(de.getContents(),
				DConst.CR_LF, DConst.STUDENT_COURSE_LINE).trim();

		addCourses(de.getHeader(), nameLine, coursesLine);
	}

	/**
	 * 
	 * @param version
	 * @param stuLine
	 * @param courses
	 */
	public void addCourses(String version, String stuLine, String courses) {
		if (version.equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
			try {
				addCourses1_6(stuLine, courses);
			} catch (StringIndexOutOfBoundsException e) {
				new DxExceptionDlg(e.getMessage()+DConst.CR_LF+stuLine);
			//	System.exit(-1);
			} 
		} else {
			addCourses1_5(stuLine, courses);
		}
	}

	/**
	 * 
	 */
	public String getError() {
		return "";
	}

	public String toWrite() {
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

	public boolean addActivityToStudents(Vector listOfStudents, String course) {
		for (int i = 0; i < listOfStudents.size(); i++) {
			Student student = (Student) getResource(Long
					.parseLong(listOfStudents.get(i).toString()));
			if (student == null)
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

	public Vector getStudentsByGroup(String activityID, String typeID, int group) {
		int iDLength = DConst.STUDENT_ID_LENGTH;
		int keyLength = DConst.STUDENT_KEY_LENGTH;
		int diff;
		String iD, key;
		Student studentRes;
		Vector <String> list = new Vector <String>();
		for (int i = 0; i < size(); i++) {
			studentRes = (Student) getResourceAt(i);
			if (studentRes.isInGroup(activityID + typeID, group)) {
				iD = studentRes.getID().trim();
				diff = Math.abs(iDLength - iD.length());
				for (int j = 0; j < diff; j++) {
					iD = iD + " ";
				}
				key = String.valueOf(studentRes.getKey());
				diff = Math.abs(keyLength - key.length());
				for (int j = 0; j < diff; j++) {
					key = "0" + key;
				}
				list.add(iD + " " + key);
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

	public Vector getStudentsByGroup(String activityID, String typeID,
			int group, int order) {
		int iDLength = DConst.STUDENT_ID_LENGTH;
		int keyLength = DConst.STUDENT_KEY_LENGTH;
		
		int diff;
		String iD, key, studentProgram, str = null;
		Student studentRes;
		Vector <String> list = new Vector <String>();
		if (order == 0)
			sortSetOfResourcesByID();
		if (order == 1)
			sortSetOfResourcesByKey();
		if (order == 2)
			sortSetOfResourcesBySelectedAttachField(5);//sort by _auxField
		
		for (int i = 0; i < size(); i++) {
			studentRes = (Student) getResourceAt(i);
			if (studentRes.isInGroup(activityID + typeID, group)) {
				iD = studentRes.getID();
				diff = Math.abs(iDLength - iD.length());
				for (int j = 0; j < diff; j++)
					iD = iD + " ";
				iD = iD.substring(0, DConst.STUDENT_KEY_LENGTH); //rgr 4 juillet
				iD = iD + " ";
				key = String.valueOf(studentRes.getKey());
				diff = Math.abs(keyLength - key.length());
				for (int j = 0; j < diff; j++)
					key = "0" + key;
				studentProgram = studentRes.getAuxField();
				studentProgram = studentProgram.substring(0, 6);
				//System.out.println("studentProgram " + studentProgram);
				if (order == 0)
					str = iD + " " + key + " " + studentProgram;
				if (order == 1)
					str = key + " " + iD + " " + studentProgram;
				if (order == 2)
					str = studentProgram + " " + iD + " " + key;
				if (studentRes.isFixedInGroup(activityID + typeID, group))
					str = str + DConst.CHAR_FIXED_IN_GROUP;
				list.add(str);
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
	 * @return if order = 0, return ID, key, studentProgram;
	 * if order = 1, return key, ID,  studentProgram;
	 * if order = 2, return studentProgram, key, ID;
	 */

	public Vector getStudentsByGroupTable(String activityID, String typeID,
			int group, int order) {
		Vector <Vector>v = new Vector <Vector>();
		Vector <String> vID = new Vector <String>();
		Vector <String> vKey = new Vector <String> ();
		Vector <String> vSelField = new Vector <String>();
		Vector <String> vFixState = new Vector <String>();
		
		int keyLength = DConst.STUDENT_KEY_LENGTH;
		
		int diff;
		String iD, key, studentProgram;
		Student studentRes;
		//Vector list= new Vector();
		if (order == 0)
			sortSetOfResourcesByID();
		if (order == 1)
			sortSetOfResourcesByKey();
		if (order == 2)
			sortSetOfResourcesBySelectedAttachField(5);//sort by _auxField
		
		for (int i = 0; i < size(); i++) {
			studentRes = (Student) getResourceAt(i);
			if (studentRes.isInGroup(activityID + typeID, group)) {
				iD = studentRes.getID();
				key = String.valueOf(studentRes.getKey());
				diff = Math.abs(keyLength - key.length());
				for (int j = 0; j < diff; j++)
					key = "0" + key;
				studentProgram = studentRes.getAuxField();
				studentProgram = studentProgram.substring(0, 6);
				vID.add(iD);
				vKey.add(key);
				vSelField.add(studentProgram);
				if (studentRes.isFixedInGroup(activityID + typeID, group))
					vFixState.add("" + DConst.CHAR_FIXED_IN_GROUP);
				else
					vFixState.add(" ");
			}//end if(((StudentAttach)studentRes.getAttach()).isInGroup(activityID+typeID,group))
		}//end for(int i=0; i< size(); i++)
		if (order == 0) {
			v.add(vID);//str = ID + " " + key + " " + studentProgram;
			v.add(vKey);
			v.add(vSelField);
		}
		if (order == 1) {
			v.add(vKey);//str = key + " " + ID + " " + studentProgram;
			v.add(vID);
			v.add(vSelField);
		}
		if (order == 2) {
			v.add(vSelField);//str = studentProgram + " " + ID + " " + key;
			v.add(vID);
			v.add(vKey);
		}
		v.add(vFixState);
		return v;
	}

	/**
	 * Create an instance of set of students from a given vector
	 * <p> this set of student is sort by key
	 * @param vec
	 * @return
	 */
	public static DSetOfResources createAStudentInstance(Vector vec) {
		DSetOfResources sor = new StandardCollection();
		if (vec.size() >= 3) {
			Vector v1 = (Vector) vec.get(0);
			Vector v2 = (Vector) vec.get(1);
			Vector v3 = (Vector) vec.get(2);
			Vector v4 = new Vector(v1.size());
			if (vec.size() == 4)
				v4 = (Vector) vec.get(3);
			for (int i = 0; i < v1.size(); i++) {
				long matricule = Long.parseLong(v1.get(i).toString());
				String name = v2.get(i).toString();
				int program = Integer.parseInt(v3.get(i).toString());
				String status = "";
				if (i < v4.size())
					status = (String) v4.get(i);
				DValue value = new DValue();
				value.setStringValue(status);
				value.setIntValue(program);
				DResource rsc = new DResource(name, value);
				sor.setCurrentKey(matricule);
				sor.addResource(rsc, 0);
			}

		}
		return sor;
	}

	/**
	 * 
	 * @param sos
	 * @return
	 */
	public static Vector createAVectorInstance(DSetOfResources sos) {
		Vector v = new Vector();
		Vector [] vec = {new Vector(), new Vector<String>(), new Vector(),
				new Vector<String>() };
		int keyLength = DConst.STUDENT_KEY_LENGTH;
		int diff;
		String key;
		for (int i = 0; i < sos.size(); i++) {
			DResource rsc = sos.getResourceAt(i);
			key = String.valueOf(rsc.getKey());
			diff = Math.abs(keyLength - key.length());
			for (int j = 0; j < diff; j++)
				key = "0" + key;
			vec[0].add(key);
			vec[1].add(rsc.getID());
			DValue value = (DValue) rsc.getAttach();
			vec[2].add(String.valueOf(value.getIntValue()));
			vec[3].add(value.getStringValue());

		}
		for (int i = 0; i < vec.length; i++)
			v.add(vec[i]);
		return v;
	}

	/**
	 *
	 * @param dml
	 */
	/*		  public synchronized void addSetOfStudentsListener(SetOfStudentsListener sosl) {
	 if (_soStudentsListeners.contains(sosl)){
	 return;
	 }
	 _soStudentsListeners.addElement(sosl);
	 }*/
	/**
	 *
	 * @param component
	 */
	/*		 public void sendEvent(Component component) {
	 SetOfStudentsEvent event = new SetOfStudentsEvent(this);
	 for (int i=0; i< _soStudentsListeners.size(); i++) {
	 SetOfStudentsListener sosl = (SetOfStudentsListener) _soStudentsListeners.elementAt(i);
	 sosl.changeInSetOfStudents(event, component);
	 }
	 }*/

	public Student getStudent(String str) {
		return (Student) this.getResource(str);
	}

	public Student getStudent(long mat) {
		return (Student) this.getResource(mat);
	}

	/**
	 * @param id
	 * @return
	 */
	public String toWrite(String site) {
		//String reslist="";
		StringBuffer save = new StringBuffer();
		if (getSetOfResources().size() > 0) {
			//DResource siteRsc;
			Student stu;
			SetOfStuCourses stuCourses;
			for (int i = 0; i < getSetOfResources().size() - 1; i++) {
				stu = ((Student) getSetOfResources().get(i));
				stuCourses = (SetOfStuCourses) stu.getAttach();
				//reslist+= externalKey(stu)+DConst.CR_LF;
				save.append(externalKey(stu) + DConst.CR_LF);
				//reslist+= stuCourses.toWrite(site)+DConst.CR_LF;
				save.append(stuCourses.toWrite(site) + DConst.CR_LF);
			}
			stu = ((Student) getSetOfResources().get(
					getSetOfResources().size() - 1));
			stuCourses = (SetOfStuCourses) stu.getAttach();
			//reslist+= externalKey(stu)+DConst.CR_LF;
			save.append(externalKey(stu) + DConst.CR_LF);
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
	private String externalKey(Student stu) {
		String temp = "0000000" + Long.toString(stu.getKey());
		temp = temp.substring(temp.length() - 8, temp.length());
		String idTemp = DConst.LINE_DESCRIPTOR_S + " " + temp
				+ stu.getAuxField() + " " + stu.getID();
		for (int i = idTemp.length(); i < 30; i++)
			idTemp += " ";
		return idTemp;
	}

	/**
	 * 
	 */
	private String buildLines1_6(String nameLine, String coursesLine) {
		String matricule = DXToolsMethods.getToken(nameLine,
				DConst.STUDENT_TOKEN_SEPARATOR, DConst.STUDENT_MAT_TOKEN);
		int countToken = DXToolsMethods.countTokens(nameLine,
				DConst.STUDENT_TOKEN_SEPARATOR);
		String name = "";
		for (int i = DConst.STUDENT_FIRST_NAME_TOKEN; i < countToken; i++) {
			name += DXToolsMethods.getToken(nameLine,
					DConst.STUDENT_TOKEN_SEPARATOR, i)
					+ DConst.STUDENT_TOKEN_SEPARATOR;
		}
		coursesLine = DXToolsMethods.getToken(coursesLine,
				DConst.STUDENT_TOKEN_SEPARATOR, DConst.STUDENT_COURSE_TOKEN)
				.trim();
		return matricule + name + DConst.CR_LF + coursesLine;
	}

	/**
	 * 
	 * @param stuLine
	 * @param courses
	 */
	private void addCourses1_5(String stuLine, String courses) {
		/*String coursesLine = DXToolsMethods.getToken(de.getContents(), DConst.CR_LF,
		 DConst.STUDENT_COURSE_LINE).trim();*/

		String studentName = stuLine.substring(DConst.BEGIN_STUDENT_NAME,
				DConst.END_STUDENT_NAME).trim();
		long studentKey = (new Integer(stuLine.substring(
				DConst.BEGIN_STUDENT_MATRICULE, DConst.END_STUDENT_MATRICULE)
				.trim())).longValue();
		String studentTemp = stuLine.substring(DConst.END_STUDENT_MATRICULE,
				DConst.BEGIN_STUDENT_NAME).trim();
		Student student = (Student) getResource(studentKey);
		if (student == null) {
			student = new Student(studentName);
			student.setAuxField(studentTemp);
			setCurrentKey(studentKey);
			addResource(student, 0);
		}
		student.addCourses(courses);
	}

	/**
	 * @param stuLine
	 * @param courses
	 */
	private void addCourses1_6(String nameLine, String coursesLine) throws StringIndexOutOfBoundsException{
		String stuInfo = buildLines1_6(nameLine, coursesLine);
		String stuLine = DXToolsMethods.getToken(stuInfo, DConst.CR_LF,
				DConst.STUDENT_NAME_LINE).trim();
		String studentName = stuLine.substring(DConst.BEGIN_STUDENT_NAME)
				.trim();
	//	System.out.println(stuLine);
		String courses = DXToolsMethods.getToken(stuInfo, DConst.CR_LF,
				DConst.STUDENT_COURSE_LINE).trim();
		long studentKey = (new Integer(stuLine.substring(
				DConst.BEGIN_STUDENT_MATRICULE, DConst.END_STUDENT_MATRICULE)
				.trim())).longValue();
		String studentTemp = stuLine.substring(DConst.END_STUDENT_MATRICULE,
				DConst.BEGIN_STUDENT_NAME).trim();
		Student student = (Student) getResource(studentKey);
		if (student == null) {
			student = new Student(studentName);
			student.setAuxField(studentTemp);
			setCurrentKey(studentKey);
			addResource(student, 0);
		}
		student.addCourses(courses);
	}

}
