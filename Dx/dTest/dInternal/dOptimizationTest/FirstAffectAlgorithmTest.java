/**
 * 
 * Title: FirstAffectAlgorithmTest $Revision: 1.2 $ $Date: 2005-03-08 16:00:45 $
 * Description: FirstAffectAlgorithmTest is a class used to 
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
 * @author $Author: syay1801 $
 * @since JDK1.3
 */

package dTest.dInternal.dOptimizationTest;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInterface.DDocument;
import dInternal.DModel;
import dInternal.dOptimization.FirstAffectAlgorithm;

public class FirstAffectAlgorithmTest extends TestCase {
    
    FirstAffectAlgorithm _first;
  	DModel 		_dm1;
  	DModel 		_dm2;
  	DDocument 	_dDocument1;
  	DDocument 	_dDocument2;
  	String 		_fileName;
  	int 		_type;
  	
  	public FirstAffectAlgorithmTest(String name) {
        super(name);
    }
    
    public void setUp(){
        _dDocument1 = new DDocument();
      	_fileName = "."  + File.separator+"dataTest"+File.separator+"loadData.dia";
      	_type = 1;
      	_dm1= new DModel(_dDocument1,_fileName,_type);
      	_dm1.getConditionsTest().initAllConditions(); //Affectation initialle
      	_dDocument2 = new DDocument();
      	_dm2= new DModel(_dDocument2,_fileName,_type);
      	_first = new FirstAffectAlgorithm(_dm2);
    }

    public static Test suite() {
        // the type safe way is in SimpleTest
        // the dynamic way :
        return new TestSuite(FirstAffectAlgorithmTest.class);
    } // end suite

    public void test_build(){
        _first.build();
       // assertEquals("test_build: assertEquals", "", _dm1);   
    }
}