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

public class ACycle {

	private String _cycleID, _cyclePeriodLength, _cycleTypeID, _cycleName;

	
	public ACycle() {
	/* */
	}

	public String getttcycleid() {
		return _cycleID;
	}

	public String getttcyclename() {
		return _cycleName;
	}

	public String getttcycleperiodlength() {
		return _cyclePeriodLength;
	}

	public String getttcycletypeid() {
		return _cycleTypeID;
	}

	public void setttcycleid(String ttcycleid) {
		this._cycleID = ttcycleid;
	}

	public void setttcyclename(String _ttcyclename) {
		this._cycleName = _ttcyclename;
	}

	public void setttcycleperiodlength(String ttcycleperiodlength) {
		this._cyclePeriodLength = ttcycleperiodlength;
	}

	public void setttcycletypeid(String _ttcycletypeid) {
		this._cycleTypeID = _ttcycletypeid;
	}

	/*
	 * public String getDayAtcycle(int position) { return
	 * this.days[position].toString(); }
	 * 
	 * int pos = this.days.length;
	 * 
	 * public void setDayAtcycle(ADay aday) { this.days[pos] = aday; }
	 */

	public String toString() {
		StringBuffer sb = new StringBuffer("TTcycleid : ");
		sb.append(_cycleID);		
		sb.append(", ");
		sb.append("TTcyclename : ");
		sb.append(_cycleName);
		sb.append(", ");
		sb.append("TTcycleperiodlength : ");
		sb.append(_cyclePeriodLength);
		sb.append(",");
		sb.append("TTcycletypeid : ");
		sb.append(_cycleTypeID);
		return sb.toString();
	}
}
