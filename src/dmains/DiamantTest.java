/**
 *
 * Title: DiamantTest 
 * Description: Diamant 1.5Test is a class used to call the suite test.
 * Copyright (c) 2002 by rgr.
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
 */
//------------------------------
/* Attention this file must be used with the terminal application
 * to execute you must type
 * java junit.swingui.TestRunner Diamant 1.5Test
 * in the application directory
 * eventually it is necessary to fix the CLASSPATH
 * include in .login file a line like
 * setenv classpath = %classpath%;INSTALL_DIR/junit.jar
 */
package dmains;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestSuite;
import developer.DxFlags;
import dInternal.dOptimization.DxAssignRoomsAlgTest;
import dInternal.dTimeTable.DxTTStructureTest;
import dTest.ScanFileTest;
import dTest.dInterface.dAssignementDlgs.DxEditEventDlgTest;
import dTest.dInterface.dMenus.DFileMenuTest;
import dTest.dInternal.DObjectTest;
import dTest.dInternal.DResourceTest;
import dTest.dInternal.DSetOfResourcesTest;
import dTest.dInternal.DValueTest;
import dTest.dInternal.DataExchangeTest;
import dTest.dInternal.DxLoadDataTest;
import dTest.dInternal.DxStateBarModelTest;
import dTest.dInternal.DxStateTest;
import dTest.dInternal.dData.ByteArrayMessageTest;
import dTest.dInternal.dData.DLoadDataTest;
import dTest.dInternal.dData.DSaveDataTest;
import dTest.dInternal.dData.DStandardReportDataTest;
import dTest.dInternal.dData.DxAvailabilityTest;
import dTest.dInternal.dData.StandardCollectionTest;
import dTest.dInternal.dData.dActivities.DSetOfActivitiesSitesTest;
import dTest.dInternal.dData.dActivities.DSetOfActivitiesTest;
import dTest.dInternal.dData.dActivities.DxSetOfActivitiesSitesTest;
import dTest.dInternal.dData.dInstructors.DxInstructorTest;
import dTest.dInternal.dData.dInstructors.DxInstructorsReaderTest;
import dTest.dInternal.dData.dInstructors.DxSetOfInstructorsTest;
import dTest.dInternal.dData.dRooms.DxSetOfSitesTest;
import dTest.dInternal.dData.dRooms.DxSiteReaderTest;
import dTest.dInternal.dData.dStudents.SetOfStuSitesTest;
import dTest.dInternal.dData.dStudents.StudentTest;
import dTest.dInternal.dDataTxt.ActivityTest;
import dTest.dInternal.dDataTxt.AssignmentTest;
import dTest.dInternal.dDataTxt.ResourceTest;
import dTest.dInternal.dDataTxt.SectionTest;
import dTest.dInternal.dDataTxt.TypeTest;
import dTest.dInternal.dDataTxt.UnityTest;
import dTest.dInternal.dOptimizationTest.ConditionsTest;
import dTest.dInternal.dOptimizationTest.ConflictsAttachTest;
import dTest.dInternal.dOptimizationTest.DxAssignAllAlgTest;
import dTest.dInternal.dOptimizationTest.InstructorsConditionsTest;
import dTest.dInternal.dOptimizationTest.RefinedStudMixAlgoTest;
import dTest.dInternal.dOptimizationTest.RoomsConditionsTest;
import dTest.dInternal.dOptimizationTest.SetOfEventsTest;
import dTest.dInternal.dOptimizationTest.StudentsConditionsTest;
import dTest.dInternal.dOptimizationTest.StudentsConflictsMatrixTest;
import dTest.dInternal.dTimeTable.CycleTest;
import dTest.dInternal.dTimeTable.DayTest;
import dTest.dInternal.dTimeTable.PeriodTest;
import dTest.dInternal.dTimeTable.SequenceTest;
import dTest.dInternal.dTimeTable.TTStructureTest;
import dTest.dInternal.dUtil.DXToolsMethodsTest;
import eTest.ByteInputFileTest;
import eTest.ByteOutputFileTest;
import eTest.FilterFileTest;
import eTest.SemiExtendedAsciiFileTest;

public class DiamantTest {

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
		System.out.println("Hello I am in Diamant tests");
		TestSuite suite = new TestSuite("Diamant Tests");
		String str = System.getProperty("user.dir");
		System.out.println("User Dir here  :" + str);
		File forOutputTests = new File("."+File.separator+ "forOutputTests");
		forOutputTests.mkdir();
		System.out.println("fot path" +forOutputTests.getAbsolutePath());
		// Using files	
		suite.addTest(DxTTStructureTest.suite());
		suite.addTest(ByteInputFileTest.suite());
		suite.addTest(ByteOutputFileTest.suite());
		suite.addTest(FilterFileTest.suite());
		suite.addTest(SemiExtendedAsciiFileTest.suite());	
		suite.addTest(DxInstructorsReaderTest.suite());
		suite.addTest(DxSetOfSitesTest.suite());
		suite.addTest(DxAssignAllAlgTest.suite());
		if (DxFlags.newAlg) {
			suite.addTest(DxAssignRoomsAlgTest.suite());
		}		
		suite.addTest(RefinedStudMixAlgoTest.suite()); //TODO to be changed 
		suite.addTest(PeriodTest.suite());
		suite.addTest(SequenceTest.suite()); 
		suite.addTest(DayTest.suite());
		suite.addTest(CycleTest.suite());
		suite.addTest(TTStructureTest.suite()); 		
		suite.addTest(SetOfEventsTest.suite());

		
		// Not using files
		suite.addTest(DxAvailabilityTest.suite());
		suite.addTest(DxInstructorTest.suite());
		suite.addTest(DxSetOfInstructorsTest.suite());
		suite.addTest(ResourceTest.suite());
		suite.addTest(AssignmentTest.suite());
		suite.addTest(UnityTest.suite());
		suite.addTest(SectionTest.suite());
		suite.addTest(TypeTest.suite());
		suite.addTest(ActivityTest.suite());
		suite.addTest(DXToolsMethodsTest.suite());
		suite.addTest(ConflictsAttachTest.suite());
		
		//to be classed 		
		suite.addTest(StudentsConflictsMatrixTest.suite());
		suite.addTest(InstructorsConditionsTest.suite());
		suite.addTest(DxSiteReaderTest.suite());
		suite.addTest(RoomsConditionsTest.suite());
		suite.addTest(StudentsConditionsTest.suite());
		suite.addTest(ConditionsTest.suite());
		suite.addTest(ScanFileTest.suite());
		suite.addTest(SetOfStuSitesTest.suite());
		suite.addTest(DSetOfActivitiesTest.suite());
		if (DxFlags.newActivity) {
			suite.addTest(DxSetOfActivitiesSitesTest.suite());
		} else {
			suite.addTest(DSetOfActivitiesSitesTest.suite());
		}
		suite.addTest(DValueTest.suite());
		suite.addTest(DObjectTest.suite());
		suite.addTest(DxStateTest.suite());
		suite.addTest(DxStateBarModelTest.suite());
		suite.addTest(DataExchangeTest.suite());
		suite.addTest(ByteArrayMessageTest.suite());
		suite.addTest(StandardCollectionTest.suite());
		suite.addTest(DResourceTest.suite());
		if (DxFlags.newDxLoadData) {
			suite.addTest(DxLoadDataTest.suite());
		} else {
			suite.addTest(DLoadDataTest.suite());
		}	
		suite.addTest(DSaveDataTest.suite());
		suite.addTest(DSetOfResourcesTest.suite());
		suite.addTest(DStandardReportDataTest.suite());
		suite.addTest(SetOfStuSitesTest.suite());
		suite.addTest(StudentTest.suite());
		suite.addTest(ResourceTest.suite());
		suite.addTest(DFileMenuTest.suite());
		suite.addTest(DxEditEventDlgTest.suite());
		
		System.out.println("Bye I was in Diamant tests");
		return suite;
	}
}