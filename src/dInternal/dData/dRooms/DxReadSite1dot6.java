/**
 * Created on May 23, 2006
 * 
 * 
 * Title: DxReadSite1dot6.java 
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
import dExceptions.DiaException;
import dInternal.DataExchange;
import dInternal.dData.DxAvailability;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxReadSite1dot6 is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
public class DxReadSite1dot6 implements DxSiteReader {
    DataExchange _deSites;

    public DxReadSite1dot6(DataExchange de) {
        _deSites = de;
    }

    public DxSetOfSites readSetOfSites()throws DiaException {
        StringTokenizer stLineTokenizer;
        StringTokenizer stFileTokenizer = new StringTokenizer(_deSites
                .getContents(), DConst.CR_LF);

        String sFileToken;
        String sLineToken;

        int nCurrentLine = 0;
        int nCurrentLineState = 0;

        // Initialized to avoid further warnings
        String sRoomName = null;
        int nRoomCapacity = 0;
        int nRoomFunction = 0;
        Vector<Integer> viCharacteristics = null;
        String sRoomSite = null;
        String sRoomCat = null;
        String sNote = null;

        DxSetOfSites dxsosBuild = new DxSetOfSites();
        DxRoom dxrTempRoom;

        // Skips useless lines
        while (stFileTokenizer.hasMoreElements()
                && nCurrentLine < DConst.ROOM_1_DOT_6_USELESS_HEADER) {
            sFileToken = stFileTokenizer.nextToken();
            nCurrentLine++;
        }

        // For every line containing a room
        while (stFileTokenizer.hasMoreElements()) {
            sFileToken = stFileTokenizer.nextToken();
            nCurrentLineState = 0;
            stLineTokenizer = new StringTokenizer(sFileToken,
                    DConst.ROOM_FIELD_SEPARATOR_TOKEN);
            if (stLineTokenizer.countTokens() == DConst.ROOM_1DOT6_TOKEN_COUNT) {
                while (nCurrentLineState < DConst.ROOM_1DOT6_TOKEN_COUNT) {
                    sLineToken = stLineTokenizer.nextToken().trim();

                    // Finite state machine for fields on a line
                    switch (nCurrentLineState) {
                    // Room name field
                    case 0:
                        sRoomName = sLineToken;
                        break;

                    // Room capacity
                    case 1:
                        try {
                            nRoomCapacity = new Integer(sLineToken).intValue();
                        } catch (NumberFormatException e) {
                            // ERROR: Invalid room capacity must be thrown
                        }
                        break;

                    // Room function
                    case 2:
                        try {
                            nRoomFunction = new Integer(sLineToken).intValue();
                        } catch (NumberFormatException e) {
                            // ERROR: Invalid room function must be thrown
                        }
                        break;

                    // Room characteristics list
                    case 3:
                        viCharacteristics = parseCharacteristics(sLineToken);
                        break;

                    case 4:
                        sRoomSite = sLineToken;
                        break;

                    case 5:
                        sRoomCat = sLineToken;
                        break;

                    // Room comment or note
                    case 6:
                        sNote = sLineToken;
                        break;

                    }
                    nCurrentLineState++;
                }
                DxAvailability dxaAlways = getDummyAvailability();
                dxrTempRoom = new DxRoom(sRoomName, sRoomCat, nRoomCapacity,
                        nRoomFunction, viCharacteristics, sNote, dxaAlways);
                dxsosBuild.addSite(sRoomSite);
                dxsosBuild.addCategory(sRoomSite, sRoomCat);
                dxsosBuild.addRoom(sRoomSite, sRoomCat, dxrTempRoom);
            } else {
                // ERROR: Invalid token count
            }
            nCurrentLine++;
        }
        return dxsosBuild;
    }

    private Vector<Integer> parseCharacteristics(String token) {
        Vector<Integer> viTemp = new Vector<Integer>();

        StringTokenizer stChar = new StringTokenizer(token,
                DConst.ROOM_CHAR_SEPARATOR_TOKEN);

        while (stChar.hasMoreTokens()) {
            try {
                viTemp.add(new Integer(stChar.nextToken()));
            } catch (NumberFormatException e) {
                // ERROR: Invalid characteristic must be thrown
            }
        }

        return viTemp;
    }

    private DxAvailability getDummyAvailability() {
        int nTemp[][] = new int[DConst.STI_NB_OF_DAYS][DConst.STI_NB_OF_PERIODS_A_DAY];
        for (int i = 0; i < DConst.STI_NB_OF_DAYS; i++) {
            for (int j = 0; j < DConst.STI_NB_OF_PERIODS_A_DAY; j++) {
                nTemp[i][j] = DConst.AVAILABILITY_YES;
            }
        }

        return new DxAvailability(nTemp);
    }

//	@Override
//	public DxSetOfSites readSetOfSitesNew() throws DiaException {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public int getLines() {
		System.out.println("DxReadInstructors1dot5.bad value -1");
		return -1;
	}

}
