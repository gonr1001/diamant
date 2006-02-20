/**
 * Created on Feb 16, 2006
 * 
 * TODO To change the class description for this generated file
 * 
 * Title: DAssignMenu.java 
 * Description:
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
package dInterface.dMenus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import dConstants.DConst;

import dInterface.DApplication;

import dInterface.dAffectation.PartialTTStructureCmd;

import dInterface.dTimeTable.ManualImprovementCmd;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DAssignMenu.java is a class used to:
 * <p>
 * 
 */
public class DAssignMenu extends JMenu implements MenuStates {

	private DApplication _dApplication;

	private JMenuItem _assignActivities;

	private JMenuItem _assignSections;

	private JMenuItem _instructorAvailability;

	private JMenuItem _roomsAvailability;

	private JMenuItem _assignEvents;

	private JMenuItem _selectiveViews;

	private JMenuItem _conflicsOfAnEvent;

	/**
	 * @param application
	 * @param bar
	 * 
	 */
	public DAssignMenu(DxMenuBar bar, DApplication dApplication) {
		super(DConst.M_ASSIGN);
		_dApplication = dApplication;
		setFont(DxMenuBar.DxMB_FONT);
		bar.add(this);
		buildMenu();
	}

	/**
	 * 
	 */
	private void buildMenu() {
		buildAssignActivities();
		buildAssignSections();
		buildInstructorAvailability();
		buildRoomAvailability();
		buildAssignEvents();
		this.addSeparator();
		buildSelectiveViews();
		this.addSeparator();
		buildConflictOfAnEvent();
	}

	/**
	 * 
	 */
	private void buildAssignActivities() {
		_assignActivities = new JMenuItem(DConst.ASSIGN_ACTIVITIES_M);
		_assignActivities.setFont(DxMenuBar.DxMB_FONT);
		class AAListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.exit();
			}
		}
		ActionListener assignActivitiesListener = new AAListener();
		_assignActivities.addActionListener(assignActivitiesListener);
		this.add(_assignActivities);
	} // end buildAssignActivities

	/**
	 * 
	 */
	private void buildAssignSections() {
		_assignSections = new JMenuItem(DConst.ASSIGN_SECTION_M);
		_assignSections.setFont(DxMenuBar.DxMB_FONT);
		class ASListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.exit();
			}
		}
		ActionListener assignSectionsListener = new ASListener();
		_assignSections.addActionListener(assignSectionsListener);
		this.add(_assignSections);
	} // end buildAssignSections

	/**
	 * 
	 */
	private void buildInstructorAvailability() {
		_instructorAvailability = new JMenuItem(DConst.INST_AVAILABILITY_M);
		_instructorAvailability.setFont(DxMenuBar.DxMB_FONT);
		class IAListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.exit();
			}
		}
		ActionListener instructorAvailabilityListener = new IAListener();
		_instructorAvailability
				.addActionListener(instructorAvailabilityListener);
		this.add(_instructorAvailability);
	} // end buildInstructorAvailability

	/**
	 * 
	 */
	private void buildRoomAvailability() {
		_roomsAvailability = new JMenuItem(DConst.ROOM_AVAILABILITY_M);
		_roomsAvailability.setFont(DxMenuBar.DxMB_FONT);
		class RAListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.exit();
			}
		}
		ActionListener roomsAvailabilityListener = new RAListener();
		_roomsAvailability.addActionListener(roomsAvailabilityListener);
		this.add(_roomsAvailability);
	} // end buildRoomAvailability

	/**
	 * 
	 */
	private void buildAssignEvents() {
		_assignEvents = new JMenuItem(DConst.EVENTS_ASSIGN_M);
		_assignEvents.setFont(DxMenuBar.DxMB_FONT);
		class AEListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.exit();
			}
		}
		ActionListener assignEventsListener = new AEListener();
		_assignEvents.addActionListener(assignEventsListener);
		this.add(_assignEvents);
	} // end buildExit

	/**
	 * 
	 */
	private void buildSelectiveViews() {
		_selectiveViews = new JMenuItem(DConst.PARTIAL_TTSTRUCTURE_M);
		_selectiveViews.setFont(DxMenuBar.DxMB_FONT);
		class SVListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.exit();
			}
		}
		ActionListener _selectiveViewsListener = new SVListener();
		_selectiveViews.addActionListener(_selectiveViewsListener);
		this.add(_selectiveViews);
	} // end buildSelectiveViews()

	/**
	 * 
	 */
	private void buildConflictOfAnEvent() {
		_conflicsOfAnEvent = new JMenuItem(DConst.MANUAL_ASSIGN_M);
		_conflicsOfAnEvent.setFont(DxMenuBar.DxMB_FONT);
		class CEListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.exit();
			}
		}
		ActionListener conflicsOfAnEventListener = new CEListener();
		_conflicsOfAnEvent.addActionListener(conflicsOfAnEventListener);
		this.add(_conflicsOfAnEvent);
	} // end buildExit

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInterface.dMenus.MenuStates#initialState()
	 */
	public void initialState() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInterface.dMenus.MenuStates#afterNewTTable()
	 */
	public void afterNewTTable() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInterface.dMenus.MenuStates#afterNewTTStruc()
	 */
	public void afterNewTTStruc() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInterface.dMenus.MenuStates#afterInitialAssign()
	 */
	public void afterInitialAssign() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInterface.dMenus.MenuStates#afterOpenTTSruc()
	 */
	public void afterOpenTTSruc() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInterface.dMenus.MenuStates#afterImport()
	 */
	public void afterImport() {
		// TODO Auto-generated method stub

	}

}
