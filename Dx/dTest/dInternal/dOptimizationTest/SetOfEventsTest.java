package dTest.dInternal.dOptimizationTest;

import java.io.File;
import java.util.StringTokenizer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dConstants.DConst;
import dInterface.DDocument;
import dInternal.DModel;
import dInternal.dOptimization.EventAttach;
import dInternal.dOptimization.SetOfEvents;
import eLib.exit.dialog.FatalProblemDlg;
import eLib.exit.exception.IOFileException;

public class SetOfEventsTest extends TestCase {

    private DModel _dm;

    private SetOfEvents _soe;

    public SetOfEventsTest(String name) {
        super(name);
        try {
            _dm = new DModel(new DDocument(), "." + File.separator + "dataTest"
                    + File.separator + "loadData5j.dia", 1);
        } catch (Exception e) {
     			System.out.println(e);
     			e.printStackTrace();    			
        }
        _soe = _dm.getSetOfEvents();
        // _soe.build(_dm.getSetOfActivities(), _dm.getSetOfImportErrors());
        //		
        // _soe = new SetOfEvents(_dm);
        // _soe.build(_dm.getSetOfActivities(), _dm.getSetOfImportErrors());
    }

    public static Test suite() {
        // the type safe way is in SimpleTest
        // the dynamic way :
        return new TestSuite(SetOfEventsTest.class);
    } // end suite

    /**
     * test the principal key of the first event of the setofevents
     */
    public void test_build() {

        String pincKey = ((EventAttach) _soe.getResourceAt(0).getAttach())
                .getPrincipalRescKey();
        StringTokenizer keys = new StringTokenizer(pincKey, ".");
        String firstEvent = _dm.getSetOfActivities().getUnityCompleteName(
                Long.parseLong(keys.nextToken()),
                Long.parseLong(keys.nextToken()),
                Long.parseLong(keys.nextToken()),
                Long.parseLong(keys.nextToken()));
        assertEquals("test_build : assertEquals: ", "AMC640.1.01.1.",
                firstEvent);
    }

    /**
     * test the instructor key of the first event of the setofevents
     */
    public void test1_build() {
        if (!DConst.newInstructors) {
            long insKey[] = ((EventAttach) _soe.getResourceAt(0).getAttach())
                    .getInstructorKey();
            assertEquals("test1_build : assertEquals: ", "THÉRIEN, NORMAND",
                    _dm.getSetOfInstructors().getResource(insKey[0]).getID());
        } else {
            long insKey[] = ((EventAttach) _soe.getResourceAt(0).getAttach())
                    .getInstructorKey();
            assertEquals("test1_build : assertEquals: ", "THÉRIEN, NORMAND",
                    _dm.getDxSetOfInstructors().getInstructorNameByKey(
                            insKey[0]));
        }
    }

    /**
     * test the rooms key of the first event of the setofevents
     */
    public void test2_build() {
        long roomKey = ((EventAttach) _soe.getResourceAt(0).getAttach())
                .getRoomKey();
        assertEquals("test_build : assertEquals: ", "D73020", _dm
                .getSetOfRooms().getResource(roomKey).getID());
    }

}
