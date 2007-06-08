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
 * DxEevnt is a class used to keep all informations conserning an Event.
 * 
 */

public class DxEvent extends DObject {

	private Assignment _assignment;

	private DResource _unity;

	private DSetOfResources _setInstructorKeys;

	/**
	 * _compositeKey is the composition of activity, type, section and unity
	 * keys of an activity wich is represent in the following format a.b.c.d
	 */
	private String _compositeKey;

	private String _fullName;

	private String _roomName; // the room key

	private String _ttsKey;// give the key of the period where event is

	private long _roomKey; // the room key

	private int _cLimit;// capacity limit for the event

	private boolean _isPlaceInAPeriod;

	/**
	 * Constructor
	 * 
	 * @param princKey
	 * @param key1
	 * @param key2
	 */
	public DxEvent(String actId, String princKey, DSetOfResources inst,
			String roomName, long key, DResource unity, Assignment assignment,
			int cLimit) {
		_fullName = actId;
		_ttsKey = "";
		_isPlaceInAPeriod = false;
		_compositeKey = princKey;
		_setInstructorKeys = inst;
		_roomName = roomName;
		_roomKey = key; // the room key
		_unity = unity;

		// setRoomFunction(((Unity) _unity.getAttach())
		// .getFirstPreferFunctionRoom());

		_assignment = assignment;
		_ttsKey = assignment.getPeriodKey();
		_cLimit = cLimit;
	}

	public DxEvent cloneEvent() {
		DxEvent eA = new DxEvent(_fullName, _compositeKey, _setInstructorKeys,
				_roomName, _roomKey, _unity, _assignment, _cLimit);
		return eA;
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
		return ((Unity) _unity.getAttach()).getDuration();
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
		return ((Unity) _unity.getAttach()).isPermanent();
	}

	public String getPrincipalRescKey() {
		return _compositeKey;
	}

	public long getRoomKey() {
		return _roomKey;
	}

	// public String getRoomName() {
	// return _roomName;
	// }

	public String getRoomName() {
		return _assignment.getRoomName();
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
		return (((Unity) _unity.getAttach()).isAssign());
	}

	// public void setRoomFunction(int function) {
	// _roomFunction = function;
	// }

	// public int getRoomFunction() {
	// return _roomFunction;
	// }

	/**
	 * check if event is already place in a period
	 * 
	 * @return
	 */
	public boolean isPlaceInAPeriod() {
		return _isPlaceInAPeriod;
	}

	public boolean isRoomFixed() {
		return _assignment.getRoomState();
	}

	public void setAssigned(boolean state) {
		((Unity) _unity.getAttach()).setAssign(state);
	}

	/**
	 * 
	 * @return
	 */
	public void setDuration(int duration) {
		((Unity) _unity.getAttach()).setDuration(duration);
	}

	/**
	 * tell to event that event is already place in a period
	 * 
	 * @param isInPeriod
	 */
	public void setInAPeriod(boolean isInPeriod) {
		_isPlaceInAPeriod = isInPeriod;
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

	public void setPermanentState(boolean state) {
		((Unity) _unity.getAttach()).setPermanent(state);
	}

	public void setRoomFixed(boolean state) {
		_assignment.setRoomState(state);
	}

	public void setRoomKey(String str) {
		_roomKey = Long.parseLong(str);
	}

	public void setRoomName(String str) {
		_assignment.setRoomName(str);
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
		//strB.append(DConst.CR_LF);
		strB.append("Full Name ");
		strB.append(this._fullName);
		strB.append(" Limit ");
		strB.append(this._cLimit);
		strB.append(" isPlacedInAPeriod ");
		strB.append(this._isPlaceInAPeriod);
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
//		strB.append(this._setInstructorKeys.toString());
		return strB.toString();
	}

}