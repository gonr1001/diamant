package dTest.dInternal.dConditionsTest;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */
import junit.framework.*;


import dInternal.dConditionsTest.StudentsConflictsMatrix;

import dInternal.DModel;
import dInterface.DDocument;

import java.io.File;

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
  _dm.getConditionsTest().initAllConditions();
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
    String key1= "ADM111.1.A";// key 1
    String key2 = "GIN310.1.A";//key 136
    int[] index= _matrix.getSectionsKeys(key1,key2);
    assertEquals("test1_Matrix : assertEquals", 136, index[1]);
    assertEquals("test1_Matrix : assertEquals", 1, index[0]);
   }

   /**
    *
    */
   public void test1_Matrix(){
     String key1= "ADM111.1.A";
     String key2 = "GIN310.1.A";
     assertEquals("test1_Matrix : assertEquals", 4, _matrix.getNumberOfCOnflicts(key1,key2));
   }

   /**
    *
    */
   public void test2_Matrix(){
     String key1= "ADM111.1.B";
     String key2 = "GIN310.1.A";
     assertEquals("test2_Matrix : assertEquals", 3, _matrix.getNumberOfCOnflicts(key1, key2));
   }

   /**
    *
    */
   public void test3_Matrix(){
     String key1= "ADM111.1.C";
     String key2 = "GIN310.1.A";
     assertEquals("test2_Matrix : assertEquals", 3, _matrix.getNumberOfCOnflicts(key1, key2));
   }



}