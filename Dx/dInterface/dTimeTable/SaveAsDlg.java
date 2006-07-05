package dInterface.dTimeTable;

/**
 *
 * Title: SaveAsDlg $Revision: 1.25 $  $Date: 2006-07-05 19:58:22 $
 * Description: SaveAsDlg(DApplication dApplic) can be created by SaveAsCmd, SaveCmd
 *              SaveAsDlg(DApplication dApplic, String data) can be created
 *                        by ConflicReport, FullReport,
 *                        ImportReport.
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
 * @version $Revision: 1.25 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import dConstants.DConst;
import dInterface.DApplication;
import eLib.exit.dialog.InformationDlg;

/**
 * 
 * SaveAsDlg is a class used to show a dialog
 * 
 */

public abstract class SaveAsDlg {

	protected DApplication _dApplic;

	/**
	 * 
	 * @param DApplication
	 *            dApplic gives acces to the dialog's parent
	 * @since JDK1.3
	 */
	public SaveAsDlg(DApplication dApplic) {
		_dApplic = dApplic;
	}

	/**
	 * Constructor
	 * 
	 * @param dApplic
	 *            The application
	 * @param data
	 *            contains a String the string will be saved as in reports
	 */
	public SaveAsDlg(DApplication dApplic, String data) {
		data += "";
		_dApplic = dApplic;
	} // end constructor

	public void saveAs(String data, boolean report) {
		String dotExt = "";

		JFileChooser fc = new JFileChooser(_dApplic.getCurrentDir());
		fc.setMultiSelectionEnabled(false);
		dotExt = setExtension(fc);

		int returnVal = fc.showSaveDialog(_dApplic.getJFrame());
		// If the file chooser exited by Ok, do Save
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// Save the file name
			String currentFile = fc.getSelectedFile().getAbsolutePath();
			// System.out.println("currentFile " + currentFile);
			if (!currentFile.endsWith(dotExt))
				currentFile = currentFile.concat(dotExt);
			// If there is a file with this file name in the same path
			File file = new File(currentFile);
			if (file.exists()) {
				doFileExist(currentFile, data, report);
			} else { // if (file.exists())
				inNewFile(currentFile, data);
				new InformationDlg(_dApplic.getJFrame(), DConst.DEF_F_D7
						+ currentFile);
			}
		}// end if(returnVal == JFileChooser.APPROVE_OPTION)
	}// end saveAs() method

	private void doFileExist(String currentFile, String data, boolean report) {
		String fileName = currentFile.substring(currentFile
				.lastIndexOf(File.separator) + 1);
		String message = fileName + " " + DConst.EXISTS + DConst.CR_LF
				+ DConst.REPLACE;
		int resp = JOptionPane.showConfirmDialog(_dApplic.getJFrame(), message,
				DConst.SAVE_AS, JOptionPane.YES_NO_OPTION);
		if (resp == JOptionPane.OK_OPTION) {
			inNewFile(currentFile, data);
			new InformationDlg(_dApplic.getJFrame(), DConst.DEF_F_D7
					+ currentFile);
		}
		if (resp == JOptionPane.NO_OPTION) {
			saveAs(data, report);
		}
		if (resp == JOptionPane.CANCEL_OPTION) {
			//
		}

	} // doFileExist

	protected abstract String inNewFile(String currentFile, String data);

	protected abstract String setExtension(JFileChooser fc);
}// end class
