package dInterface.dTimeTable;

/**
 *
 * Title: OpenTTDlg $Revision: 1.14 $  $Date: 2004-04-02 13:28:16 $
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
 * @version $Revision: 1.14 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */


import javax.swing.JDialog;

import javax.swing.JFileChooser;

import dInterface.DApplication;
import dResources.DFileFilter;
import com.iLib.gDialog.FatalProblemDlg;
import dInterface.dUtil.DXTools;

import java.awt.Dimension;

import dResources.DConst;




/**
 *
 * ImportDlg is a class used to show a dialog
 *
 */

public class OpenTTDlg extends JDialog {
  /**
    * the constructor will displays the dialog
    *
    * @param jframe    the parent of the dialog
    * @param str       the title of the window dialog
    * @since           JDK1.3
    */

   public OpenTTDlg(DApplication dApplic) {
     buildDocument(dApplic);
   } // end constructor

   /**
    * */
   /**
    *
    * */
   private void buildDocument(DApplication dApplic) {
     JFileChooser fc = new JFileChooser(dApplic.getCurrentDir());

     fc.setFileFilter( new DFileFilter (new String[] {DConst.DIA},
         DConst.DIA_FILE) );
     // Display the file chooser in a dialog
     Dimension d = fc.getPreferredSize();
     fc.setPreferredSize(new Dimension((int)d.getWidth()+ 100, (int)d.getHeight()));
     int returnVal =DXTools.showOpenDialog(dApplic.getJFrame(),fc);

     // If the file chooser exited sucessfully,
     // and a file was selected, continue
     if (returnVal == JFileChooser.APPROVE_OPTION) {
       // get the file name
       String fil = fc.getSelectedFile().getAbsolutePath();
       dApplic.setCurrentDir(fil);

       String error = dApplic.getDMediator().addDoc(fil,fil,DConst.NO_TYPE);

       if(error.length()!=0){
         new FatalProblemDlg(dApplic.getJFrame(),error);
              System.exit(1);
       }
       dApplic.setCurrentDir(fc.getSelectedFile().getPath());
       dApplic.getDMediator().getCurrentDoc().getDM().sendEvent(dApplic.getJFrame());

       dispose();
       //dApplic.getMenuBar().postOpenTTCmd();
        dApplic.getMenuBar().postInitialAssign();
     }
   }// end loadTTData

} /* end class NewTTDlg */