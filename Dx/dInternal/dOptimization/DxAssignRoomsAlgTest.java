/**
 * Created on Jun 16, 2006
 * 
 * 
 * Title: DxAssignRoomsAlgTest.java 
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

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInterface.DxDocument;
import dInterface.DxTTableDoc;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DxConflictLimits;
import dInternal.dData.DxResource;
import dInternal.dData.DxSetOfResources;
import dInternal.dData.StandardCollection;
import dInternal.dData.dRooms.DxCategory;
import dInternal.dData.dRooms.DxRoom;
import dInternal.dData.dRooms.DxSetOfCategories;
import dInternal.dData.dRooms.DxSetOfSites;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Period;
import developer.DxFlags;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxAssignRoomsAlgTest is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
public class DxAssignRoomsAlgTest extends TestCase {

	public DxAssignRoomsAlgTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxAssignRoomsAlgTest.class);
	} // end suite

	public void test_build1() {
		DxConflictLimits dxCL = new DxConflictLimits();
		String str = "conflictLimits;0;0;0;0;30;0;100;";
		dxCL.readLimits(str);
		DModel dm1 = null;

		DxDocument _dxDocument1 = new DxTTableDoc();
		String fileName = "." + File.separator;
		fileName += "dataTest" + File.separator;
		fileName += "refFiles" + File.separator;
		fileName += "facs" + File.separator;
		fileName += "flsh2_1" + File.separator;
		fileName += "RoomAffContTT.dia";

		try {

			dm1 = new DModel(_dxDocument1, fileName.toString());
			dm1.changeInDModel(new Object());
			assertEquals("test_build: assertEquals", 94, dm1
					.getSetOfActivities().size());
			assertEquals("test_build: assertEquals", 117, dm1.getSetOfEvents()
					.size());

			if (DxFlags.newAlg) {
				DxAssignRoomsAlg alg = new DxAssignRoomsAlg(dm1, dxCL,
						DxFlags.increase);
				alg.doWork();
			} else {
				RoomAssignmentAlgo alg = new RoomAssignmentAlgo(dm1);
			}
			assertEquals("test_build: assertEquals", 116, dm1.getSetOfEvents()
					.getNumberOfEventAssign());
			dm1 = null;
		} catch (Exception e) {
			// Should not fail in controled conditions
		}

	}

	public void test_build2() {
		DxConflictLimits dxCL = new DxConflictLimits();
		String str = "conflictLimits;0;0;0;0;30;0;100;";
		dxCL.readLimits(str);
		DModel dm1 = null;
		DxDocument _dxDocument1 = new DxTTableDoc();
		String fileName = "." + File.separator;
		fileName += "dataTest" + File.separator;
		fileName += "refFiles" + File.separator;
		fileName += "facs" + File.separator;
		fileName += "flsh2_1" + File.separator;
		fileName += "Aut2006flsh170m.dia";

		try {
			dm1 = new DModel(_dxDocument1, fileName.toString());
			dm1.changeInDModel(new Object());
			assertEquals("test_build: assertEquals", 199, dm1
					.getSetOfActivities().size());
			assertEquals("test_build: assertEquals", 253, dm1.getSetOfEvents()
					.size());

			if (DxFlags.newAlg) {
				DxAssignRoomsAlg alg = new DxAssignRoomsAlg(dm1, dxCL,
						DxFlags.increase);
				alg.doWork();
			} else {
				RoomAssignmentAlgo alg = new RoomAssignmentAlgo(dm1);
			}

			assertEquals("test_build: assertEquals", 250, dm1.getSetOfEvents()
					.getNumberOfEventAssign());
			dm1 = null;
		} catch (Exception e) {
			// Should not fail in controled conditions
		}
	}

	public void test_getNumberOfPeriods() {
		DxConflictLimits dxCL = new DxConflictLimits();
		String str = "conflictLimits;0;0;0;0;30;0;100;";
		dxCL.readLimits(str);
		DModel dm1 = null;

		DxDocument _dxDocument1 = new DxTTableDoc();
		String fileName = "." + File.separator;
		fileName += "dataTest" + File.separator;
		fileName += "refFiles" + File.separator;
		fileName += "facs" + File.separator;
		fileName += "flsh2_1" + File.separator;
		fileName += "RoomAffTestsFlsh170min.dia";

		try {
			dm1 = new DModel(_dxDocument1, fileName.toString());
			dm1.changeInDModel(new Object());

			DxAssignRoomsAlg alg = new DxAssignRoomsAlg(dm1, dxCL,
					DxFlags.increase);

			assertEquals("test_getNumberOfPeriods: assertEquals", 28, alg
					.getNumberOfPeriods());
			dm1 = null;
		} catch (Exception e) {
			// Should not fail in controled conditions
		}
	}

	public void test_events() {
		DxConflictLimits dxCL = new DxConflictLimits();
		String str = "conflictLimits;0;0;0;0;30;0;100;";
		dxCL.readLimits(str);
		DModel dm1 = null;

		DxDocument _dxDocument1 = new DxTTableDoc();
		String fileName = "." + File.separator;
		fileName += "dataTest" + File.separator;
		fileName += "refFiles" + File.separator;
		fileName += "facs" + File.separator;
		fileName += "flsh2_1" + File.separator;
		fileName += "RoomAffTestsFlsh170min.dia";

		try {
			dm1 = new DModel(_dxDocument1, fileName.toString());
			dm1.changeInDModel(new Object());

			DxAssignRoomsAlg alg = new DxAssignRoomsAlg(dm1, dxCL,
					DxFlags.increase);
			Cycle cycle = dm1.getTTStructure().getCurrentCycle();
			cycle.setCurrentDaySeqPerIndex(0, 0, 0);
			int periodStep = 1;
			int days = cycle.getNumberOfDays();
			DSetOfResources eventsInCat = new StandardCollection();
			Period currentPeriod = cycle.getNextPeriod(periodStep);
			DxSetOfResources listOfAvailableDxRooms = null;
			int d = 0;
			int h = 0;
			d = 0 / days;
			h = 0 % days;
			if (alg._increase) {
				assertEquals("events: number of events in period", 3,
						currentPeriod.getNumberOfEvents());
				DSetOfResources eventsInPeriod = alg
						.builEventsWithStudentsInPeriod(currentPeriod);
				// for(int j = 0; j < eventsInPeriod.size(); j++) {
				DResource eventToAssign = eventsInPeriod.getResourceAt(0);
				DxEvent e = (DxEvent) eventToAssign.getAttach();
				assertEquals("events: eventFullName ", "FRE060", e
						.getfullName());
				assertEquals("events: eventName ", "Classe", e.getRoomName());

				eventToAssign = eventsInPeriod.getResourceAt(1);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("events: eventFullName ", "FLS060", e
						.getfullName());
				assertEquals("events: eventName ", "------", e.getRoomName());

				eventToAssign = eventsInPeriod.getResourceAt(2);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("events: eventFullName ", "GEO550", e
						.getfullName());
				assertEquals("events: eventName ", "A4-262", e.getRoomName());
				// }
				DxSetOfSites sos = dm1.getDxSetOfSites();
				String currentSiteName = dm1.getCurrentSiteName();
				DxSetOfCategories soc = sos.getSetOfCat(currentSiteName);
				DxCategory[] cArray = soc.getCatsSortedByKey();
				// for (int i = 0; i < cArray.length; i++){
				DxCategory cat = cArray[0];
				String sCatName = cat.getName();
				assertEquals("events: catName ", "Classe", cat.getName());

				listOfAvailableDxRooms = alg
						.buildListofAvailableDxRooms(eventsInPeriod);
				assertEquals("events: listOfAvailableDxRooms size ", 28,
						listOfAvailableDxRooms.size());

				Vector<DxResource> roomsInCat = alg.getAvailableRoomsInCat(
						listOfAvailableDxRooms, sCatName, currentPeriod, d, h);
				assertEquals("events: roomsInCat size ", 6, roomsInCat.size());
				eventsInCat = alg.getEventsWithRoomNamed(eventsInPeriod,
						sCatName);
				assertEquals("events: eventsInCat size ", 1, eventsInCat.size());
			} else {
				assertEquals("events: number of events in period", 3,
						currentPeriod.getNumberOfEvents());
				DSetOfResources eventsInPeriod = alg
						.builEventsWithStudentsInPeriod(currentPeriod);
				// for(int j = 0; j < eventsInPeriod.size(); j++) {
				DResource eventToAssign = eventsInPeriod.getResourceAt(0);
				DxEvent e = (DxEvent) eventToAssign.getAttach();
				assertEquals("events: eventFullName ", "GEO550", e
						.getfullName());
				assertEquals("events: eventName ", "A4-262", e.getRoomName());

				eventToAssign = eventsInPeriod.getResourceAt(1);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("events: eventFullName ", "FLS060", e
						.getfullName());
				assertEquals("events: eventName ", "------", e.getRoomName());

				eventToAssign = eventsInPeriod.getResourceAt(2);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("events: eventFullName ", "FRE060", e
						.getfullName());
				assertEquals("events: eventName ", "Classe", e.getRoomName());
				// }
				DxSetOfSites sos = dm1.getDxSetOfSites();
				String currentSiteName = dm1.getCurrentSiteName();
				DxSetOfCategories soc = sos.getSetOfCat(currentSiteName);
				DxCategory[] cArray = soc.getCatsSortedByKey();
				// for (int i = 0; i < cArray.length; i++){
				DxCategory cat = cArray[0];
				String sCatName = cat.getName();
				assertEquals("events: catName ", "Classe", cat.getName());

				listOfAvailableDxRooms = alg
						.buildListofAvailableDxRooms(eventsInPeriod);
				assertEquals("events: listOfAvailableDxRooms size ", 28,
						listOfAvailableDxRooms.size());

				Vector<DxResource> roomsInCat = alg.getAvailableRoomsInCat(
						listOfAvailableDxRooms, sCatName, currentPeriod, d, h);
				assertEquals("events: roomsInCat size ", 6, roomsInCat.size());
				eventsInCat = alg.getEventsWithRoomNamed(eventsInPeriod,
						sCatName);
				assertEquals("events: eventsInCat size ", 1, eventsInCat.size());
			}

			dm1 = null;
		} catch (Exception e) {
			// Should not fail in controled conditions
			System.out.println(e.getStackTrace());
		}

	}

	public void test_getEvents() {
		DxConflictLimits dxCL = new DxConflictLimits();
		String str = "conflictLimits;0;0;0;0;30;0;100;";
		dxCL.readLimits(str);
		DModel dm1 = null;

		DxDocument _dxDocument1 = new DxTTableDoc();
		String fileName = "." + File.separator;
		fileName += "dataTest" + File.separator;
		fileName += "refFiles" + File.separator;
		fileName += "facs" + File.separator;
		fileName += "flsh2_1" + File.separator;
		fileName += "testRoomAlg.dia";

		try {
			dm1 = new DModel(_dxDocument1, fileName.toString());
			dm1.changeInDModel(new Object());

			DxAssignRoomsAlg alg = new DxAssignRoomsAlg(dm1, dxCL,
					DxFlags.increase);
			Cycle cycle = dm1.getTTStructure().getCurrentCycle();
			cycle.setCurrentDaySeqPerIndex(0, 0, 0);
			int periodStep = 1;
			int days = cycle.getNumberOfDays();
			DSetOfResources eventsInCat = new StandardCollection();
			Period currentPeriod = cycle.getNextPeriod(periodStep);
			DxSetOfResources listOfAvailableDxRooms = null;
			int d = 0;
			int h = 0;
			d = 0 / days;
			h = 0 % days;

			assertEquals("getEvents: number of events in period", 10,
					currentPeriod.getNumberOfEvents());
			if (alg._increase) {
				DSetOfResources eventsInPeriod = alg
						.builEventsWithStudentsInPeriod(currentPeriod);

				// for(int j = 0; j < eventsInPeriod.size(); j++) {
				DResource eventToAssign = eventsInPeriod.getResourceAt(0);
				DxEvent e = (DxEvent) eventToAssign.getAttach();
				assertEquals("getEvents: eventFullName ", "GMQ403", e
						.getfullName());
				assertEquals("getEvents: roomName ", "A6-2004", e.getRoomName());
				assertEquals("getEvents: students ", 35, Integer
						.parseInt(eventToAssign.getID()));

				eventToAssign = eventsInPeriod.getResourceAt(1);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("getEvents: eventFullName ", "GAE600", e
						.getfullName());
				assertEquals("getEvents: eventName ", "------", e.getRoomName());
				assertEquals("getEvents: students ", 35, Integer
						.parseInt(eventToAssign.getID()));

				eventToAssign = eventsInPeriod.getResourceAt(2);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("getEvents: eventFullName ", "HST489", e
						.getfullName());
				assertEquals("getEvents: eventName ", "------", e.getRoomName());
				assertEquals("getEvents: students ", 40, Integer
						.parseInt(eventToAssign.getID()));

				eventToAssign = eventsInPeriod.getResourceAt(3);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("getEvents: eventFullName ", "CRM103", e
						.getfullName());
				assertEquals("getEvents: eventName ", "------", e.getRoomName());
				assertEquals("getEvents: students ", 45, Integer
						.parseInt(eventToAssign.getID()));

				eventToAssign = eventsInPeriod.getResourceAt(4);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("getEvents: eventFullName ", "PSY521", e
						.getfullName());
				assertEquals("getEvents: eventName ", "------", e.getRoomName());
				assertEquals("getEvents: students ", 59, Integer
						.parseInt(eventToAssign.getID()));

				eventToAssign = eventsInPeriod.getResourceAt(5);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("getEvents: eventFullName ", "REL104", e
						.getfullName());
				assertEquals("getEvents: eventName ", "------", e.getRoomName());
				assertEquals("getEvents: students ", 60, Integer
						.parseInt(eventToAssign.getID()));

				eventToAssign = eventsInPeriod.getResourceAt(6);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("getEvents: eventFullName ", "ELC107", e
						.getfullName());
				assertEquals("getEvents: eventName ", "------", e.getRoomName());
				assertEquals("getEvents: students ", 60, Integer
						.parseInt(eventToAssign.getID()));

				eventToAssign = eventsInPeriod.getResourceAt(7);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("getEvents: eventFullName ", "SES405", e
						.getfullName());
				assertEquals("getEvents: eventName ", "------", e.getRoomName());
				assertEquals("getEvents: students ", 75, Integer
						.parseInt(eventToAssign.getID()));

				eventToAssign = eventsInPeriod.getResourceAt(8);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("getEvents: eventFullName ", "SES105", e
						.getfullName());
				assertEquals("getEvents: eventName ", "------", e.getRoomName());
				assertEquals("getEvents: students ", 75, Integer
						.parseInt(eventToAssign.getID()));

				eventToAssign = eventsInPeriod.getResourceAt(9);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("getEvents: eventFullName ", "PSY336", e
						.getfullName());
				assertEquals("getEvents: eventName ", "------", e.getRoomName());
				assertEquals("getEvents: students ", 80, Integer
						.parseInt(eventToAssign.getID()));

				listOfAvailableDxRooms = alg
						.buildListofAvailableDxRooms(eventsInPeriod);
				assertEquals("getEvents: availableRooms ", 28,
						listOfAvailableDxRooms.size());

				DxSetOfSites sos = dm1.getDxSetOfSites();
				String currentSiteName = dm1.getCurrentSiteName();
				DxSetOfCategories soc = sos.getSetOfCat(currentSiteName);
				DxCategory[] cArray = soc.getCatsSortedByKey();
				// for (int i = 0; i < cArray.length; i++){
				DxCategory cat = cArray[0];
				String sCatName = cat.getName();
				assertEquals("getEvents: catName ", "Séminaire", cat.getName());

				Vector<DxResource> roomsInCat = alg.getAvailableRoomsInCat(
						listOfAvailableDxRooms, sCatName, currentPeriod, d, h);
				assertEquals("getEvents: roomsInCat size ", 9, roomsInCat
						.size());
				eventsInCat = alg.getEventsWithRoomNamed(eventsInPeriod,
						sCatName);
				assertEquals("getEvents: eventsInCat size ", 0, eventsInCat
						.size());

				cat = cArray[1];
				sCatName = cat.getName();
				assertEquals("getEvents: catName ", "Multimédia", cat.getName());

				roomsInCat = alg.getAvailableRoomsInCat(listOfAvailableDxRooms,
						sCatName, currentPeriod, d, h);
				assertEquals("getEvents: roomsInCat size ", 6, roomsInCat
						.size());
				eventsInCat = alg.getEventsWithRoomNamed(eventsInPeriod,
						sCatName);
				assertEquals("getEvents: eventsInCat size ", 0, eventsInCat
						.size());

				cat = cArray[2];
				sCatName = cat.getName();
				assertEquals("getEvents: catName ", "Classe", cat.getName());

				roomsInCat = alg.getAvailableRoomsInCat(listOfAvailableDxRooms,
						sCatName, currentPeriod, d, h);
				assertEquals("getEvents: roomsInCat size ", 10, roomsInCat
						.size());
				eventsInCat = alg.getEventsWithRoomNamed(eventsInPeriod,
						sCatName);
				assertEquals("getEvents: eventsInCat size ", 0, eventsInCat
						.size());

				cat = cArray[3];
				sCatName = cat.getName();
				assertEquals("getEvents: catName ", "Lab", cat.getName());

				roomsInCat = alg.getAvailableRoomsInCat(listOfAvailableDxRooms,
						sCatName, currentPeriod, d, h);
				assertEquals("getEvents: roomsInCat size ", 4 - 1, roomsInCat
						.size());
				eventsInCat = alg.getEventsWithRoomNamed(eventsInPeriod,
						sCatName);
				assertEquals("getEvents: eventsInCat size ", 0, eventsInCat
						.size());

				DSetOfResources eventsToPlace = new StandardCollection();

				// eventsInPeriod =
				// alg.builEventsWithStudentsInPeriod(currentPeriod);
				assertEquals("getEvents: number of events in period", 10,
						currentPeriod.getNumberOfEvents());

				// listOfAvailableDxRooms = alg
				// .buildListofAvailableDxRooms(eventsInPeriod);

				assertEquals("getEvents: availableRooms ", 28,
						listOfAvailableDxRooms.size());

				//
				// Vector <DxResource> allRooms =
				// alg.getAvailableRooms(listOfAvailableDxRooms,
				// currentPeriod, d, h);

				Vector<DxResource> allRooms = alg.getAvailableRooms(
						listOfAvailableDxRooms, currentPeriod, d, h);

				assertEquals("getEvents: availableRooms ", 28, allRooms.size());

				alg.sortSetByCapacitymM(allRooms/* , listOfAvailableDxRooms */);
				assertEquals("getEvents: availableRooms ", 28, allRooms.size());

				eventsToPlace = alg.getEventsWithRoomNamed(eventsInPeriod,
						"------");
				assertEquals("getEvents: number of events in period", 9,
						eventsToPlace.size());
				Vector<DResource> eventsToUpdate = new Vector<DResource>();

				// while (eventsToPlace.size() > 0) {
				eventToAssign = eventsToPlace.getResourceAt(0);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("getEvents: eventFullName ", "GAE600", e
						.getfullName());
				assertEquals("getEvents: eventName ", "------", e.getRoomName());
				assertEquals("getEvents: students ", 35, Integer
						.parseInt(eventToAssign.getID()));

				eventsToUpdate.add(eventToAssign);
				eventsToPlace.removeResourceAt(0);
				Iterator it = allRooms.iterator();
				// while (it.hasNext()) {
				DxRoom room = (DxRoom) it.next();
				assertEquals("getEvents: roomsize ", 12, room.getCapacity());

				if (alg.isAddPossible(room, eventToAssign)) {
					((DxEvent) eventToAssign.getAttach()).setRoomName(room
							.getName());
					allRooms.remove(room);
					// break;
				} // if
				// } // while
				// } // while
			} else {
				DSetOfResources eventsInPeriod = alg
						.builEventsWithStudentsInPeriod(currentPeriod);

				// for(int j = 0; j < eventsInPeriod.size(); j++) {
				DResource eventToAssign = eventsInPeriod.getResourceAt(0);
				DxEvent e = (DxEvent) eventToAssign.getAttach();
				assertEquals("getEvents: eventFullName ", "PSY336", e
						.getfullName());
				assertEquals("getEvents: eventName ", "------", e.getRoomName());
				assertEquals("getEvents: students ", 80, Integer
						.parseInt(eventToAssign.getID()));
				
				eventToAssign = eventsInPeriod.getResourceAt(1);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("getEvents: eventFullName ", "SES105", e
						.getfullName());
				assertEquals("getEvents: eventName ", "------", e.getRoomName());
				assertEquals("getEvents: students ", 75, Integer
						.parseInt(eventToAssign.getID()));
				
				eventToAssign = eventsInPeriod.getResourceAt(2);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("getEvents: eventFullName ", "SES405", e
						.getfullName());
				assertEquals("getEvents: eventName ", "------", e.getRoomName());
				assertEquals("getEvents: students ", 75, Integer
						.parseInt(eventToAssign.getID()));
				
				eventToAssign = eventsInPeriod.getResourceAt(3);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("getEvents: eventFullName ", "ELC107", e
						.getfullName());
				assertEquals("getEvents: eventName ", "------", e.getRoomName());
				assertEquals("getEvents: students ", 60, Integer
						.parseInt(eventToAssign.getID()));
				
				eventToAssign = eventsInPeriod.getResourceAt(4);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("getEvents: eventFullName ", "REL104", e
						.getfullName());
				assertEquals("getEvents: eventName ", "------", e.getRoomName());
				assertEquals("getEvents: students ", 60, Integer
						.parseInt(eventToAssign.getID()));
				
				
				eventToAssign = eventsInPeriod.getResourceAt(5);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("getEvents: eventFullName ", "PSY521", e
						.getfullName());
				assertEquals("getEvents: eventName ", "------", e.getRoomName());
				assertEquals("getEvents: students ", 59, Integer
						.parseInt(eventToAssign.getID()));
				
				eventToAssign = eventsInPeriod.getResourceAt(6);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("getEvents: eventFullName ", "CRM103", e
						.getfullName());
				assertEquals("getEvents: eventName ", "------", e.getRoomName());
				assertEquals("getEvents: students ", 45, Integer
						.parseInt(eventToAssign.getID()));
				
				eventToAssign = eventsInPeriod.getResourceAt(7);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("getEvents: eventFullName ", "HST489", e
						.getfullName());
				assertEquals("getEvents: eventName ", "------", e.getRoomName());
				assertEquals("getEvents: students ", 40, Integer
						.parseInt(eventToAssign.getID()));
				
				eventToAssign = eventsInPeriod.getResourceAt(8);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("getEvents: eventFullName ", "GAE600", e
						.getfullName());
				assertEquals("getEvents: eventName ", "------", e.getRoomName());
				assertEquals("getEvents: students ", 35, Integer
						.parseInt(eventToAssign.getID()));

				eventToAssign = eventsInPeriod.getResourceAt(9);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("getEvents: eventFullName ", "GMQ403", e
						.getfullName());
				assertEquals("getEvents: roomName ", "A6-2004", e.getRoomName());
				assertEquals("getEvents: students ", 35, Integer
						.parseInt(eventToAssign.getID()));

//				eventToAssign = eventsInPeriod.getResourceAt(3);
//				e = (DxEvent) eventToAssign.getAttach();
//				assertEquals("getEvents: eventFullName ", "CRM103", e
//						.getfullName());
//				assertEquals("getEvents: eventName ", "------", e.getRoomName());
//				assertEquals("getEvents: students ", 45, Integer
//						.parseInt(eventToAssign.getID()));
//
//				eventToAssign = eventsInPeriod.getResourceAt(4);
//				e = (DxEvent) eventToAssign.getAttach();
//				assertEquals("getEvents: eventFullName ", "PSY521", e
//						.getfullName());
//				assertEquals("getEvents: eventName ", "------", e.getRoomName());
//				assertEquals("getEvents: students ", 59, Integer
//						.parseInt(eventToAssign.getID()));
//
//				eventToAssign = eventsInPeriod.getResourceAt(5);
//				e = (DxEvent) eventToAssign.getAttach();
//				assertEquals("getEvents: eventFullName ", "REL104", e
//						.getfullName());
//				assertEquals("getEvents: eventName ", "------", e.getRoomName());
//				assertEquals("getEvents: students ", 60, Integer
//						.parseInt(eventToAssign.getID()));
//
//				eventToAssign = eventsInPeriod.getResourceAt(6);
//				e = (DxEvent) eventToAssign.getAttach();
//				assertEquals("getEvents: eventFullName ", "ELC107", e
//						.getfullName());
//				assertEquals("getEvents: eventName ", "------", e.getRoomName());
//				assertEquals("getEvents: students ", 60, Integer
//						.parseInt(eventToAssign.getID()));
//
//				eventToAssign = eventsInPeriod.getResourceAt(7);
//				e = (DxEvent) eventToAssign.getAttach();
//				assertEquals("getEvents: eventFullName ", "SES405", e
//						.getfullName());
//				assertEquals("getEvents: eventName ", "------", e.getRoomName());
//				assertEquals("getEvents: students ", 75, Integer
//						.parseInt(eventToAssign.getID()));
//
//				eventToAssign = eventsInPeriod.getResourceAt(8);
//				e = (DxEvent) eventToAssign.getAttach();
//				assertEquals("getEvents: eventFullName ", "SES105", e
//						.getfullName());
//				assertEquals("getEvents: eventName ", "------", e.getRoomName());
//				assertEquals("getEvents: students ", 75, Integer
//						.parseInt(eventToAssign.getID()));
//
//				eventToAssign = eventsInPeriod.getResourceAt(9);
//				e = (DxEvent) eventToAssign.getAttach();
//				assertEquals("getEvents: eventFullName ", "PSY336", e
//						.getfullName());
//				assertEquals("getEvents: eventName ", "------", e.getRoomName());
//				assertEquals("getEvents: students ", 80, Integer
//						.parseInt(eventToAssign.getID()));

				listOfAvailableDxRooms = alg
						.buildListofAvailableDxRooms(eventsInPeriod);
				assertEquals("getEvents: availableRooms ", 28,
						listOfAvailableDxRooms.size());

				DxSetOfSites sos = dm1.getDxSetOfSites();
				String currentSiteName = dm1.getCurrentSiteName();
				DxSetOfCategories soc = sos.getSetOfCat(currentSiteName);
				DxCategory[] cArray = soc.getCatsSortedByKey();
				// for (int i = 0; i < cArray.length; i++){
				DxCategory cat = cArray[0];
				String sCatName = cat.getName();
				assertEquals("getEvents: catName ", "Séminaire", cat.getName());

				Vector<DxResource> roomsInCat = alg.getAvailableRoomsInCat(
						listOfAvailableDxRooms, sCatName, currentPeriod, d, h);
				assertEquals("getEvents: roomsInCat size ", 9, roomsInCat
						.size());
				eventsInCat = alg.getEventsWithRoomNamed(eventsInPeriod,
						sCatName);
				assertEquals("getEvents: eventsInCat size ", 0, eventsInCat
						.size());

				cat = cArray[1];
				sCatName = cat.getName();
				assertEquals("getEvents: catName ", "Multimédia", cat.getName());

				roomsInCat = alg.getAvailableRoomsInCat(listOfAvailableDxRooms,
						sCatName, currentPeriod, d, h);
				assertEquals("getEvents: roomsInCat size ", 6, roomsInCat
						.size());
				eventsInCat = alg.getEventsWithRoomNamed(eventsInPeriod,
						sCatName);
				assertEquals("getEvents: eventsInCat size ", 0, eventsInCat
						.size());

				cat = cArray[2];
				sCatName = cat.getName();
				assertEquals("getEvents: catName ", "Classe", cat.getName());

				roomsInCat = alg.getAvailableRoomsInCat(listOfAvailableDxRooms,
						sCatName, currentPeriod, d, h);
				assertEquals("getEvents: roomsInCat size ", 10, roomsInCat
						.size());
				eventsInCat = alg.getEventsWithRoomNamed(eventsInPeriod,
						sCatName);
				assertEquals("getEvents: eventsInCat size ", 0, eventsInCat
						.size());

				cat = cArray[3];
				sCatName = cat.getName();
				assertEquals("getEvents: catName ", "Lab", cat.getName());

				roomsInCat = alg.getAvailableRoomsInCat(listOfAvailableDxRooms,
						sCatName, currentPeriod, d, h);
				assertEquals("getEvents: roomsInCat size ", 4 - 1, roomsInCat
						.size());
				eventsInCat = alg.getEventsWithRoomNamed(eventsInPeriod,
						sCatName);
				assertEquals("getEvents: eventsInCat size ", 0, eventsInCat
						.size());

				DSetOfResources eventsToPlace = new StandardCollection();

				// eventsInPeriod =
				// alg.builEventsWithStudentsInPeriod(currentPeriod);
				assertEquals("getEvents: number of events in period", 10,
						currentPeriod.getNumberOfEvents());

				// listOfAvailableDxRooms = alg
				// .buildListofAvailableDxRooms(eventsInPeriod);

				assertEquals("getEvents: availableRooms ", 28,
						listOfAvailableDxRooms.size());

				//
				// Vector <DxResource> allRooms =
				// alg.getAvailableRooms(listOfAvailableDxRooms,
				// currentPeriod, d, h);

				Vector<DxResource> allRooms = alg.getAvailableRooms(
						listOfAvailableDxRooms, currentPeriod, d, h);

				assertEquals("getEvents: availableRooms ", 28, allRooms.size());

				alg.sortSetByCapacitymM(allRooms/* , listOfAvailableDxRooms */);
				assertEquals("getEvents: availableRooms ", 28, allRooms.size());

				eventsToPlace = alg.getEventsWithRoomNamed(eventsInPeriod,
						"------");
				assertEquals("getEvents: number of events in period", 9,
						eventsToPlace.size());
				Vector<DResource> eventsToUpdate = new Vector<DResource>();

				// while (eventsToPlace.size() > 0) {
				eventToAssign = eventsToPlace.getResourceAt(0);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("getEvents: eventFullName ", "PSY336", e
						.getfullName());
				assertEquals("getEvents: eventName ", "------", e.getRoomName());
				assertEquals("getEvents: students ", 80, Integer
						.parseInt(eventToAssign.getID()));

				eventsToUpdate.add(eventToAssign);
				eventsToPlace.removeResourceAt(0);
				Iterator it = allRooms.iterator();
				// while (it.hasNext()) {
				DxRoom room = (DxRoom) it.next();
				assertEquals("getEvents: roomsize ", 12, room.getCapacity());

				if (alg.isAddPossible(room, eventToAssign)) {
					((DxEvent) eventToAssign.getAttach()).setRoomName(room
							.getName());
					allRooms.remove(room);
					//break;
				} // if
				//} // while
				//} // while
			}
			dm1 = null;
		} catch (Exception e) {
			// Should not fail in controled conditions
			e.printStackTrace();
			System.out.println(e.getStackTrace());
		}

	}
} // end DxAssignRoomsAlgTest
