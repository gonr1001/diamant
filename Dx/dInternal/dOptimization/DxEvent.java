package dInternal.dOptimization;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */

import java.util.StringTokenizer;

import dConstants.DConst;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DObject;
import dInternal.dUtil.DXToolsMethods;

public class DxEvent extends DObject {

	/**
	 * _principalRescKey is the composition of activity, type, section and unity
	 * keys of an activity wich is represent in the following format a.b.c.d
	 */
	private String _principalRescKey;

	private String _fullName;

	private int _eventDuration; //in periods lingth

	// private long _instructorRescKey; // the instructor key
	/** instructor key */
	private DSetOfResources _setInstructorKeys;

	private long _roomRescKey; // the room key

	private boolean _roomFixed;/*
								 * the state of the event in the room true if
								 * event is fixed in the room and false
								 * otherwise
								 */

	private int _roomFunction; // the prefered function for the event

	private boolean _isAssigned = false;// tell if this event is placed in the

	// timetable

	private boolean isPermanent = false;// tell if this event is permanent in

	// the timetable

	private String _ttsKey = "";// give the key of the period where event is

	// place
	private int _cLimit;// give the key of the period where event is

	// place

	// is in a.b.c format where a = day, b= sequence, c = period
	private boolean _isPlaceInAPeriod = false;

	/**
	 * Constructor
	 * 
	 * @param princKey
	 * @param key1
	 * @param key2
	 */
	public DxEvent(String princKey, DSetOfResources inst, long key,
			int eventDuration, String eventPeriod, int cLimit) {
		_fullName = "name";
		_principalRescKey = princKey;
		_setInstructorKeys = inst;
		_roomRescKey = key;
		_eventDuration = eventDuration;
		_ttsKey = eventPeriod;
		_cLimit = cLimit;
		// place

		// _tabuList = new Vector<String>();
	}

	public String getPrincipalRescKey() {
		return _principalRescKey;
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

	/*
	 * public void setInstructorKey(long key){ _instructorRescKey= key; }
	 */

	public long getRoomKey() {
		return _roomRescKey;
	}

	/*
	 * public void setRoomKey(long key){ _roomRescKey= key; }
	 */

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

	/**
	 * 
	 * @return
	 */
	public int getDuration() {
		return _eventDuration;
	}

	/**
	 * 
	 * @return
	 */
	public void setDuration(int duration) {
		_eventDuration = duration;
	}

	// /**
	// * Tests if the specified string is a component in the tabulist vector.
	// * @param princKey
	// * @return
	// */
	// public boolean isInTabuList(String princKey) {
	// return _tabuList.contains(princKey);
	// }

	// /**
	// * Removes the first occurrence of the specified element in the tabulist
	// vector If
	// * it does not contain the element, it is unchanged.
	// * @param princKey
	// * @return
	// */
	// public boolean removeFromTabuList(String princKey) {
	// return _tabuList.remove(princKey);
	// }

	// /**
	// * Adds the specified element to the end of the tabulist vector or let it
	// unchanged
	// * if the element already exist in the vector
	// * @param princKey
	// * @return
	// */
	// public boolean addToTabuList(String princKey) {
	// if (_tabuList.contains(princKey))
	// return _tabuList.add(princKey);
	// return false;
	// }

	public void setAssigned(boolean state) {
		_isAssigned = state;
	}

	public boolean isAssigned() {
		return _isAssigned;
	}

	public void setRoomFixed(boolean state) {
		_roomFixed = state;
	}

	public boolean isRoomFixed() {
		return _roomFixed;
	}

	public void setRoomFunction(int function) {
		_roomFunction = function;
	}

	public void setRoomKey(int roomKey) {
		_roomRescKey = roomKey;
	}

	public int getRoomFunction() {
		return _roomFunction;
	}

	/**
	 * check if event is already place in a period
	 * 
	 * @return
	 */
	public boolean isPlaceInAPeriod() {
		return _isPlaceInAPeriod;
	}

	/**
	 * tell to event that event is already place in a period
	 * 
	 * @param isInPeriod
	 */
	public void setInAPeriod(boolean isInPeriod) {
		_isPlaceInAPeriod = isInPeriod;
	}

	public void setPermanentState(boolean state) {
		isPermanent = state;
	}

	public boolean getPermanentState() {
		return isPermanent;
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
			_principalRescKey = value;
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
			_roomRescKey = Long.parseLong(value);
			break;
		case 3:
			_eventDuration = Integer.parseInt(value);
			break;
		case 4:
			_ttsKey = value;
			break;
		}
	}

	public DxEvent cloneEvent() {
		DxEvent eA = new DxEvent(_principalRescKey, _setInstructorKeys,
				_roomRescKey, _eventDuration, _ttsKey, _cLimit);
		return eA;
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

	/**
	 * @param state
	 */
	public void setState(String state) {
		if (state.equalsIgnoreCase(DConst.FIXED_ROOM_STATE))
			_roomFixed = true;
		else
			_roomFixed = false;

	}

	public long getCapacityLimit() {
		return _cLimit;
	}

}