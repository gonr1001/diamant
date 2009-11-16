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

import java.io.File;

import ca.sixs.dTest.dFonctions.AdminFonctionsTest;
import ca.sixs.dTest.dFonctions.GenFonctionsTest;
import ca.sixs.util.pref.FileFilterPrefTest;
import ca.sixs.util.pref.LookAndFeelPrefTest;
import ca.sixs.util.pref.ParametersPrefTest;
import ca.sixs.util.pref.ReportPrefTest;

import junit.framework.Test;
import junit.framework.TestSuite;
import dInternal.dData.dRooms.DxSetOfSitesTest;
import dInternal.dOptimization.DxAssignRoomsAlgTest;
import dInternal.dTimeTable.DxTTStructureTest;
import dTest.dInternal.DxLoadDataTest;
import dTest.dInternal.dData.DLoadDataTest;
import dTest.dInternal.dData.DSaveDataTest; 
//import dTest.dInternal.dData.dActivities.DSetOfActivitiesInSitesTest;
import dTest.dInternal.dData.dActivities.DxSetOfActivitiesSitesTest;
import dTest.dInternal.dData.dActivities.DxSetOfActivitiesTest;
import dTest.dInternal.dData.dInstructors.DxInstructorsReaderTest;
import dTest.dInternal.dData.dRooms.DxSiteReaderTest;
import dTest.dInternal.dOptimizationTest.ConditionsTest;
import dTest.dInternal.dOptimizationTest.DxAssignAllAlgTest;
import dTest.dInternal.dOptimizationTest.RoomsConditionsTest;
import dTest.dInternal.dOptimizationTest.SetOfEventsTest;
import eTest.ByteInputFileTest;
import eTest.ByteOutputFileTest;

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

		File forOutputTests = new File("." + File.separator + "forOutputTests");
		if (!forOutputTests.isDirectory()) {
			forOutputTests.mkdir();
		}

		TestSuite suite = new TestSuite("rgr selected Tests");

		suite.addTest(DxSetOfActivitiesSitesTest.suite());
		//suite.addTest(DxSetOfActivitiesSitesTest.suite());

		suite.addTest(DxSetOfActivitiesTest.suite());
		//suite.addTest(DxSetOfActivitiesTest.suite());

		
			suite.addTest(DxSetOfSitesTest.suite());
			suite.addTest(DxAssignRoomsAlgTest.suite());
			suite.addTest(DxTTStructureTest.suite());
			suite.addTest(DxSiteReaderTest.suite());
			suite.addTest(ByteInputFileTest.suite());
			suite.addTest(ByteOutputFileTest.suite());
			suite.addTest(DxSetOfSitesTest.suite());
			suite.addTest(DxAssignAllAlgTest.suite());
			suite.addTest(RoomsConditionsTest.suite());

			// if (DxFlags.newDxLoadData) {
			suite.addTest(DxLoadDataTest.suite()); // TODO to review
			// } else {
			suite.addTest(DLoadDataTest.suite()); // TODO to review
			// }

			suite.addTest(SetOfEventsTest.suite());
			suite.addTest(DSaveDataTest.suite());

			suite.addTest(LookAndFeelPrefTest.suite());
			suite.addTest(ParametersPrefTest.suite());
			suite.addTest(ReportPrefTest.suite());
			suite.addTest(FileFilterPrefTest.suite());
			suite.addTest(ConditionsTest.suite());
			suite.addTest(GenFonctionsTest.suite());
		
		suite.addTest(DxInstructorsReaderTest.suite());
		suite.addTest(AdminFonctionsTest.suite());
		
		System.out.println("Bye I was in rgr selected Tests");
		return suite;
	}
}
