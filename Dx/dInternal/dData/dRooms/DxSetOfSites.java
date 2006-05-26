/**
 * Created on May 23, 2006
 * 
 * 
 * Title: DxSetOfSites.java 
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

import dInternal.dData.DxAvailability;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxSetOfSites is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
public class DxSetOfSites {
    private Vector<DxSite> _vSites;

    private long _uniqueKey;

    public DxSetOfSites() {
        _uniqueKey = 1;
        _vSites = new Vector<DxSite>();
    }

    public String getSiteName(long lSiteKey) {
        try {
            return _vSites.get(getSiteIndexByKey(lSiteKey)).getSiteName();
        } catch (Exception e) {
            // If index was invalid, Null pointer Exception will be thrown
            return null;
        }
    }

    public String getCatName(long lSiteKey, long lCatKey) {
        try {
            return _vSites.get(getSiteIndexByKey(lSiteKey)).getCatName(lCatKey);
        } catch (Exception e) {
            // If index was invalid, Null pointer Exception will be thrown
            return null;
        }
    }

    public int getSiteCount() {
        return _vSites.size();
    }

    public int getCatCount(long lSiteKey) {
        return getCatCountByIndex(getSiteIndexByKey(lSiteKey));
    }

    public int getCatCount(String sSiteName) {
        return getCatCountByIndex(getSiteIndexByName(sSiteName));
    }

    private int getCatCountByIndex(int nIndex) {
        try {
            return _vSites.get(nIndex).getCatCount();
        } catch (Exception e) {
            return -1;
        }
    }

    public int getRoomCount(long lSiteKey, long lCatKey) {
        try {
            return _vSites.get(getSiteIndexByKey(lSiteKey)).getRoomCount(
                    lCatKey);
        } catch (Exception e) {
            return -1;
        }
    }

    public int getRoomCount(String sSiteName, String sCatName) {
        try {
            return _vSites.get(getSiteIndexByName(sSiteName)).getRoomCount(
                    sCatName);
        } catch (Exception e) {
            return -1;
        }
    }

    public String getRoomName(long lSiteKey, long lCatKey, long lRoomKey) {
        try {
            return _vSites.get(getSiteIndexByKey(lSiteKey)).getRoomName(
                    lCatKey, lRoomKey);
        } catch (Exception e) {
            // If index was invalid, Null pointer Exception will be thrown
            return null;
        }
    }

    public String getRoomName(String sSiteName, String sCatName,
            String sRoomName) {
        try {
            return _vSites.get(getSiteIndexByName(sSiteName)).getRoomName(
                    sCatName, sRoomName);
        } catch (Exception e) {
            // If index was invalid, Null pointer Exception will be thrown
            return null;
        }
    }

    public int getRoomCapacity(long lSiteKey, long lCatKey, long lRoomKey) {
        try {
            return _vSites.get(getSiteIndexByKey(lSiteKey)).getRoomCapacity(
                    lCatKey, lRoomKey);
        } catch (Exception e) {
            // If index was invalid, Null pointer Exception will be thrown
            return 0;
        }
    }

    public int getRoomCapacity(String sSiteName, String sCatName,
            String sRoomName) {
        try {
            return _vSites.get(getSiteIndexByName(sSiteName)).getRoomCapacity(
                    sCatName, sRoomName);
        } catch (Exception e) {
            // If index was invalid, Null pointer Exception will be thrown
            return 0;
        }
    }

    public int[][] getRoomAvailabilityMatrixByKey(long lSiteKey, long lCatKey,
            long lRoomKey) {
        // TODO Auto-generated method stub
        return null;
    }

    public DxAvailability getRoomAvailabilityByKey(long lSiteKey, long lCatKey,
            long lRoomKey) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getSiteKeyByName(String sSiteName) {
        Iterator it = _vSites.iterator();
        while (it.hasNext()) {
            DxSite dxsIt = (DxSite) it.next();
            if (sSiteName == dxsIt.getSiteName())
                return dxsIt.getSiteKey();
        }
        return -1;
    }

    public long getCatKeyByName(long lSiteKey, String sSiteName) {
        return 0;
    }

    public long getRoomKeyByName(long lSiteKey, long lCatKey, String sSiteName) {
        return 0;
    }

    public DxSetOfCategories getDxSetOfCat(long lSiteKey) {
        return null;
    }

    public DxSetOfRooms getDxSetOfRooms(long lSiteKey, long lCatKey) {
        return null;
    }

    /**
     * Adds a site to the set of sites if this one is not already part of the
     * set of sites.
     * 
     * @param sNewSiteName
     *            Name of the new site to be added
     */
    public void addSite(String sNewSiteName) {
        if (getSiteKeyByName(sNewSiteName) != -1) {
            _vSites.add(new DxSite(_uniqueKey++, sNewSiteName));
        }
    }

    /**
     * Adds a category to the site given by lSiteKey
     * 
     * @param lSiteKey
     *            Key of the site to which the category should be added
     * @param sCatName
     *            Name of the new category to be added
     */
    public void addCat(long lSiteKey, String sCatName) {
        addCatByIndex(getSiteIndexByKey(lSiteKey), sCatName);
    }

    /**
     * Adds a category to the site given by lSiteKey
     * 
     * @param sSiteName
     *            Name of the site to which the category should be added
     * @param sCatName
     *            Name of the new category to be added
     */
    public void addCat(String sSiteName, String sCatName) {
        addCatByIndex(getSiteIndexByName(sSiteName), sCatName);
    }

    /**
     * Adds a category once the index of the site has been found
     * 
     * @param nSiteIndex
     *            Index of the site in the site vector
     * @param sCatName
     *            Name of the new category to be added
     */
    private void addCatByIndex(int nSiteIndex, String sCatName) {
        try {
            _vSites.get(nSiteIndex).addCategory(sCatName);
        } catch (Exception e) {
            // Null pointer exception can occur if index was invalid
            // Should throw and exception
        }
    }

    /**
     * Adds a category to the site given by lSiteKey
     * 
     * @param lSiteKey
     *            Key of the site to which the category should be added
     * @param lCatKey
     *            Key of the category to which the room should be added
     * @param sRoomName
     *            Name of the room to be added
     * @param nCapacity
     *            Capacity of the room to be added
     * @param nFunction
     *            Function of the room to be added
     * @param vChar
     *            Vector of characteristics of the room to be added
     * @param sNote
     *            Comment on the room to be added
     */
    public void addRoom(long lSiteKey, long lCatKey, DxRoom dxrRoom) {
        try {
            _vSites.get(getSiteIndexByKey(lSiteKey)).addRoom(lCatKey, dxrRoom);
        } catch (Exception e) {
            // Null pointer exception can occur if index was invalid
            // Should throw and exception
        }
    }

    /**
     * Adds a category to the site given by lSiteKey
     * 
     * @param sSiteName
     *            Name of the site to which the category should be added
     * @param sCatName
     *            Name of the category to which the room should be added
     * @param sRoomName
     *            Name of the room to be added
     * @param nCapacity
     *            Capacity of the room to be added
     * @param nFunction
     *            Function of the room to be added
     * @param vChar
     *            Vector of characteristics of the room to be added
     * @param sNote
     *            Comment on the room to be added
     */
    public void addRoom(String sSiteName, String sCatName, DxRoom dxrRoom) {
        try {
            _vSites.get(getSiteIndexByName(sSiteName)).addRoom(sCatName,
                    dxrRoom);
        } catch (Exception e) {
            // Null pointer exception can occur if index was invalid
            // Should throw and exception
        }
    }

    /**
     * Retreives the index of a site by its key
     * 
     * @param lKey
     *            Key of the site that is searched
     * 
     * @return Index of the site in the vector, -1 if not found
     */
    private int getSiteIndexByKey(long lKey) {
        DxSite dxsResearch = new DxSite(lKey, null);
        int nIndex = Collections.binarySearch(_vSites, dxsResearch,
                DxSite.KeyComparator);
        if (nIndex >= 0) {
            return nIndex;
        }
        return -1;
    }

    /**
     * Search the sites for a certain site name and return it's position, -1 if
     * not found
     * 
     * @param sName
     *            Name of the site that is searched
     * 
     * @return Index of the site in the vector, -1 if not found
     */
    private int getSiteIndexByName(String sName) {
        Iterator it = _vSites.iterator();
        int i;

        for (i = 0; it.hasNext(); i++) {
            DxSite dxsTemp = (DxSite) it.next();
            if (dxsTemp.getSiteName().equalsIgnoreCase(sName)) {
                return i;
            }
        }
        return -1;
    }

}
