/**
 * Created on Mar 3, 2006
 * 
 * 
 * TODO: Put more tests  
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


	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DFileMenuTest.class);
	} // end suite
//
//	public void setUp() {
//
//	}

	public void tesIsDFileMenu() {
		DApplication da = new DApplication();
		String[] args = { "-d" };
		da.doIt(args);
		DxMenuBar _dxmb = da.getDxMenuBar();

		JMenu dfm = (JMenu) _dxmb.getComponent(0);
		assertEquals("testIsDFileMenu: assertEquals", true,
				dfm instanceof DFileMenu);
		da = null;
	}

	public void testNewTTable() {
		DApplication da = new DApplication();
		String[] args = { "-d" };
		da.doIt(args);
		DxMenuBar _dxmb = da.getDxMenuBar();

		DFileMenu dfm = (DFileMenu) _dxmb.getComponent(0);
		assertEquals("testNewTTable:", true, dfm
				.getMenuComponent(0) instanceof JMenu);
		JMenu jm = (JMenu) dfm.getMenuComponent(0);
		assertEquals("testNewTTable1:", 0, jm.getText().compareToIgnoreCase(DConst.NEW_TT));
		da = null;
	}

	public void testNewTTableCycle() {
		DApplication _da = new DApplication();
		String[] args = { "-d" };
		_da.doIt(args);
		DxMenuBar _dxmb = _da.getDxMenuBar();

		DFileMenu dfm = (DFileMenu) _dxmb.getComponent(0);
		assertEquals("testNewTTableCycle: test_newTTable", true, dfm
				.getMenuComponent(0) instanceof JMenuItem);
		
		JMenuItem jmi = null;
		for (int i = 0; i < dfm.getItemCount(); i++){
			jmi = dfm.getItem(i);	
		}
		assertEquals("testNewTTableCycle1:", 14, dfm.getItemCount());
		if (jmi != null) 
			assertEquals("testNewTTableCycle2:", 0,jmi.getText().compareToIgnoreCase(DConst.EXIT));
	}
}
