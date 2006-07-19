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

	private String ttcycleid, ttcycleperiodlength, ttcycletypeid, ttcyclename;

	/*
	 * private int ttcycleid;
	 * 
	 * private String ttcyclename;
	 * 
	 * private Time ttcycleperiodlength;
	 * 
	 * private int ttcycletypeid;
	 */
	// private ADay[] days;
	public ACycle() {
	}

	public String getttcycleid() {
		return ttcycleid;
	}

	public String getttcyclename() {
		return ttcyclename;
	}

	public String getttcycleperiodlength() {
		return ttcycleperiodlength;
	}

	public String getttcycletypeid() {
		return ttcycletypeid;
	}

	public void setttcycleid(String ttcycleid) {
		this.ttcycleid = ttcycleid;
	}

	public void setttcyclename(String _ttcyclename) {
		this.ttcyclename = _ttcyclename;
	}

	public void setttcycleperiodlength(String ttcycleperiodlength) {
		this.ttcycleperiodlength = ttcycleperiodlength;
	}

	public void setttcycletypeid(String _ttcycletypeid) {
		this.ttcycletypeid = _ttcycletypeid;
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
		return new StringBuffer("TTcycleid : ").append(ttcycleid).append(", ")
				.append("TTcyclename : ").append(ttcyclename).append(", ")
				.append("TTcycleperiodlength : ").append(ttcycleperiodlength)
				.append(",").append("TTcycletypeid : ").append(ttcycletypeid)
				.toString();
	}
}
