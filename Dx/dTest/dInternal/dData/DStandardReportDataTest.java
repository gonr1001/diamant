package dTest.dInternal.dData;
	/**
	*
	* Title: DStandardReportDataTest $Revision $  $Date: 2005-02-03 20:02:02 $
	* Description: DStandardReportDataTest is a class used to test the class 
	*              DStandardReportData
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

	import dInterface.DDocument;
	import dInternal.DModel;
	import dInternal.dData.DStandardReportData;
	import junit.framework.Test;
	import junit.framework.TestCase;
	import junit.framework.TestSuite;

	
	  public class DStandardReportDataTest extends TestCase{
	  	DStandardReportData a;
	  	DModel model;
	  	DDocument dDocument;
	  	String fileName;
	  	int type;
	  
	  public DStandardReportDataTest(String name) {
	     super(name);
	  }
	  public static Test suite() {
	   // the type safe way is in SimpleTest
	   // the dynamic way :
	   return new TestSuite(DStandardReportDataTest.class);
	  } // end suite
	  public void setUp(){
	  	dDocument=new DDocument();
	  	fileName="";
	  	type = 1;
	  	
	  	//model = new DModel(dDocument, fileName, type);
	  	//a = new DStandardReportData(model);
	  }
	  public void testA(){
	  	assertTrue(true);
	  }
}