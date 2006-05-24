package dTest.dInternal.dOptimizationTest;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInterface.DDocument;
import dInternal.DModel;
import dInternal.dOptimization.DxRoomsConditionsToTest;
import dInternal.dTimeTable.Period;

public class RoomsConditionsTest extends TestCase {

    private DModel _dm1; // For LoadData7j

    private DModel _dm2; // For LoadData5j

    public RoomsConditionsTest(String name) {
        super(name);
        try {
            _dm1 = new DModel(new DDocument(), "." + File.separator
                    + "dataTest" + File.separator + "loadData7j.dia", 1);
        } catch (Exception e) {
            // Should not fail in controled conditions
        }
        _dm1.buildSetOfEvents();
        // _dm.getConditionsTest().buildStudentsMatrix(_dm.getSetOfActivities(),_dm.getSetOfStudents());
        _dm1.getConditionsTest().buildStudentConflictMatrix();
        _dm1.getConditionsTest().buildAllConditions(_dm1.getTTStructure());

        try {
            _dm2 = new DModel(new DDocument(), "." + File.separator
                    + "dataTest" + File.separator + "loadData5j.dia", 1);
        } catch (Exception e) {
            // Should not fail in controled conditions
        }
        _dm2.buildSetOfEvents();
        // _dm.getConditionsTest().buildStudentsMatrix(_dm.getSetOfActivities(),_dm.getSetOfStudents());
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
    public void test_Availability() {
        Period period = _dm1.getTTStructure().getCurrentCycle()
                .getFirstPeriod();
        DxRoomsConditionsToTest testRoom = new DxRoomsConditionsToTest(_dm1);
        int[] perKey = { 1, 1, 1 };
        int nbConf = testRoom.executeTest(perKey, period, "AMC640.1.01.1.", 0);
        assertEquals("test_Availability : assertEquals 2", 1, nbConf);
    }

    public void test2_Availability() {
        Period period = _dm2.getTTStructure().getCurrentCycle()
                .getFirstPeriod();
        DxRoomsConditionsToTest testRoom = new DxRoomsConditionsToTest(_dm2);
        int[] perKey = { 1, 1, 1 };
        int nbConf = testRoom.executeTest(perKey, period, "AMC640.1.01.1.", 0);
        assertEquals("test2_Availability : assertEquals 2", 1, nbConf);
    }

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
     DxRoomsConditionsToTest testRoom= new DxRoomsConditionsToTest(_dm1);
     int[] perKey={1,1,1};
     int nbConf= testRoom.getInfo(perKey,period,"AMC640.1.01.1.");
     assertEquals("test_Availability : assertEquals 2",1, nbConf);
   }
  public void test2_Availability(){
      Period period= _dm2.getTTStructure().getCurrentCycle().getFirstPeriod();
      DxRoomsConditionsToTest testRoom= new DxRoomsConditionsToTest(_dm2);
      int[] perKey={1,1,1};
      int nbConf= testRoom.getInfo(perKey,period,"AMC640.1.01.1.");
      assertEquals("test2_Availability : assertEquals 2",1, nbConf);
    }

   /**
    *
    */
   public void test_EventsConflicts(){
     _dm1.getTTStructure().getCurrentCycle().getNextPeriod(1);
     Period period= _dm1.getTTStructure().getCurrentCycle().getNextPeriod(1);
     DxRoomsConditionsToTest testRoom= new DxRoomsConditionsToTest(_dm1);
     int[] perKey={1,1,2};
     testRoom.addTest(perKey,period,"AMC640.1.01.1.");
     int nbConf= testRoom.getInfo(perKey,period,"AMC640.1.02.1.");
     assertEquals("test_EventsConflicts : assertEquals 2",1, nbConf);
   }
   public void test2_EventsConflicts(){
       _dm2.getTTStructure().getCurrentCycle().getNextPeriod(1);
       Period period= _dm2.getTTStructure().getCurrentCycle().getNextPeriod(1);
       DxRoomsConditionsToTest testRoom= new DxRoomsConditionsToTest(_dm2);
       int[] perKey={1,1,2};
       testRoom.addTest(perKey,period,"AMC640.1.01.1.");
       int nbConf= testRoom.getInfo(perKey,period,"AMC640.1.02.1.");
       assertEquals("test2_EventsConflicts : assertEquals 2",1, nbConf);
     }

}