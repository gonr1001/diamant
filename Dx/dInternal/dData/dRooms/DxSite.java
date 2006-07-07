/**
 * Created on May 23, 2006
 * 
 * 
 * Title: DxSite.java 
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
 * Nicolas Calderon
 * 
 * Description: DxSite is a class used to:
 * <p>
 * Hold the informations concerning a site
 * <p>
 * 
 */
public class DxSite extends DxResource {
    private static long _lUniqueKey = 1;

    private DxSetOfCategories _dxsocCat;

    /**
     * Constructor, sName is the ressource name, ressource key is -1.
     * 
     * @param SName
     *            Specifies Ressource name
     */
    public DxSite(String sName) {
        super(_lUniqueKey++, sName);
        _dxsocCat = new DxSetOfCategories();
    }
    
    /**
     * Add a category to the current site
     * 
     * @param sName
     *            Name of the category that should be added to the site
     */
    public void addCategory(String sName) {
        _dxsocCat.addCategory(sName);
    }

    public void addRoom(long lCatKey, DxRoom dxrRoom) {
        _dxsocCat.addRoom(lCatKey, dxrRoom);
    }

    public void addRoom(String sCatName, DxRoom dxrRoom) {
        _dxsocCat.addRoom(sCatName, dxrRoom);
    }
    
    public DxSetOfCategories getSetOfCat() {
        return _dxsocCat;
    }

    public DxCategory getCat(long lCatKey) {
        return _dxsocCat.getCat(lCatKey);
    }

    public DxCategory getCat(String sCatName) {
        return _dxsocCat.getCat(sCatName);
    }

    public int getCatCount() {
        return _dxsocCat.getCatCount();
    }

    public String getCatName(long lCatKey) {
        return _dxsocCat.getCatName(lCatKey);
    }
    
    public long getCatKey(String sCatName) {
        return _dxsocCat.getResourceKey(sCatName);
    }

    public DxSetOfRooms getSetOfRooms(long lCatKey) {
        return _dxsocCat.getSetOfRooms(lCatKey);
    }
    
    public DxSetOfRooms getSetOfRooms(String sCatName) {
        return _dxsocCat.getSetOfRooms(sCatName);
    }
    
    public DxRoom getRoom(long lCatKey, long lRoomKey) {
        return _dxsocCat.getRoom(lCatKey, lRoomKey);
    }

    public DxRoom getRoom(String sCatName, String sRoomName) {
        return _dxsocCat.getRoom(sCatName, sRoomName);
    }

    public int getRoomCount(long lCatKey) {
        return _dxsocCat.getRoomCount(lCatKey);
    }

    public int getRoomCount(String sCatName) {
        return _dxsocCat.getRoomCount(sCatName);
    }

    public String getRoomName(long lCatKey, long lRoomKey) {
        return _dxsocCat.getRoomName(lCatKey, lRoomKey);
    }
    
    public long getRoomKey(long lCatKey, String sSiteName) {
        return _dxsocCat.getRoomKeyByName(lCatKey, sSiteName);
    }

    public int getRoomCapacity(long lCatKey, long lRoomKey) {
        return _dxsocCat.getRoomCapacity(lCatKey, lRoomKey);
    }

    public int getRoomCapacity(String sCatName, String sRoomName) {
        return _dxsocCat.getRoomCapacity(sCatName, sRoomName);
    }
    
    public DxAvailability getRoomAvailability(long lCatKey, long lRoomKey) {
        return _dxsocCat.getRoomAvailability(lCatKey, lRoomKey);
    }

    public DxAvailability getRoomAvailability(String sCatName, String sRoomName) {
        return _dxsocCat.getRoomAvailability(sCatName, sRoomName);
    }
    
    public boolean isEqual(DxResource dxrOtherSite) {
        DxSite dxsOtherSite = (DxSite) dxrOtherSite;
        if (!this.getName().equalsIgnoreCase(dxsOtherSite.getName())) {
            return false;
        }

        if (!this._dxsocCat.isEqual(dxsOtherSite._dxsocCat)) {
            return false;
        }

        return true;
    }

    public String toWrite() {

        return _dxsocCat.toWrite(this.getName());
    }
}
