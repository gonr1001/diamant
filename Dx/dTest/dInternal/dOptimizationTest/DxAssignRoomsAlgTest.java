/**
 * Created on Jun 16, 2006
 * 
 * 
 * Title: DxAssignRoomsAlgTest.java 
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
 * 
 * 
 */
package dTest.dInternal.dOptimizationTest;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInterface.DDocument;
import dInternal.DModel;
import dInternal.DxPreferences;
import dInternal.dOptimization.DxAssignRoomsAlg;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxAssignRoomsAlgTest is a class used to:
 * <p>
 * TODO:insert comments
 * <p> 
 * 
 */
public class DxAssignRoomsAlgTest extends TestCase {
	DxAssignRoomsAlg _alg;

	public DxAssignRoomsAlgTest(String name) {
		super(name);
	}

	public void setUp() {
		DxPreferences pref = new DxPreferences();
		String str = "0;0;0;0;30;0;100;";
		pref.setConflicLimitsString(str);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxAssignRoomsAlgTest.class);
	} // end suite

	public void test_build1() {
		DModel dm1 = null;
		DDocument _dDocument1 = new DDocument();
		String fileName = "." + File.separator;
		fileName += "dataTest" + File.separator;
		fileName += "refFiles" + File.separator;
		fileName += "facs" + File.separator;
		fileName += "flsh2_1" + File.separator;
		fileName += "RoomAffContTT.dia";
		int type = 1;

		try {
			dm1 = new DModel(_dDocument1, fileName, type);
		} catch (Exception e) {
			// Should not fail in controled conditions
		}
		dm1.changeInDModel(new Object());
//		assertEquals("test_build: assertEquals", 94, dm1.getSetOfActivities()
//				.size());
//		assertEquals("test_build: assertEquals", 117, dm1.getSetOfEvents()
//				.size());
//		_alg = new DxAssignRoomsAlg(dm1);
//		_alg.build();
//		assertEquals("test_build: assertEquals", 3, dm1.getSetOfEvents()
//				.getNumberOfEventAssign());
	}

	public void test_build2() {
		DModel dm1 = null;
		DDocument _dDocument1 = new DDocument();
		String fileName = "." + File.separator;
		fileName += "dataTest" + File.separator;
		fileName += "refFiles" + File.separator;
		fileName += "facs" + File.separator;
		fileName += "flsh2_1" + File.separator;
		fileName += "RoomAffContTT.dia";
		int type = 1;

		try {
			dm1 = new DModel(_dDocument1, fileName, type);
		} catch (Exception e) {
			// Should not fail in controled conditions
		}
		dm1.changeInDModel(new Object());
//		assertEquals("test_build: assertEquals", 94, dm1.getSetOfActivities()
//				.size());
//		assertEquals("test_build: assertEquals", 117, dm1.getSetOfEvents()
//				.size());
//		_alg = new DxAssignRoomsAlg(dm1);
//		_alg.build();
//		assertEquals("test_build: assertEquals", 245, dm1.getSetOfEvents()
//				.getNumberOfEventAssign());
	}
}