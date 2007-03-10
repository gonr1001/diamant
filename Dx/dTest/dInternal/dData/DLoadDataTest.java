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
import dDeveloper.DxFlags;
import dInternal.dData.DLoadData;
import dInternal.dData.dActivities.DxActivitySite;
import dInternal.dData.dActivities.DxSetOfActivitiesSites;
import dInternal.dData.dActivities.SetOfActivities;
import dInternal.dData.dActivities.SetOfActivitiesSites;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import dInternal.dData.dRooms.DxCategory;
import dInternal.dData.dRooms.DxRoom;
import dInternal.dData.dRooms.DxSetOfSites;
import dInternal.dData.dRooms.DxSite;
import dInternal.dData.dRooms.SetOfCategories;
import dInternal.dData.dRooms.SetOfRooms;
import dInternal.dData.dRooms.SetOfSites;
import dInternal.dData.dStudents.SetOfStuCourses;
import dInternal.dData.dStudents.SetOfStuSites;
import dInternal.dData.dStudents.SetOfStudents;
import dInternal.dData.dStudents.Student;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.TTStructure;
import eLib.exit.exception.DxException;

public class DLoadDataTest extends TestCase {

//	private DLoadData _timeTable5j;
//
//	private Vector _timeTable7j;

	private DLoadData _loadData5j;

	private DLoadData _loadData7j;

	public DLoadDataTest(String name) {
		super(name);

		String path5j = "." + File.separator + "dataTest" + File.separator
				+ "loadData5j.dia";
		_loadData5j = new DLoadData();
		try {
			boolean timeTable5j = _loadData5j.loadTheTT(path5j, "." + File.separator
					+ "dataTest");
			assertEquals("test_loadTimeTable5j : loadDataOk: ", true,
					timeTable5j);
		} catch (Exception e) {
			System.out.println("DLoadDataTest failed to load loadData5j.dia");
		}

		String path7j = "." + File.separator + "dataTest" + File.separator
				+ "loadData7j.dia";
		_loadData7j = new DLoadData();

		try {
			boolean timeTable7j = _loadData7j.loadTheTT(path7j, "." + File.separator
					+ "dataTest");
			assertEquals("test_loadTimeTable7j : loadDataOk: ", true,
					timeTable7j);
		} catch (Exception e) {
			System.out.println("DLoadDataTest failed to load loadData7j.dia");
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
		assertEquals("test_loadTimeTable5j : getVersion: ", "1.5",
				 _loadData5j.getVersion());
	}

	/**
	 * test that check the xml file
	 */
	public void test1_loadTimeTable5j() {
		Cycle cycle = _loadData5j.getTTStructure().getCurrentCycle();
		assertEquals("test1_loadTimeTable5j : getMaxNumberOfPeriodsADay: ", 12, cycle
				.getMaxNumberOfPeriodsADay());
		assertEquals("test1_loadTimeTable5j : getMaxNumberOfSeqs: ", 3, cycle
				.getMaxNumberOfSeqs());
		assertEquals("test1_loadTimeTable : active days: ", 5,
				TTStructure.NUMBEROFACTIVESDAYS);
	}

	/**
	 * test that check the setofactivities
	 */
	public void test2_loadTimeTable5j() {
		if (DxFlags.newActivity) {
			DxSetOfActivitiesSites dxsoasSet = (DxSetOfActivitiesSites) _loadData5j
					.getDxActivitiesSitesReader();
			DxActivitySite dxasSite = dxsoasSet.getActivitySite("SHE");
			assertNotNull("test2_loadTimeTable5j", dxasSite);
			assertNotNull("test2_1_loadTimeTable5j", dxasSite
					.getActivity("AMC640"));
		} else {
			SetOfActivitiesSites setSite =  _loadData5j.getSetOfActivitiesSites();
			assertEquals("test2_2_loadTimeTable5j : assertEquals: ", "SHE",
					setSite.getResourceAt(0).getID());

			SetOfActivities setAct = (SetOfActivities) setSite.getResourceAt(0)
					.getAttach();
			assertEquals("test2_3_loadTimeTable5j : assertEquals: ", "AMC640",
					setAct.getResourceAt(1).getID());
		}
	}

	/**
	 * test that check the setofstudents
	 */
	public void test3_loadTimeTable5j() {
		SetOfStuSites setSite =  _loadData5j.getSetofStuSites();
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
		DxSetOfInstructors setIns = _loadData5j.getDxSetOfInstructors();
		// Above, we get instructor at index 2, and here we get at 3. Reason
		// is that above is an index and bellow is a key, which start at 1
		assertNotNull("test4_1_loadTimeTable5j : assertNotNull: ", setIns
				.getInstructor("THÉRIAULT, MICHÈLE"));
		assertEquals("test4_1_loadTimeTable5j : assertEquals: ",
				"THÉRIAULT, MICHÈLE", setIns
						.getInstructor("THÉRIAULT, MICHÈLE").getName());
	}

	/**
	 * test that check the setofrooms
	 */
	public void test5_loadTimeTable5j() {
		if (DxFlags.newRooms) {
			DxSetOfSites dxsosSetSites = _loadData5j.getDxSetOfSitesRooms();
			DxSite dxsSite = dxsosSetSites.getSite("SHE");
			assertNotNull("test5_loadTimeTable : assertNotNull: ", dxsSite);
			DxCategory dxcCat1 = dxsSite.getCat("CAT 1");
			assertNotNull("test5_1_loadTimeTable5j : assertNotNull: ", dxcCat1);
			DxRoom dxrRoom = dxcCat1.getRoom("D13016");
			assertNotNull("test5_2_loadTimeTable5j : assertNotNull: ", dxrRoom);
		} else {
			SetOfSites setSite =  _loadData5j.getSetOfSitesRooms();
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
		}
	}

	/**
	 * test that check the selective import of Students
	 */
	public void test1_ImportSelective5j() {

		SetOfStuSites newStuSites = null;
		try {
			newStuSites = (SetOfStuSites) _loadData5j.selectiveImport(
					_loadData5j.getSetofStuSites(), "." + File.separator
							+ "dataTest" + File.separator + "ImportSTUDS.SIG");
			assertEquals("test1_ImportSelective : assertEquals: ", "",
					newStuSites.getError());
			if (newStuSites.getError() == "") {
				// Set Of Student Sites. Check if Update is done
				SetOfStuSites setSite = _loadData5j.getSetofStuSites();
				// Test of DLoadData.findAddedElements
				SetOfStudents setStud = (SetOfStudents) setSite
						.getResourceAt(0).getAttach();
				assertNotNull(
						"test2_ImportSelective5j : assertNotNull: added student",
						setStud.getResource("NOUVEAUET"));

				// Test of DLoadData.findChangesInElements
				Student studentChanged = setStud.getStudent("GIRALDO-L");
				SetOfStuCourses currentCourses = studentChanged
						.getCoursesList();
				assertNull(
						"test3_ImportSelective5j : assertNull: Changed student ",
						currentCourses.getResource("GIN3252"));

				// Test of DLoadData.findDeletedElements
				assertNull(
						"test4_ImportSelective5j : assertNull: Deleted student ",
						setStud.getStudent("RHEAULT M"));
			}
		} catch (DxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * test that check the version of timetable
	 */
	public void test_loadTimeTable7j() {
		assertEquals("test_loadTimeTable7j : assertEquals: ", "1.5",
				 _loadData7j.getVersion());
	}

	/**
	 * test that check the xml file
	 */
	public void test1_loadTimeTable7j() {
		Cycle cycle =  _loadData7j.getTTStructure().getCurrentCycle();
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
		if (DxFlags.newActivity) {
			DxSetOfActivitiesSites dxsoasSet = (DxSetOfActivitiesSites) _loadData7j
					.getDxActivitiesSitesReader();
			DxActivitySite dxasSite = dxsoasSet.getActivitySite("SHE");
			assertNotNull("test2_loadTimeTable5j", dxasSite);
			assertNotNull("test2_1_loadTimeTable5j", dxasSite
					.getActivity("AMC640"));
		} else {
			SetOfActivitiesSites setSite =  _loadData7j
					.getSetOfActivitiesSites();
			assertEquals("test2_loadTimeTable7j : assertEquals: ", "SHE",
					setSite.getResourceAt(0).getID());
			SetOfActivities setAct = (SetOfActivities) setSite.getResourceAt(0)
					.getAttach();
			assertEquals("test2_1_loadTimeTable7j : assertEquals: ", "AMC640",
					setAct.getResourceAt(1).getID());
		}
	}

	/**
	 * test that check the setofstudents
	 */
	public void test3_loadTimeTable7j() {
		SetOfStuSites setSite =  _loadData7j.getSetofStuSites();
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
		DxSetOfInstructors setIns = _loadData7j.getDxSetOfInstructors();
		// Above, we get instructor at index 2, and here we get at 3. Reason
		// is that above is an index and bellow is a key, which start at 1
		assertNotNull("test4_1_loadTimeTable5j : assertNotNull: ", setIns
				.getInstructor("THÉRIAULT, MICHÈLE"));
		assertEquals("test4_1_loadTimeTable5j : assertEquals: ",
				"THÉRIAULT, MICHÈLE", setIns
						.getInstructor("THÉRIAULT, MICHÈLE").getName());
	}

	/**
	 * test that check the setofrooms
	 */
	public void test5_loadTimeTable7j() {
		if (DxFlags.newRooms) {
			DxSetOfSites dxsosSetSites = _loadData7j.getDxSetOfSitesRooms();
			DxSite dxsSite = dxsosSetSites.getSite("SHE");
			assertNotNull("test5_loadTimeTable : assertNotNull: ", dxsSite);
			DxCategory dxcCat1 = dxsSite.getCat("CAT 1");
			assertNotNull("test5_1_loadTimeTable5j : assertNotNull: ", dxcCat1);
			DxRoom dxrRoom = dxcCat1.getRoom("D13016");
			assertNotNull("test5_2_loadTimeTable5j : assertNotNull: ", dxrRoom);
		} else {
			SetOfSites setSite = _loadData7j.getSetOfSitesRooms();
			assertEquals("test5_loadTimeTable7j : assertEquals: ", "SHE",
					setSite.getResourceAt(0).getID());
			SetOfCategories setCat = ((SetOfCategories) setSite
					.getResourceAt(0).getAttach());
			assertEquals("test5_1_loadTimeTable7j : assertEquals: ", "CAT 1",
					setCat.getResourceAt(0).getID());
			SetOfRooms setRooms = ((SetOfRooms) setCat.getResourceAt(0)
					.getAttach());
			assertEquals("test5_2_loadTimeTable7j : assertEquals: ", "D13016",
					setRooms.getResourceAt(4).getID());
		}
	}

	/**
	 * test that check the selective import of Students
	 */
	public void test1_ImportSelective7j() {

		SetOfStuSites newStuSites = null;
		try {
			newStuSites = (SetOfStuSites) _loadData7j.selectiveImport(
					 _loadData7j.getSetofStuSites(), "." + File.separator
							+ "dataTest" + File.separator + "ImportSTUDS.SIG");
			assertEquals("test1_ImportSelective : assertEquals: ", "",
					newStuSites.getError());
			if (newStuSites.getError() == "") {
				// Set Of Student Sites. Check if Update is done
				SetOfStuSites setSite =  _loadData7j.getSetofStuSites();
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
		} catch (DxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}