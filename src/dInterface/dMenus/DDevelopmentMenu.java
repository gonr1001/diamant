/**
 * Created on Feb 16, 2006
 * 
 * 
 * Title: DDevelopmentMenu.java 
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

import dInterface.DApplication;

/**
 * Ruben Gonzalez-Rubio
 *
 * Description: DDevelopmentMenu.java is a class used to:
 * <p>
 * Build the DDevelopment Menu, for each menu Item there is a Listener to call the
 * activated action in DxApplication.
 * <p>
 * This menu is only used by developpers
 * to be active the option -d is needed when launching the program.
 * 
 *
 */
@SuppressWarnings("serial")
public class DDevelopmentMenu extends JMenu implements MenuStates {

	private DApplication _dApplication;

	private JMenuItem _myFile;

	private JMenuItem _showAll;

	private JMenuItem _initialState;

	/**
	 * @param application 
	 * @param bar 
	 * 
	 */
	public DDevelopmentMenu(DApplication application) {
		super("Development");
		_dApplication = application;
		setFont(DxMenuBar.DxMB_FONT);
		buildMenu();
	}

	private void buildMenu() {
		buildFile("fichier1xx.dia");
		buildShowAllMenus();
		buildInitialState();
	}

	/**
	 * @param file 
	 * 
	 */
	private void buildFile(String file) {
		_myFile = new JMenuItem(file);
		_myFile.setFont(DxMenuBar.DxMB_FONT);
		class MyFileListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.openDevFile();
			}
		}
		ActionListener myFileListener = new MyFileListener();
		_myFile.addActionListener(myFileListener);
		this.add(_myFile);
	} // end buildFile

	
	private void buildShowAllMenus() {
		_showAll = new JMenuItem("showAllMenus");
		_showAll.setFont(DxMenuBar.DxMB_FONT);
		class ShowAllListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.showAllMenus();
			}
		}
		ActionListener showAllListener = new ShowAllListener();
		_showAll.addActionListener(showAllListener);
		this.add(_showAll);
	} // end buildShowAllMenus

	/**
	 * 
	 */
	private void buildInitialState() {
		_initialState = new JMenuItem("initialMenus");
		_initialState.setFont(DxMenuBar.DxMB_FONT);
		class ISListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.initialState();
			}
		}
		ActionListener initialStateListener = new ISListener();
		_initialState.addActionListener(initialStateListener);
		this.add(_initialState);
	} // end buildInitialState

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterNewTTable()
	 */
	public void initialState() {
		//empty
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterNewTTable()
	 */
	public void afterNewTTable() {
		//empty
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterNewTTStruc()
	 */
	public void afterNewTTStruc() {
		//empty
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterInitialAssign()
	 */
	public void afterInitialAssign() {
		//empty
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterOpenTTSruc()
	 */
	public void afterOpenTTSruc() {
		//empty
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterImport()
	 */
	public void afterImport() {
		//empty
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterInitialAssignment()
	 */
	public void afterInitialAssignment() {
		//empty
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#showAllMenus()
	 */
	public void showAllMenus() {
		//empty
	}

}
