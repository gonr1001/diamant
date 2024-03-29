/*
 * Created on 26 nov. 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dInternal.dData.dActivities;

import dConstants.DConst;
import dInternal.DObject;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.dData.StandardCollection;
import dInternal.dOptimization.DxEvent;

public class Unity extends DObject {

	// /* Each field is linked to an index who serves for filtring and for
	// selecting
	// * an object.
	// * Indexes :
	// * _duration -> 0
	// * _preferSequence -> 1
	// * _assign -> 2
	// * _permanent -> 3
	// * _isCyclic -> 4
	// */

	/** duration of the bloc (in minutes) */
	private int _duration;

	/** flag to indicate the bloc is assigned in a period */
	private boolean _assign;

	/** flag to indicate if the bloc is fixed in a period */
	private boolean _fixed;

	private boolean _isCyclic;

	/** all cycles where bloc is assigned */
	private DSetOfResources _setOfAssignments;

	/**
	 * Constructor
	 * */
	public Unity() {
		_setOfAssignments = new StandardCollection();
		_duration = 1;// ???
		_isCyclic = true; // ???
	}

	/**
	 * add a cycle for period assignment
	 * 
	 * @param Resource
	 *            the cycleAssignment resource
	 * @return boolean the operation result
	 * */
	public boolean addAssignment(DResource cycleAss) {
		return _setOfAssignments.addResource(cycleAss, 1);
	}

	// /**
	// * It compares a field with the value defined by the argument "value"
	// * @param fieldIndex the index of the field
	// * @param value the value to be compared
	// * @return true if the attribute value is equal to the argument "value"
	// */
	//
	// public boolean compareByField(int fieldIndex, String value) {
	// boolean boolValue;
	// int intValue;
	// switch (fieldIndex) {
	// case 0:
	// intValue = Integer.parseInt(value);
	// if (_duration == intValue)
	// return true;
	// break;
	//
	// case 2:
	// boolValue = Boolean.valueOf(value).booleanValue();
	// if (_assign == boolValue)
	// return true;
	// break;
	// case 3:
	// boolValue = Boolean.valueOf(value).booleanValue();
	// if (_fixed == boolValue)
	// return true;
	// break;
	// case 4:
	// boolValue = Boolean.valueOf(value).booleanValue();
	// if (_isCyclic == boolValue)
	// return true;
	// break;
	// }
	// return false;
	// }

	public boolean compareToAssign(boolean value) {
		return _assign == value;
	}

	/**
	 * get a cycle for period assignment
	 * 
	 * @param String
	 *            the cycle ID
	 * @return Resource the operation result
	 * */
	public DResource getAssignment(String cycle) {
		return _setOfAssignments.getResource(cycle);
	}

	/**
	 * get duration
	 * 
	 * @return int the duration in minutes
	 * */
	public int getDuration() {
		return _duration;
	}


	/**
	 * get the cycle assignment list
	 * 
	 * @return SetOfResources the cycle assignment list
	 * */
	public DSetOfResources getSetOfAssignments() {
		return _setOfAssignments;
	}

	/**
	 * 
	 * 
	 */
	public boolean isFixed() {
		return _fixed;
	}

	/**
	 * 
	 * 
	 */
	public boolean isAssign() {
		return _assign;
	}

	/**
	 * compare this resource with the specified resource
	 * 
	 * @param resource
	 *            the specified resource
	 * @return bolean true if this resource and the specified resource are
	 *         equals false if they are not equals
	 * */
	@Override
	public boolean isEquals(DObject unit) {
		Unity unity = (Unity) unit;
		/*
		 * if(this._assign!= unity._assign) return false;
		 */
		if (this._duration != unity._duration)
			return false;
		if (this._isCyclic != unity._isCyclic)
			return false;
		/*
		 * if(this._permanent!= unity._permanent) return false;
		 */
		// if (this._preferSequence != unity._preferSequence)
		// return false;
		if (!this._setOfAssignments.isEquals(unity._setOfAssignments))
			return false;
		return true;
	}

	// /**
	// * is permanent state of the bloc
	// * @param boolean the bloc state
	// * */
	// public boolean isPermanent() {
	// return _fixed;
	// }

	/**
	 * set if bloc is fixed
	 * 
	 * @param boolean the bloc state
	 * */
	public void setAssign(boolean assign) {
		_assign = assign;
	}

	// public void setAssign(String value) {
	// boolean boolValue = Boolean.valueOf(value).booleanValue();
	// setAssign(boolValue);
	// }

	/**
	 * set duration
	 * 
	 * @param int the duration in minutes
	 * */
	public void setDuration(int duration) {
		_duration = duration;
	}

	/**
	 * Set a field according to the argument fieldIndex
	 * 
	 * @param fieldIndex
	 *            The index of a field
	 * @param value
	 *            The set value to the field
	 */
	
	@Override
	public void setField(int fieldIndex, String value) {
//		boolean boolValue;
		int intValue;
		switch (fieldIndex) {
		case 0:
			intValue = Integer.parseInt(value);
			setDuration(intValue);
			break;
		case 1:
			// intValue = Integer.parseInt(value);
			// setPreferSequence(intValue);
			break;
		// case 2:
		// boolValue = Boolean.valueOf(value).booleanValue();
		// setAssign(boolValue);
		// break;
		// case 3:
		// boolValue = Boolean.valueOf(value).booleanValue();
		// setFixed(boolValue);
		// break;
		// case 4:
		// boolValue = Boolean.valueOf(value).booleanValue();
		// setCyclic(boolValue);
		// break;
		}
	}

	// /*
	// * set the first index of the activity prefer function room
	// * @param int the function
	// */
	// public void setFirstPreferFunctionRoom(int function) {
	// String func = String.valueOf(function);
	// if (_preferFunctionSetOfRooms.size() >= 1) {
	// _preferFunctionSetOfRooms.getResourceAt(0).setID(func);
	// } else
	// this.addPreferFunctionRoom(func);
	// }

	/**
	 * it sets this state
	 * 
	 * @param boolean the bloc state
	 * */
	public void setFixed(boolean fixed) {
		_fixed = fixed;
	}

	@Override
	public String toString() {
		StringBuffer strB = new StringBuffer(DConst.CR_LF);
		strB.append("Unity ");
		strB.append(DConst.CR_LF);
		// strB.append(" duration ");
		// strB.append(this._duration);
		strB.append(" assign  ");
		strB.append(this._assign);
		strB.append(" isCyclic ");
		strB.append(this._isCyclic);
		strB.append(" isPermanent  ");
		strB.append(this._fixed);
		// strB.append(" functionSetOfRooms ");
		// strB.append(this._preferFunctionSetOfRooms.toString());
		// strB.append(" set of assignments ");
		// strB.append(this._setOfAssignments.toString());
		return strB.toString();
	}

	/**
	 *This object (which is already a string!) is itself returned.
	 * 
	 * @return the string itself
	 * */
	@Override
	public String toWrite() {
		return "";
	}

	public void updateWith(DxEvent event) {
		this.setAssign(event.isAssigned());
		this.setFixed(event.getPermanentState());
		this.setDuration(event.getDuration());
	}
	
	
	@Override
	public Unity clone() {
		Unity u = new Unity();
		u._duration = this._duration;
		u._assign = this._assign;
		u._fixed = this._fixed;
		u._isCyclic = this._isCyclic;

		// private DSetOfResources _preferFunctionSetOfRooms;
		// u._setOfAssignments = this._setOfAssignments.clone();
		return u;
	}

}