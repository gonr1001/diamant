/**
 *
 * Title: DiamantTest 1.5Test $Revision: 1.44 $  $Date: 2004-09-23 19:02:09 $
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
 * @version $Revision: 1.44 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
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
import dTest.dInternal.dDataTxt.ActivityTest;
import dTest.dInternal.dDataTxt.AssignmentTest;
import dTest.dInternal.dDataTxt.InstructorAttachTest;
import dTest.dInternal.dDataTxt.LoadDataTest;
import dTest.dInternal.dDataTxt.ResourceTest;
import dTest.dInternal.dDataTxt.RoomAttachTest;
import dTest.dInternal.dDataTxt.RoomsAttributesInterpretorTest;
import dTest.dInternal.dDataTxt.SaveDataTest;
import dTest.dInternal.dDataTxt.SectionTest;
import dTest.dInternal.dDataTxt.SetOfActivitiesTest;
import dTest.dInternal.dDataTxt.SetOfInstructorsTest;
import dTest.dInternal.dDataTxt.SetOfResourcesTest;
import dTest.dInternal.dDataTxt.SetOfRoomsTest;
import dTest.dInternal.dDataTxt.SetOfStudentsTest;
import dTest.dInternal.dDataTxt.StudentAttachTest;
import dTest.dInternal.dDataTxt.TypeTest;
import dTest.dInternal.dDataTxt.UnityTest;
import dTest.dInternal.dOptimizationTest.ConditionsTest;
import dTest.dInternal.dOptimizationTest.ConflictsAttachTest;
import dTest.dInternal.dOptimizationTest.EventAttachTest;
import dTest.dInternal.dOptimizationTest.InstructorsConditionsTest;
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


public class DiamantTest {
  public static void main (String[] args) {
      junit.textui.TestRunner.run(suite());
  }
  // The tests are very poor at the moment
  public static Test suite ( ) {
      TestSuite suite= new TestSuite("Dimanant Tests");
          suite.addTest(InstructorAttachTest.suite());
          suite.addTest(ResourceTest.suite());
          suite.addTest(SetOfResourcesTest.suite());
          suite.addTest(SetOfInstructorsTest.suite());
          suite.addTest(StudentAttachTest.suite());
          suite.addTest(SetOfStudentsTest.suite());
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
          suite.addTest(LoadDataTest.suite());
          suite.addTest(SaveDataTest.suite());
          suite.addTest(DXToolsMethodsTest.suite());
          suite.addTest(EventAttachTest.suite());
          suite.addTest(SetOfEventsTest.suite());
          //suite.addTest(ConflictsAttachTest.suite());
          //suite.addTest(StudentsConflictsMatrixTest.suite());
          //suite.addTest(InstructorsConditionsTest.suite());
          //suite.addTest(RoomsConditionsTest.suite());
          //suite.addTest(StudentsConditionsTest.suite());
          //suite.addTest(ConditionsTest.suite());/* */

      return suite;
    }
}