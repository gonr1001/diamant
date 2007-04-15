/**
 * 
 * Title: ConditionsTest
 * Description: ConditionsTest is a class used to display a ConditionsTest with
 * buttons
 * 
 * 
 * Copyright (c) 2001 by rgr. All rights reserved.
 * 
 * 
 * This software is the confidential and proprietary information of rgr-fdl.
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with rgr-fdl.
 * 
 *
 */

package dTest.dInternal.dOptimizationTest;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInterface.DxTTableDoc;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.dOptimization.EventDx;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.TTStructure;

public class ConditionsTest extends TestCase {

	private DModel _dm5j;

	private DModel _dmh;

	private DModel _dm7j;

	private Period _period5j;

	private Period _period7j;

	public ConditionsTest(String name) {
		super(name);
		try {
			_dm5j = new DModel(new DxTTableDoc(), System
					.getProperty("user.dir")
					+ File.separator
					+ "dataTest"
					+ File.separator
					+ "loadData5j.dia");
		} catch (Exception e) {
			// Should not fail in controled conditions
		}
//		_dm5j.buildSetOfEvents();
		_dm5j.getConditionsTest().buildStudentConflictMatrix();
		_dm5j.getConditionsTest().buildAllConditions(_dm5j.getTTStructure());
		_dm5j.getTTStructure().getCurrentCycle().getNextPeriod(1);
		_period5j = _dm5j.getTTStructure().getCurrentCycle().getNextPeriod(1);

		try {
			_dmh = new DModel(new DxTTableDoc(), System.getProperty("user.dir")
					+ File.separator + "dataTest" + File.separator
					+ "lData5j30min.dia");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ici " + e.toString());
		}
//		_dmh.buildSetOfEvents();
		_dmh.getConditionsTest().buildStudentConflictMatrix();
		_dmh.getConditionsTest().buildAllConditions(_dmh.getTTStructure());

		try {
			_dm7j = new DModel(new DxTTableDoc(), System
					.getProperty("user.dir")
					+ File.separator
					+ "dataTest"
					+ File.separator
					+ "loadData7j.dia");
		} catch (Exception e) {
			// Should not fail in controled conditions
		}
//		_dm7j.buildSetOfEvents();
		_dm7j.getConditionsTest().buildStudentConflictMatrix();
		_dm7j.getConditionsTest().buildAllConditions(_dm7j.getTTStructure());
		_dm7j.getTTStructure().getCurrentCycle().getNextPeriod(1);
		_period7j = _dm7j.getTTStructure().getCurrentCycle().getNextPeriod(1);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(ConditionsTest.class);
	} // end suite

	public void test_initAllConditions5j() {
		_dm5j.getConditionsTest().initAllConditions();
		assertEquals("test_initAllConditions5j : assertEquals 1", 2, _period5j
				.getEventsInPeriod().size());
	}

	/**
	 * check conflicts
	 */
	public void test_getConflictsEventInTTs5j() {
		DResource event = _dm5j.getSetOfEvents().getResourceAt(0);
		int[] dayTime = { 5, 8, 15 };
		String periodKey = _dm5j.getTTStructure().getCurrentCycle().getPeriod(
				dayTime);
		((EventDx) event.getAttach()).setKey(4, periodKey);
		((EventDx) event.getAttach()).setAssigned(true);
		int[] nbConf = _dm5j.getConditionsTest().getEventConflictsInTTs(
				_dm5j.getTTStructure(), event, true);
		assertEquals("test1_getConflictsEventInTTs5j : assertEquals 1", 0,
				nbConf[0]);
		assertEquals("test2_getConflictsEventInTTs5j : assertEquals 2", 1,
				nbConf[1]);
		assertEquals("test3_getConflictsEventInTTs5j : assertEquals 3", 0,
				nbConf[2]);
		Period period = _dm5j.getTTStructure().getCurrentCycle()
				.getPeriodByPeriodKey(periodKey);
		assertEquals("test4_getConflictsEventInTTs5j : assertEquals 4", 0,
				period.getEventsInPeriod().size());
	}

	/**
	 * check conflicts and add in period
	 */
	public void test_addEventInTTs_1_5j() {
		DResource event = _dm5j.getSetOfEvents().getResourceAt(0);
		int[] dayTime = { 5, 8, 15 };
		String periodKey = _dm5j.getTTStructure().getCurrentCycle().getPeriod(
				dayTime);
		((EventDx) event.getAttach()).setKey(4, periodKey);
		((EventDx) event.getAttach()).setAssigned(true);
		int[] nbConf = _dm5j.getConditionsTest().addEventInTTs(
				_dm5j.getTTStructure(), event, true);
		assertEquals("test1_addEventInTTs_1_5j : assertEquals 1", 0, nbConf[0]);
		assertEquals("test2_addEventInTTs_1_5j : assertEquals 2", 1, nbConf[1]);
		assertEquals("test3_addEventInTTs_1_5j : assertEquals 3", 0, nbConf[2]);
		Period period = _dm5j.getTTStructure().getCurrentCycle()
				.getPeriodByPeriodKey(periodKey);
		assertEquals("test4_addEventInTTs_1_5j : assertEquals 4", 1, period
				.getEventsInPeriod().size());
	}

	/**
	 * remove event in a period
	 */
	public void test_removeEventInTTs_3_5j() {
		DResource event = _dm5j.getSetOfEvents().getResource("AMC645.1.01.1.");
		int[] dayTime = { 5, 8, 15 };
		String periodKey = _dm5j.getTTStructure().getCurrentCycle().getPeriod(
				dayTime);
		((EventDx) event.getAttach()).setKey(4, periodKey);
		((EventDx) event.getAttach()).setAssigned(true);
		int[] nbConf = _dm5j.getConditionsTest().addEventInTTs(
				_dm5j.getTTStructure(), event, true);
		event = _dm5j.getSetOfEvents().getResourceAt(0);
		((EventDx) event.getAttach()).setKey(4, periodKey);
		((EventDx) event.getAttach()).setAssigned(true);
		nbConf = _dm5j.getConditionsTest().addEventInTTs(
				_dm5j.getTTStructure(), event, true);
		assertEquals("test1_removeEventInTTs_3_5j : assertEquals 1", 12,
				nbConf[0]);
		assertEquals("test2_removeEventInTTs_3_5j : assertEquals 2", 1,
				nbConf[1]);
		assertEquals("test3_removeEventInTTs_3_5j : assertEquals 3", 0,
				nbConf[2]);
		Period period = _dm5j.getTTStructure().getCurrentCycle()
				.getPeriodByPeriodKey(periodKey);
		assertEquals("test4_removeEventInTTs_3_5j : assertEquals 4", 2, period
				.getEventsInPeriod().size());

		event = _dm5j.getSetOfEvents().getResourceAt(0);
		((EventDx) event.getAttach()).setKey(4, periodKey);
		((EventDx) event.getAttach()).setAssigned(true);
		nbConf = _dm5j.getConditionsTest().removeEventInTTs(
				_dm5j.getTTStructure(), event, true);
		assertEquals("test5_removeEventInTTs_3_5j : assertEquals 1", 12,
				nbConf[0]);
		assertEquals("test6_removeEventInTTs_3_5j : assertEquals 2", 1,
				nbConf[1]);
		assertEquals("test7_removeEventInTTs_3_5j : assertEquals 3", 0,
				nbConf[2]);
		period = _dm5j.getTTStructure().getCurrentCycle().getPeriodByPeriodKey(
				periodKey);
		assertEquals("test8_removeEventInTTs_3_5j : assertEquals 4", 1, period
				.getEventsInPeriod().size());

	}

	/**
	 * 
	 * 
	 */
	public void test_buildAllConditions() {
		// get period day=3 (mercredi), sequence = 1 (AM) and period = 2 (8h30)
		Period per = _dmh.getTTStructure().getCurrentCycle().getPeriodByKey(3,
				1, 2);
		assertEquals("test1_buildAllConditions : assertEquals 1", 2, per
				.getNbStudConflict());
		assertEquals("test2_buildAllConditions : assertEquals 2", 0, per
				.getNbInstConflict());
		assertEquals("test3_buildAllConditions : assertEquals 3", 0, per
				.getNbRoomConflict());
		// get period day=1 (lundi), sequence = 3 (PM) and period = 6 (15h30)
		per = _dmh.getTTStructure().getCurrentCycle().getPeriodByKey(1, 3, 6);
		assertEquals("test4_buildAllConditions : assertEquals 4", 1, per
				.getNbStudConflict());
		assertEquals("test5_buildAllConditions : assertEquals 5", 2, per
				.getNbInstConflict());
		assertEquals("test6_buildAllConditions : assertEquals 6", 0, per
				.getNbRoomConflict());
	}

	/**
	 * 
	 * 
	 */
	public void test_buildAllConditionsInCloneTTS() {
		TTStructure cloneTTS = _dmh.getTTStructure().cloneCurrentTTS();
		_dmh.getConditionsTest().buildAllConditions(cloneTTS);
		// get period day=3 (mercredi), sequence = 1 (AM) and period = 2 (8h30)
		Period per = cloneTTS.getCurrentCycle().getPeriodByKey(3, 1, 2);
		assertEquals("test1_buildAllConditionsInCloneTTS : assertEquals 1", 2,
				per.getNbStudConflict());
		assertEquals("test2_buildAllConditionsInCloneTTS : assertEquals 2", 0,
				per.getNbInstConflict());
		assertEquals("test3_buildAllConditionsInCloneTTS : assertEquals 3", 0,
				per.getNbRoomConflict());
		// get period day=1 (lundi), sequence = 3 (PM) and period = 6 (15h30)
		per = cloneTTS.getCurrentCycle().getPeriodByKey(1, 3, 6);
		assertEquals("test4_buildAllConditionsInCloneTTS : assertEquals 4", 1,
				per.getNbStudConflict());
		assertEquals("test5_buildAllConditionsInCloneTTS : assertEquals 5", 2,
				per.getNbInstConflict());
		assertEquals("test6_buildAllConditionsInCloneTTS : assertEquals 6", 0,
				per.getNbRoomConflict());
		boolean isEquals = _dmh.getTTStructure().isEquals(cloneTTS);
		assertEquals("test7_buildAllConditionsInCloneTTS : assertEquals 7",
				true, isEquals);

		DResource event = _dmh.getSetOfEvents().getResource("PED200.1.21.1.");
		EventDx eventClone = ((EventDx) event.getAttach()).cloneEvent();
		eventClone.setAssigned(true);
		// String
		// periodKey=daytime[0]+System.getProperty("user.dir")+daytime[1]+System.getProperty("user.dir")+daytime[2];
		// set event key monday at 8h30
		eventClone.setKey(4, "1.1.2");
		_dmh.getConditionsTest().addEventInTTs(cloneTTS,
				new DResource("PED200.1.21.1.", eventClone), false);
		per = cloneTTS.getCurrentCycle().getPeriodByKey(1, 1, 2);
		assertEquals("test8_buildAllConditionsInCloneTTS : assertEquals 8", 14,
				per.getNbStudConflict());
		assertEquals("test9_buildAllConditionsInCloneTTS : assertEquals 9", 1,
				per.getNbInstConflict());
		assertEquals("test10_buildAllConditionsInCloneTTS : assertEquals 10",
				0, per.getNbRoomConflict());

		// set event key tuesday at 8h30
		eventClone = ((EventDx) event.getAttach()).cloneEvent();
		eventClone.setAssigned(true);
		eventClone.setKey(4, "2.1.2");
		_dmh.getConditionsTest().addEventInTTs(cloneTTS,
				new DResource("PED200.1.21.1.", eventClone), false);
		per = cloneTTS.getCurrentCycle().getPeriodByKey(2, 1, 2);
		assertEquals("test11_buildAllConditionsInCloneTTS : assertEquals 11",
				40, per.getNbStudConflict());
		assertEquals("test12_buildAllConditionsInCloneTTS : assertEquals 12",
				0, per.getNbInstConflict());
		assertEquals("test13_buildAllConditionsInCloneTTS : assertEquals 13",
				0, per.getNbRoomConflict());
		per = cloneTTS.getCurrentCycle().getPeriodByKey(2, 1, 3);
		assertEquals("test14_buildAllConditionsInCloneTTS : assertEquals 12-1",
				1, per.getNbInstConflict());
		// set event key wedneday at 15h00
		eventClone.setKey(4, "3.3.5");
		_dmh.getConditionsTest().addEventInTTs(cloneTTS,
				new DResource("PED200.1.21.1.", eventClone), false);
		per = cloneTTS.getCurrentCycle().getPeriodByKey(3, 3, 5);
		assertEquals("test15_buildAllConditionsInCloneTTS : assertEquals 14",
				20, per.getNbStudConflict());
		assertEquals("test16_buildAllConditionsInCloneTTS : assertEquals 15",
				0, per.getNbInstConflict());
		assertEquals("test16_buildAllConditionsInCloneTTS : assertEquals 16",
				0, per.getNbRoomConflict());
		per = cloneTTS.getCurrentCycle().getPeriodByKey(3, 3, 7);
		assertEquals("test16_buildAllConditionsInCloneTTS : assertEquals 14",
				0, per.getNbStudConflict());
	}

	public void test_initAllConditions7j() {
		_dm7j.getConditionsTest().initAllConditions();
		assertEquals("test_initAllConditions7j : assertEquals 1", 2, _period7j
				.getEventsInPeriod().size());
	}

	/**
	 * check conflicts
	 */
	public void test_getConflictsEventInTTs7j() {
		DResource event = _dm7j.getSetOfEvents().getResourceAt(0);
		int[] dayTime = { 5, 8, 15 };
		String periodKey = _dm7j.getTTStructure().getCurrentCycle().getPeriod(
				dayTime);
		((EventDx) event.getAttach()).setKey(4, periodKey);
		((EventDx) event.getAttach()).setAssigned(true);
		int[] nbConf = _dm7j.getConditionsTest().getEventConflictsInTTs(
				_dm7j.getTTStructure(), event, true);
		assertEquals("test1_getConflictsEventInTTs7j : assertEquals 1", 0,
				nbConf[0]);
		assertEquals("test2_getConflictsEventInTTs7j : assertEquals 2", 1,
				nbConf[1]);
		assertEquals("test3_getConflictsEventInTTs7j : assertEquals 3", 0,
				nbConf[2]);
		Period period = _dm7j.getTTStructure().getCurrentCycle()
				.getPeriodByPeriodKey(periodKey);
		assertEquals("test4_getConflictsEventInTTs7j : assertEquals 4", 0,
				period.getEventsInPeriod().size());
	}

	/**
	 * check conflicts and add in period
	 */
	public void test_addEventInTTs_1_7j() {
		DResource event = _dm7j.getSetOfEvents().getResourceAt(0);
		int[] dayTime = { 5, 8, 15 };
		String periodKey = _dm7j.getTTStructure().getCurrentCycle().getPeriod(
				dayTime);
		((EventDx) event.getAttach()).setKey(4, periodKey);
		((EventDx) event.getAttach()).setAssigned(true);
		int[] nbConf = _dm7j.getConditionsTest().addEventInTTs(
				_dm7j.getTTStructure(), event, true);
		assertEquals("test1_addEventInTTs_1_7j : assertEquals 1", 0, nbConf[0]);
		assertEquals("test2_addEventInTTs_1_7j : assertEquals 2", 1, nbConf[1]);
		assertEquals("test3_addEventInTTs_1_7j : assertEquals 3", 0, nbConf[2]);
		Period period = _dm7j.getTTStructure().getCurrentCycle()
				.getPeriodByPeriodKey(periodKey);
		assertEquals("test4_addEventInTTs_1_7j : assertEquals 4", 1, period
				.getEventsInPeriod().size());
	}

	/**
	 * check conflicts and add in period
	 */
	public void test_addEventInTTs_2_7j() {
		DResource event = _dm7j.getSetOfEvents().getResource("AMC645.1.01.1.");
		int[] dayTime = { 5, 8, 15 };
		String periodKey = _dm7j.getTTStructure().getCurrentCycle().getPeriod(
				dayTime);
		((EventDx) event.getAttach()).setKey(4, periodKey);
		((EventDx) event.getAttach()).setAssigned(true);
		int[] nbConf = _dm7j.getConditionsTest().addEventInTTs(
				_dm7j.getTTStructure(), event, true);
		event = _dm7j.getSetOfEvents().getResourceAt(0);
		((EventDx) event.getAttach()).setKey(4, periodKey);
		((EventDx) event.getAttach()).setAssigned(true);
		nbConf = _dm7j.getConditionsTest().addEventInTTs(
				_dm7j.getTTStructure(), event, true);
		assertEquals("test1_addEventInTTs_2_7j : assertEquals 1", 12, nbConf[0]);
		assertEquals("test2_addEventInTTs_2_7j : assertEquals 2", 1, nbConf[1]);
		assertEquals("test3_addEventInTTs_2_7j : assertEquals 3", 0, nbConf[2]);
		Period period = _dm7j.getTTStructure().getCurrentCycle()
				.getPeriodByPeriodKey(periodKey);
		assertEquals("test4_addEventInTTs_2_7j : assertEquals 4", 2, period
				.getEventsInPeriod().size());
		assertEquals("test5_addEventInTTs_2_7j : assertEquals 4", 2, period
				.getConflictsEventsInPeriod(event.getID()).size());
	}

	/**
	 * remove event in a period
	 */
	public void test_removeEventInTTs_3_7j() {
		DResource event = _dm7j.getSetOfEvents().getResource("AMC645.1.01.1.");
		int[] dayTime = { 5, 8, 15 };
		String periodKey = _dm7j.getTTStructure().getCurrentCycle().getPeriod(
				dayTime);
		((EventDx) event.getAttach()).setKey(4, periodKey);
		((EventDx) event.getAttach()).setAssigned(true);
		int[] nbConf = _dm7j.getConditionsTest().addEventInTTs(
				_dm7j.getTTStructure(), event, true);
		event = _dm7j.getSetOfEvents().getResourceAt(0);
		((EventDx) event.getAttach()).setKey(4, periodKey);
		((EventDx) event.getAttach()).setAssigned(true);
		nbConf = _dm7j.getConditionsTest().addEventInTTs(
				_dm7j.getTTStructure(), event, true);
		assertEquals("test1_removeEventInTTs_3_5j : assertEquals 1", 12,
				nbConf[0]);
		assertEquals("test2_removeEventInTTs_3_5j : assertEquals 2", 1,
				nbConf[1]);
		assertEquals("test3_removeEventInTTs_3_5j : assertEquals 3", 0,
				nbConf[2]);
		Period period = _dm7j.getTTStructure().getCurrentCycle()
				.getPeriodByPeriodKey(periodKey);
		assertEquals("test4_removeEventInTTs_3_5j : assertEquals 4", 2, period
				.getEventsInPeriod().size());

		event = _dm7j.getSetOfEvents().getResourceAt(0);
		((EventDx) event.getAttach()).setKey(4, periodKey);
		((EventDx) event.getAttach()).setAssigned(true);
		nbConf = _dm7j.getConditionsTest().removeEventInTTs(
				_dm7j.getTTStructure(), event, true);
		assertEquals("test5_removeEventInTTs_3_5j : assertEquals 1", 12,
				nbConf[0]);
		assertEquals("test6_removeEventInTTs_3_5j : assertEquals 2", 1,
				nbConf[1]);
		assertEquals("test7_removeEventInTTs_3_5j : assertEquals 3", 0,
				nbConf[2]);
		period = _dm7j.getTTStructure().getCurrentCycle().getPeriodByPeriodKey(
				periodKey);
		assertEquals("test8_removeEventInTTs_3_5j : assertEquals 4", 1, period
				.getEventsInPeriod().size());

	}

} // end ConditionsTest
