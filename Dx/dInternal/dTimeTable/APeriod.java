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

import java.sql.Time;

public class APeriod {

	private int _periodID;

	private Time _periodBeginTime;

	private int _periodPriority;

	private String _periodName;

	public int getttperiodperiodid() {
		return _periodID;
	}

	public Time getttperiodbegintime() {
		return _periodBeginTime;
	}

	public int getttperiodpriority() {
		return _periodPriority;
	}

	public String getttperiodname() {
		return _periodName;
	}

	public void setttperiodperiodid(int _ttperiodperiodid) {
		this._periodID = _ttperiodperiodid;
	}

	public void setttperiodbegintime(Time _ttperiodbegintime) {
		this._periodBeginTime = _ttperiodbegintime;
	}

	public void setttperiodpriority(int _ttperiodpriority) {
		this._periodPriority = _ttperiodpriority;
	}

	public void setttperiodname(String _ttperiodname) {
		this._periodName = _ttperiodname;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("TTperiodperiodid : ");
		sb.append(_periodID);		
		sb.append(", ");
		sb.append("TTperiodbegintime : ");
		sb.append(_periodBeginTime);
		sb.append(", ");
		sb.append("TTperiodpriority : ");
		sb.append(_periodPriority);
		sb.append(",");
		sb.append("TTperiodname : ");
		sb.append(_periodName);
		return sb.toString();
	}
}
