/**
 * Created on 26 nov. 2004
 *
 *
 * Class Activity
 * Description: Activity
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
 *
 */
package dInternal.dData.dActivities;

import java.util.Vector;

import dInternal.DObject;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.dData.StandardCollection;

public class Activity extends DObject {

	/*
	 * Each field is linked to an index who serves for filtering and for
	 * selecting an object. Indexes : _departement -> 0 _activityType -> 1
	 * _activitySession -> 2 _activityVisible -> 3
	 */
	//private String _departement;// eg. génie electrique

	private String _activityType; // eg. informatique

	private int _activitySession;//
	
	private int _capacityLimit;//

	private boolean _activityVisible;//

	/**
	 * is in activities file the line between type of rooms and activity is
	 * fixed or not
	 */
	private String _idemLine;

	private DSetOfResources _setOfTypes; // contents Resources of class Type

	/**
	 * @associates String
	 */
	private Vector<String> _studentRegistered; // it contains key of students

	/**
	 * Constructor
	 */
	public Activity() {
		_activityVisible = true;
		_setOfTypes = new StandardCollection();
		_studentRegistered = new Vector<String>();
	}

	/**
	 * add a nature object in the list
	 * 
	 * @param String
	 *            the ID of the nature
	 * @return boolean result of the operation
	 */
	public boolean addType(String id) {
		Type nature = new Type();
		DResource actType = new DResource(id, nature);
		return _setOfTypes.addResource(actType, 1);
	}

	/**
	 * add a nature object in the list
	 * 
	 * @param String
	 *            the ID of the nature
	 * @param Nature
	 *            the nature to be added
	 * @return boolean result of the operation
	 */
	public boolean addType(Type type, String id) {
		DResource actType = new DResource(id, type);
		return _setOfTypes.addResource(actType, 1);
	}

	/**
	 * remove a nature object from de list
	 * 
	 * @param String
	 *            the ID of the nature
	 * @return boolean result of the operation
	 */
	public boolean removeType(String id) {
		return _setOfTypes.removeResource(id);
	}

	/**
	 * set a nature object in the list
	 * 
	 * @param Resource
	 *            the nature resource
	 * @return boolean result of the operation
	 */
	public boolean setType(DResource type) {
		return _setOfTypes.setResource(type);
	}

	/**
	 * return a nature object from de the list
	 * 
	 * @param String
	 *            the ID of the nature
	 * @return Resource the nature object
	 */
	public DResource getType(String id) {
		return _setOfTypes.getResource(id);
	}

	/**
	 * return the nature list
	 * 
	 * @return SetOfResources the list of nature object
	 */
	public DSetOfResources getSetOfTypes() {
		return _setOfTypes;
	}

	/**
	 * set activity session
	 * 
	 * @param int
	 *            the activity session
	 */
	public void setActivitySession(int activitySession) {
		_activitySession = activitySession;
	}

	/**
	 * get the activity session
	 * 
	 * @return int the activity session
	 */
	public int getActivitySession() {
		return _activitySession;
	}

	/**
	 * set activity visibility
	 * 
	 * @param boolean
	 *            the activity visibility
	 */
	public void setActivityVisibility(boolean visibility) {
		_activityVisible = visibility;
	}

	/**
	 * get the activity visibility
	 * 
	 * @return boolean the activity visibility
	 */
	public boolean isActivityVisibility() {
		return _activityVisible;
	}
	


//	/**
//	 * set activity Department
//	 * 
//	 * @param String
//	 *            the activity Department
//	 */
//	public void setActivityDepartment(String department) {
//		_departement = department;
//	}
//
//	/**
//	 * get the activity Department
//	 * 
//	 * @return String the activity Department
//	 */
//	public String getActivityDepartment() {
//		return _departement;
//	}

	/**
	 * set activity Type
	 * 
	 * @param String
	 *            the activity Type
	 */
	public void setActivityType(String activityType) {
		_activityType = activityType;
	}

	/**
	 * get the activity Type
	 * 
	 * @return String the activity Type
	 */
	public String getActivityType() {
		return _activityType;
	}

	/**
	 * return the _studentRegistered vector
	 * 
	 * @return
	 */
	public Vector getStudentRegistered() {
		return _studentRegistered;
	}

	/**
	 * add a studentKey in the _studentRegistered vector if it doesn't already
	 * exist and return true. It return false otherwise
	 * 
	 * @return
	 */
	public boolean addStudentRegistered(long studentKey) {
		if (!_studentRegistered.contains(Long.toString(studentKey))) {
			_studentRegistered.add(Long.toString(studentKey));
			return true;
		}
		return false;
	}

	/**
	 * test if _studentRegistered contains a specified element
	 * 
	 * @param studentKey
	 * @return
	 */
	public boolean isStudentRegisteredContains(long studentKey) {
		return _studentRegistered.contains(Long.toString(studentKey));
	}

	/**
	 * It compares a field with the value defined by the argument "value"
	 * 
	 * @param fieldIndex
	 *            the index of the field
	 * @param value
	 *            the value to be compared
	 * @return true if the attribute value is equal to the argument "value"
	 */
	public boolean compareByField(int fieldIndex, String value) {
		switch (fieldIndex) {
//		case 0:
//			if (_departement.equals(value))
//				return true;
//			break;
		case 1:
			if (_activityType.equals(value))
				return true;
			break;
		case 2:
			int intValue = Integer.parseInt(value);
			if (_activitySession == intValue)
				return true;
			break;
		case 3:
			boolean boolValue = Boolean.valueOf(value).booleanValue();

			if (_activityVisible == boolValue)
				return true;
		}
		return false;
	}

	/**
	 * Set a field according to the argument fieldIndex
	 * 
	 * @param fieldIndex
	 *            The index of a field
	 * @param value
	 *            The set value to the field
	 */
	public void setField(int fieldIndex, String value) {
		int intValue;
		boolean boolValue;
		switch (fieldIndex) {
//		case 0:
//			setActivityDepartment(value);
//			break;
		case 1:
			setActivityType(value);
			break;
		case 2:
			intValue = Integer.parseInt(value);
			setActivitySession(intValue);
			break;
		case 3:
			boolValue = Boolean.valueOf(value).booleanValue();
			setActivityVisibility(boolValue);
			break;
		}// end switch
	}// end method

	/**
	 * This object (which is already a string!) is itself returned.
	 * 
	 * @return the string itself
	 */
	public String toWrite() {
		return _setOfTypes.toWrite();
	}

	/**
	 * compare this resource with the specified resource
	 * 
	 * @param resource
	 *            the specified resource
	 * @return bolean true if this resource and the specified resource are
	 *         equals false if they are not equals
	 */
	public boolean isEquals(DObject act) {
		Activity activity = (Activity) act;
		if (!this._setOfTypes.isEquals(activity._setOfTypes))
			return false;
		if (this._activitySession != activity._activitySession)
			return false;
		if (this._activityVisible != activity._activityVisible)
			return false;
		return true;
	}

	/**
	 * 
	 * @param idL
	 */
	public void setIdemLine(String idL) {
		_idemLine = idL;
	}

	/**
	 * 
	 * @return
	 */
	public String getIdemLine() {
		return _idemLine;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInternal.DObject#getSelectedField()
	 */
	public long getSelectedField() {
		// TODO Auto-generated method stub
		return 0;
	}
	
//	boolean iisActivityVisibility(){
//		return this.getAttach().isActivityVisibility();
//		((DResource) this.getAttach()).isActivityVisibility()
//	}
	

}