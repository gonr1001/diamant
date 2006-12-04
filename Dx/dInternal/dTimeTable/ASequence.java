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

	private int _sequenceID;

	private String _sequenceName;

	public ASequence() {
		
	}

	public int getttsequenceid() {
		return _sequenceID;
	}

	public String getttsequencename() {
		return _sequenceName;
	}

	public void setttsequenceid(int _ttsequenceid) {
		this._sequenceID = _ttsequenceid;
	}

	public void setttsequencename(String _ttsequencename) {
		this._sequenceName = _ttsequencename;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("TTsequenceid : ");
		sb.append(_sequenceID);		
		sb.append(", ");
		sb.append("TTsequencename : ");
		sb.append(_sequenceName);
		return sb.toString();
	}
}
