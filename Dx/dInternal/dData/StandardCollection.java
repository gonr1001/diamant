/**
 *
 * Title: StandardCollection 
 * Description: 
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
package dInternal.dData;

import dConstants.DConst;
import dInternal.DResource;
import dInternal.DSetOfResources;

public class StandardCollection extends DSetOfResources {

	/**
	 * 
	 */
	public StandardCollection() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInternal.DSetOfResources#getError()
	 */
	public String getError() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInternal.DObject#toWrite()
	 */
	public String toWrite() {
		String reslist = "";
		for (int i = 0; i < size() - 1; i++)
			reslist += ((DResource) this.getSetOfResources().get(i))
					.toWrite(";")
					+ DConst.CR_LF;
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInternal.DObject#getSelectedField()
	 */
	public long getSelectedField() {
		// TODO Auto-generated method stub
		return 0;
	}

}
