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

import dInternal.dData.dInstructors.DxInstructor;

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
    
    private long getCategoryKeyByName(String sCatName) {
        Iterator it= _vCategories.iterator();
        while(it.hasNext()) {
            DxCategory dxcIt = (DxCategory)it.next();
            if (sCatName == dxcIt.getCategoryName())
                return dxcIt.getCategoryKey();
        }
        return -1;
    }
    
    public void addCategory(String sNewCatName) {
        if (getCategoryKeyByName(sNewCatName) != -1) {
            _vCategories.add(new DxCategory(_uniqueKey++, sNewCatName));
        }
    }



    
}