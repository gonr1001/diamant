/**
 * 
 * Title: RefinedStudMixAlgoTest $Revision: 1.3 $ $Date: 2006-03-25 19:25:15 $
 * Description: RefinedStudMixAlgoTest is a class used to test the class
 * 				RefinedStudMixAlgo 
 * 
 * 
 * Copyright (c) 2001 by rgr. All rights reserved.
 * 
 * 
 * This software is the confidential and proprietary information of rgr-fdl.
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with rgr-fdl.
 * 
 * @version $Version$
 * @author $Author: gonzrubi $
 * @since JDK1.3
 */

package dTest.dInternal.dOptimizationTest;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInterface.DDocument;
import dInternal.DModel;
import dInternal.dOptimization.RefinedStudMixAlgo;

public class RefinedStudMixAlgoTest extends TestCase {
    
    RefinedStudMixAlgo _refined;
  	DModel 		_dm1;
  	DModel 		_dm2;
  	DDocument 	_dDocument1;
  	DDocument 	_dDocument2;
  	String 		_fileName;
  	int 		_type;
  	
  	public RefinedStudMixAlgoTest(String name) {
        super(name);
    }
    
    public void setUp(){
        _dDocument1 = new DDocument();
      	_fileName = "."  + File.separator+"dataTest"+File.separator+"loadData5j.dia";
      	_type = 1;
      	_dm1= new DModel(_dDocument1,_fileName,_type);
      	_dm1.getConditionsTest().initAllConditions(); //Affectation initialle
      	_dDocument2 = new DDocument();
      	_dm2= new DModel(_dDocument2,_fileName,_type);
      	
    }

    public static Test suite() {
        // the type safe way is in SimpleTest
        // the dynamic way :
        return new TestSuite(RefinedStudMixAlgoTest.class);
    } // end suite

    public void test_build(){
        //_refined.build();
       // assertEquals("test_build: assertEquals", "", _dm1);   
    }
}