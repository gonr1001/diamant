/**
 * Created on Feb 16, 2006
 * 
 * 
 * Title: DOptimisationMenu.java 
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

/**
 * Ruben Gonzalez-Rubio
 *
 * Description: DOptimisationMenu.java is a class used to: 
 * <p>
 *
 */
public class DOptimisationMenu extends JMenu implements MenuStates {

	private DApplication _dApplication;

	private JMenuItem _initialAssignment;

	private JMenuItem _doOptimization;

	private JMenuItem _doSectionPartition;

	/**
	 * @param application 
	 * @param bar 
	 * 
	 */
	public DOptimisationMenu(DxMenuBar bar, DApplication dApplication) {
		super(DConst.OPTIMIZATION);
		_dApplication = dApplication;
		setFont(DxMenuBar.DxMB_FONT);
		bar.add(this);
		buildMenu();
	}

	private void buildMenu() {
		buildInitialAssignment();
		buildDoTheTimeTable();
		buildDoSectionPartition();
	}

	/**
	 * 
	 */
	private void buildInitialAssignment() {
		_initialAssignment = new JMenuItem(DConst.M_INITIAL_ASSIGN);
		_initialAssignment.setFont(DxMenuBar.DxMB_FONT);
		class IAListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.initialAssignment();
			}
		}
		ActionListener initialAssignmentListener = new IAListener();
		_initialAssignment.addActionListener(initialAssignmentListener);
		this.add(_initialAssignment);
	} // end buildInitialAssignment

	/**
	 * 
	 */
	private void buildDoTheTimeTable() {
		_doOptimization = new JMenuItem(DConst.FIRSTALGORITHM);
		_doOptimization.setFont(DxMenuBar.DxMB_FONT);
		class ExitListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.doTheTimeTable();
			}
		}
		ActionListener exitlistener = new ExitListener();
		_doOptimization.addActionListener(exitlistener);
		this.add(_doOptimization);
	} // end buildExit

	/**
	 * 
	 */
	private void buildDoSectionPartition() {
		_doSectionPartition = new JMenuItem(DConst.SECTION_PARTITION);
		_doSectionPartition.setFont(DxMenuBar.DxMB_FONT);
		class ExitListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.doSectionPartition();
			}
		}
		ActionListener exitlistener = new ExitListener();
		_doSectionPartition.addActionListener(exitlistener);
		this.add(_doSectionPartition);
	} // end buildExit

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#initialState()
	 */
	public void initialState() {
		this.setEnabled(false);
		_initialAssignment.setEnabled(false);
		_doOptimization.setEnabled(false);
		_doSectionPartition.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterNewTTable()
	 */
	public void afterNewTTable() {
		this.setEnabled(false);
		_initialAssignment.setEnabled(false);
		_doOptimization.setEnabled(false);
		_doSectionPartition.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterNewTTStruc()
	 */
	public void afterNewTTStruc() {
		_initialAssignment.setEnabled(false);
		_doOptimization.setEnabled(false);
		_doSectionPartition.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterInitialAssign()
	 */
	public void afterInitialAssign() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterOpenTTSruc()
	 */
	public void afterOpenTTSruc() {
		this.setEnabled(false);
		_initialAssignment.setEnabled(false);
		_doOptimization.setEnabled(false);
		_doSectionPartition.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterImport()
	 */
	public void afterImport() {
		this.setEnabled(true);
		_initialAssignment.setEnabled(true);
		_doOptimization.setEnabled(false);
		_doSectionPartition.setEnabled(false);

	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterInitialAssignment()
	 */
	public void afterInitialAssignment() {
		this.setEnabled(true);
		_initialAssignment.setEnabled(false);
		_doOptimization.setEnabled(true);
		_doSectionPartition.setEnabled(true);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#showAllMenus()
	 */
	public void showAllMenus() {
		this.setEnabled(true);
		_initialAssignment.setEnabled(true);
		_doOptimization.setEnabled(true);
		_doSectionPartition.setEnabled(true);
	}
}
