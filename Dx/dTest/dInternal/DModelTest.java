/**
*
* Title: DataExchangeTest $Revision $  $Date: 2005-02-03 16:52:43 $
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
* @author  $Author: garr2701 $
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


}