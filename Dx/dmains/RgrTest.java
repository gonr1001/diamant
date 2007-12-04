/**
 * Created on June 21, 2006
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
import dInternal.dOptimization.DxAssignRoomsAlgTest;
//import dTest.dInterface.dMenus.DFileMenuTest;
import dTest.dInternal.DxLoadDataTest;
import dTest.dInternal.dData.DLoadDataTest;
import dTest.dInternal.dData.DSaveDataTest;
//import dTest.dInternal.dData.DStandardReportDataTest;
import dTest.dInternal.dData.dInstructors.DxInstructorsReaderTest;
import dTest.dInternal.dOptimizationTest.ConditionsTest;
import dTest.dInternal.dOptimizationTest.DxAssignAllAlgTest;
import dTest.dInternal.dOptimizationTest.RoomsConditionsTest;
import dTest.dInternal.dOptimizationTest.SetOfEventsTest;
import developer.DxFlags;
import eTest.ByteInputFileTest;
import eTest.ByteOutputFileTest;
import eTest.DxFilterFileTest;
import eTest.FilterFileTest;


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
		System.out.println("Hello I am rgr selected Tests");
		TestSuite suite = new TestSuite("rgr selected Tests");

		suite.addTest(ByteInputFileTest.suite());
		suite.addTest(ByteOutputFileTest.suite());
		suite.addTest(FilterFileTest.suite());
		suite.addTest(DxFilterFileTest.suite());
		
		suite.addTest(DxAssignAllAlgTest.suite());
		suite.addTest(RoomsConditionsTest.suite());
		if (DxFlags.newAlg) {
			suite.addTest(DxAssignRoomsAlgTest.suite());
		}
		if (DxFlags.newDxLoadData) {
			suite.addTest(DxLoadDataTest.suite());
		} else {
			suite.addTest(DLoadDataTest.suite());
		}
		suite.addTest(DxInstructorsReaderTest.suite());
		suite.addTest(SetOfEventsTest.suite());
		suite.addTest(ConditionsTest.suite());
		suite.addTest(DSaveDataTest.suite());

		System.out.println("Bye I was in rgr selected Tests");
		return suite;
	}
}
