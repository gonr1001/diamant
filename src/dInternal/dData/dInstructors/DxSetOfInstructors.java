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

import java.util.Iterator;

import dConstants.DConst;
import dInternal.dData.DxAvailability;
import dInternal.dData.DxResource;
import dInternal.dData.DxSetOfResources;

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

public class DxSetOfInstructors extends DxSetOfResources {

	/**
	 * Adds an instructor to the set
	 * 
	 * @param sName
	 *            New instructor name
	 * @param dxaAva
	 *            New instructor availability
	 */
	public void addInstructor(String sName, DxAvailability dxaAva) {
		DxInstructor dxiTemp = new DxInstructor(sName, dxaAva);
		this.addResource(dxiTemp);
	}

	/**
	 * Adds an instructor to the set
	 * 
	 * @param dxiNewInst
	 *            New instructor
	 */
	public void addInstructor(DxInstructor dxiNewInst) {
		this.addResource(dxiNewInst);

	}

	/**
	 * Adds a set of instructors to the set
	 * 
	 * @param dxsoiNew
	 *            Set of instructors that have to be added to the current set
	 */
	public void addSetOfInstructors(DxSetOfInstructors dxsoiNew) {
		this.addSetOfResources(dxsoiNew);
	}

	// TODO: a faire
	public void alwaysAvailable() {
		System.out
				.println("DxSetOfIntructors.alwaysAvailable must be implemented");
	}

	@Override
	protected DxResource findEquivalent(DxResource dxrSearch) {
		return this.getInstructor(dxrSearch.getName());
	}

	public DxInstructor getInstructor(long lKey) {
		return (DxInstructor) this.getResource(lKey);
	}

	public DxInstructor getInstructor(String sName) {
		return (DxInstructor) this.getResource(sName);
	}

	/**
	 * Retreives the name of an instructor in the set
	 * 
	 * @param nIndex
	 *            Index of the instructor whose name is wanted
	 * @return String The name of the instructor, null if the index was invalid
	 */
	public String getInstructorName(long lKey) {
		return this.getResourceName(lKey);
	}

	/**
	 * Retrieves the key of an instructor by his name. If there are two
	 * instructors with the same name, there is no guaranty on which of the
	 * instructors key will be returned
	 * 
	 * @param sName
	 *            Name of the instructor we need the key
	 * @return long The key of instructor sName, -1 if instructor not found
	 */
	public long getInstructorKey(String sName) {
		return this.getResourceKey(sName);
	}

	/**
	 * Retreives the availability of an instructor in the set
	 * 
	 * @param lKey
	 *            Key of the instructor whose availability is wanted
	 * @return DxAvailability The availability of the instructor, null if the
	 *         key was invalid
	 */
	public DxAvailability getInstructorAvailability(long lKey) {
		DxInstructor dxiTemp = (DxInstructor) this.getResource(lKey);
		if (dxiTemp != null) {
			return dxiTemp.getAvailability();
		}
		return null;
	}

//	// TODO: Should be removed from tests
//	public DxInstructor[] getInstructorsSortedByKey() {
//		return this.getKeySortedVector().toArray(new DxInstructor[this.size()]);
//	}
//
//	public DxInstructor[] getInstructorsSortedByName() {
//		return this.getNameSortedVector()
//				.toArray(new DxInstructor[this.size()]);
//	}

	protected void merge(DxResource dxrModify, DxResource dxrNew) {
		dxrModify.getKey(); // To avoid warnings
		dxrNew.getKey(); // To avoid warnings
		// If an instructor already exist, we want to keep availabilities as
		// they might have been modified
	}

	public void remAllAssignedToASite(String currentSite) {
		System.out
				.println("DxSetOfIntructors.remAllAssignedToASite must be implemented");
	}

	/**
	 * Removes an instructor from the set
	 * 
	 * @param lKey
	 *            Key of the instructor to be removed
	 */
	public void removeInstructor(long lKey) {
		this.removeResource(lKey);
	}

	/***************************************************************************
	 * Temporary function that return value that should be written to a dia
	 * file. Eventually, a class structure as the one used for reading should be
	 * implemented. An idea for DxSetOfInstructorsReader would have a method
	 * like: String toWrite(DxSetOfInstructor dxsoi) which could output data for
	 * a dia file, or eventually sql querries.
	 * 
	 * @return A string containing all the instructors informations
	 **************************************************************************/
	public String toWrite() {
		StringBuffer reslist = new StringBuffer();
		if (size() > 0) {
			Iterator itInstructors = this.iterator();
			DxInstructor dxiTemp;
			while (itInstructors.hasNext()) {
				dxiTemp = (DxInstructor) itInstructors.next();
				reslist.append(dxiTemp.getName() + DConst.CR_LF);
				reslist.append(dxiTemp.getAvailability().toWrite(
						DConst.AVAILABILITY_DAY_SEPARATOR_INST,
						DConst.AVAILABILITY_PERIOD_SEPARATOR));
				// Avoid trailing line feed
				if (itInstructors.hasNext()) {
					reslist.append(DConst.AVAILABILITY_DAY_SEPARATOR_INST);
				}
			}
		}
		return reslist.toString();
	}
}