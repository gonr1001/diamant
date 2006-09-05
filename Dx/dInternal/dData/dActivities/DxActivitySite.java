/**
 * Created on Jul 24, 2006
 * 
 * TODO To change the class description for this generated file
 * 
 * Project Dx
 * Title: DxActivitySite.java 
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

public class DxActivitySite extends DxResource {
    private static long _lUniqueKey=1;
    private DxSetOfActivities _dxsoaActivities;
    
    

    public DxActivitySite(String sName){
        super(_lUniqueKey++,sName);
        _dxsoaActivities = new DxSetOfActivities();
        
        
    }
    
	public DxActivity getActivity(String sActivitySiteName) {
		return _dxsoaActivities.getActivity(sActivitySiteName);
	}
	
	public DxActivity getActivity(Long lActivitySiteKey) {
		return _dxsoaActivities.getActivity(lActivitySiteKey);
	}
	
	public void addActivity(DxActivity dxaActivity) {
		_dxsoaActivities.addActivity(dxaActivity);
	}

	public Object getActivityCount() {
		return _dxsoaActivities.size();
	}
}
