/**
*
* Title: DResourceTest $Revision $  $Date: 2005-02-09 14:41:11 $
* Description: 	DResourceTest is a class used to test the class 
* 				DResource
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

import dInternal.DResource;
import dInternal.DValue;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DResourceTest extends TestCase{
	DValue dObj;
	DValue tmp;
	DResource a;
	DResource b;
	DResource c;
	
  public DResourceTest(String name) {
     super(name);
  }
  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(DResourceTest.class);
  } // end suite
  public void setUp(){
  	dObj = new DValue();
  	tmp = new DValue();
  	a = new DResource("10", dObj);
  	b = new DResource("20", dObj);
  	c = new DResource("20", dObj);
  }
  public void testAgetKey(){
  	assertTrue(a.getKey()==0);
  }
  public void testAgetID(){
  	assertTrue(a.getID().compareTo("10")==0);
  }
  public void testAgetAttach(){
  	DValue tmp = new DValue();
  	assertTrue(a.getAttach().isEquals(tmp));
  }
  public void testAsetAttach(){
  	tmp.setIntValue(20);
  	a.setAttach(tmp);
  	assertTrue(((DValue)a.getAttach()).getIntValue()==20);
  }
  public void testAsetIntValue(){
  	a.setID("11");
  	assertTrue(a.getID().compareTo("11")==0);
  }
  public void testAsetManuallyCreated(){
  	a.setManuallyCreated(true);
  	assertTrue(a.isManuallyCreated());
  }
  public void testBtoWrite(){
  	assertEquals("testBtoWrite: assertEquals", "020;;-1;", b.toWrite(";"));
  	//assertEquals(.compareTo()==0);
  }
  public void testBisEquals(){
  	assertTrue(b.isEquals(c));
  }
}