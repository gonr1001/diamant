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

	private DModel _dmData7j; // For LoadData7j

	private DModel _dmData5j; // For LoadData5j

	public RoomsConditionsTest(String name) {
		super(name);
		try {
			_dmData7j = new DModel(new DxTTableDoc(), "." + File.separator
					+ "dataTest" + File.separator + "loadData7j.dia");
		} catch (Exception e) {
			// Should not fail in tests
			e.printStackTrace();
		}
		_dmData7j.buildSetOfEvents();
		_dmData7j.getConditionsTest().buildStudentConflictMatrix();
		_dmData7j.getConditionsTest().buildAllConditions(_dmData7j.getTTStructure());

		try {
			_dmData5j = new DModel(new DxTTableDoc(), "." + File.separator
					+ "dataTest" + File.separator + "loadData5j.dia");
		} catch (Exception e) {
			// Should not fail in tests
			e.printStackTrace();
		}
		_dmData5j.buildSetOfEvents();
		_dmData5j.getConditionsTest().buildStudentConflictMatrix();
		_dmData5j.getConditionsTest().buildAllConditions(_dmData5j.getTTStructure());
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(RoomsConditionsTest.class);
	} // end suite

	/**
	 * 
	 */
	public void test_Availability() {
		Period period = _dmData7j.getTTStructure().getCurrentCycle()
				.getFirstPeriod();
		DxRoomsConditionsToTest testRoom = new DxRoomsConditionsToTest(_dmData7j);
		int[] perKey = { 1, 1, 1 };
		int nbConf = testRoom.getInfo(perKey, period, "AMC640.1.01.1.");
		assertEquals("test_Availability : assertEquals 2", 1, nbConf);
	}

	public void test2_Availability() {
		Period period = _dmData5j.getTTStructure().getCurrentCycle()
				.getFirstPeriod();
		DxRoomsConditionsToTest testRoom = new DxRoomsConditionsToTest(_dmData5j);
		int[] perKey = { 1, 1, 1 };
		int nbConf = testRoom.getInfo(perKey, period, "AMC640.1.01.1.");
		assertEquals("test2_Availability : assertEquals 2", 1, nbConf);
	}

	/**
	 * 
	 */
	public void test_EventsConflicts() {
		_dmData7j.getTTStructure().getCurrentCycle().getNextPeriod(1);
		Period period = _dmData7j.getTTStructure().getCurrentCycle()
				.getNextPeriod(1);
		DxRoomsConditionsToTest testRoom = new DxRoomsConditionsToTest(_dmData7j);
		int[] perKey = { 1, 1, 2 };
		testRoom.addTest(perKey, period, "AMC640.1.01.1.");
		int nbConf = testRoom.getInfo(perKey, period, "AMC640.1.02.1.");
		assertEquals("test_EventsConflicts : assertEquals 2", 1, nbConf);
	}

	public void test2_EventsConflicts() {
		_dmData5j.getTTStructure().getCurrentCycle().getNextPeriod(1);
		Period period = _dmData5j.getTTStructure().getCurrentCycle()
				.getNextPeriod(1);
		DxRoomsConditionsToTest testRoom = new DxRoomsConditionsToTest(_dmData5j);
		int[] perKey = { 1, 1, 2 };
		testRoom.addTest(perKey, period, "AMC640.1.01.1.");
		int nbConf = testRoom.getInfo(perKey, period, "AMC640.1.02.1.");
		assertEquals("test2_EventsConflicts : assertEquals 2", 1, nbConf);
	}

} // end RoomsConditionsTest