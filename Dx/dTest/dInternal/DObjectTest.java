/**
*
* Title: DObjectTest $Revision $  $Date: 2005-03-08 16:00:45 $
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
* @author  $Author: syay1801 $
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

  public void testA_etSelectedField(){
  	 assertEquals("testA_etSelectedField: assertEquals", true, (a.getSelectedField()==0));
  }
  public void testA_compareByField(){
  	assertEquals("testA_compareByField: assertEquals", false, (a.compareByField(2,"2")));
  }  
  public void testA_setField(){
  	a.setField(1,"2");
  	assertEquals("testA_setField1: assertEquals", true, (a.getIntValue()==-1));
  	assertEquals("testA_setField2: assertEquals", true, (a.getStringValue().compareTo("")==0));
  }
  public void testA_toWrite(){
  	//System.out.println("\""+a.toWrite()+"\"");
  	assertEquals("testA_toWrite: assertEquals", ";-1;",a.toWrite());
  }
  public void testA_externalKey(){
  	assertEquals("testA_externalKey: assertEquals","34",a.externalKey("3","4"));  }
  public void testA_isEquals(){
  	a.setField(1,"2");
  	b.setField(1,"2");
  	assertEquals("testA_isEquals: assertEquals", true,a.isEquals(b));
  }
  public void testA_getMatrixAvailability(){
  	assertEquals("testA_getMatrixAvailability: assertEquals", 2, a.getMatrixAvailability().length );
  }
  public void testA_setAvailability(){
  	int [][] mat= new int[2][2];
  	a.setAvailability(mat);
  	assertEquals("testA_setAvailability: assertEquals", 0, mat[0][0]);
  }
}