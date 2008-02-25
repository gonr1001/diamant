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
import dInternal.dUtil.DXToolsMethods;
import eLib.exit.exception.DxException;

public class DxReadSitedotDia implements DxSiteReader {

	private DataExchange _deSites;

	private int _nDays, _nPeriods;

	/**
	 * Used to report line where error was found
	 */
	private long currentLine = 0;

	public DxReadSitedotDia(DataExchange de, int nDays, int nPeriods) {
		_deSites = de;
		_nDays = nDays;
		_nPeriods = nPeriods;
	}

	public DxReadSitedotDia(DataExchange de, int nDays, int nPeriods, long line) {
		_deSites = de;
		_nDays = nDays;
		_nPeriods = nPeriods;
		currentLine = line;
	}

	public DxSetOfSites readSetOfSites() throws DxException {
		StringTokenizer stLineTokenizer;
		StringTokenizer stFileTokenizer = new StringTokenizer(_deSites
				.getContents(), DConst.CR_LF);
		String sFileToken;
		String sLineToken;
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
		//  "Diamant1.6" token 
		stFileTokenizer.nextToken();
		currentLine++;
		// For every line containing a room
		while (stFileTokenizer.hasMoreElements()) {
			sFileToken = stFileTokenizer.nextToken();
			currentLine++;
			stLineTokenizer = new StringTokenizer(sFileToken,
					DConst.ROOM_FIELD_SEPARATOR_TOKEN);
			if (stLineTokenizer.countTokens() == DConst.ROOM_DIA_TOKEN_COUNT) {

				// Room number or name
				sRoomName = stLineTokenizer.nextToken().trim();
				// Room capacity
				sLineToken = stLineTokenizer.nextToken().trim();
				try {
					nRoomCapacity = Integer.parseInt(sLineToken);
				} catch (NumberFormatException e) {
					throw new DxException(DConst.ROOM_TEXT2 + currentLine);
				}
				// Room function
				sLineToken = stLineTokenizer.nextToken().trim();
				try {
					nRoomFunction = Integer.parseInt(sLineToken);
				} catch (NumberFormatException e) {
					throw new DxException(DConst.ROOM_TEXT3 + currentLine);
				}
				// Room characteristics list
				sLineToken = stLineTokenizer.nextToken().trim();
				viCharacteristics = parseCharacteristics(sLineToken,
						currentLine);
				// Room site
				sRoomSite = stLineTokenizer.nextToken().trim();
				// Room category
				sRoomCat = stLineTokenizer.nextToken().trim();
				// Room comment or note
				sNote = stLineTokenizer.nextToken(); //.trim(); notrim
//				System.out.println("sNote " + sNote + "!");
				// Room availability
				sLineToken = stLineTokenizer.nextToken().trim();
				dxaAva = parseAvailability(sLineToken);
			} else {
				throw new DxException(DConst.ROOM_TEXT7 + currentLine);
			}

			dxrTempRoom = new DxRoom(sRoomName, sRoomCat, nRoomCapacity, nRoomFunction,
					viCharacteristics, sNote, dxaAva);
			dxsosBuild.addSite(sRoomSite); // If site exists, it's not added
			dxsosBuild.addCategory(sRoomSite, sRoomCat);// If cat exits, it's
			// not added
			

//			if(sRoomName.equalsIgnoreCase("!!-----")) {
//				dxsosBuild.addRoomInAllCat(sRoomSite, dxrTempRoom);
//			} else {
//				dxsosBuild.addRoom(sRoomSite, sRoomCat, dxrTempRoom);
//			}
//			if(!sRoomName.equalsIgnoreCase("------")) {
				// nothing  dxsosBuild.addRoomInAllCat(sRoomSite, dxrTempRoom);
//			} else {
				dxsosBuild.addRoom(sRoomSite, sRoomCat, dxrTempRoom);
//			}
		} // end while
		return dxsosBuild;
	}

	private Vector<Integer> parseCharacteristics(String token, long currentLine2) {
		Vector<Integer> viTemp = new Vector<Integer>();

		StringTokenizer stChar = new StringTokenizer(token,
				DConst.ROOM_CHAR_SEPARATOR_TOKEN);

		while (stChar.hasMoreTokens()) {
			try {
				viTemp.add(new Integer(stChar.nextToken()));
			} catch (NumberFormatException e) {
				new DxException(DConst.ROOM_TEXT4 + currentLine2);
			}
		}

		return viTemp;
	}

	private DxAvailability parseAvailability(String sAvailabilities)
			throws DxException {
		// extract a line that gives availability of a day

		StringTokenizer stDays = new StringTokenizer(sAvailabilities,
				DConst.AVAILABILITY_DAY_SEPARATOR_ROOM);
		String token = null;
		DxAvailability dxaAvaTemp = new DxAvailability();
		for (int i = 0; stDays.hasMoreElements() && i < _nDays; i++) {
			token = stDays.nextToken();
			// extract a line that gives availability of a day
			String line = DXToolsMethods.getToken(token,
					DConst.AVAILABILITY_SEPARATOR, 0);
			StringTokenizer tokenDispo = new StringTokenizer(line);
			// Verifies that number of period per day was correctly
			// indicated
			if (tokenDispo.countTokens() == _nPeriods) {
				// Verifies that every availability element is valid
				while (tokenDispo.hasMoreElements()) {
					String dispo = tokenDispo.nextToken();
					if (!isValidDayAvailability(dispo)) {
						throw new DxException(DConst.INVALID_AVAILABILITY_AT
								+ currentLine);
					}
				}
				// After line is validated, we add it to the availability
				dxaAvaTemp.addDayAvailability(line);
			} else
				throw new DxException(DConst.INVALID_AVAILABILITY_AT
						+ currentLine);

		}// end for(int i=1;
		return dxaAvaTemp;
	}

	private boolean isValidDayAvailability(String sDispo) {
		return (sDispo.equalsIgnoreCase("1")) || (sDispo.equalsIgnoreCase("5"))
				|| (sDispo.equalsIgnoreCase("2"));
	}
	//  public DxSetOfSites readSetOfSites2() throws DxException{
	//  StringTokenizer stLineTokenizer;
	//  StringTokenizer stFileTokenizer = new StringTokenizer(_deSites
	//          .getContents(), DConst.CR_LF);
	//
	//  String sFileToken;
	//  String sLineToken;
	//
	//  int nCurrentLineState = 0;
	//
	//  // Initialized to avoid further warnings
	//  String sRoomName = null;
	//  int nRoomCapacity = 0;
	//  int nRoomFunction = 0;
	//  Vector<Integer> viCharacteristics = null;
	//  String sRoomSite = null;
	//  String sRoomCat = null;
	//  String sNote = null;
	//  DxAvailability dxaAva = null;
	//
	//  DxSetOfSites dxsosBuild = new DxSetOfSites();
	//  DxRoom dxrTempRoom;
	//
	//////No useless lines in dia files
	////  // Skips useless lines
	////  while (stFileTokenizer.hasMoreElements()
	////          && nCurrentLine < DConst.ROOM_USELESS_DIA_HEADER) {
	////      sFileToken = stFileTokenizer.nextToken();
	////      nCurrentLine++;
	////  }
	//
	//  stFileTokenizer.nextToken(); // "Diamant1.6" token
	//  // For every line containing a room
	//  while (stFileTokenizer.hasMoreElements()) {
	//      sFileToken = stFileTokenizer.nextToken();
	//      nCurrentLineState = 0;
	//      stLineTokenizer = new StringTokenizer(sFileToken,
	//              DConst.ROOM_FIELD_SEPARATOR_TOKEN);
	//      if (stLineTokenizer.countTokens() == DConst.ROOM_DIA_TOKEN_COUNT) {
	//          while (nCurrentLineState < DConst.ROOM_DIA_TOKEN_COUNT) {
	//              sLineToken = stLineTokenizer.nextToken().trim();
	//
	//              // Finite state machine for fields on a line
	//              switch (nCurrentLineState) {
	//              // Room name field
	//              case 0:
	//                  sRoomName = sLineToken;
	//                  break;
	//
	//              // Room capacity
	//              case 1:
	//                  try {
	//                      nRoomCapacity = new Integer(sLineToken).intValue();
	//                  } catch (NumberFormatException e) {
	//                  	 throw new DxException(DConst.ROOM_TEXT2+currentLine);
	//                  }
	//                  break;
	//
	//              // Room function
	//              case 2:
	//                  try {
	//                      nRoomFunction = new Integer(sLineToken).intValue();
	//                  } catch (NumberFormatException e) {
	//                  	throw new DxException(DConst.ROOM_TEXT3+currentLine);
	//                  }
	//                  break;
	//
	//              // Room characteristics list
	//              case 3:
	//                  viCharacteristics = parseCharacteristics(sLineToken,currentLine);
	//                  break;
	//
	//              case 4:
	//                  sRoomSite = sLineToken;
	//                  break;
	//
	//              case 5:
	//                  sRoomCat = sLineToken;
	//                  break;
	//
	//              // Room comment or note
	//              case 6:
	//                  sNote = sLineToken;
	//                  break;
	//
	//              // Room availability
	//              case 7:
	//                  dxaAva = parseAvailability(sLineToken);
	//                  break;
	//
	//              }
	//              nCurrentLineState++;
	//          }
	//          dxrTempRoom = new DxRoom(sRoomName, nRoomCapacity,
	//                  nRoomFunction, viCharacteristics, sNote, dxaAva);
	//          dxsosBuild.addSite(sRoomSite); // If site exists, it's not
	//          // added
	//          dxsosBuild.addCategory(sRoomSite, sRoomCat);// If cat exits, it's
	//          // not added
	//          dxsosBuild.addRoom(sRoomSite, sRoomCat, dxrTempRoom);
	//      } else {
	//      	  //if (stLineTokenizer.countTokens() == DConst.ROOM_DIA_TOKEN_COUNT)
	//      	 throw new DxException(DConst.ROOM_TEXT7+currentLine);
	//      }
	//      currentLine++;
	//  }
	//  return dxsosBuild;
	//}

}
