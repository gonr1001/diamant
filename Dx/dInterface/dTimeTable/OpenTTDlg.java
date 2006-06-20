package dInterface.dTimeTable;

/**
 *
 * Title: OpenTTDlg $Revision: 1.30 $  $Date: 2006-06-20 20:41:24 $
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
 * @version $Revision: 1.30 $
 * @author  $Author: nuna2502 $
 * @since JDK1.3
 */

import java.awt.Dimension;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import dConstants.DConst;
import dInterface.DApplication;
import dResources.DFileFilter;
import eLib.exit.dialog.FatalProblemDlg;

/**
 * 
 * OpenTTDlg is a class used to show a dialog
 * 
 */

public class OpenTTDlg extends JDialog {
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
            try {
                /* !!!NIC!!!error = */dApplic.getDMediator().addDoc(fil, fil,
                        DConst.NO_TYPE);
            } catch (Exception e) {

            	e.printStackTrace();
            	
                new FatalProblemDlg(dApplic.getJFrame(), "In OpenTTDlg.showDialog: " + e.toString());
                System.exit(1);
            }

            // /*!!!NIC!!!*/ if (error.length() != 0) {
            // /*!!!NIC!!!*/ new FatalProblemDlg(dApplic.getJFrame(), error);
            // /*!!!NIC!!!*/ System.exit(1);
            // /*!!!NIC!!!*/ }
            dApplic.setCurrentDir(fc.getSelectedFile().getPath());
            dApplic.getCurrentDModel().changeInDModel(dApplic.getJFrame());

            dispose();
            dApplic.afterInitialAssign();
        }
    }// end OpenTTDlg

} /* end class OpenTTDlg */