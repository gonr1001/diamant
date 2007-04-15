/*
 * Created on 26 nov. 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dInternal.dData.dActivities;

import dInternal.DObject;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DValue;
import dInternal.dData.StandardCollection;
import dInternal.dOptimization.DxEvent;
import dInternal.dOptimization.EventDx;

public class Unity extends DObject {

	/* Each field is linked to an index who serves for filtring and for selecting
	 * an object.
	 * Indexes :
	 * _duration -> 0
	 * _preferSequence -> 1
	 * _assign -> 2
	 * _permanent -> 3
	 * _isCyclic -> 4
	 */

	/** duration of the bloc (in minutes)*/
	private int _duration = 1;

	/** prefere sequence in a day
	 *-1= no prefer sequence; 0= AM; 1=PM; 2= evening
	 */
	private int _preferSequence = -1;

	/**if bloc is assigned in a period*/
	private boolean _assign;

	/**if bloc is permanent assigned in a period*/
	private boolean _permanent;

	/**prefer function rooms of this bloc*/
	private DSetOfResources _preferFunctionSetOfRooms;

//	/**activities that must be placed before this bloc*/
//	private DSetOfResources _activitiesBefore;
//
//	/**activities that must be placed after this bloc*/
//	private DSetOfResources _activitiesAfter;

	/**all cycles where bloc is assigned*/
	private DSetOfResources _setOfAssignments;

	private boolean _isCyclic = true;

	/**
	 * Constructor
	 * */
	public Unity() {
		_preferFunctionSetOfRooms = new StandardCollection();
//		_activitiesBefore = new StandardCollection();
//		_activitiesAfter = new StandardCollection();
		_setOfAssignments = new StandardCollection();
	}

	/**
	 * set duration
	 * @param int the duration in minutes
	 * */
	public void setDuration(int duration) {
		_duration = duration;
	}

	/**
	 * get duration
	 * @return int the duration in hour
	 * */
	public int getDuration() {
		return _duration;
	}

	/**
	 * set if bloc is fixed
	 * @param boolean the bloc state
	 * */
	public void setAssign(boolean assign) {
		_assign = assign;
	}

	public void setCyclic(boolean cyclic) {
		_isCyclic = cyclic;
	}

	/**
	 * get fixed state of the bloc
	 * @param boolean the bloc state
	 * */
	public boolean isAssign() {
		return _assign;
	}

	/**
	 * is permanent state of the bloc
	 * @param boolean the bloc state
	 * */
	public boolean isPermanent() {
		return _permanent;
	}

	/**
	 * set if bloc is solidify
	 * @param boolean the bloc state
	 * */
	public void setPermanent(boolean permanent) {
		_permanent = permanent;
	}

//	/**
//	 * set prefer sequence
//	 * *-1= no prefer sequence; 0= AM; 1=PM; 2= evening
//	 * @param int the prefer sequence
//	 * */
//	public void setPreferSequence(int prefer) {
//		_preferSequence = prefer;
//	}

//	/**
//	 * add a cycle for period assignment
//	 * @param String the cycle ID
//	 * @return boolean the operation result
//	 * */
//	public boolean addAssignment(String cycle) {
//		return _setOfAssignments.addResource(
//				new DResource(cycle, new DValue()), 1);
//	}

	/**
	 * get a cycle for period assignment
	 * @param String the cycle ID
	 * @return Resource the operation result
	 * */
	public DResource getAssignment(String cycle) {
		return _setOfAssignments.getResource(cycle);
	}

	/**
	 * get the cycle assignment list
	 * @return SetOfResources the cycle assignment list
	 * */
	public DSetOfResources getSetOfAssignments() {
		return _setOfAssignments;
	}

	/**
	 * add a cycle for period assignment
	 * @param Resource the cycleAssignment resource
	 * @return boolean the operation result
	 * */
	public boolean addAssignment(DResource cycleAss) {
		return _setOfAssignments.addResource(cycleAss, 1);
	}

//	/**
//	 * add activity in activity before list
//	 * @param String the activity name
//	 * @return boolean the operation result
//	 * */
//	public boolean addActivityBefore(String activityName) {
//		return _activitiesBefore.addResource(new DResource(activityName,
//				new DValue()), 1);
//	}

//	/**
//	 * add activity in activity after list
//	 * @param String the activity name
//	 * @return boolean the operation result
//	 * */
//	public boolean addActivityAfter(String activityName) {
//		return _activitiesAfter.addResource(new DResource(activityName,
//				new DValue()), 1);
//	}

	/**
	 * add activity prefer function room
	 * @param String the room function
	 * @return boolean the operation result
	 * */
	public boolean addPreferFunctionRoom(String function) {
		return _preferFunctionSetOfRooms.addResource(new DResource(function,
				new DValue()), 1);
	}

	/**
	 * get activity prefer function room
	 * @param String the room function
	 * @return boolean the operation result
	 * */
	public DSetOfResources getPreferFunctionRoom() {
		return _preferFunctionSetOfRooms;
	}

	/**
	 * get first index of the activity prefer function room
	 * @return int the function of the room
	 * */
	public int getFirstPreferFunctionRoom() {
		if (_preferFunctionSetOfRooms.size() >= 1) {
			String function = _preferFunctionSetOfRooms.getResourceAt(0)
					.getID();
			return Integer.parseInt(function);
		}
		return 0;
	}

	/*
	 * set the first index of the activity prefer function room
	 * @param int the function
	 */
	public void setFirstPreferFunctionRoom(int function) {
		String func = String.valueOf(function);
		if (_preferFunctionSetOfRooms.size() >= 1) {
			_preferFunctionSetOfRooms.getResourceAt(0).setID(func);
		} else
			this.addPreferFunctionRoom(func);
	}

	/**
	 * set activity prefer function room
	 * @param String the room function
	 * @return boolean the operation result
	 * */
	public void setPreferFunctionRoom(DSetOfResources preferFuncList) {
		_preferFunctionSetOfRooms = preferFuncList;
	}

//	/**
//	 * remove activity in activity before list
//	 * @param String the activity name
//	 * @return boolean the operation result
//	 * */
//	public boolean removeActivityBefore(String activityName) {
//		return _activitiesBefore.removeResource(activityName);
//	}
//
//	/**
//	 * remove activity in activity after list
//	 * @param String the activity name
//	 * @return boolean the operation result
//	 * */
//	public boolean removeActivityAfter(String activityName) {
//		return _activitiesAfter.removeResource(activityName);
//	}

//	/**
//	 * remove activity prefer function room
//	 * @param String the room function
//	 * @return boolean the operation result
//	 * */
//	public boolean removePreferFunctionRoom(String activityName) {
//		return _preferFunctionSetOfRooms.removeResource(activityName);
//	}

	/**
	 * remove a cycle from a bloc
	 * @param String the cycle ID
	 * @return boolean the operation result
	 * */
	public boolean removeCycleAssignment(String cycle) {
		return _setOfAssignments.removeResource(cycle);
	}

	/**
	 *This object (which is already a string!) is itself returned.
	 * @return the string itself
	 * */
	public String toWrite() {
		return "";
	}

	/**
	 * compare this resource with the specified resource
	 * @param resource the specified resource
	 * @return bolean true if this resource and the specified resource are equals
	 * false if they are not equals
	 * */
	public boolean isEquals(DObject unit) {
		Unity unity = (Unity) unit;
		/*if(this._assign!= unity._assign)
		 return false;*/
		if (this._duration != unity._duration)
			return false;
		if (this._isCyclic != unity._isCyclic)
			return false;
		/*if(this._permanent!= unity._permanent)
		 return false;*/
		if (this._preferSequence != unity._preferSequence)
			return false;
		if (!this._setOfAssignments.isEquals(unity._setOfAssignments))
			return false;
		return true;
	}

	/**
	 * Set a field according to the argument fieldIndex
	 * @param fieldIndex The index of a field
	 * @param value The set value to the field
	 */
	public void setField(int fieldIndex, String value) {
		boolean boolValue;
		int intValue;
		switch (fieldIndex) {
		case 0:
			intValue = Integer.parseInt(value);
			setDuration(intValue);
			break;
		case 1:
//			intValue = Integer.parseInt(value);
//			setPreferSequence(intValue);
			break;
		case 2:
			boolValue = Boolean.valueOf(value).booleanValue();
			setAssign(boolValue);
			break;
		case 3:
			boolValue = Boolean.valueOf(value).booleanValue();
			setPermanent(boolValue);
			break;
//		case 4:
//			boolValue = Boolean.valueOf(value).booleanValue();
//			setCyclic(boolValue);
//			break;
		}
	}

	/**
	 * It compares a field with the value defined by the argument "value"
	 * @param fieldIndex the index of the field
	 * @param value the value to be compared
	 * @return true if the attribute value is equal to the argument "value"
	 */

	public boolean compareByField(int fieldIndex, String value) {
		boolean boolValue;
		int intValue;
		switch (fieldIndex) {
		case 0:
			intValue = Integer.parseInt(value);
			if (_duration == intValue)
				return true;
			break;
		case 1:
			intValue = Integer.parseInt(value);
			if (_preferSequence == intValue)
				return true;
			break;
		case 2:
			boolValue = Boolean.valueOf(value).booleanValue();
			if (_assign == boolValue)
				return true;
			break;
		case 3:
			boolValue = Boolean.valueOf(value).booleanValue();
			if (_permanent == boolValue)
				return true;
			break;
		case 4:
			boolValue = Boolean.valueOf(value).booleanValue();
			if (_isCyclic == boolValue)
				return true;
			break;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see dInternal.DObject#getSelectedField()
	 */
	public long getSelectedField() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void updateWith(EventDx event) {
		this.setAssign(event.isAssigned());
		this.setPermanent(event.getPermanentState());
		this.setDuration(event.getDuration());
		this.setFirstPreferFunctionRoom(event.getRoomFunction());
	}
	public void updateWith(DxEvent event) {
		this.setAssign(event.isAssigned());
		this.setPermanent(event.getPermanentState());
		this.setDuration(event.getDuration());
		this.setFirstPreferFunctionRoom(event.getRoomFunction());
	}
}