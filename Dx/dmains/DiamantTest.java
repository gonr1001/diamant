
/**
 *
 * Title: miniDiaTest $Revision: 1.1 $  $Date: 2003-03-05 17:57:55 $
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
 * @version $Revision: 1.1 $
 * @author  $Author: ysyam $
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

import DTest.*;
import junit.framework.*;
import dTest.dInternal.InstructorTest;

public class DRunTest {
  public static void main (String[] args) {
      junit.textui.TestRunner.run (suite());
  }
  // The tests are very poor at the moment
  public static Test suite ( ) {
      TestSuite suite= new TestSuite("Proto Tests");
          suite.addTest(InstructorTest.suite());
          //suite.addTest(ByteInputFileTest.suite());
          //suite.addTest(FilterTest.suite());
          //suite.addTest(DPeriodTest.suite());

      return suite;
    }
}