/**
 * Created on Feb 16, 2006
 * 
 * 
 * Title: DReportMenu.java 
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
 * Description: DReportMenu.java is a class used to: 
 * <p>
 *
 */
public class DReportMenu extends JMenu implements MenuStates {

	private DApplication _dApplication;
	
	private JMenuItem _report;
	/**
	 * @param application 
	 * @param bar 
	 * 
	 */
	public DReportMenu(DApplication dApplication) {
		super(DConst.REPORT_M);
		_dApplication = dApplication;
		setFont(DxMenuBar.DxMB_FONT);
		buildMenu();
	}

	private void buildMenu() {
		buildReport();
	}
	/**
	 * 
	 */
	private void buildReport() {
		_report = new JMenuItem(DConst.REPORTS);
		_report.setFont(DxMenuBar.DxMB_FONT);
		class ExitListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.report();
			}
		}
		ActionListener exitlistener = new ExitListener();
		_report.addActionListener(exitlistener);
		this.add(_report);
	} // end buildExit

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#initialState()
	 */
	public void initialState() {
		this.setEnabled(false);
		_report.setEnabled(false);		
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterNewTTable()
	 */
	public void afterNewTTable() {
		this.setEnabled(false);
		_report.setEnabled(false);	
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterNewTTStruc()
	 */
	public void afterNewTTStruc() {
		this.setEnabled(false);
		_report.setEnabled(false);	
	}


	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterOpenTTSruc()
	 */
	public void afterOpenTTSruc() {
		this.setEnabled(false);
		_report.setEnabled(false);		
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterImport()
	 */
	public void afterImport() {
		this.setEnabled(true);
		_report.setEnabled(true);		
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterInitialAssignment()
	 */
	public void afterInitialAssignment() {
		this.setEnabled(true);
		_report.setEnabled(true);		
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#showAllMenus()
	 */
	public void showAllMenus() {
		this.setEnabled(true);
		_report.setEnabled(true);		
	}
}
