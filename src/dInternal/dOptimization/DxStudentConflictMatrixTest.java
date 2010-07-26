package dInternal.dOptimization;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */
import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInterface.DxTTableDoc;
import dInternal.DModel;

public class DxStudentConflictMatrixTest extends TestCase {

	
	private final String _pathForFiles = "." + File.separator + "dataTest"
	+ File.separator;

//	StudentsConflictsMatrix matrix1;
//
//	DModel dm1; // For LoadData7j
//
//	StudentsConflictsMatrix matrix2;
//
//	DModel dm2; // For LoadData5j

//	public StudentsConflictsMatrixTest(String name) {
//		super(name);
//		try {
//			_dm1 = new DModel(new DxTTableDoc(), _pathForFiles + "loadData7j.dia");
//	
//		_dm1.getConditionsToTest().buildStudentConflictMatrix();
//		_dm1.getConditionsToTest().buildAllConditions(_dm1.getTTStructure());
//		_matrix1 = _dm1.getConditionsToTest().getConflictsMatrix();
//
//
//		_dm2 = new DModel(new DxTTableDoc(), _pathForFiles + "loadData5j.dia");
//		_dm2.getConditionsToTest().buildStudentConflictMatrix();
//		_dm2.getConditionsToTest().buildAllConditions(_dm2.getTTStructure());
//		_matrix2 = _dm2.getConditionsToTest().getConflictsMatrix();
//		} catch (Exception e) {
//			// Should not fail in tests, but if file not there gives a failure
//			assertEquals("test_writeXMLtag: exception", "nullPointer", e
//					.toString());
//			System.out.println("Exception in: test_writeXMLtag");
//			e.printStackTrace();
//		}
//
//		_dm2.getConditionsToTest().buildStudentConflictMatrix();
//		_dm2.getConditionsToTest().buildAllConditions(_dm2.getTTStructure());
//		_matrix2 = _dm2.getConditionsToTest().getConflictsMatrix();
//	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxStudentConflictMatrixTest.class);
	} // end suite

	/**
	 * 
	 */
	public void test_SectionKeys() {
		
		
		try {
			DModel dm1 = new DModel(new DxTTableDoc(), _pathForFiles + "loadData7j.dia");
	
		dm1.getConditionsToTest().buildStudentConflictMatrix();
		dm1.getConditionsToTest().buildAllConditions(dm1.getTTStructure());
		
		DxStudentConflictMatrix matrix1 = dm1.getConditionsToTest().getConflictsMatrix();


		DModel dm2 = new DModel(new DxTTableDoc(), _pathForFiles + "loadData5j.dia");
		dm2.getConditionsToTest().buildStudentConflictMatrix();
		dm2.getConditionsToTest().buildAllConditions(dm2.getTTStructure());
		DxStudentConflictMatrix matrix2 = dm2.getConditionsToTest().getConflictsMatrix();
		
		dm2.getConditionsToTest().buildStudentConflictMatrix();
		dm2.getConditionsToTest().buildAllConditions(dm2.getTTStructure());
		matrix2 = dm2.getConditionsToTest().getConflictsMatrix();
		String key1 = "GCH100.1.01";// key 1
		String key2 = "AMC645.1.01";// key 136
		int[] index = matrix1.getSectionsKeys(key1, key2);
		assertEquals("test_SectionKeys_1 : assertEquals 1", 6, index[0]);
		assertEquals("test_SectionKeys_2 : assertEquals 2", 10, index[1]);
		
		key1 = "GCH100.1.01";// key 1
		key2 = "AMC645.1.01";// key 136
		index = matrix2.getSectionsKeys(key1, key2);
		assertEquals("test2_SectionKeys_1 : assertEquals 1", 6, index[0]);
		assertEquals("test2_SectionKeys_2 : assertEquals 2", 10, index[1]);
		
		key1 = "AMC640.1.01";
		key2 = "AMC645.1.01";
		assertEquals("test1_Matrix : assertEquals", 4, matrix1
				.getNumberOfCOnflicts(key1, key2));
		
		
		key1 = "AMC640.1.02";
		key2 = "AMC645.1.01";
		assertEquals("test2_Matrix : assertEquals", 1, matrix1
				.getNumberOfCOnflicts(key1, key2));
	
		key1 = "GCH111.2.01";
		key2 = "AMC645.1.01";
		assertEquals("test3_Matrix : assertEquals", 1, matrix1
				.getNumberOfCOnflicts(key1, key2));

		key1 = "AMC640.1.01";
		key2 = "AMC645.1.01";
		assertEquals("test4_Matrix : assertEquals", 4, matrix2
				.getNumberOfCOnflicts(key1, key2));

		key1 = "AMC640.1.02";
		key2 = "AMC645.1.01";
		assertEquals("test5_Matrix : assertEquals", 1, matrix2
				.getNumberOfCOnflicts(key1, key2));


		key1 = "GCH111.2.01";
		key2 = "AMC645.1.01";
		assertEquals("test6_Matrix : assertEquals", 1, matrix2
				.getNumberOfCOnflicts(key1, key2));
		
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_writeXMLtag: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_writeXMLtag");
			e.printStackTrace();
		}
	}

//	public void test2_SectionKeys() {
//		String key1 = "GCH100.1.01";// key 1
//		String key2 = "AMC645.1.01";// key 136
//		int[] index = _matrix2.getSectionsKeys(key1, key2);
//		assertEquals("test2_SectionKeys_1 : assertEquals 1", 6, index[0]);
//		assertEquals("test2_SectionKeys_2 : assertEquals 2", 10, index[1]);
//	}

//	/**
//	 * 
//	 */
//	public void test1_Matrix() {
//		String key1 = "AMC640.1.01";
//		String key2 = "AMC645.1.01";
//		assertEquals("test1_Matrix : assertEquals", 4, _matrix1
//				.getNumberOfCOnflicts(key1, key2));
//
//	}

	/**
	 * 
	 */
//	public void test2_Matrix() {
//		String key1 = "AMC640.1.02";
//		String key2 = "AMC645.1.01";
//		assertEquals("test2_Matrix : assertEquals", 1, _matrix1
//				.getNumberOfCOnflicts(key1, key2));
//	}
//
//	/**
//	 * 
//	 */
//	public void test3_Matrix() {
//		String key1 = "GCH111.2.01";
//		String key2 = "AMC645.1.01";
//		assertEquals("test3_Matrix : assertEquals", 1, _matrix1
//				.getNumberOfCOnflicts(key1, key2));
//	}
//
//	/**
//	 * 
//	 */
//	public void test4_Matrix() {
//		String key1 = "AMC640.1.01";
//		String key2 = "AMC645.1.01";
//		assertEquals("test4_Matrix : assertEquals", 4, _matrix2
//				.getNumberOfCOnflicts(key1, key2));
//	}
//
//	/**
//	 * 
//	 */
//	public void test5_Matrix() {
//		String key1 = "AMC640.1.02";
//		String key2 = "AMC645.1.01";
//		assertEquals("test5_Matrix : assertEquals", 1, _matrix2
//				.getNumberOfCOnflicts(key1, key2));
//
//	}
//
//	/**
//	 * 
//	 */
//	public void test6_Matrix() {
//		String key1 = "GCH111.2.01";
//		String key2 = "AMC645.1.01";
//		assertEquals("test6_Matrix : assertEquals", 1, _matrix2
//				.getNumberOfCOnflicts(key1, key2));
//	}

}// end StudentsConflictsMatrixTest
