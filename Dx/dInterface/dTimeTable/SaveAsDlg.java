package dInterface.dTimeTable;
/**
 *
 * Title: SaveAsDlg $Revision: 1.10 $  $Date: 2003-07-11 10:47:49 $
 * Description: SaveAsDlg is created by DefFileToImportCmd
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
 * @version $Revision: 1.10 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.iLib.gIO.ByteOutputFile;
import com.iLib.gException.IOFileException;
import com.iLib.gDialog.FatalProblemDlg;
import com.iLib.gDialog.InformationDlg;
import dInterface.DApplication;

import dAux.ConfirmDlg;

import dResources.DConst;
import dResources.DFileFilter;




/**
 *
 * SaveAsDlg is a class used to show a dialog
 *
 */

public class SaveAsDlg extends JDialog
                                 {

  private final String CR_LF = "\r\n";

  /**
    * the constructor will displays the dialog
    *
    * @param DApplication dApplic gives the parent of the dialog
    * @param str       the title of the window dialog
    * @since           JDK1.3
    */
   DApplication _dApplic;

   public SaveAsDlg(DApplication dApplic) {
     //super(dApplic.getJFrame());
     _dApplic = dApplic;
     saveAs();
   } // end constructor




   public void saveAs() {
     File file;
     String DOTEXT="";
     //C rwfDlg = null;

     JFileChooser fc = new JFileChooser(_dApplic.getCurrentDir());
     if(_dApplic.getDMediator().getCurrentDoc().getDM().isTimeTable()){
       fc.setFileFilter( new DFileFilter ( new String[] {DConst.DIA},
           DConst.DIA_FILE ) );
       DOTEXT= DConst.DOT_DIA;
     }
     else{
       fc.setFileFilter( new DFileFilter ( new String[] {DConst.XML},
           DConst.XML_FILE ) );
       DOTEXT= DConst.DOT_XML;
     }

     fc.setMultiSelectionEnabled( false );

     // Display the file chooser in a dialog
     int returnVal = fc.showSaveDialog(_dApplic.getJFrame());

     // If the file chooser exited sucessfully,
     // and a file was selected, continue
     if (returnVal == JFileChooser.APPROVE_OPTION) {

       // Save the file name
       String currentFile = fc.getSelectedFile().getAbsolutePath();
       System.out.println("currentFile " + currentFile);
       if ( !currentFile.endsWith(DOTEXT) )
         currentFile = currentFile.concat(DOTEXT);


       // If there is a file with this file name in the same path
       file = new File(currentFile);
       if (file.exists()){
         String fileName = currentFile.substring(currentFile.lastIndexOf(File.separator)+1);
         int resp= ConfirmDlg.showMessage(_dApplic, "Remplacer le fichier " + fileName + " existante?");
         if( resp== ConfirmDlg.OK_OPTION){
           _dApplic.getDMediator().saveCurrentDoc(currentFile);
           new InformationDlg(_dApplic.getJFrame(), DConst.DEF_F_D7 + currentFile);
         }
         if(resp == ConfirmDlg.NO_OPTION){
           saveAs();
         }
         if(resp == ConfirmDlg.CANCEL_OPTION){

         }
       }else{
         _dApplic.getDMediator().saveCurrentDoc(currentFile);
         new InformationDlg(_dApplic.getJFrame(), DConst.DEF_F_D7 + currentFile);
       }
     }// end if(returnVal == JFileChooser.APPROVE_OPTION)
   }//end saveAs() method
}//end class