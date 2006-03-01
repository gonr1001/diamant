/**
 * Created on Feb 16, 2006
 * 
 * TODO To change the class description for this generated file
 * 
 * Title: DHelpMenu.java 
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
 * @author : Ruben Gonzalez-Rubio
 *
 * Description: DHelpMenu.java is a class used to: 
 * <p>
 *
 */
public class DHelpMenu extends JMenu implements MenuStates {

	private DApplication _dApplication;

	private JMenuItem _about;

	/**
	 * @param application 
	 * @param bar 
	 * 
	 */
	public DHelpMenu(DApplication application) {
		super(DConst.HELP);
		_dApplication = application;
		setFont(DxMenuBar.DxMB_FONT);
		buildMenu();
	}

	private void buildMenu() {
		buildHelp();
	}

	/**
	 * 
	 */
	private void buildHelp() {
		_about = new JMenuItem(DConst.ABOUT_M);
		_about.setFont(DxMenuBar.DxMB_FONT);
		class ExitListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.showAboutDlg();
			}
		}
		ActionListener exitListener = new ExitListener();
		_about.addActionListener(exitListener);
		this.add(_about);
	} // end buildHelp

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#initialState()
	 */
	public void initialState() {
		// empty	
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterNewTTable()
	 */
	public void afterNewTTable() {
		// empty	
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterNewTTStruc()
	 */
	public void afterNewTTStruc() {
		// empty	
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterInitialAssign()
	 */
	public void afterInitialAssign() {
		// empty	

	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterOpenTTSruc()
	 */
	public void afterOpenTTSruc() {
		// empty
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterImport()
	 */
	public void afterImport() {
		// empty
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterInitialAssignment()
	 */
	public void afterInitialAssignment() {
		// empty
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#showAllMenus()
	 */
	public void showAllMenus() {
		// empty
	}

}
