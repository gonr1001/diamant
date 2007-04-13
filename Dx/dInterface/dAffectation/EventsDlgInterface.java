/**
 *
 * Title: EventsDlgInterface
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
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */

/**
 * Description: EventsDlgInterface is a class used to
 *  
 */
package dInterface.dAffectation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.DxTools;
import dInternal.DModel;
import dInternal.dData.dActivities.SetOfActivities;
import dInternal.dData.dActivities.Unity;
import dInternal.dOptimization.DxEvent;
import dInternal.dOptimization.SetOfEvents;
import dInternal.dUtil.DXToolsMethods;

public abstract class EventsDlgInterface extends JDialog implements
		ActionListener {

	protected DApplication _dApplic;

	protected DxEvent _currEvent;

	protected int buttonsPanelHeight = 80;

	protected JLabel _leftLabel, _centerLabel, _rightLabel;

	protected JList _leftList;

	protected JList _centerList;

	protected JList _rightList;

	protected JPanel _leftPanel, _centerPanel, _rightPanel, _rightArrowsPanel,
			_leftArrowsPanel;

	protected ButtonsPanel _buttonsPanel;

	protected Object[] selectedItems;

	protected SetOfActivities _activities;

	protected SetOfEvents _events;

	protected String _eventFullKey;

	protected Unity _currUnity;

	private DModel _dmodel;

	/**
	 * @associates String
	 */
	protected Vector<String> _leftVector;

	/**
	 * @associates String 
	 */
	protected Vector<String> _centerVector;

	/**
	 * @associates String 
	 */
	protected Vector<String> _rightVector;

	protected JDialog _jDialog;

	/**
	 * Constructor
	 * 
	 * @param dApplic
	 *            The application
	 * @param title
	 *            the title of the dialog
	 */
	public EventsDlgInterface(DApplication dApplic, String title) {
		super(dApplic.getJFrame(), title + " rgrEDL", true);
		_dApplic = dApplic;
		_jDialog = this;
		if (dApplic.getCurrentDxDoc() == null)
			return;
		_dmodel = dApplic.getCurrentDxDoc().getCurrentDModel();
		_activities = _dmodel.getSetOfActivities();
		_events = _dmodel.getSetOfEvents();
	}//end method

	public abstract void actionPerformed(ActionEvent e);

	public abstract void buildArrowButtons(boolean enableArrows);

	public abstract ButtonsPanel setButtons();

	/**
	 * Initialise the dialog
	 */
	protected void initialize() {
		this.getContentPane().setLayout(new BorderLayout());
		//setSize(_dialogDim);
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

		//int x = _dApplic.getJFrame().getX();
		//int y = _dApplic.getJFrame().getY();
		//this.getContentPane().setLocation(x + DConst.X_OFFSET, y + DConst.Y_OFFSET); //_dApplic.getJFrame());
		this.pack();
		this.setLocationRelativeTo(_dApplic.getJFrame());
		//        this.setMinimumSize(new Dimension(500, 300));
		//        this.setPreferredSize(new Dimension(700, 400)); // the real
		//        this.setMaximumSize(new Dimension(800, 400)); // XXXX Pascal: lien inutile avec JDK 1.5

		this.setResizable(false);
		this.setVisible(true);
	}

	// XXXX Pascal: Fix Java 1.5->1.4
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
		_leftList.setListData(_leftVector);
		_centerLabel.setText(String.valueOf(_centerVector.size()));
		_centerList.setListData(_centerVector);
		_rightLabel.setText(String.valueOf(_rightVector.size()));
		_rightList.setListData(_rightVector);
	}

	/**
	 * build buttom to use in the dialog
	 */

	/**
	 * Sets the _centerPanel, the panel containing the _centerList and the
	 * arrows panels
	 */
	private JPanel initCenterPanel() {
		_centerList = new JList(_centerVector);
		_centerList.addMouseListener(mouseListenerLists);
		JLabel titleLabel = new JLabel(DConst.EVENTS_PLACED + " ");
		_centerLabel = new JLabel(String.valueOf(_centerVector.size()));
		_centerLabel.setForeground(DConst.COLOR_QUANTITY_DLGS);
		//The listContainerPanel
		JPanel listPanel = DxTools.listPanel(_centerList);
		listPanel.setMinimumSize(new Dimension(150, 100));
		listPanel.setPreferredSize(new Dimension(150, 300));
		listPanel.setMaximumSize(new Dimension(150, 400));
		JPanel listContainerPanel = new JPanel();

		listContainerPanel.add(titleLabel);
		listContainerPanel.add(_centerLabel);
		listContainerPanel.add(listPanel);

		//the _centerPanel
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		//_centerPanel.setPreferredSize(panelDim);
		JPanel miPanel = new JPanel();
		//miPanel.setLayout(new BorderLayout());
		miPanel.add(_leftArrowsPanel);//, BorderLayout.EAST);
		miPanel.add(listContainerPanel);//, BorderLayout.CENTER);
		miPanel.add(_rightArrowsPanel);//, BorderLayout.WEST);
		JPanel centerPanelTop = new JPanel();

		centerPanelTop.add(titleLabel);
		centerPanelTop.add(_centerLabel);
		panel.add(centerPanelTop, BorderLayout.NORTH);
		panel.add(miPanel, BorderLayout.CENTER);
		return panel;
		//getContentPane().add(_centerPanel, BorderLayout.CENTER);
	}//end method

	/**
	 * initRightPanel
	 */
	private JPanel initLeftPanel() {
		_leftList = new JList(_leftVector);
		_leftList.addMouseListener(mouseListenerLists);
		JLabel titleLabel = new JLabel(DConst.EVENTS_FIXED + " ");
		_leftLabel = new JLabel(String.valueOf(_leftVector.size()));
		_leftLabel.setForeground(DConst.COLOR_QUANTITY_DLGS);

		JPanel listPanel = DxTools.listPanel(_leftList);
		listPanel.setMinimumSize(new Dimension(150, 100));
		listPanel.setPreferredSize(new Dimension(150, 300));
		listPanel.setMaximumSize(new Dimension(150, 400));
		//the _leftPanel
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JPanel panelTop = new JPanel();
		panelTop.add(titleLabel);
		panelTop.add(_leftLabel);

		panel.add(panelTop, BorderLayout.NORTH);
		panel.add(listPanel, BorderLayout.CENTER);

		return panel;
	}//end initLeftPanel

	/**
	 * Sets the _initLeftPanel
	 */
	private JPanel initRightPanel() {
		_rightList = new JList(_rightVector);
		_rightList.addMouseListener(mouseListenerLists);
		JLabel titleLabel = new JLabel(DConst.EVENTS_NOT_PLACED + " ");
		_rightLabel = new JLabel(String.valueOf(_rightVector.size()));
		_rightLabel.setForeground(DConst.COLOR_QUANTITY_DLGS);

		JPanel listPanel = DxTools.listPanel(_rightList);
		listPanel.setMinimumSize(new Dimension(150, 100));
		listPanel.setPreferredSize(new Dimension(150, 300));
		listPanel.setMaximumSize(new Dimension(150, 400));
		//the _rightPanel
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JPanel panelTop = new JPanel();
		panelTop.add(titleLabel);
		panelTop.add(_rightLabel);

		panel.add(panelTop, BorderLayout.NORTH);
		panel.add(listPanel, BorderLayout.CENTER);

		return panel;
	}//end initRightPanel

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
			_currUnity = _activities.getUnity(Long.parseLong(stk.nextToken()),
					Long.parseLong(stk.nextToken()), Long.parseLong(stk
							.nextToken()), Long.parseLong(stk.nextToken()));
			stk = new StringTokenizer(_eventFullKey, ".");
			_eventFullID = _activities.getUnityCompleteName(Long.parseLong(stk
					.nextToken()), Long.parseLong(stk.nextToken()), Long
					.parseLong(stk.nextToken()), Long
					.parseLong(stk.nextToken()));
			if (_currUnity.compareByField(2, "false")) {
				_rightVector.add(_eventFullID);
			} else {
				if (_currUnity.compareByField(3, "true")) {
					_leftVector.add(_eventFullID);
				} else {
					_centerVector.add(_eventFullID);
				}
			}//end else if (_currUnity.compareByField(2, "false"))
		}//end for
	}//end method

	/**
	 * The MouseListener for the JLists
	 */
	private MouseListener mouseListenerLists = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			if (((JList) e.getSource()).getModel().getSize() == 0)
				return;
			if (e.getSource().equals(_leftList)) {
				_centerList.clearSelection();
				_rightList.clearSelection();
				selectedItems = _leftList.getSelectedValues();
			}//end if (e.getSource().equals(_leftList))
			if (e.getSource().equals(_centerList)) {
				_leftList.clearSelection();
				_rightList.clearSelection();
				selectedItems = _centerList.getSelectedValues();
			}//end if (e.getSource().equals(_centerList))
			if (e.getSource().equals(_rightList)) {
				_centerList.clearSelection();
				_leftList.clearSelection();
				selectedItems = _rightList.getSelectedValues();
			}//end if (e.getSource().equals(_rightList))
			if (e.getClickCount() == 2) {
				doubleClicMouseProcess();
			}//end if
		}// end public void mouseClicked
	};//end definition of MouseListener mouseListener = new MouseAdapter(){

	/**
	 *  
	 */
	protected void doubleClicMouseProcess() {
		//    	 TODO Auto-generated constructor stub
	}

	/**
	 * Set the unities with the values in each JList
	 */
	protected void setUnities() {
		String str = null;
		for (int i = 0; i < _leftVector.size(); i++) {
			str = _leftVector.elementAt(i);
			_activities.setUnityField(DXToolsMethods.getToken(str, ".", 0),
					DXToolsMethods.getToken(str, ".", 1), DXToolsMethods
							.getToken(str, ".", 2), DXToolsMethods.getToken(
							str, ".", 3), 3, "true");
			_activities.setUnityField(DXToolsMethods.getToken(str, ".", 0),
					DXToolsMethods.getToken(str, ".", 1), DXToolsMethods
							.getToken(str, ".", 2), DXToolsMethods.getToken(
							str, ".", 3), 2, "true");
		}//end for
		for (int i = 0; i < _centerVector.size(); i++) {
			str = _centerVector.elementAt(i);
			_activities.setUnityField(DXToolsMethods.getToken(str, ".", 0),
					DXToolsMethods.getToken(str, ".", 1), DXToolsMethods
							.getToken(str, ".", 2), DXToolsMethods.getToken(
							str, ".", 3), 3, "false");
			_activities.setUnityField(DXToolsMethods.getToken(str, ".", 0),
					DXToolsMethods.getToken(str, ".", 1), DXToolsMethods
							.getToken(str, ".", 2), DXToolsMethods.getToken(
							str, ".", 3), 2, "true");
		}//end for
		for (int i = 0; i < _rightVector.size(); i++) {

			str = _rightVector.elementAt(i);
			_activities.setUnityField(DXToolsMethods.getToken(str, ".", 0),
					DXToolsMethods.getToken(str, ".", 1), DXToolsMethods
							.getToken(str, ".", 2), DXToolsMethods.getToken(
							str, ".", 3), 3, "false");
			_activities.setUnityField(DXToolsMethods.getToken(str, ".", 0),
					DXToolsMethods.getToken(str, ".", 1), DXToolsMethods
							.getToken(str, ".", 2), DXToolsMethods.getToken(
							str, ".", 3), 2, "false");
		}//end for
	}

}//end EventsDlgInterface
