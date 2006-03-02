/**
 * Created on Feb 17, 2006
 * 
 * 
 * Title: DxMenuBar.java 
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

import java.awt.Font;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import dConstants.DConst;
import dInterface.DApplication;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DMenuBar.java is a class used to:
 * <p>
 * Display the menus. Each menu is a class.
 * There is a finite state automaton to change each menu Item
 * enabled or disabled.
 * <p>
 * The finite state automaton transitions are loops calling
 * the corresponding method inside each menu class.
 * <p>
 * Each menu class implements MenuState interface, the names
 * of methods in the Interface are the same that the transitions
 * 
 * 
 * 
 */
public class DxMenuBar extends JMenuBar {

	private final int NO_CHANGE_MENUS = 3;

	private static final String cMFONT = "Dialog";

	private static final int cFONT = Font.PLAIN;

	private static final int cNPT11 = 11;

	public static final Font DxMB_FONT = new java.awt.Font(cMFONT, cFONT,
			cNPT11);

	private DApplication _dApplication;

	/**
	 * @param dApplication is needed because individual
	 * menus calls methods of dApplication.
	 * the user see in the dialog display.
	 *
	 * <p>After buil the Menu transition initialState is done.
	 */
	public DxMenuBar(DApplication dApplication) {
		super();
		_dApplication = dApplication;
		buildMenuBar(); // uses _dApplication
		initialState();
	}

	/**
	 * 
	 * In this transition if the MultiSite Menu is on the
	 * menu bar it is removed
	 * 
	 * 
	 */
	public void initialState() {
		if (this.getMenuCount() != 0) {
			for (int i = 0; i < this.getComponentCount(); i++) {
				JMenu comp = (JMenu) this.getComponent(i);
				if (comp.getText().equalsIgnoreCase(DConst.MULTI_SITE))
					this.remove(i);
			}
		}
		for (int i = 0; i < this.getMenuCount(); i++) {
			((MenuStates) this.getComponent(i)).initialState();
		}
		this.updateUI();
	}

	/**
	 * 
	 * The Timetable can be cycle or exam
	 * 
	 */
	public void afterNewTTable() {
		for (int i = 0; i < this.getMenuCount(); i++) {
			((MenuStates) this.getComponent(i)).afterNewTTable();
		}
		this.updateUI();
	}

	/**
	 * 
	 */
	public void afterNewTTStruc() {
		for (int i = 0; i < this.getMenuCount(); i++) {
			((MenuStates) this.getComponent(i)).afterNewTTStruc();
		}
		this.updateUI();
	}

	/**
	 * 
	 */
	public void afterOpenTTSruc() {
		for (int i = 0; i < this.getMenuCount(); i++) {
			((MenuStates) this.getComponent(i)).afterOpenTTSruc();
		}
	}

	/**
	 * 
	 */
	public void afterImport() {
		for (int i = 0; i < this.getMenuCount(); i++) {
			((MenuStates) this.getComponent(i)).afterImport();
		}
	}

	/**
	 * 
	 */
	public void afterInitialAssignment() {
		if (_dApplication.isMultiSite()) {
			addMultiSiteToMenuBar();
		}
		for (int i = 0; i < this.getMenuCount(); i++) {
			((MenuStates) this.getComponent(i)).afterInitialAssignment();
		}
		this.updateUI();
	}

	/**
	 * 
	 */
	public void showAllMenus() {
		for (int i = 0; i < this.getMenuCount(); i++) {
			((MenuStates) this.getComponent(i)).showAllMenus();
		}
	}

	/**
	 * 
	 */
	private void buildMenuBar() {
		this.add(new DFileMenu(_dApplication));
		this.add(new DAssignMenu(_dApplication));
		this.add(new DModificationMenu(_dApplication));
		this.add(new DOptimisationMenu(_dApplication));
		this.add(new DReportMenu(_dApplication));
		this.add(new DPreferencesMenu(_dApplication));
		this.add(new DHelpMenu(_dApplication));
		this.add(new DNewFeaturesMenu(_dApplication));
		if (_dApplication.isInDevelopment()) {
			this.add(new DDevelopmentMenu(_dApplication));
		}
	} // end buildMenuBar

	/**
	 * 
	 */
	private void addMultiSiteToMenuBar() {
		for (int i = this.getMenuCount() - 1; i > NO_CHANGE_MENUS; i--) {
			this.remove(i);
		}
		this.add(new DMultiSiteMenu(_dApplication));
		this.add(new DReportMenu(_dApplication));
		this.add(new DPreferencesMenu(_dApplication));
		this.add(new DHelpMenu(_dApplication));
		this.add(new DNewFeaturesMenu(_dApplication));
		if (_dApplication.isInDevelopment()) {
			this.add(new DDevelopmentMenu(_dApplication));
		}
	}// end addMultiSiteToMenuBar
	
} // end DxMenuBar
