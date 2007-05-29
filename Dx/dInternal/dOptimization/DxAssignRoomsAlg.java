/**
 * Created on Jun 16, 2006
 * 
 * 
 * Title: DxAssignRoomsAlg.java 
 *
 * Copyright (c) 2001 by rgr.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information
 * of rgr. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with rgr.
 *
 * 
 * 
 */
package dInternal.dOptimization;

import java.util.Iterator;
import java.util.Vector;

import dConstants.DConst;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DxConflictLimits;
import dInternal.dData.DxAvailability;
import dInternal.dData.DxSetOfResources;
import dInternal.dData.StandardCollection;
import dInternal.dData.dActivities.Activity;
import dInternal.dData.dRooms.DxCategory;
import dInternal.dData.dRooms.DxRoom;
import dInternal.dData.dRooms.DxSetOfCategories;
import dInternal.dData.dRooms.DxSetOfRooms;
import dInternal.dData.dRooms.DxSetOfSites;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Period;
import dInternal.dUtil.DXToolsMethods;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxAssignRoomsAlg is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
public class DxAssignRoomsAlg implements Algorithm {

	private final int NO_ROOM_ASSIGNED = -1;

	private final String STR_NO_ROOM_ASSIGNED = "-1";

	private DModel _dm;

	private DxConflictLimits _dxCL;

	/**
	 * 
	 * @param dm
	 * @param limits
	 */
	public DxAssignRoomsAlg(DModel dm, DxConflictLimits limits) {
		super();
		_dm = dm;
		_dxCL = limits;
		_dxCL.getRoomBookingRate(); // to be used as a parameter of filling the
		// room
	}

	/*
	 * this method executes the algorithm
	 */
	public void doWork() {

		setFlagsToSelectEvents(); // maybe can be eliminate it do nothing
		Cycle cycle = _dm.getTTStructure().getCurrentCycle();
		cycle.setCurrentDaySeqPerIndex(0, 0, 0);
		// int days = cycle.getNumberOfDays();
		int hours = cycle.getMaxNumberOfPeriodsADay();
		int numberOfPeriods = cycle.getNumberOfPeriods();
		Vector<DResource> eventsToUpdate = new Vector<DResource>();
		int d = 0;
		int h = 0;
		int periodStep = 1;
		int i = 0;
		for (i = 0; i < numberOfPeriods; i++) {
			Period currentPeriod = cycle.getNextPeriod(periodStep);
			h = i % hours; // that is ok
			d = i / hours;
			// get the events to update in this period and update them
			System.out.println("Number of P " + i + " d: " + d + " h: " + h);
			eventsToUpdate = doWorkInPeriodByCat(currentPeriod, d, h);
			_dm.getSetOfEvents().updateActivities(_dm.getSetOfActivities(),
					eventsToUpdate);
			eventsToUpdate = doWorkInPeriodAll(currentPeriod, d, h);
			_dm.getSetOfEvents().updateActivities(_dm.getSetOfActivities(),
					eventsToUpdate);
		}// end for

	}

	protected Vector<DResource> doWorkInPeriodByCat(Period currentPeriod,
			int d, int h) {
		Vector<DResource> eventsToUpdate = new Vector<DResource>();
		DSetOfResources eventsInPeriod = new StandardCollection();
		DSetOfResources eventsInCat = new StandardCollection();
		DxSetOfResources listOfAvailableDxRooms = null;

		DxSetOfSites sos = _dm.getDxSetOfSites();
		String currentSiteName = _dm.getCurrentSiteName();
		DxSetOfCategories soc = sos.getSetOfCat(currentSiteName);

		eventsInPeriod = this.builEventsWithStudentsInPeriod(currentPeriod);
		listOfAvailableDxRooms = this
				.buildListofAvailableDxRooms(eventsInPeriod);

		DxCategory[] cArray = soc.getCatsSortedByKey();
		for (int i = 0; i < cArray.length; i++) {
			DxCategory cat = cArray[i];
			String sCatName = cat.getName();
			DxSetOfRooms roomsInCat = this.getAvailableRoomsInCat(
					listOfAvailableDxRooms, sCatName, currentPeriod, d, h);
			eventsInCat = this.getEventsInCat(eventsInPeriod, sCatName);
			// get an event with the greater num of students
			while (eventsInCat.size() > 0) {
				DResource eventToAssign = eventsInCat.getResourceAt(0);
				eventsToUpdate.add(eventToAssign);
				eventsInCat.removeResourceAt(0);
				Iterator it = roomsInCat.iterator();
				while (it.hasNext()) {
					DxRoom room = (DxRoom) it.next();
					if (isAddPossible(room, eventToAssign)) {
						((DxEvent) eventToAssign.getAttach()).setRoomName(room
								.getName());
						roomsInCat.removeResource(room.getKey());
						break;
					} // if
				} // while
			} // while
		}
		return eventsToUpdate;
	}

	protected Vector<DResource> doWorkInPeriodAll(Period currentPeriod, int d,
			int h) {
		Vector<DResource> eventsToUpdate = new Vector<DResource>();
		DSetOfResources eventsInPeriod = new StandardCollection();
		DSetOfResources eventsToPlace = new StandardCollection();
		DxSetOfResources listOfAvailableDxRooms = null;

		//		DxSetOfSites sos = _dm.getDxSetOfSites();
		//		String currentSiteName = _dm.getCurrentSiteName();
		//		DxSetOfCategories soc = sos.getSetOfCat(currentSiteName);

		eventsInPeriod = this.builEventsWithStudentsInPeriod(currentPeriod);
		listOfAvailableDxRooms = this
				.buildListofAvailableDxRooms(eventsInPeriod);

		DxSetOfRooms allRooms = this.getAvailableRooms(listOfAvailableDxRooms,
				currentPeriod, d, h);
		eventsToPlace = this.getEventsInCat(eventsInPeriod, "------");
		// get an event with the greater num of students
		while (eventsToPlace.size() > 0) {
			DResource eventToAssign = eventsToPlace.getResourceAt(0);
			eventsToUpdate.add(eventToAssign);
			eventsToPlace.removeResourceAt(0);
			Iterator it = allRooms.iterator();
			while (it.hasNext()) {
				DxRoom room = (DxRoom) it.next();
				if (isAddPossible(room, eventToAssign)) {
					((DxEvent) eventToAssign.getAttach()).setRoomName(room
							.getName());
					allRooms.removeResource(room.getKey());
					break;
				} // if
			} // while
		} // while
		return eventsToUpdate;
	}

	// DxEvent e = (DxEvent) eventToAssign.getAttach();
	// e.setRoomName("A4-265");

	// DResource eventToAssign = eventsInCat.getResourceAt(0);
	// DxEvent e = (DxEvent) eventToAssign.getAttach();
	// e.setRoomName("A4-265");

	// while (setOfEventsToAssign.size() > 0) {
	// DResource eventToAssign = setOfEventsToAssign.getResourceAt(0);
	// eventsToUpdate.add(eventToAssign);
	// setOfEventsToAssign.removeResourceAt(0);
	// for (int k = 0; k < setOfAvailableRooms.size(); k++) {
	// Room room = (Room) setOfAvailableRooms.getResourceAt(k);
	// if (isAddPossible(room, eventToAssign)) {
	// ((EventDx) eventToAssign.getAttach())
	// .setRoomKey((int) room.getKey());
	// setOfAvailableRooms.removeResource(room.getKey());
	// break;
	// }
	// }// end for(int k= 0; k < sor.size();k++)
	// }// end while

	//          
	// room that are only available in the period
	// events that have a category last cat is the joker
	//			
	//						
	//			
	// // so the set of events and the set of rooms must be new to make remove
	// as a
	// // room is taken corresponds to one cat
	// // take an event try in a room test capacity and availbility of room for
	// the
	// period
	// // if so then assign room and go for next
	// // at end
	// // if not all events have a room events.size() give the conflic size

	protected DSetOfResources getEventsInCat(DSetOfResources events,
			String catName) {
		DSetOfResources eventsInCat = new StandardCollection();
		for (int i = 0; i < events.size(); i++) {

			DxEvent event = (DxEvent) events.getResourceAt(i).getAttach();
			if (event.getRoomName().equalsIgnoreCase(catName)) {
				eventsInCat.addResourceUsingIDWithDuplicates(events
						.getResourceAt(i));
			}
		}
		return eventsInCat;
	}

	protected DxSetOfRooms getAvailableRoomsInCat(DxSetOfResources dxRooms,
			String catName, Period currentPeriod, int d, int h) {
		DxSetOfRooms roomsResult = new DxSetOfRooms();
		Iterator it = dxRooms.iterator();
		currentPeriod.getMatrixAvailability();
		while (it.hasNext()) {
			DxRoom dr = (DxRoom) it.next();
			if (0 == dr.getType().compareToIgnoreCase(catName)) {
				DxAvailability a = dr.getAvailability();
				if (1 == a.getPeriodAvailability(d, h)) {
					roomsResult.addRoom(dr);
				}
			}
		}
		return roomsResult;
	}

	protected DxSetOfRooms getAvailableRooms(DxSetOfResources dxRooms,
			Period currentPeriod, int d, int h) {
		DxSetOfRooms roomsResult = new DxSetOfRooms();
		Iterator it = dxRooms.iterator();
		currentPeriod.getMatrixAvailability();
		while (it.hasNext()) {
			DxRoom dr = (DxRoom) it.next();
			//			if (0 == dr.getType().compareToIgnoreCase(catName)) {
			DxAvailability a = dr.getAvailability();
			if (1 == a.getPeriodAvailability(d, h)) {
				roomsResult.addRoom(dr);
			}
			//			}
		}
		return roomsResult;
	}

	// private void auxPrintEvents(DSetOfResources setOfEventsToAssign) {
	// System.out.println("begin events in period "
	// + setOfEventsToAssign.size());
	// for (int i = 0; i < setOfEventsToAssign.size(); i++) {
	// DResource dr = setOfEventsToAssign.getResourceAt(i);
	// System.out.println("Students " + Integer.parseInt(dr.getID()));
	// System.out.println(((DxEvent) dr.getAttach()).toString());
	// }
	// System.out.println("end events in period ");
	// }
	//
	// private void auxPrintRooms(DxSetOfResources setOfAvailableDxRooms) {
	// System.out.println("begin rooms in period "
	// + setOfAvailableDxRooms.size());
	// Iterator i = setOfAvailableDxRooms.iterator();
	// while (i.hasNext()) {
	// DxRoom dr = (DxRoom) i.next();
	// System.out.println(dr.toString());
	// }
	// System.out.println("end rooms in period ");
	// }

	protected DSetOfResources builEventsWithStudentsInPeriod(
			Period currentPeriod) {
		// The container for the result
		DSetOfResources setOfEventsInPeriod = new StandardCollection();
		// Vector contains the events (Ressources) in the currentPeriod
		Vector eventsInPeriod = currentPeriod.getEventsInPeriod()
				.getSetOfResources();
		// Get all events
		SetOfEvents soe = _dm.getSetOfEvents();
		int TOKEN_RANGE = 0;
		int numberOfStudents;
		for (int i = 0; i < eventsInPeriod.size(); i++) {
			// get the name of each event in the period
			String eventInPeriodName = ((DResource) eventsInPeriod.get(i))
					.getID();
			// get the attach of the event
			DxEvent event = (DxEvent) soe.getResource(eventInPeriodName)
					.getAttach();

			String actID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE);
			Activity activity = (Activity) _dm.getSetOfActivities()
					.getResource(actID).getAttach();
			numberOfStudents = activity.getStudentRegistered().size();
			DResource resc = new DResource(Integer.toString(numberOfStudents),
					event);
			setOfEventsInPeriod.addResourceUsingIDWithDuplicates(resc);
		}// for(int i = 0; i< eventsInPeriod.size(); i++)
		return setOfEventsInPeriod;
	}

	protected void setFlagsToSelectEvents() {
		SetOfEvents soe = _dm.getSetOfEvents();
		for (int i = 0; i < soe.size(); i++) {
			DxEvent event = (DxEvent) soe.getResourceAt(i).getAttach();
			// System.out.println("event " + i + DConst.CR_LF +
			// event.toString());
			if (event.isAssigned() && !event.isRoomFixed()) {
				// il faut assigner le local
			}// end if
			else {
				// il ne faut pas assigner de local
			}// end else if
		}// end for
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
	protected DxSetOfRooms buildListofAvailableDxRooms(
	/* Period currentPeriod, */DSetOfResources eventsInPeriod) {

		DxSetOfRooms setOfAvailableRooms = new DxSetOfRooms();
		DxSetOfSites dxSos = _dm.getDxSetOfSites();
		DxSetOfRooms setAllDxRooms = dxSos.getAllDxRooms();
		setOfAvailableRooms = setAllDxRooms.clone();
		for (int i = 0; i < eventsInPeriod.size(); i++) {
			DxEvent event = (DxEvent) eventsInPeriod.getResourceAt(i)
					.getAttach();
			setOfAvailableRooms.removeResource(event.getRoomKey());
		}
		return setOfAvailableRooms;
	}

	/**
	 * cette classe initialise l'ensemble des événements
	 * <p>
	 * en annulant les locaux affectés aux événements s'ils n'y
	 * <p>
	 * sont pas figés
	 * 
	 */
	protected void setNoRoomToEventsWithRoomsNotFixed() {
		SetOfEvents soe = _dm.getSetOfEvents();
		for (int i = 0; i < soe.size(); i++) {
			DxEvent event = (DxEvent) soe.getResourceAt(i).getAttach();
			System.out.println("event " + i + DConst.CR_LF + event.toString());
			if (event.isAssigned() && !event.isRoomFixed()) {
				if (event.getRoomKey() == NO_ROOM_ASSIGNED)
					System.out.println("room key !event.isRoomFixed()"
							+ event.getRoomKey() + " " + event.getRoomName());
				event.setRoomKey(STR_NO_ROOM_ASSIGNED);
			}// end if
			else if (event.isAssigned() && event.isRoomFixed()
					&& event.getRoomKey() == NO_ROOM_ASSIGNED) {
				if (event.getRoomKey() == NO_ROOM_ASSIGNED)
					System.out.println("room key event.isRoomFixed()"
							+ event.getRoomKey() + " " + event.getRoomName());
				event.setRoomKey(STR_NO_ROOM_ASSIGNED);
				event.setRoomFixed(false);
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
	protected DSetOfResources buildSetOfEvents(Period currentPeriod) {
		// The container for the result
		DSetOfResources newSetOfEvents = new StandardCollection();
		// Vector contains the events (Ressources) in the currentPeriod
		Vector eventsInPeriod = currentPeriod.getEventsInPeriod()
				.getSetOfResources();
		// Get all events
		SetOfEvents soe = _dm.getSetOfEvents();
		int TOKEN_RANGE = 0;
		int ADD_RESOURCE_BY_ID = 1;
		int numberOfStudents;
		for (int i = 0; i < eventsInPeriod.size(); i++) {
			// get the name of an event in the period
			String eventInPeriodName = ((DResource) eventsInPeriod.get(i))
					.getID();
			// get the attach of the event
			DxEvent event = (DxEvent) soe.getResource(eventInPeriodName)
					.getAttach();
			if (event.getRoomKey() == NO_ROOM_ASSIGNED) {
				String actID = DXToolsMethods.getToken(eventInPeriodName,
						DConst.TOKENSEPARATOR, TOKEN_RANGE);
				Activity activity = (Activity) _dm.getSetOfActivities()
						.getResource(actID).getAttach();
				numberOfStudents = activity.getStudentRegistered().size();
				DResource resc = new DResource(Integer
						.toString(numberOfStudents), event);
				newSetOfEvents.addResource(resc, ADD_RESOURCE_BY_ID);
			}// end if(eventAttach.getRoomKey() == NO_ROOM_ASSIGNED)
		}// for(int i = 0; i< eventsInPeriod.size(); i++)
		return newSetOfEvents;
	}

	protected int getNumberOfPeriods() {
		Cycle cycle = _dm.getTTStructure().getCurrentCycle();
		cycle.setCurrentDaySeqPerIndex(0, 0, 0);
		return cycle.getNumberOfPeriods();
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
	private boolean isAddPossible(DxRoom room, DResource event) {
		if (room.getName().equalsIgnoreCase("......")) {
			return false;
		}
		// int FILLFULL_RATE_INDEX = 6;
		// int PERCENT = 100;
		int numberOfStudents = Integer.parseInt(event.getID());
		
		if (numberOfStudents <= room.getCapacity())
			return true;
		
		return false;
	}
}// end class
