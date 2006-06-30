/**
 * Created on Jun 27, 2006
 * 
 * 
 * Title: DxTTCycleDoc.java 
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
package dInterface;

import java.util.Observable;

import dInternal.DModel;
import dInternal.dTimeTable.TTStructure;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxTTCycleDoc is a class used to:
 * <p>
 * TODO:insert comments
 * <p> 
 * 
 */
public class DxTTCycleDoc extends DxDocument{

	public DxTTCycleDoc(DMediator mediator, String ttName, String fileName) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Observable ttS, Object component) {
        if (component != null)
            component.toString();
        _dMediator.getDApplication().setCursorWait();
        _ttPane.updateTTPane((TTStructure) ttS);
        //_stateBar.upDate();
        _dMediator.getDApplication().setCursorDefault();
	}

	@Override
	public TTStructure getTTStructure() {
		// TODO Auto-generated method stub
		return null;
	}

}
