/**
 * Created on Feb 16, 2006
 * 
 * 
 * Title: DFileMenu.java 
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
 * Description: DFileMenu.java is a class used to:
 * <p>
 * Build the File Menu, for each menu Item there is a Listener to call the
 * activated action in DxApplication.
 * 
 */
public class DFileMenu extends JMenu implements MenuStates {

	private DApplication _dApplication;

	private JMenu _newTTable;

	private JMenu _newTTStruc;

	private JMenuItem _newTTableCycle;

	private JMenuItem _newTTableExam;

	private JMenuItem _newTTStrucCycle;

	private JMenuItem _newTTStrucExam;

	private JMenuItem _openTTable;

	private JMenuItem _openTTStruc;

	private JMenuItem _close;

	private JMenuItem _save;

	private JMenuItem _saveAs;

	private JMenuItem _defineFiles;

	private JMenuItem _import;

	private JMenuItem _export;

	private JMenuItem _exit;

	/**
	 * @param application
	 * @param bar
	 * 
	 */
	public DFileMenu(DApplication dApplication) {
		super(DConst.M_FILE);
		_dApplication = dApplication;
		setFont(DxMenuBar.DxMB_FONT);
		buildMenu();
	}

	private void buildMenu() {
		buildNewTTable();
		buildNewTTStruc();
		buildOpenTTable();
		buildOpenTTStuct();
		buildClose();
		this.addSeparator();
		buildSave();
		buildSaveAs();
		this.addSeparator();
		buildDefineFiles();
		buildImport();
		buildExport();
		this.addSeparator();
		buildExit();
	}

	private void buildNewTTable() {
		_newTTable = new JMenu(DConst.NEW_TT);
		_newTTable.setFont(DxMenuBar.DxMB_FONT);

		// timetable for a cycle
		_newTTableCycle = new JMenuItem(DConst.NTT_CY);
		_newTTableCycle.setFont(DxMenuBar.DxMB_FONT);
		class NewTTableCycleListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.newTTableCycle();
			}
		}
		ActionListener newTTableCycleListener = new NewTTableCycleListener();
		_newTTableCycle.addActionListener(newTTableCycleListener);
		_newTTable.add(_newTTableCycle);

		// timetable for an exam
		_newTTableExam = new JMenuItem(DConst.NTT_EX);
		_newTTableExam.setFont(DxMenuBar.DxMB_FONT);
		class NewTTableExamListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.newTTableExam();
			}
		}
		ActionListener newTTableExamListener = new NewTTableExamListener();
		_newTTableExam.addActionListener(newTTableExamListener);
		_newTTable.add(_newTTableExam);

		this.add(_newTTable);
	} // end buildNewTTable

	private void buildNewTTStruc() {
		_newTTStruc = new JMenu(DConst.NEW_TTS);
		_newTTStruc.setFont(DxMenuBar.DxMB_FONT);
		// Struc Cycle
		_newTTStrucCycle = new JMenuItem(DConst.NTT_CY);
		_newTTStrucCycle.setFont(DxMenuBar.DxMB_FONT);
		class NewTTStrucCycleListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.newTTStrucCycle();
			}
		}
		ActionListener newTTableCycleListener = new NewTTStrucCycleListener();
		_newTTStrucCycle.addActionListener(newTTableCycleListener);
		_newTTStruc.add(_newTTStrucCycle);

		// Struc Exam
		_newTTStrucExam = new JMenuItem(DConst.NTT_EX);
		_newTTStrucExam.setFont(DxMenuBar.DxMB_FONT);
		class NewTTStrucExamListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.newTTStrucExam();
			}
		}
		ActionListener newTTableExamListener = new NewTTStrucExamListener();
		_newTTStrucExam.addActionListener(newTTableExamListener);
		_newTTStruc.add(_newTTStrucExam);

		this.add(_newTTStruc);
	} // end buildNewTTStruc

	/**
	 * 
	 */
	private void buildOpenTTable() {
		_openTTable = new JMenuItem(DConst.OPEN);
		_openTTable.setFont(DxMenuBar.DxMB_FONT);
		class OpenTTableListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.openTTable();
			}
		}
		ActionListener openTTableListener = new OpenTTableListener();
		_openTTable.addActionListener(openTTableListener);
		this.add(_openTTable);
	} // end buildOpenTTable

	/**
	 * 
	 */
	private void buildOpenTTStuct() {
		_openTTStruc = new JMenuItem(DConst.OPEN_TTS);
		_openTTStruc.setFont(DxMenuBar.DxMB_FONT);
		class OpenTTStrucListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.openTTStruc();
			}
		}
		ActionListener _openTTStrucListener = new OpenTTStrucListener();
		_openTTStruc.addActionListener(_openTTStrucListener);
		this.add(_openTTStruc);
	} // end buildOpenTTStuct

	/**
	 * 
	 */
	private void buildClose() {
		_close = new JMenuItem(DConst.BUT_CLOSE);
		_close.setFont(DxMenuBar.DxMB_FONT);
		class CloseListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.close();
			}
		}
		ActionListener closeListener = new CloseListener();
		_close.addActionListener(closeListener);
		this.add(_close);
	} // end close

	/**
	 * 
	 */
	private void buildSave() {
		_save = new JMenuItem(DConst.SAVE);
		_save.setFont(DxMenuBar.DxMB_FONT);
		class SaveListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.save();
			}
		}
		ActionListener saveListener = new SaveListener();
		_save.addActionListener(saveListener);
		this.add(_save);
	} // end buildSave

	/**
	 * 
	 */
	private void buildSaveAs() {
		_saveAs = new JMenuItem(DConst.SAVE_AS);
		_saveAs.setFont(DxMenuBar.DxMB_FONT);
		class SaveAsListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.saveAs();
			}
		}
		ActionListener saveAsListener = new SaveAsListener();
		_saveAs.addActionListener(saveAsListener);
		this.add(_saveAs);
	} // end buildSaveAs

	/**
	 * 
	 */
	private void buildDefineFiles() {
		_defineFiles = new JMenuItem(DConst.DEF_F_M);
		_defineFiles.setFont(DxMenuBar.DxMB_FONT);
		class DefineFilesListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.defineFiles();
			}
		}
		ActionListener defineFilesListener = new DefineFilesListener();
		_defineFiles.addActionListener(defineFilesListener);
		this.add(_defineFiles);
	} // end buildDefineFiles

	/**
	 * 
	 */
	private void buildImport() {
		_import = new JMenuItem(DConst.IMP_A_M);
		_import.setFont(DxMenuBar.DxMB_FONT);
		class ImportListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.importFiles();
			}
		}
		ActionListener importListener = new ImportListener();
		_import.addActionListener(importListener);
		this.add(_import);
	} // end buildImport

	/**
	 * 
	 */
	private void buildExport() {
		_export = new JMenuItem(DConst.EXPO);
		_export.setFont(DxMenuBar.DxMB_FONT);
		class ExportListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.exportFiles();
			}
		}
		ActionListener exportListener = new ExportListener();
		_export.addActionListener(exportListener);
		this.add(_export);
	} // end buildExport

	/**
	 * 
	 */
	private void buildExit() {
		_exit = new JMenuItem(DConst.EXIT);
		_exit.setFont(DxMenuBar.DxMB_FONT);
		class ExitListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				event.toString(); // to avoid warning;
				_dApplication.closeApplic();
			}
		}
		ActionListener exitListener = new ExitListener();
		_exit.addActionListener(exitListener);
		this.add(_exit);
	} // end buildExit

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInterface.dMenus.MenuStates#initialState()
	 */
	public void initialState() {
		_newTTable.setEnabled(true);
		_newTTableCycle.setEnabled(true);
		_newTTableExam.setEnabled(true);
		_newTTStruc.setEnabled(true);
		_newTTStrucCycle.setEnabled(true);
		_newTTStrucExam.setEnabled(true);
		_openTTable.setEnabled(true);
		_openTTStruc.setEnabled(true);
		_close.setEnabled(false);
		_save.setEnabled(false);
		_saveAs.setEnabled(false);
		_defineFiles.setEnabled(true);
		_import.setEnabled(false);
		_export.setEnabled(false);
		_exit.setEnabled(true);
	} // end initialState

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInterface.dMenus.MenuStates#afterNewTTCycle()
	 */
	public void afterNewTTable() {
		_newTTable.setEnabled(false);
		_newTTableCycle.setEnabled(false);
		_newTTableExam.setEnabled(false);
		_newTTStruc.setEnabled(false);
		_newTTStrucCycle.setEnabled(false);
		_newTTStrucExam.setEnabled(false);
		_openTTable.setEnabled(false);
		_openTTStruc.setEnabled(false);
		_close.setEnabled(true);
		_save.setEnabled(true);
		_saveAs.setEnabled(true);
		_defineFiles.setEnabled(true);
		_import.setEnabled(true);
		_export.setEnabled(false);
		_exit.setEnabled(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInterface.dMenus.MenuStates#afterNewTTSCycle()
	 */
	public void afterNewTTStruc() {
		_newTTable.setEnabled(false);
		_newTTableCycle.setEnabled(false);
		_newTTableExam.setEnabled(false);
		_newTTStruc.setEnabled(false);
		_newTTStrucCycle.setEnabled(false);
		_newTTStrucExam.setEnabled(false);
		_openTTable.setEnabled(false);
		_openTTStruc.setEnabled(false);
		_close.setEnabled(true);
		_save.setEnabled(true);
		_saveAs.setEnabled(true);
		_defineFiles.setEnabled(true);
		_import.setEnabled(false);
		_export.setEnabled(false);
		_exit.setEnabled(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInterface.dMenus.MenuStates#afterOpenTTSruc()
	 */
	public void afterOpenTTSruc() {
		_newTTable.setEnabled(false);
		_newTTableCycle.setEnabled(false);
		_newTTableExam.setEnabled(false);
		_newTTStruc.setEnabled(false);
		_newTTStrucCycle.setEnabled(false);
		_newTTStrucExam.setEnabled(false);
		_openTTable.setEnabled(false);
		_openTTStruc.setEnabled(false);
		_close.setEnabled(true);
		_save.setEnabled(true);
		_saveAs.setEnabled(true);
		_defineFiles.setEnabled(true);
		_import.setEnabled(false);
		_export.setEnabled(true);
		_exit.setEnabled(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInterface.dMenus.MenuStates#afterImport()
	 */
	public void afterImport() {
		_newTTable.setEnabled(false);
		_newTTableCycle.setEnabled(false);
		_newTTableExam.setEnabled(false);
		_newTTStruc.setEnabled(false);
		_newTTStrucCycle.setEnabled(false);
		_newTTStrucExam.setEnabled(false);
		_openTTable.setEnabled(false);
		_openTTStruc.setEnabled(false);
		_close.setEnabled(true);
		_save.setEnabled(false);
		_saveAs.setEnabled(false);
		_defineFiles.setEnabled(true);
		_import.setEnabled(false);
		_export.setEnabled(false);
		_exit.setEnabled(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInterface.dMenus.MenuStates#afterInitialAssignment()
	 */
	public void afterInitialAssignment() {
		_newTTable.setEnabled(false);
		_newTTableCycle.setEnabled(false);
		_newTTableExam.setEnabled(false);
		_newTTStruc.setEnabled(false);
		_newTTStrucCycle.setEnabled(false);
		_newTTStrucExam.setEnabled(false);
		_openTTable.setEnabled(false);
		_openTTStruc.setEnabled(false);
		_close.setEnabled(true);
		_save.setEnabled(true);
		_saveAs.setEnabled(true);
		_defineFiles.setEnabled(true);
		_import.setEnabled(false);
		_export.setEnabled(true);
		_exit.setEnabled(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInterface.dMenus.MenuStates#showAllMenus()
	 */
	public void showAllMenus() {
		_newTTable.setEnabled(true);
		_newTTableCycle.setEnabled(true);
		_newTTableExam.setEnabled(true);
		_newTTStruc.setEnabled(true);
		_newTTStrucCycle.setEnabled(true);
		_newTTStrucExam.setEnabled(true);
		_openTTable.setEnabled(true);
		_openTTStruc.setEnabled(true);
		_close.setEnabled(true);
		_save.setEnabled(true);
		_saveAs.setEnabled(true);
		_defineFiles.setEnabled(true);
		_import.setEnabled(true);
		_export.setEnabled(true);
		_exit.setEnabled(true);
	}

} // end DFileMenu
