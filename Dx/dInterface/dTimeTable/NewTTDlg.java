package dInterface.dTimeTable;

/**
 *
 * Title: NewTTDlg $Revision: 1.20 $  $Date: 2003-10-22 19:28:36 $
 * Description: NewTTDlg is created by NewTTDCmd it is used when
 *              a new document (timetable) will be created,
 *              it is necessary to ask for
 *              a TTStructure in ordrer to start a the timetable.
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
 * @version $Revision: 1.20 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */


import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.util.ResourceBundle;
import dInterface.DApplication;
import dInterface.dUtil.DXTools;
import dResources.DFileFilter;
import com.iLib.gDialog.FatalProblemDlg;

import java.awt.Dimension;
import java.io.File;

import dResources.DConst;
import dInternal.dTimeTable.TTStructure;


/**
 *
 * NewTTDlg is a class used to show a dialog
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
     buildDocument(dApplic, type);
   } // end constructor

   /**
    *
    * */
   private void buildDocument(DApplication dApplic, int type) {
     JFileChooser fc = new JFileChooser(dApplic.getCurrentDir());
     String str1 = DConst.XML;
     String str2 = DConst.XML_FILE;
     String  str3 = "" ;
     if (type == DConst.CYCLE) {
       str3 = DConst.NTT_CY_TD;
     }
     if (type == DConst.EXAM) {
       str3 = DConst.NTT_EX_TD;
     }
     if (type == DConst.CYCLEANDEXAM) {
       str3 = DConst.NEW_TT_M;
     }

     fc.setFileFilter(new DFileFilter(new String[] {str1}, str2));
     // Display the file chooser in a dialog
     Dimension d = fc.getPreferredSize();
     fc.setPreferredSize(new Dimension((int)d.getWidth()+ 100, (int)d.getHeight()));
     //int returnVal = fc.showDialog(dApplic.getJFrame(), str3);
     int returnVal =DXTools.showDialog(dApplic.getJFrame(),fc,str3);

     // If the file chooser exited sucessfully,
     // and a file was selected, continue
     if ((returnVal == JFileChooser.APPROVE_OPTION)) {
       // get the file name
       String fil = fc.getSelectedFile().getAbsolutePath();
       dApplic.setCurrentDir(fil);

       String error=dApplic.getDMediator().addDoc(dApplic.getCurrentDir()+DConst.NO_NAME, fil, type);

       //    dApplic.getToolBar().setToolBars(ttStruct);
       //dApplic.setCurrentDir(fc.getSelectedFile().getPath());
       if(error.length()!=0){
         new FatalProblemDlg(dApplic.getJFrame(),error);
         System.exit(1);
       }
       dispose();
       dApplic.getMenuBar().postNewTTCyCmd();
     } // if ((returnVal == JFileChooser.APPROVE_OPTION)) {
   }// end loadTTData

} /* end class NewTTDlg */