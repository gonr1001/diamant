/**
 * Created on Feb 16, 2006
 * 
 * 
 * Title: DModificationMenu.java 
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

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DModificationMenu.java is a class used to:
 * <p>
 * Build the DModification Menu, for each menu Item there is a Listener to call the
 * activated action in DxApplication.
 * 
 */
@SuppressWarnings("serial")
public class DModificationMenu extends JMenu implements MenuStates {

	private DApplication _dApplication;

	private JMenuItem _modifyActivity;

	private JMenu _specialImport;

	private JMenuItem _mergeInstructors;

	private JMenuItem _mergeRooms;

	private JMenuItem _mergeActivities;

	private JMenuItem _mergeStudents;

	/**
	 * @param application
	 * @param bar
	 * 
	 */
	public DModificationMenu(DApplication application) {
		super(DConst.MODIFICATION);
		_dApplication = application;
		setFont(DxMenuBar.DxMB_FONT);
		buildMenu();
	}

	private void buildMenu() {
		buildModifyActivity();
		buildSpecialImport();
	}

	/**
	 * 
	 */
	private void buildModifyActivity() {
		_modifyActivity = new JMenuItem(DConst.ACTIVITY_MODIF_M);
		_modifyActivity.setFont(DxMenuBar.DxMB_FONT);
		class MAListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.modifyActivity();
			}
		}
		ActionListener modifyActivityListener = new MAListener();
		_modifyActivity.addActionListener(modifyActivityListener);
		this.add(_modifyActivity);
	} // end buildModifyActivity

	/**
	 * 
	 */
	private void buildSpecialImport() {
		_specialImport = new JMenu(DConst.SPECIAL_IMPORT);
		_specialImport.setFont(DxMenuBar.DxMB_FONT);

		// Horaire cycle
		_mergeInstructors = new JMenuItem(DConst.IMP_SELECT_INST);
		_mergeInstructors.setFont(DxMenuBar.DxMB_FONT);
		class MIListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.mergeInstructors();
			}
		}
		ActionListener mergeInstructorsListener = new MIListener();
		_mergeInstructors.addActionListener(mergeInstructorsListener);
		_specialImport.add(_mergeInstructors);

		_mergeRooms = new JMenuItem(DConst.IMP_SELECT_ROOM);
		_mergeRooms.setFont(DxMenuBar.DxMB_FONT);
		class MRListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.mergeRooms();
			}
		}
		ActionListener mergeRoomslistener = new MRListener();
		_mergeRooms.addActionListener(mergeRoomslistener);
		_specialImport.add(_mergeRooms);

		_mergeActivities = new JMenuItem(DConst.IMP_SELECT_ACT);
		_mergeActivities.setFont(DxMenuBar.DxMB_FONT);
		class MAListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.mergeActivities();
			}
		}
		ActionListener mergeActivitiesListener = new MAListener();
		_mergeActivities.addActionListener(mergeActivitiesListener);
		_specialImport.add(_mergeActivities);

		_mergeStudents = new JMenuItem(DConst.IMP_SELECT_STUD);
		_mergeStudents.setFont(DxMenuBar.DxMB_FONT);
		class MSListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.mergeStudents();
			}
		}
		ActionListener mergeStudentsListener = new MSListener();
		_mergeStudents.addActionListener(mergeStudentsListener);
		_specialImport.add(_mergeStudents);

		this.add(_specialImport);

	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#initialState()
	 */
	public void initialState() {
		this.setEnabled(false);
		_modifyActivity.setEnabled(false);
		_specialImport.setEnabled(false);
		_mergeInstructors.setEnabled(false);
		_mergeRooms.setEnabled(false);
		_mergeActivities.setEnabled(false);
		_mergeStudents.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterNewTTable()
	 */
	public void afterNewTTable() {
		this.setEnabled(false);
		_modifyActivity.setEnabled(false);
		_specialImport.setEnabled(false);
		_mergeInstructors.setEnabled(false);
		_mergeRooms.setEnabled(false);
		_mergeActivities.setEnabled(false);
		_mergeStudents.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterNewTTStruc()
	 */
	public void afterNewTTStruc() {
		this.setEnabled(false);
		_modifyActivity.setEnabled(false);
		_specialImport.setEnabled(false);
		_mergeInstructors.setEnabled(false);
		_mergeRooms.setEnabled(false);
		_mergeActivities.setEnabled(false);
		_mergeStudents.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterOpenTTSruc()
	 */
	public void afterOpenTTSruc() {
		this.setEnabled(false);
		_modifyActivity.setEnabled(false);
		_specialImport.setEnabled(false);
		_mergeInstructors.setEnabled(false);
		_mergeRooms.setEnabled(false);
		_mergeActivities.setEnabled(false);
		_mergeStudents.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterImport()
	 */
	public void afterImport() {
		this.setEnabled(false);
		_modifyActivity.setEnabled(false);
		_specialImport.setEnabled(false);
		_mergeInstructors.setEnabled(false);
		_mergeRooms.setEnabled(false);
		_mergeActivities.setEnabled(false);
		_mergeStudents.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterInitialAssignment()
	 */
	public void afterInitialAssignment() {
		this.setEnabled(true);
		_modifyActivity.setEnabled(true);
		_specialImport.setEnabled(true);
		_mergeInstructors.setEnabled(true);
		_mergeRooms.setEnabled(true);
		_mergeActivities.setEnabled(true);
		_mergeStudents.setEnabled(true);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#showAllMenus()
	 */
	public void showAllMenus() {
		this.setEnabled(true);
		_modifyActivity.setEnabled(true);
		_specialImport.setEnabled(true);
		_mergeInstructors.setEnabled(true);
		_mergeRooms.setEnabled(true);
		_mergeActivities.setEnabled(true);
		_mergeStudents.setEnabled(true);
	}

}
