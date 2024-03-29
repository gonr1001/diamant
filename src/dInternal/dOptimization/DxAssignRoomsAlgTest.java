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
import dInternal.dData.DxResource;
import dInternal.dData.DxSetOfResources;
import dInternal.dData.StandardCollection;
import dInternal.dData.dRooms.DxCategory;
import dInternal.dData.dRooms.DxRoom;
import dInternal.dData.dRooms.DxSetOfCategories;
import dInternal.dData.dRooms.DxSetOfSites;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Period;

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

	private final String _pathForFiles = "." + File.separator + "dataTest"
			+ File.separator + "refFiles" + File.separator + "facs"
			+ File.separator + "tAlgorithmes" + File.separator;
	
	private final String SEMINAIRE = "SÉMINAIRE";
	private final String MULTIMEDIA = "MULTIMÉDIA";
	private final String CLASSE = "CLASSE";
	private final String LAB = "LAB";

	public DxAssignRoomsAlgTest(String name) {
		super(name);
	}

	/**
	 * 
	 * 
	 */
	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxAssignRoomsAlgTest.class);
	} // end suite

	/**
	 * 
	 * 
	 */
	public void test_basicData() {

//		String str = "conflictLimits;0;0;0;0;30;0;100;";

		boolean best = true;
		boolean increase = false;
		
		DModel dm1 = null;
		DxDocument _dxDocument1 = new DxTTableDoc();
		try {
			dm1 = new DModel(_dxDocument1, _pathForFiles + "RoomAffContTT.dia");
			dm1.changeInDModel(new Object());
			assertEquals("test_basicData: activities size", 94, dm1
					.getSetOfActivities().size());
			assertEquals("test_basicData: events size", 117, dm1
					.getSetOfEvents().size());
			DxAssignRoomsAlg alg = new DxAssignRoomsAlg(dm1, increase,
					best);
			alg.doWork();
			assertEquals("test_basicData: nbEventsToAssign", 116, dm1
					.getSetOfEvents().getNumberOfEventToAssign());
			dm1 = null;
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_basicData: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_basicData");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 */
	public void test_basicData2() {

//		String str = "conflictLimits;0;0;0;0;30;0;100;";
		boolean best = true;
		boolean increase = false;
		DModel dm1 = null;
		DxDocument _dxDocument1 = new DxTTableDoc();

		try {
			dm1 = new DModel(_dxDocument1, _pathForFiles
					+ "Aut2006flsh170m.dia");
			dm1.changeInDModel(new Object());
			assertEquals("test_basicData2: activities size", 199, dm1
					.getSetOfActivities().size());
			assertEquals("test_basicData2: events size", 253, dm1
					.getSetOfEvents().size());
//			DxAssignRoomsAlg alg = new DxAssignRoomsAlg(dm1, dxCL, _increase,
//					_best);
			DxAssignRoomsAlg alg = new DxAssignRoomsAlg(dm1, increase,
					best);
			alg.doWork();
			assertEquals("test_basicData2: nbEventsToAssign", 250, dm1
					.getSetOfEvents().getNumberOfEventToAssign());
			dm1 = null;
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_basicData: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_basicData2");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 */
	public void test_getNumberOfPeriods() {

//		String str = "conflictLimits;0;0;0;0;30;0;100;";

		boolean best = true;
		boolean increase = false;
		DModel dm1 = null;
		DxDocument _dxDocument1 = new DxTTableDoc();

		try {
			dm1 = new DModel(_dxDocument1, _pathForFiles
					+ "RoomAffTestsFlsh170min.dia");
			dm1.changeInDModel(new Object());
			DxAssignRoomsAlg alg = new DxAssignRoomsAlg(dm1,  increase,
					best);
			assertEquals("test_getNumberOfPeriods: nb Periods", 28, alg
					.getNumberOfPeriods());
			dm1 = null;
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_basicData: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_basicData2");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 */
	public void test_events() {
	
//		String str = "conflictLimits;0;0;0;0;30;0;100;";

		boolean best = true;
		boolean increase = false;
		DModel dm = null;
		DxDocument _dxDocument1 = new DxTTableDoc();

		try {
			dm = new DModel(_dxDocument1, _pathForFiles
					+ "RoomAffTestsFlsh170min.dia");
			dm.changeInDModel(new Object());
			
			DxAssignRoomsAlg alg = new DxAssignRoomsAlg(dm,  increase,
					best);
			Cycle cycle = dm.getTTStructure().getCurrentCycle();
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
				assertEquals("test_events: number of events in period", 3,
						currentPeriod.getNumberOfEvents());
				DSetOfResources eventsInPeriod = alg
						.builEventsWithStudentsInPeriod(currentPeriod);
				// for(int j = 0; j < eventsInPeriod.size(); j++) {
				DResource eventToAssign = eventsInPeriod.getResourceAt(0);
				DxEvent e = (DxEvent) eventToAssign.getAttach();
				assertEquals("test_events: eventFullName ", "FRE060", e
						.getfullName());
				assertEquals("test_events: eventName ", CLASSE, e.getRoomName());

				eventToAssign = eventsInPeriod.getResourceAt(1);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("test_events: eventFullName ", "FLS060", e
						.getfullName());
				assertEquals("test_events: eventName ", "------", e
						.getRoomName());

				eventToAssign = eventsInPeriod.getResourceAt(2);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("test_events: eventFullName ", "GEO550", e
						.getfullName());
				assertEquals("test_events: eventName ", "A4-262", e
						.getRoomName());
				// }
				DxSetOfSites sos = dm.getDxSetOfSites();
				String currentSiteName = dm.getCurrentSiteName();
				DxSetOfCategories soc = sos.getSetOfCat(currentSiteName);
				DxCategory[] cArray = soc.getCatsSortedByKey();
				// for (int i = 0; i < cArray.length; i++){
				DxCategory cat = cArray[0];
				String sCatName = cat.getName().toUpperCase();
				assertEquals("test_events: catName ", "CLASSE", cat.getName());

				listOfAvailableDxRooms = alg
						.buildListofAvailableDxRooms(eventsInPeriod);
				assertEquals("test_events: listOfAvailableDxRooms size ", 28,
						listOfAvailableDxRooms.size());

				Vector<DxResource> roomsInCat = alg.getAvailableRoomsInCat(
						listOfAvailableDxRooms, sCatName, currentPeriod, d, h);
				assertEquals("test_events: roomsInCat size ", 6, roomsInCat
						.size());
				eventsInCat = alg.getEventsWithRoomNamed(eventsInPeriod,
						sCatName);
				assertEquals("test_events: eventsInCat size ", 1, eventsInCat
						.size());
			} else {
				assertEquals("test_events: number of events in period", 3,
						currentPeriod.getNumberOfEvents());
				DSetOfResources eventsInPeriod = alg
						.builEventsWithStudentsInPeriod(currentPeriod);
				// for(int j = 0; j < eventsInPeriod.size(); j++) {
				DResource eventToAssign = eventsInPeriod.getResourceAt(0);
				DxEvent e = (DxEvent) eventToAssign.getAttach();
				assertEquals("test_events: eventFullName ", "GEO550", e
						.getfullName());
				assertEquals("test_events: eventName ", "A4-262", e
						.getRoomName());

				eventToAssign = eventsInPeriod.getResourceAt(1);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("test_events: eventFullName ", "FLS060", e
						.getfullName());
				assertEquals("test_events: eventName ", "------", e
						.getRoomName());

				eventToAssign = eventsInPeriod.getResourceAt(2);
				e = (DxEvent) eventToAssign.getAttach();
				assertEquals("test_events: eventFullName ", "FRE060", e
						.getfullName());
				assertEquals("test_events: eventName ", "CLASSE", e
						.getRoomName());
				// }
				DxSetOfSites sos = dm.getDxSetOfSites();
				String currentSiteName = dm.getCurrentSiteName();
				DxSetOfCategories soc = sos.getSetOfCat(currentSiteName);
				DxCategory[] cArray = soc.getCatsSortedByKey();
				// for (int i = 0; i < cArray.length; i++){
				DxCategory cat = cArray[0];
				String sCatName = cat.getName();
				assertEquals("test_events: catName ", "CLASSE", cat.getName());

				listOfAvailableDxRooms = alg
						.buildListofAvailableDxRooms(eventsInPeriod);
				assertEquals("test_events: listOfAvailableDxRooms size ", 28,
						listOfAvailableDxRooms.size());

				Vector<DxResource> roomsInCat = alg.getAvailableRoomsInCat(
						listOfAvailableDxRooms, sCatName, currentPeriod, d, h);
				assertEquals("test_events: roomsInCat size ", 6, roomsInCat
						.size());
				eventsInCat = alg.getEventsWithRoomNamed(eventsInPeriod,
						sCatName);
				assertEquals("test_events: eventsInCat size ", 1, eventsInCat
						.size());
			}

			dm = null;
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_basicData: exception", "nullPointer", e
					.toString());
			System.out.println(e.getStackTrace());
		}
	}

	

	public void test_sort() {

//		String str = "conflictLimits;0;0;0;0;30;0;100;";

		DxAssignRoomsAlg alg = new DxAssignRoomsAlg(null,  true, true);
		Vector<DxResource> v = new Vector<DxResource>();
		DxRoom dr = new DxRoom("C1-310", 10, 0, null, "", null);
		v.add(dr);

		alg.sortSetByCapacitymM(v);
		assertEquals("test_Sort size mM ", 1, v.size());
		alg.sortSetByCapacityMm(v);
		assertEquals("test_Sort size Mn ", 1, v.size());
		dr = new DxRoom("C1-311", 11, 0, null, "", null);
		v.add(dr);
		dr = new DxRoom("C1-3110", 110, 0, null, "", null);
		v.add(dr);
		dr = new DxRoom("C1-3111", 111, 0, null, "", null);
		v.add(dr);
		dr = new DxRoom("C1-3136", 136, 0, null, "", null);
		v.add(dr);
		alg.sortSetByCapacitymM(v);
		assertEquals("test_Sort size mM 5 ", 5, v.size());
		dr = (DxRoom) v.elementAt(0);
		assertEquals("test_Sort element 0", 10, dr.getCapacity());
		dr = (DxRoom) v.elementAt(4);
		assertEquals("test_Sort element 4", 136, dr.getCapacity());

		alg.sortSetByCapacityMm(v);
		assertEquals("test_Sort size Mm 5 ", 5, v.size());
		dr = (DxRoom) v.elementAt(0);
		assertEquals("test_Sort element 0", 136, dr.getCapacity());
		dr = (DxRoom) v.elementAt(4);
		assertEquals("test_Sort ", 10, dr.getCapacity());
		alg.sortSetByCapacityMm(v);
	}
	
	public void test_getEvents() {

		/* attention
		 * 
		 * this test produces an Out of memory if the space memory is 512 Mbytes
		 * and the test is is done with the configuration of all tests in project
		 * and DxAssignRoomAlgTest is called several times
		 * the Out of Memory arrives in dData.Students ... DSetOfRessources.addResource
		 * 
		 */

		boolean best = true;
		boolean increase = false;
		DModel dm = null;
		DxDocument _dxDocument1 = new DxTTableDoc();

		try {
			dm = new DModel(_dxDocument1, _pathForFiles + "testRoomAlg.dia");
			dm.changeInDModel(new Object());
			
			DxAssignRoomsAlg alg = new DxAssignRoomsAlg(dm, increase,
					best);
			Cycle cycle = dm.getTTStructure().getCurrentCycle();
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

				DxSetOfSites sos = dm.getDxSetOfSites();
				String currentSiteName = dm.getCurrentSiteName();
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

				assertEquals("getEvents: number of events in period", 10,
						currentPeriod.getNumberOfEvents());

				assertEquals("getEvents: availableRooms ", 28,
						listOfAvailableDxRooms.size());

				Vector<DxResource> allRooms = alg.getAvailableRooms(
						listOfAvailableDxRooms, currentPeriod, d, h);

				assertEquals("getEvents: availableRooms ", 28, allRooms.size());

				alg.sortSetByCapacitymM(allRooms/*
												 * , listOfAvailableDxRooms
												 */);
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
				Iterator<DxResource> it = allRooms.iterator();
				
				DxRoom room = (DxRoom) it.next();
				assertEquals("getEvents: roomsize ", 12, room.getCapacity());

				if (alg.fitIn(eventToAssign, room)) {
					((DxEvent) eventToAssign.getAttach()).setRoomName(room
							.getName());
					allRooms.remove(room);
				} // if
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

				listOfAvailableDxRooms = alg
						.buildListofAvailableDxRooms(eventsInPeriod);
				assertEquals("getEvents: availableRooms ", 28,
						listOfAvailableDxRooms.size());

				DxSetOfSites sos = dm.getDxSetOfSites();
				String currentSiteName = dm.getCurrentSiteName();
				DxSetOfCategories soc = sos.getSetOfCat(currentSiteName);
				DxCategory[] cArray = soc.getCatsSortedByKey();
				// for (int i = 0; i < cArray.length; i++){
				DxCategory cat = cArray[0];
				String sCatName = cat.getName();
				assertEquals("getEvents: catName ", SEMINAIRE, cat.getName());

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
				assertEquals("getEvents: catName ", MULTIMEDIA, cat.getName());

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
				assertEquals("getEvents: catName ", CLASSE, cat.getName());

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
				assertEquals("getEvents: catName ", LAB, cat.getName());

				roomsInCat = alg.getAvailableRoomsInCat(listOfAvailableDxRooms,
						sCatName, currentPeriod, d, h);
				assertEquals("getEvents: roomsInCat size ", 4 - 1, roomsInCat
						.size());
				eventsInCat = alg.getEventsWithRoomNamed(eventsInPeriod,
						sCatName);
				assertEquals("getEvents: eventsInCat size ", 0, eventsInCat
						.size());

				DSetOfResources eventsToPlace = new StandardCollection();

				assertEquals("getEvents: number of events in period", 10,
						currentPeriod.getNumberOfEvents());

				assertEquals("getEvents: availableRooms ", 28,
						listOfAvailableDxRooms.size());

				Vector<DxResource> allRooms = alg.getAvailableRooms(
						listOfAvailableDxRooms, currentPeriod, d, h);

				assertEquals("getEvents: availableRooms ", 28, allRooms.size());

				alg.sortSetByCapacitymM(allRooms/*
												 * , listOfAvailableDxRooms
												 */);
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
				Iterator<DxResource> it = allRooms.iterator();
				// while (it.hasNext()) {
				DxRoom room = (DxRoom) it.next();
				assertEquals("getEvents: roomsize ", 12, room.getCapacity());

				if (alg.fitIn(eventToAssign, room)) {
					((DxEvent) eventToAssign.getAttach()).setRoomName(room
							.getName());
					allRooms.remove(room);
					// break;
				} // if
				// } // while
				// } // while
			}
			dm = null;
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_basicData: exception", "nullPointer", e
					.toString());
			e.printStackTrace();
			System.out.println(e.getStackTrace());
		}
	}

} // end DxAssignRoomsAlgTest
