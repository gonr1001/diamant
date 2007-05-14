package dInternal.dOptimization;

import java.util.Vector;

import dConstants.DConst;
import dInterface.dUtil.DxTools;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.dData.StandardCollection;
import dInternal.dData.dRooms.Room;
import dInternal.dData.dRooms.SetOfRooms;
import dInternal.dData.dStudents.SetOfStudents;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Period;
import dInternal.dUtil.DXToolsMethods;

/**
 * @author syay1801
 * 
 * Cette classe implémente l'algorithme glouton, utilisé pour
 * <p>
 * résoudre le problème d'affectation de locaux
 */
public class RoomAssignmentAlgo implements Algorithm {

	private DModel _dm;

	private final int NO_ROOM_ASSIGNED = -1;

	private final int TOKEN_RANGE = 0;

	private final int TOKEN_RANGE1 = 1;

	private final int TOKEN_RANGE2 = 2;

	DResource _allRscFunct;

	int[] _conflictsPreference;

	/**
	 * Constructeur
	 */
	public RoomAssignmentAlgo(DModel dm) {
		super();
		_dm = dm;
		_allRscFunct = _dm.getSetOfRoomsFunctions().getResource(DConst.ALL);
		_conflictsPreference = _dm.getDxDocument().getDMediator()
				.getDApplication().getDxPreferences().getConflictLimits();
		setNoRoomToEventsWithRoomsNotFixed();
		doWork();
	}

	/*
	 * Cette méthode construit l'algorithme
	 */
	public void doWork() {
		int periodStep = 1;
		int sortRoomsByCapacity = 0;
		Cycle cycle = _dm.getTTStructure().getCurrentCycle();
		cycle.setCurrentDaySeqPerIndex(0, 0, 0);
		int numberOfPeriods = cycle.getNumberOfDays()
				* cycle.getMaxNumberOfPeriodsADay();
		Vector<DResource> eventsToUpdate = new Vector<DResource>();
		DSetOfResources setOfEventsToAssign = new StandardCollection();
		DSetOfResources setOfAvailableRooms;
		for (int i = 0; i < numberOfPeriods; i++) {
			Period currentPeriod = cycle.getNextPeriod(periodStep);
			setOfEventsToAssign = this.buildSetOfEvents(currentPeriod);
			setOfAvailableRooms = this.buildSetOfAvailableRooms(currentPeriod);
			setOfAvailableRooms
					.sortSetOfResourcesBySelectedAttachField(sortRoomsByCapacity);
			while (setOfEventsToAssign.size() > 0) {
				DResource eventsToAssign = setOfEventsToAssign.getResourceAt(0);
				eventsToUpdate.add(eventsToAssign);
				setOfEventsToAssign.removeResourceAt(0);
				for (int k = 0; k < setOfAvailableRooms.size(); k++) {
					Room room = (Room) setOfAvailableRooms.getResourceAt(k);
					if (isAddPossible(room, eventsToAssign)) {
						((EventDx) eventsToAssign.getAttach())
								.setRoomKey((int) room.getKey());
						setOfAvailableRooms.removeResource(room.getKey());
						break;
					}
				}// end for(int k= 0; k < sor.size();k++)
			}// end while
		}// end for

		_dm.getSetOfEvents().updateActivities(_dm.getSetOfActivities(),
				eventsToUpdate);
	}

	/**
	 * Construit l'ensemble fini des locaux pouvant être affectés
	 * 
	 * @param currentPeriod
	 *            la période dans laquelle il faut
	 *            <p>
	 *            affecter les locaux aux événements
	 * @return l'ensemble des locaux pouvant etre affectés
	 */
	private DSetOfResources buildSetOfAvailableRooms(Period currentPeriod) {
		DSetOfResources setOfAvailRooms = new StandardCollection();
		Vector eventsInPeriod = currentPeriod.getEventsInPeriod()
				.getSetOfResources();
		SetOfEvents soe = _dm.getSetOfEvents();
		SetOfRooms sor =_dm.getSetOfRooms();
		int ADD_RESOURCE_BY_KEY = 0;

			for (int i = 0; i < sor.size(); i++) {
				setOfAvailRooms.setCurrentKey(sor.getResourceAt(i).getKey());
				setOfAvailRooms.addResource(sor.getResourceAt(i),
						ADD_RESOURCE_BY_KEY);

		}
		for (int i = 0; i < eventsInPeriod.size(); i++) {
			String eventInPeriodName = ((DResource) eventsInPeriod.get(i))
					.getID();
			EventDx eventDx = (EventDx) soe.getResource(
					eventInPeriodName).getAttach();
			if (eventDx.getRoomKey() != NO_ROOM_ASSIGNED) {
				setOfAvailRooms.removeResource(eventDx.getRoomKey());
			}// end if(eventAttach.getRoomKey() != NO_ROOM_ASSIGNED)
		}// for(int i = 0; i< eventsInPeriod.size(); i++)
		return setOfAvailRooms;
	}

	/**
	 * cette classe initialise l'ensemble des événements
	 * <p>
	 * en annulant les locaux affectés aux événements s'ils n'y
	 * <p>
	 * sont pas figés
	 * 
	 */
	private void setNoRoomToEventsWithRoomsNotFixed() {
		SetOfEvents soe = _dm.getSetOfEvents();
		for (int i = 0; i < soe.size(); i++) {
			EventDx eventDx = (EventDx) soe.getResourceAt(i)
					.getAttach();
			if (eventDx.isAssigned() && !eventDx.isRoomFixed()) {
				eventDx.setRoomKey(NO_ROOM_ASSIGNED);
			}// end if
			else if (eventDx.isAssigned() && eventDx.isRoomFixed()
					&& eventDx.getRoomKey() == NO_ROOM_ASSIGNED) {
				eventDx.setRoomKey(NO_ROOM_ASSIGNED);
				eventDx.setRoomFixed(false);
			}// end else if
		}// end for 
	}

	/**
	 * Construit l'ensemble fini des événements auxquels on
	 * <p>
	 * souhaite associer des locaux
	 * 
	 * @param currentPeriod
	 *            la période dans laquelle il faut
	 *            <p>
	 *            affecter les locaux aux événements
	 * @return l'ensemble des événements
	 */
	private DSetOfResources buildSetOfEvents(Period currentPeriod) {
		// The container for the result
		DSetOfResources newSetOfEvents = new StandardCollection();
		// Vector contains the events (Ressources) in the currentPeriod
		Vector eventsInPeriod = currentPeriod.getEventsInPeriod()
				.getSetOfResources();
		// Get all events
		SetOfEvents soe = _dm.getSetOfEvents();
		SetOfStudents students = _dm.getSetOfStudents();
		String eventInPeriodName = "";
		String actID = "";
		String typeID = "";
		String secID = "";
		EventDx eventDx = null;
		int section = 0;
		Vector v = null;
		DResource resc = null;

		for (int i = 0; i < eventsInPeriod.size(); i++) {
			// get the name of an event in the period
			eventInPeriodName = ((DResource) eventsInPeriod.get(i)).getID();
			// get the attach of the event
			eventDx = (EventDx) soe.getResource(eventInPeriodName)
					.getAttach();
			if (eventDx.getRoomKey() == NO_ROOM_ASSIGNED) {
				actID = DXToolsMethods.getToken(eventInPeriodName,
						DConst.TOKENSEPARATOR, TOKEN_RANGE);
				typeID = DXToolsMethods.getToken(eventInPeriodName,
						DConst.TOKENSEPARATOR, TOKEN_RANGE1);
				secID = DXToolsMethods.getToken(eventInPeriodName,
						DConst.TOKENSEPARATOR, TOKEN_RANGE2);

				section = DxTools.STIConvertGroupToInt(secID);

				v = students.getStudentsByGroup(actID, typeID, section, 0);
				resc = new DResource(Integer.toString(v.size()), eventDx);

				newSetOfEvents.addResourceUsingIDWithDuplicates(resc);
				//				newSetOfEvents.addResourceUsingID(resc);
			}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)
		}// for(int i = 0; i< eventsInPeriod.size(); i++)
		return newSetOfEvents;
	}

	/**
	 * Elle teste si un couplet (local, evenement) peut être ajouté à une
	 * <p>
	 * solution partielle
	 * 
	 * @param Room
	 *            le local
	 * @param DResource
	 *            l'evenement
	 * @return boolean true si le couplet peut être ajouté à une solution
	 *         partielle
	 *         <p>
	 *         et false sinon.
	 */
	private boolean isAddPossible(Room room, DResource event) {
		int FILLFULL_RATE_INDEX = 6;
		int PERCENT = 100;
		int numberOfStudents = Integer.parseInt(event.getID());
		int needed_room_size = (numberOfStudents * PERCENT)
				/ _conflictsPreference[FILLFULL_RATE_INDEX];
		int needed_room_rest = (numberOfStudents * PERCENT)
				% _conflictsPreference[FILLFULL_RATE_INDEX];
		if (needed_room_rest > 0)
			needed_room_size += 1;
		if (_allRscFunct != null) {
			if (((EventDx) event.getAttach()).getRoomFunction() == _allRscFunct
					.getKey()) {
				if (needed_room_size <= room.getRoomCapacity())
					return true;
			} else if ((((EventDx) event.getAttach()).getRoomFunction() == room
					.getRoomFunction())) {
				if (needed_room_size <= room.getRoomCapacity())
					return true;
			}// end else
		}// end if(allRscFunct!= null)
		return false;
	}

} // end RoomAssignmentAlgo
