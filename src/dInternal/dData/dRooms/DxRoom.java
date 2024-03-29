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
    
    private String _type;

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
    
    public DxRoom(String sRoomName, String type,int nCapacity, int nFunction,
            Vector<Integer> viChar, String sNote, DxAvailability dxaAva) {
        super(_lUniqueKey++, sRoomName);
        _type = type;
        _nCapacity = nCapacity;
        _nFunction = nFunction;
        _viCharacteristics = viChar;
        _sComment = sNote;
        _dxaAva = dxaAva;
    }

    public int getCapacity() {
        return _nCapacity;
    }
    
    public String getType() {
        return _type;
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

        } catch (ClassCastException e) {
            // Message intended for programmers, application
            // can't continue after this error
            System.out
                    .println("isEqual in DxRoom was called with a Resource "
                            + "that could not be converted into a Room:");
            e.printStackTrace();
            System.exit(-1);
        }
        return true;
    }

    public String toWrite(String sSiteName, String sCatName) {
        String sReturn = new String();
//        if(_sComment.compareToIgnoreCase("") == 0){
//        	_sComment = "noComment";
//        }

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
    
    
//	public String toString(){
//		StringBuffer strB = new StringBuffer("Room ");
//		strB.append(DConst.CR_LF);
//		strB.append("Name ");
//		strB.append(this.getName());
//		strB.append(" Room capacity ");
//		strB.append(this._nCapacity);
////		strB.append(" isPlacedInAPeriod ");
////		strB.append(this._isPlaceInAPeriod);
////		strB.append(" room key ");
////		strB.append(this._roomKey);
////		strB.append(" roomName ");
////		strB.append(this._roomName);
////		strB.append(" compositeKey ");
////		strB.append(this._compositeKey);
////		strB.append(" ttsKey ");
////		strB.append(this._ttsKey);		
////		strB.append(this._assignment.toString());
////		strB.append(this._unity.getAttach().toString());
////		strB.append(this._setInstructorKeys.toString());
//		return strB.toString();
//	}

//        sReturn.append(this.getName());
//        sReturn.append(this.getName());
//                + DConst.ROOM_FIELD_SEPARATOR_TOKEN
//                + _nCapacity
//                + DConst.ROOM_FIELD_SEPARATOR_TOKEN
//                + _nFunction
//                + DConst.ROOM_FIELD_SEPARATOR_TOKEN
//                + this.getCharString()
//                + DConst.ROOM_FIELD_SEPARATOR_TOKEN
//                + sSiteName
//                + DConst.ROOM_FIELD_SEPARATOR_TOKEN
//                + sCatName
//                + DConst.ROOM_FIELD_SEPARATOR_TOKEN
//                + _sComment
//                + DConst.ROOM_FIELD_SEPARATOR_TOKEN
//                + _dxaAva.toWrite(DConst.AVAILABILITY_DAY_SEPARATOR_ROOM,
//                        DConst.AVAILABILITY_PERIOD_SEPARATOR)
//                + DConst.ROOM_FIELD_SEPARATOR_TOKEN;
//
//        return sReturn;
//    }

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
