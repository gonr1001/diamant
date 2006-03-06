/**
 * Created on Mar 3, 2006
 * 
 * TODO To change the class description for this generated file
 * 
 * Title: DFileMenuTest.java 
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
 *
 * 
 * 
 */
package dTest.dInterface.dMenus;


import javax.swing.JMenu;
import javax.swing.JMenuItem;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dConstants.DConst;
import dInterface.DApplication;
import dInterface.dMenus.DFileMenu;
import dInterface.dMenus.DxMenuBar;

/**
 * @author : Ruben Gonzalez-Rubio
 * 
 * Description: DFileMenuTest.java is a class used to:
 * <p>
 * 
 */
public class DFileMenuTest extends TestCase {
	DApplication _da;

	DxMenuBar _dxmb;

	public DFileMenuTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DFileMenuTest.class);
	} // end suite

	public void setUp() {
		_da = new DApplication();
		String[] args = { "-d" };
		_da.doIt(args);
		_dxmb = _da.getDxMenuBar();

	}

	public void tesIsDFileMenu() {
		JMenu dfm = (JMenu) _dxmb.getComponent(0);
		assertEquals("testIsDFileMenu: assertEquals", true,
				dfm instanceof DFileMenu);
	}

	public void testNewTTable() {
		DFileMenu dfm = (DFileMenu) _dxmb.getComponent(0);
		assertEquals("testNewTTable:", true, dfm
				.getMenuComponent(0) instanceof JMenu);
		JMenu jm = (JMenu) dfm.getMenuComponent(0);
		assertEquals("testNewTTable1:", 0, jm.getText().compareToIgnoreCase(DConst.NEW_TT));
	}

	public void testNewTTableCycle() {
		DFileMenu dfm = (DFileMenu) _dxmb.getComponent(0);
		assertEquals("testNewTTableCycle: test_newTTable", true, dfm
				.getMenuComponent(0) instanceof JMenuItem);
		JMenuItem jmi = (JMenuItem) dfm.getComponent();
		JMenuItem jmix = (JMenuItem) jmi.getComponent(0);
		assertEquals("testNewTTableCycle1: test_newTTable", 0, jmix.getText().compareToIgnoreCase(DConst.NTT_CY));
	}
}
