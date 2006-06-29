/**
 * Created on May 31st, 2006
 * 
 * 
 * Title: DxResource.java 
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
package dInternal.dData;

import java.util.Comparator;

/**
 * Nicolas Calderon
 * 
 * Description: DxResource is a class used to:
 * <p>
 * Contains a ressource generical informations like key and name. Ressource will
 * usually be sublcassed to implements more attributes. Attributes like name and
 * keys are not publicly modifiable as DxSetOfRessource rely on those
 * informations. If name was accessible to modifications, there would be no way
 * to tell if the name sorted vector was still sorted.
 * <p>
 * 
 */
public class DxResource {

    // -1 if not attributed, 0 is never attributed by a set
    private long _lKey;

    private String _sName;

    /**
     * Default Constructor. Attributes are set to unassigned values (lKey=-1,
     * _sName=null)
     * 
     */
    public DxResource() {
        _lKey = -1;
        _sName = null;
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
    public DxResource(long lKey, String sName) {
        _lKey = lKey;
        _sName = sName;
    }

    /**
     * @return Key of the Ressource, -1 if not attributed. 0 value is not
     *         attributed by a set.
     */
    public long getRessourceKey() {
        return _lKey;
    }

    /**
     * @return Name of the ressource, null if not attributed.
     */
    public String getRessourceName() {
        return _sName;
    }

    /**
     * Sets a Ressource key. This method should only be used by a
     * DxSetOfRessource when a ressource is added to the set. This justify
     * package visibility. Full acces to this method means that everyone could
     * modify key at will which could cause a scrambling key ordered vector in
     * DxSetOfRessource.
     * 
     * @param lKey
     *            New key of the ressource
     */
    void setRessourceKey(long lKey) {
        _lKey = lKey;
    }

    /**
     * Method that will be used for Sort and BinarySearch in a Name ordered
     * vector
     */
    public static Comparator<DxResource> NameComparator = new Comparator<DxResource>() {
        public int compare(DxResource left, DxResource right) {
            return left._sName.compareTo(right._sName);
        }
    };

    /**
     * Method that will be used for Sort and BinarySearch in a Key ordered
     * vector
     */
    public static Comparator<DxResource> KeyComparator = new Comparator<DxResource>() {
        public int compare(DxResource left, DxResource right) {
            long diff = left._lKey - right._lKey;
            if (diff > 0)
                return 1;
            if (diff < 0)
                return -1;
            return 0;
        }
    };
}
