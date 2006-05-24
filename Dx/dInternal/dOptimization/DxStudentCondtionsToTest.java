/**
 * Created on May 24, 2006
 * 
 * 
 * Title: DxStudentCondtionsToTest.java 
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
import dInternal.DResource;
import dInternal.dData.dActivities.SetOfActivities;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Period;
import dInternal.dUtil.DXToolsMethods;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxStudentCondtionsToTest is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
public class DxStudentCondtionsToTest implements DxCondition {

	StudentsConflictsMatrix _matrix;

	SetOfActivities _soa;

	Cycle _cycle;

	int _periodVariationEvents = 0;

	/**
	 * 
	 * @param matrix
	 * @param soa
	 * @param cycle
	 */
	public DxStudentCondtionsToTest(StudentsConflictsMatrix matrix,
			SetOfActivities soa, Cycle cycle) {
		_matrix = matrix;
		_soa = soa;
		_cycle = cycle;
	}

	/**
	 * 
	 * @param periodVariationEvents
	 */
	public void setPeriodVariationEvents(int periodVariationEvents) {
		_periodVariationEvents = periodVariationEvents;
	}

	/**
	 * 
	 * @param perKey
	 * @param period
	 * @param eventKey
	 * @param operation
	 * @return
	 */
	public int executeTest(int[] perKey, Period per, String eventKey,
			int operation) {
		String key1 = DXToolsMethods.getToken(eventKey, DConst.TOKENSEPARATOR,
				0)
				+ DConst.TOKENSEPARATOR
				+ DXToolsMethods.getToken(eventKey, DConst.TOKENSEPARATOR, 1)
				+ DConst.TOKENSEPARATOR
				+ DXToolsMethods.getToken(eventKey, DConst.TOKENSEPARATOR, 2);
		int number = 0;
		int nbConf = 0;
		ConflictsAttach confVal = new ConflictsAttach();
		Vector<Period> allPeriods = new Vector<Period>();
		Period period;
		if (operation == 0)
			allPeriods = periodVariationEventsPeriods(perKey);
		else
			allPeriods.add(per);

		for (int a = 0; a < allPeriods.size(); a++) {
			period = allPeriods.get(a);

			for (int i = 0; i < period.getEventsInPeriod().size(); i++) {
				String event2 = period.getEventsInPeriod().getResourceAt(i)
						.getID();
				String key2 = DXToolsMethods.getToken(event2,
						DConst.TOKENSEPARATOR, 0)
						+ DConst.TOKENSEPARATOR
						+ DXToolsMethods.getToken(event2,
								DConst.TOKENSEPARATOR, 1)
						+ DConst.TOKENSEPARATOR
						+ DXToolsMethods.getToken(event2,
								DConst.TOKENSEPARATOR, 2);
				if (!key1.equalsIgnoreCase(key2)) {
					nbConf = _matrix.getNumberOfCOnflicts(key1, key2);
					number += nbConf;
					if (nbConf != 0)
						confVal.addConflict(period.getEventsInPeriod()
								.getResourceAt(i).getID(), nbConf,
								DConst.R_STUDENT_NAME, new Vector());
				}// end if(!key1.equalsIgnoreCase(key2))
			}// end for (int i=0; i< period.getEventsInPeriod().size(); i++)

		}// for(int a=0; a< allPeriods.size(); a++)

		switch (operation) {
		case 0:
			break;
		case 1:
			DResource resc = per.getEventsInPeriod().getResource(eventKey);
			if (resc != null)
				((ConflictsAttach) resc.getAttach())
						.mergeConflictsAttach(confVal);
			else
				per.getEventsInPeriod().addResource(
						new DResource(eventKey, confVal), 1);
			per.addNbStudConflict(number);
			break;
		case -1:
			per.getEventsInPeriod().removeResource(eventKey);
			per.removeNbStudConflict(number);
			for (int i = 0; i < per.getEventsInPeriod().size(); i++)
				((ConflictsAttach) per.getEventsInPeriod().getResourceAt(i)
						.getAttach()).removeConflict(eventKey,
						DConst.R_STUDENT_NAME);

			break;
		}
		return number;
	}

	/**
	 * check a students conflicts of an event in a definite periods
	 * 
	 * @param perKey
	 *            int[] (0=day, 1=seq, 2=per)
	 * @param eventDuration
	 *            (in number of periods)
	 * @return
	 */
	public Vector<Period> periodVariationEventsPeriods(int[] perKey) {
		Vector<Period> vPeriods = new Vector<Period>();
		_cycle.setCurrentDaySeqPerIndex(perKey[0] - 1, perKey[1] - 1,
				perKey[2] - 1);
		_cycle.getPreviousPeriod(1);
		// get previous periods
		for (int i = 0; i < _periodVariationEvents; i++) {
			Period per = _cycle.getPreviousPeriod(1);
			vPeriods.insertElementAt(per, 0);
		}
		// get next periods
		_cycle.setCurrentDaySeqPerIndex(perKey[0] - 1, perKey[1] - 1,
				perKey[2] - 1);
		for (int i = 0; i < _periodVariationEvents + 1; i++) {
			Period per = _cycle.getNextPeriod(1);
			vPeriods.add(per);
		}
		return vPeriods;
	}

	public int addTest(int[] perKey, Period per, String eventKey) {
		String key1 = DXToolsMethods.getToken(eventKey, DConst.TOKENSEPARATOR,
				0)
				+ DConst.TOKENSEPARATOR
				+ DXToolsMethods.getToken(eventKey, DConst.TOKENSEPARATOR, 1)
				+ DConst.TOKENSEPARATOR
				+ DXToolsMethods.getToken(eventKey, DConst.TOKENSEPARATOR, 2);
		int number = 0;
		int nbConf = 0;
		ConflictsAttach confVal = new ConflictsAttach();
		Vector<Period> allPeriods = new Vector<Period>();
		Period period;

		allPeriods.add(per);

		for (int a = 0; a < allPeriods.size(); a++) {
			period = allPeriods.get(a);

			for (int i = 0; i < period.getEventsInPeriod().size(); i++) {
				String event2 = period.getEventsInPeriod().getResourceAt(i)
						.getID();
				String key2 = DXToolsMethods.getToken(event2,
						DConst.TOKENSEPARATOR, 0)
						+ DConst.TOKENSEPARATOR
						+ DXToolsMethods.getToken(event2,
								DConst.TOKENSEPARATOR, 1)
						+ DConst.TOKENSEPARATOR
						+ DXToolsMethods.getToken(event2,
								DConst.TOKENSEPARATOR, 2);
				if (!key1.equalsIgnoreCase(key2)) {
					nbConf = _matrix.getNumberOfCOnflicts(key1, key2);
					number += nbConf;
					if (nbConf != 0)
						confVal.addConflict(period.getEventsInPeriod()
								.getResourceAt(i).getID(), nbConf,
								DConst.R_STUDENT_NAME, new Vector());
				}// end if(!key1.equalsIgnoreCase(key2))
			}// end for (int i=0; i< period.getEventsInPeriod().size(); i++)

		}// for(int a=0; a< allPeriods.size(); a++)

		DResource resc = per.getEventsInPeriod().getResource(eventKey);
		if (resc != null)
			((ConflictsAttach) resc.getAttach()).mergeConflictsAttach(confVal);
		else
			per.getEventsInPeriod().addResource(
					new DResource(eventKey, confVal), 1);
		per.addNbStudConflict(number);

		return number;
	}

	public int removeTest(int[] perKey, Period per, String eventKey) {
		String key1 = DXToolsMethods.getToken(eventKey, DConst.TOKENSEPARATOR,
				0)
				+ DConst.TOKENSEPARATOR
				+ DXToolsMethods.getToken(eventKey, DConst.TOKENSEPARATOR, 1)
				+ DConst.TOKENSEPARATOR
				+ DXToolsMethods.getToken(eventKey, DConst.TOKENSEPARATOR, 2);
		int number = 0;
		int nbConf = 0;
		ConflictsAttach confVal = new ConflictsAttach();
		Vector<Period> allPeriods = new Vector<Period>();
		Period period;

		allPeriods.add(per);

		for (int a = 0; a < allPeriods.size(); a++) {
			period = allPeriods.get(a);

			for (int i = 0; i < period.getEventsInPeriod().size(); i++) {
				String event2 = period.getEventsInPeriod().getResourceAt(i)
						.getID();
				String key2 = DXToolsMethods.getToken(event2,
						DConst.TOKENSEPARATOR, 0)
						+ DConst.TOKENSEPARATOR
						+ DXToolsMethods.getToken(event2,
								DConst.TOKENSEPARATOR, 1)
						+ DConst.TOKENSEPARATOR
						+ DXToolsMethods.getToken(event2,
								DConst.TOKENSEPARATOR, 2);
				if (!key1.equalsIgnoreCase(key2)) {
					nbConf = _matrix.getNumberOfCOnflicts(key1, key2);
					number += nbConf;
					if (nbConf != 0)
						confVal.addConflict(period.getEventsInPeriod()
								.getResourceAt(i).getID(), nbConf,
								DConst.R_STUDENT_NAME, new Vector());
				}// end if(!key1.equalsIgnoreCase(key2))
			}// end for (int i=0; i< period.getEventsInPeriod().size(); i++)

		}// for(int a=0; a< allPeriods.size(); a++)

		per.getEventsInPeriod().removeResource(eventKey);
		per.removeNbStudConflict(number);
		for (int i = 0; i < per.getEventsInPeriod().size(); i++)
			((ConflictsAttach) per.getEventsInPeriod().getResourceAt(i)
					.getAttach()).removeConflict(eventKey,
					DConst.R_STUDENT_NAME);

		return number;
	}

	public int getInfo(int[] perKey, Period per, String eventKey) {
		String key1 = DXToolsMethods.getToken(eventKey, DConst.TOKENSEPARATOR,
				0)
				+ DConst.TOKENSEPARATOR
				+ DXToolsMethods.getToken(eventKey, DConst.TOKENSEPARATOR, 1)
				+ DConst.TOKENSEPARATOR
				+ DXToolsMethods.getToken(eventKey, DConst.TOKENSEPARATOR, 2);
		int number = 0;
		int nbConf = 0;
		ConflictsAttach confVal = new ConflictsAttach();
		Vector<Period> allPeriods = new Vector<Period>();
		Period period;

		allPeriods = periodVariationEventsPeriods(perKey);

		for (int a = 0; a < allPeriods.size(); a++) {
			period = allPeriods.get(a);

			for (int i = 0; i < period.getEventsInPeriod().size(); i++) {
				String event2 = period.getEventsInPeriod().getResourceAt(i)
						.getID();
				String key2 = DXToolsMethods.getToken(event2,
						DConst.TOKENSEPARATOR, 0)
						+ DConst.TOKENSEPARATOR
						+ DXToolsMethods.getToken(event2,
								DConst.TOKENSEPARATOR, 1)
						+ DConst.TOKENSEPARATOR
						+ DXToolsMethods.getToken(event2,
								DConst.TOKENSEPARATOR, 2);
				if (!key1.equalsIgnoreCase(key2)) {
					nbConf = _matrix.getNumberOfCOnflicts(key1, key2);
					number += nbConf;
					if (nbConf != 0)
						confVal.addConflict(period.getEventsInPeriod()
								.getResourceAt(i).getID(), nbConf,
								DConst.R_STUDENT_NAME, new Vector());
				}// end if(!key1.equalsIgnoreCase(key2))
			}// end for (int i=0; i< period.getEventsInPeriod().size(); i++)

		}// for(int a=0; a< allPeriods.size(); a++)
		return number;
	}

}