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

import dInternal.dData.DxAvailability;
import dInternal.dData.DxRessource;


/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxCategory is a class used to:
 * <p>
 * Holds a category
 * <p>
 * 
 */
public class DxCategory extends DxRessource{
    private DxSetOfRooms _dxsorRooms;

    public DxCategory() {
        _dxsorRooms = new DxSetOfRooms();
    }
    
    public DxCategory(long lKey) {
        super(lKey);
        _dxsorRooms = new DxSetOfRooms();
    }
    
    public DxCategory(String sName) {
        super(sName);
        _dxsorRooms = new DxSetOfRooms();
    }
    
    public DxCategory(long lKey, String sName) {
        super(lKey,sName);
        _dxsorRooms = new DxSetOfRooms();
    }

    public String getCategoryName() {
        return this.getRessourceName();
    }

    public long getCategoryKey() {
        return this.getRessourceKey();
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

    public DxSetOfRooms getDxSetOfRooms() {
        return _dxsorRooms;
    }
}
