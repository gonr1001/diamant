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
public class SaveAsTTDlg extends SaveAsDlg {

  /**
   * Constructor
   * @param dApplic The application
   *
   */
  public SaveAsTTDlg(DApplication dApplic) {
    super(dApplic);
    saveAs(null,false); //no data, no report
  } // end constructor*/

  public SaveAsTTDlg(DApplication dApplic, String str) {
    super(dApplic);
    saveAs(null,false); //no data, no report
  } // end constructor*/

  public String inNewFile(String currentFile, String data) {
    return _dApplic.getDMediator().saveCurrentDoc(currentFile);
  }

   public  void doSave(String fileName, String str) {
     String error = "";
     error = _dApplic.getDMediator().saveCurrentDoc(fileName);
     if (error.length() == 0)
       new InformationDlg(_dApplic.getJFrame(), DConst.DEF_F_D7 + fileName);
     else
       new FatalProblemDlg(error);
   } // doSave

   public String setExtension(JFileChooser fc){
     if(_dApplic.getDMediator().getCurrentDoc().getDM().isTimeTable()){
       fc.setFileFilter(new DFileFilter (new String[] {DConst.DIA},
           DConst.DIA_FILE) );
       return DConst.DOT_DIA;
     } else {
       fc.setFileFilter( new DFileFilter ( new String[] {DConst.XML},
           DConst.XML_FILE ) );
       return DConst.DOT_XML;
     }
  } // setFilters
}