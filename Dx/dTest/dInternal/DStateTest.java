/**
*
* Title: DStateTest $Revision $  $Date: 2005-02-03 16:52:43 $
* Description: 	DStateTest is a class used to test the class 
* 				DState
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

import java.awt.Color;

import dInternal.DState;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DStateTest extends TestCase{
	DState a;
	DState b;
	
  public DStateTest(String name) {
     super(name);

  }
  
  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(DStateTest.class);
  } // end suite
  
  public void setUp(){
  	a = new DState();
  	b = new DState(Color.BLACK, 1);
  }
   
  public void testAsetColor(){
  	a.setColor(Color.RED);
  	assertTrue(a.getColor()==Color.RED);
  }
  public void testAsetValue(){
  	a.setValue(2);
  	assertTrue(a.getValue()==2);
  }
  public void testAgetSelectedField(){
  	assertTrue(a.getSelectedField()==0);
  }
 
  public void testBsetColor(){
  	b.setColor(Color.BLUE);
  	assertTrue(b.getColor()==Color.BLUE);
  }
  public void testBsetValue(){
  	b.setValue(4);
  	assertTrue(b.getValue()==4);
  }
  public void testBgetSelectedField(){
  	assertTrue(b.getSelectedField()==0);
  }
}