package dTest.dInternal.dOptimizationTest;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInterface.DxTTableDoc;
import dInternal.DModel;
import dInternal.dOptimization.DxRoomsConditionsToTest;
import dInternal.dTimeTable.Period;

public class RoomsConditionsTest extends TestCase {

	private final String _pathForFiles = "." + File.separator + "dataTest"
	+ File.separator;
	

	/**
	 * 
	 * 
	 */
	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(RoomsConditionsTest.class);
	} // end suite

	/**
	 * 
	 * 
	 */
	public void test_RoomConfInData5j() {
		StringBuffer fileName = new StringBuffer("." + File.separator);
		fileName.append("dataTest" + File.separator);
		fileName.append("loadData5j.dia");
		try {
			DModel dmData5j = new DModel(new DxTTableDoc(), _pathForFiles + "loadData5j.dia");
			dmData5j.getConditionsToTest().buildStudentConflictMatrix();
			dmData5j.getConditionsToTest().buildAllConditions(
					dmData5j.getTTStructure());
			Period period = dmData5j.getTTStructure().getCurrentCycle()
					.getFirstPeriod();
			DxRoomsConditionsToTest testRoom = new DxRoomsConditionsToTest(
					dmData5j);
			int[] perKey = { 1, 1, 1 };
			int nbConf = testRoom.getInfo(perKey, period, "AMC640.1.01.1.");
			assertEquals("test_RoomConfInData5j : nbConflits", 1, nbConf);
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_RoomConfInData5j: exception", "nullPointer", e.toString());
			System.out.println("Exception in: test_RoomConfInData5j");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 */
	public void test_EventsConflictsInData5j() {

		try {
			DModel dmData5j = new DModel(new DxTTableDoc(), _pathForFiles + "loadData5j.dia");
			dmData5j.getConditionsToTest().buildStudentConflictMatrix();
			dmData5j.getConditionsToTest().buildAllConditions(
					dmData5j.getTTStructure());
			dmData5j.getTTStructure().getCurrentCycle().getNextPeriod(1);
			dmData5j.getTTStructure().getCurrentCycle().getNextPeriod(1);
			Period period = dmData5j.getTTStructure().getCurrentCycle()
					.getNextPeriod(1);
			DxRoomsConditionsToTest testRoom = new DxRoomsConditionsToTest(
					dmData5j);
			int[] perKey = { 1, 1, 2 };
			testRoom.addTest(perKey, period, "AMC640.1.01.1.");
			int nbConf = testRoom.getInfo(perKey, period, "AMC640.1.02.1.");
			assertEquals(
					"test_EventsConflictsInData5j : assertEquals nbConflits",
					1, nbConf);
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_buildScNoAssigned: exception", "nullPointer", e.toString());
			System.out.println("Exception in: test_EventsConflictsInData5j");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 */
	public void test_AvailabilityInData5j() {
		StringBuffer fileName = new StringBuffer("." + File.separator);
		fileName.append("dataTest" + File.separator);
		fileName.append("loadData5j.dia");
		try {
			DModel dmData5j = new DModel(new DxTTableDoc(), _pathForFiles + "loadData5j.dia");
			dmData5j.getConditionsToTest().buildStudentConflictMatrix();
			dmData5j.getConditionsToTest().buildAllConditions(
					dmData5j.getTTStructure());
			Period period = dmData5j.getTTStructure().getCurrentCycle()
					.getFirstPeriod();
			DxRoomsConditionsToTest testRoom = new DxRoomsConditionsToTest(
					dmData5j);
			int[] perKey = { 1, 1, 1 };
			int nbConf = testRoom.getInfo(perKey, period, "AMC640.1.01.1.");
			assertEquals("test_AvailabilityInData7j: nbConflicts", 1, nbConf);

		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_buildScNoAssigned: exception", "nullPointer", e.toString());
			System.out.println("Exception in: test_AvailabilityInData7j");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 */
	public void test_EventsConflictsData7j() {

		try {
			DModel dmData7j = new DModel(new DxTTableDoc(), _pathForFiles + "loadData7j.dia");
			dmData7j.getConditionsToTest().buildStudentConflictMatrix();
			dmData7j.getConditionsToTest().buildAllConditions(
					dmData7j.getTTStructure());
			dmData7j.getTTStructure().getCurrentCycle().getNextPeriod(1);
			Period period = dmData7j.getTTStructure().getCurrentCycle()
					.getNextPeriod(1);
			DxRoomsConditionsToTest testRoom = new DxRoomsConditionsToTest(
					dmData7j);
			int[] perKey = { 1, 1, 2 };
			testRoom.addTest(perKey, period, "AMC640.1.01.1.");
			int nbConf = testRoom.getInfo(perKey, period, "AMC640.1.02.1.");
			assertEquals("test_EventsConflictsData7j: nbConflicts", 1, nbConf);

		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_buildScNoAssigned: exception", "nullPointer", e.toString());
			System.out.println("Exception in: test_EventsConflictsData7j");
			e.printStackTrace();
		}
	}

} // end RoomsConditionsTest
