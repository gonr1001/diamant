/**
 * 
 * Title: FirstAffectAlgorithmTest $Revision: 1.9 $ $Date: 2006-06-20 14:39:27 $
 * Description: FirstAffectAlgorithmTest is a class used to 
 * 
 * 
 * Copyright (c) 2001 by rgr. All rights reserved.
 * 
 * 
 * This software is the confidential and proprietary information of rgr-fdl.
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with rgr-fdl.
 * 
 * @version $Version$
 * @author $Author: gonzrubi $
 * @since JDK1.3
 */

package dTest.dInternal.dOptimizationTest;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInterface.DDocument;
import dInternal.DModel;
import dInternal.DxPreferences;
import dInternal.dOptimization.FirstAffectAlgorithm;

public class FirstAffectAlgorithmTest extends TestCase {

	FirstAffectAlgorithm _first;

	// DModel _dm1;

	// DModel _dm2;
	//
	// DDocument _dDocument1;
	//
	// DDocument _dDocument2;
	//
	// String _fileName;
	//
	// int _type;

	public FirstAffectAlgorithmTest(String name) {
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
		return new TestSuite(FirstAffectAlgorithmTest.class);
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
		_first = new FirstAffectAlgorithm(dm1);
		_first.doWork();
		assertEquals("test_build: assertEquals", 255, dm1.getSetOfEvents()
				.getNumberOfEventAssign());
		dm1 = null;
		_dDocument1 = null;
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
		_first = new FirstAffectAlgorithm(dm1);
		_first.doWork();
		assertEquals("test_build: assertEquals", 245, dm1.getSetOfEvents()
				.getNumberOfEventAssign());
		dm1 = null;
		_dDocument1 = null;
	}
}