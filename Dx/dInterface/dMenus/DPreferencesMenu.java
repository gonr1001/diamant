/**
 * Created on Feb 16, 2006
 * 
 * TODO To change the class description for this generated file
 * 
 * Title: DPreferencesMenu.java 
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
 * Description: DPreferencesMenu.java is a class used to: 
 * <p>
 *
 */
public class DPreferencesMenu extends JMenu implements MenuStates {
	
	private DApplication _dApplication;
	
	private JMenuItem _lookAndFeel;
	
	private JMenuItem _allowedConflicts;
	
	private JMenu _view;
	
	private JMenuItem _viewSimple;

	private JMenuItem _viewDetailedHorizontal;

	private JMenuItem _viewDetailedVertical;
	
	

	/**
	 * @param application 
	 * @param bar 
	 * 
	 */
	public DPreferencesMenu(DxMenuBar bar, DApplication application) {
		super(DConst.PREF);
		_dApplication = application;
		setFont(DxMenuBar.DxMB_FONT);
		bar.add(this);
		buildMenu();
	}

	private void buildMenu() {
		buildLookAndFeel();
		buildAllowedConflicts();
		buildView();
	}
	
	/**
	 * 
	 */
	private void buildLookAndFeel() {
		_lookAndFeel = new JMenuItem(DConst.PLAF_M);
		_lookAndFeel.setFont(DxMenuBar.DxMB_FONT);
		class LookAndFeelListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.exit();
			}
		}
		ActionListener lookAndFeelListener = new LookAndFeelListener();
		_lookAndFeel.addActionListener(lookAndFeelListener);
		this.add(_lookAndFeel);
	} // end buildExit
	
	/**
	 * 
	 */
	private void buildAllowedConflicts() {
		_allowedConflicts = new JMenuItem(DConst.CONFLICTS);
		_allowedConflicts.setFont(DxMenuBar.DxMB_FONT);
		class ExitListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.exit();
			}
		}
		ActionListener ACListener = new ExitListener();
		_allowedConflicts.addActionListener(ACListener);
		this.add(_allowedConflicts);
	} // end buildExit
	
	/**
	 * 
	 */
	private void buildView() {
		_view = new JMenu(DConst.DISPLAY_TT);
		_view.setFont(DxMenuBar.DxMB_FONT);

		// Horaire cycle
		_viewSimple = new JMenuItem(DConst.NTT_CY);
		_viewSimple.setFont(DxMenuBar.DxMB_FONT);
		class ViewSimpleListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.newTTableCycle();
			}
		}
		ActionListener viewSimpleListener = new ViewSimpleListener();
		_viewSimple.addActionListener(viewSimpleListener);
		_view.add(_viewSimple);

		_viewDetailedHorizontal = new JMenuItem(DConst.NTT_EX);
		_viewDetailedHorizontal.setFont(DxMenuBar.DxMB_FONT);
		class VDHListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.newTTableExam();
			}
		}
		ActionListener viewDetailedHorizontalistener = new VDHListener();
		_viewDetailedHorizontal.addActionListener(viewDetailedHorizontalistener);
		_view.add(_viewDetailedHorizontal);

		_viewDetailedVertical = new JMenuItem(DConst.NTT_EX);
		_viewDetailedVertical.setFont(DxMenuBar.DxMB_FONT);
		class VDVListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.newTTableExam();
			}
		}
		ActionListener viewDetailedVerticalListener = new VDVListener();
		_viewDetailedVertical.addActionListener(viewDetailedVerticalListener);
		_view.add(_viewDetailedVertical);
		
		this.add(_view);
		
	}

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
