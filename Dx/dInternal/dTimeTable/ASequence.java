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

public class ASequence {

	private int ttsequenceid;

	private String ttsequencename;

	public ASequence() {
		//a
	}

	public int getttsequenceid() {
		return ttsequenceid;
	}

	public String getttsequencename() {
		return ttsequencename;
	}

	public void setttsequenceid(int _ttsequenceid) {
		this.ttsequenceid = _ttsequenceid;
	}

	public void setttsequencename(String _ttsequencename) {
		this.ttsequencename = _ttsequencename;
	}

	public String toString() {
		return new StringBuffer("TTsequenceid : ").append(ttsequenceid).append(
				", ").append("TTsequencename : ").append(ttsequencename)
				.toString();
	}
}
