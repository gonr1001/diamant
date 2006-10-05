/**
 * Created on 2006-09-29
 * 
 * Title: DxReadStudents1dot5.java 
 * 
 *
 * Copyright (c) 2006 by rgr.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information
 * of rgr. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with rgr.
 *
 * @author HAROUNA Abdoul-Kader
 * @since JDK1.3
 */
package dInternal.dData.dStudents;

import dInternal.DataExchange;
import eLib.exit.exception.DxException;

public class DxReadStudents1dot5 implements DxStudentsReader {

	private DataExchange _deStudents;
    public DxReadStudents1dot5(DataExchange de, int nDays, int nPeriods) {
		_deStudents = de;
	}

	public DxSetOfStudents readSetOfStudents() throws DxException {
		DxSetOfStudents dxsosStud = new DxSetOfStudents();
		
		return dxsosStud;
	}

}
