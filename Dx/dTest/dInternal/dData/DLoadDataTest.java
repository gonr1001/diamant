/**
 *
 * Title: DLoadDataTest  
 * Description: 	DLoadDataTest is a class used to test the class 
 * 				DLoadData
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
 *
 */
package dTest.dInternal.dData;

import java.io.File;
import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dConstants.DConst;
import dInternal.dData.DLoadData;
import dInternal.dData.dActivities.SetOfActivities;
import dInternal.dData.dActivities.SetOfActivitiesSites;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import dInternal.dData.dRooms.DxSetOfCategories;
import dInternal.dData.dRooms.DxSetOfRooms;
import dInternal.dData.dRooms.DxSetOfSites;
import dInternal.dData.dRooms.SetOfCategories;
import dInternal.dData.dRooms.SetOfRooms;
import dInternal.dData.dRooms.SetOfSites;
import dInternal.dData.dStudents.SetOfStuCourses;
import dInternal.dData.dStudents.SetOfStuSites;
import dInternal.dData.dStudents.SetOfStudents;
import dInternal.dData.dStudents.Student;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.TTStructure;

public class DLoadDataTest extends TestCase {

	private Vector _timeTable5j;

	private Vector _timeTable7j;

	private DLoadData _loadData5j;

	private DLoadData _loadData7j;

	public DLoadDataTest(String name) {
		super(name);

		String path5j = "." + File.separator + "dataTest" + File.separator
				+ "loadData5j.dia";
		_loadData5j = new DLoadData();
		try {
			_timeTable5j = _loadData5j.loadTheTT(path5j, "." + File.separator
					+ "dataTest");
		} catch (Exception e) {
			// Controled test should not fail
		}

		String path7j = "." + File.separator + "dataTest" + File.separator
				+ "loadData7j.dia";
		_loadData7j = new DLoadData();

		try {
			_timeTable7j = _loadData7j.loadTheTT(path7j, "." + File.separator
					+ "dataTest");
		} catch (Exception e) {
			// Controled test should not fail
		}
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DLoadDataTest.class);
	} // end suite

	/**
	 * test that check the version of timetable
	 */
	public void test_loadTimeTable5j() {
		assertEquals("test_loadTimeTable5j : assertEquals: ", "1.5",
				(String) _timeTable5j.get(0));
	}

	/**
	 * test that check the xml file
	 */
	public void test1_loadTimeTable5j() {
		Cycle cycle = ((TTStructure) _timeTable5j.get(1)).getCurrentCycle();
		assertEquals("test1_loadTimeTable5j : assertEquals1: ", 12, cycle
				.getMaxNumberOfPeriodsADay());
		assertEquals("test1_loadTimeTable5j : assertEquals2: ", 3, cycle
				.getMaxNumberOfSeqs());
		assertEquals("test1_loadTimeTable : assertEquals3: ", 5,
				TTStructure.NUMBEROFACTIVESDAYS);
	}

	/**
	 * test that check the setofactivities
	 */
	public void test2_loadTimeTable5j() {
		SetOfActivitiesSites setSite = ((SetOfActivitiesSites) _timeTable5j
				.get(4));
		assertEquals("test2_loadTimeTable5j : assertEquals: ", "SHE", setSite
				.getResourceAt(0).getID());

		// SetOfActivitiesSites setSite1 = (SetOfActivitiesSites)
		// setSite.getResourceAt(0).getAttach();
		SetOfActivities setAct = (SetOfActivities) setSite.getResourceAt(0)
				.getAttach();
		assertEquals("test2_1_loadTimeTable5j : assertEquals: ", "AMC640",
				setAct.getResourceAt(1).getID());
	}

	/**
	 * test that check the setofstudents
	 */
	public void test3_loadTimeTable5j() {
		SetOfStuSites setSite = ((SetOfStuSites) _timeTable5j.get(5));
		assertEquals("test3_loadTimeTable5j : assertEquals: ", "SHE", setSite
				.getResourceAt(0).getID());
		SetOfStudents setStud = (SetOfStudents) setSite.getResourceAt(0)
				.getAttach();
		assertEquals("test3_1_loadTimeTable5j : assertEquals: ", "BERNARD D",
				setStud.getResourceAt(1).getID());
	}

	/**
	 * test that check the setofinstructors
	 */
	public void test4_loadTimeTable5j() {

		DxSetOfInstructors setIns = ((DxSetOfInstructors) _timeTable5j.get(2));
		// Above, we get instructor at index 2, and here we get at 3. Reason
		// is that above is an index and bellow is a key, which start at 1
		assertEquals("test4_1_loadTimeTable5j : assertEquals: ",
				"THÉRIAULT, MICHÈLE", setIns.getInstructorName(3));

	}

	/**
	 * test that check the setofrooms
	 */
	public void test5_loadTimeTable5j() {
		if (!DConst.newRooms) {
			SetOfSites setSite = ((SetOfSites) _timeTable5j.get(3));
			assertEquals("test5_loadTimeTable : assertEquals: ", "SHE", setSite
					.getResourceAt(0).getID());
			SetOfCategories setCat = ((SetOfCategories) setSite
					.getResourceAt(0).getAttach());
			assertEquals("test5_1_loadTimeTable5j : assertEquals: ", "CAT 1",
					setCat.getResourceAt(0).getID());
			SetOfRooms setRooms = ((SetOfRooms) setCat.getResourceAt(0)
					.getAttach());
			assertEquals("test5_2_loadTimeTable5j : assertEquals: ", "D13016",
					setRooms.getResourceAt(4).getID());
		} else {
			DxSetOfSites setSite = ((DxSetOfSites) _timeTable5j.get(3));
			assertEquals("test5_loadTimeTable : assertEquals: ", "SHE", setSite
					.getSiteName(1));
			DxSetOfCategories setCat = setSite.getSetOfCat(1);
			assertEquals("test5_1_loadTimeTable5j : assertEquals: ", "CAT 1",
					setCat.getCatName(1));
			DxSetOfRooms setRooms =  setCat.getDxSetOfRooms(1);
			assertEquals("test5_2_loadTimeTable5j : assertEquals: ", "D13016",
					setRooms.getRoomName(5));
		}
	}

	/**
	 * test that check the selective import of Students
	 */
	public void test1_ImportSelective5j() {

		SetOfStuSites newStuSites = (SetOfStuSites) _loadData5j
				.selectiveImport(((SetOfStuSites) _timeTable5j.get(5)), "."
						+ File.separator + "dataTest" + File.separator
						+ "ImportSTUDS.SIG");
		assertEquals("test1_ImportSelective : assertEquals: ", "", newStuSites
				.getError());
		if (newStuSites.getError() == "") {
			// Set Of Student Sites. Check if Update is done
			SetOfStuSites setSite = ((SetOfStuSites) _timeTable5j.get(5));
			// Test of DLoadData.findAddedElements
			SetOfStudents setStud = (SetOfStudents) setSite.getResourceAt(0)
					.getAttach();
			assertNotNull(
					"test2_ImportSelective5j : assertNotNull: added student",
					setStud.getResource("NOUVEAUET"));

			// Test of DLoadData.findChangesInElements
			Student studentChanged = setStud.getStudent("GIRALDO-L");
			SetOfStuCourses currentCourses = studentChanged.getCoursesList();
			assertNull(
					"test3_ImportSelective5j : assertNull: Changed student ",
					currentCourses.getResource("GIN3252"));

			// Test of DLoadData.findDeletedElements
			assertNull(
					"test4_ImportSelective5j : assertNull: Deleted student ",
					setStud.getStudent("RHEAULT M"));

		}

	}

	/**
	 * test that check the version of timetable
	 */
	public void test_loadTimeTable7j() {
		assertEquals("test_loadTimeTable7j : assertEquals: ", "1.5",
				(String) _timeTable7j.get(0));
	}

	/**
	 * test that check the xml file
	 */
	public void test1_loadTimeTable7j() {
		Cycle cycle = ((TTStructure) _timeTable7j.get(1)).getCurrentCycle();
		assertEquals("test1_loadTimeTable7j : assertEquals1: ", 12, cycle
				.getMaxNumberOfPeriodsADay());
		assertEquals("test1_loadTimeTable7j : assertEquals2: ", 3, cycle
				.getMaxNumberOfSeqs());
		assertEquals("test1_loadTimeTable7j : assertEquals3: ", 5,
				TTStructure.NUMBEROFACTIVESDAYS);
	}

	/**
	 * test that check the setofactivities
	 */
	public void test2_loadTimeTable7j() {
		SetOfActivitiesSites setSite = ((SetOfActivitiesSites) _timeTable7j
				.get(4));
		assertEquals("test2_loadTimeTable7j : assertEquals: ", "SHE", setSite
				.getResourceAt(0).getID());
		SetOfActivities setAct = (SetOfActivities) setSite.getResourceAt(0)
				.getAttach();
		assertEquals("test2_1_loadTimeTable7j : assertEquals: ", "AMC640",
				setAct.getResourceAt(1).getID());
	}

	/**
	 * test that check the setofstudents
	 */
	public void test3_loadTimeTable7j() {
		SetOfStuSites setSite = ((SetOfStuSites) _timeTable7j.get(5));
		assertEquals("test3_loadTimeTable7j : assertEquals: ", "SHE", setSite
				.getResourceAt(0).getID());
		SetOfStudents setStud = (SetOfStudents) setSite.getResourceAt(0)
				.getAttach();
		assertEquals("test3_1_loadTimeTable7j : assertEquals: ", "BERNARD D",
				setStud.getResourceAt(1).getID());
	}

	/**
	 * test that check the setofinstructors
	 */
	public void test4_loadTimeTable7j() {

		DxSetOfInstructors setIns = ((DxSetOfInstructors) _timeTable5j.get(2));
		// Above, we get instructor at index 2, and here we get at 3. Reason
		// is that above is an index and bellow is a key, which start at 1
		assertEquals("test4_1_loadTimeTable7j : assertEquals: ",
				"THÉRIAULT, MICHÈLE", setIns.getInstructorName(3));

	}

	/**
	 * test that check the setofrooms
	 */
	public void test5_loadTimeTable7j() {
		if (!DConst.newRooms) {
		SetOfSites setSite = ((SetOfSites) _timeTable7j.get(3));
		assertEquals("test5_loadTimeTable7j : assertEquals: ", "SHE", setSite
				.getResourceAt(0).getID());
		SetOfCategories setCat = ((SetOfCategories) setSite.getResourceAt(0)
				.getAttach());
		assertEquals("test5_1_loadTimeTable7j : assertEquals: ", "CAT 1",
				setCat.getResourceAt(0).getID());
		SetOfRooms setRooms = ((SetOfRooms) setCat.getResourceAt(0).getAttach());
		assertEquals("test5_2_loadTimeTable7j : assertEquals: ", "D13016",
				setRooms.getResourceAt(4).getID());
		}else {
			DxSetOfSites setSite = ((DxSetOfSites) _timeTable7j.get(3));
			assertEquals("test7_loadTimeTable : assertEquals: ", "SHE", setSite
					.getSiteName(1));
			DxSetOfCategories setCat = setSite.getSetOfCat(1);
			assertEquals("test7_1_loadTimeTable5j : assertEquals: ", "CAT 1",
					setCat.getCatName(1));
			DxSetOfRooms setRooms =  setCat.getDxSetOfRooms(1);
			assertEquals("test7_2_loadTimeTable5j : assertEquals: ", "D13016",
					setRooms.getRoomName(5));
		}
	}

	/**
	 * test that check the selective import of Students
	 */
	public void test1_ImportSelective7j() {

		SetOfStuSites newStuSites = (SetOfStuSites) _loadData7j
				.selectiveImport(((SetOfStuSites) _timeTable7j.get(5)), "."
						+ File.separator + "dataTest" + File.separator
						+ "ImportSTUDS.SIG");
		assertEquals("test1_ImportSelective : assertEquals: ", "", newStuSites
				.getError());
		if (newStuSites.getError() == "") {
			// Set Of Student Sites. Check if Update is done
			SetOfStuSites setSite = ((SetOfStuSites) _timeTable7j.get(5));
			// Test of DLoadData.findAddedElements
			SetOfStudents setStud = (SetOfStudents) setSite.getResourceAt(0)
					.getAttach();
			assertNotNull(
					"test2_ImportSelective7j : assertNotNull: added student",
					setStud.getResource("NOUVEAUET"));

			// Test of DLoadData.findChangesInElements
			Student studentChanged = setStud.getStudent("GIRALDO-L");
			SetOfStuCourses currentCourses = studentChanged.getCoursesList();
			assertNull(
					"test3_ImportSelective7j : assertNull: Changed student ",
					currentCourses.getResource("GIN3252"));

			// Test of DLoadData.findDeletedElements
			assertNull(
					"test4_ImportSelective7j : assertNull: Deleted student ",
					setStud.getStudent("RHEAULT M"));

		}
	}

}