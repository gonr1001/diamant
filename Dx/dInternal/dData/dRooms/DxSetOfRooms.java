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

import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;


/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxSetofRooms is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
public class DxSetOfRooms {
    private Vector<DxRoom> _vRooms;

    private long _uniqueKey;

    public DxSetOfRooms() {
        _uniqueKey = 1;
        _vRooms = new Vector<DxRoom>();
    }

    public int getRoomCount() {
        return _vRooms.size();
    }
    
    public String getRoomName(long lRoomKey) {
        return getRoomNameByIndex(getRoomIndexByKey(lRoomKey));
    }

    public String getRoomName(String sRoomName) {
        return getRoomNameByIndex(getRoomIndexByName(sRoomName));
    }
    
    private String getRoomNameByIndex(int nRoomIndex)
    {
        try{
            return _vRooms.get(nRoomIndex).getRoomName();
        }catch (Exception e){
            //Null pointer exception will be thrown if room doesnt exist
            return null;
        }
        
    }
    
    public int getRoomCapacity(long lRoomKey) {
        return getRoomCapacityByIndex(getRoomIndexByKey(lRoomKey));
    }

    public int getRoomCapacity(String sRoomName) {
        return getRoomCapacityByIndex(getRoomIndexByName(sRoomName));
    }
    
    private int getRoomCapacityByIndex(int nRoomIndex)
    {
        try{
            return _vRooms.get(nRoomIndex).getRoomCapacity();
        }catch (Exception e){
            //Null pointer exception will be thrown if room doesnt exist
            return 0;
        }
        
    }
    
    public void addRoom(DxRoom dxrRoom) {
        if(getRoomKeyByName(dxrRoom.getRoomName()) != -1) {
            _vRooms.add(dxrRoom);
            dxrRoom.setKey(_uniqueKey++);
        }

    }

    public long getRoomKeyByName(String sRoomName) {
        Iterator it = _vRooms.iterator();
        while (it.hasNext()) {
            DxRoom dxcIt = (DxRoom) it.next();
            if (sRoomName.equalsIgnoreCase(dxcIt.getRoomName()))
                return dxcIt.getRoomKey();
        }
        return -1;
    }
    
    /**
     * Retreives the index of a categroy by its key
     * 
     * @param lKey
     *            Key of the category that is searched
     * 
     * @return Index of the site in the vector, -1 if not found
     */
    private int getRoomIndexByKey(long lKey) {
        DxRoom dxrResearch = new DxRoom(null,0,0,null,null,null);
        dxrResearch.setKey(lKey);
        int nIndex = Collections.binarySearch(_vRooms, dxrResearch,
                DxRoom.KeyComparator);
        if (nIndex >= 0) {
            return nIndex;
        }
        return -1;
    }

    /**
     * Search the categories for a certain site name and return it's position,
     * -1 if not found
     * 
     * @param sName
     *            Name of the category that is searched
     * 
     * @return Index of the site in the vector, -1 if not found
     */
    private int getRoomIndexByName(String sName) {
        Iterator it = _vRooms.iterator();
        int i;

        for (i = 0; it.hasNext(); i++) {
            DxRoom dxcTemp = (DxRoom) it.next();
            if (dxcTemp.getRoomName().equalsIgnoreCase(sName)) {
                return i;
            }
        }
        return -1;
    }
}
