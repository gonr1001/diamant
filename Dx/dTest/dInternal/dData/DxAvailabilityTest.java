/**
 * Created on May 4, 2006
 * 
 * 
 * Title: DxAvailabilityTest.java 
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
 * 
 * 
 */
package dTest.dInternal.dData;

import dInternal.dData.DxAvailability;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxAvailabilityTest is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
public class DxAvailabilityTest extends TestCase {
	DxAvailability _aTest;

	/**
	 * @param args
	 */
	public DxAvailabilityTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxAvailabilityTest.class);
	} // end suite

	public void setUp() {
		_aTest = new DxAvailability(5);
		// Add availabilites that will be parsed with tokenizer
		for (int i = 0; i < 4; i++) {
			_aTest.setDayAvailability(i, "1 1 5 5 1 1 5 5 5 5 1 1 1");
		}

		// Add availibility item by item
		for (int i = 0; i < 15; i++) {
			_aTest.addPeriodAvailability(4, 5);
		}
		// Change availability item by item
		for (int i = 0; i < 15; i += 2) {
			_aTest.setPeriodAvailability(4, i, 1);
		}
	}
	
	public void test_setDayAvailability(){
		//assertEquals(test_setDayAvailability: asser);
	}
		//Tests if value are stored correctly by the tokenizer
	public void test_getDayAvailability() {
		int[] nDayAva = _aTest.getDayAvailability(0);
		assertEquals("test_getDayAvailability: assertEquals ", nDayAva[5],1);
	}
		
		//Tests if the function returns null in case of overflow
		//(asked for a day not existing)
	public void test1_getDayAvailability() {
		int[] nDayAva = _aTest.getDayAvailability(5);
		assertEquals("test1_getDayAvailability: assertEquals ", nDayAva,null);
	}
	
	public void test_addPeriodAvailability()
	{
		int nAva = _aTest.getPeriodAvailability(4,1);
		assertEquals("test_addPeriodAvailability: assertEquals ", nAva,5);
	}
	
	public void test1_addPeriodAvailability()
	{
		int nAva = _aTest.getPeriodAvailability(4,0);
		assertEquals("test1_addPeriodAvailability: assertEquals ", nAva,1);
	}
}
