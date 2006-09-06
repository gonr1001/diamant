package dTest.dInternal.dData.dInstructors;

import java.io.File;

import dConstants.DConst;
import dInternal.DataExchange;
import dInternal.DxPreferences;
import dInternal.dData.DLoadData;
import dInternal.dData.dInstructors.DxInstructorsReader;
import dInternal.dData.dInstructors.DxReadInstructors1dot5;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import eLib.exit.exception.DxException;
import eLib.exit.txt.FilterFile;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DxInstructorsReaderTest extends TestCase {

	public DxInstructorsReaderTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxInstructorsReaderTest.class);
	} // end suite

	/**
	 * test_getSetOfInstructors, verifies v1.5 analyser Should fail because of
	 * invalid instructor count format
	 */
	public void test_getSetOfInstructors() {
		String tokens = "    1k" + "\r\n" + "ADM111" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
		DLoadData ld = new DLoadData();
		DxInstructorsReader dxriTest = new DxReadInstructors1dot5(ld
				.buildDataExchange(tokens.getBytes()), 5, 14);
		try {
			DxSetOfInstructors dxsoi = dxriTest.readSetOfInstructors();
			assertFalse("test_getSetOfInstructors: Should have failed before",
					true);

			// To avoid warning
			dxsoi.equals(dxsoi);
		} catch (NumberFormatException e) {
			assertEquals("test_1_getSetOfInstructors: assertEquals",DConst.INVALID_NUMBER_OF_INSTRUCTORS, e.getMessage());
		} catch (DxException e) {
		}

	}

	/**
	 * test1_getSetOfInstructors, verifies v1.5 analyser invalid instructor name
	 */
	public void test1_getSetOfInstructors() {
		String tokens = "    2" + "\r\n" + "" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n" + "POLM" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
		DLoadData ld = new DLoadData();
		DxInstructorsReader dxriTest = new DxReadInstructors1dot5(ld
				.buildDataExchange(tokens.getBytes()), 5, 14);
		try {
			DxSetOfInstructors dxsoi = dxriTest.readSetOfInstructors();
			assertFalse("test1_getSetOfInstructors: Should have failed before",
					true);

			// To avoid warning
			dxsoi.equals(dxsoi);
		} catch (DxException e) {
			assertEquals("test1_1_getSetOfInstructors: assertEquals",
					DConst.INVALID_PERIOD_AVAILABILITY_AT_LINE+"11", e
							.getMessage());
		}
	}

	/**
	 * test2_getSetOfInstructors, verifies v1.5 analyser Suggested instructor
	 * count and actual number of instrucator in the file does not match
	 */
	public void test2_getSetOfInstructors() {
		String tokens = "    3" + "\r\n" + "JAC" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n" + "POLM" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
		DLoadData ld = new DLoadData();
		DxInstructorsReader dxriTest = new DxReadInstructors1dot5(ld
				.buildDataExchange(tokens.getBytes()), 5, 14);
		try {
			DxSetOfInstructors dxsoi = dxriTest.readSetOfInstructors();
			assertFalse("test2_getSetOfInstructors: Should have failed before",
					true);

			// To avoid warning
			dxsoi.equals(dxsoi);
		} catch (DxException e) {
			assertEquals("test2_1_getSetOfInstructors: assertEquals",
					 DConst.INVALID_NUMBER_OF_INSTRUCTORS, e.getMessage());
		}
	}

	/**
	 * test2_getSetOfInstructors, verifies v1.5 analyser Should fail because of
	 * invalid number of periods in second instructor, 3rd day
	 */
	public void test3_getSetOfInstructors() {
		String tokens = "    2" + "\r\n" + "John" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n" + "POLM" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1 6" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
		DLoadData ld = new DLoadData();
		DxInstructorsReader dxriTest = new DxReadInstructors1dot5(ld
				.buildDataExchange(tokens.getBytes()), 5, 14);
		try {
			DxSetOfInstructors dxsoi = dxriTest.readSetOfInstructors();
			assertFalse("test3_getSetOfInstructors: Shoudd have failed before",
					true);

			// To avoid warning
			dxsoi.equals(dxsoi);
		} catch (Exception e) {
			assertEquals("test3_1_getSetOfInstructors: assertEquals",
				 DConst.INVALID_PERIOD_AVAILABILITY_AT_LINE+"15", e.getMessage());
		}
	}

	/**
	 * test2_getSetOfInstructors, verifies v1.5 analyser Make sure analyser
	 * verify nature of period availability. 3 is an invalid period availability
	 * in v1.5
	 */
	public void test4_getSetOfInstructors() {
		String tokens = "    2" + "\r\n" + "JAC" + "\r\n"
				+ "1 3 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n" + "POLM" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
		DLoadData ld = new DLoadData();
		DxInstructorsReader dxriTest = new DxReadInstructors1dot5(ld
				.buildDataExchange(tokens.getBytes()), 5, 14);
		try {
			DxSetOfInstructors dxsoi = dxriTest.readSetOfInstructors();
			assertFalse("test4_getSetOfInstructors: Shoudd have failed before",
					true);

			// To avoid warning
			dxsoi.equals(dxsoi);
		} catch (Exception e) {
			assertEquals("test4_1_getSetOfInstructors: assertEquals",
					DConst.INVALID_PERIOD_AVAILABILITY_AT_LINE+"7", e.getMessage());
		}
	}

	/**
	 * test2_getSetOfInstructors, verifies v1.5 builder Make sure analyser
	 * verify nature of period availability. 3 is an invalid period availability
	 * in v1.5
	 */
	public void test5_getSetOfInstructors() {
		String tokens = "    2" + "\r\n" + "JAC" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n" + "POLM" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
		DLoadData ld = new DLoadData();
		DxInstructorsReader dxriTest = new DxReadInstructors1dot5(ld
				.buildDataExchange(tokens.getBytes()), 5, 14);
		try {
			DxSetOfInstructors dxsoiTemp = dxriTest.readSetOfInstructors();
			assertNotSame("test5_getSetOfInstructors: assertNotSame",
					dxsoiTemp, null);
			assertNotNull("test5_1_getSetOfInstructors: assertEquals",
					dxsoiTemp.getResource("JAC"));
			assertNotNull("test5_2_getSetOfInstructors: assertEquals",
					dxsoiTemp.getResource("POLM"));
		} catch (DxException e) {
			assertFalse("test5_3_getSetOfInstructors: Should not have failed"
					+ e.getMessage(), true);
		}
	}

	public void test6_getSetOfInstructors() {
		DxSetOfInstructors dxsoi = null;
		String path = "." + File.separator + "dataTest" + File.separator
				+ "disprof.sig.DISPROF";
		byte[] dataloaded = preLoad(path);
		DLoadData ld = new DLoadData();
		DataExchange de = ld.buildDataExchange(dataloaded);

		DxInstructorsReader dxir = new DxReadInstructors1dot5(de, 5, 14);
		try {
			dxsoi = dxir.readSetOfInstructors();
		} catch (Exception e) {
			assertFalse("DxInstructorReaderTest: assertFalse" + e.toString(),
					true);
		}

		assertEquals("test6_1_getSetOfInstructors: assertEquals", 126, dxsoi
				.size());
		assertEquals("test6_2_getSetOfInstructors: assertEquals", true, dxsoi
				.areVectorsSync());
		assertNotNull("test6_3_getSetOfInstructors: assertEquals", dxsoi
				.getResource("ABATZOGLOU, NICOLAS"));
		assertNotNull("test6_4_getSetOfInstructors: assertEquals", dxsoi
				.getResource("YAHIA, AMMAR"));
		assertNotNull("test6_5_getSetOfInstructors: assertEquals", dxsoi
				.getResource("AM�DIN, CELSE KAFUI"));

		//assertEquals("test6_6_getSetOfInstructors: assertEquals", 5, dxsoi
		//		.getInstructorAvailabilityByName("YAHIA, AMMAR").getPeriodAvailability(4,
		//				13));
//		assertEquals("test6_7_getSetOfInstructors: assertEquals", 1, dxsoi
//				.getInstructorAvailabilityByKey(5).getPeriodAvailability(2, 7));
//		dxsoi.removeInstructor(1);
//		dxsoi.removeInstructor(126);
//		assertEquals("test6_8_getSetOfInstructors: assertEquals", 124, dxsoi
//				.size());
//		assertEquals("test6_9_getSetOfInstructors: assertEquals", true, dxsoi
//				.areVectorsSync());
//		assertEquals("test6_10_getSetOfInstructors: assertEquals", -1, dxsoi
//				.getInstructorKeyByName("YAHIA, AMMAR"));
	}

	private byte[] preLoad(String str) {
		DxPreferences preferences = new DxPreferences("." + File.separator
				+ "pref" + File.separator + "pref.txt");
		FilterFile filter = new FilterFile();
		filter.setCharKnown("");
		filter.appendToCharKnown(preferences._acceptedChars);
		if (filter.validFile(str)) {
			return filter.getByteArray();
		}
		return null;
	} // preLoad(String str)
}
