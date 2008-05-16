/**
 * Created on 29-November-07
 * 
 * 
 * Title: DxLoadDataTest.java
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

package dTest.dInternal;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import developer.DxFlags;
import dInternal.DxLoadData;
import dInternal.dData.dActivities.DxActivitySite;
import dInternal.dData.dActivities.DxSetOfActivitiesSites;
import dInternal.dData.dActivities.SetOfActivities;
import dInternal.dData.dActivities.SetOfActivitiesSites;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import dInternal.dData.dRooms.DxCategory;
import dInternal.dData.dRooms.DxRoom;
import dInternal.dData.dRooms.DxSetOfSites;
import dInternal.dData.dRooms.DxSite;
import dInternal.dData.dStudents.SetOfStuCourses;
import dInternal.dData.dStudents.SetOfStuSites;
import dInternal.dData.dStudents.SetOfStudents;
import dInternal.dData.dStudents.Student;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.TTStructure;

public class DxLoadDataTest extends TestCase {

	private final String _pathForFiles = "." + File.separator + "dataTest"
			+ File.separator;

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxLoadDataTest.class);
	} // end suite

	/**
	 * test that check different attributes in DxLoadData
	 */
	public void test_loadTimeTable5j() {

		DxLoadData loadData5j = new DxLoadData();
		try {
			boolean timeTable5j = loadData5j.loadDataStructures(_pathForFiles
					+ "loadData5j.dia", _pathForFiles);
			assertEquals("test_loadTimeTable5j : loadDataOk: ", true,
					timeTable5j);

			assertEquals("test_loadTimeTable5j : getVersion: ", "1.5",
					loadData5j.getVersion());
			// test that check the xml file
			Cycle cycle = loadData5j.getTTStructure().getCurrentCycle();
			assertEquals("test_loadTimeTable5j : getMaxNumberOfPeriodsADay: ",
					12, cycle.getMaxNumberOfPeriodsADay());
			assertEquals("test_loadTimeTable5j : getMaxNumberOfSeqs: ", 3,
					cycle.getMaxNumberOfSeqs());
			assertEquals("test_loadTimeTable5j : active days: ", 5,
					TTStructure.NUMBEROFACTIVESDAYS);

			// test that check the set of activities
			if (DxFlags.newActivity) {
				DxSetOfActivitiesSites dxsoasSet = (DxSetOfActivitiesSites) loadData5j
						.getDxActivitiesSitesReader();
				DxActivitySite dxasSite = dxsoasSet.getActivitySite("SHE");
				assertNotNull("test_loadTimeTable5j: setOfActivities notNull",
						dxasSite);
				assertNotNull("test_loadTimeTable5j: getActivity", dxasSite
						.getActivity("AMC640"));
			} else {
				SetOfActivitiesSites setSite = loadData5j
						.getSetOfActivitiesSites();
				assertEquals("test_loadTimeTable5j : getSiteId: ", "SHE",
						setSite.getResourceAt(0).getID());

				SetOfActivities setAct = (SetOfActivities) setSite
						.getResourceAt(0).getAttach();
				assertEquals("test_loadTimeTable5j : getActivityId: ",
						"AMC640", setAct.getResourceAt(1).getID());
			}
			// test that check the set of students
			SetOfStuSites setSite = loadData5j.getSetofStuSites();
			assertEquals("test_loadTimeTable5j : getSiteId: ", "SHE", setSite
					.getResourceAt(0).getID());
			SetOfStudents setStud = (SetOfStudents) setSite.getResourceAt(0)
					.getAttach();
			assertEquals("test_loadTimeTable5j : getStudentId: ", "BERNARD D",
					setStud.getResourceAt(1).getID());

			// test that check the set of instructors
			DxSetOfInstructors setIns = loadData5j.getDxSetOfInstructors();
			// Above, we get instructor at index 2, and here we get at 3. Reason
			// is that above is an index and bellow is a key, which start at 1
			assertNotNull("test_loadTimeTable5j : instructor NotNull: ", setIns
					.getInstructor("THÉRIAULT, MICHÈLE"));
			assertEquals("test_loadTimeTable5j : InstructorGetName: ",
					"THÉRIAULT, MICHÈLE", setIns.getInstructor(
							"THÉRIAULT, MICHÈLE").getName());

			// test that check the set of rooms
			DxSetOfSites dxsosSetSites = loadData5j.getDxSetOfSitesRooms();
			DxSite dxsSite = dxsosSetSites.getSite("SHE");
			assertNotNull("test_loadTimeTable5j : SiteNotNull: ", dxsSite);
			DxCategory dxcCat1 = dxsSite.getCat("CAT 1");
			assertNotNull("test_loadTimeTable5j : CategoryNotNull: ", dxcCat1);
			DxRoom dxrRoom = dxcCat1.getRoom("D13016");
			assertNotNull("test_loadTimeTable5j : RoomNotNull: ", dxrRoom);

		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_loadTimeTable5j: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_loadTimeTable5j");
			e.printStackTrace();
		}
	}

	/**
	 * test that check the selective import of Students
	 */
	public void test_SelectiveImport5j() {
		DxLoadData loadData5j = new DxLoadData();
		try {

			boolean timeTable5j = loadData5j.loadDataStructures(_pathForFiles
					+ "loadData5j.dia", _pathForFiles);
			assertEquals("test_SelectiveImport5j : loadDataOk: ", true,
					timeTable5j);
			SetOfStuSites newStuSites = (SetOfStuSites) loadData5j
					.selectiveImport(loadData5j.getSetofStuSites(),
							_pathForFiles + "ImportSTUDS.SIG");
			assertEquals("test_SelectiveImport5j : assertEquals: ", "",
					newStuSites.getError());
			if (newStuSites.getError() == "") {
				// Set Of Student Sites. Check if Update is done
				SetOfStuSites setSite = loadData5j.getSetofStuSites();
				// Test of DLoadData.findAddedElements
				SetOfStudents setStud = (SetOfStudents) setSite
						.getResourceAt(0).getAttach();
				assertNotNull(
						"test_SelectiveImport5j : assertNotNull: added student",
						setStud.getResource("NOUVEAUET"));

				// Test of DLoadData.findChangesInElements
				Student studentChanged = setStud.getStudent("GIRALDO-L");
				SetOfStuCourses currentCourses = studentChanged
						.getCoursesList();
				assertNull(
						"test_SelectiveImport5j : assertNull: Changed student ",
						currentCourses.getResource("GIN3252"));

				// Test of DLoadData.findDeletedElements
				assertNull(
						"test_SelectiveImport5j : assertNull: Deleted student ",
						setStud.getStudent("RHEAULT M"));
			}
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_SelectiveImport5j: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_SelectiveImport5j");
			e.printStackTrace();
		}
	}

	/**
	 * test that check the version of timetable
	 */
	public void test_loadTimeTable7j() {
		DxLoadData loadData7j = new DxLoadData();

		try {
			boolean timeTable7j = loadData7j.loadDataStructures(_pathForFiles
					+ "loadData7j.dia", _pathForFiles);
			assertEquals("test_loadTimeTable7j : loadDataOk: ", true,
					timeTable7j);
			assertEquals("test_loadTimeTable7j : assertEquals: ", "1.5",
					loadData7j.getVersion());

			// test that check the xml file
			Cycle cycle = loadData7j.getTTStructure().getCurrentCycle();
			assertEquals("test1_loadTimeTable5j : getMaxNumberOfPeriodsADay: ",
					12, cycle.getMaxNumberOfPeriodsADay());
			assertEquals("test1_loadTimeTable5j : getMaxNumberOfSeqs: ", 3,
					cycle.getMaxNumberOfSeqs());
			assertEquals("test1_loadTimeTable : active days: ", 5,
					TTStructure.NUMBEROFACTIVESDAYS);

			// test that check the set of activities

			// test that check the set of activities
			if (DxFlags.newActivity) {
				DxSetOfActivitiesSites dxsoasSet = (DxSetOfActivitiesSites) loadData7j
						.getDxActivitiesSitesReader();
				DxActivitySite dxasSite = dxsoasSet.getActivitySite("SHE");
				assertNotNull("test2_loadTimeTable5j", dxasSite);
				assertNotNull("test2_1_loadTimeTable5j", dxasSite
						.getActivity("AMC640"));
			} else {
				SetOfActivitiesSites setSite = loadData7j
						.getSetOfActivitiesSites();
				assertEquals("test2_2_loadTimeTable5j : assertEquals: ", "SHE",
						setSite.getResourceAt(0).getID());

				SetOfActivities setAct = (SetOfActivities) setSite
						.getResourceAt(0).getAttach();
				assertEquals("test2_3_loadTimeTable5j : assertEquals: ",
						"AMC640", setAct.getResourceAt(1).getID());
			}

			// test that check the set of students
			SetOfStuSites setSite = loadData7j.getSetofStuSites();
			assertEquals("test3_loadTimeTable5j : assertEquals: ", "SHE",
					setSite.getResourceAt(0).getID());
			SetOfStudents setStud = (SetOfStudents) setSite.getResourceAt(0)
					.getAttach();
			assertEquals("test3_1_loadTimeTable5j : assertEquals: ",
					"BERNARD D", setStud.getResourceAt(1).getID());

			// test that check the set of instructors
			DxSetOfInstructors setIns = loadData7j.getDxSetOfInstructors();
			// Above, we get instructor at index 2, and here we get at 3. Reason
			// is that above is an index and bellow is a key, which start at 1
			assertNotNull("test4_1_loadTimeTable5j : assertNotNull: ", setIns
					.getInstructor("THÉRIAULT, MICHÈLE"));
			assertEquals("test4_1_loadTimeTable5j : assertEquals: ",
					"THÉRIAULT, MICHÈLE", setIns.getInstructor(
							"THÉRIAULT, MICHÈLE").getName());

			// test that check the set of rooms
			DxSetOfSites dxsosSetSites = loadData7j.getDxSetOfSitesRooms();
			DxSite dxsSite = dxsosSetSites.getSite("SHE");
			assertNotNull("test5_loadTimeTable : assertNotNull: ", dxsSite);
			DxCategory dxcCat1 = dxsSite.getCat("CAT 1");
			assertNotNull("test5_1_loadTimeTable5j : assertNotNull: ", dxcCat1);
			DxRoom dxrRoom = dxcCat1.getRoom("D13016");
			assertNotNull("test5_2_loadTimeTable5j : assertNotNull: ", dxrRoom);
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_loadTimeTable7j: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_loadTimeTable7j");
			e.printStackTrace();
		}

	}

	/**
	 * test that check the selective import of Students
	 */
	public void test1_ImportSelective7j() {
		DxLoadData loadData7j = new DxLoadData();
		try {
			boolean timeTable7j = loadData7j.loadDataStructures(_pathForFiles
					+ "loadData7j.dia", _pathForFiles);
			assertEquals("test_SelectiveImport7j : loadDataOk: ", true,
					timeTable7j);
			SetOfStuSites newStuSites = (SetOfStuSites) loadData7j
					.selectiveImport(loadData7j.getSetofStuSites(), "."
							+ File.separator + "dataTest" + File.separator
							+ "ImportSTUDS.SIG");
			assertEquals("test1_ImportSelective : assertEquals: ", "",
					newStuSites.getError());
			if (newStuSites.getError() == "") {
				// Set Of Student Sites. Check if Update is done
				SetOfStuSites setSite = loadData7j.getSetofStuSites();
				// Test of DLoadData.findAddedElements
				SetOfStudents setStud = (SetOfStudents) setSite
						.getResourceAt(0).getAttach();
				assertNotNull(
						"test2_ImportSelective7j : assertNotNull: added student",
						setStud.getResource("NOUVEAUET"));

				// Test of DLoadData.findChangesInElements
				Student studentChanged = setStud.getStudent("GIRALDO-L");
				SetOfStuCourses currentCourses = studentChanged
						.getCoursesList();
				assertNull(
						"test3_ImportSelective7j : assertNull: Changed student ",
						currentCourses.getResource("GIN3252"));

				// Test of DLoadData.findDeletedElements
				assertNull(
						"test4_ImportSelective7j : assertNull: Deleted student ",
						setStud.getStudent("RHEAULT M"));
			}
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test1_ImportSelective7j: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test1_ImportSelective7j");
			e.printStackTrace();
		}
	}

}