/**
 *
 * Title: SaveAsTxtDlg $Revision: 1.5 $  $Date: 2004-06-21 15:38:17 $
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
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */


/**
 * Description: SaveAsTxtDlg is a class used to
 *
 */
package dInterface.dTimeTable;



import java.io.FileWriter;

import javax.swing.JFileChooser;

import com.iLib.gDialog.FatalProblemDlg;

import dConstants.DConst;
import dInterface.DApplication;
import dResources.DFileFilter;

public class SaveAsTxtDlg extends SaveAsDlg{

  public SaveAsTxtDlg(DApplication dApplic) {
    super(dApplic);
    saveAs(null,false); //no data, no report
  } // end constructor*/
  /**
  * Constructor
  * @param dApplic The application
  * @param data contains a String the string will be saved
  *           as in reports
  */
 public SaveAsTxtDlg(DApplication dApplic, String data) {
    super(dApplic);
   saveAs(data, true); //data, report
   } // end constructor


   public String inNewFile(String currentFile, String data) {
     String error = "";
     try{
       FileWriter fw = new FileWriter(currentFile);
       fw.write(data);
       fw.close();
       return error;
     } catch(Exception e){
       error = "Problem with the file";
       new FatalProblemDlg(error);
       return error;
     }
   }

   /**
    * Saves data in a file by using a FileWriter
    * @param currentFile the file pathname
    * @param data the data to be stored
    * @return
    */
   private String saveReport(String currentFile, String data){
     String error = "";
     try{
       FileWriter fw = new FileWriter(currentFile);
       fw.write(data);
       fw.close();
       return error;
     } catch(Exception e){
       error = "Problem with the file";
       new FatalProblemDlg(error);
       return error;
     }
   }// saveReport

   public String setExtension(JFileChooser fc){
     fc.setFileFilter( new DFileFilter ( new String[] {DConst.TXT},
         DConst.TXT_FILE ) );
     return DConst.DOT_TXT;

  } // setFilters
}