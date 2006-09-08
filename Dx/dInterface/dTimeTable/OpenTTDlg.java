package dInterface.dTimeTable;

/**
 *
 * Title: OpenTTDlg $Revision: 1.35 $  $Date: 2006-09-08 18:23:57 $
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
 * @version $Revision: 1.35 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import dConstants.DConst;
import dDeveloper.DxFlags;
import dInterface.DApplication;
import dInterface.DlgIdentification;
import dResources.DFileFilter;
import eLib.exit.exception.DxExceptionDlg;


/**
 * 
 * OpenTTDlg is a class used to show a dialog
 * 
 */

public class OpenTTDlg extends JDialog implements ActionListener, DlgIdentification {
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

			/* !!!NIC!!!String error; */

			if (DxFlags.newDoc) {
				try {
					dApplic.getDMediator().addDxTTableDoc(fil, fil);
				} catch (Exception e) {
                    new DxExceptionDlg(dApplic.getJFrame(),e.toString(),e);
					e.printStackTrace();
                    System.exit(1);
				}
			} else {
				try {
					dApplic.getDMediator().addDoc(fil, fil, DConst.NO_TYPE);
				} catch (Exception e) {
					  new DxExceptionDlg(dApplic.getJFrame(),e.toString(),e);
						e.printStackTrace();
	                    System.exit(1);
				}
			}

			// /*!!!NIC!!!*/ if (error.length() != 0) {
			// /*!!!NIC!!!*/ new FatalProblemDlg(dApplic.getJFrame(), error);
			// /*!!!NIC!!!*/ System.exit(1);
			// /*!!!NIC!!!*/ }
			dApplic.setCurrentDir(fc.getSelectedFile().getPath());
			if (DxFlags.newDoc) {
				dApplic.getCurrentDxDoc().changeInModel(this.idDlgToString());
			} else {
				dApplic.getCurrentDModel().changeInDModel(dApplic.getJFrame());
			}


			dispose();

		}
	}// end OpenTTDlg
	public String idDlgToString() {
		return this.getClass().toString();		
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub	
	}
} /* end class OpenTTDlg */