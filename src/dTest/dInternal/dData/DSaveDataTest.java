package dTest.dInternal.dData;

/**
 *
 * Title: DSaveDataTest $Revision $  $Date: 2008-01-25 19:50:31 $
 * Description: DSaveDataTest is a class used to test the class 
 *              DSaveData
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
 * @version $ $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */

import java.io.File;

import dInterface.DxDocument;
import dInterface.DxTTableDoc;
import dInternal.DModel;
import dInternal.dData.DSaveData;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DSaveDataTest extends TestCase {
	
	private final String _pathForFiles = "." + File.separator + "dataTest"
	+ File.separator;
	
	private final String _pathForOutputFiles = "." + File.separator
	+ "forOutputTests" + File.separator;

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DSaveDataTest.class);
	} // end suite


	public void test_getVersion() {
		DSaveData a = new DSaveData("1.6");
		assertEquals("test_getVersion: assertEquals", "1.6", a.getVersion());
	}

	/**
	 * test that check the version of timetable
	 */
	public void test1_saveTimeTable() {
		DSaveData a = new DSaveData("1.6");
		DxDocument dxDocument = new DxTTableDoc();

//		int type = 1;
		try {
			DModel dm1 = new DModel(dxDocument, _pathForFiles + "loadData7j.dia");
			String error = a
			.saveTimeTable(dm1.getTTStructure(), dm1  
					.getDxSetOfInstructors(), dm1.getDxSetOfSites(), dm1
					.getSetOfActivitiesSites(), dm1.getSetOfStuSites(),
					_pathForOutputFiles + "downData.dia");
			assertEquals("test_saveTimeTable: assertEquals", 0, error.length());
		
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_buildScNoAssigned: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test1_saveTimeTable");
			e.printStackTrace();
		}

	}

	/**
	 * test that check that the saved file is the same as the loaded file
	 */
	public void test2_saveTimeTable() {
		DSaveData a = new DSaveData("1.6");
		DxDocument _dxDocument1 = new DxTTableDoc();
		DxDocument _dxDocument2 = new DxTTableDoc();

//		int type = 1;
		try {
			DModel dm1 = new DModel(_dxDocument1, _pathForFiles + "loadData7j.dia");
//			String error = a
			a.saveTimeTable(dm1.getTTStructure(), dm1  
					.getDxSetOfInstructors(), dm1.getDxSetOfSites(), dm1
					.getSetOfActivitiesSites(), dm1.getSetOfStuSites(),
					_pathForOutputFiles + "downData.dia");
			DModel dm2 = new DModel(_dxDocument2, _pathForOutputFiles + "downData.dia");
			assertEquals("test2_saveTimeTable: assertEquals ", true, dm1
					.getTTStructure().isEquals(dm2.getTTStructure()));
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_buildScNoAssigned: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test1_saveTimeTable");
			e.printStackTrace();
		}

//		String error = a
//				.saveTimeTable(dm1.getTTStructure(), dm1  
//						.getDxSetOfInstructors(), dm1.getDxSetOfSites(), dm1
//						.getSetOfActivitiesSites(), dm1.getSetOfStuSites(),
//						_pathForOutputFiles + "downData.dia");

//		try {
//			_dm2 = new DModel(_dxDocument2, _pathForOutputFiles + "downData.dia");
//		} catch (Exception e) {
//			System.out
//					.println("Abnormal Exception: Should not fail in normal conditions");
//		}
//		assertEquals("test2_saveTimeTable: assertEquals ", true, _dm1
//				.getTTStructure().isEquals(_dm2.getTTStructure()));
	}

	/**
	 * test that check that the saved file is the same as the loaded file
	 */
	public void test_loadInstructors() {
		DSaveData a = new DSaveData("1.6");
		DxDocument _dxDocument1 = new DxTTableDoc();
		DxDocument _dxDocument2 = new DxTTableDoc();
		try {
			DModel dm1 = new DModel(_dxDocument1, _pathForFiles + "loadData7j.dia");
//			String error = a
			a.saveTimeTable(dm1.getTTStructure(), dm1  
					.getDxSetOfInstructors(), dm1.getDxSetOfSites(), dm1
					.getSetOfActivitiesSites(), dm1.getSetOfStuSites(),
					_pathForOutputFiles + "downData.dia");
			DModel dm2 = new DModel(_dxDocument2, _pathForOutputFiles + "downData.dia");
//			assertEquals("test2_saveTimeTable: assertEquals ", true, dm1
//					.getTTStructure().isEquals(dm2.getTTStructure()));
			assertEquals("test3_saveTimeTable: assertEquals ", true, dm1
					.getDxSetOfInstructors().isEqual(dm2.getDxSetOfInstructors()));
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_buildScNoAssigned: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test1_saveTimeTable");
			e.printStackTrace();
		}
//		assertEquals("test3_saveTimeTable: assertEquals ", true, _dm1
//				.getDxSetOfInstructors().isEqual(_dm2.getDxSetOfInstructors()));

	}

	/**
	 * test that check that the saved file is the same as the loaded file
	 */
	public void test_loadRooms() {
		DSaveData a = new DSaveData("1.6");
		DxDocument _dxDocument1 = new DxTTableDoc();
		DxDocument _dxDocument2 = new DxTTableDoc();
		try {
			DModel dm1 = new DModel(_dxDocument1, _pathForFiles + "loadData7j.dia");
//			String error = a
			a.saveTimeTable(dm1.getTTStructure(), dm1  
					.getDxSetOfInstructors(), dm1.getDxSetOfSites(), dm1
					.getSetOfActivitiesSites(), dm1.getSetOfStuSites(),
					_pathForOutputFiles + "downData.dia");
			DModel dm2 = new DModel(_dxDocument2, _pathForOutputFiles + "downData.dia");
//			assertEquals("test2_saveTimeTable: assertEquals ", true, dm1
//					.getTTStructure().isEquals(dm2.getTTStructure()));
			assertEquals("test4_saveTimeTable: assertEquals ", true, dm1
					.getDxSetOfSites().isEqual(dm2.getDxSetOfSites()));
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_buildScNoAssigned: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test1_saveTimeTable");
			e.printStackTrace();
		}

	}

	/**
	 * test that check that the saved file is the same as the loaded file
	 */
	public void test_loadActivities() {
		DSaveData a = new DSaveData("1.6");
		DxDocument _dxDocument1 = new DxTTableDoc();
		DxDocument _dxDocument2 = new DxTTableDoc();
		try {
			DModel dm1 = new DModel(_dxDocument1, _pathForFiles + "loadData7j.dia");
//			String error = a
			a.saveTimeTable(dm1.getTTStructure(), dm1  
					.getDxSetOfInstructors(), dm1.getDxSetOfSites(), dm1
					.getSetOfActivitiesSites(), dm1.getSetOfStuSites(),
					_pathForOutputFiles + "downData.dia");
			DModel dm2 = new DModel(_dxDocument2, _pathForOutputFiles + "downData.dia");
//			assertEquals("test2_saveTimeTable: assertEquals ", true, dm1
//					.getTTStructure().isEquals(dm2.getTTStructure()));
			assertEquals("test5_saveTimeTable: assertEquals ", true, dm1
					.getSetOfActivitiesSites().isEquals(
							dm2.getSetOfActivitiesSites()));
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_buildScNoAssigned: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test1_saveTimeTable");
			e.printStackTrace();
		}
//		assertEquals("test5_saveTimeTable: assertEquals ", true, _dm1
//				.getSetOfActivitiesSites().isEquals(
//						_dm2.getSetOfActivitiesSites()));
	}

	/**
	 * test that check that the saved file is the same as the loaded file
	 */
	public void test_loadStudents() {
		DSaveData a = new DSaveData("1.6");
		DxDocument _dxDocument1 = new DxTTableDoc();
		DxDocument _dxDocument2 = new DxTTableDoc();
		try {
			DModel dm1 = new DModel(_dxDocument1, _pathForFiles + "loadData7j.dia");
//			String error = a
			a.saveTimeTable(dm1.getTTStructure(), dm1  
					.getDxSetOfInstructors(), dm1.getDxSetOfSites(), dm1
					.getSetOfActivitiesSites(), dm1.getSetOfStuSites(),
					_pathForOutputFiles + "downData.dia");
			DModel dm2 = new DModel(_dxDocument2, _pathForOutputFiles + "downData.dia");
//			assertEquals("test2_saveTimeTable: assertEquals ", true, dm1
//					.getTTStructure().isEquals(dm2.getTTStructure()));
			assertEquals("test6_saveTimeTable: assertEquals ", true, dm1
					.getSetOfStuSites().isEquals(dm2.getSetOfStuSites()));
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_buildScNoAssigned: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test1_saveTimeTable");
			e.printStackTrace();
		}

	}

	/**
	 * test that check that the saved file is the same as the loaded file
	 */
	public void test_saveTTStructure() {
		DSaveData a = new DSaveData("1.6");
		DxDocument _dxDocument1 = new DxTTableDoc();
//		DxDocument _dxDocument2 = new DxTTableDoc();
		try {
			DModel dm1 = new DModel(_dxDocument1, _pathForFiles + "loadData7j.dia");
			String error = a.saveTTStructure(dm1.getTTStructure(), _pathForOutputFiles + "downDataTTS.dia");
			assertEquals("test_saveTTStructure: assertEquals", 0, error.length());
//			DModel dm2 = new DModel(_dxDocument2, _pathForOutputFiles + "downData.dia");
////			assertEquals("test2_saveTimeTable: assertEquals ", true, dm1
////					.getTTStructure().isEquals(dm2.getTTStructure()));
//			assertEquals("test6_saveTimeTable: assertEquals ", true, dm1
//					.getSetOfStuSites().isEquals(dm2.getSetOfStuSites()));
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_buildScNoAssigned: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test1_saveTimeTable");
			e.printStackTrace();
		}
//		DSaveData a = new DSaveData("1.6");
//		String error = a.saveTTStructure(_dm1.getTTStructure(), _pathForOutputFiles + "downDataTTS.dia");
//		assertEquals("test_saveTTStructure: assertEquals", 0, error.length());
	}
}