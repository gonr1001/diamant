package dInterface.dTimeTable;

/**
 *
 * Title: OpenTTDlg $Revision: 1.41 $  $Date: 2007-11-17 16:33:23 $
 * Description: OpenTTDlg is created by OpenTTDCmd
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
 * @version $Revision: 1.41 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */

import java.awt.Dimension;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.DlgIdentification;
import dResources.DFileFilter;
import eLib.exit.dialog.DxExceptionDlg;
import eLib.exit.exception.DxException;

/**
 * 
 * OpenTTDlg is a class used to show a dialog
 * 
 */

public class OpenTTDlg extends JDialog  implements //ActionListener,
		DlgIdentification {
	/**
	 * the constructor will displays the dialog
	 * 
	 * @param jframe
	 *            the parent of the dialog
	 * @param str
	 *            the title of the window dialog
	 * @since JDK1.3
	 */

	public OpenTTDlg(DApplication dApplic) {
		showDialog(dApplic);
	} // end constructor

	/**
	 * */
	/**
	 * 
	 */
	private void showDialog(DApplication dApplic) {
		JFileChooser fc = new JFileChooser(dApplic.getCurrentDir());

		fc.setFileFilter(new DFileFilter(new String[] { DConst.DIA },
				DConst.DIA_FILE));
		// Display the file chooser in a dialog
		Dimension d = fc.getPreferredSize();
		fc.setPreferredSize(new Dimension((int) d.getWidth() + 100, (int) d
				.getHeight()));
		// int returnVal = DxTools.showOpenDialog(dApplic.getJFrame(), fc);

		int returnVal = 0;
		String filename = "nothing.txt";
		while (!(new File(filename)).exists()
				&& (returnVal == JFileChooser.APPROVE_OPTION)) {
			returnVal = fc.showOpenDialog(dApplic.getJFrame());
			if (fc.getSelectedFile() != null)
				filename = fc.getSelectedFile().getAbsolutePath();

		}
		// If the file chooser exited sucessfully,
		// and a file was selected, continue
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// get the file name
			String fil = fc.getSelectedFile().getAbsolutePath();
			dApplic.setCurrentDir(fil);

			try {
				dApplic.getDMediator().addDxTTableDoc(fil, fil);
			} catch (DxException e) {
				new DxExceptionDlg(dApplic.getJFrame(), e.getMessage(), e);
				System.exit(1);
			}
			dApplic.setCurrentDir(fc.getSelectedFile().getPath());
			dApplic.getCurrentDxDoc().changeInModel(this.idDlgToString());
			dApplic.afterInitialAssign();
			dispose();
		}
		if (returnVal == JFileChooser.CANCEL_OPTION) {
			dApplic.initialState();
			dispose();
		}
	}// end OpenTTDlg

	public String idDlgToString() {
		return this.getClass().toString();
	}

//	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub	
//	}
} /* end class OpenTTDlg */