package dTest.dInternal.dData;
	/**
	*
	* Title: DSaveDataTest $Revision $  $Date: 2005-02-04 16:14:37 $
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

	import java.io.File;
	import dInterface.DDocument;
	import dInternal.DModel;
	import dInternal.dData.DSaveData;
	import junit.framework.Test;
	import junit.framework.TestCase;
	import junit.framework.TestSuite;

	
	  public class DSaveDataTest extends TestCase{
	  	DSaveData 	_a;
	  	DModel 		_dm;
	  	DDocument 	_dDocument;
	  	String 		_fileNameLoad;
	  	String 		_fileNameDown;
	  	String 		_fileNameTTS;
	  	int 		_type;
	  
	  public DSaveDataTest(String name) {
	     super(name);
	  }
	  public static Test suite() {
	   // the type safe way is in SimpleTest
	   // the dynamic way :
	   return new TestSuite(DSaveDataTest.class);
	  } // end suite
	  
	  public void setUp(){
	  	_a = new DSaveData("1.5");
	  	_dDocument = new DDocument();
	  	_fileNameLoad = "."  + File.separator+"dataTest"+File.separator+"loadData.dia";
	  	_fileNameDown = "."  + File.separator+"dataTest"+File.separator+"downData.dia";
	  	_fileNameTTS = "."  + File.separator+"dataTest"+File.separator+"downDataTTS.dia";
	  	_type = 1;
	  	_dm= new DModel(_dDocument,_fileNameLoad,_type);
	  }
	  public void testAgetVersion(){
	  	assertTrue(_a.getVersion().compareTo("1.5")==0);
	  }
	  public void testAsaveTimeTable(){  	   
	  	String error = _a.saveTimeTable(_dm.getTTStructure(),_dm.getSetOfInstructors(),
	  			_dm.get_setOfSites(),_dm.get_setOfActivitiesSites(),
				_dm.get_setOfStuSites(),_fileNameDown);
	  	assertTrue(error.length()==0);
	  } 
	  public void testAsaveTTStructure(){
	  	String error = _a.saveTTStructure(_dm.getTTStructure(), _fileNameTTS);
	  	assertTrue(error.length()==0);
	  }

}