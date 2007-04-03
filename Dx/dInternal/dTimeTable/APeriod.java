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
import java.text.NumberFormat;

public class APeriod extends Object {

	/** The number format for Hours * */
	public final static NumberFormat HourFormat = NumberFormat
			.getIntegerInstance();

	/** The default value of variables * */
	public final static int zero = 0;

	/** if begin time is 13:05 we must find 13:05 in file out Xml not 13:5 * */
	public final static int dix = 10;

	private int _periodID;

	private int[] _beginTime = { 8, 0 };

	private int[] _endTime = { 9, 0 };

	private int _priority;

	/**
	 * Constructeur
	 */
	public APeriod() {
		_periodID = zero;
		_beginTime[0] = zero;
		_beginTime[1] = zero;
		_endTime[0] = zero;
		_endTime[1] = zero;
		_priority = zero;
	}

	/**
	 * set the ID of the period
	 * 
	 * @param int
	 *            the ID
	 */
	public void setPeriodId(int id) {
		_periodID = id;
	}

	/**
	 * set the begin time of the period
	 * 
	 * @param int
	 *            the hour
	 * @param int
	 *            the minute
	 */
	public void setBeginTime(int hour, int minute) {
		_beginTime[0] = hour;
		_beginTime[1] = minute;
	}

	/**
	 * set the end time of the period
	 * 
	 * @param int
	 *            the hour
	 * @param int
	 *            the minute
	 */
	public void setEndTime(int hour, int minute) {
		_endTime[0] = hour;
		_endTime[1] = minute;
	}

	/**
	 * set the priority of the periode
	 * 
	 * @param int
	 *            the priority of the period
	 */
	public void setPriority(int prior) {
		_priority = prior;
	}

	/**
	 * get ID of the period
	 * 
	 * @param int
	 *            the ID of the period
	 */
	public int getPeriodId() {
		return _periodID;
	}

	/**
	 * get the begin time of the period
	 * 
	 * @return int[2] the table of the begin time. The in range 0 is the hour
	 *         and the element in the range 1 is the minutes
	 */
	public int[] getBeginTime() {
		return _beginTime;
	}

	/**
	 * get the end time of the period
	 * 
	 * @return int[2] the table of the begin hour. The in range 0 is the hour
	 *         and the element in the range 1 is the minutes
	 */
	public int[] getEndTime() {
		return _endTime;
	}

	/**
	 * get priority of the period
	 * 
	 * @param int
	 *            the priority of the period
	 */
	public int getPriority() {
		return _priority;
	}

	public String toString() {
		HourFormat.setMinimumIntegerDigits(2);
		String str = _periodID + " -- " + HourFormat.format(_beginTime[0])
				+ ":" + HourFormat.format(_beginTime[1]) + " -- "
				+ HourFormat.format(_endTime[0]) + ":"
				+ HourFormat.format(_endTime[1]) + " -- " + _priority;
		return str;
	}

	/**
	 * isEquals checks if this Period is equals to the Period gives in arg
	 * 
	 * @param per
	 *            the Period arg
	 * @return
	 *            <p>
	 *            true if this Period is equals to the Period gives in arg
	 *            </p>
	 *            false otherwise
	 */
	public boolean isEquals(APeriod per) {

		if (_periodID != per.getPeriodId())
			return false;
		if (_beginTime[0] != per.getBeginTime()[0])
			return false;
		if (_beginTime[1] != per.getBeginTime()[1])
			return false;
		if (_endTime[0] != per.getEndTime()[0])
			return false;
		if (_endTime[1] != per.getEndTime()[1])
			return false;
		if (_priority != per.getPriority())
			return false;

		return true;
	}

	/**
	 * Period To xml
	 * 
	 * @param out
	 */

	public void PeriodToXml(PrintStream out) {
		out.println("<TTperiod>");
		out.println("<periodID>" + _periodID + "</periodID>");
		out.print("<BeginTime>" + _beginTime[0] + ":");
		if (_beginTime[1] < dix)
			out.print(zero);
		out.println(_beginTime[1] + "</BeginTime>");
		out.print("<EndTime>" + _endTime[0] + ":");
		if (_endTime[1] < dix)
			out.print(zero);
		out.println(_endTime[1] + "</EndTime>");
		out.println("<priority>" + _priority + "</priority>");
		out.println("</TTperiod>");
	}

}
