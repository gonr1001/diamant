/**
*
* Title: DResourceTest $Revision $  $Date: 2005-04-19 20:37:53 $
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
* @author  $Author: gonzrubi $
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
  public void testA_getKey(){
  	assertEquals("testA_getKey: assertEquals", 0, a.getKey());
  }
  public void testA_getID(){
  	assertEquals("testA_getID: assertEquals", "10", a.getID());
  }
  public void testA_getAttach(){
  	DValue aux = new DValue();
  	assertEquals("testA_getAttach: assertEquals", true, a.getAttach().isEquals(aux));
  }
  public void testA_setAttach(){
  	tmp.setIntValue(20);
  	a.setAttach(tmp);
  	assertEquals("testA_setAttach: assertEquals", 20,((DValue)a.getAttach()).getIntValue());
  }
  public void test_AsetIntValue(){
  	a.setID("11");
  	assertEquals("test_AsetIntValue: assertEquals", "11",a.getID());
  }
  public void testA_setManuallyCreated(){
  	a.setManuallyCreated(true);
  	assertEquals("testA_setManuallyCreated: assertEquals", true, a.isManuallyCreated());
  }
//  public void testB_toWrite(){
//  	assertEquals("testB_toWrite: assertEquals", "20;;-1;", b.toWrite(";"));
//  	//assertEquals(.compareTo()==0);
//  }
  public void testB_isEquals(){
  	assertEquals("testB_isEquals: assertEquals", true, b.isEquals(c));
  }
}