/**
 * Created on Jul 26, 2006
 * 
 * TODO To change the class description for this generated file
 * 
 * Project Dx
 * Title: DxSetOfAssignements.java 
 * 
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

package dInternal.dData.dActivities;

import dInternal.dData.DxResource;
import dInternal.dData.DxSetOfResources;

public class DxSetOfAssignements extends DxSetOfResources {
    
    protected DxResource findEquivalent(DxResource dxrSearch) {
        // TODO Auto-generated method stub
        return null;
    }

    protected void merge(DxResource dxrModify, DxResource dxrNew) {
        // TODO Auto-generated method stub

    }

	public void addAssignement(DxAssignement dxassAssign) {
		this.addResource(dxassAssign);
	}

	public DxAssignement getAssignement(String sAssignementName) {
		return (DxAssignement)this.getResource(sAssignementName);
	}
	
	public DxAssignement getAssignement(long lAssignementKey) {
		return (DxAssignement)this.getResource(lAssignementKey);
	}

}
