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
	public void test_saveTimeTable() {
		DSaveData a = new DSaveData("1.6");
		DxDocument dxDocument = new DxTTableDoc();

		try {
			DModel dm1 = new DModel(dxDocument, _pathForFiles
					+ "loadData7j.dia");
			String error = a.saveTimeTable(dm1.getTTStructure(), dm1
					.getDxSetOfInstructors(), dm1.getDxSetOfSites(), dm1
					.getSetOfActivitiesSites(), dm1.getSetOfStuSites(),
					_pathForOutputFiles + "downData.dia");
			assertEquals("test_saveTimeTable: assertEquals", 0, error.length());

		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test1_saveTimeTable: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test1_saveTimeTable");
			e.printStackTrace();
		}

	}

	public void test_saveTTStructure() {
		DSaveData a = new DSaveData("1.6");
		DxDocument _dxDocument1 = new DxTTableDoc();

		try {
			DModel dm1 = new DModel(_dxDocument1, _pathForFiles
					+ "loadData7j.dia");
			String error = a.saveTTStructure(dm1.getTTStructure(),
					_pathForOutputFiles + "downDataTTS.dia");
			assertEquals("test_saveTTStructure: assertEquals", 0, error
					.length());

		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_saveTTStructure: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_saveTTStructure");
			e.printStackTrace();
		}
	}

	/**
	 * test that check that the saved file is the same as the loaded file
	 */
	public void test_saveTimeTableData() {
		DSaveData a = new DSaveData("1.6");
		DxDocument dxDocument1 = new DxTTableDoc();
		DxDocument dxDocument2 = new DxTTableDoc();
		try {
			DModel dm1 = new DModel(dxDocument1, _pathForFiles
					+ "loadData7j.dia");

			a.saveTimeTable(dm1.getTTStructure(), dm1.getDxSetOfInstructors(),
					dm1.getDxSetOfSites(), dm1.getSetOfActivitiesSites(), dm1
							.getSetOfStuSites(), _pathForOutputFiles
							+ "downData.dia");
			DModel dm2 = new DModel(dxDocument2, _pathForOutputFiles
					+ "downData.dia");
			assertEquals("test_TimeTableStructure: assertEquals ", true, dm1
					.getTTStructure().isEquals(dm2.getTTStructure()));
			assertEquals("test_loadInstructors: assertEquals ", true, dm1
					.getDxSetOfInstructors().isEqual(
							dm2.getDxSetOfInstructors()));
			assertEquals("test_loadRooms: assertEquals ", true, dm1
					.getDxSetOfSites().isEqual(dm2.getDxSetOfSites()));
			assertEquals("test_loadActivities: assertEquals ", true, dm1
					.getSetOfActivitiesSites().isEquals(
							dm2.getSetOfActivitiesSites()));
			assertEquals("test_loadStudents: assertEquals ", true, dm1
					.getSetOfStuSites().isEquals(dm2.getSetOfStuSites()));
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_saveTimeTableData: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_saveTimeTableData");
			e.printStackTrace();
		}
	}

}