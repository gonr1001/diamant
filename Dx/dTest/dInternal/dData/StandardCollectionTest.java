package dTest.dInternal.dData;
	/**
	*
	* Title: StandardCollectionTest $Revision $  $Date: 2005-02-03 16:52:43 $
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
	* @author  $Author: garr2701 $
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
	  
	  public void testAgetError(){
	  	assertTrue(a.getError()==null);
	  }
	  public void testAtoWrite(){
	  	assertTrue(a.toWrite()==null);
	  }
	  public void testAgetSelectedField(){
	  	assertTrue(a.getSelectedField()==0);
	  }

}