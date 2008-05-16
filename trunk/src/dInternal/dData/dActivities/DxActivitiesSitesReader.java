/**
 * Created on Jul 26, 2006
 * 
 * TODO To change the class description for this generated file
 * 
 * Project Dx
 * Title: DxActivitiesSitesReader.java 
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

import eLib.exit.exception.DxException;

public interface DxActivitiesSitesReader {
    public DxSetOfActivitiesSites readSetOfActivitiesSites() throws DxException;
}
