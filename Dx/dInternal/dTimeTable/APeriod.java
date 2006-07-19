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

	private int ttperiodperiodid;

	private Time ttperiodbegintime;

	private int ttperiodpriority;

	private String ttperiodname;

	public APeriod() {
	}

	public int getttperiodperiodid() {
		return ttperiodperiodid;
	}

	public Time getttperiodbegintime() {
		return ttperiodbegintime;
	}

	public int getttperiodpriority() {
		return ttperiodpriority;
	}

	public String getttperiodname() {
		return ttperiodname;
	}

	public void setttperiodperiodid(int _ttperiodperiodid) {
		this.ttperiodperiodid = _ttperiodperiodid;
	}

	public void setttperiodbegintime(Time _ttperiodbegintime) {
		this.ttperiodbegintime = _ttperiodbegintime;
	}

	public void setttperiodpriority(int _ttperiodpriority) {
		this.ttperiodpriority = _ttperiodpriority;
	}

	public void setttperiodname(String _ttperiodname) {
		this.ttperiodname = _ttperiodname;
	}

	public String toString() {
		return new StringBuffer("TTperiodperiodid : ").append(ttperiodperiodid)
				.append(", ").append("TTperiodbegintime : ").append(
						ttperiodbegintime).append(", ").append(
						"TTperiodpriority : ").append(ttperiodpriority).append(
						",").append("TTperiodname : ").append(ttperiodname)
				.toString();
	}
}
