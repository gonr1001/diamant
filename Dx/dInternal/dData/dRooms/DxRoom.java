/**
 * Created on May 23, 2006
 * 
 * 
 * Title: DxRoom.java 
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

import java.util.Vector;

import dConstants.DConst;
import dInternal.dData.DxAvailability;
import dInternal.dData.DxResource;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxRoom is a class used to:
 * <p>
 * Holds a room informations
 * <p>
 * 
 */
public class DxRoom extends DxResource {
    private static long _lUniqueKey=1;
    private int _nCapacity;

    // Next members are not required but are provided in legacy files
    // Kept in case it would be needed
    private int _nFunction;

    private Vector<Integer> _viCharacteristics;

    private String _sComment;

    private DxAvailability _dxaAva;

    public DxRoom(String sRoomName, int nCapacity, int nFunction,
            Vector<Integer> viChar, String sNote, DxAvailability dxaAva) {
        super(_lUniqueKey++,sRoomName);
        _nCapacity = nCapacity;
        _nFunction = nFunction;
        _viCharacteristics = viChar;
        _sComment = sNote;
        _dxaAva = dxaAva;
    }

    public String getRoomName() {
        return this.getRessourceName();
    }

    public long getRoomKey() {
        return this.getRessourceKey();
    }

    public int getRoomCapacity() {
        return _nCapacity;
    }

    public int getRoomFunction() {
        return _nFunction;
    }

    public Vector<Integer> getRoomCharacteristics() {
        return _viCharacteristics;
    }

    public String getRoomComment() {
        return _sComment;
    }

    public DxAvailability getRoomAvailability() {
        return _dxaAva;
    }

    public void setRoomAvailability(DxAvailability dxaNew) {
        _dxaAva = dxaNew;
    }

    public String toWrite(String sSiteName, String sCatName) {
        String sReturn = new String();

        sReturn = this.getRoomName()
                + DConst.ROOM_FIELD_SEPARATOR_TOKEN
                + _nCapacity
                + DConst.ROOM_FIELD_SEPARATOR_TOKEN
                + _nFunction
                + DConst.ROOM_FIELD_SEPARATOR_TOKEN
                + this.getCharString()
                + DConst.ROOM_FIELD_SEPARATOR_TOKEN
                + sSiteName
                + DConst.ROOM_FIELD_SEPARATOR_TOKEN
                + sCatName
                + DConst.ROOM_FIELD_SEPARATOR_TOKEN
                + _sComment
                + DConst.ROOM_FIELD_SEPARATOR_TOKEN
                + _dxaAva.toWrite(DConst.AVAILABILITY_DAY_SEPARATOR_ROOM,
                        DConst.AVAILABILITY_PERIOD_SEPARATOR)
                + DConst.ROOM_FIELD_SEPARATOR_TOKEN;

        return sReturn;
    }

    private String getCharString() {
        StringBuffer sbChars = new StringBuffer();
        
        for (int i = 0; i < _viCharacteristics.size(); i++) {
            sbChars.append(_viCharacteristics.get(i).intValue());
            if (i < (_viCharacteristics.size() - 1)) {
                sbChars.append(DConst.ROOM_CHAR_SEPARATOR_TOKEN);
            }
        }
        return sbChars.toString();
    }

    public boolean isEquals(DxRoom dxrOtherRoom) {
        if (!this.getRoomName().equalsIgnoreCase(
                dxrOtherRoom.getRoomName())) {
            return false;
        }
        
        if (this._nCapacity!=dxrOtherRoom._nCapacity) {
            return false;
        }
        
        if (this._nFunction!=dxrOtherRoom._nFunction) {
            return false;
        }
        
        if (!this.getCharString().equalsIgnoreCase(
                dxrOtherRoom.getCharString())) {
            return false;
        }
        
        if (!this._sComment.equalsIgnoreCase(
                dxrOtherRoom._sComment)) {
            return false;
        }
        
        if(!this._dxaAva.isEquals(dxrOtherRoom._dxaAva))
        {
            return false;
        }
        
        return true;
    }
}
