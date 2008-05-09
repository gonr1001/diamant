/**
*
* Title: DataExchangeTest $Revision $  $Date: 2005-03-08 16:00:45 $
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
* @author  $Author: syay1801 $
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
  
  public void testA_getContents(){
  	assertEquals("testA_getContents: assertEquals", "contents", a.getContents());
  }
  public void testA_getHeader(){
  	assertEquals("testA_getHeader: assertEquals", "header",a.getHeader());
  }
  public void testA_setContents(){
  	a.setContents("contenants two");
  	assertEquals("testA_setContents: assertEquals", "contenants two", a.getContents());
  }
}