/**
 * Created on 31-Jan-08
 * 
 * 
 * Title: DxConflictsOfAnEvent.java
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

/**
 * Created on Feb 21, 2007
 * 
 * 
 * Title: DxEventsDlg.java 
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
package dInterface.dAssignementDlgs;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxEventsDlg is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dConstants.DConst;
import dInterface.DlgIdentification;
import dInterface.dAffectation.EventsDlgInterface;
import dInterface.dTimeTable.ConflictsOfAnEventJDlg;
import dInterface.dTimeTable.DxConflictsOfAnEventPanel;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.TwoButtonsPanel;
import dInternal.DModel;
import dInternal.DResource;

@SuppressWarnings("serial")
public class DxConflictsOfAnEventDlg extends EventsDlgInterface implements
		Observer, DlgIdentification {

	private String[] _arrowsNames;

	/**
	 * Constructor
	 * 
	 * @param jFrame
	 *            to place the dialogue
	 * @param dModel
	 *            to have acess to data
	 * 
	 */
	public DxConflictsOfAnEventDlg(JFrame jFrame, DModel dModel) {
		super(jFrame, dModel, DConst.EVENTS_DLG_TITLE);
		_dModel = dModel;
		_arrowsNames = new String[2];
		_arrowsNames[0] = DConst.TO_RIGHT;
		_arrowsNames[1] = DConst.TO_LEFT;
		 buildArrowButtons();
		_dModel.addObserver(this);
		initialize();
	}// end DxConflictsOfAnEventDlg

	public ButtonsPanel setButtons() {
		// _modifyPanel
		String[] a = { DConst.BUT_CHANGE, DConst.BUT_CLOSE };
		_buttonsPanel = new TwoButtonsPanel(this, a);
		return _buttonsPanel;
	}


	public void actionPerformed(ActionEvent e) {
		// if Button CLOSE is pressed
		if (e.getActionCommand().equals(DConst.BUT_CLOSE)) {
			_dModel.deleteObserver(this);
			dispose();

		}
		if ((e.getActionCommand().equals(DConst.BUT_CHANGE))
				&& (selectedItems != null)) {
			new DxEditEventDlg(_jDialog, _dModel, (String) selectedItems[0],
					false);
		}
	}// end actionPerformed

	/**
	 * When double click in an event display ManualImprovmentDetailed
	 */
	protected void doubleClicMouseProcess() {
		DResource eventRes = _dModel.getSetOfEvents().getResource(
				(String) selectedItems[0]);
		new DxConflictsOfAnEventPanel(this, eventRes, _dModel);
	}

	public String idDlgToString() {
		return this.getClass().toString();
	}

	public void update(@SuppressWarnings("unused")
	Observable o, @SuppressWarnings("unused")
	Object arg) {
		this.initializePanel();
	}

	/**
	 * build button to use in the dialog
	 */
	public void buildArrowButtons() {
		_leftArrowsPanel = new JPanel();
		_rightArrowsPanel = new JPanel();
	}

}// end EventsDlg
