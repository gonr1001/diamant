/**
 * Created on May 31st, 2006
 * 
 * 
 * Title: DxSetOfResource.java 
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
 */
package dInternal.dData;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * Nicolas Calderon
 * 
 * Description: DxSetOfResource is a class used to:
 * <p>
 * Contain a set of ressource and manage keys attribution. This class will
 * usually be inherited to add properties. This set only manage comonly used
 * attributes: keys and names.
 * <p>
 * 
 */
public class DxSetOfResources implements Iterable {
    private Vector<DxResource> _vResourceSortedByKey;

    private Vector<DxResource> _vResourceSortedByName;

    private boolean _bSorted;

    public DxSetOfResources() {
        _vResourceSortedByKey = new Vector<DxResource>();
        _vResourceSortedByName = new Vector<DxResource>();
        _bSorted = false;
    }

    /**
     * Adds an ressource to the set and changes it's key so it is correct
     * according to the set to wich it is being added.
     * 
     * @param dxrRes
     *            Resource that has to be added to the set
     */
    public void addResource(DxResource dxrRes) {
        _vResourceSortedByKey.add(dxrRes);
        _vResourceSortedByName.add(dxrRes);
        _bSorted = false;
    }

    /**
     * Adds all the ressources in the other set of ressources
     * 
     * @param dxrRes
     *            Resource that has to be added to the set
     */
    public void addSetOfResources(DxSetOfResources dxsorResources) {
        for (int i = 0; i < dxsorResources.size(); i++) {
            this.addResource(dxsorResources._vResourceSortedByKey.get(i));
        }
        _bSorted = false;
    }

    /**
     * Removes an instructor from the set
     * 
     * @param lKey
     *            Key of the instructor to be removed
     */
    public void removeResource(long lKey) {
        int nIndexKey = getSortedKeyIndex(lKey);
        int nIndexName = getSortedNameIndex(lKey);
        if (nIndexKey != -1 && nIndexName != -1) {
            _vResourceSortedByKey.remove(nIndexKey);
            _vResourceSortedByName.remove(nIndexName);
        }
    }

    /**
     * Return the number of ressource currently in the set
     * 
     * @return The number of instructor currently in the set
     */
    public int size() {
        return _vResourceSortedByKey.size();
    }

    /**
     * This method is used for tests only, to make sure add and remove will
     * corretly manage both vectors
     * 
     * @return true if name ordered vector and key ordered vector are the same
     *         size
     */
    public boolean areVectorsSync() {
        return _vResourceSortedByKey.size() == _vResourceSortedByName.size();
    }

    /**
     * Retreives a ressource in the set
     * 
     * @param nIndex
     *            Index of the instructor whose name is wanted
     * @return String The name of the instructor, null if the index was invalid
     */
    public DxResource getResource(long lKey) {
        int nIndex = getSortedKeyIndex(lKey);
        if (nIndex >= 0) {
            return _vResourceSortedByKey.get(nIndex);
        }
        return null;
    }

    /**
     * Retreives a ressource by its name. If there are two ressources with the
     * same name, there is no guaranty on which ressource will be returned
     * 
     * @param sName
     *            Name of the ressource we need
     * @return The ressource
     */
    public DxResource getResource(String sName) {
        if (!_bSorted) {
            sortResources();
        }
        DxResource dxrTemp = new DxResource(0, sName);
        int nIndex = Collections.binarySearch(_vResourceSortedByName, dxrTemp,
                DxResource.NameComparator);
        if (nIndex >= 0) {
            return _vResourceSortedByName.get(nIndex);
        }
        return null;
    }

    /**
     * Retreives the name of a ressource in the set
     * 
     * @param nIndex
     *            Index of the instructor whose name is wanted
     * @return String The name of the instructor, null if the index was invalid
     */
    public String getResourceName(long lKey) {

        DxResource dxrTemp = getResource(lKey);
        if (dxrTemp != null) {
            return dxrTemp.getResourceName();
        }
        return null;
    }

    /**
     * Retreives the key of a ressource by its name. If there are two ressources
     * with the same name, there is no guaranty on which ressource key will be
     * returned
     * 
     * @param sName
     *            Name of the ressource we need the key
     * @return The key of ressour sName, -1 if instructor not found
     */
    public long getResourceKey(String sName) {
        if (!_bSorted) {
            sortResources();
        }
        DxResource dxrTemp = getResource(sName);
        if (dxrTemp != null) {
            return dxrTemp.getResourceKey();
        }
        return -1;
    }

    public DxResource[] getResourcesSortedByName() {
        if (!_bSorted) {
            sortResources();
        }
        return _vResourceSortedByName.toArray(new DxResource[this.size()]);
    }

    public DxResource[] getResourcesSortedByKey() {
        if (!_bSorted) {
            sortResources();
        }
        return _vResourceSortedByKey.toArray(new DxResource[this.size()]);
    }
    
    protected Vector<DxResource> getNameSortedVector() {
        if (!_bSorted) {
            sortResources();
        }
        return _vResourceSortedByName;
    }

    protected Vector<DxResource> getKeySortedVector() {
        if (!_bSorted) {
            sortResources();
        }
        return _vResourceSortedByKey;
    }

    public Vector<String> getNamesVector() {
        if (!_bSorted) {
            sortResources();
        }
        Vector<String> vReturn = new Vector<String>();
        Iterator itNames = _vResourceSortedByName.iterator();
        while (itNames.hasNext()) {
            vReturn.add(((DxResource) itNames.next()).getResourceName());
        }

        return vReturn;
    }

    /**
     * Searches the index of a ressource in the key sorted vector given it's
     * key. We use Collection.binarySearch as Resources are sorted
     * 
     * @param lKey
     *            Key of the resource we want the index
     * @return Index of the resource if found, -1 if not
     */
    private int getSortedKeyIndex(long lKey) {
        if (!_bSorted) {
            sortResources();
        }
        DxResource dxrTemp = new DxResource(lKey, null);
        int nIndex = Collections.binarySearch(_vResourceSortedByKey, dxrTemp,
                DxResource.KeyComparator);
        if (nIndex >= 0) {
            return nIndex;
        }
        return -1;
    }

    /**
     * Searches the index of a resource in the name sorted vector given it's
     * key. We have to iterate through all resource to find the one that matches
     * the key since keys are out of order in th name vector.
     * 
     * @param lKey
     *            Key of the resource we want the index
     * @return Index of the resource if found, -1 if not
     */
    private int getSortedNameIndex(long lKey) {
        Iterator it = _vResourceSortedByName.iterator();

        for (int i = 0; it.hasNext(); i++) {
            if (((DxResource) it.next()).getResourceKey() == lKey)
                return i;
        }
        return -1;
    }

    /**
     * Sorts the name ordered vector
     */
    private void sortResources() {
        Collections.sort((List<DxResource>) _vResourceSortedByName,
                DxResource.NameComparator);
        Collections.sort((List<DxResource>) _vResourceSortedByKey,
                DxResource.KeyComparator);
        _bSorted = true;
    }

    public Iterator iterator() {
        return _vResourceSortedByKey.iterator();
    }

    public boolean isEqual(DxSetOfResources dxsorOther) {
        if (this.size() != dxsorOther.size()) {
            return false;
        }

        Iterator itRes = this.iterator();
        while (itRes.hasNext()) {
            DxResource dxrTemp = (DxResource) itRes.next();
            if (!dxsorOther.ressourceExists(dxrTemp.getResourceName())) {
                return false;
            }
        }
        return true;
    }

    public boolean ressourceExists(String sName) {
        if (!_bSorted) {
            sortResources();
        }

        DxResource dxrTemp = new DxResource(0, sName);

        int nIndex = Collections.binarySearch(_vResourceSortedByName, dxrTemp,
                DxResource.NameComparator);

        if (nIndex >= 0) {
            return true;
        }
        return false;
    }

    public boolean ressourceExists(long lKey) {
        if (getSortedKeyIndex(lKey) == -1) {
            return false;
        }
        return true;
    }
}
