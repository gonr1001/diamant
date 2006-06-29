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
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxSite is a class used to:
 * <p>
 * Hold the informations concerning a site
 * <p>
 * 
 */
public class DxSite extends DxResource {
    private static long _lUniqueKey=1;
    private DxSetOfCategories _dxsocCat;

    /**
     * Constructor, sName is the ressource name, ressource key is -1.
     * 
     * @param SName
     *            Specifies Ressource name
     */
    public DxSite(String sName) {
        super(_lUniqueKey++,sName);
        _dxsocCat = new DxSetOfCategories();
    }

    /**
     * Return the name of the current site
     * 
     * @return The name of the current site
     */
    public String getSiteName() {
        return this.getResourceName();
    }

    /**
     * Return the key of the current site
     * 
     * @return The key of the current site
     */
    public long getSiteKey() {
        return this.getResourceKey();
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

    public int getRoomCapacity(long lCatKey, long lRoomKey) {
        return _dxsocCat.getRoomCapacity(lCatKey, lRoomKey);
    }

    public int getRoomCapacity(String sCatName, String sRoomName) {
        return _dxsocCat.getRoomCapacity(sCatName, sRoomName);
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

    public DxAvailability getRoomAvailability(long lCatKey, long lRoomKey) {
        return _dxsocCat.getRoomAvailability(lCatKey, lRoomKey);
    }

    public DxAvailability getRoomAvailability(String sCatName, String sRoomName) {
        return _dxsocCat.getRoomAvailability(sCatName, sRoomName);
    }

    public long getCatKeyByName(String sCatName) {
        return _dxsocCat.getResourceKeyByName(sCatName);
    }

    public long getRoomKeyByName(long lCatKey, String sSiteName) {
        return _dxsocCat.getRoomKeyByName(lCatKey, sSiteName);
    }

    public DxSetOfCategories getSetOfCat() {
        return _dxsocCat;
    }

    public DxSetOfRooms getSetOfRooms(long lCatKey) {
        return _dxsocCat.getSetOfRooms(lCatKey);
    }

    public DxSetOfRooms getSetOfRooms(String sCatName) {
        return _dxsocCat.getSetOfRooms(sCatName);
    }

    public String toWrite() {

        return _dxsocCat.toWrite(this.getSiteName());
    }

    public boolean isEquals(DxSite dxsOtherSite) {
        if (!this.getSiteName().equalsIgnoreCase(dxsOtherSite.getSiteName())) {
            return false;
        }

        if (!this._dxsocCat.isEquals(dxsOtherSite._dxsocCat)) {
            return false;
        }

        return true;
    }
}
