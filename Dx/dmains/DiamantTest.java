/**
 *
 * Title: miniDiaTest $Revision: 1.32 $  $Date: 2004-02-16 18:42:43 $
 * Description: miniDiaTest is a class used to call the suite test.
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
 * @version $Revision: 1.32 $
 * @author  $Author: syay1801 $
 * @since JDK1.3
 */
//------------------------------

/* Attention this file must be used with the terminal application
 * to execute you must type
 * java junit.swingui.TestRunner miniDiaTest
 * in the application directory
 * eventually it is necessary to fix the CLASSPATH
 * include in .login file a line like
 * setenv classpath=%classpath%;INSTALL_DIR/junit.jar
 *
 *
 */

import dTest.dInternal.dUtil.*;
import dTest.dInternal.dData.*;
import dTest.dInternal.dTimeTable.*;
import dTest.dInternal.dConditionsTest.*;
import junit.framework.*;


public class DRunTest {
  public static void main (String[] args) {
      junit.textui.TestRunner.run (suite());
  }
  // The tests are very poor at the moment
  public static Test suite ( ) {
      TestSuite suite= new TestSuite("Proto Tests");
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
          suite.addTest(ConflictsAttachTest.suite());
          suite.addTest(StudentsConflictsMatrixTest.suite());
          suite.addTest(InstructorsConditionsTest.suite());
          suite.addTest(RoomsConditionsTest.suite());
          suite.addTest(StudentsConditionsTest.suite());

      return suite;
    }
}