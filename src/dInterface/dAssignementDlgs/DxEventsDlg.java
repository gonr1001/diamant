/**
 * Created on Feb 21, 2007, Copied 20 mai 2008s
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
 * Display the dialog showing the events,
 * The events are in three JLists 
 * fixed, assigned or no-assigned
 * <p>
 * 
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.DlgIdentification;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.DxTools;
import dInterface.dUtil.TwoButtonsPanel;
import dInternal.dData.dActivities.Unity;
import dInternal.dOptimization.DxEvent;
import dInternal.dUtil.DXToolsMethods;
import eLib.exit.dialog.InformationDlg;

public class DxEventsDlg extends DxEventsGUIforDlg  implements ActionListener, Observer,
		DlgIdentification {

	private JLabel _labelForFixed; 	
	
	private JLabel _labelForAssigned;
	
	private JLabel _labelForNoAssigned;

	private JList _fixedList;
	
	private JList _assignedList;
	
	private JList _noAssignedList;
	
	private Vector<String> _fixedVector;

	private Vector<String> _assignedVector;

	private Vector<String> _noAssignedtVector;

	private JPanel _rightArrowsPanel;
	
	private JPanel _leftArrowsPanel;
	
	private String[] _arrowsNames;

//	private ButtonsPanel _buttonsPanel;
//
//	private SetOfActivities _activities;
//
//	private DModel _dModel;


	public DxEventsDlg(DApplication dApplic) {
		super(dApplic.getJFrame(), DConst.EVENTS_DLG_TITLE, true);

		_dModel = dApplic.getCurrentDModel();
		_activities = _dModel.getSetOfActivities();
		_dModel.addObserver(this);
		_arrowsNames = new String[2];
		_arrowsNames[0] = DConst.TO_RIGHT;
		_arrowsNames[1] = DConst.TO_LEFT;
		buildArrowButtons(); // true);
		initialize(dApplic);
	}// end DxConflictsOfAnEventDlg

	
	
	public void actionPerformed(ActionEvent e) {
		if ((e.getActionCommand().equalsIgnoreCase("left" + _arrowsNames[0]))
				|| (e.getActionCommand().equalsIgnoreCase("left"
						+ _arrowsNames[1]))) {
			// if "toRight" button
			if (e.getActionCommand().equalsIgnoreCase("left" + _arrowsNames[0])) {
				DxTools.listTransfers(_fixedList, _assignedList, _fixedVector,
						_assignedVector, 1);
			} else {
				DxTools.listTransfers(_assignedList, _fixedList, _assignedVector,
						_fixedVector, 1);
			}
			_labelForFixed.setText(String.valueOf(_fixedVector.size()));
			_labelForAssigned.setText(String.valueOf(_assignedVector.size()));
			_buttonsPanel.setFirstEnable();
		}// end if (

		// if the source is one of the the _rightArrowsPanel buttons
		if ((e.getActionCommand().equalsIgnoreCase("right" + _arrowsNames[0]))
				|| (e.getActionCommand().equalsIgnoreCase("right"
						+ _arrowsNames[1]))) {
			// if "toRight" button
			if (e.getActionCommand()
					.equalsIgnoreCase("right" + _arrowsNames[0])) {
				DxTools.listTransfers(_assignedList, _noAssignedList, _assignedVector,
						_noAssignedtVector, 1);
			} else {
				DxTools.listTransfers(_noAssignedList, _assignedList, _noAssignedtVector,
						_assignedVector, 1);
			}
			_labelForNoAssigned.setText(String.valueOf(_noAssignedtVector.size()));
			_labelForAssigned.setText(String.valueOf(_assignedVector.size()));
			_buttonsPanel.setFirstEnable();
		}// end if (
		
		if (e.getActionCommand().equals(DConst.BUT_CLOSE)) {
			_dModel.deleteObserver(this);
			dispose();
		}
	
		if (e.getActionCommand().equals(DConst.BUT_APPLY)) {
			setUnities();
			_dModel.changeInDModel(this.idDlgToString());
			_fixedList.clearSelection();
			_noAssignedList.clearSelection();
			_assignedList.clearSelection();
			_buttonsPanel.setFirstDisable();
		}// end if Button APPLY
	}// end method

	/**
	 * Initialize the dialog
	 */
	protected void initialize(DApplication dApplic) {
		this.getContentPane().setLayout(new BorderLayout());
		
		setResizable(false);
		buildVectors();

		
		JPanel leftPanel = initLeftPanel();
		JPanel centerPanel = initCenterPanel();
		JPanel rightPanel = initRightPanel();

		_buttonsPanel = setButtons();

		this.getContentPane().add(leftPanel, BorderLayout.WEST);
		this.getContentPane().add(rightPanel, BorderLayout.EAST);
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);

		this.getContentPane().add(_buttonsPanel, BorderLayout.SOUTH);
		this.pack();
		this.setLocationRelativeTo(dApplic.getJFrame());

		this.setResizable(false);
		this.setVisible(true);
	}

	/**
	 * Sets the _centerPanel, the panel containing the _centerList and the
	 * arrows panels
	 */
	private JPanel initCenterPanel() {
		_assignedList = new JList(_assignedVector);
		class CenterMouseListener extends MouseAdapter {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					doubleClicMouseProcess(_assignedList);
				}// end if
				if (e.getClickCount() == 1) {
					_fixedList.clearSelection();
					_noAssignedList.clearSelection();
				}// end if
			}
		}
		MouseAdapter CenterMouseListener = new CenterMouseListener();
		_assignedList.addMouseListener(CenterMouseListener);
		_assignedList
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		JLabel titleLabel = new JLabel(DConst.EVENTS_PLACED + " ");
		_labelForAssigned = new JLabel(String.valueOf(_assignedVector.size()));
		_labelForAssigned.setForeground(DConst.COLOR_QUANTITY_DLGS);
		// The listContainerPanel
		JPanel listPanel = DxTools.listPanel(_assignedList);
		listPanel.setPreferredSize(new Dimension(WIDTH_PANE, HEIGHT_PANE));
		JPanel listContainerPanel = new JPanel();

		listContainerPanel.add(titleLabel);
		listContainerPanel.add(_labelForAssigned);
		listContainerPanel.add(listPanel);

		// the _centerPanel
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel miPanel = new JPanel();
		miPanel.add(_leftArrowsPanel);
		miPanel.add(listContainerPanel);
		miPanel.add(_rightArrowsPanel);

		JPanel centerPanelTop = new JPanel();
		centerPanelTop.add(titleLabel);
		centerPanelTop.add(_labelForAssigned);
		panel.add(centerPanelTop, BorderLayout.NORTH);
		panel.add(miPanel, BorderLayout.CENTER);
		return panel;
	}// end method

	/**
	 * initLeftPanel
	 */
	private JPanel initLeftPanel() {
		_fixedList = new JList(_fixedVector);
		class LeftMouseListener extends MouseAdapter {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					doubleClicMouseProcess(_fixedList);
				}// end if
				 if (e.getClickCount() == 1) {
						 _assignedList.clearSelection();
						 _noAssignedList.clearSelection();
				 }// end if
			}
		}
		MouseAdapter LeftMouseListener = new LeftMouseListener();
		_fixedList.addMouseListener(LeftMouseListener);
		_fixedList
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JLabel titleLabel = new JLabel(DConst.EVENTS_FIXED + " ");
		_labelForFixed = new JLabel(String.valueOf(_fixedVector.size()));
		_labelForFixed.setForeground(DConst.COLOR_QUANTITY_DLGS);

		JPanel listPanel = DxTools.listPanel(_fixedList);
		listPanel.setPreferredSize(new Dimension(WIDTH_PANE, HEIGHT_PANE));
		// the _leftPanel
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JPanel panelTop = new JPanel();
		panelTop.add(titleLabel);
		panelTop.add(_labelForFixed);

		panel.add(panelTop, BorderLayout.NORTH);
		panel.add(listPanel, BorderLayout.CENTER);

		return panel;
	}// end initLeftPanel

	/**
	 * Sets the _initRigthPanel
	 */
	private JPanel initRightPanel() {
		_noAssignedList = new JList(_noAssignedtVector);
		_noAssignedList
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		class RightMouseListener extends MouseAdapter {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					doubleClicMouseProcess(_noAssignedList);
				}// end if
				 if (e.getClickCount() == 1) {
						 _assignedList.clearSelection();
						 _fixedList.clearSelection();
				 }// end if
			}
		}
		MouseAdapter RightMouseListener = new RightMouseListener();
		_noAssignedList.addMouseListener(RightMouseListener);

		JLabel titleLabel = new JLabel(DConst.EVENTS_NOT_PLACED + " ");
		_labelForNoAssigned = new JLabel(String.valueOf(_noAssignedtVector.size()));
		_labelForNoAssigned.setForeground(DConst.COLOR_QUANTITY_DLGS);

		JPanel listPanel = DxTools.listPanel(_noAssignedList);
		listPanel.setPreferredSize(new Dimension(WIDTH_PANE, HEIGHT_PANE));
	
		// the _rightPanel
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JPanel panelTop = new JPanel();
		panelTop.add(titleLabel);
		panelTop.add(_labelForNoAssigned);

		panel.add(panelTop, BorderLayout.NORTH);
		panel.add(listPanel, BorderLayout.CENTER);

		return panel;
	}// end initRightPanel

	public Dimension getPreferredSize() {
		return new Dimension(WIDTH_DLG, HEIGHT_DLG);
	}

	/**
	 * 
	 */
	private void doubleClicMouseProcess(JList aList) {
		String selectedItem = (String) aList.getSelectedValues()[0];

		if (!_buttonsPanel.isFirstEnable()) {
			new DxEditEventDlg(this, _dModel, selectedItem, false);
			_buttonsPanel.setFirstDisable();
		} else {
			new InformationDlg(this, "Appliquer ou fermer pour continuer",
					"Operation interdite");
		}
	}

	/**
	 * initialize label in each panel
	 */
	void initializePanel() {
		buildVectors();
		_labelForFixed.setText(String.valueOf(_fixedVector.size()));
		_fixedList.setListData(_fixedVector);
		_labelForAssigned.setText(String.valueOf(_assignedVector.size()));
		_assignedList.setListData(_assignedVector);
		_labelForNoAssigned.setText(String.valueOf(_noAssignedtVector.size()));
		_noAssignedList.setListData(_noAssignedtVector);
		_fixedList.clearSelection();
		_noAssignedList.clearSelection();
		_assignedList.clearSelection();
	}

	/**
	 * Set the unities with the values in each JList
	 */
	protected void setUnities() {
		String str = null;
		for (int i = 0; i < _fixedVector.size(); i++) {
			str = _fixedVector.elementAt(i);
			_activities.setUnityFix(DXToolsMethods.getToken(str, ".", 0),
					DXToolsMethods.getToken(str, ".", 1), DXToolsMethods
							.getToken(str, ".", 2), DXToolsMethods.getToken(
							str, ".", 3), true);
			_activities.setUnityAssign(DXToolsMethods.getToken(str, ".", 0),
					DXToolsMethods.getToken(str, ".", 1), DXToolsMethods
							.getToken(str, ".", 2), DXToolsMethods.getToken(
							str, ".", 3), true);
		}// end for
		for (int i = 0; i < _assignedVector.size(); i++) {
			str = _assignedVector.elementAt(i);
			_activities.setUnityFix(DXToolsMethods.getToken(str, ".", 0),
					DXToolsMethods.getToken(str, ".", 1), DXToolsMethods
							.getToken(str, ".", 2), DXToolsMethods.getToken(
							str, ".", 3), false);
			_activities.setUnityAssign(DXToolsMethods.getToken(str, ".", 0),
					DXToolsMethods.getToken(str, ".", 1), DXToolsMethods
							.getToken(str, ".", 2), DXToolsMethods.getToken(
							str, ".", 3), true);
		}// end for
		for (int i = 0; i < _noAssignedtVector.size(); i++) {
			str = _noAssignedtVector.elementAt(i);
			_activities.setUnityFix(DXToolsMethods.getToken(str, ".", 0),
					DXToolsMethods.getToken(str, ".", 1), DXToolsMethods
							.getToken(str, ".", 2), DXToolsMethods.getToken(
							str, ".", 3), false);
			_activities.setUnityAssign(DXToolsMethods.getToken(str, ".", 0),
					DXToolsMethods.getToken(str, ".", 1), DXToolsMethods
							.getToken(str, ".", 2), DXToolsMethods.getToken(
							str, ".", 3), false);
		}// end for
	}

	/**
	 * Builds the vectors _rightVector, _centerVector, _leftVector for their
	 * first display
	 */
	private void buildVectors() {
		_fixedVector = new Vector<String>();
		_assignedVector = new Vector<String>();
		_noAssignedtVector = new Vector<String>();
		String eventFullID;
		StringTokenizer stk;
		for (int i = 0; i < _dModel.getSetOfEvents().size(); i++) {
			String eventFullKey = ((DxEvent) _dModel.getSetOfEvents()
					.getResourceAt(i).getAttach()).getPrincipalRescKey();
			stk = new StringTokenizer(eventFullKey, ".");
			Unity currUnity = _activities.getUnity(Long.parseLong(stk
					.nextToken()), Long.parseLong(stk.nextToken()), Long
					.parseLong(stk.nextToken()), Long
					.parseLong(stk.nextToken()));
			stk = new StringTokenizer(eventFullKey, ".");
			eventFullID = _activities.getUnityCompleteName(Long.parseLong(stk
					.nextToken()), Long.parseLong(stk.nextToken()), Long
					.parseLong(stk.nextToken()), Long
					.parseLong(stk.nextToken()));
			if (currUnity.compareToAssign(false)) {
				_noAssignedtVector.add(eventFullID);
			} else {
				if (currUnity.isFixed()) {
					_fixedVector.add(eventFullID);
				} else {
					_assignedVector.add(eventFullID);
				}
			}// end else if (_currUnity.compareByField(2, "false"))
		}// end for
	}// end method

	private ButtonsPanel setButtons() {
		String[] a = { DConst.BUT_APPLY, DConst.BUT_CLOSE };
		_buttonsPanel = new TwoButtonsPanel(this, a);
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

	public void update(@SuppressWarnings("unused")
	Observable o, @SuppressWarnings("unused")
	Object arg) {
		this.initializePanel();
	}

}// end EventsDlg
