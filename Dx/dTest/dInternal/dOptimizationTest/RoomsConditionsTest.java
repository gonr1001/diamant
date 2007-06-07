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

	public RoomsConditionsTest(String name) {
		super(name);

	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(RoomsConditionsTest.class);
	} // end suite

	/**
	 * 
	 *
	 */
	public void test_RoomConfINnData5j() {
		try {
			DModel dmData5j = new DModel(new DxTTableDoc(), "." + File.separator
					+ "dataTest" + File.separator + "loadData5j.dia");
			dmData5j.getConditionsToTest().buildStudentConflictMatrix();
			dmData5j.getConditionsToTest().buildAllConditions(
					dmData5j.getTTStructure());
			Period period = dmData5j.getTTStructure().getCurrentCycle()
					.getFirstPeriod();
			DxRoomsConditionsToTest testRoom = new DxRoomsConditionsToTest(dmData5j);
			int[] perKey = { 1, 1, 1 };
			int nbConf = testRoom.getInfo(perKey, period, "AMC640.1.01.1.");
			assertEquals("test_RoomConfINnData5j : assertEquals 2", 1, nbConf);
		} catch (Exception e) {
			// Should not fail in tests
			System.out.println("Exception in: test_AvailabilityData5j");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 *
	 */
	@SuppressWarnings("null")
	public void test_EventsConflictsData5j() {
		DModel dmData5j= null;
		try {
			dmData5j = new DModel(new DxTTableDoc(), "."
					+ File.separator + "dataTest" + File.separator
					+ "loadData7j.dia");

		} catch (Exception e) {
			// Should not fail in tests
			e.printStackTrace();
		}
//		dmData5j.buildSetOfEvents();
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
		assertEquals("test2_EventsConflicts : assertEquals 2", 1, nbConf);
	}

	/**
	 * 
	 *
	 */
	@SuppressWarnings("null")
	public void test_AvailabilityData7j() {
		DModel dmData7j = null;
		try {
			dmData7j = new DModel(new DxTTableDoc(), "." + File.separator
					+ "dataTest" + File.separator + "loadData7j.dia");
		} catch (Exception e) {
			// Should not fail in tests
			e.printStackTrace();
		}
		// dmData7j.buildSetOfEvents();
		dmData7j.getConditionsToTest().buildStudentConflictMatrix();
		dmData7j.getConditionsToTest().buildAllConditions(
				dmData7j.getTTStructure());
		Period period = dmData7j.getTTStructure().getCurrentCycle()
				.getFirstPeriod();
		DxRoomsConditionsToTest testRoom = new DxRoomsConditionsToTest(dmData7j);
		int[] perKey = { 1, 1, 1 };
		int nbConf = testRoom.getInfo(perKey, period, "AMC640.1.01.1.");
		assertEquals("test_Availability : assertEquals 2", 1, nbConf);

	}

	/**
	 * 
	 *
	 */
	@SuppressWarnings("null")
	public void test_EventsConflictsData7j() {
		DModel dmData7j = null;
		try {

			dmData7j = new DModel(new DxTTableDoc(), "." + File.separator
					+ "dataTest" + File.separator + "loadData7j.dia");

		} catch (Exception e) {
			// Should not fail in tests
			e.printStackTrace();
		}
		// dmData7j.buildSetOfEvents();
		dmData7j.getConditionsToTest().buildStudentConflictMatrix();
		dmData7j.getConditionsToTest().buildAllConditions(
				dmData7j.getTTStructure());
		dmData7j.getTTStructure().getCurrentCycle().getNextPeriod(1);
		Period period = dmData7j.getTTStructure().getCurrentCycle()
				.getNextPeriod(1);
		DxRoomsConditionsToTest testRoom = new DxRoomsConditionsToTest(dmData7j);
		int[] perKey = { 1, 1, 2 };
		testRoom.addTest(perKey, period, "AMC640.1.01.1.");
		int nbConf = testRoom.getInfo(perKey, period, "AMC640.1.02.1.");
		assertEquals("test_EventsConflicts : assertEquals 2", 1, nbConf);
	}


} // end RoomsConditionsTest
