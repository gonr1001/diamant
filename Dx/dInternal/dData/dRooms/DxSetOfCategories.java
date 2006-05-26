/**
 * Created on May 23, 2006
 * 
 * 
 * Title: DxSetofCatagories.java 
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
 * Description: DxSetofCatagories is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
public class DxSetOfCategories {
    private Vector<DxCategory> _vCategories;

    private long _uniqueKey;

    public DxSetOfCategories() {
        _uniqueKey = 1;
        _vCategories = new Vector<DxCategory>();
    }
    
    public String getCatName(long lCatKey) {
        try{
            return _vCategories.get(getCategoryIndexByKey(lCatKey)).getCategoryName();
        }catch (Exception e){
            return null;
        }
    }
    
    public int getCatCount() {
        return _vCategories.size();
    }

    public long getCategoryKeyByName(String sCatName) {
        Iterator it = _vCategories.iterator();
        while (it.hasNext()) {
            DxCategory dxcIt = (DxCategory) it.next();
            if (sCatName.equalsIgnoreCase(dxcIt.getCategoryName()))
                return dxcIt.getCategoryKey();
        }
        return -1;
    }
    
    public int getRoomCount(long lCatKey) {
        return getRoomCountByIndex(getCategoryIndexByKey(lCatKey));
    }

    public int getRoomCount(String sCatName) {
        return getRoomCountByIndex(getCategoryIndexByName(sCatName));
    }
    
    private int getRoomCountByIndex(int nIndex){
        try{
            return _vCategories.get(nIndex).getRoomCount();
        }catch(Exception e){
            //If index was invalid, Null pointer Exception will be thrown
            return -1;
        }
    }
    
    public String getRoomName(long lCatKey, long lRoomKey) {
        try{
            return _vCategories.get(getCategoryIndexByKey(lCatKey)).getRoomName(lRoomKey);
        }catch(Exception e){
            //If index was invalid, Null pointer Exception will be thrown
            return null;
        }
    }

    public int getRoomCapacity(long lCatKey, long lRoomKey) {
        try{
            return _vCategories.get(getCategoryIndexByKey(lCatKey)).getRoomCapacity(lRoomKey);
        }catch(Exception e){
            //If index was invalid, Null pointer Exception will be thrown
            return 0;
        }
    }

    public int getRoomCapacity(String sCatName, String sRoomName) {
        try{
            return _vCategories.get(getCategoryIndexByName(sCatName)).getRoomCapacity(sRoomName);
        }catch(Exception e){
            //If index was invalid, Null pointer Exception will be thrown
            return 0;
        }
    }

    public void addCategory(String sNewCatName) {
        if (getCategoryKeyByName(sNewCatName) == -1) {
            _vCategories.add(new DxCategory(_uniqueKey++, sNewCatName));
        }
    }

    public void addRoom(long lCatKey, DxRoom dxrRoom) {
        addRoomByIndex(getCategoryIndexByKey(lCatKey), dxrRoom);
    }

    public void addRoom(String sCatName, DxRoom dxrRoom) {
        addRoomByIndex(getCategoryIndexByName(sCatName), dxrRoom);
    }

    private void addRoomByIndex(int nCatIndex, DxRoom dxrRoom) {
        try{
            _vCategories.get(nCatIndex).addRoom(dxrRoom);
        }catch (Exception e){
            //If category was not found a null pointer exception will occur
        }

    }

    /**
     * Retreives the index of a categroy by its key
     * 
     * @param lKey
     *            Key of the category that is searched
     * 
     * @return Index of the site in the vector, -1 if not found
     */
    private int getCategoryIndexByKey(long lKey) {
        DxCategory dxcResearch = new DxCategory(lKey, null);
        int nIndex = Collections.binarySearch(_vCategories, dxcResearch,
                DxCategory.KeyComparator);
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
    private int getCategoryIndexByName(String sName) {
        Iterator it = _vCategories.iterator();
        int i;

        for (i = 0; it.hasNext(); i++) {
            DxCategory dxcTemp = (DxCategory) it.next();
            if (dxcTemp.getCategoryName().equalsIgnoreCase(sName)) {
                return i;
            }
        }
        return -1;
    }
}