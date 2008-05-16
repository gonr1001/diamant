/**
*
* Title: DataExchangeTest $Revision $  $Date: 2005-07-05 12:04:33 $
* Description: 	DModelTest is a class used to test the class 
* 				DModel
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
* @version $ $
* @author  $Author: gonzrubi $
* @since JDK1.3
*/
package dTest.dInternal;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;




public class DModelTest extends TestCase{



  public DModelTest(String name) {
     super(name);

  }

  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(DModelTest.class);
  } // end suite

	public void test_1() {
		assertEquals("test_1: assertEquals", 0, 
				0);
	}
}