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
import dInterface.dUtil.DxTools;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.dData.StandardCollection;
import dInternal.dData.dStudents.SetOfStudents;
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

	private final int TOKEN_RANGE1 = 1;

	private final int TOKEN_RANGE2 = 2;

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
		assertEquals("test_firstEvent : ", "AMC640.1.01.1.", firstEvent);
	}

	/**
	 * test the instructor key of the first event of the setofevents
	 */
	public void test_InstructorInEvent() {
		long insKey[] = ((EventAttach) _soe.getResourceAt(0).getAttach())
				.getInstructorKey();
		assertEquals("test_InstructorInEvent : ", "THÉRIEN, NORMAND", _dm
				.getDxSetOfInstructors().getInstructorName(insKey[0]));

	}

	/**
	 * test the rooms key of the first event of the setofevents
	 */
	public void test_RoomInEvent() {
		long roomKey = ((EventAttach) _soe.getResourceAt(0).getAttach())
				.getRoomKey();
		if (DxFlags.newRooms) {
			// 22mars07
// assertEquals("test_RoomInEvent : ", "D73020", _dm.getDxSetOfRooms()
// .getRoomName(roomKey));
			assertEquals("test_RoomInEvent : ", "D73020", _dm.getDxSetOfSites().getResourceName(roomKey));
// .getRoomName(roomKey));
		} else {
			assertEquals("test_RoomInEvent : ", "D73020", _dm.getSetOfRooms()
					.getResource(roomKey).getID());
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

		int index;

		// for each event in period do some tests
		// get the name of an event in the period
		// this is the : AMC600 Event
		String eventInPeriodName = ((DResource) eventsInPeriod.get(0)).getID();
		// get the attach of the event
		EventAttach eventAttach = (EventAttach) _soe1.getResource(
				eventInPeriodName).getAttach();

		String actID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE);
		String typeID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE1);
		String secID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE2);

		int section = DxTools.STIConvertGroupToInt(secID);
		SetOfStudents students = _dm1.getSetOfStudents();
		Vector v = students.getStudentsByGroup(actID, typeID, section, 0);
		DResource resc = new DResource(Integer.toString(v.size()), eventAttach);
		index = newSetOfEvents.searchWhereToInsert(Integer.toString(v.size()));
		assertEquals("test_addEvents AMC600 Event i : ", 0, index);
		assertEquals("test_addEvents AMC600 Event nS : ", 20, v.size());
		if (eventAttach.getRoomKey() == NO_ROOM_ASSIGNED) {
			newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
		}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)

		// for each event in period do some tests
		// get next, get the name of an event in the period
		// this is the AMC640 Event
		eventInPeriodName = ((DResource) eventsInPeriod.get(1)).getID();
		// get the attach of the event
		eventAttach = (EventAttach) _soe1.getResource(eventInPeriodName)
				.getAttach();
		actID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE);
		typeID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE1);
		secID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE2);
		section = DxTools.STIConvertGroupToInt(secID);
		v = students.getStudentsByGroup(actID, typeID, section, 0);
		resc = new DResource(Integer.toString(v.size()), eventAttach);
		index = newSetOfEvents.searchWhereToInsert(Integer.toString(v.size()));
		assertEquals("test_addEvents AMC640 Event i :", 1, index);
		if (eventAttach.getRoomKey() == NO_ROOM_ASSIGNED) {
			newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
		}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)

		// for each event in period do some tests
		// get next, get the name of an event in the period
		// this is the GCH109 Event
		eventInPeriodName = ((DResource) eventsInPeriod.get(2)).getID();
		// get the attach of the event
		eventAttach = (EventAttach) _soe1.getResource(eventInPeriodName)
				.getAttach();
		actID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE);
		typeID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE1);
		secID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE2);
		section = DxTools.STIConvertGroupToInt(secID);
		v = students.getStudentsByGroup(actID, typeID, section, 0);
		resc = new DResource(Integer.toString(v.size()), eventAttach);
		index = newSetOfEvents.searchWhereToInsert(Integer.toString(v.size()));
		assertEquals("test_addEvents GCH109 Event i :", 1, index);
		if (eventAttach.getRoomKey() == NO_ROOM_ASSIGNED) {
			newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
		}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)
		newSetOfEvents.sortSetOfResourcesByID();
		assertEquals("test_addEvents GCH109 Event size :", 3, newSetOfEvents
				.size());

		// for each event in period do some tests
		// get next, get the name of an event in the period
		// this is the GCH321 Event
		eventInPeriodName = ((DResource) eventsInPeriod.get(3)).getID();
		// get the attach of the event
		eventAttach = (EventAttach) _soe1.getResource(eventInPeriodName)
				.getAttach();
		actID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE);
		typeID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE1);
		secID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE2);
		section = DxTools.STIConvertGroupToInt(secID);
		v = students.getStudentsByGroup(actID, typeID, section, 0);
		resc = new DResource(Integer.toString(v.size()), eventAttach);
		index = newSetOfEvents.searchWhereToInsert(Integer.toString(v.size()));
		assertEquals("test_addEvents GCH321 Event i :", 1, index);
		if (eventAttach.getRoomKey() == NO_ROOM_ASSIGNED) {
			newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
		}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)
		newSetOfEvents.sortSetOfResourcesByID();
		assertEquals("test_addEvents GCH321 Event size :", 4, newSetOfEvents
				.size());

		// for each event in period do some tests
		// get next, get the name of an event in the period
		// this is the GEI460 Event
		eventInPeriodName = ((DResource) eventsInPeriod.get(4)).getID();
		// get the attach of the event
		eventAttach = (EventAttach) _soe1.getResource(eventInPeriodName)
				.getAttach();
		actID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE);
		typeID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE1);
		secID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE2);
		section = DxTools.STIConvertGroupToInt(secID);
		v = students.getStudentsByGroup(actID, typeID, section, 0);
		resc = new DResource(Integer.toString(v.size()), eventAttach);
		index = newSetOfEvents.searchWhereToInsert(Integer.toString(v.size()));
		if (eventAttach.getRoomKey() == NO_ROOM_ASSIGNED) {
			newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
		}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)
		newSetOfEvents.sortSetOfResourcesByID();
		assertEquals("test_addEvents GEI460 Event size :", 5, newSetOfEvents
				.size());

		// for each event in period do some tests
		// get next, get the name of an event in the period
		// this is the GEL440 Event
		eventInPeriodName = ((DResource) eventsInPeriod.get(5)).getID();
		// get the attach of the event
		eventAttach = (EventAttach) _soe1.getResource(eventInPeriodName)
				.getAttach();
		actID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE);
		typeID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE1);
		secID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE2);
		section = DxTools.STIConvertGroupToInt(secID);
		v = students.getStudentsByGroup(actID, typeID, section, 0);
		resc = new DResource(Integer.toString(v.size()), eventAttach);
		index = newSetOfEvents.searchWhereToInsert(Integer.toString(v.size()));
		assertEquals("test_addEvents GEL440 Event i :", 3, index);
		if (eventAttach.getRoomKey() == NO_ROOM_ASSIGNED) {
			newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
		}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)
		newSetOfEvents.sortSetOfResourcesByID();
		assertEquals("test_addEvents GEL440 Event size :", 6, newSetOfEvents
				.size());

		// for each event in period do some tests
		// get next, get the name of an event in the period
		// this is the IMC111 Event
		eventInPeriodName = ((DResource) eventsInPeriod.get(6)).getID();
		// get the attach of the event
		eventAttach = (EventAttach) _soe1.getResource(eventInPeriodName)
				.getAttach();
		actID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE);
		typeID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE1);
		secID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE2);
		section = DxTools.STIConvertGroupToInt(secID);
		v = students.getStudentsByGroup(actID, typeID, section, 0);
		resc = new DResource(Integer.toString(v.size()), eventAttach);
		index = newSetOfEvents.searchWhereToInsert(Integer.toString(v.size()));
		assertEquals("test_addEvents IMC111 Event i :", 5, index);
		if (eventAttach.getRoomKey() == NO_ROOM_ASSIGNED) {
			newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
		}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)
		newSetOfEvents.sortSetOfResourcesByID();
		assertEquals("test_addEvents IMC111 Event size :", 6, newSetOfEvents
				.size());

		// for each event in period do some tests
		// get next, get the name of an event in the period
		// this is the IMC455 Event
		eventInPeriodName = ((DResource) eventsInPeriod.get(7)).getID();
		// get the attach of the event
		eventAttach = (EventAttach) _soe1.getResource(eventInPeriodName)
				.getAttach();
		actID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE);
		typeID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE1);
		secID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE2);
		section = DxTools.STIConvertGroupToInt(secID);
		v = students.getStudentsByGroup(actID, typeID, section, 0);
		resc = new DResource(Integer.toString(v.size()), eventAttach);
		index = newSetOfEvents.searchWhereToInsert(Integer.toString(v.size()));
		assertEquals("test_addEvents IMC455 Event i : ", 0, index);
		assertEquals("test_addEvents IMC455 Event nS : ", 10, v.size());
		if (eventAttach.getRoomKey() == NO_ROOM_ASSIGNED) {
			newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
		}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)

		assertEquals("test_addEvents IMC455 Event size : ", 7, newSetOfEvents
				.size());

		// for each event in period do some tests
		// get next, get the name of an event in the period
		// this is the IMC500 Event
		eventInPeriodName = ((DResource) eventsInPeriod.get(8)).getID();
		// get the attach of the event
		eventAttach = (EventAttach) _soe1.getResource(eventInPeriodName)
				.getAttach();
		actID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE);
		typeID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE1);
		secID = DXToolsMethods.getToken(eventInPeriodName,
				DConst.TOKENSEPARATOR, TOKEN_RANGE2);
		section = DxTools.STIConvertGroupToInt(secID);
		v = students.getStudentsByGroup(actID, typeID, section, 0);
		resc = new DResource(Integer.toString(v.size()), eventAttach);
		index = newSetOfEvents.searchWhereToInsert(Integer.toString(v.size()));
		assertEquals("test_addEvents IMC500 Event i : ", 6, index);
		if (eventAttach.getRoomKey() == NO_ROOM_ASSIGNED) {
			newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
		}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)
		newSetOfEvents.sortSetOfResourcesByID();
		assertEquals("test_addEvents IMC500 Event size : ", 7, newSetOfEvents
				.size());

	}

	/**
	 * test the rooms key of the first event of the setofevents
	 */
	public void test_capacityOfAnEvent() {
		EventAttach event = (EventAttach) _dm.getSetOfEvents().getResourceAt(0)
				.getAttach();
		assertEquals("test_capacityOfAnEvent 0 : ", 17, event
				.getCapacityLimit());

		event = (EventAttach) _dm.getSetOfEvents().getResourceAt(1).getAttach();
		assertEquals("test_capacityOfAnEvent 1: ", 52, event.getCapacityLimit());
		event = (EventAttach) _dm.getSetOfEvents().getResourceAt(2).getAttach();
		assertEquals("test_capacityOfAnEvent 2: ", 33, event.getCapacityLimit());
	}

} // end SetOfEventsTest
