/**
*
* Title: DObjectTest $Revision $  $Date: 2005-02-03 14:11:35 $
* Description: DObjectTest is a class used to test the class DValue
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

public class DObjectTest extends TestCase{
  DValue a;
	
  public DObjectTest(String name) {
     super(name);

  }
  
  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(DObjectTest.class);
  } // end suite
  
  public void setUp(){
  	a = new DValue();
  }
  
  /*refNo=0;
	_booleanValue=false;
  _intValue=-1;
  _stringValue="";
  _objectValue= new Object();*/
  
  public void testDValueA(){
  	
  	//assertTrue(a.getSelectedField()==0);
  	//assertTrue(a.compareByField(2,"2"));
  	//a.setField(1,"2");
  	/*assertTrue(a.);
  	assertTrue(a.toString().compareTo(""));
  	assertTrue(a.externalKey("2","2"));
  	assertTrue(a.isEquals("2","2"));
  	assertTrue(a.getMatrixAvailability("2","2"));
  	assertTrue(a.setAvailability("2","2"));*/
  }
}