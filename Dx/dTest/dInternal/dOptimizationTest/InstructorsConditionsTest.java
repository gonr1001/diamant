package dTest.dInternal.dOptimizationTest;

import junit.framework.*;
import java.util.Vector;

import dInternal.DModel;
import dInternal.dTimeTable.Period;
import dInternal.dOptimization.TestInstructorsConditions;
import dInterface.DDocument;

import java.io.File;
import java.util.StringTokenizer;


public class InstructorsConditionsTest extends TestCase {

  private DModel _dm;

  public InstructorsConditionsTest(String name) {
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
    return new TestSuite(InstructorsConditionsTest.class);
  } // end suite

  /**
   *
   */
  public void test_Availability(){
     Period period= _dm.getTTStructure().getCurrentCycle().getFirstPeriod();
     TestInstructorsConditions testIns= new TestInstructorsConditions(_dm);
     int[] perKey={1,1,1};
     int nbConf= testIns.executeTest(perKey,period,"AMC640.1.01.1.",0);
     assertEquals("test_Availability : assertEquals 2",1, nbConf);
   }

   /**
    *
    */
   public void test_2EventsConflicts(){
     _dm.getTTStructure().getCurrentCycle().getNextPeriod(1);
     Period period= _dm.getTTStructure().getCurrentCycle().getNextPeriod(1);
     TestInstructorsConditions testIns= new TestInstructorsConditions(_dm);
     int[] perKey={1,1,2};
     testIns.executeTest(perKey,period,"AMC640.1.01.1.",1);
     int nbConf= testIns.executeTest(perKey,period,"AMC640.1.02.1.",0);
     assertEquals("test_2EventsConflicts : assertEquals 2",1, nbConf);
   }

}