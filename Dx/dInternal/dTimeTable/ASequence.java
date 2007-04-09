/**
 *
 * Title: Cycle 
 * Description: Cycle
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
package dInternal.dTimeTable;

import java.io.PrintStream;
import java.util.Vector;

public class ASequence extends Object {

	public final static String CR_LF = "\r\n";

	private int _currentPeriodIndex = 0;

	private String _sequenceID;

	private Vector<APeriod> _ttPeriods;

	/**
	 * Constructor
	 * 
	 */
	public ASequence() {
		_sequenceID = null;
		_ttPeriods = new Vector<APeriod>(1, 1);
	}

	/**
	 * get the ID of sequence
	 * 
	 * @return int the ID of sequence
	 */
	public String getSequenceId() {
		return _sequenceID;
	}

	/**
	 * set the ID of sequence
	 */
	public void setSequenceId(String sequenceId) {
		_sequenceID = sequenceId;
	}

	/**
	 * get the set of periods in Sequence
	 * 
	 * @return Vector the set of periods
	 */
	public Vector<APeriod> getTTperiods() {
		return _ttPeriods;
	}

	/**
	 * */
	public APeriod getPeriodByIndex(int periodIndex) {
		if (periodIndex < _ttPeriods.size())
			return _ttPeriods.elementAt(periodIndex);
		return null;
	}

	/**
	 * */
	public APeriod getCurrentPeriod() {
		return getPeriodByIndex(_currentPeriodIndex);
	}

	/**
	 * */
	public int getCurrentPeriodIndex() {
		return _currentPeriodIndex;
	}

	/**
	 * */
	public void setCurrentPeriodIndex(int curPeriodIndex) {
		_currentPeriodIndex = curPeriodIndex;
	}

	/**
	 * set the set of periods
	 * 
	 * @param Vector
	 *            <TTperiod> the set of periods
	 */
	public void setTTperiods(Vector<APeriod> setOfPeriods) {
		_ttPeriods = setOfPeriods;
	}

	/**
	 * return the next period and increment _currentPeriodIndex
	 * 
	 * @return
	 */
	public APeriod getNextPeriod(){//int seqVal) {
		APeriod period = _ttPeriods.elementAt(_currentPeriodIndex++);
		if (_currentPeriodIndex >= _ttPeriods.size()) {
			_currentPeriodIndex = 0;
//			seqVal++;
		}
		return period;
	}

	/**
	 * return the previous period and decrement _currentPeriodIndex
	 * 
	 * @return
	 */
	public APeriod getPreviousPeriod(){//int seqVal) {
		APeriod period = _ttPeriods.elementAt(_currentPeriodIndex--);
		if (_currentPeriodIndex <= -1) {
			_currentPeriodIndex = _ttPeriods.size() - 1;
//			seqVal--;
		}
		return period;
	}

	/**
	 * 
	 */
	public String toString() {
		String str = _sequenceID;
		for (int i = 0; i < _ttPeriods.size(); i++)
			str += "--" + getPeriodByIndex(i).toString() + CR_LF;
		return str;
	}

	/**
	 * isEquals checks if this sequence is equals to the sequence gives in arg
	 * 
	 * @param seq
	 *            the Sequence arg
	 * @return
	 *            <p>
	 *            true if this sequence is equals to the sequence gives in arg
	 *            </p>
	 *            false otherwise
	 */
	public boolean isEquals(ASequence seq) {
		boolean sequenceEqual = true;
		int i = 0;
		if (_sequenceID == seq.getSequenceId()) {
			while (i < _ttPeriods.size() && sequenceEqual == true) {
				APeriod perR = new APeriod();
				APeriod perCloneR = new APeriod();
				perR = _ttPeriods.elementAt(i);
				perCloneR = seq.getTTperiods().elementAt(i);
				if (!perR.isEquals(perCloneR))
					sequenceEqual = false;
				i++;
			}

		} else {
			sequenceEqual = false;
		}

		return sequenceEqual;
	}

	/**
	 * Sequence To xml
	 * @param out
	 */
	public void SequenceToXml(PrintStream out) {
		out.println("<TTsequence>");
		out.println("<sequenceID>" + _sequenceID + "</sequenceID>");
		out.println("<TTperiods>");
		for (int i = 0; i < _ttPeriods.size(); i++) {
			_ttPeriods.elementAt(i).PeriodToXml(out);
		}
		out.println("</TTperiods>");
		out.println("</TTsequence>");

	}
}
