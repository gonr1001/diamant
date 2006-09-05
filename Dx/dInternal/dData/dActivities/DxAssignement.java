/**
 * Created on Jul 24, 2006
 * 
 * Project Dx
 * Title: DxAssignement.java 
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
 */

package dInternal.dData.dActivities;

import dInternal.dData.DxResource;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import dInternal.dData.dRooms.DxRoom;

public class DxAssignement extends DxResource {
	private static long _lUniqueKey =1;
    /* Key of the day, begin hour and begin minute at respective indexes */
    private int[] _nDayAndTime;

    /*
     * String the period complete key a.b.c where a= day key, b= sequence key c=
     * period key
     */
    private String _sPeriodKey;

    /* instructor name valid only for initialization */
    private DxSetOfInstructors _dxsoiInstructors;

    private DxRoom _dxrRoom;

    private boolean _bRoomFixed;

    public DxAssignement() {
        
    }

    public DxAssignement(String sAssignementName) {
    	super(_lUniqueKey++,sAssignementName);
    	_nDayAndTime = new int[] { 1, 8, 30 };
        _sPeriodKey = "0.0.0";
        _bRoomFixed = false;

        _dxrRoom = null;
        _dxsoiInstructors = new DxSetOfInstructors();
	}

    /**
     * Get date and time of the bloc in this week
     * 
     * @return Array of integer where index 0 is day key, index 1 is starting
     *         hour and index 2 is starting minute
     */
    public int[] getDayAndTime() {
        return _nDayAndTime;
    }

    public DxSetOfInstructors getInstructors() {
        return _dxsoiInstructors;
    }

    /**
     * Get Day Key, Sequence Key and Period Key of the bloc in this week
     * 
     * @return Period complete key a.b.c where a= day key, b= sequence key c=
     *         period key
     */
    public String getPeriodKey() {
        return _sPeriodKey;
    }

    /**
     * Returns rooms in the current assignement
     * 
     * @return The room in the assignement
     */
    public DxRoom getRoom() {
        return _dxrRoom;
    }

    public boolean isEqual(DxResource dxrOther) {
        DxAssignement dxaOther = null;
        try {
            dxaOther = (DxAssignement) dxrOther;
        } catch (ClassCastException e) {
            // Message intended for programmers, application
            // can't continue after this error
            System.out
                    .println("isEqual in DxAssignement was called with a Resource "
                            + "that could not be converted into an Assignement:");
            e.printStackTrace();
            System.exit(-1);
        }

        if (!this._dxsoiInstructors.isEqual(dxaOther._dxsoiInstructors)) {
            return false;
        }
        if (!this._dxrRoom.isEqual(dxaOther._dxrRoom)) {
            return false;
        }
        if (this._bRoomFixed != dxaOther._bRoomFixed) {
            return false;
        }
        if (!this._sPeriodKey.equalsIgnoreCase(dxaOther._sPeriodKey)) {
            return false;
        }

        return true;
    }

    /**
     * Return true if room is fixed
     * 
     * @return The state of the room, true if fixed, false if not
     */
    public boolean isRoomFixed() {
        return _bRoomFixed;
    }

    /**
     * Remove all instructors from the assignement
     */
    public void removeAllInstructors() {
        _dxsoiInstructors = new DxSetOfInstructors();
    }

    /**
     * Remove instructor from the assignement
     * 
     * @param lInstKey
     *            Key of the instructor to be removed
     */
    public void removeInstructor(long lInstKey) {
        _dxsoiInstructors.removeInstructor(lInstKey);
    }

    /**
     * Set date and time of the bloc in this week
     * 
     * @param int
     *            Key of the day
     * @param int
     *            Begin hour
     * @param int
     *            Begin minute
     */
    public void setDateAndTime(int day, int hour, int minute) {
        _nDayAndTime[0] = day;
        _nDayAndTime[1] = hour;
        _nDayAndTime[2] = minute;
    }

    /**
     * Set period key of the bloc in this week
     * 
     * @param String
     *            Complete period key a.b.c where a= Day key, b= Sequence key c=
     *            Period key
     */
    public void setPeriodKey(String sPeriodKey) {
        _sPeriodKey = sPeriodKey;
    }

    /**
     * Set assigned room
     * 
     * @param dxrRoom
     *            New room of the assignement
     */
    public void setRoom(DxRoom dxrRoom) {
        _dxrRoom = dxrRoom;
    }

    /**
     * Set room state
     * 
     * @param bFixed
     *            Rooms assignement state
     */
    public void setRoomState(boolean bFixed) {
        _bRoomFixed = bFixed;
    }

	public void setSetOfInstructors(DxSetOfInstructors dxsoiInstructors) {
		// TODO Auto-generated method stub
		
	}

	public String getRoomName() {
		return _dxrRoom.getName();
	}
}
