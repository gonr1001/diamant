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
import dInternal.dData.AvailableResource;
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
public class DxRoom extends DxResource implements AvailableResource {
    private static long _lUniqueKey = 1;

    private int _nCapacity;

    // Next members are not required but are provided in legacy files
    // Kept in case it would be needed
    private int _nFunction;

    private Vector<Integer> _viCharacteristics;

    private String _sComment;

    private DxAvailability _dxaAva;

    public DxRoom(String sRoomName, int nCapacity, int nFunction,
            Vector<Integer> viChar, String sNote, DxAvailability dxaAva) {
        super(_lUniqueKey++, sRoomName);
        _nCapacity = nCapacity;
        _nFunction = nFunction;
        _viCharacteristics = viChar;
        _sComment = sNote;
        _dxaAva = dxaAva;
    }

    public int getCapacity() {
        return _nCapacity;
    }

    public int getFunction() {
        return _nFunction;
    }

    public Vector<Integer> getCharacteristics() {
        return _viCharacteristics;
    }

    public String getComment() {
        return _sComment;
    }

    public DxAvailability getAvailability() {
        return _dxaAva;
    }

    public void setAvailability(DxAvailability dxaNew) {
        _dxaAva = dxaNew;
    }

    public boolean isEqual(DxResource dxrOther) {
        DxRoom dxrOtherRoom = null;
        try {
            dxrOtherRoom = (DxRoom) dxrOther;
        } catch (ClassCastException e) {
            // Message intended for programmers, application
            // can't continue after this error
            System.out
                    .println("isEqual in DxRoom was called with a Resource "
                            + "that could not be converted into a Room:");
            e.printStackTrace();
            System.exit(-1);
        }

        if (!this.getName().equalsIgnoreCase(dxrOtherRoom.getName())) {
            return false;
        }

        if (this._nCapacity != dxrOtherRoom._nCapacity) {
            return false;
        }

        if (this._nFunction != dxrOtherRoom._nFunction) {
            return false;
        }

        if (!this.getCharString()
                .equalsIgnoreCase(dxrOtherRoom.getCharString())) {
            return false;
        }

        if (!this._sComment.equalsIgnoreCase(dxrOtherRoom._sComment)) {
            return false;
        }

        if (!this._dxaAva.isEqual(dxrOtherRoom._dxaAva)) {
            return false;
        }

        return true;
    }

    public String toWrite(String sSiteName, String sCatName) {
        String sReturn = new String();

        sReturn = this.getName()
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
}
