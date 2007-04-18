/**
 * <p>Title: DX</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
package dInternal.dData.dActivities;

import java.util.StringTokenizer;

import dConstants.DConst;
import dInternal.DObject;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.dData.StandardCollection;

public class Assignment extends DObject {

	/** contains the key of the day, the begin hour and the begin minute */
	private int[] _dateAndTime;

	/**
	 * String the period complete key a.b.c where a= day key, b= sequence key c=
	 * period key
	 */
	private String _periodKey;

	/** instructor name valid only for initialization */
	private DSetOfResources _setInstructorNames;

	/** instructor key */
	private DSetOfResources _setInstructorKeys;

	/** room valid only for initialization */
	private String _roomName;

	private String _typeName;

	private boolean _isRoom;

	private boolean _isType;

	private boolean _roomFixed;

	/**
	 * Constructor
	 */
	public Assignment() {
		_dateAndTime = new int[] { 1, 8, 30 };
		_periodKey = "0.0.0";
		_roomName = "";
		_roomFixed = false;
		_isRoom = false;
		_isType = false;
		_typeName = "";
		_setInstructorNames = new StandardCollection();
		_setInstructorKeys = new StandardCollection();
	}

	/**
	 * set date and time of the bloc in this week
	 * 
	 * @param int
	 *            the key of the day
	 * @param int
	 *            the begin hour
	 * @param int
	 *            the begin minute
	 */
	public void setDateAndTime(int day, int hour, int minute) {
		_dateAndTime[0] = day;
		_dateAndTime[1] = hour;
		_dateAndTime[2] = minute;
	}

	/**
	 * set period key of the bloc in this week
	 * 
	 * @param String
	 *            the period complete key a.b.c where a= day key, b= sequence
	 *            key c= period key
	 */
	public void setPeriodKey(String periodKey) {
		_periodKey = periodKey;
	}

	/**
	 * set the instructor name
	 * 
	 * @param String
	 *            the instructor name
	 */
	public void addInstructorName(String instructor) {
		StringTokenizer inst = new StringTokenizer(instructor, ":");
		while (inst.hasMoreElements())
			_setInstructorNames.addResource(new DResource(inst.nextToken()
					.trim(), null), 0);
	}

	/**
	 * set the instructor key
	 * 
	 * @param long
	 *            the instructor key
	 */
	public void addInstructorKeys(long instructor) {
		_setInstructorKeys.setCurrentKey(instructor);
		_setInstructorKeys.addResource(new DResource(Long.toString(instructor),
				null), 0);
	}

	/**
	 * set the room name
	 * 
	 * @param String
	 *            the room name
	 */
	public void setRoomName(String room) {
		_roomName = room;
		_isRoom = true;
		_isType = false;
	}

	/**
	 * set the room name
	 * 
	 * @param String
	 *            the room name
	 */
	public void setTypeName(String room) {
		_typeName = room;
		_isRoom = false;
		_isType = true;
	}

	/**
	 * set the room name
	 * 
	 * @param String
	 *            the room name
	 */
	public boolean isRoom() {
		return _isRoom;
	}

	/**
	 * set the room name
	 * 
	 * @param String
	 *            the room name
	 */
	public boolean isType() {
		return _isType;
	}

	// /**
	// * set the room key
	// * @param long the room key
	// * */
	// public void setRoomKey(long room) {
	// _roomKey = room;
	// _roomOrTypeName = null;
	// }

	/**
	 * set the room state
	 * 
	 * @param boolean
	 *            the room state (true if room is fixed and false otherwise)
	 */
	public void setRoomState(boolean fixed) {
		_roomFixed = fixed;
	}

	/**
	 * get date and time of the bloc in this week
	 * 
	 * @return int[]
	 */
	public int[] getDateAndTime() {
		return _dateAndTime;
	}

	/**
	 * get dayKey, sequence Key and period Key of the bloc in this week
	 * 
	 * @return String String the period complete key a.b.c where a= day key, b=
	 *         sequence key c= period key
	 */
	public String getPeriodKey() {
		return _periodKey;
	}

	/**
	 * get instructor name of the bloc in this week
	 * 
	 * @return String the instructor name
	 */
	public String[] getInstructorNames() {
		String noNames[] = { DConst.NO_ROOM_INTERNAL };
		String names[] = new String[_setInstructorNames.size()];
		for (int i = 0; i < _setInstructorNames.size(); i++) {
			names[i] = _setInstructorNames.getResourceAt(i).getID();
		}
		if (_setInstructorNames.size() == 0)
			return noNames;
		return names;
	}

	/**
	 * get instructor name of the bloc in this week
	 * 
	 * @return String the instructor name
	 */
	public void emptyInstructorNames() {
		_setInstructorNames.getSetOfResources().removeAllElements();
	}

	/**
	 * get instructor key of the unit
	 * 
	 * @return String the instructor key
	 */
	public long[] getInstructorKeys() {
		long keys[] = new long[_setInstructorKeys.size()];
		for (int i = 0; i < _setInstructorKeys.size(); i++) {
			keys[i] = _setInstructorKeys.getResourceAt(i).getKey();
		}
		return keys;
	}

	public DSetOfResources getSetInstructorKeys() {
		return _setInstructorKeys;
	}

	/**
	 * get room name of the bloc in this week
	 * 
	 * @return String the room name
	 */
	public String getRoomName() {
		return _roomName;
	}

//	/**
//	 * get room name of the bloc in this week
//	 * 
//	 * @return String the room name
//	 */
//	public String getTypeName() {
//		return _typeName;
//	}

	// /**
	// * get room key of the unit
	// * @return long the room key
	// * */
	// public long getRoomKey() {
	// return _roomKey;
	// }

	/**
	 * get room state of the bloc in this week
	 * 
	 * @return boolean the room state (true if room is fixed and false
	 *         otherwise)
	 */
	public boolean getRoomState() {
		return _roomFixed;
	}

	/**
	 * compare this resource with the specified resource
	 * 
	 * @param resource
	 *            the specified resource
	 * @return bolean true if this resource and the specified resource are
	 *         equals false if they are not equals
	 */
	public boolean isEquals(DObject ass) {
		Assignment assmt = (Assignment) ass;
		for (int i = 0; i < _setInstructorKeys.size(); i++) {
			if (this._setInstructorKeys.getResourceAt(i).getKey() != assmt
					.getInstructorKeys()[i])
				return false;
		}
		// if (this._roomKey != assmt._roomKey)
		// return false;
		if (!this._roomName.trim().equalsIgnoreCase(assmt._roomName.trim()))
			return false;
		if (this._roomFixed != assmt._roomFixed)
			return false;
		if (!this._periodKey.equalsIgnoreCase(assmt._periodKey))
			return false;
		return true;
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

}