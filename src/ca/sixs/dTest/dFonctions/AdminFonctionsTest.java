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

import ca.sixs.util.pref.ParametersPref;

import dConstants.DConst;
import dInterface.DxTTableDoc;
import dInternal.DModel;
import dInternal.DataExchange;
import dInternal.DxLoadData;
import dInternal.DxStateBarModel;
import dInternal.dData.dActivities.DxActivitiesSitesReader;
import dInternal.dData.dActivities.DxReadActivitiesSites1dot5;
import dInternal.dData.dActivities.DxSetOfActivitiesSites;
import dInternal.dData.dInstructors.DxInstructorsReader;
import dInternal.dData.dInstructors.DxReadInstructorsdotDia;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import dInternal.dData.dRooms.DxReadSite1dot5;
import dInternal.dData.dRooms.DxRoom;
import dInternal.dData.dRooms.DxSetOfRooms;
import dInternal.dData.dRooms.DxSetOfSites;
import dInternal.dData.dRooms.DxSiteReader;
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
		
		ParametersPref pp = new ParametersPref();
		try {		
			pp.savePrefBeforeTest();
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
			assertEquals("test_conflits: assertEquals 5", 5, sbm.elementAt(6)
					.getValue());
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_basicData: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_facAdminDia");
			e.printStackTrace();
		} finally {
			pp.restorePrefAfterTest();
		}
	}

	/**
	 * 
	 *
	 */
	public void test_readInstructors() {
		ParametersPref pp = new ParametersPref();
		
		try {
			pp.savePrefBeforeTest();
			DxLoadData ld = new DxLoadData();
			byte[] dataloaded = ld.filterBadChars(_pathForFacsAdminDim
					+ "profAdm.sig");
			DataExchange de = ld.insertHeader(dataloaded);
			DxInstructorsReader dxir = new DxReadInstructorsdotDia(de, 5, 14);
			DxSetOfInstructors dxsoi = dxir.readSetOfInstructors();
			assertEquals("test_readInstructors: assertEquals 64", 64, dxsoi
					.size());
			assertEquals("test_readInstructors: assertEquals true", true, dxsoi
					.areVectorsSync());
			assertNotNull("test_readInstructors: assertNotNull First", dxsoi
					.getResource("ADM101"));
			assertNotNull("test_readInstructors: assertNotNull Last", dxsoi
					.getResource("MQG542"));
			assertNull("test_readInstructors: assertNull", dxsoi
					.getResource("rgr"));

			long i = dxsoi.getResourceKey("ADM101");
			assertEquals("test_readInstructors: assertEquals 5", 5, dxsoi
					.getInstructorAvailability(i).getPeriodAvailability(4, 13));
			assertEquals("test_readInstructors: assertEquals", 1, dxsoi
					.getInstructorAvailability(i).getPeriodAvailability(1, 1));
			dxsoi.removeInstructor(dxsoi.getResourceKey("ADM101"));
			dxsoi.removeInstructor(dxsoi.getResourceKey("MQG542"));
			assertEquals("test_readInstructors: assertEquals", 62, dxsoi.size());
			assertEquals("test_readInstructors: assertEquals", true, dxsoi
					.areVectorsSync());
			assertEquals("test6_10_getSetOfInstructors: assertEquals", -1,
					dxsoi.getResourceKey("YAHIA, AMMAR"));

		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_basicData: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_readInstructors");
			e.printStackTrace();
		} finally {
			pp.restorePrefAfterTest();
		}
	}

	/**
	 * 
	 *
	 */
	public void test_readRooms() {
		ParametersPref pp = new ParametersPref();
		
		try {
			pp.savePrefBeforeTest();
			DxLoadData ld = new DxLoadData();
			byte[] dataloaded = ld.filterBadChars(_pathForFacsAdminDim
					+ "locauxAdm.sig");
			DataExchange de = ld.insertHeader(dataloaded);

			DxSiteReader dxsr = new DxReadSite1dot5(de);
			DxSetOfSites dxsosSingle = dxsr.readSetOfSites();

			assertEquals("test_readRooms: assertEquals true", true, dxsosSingle
					.areVectorsSync());
			assertEquals("test_readRooms: asserEquals", 1, dxsosSingle
					.getSiteCount());
			assertEquals("test_readRooms: asserEquals", 1, dxsosSingle
					.getCatCount(DConst.ROOM_DEFAULT_SITE));
			assertEquals("test_readRooms: asserEquals", 13, dxsosSingle
					.getRoomCount(DConst.ROOM_DEFAULT_SITE,
							DConst.ROOM_STANDARD_CAT));
			assertEquals("test_readRooms: assertEquals",
					DConst.ROOM_DEFAULT_SITE, dxsosSingle.getSitesNamesSorted()
							.elementAt(0));
			DxSetOfRooms dsor = dxsosSingle.getAllDxRooms();
			assertNotNull("test_readRooms: assertNotNull First", dsor
					.getResource("K1-2004"));
			assertNotNull("test_readRooms: assertNotNull Last", dsor
					.getResource("K1-2046"));
			assertNull("test_readRooms: assertNull", dsor.getResource("rgr"));

			assertEquals("test_readRooms: assertEquals 1", 1, dsor
					.getRoomAvailability("K1-2004")
					.getPeriodAvailability(4, 13));

			dsor.removeResource(dsor.getResourceKey("K1-2004"));
			dsor.removeResource(dsor.getResourceKey("K1-2046"));
			assertEquals("test_readRooms: assertEquals 11 ", 11, dsor.size());
			assertEquals("test_readRooms: assertEquals true", true, dxsosSingle
					.areVectorsSync());
			assertEquals("test6_10_getSetOfInstructors: assertEquals", -1, dsor
					.getResourceKey("YAHIA, AMMAR"));

		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_basicData: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_readInstructors");
			e.printStackTrace();
		} finally {
			pp.restorePrefAfterTest();
		}
	}

	/**
	 * 
	 *
	 */
	public void test_readActivities() {
		ParametersPref pp = new ParametersPref();
		
		try {
			pp.savePrefBeforeTest();
			DxLoadData ld = new DxLoadData();
			byte[] dataloaded = ld.filterBadChars(_pathForFacsAdminDim
					+ "coursAdm.sig");
			DataExchange de = ld.insertHeader(dataloaded);
			DxSetOfInstructors dxsoiTempInst = new DxSetOfInstructors();
			dxsoiTempInst.addInstructor("LUC LAJOIE", null);

			DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
			dxsorTempRooms
					.addRoom(new DxRoom("C1-387", 0, 0, null, null, null));
			dxsorTempRooms
					.addRoom(new DxRoom("C1-330", 0, 0, null, null, null));

			DxActivitiesSitesReader dxasrReader = new DxReadActivitiesSites1dot5(
					de, dxsoiTempInst, dxsorTempRooms, 60, false);

			DxSetOfActivitiesSites dxsoa = dxasrReader
					.readSetOfActivitiesSites();
			assertEquals("test_readActivities: assertEquals 64", 1, dxsoa
					.size());
			assertEquals("test_readActivities: assertEquals true", true, dxsoa
					.areVectorsSync());
			assertNotNull("test_readActivities: assertNotNull First", dxsoa
					.getAllActivities());
			
			assertNotNull("test_readActivities: assertNotNull in Activity", dxsoa
					.getAllActivities().getResource("ADM101"));
			assertNotNull("test_readActivities: assertNotNull in Activity", dxsoa
					.getAllActivities().getResource("MQG542"));
			assertNull("test_readActivities: assertNotNull in Activity", dxsoa
					.getAllActivities().getResource("RGR101"));

			 assertEquals("readActivities: assertEquals 1", 1, dxsoa
						.getAllActivities().getResourceKey("ADM101"));
			 
			 assertEquals("readActivities: assertEquals 1", 59, dxsoa
						.getAllActivities().getResourceKey("MQG542"));
			 
			 assertEquals("readActivities: assertEquals 59", 59, dxsoa
						.getAllActivities().size());
			 
			 dxsoa
				.getAllActivities().removeResource(1); 
			 
			 assertEquals("readActivities: assertEquals 58", 58, dxsoa
						.getAllActivities().size());
			 
			 assertEquals("readActivities: assertEquals 1", 59, dxsoa
						.getAllActivities().getResourceKey("MQG542"));
			
			 dxsoa
				.getAllActivities().removeResource(1); 
			 
			 assertEquals("readActivities: assertEquals 58", 58, dxsoa
						.getAllActivities().size());
			

		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_basicData: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_readInstructors");
			e.printStackTrace();
		} finally {
			pp.restorePrefAfterTest();
		}
	}

}
