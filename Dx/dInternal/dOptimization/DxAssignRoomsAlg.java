/**
 * Created on June 16, 2006
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

	protected boolean _best;

	/**
	 * 
	 * @param dm
	 * @param limits
	 */
	public DxAssignRoomsAlg(DModel dm, DxConflictLimits limits,
			boolean increase, boolean best) {
		super();
		_dm = dm;
		_dxCL = limits;
		_increase = increase;
		_best = best;
		if (limits != null) {
			_dxCL.getRoomBookingRate();
		}
	}

	/*
	 * this method executes the algorithm
	 */
	public void doWork() {
		Cycle cycle = _dm.getTTStructure().getCurrentCycle();
		cycle.setCurrentDaySeqPerIndex(0, 0, 0);
		int hours = cycle.getMaxNumberOfPeriodsADay();
		int numberOfPeriods = cycle.getNumberOfPeriods();
		Vector<DResource> eventsToUpdate = new Vector<DResource>();
		int d = 0;
		int h = 0;
		int periodStep = 1;
		for (int i = 0; i < numberOfPeriods; i++) {
			Period currentPeriod = cycle.getNextPeriod(periodStep);
			h = i % hours;
			d = i / hours;
			// get the events to update in this period and update them
			eventsToUpdate = placeRoomsWithType(currentPeriod, d, h);
			_dm.getSetOfEvents().updateActivities(_dm.getSetOfActivities(),
					eventsToUpdate);

			eventsToUpdate = placeOtherRooms(currentPeriod, d, h);
			_dm.getSetOfEvents().updateActivities(_dm.getSetOfActivities(),
					eventsToUpdate);
		}// end for

	}

	@SuppressWarnings("boxing")
	protected Vector<DResource> placeRoomsWithType(Period currentPeriod, int d,
			int h) {
		Vector<DResource> result = new Vector<DResource>();
		Vector<DResource> eventsToUpdate = new Vector<DResource>();
		DxSetOfSites sos = _dm.getDxSetOfSites();
		String currentSiteName = _dm.getCurrentSiteName();
		DxSetOfCategories soc = sos.getSetOfCat(currentSiteName);

		DSetOfResources eventsInPeriod = this
				.builEventsWithStudentsInPeriod(currentPeriod);
		DxSetOfResources listOfAvailableDxRooms = this
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

			DSetOfResources eventsInCat = this.getEventsWithRoomNamed(
					eventsInPeriod, sCatName);

			result = placeEvents(/*eventsToUpdate,*/ roomsInCat,
					eventsInCat);
			for(int j = 0 ; j < result.size(); j++){
				eventsToUpdate.add(result.elementAt(j));
			}			
		} // for
		return eventsToUpdate;
	}

	protected Vector<DResource> placeOtherRooms(Period currentPeriod, int d,
			int h) {
	
		DSetOfResources eventsInPeriod = this
				.builEventsWithStudentsInPeriod(currentPeriod);
		DxSetOfResources listOfAvailableDxRooms = this
				.buildListofAvailableDxRooms(eventsInPeriod);
		Vector<DxResource> allRooms = this.getAvailableRooms(
				listOfAvailableDxRooms, currentPeriod, d, h);

		if (_increase) {
			sortSetByCapacitymM(allRooms);
		} else {
			sortSetByCapacityMm(allRooms);
		}
		DSetOfResources eventsToPlace = this.getEventsWithRoomNamed(
				eventsInPeriod, "------");
		return placeEvents(/*eventsToUpdate,*/ allRooms, eventsToPlace);
	}

	/**
	 * @param eventsToUpdate
	 * @param allRooms
	 * @param eventsToPlace
	 * @return
	 */
	private Vector<DResource> placeEvents(/*Vector<DResource> eventsToUpdate,*/
			Vector<DxResource> allRooms, DSetOfResources eventsToPlace) {
		Vector<DResource> eventsToUpdate = new Vector<DResource>();
		Vector<DxResource> myRooms = new Vector<DxResource>();
		while (eventsToPlace.size() > 0) {
			DResource eventToAssign = eventsToPlace.getResourceAt(0);
			eventsToUpdate.add(eventToAssign);
			eventsToPlace.removeResourceAt(0);
			Iterator<DxResource> it = allRooms.iterator();
			
			while (it.hasNext()) { // goes for all rooms
				if (!_best) {
					DxRoom room = (DxRoom) it.next();
					if (fitIn(eventToAssign, room)) {
						((DxEvent) eventToAssign.getAttach()).setRoomName(room
								.getName());
						allRooms.remove(room);
						break;
					} // if
				} else {
					DxRoom room = (DxRoom) it.next();
					if (fitIn(eventToAssign, room)) {
						myRooms.add(room);
					}
				}
			} // while
			if (myRooms.size() > 0) {
				DxRoom r = (DxRoom) myRooms.get(myRooms.size()-1);
				((DxEvent) eventToAssign.getAttach())
						.setRoomName(r.getName());
				((DxEvent) eventToAssign.getAttach())			
				.setRoomKeyWithKey(r.getKey());
				allRooms.remove(myRooms.size()-1);
				myRooms = new Vector<DxResource>();
			}
		} // while
		return eventsToUpdate;
	}

	protected DSetOfResources getEventsWithRoomNamed(DSetOfResources events,
			String name) {
		DSetOfResources eventsInCat = new StandardCollection();
		for (int i = 0; i < events.size(); i++) {

			DxEvent event = (DxEvent) events.getResourceAt(i).getAttach();
			if (event.getRoomName().equalsIgnoreCase(name)) {
				eventsInCat.addResourceUsingIDWithDuplicatesrgr(events
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
		Iterator<DxResource> it = dxRooms.iterator();
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
		Iterator<DxResource> it = dxRooms.iterator();
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
		// DSetOfResources eventsToReturn = new StandardCollection();
		// Vector contains the events (Resources) in the currentPeriod
		Vector<DResource> eventsInPeriod = currentPeriod.getEventsInPeriod()
				.getSetOfResources();
		// Get all events
		SetOfEvents soe = _dm.getSetOfEvents();
		SetOfStudents students = _dm.getSetOfStudents();
		int TOKEN_RANGE = 0;
		String eventInPeriodName = "";
		String actID = "";
		String typeID = "";
		String secID = "";
		Vector<String> v = new Vector<String>();
		DResource resc = null;
		int section = 0;
		DxEvent event = null;
		for (int i = 0; i < eventsInPeriod.size(); i++) {
			// get the name of each event in the period
			eventInPeriodName = eventsInPeriod.get(i).getID();
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

			setOfEventsInPeriod.addResourceUsingIDWithDuplicatesrgr(resc);

		}// for
		if (_increase) {
			return setOfEventsInPeriod;
		}
		return invert(setOfEventsInPeriod);
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
	 * @param setOfEventsInPeriod
	 * @return
	 */
	private DSetOfResources invert(DSetOfResources setOfEventsInPeriod) {
		DSetOfResources eventsToReturn;
		eventsToReturn = new StandardCollection();
		for (int j = setOfEventsInPeriod.size() - 1; j > -1; j--) {
			DResource r = setOfEventsInPeriod.getResourceAt(j);
			setOfEventsInPeriod.removeResourceAt(j);
			eventsToReturn.addResource(r);
		}
		return eventsToReturn;
	}

	protected boolean fitIn(DResource event, DxRoom room) {
		if (room.getName().equalsIgnoreCase("......")) {
			return false;
		}
		int numberOfStudents = Integer.parseInt(event.getID());

		if (numberOfStudents <= room.getCapacity())
			return true;

		return false;
	}

	protected Vector<DxResource> sortSetByCapacityMm(Vector<DxResource> allRooms) {
		for (int i = 0; i < allRooms.size() - 1; i++) {
			int maxPos = maximumPosition(allRooms, i);
			swap(allRooms, maxPos, i);
		}
		return allRooms;
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
