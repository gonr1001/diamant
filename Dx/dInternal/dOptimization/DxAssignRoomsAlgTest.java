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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dDeveloper.DxFlags;
import dInterface.DxDocument;
import dInterface.DxTTableDoc;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DxConflictLimits;
import dInternal.dData.DxSetOfResources;
import dInternal.dData.StandardCollection;
import dInternal.dData.dRooms.DxCategory;
import dInternal.dData.dRooms.DxSetOfCategories;
import dInternal.dData.dRooms.DxSetOfRooms;
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
				DxAssignRoomsAlg alg = new DxAssignRoomsAlg(dm1, dxCL);
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
				DxAssignRoomsAlg alg = new DxAssignRoomsAlg(dm1, dxCL);
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

			DxAssignRoomsAlg alg = new DxAssignRoomsAlg(dm1, dxCL);

			assertEquals("test_getNumberOfPeriods: assertEquals", 28, alg
					.getNumberOfPeriods());
			dm1 = null;
		} catch (Exception e) {
			// Should not fail in controled conditions
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
		fileName += "RoomAffTestsFlsh170min.dia";

		try {
			dm1 = new DModel(_dxDocument1, fileName.toString());
			dm1.changeInDModel(new Object());

			DxAssignRoomsAlg alg = new DxAssignRoomsAlg(dm1, dxCL);
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

			assertEquals("getEvents: number of events in period", 3,
					currentPeriod.getNumberOfEvents());
			DSetOfResources eventsInPeriod = alg
					.builEventsWithStudentsInPeriod(currentPeriod);
			// for(int j = 0; j < eventsInPeriod.size(); j++) {
			DResource eventToAssign = eventsInPeriod.getResourceAt(0);
			DxEvent e = (DxEvent) eventToAssign.getAttach();
			assertEquals("getEvents: eventFullName ", "FRE060", e.getfullName());
			assertEquals("getEvents: eventName ", "Classe", e.getRoomName());

			eventToAssign = eventsInPeriod.getResourceAt(1);
			e = (DxEvent) eventToAssign.getAttach();
			assertEquals("getEvents: eventFullName ", "FLS060", e.getfullName());
			assertEquals("getEvents: eventName ", "------", e.getRoomName());

			eventToAssign = eventsInPeriod.getResourceAt(2);
			e = (DxEvent) eventToAssign.getAttach();
			assertEquals("getEvents: eventFullName ", "GEO550", e.getfullName());
			assertEquals("getEvents: eventName ", "A4-262", e.getRoomName());
			// }
			DxSetOfSites sos = dm1.getDxSetOfSites();
			String currentSiteName = dm1.getCurrentSiteName();
			DxSetOfCategories soc = sos.getSetOfCat(currentSiteName);
			DxCategory[] cArray = soc.getCatsSortedByKey();
			// for (int i = 0; i < cArray.length; i++){
			DxCategory cat = cArray[0];
			String sCatName = cat.getName();
			assertEquals("getEvents: catName ", "Classe", cat.getName());

			listOfAvailableDxRooms = alg
					.buildListofAvailableDxRooms(eventsInPeriod);
			assertEquals("getEvents: listOfAvailableDxRooms size ", 28,
					listOfAvailableDxRooms.size());

			DxSetOfRooms roomsInCat = alg.getAvailableRoomsInCat(
					listOfAvailableDxRooms, sCatName, currentPeriod, d, h);
			assertEquals("getEvents: roomsInCat size ", 6, roomsInCat
					.size());
			eventsInCat = alg.getEventsInCat(eventsInPeriod, sCatName);
			assertEquals("getEvents: eventsInCat size ", 1, eventsInCat
					.size());
			dm1 = null;
		} catch (Exception e) {
			// Should not fail in controled conditions
			System.out.println(e.getStackTrace());
		}

	}
} // end DxAssignRoomsAlgTest
