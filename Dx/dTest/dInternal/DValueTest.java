/**
*
* Title: DValueTest $Revision $  $Date: 2005-02-03 16:52:43 $
* Description: 	DValueTest is a class used to test the class 
* 				DValue
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

import dInternal.DValue;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DValueTest extends TestCase{
  DValue a;
  DValue b;
	
  public DValueTest(String name) {
     super(name);

  }
  
  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(DValueTest.class);
  } // end suite
  
  public void setUp(){
  	a = new DValue();
  	b = new DValue(5,new String("30"));
  }
  
 
  public void testAsetRefNo(){
  	a.setRefNo(1);
  	assertTrue(a.getRefNo()==1);
  }
  public void testAsetBooleanValue(){
  	a.setBooleanValue(true);
  	assertTrue(a.getBooleanValue()==true);
  }
  public void testAsetIntValue(){
  	a.setIntValue(2);
  	assertTrue(a.getIntValue()==2);
  }
  public void testAsetObjectValue(){
  	a.setObjectValue(new String("3"));
  	assertTrue(((String)a.getObjectValue()).compareTo("3")==0);
  }
  public void testBsetRefNo(){
  	b.setRefNo(1);
  	assertTrue(b.getRefNo()==1);
  }
  public void testBsetBooleanValue(){
  	b.setBooleanValue(true);
  	assertTrue(b.getBooleanValue()==true);
  }
  public void testBsetIntValue(){
  	b.setIntValue(2);
  	assertTrue(b.getIntValue()==2);
  }
  public void testBsetObjectValue(){
  	b.setObjectValue(new String("3"));
  	assertTrue(((String)b.getObjectValue()).compareTo("3")==0);
  }
}