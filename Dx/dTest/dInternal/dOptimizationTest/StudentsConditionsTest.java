package dTest.dInternal.dConditionsTest;

import junit.framework.*;
import java.util.Vector;

import dInternal.DModel;
import dInternal.dTimeTable.Period;
import dInternal.dConditionsTest.TestStudentsConditions;
import dInterface.DDocument;

import java.io.File;
import java.util.StringTokenizer;


public class StudentsConditionsTest extends TestCase {

  private DModel _dm;

  public StudentsConditionsTest(String name) {
    super(name);
    _dm= new DModel(new DDocument(),System.getProperty("user.dir")+ File.separator+"dataTest"+File.separator+"loadData.dia",1);
    _dm.buildSetOfEvents();
    //_dm.getConditionsTest().buildStudentsMatrix(_dm.getSetOfActivities(),_dm.getSetOfStudents());
    _dm.getConditionsTest().buildStudentConflictMatrix();
    _dm.getConditionsTest().buildAllConditions(_dm.getTTStructure());
  }

  public static Test suite() {
    // the type safe way is in SimpleTest
    // the dynamic way :
    return new TestSuite(StudentsConditionsTest.class);
  } // end suite



  /**
   *
   */
  public void test_2EventsConflicts(){
    _dm.getTTStructure().getCurrentCycle().getNextPeriod(1);
    Period period= _dm.getTTStructure().getCurrentCycle().getNextPeriod(1);
    TestStudentsConditions testStud= new TestStudentsConditions(_dm.getConditionsTest().getConflictsMatrix(),
        _dm.getSetOfActivities(), _dm.getTTStructure().getCurrentCycle());
    int[] perKey={1,1,2};
    testStud.executeTest(perKey,period,"AMC640.1.01.1.",1);
    int nbConf= testStud.executeTest(perKey,period,"AMC645.1.01.1.",0);
    assertEquals("test_2EventsConflicts : assertEquals 2",4,nbConf);
  }

  /**
   *
   */
  public void test_periodVariationEventsPeriods_1(){
    TestStudentsConditions testStud= new TestStudentsConditions(_dm.getConditionsTest().getConflictsMatrix(),
        _dm.getSetOfActivities(), _dm.getTTStructure().getCurrentCycle());
    testStud.setPeriodVariationEvents(3);
    int[] perKey={2,1,2};
    Vector perVec= testStud.periodVariationEventsPeriods(perKey);
    assertEquals("test_periodVariationEventsPeriods_1 : assertEquals 2",7,perVec.size());
  }

  /**
   *
   */
  public void test_periodVariationEventsPeriods_2(){
    TestStudentsConditions testStud= new TestStudentsConditions(_dm.getConditionsTest().getConflictsMatrix(),
        _dm.getSetOfActivities(), _dm.getTTStructure().getCurrentCycle());
    testStud.setPeriodVariationEvents(3);
    int[] perKey={2,1,2};
    Vector perVec= testStud.periodVariationEventsPeriods(perKey);
    Period per= (Period) perVec.get(3);
    assertEquals("test_periodVariationEventsPeriods_2  : assertEquals 1(Hour):", 9, per.getBeginHour()[0]);
    assertEquals("test_periodVariationEventsPeriods_2  : assertEquals 2(Minute):", 15, per.getBeginHour()[1]);
    assertEquals("test_periodVariationEventsPeriods_2  : assertEquals 3(priotity):", 0, per.getPriority());
  }

  /**
   *
   */
  public void test_periodVariationEventsPeriods_3(){
    TestStudentsConditions testStud= new TestStudentsConditions(_dm.getConditionsTest().getConflictsMatrix(),
        _dm.getSetOfActivities(), _dm.getTTStructure().getCurrentCycle());
    testStud.setPeriodVariationEvents(3);
    int[] perKey={2,1,2};
    Vector perVec= testStud.periodVariationEventsPeriods(perKey);
    Period per= (Period) perVec.get(6);
    assertEquals("test_periodVariationEventsPeriods_3  : assertEquals 1(Hour):", 13, per.getBeginHour()[0]);
    assertEquals("test_periodVariationEventsPeriods_3  : assertEquals 2(Minute):", 30, per.getBeginHour()[1]);
    assertEquals("test_periodVariationEventsPeriods_3  : assertEquals 3(priotity):", 0, per.getPriority());
  }

  /**
   *
   */
  public void test_periodVariationEventsPeriods_4(){
    TestStudentsConditions testStud= new TestStudentsConditions(_dm.getConditionsTest().getConflictsMatrix(),
        _dm.getSetOfActivities(), _dm.getTTStructure().getCurrentCycle());
    testStud.setPeriodVariationEvents(3);
    int[] perKey={2,1,2};
    Vector perVec= testStud.periodVariationEventsPeriods(perKey);
    Period per= (Period) perVec.get(0);
    assertEquals("test_periodVariationEventsPeriods_3  : assertEquals 1(Hour):", 20, per.getBeginHour()[0]);
    assertEquals("test_periodVariationEventsPeriods_3  : assertEquals 2(Minute):", 00, per.getBeginHour()[1]);
    assertEquals("test_periodVariationEventsPeriods_3  : assertEquals 3(priotity):", 1, per.getPriority());
  }

}