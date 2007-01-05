/**
 * Created on May 24, 2006
 * 
 * 
 * Title: DxConditionsToTest.java 
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

import dDeveloper.DxFlags;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.DxConflictLimits;
import dInternal.dTimeTable.Day;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.Sequence;
import dInternal.dTimeTable.TTStructure;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxConditionsToTest is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
public class DxConditionsToTest {

	private StudentsConflictsMatrix _matrix;

	private DModel _dm;

	private Vector<DxCondition> _conditionsToTest;

	private boolean _matrixIsBuilded;

	private int[] _avoidPriority;

	private int[] _acceptableConflictsTable;

	private int _periodAcceptableSize;

	private int _periodVariationEvents;

	/**
	 * Constructor
	 * 
	 * @param dm
	 * 
	 */
	public DxConditionsToTest(DModel dm) {
		_dm = dm;
		_matrixIsBuilded = false;
		_avoidPriority = new int[] { 1, 2 };
		_acceptableConflictsTable = new int[] { 0, 0, 0 };
		_periodAcceptableSize = 20;
		_periodVariationEvents = 0;

		_conditionsToTest = new Vector<DxCondition>();
		_matrix = new StudentsConflictsMatrix();
		_conditionsToTest.add(new DxStudentCondtionsToTest(_matrix, _dm
				.getSetOfActivities(), _dm.getTTStructure().getCurrentCycle()));
		_conditionsToTest.add(new DxInstructorsConditionsToTest(_dm));
		_conditionsToTest.add(new DxRoomsConditionsToTest(_dm));
	}

	public StudentsConflictsMatrix getConflictsMatrix() {
		return _matrix;
	}

	/**
	 * 
	 * @return
	 */
	public int[] getAvoidPriorityTable() {
		return _avoidPriority;
	}

	/**
	 * 
	 * @return
	 */
	public int getPeriodAcceptableSize() {
		return _periodAcceptableSize;
	}

	/**
	 * 
	 * @return int[] acceptableConflictsTable range 0= student, range 1=
	 *         instructor range 2= room
	 */
	public int[] getAcceptableConflictsTable() {
		return _acceptableConflictsTable;
	}

	/**
	 * 
	 */
	public void buildStudentConflictMatrix() {
		if (!_matrixIsBuilded) {
			_matrix.buildMatrix(_dm);
			_matrixIsBuilded = true;
		}
	}

	/**
	 * set the _matrixIsBuilded value to true
	 */
	public void setMatrixBuilded(boolean value, boolean doFirstGroupAssignement) {
		_matrixIsBuilded = value;
		if (doFirstGroupAssignement)
			_matrix.doFirstGroupAssignement();
	}

	/**
	 * 
	 */
	public void initAllConditions() {
		buildStudentConflictMatrix();
		if (DxFlags.newAlg) {
			extractDxPreference();
		} else {
			extractPreference();
		}
		buildAllConditions(_dm.getTTStructure());
	}

	/**
	 * buildAllConditions all assigned events in SetOfEvents are placed in the
	 * corresponding period in the ttStructure further more, if an event has an
	 * invalid property according with the ttStructure, the SetOfActivities will
	 * be updated.
	 * 
	 * @param tts
	 *            <p>
	 *            a TTStructure where periods are updated.
	 *            </p>
	 *            <p>
	 *            TTStructure could be modified
	 *            </p>
	 *            <p>
	 *            an Event in the SetOfEvents could be modified
	 *            (_dm.getSetOfEvents)
	 *            </p>
	 *            <p>
	 *            an Activity in the SetOfActivities could be modified
	 *            (_dm.getSetOfActivities)
	 *            </p>
	 */
	public void buildAllConditions(TTStructure tts) {
		if (_matrixIsBuilded) {
			tts.getCurrentCycle().emptyAllEventsInPeriod();

			for (int i = 0; i < _dm.getSetOfEvents().size(); i++) {
				DResource event = _dm.getSetOfEvents().getResourceAt(i);
				addEventInTTs(tts, event, false);
			}// end for (int i=0; i< _dm.getSetOfEvents().size(); i++)
			_dm.getSetOfEvents().updateActivities(_dm.getSetOfActivities(),
					_dm.getSetOfEvents().getSetOfResources());
		}
	}

	public int[] addEventInTTs(TTStructure tts, DResource event,
			boolean usePriority) {
		// return addOrRemoveOrGetConflictsEventInTTs(tts, event, 1,
		// usePriority);
		int[] numberOfConflicts = { 0, 0, 0 };

		if (((EventAttach) event.getAttach()).isAssigned()) {
			int[] perKey = ((EventAttach) event.getAttach())
					.getPeriodKeyTable();
			int duration = ((EventAttach) event.getAttach()).getDuration()
					/ tts.getPeriodLenght();

			if ((tts.getCurrentCycle().isPeriodContiguous(perKey[0], perKey[1],
					perKey[2], duration, _avoidPriority, usePriority))
					&& (duration > 0)) {

				for (int j = 0; j < duration; j++) {
					Period per = tts.getCurrentCycle().getPeriodByKey(
							perKey[0], perKey[1], perKey[2] + j);
					int[] newPerKey = { perKey[0], perKey[1], perKey[2] + j };

					for (int k = 0; k < _conditionsToTest.size(); k++) {
						DxCondition cond = _conditionsToTest.get(k);
						numberOfConflicts[k] += cond.addTest(newPerKey, per,
								event.getID());
					}// end for (int j=0; j< _testToRun.size(); j++)
					((EventAttach) event.getAttach())
							.setInAPeriod(getBooleanValue(1));
					((EventAttach) event.getAttach())
							.setAssigned(getBooleanValue(1));
				}// end for (int j=0; j< ((EventAttach)event.getAttach())
			} else {// end if (tts.getCurrentCycle().isPeriodContiguous(
				((EventAttach) event.getAttach()).setInAPeriod(false);
				((EventAttach) event.getAttach()).setAssigned(false);
				((EventAttach) event.getAttach()).setPermanentState(false);
			}// end else if (tts.getCurrentCycle().isPeriodContiguous(
		}
		return numberOfConflicts;
	}

	public int[] removeEventInTTs(TTStructure tts, DResource event,
			boolean usePriority) {
		// return addOrRemoveOrGetConflictsEventInTTs(tts, event, -1,
		// usePriority);
		int[] numberOfConflicts = { 0, 0, 0 };

		if (((EventAttach) event.getAttach()).isAssigned()) {
			int[] perKey = ((EventAttach) event.getAttach())
					.getPeriodKeyTable();
			int duration = ((EventAttach) event.getAttach()).getDuration()
					/ tts.getPeriodLenght();

			if ((tts.getCurrentCycle().isPeriodContiguous(perKey[0], perKey[1],
					perKey[2], duration, _avoidPriority, usePriority))
					&& (duration > 0)) {

				for (int j = 0; j < duration; j++) {
					Period per = tts.getCurrentCycle().getPeriodByKey(
							perKey[0], perKey[1], perKey[2] + j);
					int[] newPerKey = { perKey[0], perKey[1], perKey[2] + j };

					for (int k = 0; k < _conditionsToTest.size(); k++) {
						DxCondition cond = _conditionsToTest.get(k);
						numberOfConflicts[k] += cond.removeTest(newPerKey, per,
								event.getID());
					}// end for (int j=0; j< _testToRun.size(); j++)

					((EventAttach) event.getAttach())
							.setInAPeriod(getBooleanValue(-1));
					((EventAttach) event.getAttach())
							.setAssigned(getBooleanValue(-1));

				}// end for (int j=0; j< ((EventAttach)event.getAttach())
			} else {// end if (tts.getCurrentCycle().isPeriodContiguous(
				((EventAttach) event.getAttach()).setInAPeriod(false);
				((EventAttach) event.getAttach()).setAssigned(false);
				((EventAttach) event.getAttach()).setPermanentState(false);
			}// end else if (tts.getCurrentCycle().isPeriodContiguous(
		}
		return numberOfConflicts;
	}

	public int[] getEventConflictsInTTs(TTStructure tts, DResource event,
			boolean usePriority) {
		// return addOrRemoveOrGetConflictsEventInTTs(tts, event, 0,
		// usePriority);
		int[] numberOfConflicts = { 0, 0, 0 };

		if (((EventAttach) event.getAttach()).isAssigned()) {
			int[] perKey = ((EventAttach) event.getAttach())
					.getPeriodKeyTable();
			int duration = ((EventAttach) event.getAttach()).getDuration()
					/ tts.getPeriodLenght();

			if ((tts.getCurrentCycle().isPeriodContiguous(perKey[0], perKey[1],
					perKey[2], duration, _avoidPriority, usePriority))
					&& (duration > 0)) {

				for (int j = 0; j < duration; j++) {
					Period per = tts.getCurrentCycle().getPeriodByKey(
							perKey[0], perKey[1], perKey[2] + j);
					int[] newPerKey = { perKey[0], perKey[1], perKey[2] + j };

					for (int k = 0; k < _conditionsToTest.size(); k++) {
						DxCondition cond = _conditionsToTest.get(k);
						numberOfConflicts[k] += cond.getInfo(newPerKey, per,
								event.getID());
					}// end for
				}// end for
			} else {// end if (tts.getCurrentCycle().isPeriodContiguous(
				((EventAttach) event.getAttach()).setInAPeriod(false);
				((EventAttach) event.getAttach()).setAssigned(false);
				((EventAttach) event.getAttach()).setPermanentState(false);
			}// end else if (tts.getCurrentCycle().isPeriodContiguous(
		}
		return numberOfConflicts;

	}

	/**
	 * addOrRemoveOrGetConflictsEventInTTs
	 * <p>
	 * if add an event in tts the event is placed in the corresponding Periods
	 * </p>
	 * <p>
	 * if remove an event in tts the event is removed from the corresponding
	 * Periods
	 * </p>
	 * <p>
	 * if getConflicts of an event in tts the Conflicts are calculated for the
	 * corresponding periods (where the event is plan to be placed )
	 * </p>
	 * 
	 * @param tts
	 *            the TTStructure to be used (it can be modified)
	 * @param event
	 *            the Event to work with
	 * @param int
	 *            operation -1= remove event, 0= getConflicts, 1= add event
	 * @return int [] conflicts after the operation
	 *         <p>
	 *         range 0= nb of students conflicts
	 *         </p>
	 *         <p>
	 *         range 1= nb of instructors conflicts
	 *         </p>
	 *         <p>
	 *         range 2= nb of rooms conflicts
	 *         </p>
	 */

	// // XXXX Pascal: Candidat pour un anti-pattern ?
	// private int[] addOrRemoveOrGetConflictsEventInTTs(TTStructure tts,
	// DResource event, int operation, boolean usePriority) {
	// int[] numberOfConflicts = { 0, 0, 0 };
	//
	// if (((EventAttach) event.getAttach()).isAssigned()) {
	// int[] perKey = ((EventAttach) event.getAttach())
	// .getPeriodKeyTable();
	// int duration = ((EventAttach) event.getAttach()).getDuration()
	// / tts.getPeriodLenght();
	//
	// if ((tts.getCurrentCycle().isPeriodContiguous(perKey[0], perKey[1],
	// perKey[2], duration, _avoidPriority, usePriority))
	// && (duration > 0)) {
	//
	// for (int j = 0; j < duration; j++) {
	// Period per = tts.getCurrentCycle().getPeriodByKey(
	// perKey[0], perKey[1], perKey[2] + j);
	// int[] newPerKey = { perKey[0], perKey[1], perKey[2] + j };
	//
	// for (int k = 0; k < _conditionsToTest.size(); k++) {
	// DxCondition cond = _conditionsToTest.get(k);
	// numberOfConflicts[k] += cond.executeTest(newPerKey,
	// per, event.getID(), operation);
	// }// end for (int j=0; j< _testToRun.size(); j++)
	//
	// if (operation != 0) {
	// ((EventAttach) event.getAttach())
	// .setInAPeriod(getBooleanValue(operation));
	// ((EventAttach) event.getAttach())
	// .setAssigned(getBooleanValue(operation));
	// }
	// }// end for (int j=0; j< ((EventAttach)event.getAttach())
	// } else {// end if (tts.getCurrentCycle().isPeriodContiguous(
	// ((EventAttach) event.getAttach()).setInAPeriod(false);
	// ((EventAttach) event.getAttach()).setAssigned(false);
	// ((EventAttach) event.getAttach()).setPermanentState(false);
	// }// end else if (tts.getCurrentCycle().isPeriodContiguous(
	// }
	// return numberOfConflicts;
	// }
	/**
	 * 
	 * @param oper
	 * @return
	 */
	private boolean getBooleanValue(int oper) {
		if (oper == 1)
			return true;

		return false;
	}

	/**
	 * extract preference tables
	 */
	public void extractPreference() {

		if (_dm.getDxDocument().getDMediator() != null) {
			int[] conflictsPreference = _dm.getDxDocument().getDMediator()
					.getDApplication().getPreferences().getConflictLimits();
			for (int i = 0; i < _acceptableConflictsTable.length; i++)
				_acceptableConflictsTable[i] = conflictsPreference[i];
			_avoidPriority = new int[2 - conflictsPreference[3]];
			int inc = 0;
			for (int i = conflictsPreference[3] + 1; i < 3; i++)
				_avoidPriority[inc++] = i;
			_periodAcceptableSize = conflictsPreference[4];
			_periodVariationEvents = conflictsPreference[5];
			((DxStudentCondtionsToTest) _conditionsToTest.get(0))
					.setPeriodVariationEvents(_periodVariationEvents);
		}
	}

	/**
	 * extract preference tables
	 */
	public void extractDxPreference() {

		if (_dm.getDxDocument().getDMediator() != null) {
			DxConflictLimits conflictsPreference = _dm.getDxDocument().getDMediator()
					.getDApplication().getPreferences().getDxConflictLimits();
//			for (int i = 0; i < _acceptableConflictsTable.length; i++)
				_acceptableConflictsTable[0] = conflictsPreference.getMStudConfBetweenTwoEvents();
				_acceptableConflictsTable[1] = conflictsPreference.getMInstConfBetweenTwoEvents();
				_acceptableConflictsTable[2] = conflictsPreference.getMRoomConfBetweenTwoEvents();
//				_acceptableConflictsTable[3] = conflictsPreference.getMAllowedPriority();
//				_acceptableConflictsTable[4] = conflictsPreference.getMNumOfEventsInPeriod();
//				_acceptableConflictsTable[5] = conflictsPreference.getMinPeriodSpacing();
//				_acceptableConflictsTable[6] = conflictsPreference.getRoomBookingRate();
			_avoidPriority = new int[2 - conflictsPreference.getMAllowedPriority()];
			int inc = 0;
			for (int i = conflictsPreference.getMAllowedPriority() + 1; i < 3; i++)
				_avoidPriority[inc++] = i;
			_periodAcceptableSize = conflictsPreference.getMNumOfEventsInPeriod();
			_periodVariationEvents = conflictsPreference.getMinPeriodSpacing();
			((DxStudentCondtionsToTest) _conditionsToTest.get(0))
					.setPeriodVariationEvents(_periodVariationEvents);
		}
	}

	/**
	 * get all test to run
	 * 
	 * @return
	 */
	public Vector getConditionsToTest() {
		return _conditionsToTest;
	}

	/**
	 * 
	 * @param perKey
	 * @return
	 */
	public Vector periodVariationEvents(int[] perKey) {
		if (DxFlags.newAlg) {
			extractDxPreference();
		} else {
			extractPreference();
		}
		DxStudentCondtionsToTest studTest = new DxStudentCondtionsToTest(
				_matrix, _dm.getSetOfActivities(), _dm.getTTStructure()
						.getCurrentCycle());
		studTest.setPeriodVariationEvents(_periodVariationEvents);
		return studTest.periodVariationEventsPeriods(perKey);
	}

	/**
	 * @param improveTTStruct
	 * @param event
	 */
	public void addEventInAllPeriods(TTStructure improveTTStruct,
			DResource event) {
		EventAttach eventAttach = ((EventAttach) event.getAttach())
				.cloneEvent();
		eventAttach.setAssigned(true);
		DResource res = new DResource(event.getID(), eventAttach);
		eventAttach.setDuration(improveTTStruct.getPeriodLenght());
		for (int i = 0; i < improveTTStruct.getCurrentCycle().getSetOfDays()
				.size(); i++) {
			DResource day = improveTTStruct.getCurrentCycle().getSetOfDays()
					.getResourceAt(i);
			for (int j = 0; j < ((Day) day.getAttach()).getSetOfSequences()
					.size(); j++) {
				DResource seq = ((Day) day.getAttach()).getSetOfSequences()
						.getResourceAt(j);
				for (int k = 0; k < ((Sequence) seq.getAttach())
						.getSetOfPeriods().size(); k++) {
					DResource per = ((Sequence) seq.getAttach())
							.getSetOfPeriods().getResourceAt(k);
					int[] daytime = { (int) day.getKey(), (int) seq.getKey(),
							(int) per.getKey() };

					String periodKey = daytime[0] + "." + daytime[1] + "."
							+ daytime[2];
					eventAttach.setKey(4, periodKey);
					System.out.println(i + " " + j + " " + k);
					addEventInTTs(improveTTStruct, res, false);

				}// end for(int k=0; k< ((Sequence)seq.getAttach())
			}// end for(int j=0; j<
		}// end for(int i=0; i< _newTTS.getCurrentCycle()
	}

}// end DxConditionsToTest
