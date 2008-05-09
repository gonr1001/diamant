/**
 * Created on Jul 24, 2006
 * 
 * TODO To change the class description for this generated file
 * 
 * Project Dx
 * Title: DxSetOfActivitiesSites.java 
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

import java.util.Iterator;

import dInternal.dData.DxResource;
import dInternal.dData.DxSetOfResources;

public class DxSetOfActivitiesSites extends DxSetOfResources {

    protected DxResource findEquivalent(DxResource dxrSearch) {
        return null;
    }

    protected void merge(DxResource dxrModify, DxResource dxrNew) {
    	//to avoid warning
    }

	public void addActivitiySite(DxActivitySite dxasSite) {
		this.addResource(dxasSite);
		
	}

	public DxActivitySite getActivitySite(String sSiteName) {
		return (DxActivitySite)this.getResource(sSiteName);
	}
	
	public DxActivitySite getActivitySite(long lSiteKey) {
		return (DxActivitySite)this.getResource(lSiteKey);
	}

	public DxSetOfActivities getAllActivities() {
		DxSetOfActivities dxsoaReturn = null;
		Iterator itSites = this.iterator();
		while(itSites.hasNext())
		{
			DxActivitySite dxasCurrentSite = (DxActivitySite)itSites.next();
			if(dxsoaReturn == null)
			{
				dxsoaReturn = dxasCurrentSite.getSetOfActivities();
			}else{
				dxsoaReturn.addSetOfActivities(dxasCurrentSite.getSetOfActivities());
			}
		}
		return dxsoaReturn;
	}
	
	public String toWrite() {
		// TODO Auto-generated method stub
		return null;
	}
}
