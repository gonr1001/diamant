package dTest.dInternal.dData.dInstructors;

import dConstants.DConst;
import dInternal.dData.DLoadData;
import dInternal.dData.dInstructors.DxInstructorReader;
import dInternal.dData.dInstructors.DxReadInstructor1dot5;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DxInstructorReaderTest extends TestCase {
    public DxInstructorReaderTest(String name) {
        super(name);
    }

    public static Test suite() {
        // the type safe way is in SimpleTest
        // the dynamic way :
        return new TestSuite(DxInstructorReaderTest.class);
    } // end suite

    /**
     * test_getSetOfInstructors, verifies v1.5 analyser Should fail because of
     * invalid instructor count format
     */
    public void test_getSetOfInstructors() {
        String tokens = "    1k" + "\r\n" + "ADM111" + "\r\n"
                + "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
                + "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
        DLoadData ld = new DLoadData();
        DxInstructorReader dxriTest = new DxReadInstructor1dot5(ld
                .buildDataExchange(tokens.getBytes()), 5, 14);
        try {
            DxSetOfInstructors dxsoi = dxriTest.getSetOfInstructors();
            assertFalse("test_getSetOfInstructors: Should have failed before",
                    true);

            // To avoid warning
            dxsoi.getIndexByKey(0);
        } catch (Exception e) {
            assertEquals("test_1_getSetOfInstructors: assertEquals",
                    "java.lang.Exception: " + DConst.INST_TEXT1 + "\n"
                            + DConst.INST_TEXT6, e.toString());
        }

    }

    /**
     * test1_getSetOfInstructors, verifies v1.5 analyser invalid instructor name
     */
    public void test1_getSetOfInstructors() {
        String tokens = "    2" + "\r\n" + "" + "\r\n"
                + "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
                + "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n" + "POLM" + "\r\n"
                + "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
                + "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
        DLoadData ld = new DLoadData();
        DxInstructorReader dxriTest = new DxReadInstructor1dot5(ld
                .buildDataExchange(tokens.getBytes()), 5, 14);
        try {
            DxSetOfInstructors dxsoi = dxriTest.getSetOfInstructors();
            assertFalse("test1_getSetOfInstructors: Should have failed before",
                    true);

            // To avoid warning
            dxsoi.getIndexByKey(0);
        } catch (Exception e) {
            assertEquals("test1_1_getSetOfInstructors: assertEquals",
                    "java.lang.Exception: " + DConst.INST_TEXT3 + 7
                    + DConst.INST_TEXT5 + "\n" + DConst.INST_TEXT6, e
                            .toString());
        }
    }

    /**
     * test2_getSetOfInstructors, verifies v1.5 analyser Suggested instructor
     * count and actual number of instrucator in the file does not match
     */
    public void test2_getSetOfInstructors() {
        String tokens = "    3" + "\r\n" + "JAC" + "\r\n"
                + "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
                + "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n" + "POLM" + "\r\n"
                + "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
                + "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
        DLoadData ld = new DLoadData();
        DxInstructorReader dxriTest = new DxReadInstructor1dot5(ld
                .buildDataExchange(tokens.getBytes()), 5, 14);
        try {
            DxSetOfInstructors dxsoi = dxriTest.getSetOfInstructors();
            assertFalse("test2_getSetOfInstructors: Should have failed before",
                    true);

            // To avoid warning
            dxsoi.getIndexByKey(0);
        } catch (Exception e) {
            assertEquals("test2_1_getSetOfInstructors: assertEquals",
                    "java.lang.Exception: " + DConst.INST_TEXT1 + "\n"
                            + DConst.INST_TEXT6, e.toString());
        }
    }

    /**
     * test2_getSetOfInstructors, verifies v1.5 analyser Should fail because of
     * invalid number of periods in second instructor, 3rd day
     */
    public void test3_getSetOfInstructors() {
        String tokens = "    2" + "\r\n" + "John" + "\r\n"
                + "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
                + "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n" + "POLM" + "\r\n"
                + "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
                + "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 1 1 1 6" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
        DLoadData ld = new DLoadData();
        DxInstructorReader dxriTest = new DxReadInstructor1dot5(ld
                .buildDataExchange(tokens.getBytes()), 5, 14);
        try {
            DxSetOfInstructors dxsoi = dxriTest.getSetOfInstructors();
            assertFalse("test3_getSetOfInstructors: Shoudd have failed before",
                    true);

            // To avoid warning
            dxsoi.getIndexByKey(0);
        } catch (Exception e) {
            assertEquals("test3_1_getSetOfInstructors: assertEquals",
                    "java.lang.Exception: " + DConst.INST_TEXT3 + 11
                            + DConst.INST_TEXT5 + "\n" + DConst.INST_TEXT6, e
                            .toString());
        }
    }

    /**
     * test2_getSetOfInstructors, verifies v1.5 analyser Make sure analyser
     * verify nature of period availability. 3 is an invalid period availability
     * in v1.5
     */
    public void test4_getSetOfInstructors() {
        String tokens = "    2" + "\r\n" + "JAC" + "\r\n"
                + "1 3 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
                + "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n" + "POLM" + "\r\n"
                + "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
                + "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
        DLoadData ld = new DLoadData();
        DxInstructorReader dxriTest = new DxReadInstructor1dot5(ld
                .buildDataExchange(tokens.getBytes()), 5, 14);
        try {
            DxSetOfInstructors dxsoi = dxriTest.getSetOfInstructors();
            assertFalse("test4_getSetOfInstructors: Shoudd have failed before",
                    true);

            // To avoid warning
            dxsoi.getIndexByKey(0);
        } catch (Exception e) {
            assertEquals("test4_1_getSetOfInstructors: assertEquals",
                    "java.lang.Exception: " + DConst.INST_TEXT4 + 3
                            + DConst.INST_TEXT5 + "\n" + DConst.INST_TEXT6, e
                            .toString());
        }
    }

    /**
     * test2_getSetOfInstructors, verifies v1.5 builder Make sure analyser
     * verify nature of period availability. 3 is an invalid period availability
     * in v1.5
     */
    public void test5_getSetOfInstructors() {
        String tokens = "    2" + "\r\n" + "JAC" + "\r\n"
                + "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
                + "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n" + "POLM" + "\r\n"
                + "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
                + "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
                + "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
        DLoadData ld = new DLoadData();
        DxInstructorReader dxriTest = new DxReadInstructor1dot5(ld
                .buildDataExchange(tokens.getBytes()), 5, 14);
        try {
            DxSetOfInstructors dxsoiTemp = dxriTest.getSetOfInstructors();
            assertNotSame("test5_getSetOfInstructors: assertNotSame",
                    dxsoiTemp, null);
            assertEquals("test5_1_getSetOfInstructors: assertEquals", dxsoiTemp
                    .getInstructorName(0), "JAC");
            assertEquals("test5_2_getSetOfInstructors: assertEquals", dxsoiTemp
                    .getInstructorName(1), "POLM");
        } catch (Exception e) {
            assertFalse("test5_3_getSetOfInstructors: Should not have failed" + e.toString(), true);
        }
    }
}
