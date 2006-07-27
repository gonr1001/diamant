/**
 * Created on Jun 29, 2006
 * 
 * 
 * Title: AmineTest.java 
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
import dTest.dInternal.dTimeTable.ATTStructureTest;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: AmineTest is a class used to:
 * <p>
 * TODO:insert comments
 * <p> 
 * 
 */
public class AmineTest {
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
		System.out.println("Hello I am in Aminetests");
		TestSuite suite = new TestSuite("AmineTest");

		suite.addTest(ATTStructureTest.suite());

		System.out.println("Bye I was in AmineTest");
		return suite;
	}
}
