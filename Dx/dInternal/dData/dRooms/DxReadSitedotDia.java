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
import dInternal.DataExchange;
import dInternal.dData.DxAvailability;
import eLib.exit.dialog.DxExceptionDlg;
import eLib.exit.exception.DxException;

public class DxReadSitedotDia implements DxSiteReader {

    private DataExchange _deSites;

    private int _nDays, _nPeriods;

    public DxReadSitedotDia(DataExchange de, int nDays, int nPeriods) {
        _deSites = de;
        _nDays = nDays;
        _nPeriods = nPeriods;
    }

    public DxSetOfSites readSetOfSites() throws DxException{
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
        DxAvailability dxaAva = null;

        DxSetOfSites dxsosBuild = new DxSetOfSites();
        DxRoom dxrTempRoom;

////	No useless lines in dia files
//        // Skips useless lines
//        while (stFileTokenizer.hasMoreElements()
//                && nCurrentLine < DConst.ROOM_USELESS_DIA_HEADER) {
//            sFileToken = stFileTokenizer.nextToken();
//            nCurrentLine++;
//        }

        stFileTokenizer.nextToken(); // "Diamant1.6" token
        // For every line containing a room
        while (stFileTokenizer.hasMoreElements()) {
            sFileToken = stFileTokenizer.nextToken();
            nCurrentLineState = 0;
            stLineTokenizer = new StringTokenizer(sFileToken,
                    DConst.ROOM_FIELD_SEPARATOR_TOKEN);
            if (stLineTokenizer.countTokens() == DConst.ROOM_DIA_TOKEN_COUNT) {
                while (nCurrentLineState < DConst.ROOM_DIA_TOKEN_COUNT) {
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
                        	 throw new DxException(DConst.ROOM_TEXT2+nCurrentLine);
                        }
                        break;

                    // Room function
                    case 2:
                        try {
                            nRoomFunction = new Integer(sLineToken).intValue();
                        } catch (NumberFormatException e) {
                        	throw new DxException(DConst.ROOM_TEXT3+nCurrentLine);
                        }
                        break;

                    // Room characteristics list
                    case 3:
                        viCharacteristics = parseCharacteristics(sLineToken,nCurrentLine);
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

                    // Room availability
                    case 7:
                        dxaAva = parseAvailability(sLineToken,nCurrentLine);
                        break;

                    }
                    nCurrentLineState++;
                }
                dxrTempRoom = new DxRoom(sRoomName, nRoomCapacity,
                        nRoomFunction, viCharacteristics, sNote, dxaAva);
                dxsosBuild.addSite(sRoomSite); // If site exists, it's not
                // added
                dxsosBuild.addCategory(sRoomSite, sRoomCat);// If cat exits, it's
                // not added
                dxsosBuild.addRoom(sRoomSite, sRoomCat, dxrTempRoom);
            } else {
            	 throw new DxException(DConst.ROOM_TEXT7+nCurrentLine);
            }
            nCurrentLine++;
        }
        return dxsosBuild;
    }

    private Vector<Integer> parseCharacteristics (String token, int nCurrentLine) {
		Vector<Integer> viTemp = new Vector<Integer>();

		StringTokenizer stChar = new StringTokenizer(token,
				DConst.ROOM_CHAR_SEPARATOR_TOKEN);

		while (stChar.hasMoreTokens()) {
			try {
				viTemp.add(new Integer(stChar.nextToken()));
			} catch (NumberFormatException e) {
				new DxException(DConst.ROOM_TEXT4 + nCurrentLine);
			}
		}

		return viTemp;
	}

    private DxAvailability parseAvailability(String sAvailabilities,
			int nCurrentLine) throws DxException {
		// extract a line that gives availability of a day

		DxAvailability dxaRet = new DxAvailability();

		StringTokenizer stDays = new StringTokenizer(sAvailabilities,
				DConst.AVAILABILITY_DAY_SEPARATOR_ROOM);

		if (stDays.countTokens() == _nDays) {
			while (stDays.hasMoreTokens()) {
				String sDay = stDays.nextToken();
				StringTokenizer stPeriods = new StringTokenizer(sDay);

				// Verifies that number of period per day was correctly
				// indicated
				if (stPeriods.countTokens() == _nPeriods) {
					// Verifies that every availability element is valid
					while (stPeriods.hasMoreElements()) {
						String dispo = stPeriods.nextToken();
						if (isValidDayAvailability(dispo)) {
							return null;
						}
					}
            	// After line is validated, we add it to the availability
					dxaRet.addDayAvailability(sDay);
				} else
					throw new DxException(
							DConst.INVALID_NUMBER_OF_PERIODS_AT
									+ nCurrentLine);
               }
		} else {
			throw new DxException(DConst.INVALID_NUMBER_OF_DAYS_LINE);
		}

		return dxaRet;
	}

    private boolean isValidDayAvailability(String sDispo) {
        return (!sDispo.equalsIgnoreCase("1"))
                && (!sDispo.equalsIgnoreCase("5"))
                && (!sDispo.equalsIgnoreCase("2"));
    }

}
