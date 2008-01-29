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

import dConstants.DConst;
import dInterface.DlgIdentification;
import dInterface.dAffectation.EventsDlgInterface;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.DxTools;
import dInterface.dUtil.TwoButtonsPanel;
import dInternal.DModel;
import developer.DxFlags;
import eLib.exit.dialog.InformationDlg;

public class DxEventsDlg extends EventsDlgInterface implements Observer, DlgIdentification {
	
	private String[] _arrowsNames;


	/**
	 * Constructor
	 * 
	 * @param dApplic
	 *            The application
	 * @param String
	 *            the title of the dialog
	 * @boolean if false use EditActivityDld (the old dialog without room
	 *          function and room state) else if true use EditEventDlg (the new
	 *          dialog with room function and room state)
	 */
//	public DxEventsDlg(DApplication dApplic) {
//		super(dApplic, DConst.EVENTS_DLG_TITLE);
//		_arrowsNames = new String [2];		
//		_arrowsNames [0] = DConst.TO_RIGHT;
//		_arrowsNames [1] = DConst.TO_LEFT;
//		buildArrowButtons(true);
//		_dApplic.getCurrentDModel().addObserver(this);
//		initialize();
//	}// end method
	
	public DxEventsDlg(JFrame jFrame, DModel dModel) {
		super(jFrame,dModel, DConst.EVENTS_DLG_TITLE);
		_dModel =dModel;
		_arrowsNames = new String [2];		
		_arrowsNames [0] = DConst.TO_RIGHT;
		_arrowsNames [1] = DConst.TO_LEFT;
		buildArrowButtons(); //true);
		_dModel.addObserver(this);
		initialize();
	}// end method


	public ButtonsPanel setButtons() {
		// _applyPanel
		String[] a = { DConst.BUT_APPLY, DConst.BUT_CLOSE };
		_buttonsPanel = new TwoButtonsPanel(this, a);
		// Setting the button APPLY disable
		_buttonsPanel.setFirstDisable();
		return _buttonsPanel;
	}

	/**
	 * build buttom to use in the dialog
	 */
	public void buildArrowButtons() {
		boolean enableArrows = true;
		_leftArrowsPanel = DxTools.arrowsPanel(this, "left", _arrowsNames,
				enableArrows);
		_rightArrowsPanel = DxTools.arrowsPanel(this, "right", _arrowsNames,
				enableArrows);
	}

	public void actionPerformed(ActionEvent e) {
		if ((e.getActionCommand().equalsIgnoreCase("left" + _arrowsNames[0]))
				|| (e.getActionCommand().equalsIgnoreCase("left"
						+ _arrowsNames[1]))) {
			// if "toRight" button
			if (e.getActionCommand().equalsIgnoreCase("left" + _arrowsNames[0]))
				DxTools.listTransfers(_leftList, _centerList, _leftVector,
						_centerVector, 1);
			else
				DxTools.listTransfers(_centerList, _leftList, _centerVector,
						_leftVector, 1);

			_leftLabel.setText(String.valueOf(_leftVector.size()));
			_centerLabel.setText(String.valueOf(_centerVector.size()));
			_buttonsPanel.setFirstEnable();
		}// end if (

		// if the source is one of the the _rightArrowsPanel buttons
		if ((e.getActionCommand().equalsIgnoreCase("right" + _arrowsNames[0]))
				|| (e.getActionCommand().equalsIgnoreCase("right"
						+ _arrowsNames[1]))) {
			// if "toRight" button
			if (e.getActionCommand()
					.equalsIgnoreCase("right" + _arrowsNames[0]))
				DxTools.listTransfers(_centerList, _rightList, _centerVector,
						_rightVector, 1);
			else
				DxTools.listTransfers(_rightList, _centerList, _rightVector,
						_centerVector, 1);
			_rightLabel.setText(String.valueOf(_rightVector.size()));
			_centerLabel.setText(String.valueOf(_centerVector.size()));
			_buttonsPanel.setFirstEnable();
		}// end if (
		// if Button CLOSE is pressed
		if (e.getActionCommand().equals(DConst.BUT_CLOSE)){
			_dModel.deleteObserver(this);
			dispose();
			
		}
		// if Button APPLY is pressed
		if (e.getActionCommand().equals(DConst.BUT_APPLY)) {
			setUnities();
			_dModel.changeInDModel(
					this.idDlgToString());
			_buttonsPanel.setFirstDisable();
		}// end if Button APPLY
	}// end method

	/**
	 * 
	 */
	protected void doubleClicMouseProcess() {
		if (!_buttonsPanel.isFirstEnable()) {			
			if(DxFlags.newDxEditEventDlg) {
				new DxEditEventDlg(_jDialog, _dModel, (String) selectedItems[0],
						/*this,*/ false);
			} else {
				new EditEventDlg(_jDialog, _dModel, (String) selectedItems[0],
						/*this,*/ false);
			}
		
			_buttonsPanel.setFirstDisable();
		} else {
			new InformationDlg(_jDialog, "Appliquer ou fermer pour continuer",
					"Operation interdite");
		}
	} // end doubleClicMouseProcess

	public String idDlgToString() {
		return this.getClass().toString();
	}

	public void update(@SuppressWarnings("unused")
	Observable o, @SuppressWarnings("unused")
	Object arg) {
		this.initializePanel();		
	}

}// end EventsDlg
