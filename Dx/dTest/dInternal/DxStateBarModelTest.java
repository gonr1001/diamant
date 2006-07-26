/**
 * Created on Mar 27, 2006
 * 
 * 
 * Title: DxStateBarModelTest.java 
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
package dTest.dInternal;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dDeveloper.DxFlags;
import dInterface.DDocument;
import dInterface.DxDocument;
import dInterface.DxTTableDoc;
import dInternal.DModel;
import dInternal.DxStateBarModel;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxStateBarModelTest is a class used to:
 * <p>
 * Test DxStateBarModel
 * <p>
 * 
 */
public class DxStateBarModelTest extends TestCase {

	public DxStateBarModelTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxStateBarModelTest.class);
	} // end suite

	public void testStateBarModel_5j() throws Exception {
		DModel _dm5j;

		DDocument _doc5j;
		_doc5j = new DDocument();

		DxStateBarModel sbm;

		DxDocument _dxDoc5j;
		_dxDoc5j = new DxTTableDoc();
		String fileName = "." + File.separator + "dataTest" + File.separator
				+ "loadData5j.dia";
		int _type = 1;
		if (DxFlags.newDoc) {
			_dm5j = new DModel(_dxDoc5j, fileName);
			_dm5j.changeInDModel(new Object());
			sbm = new DxStateBarModel(_dm5j);
			sbm.update();
		} else {
			_dm5j = new DModel(_doc5j, fileName, _type);
			_dm5j.changeInDModel(new Object());
			sbm = new DxStateBarModel(_dm5j);
			sbm.update();
		}
		assertEquals("test0_StateBarModel_5j: assertEquals", -1, sbm.elementAt(
				0).getValue());
		assertEquals("test1_StateBarModel_5j: assertEquals", 11, sbm.elementAt(
				1).getValue());
		assertEquals("test2_StateBarModel_5j: assertEquals", 7, sbm
				.elementAt(2).getValue());
		assertEquals("test3_StateBarModel_5j: assertEquals", 44, sbm.elementAt(
				3).getValue());
		assertEquals("test4_StateBarModel_5j: assertEquals", 15, sbm.elementAt(
				4).getValue());
		assertEquals("test5_StateBarModel_5j: assertEquals", 27, sbm.elementAt(
				5).getValue());
		assertEquals("test6_StateBarModel_5j: assertEquals", 3, sbm
				.elementAt(6).getValue());
		assertEquals("test7_StateBarModel_5j: assertEquals", 4, sbm
				.elementAt(7).getValue());
		assertEquals("test8_StateBarModel_5j: assertEquals", 0, sbm
				.elementAt(8).getValue());
		assertEquals("test9_StateBarModel_5j: assertEquals", 2, sbm
				.elementAt(9).getValue());
		assertEquals("test10_StateBarModel_5j: assertEquals", 2, sbm.elementAt(
				10).getValue());
	}

	public void testStateBarModel_7j() throws Exception {
		DDocument _doc7j = new DDocument();
		DxDocument _dxDoc7j = new DxTTableDoc();
		DModel _dm7j;
		
		String fileName1 = "." + File.separator + "dataTest" + File.separator
				+ "loadData7j.dia";
		int _type = 1;
		if(DxFlags.newDoc){
			_dm7j = new DModel(_dxDoc7j, fileName1);
		} else {
			_dm7j = new DModel(_doc7j, fileName1, _type);
		}
//		_dm7j = new DModel(_doc7j, fileName1, _type);
		_dm7j.changeInDModel(new Object());
		DxStateBarModel sbm = new DxStateBarModel(_dm7j);
		sbm.update();
		assertEquals("test0_StateBarModel_7j: assertEquals", -1, sbm.elementAt(
				0).getValue());
		assertEquals("test1_StateBarModel_7j: assertEquals", 11, sbm.elementAt(
				1).getValue());
		assertEquals("test2_StateBarModel_7j: assertEquals", 7, sbm
				.elementAt(2).getValue());
		assertEquals("test3_StateBarModel_7j: assertEquals", 44, sbm.elementAt(
				3).getValue());
		assertEquals("test4_StateBarModel_7j: assertEquals", 15, sbm.elementAt(
				4).getValue());
		assertEquals("test5_StateBarModel_7j: assertEquals", 27, sbm.elementAt(
				5).getValue());
		assertEquals("test6_StateBarModel_7j: assertEquals", 2, sbm
				.elementAt(6).getValue());
		assertEquals("test7_StateBarModel_7j: assertEquals", 0, sbm
				.elementAt(7).getValue());
		assertEquals("test8_StateBarModel_7j: assertEquals", 0, sbm
				.elementAt(8).getValue());
		assertEquals("test9_StateBarModel_7j: assertEquals", 0, sbm
				.elementAt(9).getValue());
		assertEquals("test10_StateBarModel_7j: assertEquals", 0, sbm.elementAt(
				10).getValue());
	}

}
