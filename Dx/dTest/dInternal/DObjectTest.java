/**
*
* Title: DObjectTest $Revision $  $Date: 2005-02-09 14:41:11 $
* Description: 	DObjectTest is a class used to test the class 
* 				DObject using the class DValue
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
  DValue b;
	
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
  	b = new DValue();
  }

  public void testAetSelectedField(){
  	 assertEquals("testAetSelectedField: assertEquals", true, (a.getSelectedField()==0));
  }
  public void testAcompareByField(){
  	assertEquals("testAcompareByField: assertEquals", false, (a.compareByField(2,"2")));
  }  
  public void testAsetField(){
  	a.setField(1,"2");
  	assertEquals("testAsetField1: assertEquals", true, (a.getIntValue()==-1));
  	assertEquals("testAsetField2: assertEquals", true, (a.getStringValue().compareTo("")==0));
  }
  public void testAtoWrite(){
  	//System.out.println("\""+a.toWrite()+"\"");
  	assertTrue(a.toWrite().compareTo(";-1;")==0);
  }
  public void testAexternalKey(){
  	assertTrue(a.externalKey("3","4").compareTo("34")==0);
  }
  public void testAisEquals(){
  	a.setField(1,"2");
  	b.setField(1,"2");
  	assertTrue(a.isEquals(b)==true);
  }
  public void testAgetMatrixAvailability(){
  	assertTrue(a.getMatrixAvailability().length==2 );
  }
  public void testAsetAvailability(){
  	int [][] mat= new int[2][2];
  	a.setAvailability(mat);
  	assertTrue(mat[0][0]==0);
  }
}