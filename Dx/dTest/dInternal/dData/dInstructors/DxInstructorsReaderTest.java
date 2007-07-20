package dTest.dInternal.dData.dInstructors;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dConstants.DConst;
import dInternal.DataExchange;
import dInternal.dData.DLoadData;
import dInternal.dData.dInstructors.DxInstructorsReader;
import dInternal.dData.dInstructors.DxReadInstructorsdotDia;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import eLib.exit.exception.DxException;


public class DxInstructorsReaderTest extends TestCase {

	/**
	 * 
	 * 
	 */
	public DxInstructorsReaderTest(String name) {
		super(name);
	}

	/**
	 * 
	 * 
	 */
	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxInstructorsReaderTest.class);
	} // end suite

	/**
	 * test_exceptionOnFirstLine, analysis should fail if an
	 * invalid instructor format in first line
	 */
	public void test_exceptionOnFirstLine() {
		String tokens = "    1k" + "\r\n" 
			    + "ADM111" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
		DLoadData ld = new DLoadData();
		DxInstructorsReader dxriTest = new DxReadInstructorsdotDia(ld
				.buildDataExchange(tokens.getBytes()), 5, 14);
		try {
			DxSetOfInstructors dxsoi = dxriTest.readSetOfInstructors();
			dxsoi.getClass(); // exception is throw before this line
			assertFalse("test_exceptionOnFirstLine: Should have failed before",
					true);
		} catch (DxException e) {
			assertEquals("test_exceptionOnFirstLine:",DConst.INVALID_NUMBER_OF_INSTRUCTORS+"For input string: \"1k\"", e.getMessage());			
		}
	}

	/**
	 * test1_exceptionOnName, analysis fail if an invalid instructor name
	 */
	public void test1_exceptionOnName() {
		String tokens = "    2" + "\r\n"  
				+ " " + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n" 
				+ "POLM" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
		DLoadData ld = new DLoadData(); 
	
		DxInstructorsReader dxriTest = new DxReadInstructorsdotDia(ld
				.buildDataExchange(tokens.getBytes()), 5, 14);
		try {
			DxSetOfInstructors dxsoi = dxriTest.readSetOfInstructors();
			dxsoi.getClass(); // exception is throw before this line
			assertFalse("test1_exceptionOnName: Should have failed before",
					true);
		} catch (DxException e) {
			assertEquals("test1_1_getSetOfInstructors: assertEquals",
					DConst.INVALID_INSTRUCTOR_NAME+"2", e
							.getMessage());
		}
	}

	/**
	 * test_numberOfInstructors, analysis throws exception if
	 * count and actual number of instructor in the file does not match
	 */
	public void test_numberOfInstructors() {
		String tokens = "    3" + "\r\n" 
		        + "JAC" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n" 
				+ "POLM" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
		DLoadData ld = new DLoadData();
		DxInstructorsReader dxriTest = new DxReadInstructorsdotDia(ld
				.buildDataExchange(tokens.getBytes()), 5, 14);
		try {
			DxSetOfInstructors dxsoi = dxriTest.readSetOfInstructors();
			dxsoi.getClass(); // exception is throw before this line
			assertFalse("test_numberOfInstructors: Should have failed before",
					true);

		} catch (DxException e) {
			assertEquals("test_numberOfInstructors: ",
					 DConst.INVALID_NUMBER_OF_INSTRUCTORS, e.getMessage());
		}
	}

	/**
	 * test_badAvailability, analysis throws a exception when
	 * the availability format contains a 6
	 */
	public void test_badAvailability6() {
		String tokens = "    2" + "\r\n" 
		        + "John" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n" 
				+ "POLM" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1 6" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
		DLoadData ld = new DLoadData();
		DxInstructorsReader dxriTest = new DxReadInstructorsdotDia(ld
				.buildDataExchange(tokens.getBytes()), 5, 14);
		try {
			DxSetOfInstructors dxsoi = dxriTest.readSetOfInstructors();
			dxsoi.getClass(); // exception is throw before this line
			assertFalse("test_badAvailability6: Shoudd have failed before",
					true);
		} catch (Exception e) {
			assertEquals("test_badAvailability6: ",
					DConst.INVALID_AVAILABILITY_AT+"11", e.getMessage());
		}
	}

	/**
	test_badAvailability, analysis throws a exception when
	 * the availability format contains a 3
	 */
	public void test_badAvailability3() {
		String tokens = "    2" + "\r\n" 
		        + "JAC" + "\r\n"
				+ "1 3 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n" 
				+ "POLM" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
		DLoadData ld = new DLoadData();
		DxInstructorsReader dxriTest = new DxReadInstructorsdotDia(ld
				.buildDataExchange(tokens.getBytes()), 5, 14);
		try {
			DxSetOfInstructors dxsoi = dxriTest.readSetOfInstructors();
			dxsoi.getClass(); // exception is throw before this line
			assertFalse("test_badAvailability3: Shoudd have failed before",
					true);
		} catch (Exception e) {
			assertEquals("test_badAvailability3:",
					DConst.INVALID_AVAILABILITY_AT+"3", e.getMessage());
		}
	}

	/**
	 * test5_getSetOfInstructors
	 */
	public void test_values() {
		String tokens = "    2" + "\r\n" 
				+ "JAC" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n" 
				+ "POLM" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
		DLoadData ld = new DLoadData();
		DxInstructorsReader dxriTest = new DxReadInstructorsdotDia(ld
				.buildDataExchange(tokens.getBytes()), 5, 14);
		try {
			DxSetOfInstructors dxsoiTemp = dxriTest.readSetOfInstructors();
			assertNotNull("test_values: assertNotNull",
					dxsoiTemp);
			assertNotNull("test_values: assertEquals",
					dxsoiTemp.getResource("JAC"));
			assertNotNull("test_values: assertEquals",
					dxsoiTemp.getResource("POLM"));
		} catch (Exception e) {
			// Should not fail in tests
			System.out.println("Exception in: test_values");
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 *
	 */
	public void test_valuesOnDispoInst() {
		DxSetOfInstructors dxsoi = null;
		
		StringBuffer fileName = new StringBuffer("." + File.separator);
		fileName.append("dataTest" + File.separator);
		fileName.append("testDispoInst.sig");

		DLoadData ld = new DLoadData();
		byte[] dataloaded = null;
		try {
			dataloaded = ld.filterBadChars(fileName.toString());
		} catch (Exception e) {
			// Should not fail in tests
			System.out.println("Exception in: test_valuesOnDispoInst");
			e.printStackTrace();
		}
		
		DataExchange de = ld.buildDataExchange(dataloaded);

		DxInstructorsReader dxir = new DxReadInstructorsdotDia(de, 5, 14);
		try {
			dxsoi = dxir.readSetOfInstructors();
			assertEquals("test6_1_getSetOfInstructors: assertEquals", 126, dxsoi
					.size());
			assertEquals("test6_2_getSetOfInstructors: assertEquals", true, dxsoi
					.areVectorsSync());
			assertNotNull("test6_3_getSetOfInstructors: assertEquals", dxsoi
					.getResource("ABATZOGLOU, NICOLAS"));
			assertNotNull("test6_4_getSetOfInstructors: assertEquals", dxsoi
					.getResource("YAHIA, AMMAR"));
			assertNotNull("test6_5_getSetOfInstructors: assertEquals", dxsoi
					.getResource("AMÉDIN, CELSE KAFUI"));

			//assertEquals("test6_6_getSetOfInstructors: assertEquals", 5, dxsoi
			//		.getInstructorAvailabilityByName("YAHIA, AMMAR").getPeriodAvailability(4,
			//				13));
//			assertEquals("test6_7_getSetOfInstructors: assertEquals", 1, dxsoi
//					.getInstructorAvailabilityByKey(5).getPeriodAvailability(2, 7));
//			dxsoi.removeInstructor(1);
//			dxsoi.removeInstructor(126);
//			assertEquals("test6_8_getSetOfInstructors: assertEquals", 124, dxsoi
//					.size());
//			assertEquals("test6_9_getSetOfInstructors: assertEquals", true, dxsoi
//					.areVectorsSync());
//			assertEquals("test6_10_getSetOfInstructors: assertEquals", -1, dxsoi
//					.getInstructorKeyByName("YAHIA, AMMAR"));
		} catch (Exception e) {
			// Should not fail in tests
			System.out.println("Exception in: test_valuesOnDispoInst");
			e.printStackTrace();
		}


	}

}
