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

import java.util.Comparator;
import java.util.Vector;

import dInternal.dData.DxAvailability;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxRoom is a class used to:
 * <p>
 * TODO:insert comments
 * <p> 
 * 
 */
public class DxRoom {
    private long _lKey;
    private String _sName;
    private int _nCapacity;
    
        //Next members are not required but are provided in legacy files
        //Kept in case it would be needed
    private int _nFunction;
    private Vector<Integer> _viCharacteristics;
    private String _sComment;
    private DxAvailability _dxaAva;
    
    public DxRoom(String sRoomName, int nCapacity, int nFunction, Vector<Integer> viChar, String sNote, DxAvailability dxaAva) {
        _lKey=-1;
        _sName=sRoomName;
        _nCapacity=nCapacity;
        _nFunction=nFunction;
        _viCharacteristics=viChar;
        _sComment=sNote;
        _dxaAva=dxaAva;
    }

    public String getRoomName() {
        return _sName;
    }

    public long getRoomKey() {
        return _lKey;
    }
    
    public int getRoomCapacity(){
        return _nCapacity;
    }
    
    public int getRoomFunction(){
        return _nFunction;
    }

    public Vector<Integer> getRoomCharacteristics(){
        return _viCharacteristics;
    }
    
    public String getRoomComment(){
        return _sComment;
    }
    
    public DxAvailability getRoomAvailability(){
        return _dxaAva;
    }
    
    protected void setKey(long lKey)
    {
        _lKey=lKey;
    }
    
    public static Comparator<DxRoom> KeyComparator = new Comparator<DxRoom>() {
        public int compare(DxRoom arg0, DxRoom arg1) {
            DxRoom left = arg0;
            DxRoom right = arg1;
            long diff = left._lKey - right._lKey;
            if (diff > 0)
                return 1;
            if (diff < 0)
                return -1;
            return 0;
        }
    };
}
