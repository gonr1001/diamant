/**
*
* Title: DSetOfResourcesTest $Revision $  $Date: 2005-03-08 16:00:45 $
* Description: 	DSetOfResourcesTest is a class used to test the class 
* 				DSetOfResources using SetOfStudents
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

package dTest.dInternal;

import java.util.Vector;

import dInternal.DResource;
import dInternal.DValue;
import dInternal.dData.ByteArrayMsg;
import dInternal.dData.dStudents.SetOfStudents;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DSetOfResourcesTest extends TestCase{
	SetOfStudents 	_sos;
	DResource resource1; 
	DResource resource2;
	DResource resource3;
	DResource resource4;
	DResource resource10;
	DResource resource11;
	DResource resource12;
	DResource resource21;
	int insertType;
	
	public DSetOfResourcesTest(String name) {
		super(name);
	}
  
	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DSetOfResourcesTest.class);
	} // end suite
  
	public void setUp(){
		_sos = new SetOfStudents();
		resource1 = new DResource("1", new DValue(1,"One"));
		resource2 = new DResource("2", new DValue(2,"two"));
		resource3 = new DResource("3", new DValue(3,"three"));
		resource4 = new DResource("4", new DValue(4,"four"));
		resource10 = new DResource("2", new DValue(10,"ten"));
		resource11 = new DResource("11", new DValue());
		resource12 = new DResource("12", new DValue());
		resource21 = new DResource("1", new DValue(1,"One"));
		// insertType=0 (insert by using key); insertType=1 (insert by using ID)
		insertType = 0;
		_sos.addResource(resource1,insertType);
		_sos.addResource(resource2,insertType);
		
		
	}
	public void test_analyseTokens(){
		assertFalse(_sos.analyseTokens(new ByteArrayMsg("",""), 1));
	}
	
	public void test_addResource(){
		assertEquals("test_addResource: assertEquals",true,_sos.addResource(resource3,insertType));
	}
	
	public void test_setSetOfResources(){
		Vector rlist = new Vector();
		rlist.add(resource11);
		rlist.add(resource12);
		_sos.setSetOfResources(rlist);
		assertEquals("test_setSetOfResources: assertEquals", "11", _sos.getResource(0).getID());
	}
	//setSubsetOfResources(String [] IDs, int fieldIndex, String value){
	
	public void test_setSubsetOfResources(){
		//test lgd: 
		String [] IDs = {"1","2"};
		int fieldIndex = 0; //"2"
		_sos.setSubsetOfResources(IDs,fieldIndex,"test");
		//System.out.println(_sos.getResource("2").toString());
		//System.out.println("--"+((DValue)_sos.getResource("1").getAttach()).getStringValue()+"++");
		assertEquals("test_setSubsetOfResources: assertEquals",true,true);
	}
	
	public void test_getSetOfResources(){
		Vector tmp = _sos.getSetOfResources();
		assertEquals("test_getSetOfResources: assertEquals", 2,tmp.size());
	}
	
	public void test_setCurrentKey(){
		_sos.setCurrentKey(40); 
		assertEquals("test_setCurrentKey: assertEquals", 40,_sos.getCurrentKey());
	}
	public void test_removeResourceAt(){
		int count1 = _sos.getSetOfResources().size();
		_sos.removeResourceAt(0);
		int count2 = _sos.getSetOfResources().size();
		assertEquals("test_removeResourceAt: assertEquals",(count1-1),count2);
	}
	public void test_removeResourceString(){
		int count1 = _sos.getSetOfResources().size();
		_sos.removeResource("1");
		int count2 = _sos.getSetOfResources().size();
		assertEquals("test_removeResourceString: assertEquals", (count1-1), count2);
	}
	public void test_removeResourceLong(){
		int count1 = _sos.getSetOfResources().size();
		_sos.removeResource(1);
		int count2 = _sos.getSetOfResources().size();
		assertEquals("test_removeResourceLong: assertEquals", (count1-1), count2);
	}
	public void test_getResourceString(){
		assertEquals("test_getResourceString: assertEquals", 1, _sos.getResource("1").getKey());
	}
	public void test_getResourceLong(){
		assertEquals("test_getResourceLong: assertEquals", 1, _sos.getResource(1).getKey());
	}
	public void test_getResourceAt(){
		assertEquals("test_getResourceAt: assertEquals", 1, _sos.getResourceAt(0).getKey());
	}
	
	public void test_getIndexOfResourceLong(){
		assertEquals("test_getIndexOfResourceLong: assertEquals", 0, _sos.getIndexOfResource(1));
	}
	public void test_getIndexOfResource(){
		assertEquals("test_getIndexOfResource: assertEquals", 0, _sos.getIndexOfResource("1"));
	}
	public void test1_setResource(){//lgd:quel resource???????
		assertEquals("test1_setResource: assertEquals",true,_sos.setResource(resource1)); 
	}
	public void test2_setResource(){//lgd:???????
		_sos.setResource(resource1);
		assertEquals("test2_setResource: assertEquals", 1, ((DValue)_sos.getResourceAt(0).getAttach()).getIntValue());
	}
	public void test_sortSetOfResourcesByID(){
		_sos.addResource(resource4,insertType);
		_sos.addResource(resource3,insertType);
		_sos.sortSetOfResourcesByID();
		assertEquals("test_sortSetOfResourcesByID: assertEquals", "3", _sos.getResourceAt(2).getID());
	}
	
	public void test_sortSetOfResourcesByKey(){
		_sos.addResource(resource4,insertType);
		_sos.addResource(resource3,insertType);
		_sos.sortSetOfResourcesByKey();
		assertEquals("test_sortSetOfResourcesByKey: assertEquals", "4", _sos.getResourceAt(2).getID());
	}
	public void test_sortSetOfResourcesBySelectedAttachField(){
		_sos.addResource(resource4,insertType);
		_sos.addResource(resource3,insertType);
		_sos.sortSetOfResourcesBySelectedAttachField(1); // 4,3,1,2 
		//rgr to review
		assertEquals("test_sortSetOfResourcesBySelectedAttachField: assertEquals", "3", _sos.getResourceAt(2).getID());
	}
	public void test0_getNamesVector0(){
		Vector vector=_sos.getNamesVector(0); // sortSetOfResourcesByKey();
		assertEquals("test0_getNamesVector: assertEquals", "1", vector.get(0));
	}
	public void test1_getNamesVector(){
		Vector vector=_sos.getNamesVector(1); // sortSetOfResourcesByID();
		assertEquals("test1_getNamesVector: assertEquals", "2", vector.get(1));	
	}
	
	public void test_getNamesVector(){
		Vector vector= new Vector();
		//	lgd:it's the same function of getNamesVector(0) or getNamesVector(1)
		_sos.getNamesVector(vector); 
		assertEquals("test_getNamesVector: assertEquals", "2", vector.get(1)
		        .toString());
	}
	
	public void test_selectIDValue(){
		Vector vector = _sos.selectIDValue("2");
		assertEquals("test_selectIDValue: assertEquals", 2,((DResource)vector.get(0)).getKey());
	}
	public void test_getIDsByField(){ 
		// Not here
		assertEquals("test_getIDsByField: assertEquals",true,true);
	}
}