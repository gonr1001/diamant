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
import dInternal.dOptimization.DxEvent;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.TTStructure;


public class ConditionsTest extends TestCase {

	/**
	 * 
	 * 
	 */
	public ConditionsTest(String name) {
		super(name);
	}

	/**
	 * 
	 * 
	 */
	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(ConditionsTest.class);
	} // end suite

	public void test_initAllConditions5j() {
		StringBuffer fileName = new StringBuffer("." + File.separator);
		fileName.append("dataTest" + File.separator);
		fileName.append("loadData5j.dia");

		try {
			DModel dm5j = new DModel(new DxTTableDoc(), fileName.toString());
			dm5j.getConditionsToTest().initAllConditions();
			dm5j.getConditionsToTest().buildStudentConflictMatrix();
			dm5j.getConditionsToTest()
					.buildAllConditions(dm5j.getTTStructure());
			dm5j.getTTStructure().getCurrentCycle().getNextPeriod(1);
			Period period5j = dm5j.getTTStructure().getCurrentCycle()
					.getNextPeriod(1);
			assertEquals("test_initAllConditions5j : assertEquals 1", 2,
					period5j.getEventsInPeriod().size());
			DResource event = dm5j.getSetOfEvents().getResourceAt(0);
			int[] dayTime = { 5, 8, 15 };
			String periodKey = dm5j.getTTStructure().getCurrentCycle()
					.getPeriod(dayTime);

			((DxEvent) event.getAttach()).setKey(4, periodKey);
			((DxEvent) event.getAttach()).setAssigned(true);
			int[] nbConf = dm5j.getConditionsToTest().getEventConflictsInTTs(
					dm5j.getTTStructure(), event, true);
			assertEquals("test1_getConflictsEventInTTs5j : assertEquals 1", 0,
					nbConf[0]);
			assertEquals("test2_getConflictsEventInTTs5j : assertEquals 2", 1,
					nbConf[1]);
			assertEquals("test3_getConflictsEventInTTs5j : assertEquals 3", 0,
					nbConf[2]);
			Period period = dm5j.getTTStructure().getCurrentCycle()
					.getPeriodByPeriodKey(periodKey);
			assertEquals("test4_getConflictsEventInTTs5j : assertEquals 4", 0,
					period.getEventsInPeriod().size());
		} catch (Exception e) {
			// Should not fail in tests
			System.out.println("Exception in: test_initAllConditions5j");
			e.printStackTrace();
		}
	}

	/**
	 * check conflicts and add in period
	 */
	public void test_addEventInTTs_1_5j() {
		StringBuffer fileName = new StringBuffer("." + File.separator);
		fileName.append("dataTest" + File.separator);
		fileName.append("loadData5j.dia");

		try {
			DModel dm5j = new DModel(new DxTTableDoc(), fileName.toString());
			dm5j.getConditionsToTest().initAllConditions();
			dm5j.getConditionsToTest().buildStudentConflictMatrix();
			dm5j.getConditionsToTest()
					.buildAllConditions(dm5j.getTTStructure());
			dm5j.getTTStructure().getCurrentCycle().getNextPeriod(1);

			DResource event = dm5j.getSetOfEvents().getResourceAt(0);
			int[] dayTime = { 5, 8, 15 };
			String periodKey = dm5j.getTTStructure().getCurrentCycle()
					.getPeriod(dayTime);
			((DxEvent) event.getAttach()).setKey(4, periodKey);
			((DxEvent) event.getAttach()).setAssigned(true);
			int[] nbConf = dm5j.getConditionsToTest().addEventInTTs(
					dm5j.getTTStructure(), event, true);
			assertEquals("test1_addEventInTTs_1_5j : assertEquals 1", 0,
					nbConf[0]);
			assertEquals("test2_addEventInTTs_1_5j : assertEquals 2", 1,
					nbConf[1]);
			assertEquals("test3_addEventInTTs_1_5j : assertEquals 3", 0,
					nbConf[2]);
			Period period = dm5j.getTTStructure().getCurrentCycle()
					.getPeriodByPeriodKey(periodKey);
			assertEquals("test4_addEventInTTs_1_5j : assertEquals 4", 1, period
					.getEventsInPeriod().size());
		} catch (Exception e) {
			// Should not fail in tests
			System.out.println("Exception in: test_initAllConditions5j");
			e.printStackTrace();
		}
	}

	/**
	 * remove event in a period
	 */
	public void test_removeEventInTTs_5j() {
		StringBuffer fileName = new StringBuffer("." + File.separator);
		fileName.append("dataTest" + File.separator);
		fileName.append("loadData5j.dia");

		try {
			DModel dm5j = new DModel(new DxTTableDoc(), fileName.toString());
			dm5j.getConditionsToTest().initAllConditions();
			dm5j.getConditionsToTest().buildStudentConflictMatrix();
			dm5j.getConditionsToTest()
					.buildAllConditions(dm5j.getTTStructure());
			dm5j.getTTStructure().getCurrentCycle().getNextPeriod(1);
			DResource event = dm5j.getSetOfEvents().getResource(
					"AMC645.1.01.1.");
			int[] dayTime = { 5, 8, 15 };
			String periodKey = dm5j.getTTStructure().getCurrentCycle()
					.getPeriod(dayTime);
			((DxEvent) event.getAttach()).setKey(4, periodKey);
			((DxEvent) event.getAttach()).setAssigned(true);
			int[] nbConf = dm5j.getConditionsToTest().addEventInTTs(
					dm5j.getTTStructure(), event, true);
			event = dm5j.getSetOfEvents().getResourceAt(0);
			((DxEvent) event.getAttach()).setKey(4, periodKey);
			((DxEvent) event.getAttach()).setAssigned(true);
			nbConf = dm5j.getConditionsToTest().addEventInTTs(
					dm5j.getTTStructure(), event, true);
			assertEquals("test1_removeEventInTTs_3_5j : assertEquals 1", 12,
					nbConf[0]);
			assertEquals("test2_removeEventInTTs_3_5j : assertEquals 2", 1,
					nbConf[1]);
			assertEquals("test3_removeEventInTTs_3_5j : assertEquals 3", 0,
					nbConf[2]);
			Period period = dm5j.getTTStructure().getCurrentCycle()
					.getPeriodByPeriodKey(periodKey);
			assertEquals("test4_removeEventInTTs_3_5j : assertEquals 4", 2,
					period.getEventsInPeriod().size());

			event = dm5j.getSetOfEvents().getResourceAt(0);
			((DxEvent) event.getAttach()).setKey(4, periodKey);
			((DxEvent) event.getAttach()).setAssigned(true);
			nbConf = dm5j.getConditionsToTest().removeEventInTTs(
					dm5j.getTTStructure(), event, true);
			assertEquals("test5_removeEventInTTs_3_5j : assertEquals 1", 12,
					nbConf[0]);
			assertEquals("test6_removeEventInTTs_3_5j : assertEquals 2", 1,
					nbConf[1]);
			assertEquals("test7_removeEventInTTs_3_5j : assertEquals 3", 0,
					nbConf[2]);
			period = dm5j.getTTStructure().getCurrentCycle()
					.getPeriodByPeriodKey(periodKey);
			assertEquals("test8_removeEventInTTs_3_5j : assertEquals 4", 1,
					period.getEventsInPeriod().size());
		} catch (Exception e) {
			// Should not fail in tests
			System.out.println("Exception in: test_removeEventInTTs_3_5j");
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * 
	 */
	public void test_buildAllConditionsDh() {
		StringBuffer fileName = new StringBuffer("." + File.separator);
		fileName.append("dataTest" + File.separator);
		fileName.append("lData5j30min.dia");

		try {
			DModel dmh = new DModel(new DxTTableDoc(), fileName.toString());
			dmh.getConditionsToTest().initAllConditions();
			dmh.getConditionsToTest().buildStudentConflictMatrix();
			dmh.getConditionsToTest().buildAllConditions(dmh.getTTStructure());
			dmh.getTTStructure().getCurrentCycle().getNextPeriod(1);

			// get period day=3 (mercredi), sequence = 1 (AM) and period = 2
			// (8h30)
			Period per = dmh.getTTStructure().getCurrentCycle().getPeriodByKey(
					3, 1, 2);
			assertEquals("test1_buildAllConditions : assertEquals 1", 2, per
					.getNbStudConflict());
			assertEquals("test2_buildAllConditions : assertEquals 2", 0, per
					.getNbInstConflict());
			assertEquals("test3_buildAllConditions : assertEquals 3", 0, per
					.getNbRoomConflict());
			// get period day=1 (lundi), sequence = 3 (PM) and period = 6
			// (15h30)
			per = dmh.getTTStructure().getCurrentCycle()
					.getPeriodByKey(1, 3, 6);
			assertEquals("test4_buildAllConditions : assertEquals 4", 1, per
					.getNbStudConflict());
			assertEquals("test5_buildAllConditions : assertEquals 5", 2, per
					.getNbInstConflict());
			assertEquals("test6_buildAllConditions : assertEquals 6", 0, per
					.getNbRoomConflict());

		} catch (Exception e) {
			// Should not fail in tests
			System.out.println("Exception in: test_buildAllConditionsDh");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 */
	public void test_buildAllConditionsInCloneTTS() {
		StringBuffer fileName = new StringBuffer("." + File.separator);
		fileName.append("dataTest" + File.separator);
		fileName.append("lData5j30min.dia");

		try {
			DModel dmh = new DModel(new DxTTableDoc(), fileName.toString());
			dmh.getConditionsToTest().initAllConditions();
			dmh.getConditionsToTest().buildStudentConflictMatrix();
			dmh.getConditionsToTest().buildAllConditions(dmh.getTTStructure());
			dmh.getTTStructure().getCurrentCycle().getNextPeriod(1);
			TTStructure cloneTTS = dmh.getTTStructure().cloneCurrentTTS();
			dmh.getConditionsToTest().buildAllConditions(cloneTTS);
			// get period day=3 (mercredi), sequence = 1 (AM) and period = 2
			// (8h30)
			Period per = cloneTTS.getCurrentCycle().getPeriodByKey(3, 1, 2);
			assertEquals("test1_buildAllConditionsInCloneTTS : assertEquals 2",
					2, per.getNbStudConflict());
			assertEquals("test2_buildAllConditionsInCloneTTS : assertEquals 0",
					0, per.getNbInstConflict());
			assertEquals("test3_buildAllConditionsInCloneTTS : assertEquals 0",
					0, per.getNbRoomConflict());
			
			assertEquals("test_Wen.beginHour hour : assertEquals 8",
					8, per.getBeginHour()[0]);
			assertEquals("test_WenbeginHour min : assertEquals 30",
					30, per.getBeginHour()[1]);
			
			// get period day=1 (lundi), sequence = 3 (PM) and period = 6
			// (15h30)
			per = cloneTTS.getCurrentCycle().getPeriodByKey(1, 3, 6);
			assertEquals("test4_buildAllConditionsInCloneTTS : assertEquals 1",
					1, per.getNbStudConflict());
			assertEquals("test5_buildAllConditionsInCloneTTS : assertEquals 2",
					2, per.getNbInstConflict());
			assertEquals("test6_buildAllConditionsInCloneTTS : assertEquals 0",
					0, per.getNbRoomConflict());
			boolean isEquals = dmh.getTTStructure().isEquals(cloneTTS);
			assertEquals("test7_buildAllConditionsInCloneTTS : assertEquals true",
					true, isEquals);

			assertEquals("test_Mon.beginHour hour : assertEquals 8",
					15, per.getBeginHour()[0]);
			assertEquals("test_Mon.beginHour min : assertEquals 30",
					30, per.getBeginHour()[1]);
			DResource event = dmh.getSetOfEvents()
					.getResource("PED200.1.21.1.");
			
			
			DxEvent eventClone = null;
//			if (DxFlags.newEventClone) {
				eventClone = ((DxEvent) event.getAttach()).eventClone();
//			} else {
//				eventClone = ((DxEvent) event.getAttach()).oldEventClone();
//			}	

			eventClone.setAssigned(true);
			// String
			// periodKey=daytime[0]+System.getProperty("user.dir")+daytime[1]+System.getProperty("user.dir")+daytime[2];
			// set event key monday at 8h30
			eventClone.setKey(4, "1.1.2");
			dmh.getConditionsToTest().addEventInTTs(cloneTTS,
					new DResource("PED200.1.21.1.", eventClone), false);
			per = cloneTTS.getCurrentCycle().getPeriodByKey(1, 1, 2);
			assertEquals("test8_buildAllConditionsInCloneTTS : assertEquals 14",
					14, per.getNbStudConflict());
			assertEquals("test9_buildAllConditionsInCloneTTS : assertEquals 9",
					1, per.getNbInstConflict());
			assertEquals(
					"test10_buildAllConditionsInCloneTTS : assertEquals 10", 0,
					per.getNbRoomConflict());

			// set event key tuesday at 8h30
//			if (DxFlags.newEventClone) {
				eventClone = ((DxEvent) event.getAttach()).eventClone();
//			} else {
//				eventClone = ((DxEvent) event.getAttach()).oldEventClone();
//			}	
			eventClone.setAssigned(true);
			eventClone.setKey(4, "2.1.2");
			dmh.getConditionsToTest().addEventInTTs(cloneTTS,
					new DResource("PED200.1.21.1.", eventClone), false);
			per = cloneTTS.getCurrentCycle().getPeriodByKey(2, 1, 2);
			assertEquals(
					"test11_buildAllConditionsInCloneTTS : assertEquals 11",
					40, per.getNbStudConflict());
			assertEquals(
					"test12_buildAllConditionsInCloneTTS : assertEquals 12", 0,
					per.getNbInstConflict());
			assertEquals(
					"test13_buildAllConditionsInCloneTTS : assertEquals 13", 0,
					per.getNbRoomConflict());
			per = cloneTTS.getCurrentCycle().getPeriodByKey(2, 1, 3);
			assertEquals(
					"test14_buildAllConditionsInCloneTTS : assertEquals 12-1",
					1, per.getNbInstConflict());
			// set event key wedneday at 15h00
			eventClone.setKey(4, "3.3.5");
			dmh.getConditionsToTest().addEventInTTs(cloneTTS,
					new DResource("PED200.1.21.1.", eventClone), false);
			per = cloneTTS.getCurrentCycle().getPeriodByKey(3, 3, 5);
			assertEquals(
					"test15_buildAllConditionsInCloneTTS : assertEquals 14",
					20, per.getNbStudConflict());
			assertEquals(
					"test16_buildAllConditionsInCloneTTS : assertEquals 15", 0,
					per.getNbInstConflict());
			assertEquals(
					"test16_buildAllConditionsInCloneTTS : assertEquals 16", 0,
					per.getNbRoomConflict());
			per = cloneTTS.getCurrentCycle().getPeriodByKey(3, 3, 7);
			assertEquals(
					"test16_buildAllConditionsInCloneTTS : assertEquals 14", 0,
					per.getNbStudConflict());

		} catch (Exception e) {
			// Should not fail in tests
			System.out
					.println("Exception in: test_buildAllConditionsInCloneTTS()");
			e.printStackTrace();
		}

	}

	public void test_initAllConditions7j() {
		StringBuffer fileName = new StringBuffer("." + File.separator);
		fileName.append("dataTest" + File.separator);
		fileName.append("loadData7j.dia");

		try {

			DModel dm7j = new DModel(new DxTTableDoc(), fileName.toString());
			dm7j.getConditionsToTest().buildStudentConflictMatrix();
			dm7j.getConditionsToTest()
					.buildAllConditions(dm7j.getTTStructure());
			dm7j.getTTStructure().getCurrentCycle().getNextPeriod(1);
			Period period7j = dm7j.getTTStructure().getCurrentCycle()
					.getNextPeriod(1);
			dm7j.getConditionsToTest().initAllConditions();
			assertEquals("test_initAllConditions7j : assertEquals 1", 2,
					period7j.getEventsInPeriod().size());
			DResource event = dm7j.getSetOfEvents().getResourceAt(0);
			int[] dayTime = { 5, 8, 15 };
			String periodKey = dm7j.getTTStructure().getCurrentCycle()
					.getPeriod(dayTime);
			((DxEvent) event.getAttach()).setKey(4, periodKey);
			((DxEvent) event.getAttach()).setAssigned(true);
			int[] nbConf = dm7j.getConditionsToTest().getEventConflictsInTTs(
					dm7j.getTTStructure(), event, true);
			assertEquals("test1_getConflictsEventInTTs7j : assertEquals 1", 0,
					nbConf[0]);
			assertEquals("test2_getConflictsEventInTTs7j : assertEquals 2", 1,
					nbConf[1]);
			assertEquals("test3_getConflictsEventInTTs7j : assertEquals 3", 0,
					nbConf[2]);
			Period period = dm7j.getTTStructure().getCurrentCycle()
					.getPeriodByPeriodKey(periodKey);
			assertEquals("test4_getConflictsEventInTTs7j : assertEquals 4", 0,
					period.getEventsInPeriod().size());
		} catch (Exception e) {
			// Should not fail in tests
			System.out.println("Exception in: test_initAllConditions7j");
			e.printStackTrace();
		}
	}

	/**
	 * check conflicts and add in period
	 */
	public void test_addEventInTTs_1_7j() {
		StringBuffer fileName = new StringBuffer("." + File.separator);
		fileName.append("dataTest" + File.separator);
		fileName.append("loadData7j.dia");

		try {

			DModel dm7j = new DModel(new DxTTableDoc(), fileName.toString());
			dm7j.getConditionsToTest().buildStudentConflictMatrix();
			dm7j.getConditionsToTest()
					.buildAllConditions(dm7j.getTTStructure());
			dm7j.getTTStructure().getCurrentCycle().getNextPeriod(1);
			Period period7j = dm7j.getTTStructure().getCurrentCycle()
					.getNextPeriod(1);
			dm7j.getConditionsToTest().initAllConditions();
			assertEquals("test_initAllConditions7j : assertEquals 1", 2,
					period7j.getEventsInPeriod().size());
			DResource event = dm7j.getSetOfEvents().getResourceAt(0);
			int[] dayTime = { 5, 8, 15 };
			String periodKey = dm7j.getTTStructure().getCurrentCycle()
					.getPeriod(dayTime);
			((DxEvent) event.getAttach()).setKey(4, periodKey);
			((DxEvent) event.getAttach()).setAssigned(true);
			int[] nbConf = dm7j.getConditionsToTest().addEventInTTs(
					dm7j.getTTStructure(), event, true);
			assertEquals("test1_addEventInTTs_1_7j : assertEquals 1", 0,
					nbConf[0]);
			assertEquals("test2_addEventInTTs_1_7j : assertEquals 2", 1,
					nbConf[1]);
			assertEquals("test3_addEventInTTs_1_7j : assertEquals 3", 0,
					nbConf[2]);
			Period period = dm7j.getTTStructure().getCurrentCycle()
					.getPeriodByPeriodKey(periodKey);
			assertEquals("test4_addEventInTTs_1_7j : assertEquals 4", 1, period
					.getEventsInPeriod().size());
		} catch (Exception e) {
			// Should not fail in tests
			System.out.println("Exception in: test_addEventInTTs_1_7j");
			e.printStackTrace();
		}
	}

	/**
	 * check conflicts and add in period
	 */
	public void test_addEventInTTs_2_7j() {
		StringBuffer fileName = new StringBuffer("." + File.separator);
		fileName.append("dataTest" + File.separator);
		fileName.append("loadData7j.dia");

		try {

			DModel dm7j = new DModel(new DxTTableDoc(), fileName.toString());
			dm7j.getConditionsToTest().buildStudentConflictMatrix();
			dm7j.getConditionsToTest()
					.buildAllConditions(dm7j.getTTStructure());
			dm7j.getTTStructure().getCurrentCycle().getNextPeriod(1);
			Period period7j = dm7j.getTTStructure().getCurrentCycle()
					.getNextPeriod(1);
			dm7j.getConditionsToTest().initAllConditions();
			assertEquals("test_initAllConditions7j : assertEquals 1", 2,
					period7j.getEventsInPeriod().size());
			DResource event = dm7j.getSetOfEvents().getResource(
					"AMC645.1.01.1.");
			int[] dayTime = { 5, 8, 15 };
			String periodKey = dm7j.getTTStructure().getCurrentCycle()
					.getPeriod(dayTime);
			((DxEvent) event.getAttach()).setKey(4, periodKey);
			((DxEvent) event.getAttach()).setAssigned(true);
			int[] nbConf = dm7j.getConditionsToTest().addEventInTTs(
					dm7j.getTTStructure(), event, true);
			event = dm7j.getSetOfEvents().getResourceAt(0);
			((DxEvent) event.getAttach()).setKey(4, periodKey);
			((DxEvent) event.getAttach()).setAssigned(true);
			nbConf = dm7j.getConditionsToTest().addEventInTTs(
					dm7j.getTTStructure(), event, true);
			assertEquals("test1_addEventInTTs_2_7j : assertEquals 1", 12,
					nbConf[0]);
			assertEquals("test2_addEventInTTs_2_7j : assertEquals 2", 1,
					nbConf[1]);
			assertEquals("test3_addEventInTTs_2_7j : assertEquals 3", 0,
					nbConf[2]);
			Period period = dm7j.getTTStructure().getCurrentCycle()
					.getPeriodByPeriodKey(periodKey);
			assertEquals("test4_addEventInTTs_2_7j : assertEquals 4", 2, period
					.getEventsInPeriod().size());
			assertEquals("test5_addEventInTTs_2_7j : assertEquals 4", 2, period
					.getConflictsEventsInPeriod(event.getID()).size());
		} catch (Exception e) {
			// Should not fail in tests
			System.out.println("Exception in: test_addEventInTTs_2_7j");
			e.printStackTrace();
		}
	}

	/**
	 * remove event in a period
	 */
	public void test_removeEventInTTs_3_7j() {
		StringBuffer fileName = new StringBuffer("." + File.separator);
		fileName.append("dataTest" + File.separator);
		fileName.append("loadData7j.dia");

		try {

			DModel dm7j = new DModel(new DxTTableDoc(), fileName.toString());
			dm7j.getConditionsToTest().buildStudentConflictMatrix();
			dm7j.getConditionsToTest()
					.buildAllConditions(dm7j.getTTStructure());
			dm7j.getTTStructure().getCurrentCycle().getNextPeriod(1);
			Period period7j = dm7j.getTTStructure().getCurrentCycle()
					.getNextPeriod(1);
			dm7j.getConditionsToTest().initAllConditions();
			assertEquals("test_initAllConditions7j : assertEquals 1", 2,
					period7j.getEventsInPeriod().size());
			DResource event = dm7j.getSetOfEvents().getResource(
					"AMC645.1.01.1.");
			int[] dayTime = { 5, 8, 15 };
			String periodKey = dm7j.getTTStructure().getCurrentCycle()
					.getPeriod(dayTime);
			((DxEvent) event.getAttach()).setKey(4, periodKey);
			((DxEvent) event.getAttach()).setAssigned(true);
			int[] nbConf = dm7j.getConditionsToTest().addEventInTTs(
					dm7j.getTTStructure(), event, true);
			event = dm7j.getSetOfEvents().getResourceAt(0);
			((DxEvent) event.getAttach()).setKey(4, periodKey);
			((DxEvent) event.getAttach()).setAssigned(true);
			nbConf = dm7j.getConditionsToTest().addEventInTTs(
					dm7j.getTTStructure(), event, true);
			assertEquals("test1_removeEventInTTs_3_5j : assertEquals 1", 12,
					nbConf[0]);
			assertEquals("test2_removeEventInTTs_3_5j : assertEquals 2", 1,
					nbConf[1]);
			assertEquals("test3_removeEventInTTs_3_5j : assertEquals 3", 0,
					nbConf[2]);
			Period period = dm7j.getTTStructure().getCurrentCycle()
					.getPeriodByPeriodKey(periodKey);
			assertEquals("test4_removeEventInTTs_3_5j : assertEquals 4", 2,
					period.getEventsInPeriod().size());

			event = dm7j.getSetOfEvents().getResourceAt(0);
			((DxEvent) event.getAttach()).setKey(4, periodKey);
			((DxEvent) event.getAttach()).setAssigned(true);
			nbConf = dm7j.getConditionsToTest().removeEventInTTs(
					dm7j.getTTStructure(), event, true);
			assertEquals("test5_removeEventInTTs_3_5j : assertEquals 1", 12,
					nbConf[0]);
			assertEquals("test6_removeEventInTTs_3_5j : assertEquals 2", 1,
					nbConf[1]);
			assertEquals("test7_removeEventInTTs_3_5j : assertEquals 3", 0,
					nbConf[2]);
			period = dm7j.getTTStructure().getCurrentCycle()
					.getPeriodByPeriodKey(periodKey);
			assertEquals("test8_removeEventInTTs_3_5j : assertEquals 4", 1,
					period.getEventsInPeriod().size());
		} catch (Exception e) {
			// Should not fail in tests
			System.out.println("Exception in: test_removeEventInTTs_3_7j");
			e.printStackTrace();
		}
	}

} // end ConditionsTest
