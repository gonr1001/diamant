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

import dInternal.dData.DxAvailability;
import dInternal.dData.DxSetOfRessources;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxSetofCatagories is a class used to:
 * <p>
 * Holds a collection of categories
 * <p>
 * 
 */
public class DxSetOfCategories extends DxSetOfRessources {
    public DxCategory getCat(long lCatKey) {
        try {
            return ((DxCategory) this.getRessource(lCatKey));
        } catch (Exception e) {
            return null;
        }
    }

    public DxCategory getCat(String sCatName) {
        try {
            return ((DxCategory) this.getRessourceByName(sCatName));
        } catch (Exception e) {
            return null;
        }
    }

    public String getCatName(long lCatKey) {
        return this.getRessourceName(lCatKey);
    }

    public int getCatCount() {
        return this.size();
    }

    public long getCategoryKeyByName(String sCatName) {
        return this.getRessourceKeyByName(sCatName);
    }

    public DxRoom getRoom(long lCatKey, long lRoomKey) {
        try {
            return ((DxCategory) this.getRessource(lCatKey)).getRoom(lRoomKey);
        } catch (Exception e) {
            return null;
        }
    }

    public DxRoom getRoom(String sCatName, String sRoomName) {
        try {
            return ((DxCategory) this.getRessourceByName(sCatName)).getRoom(sRoomName);
        } catch (Exception e) {
            return null;
        }
    }

    public int getRoomCount(long lCatKey) {
        try {
            return ((DxCategory) this.getRessource(lCatKey)).getRoomCount();
        } catch (Exception e) {
            return -1;
        }
    }

    public int getRoomCount(String sCatName) {
        try {
            return ((DxCategory) this.getRessourceByName(sCatName))
                    .getRoomCount();
        } catch (Exception e) {
            return -1;
        }
    }

    public String getRoomName(long lCatKey, long lRoomKey) {
        try {
            return ((DxCategory) this.getRessource(lCatKey))
                    .getRoomName(lRoomKey);
        } catch (Exception e) {
            return null;
        }
    }

    public int getRoomCapacity(long lCatKey, long lRoomKey) {
        try {
            return ((DxCategory) this.getRessource(lCatKey))
                    .getRoomCapacity(lRoomKey);
        } catch (Exception e) {
            // If index was invalid, Null pointer Exception will be thrown
            return 0;
        }
    }

    public int getRoomCapacity(String sCatName, String sRoomName) {
        try {
            return ((DxCategory) this.getRessourceByName(sCatName))
                    .getRoomCapacity(sRoomName);
        } catch (Exception e) {
            // If index was invalid, Null pointer Exception will be thrown
            return 0;
        }
    }

    public void addCategory(String sNewCatName) {
        if (this.getRessourceKeyByName(sNewCatName) == -1) {
            this.addRessource(new DxCategory(sNewCatName));
        }
    }

    public void addRoom(long lCatKey, DxRoom dxrRoom) {
        try {
            ((DxCategory) this.getRessource(lCatKey)).addRoom(dxrRoom);
        } catch (Exception e) {
            // If category was not found a null pointer exception will occur
        }
    }

    public void addRoom(String sCatName, DxRoom dxrRoom) {
        try {
            ((DxCategory) this.getRessourceByName(sCatName)).addRoom(dxrRoom);
        } catch (Exception e) {
            // If category was not found a null pointer exception will occur
        }
    }

    public DxAvailability getRoomAvailability(long lCatKey, long lRoomKey) {
        try {
            return ((DxCategory) this.getRessource(lCatKey))
                    .getRoomAvailability(lRoomKey);
        } catch (Exception e) {
            return null;
        }
    }

    public DxAvailability getRoomAvailability(String sCatName, String sRoomName) {
        try {
            return ((DxCategory) this.getRessourceByName(sCatName))
                    .getRoomAvailability(sRoomName);
        } catch (Exception e) {
            return null;
        }
    }

    public long getRoomKeyByName(long lCatKey, String sSiteName) {
        try {
            return ((DxCategory) this.getRessource(lCatKey))
                    .getRoomKeyByName(sSiteName);
        } catch (Exception e) {
            return -1;
        }
    }

    public DxSetOfRooms getSetOfRooms(long lCatKey) {
        try {
            return ((DxCategory) this.getRessource(lCatKey)).getSetOfRooms();
        } catch (Exception e) {
            return null;
        }
    }

    public DxSetOfRooms getSetOfRooms(String sCatName) {
        try {
            return ((DxCategory) this.getRessourceByName(sCatName))
                    .getSetOfRooms();
        } catch (Exception e) {
            return null;
        }
    }
}