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
 * setenv classpath=%classpath%;INSTALL_DIR/junit.jar
 *
 *
 */
package dmains;

import junit.framework.Test;
import junit.framework.TestSuite;
import dTest.dInternal.DObjectTest;
import dTest.dInternal.DResourceTest;
import dTest.dInternal.DSetOfResourcesTest;
import dTest.dInternal.DSetOfStatesTest;
import dTest.dInternal.DStateTest;
import dTest.dInternal.DValueTest;
import dTest.dInternal.DataExchangeTest;
import dTest.dInternal.dData.ByteArrayMessageTest;
import dTest.dInternal.dData.DLoadDataTest;
import dTest.dInternal.dData.DSaveDataTest;
import dTest.dInternal.dData.DStandardReportDataTest;
import dTest.dInternal.dData.StandardCollectionTest;
import dTest.dInternal.dData.dActivities.DSetOfActivitiesSitesTest;
import dTest.dInternal.dData.dActivities.DSetOfActivitiesTest;
import dTest.dInternal.dData.dInstructors.DSetOfInstructorsTest;
import dTest.dInternal.dData.dRooms.DRoomTest;
import dTest.dInternal.dData.dRooms.SetOfCategoriesTest;
// import dTest.dInternal.dData.dRooms.RoomsAttributesInterpretorTest;
import dTest.dInternal.dData.dRooms.SetOfSitesTest;
import dTest.dInternal.dData.dStudents.SetOfStuSitesTest;
import dTest.dInternal.dData.dStudents.StudentTest;
import dTest.dInternal.dDataTxt.ActivityTest;
import dTest.dInternal.dDataTxt.AssignmentTest;
import dTest.dInternal.dDataTxt.InstructorAttachTest;

import dTest.dInternal.dDataTxt.ResourceTest;
import dTest.dInternal.dDataTxt.RoomAttachTest;
import dTest.dInternal.dDataTxt.RoomsAttributesInterpretorTest;

import dTest.dInternal.dDataTxt.SectionTest;
import dTest.dInternal.dDataTxt.SetOfActivitiesTest;

import dTest.dInternal.dDataTxt.SetOfRoomsTest;
import dTest.dInternal.dDataTxt.TypeTest;
import dTest.dInternal.dDataTxt.UnityTest;
import dTest.dInternal.dOptimizationTest.ConditionsTest;
import dTest.dInternal.dOptimizationTest.ConflictsAttachTest;
import dTest.dInternal.dOptimizationTest.EventAttachTest;
import dTest.dInternal.dOptimizationTest.FirstAffectAlgorithmTest;
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
import dTest.dmains.ScanFileTest;

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
		System.out.println("Hello I am in tests");
		TestSuite suite = new TestSuite("Dimanant Tests");
		suite.addTest(InstructorAttachTest.suite());
		suite.addTest(ResourceTest.suite());
		suite.addTest(RoomsAttributesInterpretorTest.suite());
		suite.addTest(RoomAttachTest.suite());
		suite.addTest(SetOfRoomsTest.suite());
		suite.addTest(AssignmentTest.suite());
		suite.addTest(UnityTest.suite());
		suite.addTest(SectionTest.suite());
		suite.addTest(TypeTest.suite());
		suite.addTest(ActivityTest.suite());
		suite.addTest(SetOfActivitiesTest.suite());
		suite.addTest(PeriodTest.suite());
		suite.addTest(SequenceTest.suite());
		suite.addTest(DayTest.suite());
		suite.addTest(CycleTest.suite());
		suite.addTest(TTStructureTest.suite());
		suite.addTest(DXToolsMethodsTest.suite());
		suite.addTest(EventAttachTest.suite());
		suite.addTest(SetOfEventsTest.suite());
		suite.addTest(ConflictsAttachTest.suite());
		suite.addTest(StudentsConflictsMatrixTest.suite());
		suite.addTest(InstructorsConditionsTest.suite());
		suite.addTest(RoomsConditionsTest.suite());
		suite.addTest(StudentsConditionsTest.suite());
		suite.addTest(ConditionsTest.suite());
		suite.addTest(ScanFileTest.suite());
		suite.addTest(DRoomTest.suite());
		suite.addTest(SetOfSitesTest.suite());
		suite.addTest(SetOfStuSitesTest.suite());
		suite.addTest(DSetOfActivitiesTest.suite());
		suite.addTest(DSetOfActivitiesSitesTest.suite());
		suite.addTest(DSetOfInstructorsTest.suite());
		suite.addTest(DValueTest.suite());
		suite.addTest(DObjectTest.suite());
		suite.addTest(DStateTest.suite());
		suite.addTest(DataExchangeTest.suite());
		suite.addTest(ByteArrayMessageTest.suite());
		suite.addTest(StandardCollectionTest.suite());
		suite.addTest(DResourceTest.suite());
		suite.addTest(DLoadDataTest.suite());
		suite.addTest(DSaveDataTest.suite());
		suite.addTest(DSetOfResourcesTest.suite());
		suite.addTest(DSetOfStatesTest.suite());
		suite.addTest(DStandardReportDataTest.suite());
		suite.addTest(SetOfStuSitesTest.suite());
		suite.addTest(StudentTest.suite());
		suite.addTest(FirstAffectAlgorithmTest.suite());
		suite.addTest(SetOfCategoriesTest.suite());
		suite.addTest(RefinedStudMixAlgoTest.suite());
		suite.addTest(InstructorAttachTest.suite());
		suite.addTest(ResourceTest.suite());
		suite.addTest(RoomsAttributesInterpretorTest.suite());
		suite.addTest(RoomAttachTest.suite());
		suite.addTest(SetOfRoomsTest.suite());
		return suite;
	}
}