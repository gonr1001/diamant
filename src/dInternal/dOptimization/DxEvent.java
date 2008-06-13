/**
 *
 * Title: DoNothingDlg 
 * Description: DoNothingDlg is created by DoNotingCmd
 *              The dialog is displayed nothing is done.
 *              This class is used until
 *              the corresponding command will be written.
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

package dInternal.dOptimization;

import java.util.StringTokenizer;

import dConstants.DConst;
import dInternal.DObject;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.dData.dActivities.Assignment;
import dInternal.dData.dActivities.Unity;
import dInternal.dData.dRooms.DxSite;
import dInternal.dUtil.DXToolsMethods;

/**
 * 
 * DxEvent is a class used to keep all informations concerning an Event.
 * 
 */

public class DxEvent extends DObject {

	private Assignment _assignment;

	private DResource _unity;

	private Unity _bloc;

	private DSetOfResources _setInstructorKeys;

	/**
	 * _compositeKey is the composition of activity, type, section and unity
	 * keys of an activity which is represent in the following format a.b.c.d
	 */
	private String _compositeKey;

	private String _fullName;

	private String _roomName; // the room key

	private String _ttsKey;// give the key of the period where event is

	private long _roomKey; // the room key

	private int _cLimit;// capacity limit for the event

	private boolean _isPlacedInAPeriod;

	public DxEvent() {
		_fullName = "";
		_compositeKey = "";
		_roomName = "";
		_roomKey = -1; // the room key
		_cLimit = -1;
		_setInstructorKeys = null;
		_unity = null;
		_assignment = null;
		_ttsKey = null; // assignment.getPeriodKey();
		_isPlacedInAPeriod = false;
	}

	public DxEvent eventClone() {
		DxEvent eA = new DxEvent();
		eA.setID(new String(this._fullName));
		eA.setCompositeKey(new String(this._compositeKey));
		eA._assignment = cloneAssigment();
		eA._ttsKey = new String(this._ttsKey);
		eA.setRoomName(new String(this._roomName));
		eA.setIntructors(this._setInstructorKeys.clone()); // it is necessary
		// to clone the set
		eA.setRoomKeyWithKey(this._roomKey);
		eA.setUnit(this._bloc.clone()); // its is necessary to clone the unit
		eA.setCapacityLimit(this._cLimit);
		eA._isPlacedInAPeriod = this.isPlaceInAPeriod();
		return eA;
	}

	private Assignment cloneAssigment() {
		return _assignment.clone();
	}

	public long getCapacityLimit() {
		return _cLimit;
	}

	public String getCatName(DxSite dxsCurrentSite) {
		return dxsCurrentSite.getCatNameOfRoom(_assignment.getRoomName());
	}

	/**
	 * 
	 * @return
	 */
	public int getDuration() {
		return _bloc.getDuration();
	}

	public String getfullName() {
		return _fullName;
	}

	public long[] getInstructorKey() {
		long[] keys = new long[_setInstructorKeys.size()];
		for (int i = 0; i < _setInstructorKeys.size(); i++) {
			keys[i] = _setInstructorKeys.getResourceAt(i).getKey();
		}
		return keys;
	}

	/**
	 * 
	 * @return
	 */
	public String getPeriodKey() {
		return _ttsKey;
	}

	/**
	 * 
	 * @return
	 */
	public int[] getPeriodKeyTable() {
		StringTokenizer periodKey = new StringTokenizer(_ttsKey,
				DConst.TOKENSEPARATOR);
		int[] perKey = { Integer.parseInt(periodKey.nextToken()),
				Integer.parseInt(periodKey.nextToken()),
				Integer.parseInt(periodKey.nextToken()) };
		return perKey;
	}

	public boolean getPermanentState() {
		return _bloc.isFixed(); // isPermanent();

	}

	public String getPrincipalRescKey() {
		return _compositeKey;
	}

	public long getRoomKey() {
		return _roomKey;
	}

	public String getRoomName() {
		return _assignment.getRoomName().toUpperCase();
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

	public boolean isAssigned() {
		return _bloc.isAssign();
	}

	/**
	 * check if event is already place in a period
	 * 
	 * @return
	 */
	public boolean isPlaceInAPeriod() {
		return _isPlacedInAPeriod;
	}

	public boolean isRoomFixed() {
		return _assignment.getRoomState();
	}

	public void setAssigned(boolean state) {
		_bloc.setAssign(state);
	}

	/**
	 * 
	 * @return
	 */
	public void setDuration(int duration) {
		_bloc.setDuration(duration);
	}

	/**
	 * tell to event that event is already place in a period
	 * 
	 * @param isInPeriod
	 */
	public void setInAPeriod(boolean isInPeriod) {
		_isPlacedInAPeriod = isInPeriod;
	}

	public void setInstructorKey(String str) {
		_setInstructorKeys.getSetOfResources().removeAllElements();
		int n = DXToolsMethods.countTokens(str, ":");
		for (int i = 0; i < n; i++) {
			_setInstructorKeys.setCurrentKey(Long.parseLong(DXToolsMethods
					.getToken(str, ":", i)));
			_setInstructorKeys.addResource(new DResource("", null), 0);
		}
	}

	/**
	 * set events keys by the appropriate field
	 * 
	 * @param field
	 *            0= set principal key, 1= set secondary key1, 2= set secondary
	 *            key2 4= tts Key
	 * @param value
	 */
	public void setKey(int field, String value) {
		switch (field) {
		case 0:
			_compositeKey = value;
			break;
		case 1:
			_setInstructorKeys.getSetOfResources().removeAllElements();
			int n = DXToolsMethods.countTokens(value, ":");
			for (int i = 0; i < n; i++) {
				_setInstructorKeys.setCurrentKey(Long.parseLong(DXToolsMethods
						.getToken(value, ":", i)));
				_setInstructorKeys.addResource(new DResource("", null), 0);
			}
			// /_instructorRescKey = Long.parseLong(value);
			break;
		case 2:
			_roomKey = Long.parseLong(value);
			break;
		case 3:
			setDuration(Integer.parseInt(value));
			break;
		case 4:
			_ttsKey = value;
			break;
		}
	}

	public void setPeriodKey(String str) {
		_ttsKey = str;
	}

	public void setFixed(boolean state) {
		_bloc.setFixed(state);
	}

	public void setRoomFixed(boolean state) {
		_assignment.setRoomState(state);
	}

	public void setRoomKey(String str) {
		_roomKey = Long.parseLong(str);
	}

	/**
	 * @param name
	 */
	public void setRoomKeyWithKey(long key) {
		_roomKey = key;
	}

	public void setRoomName(String str) {
		_assignment.setRoomName(str);
		_roomName = str;
	}

	/**
	 * @param state
	 */
	public void setState(String state) {
		if (state.equalsIgnoreCase(DConst.FIXED_ROOM_STATE))
			_assignment.setRoomState(true);
		else
			_assignment.setRoomState(false);

	}

	public String toString() {
		StringBuffer strB = new StringBuffer();
		// strB.append(DConst.CR_LF);
		strB.append("Full Name ");
		strB.append(this._fullName);
		strB.append(" Limit ");
		strB.append(this._cLimit);
		strB.append(" isPlacedInAPeriod ");
		strB.append(this._isPlacedInAPeriod);
		strB.append(" room key ");
		strB.append(this._roomKey);
		strB.append(" roomName ");
		strB.append(this._roomName);
		strB.append(" compositeKey ");
		strB.append(this._compositeKey);
		strB.append(" ttsKey ");
		strB.append(this._ttsKey);
		strB.append(this._assignment.toString());
		strB.append(this._unity.getAttach().toString());
		strB.append(DConst.CR_LF);
		// strB.append(this._setInstructorKeys.toString());
		return strB.toString();
	}

	public void setID(String id) {
		_fullName = id;
	}

	public void setCompositeKey(String compositeKey) {
		_compositeKey = compositeKey;
	}

	public void setCapacityLimit(int limit) {
		_cLimit = limit;
	}

	public void setIntructors(DSetOfResources setInstructorKeys) {
		_setInstructorKeys = setInstructorKeys;
	}

	public void setUnit(Unity unit) {
		_bloc = unit;
	}

	public void setAssigment(Assignment assignment) {
		_assignment = assignment;
		_ttsKey = _assignment.getPeriodKey();
	}

}