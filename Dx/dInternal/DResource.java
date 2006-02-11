/**
 *
 * Title: DResource 
 * Description: 
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
 * 
 */

package dInternal;

import dInternal.DObject;

public class DResource {
	/**
	 * show if the resource has been manually added, or automatic (during
	 * extract data)
	 */
	private boolean _manuallyCreated;

	/** Resource key */
	private long _resourceKey = 0;

	/** Resource ID it can be a name, an ID, a CIP code */
	private String _resourceID;

	/** Resource Object */
	private DObject _resourceAttach;

	/**
	 * Constructor
	 * 
	 * @param String
	 *            the id of the Resource
	 * @param DXObject
	 *            the object to set in the _resourceAttach field
	 */
	public DResource(String id, DObject obj) {
		_resourceID = id;
		_resourceAttach = obj;
	}

	/**
	 * Return the resource key
	 * 
	 * @return long the resource key
	 */
	public long getKey() {
		return _resourceKey;
	}

	/**
	 * set the resource key
	 * 
	 * @param long
	 *            the resource key
	 */
	public void setKey(long k) {
		_resourceKey = k;
	}

	/**
	 * Return the resource ID
	 * 
	 * @return String the resource ID
	 */
	public String getID() {
		return _resourceID;
	}

	/**
	 * Return the resource Object
	 * 
	 * @return DXObject the resource Object
	 */
	public DObject getAttach() {
		return _resourceAttach;
	}

	/**
	 * set the resource Object
	 * 
	 * @param DXObject
	 *            the resource Object
	 */
	public void setAttach(DObject obj) {
		_resourceAttach = obj;
	}

	/**
	 * set the resource ID
	 * 
	 * @param String
	 *            the resource ID
	 */
	public void setID(String id) {
		_resourceID = id;
	}

	/**
	 * set if the resource has been manualy added, or automatic (during extract
	 * data)
	 * 
	 * @param boolean
	 *            the resource state
	 */
	public void setManuallyCreated(boolean manual) {
		_manuallyCreated = manual;
	}

	/**
	 * Return the resource state showing if the resource has been manualy added,
	 * or automatic (during extract data)
	 * 
	 * @return boolean the resource state
	 */
	public boolean isManuallyCreated() {
		return _manuallyCreated;
	}

	/**
	 * This object (which is already a string!) is itself returned.
	 * 
	 * @return the string itself
	 */
	public String toWrite(String separator) {
		String instInfo;
		String id = _resourceID;
		// id= _resourceAttach.externalKey( Long.toString(_resourceKey),
		// id);//+id;
		instInfo = id + separator;
		instInfo += _resourceAttach.toWrite();
		return instInfo;
	}

	/**
	 * compare this resource with the specified resource
	 * 
	 * @param resource
	 *            the specified resource
	 * @return bolean true if this resource and the specified resource are
	 *         equals false if they are not equals
	 */
	public boolean isEquals(DResource resource) {
		if (_resourceKey != resource._resourceKey)
			return false;
		else if (!_resourceID.equals(resource._resourceID))
			return false;
		else if (!_resourceAttach.isEquals(resource._resourceAttach))
			return false;
		return true;
	}

}