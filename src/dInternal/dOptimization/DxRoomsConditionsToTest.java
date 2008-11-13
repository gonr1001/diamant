/**
 * Created on May 24, 2006
 * 
 * 
 * Title: DxRoomsConditionsToTest.java 
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
package dInternal.dOptimization;

import java.util.StringTokenizer;
import java.util.Vector;

import dConstants.DConst;
import dInterface.dUtil.DxTools;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.dData.dActivities.Activity;
import dInternal.dData.dActivities.Type;
import dInternal.dTimeTable.Period;
import dInternal.dUtil.DXToolsMethods;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxRoomsConditionsToTest is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
public class DxRoomsConditionsToTest implements DxCondition {

	private DModel _dm;

	private int _NOTAVAIL = 5;

	/**
	 * Constructor
	 * 
	 * @param sor
	 */
	public DxRoomsConditionsToTest(DModel dm) {
		_dm = dm;
	}

	/**
	 * check room availability conflicts
	 * 
	 * @param period
	 * @param eventKey
	 * @return
	 */
	private int roomAvailibilityConflicts(Period period, String eventKey) {
		DxEvent event = (DxEvent) _dm.getSetOfEvents().getResource(eventKey)
				.getAttach();
		long roomKey = event.getRoomKey();
		if ((roomKey != -1) && (event.getPeriodKey().length() != 0)) {
			long dayKey = Integer.parseInt(DXToolsMethods.getToken(event
					.getPeriodKey(), ".", 0));
			int[] dayTime = { (int) dayKey, period.getBeginHour()[0],
					period.getBeginHour()[1] };
			String thePeriod = _dm.getTTStructure().getCurrentCycle()
					.getPeriod(dayTime);
			long seqKey = Integer.parseInt(DXToolsMethods.getToken(thePeriod,
					".", 1));
			long perKey = Integer.parseInt(DXToolsMethods.getToken(thePeriod,
					".", 2));
			int dayIndexAvail = _dm.getTTStructure().findIndexInWeekTable(
					dayKey);
			int perPosition = _dm.getTTStructure().getCurrentCycle()
					.getPeriodPositionInDay(dayKey, seqKey, perKey);
			if (perPosition > 0) {
				int[][] matrix = null;
				matrix = _dm.getDxSetOfRooms().getRoom(roomKey)
						.getAvailability().getMatrixAvailability();

				if ((dayIndexAvail < matrix.length)) {
					if (matrix[dayIndexAvail][perPosition - 1] == _NOTAVAIL)
						return 1;
				} else {// else if ((dayIndexAvail < matrix.length))
					return 1;
				}// end else if ((dayIndexAvail < matrix.length))
			}// end if(perPosition>0)
		}
		//        } 
		return 0;
	}

	/**
	 * Check room capicity
	 * 
	 * @param period
	 * @param eventKey
	 * @return
	 */
	private int roomCapacityConflicts(Period period, String eventKey) {
		DxEvent event = (DxEvent) _dm.getSetOfEvents().getResource(eventKey)
				.getAttach();
		StringTokenizer event1 = new StringTokenizer(eventKey,
				DConst.TOKENSEPARATOR);
		DResource activity = _dm.getSetOfActivities().getResource(
				event1.nextToken());
		DResource type = ((Activity) activity.getAttach()).getSetOfTypes()
				.getResource(event1.nextToken());
		DResource section = ((Type) type.getAttach()).getSetOfSections()
				.getResource(event1.nextToken());
		int nbOfStudents = _dm.getSetOfStudents().getStudentsSortedInGroup(
				activity.getID(), type.getID(),
				DxTools.STIConvertGroupToInt(section.getID())).size();
		long roomKey = event.getRoomKey();
		if (roomKey != -1) {
			int nCapa = 0;

			nCapa = _dm.getDxSetOfRooms().getRoomCapacity(roomKey);
			if (nCapa < nbOfStudents)
				return 1;
		}
		//       } 
		return 0;
	}

	/**
	 * Check rooms conflicts between 2 events
	 * 
	 * @param period
	 * @param eventKey
	 * @return
	 */
	private int roomEventsConflicts(Period period, String eventKey,
			ConflictsAttach confV) {
		int nbConf = 0;
		DxEvent event1 = (DxEvent) _dm.getSetOfEvents().getResource(eventKey)
				.getAttach();
		DxEvent event2;

		for (int i = 0; i < period.getEventsInPeriod().size(); i++) {
			String event2ID = period.getEventsInPeriod().getResourceAt(i)
					.getID();
			event2 = (DxEvent) _dm.getSetOfEvents().getResource(event2ID)
					.getAttach();
			if (!event1.getPrincipalRescKey().equalsIgnoreCase(
					event2.getPrincipalRescKey())) {
				if ((event1.getRoomKey() == event2.getRoomKey())
						&& (event1.getRoomKey() != -1)) {
					confV.addConflict(period.getEventsInPeriod().getResourceAt(
							i).getID(), 1, DConst.R_ROOM_NAME, new Vector());
					nbConf++;
				}
			}
		}
		return nbConf;
	}

//	/**
//	 * check room availability conflicts
//	 * 
//	 * @param period
//	 * @param eventKey
//	 * @return
//	 */
//	private int oldRoomAvailibilityConflicts(Period period, String eventKey) {
//		DxEvent event = (DxEvent) _dm.getSetOfEvents().getResource(eventKey)
//				.getAttach();
//		long roomKey = event.getRoomKey();
//		if ((roomKey != -1) && (event.getPeriodKey().length() != 0)) {
//			long dayKey = Integer.parseInt(DXToolsMethods.getToken(event
//					.getPeriodKey(), ".", 0));
//			int[] dayTime = { (int) dayKey, period.getBeginHour()[0],
//					period.getBeginHour()[1] };
//			String thePeriod = _dm.getTTStructure().getCurrentCycle()
//					.getPeriod(dayTime);
//			long seqKey = Integer.parseInt(DXToolsMethods.getToken(thePeriod,
//					".", 1));
//			long perKey = Integer.parseInt(DXToolsMethods.getToken(thePeriod,
//					".", 2));
//			int dayIndexAvail = _dm.getTTStructure().findIndexInWeekTable(
//					dayKey);
//			int perPosition = _dm.getTTStructure().getCurrentCycle()
//					.getPeriodPositionInDay(dayKey, seqKey, perKey);
//			if (perPosition > 0) {
//				int[][] matrix = null;
//				
//				matrix = _dm.getDxSetOfRooms().getRoom(roomKey)
//						.getAvailability().getMatrixAvailability();
//
//				if ((dayIndexAvail < matrix.length)) {
//					if (matrix[dayIndexAvail][perPosition - 1] == _NOTAVAIL)
//						return 1;
//				} else {// else if ((dayIndexAvail < matrix.length))
//					return 1;
//				}// end else if ((dayIndexAvail < matrix.length))
//			}// end if(perPosition>0)
//		}
//		return 0;
//	}



//	/**
//	 * Check room capicity
//	 * 
//	 * @param period
//	 * @param eventKey
//	 * @return
//	 */
//	private int oldRoomCapacityConflicts(Period period, String eventKey) {
//		DxEvent event = (DxEvent) _dm.getSetOfEvents().getResource(eventKey)
//				.getAttach();
//		StringTokenizer event1 = new StringTokenizer(eventKey,
//				DConst.TOKENSEPARATOR);
//		DResource activity = _dm.getSetOfActivities().getResource(
//				event1.nextToken());
//		DResource type = ((Activity) activity.getAttach()).getSetOfTypes()
//				.getResource(event1.nextToken());
//		DResource section = ((Type) type.getAttach()).getSetOfSections()
//				.getResource(event1.nextToken());
//		int nbOfStudents = _dm.getSetOfStudents().getStudentsByGroup(
//				activity.getID(), type.getID(),
//				DxTools.STIConvertGroupToInt(section.getID())).size();
//		long roomKey = event.getRoomKey();
//		if (roomKey != -1) {
//			int nCapa = 0;
//			nCapa = _dm.getDxSetOfRooms().getRoomCapacity(roomKey);
//			if (nCapa < nbOfStudents)
//				return 1;
//		}
//		return 0;
//	}


	/**
	 * Check rooms conflicts between 2 events
	 * 
	 * @param period
	 * @param eventKey
	 * @return
	 */
//	private int oldRoomEventsConflicts(Period period, String eventKey,
//			ConflictsAttach confV) {
//		int nbConf = 0;
//		DxEvent event1 = (DxEvent) _dm.getSetOfEvents().getResource(eventKey)
//				.getAttach();
//		DxEvent event2;
//
//		for (int i = 0; i < period.getEventsInPeriod().size(); i++) {
//			String event2ID = period.getEventsInPeriod().getResourceAt(i)
//					.getID();
//			event2 = (DxEvent) _dm.getSetOfEvents().getResource(event2ID)
//					.getAttach();
//			if (!event1.getPrincipalRescKey().equalsIgnoreCase(
//					event2.getPrincipalRescKey())) {
//				if ((event1.getRoomKey() == event2.getRoomKey())
//						&& (event1.getRoomKey() != -1)) {
//					confV.addConflict(period.getEventsInPeriod().getResourceAt(
//							i).getID(), 1, DConst.R_ROOM_NAME, new Vector());
//					nbConf++;
//				}
//			}
//		}
//
//
//		return nbConf;
//	}

	public int addTest(int[] perKey, Period period, String eventKey) {
		int number = 0;
		int nbConf1, nbConf2, nbConf3 = 0;
		ConflictsAttach confVal = new ConflictsAttach();
		nbConf1 = roomAvailibilityConflicts(period, eventKey);
		nbConf2 = roomCapacityConflicts(period, eventKey);
		nbConf3 = roomEventsConflicts(period, eventKey, confVal);
		number = nbConf1 + nbConf2 + nbConf3;
		if (nbConf1 != 0)
			confVal.addConflict("Disponibilite Local", nbConf1,
					DConst.R_ROOM_NAME, new Vector());
		if (nbConf2 != 0)
			confVal.addConflict("Capacité Local", nbConf2, DConst.R_ROOM_NAME,
					new Vector());

		DResource resc = period.getEventsInPeriod().getResource(eventKey);
		if (resc != null)
			((ConflictsAttach) resc.getAttach()).mergeConflictsAttach(confVal);
		else
			period.getEventsInPeriod().addResource(
					new DResource(eventKey, confVal), 1);
		period.addNbRoomsConflict(number);

		return number;
	}

	public int removeTest(int[] perKey, Period period, String eventKey) {
		int number = 0;
		int nbConf1, nbConf2, nbConf3 = 0;
		ConflictsAttach confVal = new ConflictsAttach();
		nbConf1 = roomAvailibilityConflicts(period, eventKey);
		nbConf2 = roomCapacityConflicts(period, eventKey);
		nbConf3 = roomEventsConflicts(period, eventKey, confVal);
		number = nbConf1 + nbConf2 + nbConf3;
		if (nbConf1 != 0)
			confVal.addConflict("Disponibilite Local", nbConf1,
					DConst.R_ROOM_NAME, new Vector());
		if (nbConf2 != 0)
			confVal.addConflict("Capacité Local", nbConf2, DConst.R_ROOM_NAME,
					new Vector());

		period.getEventsInPeriod().removeResource(eventKey);
		period.removeNbRoomsConflict(number);
		for (int i = 0; i < period.getEventsInPeriod().size(); i++)
			((ConflictsAttach) period.getEventsInPeriod().getResourceAt(i)
					.getAttach()).removeConflict(eventKey, DConst.R_ROOM_NAME);

		return number;
	}

	public int getInfo(int[] perKey, Period period, String eventKey) {
		int number = 0;
		int nbConf1, nbConf2, nbConf3 = 0;
		//       perKey[0] = perKey[0];
		ConflictsAttach confVal = new ConflictsAttach();
		nbConf1 = roomAvailibilityConflicts(period, eventKey);
		nbConf2 = roomCapacityConflicts(period, eventKey);
		nbConf3 = roomEventsConflicts(period, eventKey, confVal);
		number = nbConf1 + nbConf2 + nbConf3;
		if (nbConf1 != 0)
			confVal.addConflict("Disponibilite Local", nbConf1,
					DConst.R_ROOM_NAME, new Vector());
		if (nbConf2 != 0)
			confVal.addConflict("Capacité Local", nbConf2, DConst.R_ROOM_NAME,
					new Vector());

		return number;
	}

}