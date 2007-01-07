/**
 * Created on Jul 26, 2006
 * 
 * TODO To change the class description for this generated file
 * 
 * Project Dx
 * Title: DxReadActivitiesSites1dot5.java 
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
import java.util.StringTokenizer;

import dConstants.DConst;
import dInternal.DataExchange;
import dInternal.dData.dInstructors.DxInstructor;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import dInternal.dData.dRooms.DxRoom;
import dInternal.dData.dRooms.DxSetOfRooms;
import dInternal.dUtil.DXToolsMethods;
import eLib.exit.exception.DxException;

public class DxReadActivitiesSites1dot5 implements DxActivitiesSitesReader {

	private DataExchange _deActivities;

	private DxSetOfInstructors _dxsoiInstructors;

	private DxSetOfRooms _dxsorRooms;

	private int _nPeriodLength;

	final static private int _COURSENAMELENGTH = 6;

	final static private int _ACTIVITYLENGTH = 11;

	private int _NUMBEROFCYCLE = 1;

	private boolean _isDia;

	public DxReadActivitiesSites1dot5(DataExchange de,
			DxSetOfInstructors dxsoiInst, DxSetOfRooms dxsorRooms,
			int nPeriodLength, boolean isDia) {
		_deActivities = de;
		_dxsoiInstructors = dxsoiInst;
		_dxsorRooms = dxsorRooms;
		_nPeriodLength = nPeriodLength;
		_isDia = isDia;
	}

	public DxSetOfActivitiesSites readSetOfActivitiesSites() throws DxException {

		String sToken;
		// String sousString; //auxiliar String for stocking a substring of a
		// line
		StringTokenizer st = new StringTokenizer(_deActivities.getContents(),
				"\r\n");
		StringTokenizer stLine = null; // auxiliar StringTokenizer for reading
		// subStrings in a line

		// Starting position of the finite state machine
		int nPosition = 0;

		String sInstructorsName = "";

		// In version 1.5, no sites. We assign all activities to the same site.
		DxSetOfActivitiesSites dxsoasAllSites = new DxSetOfActivitiesSites();
		DxActivitySite dxasCurentSite = new DxActivitySite(
				DConst.ACTIVITY_STANDARD_SITE);
		dxsoasAllSites.addActivitiySite(dxasCurentSite);

		DxActivity dxaActivity = null;
		DxType dxtType = null;
		DxSection dxsSection = null;
		DxUnity dxuUnity = null;
		DxAssignement dxassAssign = null;

		Iterator<DxUnity> iUnities = null;
		Iterator<DxAssignement> iAssignement = null;
		int nUnityCount = 0;

		while (st.hasMoreElements()) {
			sToken = st.nextToken();
			switch (nPosition) {
			case 0:// activity name
				if (sToken.trim().length() < _ACTIVITYLENGTH) {
					// TODO: Must throw error
					// _error = DConst.ACTI_TEXT1
					// + _line
					// + " in the activity file:"
					// + "\n"
					// + "I was in ActiviesList class and in analyseTokens
					// method ";
				}
				String sActivityName = sToken.substring(0, _COURSENAMELENGTH);
				dxaActivity = dxasCurentSite.getActivity(sActivityName);
				if (dxaActivity == null) {
					dxaActivity = new DxActivity(sActivityName);
					dxasCurentSite.addActivity(dxaActivity);
				}

				String sTypeName = sToken.substring(_COURSENAMELENGTH,
						_COURSENAMELENGTH + 1);
				dxtType = dxaActivity.getType(sTypeName);
				if (dxtType == null) {
					dxtType = new DxType(sTypeName);
					dxaActivity.addType(dxtType);
				}

				String sSectionName = DXToolsMethods.getToken(sToken, " ", 1);
				dxsSection = dxtType.getSection(sSectionName);
				if (dxsSection == null) {
					dxsSection = new DxSection(DXToolsMethods.getToken(sToken,
							" ", 1));
					dxtType.addSection(dxsSection);
				}

				// Site and capacity on this line in 1.6 version

				nPosition = 1;
				break;
			case 1:// activity visibility
				int nVisibility = 0;
				try {
					nVisibility = Integer.parseInt(sToken.trim());
				} catch (NumberFormatException e) {
					// TODO: Must throw error
					// DConst.ACTI_TEXT2 + _line, "ActivityList"
				}

				if (nVisibility == 0 || nVisibility == 1) {
					dxaActivity.setVisibility(nVisibility == 1);
				} else {
					// TODO: Must throw error
					// DConst.ACTI_TEXT2 + _line, "ActivityList"
				}

				nPosition = 2;
				break;
			case 2:// number of activities
				try {
					new Integer(sToken.trim());
				} catch (NumberFormatException e) {
					// TODO: Must throw error
					// DConst.ACTI_TEXT3 + _line
				}

				nPosition = 3;
				break;
			case 3:// teachers' names
				sInstructorsName = sToken;
				nPosition = 4;
				break;
			case 4:// number of blocs
				try {
					nUnityCount = Integer.parseInt(sToken.trim());
				} catch (NumberFormatException e) {
					// TODO: Must throw error
					// DConst.ACTI_TEXT5 + _line, " ActivityList"
				}

				// Verify that we have an instructor for every bloc
				if (DXToolsMethods.countTokens(sInstructorsName, ";") != nUnityCount) {
					// TODO: Must throw error
					// _error = DConst.ACTI_TEXT13
					// + _line
					// + ", I was in SetOfActivies class and in
					// analyseDeltaTokens method ";
				}

				for (int i = 1; i <= nUnityCount; i++) {
					dxuUnity = new DxUnity(Integer.toString(i));
					dxsSection.addUnity(dxuUnity);
				}

				nPosition = 5;
				break;
			case 5:// duration of blocs
				stLine = new StringTokenizer(sToken);
				if (stLine.countTokens() != nUnityCount) {
					// TODO: Must throw error
					// _error = DConst.ACTI_TEXT5
					// + _line
					// + " in the activity file:"
					// + "\n"
					// + "I was in ActiviesList class and in analyseTokens
					// method ";
				}

				iUnities = dxsSection.getUnitiesIterator();
				int nDuration;
				while (iUnities.hasNext()) {
					try {
						nDuration = Integer.parseInt(stLine.nextToken().trim())
								* _nPeriodLength;
						dxuUnity = iUnities.next();
						dxuUnity.setDuration(nDuration);
					} catch (NumberFormatException e) {
						// TODO: Must throw error
						// DConst.ACTI_TEXT7 + _line, "ActivityList"
					}
				}

				nPosition = 6;
				break;
			case 6:// days and periods of blocs
				int nType;
				String sDay;
				String sPeriod;

				stLine = new StringTokenizer(sToken);
				if (nUnityCount != stLine.countTokens()) {
					// TODO: Must throw error
					// _error = DConst.ACTI_TEXT5 + _line + " ActivityList";
				}

				iUnities = dxsSection.getUnitiesIterator();

				while (iUnities.hasNext()) {
					dxuUnity = iUnities.next();
					for (int i = 1; i <= _NUMBEROFCYCLE; i++) {
						dxassAssign = new DxAssignement(Integer.toString(i));

						// Verify if we are using the dotted notation or the
						// day/period notation
						nType = DXToolsMethods.countTokens(sToken, ".");

						// Day/Perdio notation
						if (nType == 1) {
							if ((nUnityCount * 2) != stLine.countTokens()) {
								// TODO: Must throw error
								// _error = DConst.ACTI_TEXT5 + _line + "
								// ActivityList";
							}

							sDay = stLine.nextToken().trim();
							sPeriod = stLine.nextToken().trim();
							int nDayKey;
							int[] nTime;
							try {
								nDayKey = Integer.parseInt(sDay);
								nTime = DXToolsMethods
										.convertSTIPeriods(Integer
												.parseInt(sPeriod));

								dxassAssign.setDateAndTime(nDayKey, nTime[0],
										nTime[1]);
							} catch (NumberFormatException e) {
								// TODO: Must throw error
								// DConst.ACTI_TEXT8 + _line, "ActivityList"
							}

						} else { // Dotted notation
							sPeriod = stLine.nextToken().trim();
							dxassAssign.setPeriodKey(sPeriod);
						}// end else if(typeOfData==1)
						dxuUnity.addAssignment(dxassAssign);
					}
				}

				nPosition = 7;
				break;
			case 7:// fixed rooms
				stLine = new StringTokenizer(sToken);
				if (stLine.countTokens() != nUnityCount) {
					// TODO: Must throw error
					// _error = DConst.ACTI_TEXT5
					// + _line
					// + " in the activity file:"
					// + "\n"
					// + "I was in ActiviesList class and in analyseTokens
					// method ";
				}

				iUnities = dxsSection.getUnitiesIterator();
				while (iUnities.hasNext()) {
					dxuUnity = iUnities.next();
					int nFixed = 0;
					try {
						nFixed = Integer.parseInt(stLine.nextToken().trim());
					} catch (NumberFormatException e) {
						//TODO
					}
					if (!(nFixed == 0 || nFixed == 1)) {
						// TODO: Must throw error
						// DConst.ACTI_TEXT9 + _line, "ActivityList"
					}

					iAssignement = dxuUnity.getAssignementsIterator();
					while (iAssignement.hasNext()) {
						iAssignement.next().setRoomState(nFixed == 1);
					}
				}

				nPosition = 8;
				break;
			case 8:// Preferred rooms
				stLine = new StringTokenizer(sToken);
				if (stLine.countTokens() != nUnityCount) {
					// TODO: Must throw error
					// DConst.ACTI_TEXT10
					// + _line
					// + "in the activity file:"
					// + "\n"
					// + "I was in ActiviesList class and in analyseTokens
					// method ";
				}

				// In non dia files, instructor name appeared only once.
				// We repeat it for every unities. Although it's not very
				// elegant, it's the way it was done originally.
				if (!_isDia) {
					String sOneInst = sInstructorsName;
					for (int i = 1; i <= nUnityCount; i++)
						sInstructorsName += DConst.ACT_UNITY_SEPARATOR
								+ sOneInst;
				}

				StringTokenizer stInstLine = new StringTokenizer(
						sInstructorsName, DConst.ACT_UNITY_SEPARATOR);

				iUnities = dxsSection.getUnitiesIterator();
				String sName,
				sRoom;
				while (iUnities.hasNext()) {
					dxuUnity = iUnities.next();
					sRoom = stLine.nextToken().trim();
					sName = stInstLine.nextToken().trim();
					iAssignement = dxuUnity.getAssignementsIterator();

					DxRoom dxrRoom;
					dxrRoom = _dxsorRooms.getRoom(sRoom);
					if (dxrRoom == null) {
						// TODO: What is comportement for unexisting room
					}

					// Many instructor could be assigned to an unity. They
					// will be separated by a ":".
					DxSetOfInstructors dxsoiInstructors = new DxSetOfInstructors();
					DxInstructor dxiNextInst;
					StringTokenizer stUnityInst = new StringTokenizer(sName,
							DConst.ACT_INST_SEPARATOR);
					while (stUnityInst.hasMoreElements()) {
						dxiNextInst = _dxsoiInstructors
								.getInstructor(stUnityInst.nextToken().trim());
						if (dxiNextInst != null) {
							dxsoiInstructors.addInstructor(dxiNextInst);
						} else {
							// TODO: Must throw error
							// Should throw an excpetion as this instructor
							// does not exist
						}
					}

					while (iAssignement.hasNext()) {
						dxassAssign = iAssignement.next();
						dxassAssign.setRoom(dxrRoom);
						dxassAssign.setSetOfInstructors(dxsoiInstructors);
					}
				}

				nPosition = 9;
				break;
			case 9:// type of rooms
				stLine = new StringTokenizer(sToken);
				if (stLine.countTokens() != nUnityCount) {
					// TODO: Must throw error
					// DConst.ACTI_TEXT10
					// + _line
					// + "in the activity file:"
					// + "\n"
					// + "I was in ActiviesList class and in analyseTokens
					// method ";
				}

				iUnities = dxsSection.getUnitiesIterator();
				while (iUnities.hasNext()) {
					dxuUnity = iUnities.next();
					String sRoomType = stLine.nextToken().trim();
					try {
						new Integer(sRoomType);
					} catch (NumberFormatException e) {
						// TODO: Must throw error
						// DConst.ACTI_TEXT11 + _line, "ActivityList"
					}
					dxuUnity.addPreferFunctionRoom(sRoomType);
				}

				nPosition = 10;
				break;
			case 10:// idem
				stLine = new StringTokenizer(sToken);
				while (stLine.hasMoreElements()) {
					try {
						new Integer(stLine.nextToken().trim());
					} catch (NumberFormatException e) {
						// TODO: Must throw error
						// DConst.ACTI_TEXT11 + _line, "ActivityList"
					}
				}
//				dxaActivity.setIdemLine(sToken.trim());

				nPosition = 11;
				break;
			case 11:// activity is fixed or not
				StringTokenizer visiToken = new StringTokenizer(sToken, ";");
				int nbTokens = visiToken.countTokens();

				stLine = new StringTokenizer(visiToken.nextToken());
				if (stLine.countTokens() != nUnityCount) {
					// TODO: Must throw error
					// DConst.ACTI_TEXT10
					// + _line
					// + "in the activity file:"
					// + "\n"
					// + "I was in ActiviesList class and in analyseTokens
					// method ";
				}

				StringTokenizer assignLine = null;
				if (nbTokens == 2) {
					assignLine = new StringTokenizer(visiToken.nextToken());
					if (assignLine.countTokens() != nUnityCount) {
						// TODO: Must throw error
						// DConst.ACTI_TEXT10
						// + _line
						// + "in the activity file:"
						// + "\n"
						// + "I was in ActiviesList class and in analyseTokens
						// method ";
					}
				}

				iUnities = dxsSection.getUnitiesIterator();
				while (iUnities.hasNext()) {
					dxuUnity = iUnities.next();
					int nFix = 0;
					try {
						nFix = Integer.parseInt(stLine.nextToken().trim());
					} catch (NumberFormatException e) {
						// TODO: Must throw error
						// DConst.ACTI_TEXT11 + _line, "ActivityList"
					}
					if (nFix == 1 || nFix == 0) {
						dxuUnity.setPermanent(nFix == 1);
					} else {
						// TODO: Must throw error
						// DConst.ACTI_TEXT12 + line + " ActivityList"
					}

					if (nbTokens == 2) {
						int nFix1 = 0;
						try {
							nFix1 = Integer.parseInt(assignLine.nextToken()
									.trim());
						} catch (NumberFormatException e) {
							// TODO: Must throw error
							// DConst.ACTI_TEXT12 + line + " ActivityList"
						}
						if (nFix1 == 1 || nFix1 == 0) {
							dxuUnity.setAssign(nFix1 == 1);
						} else {
							// TODO: Must throw error
							// DConst.ACTI_TEXT12 + line + " ActivityList"
						}
					} else
						dxuUnity.setAssign(nFix == 1);
				}// end while(stLine.hasMoreElements())

				nPosition = 0;
				break;
			}// end switch (position)
		}// end while (st.hasMoreElements())
		return dxsoasAllSites;
	}
}
