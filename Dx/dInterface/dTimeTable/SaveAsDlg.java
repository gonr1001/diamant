package dInterface.dTimeTable;
/**
 *
 * Title: SaveAsDlg $Revision: 1.14 $  $Date: 2003-10-28 14:19:06 $
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
 * @version $Revision: 1.14 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */


import java.io.File;
import java.io.FileWriter;


import javax.swing.JDialog;





import javax.swing.JFileChooser;

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
     //saveAs();
     saveAs(null);
   } // end constructor

   /**
    * Constructor
    * @param dApplic The application
    * @param fileType -1 if the file type is "dia" or "xml". 0 if "txt"
    */
   public SaveAsDlg(DApplication dApplic, String data) {
     _dApplic = dApplic;
     saveAs(data);
   } // end constructor


/*
   public void saveAs() {
     File file;
     String DOTEXT="";
     String error  = "";
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
         error = _dApplic.getDMediator().saveCurrentDoc(currentFile);
         if (error.length() == 0)
           new InformationDlg(_dApplic.getJFrame(), DConst.DEF_F_D7 + currentFile);
         else
           new FatalProblemDlg(error);
       }
     }// end if(returnVal == JFileChooser.APPROVE_OPTION)
   }//end saveAs() method

   */

   public void saveAs(String data) {
     File file;
     String DOTEXT="";
     String error  = "";
     JFileChooser fc = new JFileChooser(_dApplic.getCurrentDir());
     if (data == null){
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
     }else{//end if (data == null)
       fc.setFileFilter( new DFileFilter ( new String[] {DConst.TXT},
             DConst.TXT_FILE ) );
         DOTEXT= DConst.DOT_TXT;
     }

  fc.setMultiSelectionEnabled( false );

  // Display the file chooser in a dialog
  int returnVal = fc.showSaveDialog(_dApplic.getJFrame());

  // If the file chooser exited sucessfully,
  // and a file was selected, continue
  if (returnVal == JFileChooser.APPROVE_OPTION) {

    // Save the file name
    String currentFile = fc.getSelectedFile().getAbsolutePath();
    if ( !currentFile.endsWith(DOTEXT) )
      currentFile = currentFile.concat(DOTEXT);
    // If there is a file with this file name in the same path
    file = new File(currentFile);
    if (file.exists()){
      String fileName = currentFile.substring(currentFile.lastIndexOf(File.separator)+1);
      int resp= ConfirmDlg.showMessage(_dApplic, "Remplacer le fichier " + fileName + " existante?");
      if( resp== ConfirmDlg.OK_OPTION){
        //if it's a "dia" or "xml" file
        if(data == null){
          _dApplic.getDMediator().saveCurrentDoc(currentFile);
          new InformationDlg(_dApplic.getJFrame(), DConst.DEF_F_D7 + currentFile);
        }
        //if it'a a txt file
        if (data != null){
          saveWithData(currentFile, data);
        }//end if (fileType == 0)
      }
      if(resp == ConfirmDlg.NO_OPTION){
        saveAs(data);
      }
      if(resp == ConfirmDlg.CANCEL_OPTION){

      }
    }else{
      if(data == null){
        error = _dApplic.getDMediator().saveCurrentDoc(currentFile);
      }
      //if it's a txt file
      if (data != null){
        error = saveWithData(currentFile, data);
        if (error.length() == 0)
          new InformationDlg(_dApplic.getJFrame(), DConst.DEF_F_D7 + currentFile);
        else
          new FatalProblemDlg(error);
      }//end if (data != null)
    }//end else if(file.exists())
  }// end if(returnVal == JFileChooser.APPROVE_OPTION)
   }//end saveAs() method

   /**
    * Saves data in a file by using a FileWriter
    * @param currentFile the file pathname
    * @param data the data to be stored
    * @return
    */
   private String saveWithData(String currentFile, String data){
     String error = "";
     try{
          FileWriter fw = new FileWriter(currentFile);
          fw.write(data);
          fw.close();
          return error;
        }catch(Exception e){
          error = "Problem with the file";
          new FatalProblemDlg(error);
          return error;
        }
   }


}//end class