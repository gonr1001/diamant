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
import java.util.Vector;

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
public class DxReadSite1dot5 implements DxSiteReader {
    DataExchange _deSites;

    public DxReadSite1dot5(DataExchange de) {
        _deSites = de;
    }

    public DxSetOfSites getSetOfSite() {
        StringTokenizer stLineTokenizer;
        StringTokenizer stFileTokenizer = new StringTokenizer(_deSites
                .getContents(), DConst.CR_LF);

        String sFileToken;
        String sLineToken;

        int nCurrentLine = 1;
        int nCurrentLineState = 0;
        int nState = 0;

        String sRoomName;
        int nRoomCapacity;
        int nRoomFunction;
        Vector<Integer> viCharacteristics;
        String sNote;

        DxSetOfSites dxsosTemp = new DxSetOfSites();
        
        //In version 1.5, there is only one site and one category
        dxsosTemp.addSite(DConst.ROOM_STANDARD_SITE);
        dxsosTemp.addCat(dxsosTemp.getSiteKeyByName(DConst.ROOM_STANDARD_SITE),DConst.ROOM_STANDARD_SITE);
        
        

        // Skips useless lines
        while (stFileTokenizer.hasMoreElements()
                && nCurrentLine <= DConst.ROOM_USELESS_HEADER) {
            sFileToken = stFileTokenizer.nextToken();
            nCurrentLine++;
        }

        // For every line containing a room
        while (stFileTokenizer.hasMoreElements()) {
            sFileToken = stFileTokenizer.nextToken();
            nCurrentLineState = 0;
            stLineTokenizer = new StringTokenizer(sFileToken,
                    DConst.ROOM_FIELD_SEPARATOR_TOKEN);
            if (stLineTokenizer.countTokens() == DConst.ROOM_1DOT5_TOKEN_COUNT) {
                while (nCurrentLineState < DConst.ROOM_1DOT5_TOKEN_COUNT) {
                    sLineToken = stLineTokenizer.nextToken();
                    
                    //Finite state machine for fields on a line
                    switch (nCurrentLineState) {
                    //Room name field
                    case 0:
                        sRoomName = sLineToken;
                        break;

                        //Room capacity
                    case 1:
                        try{
                            nRoomCapacity=new Integer(sLineToken).intValue();
                        }catch(NumberFormatException e){
                            //ERROR: Invalid room capacity must be thrown
                        }
                        break;
                        
                        //Room function
                    case 2:
                        try{
                            nRoomFunction=new Integer(sLineToken).intValue();
                        }catch(NumberFormatException e){
                            //ERROR: Invalid room function must be thrown
                        }
                        break;

                        //Room characteristics list
                    case 3:
                        viCharacteristics=parseCharacteristics(sLineToken);
                        break;

                        //Room comment or note
                    case 4:
                        sNote=sLineToken;
                        break;

                    }
                    nCurrentLineState++;
                }
            } else {
                // ERROR: Wrong file type must be thrown
            }
            nCurrentLine++;
        }
        return dxsosTemp;
    }
    
    private Vector<Integer> parseCharacteristics(String token)
    {
        Vector<Integer> viTemp=new Vector<Integer>();
        
        StringTokenizer stChar=new StringTokenizer(token,DConst.ROOM_CHAR_SEPARATOR_TOKEN);
        
        while(stChar.hasMoreTokens())
        {
            try{
                viTemp.add(new Integer(stChar.nextToken()));
            }catch(NumberFormatException e)
            {
                //ERROR: Invalid characteristic must be thrown
            }
        }
        
        return viTemp;
    }
}
