package dTest.dInternal.dOptimizationTest;

import java.io.File;
import java.util.StringTokenizer;
import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dConstants.DConst;
import dInterface.DxTTableDoc;
import dInterface.dUtil.DxTools;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.dData.StandardCollection;
import dInternal.dData.dRooms.DxSetOfRooms;
import dInternal.dData.dRooms.DxSetOfSites;
import dInternal.dData.dStudents.SetOfStudents;
import dInternal.dOptimization.DxEvent;
import dInternal.dOptimization.SetOfEvents;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Period;
import dInternal.dUtil.DXToolsMethods;

public class SetOfEventsTest extends TestCase {

	private final int NO_ROOM_ASSIGNED = -1;

	private final int TOKEN_RANGE = 0;

	private final int TOKEN_RANGE1 = 1;

	private final int TOKEN_RANGE2 = 2;

	public SetOfEventsTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(SetOfEventsTest.class);
	} // end suite

	/**
	 * test the principal key of the first event of the set of events
	 */
	public void testEvents_5j() {
		StringBuffer fileName = new StringBuffer("." + File.separator);
		fileName.append("dataTest" + File.separator);
		fileName.append("loadData5j.dia");
		try {
			DModel _dmData5j = new DModel(new DxTTableDoc(), fileName
					.toString());

			String pincKey;
			SetOfEvents soe = _dmData5j.getSetOfEvents();

			pincKey = ((DxEvent) soe.getResourceAt(0).getAttach())
					.getPrincipalRescKey();

			StringTokenizer keys = new StringTokenizer(pincKey, ".");
			String firstEvent = _dmData5j.getSetOfActivities()
					.getUnityCompleteName(Long.parseLong(keys.nextToken()),
							Long.parseLong(keys.nextToken()),
							Long.parseLong(keys.nextToken()),
							Long.parseLong(keys.nextToken()));
			assertEquals("testFirstEvent : ", "AMC640.1.01.1.", firstEvent);
			long insKey[];

			insKey = ((DxEvent) soe.getResourceAt(0).getAttach())
					.getInstructorKey();

			assertEquals("test_InstructorInEvent : ", "THÉRIEN, NORMAND",
					_dmData5j.getDxSetOfInstructors().getInstructorName(
							insKey[0]));

			DxSetOfSites sos = _dmData5j.getDxSetOfSites();
			DxSetOfRooms sor = sos.getAllDxRooms();
			assertEquals("test_RoomInEvent : siteCount1  ", 1, sos
					.getSiteCount());
			assertEquals("test_RoomInEvent : catCount 1", 1, sos
					.getCatCount(sos.getSiteKey(DConst.ROOM_DEFAULT_SITE)));
			assertEquals("test_RoomInEvent : ", true, sor.getRoomsNameSorted()
					.contains("D73020"));

			DxEvent event = (DxEvent) _dmData5j.getSetOfEvents().getResourceAt(
					0).getAttach();
			assertEquals("test_capacityOfAnEvent 0 : ", 17, event
					.getCapacityLimit());

			event = (DxEvent) _dmData5j.getSetOfEvents().getResourceAt(1)
					.getAttach();
			assertEquals("test_capacityOfAnEvent 1: ", 52, event
					.getCapacityLimit());
			event = (DxEvent) _dmData5j.getSetOfEvents().getResourceAt(2)
					.getAttach();
			assertEquals("test_capacityOfAnEvent 2: ", 33, event
					.getCapacityLimit());
		} catch (Exception e) {
			// Should not fail in tests
			System.out.println("Exception in: testEvents_5j");
			e.printStackTrace();
		}
	}

	/**
	 * test the principal key of the first event of the set of events
	 */
	public void test_addEvents_7j() {
		StringBuffer fileName = new StringBuffer("." + File.separator);
		fileName.append("dataTest" + File.separator);
		fileName.append("assignRooms.dia");
		try {
			DModel dmData7j = new DModel(new DxTTableDoc(), fileName.toString());
			SetOfEvents soe = dmData7j.getSetOfEvents();
			Cycle cycle = dmData7j.getTTStructure().getCurrentCycle();
			cycle.setCurrentDaySeqPerIndex(0, 0, 0);
			Period currentPeriod = cycle.getNextPeriod(1);
			DSetOfResources newSetOfEvents = new StandardCollection();
			// Vector contains the events (Resources) in the currentPeriod
			Vector<DResource> eventsInPeriod = currentPeriod
					.getEventsInPeriod().getSetOfResources();
			int index;
			// for each event in period do some tests
			// get the name of an event in the period
			// this is the : AMC600 Event
			String eventInPeriodName = eventsInPeriod.get(0).getID();
			// get the attach of the event

			DxEvent dxEvent = (DxEvent) soe.getResource(eventInPeriodName)
					.getAttach();
			String actID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE);
			String typeID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE1);
			String secID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE2);

			int section = DxTools.STIConvertGroupToInt(secID);
			SetOfStudents students = dmData7j.getSetOfStudents();
			Vector<String> v = students.getStudentsByGroup(actID, typeID,
					section, 0);
			DResource resc = new DResource(Integer.toString(v.size()), dxEvent);
			index = newSetOfEvents.searchWhereToInsert(Integer.toString(v
					.size()));
			assertEquals("test_addEvents AMC600 Event i : ", 0, index);
			assertEquals("test_addEvents AMC600 Event nS : ", 20, v.size());
			if (dxEvent.getRoomKey() == NO_ROOM_ASSIGNED) {
				newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
			}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)
			// for each event in period do some tests
			// get next, get the name of an event in the period
			// this is the AMC640 Event
			eventInPeriodName = eventsInPeriod.get(1).getID();
			// get the attach of the event
			dxEvent = (DxEvent) soe.getResource(eventInPeriodName).getAttach();
			actID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE);
			typeID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE1);
			secID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE2);
			section = DxTools.STIConvertGroupToInt(secID);
			v = students.getStudentsByGroup(actID, typeID, section, 0);
			resc = new DResource(Integer.toString(v.size()), dxEvent);
			index = newSetOfEvents.searchWhereToInsert(Integer.toString(v
					.size()));
			assertEquals("test_addEvents AMC640 Event i :", 1, index);
			if (dxEvent.getRoomKey() == NO_ROOM_ASSIGNED) {
				newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
			}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)

			// for each event in period do some tests
			// get next, get the name of an event in the period
			// this is the GCH109 Event
			eventInPeriodName = eventsInPeriod.get(2).getID();
			// get the attach of the event
			dxEvent = (DxEvent) soe.getResource(eventInPeriodName).getAttach();
			actID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE);
			typeID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE1);
			secID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE2);
			section = DxTools.STIConvertGroupToInt(secID);
			v = students.getStudentsByGroup(actID, typeID, section, 0);
			resc = new DResource(Integer.toString(v.size()), dxEvent);
			index = newSetOfEvents.searchWhereToInsert(Integer.toString(v
					.size()));
			assertEquals("test_addEvents GCH109 Event i :", 1, index);
			if (dxEvent.getRoomKey() == NO_ROOM_ASSIGNED) {
				newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
			}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)
			newSetOfEvents.sortSetOfResourcesByID();
			assertEquals("test_addEvents GCH109 Event size :", 3,
					newSetOfEvents.size());

			// for each event in period do some tests
			// get next, get the name of an event in the period
			// this is the GCH321 Event
			eventInPeriodName = eventsInPeriod.get(3).getID();
			// get the attach of the event
			dxEvent = (DxEvent) soe.getResource(eventInPeriodName).getAttach();
			actID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE);
			typeID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE1);
			secID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE2);
			section = DxTools.STIConvertGroupToInt(secID);
			v = students.getStudentsByGroup(actID, typeID, section, 0);
			resc = new DResource(Integer.toString(v.size()), dxEvent);
			index = newSetOfEvents.searchWhereToInsert(Integer.toString(v
					.size()));
			assertEquals("test_addEvents GCH321 Event i :", 1, index);
			if (dxEvent.getRoomKey() == NO_ROOM_ASSIGNED) {
				newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
			}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)
			newSetOfEvents.sortSetOfResourcesByID();
			assertEquals("test_addEvents GCH321 Event size :", 4,
					newSetOfEvents.size());

			// for each event in period do some tests
			// get next, get the name of an event in the period
			// this is the GEI460 Event
			eventInPeriodName = eventsInPeriod.get(4).getID();
			// get the attach of the event
			dxEvent = (DxEvent) soe.getResource(eventInPeriodName).getAttach();
			actID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE);
			typeID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE1);
			secID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE2);
			section = DxTools.STIConvertGroupToInt(secID);
			v = students.getStudentsByGroup(actID, typeID, section, 0);
			resc = new DResource(Integer.toString(v.size()), dxEvent);
			index = newSetOfEvents.searchWhereToInsert(Integer.toString(v
					.size()));
			if (dxEvent.getRoomKey() == NO_ROOM_ASSIGNED) {
				newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
			}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)
			newSetOfEvents.sortSetOfResourcesByID();
			assertEquals("test_addEvents GEI460 Event size :", 5,
					newSetOfEvents.size());

			// for each event in period do some tests
			// get next, get the name of an event in the period
			// this is the GEL440 Event
			eventInPeriodName = eventsInPeriod.get(5).getID();
			// get the attach of the event
			dxEvent = (DxEvent) soe.getResource(eventInPeriodName).getAttach();
			actID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE);
			typeID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE1);
			secID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE2);
			section = DxTools.STIConvertGroupToInt(secID);
			v = students.getStudentsByGroup(actID, typeID, section, 0);
			resc = new DResource(Integer.toString(v.size()), dxEvent);
			index = newSetOfEvents.searchWhereToInsert(Integer.toString(v
					.size()));
			assertEquals("test_addEvents GEL440 Event i :", 3, index);
			if (dxEvent.getRoomKey() == NO_ROOM_ASSIGNED) {
				newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
			}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)
			newSetOfEvents.sortSetOfResourcesByID();
			assertEquals("test_addEvents GEL440 Event size :", 6,
					newSetOfEvents.size());

			// for each event in period do some tests
			// get next, get the name of an event in the period
			// this is the IMC111 Event
			eventInPeriodName = eventsInPeriod.get(6).getID();
			// get the attach of the event
			dxEvent = (DxEvent) soe.getResource(eventInPeriodName).getAttach();
			actID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE);
			typeID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE1);
			secID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE2);
			section = DxTools.STIConvertGroupToInt(secID);
			v = students.getStudentsByGroup(actID, typeID, section, 0);
			resc = new DResource(Integer.toString(v.size()), dxEvent);
			index = newSetOfEvents.searchWhereToInsert(Integer.toString(v
					.size()));
			assertEquals("test_addEvents IMC111 Event i :", 5, index);
			if (dxEvent.getRoomKey() == NO_ROOM_ASSIGNED) {
				newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
			}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)
			newSetOfEvents.sortSetOfResourcesByID();
			assertEquals("test_addEvents IMC111 Event size :", 6,
					newSetOfEvents.size());

			// for each event in period do some tests
			// get next, get the name of an event in the period
			// this is the IMC455 Event
			eventInPeriodName = eventsInPeriod.get(7).getID();
			// get the attach of the event
			dxEvent = (DxEvent) soe.getResource(eventInPeriodName).getAttach();
			actID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE);
			typeID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE1);
			secID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE2);
			section = DxTools.STIConvertGroupToInt(secID);
			v = students.getStudentsByGroup(actID, typeID, section, 0);
			resc = new DResource(Integer.toString(v.size()), dxEvent);
			index = newSetOfEvents.searchWhereToInsert(Integer.toString(v
					.size()));
			assertEquals("test_addEvents IMC455 Event i : ", 0, index);
			assertEquals("test_addEvents IMC455 Event nS : ", 10, v.size());
			if (dxEvent.getRoomKey() == NO_ROOM_ASSIGNED) {
				newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
			}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)

			assertEquals("test_addEvents IMC455 Event size : ", 7,
					newSetOfEvents.size());

			// for each event in period do some tests
			// get next, get the name of an event in the period
			// this is the IMC500 Event
			eventInPeriodName = eventsInPeriod.get(8).getID();
			// get the attach of the event
			dxEvent = (DxEvent) soe.getResource(eventInPeriodName).getAttach();
			actID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE);
			typeID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE1);
			secID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE2);
			section = DxTools.STIConvertGroupToInt(secID);
			v = students.getStudentsByGroup(actID, typeID, section, 0);
			resc = new DResource(Integer.toString(v.size()), dxEvent);
			index = newSetOfEvents.searchWhereToInsert(Integer.toString(v
					.size()));
			assertEquals("test_addEvents IMC500 Event i : ", 6, index);
			if (dxEvent.getRoomKey() == NO_ROOM_ASSIGNED) {
				newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
			}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)
			newSetOfEvents.sortSetOfResourcesByID();
			assertEquals("test_addEvents IMC500 Event size : ", 7,
					newSetOfEvents.size());

		} catch (Exception e) {
			// Should not fail in tests
			System.out.println("Exception in: test_addEvents_7j()");
			e.printStackTrace();
		}
	}

} // end SetOfEventsTest
