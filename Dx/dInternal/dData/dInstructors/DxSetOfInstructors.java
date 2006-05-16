/**
 * Created on May 4, 2006
 * 
 * 
 * Title: DxSetOfInstructors.java 
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
 * 
 * 
 */
package dInternal.dData.dInstructors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import dInternal.dData.DxAvailability;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxSetOfInstructors is a class used to:
 * <p>
 * Hold many instructors in a single object. This object will provide access to
 * instructors to reduce interconnections between classes.
 * <p>
 * 
 */

public class DxSetOfInstructors{
	
	private ArrayList<DxInstructor> _vInstructors;
	
	/**
	 * Constructor
	 */
	public DxSetOfInstructors(){
		_vInstructors=new ArrayList<DxInstructor>();
	}
	
	/**
	 * Adds an instructor to the set
	 * 
	 * @param iNewInstructor The new instructor to be inserted
	 */
	public void addInstructor(DxInstructor iNewInstructor)
	{
		_vInstructors.add(iNewInstructor);
	}
	
	/**
	 * Removes an instructor from the set
	 * 
	 * @param nIndex Index of the instructor to be removed
	 * @return boolean Returns true if the index was valid, false otherwise 
	 */
	public boolean removeInstructor(int nIndex) {
		if(isValidIndex(nIndex))
		{
			_vInstructors.remove(nIndex);
			return true;
		}
		return false;
	}

	/**
	 * Sort the set of instructors by their name 
	 */
	public void sortIntructors() {
		Collections.sort((List<DxInstructor>)_vInstructors,DxInstructor.NameComparator);		
	}
	
	/**
	 * Return the number of instructors currently in the set
	 * 
	 * @return int The number of instructor currently in the set 
	 */
	public int size() {
		return _vInstructors.size();
	}
	
	/**
	 * Removes an instructor from the set
	 * 
	 * @param nIndex Index of the instructor to be removed
	 * @return boolean Returns true if the index was valid, false otherwise 
	 */
	public String toWrite() {
		return null;
	}

	/**
	 * Removes an instructor from the set
	 * 
	 * @param nIndex Index of the instructor to be removed
	 * @return boolean Returns true if the index was valid, false otherwise 
	 */
	public Vector getNamesVector() {
		return null;
	}

	/**
	 * Retreives the name of an instructor in the set
	 * 
	 * @param nIndex Index of the instructor whose name is wanted
	 * @return String The name of the instructor, null if the index was invalid 
	 */
	public String getInstructorName(int nIndex) {
		if(isValidIndex(nIndex))
		{
			return _vInstructors.get(nIndex).getInstructorName();
		}
		return null;
	}
	
	/**
	 * Retreives the ID of an instructor in the set
	 * 
	 * @param nIndex Index of the instructor whose ID is wanted
	 * @return int ID of the instructor, -1 if the index was invalid 
	 */
	public int getInstructorID(int nIndex) {
		if(isValidIndex(nIndex))
		{
			return _vInstructors.get(nIndex).getInstructorID();
		}
		return -1;
	}
	
	/**
	 * Retreives the availability of an instructor in the set
	 * 
	 * @param nIndex Index of the instructor whose availability is wanted
	 * @return DxAvailability The availability of the instructor, null if the index was invalid 
	 */
	public DxAvailability getInstructorAvailability(int nIndex) {
		if(isValidIndex(nIndex))
		{
			return _vInstructors.get(nIndex).getInstructorAvailability();
		}
		return null;
		
	}
	
	/**
	 * Retreives the availability of an instructor in the set
	 * 
	 * @param nIndex Index of the instructor whose availability is wanted
	 * @return DxAvailability The availability of the instructor, null if the index was invalid 
	 */
	public int[][] getInstructorAvaMatrix(int nIndex) {
		if(isValidIndex(nIndex))
		{
			return _vInstructors.get(nIndex).getInstructorAvailability().getMatrixAvailability();
		}
		return null;
		
	}

	/**
	 * Modify the availability of instructor at index nIndex
	 * 
	 * @param nIndex Index of the instructor that availability needs to be modified
	 * @return boolean true if the index was valid, false otherwise 
	 */
	public boolean setInstructorAvailability(int nIndex, DxAvailability dxaNewAva) {
		// TODO Auto-generated method stub
		if(isValidIndex(nIndex))
		{
			_vInstructors.get(nIndex).setInstructorAvailability(dxaNewAva);
			return true; 
		}
		return false;
		
	}

	
	/**
	 * Modify the name of instructor at index nIndex
	 * 
	 * @param nIndex Index of the instructor that name needs to be modified
	 * @return boolean true if the index was valid, false otherwise 
	 */
	public boolean setInstructorName(int nIndex, String sNewName) {
		// TODO Auto-generated method stub
		if(isValidIndex(nIndex))
		{
			_vInstructors.get(nIndex).setInstructorName(sNewName);
			return true; 
		}
		return false;
	}
	
	/**
	 * Validate that nIndex is a valid instrutor in the set
	 * 
	 * @param nIndex Index to be verified
	 * @return boolean true if the index was valid, false otherwise 
	 */
	private boolean isValidIndex(int nIndex)
	{
		return ((nIndex >= 0) && (nIndex < _vInstructors.size()));
	}
	
	public Vector<String> getNamesVector(int sortField)
	{
		Vector<String> vReturn=new Vector<String>();
		if(sortField==1)
		{
			for(int i=0;i<this.size();i++)
			{
				vReturn.add(this.getInstructorName(i));
			}
		}
		return vReturn;
	}

	public int getIndexofInstructor(String string) {
		// TODO Auto-generated method stub
		return 0;
	}
}
