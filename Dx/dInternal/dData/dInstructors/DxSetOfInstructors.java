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

public class DxSetOfInstructors {
	
	private ArrayList<DxInstructor> _vInstructors;
	
	public DxSetOfInstructors(){
		_vInstructors=new ArrayList<DxInstructor>();
	}
	
	public void addInstructor(DxInstructor iNewInstructor)
	{
		_vInstructors.add(iNewInstructor);
	}
	
	public boolean removeInstructor(int nIndex) {
		if(isValidIndex(nIndex))
		{
			_vInstructors.remove(nIndex);
			return true;
		}
		return false;
	}

	public void sortIntructors() {
		Collections.sort((List<DxInstructor>)_vInstructors,DxInstructor.NameComparator);		
	}
	
	public int instructorCount() {
		return _vInstructors.size();
	}
	
	public String toWrite() {
		return null;
	}

	public Vector getNamesVector() {
		return null;
	}

	public String getInstructorName(int nIndex) {
		if(isValidIndex(nIndex))
		{
			return _vInstructors.get(nIndex).getInstructorName();
		}
		return null;
	}
	
	public int getInstructorID(int nIndex) {
		if(isValidIndex(nIndex))
		{
			return _vInstructors.get(nIndex).getInstructorID();
		}
		return -1;
	}
	
	public DxAvailability getInstructorAvailability(int nIndex) {
		if(isValidIndex(nIndex))
		{
			return _vInstructors.get(nIndex).getInstructorAvailability();
		}
		return null;
		
	}

	public boolean setInstructorAvailability(int nIndex, DxAvailability dxaNewAva) {
		// TODO Auto-generated method stub
		if(isValidIndex(nIndex))
		{
			_vInstructors.get(nIndex).setInstructorAvailability(dxaNewAva);
			return true; 
		}
		return false;
		
	}

	public boolean setInstructorName(int nIndex, String sNewName) {
		// TODO Auto-generated method stub
		if(isValidIndex(nIndex))
		{
			_vInstructors.get(nIndex).setInstructorName(sNewName);
			return true; 
		}
		return false;
	}
	
	private boolean isValidIndex(int nIndex)
	{
		return ((nIndex >= 0) && (nIndex < _vInstructors.size()));
	}
}
