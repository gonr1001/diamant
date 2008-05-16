/**
 * Created on Feb 16, 2006
 * 
 * 
 * Title: DPreferencesMenu.java 
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
 * Build the DPreferences Menu, for each menu Item there is a Listener to call the
 * activated action in DxApplication.
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
	public DPreferencesMenu(DApplication application) {
		super(DConst.PREF);
		_dApplication = application;
		setFont(DxMenuBar.DxMB_FONT);
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
				_dApplication.showPLAFDlg();
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
				_dApplication.showConflictsDlg();
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
		_viewSimple = new JMenuItem(DConst.SIMPLE);
		_viewSimple.setFont(DxMenuBar.DxMB_FONT);
		class ViewSimpleListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.simpleView();
			}
		}
		ActionListener viewSimpleListener = new ViewSimpleListener();
		_viewSimple.addActionListener(viewSimpleListener);
		_view.add(_viewSimple);

		_viewDetailedHorizontal = new JMenuItem(DConst.SPLIT_HORIZONTAL);
		_viewDetailedHorizontal.setFont(DxMenuBar.DxMB_FONT);
		class VDHListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.horizontalSplitView();
			}
		}
		ActionListener viewDetailedHorizontalistener = new VDHListener();
		_viewDetailedHorizontal
				.addActionListener(viewDetailedHorizontalistener);
		_view.add(_viewDetailedHorizontal);

		_viewDetailedVertical = new JMenuItem(DConst.SPLIT_VERTICAL);
		_viewDetailedVertical.setFont(DxMenuBar.DxMB_FONT);
		class VDVListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); //to avoid warning;
				_dApplication.vericalSplitview();
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
		_lookAndFeel.setEnabled(true);
		_allowedConflicts.setEnabled(true);
		_view.setEnabled(false);
		_viewSimple.setEnabled(false);
		_viewDetailedHorizontal.setEnabled(false);
		_viewDetailedVertical.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterNewTTable()
	 */
	public void afterNewTTable() {
		_lookAndFeel.setEnabled(true);
		_allowedConflicts.setEnabled(true);
		_view.setEnabled(true);
		_viewSimple.setEnabled(true);
		_viewDetailedHorizontal.setEnabled(true);
		_viewDetailedVertical.setEnabled(true);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterNewTTStruc()
	 */
	public void afterNewTTStruc() {
		_lookAndFeel.setEnabled(true);
		_allowedConflicts.setEnabled(true);
		_view.setEnabled(false);
		_viewSimple.setEnabled(false);
		_viewDetailedHorizontal.setEnabled(false);
		_viewDetailedVertical.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterInitialAssign()
	 */
	public void afterInitialAssign() {
		_lookAndFeel.setEnabled(true);
		_allowedConflicts.setEnabled(true);
		_view.setEnabled(true);
		_viewSimple.setEnabled(true);
		_viewDetailedHorizontal.setEnabled(true);
		_viewDetailedVertical.setEnabled(true);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterOpenTTSruc()
	 */
	public void afterOpenTTSruc() {
		_lookAndFeel.setEnabled(true);
		_allowedConflicts.setEnabled(true);
		_view.setEnabled(false);
		_viewSimple.setEnabled(false);
		_viewDetailedHorizontal.setEnabled(false);
		_viewDetailedVertical.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterImport()
	 */
	public void afterImport() {
		_lookAndFeel.setEnabled(true);
		_allowedConflicts.setEnabled(true);
		_view.setEnabled(true);
		_viewSimple.setEnabled(true);
		_viewDetailedHorizontal.setEnabled(true);
		_viewDetailedVertical.setEnabled(true);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#afterInitialAssignment()
	 */
	public void afterInitialAssignment() {
		_lookAndFeel.setEnabled(true);
		_allowedConflicts.setEnabled(true);
		_view.setEnabled(true);
		_viewSimple.setEnabled(true);
		_viewDetailedHorizontal.setEnabled(true);
		_viewDetailedVertical.setEnabled(true);
	}

	/* (non-Javadoc)
	 * @see dInterface.dMenus.MenuStates#showAllMenus()
	 */
	public void showAllMenus() {
		_lookAndFeel.setEnabled(true);
		_allowedConflicts.setEnabled(true);
		_view.setEnabled(true);
		_viewSimple.setEnabled(true);
		_viewDetailedHorizontal.setEnabled(true);
		_viewDetailedVertical.setEnabled(true);
	}

}
