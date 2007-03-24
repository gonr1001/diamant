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



import dTest.dInternal.DxStateBarModelTest;
import dTest.dInternal.dData.dRooms.DxSetOfSitesTest;
import dTest.dInternal.dData.dRooms.DxSiteReaderTest;
import dTest.dInternal.dOptimizationTest.ConditionsTest;
import dTest.dInternal.dOptimizationTest.DxAssignAllAlgTest;
import dTest.dInternal.dOptimizationTest.DxAssignRoomsAlgTest;
import dTest.dInternal.dOptimizationTest.InstructorsConditionsTest;
import dTest.dInternal.dOptimizationTest.StudentsConditionsTest;
import dTest.dInternal.dOptimizationTest.StudentsConflictsMatrixTest;

import dTest.dInternal.dOptimizationTest.RoomsConditionsTest;
import dTest.dInternal.dOptimizationTest.SetOfEventsTest;

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
	 * @return suite
	 * 	 
	 */
	public static Test suite() {
		System.out.println("Hello I am in tests");
		TestSuite suite = new TestSuite("rgrTest");
//		suite.addTest(DxSiteReaderTest.suite());
//		suite.addTest(DxSetOfSitesTest.suite());
//		suite.addTest(DxAssignAllAlgTest.suite());
//		suite.addTest(DxAssignRoomsAlgTest.suite());
//		suite.addTest(SetOfEventsTest.suite());
//		suite.addTest(RoomsConditionsTest.suite());
//		suite.addTest(DxStateBarModelTest.suite());
		suite.addTest(StudentsConflictsMatrixTest.suite());
		suite.addTest(InstructorsConditionsTest.suite());
	    suite.addTest(DxSiteReaderTest.suite());
		suite.addTest(RoomsConditionsTest.suite());
		suite.addTest(StudentsConditionsTest.suite());
		suite.addTest(ConditionsTest.suite());
		System.out.println("Bye I was in rgrTest");
		return suite;
	}
}
