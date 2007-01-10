package dTest.dInternal.dOptimizationTest;

import java.io.File;
import java.util.StringTokenizer;
import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dConstants.DConst;
import dDeveloper.DxFlags;
import dInterface.DxTTableDoc;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.dData.StandardCollection;
import dInternal.dData.dActivities.Activity;
import dInternal.dOptimization.EventAttach;
import dInternal.dOptimization.SetOfEvents;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Period;
import dInternal.dUtil.DXToolsMethods;

public class SetOfEventsTest extends TestCase {

	private DModel _dm;

	private DModel _dm1;

	private SetOfEvents _soe;

	private SetOfEvents _soe1;

	private final int NO_ROOM_ASSIGNED = -1;

	private final int TOKEN_RANGE = 0;

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
		assertEquals("test1_Instructor_In_Event : assertEquals: ",
				"THÉRIEN, NORMAND", _dm.getDxSetOfInstructors()
						.getInstructorName(insKey[0]));

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
		Cycle cycle = _dm1.getTTStructure().getCurrentCycle();
		cycle.setCurrentDaySeqPerIndex(0, 0, 0);
		Period currentPeriod = cycle.getNextPeriod(1);
		DSetOfResources newSetOfEvents = new StandardCollection();
		// Vector contains the events (Ressources) in the currentPeriod
		Vector eventsInPeriod = currentPeriod.getEventsInPeriod()
				.getSetOfResources();
		int numberOfStudents;
		int index;

		// for each event in period do some tests
		// get the name of an event in the period
		String eventInPeriodName = ((DResource) eventsInPeriod.get(0)).getID();
		// get the attach of the event
		EventAttach eventAttach = (EventAttach) _soe1.getResource(
				eventInPeriodName).getAttach();

		String actID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE);
		Activity activity = (Activity) _dm1.getSetOfActivities().getResource(
				actID).getAttach();
		numberOfStudents = activity.getStudentRegistered().size();
		DResource resc = new DResource(Integer.toString(numberOfStudents),
				eventAttach);
		index = newSetOfEvents.searchWhereToInsert(Integer
				.toString(numberOfStudents));
		assertEquals("test_addEvents : assertEquals: ", 0, index);
		if (eventAttach.getRoomKey() == NO_ROOM_ASSIGNED) {
			newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
		}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)

		// for each event in period do some tests
		// get next, get the name of an event in the period
		eventInPeriodName = ((DResource) eventsInPeriod.get(1)).getID();
		// get the attach of the event
		eventAttach = (EventAttach) _soe1.getResource(eventInPeriodName)
				.getAttach();

		actID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE);
		activity = (Activity) _dm1.getSetOfActivities().getResource(actID)
				.getAttach();
		numberOfStudents = activity.getStudentRegistered().size();
		resc = new DResource(Integer.toString(numberOfStudents), eventAttach);
		index = newSetOfEvents.searchWhereToInsert(Integer
				.toString(numberOfStudents));
		assertEquals("test_addEvents : assertEquals: ", 1, index);
		if (eventAttach.getRoomKey() == NO_ROOM_ASSIGNED) {
			newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
		}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)

		// for each event in period do some tests
		// get next, get the name of an event in the period
		eventInPeriodName = ((DResource) eventsInPeriod.get(2)).getID();
		// get the attach of the event
		eventAttach = (EventAttach) _soe1.getResource(eventInPeriodName)
				.getAttach();

		actID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE);
		activity = (Activity) _dm1.getSetOfActivities().getResource(actID)
				.getAttach();
		numberOfStudents = activity.getStudentRegistered().size();
		resc = new DResource(Integer.toString(numberOfStudents), eventAttach);
		index = newSetOfEvents.searchWhereToInsert(Integer
				.toString(numberOfStudents));
		assertEquals("test_addEvents : assertEquals: ", 1, index);
		if (eventAttach.getRoomKey() == NO_ROOM_ASSIGNED) {
			newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
		}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)
		newSetOfEvents.sortSetOfResourcesByID();
		assertEquals("test_addEvents : assertEquals: ", 3, newSetOfEvents
				.size());

		// for each event in period do some tests
		// get next, get the name of an event in the period
		eventInPeriodName = ((DResource) eventsInPeriod.get(3)).getID();
		// get the attach of the event
		eventAttach = (EventAttach) _soe1.getResource(eventInPeriodName)
				.getAttach();

		actID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE);
		activity = (Activity) _dm1.getSetOfActivities().getResource(actID)
				.getAttach();
		numberOfStudents = activity.getStudentRegistered().size();
		resc = new DResource(Integer.toString(numberOfStudents), eventAttach);
		index = newSetOfEvents.searchWhereToInsert(Integer
				.toString(numberOfStudents));
		assertEquals("test_addEvents : assertEquals: ", 1, index);
		if (eventAttach.getRoomKey() == NO_ROOM_ASSIGNED) {
			newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
		}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)
		newSetOfEvents.sortSetOfResourcesByID();
		assertEquals("test_addEvents : assertEquals: ", 4, newSetOfEvents
				.size());

		// for each event in period do some tests
		// get next, get the name of an event in the period
		eventInPeriodName = ((DResource) eventsInPeriod.get(4)).getID();
		// get the attach of the event
		eventAttach = (EventAttach) _soe1.getResource(eventInPeriodName)
				.getAttach();

		actID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE);
		activity = (Activity) _dm1.getSetOfActivities().getResource(actID)
				.getAttach();
		numberOfStudents = activity.getStudentRegistered().size();
		resc = new DResource(Integer.toString(numberOfStudents), eventAttach);
		index = newSetOfEvents.searchWhereToInsert(Integer
				.toString(numberOfStudents));
		assertEquals("test_addEvents : assertEquals: ", 3, index);
		if (eventAttach.getRoomKey() == NO_ROOM_ASSIGNED) {
			newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
		}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)
		newSetOfEvents.sortSetOfResourcesByID();
		assertEquals("test_addEvents : assertEquals: ", 5, newSetOfEvents
				.size());

		// for each event in period do some tests
		// get next, get the name of an event in the period
		eventInPeriodName = ((DResource) eventsInPeriod.get(5)).getID();
		// get the attach of the event
		eventAttach = (EventAttach) _soe1.getResource(eventInPeriodName)
				.getAttach();

		actID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE);
		activity = (Activity) _dm1.getSetOfActivities().getResource(actID)
				.getAttach();
		numberOfStudents = activity.getStudentRegistered().size();
		resc = new DResource(Integer.toString(numberOfStudents), eventAttach);
		index = newSetOfEvents.searchWhereToInsert(Integer
				.toString(numberOfStudents));
		assertEquals("test_addEvents : assertEquals: ", 3, index);
		if (eventAttach.getRoomKey() == NO_ROOM_ASSIGNED) {
			newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
		}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)
		newSetOfEvents.sortSetOfResourcesByID();
		assertEquals("test_addEvents : assertEquals: ", 6, newSetOfEvents
				.size());

		// for each event in period do some tests
		// get next, get the name of an event in the period
		eventInPeriodName = ((DResource) eventsInPeriod.get(6)).getID();
		// get the attach of the event
		eventAttach = (EventAttach) _soe1.getResource(eventInPeriodName)
				.getAttach();

		actID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE);
		activity = (Activity) _dm1.getSetOfActivities().getResource(actID)
				.getAttach();
		numberOfStudents = activity.getStudentRegistered().size();
		resc = new DResource(Integer.toString(numberOfStudents), eventAttach);
		index = newSetOfEvents.searchWhereToInsert(Integer
				.toString(numberOfStudents));
		assertEquals("test_addEvents : assertEquals: ", 5, index);
		if (eventAttach.getRoomKey() == NO_ROOM_ASSIGNED) {
			newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
		}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)
		newSetOfEvents.sortSetOfResourcesByID();
		assertEquals("test_addEvents : assertEquals: ", 6, newSetOfEvents
				.size());

		// for each event in period do some tests
		// get next, get the name of an event in the period
		eventInPeriodName = ((DResource) eventsInPeriod.get(7)).getID();
		// get the attach of the event
		eventAttach = (EventAttach) _soe1.getResource(eventInPeriodName)
				.getAttach();

		actID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE);
		activity = (Activity) _dm1.getSetOfActivities().getResource(actID)
				.getAttach();
		numberOfStudents = activity.getStudentRegistered().size();
		resc = new DResource(Integer.toString(numberOfStudents), eventAttach);
		index = newSetOfEvents.searchWhereToInsert(Integer
				.toString(numberOfStudents));
		assertEquals("test_addEvents : assertEquals: ", 5, index);
		if (eventAttach.getRoomKey() == NO_ROOM_ASSIGNED) {
			newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
		}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)
		newSetOfEvents.sortSetOfResourcesByID();
		assertEquals("test_addEvents : assertEquals: ", 6, newSetOfEvents
				.size());

		// for each event in period do some tests
		// get next, get the name of an event in the period
		eventInPeriodName = ((DResource) eventsInPeriod.get(8)).getID();
		// get the attach of the event
		eventAttach = (EventAttach) _soe1.getResource(eventInPeriodName)
				.getAttach();

		actID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE);
		activity = (Activity) _dm1.getSetOfActivities().getResource(actID)
				.getAttach();
		numberOfStudents = activity.getStudentRegistered().size();
		resc = new DResource(Integer.toString(numberOfStudents), eventAttach);
		index = newSetOfEvents.searchWhereToInsert(Integer
				.toString(numberOfStudents));
		assertEquals("test_addEvents : assertEquals: ", 5, index);
		if (eventAttach.getRoomKey() == NO_ROOM_ASSIGNED) {
			newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
		}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)
		newSetOfEvents.sortSetOfResourcesByID();
		assertEquals("test_addEvents : assertEquals: ", 6, newSetOfEvents
				.size());
		eventInPeriodName = (newSetOfEvents.getResourceAt(0).getID());
		// get the attach of the event
		eventAttach = (EventAttach) newSetOfEvents.getResource(
				eventInPeriodName).getAttach();
		actID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE);
		assertEquals("test_addEvents : assertEquals: ", "20", actID);
		eventInPeriodName = (newSetOfEvents.getResourceAt(1).getID());
		//		 get the attach of the event
		eventAttach = (EventAttach) newSetOfEvents.getResource(
				eventInPeriodName).getAttach();
		actID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE);
		assertEquals("test_addEvents : assertEquals: ", "33", actID);
		eventInPeriodName = (newSetOfEvents.getResourceAt(2).getID());
		//		 get the attach of the event
		//		 get the attach of the event
		eventAttach = (EventAttach) newSetOfEvents.getResource(
				eventInPeriodName).getAttach();
		actID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE);

		//		assertEquals("test_addEvents : ", "34", actID);
		//		eventInPeriodName = (newSetOfEvents.getResourceAt(3).getID());
		//		//		 get the attach of the event
		//		//		 get the attach of the event
		//		eventAttach = (EventAttach) newSetOfEvents.getResource(
		//				eventInPeriodName).getAttach();
		//		actID = DXToolsMethods.getToken(eventInPeriodName,
		//				DConst.TOKENSEPARATOR, TOKEN_RANGE);
		//		assertEquals("test_addEvents : ", "36", actID);
		//		eventInPeriodName = (newSetOfEvents.getResourceAt(4).getID());
		//		//		 get the attach of the event
		//		//		 get the attach of the event
		//		eventAttach = (EventAttach) newSetOfEvents.getResource(
		//				eventInPeriodName).getAttach();
		//		actID = DXToolsMethods.getToken(eventInPeriodName,
		//				DConst.TOKENSEPARATOR, TOKEN_RANGE);
		//		assertEquals("test_addEvents : ", "7", actID);
		//
		//		assertEquals("test_addEvents : assertEquals: ", 5, newSetOfEvents
		//				.size());
	}

} //end SetOfEventsTest
