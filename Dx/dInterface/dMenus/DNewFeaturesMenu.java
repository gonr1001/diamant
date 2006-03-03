/**
 * Created on Feb 16, 2006
 * 
 * 
 * 
 * Title: DNewFeaturesMenu.java 
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
 * Description: DNewFeaturesMenu.java is a class used to: 
 * <p>
 * Build the DNewFeatures Menu, for each menu Item there is a Listener to call the
 * activated action in DxApplication.
 * 
 * This menu is used only with new features that are in test
 *
 */
public class DNewFeaturesMenu extends JMenu implements MenuStates {

	private DApplication _dApplication;

	private JMenuItem _test1;

	private JMenu _test2;

	private JMenuItem _test2_1;

	private JMenuItem _test2_2;

	private JMenuItem _test2_3;

	/**
	 * @param application 
	 * @param bar 
	 * 
	 */
	public DNewFeaturesMenu(DApplication application) {
		super(DConst.IN_TEST);
		_dApplication = application;
		setFont(DxMenuBar.DxMB_FONT);
		buildMenu();
	}

	private void buildMenu() {
		buildTest1();
		this.addSeparator();
		buildTest2();
	}

	/**
	 * 
	 */
	private void buildTest1() {
		_test1 = new JMenuItem(DConst.CONFLICTS_OF_AN_EVENT);
		_test1.setFont(DxMenuBar.DxMB_FONT);
		class Test1Listener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.conflictsOfAnEvent();
			}
		}
		ActionListener test1Listener = new Test1Listener();
		_test1.addActionListener(test1Listener);
		this.add(_test1);
	} // end buildTest1

	/**
	 * 
	 */
	private void buildTest2() {
		_test2 = new JMenu(DConst.ROOMASSIGN);
		_test2.setFont(DxMenuBar.DxMB_FONT);

		// Horaire cycle
		_test2_1 = new JMenuItem(DConst.ROOMASSIGN);
		_test2_1.setFont(DxMenuBar.DxMB_FONT);
		class Test2_1Listener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.roomAssignment();
			}
		}
		ActionListener test2_1Listener = new Test2_1Listener();
		_test2_1.addActionListener(test2_1Listener);
		_test2.add(_test2_1);

		// test2_2
		_test2_2 = new JMenuItem(DConst.EVENTASSIGN);
		_test2_2.setFont(DxMenuBar.DxMB_FONT);
		class Test2_2Listener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.eventAssignment();
			}
		}
		ActionListener test2_2Listener = new Test2_2Listener();
		_test2_2.addActionListener(test2_2Listener);
		_test2.add(_test2_2);

		// test2_3
		_test2_3 = new JMenuItem(DConst.CONFLICTEFFECT);
		_test2_3.setFont(DxMenuBar.DxMB_FONT);
		class Test2_3Listener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.showConflictsDlg();
			}
		}
		ActionListener test2_3Listener = new Test2_3Listener();
		_test2_3.addActionListener(test2_3Listener);
		_test2.add(_test2_3);

		this.add(_test2);

	} // end buildTest2

	/**
	 * 
	 */

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#initialState()
	 */
	public void initialState() {
		this.setEnabled(false);
		_test1.setEnabled(false);
		_test2.setEnabled(false);
		_test2_1.setEnabled(false);
		_test2_2.setEnabled(false);
		_test2_3.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterNewTTable()
	 */
	public void afterNewTTable() {
		this.setEnabled(false);
		_test1.setEnabled(false);
		_test2.setEnabled(false);
		_test2_1.setEnabled(false);
		_test2_2.setEnabled(false);
		_test2_3.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterNewTTStruc()
	 */
	public void afterNewTTStruc() {
		this.setEnabled(false);
		_test1.setEnabled(false);
		_test2.setEnabled(false);
		_test2_1.setEnabled(false);
		_test2_2.setEnabled(false);
		_test2_3.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterInitialAssign()
	 */
	public void afterInitialAssign() {
		this.setEnabled(false);
		_test1.setEnabled(false);
		_test2.setEnabled(false);
		_test2_1.setEnabled(false);
		_test2_2.setEnabled(false);
		_test2_3.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterOpenTTSruc()
	 */
	public void afterOpenTTSruc() {
		this.setEnabled(false);
		_test1.setEnabled(false);
		_test2.setEnabled(false);
		_test2_1.setEnabled(false);
		_test2_2.setEnabled(false);
		_test2_3.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterImport()
	 */
	public void afterImport() {
		this.setEnabled(false);
		_test1.setEnabled(false);
		_test2.setEnabled(false);
		_test2_1.setEnabled(false);
		_test2_2.setEnabled(false);
		_test2_3.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterInitialAssignment()
	 */
	public void afterInitialAssignment() {
		this.setEnabled(true);
		_test1.setEnabled(true);
		_test2.setEnabled(true);
		_test2_1.setEnabled(true);
		_test2_2.setEnabled(true);
		_test2_3.setEnabled(true);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#showAllMenus()
	 */
	public void showAllMenus() {
		this.setEnabled(true);
		_test1.setEnabled(true);
		_test2.setEnabled(true);
		_test2_1.setEnabled(true);
		_test2_2.setEnabled(true);
		_test2_3.setEnabled(true);
	}

}
