/**
 * Created on May 11, 2006
 * 
 * 
 * Title: DxReadInstructors.java 
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
package dInternal.dData.dInstructors;

import dConstants.DConst;
import dInternal.DataExchange;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxReadInstructors is a class used to:
 * <p>
 * Retreive a SetOfInstructors no mather what file format is currently used.
 * This is also an implementation of the Strategy Pattern, since this class will
 * delegate its work to the proper reader after determining the version.
 * <p>
 * 
 */
public class DxReadInstructors {
	private DxReadInstructorsBehavior _dxribVersion;

	public DxSetOfInstructors getSetOfInstructors(DataExchange de) {
		if (de.getHeader().equalsIgnoreCase(DConst.FILE_VER_NAME1_5)) {
			_dxribVersion = new DxReadInstructors1dot5();
		}
		/*
		 * else if(de.getHeader().equalsIgnoreCase(DConst.FILE_VER_NAME1_6))
		 * 
		 */
		// try{
		if (_dxribVersion.analyseTokens(de)) {
			return _dxribVersion.buildSetOfInstructors(de);
		}
		return null;

	}
}
