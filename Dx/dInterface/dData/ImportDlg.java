package dInterface.dData;
/**
 *
 * Title: ImportDlg $Revision: 1.19 $  $Date: 2005-01-21 21:56:52 $
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
 * @version $Revision: 1.19 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import dConstants.DConst;
import dInterface.DApplication;
import dResources.DFileFilter;
import eLib.exit.dialog.FatalProblemDlg;
import eLib.exit.dialog.InformationDlg;



/**
 *
 * ImportDlg is a class used to show a dialog
 *
 */

public class ImportDlg extends JDialog {

  //DApplication _dApplic;
  /**
    * the constructor will displays the dialog
    *
    * @param DApplication     to get the parent of the dialog
    * @since           JDK1.3
    */

   public ImportDlg(DApplication dApplic) {
     //_dApplic= dApplic;
     loadData(dApplic);
   } // end constructor

   /**
    *
    * */
   private void loadData(DApplication dApplic){
     JFileChooser fc = new JFileChooser(dApplic.getCurrentDir());
     fc.setFileFilter(new DFileFilter ( new String[] {DConst.DIM},
         DConst.DIM_FILE ) );
     // Display the file chooser in a dialog
     Dimension d = fc.getPreferredSize();
     fc.setPreferredSize(new Dimension((int)d.getWidth()+100, (int)d.getHeight()));
     int returnVal = fc.showDialog(dApplic.getJFrame(), DConst.IMP_A_TD);

     // If the file chooser exited sucessfully,
     // and a file was selected, continue
     if (returnVal == JFileChooser.APPROVE_OPTION) {
       // get the file name
       String fil = fc.getSelectedFile().getAbsolutePath();
       dApplic.setCurrentDir(fil);

       String error = "";
       if (dApplic.getDMediator().getCurrentDoc() != null)
         error= dApplic.getDModel().importData(fil);
       else
         error = "ImportDlg : Il n'existe pas de document pour effectuer l'importation des donn�es";
       if(error.length()==0){
         new InformationDlg(dApplic.getJFrame(), DConst.IMP_A_SUC);
       }else{
         new FatalProblemDlg(dApplic.getJFrame(),error);
              System.exit(1);
       }
       dApplic.setCurrentDir(fc.getSelectedFile().getPath());
       dispose();
       dApplic.getMenuBar().postImportCmd();
     }
   }// end method

} /* end class ImportDlg */


