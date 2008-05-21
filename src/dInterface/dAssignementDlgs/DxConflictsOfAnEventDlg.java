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
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JDialog;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.DlgIdentification; // import
// dInterface.dTimeTable.DxConflictsOfAnEventPanel;
import dInterface.dTimeTable.DxConflictsOfAnEventPanel;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.DxTools;
import dInterface.dUtil.TwoButtonsPanel;
import dInternal.DModel; // import dInternal.DResource;
import dInternal.DResource;
import dInternal.dData.dActivities.SetOfActivities;
import dInternal.dData.dActivities.Unity;
import dInternal.dOptimization.DxEvent;
import dInternal.dOptimization.SetOfEvents;
import dInternal.dUtil.DXToolsMethods;

public class DxConflictsOfAnEventDlg extends JDialog implements ActionListener,
		Observer, DlgIdentification {
	// protected JFrame _jFrame;

	// private DxEvent _currEvent;

	// private int buttonsPanelHeight = 80;

	private JLabel _leftLabel, _centerLabel, _rightLabel;

	private JList _leftList;

	private JList _centerList;

	private JList _rightList;

	// private JPanel _leftPanel, _centerPanel, _rightPanel, _rightArrowsPanel,
	// _leftArrowsPanel;

	private ButtonsPanel _buttonsPanel;

	// private Object[] _selectedItems;

	private SetOfActivities _activities;

	private SetOfEvents _events;

	private String _eventFullKey;

	private DModel _dModel;

	/**
	 * @associates String
	 */
	private Vector<String> _leftVector;

	/**
	 * @associates String
	 */
	private Vector<String> _centerVector;

	/**
	 * @associates String
	 */
	private Vector<String> _rightVector;

	// private JDialog _jDialog;
	// private String[] _arrowsNames;

	/**
	 * Constructor
	 * 
	 * @param jFrame
	 *            to place the dialogue
	 * @param dModel
	 *            to have access to data
	 * 
	 */
	public DxConflictsOfAnEventDlg(DApplication dApplic) {
		super(dApplic.getJFrame(), DConst.EVENTS_DLG_TITLE, true);
		// _jFrame = jFrame;
		// _jDialog = this;
		_dModel = dApplic.getCurrentDModel();
		_activities = _dModel.getSetOfActivities();
		_events = _dModel.getSetOfEvents();
		// _arrowsNames = new String[2];
		// _arrowsNames[0] = DConst.TO_RIGHT;
		// _arrowsNames[1] = DConst.TO_LEFT;
		// buildArrowButtons();
		_dModel.addObserver(this);
		initialize(dApplic);
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
				&& (null != this.getSelectedValue())) {
			new DxEditEventDlg(this, _dModel, (String)getSelectedValue().getSelectedValue(), false);
		}
	}// end actionPerformed

	private JList getSelectedValue() {
		if(_leftList.getSelectedValue()!= null)
			return _leftList;
		if(_centerList.getSelectedValue()!= null)
			return _centerList;
		if(_rightList.getSelectedValue()!= null)
			return _rightList;
		return null;
	}

	public String idDlgToString() {
		return this.getClass().toString();
	}

	public void update(@SuppressWarnings("unused")
	Observable o, @SuppressWarnings("unused")
	Object arg) {
		this.initializePanel();
	}

	// public void valueChanged(ListSelectionEvent lse) {
	// if (lse.getSource().equals(_leftList)) {
	// if (!lse.getValueIsAdjusting()) {
	// _centerList.clearSelection();
	// _rightList.clearSelection();
	// String selectedItem = (String) _leftList.getSelectedValue();
	// System.out.println("left" + selectedItem);
	// }
	// }// end if (e.getSource().equals(_leftList))
	// if (lse.getSource().equals(_centerList)) {
	// if (!lse.getValueIsAdjusting()) {
	// _leftList.clearSelection();
	// _rightList.clearSelection();
	// String selectedItem = (String) _centerList.getSelectedValue();
	// System.out.println("center" + selectedItem);
	// }
	// }// end if (e.getSource().equals(_centerList))
	// // if (lse.getSource().equals(_rightList)) {
	// // if (!lse.getValueIsAdjusting()) {
	// // _centerList.clearSelection();
	// // _leftList.clearSelection();
	// // String selectedItem = (String) _rightList.getSelectedValue();
	// // System.out.println("right " + selectedItem);
	// // }
	// // }// end if (e.getSource().equals(_rightList))
	// }

	// /**
	// * The MouseListener for the JLists
	// */
	// private MouseListener mouseListenerLists = new MouseAdapter() {
	// public void mouseClicked(MouseEvent e) {
	// if (((JList) e.getSource()).getModel().getSize() == 0)
	// return;
	// if (e.getSource().equals(_leftList)) {
	// _centerList.clearSelection();
	// _rightList.clearSelection();
	// _selectedItems = _leftList.getSelectedValues();
	// }//end if (e.getSource().equals(_leftList))
	// if (e.getSource().equals(_centerList)) {
	// _leftList.clearSelection();
	// _rightList.clearSelection();
	// _selectedItems = _centerList.getSelectedValues();
	// }//end if (e.getSource().equals(_centerList))
	// if (e.getSource().equals(_rightList)) {
	// _centerList.clearSelection();
	// _leftList.clearSelection();
	// _selectedItems = _rightList.getSelectedValues();
	// }//end if (e.getSource().equals(_rightList))
	// if (e.getClickCount() == 2) {
	// doubleClicMouseProcess();
	// }//end if
	// }// end public void mouseClicked
	// };//end definition of MouseListener mouseListener = new MouseAdapter(){

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
		// _leftList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//		
		// _rightList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setResizable(false);
		this.setVisible(true);
		// _leftList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// _centerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// _rightList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	public Dimension getMinimumSize() {
		return new Dimension(500, 300);
	}

	public Dimension getPreferredSize() {
		return new Dimension(700, 400);
	}

	public Dimension getMaximumSize() {
		return new Dimension(800, 400);
	}

	/**
	 * initialize label in each panel
	 */
	public void initializePanel() {
		buildVectors();
		_leftLabel.setText(String.valueOf(_leftVector.size()));
		//_leftList.setListData(_leftVector);
		_centerLabel.setText(String.valueOf(_centerVector.size()));
		//_centerList.setListData(_centerVector);
		_rightLabel.setText(String.valueOf(_rightVector.size()));
		//_rightList.setListData(_rightVector);

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
		//_centerList.addMouseListener(mouseListenerLists);
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
		listPanel.setMinimumSize(new Dimension(150, 100));
		listPanel.setPreferredSize(new Dimension(150, 300));
		listPanel.setMaximumSize(new Dimension(150, 400));
		JPanel listContainerPanel = new JPanel();

		listContainerPanel.add(titleLabel);
		listContainerPanel.add(_centerLabel);
		listContainerPanel.add(listPanel);

		// the _centerPanel
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		// _centerPanel.setPreferredSize(panelDim);
		JPanel miPanel = new JPanel();
		// miPanel.setLayout(new BorderLayout());
		// miPanel.add(_leftArrowsPanel);//, BorderLayout.EAST);
		miPanel.add(listContainerPanel);// , BorderLayout.CENTER);
		// miPanel.add(_rightArrowsPanel);//, BorderLayout.WEST);
		JPanel centerPanelTop = new JPanel();

		centerPanelTop.add(titleLabel);
		centerPanelTop.add(_centerLabel);
		panel.add(centerPanelTop, BorderLayout.NORTH);
		panel.add(miPanel, BorderLayout.CENTER);
		return panel;
		// getContentPane().add(_centerPanel, BorderLayout.CENTER);
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
		listPanel.setMinimumSize(new Dimension(150, 100));
		listPanel.setPreferredSize(new Dimension(150, 300));
		listPanel.setMaximumSize(new Dimension(150, 400));
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
		listPanel.setMinimumSize(new Dimension(150, 100));
		listPanel.setPreferredSize(new Dimension(150, 300));
		listPanel.setMaximumSize(new Dimension(150, 400));
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
		for (int i = 0; i < _events.size(); i++) {
			_eventFullKey = ((DxEvent) _events.getResourceAt(i).getAttach())
					.getPrincipalRescKey();
			stk = new StringTokenizer(_eventFullKey, ".");
			Unity currUnity = _activities.getUnity(Long.parseLong(stk
					.nextToken()), Long.parseLong(stk.nextToken()), Long
					.parseLong(stk.nextToken()), Long
					.parseLong(stk.nextToken()));
			stk = new StringTokenizer(_eventFullKey, ".");
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

//	/**
//	 * The MouseListener for the JLists
//	 */
//	private MouseListener mouseListenerLists = new MouseAdapter() {
//
//		public void mousePressed(MouseEvent e) {
//			saySomething("Mouse pressed; # of clicks: " + e.getClickCount(), e);
//		}
//
//		public void mouseReleased(MouseEvent e) {
//			saySomething("Mouse released; # of clicks: " + e.getClickCount(), e);
//		}
//
//		// public void mouseEntered(MouseEvent e) {
//		// saySomething("Mouse entered", e);
//		// }
//
//		// public void mouseExited(MouseEvent e) {
//		// saySomething("Mouse exited", e);
//		// }
//
//		public void mouseClicked(MouseEvent e) {
//			saySomething("Mouse clicked (# of clicks: " + e.getClickCount()
//					+ ")", e);
//			if (e.getClickCount() == 2) {
//				int index = _rightList.locationToIndex(e.getPoint());
//				saySomething("Selected Item index " + index + " "
//						+ _rightList.getSelectedValue(), e);
//				doubleClicMouseProcess();
//			}// end if
//		}

		// void saySomething(String eventDescription, MouseEvent e) {
		// textArea.append(eventDescription + " detected on "
		// + e.getComponent().getClass().getName()
		// + "." + newline);
		// }

		// public void mouseClicked(MouseEvent e) {
		// if (((JList) e.getSource()).getModel().getSize() == 0)
		// return;
		// if (e.getSource().equals(_leftList)) {
		// _centerList.clearSelection();
		// _rightList.clearSelection();
		// _selectedItems = _leftList.getSelectedValues();
		// }// end if (e.getSource().equals(_leftList))
		// if (e.getSource().equals(_centerList)) {
		// _leftList.clearSelection();
		// _rightList.clearSelection();
		// _selectedItems = _centerList.getSelectedValues();
		// }// end if (e.getSource().equals(_centerList))
		// if (e.getSource().equals(_rightList)) {
		// _centerList.clearSelection();
		// _leftList.clearSelection();
		// _selectedItems = _rightList.getSelectedValues();
		// }// end if (e.getSource().equals(_rightList))
		// if (e.getClickCount() == 2) {
		// doubleClicMouseProcess();
		// }// end if
		// }// end public void mouseClicked
//	};// end definition of MouseListener mouseListener = new MouseAdapter(){

	/**
	 * 
	 */
	protected void doubleClicMouseProcess(JList aList) {
		String selectedItem = (String) aList.getSelectedValue();
		DResource eventRes = _dModel.getSetOfEvents().getResource(selectedItem);
		new DxConflictsOfAnEventPanel(this, eventRes, _dModel);
	}

	/**
	 * Set the unities with the values in each JList
	 */
	protected void setUnities() {
		String str = null;
		for (int i = 0; i < _leftVector.size(); i++) {
			str = _leftVector.elementAt(i);
			_activities.setUnityFix(DXToolsMethods.getToken(str, ".", 0),
					DXToolsMethods.getToken(str, ".", 1), DXToolsMethods
							.getToken(str, ".", 2), DXToolsMethods.getToken(
							str, ".", 3), true);
			_activities.setUnityAssign(DXToolsMethods.getToken(str, ".", 0),
					DXToolsMethods.getToken(str, ".", 1), DXToolsMethods
							.getToken(str, ".", 2), DXToolsMethods.getToken(
							str, ".", 3), true);
		}// end for
		for (int i = 0; i < _centerVector.size(); i++) {
			str = _centerVector.elementAt(i);
			_activities.setUnityFix(DXToolsMethods.getToken(str, ".", 0),
					DXToolsMethods.getToken(str, ".", 1), DXToolsMethods
							.getToken(str, ".", 2), DXToolsMethods.getToken(
							str, ".", 3), false);
			_activities.setUnityAssign(DXToolsMethods.getToken(str, ".", 0),
					DXToolsMethods.getToken(str, ".", 1), DXToolsMethods
							.getToken(str, ".", 2), DXToolsMethods.getToken(
							str, ".", 3), true);
		}// end for
		for (int i = 0; i < _rightVector.size(); i++) {
			str = _rightVector.elementAt(i);
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

	private void saySomething(String str, MouseEvent e) {
		System.out.println(str + e.toString());
	}

}// end EventsDlg
