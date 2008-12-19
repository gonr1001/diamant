/**
 * Created on May 23, 2006
 * 
 * 
 * Title: DxSiteReader.java 
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
 */

package dInternal.dData.dInstructors;

import eLib.exit.exception.DxException;

public interface DxInstructorsReader {

	public DxSetOfInstructors readSetOfInstructors() throws DxException;

	public int getLines();

}
