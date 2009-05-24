/**
 *
 * Title: FonctionalTest 
 * Description: 	FonctionalTest is a class used to test that
 * 				All files in the original Distribution can be handled by
 *               Diamant
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
* 
* @author gonr1001
* @since JDK1.3
*/
package ca.sixs.dTest.dOptimzation;

import java.io.File;

import dInterface.DxTTableDoc;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.dOptimization.DxEvent;
import dInternal.dTimeTable.Period;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class FonctionalTest extends TestCase {
	
	/**
	 * 
	 * 
	 */
	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(FonctionalTest.class);
	} // end suite

	
	private final String _pathForFacsAdmin = "." + File.separator + "origDist"
	+ File.separator + "facs"+ File.separator + "admin";
	
	private final String _pathForqVerif = "." + File.separator + "origDist"
	+ File.separator + "qVerif" + File.separator;

	
	
	public void test_qVerif() {
		try {
			DModel dmQVerif = new DModel(new DxTTableDoc(), _pathForqVerif + "horaire0.dia");
			dmQVerif.getConditionsToTest().initAllConditions();
			dmQVerif.getConditionsToTest().buildStudentConflictMatrix();
			dmQVerif.getConditionsToTest()
					.buildAllConditions(dmQVerif.getTTStructure());
			Period period5j = dmQVerif.getTTStructure().getCurrentCycle()
					.getNextPeriod(1);
			assertEquals("test_qVerif : assertEquals 1", 13,
					period5j.getEventsInPeriod().size());
			DResource event = dmQVerif.getSetOfEvents().getResourceAt(0);
			int[] dayTime = { 5, 8, 15 };
			String periodKey = dmQVerif.getTTStructure().getCurrentCycle()
					.getPeriod(dayTime);

			((DxEvent) event.getAttach()).setKey(4, periodKey);
			((DxEvent) event.getAttach()).setAssigned(true);
			int[] nbConf = dmQVerif.getConditionsToTest().getEventConflictsInTTs(
					dmQVerif.getTTStructure(), event, true);
			assertEquals("test_qVerif : : nbConf 12", 12,
					nbConf[0]);
			assertEquals("test_qVerif : : nbConf 0", 0,
					nbConf[1]);
			assertEquals("test_qVerif : : nbConf 3", 3,
					nbConf[2]);
			Period period = dmQVerif.getTTStructure().getCurrentCycle()
					.getPeriodByPeriodKey(periodKey);
			assertEquals("test_qVerif : : nbConf 14", 14,
					period.getEventsInPeriod().size());
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_basicData: exception", "nullPointer", e.toString());
			System.out.println("Exception in: test_qVerif");
			e.printStackTrace();
		}
	}

	public void test_facAdmin() {
		try {
			DModel dmQVerif = new DModel(new DxTTableDoc(), _pathForFacsAdmin + "locauxAdm.sig");
			dmQVerif.getConditionsToTest().initAllConditions();
			dmQVerif.getConditionsToTest().buildStudentConflictMatrix();
			dmQVerif.getConditionsToTest()
					.buildAllConditions(dmQVerif.getTTStructure());
			Period period5j = dmQVerif.getTTStructure().getCurrentCycle()
					.getNextPeriod(1);
			assertEquals("test_qVerif : assertEquals 1", 13,
					period5j.getEventsInPeriod().size());
			DResource event = dmQVerif.getSetOfEvents().getResourceAt(0);
			int[] dayTime = { 5, 8, 15 };
			String periodKey = dmQVerif.getTTStructure().getCurrentCycle()
					.getPeriod(dayTime);

			((DxEvent) event.getAttach()).setKey(4, periodKey);
			((DxEvent) event.getAttach()).setAssigned(true);
			int[] nbConf = dmQVerif.getConditionsToTest().getEventConflictsInTTs(
					dmQVerif.getTTStructure(), event, true);
			assertEquals("test_qVerif : : nbConf 12", 12,
					nbConf[0]);
			assertEquals("test_qVerif : : nbConf 0", 0,
					nbConf[1]);
			assertEquals("test_qVerif : : nbConf 3", 3,
					nbConf[2]);
			Period period = dmQVerif.getTTStructure().getCurrentCycle()
					.getPeriodByPeriodKey(periodKey);
			assertEquals("test_qVerif : : nbConf 14", 14,
					period.getEventsInPeriod().size());
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_basicData: exception", "nullPointer", e.toString());
			System.out.println("Exception in: test_qVerif");
			e.printStackTrace();
		}
	}
	
	
}
