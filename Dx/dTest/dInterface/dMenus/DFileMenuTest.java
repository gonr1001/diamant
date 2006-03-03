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

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

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
public class DFileMenuTest extends TestCase{
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
  
	public void setUp(){
		_da = new DApplication();
		String [] args = {"-d"} ; 
		_da.doIt(args);
		_dxmb = _da.getDxMenuBar();
		
	}
	
	public void test_isDFileMenu(){				
		JMenu dfm =(JMenu)_dxmb.getComponent(0);
		assertEquals("test_isDFileMenu: assertEquals",true,dfm instanceof DFileMenu);
	}
	
	public void test_newTTable(){
		DFileMenu dfm =(DFileMenu)_dxmb.getComponent(0);
		assertEquals("test_addState: test_newTTable",true,dfm.getMenuComponent(0) instanceof JMenu);
	}
}
