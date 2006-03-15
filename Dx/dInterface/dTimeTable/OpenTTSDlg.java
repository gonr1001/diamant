package dInterface.dTimeTable;

/**
*
* Title: OpenTTSDlg $Revision: 1.16 $  $Date: 2006-03-15 14:25:35 $
* Description: OpenTTSDlg is a class used to
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
* @version $Revision: 1.16 $
* @author  $Author: gonzrubi $
* @since JDK1.3
*/

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.dUtil.DXTools;
import dResources.DFileFilter;
import eLib.exit.dialog.FatalProblemDlg;


  public class OpenTTSDlg extends JDialog{

    /**
     * the constructor will displays the dialog
     *
     * @param jframe    the parent of the dialog
     * @param str       the title of the window dialog
     * @since           JDK1.3
     */

    public OpenTTSDlg(DApplication dApplic) {
   //dApplic= dApplic;
   buildDocument(dApplic);
 } // end constructor

 /**
     *
     * */
    private void buildDocument(DApplication dApplic) {
      JFileChooser fc = new JFileChooser(dApplic.getCurrentDir());
      String str1 = DConst.XML;
      String str2 = DConst.XML_FILE;
      String  str3 = DConst.NEW_TT_M ;

      fc.setFileFilter(new DFileFilter(new String[] {str1}, str2));
      // Display the file chooser in a dialog
      Dimension d = fc.getPreferredSize();
      fc.setPreferredSize(new Dimension((int)d.getWidth()+ 100, (int)d.getHeight()));
      //int returnVal = fc.showDialog(dApplic.getJFrame(), str3);
      int returnVal = DXTools.showDialog(dApplic.getJFrame(), fc, str3);
      // If the file chooser exited sucessfully,
      // and a file was selected, continue
       if (returnVal == JFileChooser.CANCEL_OPTION) {
         dApplic.getDMediator().closeCurrentDoc();
         dApplic.hideToolBar();
       }
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        // get the file name
        String fil = fc.getSelectedFile().getAbsolutePath();
        dApplic.setCurrentDir(fil);
        String error = dApplic.getDMediator().addDoc(fil, 0);
        if(error.length()!=0){
          new FatalProblemDlg(dApplic.getJFrame(),error);
          System.exit(1);
        }
        dispose();
		if (dApplic.isInDevelopment()) {
			dApplic.afterOpenTTSruc();
		} else {
//			dApplic.getMenuBar().postOpenTTSCmd();
		}

      }
   }// end loadTTData

  } /* end class OpenTTSDlg */



