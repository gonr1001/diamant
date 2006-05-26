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
 *
 * 
 * 
 */
package dInternal.dData.dRooms;

import java.util.Comparator;


/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxCategory is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
public class DxCategory {
    private long _lKey;

    private String _sName;

    private DxSetOfRooms _dxsorRooms;

    public DxCategory(long lKey, String sName) {
        _lKey = lKey;
        _sName = sName;
        _dxsorRooms = new DxSetOfRooms();
    }

    public String getCategoryName() {
        return _sName;
    }

    public long getCategoryKey() {
        return _lKey;
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

    public void addRoom(DxRoom dxrRoom) {
        _dxsorRooms.addRoom(dxrRoom);

    }

    public static Comparator<DxCategory> KeyComparator = new Comparator<DxCategory>() {
        public int compare(DxCategory arg0, DxCategory arg1) {
            DxCategory left = arg0;
            DxCategory right = arg1;
            long diff = left._lKey - right._lKey;
            if (diff > 0)
                return 1;
            if (diff < 0)
                return -1;
            return 0;
        }
    };

    public int getRoomCount() {
        return _dxsorRooms.getRoomCount();
    }
}
