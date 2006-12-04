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

	private int _dayID;

	private String _dayName;

	public ADay() {
		//a
	}

	public int getttdayid() {
		return _dayID;
	}

	public String getttdayname() {
		return _dayName;
	}

	public void setttdayid(int _ttdayid) {
		this._dayID = _ttdayid;
	}

	public void setttdayname(String _ttdayname) {
		this._dayName = _ttdayname;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("TTdayid : ");
		sb.append(_dayID);		
		sb.append(", ");
		sb.append("TTdayname : ");
		sb.append(_dayName);
		return sb.toString();
	}
}
