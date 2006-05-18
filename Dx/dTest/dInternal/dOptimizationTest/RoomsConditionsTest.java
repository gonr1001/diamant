package dTest.dInternal.dOptimizationTest;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInterface.DDocument;
import dInternal.DModel;
import dInternal.dOptimization.TestRoomsConditions;
import dInternal.dTimeTable.Period;



public class RoomsConditionsTest extends TestCase {

  private DModel _dm1; //For LoadData7j
  private DModel _dm2; //For LoadData5j

  public RoomsConditionsTest(String name) {
    super(name);
    _dm1= new DModel(new DDocument(),"."+ File.separator+"dataTest"+File.separator+"loadData7j.dia",1);
    _dm1.buildSetOfEvents();
    //_dm.getConditionsTest().buildStudentsMatrix(_dm.getSetOfActivities(),_dm.getSetOfStudents());
    _dm1.getConditionsTest().buildStudentConflictMatrix();
    _dm1.getConditionsTest().buildAllConditions(_dm1.getTTStructure());
    
    _dm2= new DModel(new DDocument(),"."+ File.separator+"dataTest"+File.separator+"loadData5j.dia",1);
    _dm2.buildSetOfEvents();
    //_dm.getConditionsTest().buildStudentsMatrix(_dm.getSetOfActivities(),_dm.getSetOfStudents());
    _dm2.getConditionsTest().buildStudentConflictMatrix();
    _dm2.getConditionsTest().buildAllConditions(_dm2.getTTStructure());
  }

  public static Test suite() {
    // the type safe way is in SimpleTest
    // the dynamic way :
    return new TestSuite(RoomsConditionsTest.class);
  } // end suite

  /**
   *
   */
  public void test_Availability(){
     Period period= _dm1.getTTStructure().getCurrentCycle().getFirstPeriod();
     TestRoomsConditions testRoom= new TestRoomsConditions(_dm1);
     int[] perKey={1,1,1};
     int nbConf= testRoom.executeTest(perKey,period,"AMC640.1.01.1.",0);
     assertEquals("test_Availability : assertEquals 2",1, nbConf);
   }
  public void test2_Availability(){
      Period period= _dm2.getTTStructure().getCurrentCycle().getFirstPeriod();
      TestRoomsConditions testRoom= new TestRoomsConditions(_dm2);
      int[] perKey={1,1,1};
      int nbConf= testRoom.executeTest(perKey,period,"AMC640.1.01.1.",0);
      assertEquals("test2_Availability : assertEquals 2",1, nbConf);
    }

   /**
    *
    */
   public void test_EventsConflicts(){
     _dm1.getTTStructure().getCurrentCycle().getNextPeriod(1);
     Period period= _dm1.getTTStructure().getCurrentCycle().getNextPeriod(1);
     TestRoomsConditions testRoom= new TestRoomsConditions(_dm1);
     int[] perKey={1,1,2};
     testRoom.executeTest(perKey,period,"AMC640.1.01.1.",1);
     int nbConf= testRoom.executeTest(perKey,period,"AMC640.1.02.1.",0);
     assertEquals("test_EventsConflicts : assertEquals 2",1, nbConf);
   }
   public void test2_EventsConflicts(){
       _dm2.getTTStructure().getCurrentCycle().getNextPeriod(1);
       Period period= _dm2.getTTStructure().getCurrentCycle().getNextPeriod(1);
       TestRoomsConditions testRoom= new TestRoomsConditions(_dm2);
       int[] perKey={1,1,2};
       testRoom.executeTest(perKey,period,"AMC640.1.01.1.",1);
       int nbConf= testRoom.executeTest(perKey,period,"AMC640.1.02.1.",0);
       assertEquals("test2_EventsConflicts : assertEquals 2",1, nbConf);
     }

}