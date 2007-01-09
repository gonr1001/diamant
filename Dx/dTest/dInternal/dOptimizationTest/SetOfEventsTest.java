package dTest.dInternal.dOptimizationTest;

import java.io.File;
import java.util.StringTokenizer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dDeveloper.DxFlags;
import dInterface.DxTTableDoc;
import dInternal.DModel;
import dInternal.dOptimization.EventAttach;
import dInternal.dOptimization.SetOfEvents;

public class SetOfEventsTest extends TestCase {

	private DModel _dm;
	
	private DModel _dm1;


	private SetOfEvents _soe;
	
	private SetOfEvents _soe1;

	public SetOfEventsTest(String name) {
		super(name);
		try {
			_dm = new DModel(new DxTTableDoc(), "." + File.separator
					+ "dataTest" + File.separator + "loadData5j.dia");
			_dm1 = new DModel(new DxTTableDoc(), "." + File.separator
					+ "dataTest" + File.separator + "assignRooms.dia");
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		_soe = _dm.getSetOfEvents();
		_soe1 = _dm1.getSetOfEvents();
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(SetOfEventsTest.class);
	} // end suite

	/**
	 * test the principal key of the first event of the setofevents
	 */
	public void test_firstEvent() {

		String pincKey = ((EventAttach) _soe.getResourceAt(0).getAttach())
				.getPrincipalRescKey();
		StringTokenizer keys = new StringTokenizer(pincKey, ".");
		String firstEvent = _dm.getSetOfActivities().getUnityCompleteName(
				Long.parseLong(keys.nextToken()),
				Long.parseLong(keys.nextToken()),
				Long.parseLong(keys.nextToken()),
				Long.parseLong(keys.nextToken()));
		assertEquals("test_firstEvent : assertEquals: ", "AMC640.1.01.1.",
				firstEvent);
	}

	/**
	 * test the instructor key of the first event of the setofevents
	 */
	public void test1_Instructor_In_Event() {
		long insKey[] = ((EventAttach) _soe.getResourceAt(0).getAttach())
				.getInstructorKey();
		assertEquals("test1_Instructor_In_Event : assertEquals: ", "THÉRIEN, NORMAND", _dm
				.getDxSetOfInstructors().getInstructorName(insKey[0]));

	}

	/**
	 * test the rooms key of the first event of the setofevents
	 */
	public void test2_RoomInEvent() {
		long roomKey = ((EventAttach) _soe.getResourceAt(0).getAttach())
				.getRoomKey();
		if (DxFlags.newRooms) {
			assertEquals("test2_RoomInEvent1 : assertEquals: ", "D73020", _dm
					.getDxSetOfRooms().getRoomName(roomKey));
		} else {
			assertEquals("test2_RoomInEvent2 : assertEquals: ", "D73020", _dm
					.getSetOfRooms().getResource(roomKey).getID());
		}
	}
	
	/**
	 * test the principal key of the first event of the setofevents
	 */
	public void test_addEvents() {

		String pincKey = ((EventAttach) _soe1.getResourceAt(0).getAttach())
				.getPrincipalRescKey();
		StringTokenizer keys = new StringTokenizer(pincKey, ".");
		String firstEvent = _dm1.getSetOfActivities().getUnityCompleteName(
				Long.parseLong(keys.nextToken()),
				Long.parseLong(keys.nextToken()),
				Long.parseLong(keys.nextToken()),
				Long.parseLong(keys.nextToken()));
		assertEquals("test_addEvents : assertEquals: ", "AMC640.1.01.1.",
				firstEvent);
	}

} //end SetOfEventsTest
