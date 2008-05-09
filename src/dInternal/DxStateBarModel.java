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

	private final int _ZERO = 0;

	private DModel _dm;

	private Vector<DxState> _stateVec;

	public DxStateBarModel(DModel dm) {
		_dm = dm;
		_stateVec = new Vector<DxState>();
		/* 0 */
		_stateVec.add(new DxState(DConst.SB_TOTAL, DConst.COLOR_BLACK, _ZERO));
		/* 1 */
		_stateVec.add(new DxState(DConst.SB_T_ACT, DConst.COLOR_AUX, _ZERO));
		/* 2 */
		_stateVec.add(new DxState(DConst.SB_T_INST, DConst.COLOR_AUX, _ZERO));
		/* 3 */
		_stateVec.add(new DxState(DConst.SB_T_ROOM, DConst.COLOR_AUX, _ZERO));
		/* 4 */
		_stateVec.add(new DxState(DConst.SB_T_STUD, DConst.COLOR_AUX, _ZERO));
		/* 5 */
		_stateVec.add(new DxState(DConst.SB_T_EVENT, DConst.COLOR_AUX, _ZERO));
		/* 6 */
		_stateVec.add(new DxState(DConst.SB_T_ASSIG, DConst.COLOR_AUX, _ZERO));
		/* 7 */
		_stateVec.add(new DxState(DConst.SB_CONF, DConst.COLOR_BLACK, _ZERO));
		/* 8 */
		_stateVec.add(new DxState(DConst.SB_C_STUD, DConst.COLOR_STUD, _ZERO));
		/* 9 */
		_stateVec.add(new DxState(DConst.SB_C_INST, DConst.COLOR_INST, _ZERO));
		/* 10 */
		_stateVec.add(new DxState(DConst.SB_C_ROOM, DConst.COLOR_ROOM, _ZERO));
	}

	public void update() {
		/* 0 */
		_stateVec.elementAt(0).setValue(_EMPTY);
		/* 1 */
		_stateVec.elementAt(1).setValue(
				_dm.getSetOfActivities().getIDsByField(3, "true").size());
		/* 2 */
		_stateVec.elementAt(2).setValue(_dm.getDxSetOfInstructors().size());
		/* 3 */
//		if(DxFlags.newRooms)
//		{
			_stateVec.elementAt(3).setValue(_dm.getDxSetOfRooms().size());
//		}else{
//			_stateVec.elementAt(3).setValue(_dm.getSetOfRooms().size());
//		}
		/* 4 */
		_stateVec.elementAt(4).setValue(_dm.getSetOfStudents().size());
		/* 5 */
		_stateVec.elementAt(5).setValue(_dm.getSetOfEvents().size());
		/* 6 */
		_stateVec.elementAt(6).setValue(
				_dm.getSetOfEvents().getNumberOfEventToAssign());
		/* 7 */
		_stateVec.elementAt(7).setValue(
				_dm.getStudentConflicts() + _dm.getInstructorConflicts()
						+ _dm.getRoomConflicts());
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
