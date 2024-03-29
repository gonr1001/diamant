package dTest.dInternal.dData.dInstructors;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dConstants.DConst;
import dExceptions.DiaException;
import dInternal.DataExchange;
import dInternal.DxLoadData;
import dInternal.dData.dInstructors.DxInstructorsReader;
import dInternal.dData.dInstructors.DxReadInstructorsdotDia;
import dInternal.dData.dInstructors.DxSetOfInstructors;

public class DxInstructorsReaderTest extends TestCase {

	private final String _pathForFiles = "." + File.separator + "dataTest"
			+ File.separator;

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
	 * test_exceptionOnFirstLine, analysis should fail if an invalid instructor
	 * format in first line
	 */
	public void test_exceptionOnFirstLine() {
		String tokens = "    1k" + "\r\n" + "ADM111" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
		DxLoadData ld = new DxLoadData();
		DxInstructorsReader dxriTest = new DxReadInstructorsdotDia(ld
				.insertHeader(tokens.getBytes()), 5, 14);
		try {
			DxSetOfInstructors dxsoi = dxriTest.readSetOfInstructors();
			dxsoi.getClass(); // exception is throw before this line
			assertFalse("test_exceptionOnFirstLine: Should have failed before",
					true);
		} catch (DiaException e) {
			assertEquals("test_exceptionOnFirstLine:",
					DConst.INVALID_NUMBER_OF_INSTRUCTORS
							+ "For input string: \"1k\"", e.getMessage());
		}
	}

	/**
	 * test1_exceptionOnName, analysis fail if an invalid instructor name
	 */
	public void test1_exceptionOnName() {
		String tokens = "    2" + "\r\n" + " " + "\r\n"
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
		DxLoadData ld = new DxLoadData();

		DxInstructorsReader dxriTest = new DxReadInstructorsdotDia(ld
				.insertHeader(tokens.getBytes()), 5, 14);
		try {
			DxSetOfInstructors dxsoi = dxriTest.readSetOfInstructors();
			dxsoi.getClass(); // exception is throw before this line
			assertFalse("test1_exceptionOnName: Should have failed before",
					true);
		} catch (DiaException e) {
			assertEquals("test1_1_getSetOfInstructors: assertEquals",
					DConst.INVALID_INSTRUCTOR_NAME + "2", e.getMessage());
		}
	}

	/**
	 * test_numberOfInstructors, analysis throws exception if count and actual
	 * number of instructor in the file does not match
	 */
	public void test_numberOfInstructors() {
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
		DxLoadData ld = new DxLoadData();
		DxInstructorsReader dxriTest = new DxReadInstructorsdotDia(ld
				.insertHeader(tokens.getBytes()), 5, 14);
		try {
			DxSetOfInstructors dxsoi = dxriTest.readSetOfInstructors();
			dxsoi.getClass(); // exception is throw before this line
			assertFalse("test_numberOfInstructors: Should have failed before",
					true);

		} catch (DiaException e) {
			assertEquals("test_numberOfInstructors: ", true, e.getMessage()
					.contains(DConst.INVALID_NUMBER_OF_INSTRUCTORS));
		}
	}

	/**
	 * test_badAvailability, analysis throws a exception when the availability
	 * format contains a 6
	 */
	public void test_badAvailability6() {
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
		DxLoadData ld = new DxLoadData();
		DxInstructorsReader dxriTest = new DxReadInstructorsdotDia(ld
				.insertHeader(tokens.getBytes()), 5, 14);
		try {
			DxSetOfInstructors dxsoi = dxriTest.readSetOfInstructors();
			dxsoi.getClass(); // exception is throw before this line
			assertFalse("test_badAvailability6: Should have failed before",
					true);
		} catch (Exception e) {
			assertEquals("test_badAvailability6: ",
					DConst.INVALID_AVAILABILITY_AT + "11", e.getMessage());
		}
	}

	/**
	 * test_badAvailability, analysis throws a exception when the availability
	 * format contains a 3
	 */
	public void test_badAvailability3() {
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
		DxLoadData ld = new DxLoadData();
		DxInstructorsReader dxriTest = new DxReadInstructorsdotDia(ld
				.insertHeader(tokens.getBytes()), 5, 14);
		try {
			DxSetOfInstructors dxsoi = dxriTest.readSetOfInstructors();
			dxsoi.getClass(); // exception is throw before this line
			assertFalse("test_badAvailability3: Should have failed before",
					true);
		} catch (Exception e) {
			assertEquals("test_badAvailability3:",
					DConst.INVALID_AVAILABILITY_AT + "3", e.getMessage());
		}
	}

	/**
	 * test5_getSetOfInstructors
	 */
	public void test_values() {
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
		DxLoadData ld = new DxLoadData();
		DxInstructorsReader dxriTest = new DxReadInstructorsdotDia(ld
				.insertHeader(tokens.getBytes()), 5, 14);
		try {
			DxSetOfInstructors dxsoiTemp = dxriTest.readSetOfInstructors();
			assertNotNull("test_values: assertNotNull", dxsoiTemp);
			assertNotNull("test_values: assertEquals", dxsoiTemp
					.getResource("JAC"));
			assertNotNull("test_values: assertEquals", dxsoiTemp
					.getResource("POLM"));
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

		try {

			DxLoadData ld = new DxLoadData();
			byte[] dataloaded = ld.filterBadChars(_pathForFiles
					+ "testDispoInst.sig");

			DataExchange de = ld.insertHeader(dataloaded);

			DxInstructorsReader dxir = new DxReadInstructorsdotDia(de, 5, 14);
			DxSetOfInstructors dxsoi = dxir.readSetOfInstructors();
			assertEquals("test_valuesOnDispoInst: assertEquals", 126,
					dxsoi.size());
			assertEquals("test_valuesOnDispoInst: assertEquals", true,
					dxsoi.areVectorsSync());
			assertNotNull("test_valuesOnDispoInst: assertEquals", dxsoi
					.getResource("ABATZOGLOU, NICOLAS"));
			assertNotNull("test_valuesOnDispoInst: assertEquals", dxsoi
					.getResource("YAHIA, AMMAR"));
			assertNotNull("test_valuesOnDispoInst: assertEquals", dxsoi
					.getResource("AM�DIN, CELSE KAFUI"));

			long i = dxsoi.getResourceKey("YAHIA, AMMAR");
			assertEquals("test6_6_getSetOfInstructors: assertEquals 5", 5,
					dxsoi.getInstructorAvailability(i).getPeriodAvailability(4,
							13));
			
			i = dxsoi.getResourceKey("ABATZOGLOU, NICOLAS");
			assertEquals("test_readInstructors: assertEquals 5", 5,
					dxsoi.getInstructorAvailability(i).getPeriodAvailability(4,
							13));
			 assertEquals("test_readInstructors: assertEquals", 1,
					 dxsoi.getInstructorAvailability(i).getPeriodAvailability(1, 1));
			 
			 dxsoi.removeInstructor(dxsoi.getResourceKey("ABATZOGLOU, NICOLAS"));
			 dxsoi.removeInstructor(dxsoi.getResourceKey("YAHIA, AMMAR"));
			 assertEquals("test_readInstructors: assertEquals", 124,
			 dxsoi
			 .size());
			 assertEquals("test_readInstructors: assertEquals", true,
			 dxsoi
			 .areVectorsSync());
			 assertEquals("test6_10_getSetOfInstructors: assertEquals", -1,
			 dxsoi.getResourceKey("YAHIA, AMMAR"));
			
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_basicData: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_valuesOnDispoInst");
			e.printStackTrace();
		}

	}

}
