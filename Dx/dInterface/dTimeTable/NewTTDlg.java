package dInterface.dTimeTable;

/**
 *
 * Title: NewTTDlg $Revision: 1.6 $  $Date: 2003-06-02 15:05:36 $
 * Description: NewTTDlg is created by NewTTDCmd
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
 * @version $Revision: 1.6 $
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
  /**
    * the constructor will displays the dialog
    *
    * @param jframe    the parent of the dialog
    * @param str       the title of the window dialog
    * @since           JDK1.3
    */

   public NewTTDlg(DApplication dApplic, int type) {
     loadTTData(dApplic, type);
   } // end constructor

   /**
    * */
   /**
    *
    * */
   private void loadTTData(DApplication dApplic, int type) {
     JFileChooser fc = new JFileChooser(dApplic.getCurrentDir());
     String str1= "";
     String  str2 = "";
     String  str3 = "" ;
     if (type == DConst.CYCLE) {
       str1 = DConst.DGH;
       str2 = DConst.DGH_FILE;
       str3 = DConst.NTT_CY_TD;
     }
     if (type == DConst.EXAM) {
       str1 = DConst.DGH;
       str2 = DConst.DGH_FILE;
       str3 = DConst.NTT_EX_TD;
     }

     fc.setFileFilter( new DFileFilter (new String[] {str1},
         str2) );
     // Display the file chooser in a dialog
     Dimension d = fc.getPreferredSize();
     fc.setPreferredSize(new Dimension((int)d.getWidth()+ 100, (int)d.getHeight()));
     int returnVal = fc.showDialog(dApplic.getJFrame(), str3);

     // If the file chooser exited sucessfully,
     // and a file was selected, continue
     if (returnVal == JFileChooser.APPROVE_OPTION) {
       // get the file name
       String fil = fc.getSelectedFile().getAbsolutePath();
       dApplic.setCurrentDir(fil);
       //load grille,
       TTStructure ttStruct = new TTStructure();
       String error = ttStruct.rloadData(fil);

       dApplic.getDMediator().addDoc(dApplic.getCurrentDir() + DConst.NO_NAME, ttStruct);

       if(error.length()!=0){
         new FatalProblemDlg(dApplic.getJFrame(),error);
              System.exit(1);
       }
       dApplic.setCurrentDir(fc.getSelectedFile().getPath());
       dispose();

     }
   }// end loadTTData

} /* end class NewTTDlg */