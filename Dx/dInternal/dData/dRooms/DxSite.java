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

import java.util.Comparator;


/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxSite is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
public class DxSite {
    private long _lKey;

    private String _sName;

    private DxSetOfCategories _dxsocCat;

    /**
     * Constructor
     * 
     * @param lKey
     *            Unique key of the new site
     * @param sName
     *            Name if the site created
     */
    public DxSite(long lKey, String sName) {
        _lKey = lKey;
        _sName = sName;
        _dxsocCat = new DxSetOfCategories();
    }
    
    /**
     * Return the name of the current site
     * 
     * @return The name of the current site
     */
    public String getSiteName() {
        return _sName;
    }

    /**
     * Return the key of the current site
     * 
     * @return The key of the current site
     */
    public long getSiteKey() {
        return _lKey;
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
        // TODO Auto-generated method stub
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
    
    public static Comparator<DxSite> KeyComparator = new Comparator<DxSite>() {
        public int compare(DxSite arg0, DxSite arg1) {
            DxSite left = arg0;
            DxSite right = arg1;
            long diff = left._lKey - right._lKey;
            if (diff > 0)
                return 1;
            if (diff < 0)
                return -1;
            return 0;
        }
    };
}
