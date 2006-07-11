package dTest.dInternal.dData;
/**
*
* Title: DStandardReportDataTest $Revision $  $Date: 2006-07-11 20:40:00 $
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
* @author  $Author: gonzrubi $
* @since JDK1.3
*/

import java.io.File;

import dConstants.DConst;
import dInterface.DDocument;
import dInterface.DxDocument;
import dInterface.DxTTableDoc;
import dInternal.DModel;
import dInternal.dData.DStandardReportData;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

	
public class DStandardReportDataTest extends TestCase{
  	DStandardReportData _report;
  	DModel 		_dm;
  	DDocument 	_dDocument;
  	DxDocument 	_dxDocument;
  	String 		_fileName;
  	int 		_type;
	  
  public DStandardReportDataTest(String name) {
     super(name);
  }
  public static Test suite() {
     // the type safe way is in SimpleTest
     // the dynamic way :
     return new TestSuite(DStandardReportDataTest.class);
  } // end suite
  
  public void setUp(){
  	_dDocument = new DDocument();
  	_dxDocument = new DxTTableDoc();
  	_fileName = "."  + File.separator+"dataTest"+File.separator+"loadData5j.dia";
  	_type = 1;
  	try {
//        _dm= new DModel(_dDocument,_fileName,_type);
        if(DConst.newDoc){
			_dm = new DModel(_dxDocument, _fileName);
		} else {
			_dm = new DModel(_dDocument, _fileName, _type);
		}
    } catch (Exception e) {
        //Shoudl not fail in controled conditions
    }
  	_dm.getConditionsTest().initAllConditions(); //Affectation initialle
	_report = new DStandardReportData(_dm);
  }
  
  public void test_getActivitiesReport(){
  	int principalElt = 1;
  	int[] otherElts = {2,3};
	assertEquals("test_getActivitiesReport: assertEquals","1;01;1;\r\n",_report.getActivitiesReport(principalElt,otherElts));
  }
  public void test_getConflictsReport(){
  	int principalElt = 1;
  	int[] otherElts = {2,3};
	assertEquals("test_getConflictsReport: assertEquals","Je;AM;3;\r\nJe;AM;4;\r\n",_report.getConflictsReport(principalElt,otherElts));
  }
  public void test_getStudentsReport(){
  	int principalElt = 1;
  	int[] otherElts = {2,3};
  	String s =_report.getStudentsReport(principalElt,otherElts);
	assertEquals("test_getStudentsReport: assertEquals",s,_report.getStudentsReport(principalElt,otherElts));
  }
  
  
}