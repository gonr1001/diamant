package dInterface.dTimeTable;

/**
 *
 * Title: ImportDlg $Revision: 1.5 $  $Date: 2003-05-23 15:34:10 $
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
 * @version $Revision: 1.5 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */


import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.util.ResourceBundle;
import dInterface.DApplication;
import dResources.DFileFilter;
import com.iLib.gDialog.FatalProblemDlg;

import java.awt.Dimension;

import dResources.DConst;

import dInternal.dTimeTable.TTStructure;


/**
 *
 * ImportDlg is a class used to show a dialog
 *
 */

public class NewTTDlg extends JDialog {
  //DApplication _dApplic;
  /**
    * the constructor will displays the dialog
    *
    * @param jframe    the parent of the dialog
    * @param str       the title of the window dialog
    * @since           JDK1.3
    */

   public NewTTDlg(DApplication dApplic) {
     loadTTData(dApplic);
   } // end constructor

   /**
    * */
   /**
    *
    * */
   private void loadTTData(DApplication dApplic){
     JFileChooser fc = new JFileChooser(dApplic.getCurrentDir());
     fc.setFileFilter( new DFileFilter (new String[] {DConst.DGH},
         DConst.DGH_FILE) );
     // Display the file chooser in a dialog
     Dimension d = fc.getPreferredSize();
     fc.setPreferredSize(new Dimension((int)d.getWidth()+ 100, (int)d.getHeight()));
     int returnVal = fc.showDialog(dApplic.getJFrame(), DConst.NEW_TT_M);

     // If the file chooser exited sucessfully,
     // and a file was selected, continue
     if (returnVal == JFileChooser.APPROVE_OPTION) {
       // get the file name
       String fil = fc.getSelectedFile().getAbsolutePath();
       dApplic.setCurrentDir(fil);
       //load grille,
       TTStructure ttStruct = new TTStructure();
       String error = "";// ttStruct.loadData(fil);

       dApplic.getDMediator().addDoc(dApplic.getCurrentDir() + DConst.NO_NAME, ttStruct);

       if(error.length()!=0){
         new FatalProblemDlg(dApplic.getJFrame(),error);
              System.exit(1);
       }
       dApplic.setCurrentDir(fc.getSelectedFile().getPath());
       dispose();

     }
   }// end method


} /* end class NewDlg */