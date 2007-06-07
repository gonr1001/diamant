package dTest.dInternal.dOptimizationTest;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInterface.DxTTableDoc;
import dInternal.DModel;
import dInternal.dOptimization.DxInstructorsConditionsToTest;
import dInternal.dTimeTable.Period;

public class InstructorsConditionsTest extends TestCase {

	private DModel _dm5j;

	private DModel _dm7j;

	public InstructorsConditionsTest(String name) {
		super(name);
		try {
			_dm5j = new DModel(new DxTTableDoc(), "." + File.separator
					+ "dataTest" + File.separator + "loadData5j.dia");
		} catch (Exception e) {
			// !!!NIC!!! Has to be handled or causes errors
		}
//		_dm5j.buildSetOfEvents();
		_dm5j.getConditionsToTest().buildStudentConflictMatrix();
		_dm5j.getConditionsToTest().buildAllConditions(_dm5j.getTTStructure());

		try {
			_dm7j = new DModel(new DxTTableDoc(), "." + File.separator
					+ "dataTest" + File.separator + "loadData5j.dia");
		} catch (Exception e) {
			// !!!NIC!!! Has to be handled or causes errors
		}
//		_dm7j.buildSetOfEvents();
		_dm7j.getConditionsToTest().buildStudentConflictMatrix();
		_dm7j.getConditionsToTest().buildAllConditions(_dm7j.getTTStructure());
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(InstructorsConditionsTest.class);
	} // end suite

	/**
	 * 
	 */
	public void test_Availability5j() {
		Period period = _dm5j.getTTStructure().getCurrentCycle()
				.getFirstPeriod();
		DxInstructorsConditionsToTest testIns = new DxInstructorsConditionsToTest(
				_dm5j);
		int[] perKey = { 1, 1, 1 };
		int nbConf = testIns.addTest(perKey, period, "AMC640.1.01.1.");
		assertEquals("test_Availability5j : assertEquals 2", 1, nbConf);
	}

	/**
	 * 
	 */
	public void test_2EventsConflicts5j() {
		_dm5j.getTTStructure().getCurrentCycle().getNextPeriod(1);
		Period period = _dm5j.getTTStructure().getCurrentCycle().getNextPeriod(
				1);
		DxInstructorsConditionsToTest testIns = new DxInstructorsConditionsToTest(
				_dm5j);
		int[] perKey = { 1, 1, 2 };
		testIns.addTest(perKey, period, "AMC640.1.01.1.");
		int nbConf = testIns.getInfo(perKey, period, "AMC640.1.02.1.");
		assertEquals("test_2EventsConflicts5j : assertEquals 2", 1, nbConf);
	}

	/**
	 * 
	 */
	public void test_Availability7j() {
		Period period = _dm7j.getTTStructure().getCurrentCycle()
				.getFirstPeriod();
		DxInstructorsConditionsToTest testIns = new DxInstructorsConditionsToTest(
				_dm7j);
		int[] perKey = { 1, 1, 1 };
		int nbConf = testIns.getInfo(perKey, period, "AMC640.1.01.1.");
		assertEquals("test_Availability7j : assertEquals 2", 1, nbConf);
	}

	/**
	 * 
	 */
	public void test_2EventsConflicts7j() {
		_dm7j.getTTStructure().getCurrentCycle().getNextPeriod(1);
		Period period = _dm7j.getTTStructure().getCurrentCycle().getNextPeriod(
				1);
		DxInstructorsConditionsToTest testIns = new DxInstructorsConditionsToTest(
				_dm7j);
		int[] perKey = { 1, 1, 2 };
		testIns.addTest(perKey, period, "AMC640.1.01.1.");
		int nbConf = testIns.getInfo(perKey, period, "AMC640.1.02.1.");
		assertEquals("test_2EventsConflicts7j : assertEquals 2", 1, nbConf);
	}

} // end InstructorsConditionsTest