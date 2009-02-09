package dTest.dInternal.dData.dActivities;

import dInternal.DxLoadData;
import dInternal.dData.dActivities.DxActivity;
import dInternal.dData.dActivities.DxActivitySite;
import dInternal.dData.dActivities.DxReadActivitiesSites1dot5;
import dInternal.dData.dActivities.DxReadActivitiesSites1dot6;
import dInternal.dData.dActivities.DxSetOfActivitiesSites;
import dInternal.dData.dActivities.DxType;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import dInternal.dData.dRooms.DxRoom;
import dInternal.dData.dRooms.DxSetOfRooms;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DxSetOfActivitiesSitesTest extends TestCase {
	public DxSetOfActivitiesSitesTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxSetOfActivitiesSitesTest.class);
	} // end suite

	/**
	 * test_buildSetOfResources1_5, test that all elements of the activity are
	 * added in the activities file
	 */
	public void test_buildSetOfResources1_5() {
		String tokens = "ADM1111  01" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "LUC LAJOIE" + "\r\n" + "1" + "\r\n" + "3" + "\r\n" + "1 12"
				+ "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n" + "0" + "\r\n"
				+ "0" + "\r\n" + "0" + "\r\n" + "ADM1111  02" + "\r\n" + "1"
				+ "\r\n" + "1" + "\r\n" + "RÉAL CAOUETTE" + "\r\n" + "1"
				+ "\r\n" + "3" + "\r\n" + "1 12" + "\r\n" + "1" + "\r\n"
				+ "C1-387" + "\r\n" + "0" + "\r\n" + "0" + "\r\n" + "0"
				+ "\r\n" + "ADM1112  01" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "Yannick" + "\r\n" + "1" + "\r\n" + "3" + "\r\n" + "1 12"
				+ "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n" + "0" + "\r\n"
				+ "0" + "\r\n" + "0" + "\r\n" + "GEI4411  01" + "\r\n" + "1"
				+ "\r\n" + "1" + "\r\n" + "Ruben" + "\r\n" + "2" + "\r\n"
				+ "3 2" + "\r\n" + "1 12 2 2" + "\r\n" + "1 1" + "\r\n"
				+ "C1-387 C1-330" + "\r\n" + "0 0" + "\r\n" + "0 0" + "\r\n"
				+ "0 0" + "\r\n";
		DxSetOfInstructors dxsoiTempInst = new DxSetOfInstructors();
		dxsoiTempInst.addInstructor("LUC LAJOIE", null);
		dxsoiTempInst.addInstructor("RÉAL CAOUETTE", null);
		dxsoiTempInst.addInstructor("Yannick", null);
		dxsoiTempInst.addInstructor("Ruben", null);

		DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
		dxsorTempRooms.addRoom(new DxRoom("C1-387", 0, 0, null, null, null));
		dxsorTempRooms.addRoom(new DxRoom("C1-330", 0, 0, null, null, null));

		DxLoadData ld = new DxLoadData();

		DxReadActivitiesSites1dot5 dxrasSiteReader = new DxReadActivitiesSites1dot5(
				ld.insertHeader(tokens.getBytes()), dxsoiTempInst,
				dxsorTempRooms, 60, false);
		DxSetOfActivitiesSites dxsoasAct = null;
		try {
			dxsoasAct = dxrasSiteReader.readSetOfActivitiesSites();
			DxActivitySite dxasShe = dxsoasAct.getActivitySite("SHE");
			assertNotNull("test1_buildSetOfResources1_5: assertNotNull 0",
					dxasShe);

			DxActivity dxaAdm111 = dxasShe.getActivity("ADM111");
			assertNotNull("test2_buildSetOfResources1_5: assertNotNull 1",
					dxaAdm111);

			DxType dxtType1 = dxaAdm111.getType("1");
			assertNotNull("test2_buildSetOfResources1_5: assertNotNull 2",
					dxtType1);

			assertEquals("test0_buildSetOfResources1_5: assertEquals 0", 1,
					dxsoasAct.size());
			assertEquals("test3_buildSetOfResources1_5: assertEquals 1", 2,
					dxaAdm111.getTypeCount());
			assertEquals("test4_buildSetOfResources1_5: assertEquals 2", 2,
					dxtType1.getSectionCount());
		} catch (Exception e) {
			assertTrue("test_buildSetOfResources1_5: Read failed", false);
		}

	}

	public void test_buildSetOfResources1_6() {
		String tokens = "Diamant1.6" + "\r\n" + "ADM1111  01 SHE 50" + "\r\n"
				+ "1" + "\r\n" + "1" + "\r\n" + "LUC LAJOIE" + "\r\n" + "1"
				+ "\r\n" + "3" + "\r\n" + "1 12" + "\r\n" + "1" + "\r\n"
				+ "C1-387" + "\r\n" + "0" + "\r\n" + "0" + "\r\n" + "0"
				+ "\r\n" + "ADM1111  02 SHE 99999" + "\r\n" + "1" + "\r\n"
				+ "1" + "\r\n" + "RÉAL CAOUETTE" + "\r\n" + "1" + "\r\n" + "3"
				+ "\r\n" + "1 12" + "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n"
				+ "0" + "\r\n" + "0" + "\r\n" + "0" + "\r\n"
				+ "ADM1112  01 SHE 99999" + "\r\n" + "1" + "\r\n" + "1"
				+ "\r\n" + "Yannick" + "\r\n" + "1" + "\r\n" + "3" + "\r\n"
				+ "1 12" + "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n" + "0"
				+ "\r\n" + "0" + "\r\n" + "0" + "\r\n"
				+ "GEI4411  01 SHE 99999" + "\r\n" + "1" + "\r\n" + "1"
				+ "\r\n" + "Ruben" + "\r\n" + "2" + "\r\n" + "3 2" + "\r\n"
				+ "1 12 2 2" + "\r\n" + "1 1" + "\r\n" + "C1-387 C1-330"
				+ "\r\n" + "0 0" + "\r\n" + "0 0" + "\r\n" + "0 0" + "\r\n";

		DxSetOfInstructors dxsoiTempInst = new DxSetOfInstructors();
		dxsoiTempInst.addInstructor("LUC LAJOIE", null);
		dxsoiTempInst.addInstructor("RÉAL CAOUETTE", null);
		dxsoiTempInst.addInstructor("Yannick", null);
		dxsoiTempInst.addInstructor("Ruben", null);

		DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
		dxsorTempRooms.addRoom(new DxRoom("C1-387", 0, 0, null, null, null));
		dxsorTempRooms.addRoom(new DxRoom("C1-330", 0, 0, null, null, null));

		DxLoadData ld = new DxLoadData();

		DxReadActivitiesSites1dot6 dxrasSiteReader = new DxReadActivitiesSites1dot6(
				ld.insertHeader(tokens.getBytes()), dxsoiTempInst,
				dxsorTempRooms, 60, false);
		DxSetOfActivitiesSites dxsoasAct = null;
		try {
			dxsoasAct = dxrasSiteReader.readSetOfActivitiesSites();
			DxActivitySite dxasSite = dxsoasAct.getActivitySite("SHE");
			assertNotNull("test1_buildSetOfResources1_6: assertNotNull 0",
					dxasSite);

			DxActivity dxaActivity = dxasSite.getActivity("ADM111");
			assertNotNull("test1_buildSetOfResources1_6: assertNotNull 1",
					dxaActivity);

			DxType dxtType = dxaActivity.getType("1");
			assertNotNull("test1_buildSetOfResources1_6: assertNotNull 2",
					dxtType);

			assertEquals("test0_buildSetOfResources1_6: assertEquals 0", 1,
					dxsoasAct.size());
			assertEquals("test3_buildSetOfResources1_6: assertEquals 1", 2,
					dxaActivity.getTypeCount());
			assertEquals("test4_buildSetOfResources1_6: assertEquals 2", 2,
					dxtType.getSectionCount());
			// assertEquals("test5_buildSetOfResources1_6: assertEquals 3", 50,
			// dxaActivity.getCapacity());
		} catch (Exception e) {
			assertTrue("test_buildSetOfResources1_6: Read failed", false);
		}

	}
}
