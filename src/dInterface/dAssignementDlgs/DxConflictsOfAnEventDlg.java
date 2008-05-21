/**
 * Created on Feb 21, 2007, Copied 20 mai 2008
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.DlgIdentification;
import dInterface.dTimeTable.DxConflictsOfAnEventPanel;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.DxTools;
import dInterface.dUtil.TwoButtonsPanel;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.dData.dActivities.SetOfActivities;
import dInternal.dData.dActivities.Unity;
import dInternal.dOptimization.DxEvent;

public class DxConflictsOfAnEventDlg extends DxEventsGUIforDlg implements
		ActionListener, Observer, DlgIdentification {

	private JLabel _leftLabel, _centerLabel, _rightLabel;

	private JList _leftList;

	private JList _centerList;

	private JList _rightList;

	private ButtonsPanel _buttonsPanel;

	private SetOfActivities _activities;

	private DModel _dModel;

	private Vector<String> _leftVector;

	private Vector<String> _centerVector;

	private Vector<String> _rightVector;

	/**
	 * Constructor
	 * 
	 * @param DApplication dApplic
	 *            to get access to the rest
	 * 
	 */
	public DxConflictsOfAnEventDlg(DApplication dApplic) {
		super(dApplic.getJFrame(), DConst.EVENTS_DLG_TITLE, true);

		_dModel = dApplic.getCurrentDModel();
		_activities = _dModel.getSetOfActivities();
		_dModel.addObserver(this);
		initialize(dApplic);
	}// end DxConflictsOfAnEventDlg

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(DConst.BUT_CLOSE)) {
			_dModel.deleteObserver(this);
			dispose();
		}
		if ((e.getActionCommand().equals(DConst.BUT_CHANGE))
				&& (null != this.getSelectedValue())) {
			new DxEditEventDlg(this, _dModel, (String) getSelectedValue()
					.getSelectedValue(), false);
		}
	}// end actionPerformed

	/**
	 * Initialize the dialog
	 */
	private void initialize(DApplication dApplic) {
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

	public Dimension getPreferredSize() {
		return new Dimension(WIDTH_DLG, HEIGHT_DLG);
	}

	/**
	 * initialize label in each panel
	 */
	void initializePanel() {
		buildVectors();
		_leftLabel.setText(String.valueOf(_leftVector.size()));
		_centerLabel.setText(String.valueOf(_centerVector.size()));
		_rightLabel.setText(String.valueOf(_rightVector.size()));
	}

	/**
	 * Sets the _centerPanel, the panel containing the _centerList and the
	 * arrows panels
	 */
	private JPanel initCenterPanel() {
		_centerList = new JList(_centerVector);
		class CenterMouseListener extends MouseAdapter {
			@Override
			public void mouseClicked(MouseEvent e) {
				saySomething("Mouse clicked (# of clicks: " + e.getClickCount()
						+ ")", e);
				if (e.getClickCount() == 2) {
					int index = _centerList.locationToIndex(e.getPoint());
					saySomething("Selected Item index " + index + " "
							+ _centerList.getSelectedValue(), e);
					doubleClicMouseProcess(_centerList);
				}// end if
				if (e.getClickCount() == 1) {
					int index = _centerList.locationToIndex(e.getPoint());
					saySomething("Selected Item index " + index + " "
							+ _centerList.getSelectedValue(), e);
					_centerList.setSelectedIndex(index);
				}// end if
			}
		}
		MouseAdapter CenterMouseListener = new CenterMouseListener();
		_centerList.addMouseListener(CenterMouseListener);
		_centerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		class CenterSelectedItemListener implements ListSelectionListener {
			public void valueChanged(ListSelectionEvent lse) {
				if (!lse.getValueIsAdjusting()) {
					_leftList.clearSelection();
					_rightList.clearSelection();
					String selectedItem = (String) _centerList
							.getSelectedValue();
					System.out.println("center" + selectedItem);
				}
			}
		}
		ListSelectionListener CenterSelectedItemListener = new CenterSelectedItemListener();
		_centerList.addListSelectionListener(CenterSelectedItemListener);
		JLabel titleLabel = new JLabel(DConst.EVENTS_PLACED + " ");
		_centerLabel = new JLabel(String.valueOf(_centerVector.size()));
		_centerLabel.setForeground(DConst.COLOR_QUANTITY_DLGS);
		// The listContainerPanel
		JPanel listPanel = DxTools.listPanel(_centerList);
		//		listPanel.setMinimumSize(new Dimension(150, 100));
		listPanel.setPreferredSize(new Dimension(WIDTH_PANE, HEIGHT_PANE));
		//		listPanel.setMaximumSize(new Dimension(150, 400));
		JPanel listContainerPanel = new JPanel();

		listContainerPanel.add(titleLabel);
		listContainerPanel.add(_centerLabel);
		listContainerPanel.add(listPanel);

		// the _centerPanel
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		// _centerPanel.setPreferredSize(panelDim);
		JPanel miPanel = new JPanel();
		miPanel.add(listContainerPanel);
		JPanel centerPanelTop = new JPanel();

		centerPanelTop.add(titleLabel);
		centerPanelTop.add(_centerLabel);
		panel.add(centerPanelTop, BorderLayout.NORTH);
		panel.add(miPanel, BorderLayout.CENTER);
		return panel;
	}// end method

	/**
	 * initLeftPanel
	 */
	private JPanel initLeftPanel() {
		_leftList = new JList(_leftVector);
		class LeftMouseListener extends MouseAdapter {
			@Override
			public void mouseClicked(MouseEvent e) {
				saySomething("Mouse clicked (# of clicks: " + e.getClickCount()
						+ ")", e);
				if (e.getClickCount() == 2) {
					int index = _leftList.locationToIndex(e.getPoint());
					saySomething("Selected Item index " + index + " "
							+ _leftList.getSelectedValue(), e);
					doubleClicMouseProcess(_leftList);
				}// end if
				if (e.getClickCount() == 1) {
					int index = _leftList.locationToIndex(e.getPoint());
					saySomething("Selected Item index " + index + " "
							+ _leftList.getSelectedValue(), e);
					_leftList.setSelectedIndex(index);
				}// end if
			}
		}
		MouseAdapter LeftMouseListener = new LeftMouseListener();
		_leftList.addMouseListener(LeftMouseListener);
		_leftList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		class LeftSelectedItemListener implements ListSelectionListener {
			public void valueChanged(ListSelectionEvent lse) {
				if (!lse.getValueIsAdjusting()) {
					_centerList.clearSelection();
					_rightList.clearSelection();
					String selectedItem = (String) _leftList.getSelectedValue();
					System.out.println("left" + selectedItem);
				}
			}
		}
		ListSelectionListener LeftSelectedItemListener = new LeftSelectedItemListener();
		_leftList.addListSelectionListener(LeftSelectedItemListener);
		JLabel titleLabel = new JLabel(DConst.EVENTS_FIXED + " ");
		_leftLabel = new JLabel(String.valueOf(_leftVector.size()));
		_leftLabel.setForeground(DConst.COLOR_QUANTITY_DLGS);

		JPanel listPanel = DxTools.listPanel(_leftList);
		//		listPanel.setMinimumSize(new Dimension(150, 100));
		listPanel.setPreferredSize(new Dimension(WIDTH_PANE, HEIGHT_PANE));
		//		listPanel.setMaximumSize(new Dimension(150, 400));
		// the _leftPanel
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JPanel panelTop = new JPanel();
		panelTop.add(titleLabel);
		panelTop.add(_leftLabel);

		panel.add(panelTop, BorderLayout.NORTH);
		panel.add(listPanel, BorderLayout.CENTER);

		return panel;
	}// end initLeftPanel

	/**
	 * Sets the _initRigthPanel
	 */
	private JPanel initRightPanel() {
		_rightList = new JList(_rightVector);
		class RightMouseListener extends MouseAdapter {
			@Override
			public void mouseClicked(MouseEvent e) {
				saySomething("Mouse clicked (# of clicks: " + e.getClickCount()
						+ ")", e);
				if (e.getClickCount() == 2) {
					int index = _rightList.locationToIndex(e.getPoint());
					saySomething("Selected Item index " + index + " "
							+ _rightList.getSelectedValue(), e);
					doubleClicMouseProcess(_rightList);
				}// end if
				if (e.getClickCount() == 1) {
					int index = _rightList.locationToIndex(e.getPoint());
					saySomething("Selected Item index " + index + " "
							+ _rightList.getSelectedValue(), e);
					_rightList.setSelectedIndex(index);
				}// end if
			}
		}
		MouseAdapter RightMouseListener = new RightMouseListener();
		_rightList.addMouseListener(RightMouseListener);
		_rightList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		class RightSelectedItemListener implements ListSelectionListener {
			public void valueChanged(ListSelectionEvent lse) {
				if (!lse.getValueIsAdjusting()) {
					_centerList.clearSelection();
					_leftList.clearSelection();
					String selectedItem = (String) _rightList
							.getSelectedValue();
					System.out.println("right " + selectedItem);
				}
			}
		}
		ListSelectionListener RightSelectedItemListener = new RightSelectedItemListener();
		_rightList.addListSelectionListener(RightSelectedItemListener);
		JLabel titleLabel = new JLabel(DConst.EVENTS_NOT_PLACED + " ");
		_rightLabel = new JLabel(String.valueOf(_rightVector.size()));
		_rightLabel.setForeground(DConst.COLOR_QUANTITY_DLGS);

		JPanel listPanel = DxTools.listPanel(_rightList);
		//		listPanel.setMinimumSize(new Dimension(150, 100));
		listPanel.setPreferredSize(new Dimension(WIDTH_PANE, HEIGHT_PANE));
		//		listPanel.setMaximumSize(new Dimension(150, 400));
		// the _rightPanel
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JPanel panelTop = new JPanel();
		panelTop.add(titleLabel);
		panelTop.add(_rightLabel);

		panel.add(panelTop, BorderLayout.NORTH);
		panel.add(listPanel, BorderLayout.CENTER);

		return panel;
	}// end initRightPanel

	/**
	 * Builds the vectors _rightVector, _centerVector, _leftVector for their
	 * first display
	 */
	private void buildVectors() {
		_leftVector = new Vector<String>();
		_centerVector = new Vector<String>();
		_rightVector = new Vector<String>();
		String _eventFullID;
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
			_eventFullID = _activities.getUnityCompleteName(Long.parseLong(stk
					.nextToken()), Long.parseLong(stk.nextToken()), Long
					.parseLong(stk.nextToken()), Long
					.parseLong(stk.nextToken()));
			if (currUnity.compareToAssign(false)) {
				_rightVector.add(_eventFullID);
			} else {
				if (currUnity.isFixed()) {
					_leftVector.add(_eventFullID);
				} else {
					_centerVector.add(_eventFullID);
				}
			}// end else if (_currUnity.compareByField(2, "false"))
		}// end for
	}// end method

	private ButtonsPanel setButtons() {
		String[] a = { DConst.BUT_CHANGE, DConst.BUT_CLOSE };
		_buttonsPanel = new TwoButtonsPanel(this, a);
		return _buttonsPanel;
	}

	/**
	 * 
	 */
	private void doubleClicMouseProcess(JList aList) {
		String selectedItem = (String) aList.getSelectedValue();
		DResource eventRes = _dModel.getSetOfEvents().getResource(selectedItem);
		new DxConflictsOfAnEventPanel(this, eventRes, _dModel);
	}

	private JList getSelectedValue() {
		if (_leftList.getSelectedValue() != null)
			return _leftList;
		if (_centerList.getSelectedValue() != null)
			return _centerList;
		if (_rightList.getSelectedValue() != null)
			return _rightList;
		return null;
	}

	public void update(@SuppressWarnings("unused")
	Observable o, @SuppressWarnings("unused")
	Object arg) {
		this.initializePanel();
	}

}// end EventsDlg
