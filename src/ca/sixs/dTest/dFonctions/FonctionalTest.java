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
package ca.sixs.dTest.dFonctions;

import java.io.File;

import dInterface.DxTTableDoc;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.DxStateBarModel;
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

	private final String _pathForFacsAdminDia = "." + File.separator
			+ "origDist" + File.separator + "facs" + File.separator + "admin"
			+ File.separator + "styleDia" + File.separator;

	private final String _pathForqVerif = "." + File.separator + "origDist"
			+ File.separator + "qVerif" + File.separator;

	public void test_qVerif() {
		try {
			DModel dmQVerif = new DModel(new DxTTableDoc(), _pathForqVerif
					+ "horaire0.dia");
			dmQVerif.getConditionsToTest().initAllConditions();
			dmQVerif.getConditionsToTest().buildStudentConflictMatrix();
			dmQVerif.getConditionsToTest().buildAllConditions(
					dmQVerif.getTTStructure());
			Period period5j = dmQVerif.getTTStructure().getCurrentCycle()
					.getNextPeriod(1);
			assertEquals("test_qVerif : assertEquals 1", 13, period5j
					.getEventsInPeriod().size());
			DResource event = dmQVerif.getSetOfEvents().getResourceAt(0);
			int[] dayTime = { 5, 8, 15 };
			String periodKey = dmQVerif.getTTStructure().getCurrentCycle()
					.getPeriod(dayTime);

			((DxEvent) event.getAttach()).setKey(4, periodKey);
			((DxEvent) event.getAttach()).setAssigned(true);
			int[] nbConf = dmQVerif.getConditionsToTest()
					.getEventConflictsInTTs(dmQVerif.getTTStructure(), event,
							true);
			assertEquals("test_qVerif : : nbConf 12", 12, nbConf[0]);
			assertEquals("test_qVerif : : nbConf 0", 0, nbConf[1]);
			assertEquals("test_qVerif : : nbConf 3", 3, nbConf[2]);
			Period period = dmQVerif.getTTStructure().getCurrentCycle()
					.getPeriodByPeriodKey(periodKey);
			assertEquals("test_qVerif : : nbConf 14", 14, period
					.getEventsInPeriod().size());
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_basicData: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_qVerif");
			e.printStackTrace();
		}
	}

	public void test_facAdminDia() {
		try {
			DModel facAdminDia = new DModel(new DxTTableDoc(),
					_pathForFacsAdminDia + "horaireAdmin.dia");
			facAdminDia.changeInDModel(new Object());
			DxStateBarModel sbm = new DxStateBarModel(facAdminDia);
			sbm.update();

			assertEquals("test_facAdminDia: assertEquals -1", -1, sbm
					.elementAt(0).getValue());
			assertEquals("test_facAdminDia: assertEquals 59", 59, sbm
					.elementAt(1).getValue());
			assertEquals("test_facAdminDia: assertEquals 64", 64, sbm
					.elementAt(2).getValue());
			assertEquals("test_facAdminDia: assertEquals 13", 13, sbm
					.elementAt(3).getValue());
			assertEquals("test_facAdminDia: assertEquals 624", 624, sbm
					.elementAt(4).getValue());
			assertEquals("test_facAdminDia: assertEquals 101", 101, sbm
					.elementAt(5).getValue());
			assertEquals("test_facAdminDia: assertEquals 0", 0, sbm
					.elementAt(6).getValue());
			assertEquals("test_facAdminDia: assertEquals 0", 0, sbm
					.elementAt(7).getValue());
			assertEquals("test_facAdminDia: assertEquals 0", 0, sbm
					.elementAt(8).getValue());
			assertEquals("test_facAdminDia: assertEquals 0", 0, sbm
					.elementAt(9).getValue());
			assertEquals("test_facAdminDia: assertEquals 0", 0, sbm
					.elementAt(10).getValue());
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_basicData: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_facAdminDia");
			e.printStackTrace();
		}
	}

	// public void testStateBarModel_5j() throws Exception {
	// DModel _dm5j;
	// DxStateBarModel sbm;
	//
	// DxDocument _dxDoc5j;
	// _dxDoc5j = new DxTTableDoc();
	// String fileName = "." + File.separator + "dataTest" + File.separator
	// + "loadData5j.dia";
	//
	// _dm5j = new DModel(_dxDoc5j, fileName);
	// _dm5j.changeInDModel(new Object());
	// sbm = new DxStateBarModel(_dm5j);
	// sbm.update();
	//
	// assertEquals("test0_StateBarModel_5j: assertEquals", -1, sbm.elementAt(
	// 0).getValue());
	// assertEquals("test1_StateBarModel_5j: assertEquals", 11, sbm.elementAt(
	// 1).getValue());
	// assertEquals("test2_StateBarModel_5j: assertEquals", 7, sbm
	// .elementAt(2).getValue());
	// assertEquals("test3_StateBarModel_5j: assertEquals", 44, sbm.elementAt(
	// 3).getValue());
	// assertEquals("test4_StateBarModel_5j: assertEquals", 15, sbm.elementAt(
	// 4).getValue());
	// assertEquals("test5_StateBarModel_5j: assertEquals", 27, sbm.elementAt(
	// 5).getValue());
	// assertEquals("test6_StateBarModel_5j: assertEquals", 3, sbm
	// .elementAt(6).getValue());
	// assertEquals("test7_StateBarModel_5j: assertEquals", 4, sbm
	// .elementAt(7).getValue());
	// assertEquals("test8_StateBarModel_5j: assertEquals", 0, sbm
	// .elementAt(8).getValue());
	// assertEquals("test9_StateBarModel_5j: assertEquals", 2, sbm
	// .elementAt(9).getValue());
	// assertEquals("test10_StateBarModel_5j: assertEquals", 2, sbm.elementAt(
	// 10).getValue());
	// }

}
