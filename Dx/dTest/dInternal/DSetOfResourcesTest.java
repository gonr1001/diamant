/**
*
* Title: DSetOfResourcesTest $Revision $  $Date: 2005-02-04 22:21:57 $
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
* @author  $Author: garr2701 $
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
		resource4 = new DResource("4", new DValue(3,"four"));
		resource10 = new DResource("2", new DValue(2,"ten"));
		resource11 = new DResource("11", new DValue());
		resource12 = new DResource("12", new DValue());
		// insertType=0 (insert by using key); insertType=1 (insert by using ID)
		insertType = 0;
		_sos.addResource(resource1,insertType);
		_sos.addResource(resource2,insertType);
		
		
	}
	public void testAanalyseTokens(){
		assertFalse(_sos.analyseTokens(new ByteArrayMsg("",""), 1));
	}
	
	public void testAaddResource(){
		assertTrue(_sos.addResource(resource3,insertType));
	}
	
	public void testAsetSetOfResources(){
		Vector rlist = new Vector();
		rlist.add(resource11);
		rlist.add(resource12);
		_sos.setSetOfResources(rlist);
		assertTrue(_sos.getResource(0).getID().compareTo("11")==0);
	}
	//setSubsetOfResources(String [] IDs, int fieldIndex, String value){
	
	public void testAsetSubsetOfResources(){
		//test 
		String [] IDs = {"1","2"};
		int fieldIndex = 0; //"2"
		_sos.setSubsetOfResources(IDs,fieldIndex,"test");
		//System.out.println(_sos.getResource("2").toString());
		//System.out.println("--"+((DValue)_sos.getResource("1").getAttach()).getStringValue()+"++");
		assertTrue(true);
	}
	
	public void testAgetSetOfResources(){
		Vector tmp = _sos.getSetOfResources();
		assertTrue(tmp.size()==2);
	}
	
	public void testAsetCurrentKey(){
		_sos.setCurrentKey(40); 
		assertTrue(_sos.getCurrentKey()==40);
	}
	public void testAremoveResourceAt(){
		int count1 = _sos.getSetOfResources().size();
		_sos.removeResourceAt(0);
		int count2 = _sos.getSetOfResources().size();
		assertTrue(count2==(count1-1));
	}
	public void testAremoveResourceString(){
		int count1 = _sos.getSetOfResources().size();
		_sos.removeResource("1");
		int count2 = _sos.getSetOfResources().size();
		assertTrue(count2==(count1-1));
	}
	public void testAremoveResourceLong(){
		int count1 = _sos.getSetOfResources().size();
		_sos.removeResource(1);
		int count2 = _sos.getSetOfResources().size();
		assertTrue(count2==(count1-1));
	}
	public void testAgetResourceString(){
		assertTrue(_sos.getResource("1").getKey()==1);
	}
	public void testAgetResourceLong(){
		assertTrue(_sos.getResource(1).getKey()==1);
	}
	public void testAgetResourceAt(){
		assertTrue(_sos.getResourceAt(0).getKey()==1);
	}
	
	public void testAgetIndexOfResourceLong(){
		assertTrue(_sos.getIndexOfResource(1)==0);
	}
	public void testAgetIndexOfResource(){
		assertTrue(_sos.getIndexOfResource("1")==0);
	}
	public void testA1setResource(){//lgd:???????
		assertTrue(_sos.setResource(resource1)); 
	}
	public void testA2setResource(){//lgd:???????
		_sos.setResource(resource1);
		assertTrue(((DValue)_sos.getResourceAt(0).getAttach()).getIntValue()==1);
	}
	public void testAsortSetOfResourcesByID(){
		_sos.addResource(resource4,insertType);
		_sos.addResource(resource3,insertType);
		_sos.sortSetOfResourcesByID();
		assertTrue(_sos.getResourceAt(2).getID().compareTo("3")==0);
	}
	/*
	public void testAsortSetOfResourcesByKey(){
		_sos.addResource(resource4,insertType);
		_sos.addResource(resource3,insertType);
		_sos.sortSetOfResourcesByKey();
		assertTrue(_sos.getResourceAt(2).getID().compareTo("3")==0);
	}
	public void testAsortSetOfResourcesBySelectedAttachField(){
		_sos.addResource(resource4,insertType);
		_sos.addResource(resource3,insertType);
		_sos.sortSetOfResourcesBySelectedAttachField(1);
		assertTrue(_sos.getResourceAt(2).getID().compareTo("3")==0);
	}
	/*public void testA(){
		assertTrue(true);
	}*/
	
}