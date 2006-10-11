/**
 * Created on 2006-09-29
 * 
 * Title: DxSetOfStudents.java 
 * 
 *
 * Copyright (c) 2006 by rgr.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information
 * of rgr. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with rgr.
 *
 * @author HAROUNA Abdoul-Kader
 * @since JDK1.3
 */

package dInternal.dData.dStudents;


import dInternal.dData.DxResource;
import dInternal.dData.DxSetOfResources;

/** 
 * Description: DxSetOfStudents is a class used to:
 * <p>
 * Holds many students in a single object.
 * <p>
 * 
 */

public class DxSetOfStudents extends DxSetOfResources {

	/**
	 * Adds an Student to the set
	 * @param sName New Student name
	 */
	public void addStudent(String sName) {
		DxStudent dxiTemp = new DxStudent(sName);
		this.addResource(dxiTemp);
	}

	/**
	 * Adds an Student to the set
	 * @param dxiNewStud New Student
	 */
	public void addStudent(DxStudent dxiNewStud) {
		this.addResource(dxiNewStud);

	}

	/**
	 * Adds a set of Students to the set
	 * 
	 * @param dxsosNew
	 *            Set of Students that have to be added to the current set
	 */
	public void addSetOfStudents(DxSetOfStudents dxsosNew) {
		this.addSetOfResources(dxsosNew);
	}


	protected DxResource findEquivalent(DxResource dxrSearch) {
		return this.getStudent(dxrSearch.getName());
	}

	public DxStudent getStudent(long lKey) {
		return (DxStudent) this.getResource(lKey);
	}

	public DxStudent getStudent(String sName) {
		return (DxStudent) this.getResource(sName);
	}

	/**
	 * Retreives the name of an Student in the set
	 * 
	 * @param nIndex
	 *            Index of the Student whose name is wanted
	 * @return String The name of the Student, null if the index was invalid
	 */
	public String getStudentName(long lKey) {
		return this.getResourceName(lKey);
	}

	/**
	 * Retreives the key of an Student by his name. If there are two
	 * Students with the same name, there is no guaranty on which of the
	 * Students key will be returned
	 * 
	 * @param sName
	 *            Name of the Student we need the key
	 * @return long The key of Student sName, -1 if Student not found
	 */
	public long getStudentKey(String sName) {
		return this.getResourceKey(sName);
	}


	protected void merge(DxResource dxrModify, DxResource dxrNew) {
		// If an Student already exist, we want to keep courses as
		// they might have been modified
	}

	/**
	 * Removes an Student from the set
	 * 
	 * @param lKey
	 *            Key of the Student to be removed
	 */
	public void removeStudent(long lKey) {
		this.removeResource(lKey);
	}

	/***************************************************************************
	 * Temporary function that return value that should be written to a dia
	 * file. Eventually, a class structure as the one used for reading should be
	 * implemented. An idea for DxSetOfStudentsReader would have a method
	 * like: String toWrite(DxSetOfStudent dxsos) which could output data for
	 * a dia file, or eventually sql querries.
	 * 
	 * @return A string containing all the Students informations
	 **************************************************************************/
	public String toWrite() {
		StringBuffer reslist = new StringBuffer();

		return reslist.toString();
	}
}
