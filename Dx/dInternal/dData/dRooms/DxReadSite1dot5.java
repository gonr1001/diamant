/**
 * Created on May 23, 2006
 * 
 * 
 * Title: DxReadSite1dot5.java 
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

import java.util.StringTokenizer;

import dConstants.DConst;
import dInternal.DataExchange;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxReadSite1dot5 is a class used to:
 * <p>
 * TODO:insert comments
 * <p> 
 * 
 */
public class DxReadSite1dot5 implements DxSiteReader{
    DataExchange _deSites;
    public DxReadSite1dot5(DataExchange de)
    {
        _deSites=de;
    }

    public DxSetOfSites getSetOfSite() {
        StringTokenizer st = new StringTokenizer(_deSites.getContents(),
                DConst.CR_LF);
        String sToken;
        
        int currentLine=0;
        int state = 0;
        
        while (st.hasMoreElements()) {
            sToken = st.nextToken();
            currentLine++;
        }
        return null;
    }

}
