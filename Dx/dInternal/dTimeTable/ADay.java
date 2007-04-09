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

public class ADay extends Object {
	/** The default value of variables * */
	public final static int zero = 0;

	private int _currentSequenceIndex = 0;

	private Vector<ASequence> _ttSequences;

	private int _dayRef;

	private String _dayId;

	public ADay() {
		_ttSequences = new Vector<ASequence>(1, 1);
		_dayRef = zero;
		_dayId = null;
	}

	/**
	 * get the set of sequences
	 * 
	 * @return Vector of sequences
	 */
	public Vector<ASequence> getTTsequences() {
		return _ttSequences;
	}

	/**
	 * get the reference of day
	 * 
	 * @return int Day Reference
	 */
	public int getDayRef() {
		return _dayRef;
	}

	/**
	 * get the ID of Day
	 * 
	 * @return String Identifier Of Day
	 */
	public String getDayId() {
		return _dayId;
	}

	/**
	 * @param int
	 *            the index of the sequence
	 */
	public ASequence getSequence(int sequenceIndex) {
		return _ttSequences.elementAt(sequenceIndex);
	}

	/**
	 * */
	public ASequence getCurrentSequence() {
		return getSequence(_currentSequenceIndex);
	}

	/**
	 * */
	public int getCurrentSequenceIndex() {
		return _currentSequenceIndex;
	}

	/**
	 * */
	public void setCurrentSequenceIndex(int curSequenceIndex) {
		_currentSequenceIndex = curSequenceIndex;
	}

	/**
	 * set the reference of day
	 * 
	 * @param int
	 *            the reference of day
	 */
	public void setDayRef(int dayRef) {
		_dayRef = dayRef;
	}

	/**
	 * set the identifier of day
	 * 
	 * @param String
	 *            the identifier of day
	 */
	public void setDayId(String dayId) {
		_dayId = dayId;
	}

	/**
	 * set the set of sequences
	 * 
	 * @param Vector
	 *            the set of sequences
	 */
	public void setTTsequences(Vector<ASequence> setOfSequences) {
		_ttSequences = setOfSequences;
	}

	/**
	 * return the next sequence and increment _currentSequenceIndex
	 * 
	 * @return
	 */
	public ASequence getNextSequence(){//int seqVal) {
		ASequence sequence = _ttSequences.elementAt(_currentSequenceIndex++);
		if (_currentSequenceIndex >= _ttSequences.size()) {
			_currentSequenceIndex = 0;
//			seqVal++;
		}
		return sequence;
	}

	/**
	 * return the previous sequence and decrement _currentSequenceIndex
	 * 
	 * @return
	 */
	public ASequence getPreviousSequence(){//int seqVal) {
		ASequence sequence = _ttSequences.elementAt(_currentSequenceIndex--);
		if (_currentSequenceIndex <= -1) {
			_currentSequenceIndex = _ttSequences.size() - 1;
//			seqVal--;
		}
		return sequence;
	}

	/**
	 * return the period and increment _currentSequenceIndex
	 * 
	 * @return
	 */
	public APeriod getNextPeriod(){//int dayValue) {
		APeriod period = (_ttSequences.elementAt(_currentSequenceIndex))
				.getNextPeriod();//dayValue);
		if (_currentSequenceIndex >= _ttSequences.size()) {
			_currentSequenceIndex = 0;
//			dayValue++;
		}
		return period;
	}

	/**
	 * return the previous period and decrement _currentSequenceIndex
	 * 
	 * @return
	 */
	public APeriod getPreviousPeriod(){//int dayValue) {
		int seqValue = _currentSequenceIndex;
		APeriod period = (_ttSequences.elementAt(_currentSequenceIndex))
				.getPreviousPeriod();//dayValue);
		if (_currentSequenceIndex <= -1) {
			_currentSequenceIndex = _ttSequences.size() - 1;
//			dayValue--;
		}
		if (_currentSequenceIndex != seqValue) {
			_currentSequenceIndex = seqValue;
			getCurrentSequence().setCurrentPeriodIndex(
					getCurrentSequence().getTTperiods().size() - 1);
		}
		return period;
	}

	/**
	 * 
	 */
	public String toString() {
		String str = _dayRef + "--" + _dayId;
		for (int i = 0; i < _ttSequences.size(); i++) {
			ASequence seqD = _ttSequences.elementAt(i);
			str += (seqD.toString() + "--");
		}
		return str;
	}

	/**
	 * isEquals checks if this Day is equals to the Day gives in arg
	 * 
	 * @param day
	 *            the Day arg
	 * @return
	 *            <p>
	 *            true if this Day is equals to the Day gives in arg
	 *            </p>
	 *            false otherwise
	 */
	public boolean isEquals(ADay day) {
		boolean dayEqual = true;
		int i = 0;
		if (_dayRef == day.getDayRef() && _dayId == day.getDayId()) {
			while (i < _ttSequences.size() && dayEqual) {
				ASequence seqR = new ASequence();
				ASequence seqCloneR = new ASequence();
				seqR = _ttSequences.elementAt(i);
				seqCloneR = day.getTTsequences().elementAt(i);
				if (!seqR.isEquals(seqCloneR))
					dayEqual = false;
				i++;
			}

		} else {
			dayEqual = false;
		}

		return dayEqual;
	}

	/**
	 * Day To xml
	 * 
	 * @param out
	 */
	public void DayToXml(PrintStream out) {
		out.println("<TTday>");
		out.println("<TTsequences>");
		for (int i = 0; i < _ttSequences.size(); i++) {
			_ttSequences.elementAt(i).SequenceToXml(out);
		}
		out.println("</TTsequences>");
		out.println("<dayRef>" + _dayRef + "</dayRef>");
		out.println("<dayID>" + _dayId + "</dayID>");
		out.println("</TTday>");
	}

}
