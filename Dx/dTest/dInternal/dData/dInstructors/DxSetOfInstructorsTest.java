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

        soiTest.removeInstructor(1);
        assertEquals("test_removeInstructor: assertEquals",  -1, soiTest.getInstructorKeyByName("Erick"));
        
        assertEquals("test_getInstructorKeyByName: assertEquals", 5, soiTest.getInstructorKeyByName("Alex"));
        assertEquals("test_getInstructorName: assertEquals", null, soiTest.getInstructorName(1));
        assertEquals("test_1_getInstructorName: assertEquals", "Claude", soiTest.getInstructorName(3));
        
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
