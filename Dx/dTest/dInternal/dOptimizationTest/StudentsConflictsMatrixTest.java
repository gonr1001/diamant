package dTest.dInternal.dOptimizationTest;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */
import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInterface.DDocument;
import dInternal.DModel;
import dInternal.dOptimization.StudentsConflictsMatrix;

public class StudentsConflictsMatrixTest extends TestCase{

StudentsConflictsMatrix _matrix;
DModel _dm;
//SetOfActivities _soa;
//SetOfStudents _sos;
//  public class ResourceTest extends TestCase {

public StudentsConflictsMatrixTest(String name) {
  super(name);
  _dm= new DModel(new DDocument(),System.getProperty("user.dir")+ File.separator+"dataTest"+
                    File.separator+"loadData.dia",0);
  _dm.buildSetOfEvents();
  //_dm.getConditionsTest().buildStudentsMatrix(_dm.getSetOfActivities(),_dm.getSetOfStudents());
  _dm.getConditionsTest().buildStudentConflictMatrix();
  _dm.getConditionsTest().buildAllConditions(_dm.getTTStructure());
  _dm.setStateBarComponent();
  _matrix = _dm.getConditionsTest().getConflictsMatrix();
}

   public static Test suite() {
    // the type safe way is in SimpleTest
    // the dynamic way :
    return new TestSuite(StudentsConflictsMatrixTest.class);
   } // end suite

   /**
   *
   */
  public void test_SectionKeys(){
    String key1= "GCH100.1.01";// key 1
    String key2 = "AMC645.1.01";//key 136
    int[] index= _matrix.getSectionsKeys(key1,key2);
    assertEquals("test_SectionKeys : assertEquals 1", 6, index[0]);
    assertEquals("test_SectionKeys : assertEquals 2", 10, index[1]);
   }

   /**
    *
    */
   public void test1_Matrix(){
     String key1= "AMC640.1.01";
     String key2 = "AMC645.1.01";
     assertEquals("test1_Matrix : assertEquals", 4, _matrix.getNumberOfCOnflicts(key1,key2));

   }

   /**
    *
    */
   public void test2_Matrix(){
     String key1= "AMC640.1.02";
    String key2 = "AMC645.1.01";
    assertEquals("test1_Matrix : assertEquals", 1, _matrix.getNumberOfCOnflicts(key1,key2));

   }

   /**
    *
    */
   public void test3_Matrix(){
     String key1= "GCH111.2.01";
     String key2 = "AMC645.1.01";
     assertEquals("test3_Matrix : assertEquals", 1, _matrix.getNumberOfCOnflicts(key1, key2));
   }



}