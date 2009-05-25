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
import dInternal.DataExchange;
import dInternal.DxLoadData;
import dInternal.DxStateBarModel;
import dInternal.dData.dInstructors.DxInstructorsReader;
import dInternal.dData.dInstructors.DxReadInstructorsdotDia;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import dInternal.dOptimization.DxAssignAllAlg;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AdminFonctionsTest extends TestCase {

	/**
	 * 
	 * 
	 */
	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(AdminFonctionsTest.class);
	} // end suite

	private final String _pathForFacsAdminDia = "." + File.separator
			+ "origDist" + File.separator + "facs" + File.separator + "admin"
			+ File.separator + "styleDia" + File.separator;

	private final String _pathForFacsAdminDim = "." + File.separator
			+ "origDist" + File.separator + "facs" + File.separator + "admin"
			+ File.separator + "styleDim" + File.separator;

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
			assertEquals("test_facAdminDia: assertEquals 0", 0, sbm.elementAt(
					10).getValue());

			new DxAssignAllAlg(facAdminDia).doWork();
			facAdminDia.changeInDModel(new Object());
			sbm.update();
			assertEquals("test_conflits: assertEquals 239", 239, sbm.elementAt(
					6).getValue());
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_basicData: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_facAdminDia");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 *
	 */
	public void test_readInstructors() {
		try {
			DxLoadData ld = new DxLoadData();
			byte[] dataloaded = ld
					.filterBadChars(_pathForFacsAdminDim + "profAdm.sig");
			DataExchange de = ld.insertHeader(dataloaded);
			DxInstructorsReader dxir = new DxReadInstructorsdotDia(de, 5, 14);
			DxSetOfInstructors dxsoi = dxir.readSetOfInstructors();
			assertEquals("test_readInstructors: assertEquals 64", 64, dxsoi
					.size());
			assertEquals("test_readInstructors: assertEquals true", true,
					dxsoi.areVectorsSync());
			assertNotNull("test_readInstructors: assertNotNull First", dxsoi
					.getResource("ADM101"));
			assertNotNull("test_readInstructors: assertNotNull Last", dxsoi
					.getResource("MQG542"));
			assertNull("test_readInstructors: assertNull", dxsoi
					.getResource("rgr"));

//			 assertEquals("test6_6_getSetOfInstructors: assertEquals", 5,
//			dxsoi.getInstructorAvailabilityByName("YAHIA, AMMAR").getPeriodAvailability(4,
//			 13));
			// assertEquals("test6_7_getSetOfInstructors: assertEquals", 1,
			// dxsoi
			// .getInstructorAvailabilityByKey(5).getPeriodAvailability(2, 7));
			// dxsoi.removeInstructor(1);
			// dxsoi.removeInstructor(126);
			// assertEquals("test6_8_getSetOfInstructors: assertEquals", 124,
			// dxsoi
			// .size());
			// assertEquals("test6_9_getSetOfInstructors: assertEquals", true,
			// dxsoi
			// .areVectorsSync());
			// assertEquals("test6_10_getSetOfInstructors: assertEquals", -1,
			// dxsoi
			// .getInstructorKeyByName("YAHIA, AMMAR"));
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_basicData: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_readInstructors");
			e.printStackTrace();
		}

	}

	// public void test_facAdminDim() {
	// try {
	// DModel facAdminDia = new DModel(new DxTTableDoc(),
	// _pathForFacsAdminDim + "horaireAdmin.dia");
	// facAdminDia.changeInDModel(new Object());
	// DxStateBarModel sbm = new DxStateBarModel(facAdminDia);
	// sbm.update();
	//
	// assertEquals("test_facAdminDia: assertEquals -1", -1, sbm
	// .elementAt(0).getValue());
	// assertEquals("test_facAdminDia: assertEquals 59", 59, sbm
	// .elementAt(1).getValue());
	// assertEquals("test_facAdminDia: assertEquals 64", 64, sbm
	// .elementAt(2).getValue());
	// assertEquals("test_facAdminDia: assertEquals 13", 13, sbm
	// .elementAt(3).getValue());
	// assertEquals("test_facAdminDia: assertEquals 624", 624, sbm
	// .elementAt(4).getValue());
	// assertEquals("test_facAdminDia: assertEquals 101", 101, sbm
	// .elementAt(5).getValue());
	// assertEquals("test_facAdminDia: assertEquals 0", 0, sbm
	// .elementAt(6).getValue());
	// assertEquals("test_facAdminDia: assertEquals 0", 0, sbm
	// .elementAt(7).getValue());
	// assertEquals("test_facAdminDia: assertEquals 0", 0, sbm
	// .elementAt(8).getValue());
	// assertEquals("test_facAdminDia: assertEquals 0", 0, sbm
	// .elementAt(9).getValue());
	// assertEquals("test_facAdminDia: assertEquals 0", 0, sbm
	// .elementAt(10).getValue());
	//			
	// new DxAssignAllAlg(facAdminDia).doWork();
	// facAdminDia.changeInDModel(new Object());
	// sbm.update();
	// assertEquals("test_conflits: assertEquals 239", 239, sbm
	// .elementAt(6).getValue());
	// } catch (Exception e) {
	// // Should not fail in tests, but if file not there gives a failure
	// assertEquals("test_basicData: exception", "nullPointer", e
	// .toString());
	// System.out.println("Exception in: test_facAdminDia");
	// e.printStackTrace();
	// }
	// }

}
