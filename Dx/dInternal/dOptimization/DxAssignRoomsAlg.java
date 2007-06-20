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
import dInterface.dUtil.DxTools;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DxConflictLimits;
import dInternal.dData.DxAvailability;
import dInternal.dData.DxResource;
import dInternal.dData.DxSetOfResources;
import dInternal.dData.StandardCollection;
import dInternal.dData.dRooms.DxCategory;
import dInternal.dData.dRooms.DxRoom;
import dInternal.dData.dRooms.DxSetOfCategories;
import dInternal.dData.dRooms.DxSetOfRooms;
import dInternal.dData.dRooms.DxSetOfSites;
import dInternal.dData.dStudents.SetOfStudents;
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

	private DModel _dm;

	private DxConflictLimits _dxCL;

	protected boolean _increase;

	/**
	 * 
	 * @param dm
	 * @param limits
	 */
	public DxAssignRoomsAlg(DModel dm, DxConflictLimits limits, boolean increase) {
		super();
		_dm = dm;
		_dxCL = limits;
		_increase = increase;
		_dxCL.getRoomBookingRate(); // the parameter how full is the room
	}

	/*
	 * this method executes the algorithm
	 */
	public void doWork() {
		// _dm.getSetOfEvents().auxPrintEvents("./aa.txt");
		// _dm.getDxSetOfRooms().auxPrintRooms("./bb.txt");

		Cycle cycle = _dm.getTTStructure().getCurrentCycle();
		cycle.setCurrentDaySeqPerIndex(0, 0, 0);
		int hours = cycle.getMaxNumberOfPeriodsADay();
		int numberOfPeriods = cycle.getNumberOfPeriods();
		Vector<DResource> eventsToUpdate = new Vector<DResource>();
		int d = 0;
		int h = 0;
		int periodStep = 1;
		int i = 0;
		for (i = 0; i < numberOfPeriods; i++) {
			Period currentPeriod = cycle.getNextPeriod(periodStep);
			h = i % hours;
			d = i / hours;
			// get the events to update in this period and update them
			// System.out.println("Number of P " + i + " d: " + d + " h: " + h);
			eventsToUpdate = placeRoomsWithCat(currentPeriod, d, h);
			_dm.getSetOfEvents().updateActivities(_dm.getSetOfActivities(),
					eventsToUpdate);

			eventsToUpdate = doWorkInPeriodAll(currentPeriod, d, h);
			_dm.getSetOfEvents().updateActivities(_dm.getSetOfActivities(),
					eventsToUpdate);
		}// end for

	}

	@SuppressWarnings("boxing")
	protected Vector<DResource> placeRoomsWithCat(Period currentPeriod, int d,
			int h) {
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
			Vector<DxResource> roomsInCat = this.getAvailableRoomsInCat(
					listOfAvailableDxRooms, sCatName, currentPeriod, d, h);
			if (_increase) {
				sortSetByCapacitymM(roomsInCat);
			} else {
				sortSetByCapacityMm(roomsInCat);
			}

			eventsInCat = this.getEventsWithRoomNamed(eventsInPeriod, sCatName);
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
						((DxEvent) eventToAssign.getAttach())
								.setRoomKey(((Long) room.getKey()).toString());
						roomsInCat.remove(room);
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

		eventsInPeriod = this.builEventsWithStudentsInPeriod(currentPeriod);
		listOfAvailableDxRooms = this
				.buildListofAvailableDxRooms(eventsInPeriod);

		Vector<DxResource> allRooms = this.getAvailableRooms(
				listOfAvailableDxRooms, currentPeriod, d, h);

		if (_increase) {
			sortSetByCapacitymM(allRooms);
		} else {
			sortSetByCapacityMm(allRooms);
		}

		eventsToPlace = this.getEventsWithRoomNamed(eventsInPeriod, "------");
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
					allRooms.remove(room);
					break;
				} // if
			} // while
		} // while
		return eventsToUpdate;
	}

	protected DSetOfResources getEventsWithRoomNamed(DSetOfResources events,
			String name) {
		DSetOfResources eventsInCat = new StandardCollection();
		for (int i = 0; i < events.size(); i++) {

			DxEvent event = (DxEvent) events.getResourceAt(i).getAttach();
			if (event.getRoomName().equalsIgnoreCase(name)) {
				eventsInCat.addResourceUsingIDWithDuplicates(events
						.getResourceAt(i));
			}
		}
		if (_increase) {
			return eventsInCat;
		}
		DSetOfResources eventsToReturn = new StandardCollection();
		for (int j = eventsInCat.size() - 1; j > -1; j--) {
			DResource r = eventsInCat.getResourceAt(j);
			eventsInCat.removeResourceAt(j);
			eventsToReturn.addResource(r);
		}
		return eventsToReturn;
	}

	protected Vector<DxResource> getAvailableRoomsInCat(
			DxSetOfResources dxRooms, String catName, Period currentPeriod,
			int d, int h) {
		Vector<DxResource> roomsResult = new Vector<DxResource>();
		Iterator it = dxRooms.iterator();
		currentPeriod.getMatrixAvailability();
		while (it.hasNext()) {
			DxRoom dr = (DxRoom) it.next();
			if (0 == dr.getType().compareToIgnoreCase(catName)) {
				DxAvailability a = dr.getAvailability();
				if (1 == a.getPeriodAvailability(d, h)) {
					roomsResult.add(dr);
				}
			}
		}
		return roomsResult;
	}

	protected Vector<DxResource> getAvailableRooms(DxSetOfResources dxRooms,
			Period currentPeriod, int d, int h) {
		Vector<DxResource> roomsResult = new Vector<DxResource>();
		Iterator it = dxRooms.iterator();
		currentPeriod.getMatrixAvailability();
		while (it.hasNext()) {
			DxRoom dr = (DxRoom) it.next();
			DxAvailability a = dr.getAvailability();
			if (1 == a.getPeriodAvailability(d, h)) {
				roomsResult.add(dr);
			}
			// }
		}
		return roomsResult;
	}

	protected DSetOfResources builEventsWithStudentsInPeriod(
			Period currentPeriod) {
		final int TOKEN_RANGE1 = 1;

		final int TOKEN_RANGE2 = 2;
		// The container for the result
		DSetOfResources setOfEventsInPeriod = new StandardCollection();
		DSetOfResources eventsToReturn = new StandardCollection();
		// Vector contains the events (Ressources) in the currentPeriod
		Vector eventsInPeriod = currentPeriod.getEventsInPeriod()
				.getSetOfResources();
		// Get all events
		SetOfEvents soe = _dm.getSetOfEvents();
		SetOfStudents students = _dm.getSetOfStudents();
		int TOKEN_RANGE = 0;
		// int numberOfStudents;
		String eventInPeriodName = "";
		String actID = "";
		String typeID = "";
		String secID = "";
		Vector v = new Vector();
		DResource resc = null;
		int section = 0;
		DxEvent event = null;
		for (int i = 0; i < eventsInPeriod.size(); i++) {
			// get the name of each event in the period
			eventInPeriodName = ((DResource) eventsInPeriod.get(i)).getID();
			// get the attach of the event
			event = (DxEvent) soe.getResource(eventInPeriodName).getAttach();
			actID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE);
			typeID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE1);
			secID = DXToolsMethods.getToken(eventInPeriodName,
					DConst.TOKENSEPARATOR, TOKEN_RANGE2);
			section = DxTools.STIConvertGroupToInt(secID);

			v = students.getStudentsByGroup(actID, typeID, section, 0);
			resc = new DResource(Integer.toString(v.size()), event);

//			if (_increase) {
				setOfEventsInPeriod.addResourceUsingIDWithDuplicates(resc);
//		} // else {
//			setOfEventsInPeriod.addResourceUsingIDWithDuplicates(resc);
//			eventsToReturn = new StandardCollection();
//			for (int j = setOfEventsInPeriod.size() - 1; j > -1; j--) {
//				DResource r = setOfEventsInPeriod.getResourceAt(j);
//				setOfEventsInPeriod.removeResourceAt(j);
//				eventsToReturn.addResource(r);
//			}
//			// return eventsToReturn;
//			 }
			// if(INCREASE) {
			// setOfEventsInPeriod.addResourceUsingIDWithDuplicates(resc);
			// }
			// DSetOfResources eventsToReturn = new StandardCollection();
			// for(int j = eventsInCat.size()-1 ; j > -1; j--) {
			// DResource r = eventsInCat.getResourceAt(j);
			// eventsInCat.removeResourceAt(j);
			// eventsToReturn.addResource(r);
			// }
			// return eventsToReturn;
		}// for(int i = 0; i< eventsInPeriod.size(); i++)
		if (_increase) {
			return setOfEventsInPeriod;
		}
		eventsToReturn = new StandardCollection();
		for (int j = setOfEventsInPeriod.size() - 1; j > -1; j--) {
			DResource r = setOfEventsInPeriod.getResourceAt(j);
			setOfEventsInPeriod.removeResourceAt(j);
			eventsToReturn.addResource(r);
		}
		return eventsToReturn;
//		return eventsToReturn;
	}

	// protected void setFlagsToSelectEvents() {
	// SetOfEvents soe = _dm.getSetOfEvents();
	// for (int i = 0; i < soe.size(); i++) {
	// DxEvent event = (DxEvent) soe.getResourceAt(i).getAttach();
	// // System.out.println("event " + i + DConst.CR_LF +
	// // event.toString());
	// if (event.isAssigned() && !event.isRoomFixed()) {
	// // il faut assigner le local
	// }// end if
	// else {
	// // il ne faut pas assigner de local
	// }// end else if
	// }// end for
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
	protected DxSetOfRooms buildListofAvailableDxRooms(
			DSetOfResources eventsInPeriod) {

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
	protected boolean isAddPossible(DxRoom room, DResource event) {
		if (room.getName().equalsIgnoreCase("......")) {
			return false;
		}
		int numberOfStudents = Integer.parseInt(event.getID());

		if (numberOfStudents <= room.getCapacity())
			return true;

		return false;
	}

	public Vector<DxResource> sortSetByCapacityMm(Vector<DxResource> rooms) {
		for (int i = 0; i < rooms.size() - 1; i++) {
			int maxPos = maximumPosition(rooms, i);
			swap(rooms, maxPos, i);
		}
		return rooms;
	}

	protected Vector<DxResource> sortSetByCapacitymM(Vector<DxResource> allRooms) {
		for (int i = 0; i < allRooms.size() - 1; i++) {
			int minPos = minimumPosition(allRooms, i);
			swap(allRooms, minPos, i);
		}
		return allRooms;
	}

	private int maximumPosition(Vector<DxResource> vr, int from) {
		int maxPos = from;
		for (int i = from + 1; i < vr.size(); i++) {
			DxRoom r = (DxRoom) vr.elementAt(i);
			DxRoom m = (DxRoom) vr.elementAt(maxPos);
			if (r.getCapacity() > m.getCapacity()) {
				maxPos = i;
			}
		}
		return maxPos;
	}

	private int minimumPosition(Vector<DxResource> vr, int from) {
		int minPos = from;
		for (int i = from + 1; i < vr.size(); i++) {
			DxRoom r = (DxRoom) vr.elementAt(i);
			DxRoom m = (DxRoom) vr.elementAt(minPos);
			if (r.getCapacity() < m.getCapacity()) {
				minPos = i;
			}
		}
		return minPos;
	}

	private void swap(Vector<DxResource> vr, int i, int j) {
		DxRoom temp = (DxRoom) vr.elementAt(i);
		vr.setElementAt(vr.elementAt(j), i);
		vr.setElementAt(temp, j);
	}

}// end class
