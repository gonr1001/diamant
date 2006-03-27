/**
 * Created on Mar 25, 2006
 * 
 * 
 * Title: DxStatusBarModel.java 
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
package dInternal;

import java.util.Vector;

import dConstants.DConst;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxStatusBarModel is a class used to:
 * <p>
 * TODO:insert comments
 * <p> 
 * 
 */
public class DxStateBarModel {
	
	private final int _EMPTY = -1;
	
	private DModel _dm;
	
	private Vector <DxState> _stateVec;
	
	public DxStateBarModel(DModel dm){
		_dm = dm;
		_stateVec = new Vector<DxState>();
		/* 0 */
	    _stateVec.add(new DxState(DConst.SB_TOTAL, DConst.COLOR_BLACK, _EMPTY));
	    /* 1 */
	    _stateVec.add(new DxState(DConst.SB_T_ACT, DConst.COLOR_AUX, _EMPTY));
	    /* 2 */
	    _stateVec.add(new DxState(DConst.SB_T_INST, DConst.COLOR_AUX, _EMPTY));
	    /* 3 */
	    _stateVec.add(new DxState(DConst.SB_T_ROOM, DConst.COLOR_AUX, _EMPTY));
	    /* 4 */
	    _stateVec.add(new DxState(DConst.SB_T_STUD, DConst.COLOR_AUX, _EMPTY));
	    /* 5 */
	    _stateVec.add(new DxState(DConst.SB_T_EVENT, DConst.COLOR_AUX, _EMPTY));
	    /* 6 */
	    _stateVec.add(new DxState(DConst.SB_T_ASSIG, DConst.COLOR_AUX, _EMPTY));
	    /* 7 */
	    _stateVec.add(new DxState(DConst.SB_CONF, DConst.COLOR_BLACK, _EMPTY));
	    /* 8 */
	    _stateVec.add(new DxState(DConst.SB_C_STUD, DConst.COLOR_STUD, _EMPTY));
	    /* 9 */
	    _stateVec.add(new DxState(DConst.SB_C_INST, DConst.COLOR_INST, _EMPTY));
	    /* 10 */
	    _stateVec.add(new DxState(DConst.SB_C_ROOM, DConst.COLOR_ROOM, _EMPTY));
	}

	public void update() {					
		/* 0 */
		_stateVec.elementAt(0).setValue(_EMPTY);
	    /* 1 */
		_stateVec.elementAt(1).setValue(_dm.getSetOfActivities().getIDsByField(3, "true").size());
	    /* 2 */
		_stateVec.elementAt(2).setValue(_dm.getSetOfInstructors().size());
	    /* 3 */
		_stateVec.elementAt(3).setValue(_dm.getSetOfRooms().size());
	    /* 4 */
		_stateVec.elementAt(4).setValue(_dm.getSetOfStudents().size());
	    /* 5 */
		_stateVec.elementAt(5).setValue(_dm.getSetOfEvents().size());
	    /* 6 */
		_stateVec.elementAt(6).setValue(_dm.getSetOfActivities().getIDsByField(3, "true").size());
	    /* 7 */
		_stateVec.elementAt(7).setValue(_dm.getStudentConflicts() + _dm.getInstructorConflicts() + _dm.getRoomConflicts());
	    /* 8 */
		_stateVec.elementAt(8).setValue(_dm.getStudentConflicts());
	    /* 9 */
		_stateVec.elementAt(9).setValue(_dm.getInstructorConflicts());
	    /* 10 */
		_stateVec.elementAt(10).setValue(_dm.getRoomConflicts());        
	}

	public int size() {
		return _stateVec.size();
	}

	public DxState elementAt(int i) {
		return _stateVec.elementAt(i);
	}

}// end class DxStateBarModel
