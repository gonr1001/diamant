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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import dConstants.DConst;
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

    private Vector<DxInstructor> _vInstructors;

    private long _uniqueKey;

    // private boolean _isSorted;

    /**
     * Constructor
     */
    public DxSetOfInstructors() {
        _vInstructors = new Vector<DxInstructor>();
        _uniqueKey = 1;
        // _isSorted = false;
    }

    /**
     * Adds an instructor to the set
     * 
     * @param iNewInstructor
     *            The new instructor to be inserted
     */
    public void addInstructor(String sName, DxAvailability dxaAva) {
        _vInstructors.add(new DxInstructor(sName, dxaAva, _uniqueKey++));
        // _isSorted = false;
    }

    /**
     * Removes an instructor from the set
     * 
     * @param nIndex
     *            Index of the instructor to be removed
     * @return boolean Returns true if the index was valid, false otherwise
     */
    public boolean removeInstructor(int nIndex) {
        if (isValidIndex(nIndex)) {
            _vInstructors.remove(nIndex);
            return true;
        }
        return false;
    }

    // /**
    // * Sort the set of instructors by their name
    // */
    // public void sortInstructors() {
    // Collections.sort((List <DxInstructor>) _vInstructors,
    // DxInstructor.NameComparator);
    // }

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
     * @return String A string containing all the instructors informations
     */
    public String toWrite() {
        StringBuffer reslist = new StringBuffer();
        int i;
        if (size() > 0) {
            for (i = 0; i < this.size(); i++) {
                reslist.append(getInstructorName(i) + DConst.CR_LF);
                reslist.append(getInstructorAvailability(i).toWrite());
                // Avoid trailing line feed
                if (i < (this.size() - 1)) {
                    reslist.append(DConst.CR_LF);
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
    public String getInstructorNameByKey(long lKey) {
        DxInstructor dxiTemp = new DxInstructor(null, null, lKey);
        int nIndex = Collections.binarySearch(_vInstructors, dxiTemp,
                DxInstructor.KeyComparator);
        if (nIndex >= 0) {
            return _vInstructors.get(nIndex).getInstructorName();
        }

        return null;
    }

    /**
     * Retreives the name of an instructor in the set
     * 
     * @param nIndex
     *            Index of the instructor whose name is wanted
     * @return String The name of the instructor, null if the index was invalid
     */
    public String getInstructorName(int nIndex) {
        if (isValidIndex(nIndex)) {
            return _vInstructors.get(nIndex).getInstructorName();
        }
        return null;
    }

    /**
     * Retreives the ID of an instructor in the set
     * 
     * @param nIndex
     *            Index of the instructor whose ID is wanted
     * @return int ID of the instructor, -1 if the index was invalid
     */
    public long getInstructorKey(int nIndex) {
        if (isValidIndex(nIndex)) {
            return _vInstructors.get(nIndex).getInstructorKey();
        }
        return -1;
    }

    /**
     * Retreives the availability of an instructor in the set
     * 
     * @param nIndex
     *            Index of the instructor whose availability is wanted
     * @return DxAvailability The availability of the instructor, null if the
     *         index was invalid
     */
    public DxAvailability getInstructorAvailability(int nIndex) {
        if (isValidIndex(nIndex)) {
            return _vInstructors.get(nIndex).getInstructorAvailability();
        }
        return null;
    }

    /**
     * Retreives the availability of an instructor in the set
     * 
     * @param nIndex
     *            Index of the instructor whose availability is wanted
     * @return DxAvailability The availability of the instructor, null if the
     *         index was invalid
     */
    public int[][] getInstructorAvaMatrix(int nIndex) {
        if (isValidIndex(nIndex)) {
            return _vInstructors.get(nIndex).getInstructorAvailability()
                    .getMatrixAvailability();
        }
        return null;

    }

    public int getIndexByName(String sName) {
        Iterator it = _vInstructors.iterator();

        for (int i = 0; it.hasNext(); i++) {
            if (sName.equalsIgnoreCase(((DxInstructor) it.next()).getInstructorName()))
                return i;
        }
        return -1;
    }
    
    public int getIndexByKey(long lKey) {
        DxInstructor dxiTemp = new DxInstructor(null, null, lKey);
        int nIndex = Collections.binarySearch(_vInstructors, dxiTemp,
                DxInstructor.KeyComparator);
        if (nIndex >= 0) {
            return nIndex;
        }
        return -1;
    }

    public Vector<String> getNamesVector() {
        // if (!_isSorted) {
        // sortInstructors();
        // }
        Vector<String> vReturn = new Vector<String>();
        for (int i = 0; i < this.size(); i++) {
            vReturn.add(this.getInstructorName(i));
        }
        Collections.sort((List<String>) vReturn);
        return vReturn;
    }

    /**
     * Modify the availability of instructor at index nIndex
     * 
     * @param nIndex
     *            Index of the instructor that availability needs to be modified
     * @return boolean true if the index was valid, false otherwise
     */
    public boolean setInstructorAvailability(int nIndex,
            DxAvailability dxaNewAva) {
        if (isValidIndex(nIndex)) {
            _vInstructors.get(nIndex).setInstructorAvailability(dxaNewAva);
            return true;
        }
        return false;

    }

    /**
     * Modify the name of instructor at index nIndex
     * 
     * @param nIndex
     *            Index of the instructor that name needs to be modified
     * @return boolean true if the index was valid, false otherwise
     */
    public boolean setInstructorName(int nIndex, String sNewName) {
        if (isValidIndex(nIndex)) {
            _vInstructors.get(nIndex).setInstructorName(sNewName);
            // _isSorted = false;
            return true;
        }
        return false;
    }

    public boolean isEquals(DxSetOfInstructors dxsoi) {
        // If sizes differ, it is necessarly different
        if (this.size() != dxsoi.size()) {
            return false;
        }
        // For every instructors, verify that name and availabilities match
        // Key is not verified
        for (int i = 0; i < this.size(); i++) {
            if (!this.getInstructorName(i).equalsIgnoreCase(
                    dxsoi.getInstructorName(i))
                    || !this.getInstructorAvailability(i).isEquals(
                            dxsoi.getInstructorAvailability(i)))
                return false;
        }
        return true;
    }

    /**
     * Validate that nIndex is a valid instrutor in the set
     * 
     * @param nIndex
     *            Index to be verified
     * @return boolean true if the index was valid, false otherwise
     */
    private boolean isValidIndex(int nIndex) {
        return ((nIndex >= 0) && (nIndex < _vInstructors.size()));
    }

}
