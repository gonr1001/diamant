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

public class ADay {

	private int ttdayid;

	private String ttdayname;

	public ADay() {
	}

	public int getttdayid() {
		return ttdayid;
	}

	public String getttdayname() {
		return ttdayname;
	}

	public void setttdayid(int _ttdayid) {
		this.ttdayid = _ttdayid;
	}

	public void setttdayname(String _ttdayname) {
		this.ttdayname = _ttdayname;
	}

	public String toString() {
		return new StringBuffer("TTdayid : ").append(ttdayid).append(", ")
				.append("TTdayname : ").append(ttdayname).toString();
	}
}
