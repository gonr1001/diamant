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
	DSaveData _a;

	DModel _dm1;

	DModel _dm2;

	DxDocument _dxDocument1;

	DxDocument _dxDocument2;


	int _type;

	String _error;



	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DSaveDataTest.class);
	} // end suite

	public void setUp() {
		_a = new DSaveData("1.6");
		_dxDocument1 = new DxTTableDoc();
		_dxDocument2 = new DxTTableDoc();
//		_fileName1 = "." + File.separator + "dataTest" + File.separator
//				+ "loadData7j.dia";
//		_fileName2 = "." + File.separator + "dataTest" + File.separator
//				+ "downData.dia";
//		_fileNameTTS = "." + File.separator + "dataTest" + File.separator
//				+ "downDataTTS.dia";
		_type = 1;
		try {
			_dm1 = new DModel(_dxDocument1, _pathForFiles + "loadData7j.dia");
		} catch (Exception e) {
			System.out
					.println("Abnormal Exception: Should not fail in normal conditions");
		}

		_error = _a
				.saveTimeTable(_dm1.getTTStructure(), _dm1  
						.getDxSetOfInstructors(), _dm1.getDxSetOfSites(), _dm1
						.getSetOfActivitiesSites(), _dm1.getSetOfStuSites(),
						_pathForOutputFiles + "downData.dia");

		try {
			_dm2 = new DModel(_dxDocument2, _pathForOutputFiles + "downData.dia");
		} catch (Exception e) {
			System.out
					.println("Abnormal Exception: Should not fail in normal conditions");
		}
	}

	public void test_getVersion() {
		assertEquals("test_getVersion: assertEquals", "1.6", _a.getVersion());
	}

	/**
	 * test that check the version of timetable
	 */
	public void test1_saveTimeTable() {
		assertEquals("test_saveTimeTable: assertEquals", 0, _error.length());
	}

	/**
	 * test that check that the saved file is the same as the loaded file
	 */
	public void test2_saveTimeTable() {
		assertEquals("test2_saveTimeTable: assertEquals ", true, _dm1
				.getTTStructure().isEquals(_dm2.getTTStructure()));
	}

	/**
	 * test that check that the saved file is the same as the loaded file
	 */
	public void test_loadInstructors() {
		assertEquals("test3_saveTimeTable: assertEquals ", true, _dm1
				.getDxSetOfInstructors().isEqual(_dm2.getDxSetOfInstructors()));

	}

	/**
	 * test that check that the saved file is the same as the loaded file
	 */
	public void test_loadRooms() {
		// if (DxFlags.newRooms) {
		assertEquals("test4_saveTimeTable: assertEquals ", true, _dm1
				.getDxSetOfSites().isEqual(_dm2.getDxSetOfSites()));
		// } else {
		// assertEquals("test4_saveTimeTable: assertEquals ", true, _dm1
		// .getSetOfSites().isEquals(_dm2.getSetOfSites()));
		// }
	}

	/**
	 * test that check that the saved file is the same as the loaded file
	 */
	public void test_loadActivities() {
		assertEquals("test5_saveTimeTable: assertEquals ", true, _dm1
				.getSetOfActivitiesSites().isEquals(
						_dm2.getSetOfActivitiesSites()));
	}

	/**
	 * test that check that the saved file is the same as the loaded file
	 */
	public void test_loadStudents() {
		assertEquals("test6_saveTimeTable: assertEquals ", true, _dm1
				.getSetOfStuSites().isEquals(_dm2.getSetOfStuSites()));
	}

	/**
	 * test that check that the saved file is the same as the loaded file
	 */
	public void test_saveTTStructure() {
		String error = _a.saveTTStructure(_dm1.getTTStructure(), _pathForOutputFiles + "downDataTTS.dia");
		assertEquals("test_saveTTStructure: assertEquals", 0, error.length());
	}
}