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

import dConstants.DConst;
import dInternal.dData.DxAvailability;
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
     * @param iNewInstructor
     *            The new instructor to be inserted
     */
    public void addInstructor(String sName, DxAvailability dxaAva) {
        DxInstructor dxiTemp = new DxInstructor(sName, dxaAva);
        this.addResource(dxiTemp);
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

    /**
     * Removes an instructor from the set
     * 
     * @return String A string containing all the instructors informations
     */
    public String toWrite() {
        StringBuffer reslist = new StringBuffer();
        if (size() > 0) {
            Iterator itInstructors = this.iterator();
            while(itInstructors.hasNext()){
                DxInstructor dxiTemp = (DxInstructor) itInstructors.next();
                reslist.append(dxiTemp.getInstructorName() + DConst.CR_LF);
                reslist.append(dxiTemp.getInstructorAvailability().toWrite(DConst.AVAILABILITY_DAY_SEPARATOR_INST, DConst.AVAILABILITY_PERIOD_SEPARATOR));
                // Avoid trailing line feed
                if (itInstructors.hasNext()) {
                    reslist.append(DConst.AVAILABILITY_DAY_SEPARATOR_INST);
                }
            }
        }
        return reslist.toString();
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
     * Retreives the key of an instructor by his name. If there are two
     * instructors with the same name, there is no guaranty on which of the
     * instructors key will be returned
     * 
     * @param sName
     *            Name of the instructor we need the key
     * @return long The key of instructor sName, -1 if instructor not found
     */
    public long getInstructorKeyByName(String sName) {
        return this.getResourceKeyByName(sName);
    }

//    /**
//     * Retreives the availability of an instructor in the set
//     * 
//     * @param nIndex
//     *            Index of an instructor who's availability is wanted. This
//     *            should be the index in the list returned by getNamesVector.
//     *            Note that this index is valid until an instructor is added or
//     *            removed.
//     * @return DxAvailability The availability of the instructor, null if the
//     *         index was invalid
//     */
//    public DxAvailability getInstructorAvailability(int nIndex) {
//        DxInstructor dxiTemp = (DxInstructor) this
//                .getResourceByNameIndex(nIndex);
//        if (dxiTemp != null) {
//            return dxiTemp.getInstructorAvailability();
//        }
//        return null;
//    }

    /**
     * Retreives the availability of an instructor in the set
     * 
     * @param nIndex
     *            Key of the Instructor whose availability is wanted
     * @return DxAvailability The availability of the instructor, null if the
     *         index was invalid
     */
    public DxAvailability getInstructorAvailabilityByKey(long lKey) {
        DxInstructor dxiTemp = (DxInstructor) this.getResource(lKey);
        if (dxiTemp != null) {
            return dxiTemp.getInstructorAvailability();
        }
        return null;
    }

//    /**
//     * Modify the availability of instructor at index nIndex
//     * 
//     * @param nIndex
//     *            Index of the instructor that availability needs to be modified
//     */
//    public void setInstructorAvailability(int nIndex, DxAvailability dxaNewAva) {
//        DxInstructor dxiTemp = (DxInstructor) this
//                .getResourceByNameIndex(nIndex);
//        if (dxiTemp != null) {
//            dxiTemp.setInstructorAvailability(dxaNewAva);
//        }
//    }

    public boolean isEquals(DxSetOfInstructors dxsoi) {
        if(!super.isEqual(dxsoi))
        {
            return false;
        }
        // For every instructors, verify that availabilities match
        Iterator itRes = this.iterator();
        while(itRes.hasNext()){
            DxInstructor dxiThis = (DxInstructor)itRes.next();
            DxInstructor dxiOther = dxsoi.getInstructor(dxiThis.getInstructorKey());
            if (!dxiThis.getInstructorAvailability().isEquals(
                    dxiOther.getInstructorAvailability()))
                return false;
        }
        return true;
    }

	public DxInstructor getInstructor(long lKey) {
        return (DxInstructor)this.getResource(lKey);
    }

    public void remAllAssignedToASite(String currentSite) {
		System.out.println("DxSetOfIntructors.remAllAssignedToASite must be implemented");		
	}

	public void alwaysAvailable() {
		System.out.println("DxSetOfIntructors.alwaysAvailable must be implemented");			
	}
}