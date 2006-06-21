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
import dInternal.DxConflictLimits;
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
	
	public DxAssignRoomsAlgTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxAssignRoomsAlgTest.class);
	} // end suite

	public void test_build1() {
		DxConflictLimits dxCL = new DxConflictLimits();
		String str = "conflictLimits;0;0;0;0;30;0;100;";
		dxCL.readLimits(str);
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
		assertEquals("test_build: assertEquals", 94, dm1.getSetOfActivities()
				.size());
		assertEquals("test_build: assertEquals", 117, dm1.getSetOfEvents()
				.size());
		DxAssignRoomsAlg alg = new DxAssignRoomsAlg(dm1, dxCL);
		alg.doWork();
		assertEquals("test_build: assertEquals", 116, dm1.getSetOfEvents()
				.getNumberOfEventAssign());
		dm1 = null;
		_dDocument1 = null;
	}

	public void test_build2() {
		DxConflictLimits dxCL = new DxConflictLimits();
		String str = "conflictLimits;0;0;0;0;30;0;100;";
		dxCL.readLimits(str);
		DModel dm1 = null;
		DDocument _dDocument1 = new DDocument();
		String fileName = "." + File.separator;
		fileName += "dataTest" + File.separator;
		fileName += "refFiles" + File.separator;
		fileName += "facs" + File.separator;
		fileName += "flsh2_1" + File.separator;
		fileName += "Aut2006flsh170m.dia";
		int type = 1;

		try {
			dm1 = new DModel(_dDocument1, fileName, type);
		} catch (Exception e) {
			// Should not fail in controled conditions
		}
		dm1.changeInDModel(new Object());
		assertEquals("test_build: assertEquals", 199, dm1.getSetOfActivities()
				.size());
		assertEquals("test_build: assertEquals", 253, dm1.getSetOfEvents()
				.size());
		DxAssignRoomsAlg alg = new DxAssignRoomsAlg(dm1, dxCL);
		alg.doWork();
		assertEquals("test_build: assertEquals", 250, dm1.getSetOfEvents()
				.getNumberOfEventAssign());
		dm1 = null;
		_dDocument1 = null;
	}
}