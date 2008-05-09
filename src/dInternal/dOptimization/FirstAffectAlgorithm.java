///**
// *
// * Title: FirstAffectAlgorithm $Revision: 1.30 $  $Date: 2007-07-24 14:04:41 $
// * Description: FirstAffectAlgorithm is a class used to
// *
// *
// * Copyright (c) 2001 by rgr.
// * All rights reserved.
// *
// *
// * This software is the confidential and proprietary information
// * of rgr. ("Confidential Information").  You
// * shall not disclose such Confidential Information and shall use
// * it only in accordance with the terms of the license agreement
// * you entered into with rgr.
// *
// * @version $Revision: 1.30 $
// * @author  $Author: gonzrubi $
// * @since JDK1.3
// */
//
//package dInternal.dOptimization;
//
//import java.util.Vector;
//
//import developer.DxFlags;
//import dInternal.DModel;
//import dInternal.DResource;
//import dInternal.DSetOfResources;
//import dInternal.dData.StandardCollection;
//import dInternal.dTimeTable.Day;
//import dInternal.dTimeTable.Period;
//import dInternal.dTimeTable.Sequence;
//import dInternal.DValue;
//
//public class FirstAffectAlgorithm implements Algorithm {
//	/**
//	 * @associates DResource
//	 */
//	private Vector<DResource> _placeEvent; // XXXX Pascal: Commentaire?
//
//	private DModel _dm;
//
//	/**
//	 * constructor
//	 */
//	public FirstAffectAlgorithm(DModel dm) {
//		_placeEvent = new Vector<DResource>();
//		_dm = dm;
//	}
//
//	/**
//	 * 
//	 * @param tts
//	 * @param vectorOfEvents
//	 */
//	public void doWork() {
//		DResource currentEvent;
//		Period currentPeriod;
//		Vector periodList;
//		Vector vect = buildEventsVector();
//		int currentDuration = 0;
//
//		// _dm.getConditionsTest().setAvoidPriorityTable(_avoidPriority);
//		// _dm.getConditionsTest().setacceptableConflictsTable(_acceptableConflictsTable);
//		if (DxFlags.newAlg) {
//			_dm.getConditionsToTest().extractDxPreference();
//		} else {
//			_dm.getConditionsToTest().extractPreference();
//		}
//		
//
//		for (int i = 0; i < vect.size(); i++) {
//			currentEvent = (DResource) vect.get(i);
//			boolean isNumberOfConflictsAccept = false;
//			int[] nbConf;
//			/*
//			 * while(((EventAttach)currentEvent.getAttach()).getAssignState())
//			 * currentEvent= (Resource)vectorOfEvents.remove(0);
//			 */
//			if (!((EventDx) currentEvent.getAttach()).isAssigned()) {
//				currentDuration = ((EventDx) currentEvent.getAttach())
//						.getDuration()
//						/ _dm.getTTStructure().getPeriodLenght();
//				periodList = buildSortContiguousPeriodVector(currentDuration,
//						_dm.getConditionsToTest().getAvoidPriorityTable());
//				while (!periodList.isEmpty()) {
//					DValue value = (DValue) ((DResource) periodList.remove(0))
//							.getAttach();
//					currentPeriod = (Period) value.getObjectValue();
//					int[] dayTime = { value.getIntValue(),
//							currentPeriod.getBeginHour()[0],
//							currentPeriod.getBeginHour()[1] };
//					((EventDx) currentEvent.getAttach()).setKey(4, _dm
//							.getTTStructure().getCurrentCycle().getPeriod(
//									dayTime));
//					((EventDx) currentEvent.getAttach()).setAssigned(true);
//					nbConf = _dm.getConditionsToTest().getEventConflictsInTTs(
//							_dm.getTTStructure(), currentEvent, true);
//					isNumberOfConflictsAccept = isConflictsAcceptable(nbConf);
//					((EventDx) currentEvent.getAttach()).setAssigned(false);
//					if ((isNumberOfConflictsAccept)
//							&& ((EventDx) currentEvent.getAttach())
//									.getDuration() != 0) {
//						((EventDx) currentEvent.getAttach())
//								.setAssigned(true);
//						_dm.getConditionsToTest().addEventInTTs(
//								_dm.getTTStructure(), currentEvent, true);
//						_placeEvent.add(currentEvent);
//						periodList.removeAllElements();
//					}// else{// end if if(numberOfConflicts==0)
//				}// end while(!periodList.isEmpty())
//			}// end
//				// if(!((EventAttach)currentEvent.getAttach()).getAssignState())
//		}// end for(int i=0; i< vect.size(); i++)
//		// _dm.getConditionsTest().emptyAvoidPriorityTable();
//		_dm.getSetOfEvents().updateActivities(_dm.getSetOfActivities(),
//				_placeEvent);
//		_dm.changeInDModel(null);
//	}
//
//	/**
//	 * return events list to use by the algorithm
//	 * 
//	 * @return
//	 */
//	private Vector buildEventsVector() {
//		return _dm.getSetOfEvents().getSetOfResources();
//	}
//
//	/**
//	 * check if conflicts are acceptable
//	 * 
//	 * @param conflicts
//	 * @return
//	 */
//	private boolean isConflictsAcceptable(int[] conflicts) {
//		for (int i = 0; i < conflicts.length; i++) {
//			if (_dm.getConditionsToTest().getAcceptableConflictsTable()[i] < conflicts[i])
//				return false;
//		}
//		return true;
//	}
//
//	/**
//	 * build Sort of Contiguous Period Vector
//	 * 
//	 * @return
//	 */
//	private Vector buildSortContiguousPeriodVector(int duration,
//			int[] avoidPriority) {
//		DSetOfResources soresc = new StandardCollection();
//		int counter = 1;
//		for (int i = 0; i < _dm.getTTStructure().getCurrentCycle()
//				.getSetOfDays().size(); i++) {
//			DResource day = _dm.getTTStructure().getCurrentCycle()
//					.getSetOfDays().getResourceAt(i);
//			for (int j = 0; j < ((Day) day.getAttach()).getSetOfSequences()
//					.size(); j++) {
//				DResource seq = ((Day) day.getAttach()).getSetOfSequences()
//						.getResourceAt(j);
//				for (int k = 0; k < ((Sequence) seq.getAttach())
//						.getSetOfPeriods().size(); k++) {
//					DResource period = ((Sequence) seq.getAttach())
//							.getSetOfPeriods().getResourceAt(k);
//					Period per = (Period) period.getAttach();
//					if (per.getEventsInPeriod().size() < _dm
//							.getConditionsToTest().getPeriodAcceptableSize()) {
//						if (_dm.getTTStructure().getCurrentCycle()
//								.isPeriodContiguous(day.getKey(), seq.getKey(),
//										period.getKey(), duration,
//										avoidPriority, true)) {
//							int number = 0;
//							for (int l = 0; l < duration; l++) {
//								number += ((Period) ((Sequence) seq.getAttach())
//										.getSetOfPeriods().getResourceAt(k + l)
//										.getAttach()).getEventsInPeriod()
//										.size();
//							}
//							DValue value = new DValue();
//							value.setIntValue((int) day.getKey());
//							value.setObjectValue(per);
//							soresc.setCurrentKey(number);
//							soresc.addResource(new DResource(Integer
//									.toString(counter++), value), 1);
//						}// end if (_dm.getTTStructure()
//					}// end if(per.getEventsInPeriod().size()<
//						// _dm.getConditionsTest().
//				}// end for(int k=0; k<
//					// ((Sequence)seq.getAttach()).getSetOfPeriod
//			}// end for(int j=0; j< ((Day)day.getAttach()).getSetO
//		}// end for (int i=0; i< _dm.getTTStructure().getCurrentCycle()
//		soresc.sortSetOfResourcesByKey();
//		return (Vector) soresc.getSetOfResources().clone();
//	}
//
//}// end class
