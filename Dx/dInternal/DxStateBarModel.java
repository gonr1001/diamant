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
	
	private Vector <DxState> _al;
	
	public DxStateBarModel(DModel dm){
		_dm = dm;
		_al = new Vector<DxState>();
		/* 0 */
	    _al.add(new DxState(DConst.SB_TOTAL, DConst.COLOR_BLACK, _EMPTY));
	    /* 1 */
	    _al.add(new DxState(DConst.SB_T_ACT, DConst.COLOR_AUX, _EMPTY));
	    /* 2 */
	    _al.add(new DxState(DConst.SB_T_INST, DConst.COLOR_AUX, _EMPTY));
	    /* 3 */
	    _al.add(new DxState(DConst.SB_T_ROOM, DConst.COLOR_AUX, _EMPTY));
	    /* 4 */
	    _al.add(new DxState(DConst.SB_T_STUD, DConst.COLOR_AUX, _EMPTY));
	    /* 5 */
	    _al.add(new DxState(DConst.SB_T_EVENT, DConst.COLOR_AUX, _EMPTY));
	    /* 6 */
	    _al.add(new DxState(DConst.SB_T_ASSIG, DConst.COLOR_AUX, _EMPTY));
	    /* 7 */
	    _al.add(new DxState(DConst.SB_CONF, DConst.COLOR_BLACK, _EMPTY));
	    /* 8 */
	    _al.add(new DxState(DConst.SB_C_STUD, DConst.COLOR_STUD, _EMPTY));
	    /* 9 */
	    _al.add(new DxState(DConst.SB_C_INST, DConst.COLOR_INST, _EMPTY));
	    /* 10 */
	    _al.add(new DxState(DConst.SB_C_ROOM, DConst.COLOR_ROOM, _EMPTY));
	}

	public void update() {	
				
		/* 0 */
		_al.elementAt(0).setValue(_EMPTY);
	    /* 1 */
		_al.elementAt(1).setValue(_dm.getSetOfActivities().getIDsByField(3, "true").size());
	    /* 2 */
		_al.elementAt(2).setValue(_dm.getSetOfInstructors().size());
	    /* 3 */
		_al.elementAt(3).setValue(_dm.getSetOfRooms().size());
	    /* 4 */
		_al.elementAt(4).setValue(_dm.getSetOfStudents().size());
	    /* 5 */
		_al.elementAt(5).setValue(_dm.getSetOfEvents().size());
	    /* 6 */
		_al.elementAt(6).setValue(_dm.getSetOfActivities().getIDsByField(3, "true").size());
	    /* 7 */
		_al.elementAt(7).setValue(_dm.getSetOfEvents().getNumberOfEventAssign());
	    /* 8 */
		_al.elementAt(8).setValue(_dm.getStudentConflicts());
	    /* 9 */
		_al.elementAt(9).setValue(_dm.getInstructorConflicts());
	    /* 10 */
		_al.elementAt(10).setValue(_dm.getRoomConflicts());        
	}

	public int size() {
		return _al.size();
	}

	public DxState elementAt(int i) {
		return _al.elementAt(i);
	}

}// end class DxStateBarModel
