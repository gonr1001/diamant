/**
 * Created on May 4, 2006
 * 
 * 
 * Title: DxRWInstructorsTest.java 
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
package dTest.dInternal.dData.dInstructors;

import dConstants.DConst;
import dInternal.dData.DLoadData;
import dInternal.dData.dInstructors.DxRWInstructors;
import dInternal.dData.dInstructors.SetOfInstructors;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxRWInstructorsTest is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
public class DxRWInstructorsTest extends TestCase {

	/**
	 * @param args
	 */
	public DxRWInstructorsTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxRWInstructorsTest.class);
	} // end suite

	public void test_analyseTokens() {
		String tokens = "    1k" + "\r\n" + "ADM111" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
		DLoadData ld = new DLoadData();
		DxRWInstructors dxrwi = new DxRWInstructors(5, 14);		
		assertEquals("test_analyseTokens: assertEquals", false,
				dxrwi.analyseTokens(ld.buildDataExchange(tokens.getBytes()),
						0));

	}

	/**
	 * test1_analyseTokens, test that analyse the empty instructor name
	 * in the instructors file
	 */
	public void test1_analyseTokens() {
		String tokens = "    2" + "\r\n" + "" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n" + "POLM" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
		DLoadData ld = new DLoadData();
		SetOfInstructors instructorsList = new SetOfInstructors(5, 14);
		instructorsList.analyseTokens(ld.buildDataExchange(tokens.getBytes()),
				0);
		assertEquals("test1_analyseTokens: assertEquals", DConst.INST_TEXT3,
				instructorsList.getError().substring(0,
						DConst.INST_TEXT3.length()));

	}

	/**
	 * test2_analyseTokens, test that analyse the wrong number of
	 * instructors in the instructors file
	 */
	public void test2_analyseTokens() {
		String tokens = "    3" + "\r\n" + "JAC" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n" + "POLM" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
		DLoadData ld = new DLoadData();
		SetOfInstructors instructorsList = new SetOfInstructors(5, 14);
		instructorsList.analyseTokens(ld.buildDataExchange(tokens.getBytes()),
				0);
		assertEquals("test2_analyseTokens: assertEquals", DConst.INST_TEXT1,
				instructorsList.getError().substring(0,
						DConst.INST_TEXT1.length()));
	}

	/**
	 * test3_analyseTokens, test the number of instructor availabilities
	 * periods per day in the instructors file
	 */
	public void test3_analyseTokens() {
		String tokens = "    2" + "\r\n" + "" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n" + "POLM" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1 6" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
		DLoadData ld = new DLoadData();
		SetOfInstructors instructorsList = new SetOfInstructors(5, 14);
		instructorsList.analyseTokens(ld.buildDataExchange(tokens.getBytes()),
				0);
		assertEquals("test3_analyseTokens: assertEquals", DConst.INST_TEXT3,
				instructorsList.getError().substring(0,
						DConst.INST_TEXT3.length()));
	}

	/**
	 * test4_analyseTokens, test that analyse the wrong description of
	 * instructors availability in the instructors file
	 */
	public void test4_analyseTokens() {
		String tokens = "    2" + "\r\n" + "JAC" + "\r\n"
				+ "1 3 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n" + "POLM" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
		DLoadData ld = new DLoadData();
		SetOfInstructors instructorsList = new SetOfInstructors(5, 14);
		instructorsList.analyseTokens(ld.buildDataExchange(tokens.getBytes()),
				0);
		assertEquals("test4_analyseTokens: assertEquals", DConst.INST_TEXT4,
				instructorsList.getError().substring(0,
						DConst.INST_TEXT4.length()));
	}

}
