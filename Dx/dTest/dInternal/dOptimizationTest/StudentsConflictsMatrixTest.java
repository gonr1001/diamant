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

public class StudentsConflictsMatrixTest extends TestCase {

    StudentsConflictsMatrix _matrix1;
    DModel _dm1;    //For LoadData7j
    StudentsConflictsMatrix _matrix2;
    DModel _dm2;    //For LoadData5j

    // SetOfActivities _soa;
    // SetOfStudents _sos;
    // public class ResourceTest extends TestCase {

    public StudentsConflictsMatrixTest(String name) {
        super(name);
        _dm1 = new DModel(new DDocument(), "." + File.separator + "dataTest"
                + File.separator + "loadData7j.dia", 0);
        _dm1.buildSetOfEvents();
        // _dm.getConditionsTest().buildStudentsMatrix(_dm.getSetOfActivities(),_dm.getSetOfStudents());
        _dm1.getConditionsTest().buildStudentConflictMatrix();
        _dm1.getConditionsTest().buildAllConditions(_dm1.getTTStructure());
        // _dm.setStateBarComponent();
        _matrix1 = _dm1.getConditionsTest().getConflictsMatrix();

        _dm2 = new DModel(new DDocument(), "." + File.separator + "dataTest"
                + File.separator + "loadData5j.dia", 0);
        _dm2.buildSetOfEvents();
        // _dm.getConditionsTest().buildStudentsMatrix(_dm.getSetOfActivities(),_dm.getSetOfStudents());
        _dm2.getConditionsTest().buildStudentConflictMatrix();
        _dm2.getConditionsTest().buildAllConditions(_dm2.getTTStructure());
        // _dm.setStateBarComponent();
        _matrix2 = _dm2.getConditionsTest().getConflictsMatrix();
    }

    public static Test suite() {
        // the type safe way is in SimpleTest
        // the dynamic way :
        return new TestSuite(StudentsConflictsMatrixTest.class);
    } // end suite

    /**
     * 
     */
    public void test_SectionKeys() {
        String key1 = "GCH100.1.01";// key 1
        String key2 = "AMC645.1.01";// key 136
        int[] index = _matrix1.getSectionsKeys(key1, key2);
        assertEquals("test_SectionKeys_1 : assertEquals 1", 6, index[0]);
        assertEquals("test_SectionKeys_2 : assertEquals 2", 10, index[1]);
    }
    public void test2_SectionKeys() {
        String key1 = "GCH100.1.01";// key 1
        String key2 = "AMC645.1.01";// key 136
        int[] index = _matrix2.getSectionsKeys(key1, key2);
        assertEquals("test2_SectionKeys_1 : assertEquals 1", 6, index[0]);
        assertEquals("test2_SectionKeys_2 : assertEquals 2", 10, index[1]);
    }

    /**
     * 
     */
    public void test1_Matrix() {
        String key1 = "AMC640.1.01";
        String key2 = "AMC645.1.01";
        assertEquals("test1_Matrix : assertEquals", 4, _matrix1
                .getNumberOfCOnflicts(key1, key2));

    }

    /**
     * 
     */
    public void test2_Matrix() {
        String key1 = "AMC640.1.02";
        String key2 = "AMC645.1.01";
        assertEquals("test2_Matrix : assertEquals", 1, _matrix1
                .getNumberOfCOnflicts(key1, key2));

    }

    /**
     * 
     */
    public void test3_Matrix() {
        String key1 = "GCH111.2.01";
        String key2 = "AMC645.1.01";
        assertEquals("test3_Matrix : assertEquals", 1, _matrix1
                .getNumberOfCOnflicts(key1, key2));
    }
    
    /**
     * 
     */
    public void test4_Matrix() {
        String key1 = "AMC640.1.01";
        String key2 = "AMC645.1.01";
        assertEquals("test4_Matrix : assertEquals", 4, _matrix2
                .getNumberOfCOnflicts(key1, key2));

    }

    /**
     * 
     */
    public void test5_Matrix() {
        String key1 = "AMC640.1.02";
        String key2 = "AMC645.1.01";
        assertEquals("test5_Matrix : assertEquals", 1, _matrix2
                .getNumberOfCOnflicts(key1, key2));

    }

    /**
     * 
     */
    public void test6_Matrix() {
        String key1 = "GCH111.2.01";
        String key2 = "AMC645.1.01";
        assertEquals("test6_Matrix : assertEquals", 1, _matrix2
                .getNumberOfCOnflicts(key1, key2));
    }

}