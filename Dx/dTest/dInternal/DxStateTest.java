/**
 * Created on Mar 27, 2006
 * 
 * 
 * Title: DxStateTest.java 
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
 * 
 * 
 */
package dTest.dInternal;

import java.awt.Color;

import dInternal.DxState;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxStateTest is a class used to:
 * <p>
 * TODO:insert comments
 * <p> 
 * 
 */
public class DxStateTest extends TestCase{
	DxState a;
	DxState b;
	
  public DxStateTest(String name) {
     super(name);
  }
  
  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(DStateTest.class);
  } // end suite
  
  public void setUp(){
  	a = new DxState("T", Color.BLACK, 1);
  	b = new DxState("S", Color.BLACK, 1);
  }
   
  public void testA_setColor(){
  	a.setColor(Color.RED);
  	assertEquals("testA_setColor: assertEquals", Color.RED, a.getColor());
  }
  public void testA_setValue(){
  	a.setValue(2);
  	assertEquals("testA_setValue: assertEquals", 2, a.getValue());
  }
//  public void testA_getSelectedField(){
//  	assertEquals("testA_getSelectedField: assertEquals", 0, a.getSelectedField());
//  }
 
  public void testB_setColor(){
  	b.setColor(Color.BLUE);
  	assertEquals("testB_setColor: assertEquals", Color.BLUE, b.getColor());
  }
  public void testB_setValue(){
  	b.setValue(4);
  	assertEquals("testB_setValue: assertEquals", 4, b.getValue());
  }
//  public void testB_getSelectedField(){
//  	assertEquals("testB_getSelectedField: assertEquals", 0, b.getSelectedField());
//  }
}
