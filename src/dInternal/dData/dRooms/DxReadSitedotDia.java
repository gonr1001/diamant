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
import dInternal.dUtil.DXToolsMethods;

public class DxReadSitedotDia implements DxSiteReader {

	private DataExchange _deSites;

	private int _nDays, _nPeriods;

	/**
	 * Used to report line where error was found
	 */
	private int _linesCounter = 0;

	public DxReadSitedotDia(DataExchange de, int nDays, int nPeriods) {
		_deSites = de;
		_nDays = nDays;
		_nPeriods = nPeriods;
	}

	public DxReadSitedotDia(DataExchange de, int nDays, int nPeriods, int line) {
		_deSites = de;
		_nDays = nDays;
		_nPeriods = nPeriods;
		_linesCounter = line;
	}

	public DxSetOfSites readSetOfSites() throws DiaException {
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
		// "Diamant1.6" token
		stFileTokenizer.nextToken();
		_linesCounter++;
		// For every line containing a room
		while (stFileTokenizer.hasMoreElements()) {
			sFileToken = stFileTokenizer.nextToken();
			_linesCounter++;
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
					throw new DiaException(DConst.ROOM_TEXT2 + _linesCounter);
				}
				// Room function
				sLineToken = stLineTokenizer.nextToken().trim();
				try {
					nRoomFunction = Integer.parseInt(sLineToken);
				} catch (NumberFormatException e) {
					throw new DiaException(DConst.ROOM_TEXT3 + _linesCounter);
				}
				// Room characteristics list
				sLineToken = stLineTokenizer.nextToken().trim();
				viCharacteristics = parseCharacteristics(sLineToken,
						_linesCounter);
				// Room site
				sRoomSite = stLineTokenizer.nextToken().trim();
				// Room category
				sRoomCat = stLineTokenizer.nextToken().trim();
				// Room comment or note
				sNote = stLineTokenizer.nextToken(); // .trim(); notrim
				// System.out.println("sNote " + sNote + "!");
				// Room availability
				sLineToken = stLineTokenizer.nextToken().trim();
				dxaAva = parseAvailability(sLineToken);
			} else {
				throw new DiaException(DConst.ROOM_TEXT7 + _linesCounter);
			}
			sRoomName = sRoomName.toUpperCase();
			sRoomCat = sRoomCat.toUpperCase();
			sRoomSite = sRoomSite.toUpperCase();
			dxrTempRoom = new DxRoom(sRoomName, sRoomCat, nRoomCapacity,
					nRoomFunction, viCharacteristics, sNote, dxaAva);
			dxsosBuild.addSite(sRoomSite); // If site exists, it's not added
			dxsosBuild.addCategory(sRoomSite, sRoomCat);// If cat exits, it's

			dxsosBuild.addRoom(sRoomSite, sRoomCat, dxrTempRoom);

		} // end while
		return dxsosBuild;
	}

	public DxSetOfSites readSetOfSitesNew() throws DiaException {
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
		// "Diamant1.6" token
		stFileTokenizer.nextToken();
		_linesCounter++;
		// For every line containing a room
		while (stFileTokenizer.hasMoreElements()) {
			sFileToken = stFileTokenizer.nextToken();
			_linesCounter++;
			stLineTokenizer = new StringTokenizer(sFileToken,
					DConst.ROOM_FIELD_SEPARATOR_TOKEN, true);
			if (stLineTokenizer.countTokens() == DConst.ROOM_DIA_TOKEN_COUNT) {
				// Room number or name
				sRoomName = stLineTokenizer.nextToken().trim();
				// Room capacity
				sLineToken = stLineTokenizer.nextToken().trim();
				try {
					nRoomCapacity = Integer.parseInt(sLineToken);
				} catch (NumberFormatException e) {
					throw new DiaException(DConst.ROOM_TEXT2 + _linesCounter);
				}
				// Room function
				sLineToken = stLineTokenizer.nextToken().trim();
				try {
					nRoomFunction = Integer.parseInt(sLineToken);
				} catch (NumberFormatException e) {
					throw new DiaException(DConst.ROOM_TEXT3 + _linesCounter);
				}
				// Room characteristics list
				sLineToken = stLineTokenizer.nextToken().trim();
				viCharacteristics = parseCharacteristics(sLineToken,
						_linesCounter);
				// Room site
				sRoomSite = stLineTokenizer.nextToken().trim();
				// Room category
				sRoomCat = stLineTokenizer.nextToken().trim();
				// Room comment or note
				sNote = stLineTokenizer.nextToken(); // .trim(); notrim
				// System.out.println("sNote " + sNote + "!");
				// Room availability
				sLineToken = stLineTokenizer.nextToken().trim();
				dxaAva = parseAvailability(sLineToken);
			} else {
				throw new DiaException(DConst.ROOM_TEXT7 + _linesCounter);
			}

			dxrTempRoom = new DxRoom(sRoomName, sRoomCat, nRoomCapacity,
					nRoomFunction, viCharacteristics, sNote, dxaAva);
			dxsosBuild.addSite(sRoomSite); // If site exists, it's not added
			dxsosBuild.addCategory(sRoomSite, sRoomCat);// If cat exits, it's
			// not added

			// if(sRoomName.equalsIgnoreCase("!!-----")) {
			// dxsosBuild.addRoomInAllCat(sRoomSite, dxrTempRoom);
			// } else {
			// dxsosBuild.addRoom(sRoomSite, sRoomCat, dxrTempRoom);
			// }
			// if(!sRoomName.equalsIgnoreCase("------")) {
			// nothing dxsosBuild.addRoomInAllCat(sRoomSite, dxrTempRoom);
			// } else {
			dxsosBuild.addRoom(sRoomSite, sRoomCat, dxrTempRoom);
			// }
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
				new DiaException(DConst.ROOM_TEXT4 + currentLine2);
			}
		}

		return viTemp;
	}

	private DxAvailability parseAvailability(String sAvailabilities)
			throws DiaException {
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
						throw new DiaException(DConst.INVALID_AVAILABILITY_AT
								+ _linesCounter);
					}
				}
				// After line is validated, we add it to the availability
				dxaAvaTemp.addDayAvailability(line);
			} else
				throw new DiaException(DConst.INVALID_AVAILABILITY_AT
						+ _linesCounter);

		}// end for(int i=1;
		return dxaAvaTemp;
	}

	private boolean isValidDayAvailability(String sDispo) {
		return (sDispo.equalsIgnoreCase("1")) || (sDispo.equalsIgnoreCase("5"))
				|| (sDispo.equalsIgnoreCase("2"));
	}


	@Override
	public int getLines() {
		return _linesCounter;
	}

}
