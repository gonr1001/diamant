package dInterface.dTimeTable;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import java.io.File;
import java.io.FileWriter;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JInternalFrame;

import com.iLib.gDialog.FatalProblemDlg;

import com.iLib.gDialog.InformationDlg;

import dInterface.DApplication;

//import dAux.ConfirmDlg;

import dResources.DConst;
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