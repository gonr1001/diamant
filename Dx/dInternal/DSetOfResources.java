/**
 *
 * Title: DSetOfResources
 * Description: DSetOfResources is a class used to
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
 * @since JDK1.3
 */

package dInternal;

import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;
import dInternal.DResource;

public abstract class DSetOfResources extends DObject {

	/**contains list of resource (instructor, rooms, student or activity)*/
	// XXXX Pascal: Pkoi pas une Hash au lieu de Vecteur?  Il semble que quelqu'un a essaye d'implementer 
	//              un hash en utiliant un Vector (deprecated en passant) comme contenant.  C'est tres bizzare.
	private Vector<DResource> _resourceList;

	/**give type of the last sort _stateSort 0= sortSetOfResourcesByKey;
	 * 1= sortSetOfResourcesByID; 2= sortSetOfResourcesBySelectedField
	 */
	int _stateSort = 0;

	private long _currentKey = 1;

	/**0= activities, 1= students, 2= instructors, 3 = rooms, 4= other*/

	/**
	 * Constructor
	 * 0= activities, 1= students, 2= instructors, 3 = rooms, 4= ttstructure
	 * 5= states, 6 = events
	 * @param int resType The resource type
	 * */
	public DSetOfResources() {
		//_currentKey = KeyFactory.getKey();
		_resourceList = new Vector<DResource>(1, 1); // XXXX Pascal: Pourquoi (1,1) ?  Quel est la motivation?
		//_resourceType = resType;
	}

	//	/**
	//	 * methode analyse st, a stringtokenizer variable
	//	 * @param
	//	 * @return <{Vector}>
	//	 */
	//	public boolean analyseTokens(byte[] dataloaded, int beginPosition) {
	//		beginPosition += 0;
	//		dataloaded[0] += 0;
	//		return false;
	//	}

	/**
	 * 
	 * @param de
	 * @param beginPosition
	 */
	public void buildSetOfResources(DataExchange de, @SuppressWarnings("unused")
	int beginPosition) {
		de.getHeader();
	}

	/**
	 *build SetOfResources list.
	 *use StringTokenizer st: instructors in text format
	 *
	 */
	/*  public abstract void buildSetOfResources(byte[]  dataloaded, int beginPosition);/*{
	 beginPosition+=0;
	 }*/

	/**
	 * Add a Resource to SetOfResources
	 * @param resource a Resource object
	 * @param insertType an integer. it select the type of insert you want to do
	 * insertType=0 (insert by using key); insertType=1 (insert by using ID)
	 * @return boolean (true if Resource added to list, false if Resource not
	 * added because it already exists)
	 * */
	// XXXX Pascal: cette methode a un comportement X si insertType vaut 0 et Y si insertType vaut 1?
	//              Ouch!  Pourquoi pas deux methodes distinctes?  On peut utiliser une methode privee pour le 
	//              code qui est semblable afin d'eviter la redondance.
	//              Sinon, on cree un API monstrueux.
	public boolean addResource(DResource resource, int insertType) {
		//if (getIndexOfResource(resource.getID()) == -1){
		//if (_stateSort!=0)
		//this.sortSetOfResourcesByKey();
		int index = 0;
		int add = -1;
		if (insertType == 0) {
			add = getIndexOfResource(_currentKey);
			if (add == -1)
				index = searchWhereToInsert(_currentKey);
		} else {
			if (insertType == 1) {
				add = getIndexOfResource(resource.getID());
				if (add == -1)
					index = searchWhereToInsert(resource.getID());
			}// end if (insertType==1)
		}// end else if (insertType==0)

		if (add == -1) {
			//_currentKey = KeyFactory.getKey();
			resource.setKey(_currentKey);
			if (index > (_resourceList.size() - 1))
				_resourceList.add(resource);
			else
				_resourceList.insertElementAt(resource, index);
			_currentKey++;
			return true;
		}
		return false;
	}

	public boolean addResourceUsingKey(DResource resource) {
		int index = 0;
		int add = -1;

		add = searchKey(_currentKey);
		if (add == -1){
			index = searchWhereToInsert(_currentKey);
			resource.setKey(_currentKey);
			if (index > (_resourceList.size() - 1))
				_resourceList.add(resource);
			else
				_resourceList.insertElementAt(resource, index);
			_currentKey++;
			return true;
		}
		return false;
	}

	
	public boolean addResourceUsingID(DResource resource) {
		int index = 0;
		int add = -1;

		add = searchID(resource.getID());
		if (add == -1) {
			index = searchWhereToInsert(resource.getID());
			resource.setKey(_currentKey);
			if (index > (_resourceList.size() - 1))
				_resourceList.add(resource);
			else
				_resourceList.insertElementAt(resource, index);
			_currentKey++;
			return true;
		}
		return false;
	}
	
	public boolean addResource(DResource resource) {
//		int index = 0;
//		int add = -1;
		_resourceList.add(resource);
//		add = searchID(resource.getID());
//		if (add == -1) {
//			index = searchWhereToInsert(resource.getID());
//			resource.setKey(_currentKey);
//			if (index > (_resourceList.size() - 1))
//				_resourceList.add(resource);
//			else
//				_resourceList.insertElementAt(resource, index);
//			_currentKey++;
			return true;
//		}
//		return false;
	}
	
	public boolean addResourceUsingIDWithDuplicates(DResource resource) {
		int index = 0;
		int add = -1;

		if (add == -1) {
			index = searchWhereToInsert(resource.getID());
			resource.setKey(_currentKey);
			if (index > (_resourceList.size() - 1))
				_resourceList.add(resource);
			else
				_resourceList.insertElementAt(resource, index);
			_currentKey++;
			return true;
		}
		return false;
	}
	
	
//	public boolean addResourceUsingIDWithDuplicatesMm(DResource resource) {
//		int index = 0;
//		int add = -1;
//
//		if (add == -1) {
//			index = searchWhereToInsertMn(resource.getID());
//			resource.setKey(_currentKey);
//			if (index < (_resourceList.size() - 1))
//				_resourceList.add(resource);
//			else
//				_resourceList.insertElementAt(resource, index);
//			_currentKey++;
//			return true;
//		}
//		return false;
//	}

	//}
	public boolean addResourceMod(DResource resource, int insertType) {
		//if (getIndexOfResource(resource.getID()) == -1){
		//if (_stateSort!=0)
		//this.sortSetOfResourcesByKey();
		int index = 0;
		int add = -1;
		if (insertType == 0) {
			add = getIndexOfResource(_currentKey);
			if (add == -1)
				index = searchWhereToInsert(_currentKey);
		} else {
			if (insertType == 1) {
				add = getIndexOfResource(resource.getID());
				if (add == -1)
					index = searchWhereToInsert(resource.getID());
			}// end if (insertType==1)
		}// end else if (insertType==0)

		if (add == -1) {
			// resource.setKey(_currentKey);
			if (index > (_resourceList.size() - 1))
				_resourceList.add(resource);
			else
				_resourceList.insertElementAt(resource, index);
			_currentKey++;
			return true;
		}
		//_resourceList.add(resource);

		return false;
		//}
		//return false;
	}

	/**
	 * set the resource list
	 * @param Vector the vector of resource list to set
	 * */
	public void setSetOfResources(Vector<DResource> rlist) {
		_resourceList = rlist;
	}

	/**
	 * For a subset of a setOfResources, this method change a field value
	 * @param IDs Array containing the IDs of the resources to be setted
	 * @param fieldIndex The index of the value to be setted
	 * @param value The value to be setted in the field
	 */
	public void setSubsetOfResources(String[] IDs, int fieldIndex, String value) {
		for (int i = 0; i < IDs.length; i++)
			(getResource(IDs[i]).getAttach()).setField(fieldIndex, value);
	}

	/**
	 * For a subset of a setOfResources, this method change a field value
	 * @param IDs Array containing the IDs of the resources to be setted
	 * @param fieldIndex The index of the value to be setted
	 * @param value The value to be setted in the field
	 */
	public void setSubsetOfResources(Object[] IDs, int fieldIndex, String value) {
		for (int i = 0; i < IDs.length; i++)
			(getResource((String) IDs[i]).getAttach()).setField(fieldIndex,
					value);
	}

	/**
	 * get the resource list
	 * @return Vector the vector of resource list
	 * */
	public Vector<DResource> getSetOfResources() {
		return _resourceList;
	}

	/**
	 * return the size of nature list
	 * */
	public int size() {
		return _resourceList.size();
	}

	/**
	 * Set the current key of the SetOfResources
	 * @param currentkey, a long integer
	 * */
	public void setCurrentKey(long currentkey) {
		_currentKey = currentkey;
	}

	/**
	 * Remove a Resource from SetOfResources
	 * @param int the position of the resource in the resourcelist
	 * @return boolean result of the operation. true if resource removed
	 * succesfully and false otherwise
	 * */
	public boolean removeResourceAt(int position) {
		if (position < _resourceList.size()) {
			_resourceList.removeElementAt(position);
			_resourceList.trimToSize();
			return true;
		}
		return false;
	}

	/**
	 * Remove a Resource from SetOfResources
	 * @param ResourceID, a String
	 * @return boolean result of the operation. true if resource removed
	 * succesfully and false otherwise
	 * */
	public boolean removeResource(String ResourceID) {
		int index = getIndexOfResource(ResourceID);
		if (index != -1) {
			_resourceList.removeElementAt(index);
			_resourceList.trimToSize();
			return true;
		}
		return false;
	}

	/**
	 * Remove a Resource from SetOfResources
	 * @param key, a long integer
	 * @return boolean result of the operation. true if resource removed
	 * succesfully and false otherwise
	 * */
	public boolean removeResource(long key) {
		int index = getIndexOfResource(key);
		if (index != -1) {
			_resourceList.removeElementAt(index);
			_resourceList.trimToSize();
			return true;
		}
		return false;
	}

	/**
	 * Get a Resource from the SetOfResources
	 * @param long integer, the key of the Resource
	 * @return Resource null if Resource didn't found
	 * */
	public DResource getResource(long key) {
		int index = getIndexOfResource(key);
		if (index != -1)
			return _resourceList.get(index);
		return null;
	}

	/**
	 * Get a Resource from the SetOfResources
	 * @param String The ID of Resource
	 * @return Resource null if Resource didn't found
	 * */
	public DResource getResource(String ID) {
		int index = getIndexOfResource(ID);
		if (index != -1)
			return _resourceList.get(index);
		return null;
	}

	/**
	 * Get a Resource from the SetOfResources
	 * @param integer, the position of the Resource
	 * @return DResource null if DResource didn't found
	 * */
	public DResource getResourceAt(int position) {
		if (position < _resourceList.size())
			return _resourceList.get(position);
		return null;
	}

	/**
	 *
	 * */
	public int getIndexOfResource(long key) {
		return searchKey(key);
	}

	/**
	 *
	 * @return
	 */
	public abstract String getError();

	/**
	 * @return Returns the _currentKey.
	 */
	public long getCurrentKey() {
		return _currentKey;
	}

	/**
	 *
	 * */
	public int getIndexOfResource(String id) {
		return searchID(id);
	}

	/**
	 * Set a DResource in the SetOfResources
	 * @param DResource the resource to set
	 * @return boolean true if DResource set succesful and false otherwise
	 * */
	public boolean setResource(DResource resc) {
		int index = getIndexOfResource(resc.getKey());
		if (index != -1) {
			_resourceList.setElementAt(resc, index);
			return true;
		}
		return false;
	}

	/**
	 * Sort the SetOfResources by DResource's ID from smallest to biggest
	 * */
	public void sortSetOfResourcesByID() {
		Collections.sort(_resourceList, DResource.IDComparator);
		_stateSort = 1;
	}
	
	/**
	 * Sort the SetOfResources by DResource's ID from smallest to biggest
	 * */
	public void sortSetOfResourcesByIDMm() {
		Collections.sort(_resourceList, DResource.IDComparatorMm);
		_stateSort = 1;
	}


	/**
	 * Sort the SetOfResources by DResource's Key from smallest to biggest
	 * */
	public void sortSetOfResourcesByKey() {
		Collections.sort(_resourceList, DResource.KeyComparator);
		//		sort(0, _resourceList.size() - 1, 0, 0);
		_stateSort = 0;
	}

	/**
	 * Sort the SetOfResources by DResource object selected field from smallest to biggest
	 * */
	public void sortSetOfResourcesBySelectedAttachField(int field) {
		Iterator it = _resourceList.iterator();
		while (it.hasNext())
			((DResource) it.next()).setSearchField(field);

		Collections.sort(_resourceList, DResource.FieldComparator);
		//		sort(0, _resourceList.size() - 1, 2, field);
		_stateSort = 2;
	}

	/**
	 *This object (which is already a string!) is itself returned.
	 * @return the string itself
	 * */
	public abstract String toWrite();

	/**
	 * Builds a list of Resources's ID
	 * @param orderIndex The order of elements arrangement. If sortIndex == 0 then
	 * the set of resources is sorted by the resource key. If sortIndex == 1 then
	 * the set of resources is sorted by the resource ID.
	 * @return
	 */
	public Vector<String> getNamesVector(int sortIndex) {
		Vector<String> namesVector = new Vector<String>();
		if (sortIndex == 0)
			sortSetOfResourcesByKey();
		if (sortIndex == 1) {
			sortSetOfResourcesByID();
		}
		for (int i = 0; i < this._resourceList.size(); i++)
			namesVector.add(_resourceList.get(i).getID());
		return namesVector;
	}

	/**
	 * Build a list of Resources's ID
	 * @return Vector It contents the Resources's ID
	 * */
	public Vector getNamesVector(Vector<String> namesVector) {
		if (_stateSort != 1)
			sortSetOfResourcesByID();
		for (int i = 0; i < this._resourceList.size(); i++)
			namesVector.add(_resourceList.get(i).getID());
		return namesVector;
	}

	/**
	 * return a vector where each resource has idToSelect as ID
	 * @return
	 */
	public Vector selectIDValue(String idToSelect) {
		Vector<DResource> select = new Vector<DResource>();
		if (_stateSort != 1)
			sortSetOfResourcesByID();
		int beginIndex = getIndexOfResource(idToSelect);
		if (beginIndex != -1)
			while ((getResourceAt(beginIndex) != null)
					&& (getResourceAt(beginIndex).getID()
							.equalsIgnoreCase(idToSelect))) {
				select.add(getResourceAt(beginIndex));
				beginIndex++;
			}
		return select;

	}

	/**
	 * Creates a Vector containig the IDs of the resources whose the value of the
	 * field (defined by the argument "fieldIndex") is equals to value defined by
	 * the argument "fieldValue".  The fieldIndex is defined in each resource. The value
	 * may be a String, or the string representation of an int or a boolean.
	 * @param fieldIndex The identification index of a field belonging the resource
	 * @param fieldValue The comparaison value for the field selected
	 * @return a vector containing the IDs of the resources selected
	 */
	public Vector getIDsByField(int fieldIndex, String fieldValue) {
		Vector<String> idVector = new Vector<String>();
		DResource res = null;
		boolean membership = false;
		for (int i = 0; i < size(); i++) {
			res = getResourceAt(i);
			membership = (res.getAttach()).compareByField(fieldIndex,
					fieldValue);
			if (membership == true)
				idVector.add(res.getID());
		}
		return idVector;
	}

	/**
	 * @param setOflements the elements in wich we want to change value
	 * @param fieldIndex The identification index of a field belonging the resource
	 * @param fieldValue The comparaison value for the field selected
	 */
	public void setByField(Vector setOfElements, int fieldIndex,
			String fieldValue) {
		DResource res = null;
		//boolean membership = false;
		for (int i = 0; i < setOfElements.size(); i++) {
			res = getResource((String) setOfElements.get(i));
			if (res != null)
				res.getAttach().setField(fieldIndex, fieldValue);
		}
	}

	//	/**
	//	 * principal sort
	//	 * @param integer represent the beginning of resourceList vector
	//	 * @param integer represent the end of resourceList vector
	//	 * @param integer 0= sort by key; 1= sort by ID, 2= sort by selected object field
	//	 * @param integer if there's sort by selected object field it represent the
	//	 * object's field selected
	//	 * */
	//	private void sort(int begin, int end, int sortType, int field) {
	//		if (begin >= end)
	//			return;
	//		int p = 0;
	//		switch (sortType) {
	//		case 0:
	//			p = partitionKey(begin, end);
	//			break;
	//		case 1:
	//			p = partitionID(begin, end);
	//			break;
	//		case 2:
	//			p = partitionSelectedField(begin, end, field);
	//			break;
	//		}// end switch(sortType)
	//		//System.out.print("+"+begin+"-"+end);//debug
	//		sort(begin, p, sortType, field);
	//		sort(p + 1, end, sortType, field);
	//	}

	//	// manage partition by key
	//	private int partitionKey(int begin, int end) {
	//		DResource pivot = (DResource) _resourceList.get(begin);
	//		int i = begin - 1;
	//		int j = end + 1;
	//		while (i < j) {
	//			i++;
	//			while (((DResource) _resourceList.get(i)).getKey() < pivot.getKey())
	//				i++;
	//			j--;
	//			while (((DResource) _resourceList.get(j)).getKey() > pivot.getKey())
	//				j--;
	//			if (i < j)
	//				swap(i, j);
	//		}
	//		return j;
	//	}

	//	// manage partition by selected field of object
	//	private int partitionSelectedField(int begin, int end, int field) {
	//		field = field + 0;
	//		DResource pivot = (DResource) _resourceList.get(begin);
	//		int i = begin - 1;
	//		int j = end + 1;
	//		while (i < j) {
	//			i++;
	//			while (((DResource) _resourceList.get(i)).getAttach()
	//					.getSelectedField(field) < pivot.getAttach()
	//					.getSelectedField(field))
	//				i++;
	//			j--;
	//			while (((DResource) _resourceList.get(j)).getAttach()
	//					.getSelectedField(field) > pivot.getAttach()
	//					.getSelectedField(field))
	//				j--;
	//			if (i < j)
	//				swap(i, j);
	//		}
	//		return j;
	//	}

	//	// manage partition by ID
	//	private int partitionID(int begin, int end) {
	//		DResource pivot = (DResource) _resourceList.get(begin);
	//		int i = begin - 1;
	//		int j = end + 1;
	//		while (i < j) {
	//			i++;
	//			while (((DResource) _resourceList.get(i)).getID().compareTo(
	//					pivot.getID()) < 0)
	//				i++;
	//			j--;
	//			while (((DResource) _resourceList.get(j)).getID().compareTo(
	//					pivot.getID()) > 0)
	//				j--;
	//			if (i < j)
	//				swap(i, j);
	//		}
	//		return j;
	//	}

	//	// permit elements
	//	private void swap(int begin, int end) {
	//		DResource temp;
	//		//System.out.print("+"+begin+"-"+end);//debug
	//		temp = (DResource) _resourceList.get(begin);
	//		_resourceList.setElementAt(_resourceList.get(end), begin);
	//		_resourceList.setElementAt(temp, end);
	//	}

	//end of private sort methods

	/**
	 * finds a index in a sorted vector, using the binary search algorithm
	 * @param long the DResource key from wich to find index in SetOfResources
	 * @return int index of the DResource in SetOfResources
	 * */
	private int searchKey(long key) {
		if (_stateSort != 0)
			sortSetOfResourcesByKey();
		int low = 0;
		int high = _resourceList.size() - 1;
		long middleKey = 0;
		while (low <= high) {
			int mid = (low + high) / 2;
			int diff = 1;
			middleKey = (_resourceList.get(mid)).getKey();
			if (middleKey > key)
				diff = 1;
			else {
				if (middleKey < key)
					diff = -1;
				else
					diff = 0;
			}
			//long diff = ((DResource)_resourceList.get(mid)).getKey() - key;
			if (diff == 0)
				return mid;
			//else{
			if (diff < 0)
				low = mid + 1;
			else
				high = mid - 1;
			//}//end else if (diff == 0)
		}//end while(low <= high)
		return -1;
	}

	/**
	 * finds a index in a sorted vector, using the binary search algorithm
	 * @param String the DResource ID from wich to find index in SetOfResources
	 * @return int index of the DResource in SetOfResources
	 * */
	private int searchID(String id) {
		if (_stateSort != 1)
			this.sortSetOfResourcesByID();
		int low = 0;
		int high = _resourceList.size() - 1;
		while (low <= high) {
			int mid = (low + high) / 2;
			int diff = (_resourceList.get(mid)).getID().compareTo(id);
			if (diff == 0)
				return mid;
			//else{
			if (diff < 0)
				low = mid + 1;
			else
				high = mid - 1;
			//}//end else if (diff == 0)
		}//end while(low <= high)
		return -1;  // element is not in the Set
	}

	/**
	 * finds a index in a sorted vector, using the binary search algorithm
	 * @param String the DResource ID from wich to find index in SetOfResources
	 * @return int index of the DResource in SetOfResources
	 * */
	public int searchWhereToInsert(String id) {
		if ((_stateSort != 1)) //|| (_resourceList.size()<=3))
			sortSetOfResourcesByID();
		int low = 0;
		int high = _resourceList.size() - 1;
		while (low <= high) {
			int mid = (low + high) / 2;
			int diff = (_resourceList.get(mid)).getID().compareTo(id);
			if (diff == 0)
				return mid;
			//else{
			if (diff < 0)
				low = mid + 1;
			else
				high = mid - 1;
			//}//end else if (diff == 0)
		}//end while(low <= high)
		return low;
	}
	
	/**
	 * finds a index in a sorted vector, using the binary search algorithm
	 * @param String the DResource ID from wich to find index in SetOfResources
	 * @return int index of the DResource in SetOfResources
	 * */
	public int searchWhereToInsertMn(String id) {
		if ((_stateSort != 1)) //|| (_resourceList.size()<=3))
			sortSetOfResourcesByID();
		int low = 0;
		int high = _resourceList.size() - 1;
		while (low <= high) {
			int mid = (low + high) / 2;
			int diff = (_resourceList.get(mid)).getID().compareTo(id);
			if (diff == 0)
				return mid;
			//else{
			if (diff < 0)
				low = mid + 1;
			else
				high = mid - 1;
			//}//end else if (diff == 0)
		}//end while(low <= high)
		return low;
	}

	/**
	 * finds a index where to insert a RESOURCE in a sorted vector, using the
	 * binary search algorithm
	 * @param long the DResource key from wich to find index in SetOfResources
	 * @return int index of the DResource in SetOfResources
	 * */
	private int searchWhereToInsert(long key) {
		if (_stateSort != 0)
			sortSetOfResourcesByKey();
		int low = 0;
		int high = _resourceList.size() - 1;
		long middleKey = 0;
		while (low <= high) {
			int mid = (low + high) / 2;
			int diff = 1;
			middleKey = (_resourceList.get(mid)).getKey();
			if (middleKey > key)
				diff = 1;
			else {
				if (middleKey < key)
					diff = -1;
				else
					diff = 0;
			}
			//long diff = ((DResource)_resourceList.get(mid)).getKey() - key;
			if (diff == 0)
				return mid;
			//else{
			if (diff < 0)
				low = mid + 1;
			else
				high = mid - 1;
			//}//end else if (diff == 0)
		}//end while(low <= high)
		return low;
	}

	/**
	 * compare this resource with the specified resource
	 * @param SetOfResources the specified SetOfResources
	 * @return bolean true if this SetOfResources and the specified SetOfResources are equals
	 * false if they are not equals
	 * */
	public boolean isEquals(DSetOfResources setOfRes) {
		if (size() != setOfRes.size())
			return false;
		for (int i = 0; i < size(); i++)
			if (!getResourceAt(i).isEquals(setOfRes.getResourceAt(i)))
				return false;
		return true;
	}

}