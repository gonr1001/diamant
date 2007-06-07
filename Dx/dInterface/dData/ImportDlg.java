package dInterface.dData;

/**
 *
 * Title: ImportDlg $Revision: 1.36 $  $Date: 2007-06-07 18:00:53 $
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
 * @version $Revision: 1.36 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.DlgIdentification;
import dResources.DFileFilter;

/**
 * 
 * ImportDlg is a class used to show a dialog
 * 
 */

public class ImportDlg extends JDialog implements DlgIdentification {

	// DApplication _dApplic;
	/**
	 * the constructor will displays the dialog
	 * 
	 * @param DApplication
	 *            to get the parent of the dialog
	 * @since JDK1.3
	 */

	public ImportDlg(DApplication dApplic) {
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
			String fil = fc.getSelectedFile().getAbsolutePath();
			dApplic.doImport(fc, fil, this.idDlgToString());
			dispose();
		}
	} // end constructor

	// /**
	// *
	// */
	// private void buildDialog(DApplication dApplic) {
	// JFileChooser fc = new JFileChooser(dApplic.getCurrentDir());
	// fc.setFileFilter(new DFileFilter(new String[] { DConst.DIM },
	// DConst.DIM_FILE));
	// // Display the file chooser in a dialog
	// Dimension d = fc.getPreferredSize();
	// fc.setPreferredSize(new Dimension((int) d.getWidth() + 100, (int) d
	// .getHeight()));
	// int returnVal = fc.showDialog(dApplic.getJFrame(), DConst.IMP_A_TD);
	//
	// // If the file chooser exited sucessfully,
	// // and a file was selected, continue
	// if (returnVal == JFileChooser.APPROVE_OPTION) {
	// // get the file name
	// String fil = fc.getSelectedFile().getAbsolutePath();
	// dApplic.doImport(fc,fil);
	// dispose();
	// }
	// }// end loadData

	public String idDlgToString() {
		return this.getClass().toString();
	}

} /* end class ImportDlg */
