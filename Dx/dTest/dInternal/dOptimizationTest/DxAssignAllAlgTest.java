/**
 * Created on Jun 16, 2006
 * 
 * 
 * Title: DxAssignAllAlgTest.java 
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
import dInternal.dOptimization.DxAssignAllAlg;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxAssignAllAlgTest is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
public class DxAssignAllAlgTest extends TestCase {

	DxAssignAllAlg _alg;

	public DxAssignAllAlgTest(String name) {
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
		return new TestSuite(DxAssignAllAlgTest.class);
	} // end suite

	public void test_build1() {
		DModel dm1 = null;
		DDocument _dDocument1 = new DDocument();
		String fileName = "." + File.separator;
		fileName += "dataTest" + File.separator;
		fileName += "refFiles" + File.separator;
		fileName += "facs" + File.separator;
		fileName += "sciBase" + File.separator;
		fileName += "scNoAssigned.dia";
		int type = 1;

		try {
			dm1 = new DModel(_dDocument1, fileName, type);
		} catch (Exception e) {
			// Should not fail in controled conditions
		}
		dm1.changeInDModel(new Object());
		assertEquals("test_build: assertEquals", 140, dm1.getSetOfActivities()
				.size());
		assertEquals("test_build: assertEquals", 275, dm1.getSetOfEvents()
				.size());
		_alg = new DxAssignAllAlg(dm1);
		_alg.build();
		assertEquals("test_build: assertEquals", 255, dm1.getSetOfEvents()
				.getNumberOfEventAssign());
	}

	public void test_build2() {
		DModel dm1 = null;
		DDocument _dDocument1 = new DDocument();
		String fileName = "." + File.separator;
		fileName += "dataTest" + File.separator;
		fileName += "refFiles" + File.separator;
		fileName += "facs" + File.separator;
		fileName += "genAlgo" + File.separator;
		fileName += "genNoAssigned.dia";
		int type = 1;

		try {
			dm1 = new DModel(_dDocument1, fileName, type);
		} catch (Exception e) {
			// Should not fail in controled conditions
		}
		dm1.changeInDModel(new Object());
		assertEquals("test_build: assertEquals", 140, dm1.getSetOfActivities()
				.size());
		assertEquals("test_build: assertEquals", 293, dm1.getSetOfEvents()
				.size());
		_alg = new DxAssignAllAlg(dm1);
		_alg.build();
		assertEquals("test_build: assertEquals", 245, dm1.getSetOfEvents()
				.getNumberOfEventAssign());
	}
}
