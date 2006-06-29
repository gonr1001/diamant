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

import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInternal.dData.DxAvailability;
import dInternal.dData.dInstructors.DxSetOfInstructors;

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

        Vector<String> vTemp = soiTest.getNamesVector();

        assertEquals("test_addInstructor: assertEquals", vTemp.get(soiTest
                .size() - 1), "Erick");
        assertEquals("test1_addInstructor: assertEquals", vTemp.get(0), "Alex");

        soiTest.removeInstructor(soiTest.getInstructorKeyByName("Erick"));
        assertEquals("test_removeInstructor: assertEquals",  -1, soiTest.getInstructorKeyByName("Erick"));
        
        assertNotNull("test_getInstructorKeyByName: assertEquals", soiTest.getResourceByName("Alex"));
        
    }

    public void test_getInstructorAvailability() {
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

        aTemp = soiTest.getInstructorAvailability(0);

        assertEquals("test_getInstructorAvailability: assertEquals", aTemp
                .getPeriodAvailability(aTemp.getDayCount() - 1, aTemp
                        .getPeriodCount(aTemp.getDayCount() - 1) - 1), 5);

        soiTest.setInstructorAvailability(0, soiTest
                .getInstructorAvailability(1));
        assertEquals("test_setInstructorAvailability: assertEquals", soiTest
                .getInstructorAvailability(0).getPeriodAvailability(0, 0),
                soiTest.getInstructorAvailability(1)
                        .getPeriodAvailability(0, 0));
    }

    public void test_instructorCount() {
        DxSetOfInstructors soiTest = new DxSetOfInstructors();

        assertEquals("test_instructorCount: assertEquals", soiTest.size(), 0);

        DxAvailability aTemp = new DxAvailability();
        aTemp.addDayAvailability("5 5 5");
        aTemp.addDayAvailability("5 5 5 5");
        aTemp.addDayAvailability("5 5 5 5 5");
        soiTest.addInstructor("Alsa", aTemp);

        assertEquals("test1_instructorCount: assertEquals", soiTest.size(), 1);

        soiTest.addInstructor("Daniel", aTemp);
        assertEquals("test2_instructorCount: assertEquals", soiTest.size(), 2);
    }
}
