package dInterface.dData;

/**
 *
 * Title: ImportDlg $Revision: 1.35 $  $Date: 2007-01-05 20:14:24 $
 * Description: ImportDlg is created by DefFileToImportCmd
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
 * @version $Revision: 1.35 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */

import java.awt.Dimension;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import dConstants.DConst;
import dInterface.DApplication;
import dResources.DFileFilter;

/**
 * 
 * ImportDlg is a class used to show a dialog
 * 
 */

public class ImportDlg extends JDialog {

	// DApplication _dApplic;
	/**
	 * the constructor will displays the dialog
	 * 
	 * @param DApplication
	 *            to get the parent of the dialog
	 * @since JDK1.3
	 */

	public ImportDlg(DApplication dApplic) {
		loadData(dApplic);
	} // end constructor

	/**
	 * 
	 */
	private void loadData(DApplication dApplic) {
		JFileChooser fc = new JFileChooser(dApplic.getCurrentDir());
		fc.setFileFilter(new DFileFilter(new String[] { DConst.DIM },
				DConst.DIM_FILE));
		// Display the file chooser in a dialog
		Dimension d = fc.getPreferredSize();
		fc.setPreferredSize(new Dimension((int) d.getWidth() + 100, (int) d
				.getHeight()));
		int returnVal = fc.showDialog(dApplic.getJFrame(), DConst.IMP_A_TD);

		// If the file chooser exited sucessfully,
		// and a file was selected, continue
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// get the file name
//			if (DxFlags.newDoc) {
				String fil = fc.getSelectedFile().getAbsolutePath();
				dApplic.getCurrentDxDoc().setAutoImportDIMFilePath(
						fc.getSelectedFile().getAbsolutePath().substring(
								0,
								fc.getSelectedFile().getAbsolutePath()
										.lastIndexOf(File.separatorChar) + 1));
				dApplic.setCurrentDir(fil);
				dApplic.doImport(fil);
				dApplic.getCurrentDModel().setIsATimeTable();
				dApplic.getCurrentDModel()
						.changeInDModelByImportDlg(this);
				dApplic.setCurrentDir(fc.getSelectedFile().getPath());
				dispose();
				dApplic.afterImport();

//			} else {
//				String fil = fc.getSelectedFile().getAbsolutePath();
//				dApplic.getCurrentDoc().setAutoImportDIMFilePath(
//						fc.getSelectedFile().getAbsolutePath().substring(
//								0,
//								fc.getSelectedFile().getAbsolutePath()
//										.lastIndexOf(File.separatorChar) + 1));
//				dApplic.setCurrentDir(fil);
//
//				//dApplic.doImport(this, fil);
//				dApplic.doImport(fil);
//
//
//				// String error = "";
//				// dApplic.setCursorWait();
//				// if (dApplic.getCurrentDoc() != null) {
//				// error = dApplic.getCurrentDModel().importData(fil);
//				// } else
//				// error = "ImportDlg : Il n'existe pas de document pour
//				// effectuer l'importation des données";
//				// if (error.length() == 0) {
//				// new InformationDlg(dApplic.getJFrame(), DConst.IMP_A_SUC);
//				// } else {
//				// new FatalProblemDlg(dApplic.getJFrame(), error);
//				// System.exit(1);
//				// }
//				// dApplic.setCursorDefault();
//				dApplic.getCurrentDModel().changeInDModelByImportDlg(this);
//				dApplic.setCurrentDir(fc.getSelectedFile().getPath());
//				dispose();
//				dApplic.afterImport();
//			}

		}
	}// end method

} /* end class ImportDlg */
