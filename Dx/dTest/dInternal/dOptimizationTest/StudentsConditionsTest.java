package dTest.dInternal.dOptimizationTest;

import java.io.File;
import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dDeveloper.DxFlags;
import dInterface.DDocument;
import dInterface.DxTTableDoc;
import dInternal.DModel;
import dInternal.dOptimization.DxStudentCondtionsToTest;
import dInternal.dTimeTable.Period;

public class StudentsConditionsTest extends TestCase {

    private DModel _dm1; // For dataTest7j

    private DModel _dm2; // For dataTest5j

  public StudentsConditionsTest(String name) {
    super(name);
    try {
//        _dm1= new DModel(new DDocument(),"."+ File.separator+"dataTest"+File.separator+"loadData7j.dia",1);
//        if (DxFlags.newDoc) {
			_dm1 = new DModel(new DxTTableDoc(), "." + File.separator
					+ "dataTest" + File.separator + "loadData7j.dia");
//		} else {
//			_dm1 = new DModel(new DDocument(), "." + File.separator
//					+ "dataTest" + File.separator + "loadData7j.dia", 1);
//		}
    } catch (Exception e) {
        // Should not fail in tests
        e.printStackTrace();
    }
    _dm1.buildSetOfEvents();
    //_dm.getConditionsTest().buildStudentsMatrix(_dm.getSetOfActivities(),_dm.getSetOfStudents());
    _dm1.getConditionsTest().buildStudentConflictMatrix();
    _dm1.getConditionsTest().buildAllConditions(_dm1.getTTStructure());
    
    try {
//        _dm2= new DModel(new DDocument(),"."+ File.separator+"dataTest"+File.separator+"loadData5j.dia",1);
//        if (DxFlags.newDoc) {
			_dm2 = new DModel(new DxTTableDoc(), "." + File.separator
					+ "dataTest" + File.separator + "loadData5j.dia");
//		} else {
//			_dm2 = new DModel(new DDocument(), "." + File.separator
//					+ "dataTest" + File.separator + "loadData5j.dia", 1);
//		}
    } catch (Exception e) {
        // Should not fail in tests
        e.printStackTrace();
    }
    _dm2.buildSetOfEvents();
    //_dm.getConditionsTest().buildStudentsMatrix(_dm.getSetOfActivities(),_dm.getSetOfStudents());
    _dm2.getConditionsTest().buildStudentConflictMatrix();
    _dm2.getConditionsTest().buildAllConditions(_dm1.getTTStructure());
  }

  public static Test suite() {
    // the type safe way is in SimpleTest
    // the dynamic way :
    return new TestSuite(StudentsConditionsTest.class);
  } // end suite



  /**
   *
   */
  public void test_EventsConflicts(){
    _dm1.getTTStructure().getCurrentCycle().getNextPeriod(1);
    Period period= _dm1.getTTStructure().getCurrentCycle().getNextPeriod(1);
    DxStudentCondtionsToTest testStud= new DxStudentCondtionsToTest(_dm1.getConditionsTest().getConflictsMatrix(),
        _dm1.getSetOfActivities(), _dm1.getTTStructure().getCurrentCycle());
    int[] perKey={1,1,2};
    testStud.addTest(perKey,period,"AMC640.1.01.1.");
    int nbConf= testStud.getInfo(perKey,period,"AMC645.1.01.1.");
    assertEquals("test_2EventsConflicts : assertEquals 2",4,nbConf);
  }
  
  public void test2_EventsConflicts(){
      _dm2.getTTStructure().getCurrentCycle().getNextPeriod(1);
      Period period= _dm2.getTTStructure().getCurrentCycle().getNextPeriod(1);
      DxStudentCondtionsToTest testStud= new DxStudentCondtionsToTest(_dm2.getConditionsTest().getConflictsMatrix(),
          _dm2.getSetOfActivities(), _dm2.getTTStructure().getCurrentCycle());
      int[] perKey={1,1,2};
      testStud.addTest(perKey,period,"AMC640.1.01.1.");
      int nbConf= testStud.getInfo(perKey,period,"AMC645.1.01.1.");
      assertEquals("test2_EventsConflicts : assertEquals 2",4,nbConf);
    }

  /**
   *
   */
  public void test_periodVariationEventsPeriods(){
	  DxStudentCondtionsToTest testStud= new DxStudentCondtionsToTest(_dm1.getConditionsTest().getConflictsMatrix(),
        _dm1.getSetOfActivities(), _dm1.getTTStructure().getCurrentCycle());
    testStud.setPeriodVariationEvents(3);
    int[] perKey={2,1,2};
    Vector perVec= testStud.periodVariationEventsPeriods(perKey);
    assertEquals("test_periodVariationEventsPeriods_1 : assertEquals 2",7,perVec.size());
  }
  
  public void test2_periodVariationEventsPeriods(){
	  DxStudentCondtionsToTest testStud= new DxStudentCondtionsToTest(_dm2.getConditionsTest().getConflictsMatrix(),
          _dm2.getSetOfActivities(), _dm2.getTTStructure().getCurrentCycle());
      testStud.setPeriodVariationEvents(3);
      int[] perKey={2,1,2};
      Vector perVec= testStud.periodVariationEventsPeriods(perKey);
      assertEquals("test2_periodVariationEventsPeriods : assertEquals 2",7,perVec.size());
    }

  /**
   *
   */
  public void test3_periodVariationEventsPeriods(){
	  DxStudentCondtionsToTest testStud= new DxStudentCondtionsToTest(_dm1.getConditionsTest().getConflictsMatrix(),
        _dm1.getSetOfActivities(), _dm1.getTTStructure().getCurrentCycle());
    testStud.setPeriodVariationEvents(3);
    int[] perKey={2,1,2};
    Vector perVec= testStud.periodVariationEventsPeriods(perKey);
    Period per= (Period) perVec.get(3);
    assertEquals("test3_1_periodVariationEventsPeriods  : assertEquals 1(Hour):", 9, per.getBeginHour()[0]);
    assertEquals("test3_2_periodVariationEventsPeriods  : assertEquals 2(Minute):", 15, per.getBeginHour()[1]);
    assertEquals("test3_3_periodVariationEventsPeriods  : assertEquals 3(priotity):", 0, per.getPriority());
  }
  
  public void test4_periodVariationEventsPeriods(){
	  DxStudentCondtionsToTest testStud= new DxStudentCondtionsToTest(_dm2.getConditionsTest().getConflictsMatrix(),
          _dm2.getSetOfActivities(), _dm2.getTTStructure().getCurrentCycle());
      testStud.setPeriodVariationEvents(3);
      int[] perKey={2,1,2};
      Vector perVec= testStud.periodVariationEventsPeriods(perKey);
      Period per= (Period) perVec.get(3);
      assertEquals("test4_1_periodVariationEventsPeriods  : assertEquals 1(Hour):", 9, per.getBeginHour()[0]);
      assertEquals("test4_2_periodVariationEventsPeriods  : assertEquals 2(Minute):", 15, per.getBeginHour()[1]);
      assertEquals("test4_3_periodVariationEventsPeriods  : assertEquals 3(priotity):", 0, per.getPriority());
    }

  /**
   *
   */
  public void test5_periodVariationEventsPeriods(){
	  DxStudentCondtionsToTest testStud= new DxStudentCondtionsToTest(_dm1.getConditionsTest().getConflictsMatrix(),
        _dm1.getSetOfActivities(), _dm1.getTTStructure().getCurrentCycle());
    testStud.setPeriodVariationEvents(3);
    int[] perKey={2,1,2};
    Vector perVec= testStud.periodVariationEventsPeriods(perKey);
    Period per= (Period) perVec.get(6);
    assertEquals("test5_1_periodVariationEventsPeriods  : assertEquals 1(Hour):", 13, per.getBeginHour()[0]);
    assertEquals("test5_2_periodVariationEventsPeriods  : assertEquals 2(Minute):", 30, per.getBeginHour()[1]);
    assertEquals("test5_3_periodVariationEventsPeriods  : assertEquals 3(priotity):", 0, per.getPriority());
  }
  
  public void test6_periodVariationEventsPeriods(){
	  DxStudentCondtionsToTest testStud= new DxStudentCondtionsToTest(_dm2.getConditionsTest().getConflictsMatrix(),
          _dm2.getSetOfActivities(), _dm2.getTTStructure().getCurrentCycle());
      testStud.setPeriodVariationEvents(3);
      int[] perKey={2,1,2};
      Vector perVec= testStud.periodVariationEventsPeriods(perKey);
      Period per= (Period) perVec.get(6);
      assertEquals("test6_1_periodVariationEventsPeriods  : assertEquals 1(Hour):", 13, per.getBeginHour()[0]);
      assertEquals("test6_2_periodVariationEventsPeriods  : assertEquals 2(Minute):", 30, per.getBeginHour()[1]);
      assertEquals("test6_3_periodVariationEventsPeriods  : assertEquals 3(priotity):", 0, per.getPriority());
    }

  /**
   *
   */
  public void test7_periodVariationEventsPeriods(){
	  DxStudentCondtionsToTest testStud= new DxStudentCondtionsToTest(_dm1.getConditionsTest().getConflictsMatrix(),
        _dm1.getSetOfActivities(), _dm1.getTTStructure().getCurrentCycle());
    testStud.setPeriodVariationEvents(3);
    int[] perKey={2,1,2};
    Vector perVec= testStud.periodVariationEventsPeriods(perKey);
    Period per= (Period) perVec.get(0);
    assertEquals("test7_1_periodVariationEventsPeriods  : assertEquals 1(Hour):", 20, per.getBeginHour()[0]);
    assertEquals("test7_2_periodVariationEventsPeriods  : assertEquals 2(Minute):", 00, per.getBeginHour()[1]);
    assertEquals("test7_3_periodVariationEventsPeriods  : assertEquals 3(priotity):", 1, per.getPriority());
  }
  
  public void test8_periodVariationEventsPeriods(){
	  DxStudentCondtionsToTest testStud= new DxStudentCondtionsToTest(_dm2.getConditionsTest().getConflictsMatrix(),
          _dm2.getSetOfActivities(), _dm2.getTTStructure().getCurrentCycle());
      testStud.setPeriodVariationEvents(3);
      int[] perKey={2,1,2};
      Vector perVec= testStud.periodVariationEventsPeriods(perKey);
      Period per= (Period) perVec.get(0);
      assertEquals("test8_1_periodVariationEventsPeriods  : assertEquals 1(Hour):", 20, per.getBeginHour()[0]);
      assertEquals("test8_2_periodVariationEventsPeriods  : assertEquals 2(Minute):", 00, per.getBeginHour()[1]);
      assertEquals("test8_3_periodVariationEventsPeriods  : assertEquals 3(priotity):", 1, per.getPriority());
    }

}