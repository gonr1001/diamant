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

import dInternal.dData.DxAvailability;
import dInternal.dData.DxSetOfRessources;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxSetOfSites is a class used to:
 * <p>
 * Holds a collection of sites
 * <p>
 * 
 */
public class DxSetOfSites extends DxSetOfRessources {
    /**
     * Adds a site to the set of sites if this one is not already part of the
     * set of sites.
     * 
     * @param sNewSiteName
     *            Name of the new site to be added
     */
    public void addSite(String sNewSiteName) {
        if (this.getRessourceKeyByName(sNewSiteName) == -1) {
            this.addRessource(new DxSite(sNewSiteName));
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
        try {
            ((DxSite) this.getRessource(lSiteKey)).addCategory(sCatName);
        } catch (Exception e) {
            // Could produce a null pointer exception if get returns null
        }

    }

    /**
     * Adds a category to the site given by sCatName. If two or more categories
     * have the same name, the is no guarenty on which sites the category will
     * be added to.
     * 
     * @param sSiteName
     *            Name of the site to which the category should be added
     * @param sCatName
     *            Name of the new category to be added
     */
    public void addCat(String sSiteName, String sCatName) {
        try {
            ((DxSite) this.getRessourceByName(sSiteName)).addCategory(sCatName);
        } catch (Exception e) {
            // Could produce a null pointer exception if get returns null
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
            ((DxSite) this.getRessource(lSiteKey)).addRoom(lCatKey, dxrRoom);
        } catch (Exception e) {
            // Could produce a null pointer exception if get returns null
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
            ((DxSite) this.getRessourceByName(sSiteName)).addRoom(sCatName,
                    dxrRoom);
        } catch (Exception e) {
            // Could produce a null pointer exception if get returns null
        }

    }

    public String getSiteName(long lSiteKey) {
        return this.getRessourceName(lSiteKey);
    }

    public String getCatName(long lSiteKey, long lCatKey) {
        try {
            return ((DxSite) this.getRessource(lSiteKey)).getCatName(lCatKey);
        } catch (Exception e) {
            // if key was invalid, getter returns null
            return null;
        }
    }

    public int getSiteCount() {
        return this.size();
    }

    public int getCatCount(long lSiteKey) {
        try {
            return ((DxSite) this.getRessource(lSiteKey)).getCatCount();
        } catch (Exception e) {
            return -1;
        }
    }

    public int getCatCount(String sSiteName) {
        try {
            return ((DxSite) this.getRessourceByName(sSiteName)).getCatCount();
        } catch (Exception e) {
            return -1;
        }
    }

    public int getRoomCount(long lSiteKey, long lCatKey) {
        try {
            return ((DxSite) this.getRessource(lSiteKey)).getRoomCount(lCatKey);
        } catch (Exception e) {
            return -1;
        }
    }

    public int getRoomCount(String sSiteName, String sCatName) {
        try {
            return ((DxSite) this.getRessourceByName(sSiteName))
                    .getRoomCount(sCatName);
        } catch (Exception e) {
            return -1;
        }
    }

    public String getRoomName(long lSiteKey, long lCatKey, long lRoomKey) {
        try {
            return ((DxSite) this.getRessource(lSiteKey)).getRoomName(lCatKey,
                    lRoomKey);
        } catch (Exception e) {
            // If index was invalid, Null pointer Exception will be thrown
            return null;
        }
    }

    public int getRoomCapacity(long lSiteKey, long lCatKey, long lRoomKey) {
        try {
            return ((DxSite) this.getRessource(lCatKey)).getRoomCapacity(
                    lCatKey, lRoomKey);
        } catch (Exception e) {
            // If index was invalid, Null pointer Exception will be thrown
            return 0;
        }
    }

    public int getRoomCapacity(String sSiteName, String sCatName,
            String sRoomName) {
        try {
            return ((DxSite) this.getRessourceByName(sSiteName))
                    .getRoomCapacity(sCatName, sRoomName);
        } catch (Exception e) {
            // If index was invalid, Null pointer Exception will be thrown
            return 0;
        }
    }

    public DxAvailability getRoomAvailability(long lSiteKey, long lCatKey,
            long lRoomKey) {
        try {
            return ((DxSite) this.getRessource(lSiteKey)).getRoomAvailability(
                    lCatKey, lRoomKey);
        } catch (Exception e) {
            // If index was invalid, Null pointer Exception will be thrown
            return null;
        }
    }

    public DxAvailability getRoomAvailability(String sSiteName,
            String sCatName, String sRoomName) {
        try {
            return ((DxSite) this.getRessourceByName(sSiteName)).getRoomAvailability(
                    sCatName, sRoomName);
        } catch (Exception e) {
            // If index was invalid, Null pointer Exception will be thrown
            return null;
        }
    }

    public long getSiteKeyByName(String sSiteName) {
        return this.getRessourceKeyByName(sSiteName);
    }

    public long getCatKeyByName(long lSiteKey, String sCatName) {
        try {
            return ((DxSite) this.getRessource(lSiteKey))
                    .getCatKeyByName(sCatName);
        } catch (Exception e) {
            // If index was invalid, Null pointer Exception will be thrown
            return -1;
        }
    }

    public long getRoomKeyByName(long lSiteKey, long lCatKey, String sSiteName) {
        try {
            return ((DxSite) this.getRessource(lSiteKey)).getRoomKeyByName(
                    lCatKey, sSiteName);
        } catch (Exception e) {
            // If index was invalid, Null pointer Exception will be thrown
            return -1;
        }
    }

    public DxSetOfCategories getDxSetOfCat(long lSiteKey) {
        try {
            return ((DxSite) this.getRessource(lSiteKey)).getDxSetOfCat();
        } catch (Exception e) {
            // If index was invalid, Null pointer Exception will be thrown
            return null;
        }
    }

    public DxSetOfCategories getDxSetOfCat(String sSiteName) {
        try {
            return ((DxSite) this.getRessourceByName(sSiteName))
                    .getDxSetOfCat();
        } catch (Exception e) {
            // If index was invalid, Null pointer Exception will be thrown
            return null;
        }
    }

    public DxSetOfRooms getDxSetOfRooms(long lSiteKey, long lCatKey) {
        try {
            return ((DxSite) this.getRessource(lSiteKey))
                    .getDxSetOfRooms(lCatKey);
        } catch (Exception e) {
            // If index was invalid, Null pointer Exception will be thrown
            return null;
        }
    }

    public DxSetOfRooms getDxSetOfRooms(String sSiteName, String sCatName) {
        try {
            return ((DxSite) this.getRessourceByName(sSiteName))
                    .getDxSetOfRooms(sCatName);
        } catch (Exception e) {
            // If index was invalid, Null pointer Exception will be thrown
            return null;
        }
    }
}
