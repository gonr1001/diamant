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
import dInternal.dData.dInstructors.DxInstructor;

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
        // TODO Auto-generated method stub
        return null;
    }

    public String getCatName(long lSiteKey, long lCatKey) {
        // TODO Auto-generated method stub
        return null;
    }

    public Object getRoomName(long lSiteKey, long lCatKey, long lRoomKey) {
        // TODO Auto-generated method stub
        return null;
    }

    public int getSiteCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getCatCount(long lSiteKey) {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getRoomCount(long lSiteKey, long lCatKey) {
        // TODO Auto-generated method stub
        return 0;
    }

    public DxAvailability getRoomAvailabilityByKey(long lSiteKey, long lCatKey,
            long lRoomKey) {
        // TODO Auto-generated method stub
        return null;
    }

    public int[][] getRoomAvailabilityMatrixByKey(long lSiteKey, long lCatKey,
            long lRoomKey) {
        // TODO Auto-generated method stub
        return null;
    }

    public int getRoomCapacity(long lSiteKey, long lCatKey, long lRoomKey) {
        // TODO Auto-generated method stub
        return 0;
    }

    public long getSiteKeyByName(String sSiteName) {
        Iterator it= _vSites.iterator();
        while(it.hasNext()) {
            DxSite dxsIt = (DxSite)it.next();
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
        try{
            _vSites.get(getSiteIndexByKey(lSiteKey)).addCategory(sCatName);
        }catch(Exception e)
        {
            //Null pointer exception can occur if index was invalid
            //Should throw and exception
        }

    }
    /**
     * Adds a category to the site given by lSiteKey
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

}
