/**
 * 
 * Title: ConditionsTest $Revision: 1.12 $ $Date: 2006-03-22 20:07:44 $
 * Description: ConditionsTest is a class used to display a ConditionsTest with
 * buttons
 * 
 * 
 * Copyright (c) 2001 by rgr. All rights reserved.
 * 
 * 
 * This software is the confidential and proprietary information of rgr-fdl.
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with rgr-fdl.
 * 
 * @version $Version$
 * @author $Author: gonzrubi $
 * @since JDK1.3
 */

package dTest.dInternal.dOptimizationTest;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInterface.DDocument;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.dOptimization.EventAttach;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.TTStructure;

public class ConditionsTest extends TestCase {

    private DModel _dm5j;

    private Period _period;

    public ConditionsTest(String name) {
        super(name);
        _dm5j = new DModel(new DDocument(), "." + File.separator + "dataTest" + File.separator
                + "loadData.dia", 1);
        _dm5j.buildSetOfEvents();
        _dm5j.getConditionsTest().buildStudentConflictMatrix();
        _dm5j.getConditionsTest().buildAllConditions(_dm5j.getTTStructure());
        _dm5j.getTTStructure().getCurrentCycle().getNextPeriod(1);
        _period = _dm5j.getTTStructure().getCurrentCycle().getNextPeriod(1);
    }

    public static Test suite() {
        // the type safe way is in SimpleTest
        // the dynamic way :
        return new TestSuite(ConditionsTest.class);
    } // end suite

    public void test_initAllConditions() {
        _dm5j.getConditionsTest().initAllConditions();
        assertEquals("test_initAllConditions : assertEquals 1", 2, _period.getEventsInPeriod()
                .size());
    }

    /**
     * check conflicts
     */
    public void test_getConflictsEventInTTs() {
        DResource event = _dm5j.getSetOfEvents().getResourceAt(0);
        int[] dayTime = { 5, 8, 15 };
        String periodKey = _dm5j.getTTStructure().getCurrentCycle().getPeriod(dayTime);
        ((EventAttach) event.getAttach()).setKey(4, periodKey);
        ((EventAttach) event.getAttach()).setAssignState(true);
        int[] nbConf = _dm5j.getConditionsTest().getEventConflictsInTTs(_dm5j.getTTStructure(), event,
            true);
        assertEquals("test_AddOrRemEventInTTs : assertEquals 1", 0, nbConf[0]);
        assertEquals("test_AddOrRemEventInTTs : assertEquals 2", 1, nbConf[1]);
        assertEquals("test_AddOrRemEventInTTs : assertEquals 3", 0, nbConf[2]);
        Period period = _dm5j.getTTStructure().getCurrentCycle().getPeriodByPeriodKey(periodKey);
        assertEquals("test_AddOrRemEventInTTs : assertEquals 4", 0, period.getEventsInPeriod()
                .size());
    }

    /**
     * check conflicts and add in period
     */
    public void test_addEventInTTs_1() {
        DResource event = _dm5j.getSetOfEvents().getResourceAt(0);
        int[] dayTime = { 5, 8, 15 };
        String periodKey = _dm5j.getTTStructure().getCurrentCycle().getPeriod(dayTime);
        ((EventAttach) event.getAttach()).setKey(4, periodKey);
        ((EventAttach) event.getAttach()).setAssignState(true);
        int[] nbConf = _dm5j.getConditionsTest().addEventInTTs(_dm5j.getTTStructure(), event, true);
        assertEquals("test_AddOrRemEventInTTs_1 : assertEquals 1", 0, nbConf[0]);
        assertEquals("test_AddOrRemEventInTTs_1 : assertEquals 2", 1, nbConf[1]);
        assertEquals("test_AddOrRemEventInTTs_1 : assertEquals 3", 0, nbConf[2]);
        Period period = _dm5j.getTTStructure().getCurrentCycle().getPeriodByPeriodKey(periodKey);
        assertEquals("test_AddOrRemEventInTTs_1 : assertEquals 4", 1, period.getEventsInPeriod()
                .size());
    }

    /**
     * check conflicts and add in period
     */
    public void test_addEventInTTs_2() {
        DResource event = _dm5j.getSetOfEvents().getResource("AMC645.1.01.1.");
        int[] dayTime = { 5, 8, 15 };
        String periodKey = _dm5j.getTTStructure().getCurrentCycle().getPeriod(dayTime);
        ((EventAttach) event.getAttach()).setKey(4, periodKey);
        ((EventAttach) event.getAttach()).setAssignState(true);
        int[] nbConf = _dm5j.getConditionsTest().addEventInTTs(_dm5j.getTTStructure(), event, true);
        event = _dm5j.getSetOfEvents().getResourceAt(0);
        ((EventAttach) event.getAttach()).setKey(4, periodKey);
        ((EventAttach) event.getAttach()).setAssignState(true);
        nbConf = _dm5j.getConditionsTest().addEventInTTs(_dm5j.getTTStructure(), event, true);
        assertEquals("test_AddOrRemEventInTTs_2 : assertEquals 1", 12, nbConf[0]);
        assertEquals("test_AddOrRemEventInTTs_2 : assertEquals 2", 1, nbConf[1]);
        assertEquals("test_AddOrRemEventInTTs_2 : assertEquals 3", 0, nbConf[2]);
        Period period = _dm5j.getTTStructure().getCurrentCycle().getPeriodByPeriodKey(periodKey);
        assertEquals("test_AddOrRemEventInTTs_1 : assertEquals 4", 2, period.getEventsInPeriod()
                .size());
        assertEquals("test_AddOrRemEventInTTs_1 : assertEquals 4", 2, period
                .getConflictsEventsInPeriod(event.getID()).size());
    }

    /**
     * remove event in a period
     */
    public void test_removeEventInTTs_3() {
        DResource event = _dm5j.getSetOfEvents().getResource("AMC645.1.01.1.");
        int[] dayTime = { 5, 8, 15 };
        String periodKey = _dm5j.getTTStructure().getCurrentCycle().getPeriod(dayTime);
        ((EventAttach) event.getAttach()).setKey(4, periodKey);
        ((EventAttach) event.getAttach()).setAssignState(true);
        int[] nbConf = _dm5j.getConditionsTest().addEventInTTs(_dm5j.getTTStructure(), event, true);
        event = _dm5j.getSetOfEvents().getResourceAt(0);
        ((EventAttach) event.getAttach()).setKey(4, periodKey);
        ((EventAttach) event.getAttach()).setAssignState(true);
        nbConf = _dm5j.getConditionsTest().addEventInTTs(_dm5j.getTTStructure(), event, true);
        assertEquals("test_AddOrRemEventInTTs_2 : assertEquals 1", 12, nbConf[0]);
        assertEquals("test_AddOrRemEventInTTs_2 : assertEquals 2", 1, nbConf[1]);
        assertEquals("test_AddOrRemEventInTTs_2 : assertEquals 3", 0, nbConf[2]);
        Period period = _dm5j.getTTStructure().getCurrentCycle().getPeriodByPeriodKey(periodKey);
        assertEquals("test_AddOrRemEventInTTs_1 : assertEquals 4", 2, 
            		period.getEventsInPeriod().size());

        event = _dm5j.getSetOfEvents().getResourceAt(0);
        ((EventAttach) event.getAttach()).setKey(4, periodKey);
        ((EventAttach) event.getAttach()).setAssignState(true);
        nbConf = _dm5j.getConditionsTest().removeEventInTTs(_dm5j.getTTStructure(), event, true);
        assertEquals("test_AddOrRemEventInTTs_2 : assertEquals 1", 12, nbConf[0]);
        assertEquals("test_AddOrRemEventInTTs_2 : assertEquals 2", 1, nbConf[1]);
        assertEquals("test_AddOrRemEventInTTs_2 : assertEquals 3", 0, nbConf[2]);
        period = _dm5j.getTTStructure().getCurrentCycle().getPeriodByPeriodKey(periodKey);
        assertEquals("test_AddOrRemEventInTTs_1 : assertEquals 4", 1, period.getEventsInPeriod()
                .size());

    }

    /**
     * 
     *  
     */
    public void test_buildAllConditions() {
        DModel dm = new DModel(new DDocument(), "." + File.separator + "dataTest" + File.separator
                + "horaireRGR.dia", 1);
        dm.buildSetOfEvents();
        dm.getConditionsTest().buildStudentConflictMatrix();
        dm.getConditionsTest().buildAllConditions(dm.getTTStructure());
        // get period day=3 (mercredi), sequence = 1 (AM) and period = 2 (8h30)
        Period per = dm.getTTStructure().getCurrentCycle().getPeriodByKey(3, 1, 2);
        assertEquals("test_buildAllConditions : assertEquals 1", 2, per.getNbStudConflict());
        assertEquals("test_buildAllConditions : assertEquals 2", 0, per.getNbInstConflict());
        assertEquals("test_buildAllConditions : assertEquals 3", 0, per.getNbRoomConflict());
        // get period day=1 (lundi), sequence = 3 (PM) and period = 6 (15h30)
        per = dm.getTTStructure().getCurrentCycle().getPeriodByKey(1, 3, 6);
        assertEquals("test_buildAllConditions : assertEquals 4", 1, per.getNbStudConflict());
        assertEquals("test_buildAllConditions : assertEquals 5", 2, per.getNbInstConflict());
        assertEquals("test_buildAllConditions : assertEquals 6", 0, per.getNbRoomConflict());

    }

    /**
     * 
     *  
     */
    public void test_buildAllConditionsInCloneTTS() {
        DModel dm = new DModel(new DDocument(), "." + File.separator + "dataTest" + File.separator
                + "horaireRGR.dia", 1);
        dm.buildSetOfEvents();
        dm.getConditionsTest().buildStudentConflictMatrix();
        //dm.getConditionsTest().buildAllConditions(dm.getTTStructure());//
        TTStructure cloneTTS = dm.getTTStructure().cloneCurrentTTS();
        dm.getConditionsTest().buildAllConditions(cloneTTS);
        // get period day=3 (mercredi), sequence = 1 (AM) and period = 2 (8h30)
        Period per = cloneTTS.getCurrentCycle().getPeriodByKey(3, 1, 2);
        assertEquals("test_buildAllConditions : assertEquals 1", 2, per.getNbStudConflict());
        assertEquals("test_buildAllConditions : assertEquals 2", 0, per.getNbInstConflict());
        assertEquals("test_buildAllConditions : assertEquals 3", 0, per.getNbRoomConflict());
        // get period day=1 (lundi), sequence = 3 (PM) and period = 6 (15h30)
        per = cloneTTS.getCurrentCycle().getPeriodByKey(1, 3, 6);
        assertEquals("test_buildAllConditions : assertEquals 4", 1, per.getNbStudConflict());
        assertEquals("test_buildAllConditions : assertEquals 5", 2, per.getNbInstConflict());
        assertEquals("test_buildAllConditions : assertEquals 6", 0, per.getNbRoomConflict());
        boolean isEquals = dm.getTTStructure().isEquals(cloneTTS);
        assertEquals("test_buildAllConditions : assertEquals 7", true, isEquals);

        DResource event = dm.getSetOfEvents().getResource("PED200.1.21.1.");
        EventAttach eventClone = ((EventAttach) event.getAttach()).cloneEvent();
        eventClone.setAssignState(true);
        //String periodKey=daytime[0]+"."+daytime[1]+"."+daytime[2];
        // set event key monday at 8h30
        eventClone.setKey(4, "1.1.2");
        dm.getConditionsTest().addEventInTTs(cloneTTS, new DResource("PED200.1.21.1.", eventClone),
            false);
        per = cloneTTS.getCurrentCycle().getPeriodByKey(1, 1, 2);
        assertEquals("test_buildAllConditions : assertEquals 8", 14, per.getNbStudConflict());
        assertEquals("test_buildAllConditions : assertEquals 9", 1, per.getNbInstConflict());
        assertEquals("test_buildAllConditions : assertEquals 10", 0, per.getNbRoomConflict());

        // set event key tuesday at 8h30
        eventClone = ((EventAttach) event.getAttach()).cloneEvent();
        eventClone.setAssignState(true);
        eventClone.setKey(4, "2.1.2");
        dm.getConditionsTest().addEventInTTs(cloneTTS, new DResource("PED200.1.21.1.", eventClone),
            false);
        per = cloneTTS.getCurrentCycle().getPeriodByKey(2, 1, 2);
        assertEquals("test_buildAllConditions : assertEquals 11", 40, per.getNbStudConflict());
        assertEquals("test_buildAllConditions : assertEquals 12", 0, per.getNbInstConflict());
        assertEquals("test_buildAllConditions : assertEquals 13", 0, per.getNbRoomConflict());
        per = cloneTTS.getCurrentCycle().getPeriodByKey(2, 1, 3);
        assertEquals("test_buildAllConditions : assertEquals 12-1", 1, per.getNbInstConflict());
        //set event key wedneday at 15h00
        eventClone.setKey(4, "3.3.5");
        dm.getConditionsTest().addEventInTTs(cloneTTS, new DResource("PED200.1.21.1.", eventClone),
            false);
        per = cloneTTS.getCurrentCycle().getPeriodByKey(3, 3, 5);
        assertEquals("test_buildAllConditions : assertEquals 14", 84, per.getNbStudConflict());
        assertEquals("test_buildAllConditions : assertEquals 15", 0, per.getNbInstConflict());
        assertEquals("test_buildAllConditions : assertEquals 16", 0, per.getNbRoomConflict());
        per = cloneTTS.getCurrentCycle().getPeriodByKey(3, 3, 7);
        assertEquals("test_buildAllConditions : assertEquals 14", 20, per.getNbStudConflict());
    }
}