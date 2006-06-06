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
 * Description: DxSite is a class used to:
 * <p>
 * Hold the informations concerning a site
 * <p>
 * 
 */
public class DxSite extends DxRessource {
    private DxSetOfCategories _dxsocCat;

    /**
     * Default constructor
     */
    public DxSite() {
        _dxsocCat = new DxSetOfCategories();
    }

    /**
     * Constructor, lKey is the site key.
     * 
     * @param lKey
     *            Specifies Ressource key
     */
    public DxSite(long lKey) {
        super(lKey);
        _dxsocCat = new DxSetOfCategories();
    }

    /**
     * Constructor, sName is the ressource name, ressource key is -1.
     * 
     * @param SName
     *            Specifies Ressource name
     */
    public DxSite(String sName) {
        super(sName);
        _dxsocCat = new DxSetOfCategories();
    }

    /**
     * Constructor, sName is the ressource name, lKey is the ressource key.
     * 
     * @param lKey
     *            Specifies Ressource key
     * @param sName
     *            Specifies Ressource name
     * 
     */
    public DxSite(long lKey, String sName) {
        super(lKey, sName);
        _dxsocCat = new DxSetOfCategories();
    }

    /**
     * Return the name of the current site
     * 
     * @return The name of the current site
     */
    public String getSiteName() {
        return this.getRessourceName();
    }

    /**
     * Return the key of the current site
     * 
     * @return The key of the current site
     */
    public long getSiteKey() {
        return this.getRessourceKey();
    }

    public DxCategory getCat(long catKey) {
        // TODO Auto-generated method stub
        return null;
    }

    public DxCategory getCat(String catName) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public int getCatCount() {
        return _dxsocCat.getCatCount();
    }

    public String getCatName(long lCatKey) {
        return _dxsocCat.getCatName(lCatKey);
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
        return _dxsocCat.getRessourceKeyByName(sCatName);
    }

    public long getRoomKeyByName(long lCatKey, String sSiteName) {
        return _dxsocCat.getRoomKeyByName(lCatKey, sSiteName);
    }

    public DxSetOfCategories getDxSetOfCat() {
        return _dxsocCat;
    }

    public DxSetOfRooms getDxSetOfRooms(long lCatKey) {
        return _dxsocCat.getDxSetOfRooms(lCatKey);
    }

    public DxSetOfRooms getDxSetOfRooms(String sCatName) {
        return _dxsocCat.getDxSetOfRooms(sCatName);
    }

    public DxRoom getRoom(long catKey, long roomKey) {
        // TODO Auto-generated method stub
        return null;
    }

    public DxRoom getRoom(String catName, String roomName) {
        // TODO Auto-generated method stub
        return null;
    }
}
