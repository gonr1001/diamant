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



import dInternal.dOptimization.DxAssignRoomsAlgTest;
import dTest.dInternal.DxStateBarModelTest;
import dTest.dInternal.dData.DSaveDataTest;
import dTest.dInternal.dData.DStandardReportDataTest;
import dTest.dInternal.dData.dRooms.DRoomTest;
import dTest.dInternal.dData.dRooms.DxSiteReaderTest;
import dTest.dInternal.dData.dRooms.SetOfSitesTest;
import dTest.dInternal.dOptimizationTest.ConditionsTest;
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
		System.out.println("Hello I am rgr selected Tests");
		TestSuite suite = new TestSuite("rgr selected Tests");
		suite.addTest(DxAssignRoomsAlgTest.suite());
		suite.addTest(DRoomTest.suite());
		suite.addTest(SetOfSitesTest.suite());
		suite.addTest(SetOfEventsTest.suite());
		suite.addTest(ConditionsTest.suite());
		suite.addTest(DxStateBarModelTest.suite());
		suite.addTest(DStandardReportDataTest.suite());
		suite.addTest(RoomsConditionsTest.suite());
		suite.addTest(DSaveDataTest.suite());
	    suite.addTest(DxSiteReaderTest.suite());


		System.out.println("Bye I was in rgr selected Tests");
		return suite;
	}
}
