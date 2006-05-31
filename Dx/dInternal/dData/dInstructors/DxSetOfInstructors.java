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

    private Vector<DxInstructor> _vSortedByKey;

    private Vector<DxInstructor> _vSortedByName;

    private boolean _isSorted;

    private long _uniqueKey;

    /**
     * Constructor
     */
    public DxSetOfInstructors() {
        _vSortedByKey = new Vector<DxInstructor>();
        _vSortedByName = new Vector<DxInstructor>();
        _uniqueKey = 1;
        _isSorted = false;
    }

    /**
     * Adds an instructor to the set
     * 
     * @param iNewInstructor
     *            The new instructor to be inserted
     */
    public void addInstructor(String sName, DxAvailability dxaAva) {
        DxInstructor dxiTemp = new DxInstructor(sName, dxaAva, _uniqueKey++);
        _vSortedByKey.add(dxiTemp);
        _vSortedByName.add(dxiTemp);
        _isSorted = false;
    }

    /**
     * Removes an instructor from the set
     * 
     * @param nIndex
     *            Index of the instructor to be removed
     * @return boolean Returns true if the index was valid, false otherwise
     */
    public void removeInstructor(long lKey) {
        int nIndexKey = getSortedKeyIndexByKey(lKey);
        int nIndexName = getSortedNameIndexByKey(lKey);
        if (nIndexKey != -1 && nIndexName != -1) {
            _vSortedByKey.remove(nIndexKey);
            _vSortedByName.remove(nIndexName);
        }
    }

    /**
     * Return the number of instructors currently in the set
     * 
     * @return int The number of instructor currently in the set
     */
    public int size(){
        return _vSortedByKey.size();
    }
    
    public boolean areVectorsSync(){
        return _vSortedByKey.size()==_vSortedByName.size();
    }

    /**
     * Removes an instructor from the set
     * 
     * @return String A string containing all the instructors informations
     */
    public String toWrite() {
        if(!_isSorted)
        {
            sortInstructors();
        }
        
        StringBuffer reslist = new StringBuffer();
        int i;
        if (size() > 0) {
            for (i = 0; i < this.size(); i++) {
                reslist.append(_vSortedByName.get(i).getInstructorName()
                        + DConst.CR_LF);
                reslist.append(_vSortedByName.get(i)
                        .getInstructorAvailability().toWrite());
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
    public String getInstructorName(long lKey) {
        int nIndex = getSortedKeyIndexByKey(lKey);
        if (nIndex >= 0) {
            return _vSortedByKey.get(nIndex).getInstructorName();
        }

        return null;
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
        DxInstructor dxiTemp = new DxInstructor(sName, null, 0);
        int nIndex = Collections.binarySearch(_vSortedByName, dxiTemp,
                DxInstructor.NameComparator);
        if (nIndex >= 0) {
            return _vSortedByName.get(nIndex).getInstructorKey();
        }
        return -1;
    }

    /**
     * Retreives the availability of an instructor in the set
     * 
     * @param nIndex
     *            Index of an instructor who's availability is wanted. This
     *            should be the index in the list returned by getNamesVector.
     *            Note that this index is valid until an instructor is added or
     *            removed.
     * @return DxAvailability The availability of the instructor, null if the
     *         index was invalid
     */
    public DxAvailability getInstructorAvailability(int nIndex) {
        if(!_isSorted)
        {
            sortInstructors();
        }
        if (isValidIndex(nIndex)) {
            return _vSortedByName.get(nIndex).getInstructorAvailability();
        }
        return null;
    }

    /**
     * Retreives the availability of an instructor in the set
     * 
     * @param nIndex
     *            Key of the Instructor whose availability is wanted
     * @return DxAvailability The availability of the instructor, null if the
     *         index was invalid
     */
    public DxAvailability getInstructorAvailabilityByKey(long lKey) {
        int nIndex = getSortedKeyIndexByKey(lKey);
        if (isValidIndex(nIndex)) {
            return _vSortedByKey.get(nIndex).getInstructorAvailability();
        }
        return null;
    }

    public Vector<String> getNamesVector() {
        if (!_isSorted) {
            sortInstructors();
        }
        Vector<String> vReturn = new Vector<String>();
        for (int i = 0; i < this.size(); i++) {
            vReturn.add(_vSortedByName.get(i).getInstructorName());
        }
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
            _vSortedByName.get(nIndex).setInstructorAvailability(dxaNewAva);
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
            if (!this._vSortedByKey.get(i).getInstructorName().equalsIgnoreCase(
                    dxsoi._vSortedByKey.get(i).getInstructorName())
                    || !this._vSortedByKey.get(i).getInstructorAvailability().isEquals(
                            dxsoi._vSortedByKey.get(i).getInstructorAvailability()))
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
        return ((nIndex >= 0) && (nIndex < _vSortedByKey.size()) && (nIndex < _vSortedByName
                .size()));
    }

    private int getSortedKeyIndexByKey(long lKey) {
        DxInstructor dxiTemp = new DxInstructor(null, null, lKey);
        int nIndex = Collections.binarySearch(_vSortedByKey, dxiTemp,
                DxInstructor.KeyComparator);
        if (nIndex >= 0) {
            return nIndex;
        }
        return -1;
    }

    private int getSortedNameIndexByKey(long lKey) {
        Iterator it = _vSortedByName.iterator();

        for (int i = 0; it.hasNext(); i++) {
            if (((DxInstructor) it.next()).getInstructorKey() == lKey)
                return i;
        }
        return -1;
    }

    /**
     * Sort the set of instructors by their name
     */
    private void sortInstructors() {
        Collections.sort((List<DxInstructor>) _vSortedByName,
                DxInstructor.NameComparator);
    }
}
