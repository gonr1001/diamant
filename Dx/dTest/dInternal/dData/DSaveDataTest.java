package dTest.dInternal.dData;

/**
 *
 * Title: DSaveDataTest $Revision $  $Date: 2006-07-06 16:01:20 $
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
 * @author  $Author: caln1901 $
 * @since JDK1.3
 */

import java.io.File;

import dConstants.DConst;
import dInterface.DDocument;
import dInternal.DModel;
import dInternal.dData.DSaveData;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DSaveDataTest extends TestCase {
	DSaveData _a;

	DModel _dm1;

	DModel _dm2;

	DDocument _dDocument1;

	DDocument _dDocument2;

	String _fileName1; // to read

	String _fileName2; // to write and read

	String _fileNameTTS;

	int _type;

	String _error;

	public DSaveDataTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DSaveDataTest.class);
	} // end suite

	public void setUp() {
		_a = new DSaveData("1.6");
		_dDocument1 = new DDocument();
		_dDocument2 = new DDocument();
		_fileName1 = "." + File.separator + "dataTest" + File.separator
				+ "loadData7j.dia";
		_fileName2 = "." + File.separator + "dataTest" + File.separator
				+ "downData.dia";
		_fileNameTTS = "." + File.separator + "dataTest" + File.separator
				+ "downDataTTS.dia";
		_type = 1;
		try {
			_dm1 = new DModel(_dDocument1, _fileName1, _type);
		} catch (Exception e) {
			// Should not fail in controled conditions
		}
		if (DConst.newRooms) {
			_error = _a.saveTimeTable(_dm1.getTTStructure(), _dm1
					.getDxSetOfInstructors(), _dm1.getDxSetOfSites(), _dm1
					.getSetOfActivitiesSites(), _dm1.getSetOfStuSites(),
					_fileName2);

		} else {
			_error = _a.saveTimeTable(_dm1.getTTStructure(), _dm1
					.getDxSetOfInstructors(), _dm1.getSetOfSites(), _dm1
					.getSetOfActivitiesSites(), _dm1.getSetOfStuSites(),
					_fileName2);
		}

		try {
			_dm2 = new DModel(_dDocument2, _fileName2, _type);
		} catch (Exception e) {
			// Should not fail in controled conditions
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
		if (DConst.newRooms) {
			assertEquals("test4_saveTimeTable: assertEquals ", true, _dm1
					.getDxSetOfSites().isEqual(_dm2.getDxSetOfSites()));
		} else {
			assertEquals("test4_saveTimeTable: assertEquals ", true, _dm1
					.getSetOfSites().isEquals(_dm2.getSetOfSites()));
		}
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
		String error = _a.saveTTStructure(_dm1.getTTStructure(), _fileNameTTS);
		assertEquals("test_saveTTStructure: assertEquals", 0, error.length());
	}
}