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
		// TODO find out an equivalence for next line
		// _allRscFunct = _dm.getSetOfRoomsFunctions().getResource(DConst.ALL);
		_dm.getConditionsTest().extractDxPreference();
		_dxCL = limits;
		_dxCL.getRoomBookingRate();
	}

	/*
	 * this method executes the algorithm
	 */
	public void doWork() {

		setFlagsToSelectEvents();
		Cycle cycle = _dm.getTTStructure().getCurrentCycle();
		cycle.setCurrentDaySeqPerIndex(0, 0, 0);
		int numberOfPeriods = cycle.getNumberOfPeriods();
		Vector<DResource> eventsToUpdate = new Vector<DResource>();

		int periodStep = 1;
		int i = 0;
		for (i = 0; i < numberOfPeriods; i++) {
			Period currentPeriod = cycle.getNextPeriod(periodStep);
			// working in one period setOfEventsToAssign and setOfAvailable
			// rooms
			// for that period are known
			doWorkInPeriod(currentPeriod);

		}// end for

		_dm.getSetOfEvents().updateActivities(_dm.getSetOfActivities(),
				eventsToUpdate);
		// event.setRoomName("A4-265");
		// setOfEventsToAssign = this.buildSetOfEventsToAssign(currentPeriod);
	}

	private void doWorkInPeriod(Period currentPeriod) {

		DSetOfResources eventsWithStudentsInPeriod = new StandardCollection();
		DSetOfResources setOfEventsToAssign = new StandardCollection();
		DxSetOfResources setOfAvailableDxRooms = null;


		setOfAvailableDxRooms = this
				.buildSetOfAvailableDxRooms(eventsWithStudentsInPeriod);
		auxPrintEvents(setOfEventsToAssign);
		auxPrintRooms(setOfAvailableDxRooms);
		DxSetOfSites sos = _dm.getDxSetOfSites();
		String currentSiteName = _dm.getCurrentSiteName();
		DxSetOfCategories soc = sos.getSetOfCat(currentSiteName);
		
		eventsWithStudentsInPeriod = this.builEventsWithStudentsInPeriod(currentPeriod);		
		DxCategory[] cArray = soc.getCatsSortedByName();		
		for (int i = 1; i < soc.getCatCount(); i++){
			DxCategory cat = cArray[i];
			String sCatName = cat.getName();
			DxSetOfRooms sor = (DxSetOfRooms) soc.getSetOfRooms(sCatName);
			// events are new ordered by number of expected students  
			// get only events that are in cat
			
			// so the set of events and the set of rooms  must be new to make remove as a
			// room is taken corresponds to one cat
			// take an event try in a room test capacity and availbility of room for the period
			// if so then assign room and go for next
			// at end 
			// if not all events have a room events.size() give the conflic size

		}


	}

	private void auxPrintEvents(DSetOfResources setOfEventsToAssign) {
		System.out.println("begin events in period "
				+ setOfEventsToAssign.size());
		for (int i = 0; i < setOfEventsToAssign.size(); i++) {
			DResource dr = setOfEventsToAssign.getResourceAt(i);
			System.out.println(((DxEvent) dr.getAttach()).toString());
		}
		System.out.println("end events in period ");
	}

	private void auxPrintRooms(DxSetOfResources setOfAvailableDxRooms) {
		System.out.println("begin rooms in period "
				+ setOfAvailableDxRooms.size());
		Iterator i = setOfAvailableDxRooms.iterator();
		while (i.hasNext()) {
			DxRoom dr = (DxRoom) i.next();
			System.out.println(dr.toString());
		}
		System.out.println("end rooms in period ");
	}



	private DSetOfResources builEventsWithStudentsInPeriod(Period currentPeriod) {
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
			//setOfEventsInPeriod.addResource(resc, 0);
			setOfEventsInPeriod.addResourceUsingIDWithDuplicates(resc);
		}// for(int i = 0; i< eventsInPeriod.size(); i++)
		return setOfEventsInPeriod;
	}



	private void setFlagsToSelectEvents() {
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
	private DxSetOfRooms buildSetOfAvailableDxRooms(
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
	private void setNoRoomToEventsWithRoomsNotFixed() {
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

}// end class
