package dTest.dInternal.dData;
	/**
	*
	* Title: StandardCollectionTest $Revision $  $Date: 2005-03-08 16:00:45 $
	* Description: StandardCollectionTest is a class used to test the class 
	*              StandardCollection
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

	import dInternal.dData.StandardCollection;
	import junit.framework.Test;
	import junit.framework.TestCase;
	import junit.framework.TestSuite;

	
	public class StandardCollectionTest extends TestCase{
	  StandardCollection a;
	  
		
	  public StandardCollectionTest(String name) {
	     super(name);
	  }
	  
	  public static Test suite() {
	   // the type safe way is in SimpleTest
	   // the dynamic way :
	   return new TestSuite(StandardCollectionTest.class);
	  } // end suite
	  
	  public void setUp(){
	  	a = new StandardCollection();
	  }
	  
	  public void test_getError(){
	  	assertEquals("test_getError: assertEquals", null, a.getError());
	  }
	  public void test_toWrite(){
	  	assertEquals("test_toWrite: assertEquals", null, a.toWrite());
	  }
	  public void test_getSelectedField(){
	  	assertEquals("test_getSelectedField: assertEquals", 0, a.getSelectedField());
	  }

}