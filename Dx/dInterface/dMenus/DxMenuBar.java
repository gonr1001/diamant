/**
 * Created on Feb 17, 2006
 * 
 * 
 * Title: DMenuBar.java 
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

import java.awt.Font;
import java.util.Vector;

import javax.swing.JMenuBar;

import dInterface.DApplication;


/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DMenuBar.java is a class used to:
 * <p>
 * 
 */
public class DxMenuBar extends JMenuBar {

	//private DApplication _dApplication;

	private static final String cMFONT = "Dialog";

	private static final int cFONT = Font.PLAIN;

	private static final int cNPT11 = 11;

	public static final Font DxMB_FONT = new java.awt.Font(cMFONT, cFONT,
			cNPT11);

	private Vector _menus;

	/**
	 * 
	 */
	public DxMenuBar(DApplication dApplication) {
		super();
		//_dApplication = dApplication;
		_menus = buildMenuBar(dApplication);
		setInitialState();
	}

	/**
	 * 
	 */
	private Vector buildMenuBar(DApplication dApplication) {
		Vector v = new Vector();
		v.add(new DFileMenu(this, dApplication));
		v.add(new DAssignMenu(this, dApplication));
//		v.add(new DModificationMenu());
//		v.add(new DOptimisationMenu());
//		v.add(new DReportMenu());
//		v.add(new DPreferencesMenu());
//		v.add(new DHelpMenu());
//		v.add(new DNewFeaturesMenu());
//		if (_dApplication.getInDevelopment())
//			v.add(new DDevelopmentMenu());
		return v;
	}

	/**
	 * 
	 */
	public void setInitialState() {
		for (int i = 0; i < _menus.size(); i++) {
			((MenuStates) _menus.get(i)).initialState();
		}
	}
	/**
	 * the Time table can be cycle or exam
	 */
	public void afterNewTTable() {
		for (int i = 0; i < _menus.size(); i++) {
			((MenuStates) _menus.get(i)).afterNewTTable();
		}
	}

	/**
	 * 
	 */
	public void afterNewTTStruc() {
		for (int i = 0; i < _menus.size(); i++) {
			((MenuStates) _menus.get(i)).afterNewTTStruc();
		}
	}


	/**
	 * 
	 */
	public void afterInitialAssign() {
		for (int i = 0; i < _menus.size(); i++) {
			((MenuStates) _menus.get(i)).afterInitialAssign();
		}
	}

	/**
	 * 
	 */
	public void afterOpenTTSruc() {
		for (int i = 0; i < _menus.size(); i++) {
			((MenuStates) _menus.get(i)).afterOpenTTSruc();
		}
	}
	
	/**
	 * 
	 */
	public void afterClose() {
		for (int i = 0; i < _menus.size(); i++) {
			((MenuStates) _menus.get(i)).initialState();
		}
	}

	/**
	 * 
	 */
	public void afterImport() {
		for (int i = 0; i < _menus.size(); i++) {
			((MenuStates) _menus.get(i)).afterImport();
		}
	}
}
