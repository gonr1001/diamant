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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInternal.dData.DxAvailability;
import dInternal.dData.dInstructors.DxInstructor;
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

        DxInstructor dxiTemp[] = soiTest.getInstructorsSortedByKey();
        assertEquals("test1_getInstructorsSortedByKey: assertEquals", "Erick",
                dxiTemp[0].getInstructorName());
        assertEquals("test2_getInstructorsSortedByKey: assertEquals", "Claude",
                dxiTemp[2].getInstructorName());
        assertEquals("test3_getInstructorsSortedByKey: assertEquals", "Alex",
                dxiTemp[4].getInstructorName());

        dxiTemp = soiTest.getInstructorsSortedByName();
        assertEquals("test1_getInstructorsSortedByName: assertEquals", "Alex",
                dxiTemp[0].getInstructorName());
        assertEquals("test2_getInstructorsSortedByName: assertEquals",
                "Claude", dxiTemp[2].getInstructorName());
        assertEquals("test3_getInstructorsSortedByName: assertEquals", "Erick",
                dxiTemp[4].getInstructorName());

        soiTest.removeInstructor(soiTest.getInstructorKey("Erick"));
        dxiTemp = soiTest.getInstructorsSortedByKey();
        assertEquals("test1_removeInstructor: assertEquals", "Daniel",
                dxiTemp[0].getInstructorName());
        assertEquals("test2_removeInstructor: assertEquals", -1, soiTest
                .getInstructorKey("Erick"));
        assertEquals("test3_removeInstructor: asserEquals", null, soiTest
                .getInstructor("Erick"));

        assertNotNull("test1_getInstructor: assertEquals", soiTest
                .getInstructor("Alex"));
        assertEquals("test2_getInstructor: assertEquals", "Alex", soiTest
                .getInstructor("Alex").getInstructorName());
        assertEquals("test3_getInstructor: assertEquals", "Daniel", soiTest
                .getInstructor("Daniel").getInstructorName());

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

        DxInstructor[] dxiInsts = soiTest.getInstructorsSortedByName();
        aTemp = soiTest.getInstructorAvailability(dxiInsts[0]
                .getInstructorKey());

        assertEquals("test_getInstructorAvailability: assertEquals", 5, aTemp
                .getPeriodAvailability(aTemp.getDayCount() - 1, aTemp
                        .getPeriodCount(aTemp.getDayCount() - 1) - 1));

        dxiInsts[1].setInstructorAvailability(dxiInsts[0]
                .getInstructorAvailability());

        assertEquals("test_setInstructorAvailability: assertEquals",
                dxiInsts[0].getInstructorAvailability().getPeriodAvailability(
                        0, 0), dxiInsts[1].getInstructorAvailability()
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
