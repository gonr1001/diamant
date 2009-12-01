/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
package dTest.dInternal.dData.dActivities;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import dInternal.DxLoadData;
//import dInternal.dData.DLoadData;
import dInternal.dData.DxAvailability;
import dInternal.dData.dActivities.DxActivitiesSitesReader;
import dInternal.dData.dActivities.DxActivity;
import dInternal.dData.dActivities.DxActivitySite;
import dInternal.dData.dActivities.DxAssignement;
import dInternal.dData.dActivities.DxReadActivitiesSites1dot5;
import dInternal.dData.dActivities.DxSection;

import dInternal.dData.dActivities.DxSetOfActivitiesSites;
import dInternal.dData.dActivities.DxType;
import dInternal.dData.dActivities.DxUnity;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import dInternal.dData.dRooms.DxRoom;
import dInternal.dData.dRooms.DxSetOfRooms;

public class DxSetOfActivitiesTest extends TestCase {

	public DxSetOfActivitiesTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxSetOfActivitiesTest.class);
	} // end suite

	/**
	 * test_analyseTokens, test that analyse the empty activity name in the
	 * activities file
	 */
	public void test_analyseTokens() {
		String tokens = "" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "LUC LAJOIE" + "\r\n" + "1" + "\r\n" + "3" + "\r\n" + "1 12"
				+ "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n" + "0" + "\r\n"
				+ "0" + "\r\n" + "0" + "\r\n" + "ADM1112  A" + "\r\n" + "1"
				+ "\r\n" + "1" + "\r\n" + "LUC LAJOIE" + "\r\n" + "1" + "\r\n"
				+ "3" + "\r\n" + "1 12" + "\r\n" + "1" + "\r\n" + "C1-387"
				+ "\r\n" + "0" + "\r\n" + "0" + "\r\n" + "0" + "\r\n";

		DxSetOfInstructors dxsoiTempInst = new DxSetOfInstructors();
		dxsoiTempInst.addInstructor("LUC LAJOIE", null);

		DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
		dxsorTempRooms.addRoom(new DxRoom("C1-387", 0, 0, null, null, null));
		dxsorTempRooms.addRoom(new DxRoom("C1-330", 0, 0, null, null, null));

		DxLoadData ld = new DxLoadData();

		DxActivitiesSitesReader dxasrReader = new DxReadActivitiesSites1dot5(ld
				.insertHeader(tokens.getBytes()), dxsoiTempInst,
				dxsorTempRooms, 60, false);
		try {
			DxSetOfActivitiesSites dxsoasSites = dxasrReader
					.readSetOfActivitiesSites();
			// assertTrue("test_analyseTokens: assertTrue",false);
		} catch (Exception e) {
			// TODO
			// assertEquals("test_analyseTokens: assertEquals",
			// DConst.ACTI_TEXT1,
			// e.getMessage().substring(0,
			// DConst.ACTI_TEXT1.length()));
		}

	}

	/**
	 * test1_analyseTokens, test that analyse the activity visibility in the
	 * activities file
	 */
	public void test1_analyseTokens() {
		String tokens = "ADM1111  01" + "\r\n" + "1x" + "\r\n" + "1" + "\r\n"
				+ "LUC LAJOIE" + "\r\n" + "1" + "\r\n" + "3" + "\r\n" + "1 12"
				+ "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n" + "0" + "\r\n"
				+ "0" + "\r\n" + "0" + "\r\n" + "ADM1112  01" + "\r\n" + "1"
				+ "\r\n" + "1" + "\r\n" + "LUC LAJOIE" + "\r\n" + "1" + "\r\n"
				+ "3" + "\r\n" + "1 12" + "\r\n" + "1" + "\r\n" + "C1-387"
				+ "\r\n" + "0" + "\r\n" + "0" + "\r\n" + "0" + "\r\n";

		DxSetOfInstructors dxsoiTempInst = new DxSetOfInstructors();
		dxsoiTempInst.addInstructor("LUC LAJOIE", null);

		DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
		dxsorTempRooms.addRoom(new DxRoom("C1-387", 0, 0, null, null, null));
		dxsorTempRooms.addRoom(new DxRoom("C1-330", 0, 0, null, null, null));

		DxLoadData ld = new DxLoadData();

		DxActivitiesSitesReader dxasrReader = new DxReadActivitiesSites1dot5(ld
				.insertHeader(tokens.getBytes()), dxsoiTempInst,
				dxsorTempRooms, 60, false);

		try {
			DxSetOfActivitiesSites dxsoasSites = dxasrReader
					.readSetOfActivitiesSites();
			// assertTrue("test_analyseTokens: assertTrue",false);
		} catch (Exception e) {
			// TODO
			// assertEquals("test1_analyseTokens: assertEquals",
			// DConst.ACTI_TEXT2,
			// e.getMessage().substring(0,
			// DConst.ACTI_TEXT2.length()));
		}

	}

	/**
	 * test2_analyseTokens, test that analyse the number of activities in the
	 * activities file
	 */
	public void test2_analyseTokens() {
		String tokens = "ADM1111  01" + "\r\n" + "1" + "\r\n" + "1k" + "\r\n"
				+ "LUC LAJOIE" + "\r\n" + "1" + "\r\n" + "3" + "\r\n" + "1 12"
				+ "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n" + "0" + "\r\n"
				+ "0" + "\r\n" + "0" + "\r\n" + "ADM1112  01" + "\r\n" + "1"
				+ "\r\n" + "1" + "\r\n" + "LUC LAJOIE" + "\r\n" + "1" + "\r\n"
				+ "3" + "\r\n" + "1 12" + "\r\n" + "1" + "\r\n" + "C1-387"
				+ "\r\n" + "0" + "\r\n" + "0" + "\r\n" + "0" + "\r\n";

		DxSetOfInstructors dxsoiTempInst = new DxSetOfInstructors();
		dxsoiTempInst.addInstructor("LUC LAJOIE", null);

		DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
		dxsorTempRooms.addRoom(new DxRoom("C1-387", 0, 0, null, null, null));
		dxsorTempRooms.addRoom(new DxRoom("C1-330", 0, 0, null, null, null));

		DxLoadData ld = new DxLoadData();
		DxActivitiesSitesReader dxasrReader = new DxReadActivitiesSites1dot5(ld
				.insertHeader(tokens.getBytes()), dxsoiTempInst,
				dxsorTempRooms, 60, false);

		try {
			DxSetOfActivitiesSites dxsoasSites = dxasrReader
					.readSetOfActivitiesSites();
			// assertTrue("test_analyseTokens: assertTrue",false);
		} catch (Exception e) {
			// TODO
			// assertEquals("test2_analyseTokens: assertEquals",
			// DConst.ACTI_TEXT3,
			// e.getMessage().substring(0,
			// DConst.ACTI_TEXT3.length()));
		}

	}

	/**
	 * test3_analyseTokens, test that the instructor name is empty in the
	 * activities file, the result is ACTI_TEXT5, because the next token is used
	 * as instruction name then other error will be dtected.
	 * 
	 */
	public void test3_analyseTokens() {
		String tokens = "ADM1111  01" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "" + "\r\n" + "1" + "\r\n" + "3" + "\r\n" + "1 12" + "\r\n"
				+ "1" + "\r\n" + "C1-387" + "\r\n" + "0" + "\r\n" + "0"
				+ "\r\n" + "0" + "\r\n" + "ADM1112  01" + "\r\n" + "1" + "\r\n"
				+ "1" + "\r\n" + "LUC LAJOIE" + "\r\n" + "1" + "\r\n" + "3"
				+ "\r\n" + "1 12" + "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n"
				+ "0" + "\r\n" + "0" + "\r\n" + "0" + "\r\n";

		DxSetOfInstructors dxsoiTempInst = new DxSetOfInstructors();
		dxsoiTempInst.addInstructor("LUC LAJOIE", null);

		DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
		dxsorTempRooms.addRoom(new DxRoom("C1-387", 0, 0, null, null, null));
		dxsorTempRooms.addRoom(new DxRoom("C1-330", 0, 0, null, null, null));

		DxLoadData ld = new DxLoadData();

		DxActivitiesSitesReader dxasrReader = new DxReadActivitiesSites1dot5(ld
				.insertHeader(tokens.getBytes()), dxsoiTempInst,
				dxsorTempRooms, 60, false);

		try {
			DxSetOfActivitiesSites dxsoasSites = dxasrReader
					.readSetOfActivitiesSites();
			// assertTrue("test_analyseTokens: assertTrue",false);
		} catch (Exception e) {
			// TODO
			// assertEquals("test3_analyseTokens: assertEquals",
			// DConst.ACTI_TEXT5,
			// setOfActivities.getError().substring(0,
			// DConst.ACTI_TEXT5.length()));
		}
	}

	/**
	 * test4_analyseTokens, test that analyse the number of blocs in the
	 * activities file
	 */
	public void test4_analyseTokens() {
		String tokens = "ADM1111  01" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "LUC LAJOIE" + "\r\n" + "1" + "\r\n" + "3" + "\r\n" + "1 12"
				+ "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n" + "0" + "\r\n"
				+ "0" + "\r\n" + "0" + "\r\n" + "ADM1112  01" + "\r\n" + "1"
				+ "\r\n" + "1" + "\r\n" + "LUC LAJOIE" + "\r\n" + "1v" + "\r\n"
				+ "3" + "\r\n" + "1 12" + "\r\n" + "1" + "\r\n" + "C1-387"
				+ "\r\n" + "0" + "\r\n" + "0" + "\r\n" + "0" + "\r\n";

		DxSetOfInstructors dxsoiTempInst = new DxSetOfInstructors();
		dxsoiTempInst.addInstructor("LUC LAJOIE", null);

		DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
		dxsorTempRooms.addRoom(new DxRoom("C1-387", 0, 0, null, null, null));
		dxsorTempRooms.addRoom(new DxRoom("C1-330", 0, 0, null, null, null));

		DxLoadData ld = new DxLoadData();

		DxActivitiesSitesReader dxasrReader = new DxReadActivitiesSites1dot5(ld
				.insertHeader(tokens.getBytes()), dxsoiTempInst,
				dxsorTempRooms, 60, false);

		try {
			DxSetOfActivitiesSites dxsoasSites = dxasrReader
					.readSetOfActivitiesSites();
			// assertTrue("test_analyseTokens: assertTrue",false);
		} catch (Exception e) {
			// TODO
			// assertEquals("test4_analyseTokens: assertEquals 1",
			// DConst.ACTI_TEXT5,e.getMessage().substring(0,
			// DConst.ACTI_TEXT5.length()));
		}
	}

	/**
	 * test5_analyseTokens, test that analyse the number of blocs in the
	 * activities file
	 */
	public void test5_analyseTokens() {
		String tokens = "ADM1111  01" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "LUC LAJOIE" + "\r\n" + "2" + "\r\n" + "3" + "\r\n" + "1 12"
				+ "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n" + "0" + "\r\n"
				+ "0" + "\r\n" + "0" + "\r\n" + "ADM1112  01" + "\r\n" + "1"
				+ "\r\n" + "1" + "\r\n" + "LUC LAJOIE" + "\r\n" + "1" + "\r\n"
				+ "3" + "\r\n" + "1 12" + "\r\n" + "1" + "\r\n" + "C1-387"
				+ "\r\n" + "0" + "\r\n" + "0" + "\r\n" + "0" + "\r\n";

		DxSetOfInstructors dxsoiTempInst = new DxSetOfInstructors();
		dxsoiTempInst.addInstructor("LUC LAJOIE", null);

		DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
		dxsorTempRooms.addRoom(new DxRoom("C1-387", 0, 0, null, null, null));
		dxsorTempRooms.addRoom(new DxRoom("C1-330", 0, 0, null, null, null));

		DxLoadData ld = new DxLoadData();
		DxActivitiesSitesReader dxasrReader = new DxReadActivitiesSites1dot5(ld
				.insertHeader(tokens.getBytes()), dxsoiTempInst,
				dxsorTempRooms, 60, false);

		try {
			DxSetOfActivitiesSites dxsoasSites = dxasrReader
					.readSetOfActivitiesSites();
			// assertTrue("test_analyseTokens: assertTrue",false);
		} catch (Exception e) {
			// TODO
			// assertEquals("test5_analyseTokens: assertEquals",
			// DConst.ACTI_TEXT5, e.getMessage().substring(0,
			// DConst.ACTI_TEXT5.length()));
		}

	}

	/**
	 * test6_analyseTokens, test that analyse the number of blocs in the
	 * activities file
	 */
	public void test6_analyseTokens() {
		String tokens = "ADM1111  01" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "LUC LAJOIE" + "\r\n" + "1" + "\r\n" + "3 2" + "\r\n"
				+ "1 12" + "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n" + "0"
				+ "\r\n" + "0" + "\r\n" + "0" + "\r\n" + "ADM1112  01" + "\r\n"
				+ "1" + "\r\n" + "1" + "\r\n" + "LUC LAJOIE" + "\r\n" + "1"
				+ "\r\n" + "3" + "\r\n" + "1 12" + "\r\n" + "1" + "\r\n"
				+ "C1-387" + "\r\n" + "0" + "\r\n" + "0" + "\r\n" + "0"
				+ "\r\n";

		DxSetOfInstructors dxsoiTempInst = new DxSetOfInstructors();
		dxsoiTempInst.addInstructor("LUC LAJOIE", null);

		DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
		dxsorTempRooms.addRoom(new DxRoom("C1-387", 0, 0, null, null, null));
		dxsorTempRooms.addRoom(new DxRoom("C1-330", 0, 0, null, null, null));

		DxLoadData ld = new DxLoadData();

		DxActivitiesSitesReader dxasrReader = new DxReadActivitiesSites1dot5(ld
				.insertHeader(tokens.getBytes()), dxsoiTempInst,
				dxsorTempRooms, 60, false);

		try {
			DxSetOfActivitiesSites dxsoasSites = dxasrReader
					.readSetOfActivitiesSites();
			// assertTrue("test_analyseTokens: assertTrue",false);
		} catch (Exception e) {
			// TODO
			// assertEquals("test5_analyseTokens: assertEquals",
			// DConst.ACTI_TEXT5, e.getMessage().substring(0,
			// DConst.ACTI_TEXT5.length()));
		}
	}

	/**
	 * test7_analyseTokens, test that analyse the number of blocs in the
	 * activities file
	 */
	public void test7_analyseTokens() {
		String tokens = "ADM1111  01" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "LUC LAJOIE" + "\r\n" + "1" + "\r\n" + "3" + "\r\n"
				+ "1 12 1" + "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n" + "0"
				+ "\r\n" + "0" + "\r\n" + "0" + "\r\n" + "ADM1112  01" + "\r\n"
				+ "1" + "\r\n" + "1" + "\r\n" + "LUC LAJOIE" + "\r\n" + "1"
				+ "\r\n" + "3" + "\r\n" + "1 12" + "\r\n" + "1" + "\r\n"
				+ "C1-387" + "\r\n" + "0" + "\r\n" + "0" + "\r\n" + "0"
				+ "\r\n";

		DxSetOfInstructors dxsoiTempInst = new DxSetOfInstructors();
		dxsoiTempInst.addInstructor("LUC LAJOIE", null);

		DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
		dxsorTempRooms.addRoom(new DxRoom("C1-387", 0, 0, null, null, null));
		dxsorTempRooms.addRoom(new DxRoom("C1-330", 0, 0, null, null, null));

		DxLoadData ld = new DxLoadData();

		DxActivitiesSitesReader dxasrReader = new DxReadActivitiesSites1dot5(ld
				.insertHeader(tokens.getBytes()), dxsoiTempInst,
				dxsorTempRooms, 60, false);

		try {
			DxSetOfActivitiesSites dxsoasSites = dxasrReader
					.readSetOfActivitiesSites();
			// assertTrue("test_analyseTokens: assertTrue",false);
		} catch (Exception e) {
			// TODO
			// assertEquals("test5_analyseTokens: assertEquals",
			// DConst.ACTI_TEXT5, e.getMessage().substring(0,
			// DConst.ACTI_TEXT5.length()));
		}
	}

	/**
	 * test8_analyseTokens, test that analyse the duration of blocs in the
	 * activities file
	 */
	public void test8_analyseTokens() {
		String tokens = "ADM1111  01" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "LUC LAJOIE" + "\r\n" + "1" + "\r\n" + "3k" + "\r\n" + "1 12"
				+ "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n" + "0" + "\r\n"
				+ "0" + "\r\n" + "0" + "\r\n" + "ADM1112  01" + "\r\n" + "1"
				+ "\r\n" + "1" + "\r\n" + "LUC LAJOIE" + "\r\n" + "1" + "\r\n"
				+ "3" + "\r\n" + "1 12" + "\r\n" + "1" + "\r\n" + "C1-387"
				+ "\r\n" + "0" + "\r\n" + "0" + "\r\n" + "0" + "\r\n";

		DxSetOfInstructors dxsoiTempInst = new DxSetOfInstructors();
		dxsoiTempInst.addInstructor("LUC LAJOIE", null);

		DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
		dxsorTempRooms.addRoom(new DxRoom("C1-387", 0, 0, null, null, null));
		dxsorTempRooms.addRoom(new DxRoom("C1-330", 0, 0, null, null, null));

		DxLoadData ld = new DxLoadData();

		DxActivitiesSitesReader dxasrReader = new DxReadActivitiesSites1dot5(ld
				.insertHeader(tokens.getBytes()), dxsoiTempInst,
				dxsorTempRooms, 60, false);

		try {
			DxSetOfActivitiesSites dxsoasSites = dxasrReader
					.readSetOfActivitiesSites();
			// assertTrue("test_analyseTokens: assertTrue",false);
		} catch (Exception e) {
			// TODO
			// assertEquals("test5_analyseTokens: assertEquals",
			// DConst.ACTI_TEXT7, e.getMessage().substring(0,
			// DConst.ACTI_TEXT7.length()));
		}
	}

	/**
	 * test9_analyseTokens, test that analyse days and periods format of blocs
	 * in the activities file
	 */
	public void test9_analyseTokens() {
		String tokens = "ADM1111  01" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "LUC LAJOIE" + "\r\n" + "1" + "\r\n" + "3" + "\r\n" + "1k 12"
				+ "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n" + "0" + "\r\n"
				+ "0" + "\r\n" + "0" + "\r\n" + "ADM1112  01" + "\r\n" + "1"
				+ "\r\n" + "1" + "\r\n" + "LUC LAJOIE" + "\r\n" + "1" + "\r\n"
				+ "3" + "\r\n" + "1 12" + "\r\n" + "1" + "\r\n" + "C1-387"
				+ "\r\n" + "0" + "\r\n" + "0" + "\r\n" + "0" + "\r\n";

		DxSetOfInstructors dxsoiTempInst = new DxSetOfInstructors();
		dxsoiTempInst.addInstructor("LUC LAJOIE", null);

		DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
		dxsorTempRooms.addRoom(new DxRoom("C1-387", 0, 0, null, null, null));
		dxsorTempRooms.addRoom(new DxRoom("C1-330", 0, 0, null, null, null));

		DxLoadData ld = new DxLoadData();
		DxActivitiesSitesReader dxasrReader = new DxReadActivitiesSites1dot5(ld
				.insertHeader(tokens.getBytes()), dxsoiTempInst,
				dxsorTempRooms, 60, false);

		try {
			DxSetOfActivitiesSites dxsoasSites = dxasrReader
					.readSetOfActivitiesSites();
			// assertTrue("test_analyseTokens: assertTrue",false);
		} catch (Exception e) {
			// TODO
			// assertEquals("test5_analyseTokens: assertEquals",
			// DConst.ACTI_TEXT8, e.getMessage().substring(0,
			// DConst.ACTI_TEXT8.length()));
		}
	}

	/**
	 * test10_analyseTokens, test that analyse the fixed rooms state in the
	 * activities file
	 */
	public void test10_analyseTokens() {
		String tokens = "ADM1111  01" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "LUC LAJOIE" + "\r\n" + "1" + "\r\n" + "3" + "\r\n" + "1 12"
				+ "\r\n" + "1v" + "\r\n" + "C1-387" + "\r\n" + "0" + "\r\n"
				+ "0" + "\r\n" + "0" + "\r\n" + "ADM1112  01" + "\r\n" + "1"
				+ "\r\n" + "1" + "\r\n" + "LUC LAJOIE" + "\r\n" + "1" + "\r\n"
				+ "3" + "\r\n" + "1 12" + "\r\n" + "1" + "\r\n" + "C1-387"
				+ "\r\n" + "0" + "\r\n" + "0" + "\r\n" + "0" + "\r\n";

		DxSetOfInstructors dxsoiTempInst = new DxSetOfInstructors();
		dxsoiTempInst.addInstructor("LUC LAJOIE", null);

		DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
		dxsorTempRooms.addRoom(new DxRoom("C1-387", 0, 0, null, null, null));
		dxsorTempRooms.addRoom(new DxRoom("C1-330", 0, 0, null, null, null));

		DxLoadData ld = new DxLoadData();
		DxActivitiesSitesReader dxasrReader = new DxReadActivitiesSites1dot5(ld
				.insertHeader(tokens.getBytes()), dxsoiTempInst,
				dxsorTempRooms, 60, false);

		try {
			DxSetOfActivitiesSites dxsoasSites = dxasrReader
					.readSetOfActivitiesSites();
			// assertTrue("test_analyseTokens: assertTrue",false);
		} catch (Exception e) {
			// TODO
			// assertEquals("test5_analyseTokens: assertEquals",
			// DConst.ACTI_TEXT9, e.getMessage().substring(0,
			// DConst.ACTI_TEXT9.length()));
		}
	}

	/**
	 * test11_analyseTokens, test that analyse the wrong room name in the
	 * activities file
	 */
	public void test11_analyseTokens() {
		String tokens = "ADM1111  01" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "LUC LAJOIE" + "\r\n" + "1" + "\r\n" + "3" + "\r\n" + "1 12"
				+ "\r\n" + "1" + "\r\n" + "C1 387" + "\r\n" + "0" + "\r\n"
				+ "0" + "\r\n" + "0" + "\r\n" + "ADM1112  01" + "\r\n" + "1"
				+ "\r\n" + "1" + "\r\n" + "LUC LAJOIE" + "\r\n" + "1" + "\r\n"
				+ "3" + "\r\n" + "1 12" + "\r\n" + "1" + "\r\n" + "C1-387"
				+ "\r\n" + "0" + "\r\n" + "0" + "\r\n" + "0" + "\r\n";

		DxSetOfInstructors dxsoiTempInst = new DxSetOfInstructors();
		dxsoiTempInst.addInstructor("LUC LAJOIE", null);

		DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
		dxsorTempRooms.addRoom(new DxRoom("C1-387", 0, 0, null, null, null));
		dxsorTempRooms.addRoom(new DxRoom("C1-330", 0, 0, null, null, null));

		DxLoadData ld = new DxLoadData();
		DxActivitiesSitesReader dxasrReader = new DxReadActivitiesSites1dot5(ld
				.insertHeader(tokens.getBytes()), dxsoiTempInst,
				dxsorTempRooms, 60, false);

		try {
			DxSetOfActivitiesSites dxsoasSites = dxasrReader
					.readSetOfActivitiesSites();
			// assertTrue("test_analyseTokens: assertTrue",false);
		} catch (Exception e) {
			// TODO
			// assertEquals("test5_analyseTokens: assertEquals",
			// DConst.ACTI_TEXT10, e.getMessage().substring(0,
			// DConst.ACTI_TEXT10.length()));
		}
	}

	/**
	 * test12_analyseTokens, test that analyse the type of rooms in the
	 * activities file
	 */
	public void test12_analyseTokens() {
		String tokens = "ADM1111  01" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "LUC LAJOIE" + "\r\n" + "1" + "\r\n" + "3" + "\r\n" + "1 12"
				+ "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n" + "0v" + "\r\n"
				+ "0" + "\r\n" + "0" + "\r\n" + "ADM1112  01" + "\r\n" + "1"
				+ "\r\n" + "1" + "\r\n" + "LUC LAJOIE" + "\r\n" + "1" + "\r\n"
				+ "3" + "\r\n" + "1 12" + "\r\n" + "1" + "\r\n" + "C1-387"
				+ "\r\n" + "0" + "\r\n" + "0" + "\r\n" + "0" + "\r\n";

		DxSetOfInstructors dxsoiTempInst = new DxSetOfInstructors();
		dxsoiTempInst.addInstructor("LUC LAJOIE", null);

		DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
		dxsorTempRooms.addRoom(new DxRoom("C1-387", 0, 0, null, null, null));
		dxsorTempRooms.addRoom(new DxRoom("C1-330", 0, 0, null, null, null));

		DxLoadData ld = new DxLoadData();
		DxActivitiesSitesReader dxasrReader = new DxReadActivitiesSites1dot5(ld
				.insertHeader(tokens.getBytes()), dxsoiTempInst,
				dxsorTempRooms, 60, false);

		try {
			DxSetOfActivitiesSites dxsoasSites = dxasrReader
					.readSetOfActivitiesSites();
			// assertTrue("test_analyseTokens: assertTrue",false);
		} catch (Exception e) {
			// TODO
			// assertEquals("test5_analyseTokens: assertEquals",
			// DConst.ACTI_TEXT11, e.getMessage().substring(0,
			// DConst.ACTI_TEXT11.length()));
		}
	}

	/**
	 * test13_analyseTokens, test that analyse the type of rooms idem in the
	 * activities file
	 */
	public void test13_analyseTokens() {
		String tokens = "ADM1111  01" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "LUC LAJOIE" + "\r\n" + "1" + "\r\n" + "3" + "\r\n" + "1 12"
				+ "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n" + "0" + "\r\n"
				+ "0G" + "\r\n" + "0" + "\r\n" + "ADM1112  01" + "\r\n" + "1"
				+ "\r\n" + "1" + "\r\n" + "LUC LAJOIE" + "\r\n" + "1" + "\r\n"
				+ "3" + "\r\n" + "1 12" + "\r\n" + "1" + "\r\n" + "C1-387"
				+ "\r\n" + "0" + "\r\n" + "0" + "\r\n" + "0" + "\r\n";

		DxSetOfInstructors dxsoiTempInst = new DxSetOfInstructors();
		dxsoiTempInst.addInstructor("LUC LAJOIE", null);

		DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
		dxsorTempRooms.addRoom(new DxRoom("C1-387", 0, 0, null, null, null));
		dxsorTempRooms.addRoom(new DxRoom("C1-330", 0, 0, null, null, null));

		DxLoadData ld = new DxLoadData();

		DxActivitiesSitesReader dxasrReader = new DxReadActivitiesSites1dot5(ld
				.insertHeader(tokens.getBytes()), dxsoiTempInst,
				dxsorTempRooms, 60, false);

		try {
			DxSetOfActivitiesSites dxsoasSites = dxasrReader
					.readSetOfActivitiesSites();
			// assertTrue("test_analyseTokens: assertTrue",false);
		} catch (Exception e) {
			// TODO
			// assertEquals("test5_analyseTokens: assertEquals",
			// DConst.ACTI_TEXT11, e.getMessage().substring(0,
			// DConst.ACTI_TEXT11.length()));
		}
	}

	/**
	 * test14_analyseTokens, test that analyse the format of pre-affected rooms
	 * in the activities file
	 */
	public void test14_analyseTokens() {
		String tokens = "ADM1111  01" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "LUC LAJOIE" + "\r\n" + "1" + "\r\n" + "3" + "\r\n" + "1 12"
				+ "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n" + "0" + "\r\n"
				+ "0" + "\r\n" + "0W" + "\r\n" + "ADM1112  01" + "\r\n" + "1"
				+ "\r\n" + "1" + "\r\n" + "LUC LAJOIE" + "\r\n" + "1" + "\r\n"
				+ "3" + "\r\n" + "1 12" + "\r\n" + "1" + "\r\n" + "C1-387"
				+ "\r\n" + "0" + "\r\n" + "0" + "\r\n" + "0" + "\r\n";

		DxSetOfInstructors dxsoiTempInst = new DxSetOfInstructors();
		dxsoiTempInst.addInstructor("LUC LAJOIE", null);

		DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
		dxsorTempRooms.addRoom(new DxRoom("C1-387", 0, 0, null, null, null));
		dxsorTempRooms.addRoom(new DxRoom("C1-330", 0, 0, null, null, null));

		DxLoadData ld = new DxLoadData();
		DxActivitiesSitesReader dxasrReader = new DxReadActivitiesSites1dot5(ld
				.insertHeader(tokens.getBytes()), dxsoiTempInst,
				dxsorTempRooms, 60, false);

		try {
			DxSetOfActivitiesSites dxsoasSites = dxasrReader
					.readSetOfActivitiesSites();
			// assertTrue("test_analyseTokens: assertTrue",false);
		} catch (Exception e) {
			// TODO
			// assertEquals("test5_analyseTokens: assertEquals",
			// DConst.ACTI_TEXT12, e.getMessage().substring(0,
			// DConst.ACTI_TEXT12.length()));
		}
	}

	/**
	 * test_addActivity, test that all elements of the activity are added in the
	 * activities file
	 */
	public void test_addActivity() {
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
		DxAvailability aTemp = new DxAvailability();
        aTemp.addDayAvailability("1 5 1");
        aTemp.addDayAvailability("5 5 1 1");
        aTemp.addDayAvailability("5 5 5 5 1");
        aTemp.addDayAvailability("1 1 1 5 5 5");
        aTemp.addDayAvailability("1 5 1 5 1 5 1");
		dxsoiTempInst.addInstructor("LUC LAJOIE", aTemp);
		dxsoiTempInst.addInstructor("RÉAL CAOUETTE", aTemp);
		dxsoiTempInst.addInstructor("Yannick", aTemp);
		dxsoiTempInst.addInstructor("Ruben", aTemp);

		DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
		dxsorTempRooms.addRoom(new DxRoom("C1-387", 0, 0, null, null, aTemp));
		dxsorTempRooms.addRoom(new DxRoom("C1-330", 0, 0, null, null, aTemp));

		DxLoadData ld = new DxLoadData();
		DxActivitiesSitesReader dxasrReader = new DxReadActivitiesSites1dot5(ld
				.insertHeader(tokens.getBytes()), dxsoiTempInst,
				dxsorTempRooms, 60, false);

		DxSetOfActivitiesSites dxsoasSites = null;

		try {
			dxsoasSites = dxasrReader.readSetOfActivitiesSites();
		} catch (Exception e) {
			assertTrue("test_analyseTokens: assert true", false);
		}

		DxActivitySite dxsoaActivitySite = dxsoasSites.getActivitySite("SHE");
		assertNotNull("test0_addActivity: assertNotNull", dxsoaActivitySite);
		assertEquals("test1_addActivity: assertEquals", 2, dxsoaActivitySite
				.getActivityCount());

		DxActivity dxaActivity = dxsoaActivitySite.getActivity("ADM111");
		assertNotNull("test2_addActivity: assertNotNull", dxaActivity);
		assertEquals("test2_addActivity: assertEquals 2", 2, dxaActivity
				.getTypeCount());

		DxType dxtType = dxaActivity.getType("1");
		assertNotNull("test3_addActivity: assertNotNull", dxtType);
		assertEquals("test3_addActivity: assertEquals 3", 2, dxtType
				.getSectionCount());
	}

	/**
	 * test_addActivity, test that all elements of the activity are added in the
	 * activities file
	 */
	public void test_addActivitDiffRooms() {
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
				+ "C1-387 C1-388" + "\r\n" + "0 0" + "\r\n" + "0 0" + "\r\n"
				+ "0 0" + "\r\n";

		DxSetOfInstructors dxsoiTempInst = new DxSetOfInstructors();
		DxAvailability aTemp = new DxAvailability();
        aTemp.addDayAvailability("1 5 1");
        aTemp.addDayAvailability("5 5 1 1");
        aTemp.addDayAvailability("5 5 5 5 1");
        aTemp.addDayAvailability("1 1 1 5 5 5");
        aTemp.addDayAvailability("1 5 1 5 1 5 1");
		dxsoiTempInst.addInstructor("LUC LAJOIE", aTemp);
		dxsoiTempInst.addInstructor("RÉAL CAOUETTE", aTemp);
		dxsoiTempInst.addInstructor("Yannick", aTemp);
		dxsoiTempInst.addInstructor("Ruben", aTemp);

		DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
		dxsorTempRooms.addRoom(new DxRoom("C1-387", 0, 0, null, null, aTemp));
		dxsorTempRooms.addRoom(new DxRoom("C1-388", 0, 0, null, null, aTemp));

		DxLoadData ld = new DxLoadData();

		DxActivitiesSitesReader dxasrReader = new DxReadActivitiesSites1dot5(ld
				.insertHeader(tokens.getBytes()), dxsoiTempInst,
				dxsorTempRooms, 60, false);

		DxSetOfActivitiesSites dxsoasSites = null;

		try {
			dxsoasSites = dxasrReader.readSetOfActivitiesSites();
		} catch (Exception e) {
			assertTrue("test_addActivitDiffRooms: assertTrue", false);
		}

		DxActivitySite dxsoasShe = dxsoasSites.getActivitySite("SHE");
		assertNotNull("test0_addActivitDiffRooms: assertNotNull", dxsoasShe);
		assertEquals("test0_addActivitDiffRooms: assertEquals 0", 2, dxsoasShe
				.getActivityCount());
		DxActivity dxaGEI441 = dxsoasShe.getActivity("GEI441");
		assertNotNull("test1_addActivitDiffRooms: assertNotNull", dxaGEI441);
		assertEquals("test1_addActivitDiffRooms: assertEquals 1", 1, dxaGEI441
				.getTypeCount());

		DxType dxtType1 = dxaGEI441.getType("1");
		assertNotNull("test2_addActivitDiffRooms: assertNotNull", dxtType1);
		assertEquals("test2_addActivitDiffRooms: assertEquals 1", 1, dxtType1
				.getSectionCount());

		DxSection dxsSection = dxtType1.getSection("01");
		assertNotNull("test3_addActivitDiffRooms: assertNotNull", dxsSection);
		assertEquals("test3_addActivitDiffRooms: assertEquals 2", 2, dxsSection
				.getUnityCount());

		DxUnity dxuUnityResc1 = dxsSection.getUnity("1");
		assertNotNull("test4_addActivitDiffRooms: assertNotNull", dxuUnityResc1);
		assertEquals("test4_addActivitDiffRooms: assertEquals 5", 1,
				dxuUnityResc1.getAssignementCount());

		DxUnity dxuUnityResc2 = dxsSection.getUnity("2");
		assertNotNull("test5_addActivitDiffRooms: assertNotNull", dxuUnityResc2);
		assertEquals("test5_addActivitDiffRooms: assertEquals 6", 1,
				dxuUnityResc2.getAssignementCount());

		DxAssignement dxassAssign1 = dxuUnityResc1.getAssignement("1");
		assertNotNull("test6_addActivitDiffRooms: assertNotNull", dxassAssign1);
		assertEquals("test6_addActivitDiffRooms: assertEquals 7", "C1-387",
				dxassAssign1.getRoomName());

		DxAssignement dxassignRes2 = dxuUnityResc2.getAssignement("1");
		assertNotNull("test7_addActivitDiffRooms: assertNotNull", dxassignRes2);
		assertEquals("test7_addActivitDiffRooms: assertEquals 8", "C1-388",
				dxassignRes2.getRoomName());
	}

	/**
	 * test instructorEqualsBlocs,
	 * 
	 * 
	 */
	public void test_nInstructorEqualsnBlocs() {
		String tokens = "ADM1111  01" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "rgr, ys; rgr, ys " + "\r\n" + "2" + "\r\n" + "2 1" + "\r\n"
				+ "1.3.1 1.1.1" + "\r\n" + "1 1" + "\r\n" + "C1-387 C1-387"
				+ "\r\n" + "0 0" + "\r\n" + "0 0" + "\r\n" + "0 0; 0 0"
				+ "\r\n" + "ADM1112  01" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "LUC LAJOIE" + "\r\n" + "1" + "\r\n" + "3" + "\r\n" + "1.3.1"
				+ "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n" + "0" + "\r\n"
				+ "0" + "\r\n" + "0 ; 0" + "\r\n";

		DxSetOfInstructors dxsoiTempInst = new DxSetOfInstructors();
		DxAvailability aTemp = new DxAvailability();
        aTemp.addDayAvailability("1 5 1");
        aTemp.addDayAvailability("5 5 1 1");
        aTemp.addDayAvailability("5 5 5 5 1");
        aTemp.addDayAvailability("1 1 1 5 5 5");
        aTemp.addDayAvailability("1 5 1 5 1 5 1");

		dxsoiTempInst.addInstructor("LUC LAJOIE", aTemp);
		dxsoiTempInst.addInstructor("Gonzalez-Rubio, Ruben", aTemp);

		DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
		dxsorTempRooms.addRoom(new DxRoom("C1-387", 0, 0, null, null, aTemp));
		dxsorTempRooms.addRoom(new DxRoom("C1-330", 0, 0, null, null, aTemp));

		DxLoadData ld = new DxLoadData();
		DxActivitiesSitesReader dxasrReader = new DxReadActivitiesSites1dot5(ld
				.insertHeader(tokens.getBytes()), dxsoiTempInst,
				dxsorTempRooms, 60, false);

		DxSetOfActivitiesSites dxsoasSites = null;

		try {
			dxsoasSites = dxasrReader.readSetOfActivitiesSites();
		} catch (Exception e) {
			assertTrue("test_nInstructorEqualsnBlocs: assertTrue", false);
		}
	}

	/**
	 * test instructorEqualsBlocs,
	 * 
	 * 
	 */
	public void test_nSetInstructorEqualsnBlocs() {
		String tokens = "ADM1111  01" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "rgr: ys; rgr: ys" + "\r\n" + "2" + "\r\n" + "2 1" + "\r\n"
				+ "1.3.1 1.1.1" + "\r\n" + "1 1" + "\r\n" + "C1-387 C1-387"
				+ "\r\n" + "0 0" + "\r\n" + "0 0" + "\r\n" + "0 0; 0 0"
				+ "\r\n" + "ADM1112  01" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "LUC LAJOIE" + "\r\n" + "1" + "\r\n" + "3" + "\r\n" + "1.3.1"
				+ "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n" + "0" + "\r\n"
				+ "0" + "\r\n" + "0 ; 0" + "\r\n";

		DxSetOfInstructors dxsoiTempInst = new DxSetOfInstructors();
		DxAvailability aTemp = new DxAvailability();
        aTemp.addDayAvailability("1 5 1");
        aTemp.addDayAvailability("5 5 1 1");
        aTemp.addDayAvailability("5 5 5 5 1");
        aTemp.addDayAvailability("1 1 1 5 5 5");
        aTemp.addDayAvailability("1 5 1 5 1 5 1");

		dxsoiTempInst.addInstructor("LUC LAJOIE", aTemp);
		dxsoiTempInst.addInstructor("Gonzalez-Rubio, Ruben", aTemp);

		DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
		dxsorTempRooms.addRoom(new DxRoom("C1-387", 0, 0, null, null, aTemp));
		dxsorTempRooms.addRoom(new DxRoom("C1-330", 0, 0, null, null, aTemp));

		DxLoadData ld = new DxLoadData();
		DxActivitiesSitesReader dxasrReader = new DxReadActivitiesSites1dot5(ld
				.insertHeader(tokens.getBytes()), dxsoiTempInst,
				dxsorTempRooms, 60, false);

		DxSetOfActivitiesSites dxsoasSites = null;

		try {
			dxsoasSites = dxasrReader.readSetOfActivitiesSites();
		} catch (Exception e) {
			assertTrue("test_nSetInstructorEqualsnBlocs: assert true", false);
		}
	}

	public void test_nInstructorNoEqualsnBlocs() {
		String tokens = "ADM1111  01" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "rgr, ys " + "\r\n" + "2" + "\r\n" + "2 1" + "\r\n"
				+ "1.3.1 1.1.1" + "\r\n" + "1 1" + "\r\n" + "C1-387 C1-387"
				+ "\r\n" + "0 0" + "\r\n" + "0 0" + "\r\n" + "0 0; 0 0"
				+ "\r\n" + "ADM1112  01" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "LUC LAJOIE" + "\r\n" + "1" + "\r\n" + "3" + "\r\n" + "1.3.1"
				+ "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n" + "0" + "\r\n"
				+ "0" + "\r\n" + "0 ; 0" + "\r\n";
		DxSetOfInstructors dxsoiTempInst = new DxSetOfInstructors();
		DxAvailability aTemp = new DxAvailability();
        aTemp.addDayAvailability("1 5 1");
        aTemp.addDayAvailability("5 5 1 1");
        aTemp.addDayAvailability("5 5 5 5 1");
        aTemp.addDayAvailability("1 1 1 5 5 5");
        aTemp.addDayAvailability("1 5 1 5 1 5 1");

		dxsoiTempInst.addInstructor("LUC LAJOIE", aTemp);
		dxsoiTempInst.addInstructor("Gonzalez-Rubio, Ruben", aTemp);

		DxSetOfRooms dxsorTempRooms = new DxSetOfRooms();
		dxsorTempRooms.addRoom(new DxRoom("C1-387", 0, 0, null, null, aTemp));
		dxsorTempRooms.addRoom(new DxRoom("C1-330", 0, 0, null, null, aTemp));


		DxLoadData ld = new DxLoadData();
		DxActivitiesSitesReader dxasrReader = new DxReadActivitiesSites1dot5(ld
				.insertHeader(tokens.getBytes()), dxsoiTempInst,
				dxsorTempRooms, 60, false);

		DxSetOfActivitiesSites dxsoasSites = null;

		try {
			dxsoasSites = dxasrReader.readSetOfActivitiesSites();
		} catch (Exception e) {
			// assertEquals("test_nInstructorNoEqualsnBlocs: assertEquals",
			// DConst.ACTI_TEXT13, e.getMessage().substring(0,
			// DConst.ACTI_TEXT13.length()));
		}
	}
}