/**
 * Created on Jun 16, 2006
 * 
 * 
 * Title: DxAssignAllAlg.java 
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

import developer.DxFlags;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DValue;
//import dInternal.DxConflictLimits;
import dInternal.dData.StandardCollection;
import dInternal.dTimeTable.Day;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.Sequence;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxAssignAllAlg is a class used to:
 * <p>
 * assign events to the timetable
 * <p>
 * 
 */
public class DxAssignAllAlg implements Algorithm {

	private Vector<DResource> _placedEvents;

	private DModel _dm;

//	private DxConflictLimits _dxCL;

	/**
	 * constructor
	 */
//	public DxAssignAllAlg(DModel dm, DxConflictLimits dxcl) {
//		_placedEvents = new Vector<DResource>();
//		_dm = dm;
//		_dxCL = dxcl;
//	}

	public DxAssignAllAlg(DModel dm) {
		_placedEvents = new Vector<DResource>();
		_dm = dm;
	}

	/**
	 * 
	 */
	public void doWork() {
		DResource currentEvent = null;
		Period currentPeriod = null;
		Vector<DResource> vPeriods;
		Vector<DResource> vNotYetAssignedEvents = getEvents();
		int currentDuration = 0;
		
		if (DxFlags.newPref) {			
			// aa
//		} else {
//			_dxCL.getMNumOfEventsInPeriod(); // to avoid warning
		}
		

	
		if (DxFlags.newAlg) {
			
			if (DxFlags.newPref) {			
				_dm.getConditionsToTest().extractParametersPref();
//			} else {
//				_dm.getConditionsToTest().extractDxPreference();
			}
		} else {
//			_dm.getConditionsToTest().extractPreference();
		}
		int[] nbConf;
		for (int i = 0; i < vNotYetAssignedEvents.size(); i++) {
			currentEvent = vNotYetAssignedEvents.get(i);
			boolean isNumberOfConflictsAcceptable = false;

			/*
			 * while(((EventAttach)currentEvent.getAttach()).getAssignState())
			 * currentEvent= (Resource)vectorOfEvents.remove(0);
			 */
				if (!((DxEvent) currentEvent.getAttach()).isAssigned()) {
					currentDuration = ((DxEvent) currentEvent.getAttach())
							.getDuration()
							/ _dm.getTTStructure().getPeriodLenght();
					vPeriods = buildSortContiguousPeriodVector(currentDuration, _dm
							.getConditionsToTest().getAvoidPriorityTable());
					while (!vPeriods.isEmpty()) {
						DValue value = (DValue) ( vPeriods.remove(0)).getAttach();
						currentPeriod = (Period) value.getObjectValue();
						int[] dayTime = { value.getIntValue(),
								currentPeriod.getBeginHour()[0],
								currentPeriod.getBeginHour()[1] };
//						((DxEvent) currentEvent.getAttach()).setKey(4, _dm
//								.getTTStructure().getCurrentCycle().getPeriod(
//										dayTime));
						((DxEvent) currentEvent.getAttach()).setPeriodKey(_dm
								.getTTStructure().getCurrentCycle().getPeriod(
										dayTime));
						((DxEvent) currentEvent.getAttach()).setAssigned(true);
						nbConf = _dm.getConditionsToTest().getEventConflictsInTTs(
								_dm.getTTStructure(), currentEvent, true);
						isNumberOfConflictsAcceptable = isConflictsAcceptable(nbConf);
						((DxEvent) currentEvent.getAttach()).setAssigned(false);
						if ((isNumberOfConflictsAcceptable)
								&& ((DxEvent) currentEvent.getAttach())
										.getDuration() != 0) {
							((DxEvent) currentEvent.getAttach())
									.setAssigned(true);
							_dm.getConditionsToTest().addEventInTTs(
									_dm.getTTStructure(), currentEvent, true);
							_placedEvents.add(currentEvent);
							vPeriods.removeAllElements();
						}// else{// end if if(numberOfConflicts==0)
					}// end while(!periodList.isEmpty())
				}// end
				// if(!((EventAttach)currentEvent.getAttach()).getAssignState())
				

		}// end for(int i=0; i< vect.size(); i++)
		// _dm.getConditionsTest().emptyAvoidPriorityTable();
		_dm.getSetOfEvents().updateActivities(_dm.getSetOfActivities(),
				_placedEvents);
		_dm.changeInDModel(null);
	}

	/**
	 * return events list to use by the algorithm
	 * 
	 * @return
	 */
	private Vector<DResource> getEvents() {
		return _dm.getSetOfEvents().getSetOfResources();
	}

	/**
	 * check if conflicts are acceptable
	 * 
	 * @param conflicts
	 * @return
	 */
	private boolean isConflictsAcceptable(int[] conflicts) {
		for (int i = 0; i < conflicts.length; i++) {
			if (_dm.getConditionsToTest().getAcceptableConflictsTable()[i] < conflicts[i])
				return false;
		}
		return true;
	}

	/**
	 * build Sort of Contiguous Period Vector
	 * 
	 * @return
	 */
	private Vector<DResource> buildSortContiguousPeriodVector(int duration,
			int[] avoidPriority) {
		DSetOfResources soresc = new StandardCollection();
		int counter = 1;
		for (int i = 0; i < _dm.getTTStructure().getCurrentCycle()
				.getSetOfDays().size(); i++) {
			DResource day = _dm.getTTStructure().getCurrentCycle()
					.getSetOfDays().getResourceAt(i);
			for (int j = 0; j < ((Day) day.getAttach()).getSetOfSequences()
					.size(); j++) {
				DResource seq = ((Day) day.getAttach()).getSetOfSequences()
						.getResourceAt(j);
				for (int k = 0; k < ((Sequence) seq.getAttach())
						.getSetOfPeriods().size(); k++) {
					DResource period = ((Sequence) seq.getAttach())
							.getSetOfPeriods().getResourceAt(k);
					Period per = (Period) period.getAttach();
					if (per.getEventsInPeriod().size() < _dm
							.getConditionsToTest().getPeriodAcceptableSize()) {
						if (_dm.getTTStructure().getCurrentCycle()
								.isPeriodContiguous(day.getKey(), seq.getKey(),
										period.getKey(), duration,
										avoidPriority, true)) {
							int number = 0;
							for (int l = 0; l < duration; l++) {
								number += ((Period) ((Sequence) seq.getAttach())
										.getSetOfPeriods().getResourceAt(k + l)
										.getAttach()).getEventsInPeriod()
										.size();
							}
							DValue value = new DValue();
							value.setIntValue((int) day.getKey());
							value.setObjectValue(per);
							soresc.setCurrentKey(number);
							soresc.addResource(new DResource(Integer
									.toString(counter++), value), 1);
						}// end if (_dm.getTTStructure()
					}// end if(per.getEventsInPeriod().size()<

				}// end for(int k=0; k<

			}// end for(int j=0; j< ((Day)day.getAttach()).getSetO
		}// end for (int i=0; i< _dm.getTTStructure().getCurrentCycle()
		soresc.sortSetOfResourcesByKey();
		return  (Vector<DResource>) soresc.getSetOfResources().clone();
	}

}// end class
