package dTest.dInternal.dOptimizationTest;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInterface.DDocument;
import dInternal.DModel;
import dInternal.dDataTxt.Resource;
import dInternal.dOptimization.EventAttach;
import dInternal.dOptimization.TestConditions;
import dInternal.dTimeTable.Period;



public class ConditionsTest extends TestCase {

  private DModel _dm;
  private TestConditions _testCond;
  private Period _period;
  private int[] _perKey={1,1,2};

  public ConditionsTest(String name) {
    super(name);
    _dm= new DModel(new DDocument(),"."  + File.separator+"dataTest"+File.separator+"loadData.dia",1);
    _dm.buildSetOfEvents();
    //_dm.getConditionsTest().buildStudentsMatrix(_dm.getSetOfActivities(),_dm.getSetOfStudents());
    _dm.getConditionsTest().buildStudentConflictMatrix();
    _dm.getConditionsTest().buildAllConditions(_dm.getTTStructure());
    _dm.getTTStructure().getCurrentCycle().getNextPeriod(1);
     _period= _dm.getTTStructure().getCurrentCycle().getNextPeriod(1);
     _testCond= new TestConditions(_dm);
  }

  public static Test suite() {
    // the type safe way is in SimpleTest
    // the dynamic way :
    return new TestSuite(ConditionsTest.class);
  } // end suite


  public void test_initAllConditions(){
    _dm.getConditionsTest().initAllConditions();
    assertEquals("test_initAllConditions : assertEquals 1",2, _period.getEventsInPeriod().size());
  }

  /**
    * check  conflicts
    */
   public void test_AddOrRemEventInTTs(){
     Resource event= _dm.getSetOfEvents().getResourceAt(0);
     int[] dayTime= {5, 8,15};
     String periodKey= _dm.getTTStructure().getCurrentCycle().getPeriod(dayTime);
     ((EventAttach)event.getAttach()).setKey(4,periodKey);
     ((EventAttach)event.getAttach()).setAssignState(true);
     int[] nbConf=_dm.getConditionsTest().addOrRemEventInTTs(event,0,true);
     assertEquals("test_AddOrRemEventInTTs : assertEquals 1",0, nbConf[0]);
     assertEquals("test_AddOrRemEventInTTs : assertEquals 2",1, nbConf[1]);
     assertEquals("test_AddOrRemEventInTTs : assertEquals 3",0, nbConf[2]);
     Period period= _dm.getTTStructure().getCurrentCycle().getPeriodByPeriodKey(periodKey);
     assertEquals("test_AddOrRemEventInTTs : assertEquals 4",0, period.getEventsInPeriod().size());
   }

   /**
    * check conflicts and add in period
    */
   public void test_AddOrRemEventInTTs_1(){
     Resource event= _dm.getSetOfEvents().getResourceAt(0);
     int[] dayTime= {5, 8,15};
     String periodKey= _dm.getTTStructure().getCurrentCycle().getPeriod(dayTime);
     ((EventAttach)event.getAttach()).setKey(4,periodKey);
     ((EventAttach)event.getAttach()).setAssignState(true);
     int[] nbConf=_dm.getConditionsTest().addOrRemEventInTTs(event,1,true);
     assertEquals("test_AddOrRemEventInTTs_1 : assertEquals 1",0, nbConf[0]);
     assertEquals("test_AddOrRemEventInTTs_1 : assertEquals 2",1, nbConf[1]);
     assertEquals("test_AddOrRemEventInTTs_1 : assertEquals 3",0, nbConf[2]);
     Period period= _dm.getTTStructure().getCurrentCycle().getPeriodByPeriodKey(periodKey);
     assertEquals("test_AddOrRemEventInTTs_1 : assertEquals 4",1, period.getEventsInPeriod().size());
   }

   /**
    * check conflicts and add in period
    */
   public void test_AddOrRemEventInTTs_2(){
     Resource event= _dm.getSetOfEvents().getResource("AMC645.1.01.1.");
     int[] dayTime= {5, 8,15};
     String periodKey= _dm.getTTStructure().getCurrentCycle().getPeriod(dayTime);
     ((EventAttach)event.getAttach()).setKey(4,periodKey);
     ((EventAttach)event.getAttach()).setAssignState(true);
     int[] nbConf=_dm.getConditionsTest().addOrRemEventInTTs(event,1,true);
     event= _dm.getSetOfEvents().getResourceAt(0);
     ((EventAttach)event.getAttach()).setKey(4,periodKey);
     ((EventAttach)event.getAttach()).setAssignState(true);
      nbConf=_dm.getConditionsTest().addOrRemEventInTTs(event,1,true);
     assertEquals("test_AddOrRemEventInTTs_2 : assertEquals 1",12, nbConf[0]);
     assertEquals("test_AddOrRemEventInTTs_2 : assertEquals 2",1, nbConf[1]);
     assertEquals("test_AddOrRemEventInTTs_2 : assertEquals 3",0, nbConf[2]);
     Period period= _dm.getTTStructure().getCurrentCycle().getPeriodByPeriodKey(periodKey);
     assertEquals("test_AddOrRemEventInTTs_1 : assertEquals 4",2, period.getEventsInPeriod().size());
     assertEquals("test_AddOrRemEventInTTs_1 : assertEquals 4",2, period.getConflictsEventsInPeriod(event.getID()).size());
   }

   /**
    * check conflicts and add in period
    */
   public void test_AddOrRemEventInTTs_3(){
     Resource event= _dm.getSetOfEvents().getResource("AMC645.1.01.1.");
     int[] dayTime= {5, 8,15};
     String periodKey= _dm.getTTStructure().getCurrentCycle().getPeriod(dayTime);
     ((EventAttach)event.getAttach()).setKey(4,periodKey);
     ((EventAttach)event.getAttach()).setAssignState(true);
     int[] nbConf=_dm.getConditionsTest().addOrRemEventInTTs(event,1,true);
     event= _dm.getSetOfEvents().getResourceAt(0);
     ((EventAttach)event.getAttach()).setKey(4,periodKey);
     ((EventAttach)event.getAttach()).setAssignState(true);
      nbConf=_dm.getConditionsTest().addOrRemEventInTTs(event,1,true);
     assertEquals("test_AddOrRemEventInTTs_2 : assertEquals 1",12, nbConf[0]);
     assertEquals("test_AddOrRemEventInTTs_2 : assertEquals 2",1, nbConf[1]);
     assertEquals("test_AddOrRemEventInTTs_2 : assertEquals 3",0, nbConf[2]);
     Period period= _dm.getTTStructure().getCurrentCycle().getPeriodByPeriodKey(periodKey);
     assertEquals("test_AddOrRemEventInTTs_1 : assertEquals 4",2, period.getEventsInPeriod().size());

     event= _dm.getSetOfEvents().getResourceAt(0);
     ((EventAttach)event.getAttach()).setKey(4,periodKey);
     ((EventAttach)event.getAttach()).setAssignState(true);
      nbConf=_dm.getConditionsTest().addOrRemEventInTTs(event,-1,true);
     assertEquals("test_AddOrRemEventInTTs_2 : assertEquals 1",12, nbConf[0]);
     assertEquals("test_AddOrRemEventInTTs_2 : assertEquals 2",1, nbConf[1]);
     assertEquals("test_AddOrRemEventInTTs_2 : assertEquals 3",0, nbConf[2]);
     period= _dm.getTTStructure().getCurrentCycle().getPeriodByPeriodKey(periodKey);
     assertEquals("test_AddOrRemEventInTTs_1 : assertEquals 4",1, period.getEventsInPeriod().size());

   }

}