package dInterface.dAffectation;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import java.awt.event.ActionEvent;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.DlgIdentification;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.DxTools;
import dInterface.dUtil.TwoButtonsPanel;
import eLib.exit.dialog.InformationDlg;

public class EventsDlg extends EventsDlgInterface implements DlgIdentification {
	private String[] _arrowsNames = { DConst.TO_RIGHT, DConst.TO_LEFT, };

	// private boolean _withRoomFunction;

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
	public EventsDlg(DApplication dApplic) {
		super(dApplic, DConst.EVENTS_DLG_TITLE);
		// _withRoomFunction = withRoomFunction;
		buildArrowButtons(true);
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
	public void buildArrowButtons(boolean enableArrows) {
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
		// (e.getSource().equals(_leftArrowsPanel.getComponent(0)))) ||
		// (e.getSource().equals(_leftArrowsPanel.getComponent(1)))) )
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
		// (e.getSource().equals(_rightArrowsPanel.getComponent(0))) ||
		// (e.getSource().equals(_rightArrowsPanel.getComponent(1))) )
		// if Button CLOSE is pressed
		if (e.getActionCommand().equals(DConst.BUT_CLOSE))
			dispose();
		// if Button APPLY is pressed
		if (e.getActionCommand().equals(DConst.BUT_APPLY)) {
			setUnities();
			_dApplic.getCurrentDxDoc().getCurrentDModel().changeInDModel(
					this.idDlgToString());
			_buttonsPanel.setFirstDisable();
		}// end if Button APPLY
	}// end method

	/**
	 * 
	 */
	protected void doubleClicMouseProcess() {
		if (!_buttonsPanel.isFirstEnable()) {
			// if (this._withRoomFunction)
			new EditEventDlg(_jDialog, _dApplic, (String) selectedItems[0],
					this, false);
			// else
			// new EditActivityDlg(_jDialog,_dApplic, (String)selectedItems[0],
			// this, false);
			_buttonsPanel.setFirstDisable();
		} else {
			new InformationDlg(_jDialog, "Appliquer ou fermer pour continuer",
					"Operation interdite");
		}
	} // end doubleClicMouseProcess

	public String idDlgToString() {
		return this.getClass().toString();
	}

}// end EventsDlg
