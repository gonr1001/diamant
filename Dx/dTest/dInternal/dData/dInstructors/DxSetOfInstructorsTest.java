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
       
        DxInstructor[] dxiTemp = soiTest.getKeySortedRessources().toArray(new DxInstructor[soiTest.size()]);
        //DxInstructor dxiTemp[] = soiTest.getInstructorsSortedByKey();
        assertEquals("test1_getInstructorsSortedByKey: assertEquals", "Erick",
                dxiTemp[0].getName());
        assertEquals("test2_getInstructorsSortedByKey: assertEquals", "Claude",
                dxiTemp[2].getName());
        assertEquals("test3_getInstructorsSortedByKey: assertEquals", "Alex",
                dxiTemp[4].getName());
        
        dxiTemp = soiTest.getNameSortedRessources().toArray(new DxInstructor[soiTest.size()]);
     //   dxiTemp = soiTest.getInstructorsSortedByName();
        
        assertEquals("test1_getInstructorsSortedByName: assertEquals", "Alex",
                dxiTemp[0].getName());
        assertEquals("test2_getInstructorsSortedByName: assertEquals",
                "Claude", dxiTemp[2].getName());
        assertEquals("test3_getInstructorsSortedByName: assertEquals", "Erick",
                dxiTemp[4].getName());
       
        soiTest.removeInstructor(soiTest.getInstructorKey("Erick"));
        dxiTemp = soiTest.getKeySortedRessources().toArray(new DxInstructor[soiTest.size()]);
        assertEquals("test1_removeInstructor: assertEquals", "Daniel",
                dxiTemp[0].getName());
        assertEquals("test2_removeInstructor: assertEquals", -1, soiTest
                .getInstructorKey("Erick"));
        assertEquals("test3_removeInstructor: asserEquals", null, soiTest
                .getInstructor("Erick"));

        assertNotNull("test1_getInstructor: assertEquals", soiTest
                .getInstructor("Alex"));
        assertEquals("test2_getInstructor: assertEquals", "Alex", soiTest
                .getInstructor("Alex").getName());
        assertEquals("test3_getInstructor: assertEquals", "Daniel", soiTest
                .getInstructor("Daniel").getName());

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
        
        DxInstructor[] dxiInsts = soiTest.getNameSortedRessources().toArray(new DxInstructor[soiTest.size()]);
		 //  DxInstructor[] dxiInsts = soiTest.getInstructorsSortedByName();
       
        aTemp = soiTest.getInstructorAvailability(dxiInsts[0]
                .getKey());

        assertEquals("test_getInstructorAvailability: assertEquals", 5, aTemp
                .getPeriodAvailability(aTemp.getDayCount() - 1, aTemp
                        .getPeriodCount(aTemp.getDayCount() - 1) - 1));

        dxiInsts[1].setAvailability(dxiInsts[0]
                .getAvailability());

        assertEquals("test_setInstructorAvailability: assertEquals",
                dxiInsts[0].getAvailability().getPeriodAvailability(
                        0, 0), dxiInsts[1].getAvailability()
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

    public void test_addSetOfInstructors() {
        DxSetOfInstructors dxsoiOne = new DxSetOfInstructors();
        DxSetOfInstructors dxsoiTwo = new DxSetOfInstructors();
        DxAvailability aOne = new DxAvailability();
        DxAvailability aTwo = new DxAvailability();

        aOne.addDayAvailability("5 5 5");
        aOne.addDayAvailability("5 5 5 5");
        aOne.addDayAvailability("5 5 5 5 5");

        aTwo.addDayAvailability("1 1 1");
        aTwo.addDayAvailability("1 1 1 1");
        aTwo.addDayAvailability("1 1 1 1 1");

        dxsoiOne.addInstructor("a", aOne);
        dxsoiOne.addInstructor("b", aOne);
        dxsoiOne.addInstructor("c", aOne);

        dxsoiTwo.addInstructor("a", aTwo);
        dxsoiTwo.addInstructor("b", aTwo);
        dxsoiTwo.addInstructor("c", aTwo);
        dxsoiTwo.addInstructor("d", aTwo);
        dxsoiTwo.addInstructor("e", aTwo);
        dxsoiTwo.addInstructor("f", aTwo);
        dxsoiTwo.addInstructor("g", aTwo);
        dxsoiTwo.addInstructor("h", aTwo);
        dxsoiTwo.addInstructor("i", aTwo);
        dxsoiTwo.addInstructor("j", aTwo);

        dxsoiOne.addSetOfInstructors(dxsoiTwo);

        assertEquals("test_addSetOfInstructors: assertEquals", 10, dxsoiOne
                .size());
        DxInstructor[] instructorsSortedByName = dxsoiOne.getNameSortedRessources().toArray(new DxInstructor[dxsoiOne.size()]);
		
        assertEquals("test_addSetOfInstructors: assertEquals", "a", instructorsSortedByName[0].getName());
        assertEquals("test_addSetOfInstructors: assertEquals", "j", instructorsSortedByName[9].getName());
        assertEquals("test_addSetOfInstructors: assertEquals", 5, instructorsSortedByName[2].getAvailability()
                .getPeriodAvailability(2, 4));
        assertEquals("test_addSetOfInstructors: assertEquals", 1, instructorsSortedByName[9].getAvailability()
                .getPeriodAvailability(2, 4));
    }
}
