/**
 * Created on May 4, 2006
 * 
 * 
 * Title: DxSetOfInstructorsTest.java 
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
import dInternal.dData.dInstructors.DxSetOfInstructors;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxSetOfInstructorsTest is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
public class DxSetOfInstructorsTest extends TestCase {
	/**
	 * @param args
	 */
	public DxSetOfInstructorsTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxSetOfInstructorsTest.class);
	} // end suite

	public void test_addremoveInstructor() {
		DxSetOfInstructors soiTest = new DxSetOfInstructors();
		DxAvailability aTemp = new DxAvailability();
		aTemp.addDayAvailability("1 5 1");
		aTemp.addDayAvailability("5 5 1 1");
		aTemp.addDayAvailability("5 5 5 5 1");
		aTemp.addDayAvailability("1 1 1 5 5 5");
		aTemp.addDayAvailability("1 5 1 5 1 5 1");

		soiTest.addInstructor("Erick", aTemp);
		soiTest.addInstructor("Daniel", aTemp);
		soiTest.addInstructor("Claude", aTemp);
		soiTest.addInstructor("Bruno", aTemp);
		soiTest.addInstructor("Alex", aTemp);
        soiTest.sortIntructors();

		assertEquals("test_addInstructor: assertEquals", soiTest
				.getInstructorName(soiTest.size() - 1), "Erick");
		assertEquals("test1_addInstructor: assertEquals", soiTest
				.getInstructorName(0), "Alex");

		soiTest.removeInstructor(0);

		assertEquals("test_removeInstructor: assertEquals", soiTest
				.getInstructorName(0), "Bruno");
	}

	public void test_getsetInstructor() {
		DxSetOfInstructors soiTest = new DxSetOfInstructors();

		DxAvailability aTemp = new DxAvailability();
		aTemp.addDayAvailability("5 5 5");
		aTemp.addDayAvailability("5 5 5 5");
		aTemp.addDayAvailability("5 5 5 5 5");
		soiTest.addInstructor("Alsa", aTemp);

		aTemp = new DxAvailability();
		aTemp.addDayAvailability("1 1 1");
		aTemp.addDayAvailability("1 1 1 1");
		aTemp.addDayAvailability("1 1 1 1 1");
		soiTest.addInstructor("Zoe", aTemp);

		soiTest.sortIntructors();
		aTemp = soiTest.getInstructorAvailability(0);

		assertEquals("test_getInstructorAvailability: assertEquals", aTemp
		 .getPeriodAvailability(aTemp.getDayCount() - 1, aTemp
		 .getPeriodCount(aTemp.getDayCount() - 1)-1), 5);
		
		soiTest.setInstructorAvailability(0, soiTest
				.getInstructorAvailability(1));
		assertEquals("test_setInstructorAvailability: assertEquals", soiTest
				.getInstructorAvailability(0).getPeriodAvailability(0, 0),
				soiTest.getInstructorAvailability(1)
						.getPeriodAvailability(0, 0));

		soiTest.setInstructorName(0, "John");
		assertEquals("test_setInstructorName: assertEquals", soiTest
				.getInstructorName(0), "John");
	}

	public void test_isValidIndex() {
		DxSetOfInstructors soiTest = new DxSetOfInstructors();

		DxAvailability aTemp = new DxAvailability();
		aTemp.addDayAvailability("5 5 5");
		aTemp.addDayAvailability("5 5 5 5");
		aTemp.addDayAvailability("5 5 5 5 5");
		soiTest.addInstructor("Alsa", aTemp);
		soiTest.addInstructor("Daniel", aTemp);

		assertEquals("test_isValidIndex: assertEquals", soiTest
				.setInstructorAvailability(0, aTemp), true);
		assertEquals("test1_isValidIndex: assertEquals", soiTest
				.setInstructorAvailability(1, aTemp), true);
		assertEquals("test2_isValidIndex: assertEquals", soiTest
				.setInstructorAvailability(2, aTemp), false);

	}

	public void test_instructorCount() {
		DxSetOfInstructors soiTest = new DxSetOfInstructors();

		assertEquals("test_instructorCount: assertEquals", soiTest
				.size(), 0);

		DxAvailability aTemp = new DxAvailability();
		aTemp.addDayAvailability("5 5 5");
		aTemp.addDayAvailability("5 5 5 5");
		aTemp.addDayAvailability("5 5 5 5 5");
		soiTest.addInstructor("Alsa", aTemp);

		assertEquals("test1_instructorCount: assertEquals", soiTest
				.size(), 1);

		soiTest.addInstructor("Daniel", aTemp);
		assertEquals("test2_instructorCount: assertEquals", soiTest
				.size(), 2);

	}
    
    public void test_uniqueID() {
        DxAvailability aTemp = new DxAvailability();
        aTemp.addDayAvailability("1 5 1 5");
        DxSetOfInstructors dxsoi=new DxSetOfInstructors();
        dxsoi.addInstructor("Instru1", aTemp);
        dxsoi.addInstructor("Instru2", aTemp);
        assertNotSame("test_uniqueID: assertNotSame", dxsoi.getInstructorKey(0), dxsoi.getInstructorKey(1));
    }
    
    /*public void test_getIndexbyName() {
        DxSetOfInstructors soiTest = new DxSetOfInstructors();
        DxAvailability aTemp = new DxAvailability();
        aTemp.addDayAvailability("1 5 1");
        aTemp.addDayAvailability("5 5 1 1");
        aTemp.addDayAvailability("5 5 5 5 1");
        aTemp.addDayAvailability("1 1 1 5 5 5");
        aTemp.addDayAvailability("1 5 1 5 1 5 1");
    
        DxInstructor iTemp = new DxInstructor("Erick", aTemp);
        soiTest.addInstructor(iTemp);
        iTemp = new DxInstructor("Daniel", aTemp);
        soiTest.addInstructor(iTemp);
        iTemp = new DxInstructor("Claude", aTemp);
        soiTest.addInstructor(iTemp);
        iTemp = new DxInstructor("Bruno", aTemp);
        soiTest.addInstructor(iTemp);
        iTemp = new DxInstructor("Alex", aTemp);
        soiTest.addInstructor(iTemp);
        soiTest.sortIntructors();
        
        assertEquals("test_getIndexbyName: assertEquals",soiTest.getIndexbyName("Alex"),0);
        assertEquals("test_getIndexbyName: assertEquals",soiTest.getIndexbyName("Erick"),4);
        assertEquals("test_getIndexbyName: assertEquals",soiTest.getIndexbyName("John"),-1);
    }*/
}
