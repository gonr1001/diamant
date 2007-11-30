/**
 *
 * Title: ELibTest $Revision: 1.9 $  $Date: 2007-11-30 14:35:33 $
 *
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
 * @version $Revision: 1.9 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */

//------------------------------
/* Attention to execute this class you must have a batch file as
 * follows :this file must be used with the terminal application
 *
 * java -classpath "d:\Program Files\JBuilder7\extras\junit\junit.jar";
 *                  d:\Developpements\Temp\theLibrary\classes\
 *                  junit.swingui.TestRunner
 *                  ILibTest
 *
 * junit must be added as a library.
 */

package dmains;

import eTest.ByteInputFileTest;
import eTest.FilterFileTest;
import junit.framework.*;

/**
 * Description: TestSuite that runs all the sample tests
 * 
 */

public class ELibTest {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	// The tests are very poor at the moment
	public static Test suite() {
		TestSuite suite = new TestSuite("Library eLib tests");
		suite.addTest(ByteInputFileTest.suite());
		suite.addTest(FilterFileTest.suite());
		return suite;
	} // end suite
} /* end ILibTest */
