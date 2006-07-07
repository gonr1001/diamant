/**
 * Created on May 23, 2006
 * 
 * 
 * Title: DxSetofRooms.java 
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

import java.util.Iterator;

import dConstants.DConst;
import dInternal.dData.DxAvailability;
import dInternal.dData.DxResource;
import dInternal.dData.DxSetOfResources;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxSetofRooms is a class used to:
 * <p>
 * Holds a collection of rooms
 * <p>
 * 
 */
public class DxSetOfRooms extends DxSetOfResources {

    public void addSetOfRooms(DxSetOfRooms dxsor) {
        this.addSetOfResources(dxsor);
    }
    
    public DxResource findEquivalent(DxResource dxrSearch) {
        // TODO Auto-generated method stub
        return null;
    }

    public DxRoom getRoom(long lRoomKey) {
        try {
            return (DxRoom) this.getResource(lRoomKey);
        } catch (Exception e) {
            // Null pointer exception will be thrown if room doesnt exist
            return null;
        }
    }

    public DxRoom getRoom(String sRoomName) {
        try {
            return (DxRoom) this.getResource(sRoomName);
        } catch (Exception e) {
            // Null pointer exception will be thrown if room doesnt exist
            return null;
        }
    }

    public int getRoomCount() {
        return this.size();
    }

    public String getRoomName(long lRoomKey) {
        try {
            return this.getResourceName(lRoomKey);
        } catch (Exception e) {
            // Null pointer exception will be thrown if room doesnt exist
            return null;
        }
    }

    public int getRoomCapacity(long lRoomKey) {
        try {
            return ((DxRoom) this.getResource(lRoomKey)).getCapacity();
        } catch (Exception e) {
            // Null pointer exception will be thrown if room doesnt exist
            return 0;
        }
    }

    public int getRoomCapacity(String sRoomName) {
        try {
            return ((DxRoom) this.getResource(sRoomName)).getCapacity();
        } catch (Exception e) {
            // Null pointer exception will be thrown if room doesnt exist
            return 0;
        }
    }

    public void addRoom(DxRoom dxrRoom) {
        if (this.getResourceKey(dxrRoom.getName()) == -1) {
            this.addResource(dxrRoom);
        }

    }

    public long getRoomKeyByName(String sRoomName) {
        return this.getResourceKey(sRoomName);
    }

    public DxAvailability getRoomAvailability(long lRoomKey) {
        try {
            return ((DxRoom) this.getResource(lRoomKey)).getAvailability();
        } catch (Exception e) {
            // Null pointer exception will be thrown if room doesnt exist
            return null;
        }
    }

    public DxAvailability getRoomAvailability(String sRoomName) {
        try {
            return ((DxRoom) this.getResource(sRoomName)).getAvailability();
        } catch (Exception e) {
            // Null pointer exception will be thrown if room doesnt exist
            return null;
        }
    }

    public DxRoom[] getRoomsSortedByKey() {
        return this.getKeySortedVector().toArray(new DxRoom[this.size()]);
    }

    public DxRoom[] getRoomsSortedByName() {
        return this.getNameSortedVector().toArray(new DxRoom[this.size()]);
    }
    
    public void merge(DxResource dxrModify, DxResource dxrNew) {
        // TODO Auto-generated method stub

    }

    public String toWrite(String sSiteName, String sCatName) {
        StringBuffer sbReturn = new StringBuffer();
        Iterator itRooms = this.iterator();
        while (itRooms.hasNext()) {
            sbReturn.append(((DxRoom) itRooms.next()).toWrite(sSiteName,
                    sCatName));
            if (itRooms.hasNext()) {
                sbReturn.append(DConst.CR_LF);
            }
        }
        return sbReturn.toString();
    }
}
