/**
 * 
 * Title: ActivityDlg $Revision: 1.69 $ $Date: 2008-02-01 14:31:00 $
 * Description: ActivityDlg is a class used to
 * 
 * 
 * Copyright (c) 2001 by rgr. All rights reserved.
 * 
 * 
 * This software is the confidential and proprietary information of rgr.
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with rgr.
 * 
 * @version $Revision: 1.69 $
 * @author $Author: gonzrubi $
 * @since JDK1.3
 */
package dInterface.dAffectation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import dConstants.DConst;
import dInterface.DlgIdentification;
import dInterface.dAssignementDlgs.DxEditEventDlg;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.DxTools;
import dInterface.dUtil.TwoButtonsPanel;
import dInternal.DModel;
import dInternal.dData.dActivities.SetOfActivities;


@SuppressWarnings("serial")
public class ActivityDlg extends JDialog implements ActionListener,
		DlgIdentification {

	// private DApplication _dApplic;

	private JDialog _jd;

	/**
	 * the lists containing the activities ID
	 */
	private JLabel _lVisible;

	private JLabel _lNoVisible;

	private JList _rightList;

	private JList _leftList;

	private ButtonsPanel _buttonsPanel;

	private Object[] _currentActivities = new Object[0];

	private SetOfActivities _activities;

	private String[] _arrowsNames = { DConst.TO_RIGHT, DConst.TO_LEFT };

	/**
	 * the vectors containing the activities ID
	 */
	private Vector _rightVec;

	private Vector _leftVec;

	private DModel _dModel;

	/**
	 * Default constructor
	 * 
	 * @param dApplic
	 *            The application object (for extracting the JFrame)
	 */

	// public ActivityDlg(DApplication dApplic) {
	// super(dApplic.getJFrame(), DConst.ACT_LIST, true);
	// _dApplic = dApplic;
	// _jd = this; // to pass this dialog to the EditActivityDlg
	//
	// // if (dApplic.getCurrentDxDoc() == null)
	// // return;
	// _dModel = dApplic.getCurrentDModel();
	//
	// _activities = _dModel.getSetOfActivities();
	//
	// initialize();
	// int x = _dApplic.getJFrame().getX();
	// int y = _dApplic.getJFrame().getY();
	// this.setLocation(x + DConst.X_OFFSET, y + DConst.Y_OFFSET);
	//
	// this.pack();
	// this.setResizable(false);
	// this.setVisible(true);
	// }
	public ActivityDlg(JFrame jFrame, DModel dModel) {
		super(jFrame, DConst.ACT_LIST, true);
		// _dApplic = dApplic;
		_jd = this; // to pass this dialog to the EditActivityDlg

		// if (dApplic.getCurrentDxDoc() == null)
		// return;
		_dModel = dModel;

		_activities = _dModel.getSetOfActivities();

		initialize();
		int x = jFrame.getX();
		int y = jFrame.getY();
		this.setLocation(x + DConst.X_OFFSET, y + DConst.Y_OFFSET);

		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}

	public Dimension getMinimumSize() {
		return new Dimension(300, 300);
	}

	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}

	public Dimension getMaximumSize() {
		return new Dimension(500, 500);
	}

	/**
	 * Initialize the dialog
	 */
	protected void initialize() {
		// right panel
		_rightVec = _activities.getIDsByField(3, "false");
		_rightList = new JList(_rightVec);
		_rightList.addMouseListener(mouseListenerLists);

		JPanel listPanel = DxTools.listPanel(_rightList);
		_lNoVisible = new JLabel(_rightVec.size() + " " + DConst.NOT_INCLUDED);
		JPanel rightPanel = new JPanel(new BorderLayout());
		listPanel.setMinimumSize(new Dimension(100, 100));
		listPanel.setPreferredSize(new Dimension(100, 300));
		listPanel.setMaximumSize(new Dimension(100, 400));
		rightPanel.add(_lNoVisible, BorderLayout.NORTH);
		rightPanel.add(listPanel, BorderLayout.SOUTH);
		// left panel
		_leftVec = _activities.getIDsByField(3, "true");
		_leftList = new JList(_leftVec);
		_leftList.addMouseListener(mouseListenerLists);
		_lVisible = new JLabel(_leftVec.size() + " " + DConst.INCLUDED);
		JPanel listPanelLeft = DxTools.listPanel(_leftList);
		JPanel leftPanel = new JPanel();
		listPanelLeft.setMinimumSize(new Dimension(100, 100));
		listPanelLeft.setPreferredSize(new Dimension(100, 300));
		listPanelLeft.setMaximumSize(new Dimension(100, 400));
		leftPanel = new JPanel(new BorderLayout());
		leftPanel.add(_lVisible, BorderLayout.NORTH);
		leftPanel.add(listPanelLeft, BorderLayout.CENTER);
		// arrows panel
		JPanel arrowsPanel = DxTools.arrowsPanel(this, _arrowsNames, true);
		// placing the panels and buttons into the _listsPanel
		JPanel centerPanel = new JPanel();
		centerPanel.add(leftPanel, BorderLayout.EAST);
		centerPanel.add(arrowsPanel, BorderLayout.CENTER);
		centerPanel.add(rightPanel, BorderLayout.WEST);
		// _applyPanel
		String[] a = { DConst.BUT_APPLY, DConst.BUT_CLOSE };
		_buttonsPanel = new TwoButtonsPanel(this, a);
		// Setting the button APPLY disable
		_buttonsPanel.setFirstDisable();
		// placing the elements into the JDialog
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		getContentPane().add(_buttonsPanel, BorderLayout.SOUTH);
	}// end initialize

	/**
	 * Define the mouse adapter and actions for the JList is
	 */
	private MouseListener mouseListenerLists = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			if (((JList) e.getSource()).getModel().getSize() == 0)
				return;
			if (e.getSource().equals(_leftList))
				_rightList.clearSelection();
			else
				_leftList.clearSelection();
			_currentActivities = ((JList) e.getSource()).getSelectedValues();
			if (e.getClickCount() == 2) {
//				if (DxFlags.newDxEditEventDlg) {
					new DxEditEventDlg(_jd, _dModel,
							(String) _currentActivities[0], false);
//				} else {
//					new EditEventDlg(_jd, _dModel, /* _dApplic, */
//					(String) _currentActivities[0], false);
//				}

			}// end if
		}// end public void mouseClicked
	};// end definition of MouseListener mouseListener = new MouseAdapter(){

	/**
	 * 
	 * @param e
	 *            an event
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		// if buttons CLOSE
		if (command.equals(DConst.BUT_CLOSE))
			dispose();
		// if button APPLY
		if (command.equals(DConst.BUT_APPLY)) {
			setActivitesVisibility();
			// if (DxFlags.newDoc) {
			// _dmodel.changeInDModel(this.idDlgToString());
			// } else {
			_dModel.changeInDModelByActivity(this.idDlgToString());
			// }
			_buttonsPanel.setFirstDisable();
		}
		// if arrows
		if (command.equals(_arrowsNames[0]) || command.equals(_arrowsNames[1])) {
			if (command.equals(_arrowsNames[1])) {
				DxTools.listTransfers(_rightList, _leftList, _rightVec,
						_leftVec, 1);
			} else {
				DxTools.listTransfers(_leftList, _rightList, _leftVec,
						_rightVec, 1);
			}
			_lNoVisible.setText(_rightVec.size() + " " + DConst.NOT_INCLUDED);
			_lVisible.setText(_leftVec.size() + " " + DConst.INCLUDED);
			_buttonsPanel.setFirstEnable();
		}// end if (command.equals(_arrowsNames[0]) ||
		// command.equals(_arrowsNames[1]))
	}// end method

	/**
	 * Sets the field "Visible" of the activities, according with their position
	 * in the JLists. If an activity is in the _rightList, Visible = false.
	 */
	private void setActivitesVisibility() {
		_activities.setByField(_leftVec, 3, "true");
		_activities.setByField(_rightVec, 3, "false");
	}

	public String idDlgToString() {
		return this.getClass().toString();
	}

}// end class
