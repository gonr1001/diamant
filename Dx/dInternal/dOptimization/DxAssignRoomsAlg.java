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

import java.util.Vector;

import dConstants.DConst;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DxConflictLimits;
import dInternal.dData.DxSetOfResources;
import dInternal.dData.StandardCollection;
import dInternal.dData.dActivities.Activity;
import dInternal.dData.dRooms.DxRoom;
import dInternal.dData.dRooms.DxSetOfRooms;
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
		// TODO find out an equivalence for next line
		// _allRscFunct = _dm.getSetOfRoomsFunctions().getResource(DConst.ALL);
		//next line can be suppres i think rgr
		_dm.getConditionsTest().extractDxPreference();
		_dxCL = limits;
	}

	/*
	 * this method executes the algorithm
	 */
	public void doWork() {
		
		int periodStep = 1;
		setNoRoomToEventsWithRoomsNotFixed();
		Cycle cycle = _dm.getTTStructure().getCurrentCycle();
		cycle.setCurrentDaySeqPerIndex(0, 0, 0);
		int numberOfPeriods = cycle.getNumberOfDays()
				* cycle.getMaxNumberOfPeriodsADay();
		Vector<DResource> eventsToUpdate = new Vector<DResource>();
		DSetOfResources setOfEventsToAssign = new StandardCollection();
		DxSetOfResources setOfAvailableDxRooms;

		// for each period try to assign the free rooms
		for (int i = 0; i < numberOfPeriods; i++) {
			Period currentPeriod = cycle.getNextPeriod(periodStep);
			setOfEventsToAssign = this.buildSetOfEvents(currentPeriod);
			System.out.println("in for "+ setOfEventsToAssign.size());
//			if (DxFlags.newRooms) {
				// to be changed
				setOfAvailableDxRooms = this
						.buildSetOfAvailableDxRooms(currentPeriod);
				// TODO Find out equivalence
				// setOfAvailableDxRooms.
				// .sortSetOfResourcesBySelectedAttachField(sortRoomsByCapacity);
				while (setOfEventsToAssign.size() > 0) {
					System.out.println("while "+ setOfEventsToAssign.size() + ">0");
					DResource eventsToAssign = setOfEventsToAssign
							.getResourceAt(0);
					eventsToUpdate.add(eventsToAssign);
					setOfEventsToAssign.removeResourceAt(0);
//					for (int k = 0; k < setOfAvailableDxRooms.size(); k++) {
//						DxRoom room = (DxRoom) setOfAvailableDxRooms
//								.getResource(k);
//						if (isAddPossible(room, eventsToAssign)) {
// this does nothing
//					((DxEvent) eventsToAssign.getAttach())
//									.setRoomKey("11");//(int) room.getKey());
// This if the effective way to affect the room							
							((DxEvent) eventsToAssign.getAttach())
							.setRoomName("A4-265");
							//setOfAvailableDxRooms.removeResource(room.getKey());
							break;
//						}
//					}// end for(int k= 0; k < sor.size();k++)
				}// end while
//			}
			// } else {
			// setOfAvailableRooms = this
			// .buildSetOfAvailableRooms(currentPeriod);
			// setOfAvailableRooms
			// .sortSetOfResourcesBySelectedAttachField(sortRoomsByCapacity);
			// while (setOfEventsToAssign.size() > 0) {
			// DResource eventsToAssign = setOfEventsToAssign.getResourceAt(0);
			// eventsToUpdate.add(eventsToAssign);
			// setOfEventsToAssign.removeResourceAt(0);
			// for (int k = 0; k < setOfAvailableRooms.size(); k++) {
			// Room room = (Room) setOfAvailableRooms.getResourceAt(k);
			// if (isAddPossible(room, eventsToAssign)) {
			// ((EventAttach) eventsToAssign.getAttach())
			// .setRoomKey((int) room.getKey());
			// setOfAvailableRooms.removeResource(room.getKey());
			// break;
			// }
			// }// end for(int k= 0; k < sor.size();k++)
			// }// end while
			// }

		}// end for
		_dm.getSetOfEvents().updateActivities(_dm.getSetOfActivities(),
				eventsToUpdate);
		_dm.changeInDModel("hello");
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
	// private DSetOfResources buildSetOfAvailableRooms(Period currentPeriod) {
	// DSetOfResources setOfAvailRooms = new StandardCollection();
	// Vector eventsInPeriod = currentPeriod.getEventsInPeriod()
	// .getSetOfResources();
	// SetOfEvents soe = _dm.getSetOfEvents();
	// SetOfRooms sor = _dm.getSetOfRooms();
	//
	// int ADD_RESOURCE_BY_KEY = 0;
	//
	// for (int i = 0; i < sor.size(); i++) {
	// setOfAvailRooms.setCurrentKey(sor.getResourceAt(i).getKey());
	// setOfAvailRooms.addResource(sor.getResourceAt(i),
	// ADD_RESOURCE_BY_KEY);
	// }
	// for (int i = 0; i < eventsInPeriod.size(); i++) {
	// String eventInPeriodName = ((DResource) eventsInPeriod.get(i))
	// .getID();
	// EventAttach eventAttach = (EventAttach) soe.getResource(
	// eventInPeriodName).getAttach();
	// if (eventAttach.getRoomKey() != NO_ROOM_ASSIGNED) {
	// setOfAvailRooms.removeResource(eventAttach.getRoomKey());
	// }// end if(eventAttach.getRoomKey() != NO_ROOM_ASSIGNED)
	// }// for(int i = 0; i< eventsInPeriod.size(); i++)
	// return setOfAvailRooms;
	// }
	/**
	 * Construit l'ensemble fini des locaux pouvant être affectés
	 * 
	 * @param currentPeriod
	 *            la période dans laquelle il faut
	 *            <p>
	 *            affecter les locaux aux événements
	 * @return l'ensemble des locaux pouvant etre affectés
	 */
	private DxSetOfResources buildSetOfAvailableDxRooms(Period currentPeriod) {

		DxSetOfResources setOfAvailRooms = new DxSetOfRooms();
		Vector eventsInPeriod = currentPeriod.getEventsInPeriod()
				.getSetOfResources();
		SetOfEvents soe = _dm.getSetOfEvents();
		// SetOfRooms sor = _dm.getSetOfRooms();

		// int ADD_RESOURCE_BY_KEY = 0;
		//
		// for (int i = 0; i < sor.size(); i++) {
		// setOfAvailRooms.setCurrentKey(sor.getResourceAt(i).getKey());
		// setOfAvailRooms.addResource(sor.getResourceAt(i),
		// ADD_RESOURCE_BY_KEY);
		// }
		for (int i = 0; i < eventsInPeriod.size(); i++) {
			String eventInPeriodName = ((DResource) eventsInPeriod.get(i))
					.getID();
			DxEvent event = (DxEvent) soe.getResource(eventInPeriodName)
					.getAttach();
			if (event.getRoomKey() != NO_ROOM_ASSIGNED) {
				setOfAvailRooms.removeResource(event.getRoomKey());
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
			DxEvent event = (DxEvent) soe.getResourceAt(i).getAttach();
			System.out.println("event " + i + DConst.CR_LF + event.toString());				
			if (event.isAssigned() && !event.isRoomFixed()) {
				if (event.getRoomKey()== NO_ROOM_ASSIGNED)
					System.out.println("room key !event.isRoomFixed()" + event.getRoomKey() +" "+event.getRoomName());				
				event.setRoomKey(STR_NO_ROOM_ASSIGNED);
			}// end if
			else if (event.isAssigned() && event.isRoomFixed()
					&& event.getRoomKey() == NO_ROOM_ASSIGNED) {
				if (event.getRoomKey()== NO_ROOM_ASSIGNED)
					System.out.println("room key event.isRoomFixed()" + event.getRoomKey()+" "+event.getRoomName());
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
	private DSetOfResources buildSetOfEvents(Period currentPeriod) {
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
		int PERCENT = 100;
		int needed_room_size = 0;
		int needed_room_rest = 0;
		int numberOfStudents = Integer.parseInt(event.getID());

		needed_room_size = (numberOfStudents * PERCENT)
				/ _dxCL.getRoomBookingRate();
		needed_room_rest = (numberOfStudents * PERCENT)
				% _dxCL.getRoomBookingRate();

		if (needed_room_rest > 0)
			needed_room_size += 1;
//		 if (_allRscFunct != null) {
//		 if (((EventAttach) event.getAttach()).getRoomFunction() ==
//		 _allRscFunct
//		 .getKey()) {
//		 if (needed_room_size <= room.getCapacity())
//		 return true;
//		 }// end if
//		 else if ((((EventAttach) event.getAttach()).getRoomFunction() == room
//		 .getFunction())) {
//		 if (needed_room_size <= room.getCapacity())
//		 return true;
//		 }// end else
//		 }// end if(allRscFunct!= null)
		return false;
	}

	// private boolean isAddPossible(Room room, DResource event) {
	// int PERCENT = 100;
	// int needed_room_size = 0;
	// int needed_room_rest = 0;
	// int numberOfStudents = Integer.parseInt(event.getID());
	//
	// needed_room_size = (numberOfStudents * PERCENT)
	// / _dxCL.getRoomBookingRate();
	// needed_room_rest = (numberOfStudents * PERCENT)
	// % _dxCL.getRoomBookingRate();
	//
	// if (needed_room_rest > 0)
	// needed_room_size += 1;
	// if (_allRscFunct != null) {
	// if (((EventAttach) event.getAttach()).getRoomFunction() == _allRscFunct
	// .getKey()) {
	// if (needed_room_size <= room.getRoomCapacity())
	// return true;
	// }// end if
	// else if ((((EventAttach) event.getAttach()).getRoomFunction() == room
	// .getRoomFunction())) {
	// if (needed_room_size <= room.getRoomCapacity())
	// return true;
	// }// end else
	// }// end if(allRscFunct!= null)
	// return false;
	// }
}// end class
