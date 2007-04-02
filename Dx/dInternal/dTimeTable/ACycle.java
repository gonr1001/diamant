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
public class ACycle extends Object {
	
	public final static int zero = 0;
	private int _currentDayIndex = 0;
	private int _cycleID;
	private int _pLength;
	private Vector <ADay> _ttDays;
	
	public ACycle(){
		_cycleID = zero;
		_pLength = zero;
		_ttDays = new Vector<ADay>(1,1);
	}
	/**
	 * get the set of days
	 * 
	 * @return Vector of days
	 */
	public Vector<ADay> getTTdays() {
		return _ttDays;
	}

	/**
	 * get the ID of cycle
	 * 
	 * @return int Cycle ID
	 */
	public int getCycleID() {
		return _cycleID;
	}

	/**
	 * get the pLength of period of cycle
	 * 
	 * @return int priod Length Of Cycle
	 */
	public int getpLength() {
		return _pLength;
	}

	/**
	 * @param int
	 *            the index of the sequence
	 */
	public ADay getDay(int dayIndex) {
		return _ttDays.elementAt(dayIndex);
	}

	/**
	 * */
	public ADay getCurrentDay() {
		return getDay(_currentDayIndex);
	}

	/**
	 * */
	public int getCurrentDayIndex() {
		return _currentDayIndex;
	}

	/**
	 * */
	public void setCurrentDayIndex(int curDayIndex) {
		_currentDayIndex = curDayIndex;
	}

	/**
	 * set the ID of cycle
	 * 
	 * @param int the ID of cycle
	 */
	public void setCycleID(int cycleId) {
		_cycleID = cycleId;
	}
	
	/**
	 * set the period Lengt of cycle
	 * 
	 * @param int  the Length  of period in yhe Cycle
	 */
	public void setpLength(int pLength) {
		_pLength = pLength;
	}
	
	/**
	 * set the set of days
	 * 
	 * @param Vector
	 *            the set of days
	 */
	public void setTTdays(Vector<ADay> setOfDays) {
		_ttDays = setOfDays;
	}
	
	
	/**
	 * return the next day and increment _currentDayIndex
	 * 
	 * @return
	 */
	public ADay getNextDay(int seqVal) {
		ADay day = _ttDays.elementAt(_currentDayIndex++);
		if (_currentDayIndex >= _ttDays.size()) {
			_currentDayIndex = 0;
			seqVal++;
		}
		return day;
	}

	/**
	 * return the previous day and decrement _currentDayIndex
	 * 
	 * @return
	 */
	public ADay getPreviousDay(int seqVal) {
		ADay day = _ttDays.elementAt(_currentDayIndex--);
		if (_currentDayIndex <= -1) {
			_currentDayIndex = _ttDays.size() - 1;
			seqVal--;
		}
		return day;
	}

	
	/**
	 * return the sequence and increment _currentDayIndex
	 * 
	 * @return
	 */
	public ASequence getNextSequence(int dayValue) {
		ASequence sequence = (_ttDays.elementAt(_currentDayIndex))
				.getNextSequence(dayValue);
		if (_currentDayIndex >= _ttDays.size()) {
			_currentDayIndex = 0;
			dayValue++;
		}
		return sequence;
	}

	/**
	 * return the previous period and decrement _currentSequenceIndex
	 * 
	 * @return
	 */
	public ASequence getPreviousSequence(int dayValue) {
		int seqValue = _currentDayIndex;
		ASequence sequence = (_ttDays.elementAt(_currentDayIndex))
				.getPreviousSequence(dayValue);
		if (_currentDayIndex <= -1) {
			_currentDayIndex = _ttDays.size() - 1;
			dayValue--;
		}
		if (_currentDayIndex != seqValue) {
			_currentDayIndex = seqValue;
			getCurrentDay().setCurrentSequenceIndex(
					getCurrentDay().getTTsequences().size() - 1);
		}
		return sequence;
	}

	/**
	 * 
	 */
	public String toString() {
		String str = _cycleID + "--" + _pLength ;
		for (int i = 0; i < _ttDays.size(); i++) {
			ADay dayD = _ttDays.elementAt(i);
			str += ( dayD.toString() + "--");
		}
		return str;
	}

	/**
	 * isEquals checks if this cycle is equals to the cycle gives in arg
	 * 
	 * @param cycle
	 *            the cycle arg
	 * @return
	 *            <p>
	 *            true if this cycele is equals to the cycle gives in arg
	 *            </p>
	 *            false otherwise
	 */
	public boolean isEquals(ACycle cycle) {
		boolean cycleEqual = true;
		int i = 0;
		if(_cycleID == cycle.getCycleID() && _pLength == cycle.getpLength()){
		while (i < _ttDays.size()&& cycleEqual) {
			ADay dayR = new ADay();
			ADay dayCloneR = new ADay();
			dayR = _ttDays.elementAt(i);
			dayCloneR = cycle.getTTdays().elementAt(i);
			if (!dayR.isEquals(dayCloneR))
				cycleEqual =  false;
			i++;
		}
		
	} else {cycleEqual = false;}
	
	return cycleEqual;
	}
	/**
	 * Day To xml
	 * @param out
	 */
	public void CycleToXml(PrintStream out) {
		out.println("<DXTimeTable>");
		out.println("<TTcycle>");
		out.println("<cycleID>" + _cycleID + "</cycleID>");
		out.println("<pLength>" + _pLength + "</pLength>");
		out.println("<TTdays>");
		for(int i = 0 ; i < _ttDays.size(); i++){
			_ttDays.elementAt(i).DayToXml(out);
			}
		out.println("</TTdays>");
		out.println("</TTcycle>");
		out.println("</DXTimeTable>");
	}
}