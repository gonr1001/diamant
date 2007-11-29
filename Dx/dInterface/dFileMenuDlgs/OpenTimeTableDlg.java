/**
 * Created on 29-Nov-07
 * 
 * 
 * Title: OpenTimeTableDlg.java
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

package dInterface.dFileMenuDlgs;

import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.DlgGetFileName;
import dResources.DFileFilter;

/**
 * 
 * OpenTTDlg is a class used to show a dialog
 * 
 */

public class OpenTimeTableDlg extends JDialog  implements DlgGetFileName {

	@Override
	public String getFileName(DApplication dApplic) {
		JFileChooser fc = new JFileChooser(dApplic.getCurrentDir());
		fc.setFileFilter(new DFileFilter(new String[] {DConst.DIA},
				DConst.DIA_FILE));

		int returnVal = 0;
		String fileName = "";
		while (!(new File(fileName)).exists()
				&& (returnVal == JFileChooser.APPROVE_OPTION)) {
			returnVal = fc.showOpenDialog(dApplic.getJFrame());
			if (fc.getSelectedFile() != null)
				fileName = fc.getSelectedFile().getAbsolutePath();
		}
		// If the file chooser has a file Name,
		// continue
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// return  the file name
			return  fileName;		
		}
		if (returnVal == JFileChooser.CANCEL_OPTION) {
			return "";			
		}
		return "";
	}

	
} /* end class OpenTimeTableDlg */