package dTest.dInternal.dData;
	/**
	*
	* Title: DSaveDataTest $Revision $  $Date: 2005-02-03 20:02:02 $
	* Description: DSaveDataTest is a class used to test the class 
	*              DSaveData
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

	import dInternal.dData.DSaveData;
	import junit.framework.Test;
	import junit.framework.TestCase;
	import junit.framework.TestSuite;

	
	  public class DSaveDataTest extends TestCase{
	  	DSaveData a;
	  
	  public DSaveDataTest(String name) {
	     super(name);
	  }
	  public static Test suite() {
	   // the type safe way is in SimpleTest
	   // the dynamic way :
	   return new TestSuite(DSaveDataTest.class);
	  } // end suite
	  public void setUp(){
	  	a = new DSaveData("1.5");
	  }
	  public void testAgetVersion(){
	  	assertTrue(a.getVersion().compareTo("1.5")==0);
	  }
	  public void testAsaveTimeTable(){
	  	//a.saveTimeTable(_ttStruct,_setOfInstructors,_setOfSites,_setOfActivitiesSites,_setOfStuSites,filename);
	  	//assertTrue();
	  } 
	  public void testAsaveTTStructure(){
	  	//saveD.saveTTStructure(_ttStruct,filename);
	  	//assertTrue();
	  }

}