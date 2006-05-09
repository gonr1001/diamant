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

import java.util.Vector;

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
		for (int i = 0; i < 3; i++) {
			_aTest.setDayAvailability(i, "1 1 5 5 1 1 5 5 5 5 1 1 1");
		}

		// Add availibility item by item
		_aTest.setDayAvailability(4, "5 5 5 5 5 5 5 5 5 5 5 5 5 5 5");
		
		// Change availability item by item
		for (int i = 0; i < 15; i += 2) {
			_aTest.setPeriodAvailability(4, i, 1);
		}
	}

	// Trying to set an unexisting day
	public void test_setDayAvailability() {
		assertEquals("test_setDayAvailability: assertEquals", _aTest
				.setDayAvailability(5, "1 5"), false);
	}

	// Trying to set a period on an unexisting day
	public void test_setPeriodAvailability() {
		assertEquals("test_setPeriodAvailability: assertEquals", _aTest
				.setPeriodAvailability(5, 5, 1), false);
	}

	// Trying to set a period after the last existing period of a day
	public void test1_setPeriodAvailability() {
		assertEquals("test1_setPeriodAvailability: assertEquals", _aTest
				.setPeriodAvailability(0, 13, 1), false);
	}

	// Tests that values are stored correctly by the tokenizer
	// and that get works correctly
	public void test_getDayAvailability() {
		Vector<Integer> nTemp=_aTest.getDayAvailability(0);
		if(nTemp!=null)
			assertEquals("test_getDayAvailability: assertEquals ", nTemp.get(5).intValue(), 1);
	}

	// Tests that getDayAvailability returns null in case of overflow
	// (asked for a non existing day)
	public void test1_getDayAvailability() {
		assertEquals("test1_getDayAvailability: assertEquals ", _aTest
				.getDayAvailability(5), null);
	}

	// Verify that addPeriodAvailability works correctly, along with
	// getPeriodAvailability
	public void test_getPeriodAvailability() {
		assertEquals("test_getPeriodAvailability: assertEquals ", _aTest
				.getPeriodAvailability(4, 1), 5);
	}

	// Verify that addPeriodAvailability works correctly, along with
	// getPeriodAvailability
	public void test1_getPeriodAvailability() {
		assertEquals("test1_getPeriodAvailability: assertEquals ", _aTest
				.getPeriodAvailability(4, 0), 1);
	}

	// Tests limit case where a period on an unexisting day is requested
	public void test2_getPeriodAvailability() {
		assertEquals("test2_getPeriodAvailability: assertEquals ", _aTest
				.getPeriodAvailability(5, 0), -1);
	}

	// Tests limit case where an unexisting period is requested
	public void test3_getPeriodAvailability() {
		assertEquals("test3_getPeriodAvailability: assertEquals ", _aTest
				.getPeriodAvailability(2, 13), -1);
	}

	// Verify that number of days is correct
	public void test_getDayCount() {
		assertEquals("test_getDayCount: assertEquals ", _aTest.getDayCount(), 5);
	}

	// Verify that number of period in day is correct
	public void test_getPeriodCount() {
		assertEquals("test_getPeriodCount: assertEquals ", _aTest
				.getPeriodCount(0), 13);
	}

	// Verify that number of period in day is correct, even on a day where
	// there are no availabilities
	public void test1_getPeriodCount() {
		assertEquals("test1_getPeriodCount: assertEquals ", _aTest
				.getPeriodCount(3), 0);
	}

	// Verify that number of period in day is correct, even on a day where
	// availabilities were added by addPeriodAvailability
	public void test2_getPeriodCount() {
		assertEquals("test2_getPeriodCount: assertEquals ", _aTest
				.getPeriodCount(4), 15);
	}
	
//	public void test_(){
//		
//	}
}
