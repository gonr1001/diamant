/**
 * Created on May 23, 2006
 * 
 * 
 * Title: DxCategory.java 
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
package dInternal.dData.dRooms;

import dInternal.dData.DxAvailability;
import dInternal.dData.DxResource;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxCategory is a class used to:
 * <p>
 * Holds a category
 * <p>
 * 
 */
public class DxCategory extends DxResource {
	private static long _lUniqueKey = 1;

	private DxSetOfRooms _dxsorRooms;

	public DxCategory(String sName) {
		super(_lUniqueKey++, sName);
		_dxsorRooms = new DxSetOfRooms();
	}
    
    public void addRoom(DxRoom dxrRoom) {
        _dxsorRooms.addRoom(dxrRoom);

    }

	public DxRoom getRoom(long lRoomKey) {
		return _dxsorRooms.getRoom(lRoomKey);
	}

	public DxRoom getRoom(String sRoomName) {
		return _dxsorRooms.getRoom(sRoomName);
	}

	public String getRoomName(long lRoomKey) {
		return _dxsorRooms.getRoomName(lRoomKey);
	}

	public int getRoomCapacity(long lRoomKey) {
		return _dxsorRooms.getRoomCapacity(lRoomKey);
	}

	public int getRoomCapacity(String sRoomName) {
		return _dxsorRooms.getRoomCapacity(sRoomName);
	}

	public int getRoomCount() {
		return _dxsorRooms.getRoomCount();
	}

	public DxAvailability getRoomAvailability(long lRoomKey) {
		return _dxsorRooms.getRoomAvailability(lRoomKey);
	}

	public DxAvailability getRoomAvailability(String sRoomName) {
		return _dxsorRooms.getRoomAvailability(sRoomName);
	}

	public long getRoomKeyByName(String sSiteName) {
		return _dxsorRooms.getRoomKeyByName(sSiteName);
	}

	public DxSetOfRooms getSetOfRooms() {
		return _dxsorRooms;
	}
    
    public boolean isEqual(DxResource dxrOther) {
        DxCategory dxcOtherCat = null;
        try {
            dxcOtherCat = (DxCategory) dxrOther;
        } catch (ClassCastException e) {
            // Message intended for programmers, application
            // can't continue after this error
            System.out
                    .println("isEqual in DxCategory was called with a Resource "
                            + "that could not be converted into a Category:");
            e.printStackTrace();
            System.exit(-1);
        }
        
        if (!this.getName().equalsIgnoreCase(
                dxcOtherCat.getName())) {
            return false;
        }

        if (!this._dxsorRooms.isEqual(dxcOtherCat._dxsorRooms)) {
            return false;
        }

        return true;
    }

	public String toWrite(String sSiteName) {
		return _dxsorRooms.toWrite(sSiteName, this.getName());
	}
}
