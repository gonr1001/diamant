/**
 * Created on Feb 16, 2006
 * 
 * TODO To change the class description for this generated file
 * 
 * Title: DNewFeaturesMenu.java 
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
 * Description: DNewFeaturesMenu.java is a class used to: 
 * <p>
 *
 */
public class DNewFeaturesMenu extends JMenu implements MenuStates {
	
	private DApplication _dApplication;

	private JMenuItem _about;
	/**
	 * @param application 
	 * @param bar 
	 * 
	 */
	public DNewFeaturesMenu(DxMenuBar bar, DApplication application) {
		super(DConst.IN_TEST);
		_dApplication = application;
		setFont(DxMenuBar.DxMB_FONT);
		bar.add(this);
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
				_dApplication.exit();
			}
		}
		ActionListener exitListener = new ExitListener();
		_about.addActionListener(exitListener);
		this.add(_about);
	} // end buildExit

	/**
	 * 
	 */
	private void buildHelp1() {
		_about = new JMenuItem(DConst.ABOUT_M);
		_about.setFont(DxMenuBar.DxMB_FONT);
		class ExitListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.exit();
			}
		}
		ActionListener exitListener = new ExitListener();
		_about.addActionListener(exitListener);
		this.add(_about);
	} // end buildExit
	
	/**
	 * 
	 */
	private void buildHelp2() {
		_about = new JMenuItem(DConst.ABOUT_M);
		_about.setFont(DxMenuBar.DxMB_FONT);
		class ExitListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.exit();
			}
		}
		ActionListener exitListener = new ExitListener();
		_about.addActionListener(exitListener);
		this.add(_about);
	} // end buildExit
	
	/**
	 * 
	 */
	private void buildHelp3() {
		_about = new JMenuItem(DConst.ABOUT_M);
		_about.setFont(DxMenuBar.DxMB_FONT);
		class ExitListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.exit();
			}
		}
		ActionListener exitListener = new ExitListener();
		_about.addActionListener(exitListener);
		this.add(_about);
	} // end buildExit
	
	/**
	 * 
	 */
	private void buildHelp4() {
		_about = new JMenuItem(DConst.ABOUT_M);
		_about.setFont(DxMenuBar.DxMB_FONT);
		class ExitListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.exit();
			}
		}
		ActionListener exitListener = new ExitListener();
		_about.addActionListener(exitListener);
		this.add(_about);
	} // end buildExit
	
	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#initialState()
	 */
	public void initialState() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterNewTTable()
	 */
	public void afterNewTTable() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterNewTTStruc()
	 */
	public void afterNewTTStruc() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterImport()
	 */
	public void afterImport() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterInitialAssignment()
	 */
	public void afterInitialAssignment() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#showAllMenus()
	 */
	public void showAllMenus() {
		// TODO Auto-generated method stub
		
	}

}
