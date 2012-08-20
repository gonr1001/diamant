/**
 * 
 * Title: RefinedStudMixAlgoTest $Revision: 1.11 $ $Date: 2008-02-20 16:58:30 $
 * Description: RefinedStudMixAlgoTest is a class used to test the class
 * 				RefinedStudMixAlgo 
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
import dInterface.DxDocument;
import dInterface.DxTTableDoc;
import dInternal.DModel;
import dInternal.dOptimization.RefinedStudMixAlgo;

public class RefinedStudMixAlgoTest extends TestCase {

	RefinedStudMixAlgo _refined;

	DModel _dm1;

	DModel _dm2;

	DxDocument _dxDocument1;

	DxDocument _dxDocument2;

	String _fileName;

	int _type;

	public RefinedStudMixAlgoTest(String name) {
		super(name);
	}

	public void setUp() {
		_dxDocument1 = new DxTTableDoc();
		_fileName = "." + File.separator + "dataTest" + File.separator
				+ "loadData5j.dia";
		_type = 1;
		try {
			_dm1 = new DModel(_dxDocument1, _fileName.toString());
		} catch (Exception e) {
			// Should not fail in controled conditions
		}
		_dm1.getConditionsToTest().initAllConditions(); // Affectation initialize
		_dxDocument2 = new DxTTableDoc();
		try {
			// if (DxFlags.newDoc) {
			_dm2 = new DModel(_dxDocument2, _fileName.toString());
			// } else {
			// _dm2 = new DModel(_dDocument2, _fileName.toString(), _type);
			// }
		} catch (Exception e) {
			// Should not fail in controled conditions
		}

	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(RefinedStudMixAlgoTest.class);
	} // end suite

	public void test_build() {
//		 _refined.build();
	 assertEquals("test_build: assertEquals", "", _dm1);
	}
}