/**
 * Created on May 4, 2006
 * 
 * 
 * Title: DxInstructorTest.java 
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
package dTest.dInternal.dData.dInstructors;

import dInternal.dData.DxAvailability;
import dInternal.dData.dInstructors.DxInstructor;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxInstructorTest is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
public class DxInstructorTest extends TestCase {

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxInstructorTest.class);
	} // end suite

	// Trying to set an unexisting day
	public void test_DxInstructor() {
		DxAvailability aTemp = new DxAvailability();
		aTemp.addDayAvailability("1 5 1 5 1 5 1 5 1 5 1 5");
		DxInstructor iTemp = new DxInstructor("Smith, John", aTemp);
		assertEquals("test_DxInstructor: assertEquals", iTemp
				.getName(), "Smith, John");
		assertEquals("test1_DxInstructor: assertEquals", iTemp
				.getAvailability().getPeriodAvailability(0, 0), 1);
	}

	public void test_setInstructors() {
		DxAvailability aTemp = new DxAvailability();
		aTemp.addDayAvailability("1 5 1 5 1 5 1 5 1 5 1 5");
		DxInstructor iTemp = new DxInstructor("Smith, John", aTemp);

		aTemp=new DxAvailability();
		aTemp.addDayAvailability("5 5 5 5 5 5 5 5");
		assertEquals("test1_setInstructors: assertEquals", iTemp
				.getAvailability().getPeriodAvailability(0, 0), 1);
		iTemp.setAvailability(aTemp);
		assertEquals("test2_setInstructors: assertEquals", iTemp
				.getAvailability().getPeriodAvailability(0, 0), 5);
	}
}
