/**
*
* Title: DataExchangeTest $Revision $  $Date: 2005-02-03 16:52:43 $
* Description: 	DataExchangeTest is a class used to test the class 
* 				DataExchange using the class ByteArrayMsg
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

import dInternal.dData.ByteArrayMsg;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DataExchangeTest extends TestCase{
  ByteArrayMsg a;
  
	
  public DataExchangeTest(String name) {
     super(name);

  }
  
  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(DataExchangeTest.class);
  } // end suite
  
  public void setUp(){
  	a = new ByteArrayMsg("header","contents");
  }
  
  public void testAgetContents(){
  	assertTrue(a.getContents().compareTo("contents")==0);
  }
  public void testAgetHeader(){
  	assertTrue(a.getHeader().compareTo("header")==0);
  }
  public void testAsetContents(){
  	a.setContents("contenants two");
  	assertTrue(a.getContents().compareTo("contenants two")==0);
  }
}