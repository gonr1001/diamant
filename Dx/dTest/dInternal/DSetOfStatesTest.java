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

import dConstants.DConst;
import dInternal.DSetOfStates;
import dInternal.DState;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DSetOfStatesTest extends TestCase{
	DSetOfStates _states;
	
	public DSetOfStatesTest(String name) {
		super(name);
	}
  
	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DSetOfStatesTest.class);
	} // end suite
  
	public void setUp(){
		_states = new DSetOfStates();
	}
	
	public void test_getError(){
		assertEquals("test_getError: assertEquals","",_states.getError());
	}
	public void test_addState(){
		DState state= new DState(DConst.COLOR_BLACK,-1);
		_states.addState("99",state);
		assertEquals("test_addState: assertEquals","99",_states.getResource("99").getID());
	}
}