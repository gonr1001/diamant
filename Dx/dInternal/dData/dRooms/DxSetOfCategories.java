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

import java.util.Iterator;

import dConstants.DConst;
import dInternal.dData.DxAvailability;
import dInternal.dData.DxSetOfResources;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxSetofCatagories is a class used to:
 * <p>
 * Holds a collection of categories
 * <p>
 * 
 */
public class DxSetOfCategories extends DxSetOfResources {
    public DxCategory getCat(long lCatKey) {
        try {
            return ((DxCategory) this.getResource(lCatKey));
        } catch (Exception e) {
            return null;
        }
    }

    public DxCategory getCat(String sCatName) {
        try {
            return ((DxCategory) this.getResource(sCatName));
        } catch (Exception e) {
            return null;
        }
    }

    public String getCatName(long lCatKey) {
        return this.getResourceName(lCatKey);
    }

    public int getCatCount() {
        return this.size();
    }

    public long getCategoryKeyByName(String sCatName) {
        return this.getResourceKey(sCatName);
    }

    public DxRoom getRoom(long lCatKey, long lRoomKey) {
        try {
            return ((DxCategory) this.getResource(lCatKey)).getRoom(lRoomKey);
        } catch (Exception e) {
            return null;
        }
    }

    public DxRoom getRoom(String sCatName, String sRoomName) {
        try {
            return ((DxCategory) this.getResource(sCatName)).getRoom(sRoomName);
        } catch (Exception e) {
            return null;
        }
    }

    public int getRoomCount(long lCatKey) {
        try {
            return ((DxCategory) this.getResource(lCatKey)).getRoomCount();
        } catch (Exception e) {
            return -1;
        }
    }

    public int getRoomCount(String sCatName) {
        try {
            return ((DxCategory) this.getResource(sCatName))
                    .getRoomCount();
        } catch (Exception e) {
            return -1;
        }
    }

    public String getRoomName(long lCatKey, long lRoomKey) {
        try {
            return ((DxCategory) this.getResource(lCatKey))
                    .getRoomName(lRoomKey);
        } catch (Exception e) {
            return null;
        }
    }

    public int getRoomCapacity(long lCatKey, long lRoomKey) {
        try {
            return ((DxCategory) this.getResource(lCatKey))
                    .getRoomCapacity(lRoomKey);
        } catch (Exception e) {
            // If index was invalid, Null pointer Exception will be thrown
            return 0;
        }
    }

    public int getRoomCapacity(String sCatName, String sRoomName) {
        try {
            return ((DxCategory) this.getResource(sCatName))
                    .getRoomCapacity(sRoomName);
        } catch (Exception e) {
            // If index was invalid, Null pointer Exception will be thrown
            return 0;
        }
    }

    public void addCategory(String sNewCatName) {
        if (this.getResourceKey(sNewCatName) == -1) {
            this.addResource(new DxCategory(sNewCatName));
        }
    }

    public void addRoom(long lCatKey, DxRoom dxrRoom) {
        try {
            ((DxCategory) this.getResource(lCatKey)).addRoom(dxrRoom);
        } catch (Exception e) {
            // If category was not found a null pointer exception will occur
        }
    }

    public void addRoom(String sCatName, DxRoom dxrRoom) {
        try {
            ((DxCategory) this.getResource(sCatName)).addRoom(dxrRoom);
        } catch (Exception e) {
            // If category was not found a null pointer exception will occur
        }
    }

    public DxAvailability getRoomAvailability(long lCatKey, long lRoomKey) {
        try {
            return ((DxCategory) this.getResource(lCatKey))
                    .getRoomAvailability(lRoomKey);
        } catch (Exception e) {
            return null;
        }
    }

    public DxAvailability getRoomAvailability(String sCatName, String sRoomName) {
        try {
            return ((DxCategory) this.getResource(sCatName))
                    .getRoomAvailability(sRoomName);
        } catch (Exception e) {
            return null;
        }
    }

    public long getRoomKeyByName(long lCatKey, String sSiteName) {
        try {
            return ((DxCategory) this.getResource(lCatKey))
                    .getRoomKeyByName(sSiteName);
        } catch (Exception e) {
            return -1;
        }
    }

    public DxSetOfRooms getSetOfRooms(long lCatKey) {
        try {
            return ((DxCategory) this.getResource(lCatKey)).getSetOfRooms();
        } catch (Exception e) {
            return null;
        }
    }

    public DxSetOfRooms getSetOfRooms(String sCatName) {
        try {
            return ((DxCategory) this.getResource(sCatName))
                    .getSetOfRooms();
        } catch (Exception e) {
            return null;
        }
    }
    
    public DxCategory[] getCatsSortedByKey(){
        return this.getKeySortedVector().toArray(new DxCategory[this.size()]);
    }
    
    public DxCategory[] getCatsSortedByName(){
        return this.getNameSortedVector().toArray(new DxCategory[this.size()]);
    }

    public String toWrite(String sSiteName) {
        StringBuffer sbReturn = new StringBuffer();
        Iterator itCategories = this.iterator();
        while(itCategories.hasNext())
        {
            sbReturn.append(((DxCategory)itCategories.next()).toWrite(sSiteName));
            if(itCategories.hasNext())
            {
                sbReturn.append(DConst.CR_LF);
            }
        }
        return sbReturn.toString();
    }

    public boolean isEquals(DxSetOfCategories dxsocOtherCats) {
        if (!super.isEqual(dxsocOtherCats)) {
            return false;
        }

        Iterator itRes = this.iterator();
        DxCategory dxcThis;
        DxCategory dxcOther;
        while (itRes.hasNext()) {
            dxcThis = (DxCategory) itRes.next();
            dxcOther = dxsocOtherCats.getCat(dxcThis.getCategoryName());
            if (!dxcThis.isEquals(dxcOther))
                return false;
        }
        return true;
    }
}