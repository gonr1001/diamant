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
import dInternal.dData.dActivities.Assignment;
import dInternal.dData.dActivities.Unity;
import dInternal.dData.dRooms.DxRoom;
import dInternal.dData.dRooms.DxSite;
import dInternal.dUtil.DXToolsMethods;

public class DxEvent extends DObject {

	/**
	 * _principalRescKey is the composition of activity, type, section and unity
	 * keys of an activity wich is represent in the following format a.b.c.d
	 */
	private String _principalRescKey;

	private String _fullName;

	private DSetOfResources _setInstructorKeys;

	private DResource _unity;
	
	private long _roomKey; // the room key

	private Assignment _assignment;

	private String _roomName; // the room key

	private int _roomFunction; // the prefered function for the event

	private String _ttsKey;// give the key of the period where event is

	private int _cLimit;// give the key of the period where event is

	private boolean _isPlaceInAPeriod;

	/**
	 * Constructor
	 * 
	 * @param princKey
	 * @param key1
	 * @param key2
	 */
	public DxEvent(String princKey, DSetOfResources inst, String roomName, long key,
			DResource unity, Assignment assignment, int cLimit) {
		_fullName = "name";
		_ttsKey = "";
		_isPlaceInAPeriod = false;
		_principalRescKey = princKey;
		_setInstructorKeys = inst;
		_roomName = roomName;
		 _roomKey = key; // the room key
		_unity = unity;

		setRoomFunction(((Unity) _unity.getAttach())
				.getFirstPreferFunctionRoom());
		
		_assignment = assignment;
		_ttsKey = assignment.getPeriodKey();
		_cLimit = cLimit;
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

	public long getRoomKey() {
		return _roomKey;
	}
//	public String getRoomName() {
//		return _roomName;
//	}

	public String getRoomName() {
		return _assignment.getRoomName();
	}
	
	public void setRoomName(String str) {
		_assignment.setRoomName(str);
	}

	public String getCatName(DxSite dxsCurrentSite) {
//		if (dxsCurrentSite.isInCatName(_assignment.getRoomName())) {
//			_assignment.getRoomName(); // is a category
//		}
		return dxsCurrentSite.getCatNameOfRoom(_assignment.getRoomName());
	}
	
//	public String getCatName() {
//		if (false) {
//			_assignment.getRoomName(); // is a category
//		}
//		return "Classe";
//	}
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
		return ((Unity) _unity.getAttach()).getDuration();
	}

	/**
	 * 
	 * @return
	 */
	public void setDuration(int duration) {
		((Unity) _unity.getAttach()).setDuration(duration);
	}


	public void setAssigned(boolean state) {
		((Unity) _unity.getAttach()).setAssign(state);
	}

	public boolean isAssigned() {
		return (((Unity) _unity.getAttach()).isAssign());
	}


	public boolean isRoomFixed() {
		return _assignment.getRoomState();
	}

	public void setRoomFunction(int function) {
		_roomFunction = function;
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
		((Unity) _unity.getAttach()).setPermanent(state);
	}

	public boolean getPermanentState() {
		return ((Unity) _unity.getAttach()).isPermanent();
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

	public DxEvent cloneEvent() {
		DxEvent eA = new DxEvent(_principalRescKey, _setInstructorKeys,
				_roomName, _roomKey, _unity, _assignment, _cLimit);
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
			_assignment.setRoomState(true);
		else
			_assignment.setRoomState(false);

	}

	public long getCapacityLimit() {
		return _cLimit;
	}

	public void setPeriodKey(String str) {
		_ttsKey = str;
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

	public void setRoomKey(String str) {
		_roomKey = Long.parseLong(str);
	}
	

}