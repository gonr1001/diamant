/**
 * Created on Jun 21, 2006
 * 
 * 
 * Title: MiniTest.java 
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
package dmains;

import junit.framework.Test;
import junit.framework.TestSuite;

import dTest.dInternal.dOptimizationTest.DxAssignAllAlgTest;
import dTest.dInternal.dOptimizationTest.DxAssignRoomsAlgTest;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: RgrTest is a class used to:
 * <p>
 * pass only some tests quickly
 * <p>
 * 
 */
public class RgrTest {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	/**
	 * 
	 * @return
	 */
	public static Test suite() {
		System.out.println("Hello I am in tests");
		TestSuite suite = new TestSuite("rgrTest");

		suite.addTest(DxAssignAllAlgTest.suite());
		suite.addTest(DxAssignRoomsAlgTest.suite());

		System.out.println("Bye I was in rgrTest");
		return suite;
	}
}
