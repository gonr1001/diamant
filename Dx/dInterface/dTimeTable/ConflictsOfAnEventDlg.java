/**
 *
 * Title: ConflictsOfAnEventDlg $Revision: 1.15 $  $Date: 2008-02-01 13:50:22 $
 * Description: ConflictsOfAnEventDlg is a class used to
 *
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
 * @version $Revision: 1.15 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInterface.dTimeTable;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dConstants.DConst;
import dInterface.DToolBar; // import dInterface.dAffectation.EditEventDlg;
import dInterface.dAffectation.EventsDlgInterface;
import dInterface.dAssignementDlgs.DxEditEventDlg;
import dInterface.dAssignementDlgs.EditEventDlg;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.TwoButtonsPanel;

import dInternal.DModel;
import dInternal.DResource;
import developer.DxFlags;

public class ConflictsOfAnEventDlg extends EventsDlgInterface {
	private DModel _dm;
	private DToolBar _toolBar;

	/**
	 * Constructor
	 * 
	 * @param dApplic
	 *            The application
	 */
	public ConflictsOfAnEventDlg(JFrame jFrame, DModel dModel) {
		super(jFrame,dModel, DConst.EVENTS_DLG_TITLE);
		// @todo RGR _toolBar= dApplic.getToolBar();
		_dm = dModel;
		buildArrowButtons();// false);
		initialize();
	}// end method

	public ButtonsPanel setButtons() {
		// _applyPanel
		String[] a = { DConst.BUT_CHANGE, DConst.BUT_CLOSE };
		_buttonsPanel = new TwoButtonsPanel(this, a);
		return _buttonsPanel;
	}

	/**
	 * build button to use in the dialog
	 */
	public void buildArrowButtons() {// boolean enableArrows) {
		_leftArrowsPanel = new JPanel();
		_rightArrowsPanel = new JPanel();
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		// if Button CLOSE is pressed
		if (command.equals(DConst.BUT_CLOSE))
			dispose();
		if ((command.equals(DConst.BUT_CHANGE)) && (selectedItems != null)) {			
			if(DxFlags.newDxEditEventDlg) {
				new DxEditEventDlg(_jDialog, _dModel, (String) selectedItems[0],
						false);
			} else {
				new EditEventDlg(_jDialog, _dModel, (String) selectedItems[0],
						false);
			}
		}
	}// end actionPerformed

	/**
	 * When double click in an event display ManualImprovmentDetailed
	 */
	protected void doubleClicMouseProcess() {
		DResource event = _dm.getSetOfEvents().getResource(
				(String) selectedItems[0]);
		new ConflictsOfAnEventJDlg(this, _toolBar, event, _dm);
	}
}// end class ConflictsOfAnEventDlg
