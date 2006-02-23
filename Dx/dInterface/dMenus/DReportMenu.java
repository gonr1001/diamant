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
	public DReportMenu(DxMenuBar bar, DApplication dApplication) {
		super(DConst.REPORT_M);
		_dApplication = dApplication;
		setFont(DxMenuBar.DxMB_FONT);
		bar.add(this);
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
