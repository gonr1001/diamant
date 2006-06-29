/**
 * Created on May 31st, 2006
 * 
 * 
 * Title: DxSetOfRessource.java 
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
 * Description: DxSetOfRessource is a class used to:
 * <p>
 * Contain a set of ressource and manage keys attribution. This class will
 * usually be inherited to add properties. This set only manage comonly used
 * attributes: keys and names.
 * <p>
 * 
 */
public class DxSetOfResources implements Iterable {
	private Vector<DxResource> _vRessourceSortedByKey;

	private Vector<DxResource> _vRessourceSortedByName;

	private boolean _bNamesSorted;

	// private long _lUniqueKey;

	public DxSetOfResources() {
		_vRessourceSortedByKey = new Vector<DxResource>();
		_vRessourceSortedByName = new Vector<DxResource>();
		// _lUniqueKey = 1;
		_bNamesSorted = false;
	}

	/**
	 * Adds an ressource to the set and changes it's key so it is correct
	 * according to the set to wich it is being added.
	 * 
	 * @param dxrRes
	 *            Ressource that has to be added to the set
	 */
	public void addResource(DxResource dxrRes) {
		// dxrRes.setRessourceKey(_lUniqueKey++);
		_vRessourceSortedByKey.add(dxrRes);
		_vRessourceSortedByName.add(dxrRes);
		_bNamesSorted = false;
	}

	/**
	 * Adds all the ressources in the other set of ressources
	 * 
	 * @param dxrRes
	 *            Ressource that has to be added to the set
	 */
	public void addResources(DxSetOfResources dxsorRessources) {
		for (int i = 0; i < dxsorRessources.size(); i++) {
			this.addResource(dxsorRessources._vRessourceSortedByKey.get(i));
		}
	}

	/**
	 * Removes an instructor from the set
	 * 
	 * @param lKey
	 *            Key of the instructor to be removed
	 */
	public void removeResource(long lKey) {
		int nIndexKey = getSortedKeyIndexByKey(lKey);
		int nIndexName = getSortedNameIndexByKey(lKey);
		if (nIndexKey != -1 && nIndexName != -1) {
			_vRessourceSortedByKey.remove(nIndexKey);
			_vRessourceSortedByName.remove(nIndexName);
		}
	}

	/**
	 * Return the number of ressource currently in the set
	 * 
	 * @return The number of instructor currently in the set
	 */
	public int size() {
		return _vRessourceSortedByKey.size();
	}

	/**
	 * This method is used for tests only, to make sure add and remove will
	 * corretly manage both vectors
	 * 
	 * @return true if name ordered vector and key ordered vector are the same
	 *         size
	 */
	public boolean areVectorsSync() {
		return _vRessourceSortedByKey.size() == _vRessourceSortedByName.size();
	}

	/**
	 * Retreives a ressource in the set
	 * 
	 * @param nIndex
	 *            Index of the instructor whose name is wanted
	 * @return String The name of the instructor, null if the index was invalid
	 */
	public DxResource getResource(long lKey) {
		int nIndex = getSortedKeyIndexByKey(lKey);
		if (nIndex >= 0) {
			return _vRessourceSortedByKey.get(nIndex);
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
	 * Retreives a ressource by its name. If there are two ressources with the
	 * same name, there is no guaranty on which ressource will be returned
	 * 
	 * @param sName
	 *            Name of the ressource we need
	 * @return The ressource
	 */
	public DxResource getResourceByName(String sName) {
		if (!_bNamesSorted) {
			sortRessources();
		}
		DxResource dxrTemp = new DxResource(0, sName);
		int nIndex = Collections.binarySearch(_vRessourceSortedByName, dxrTemp,
				DxResource.NameComparator);
		if (nIndex >= 0) {
			return _vRessourceSortedByName.get(nIndex);
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
	public long getResourceKeyByName(String sName) {
		if (!_bNamesSorted) {
			sortRessources();
		}
		DxResource dxrTemp = getResourceByName(sName);
		if (dxrTemp != null) {
			return dxrTemp.getResourceKey();
		}
		return -1;
	}

	/**
	 * Retreives a ressource by its index in the name vector.
	 * 
	 * @param sName
	 *            index of the ressource we need
	 * @return The ressource, null if index was invalid
	 */
	public DxResource getResourceByNameIndex(int nIndex) {
		if (!_bNamesSorted) {
			sortRessources();
		}
		if (isValidIndex(nIndex)) {
			return _vRessourceSortedByName.get(nIndex);
		}
		return null;
	}

	// public Vector<String> getNamesVector() {
	// if (!_bNamesSorted) {
	// sortRessources();
	// }
	// Vector<String> vReturn = new Vector<String>();
	// for (int i = 0; i < _vRessourceSortedByName.size(); i++) {
	// vReturn.add(_vRessourceSortedByName.get(i).getResourceName());
	// }
	// return vReturn;
	// }

	public DxResource[] getRessourcesSortedByName() {
		if (!_bNamesSorted) {
			sortRessources();
		}
		return _vRessourceSortedByName.toArray(new DxResource[this.size()]);
	}

	public DxResource[] getRessourcesSortedByKey() {
		return _vRessourceSortedByKey.toArray(new DxResource[this.size()]);
	}
	
	public Vector<String> getNamesVector() {
		if (!_bNamesSorted) {
			sortRessources();
		}
		Vector<String> vReturn = new Vector<String>();
		Iterator itNames = _vRessourceSortedByName.iterator();
		while(itNames.hasNext())
		{
			vReturn.add(((DxResource)itNames.next()).getResourceName());
		}
		
		return vReturn;
	}

	/**
	 * Validate that nIndex is a valid index in the set
	 * 
	 * @param nIndex
	 *            Index to be verified
	 * @return true if the index was valid, false otherwise
	 */
	private boolean isValidIndex(int nIndex) {
		return ((nIndex >= 0) && (nIndex < _vRessourceSortedByKey.size()) && (nIndex < _vRessourceSortedByName
				.size()));
	}

	/**
	 * Searches the index of a ressource in the key sorted vector given it's
	 * key. We use Collection.binarySearch as Ressources are sorted
	 * 
	 * @param lKey
	 *            Key of the ressource we want the index
	 * @return Index of the ressource if found, -1 if not
	 */
	private int getSortedKeyIndexByKey(long lKey) {
		DxResource dxrTemp = new DxResource(lKey, null);
		int nIndex = Collections.binarySearch(_vRessourceSortedByKey, dxrTemp,
				DxResource.KeyComparator);
		if (nIndex >= 0) {
			return nIndex;
		}
		return -1;
	}

	/**
	 * Searches the index of a ressource in the name sorted vector given it's
	 * key. We have to iterate through all ressource to find the one that
	 * matches the key since keys are out of order in th name vector.
	 * 
	 * @param lKey
	 *            Key of the ressource we want the index
	 * @return Index of the ressource if found, -1 if not
	 */
	private int getSortedNameIndexByKey(long lKey) {
		Iterator it = _vRessourceSortedByName.iterator();

		for (int i = 0; it.hasNext(); i++) {
			if (((DxResource) it.next()).getResourceKey() == lKey)
				return i;
		}
		return -1;
	}

	/**
	 * Sorts the name ordered vector
	 */
	private void sortRessources() {
		Collections.sort((List<DxResource>) _vRessourceSortedByName,
				DxResource.NameComparator);

		_bNamesSorted = true;
	}

	public Iterator iterator() {
		return _vRessourceSortedByKey.iterator();
	}
}
