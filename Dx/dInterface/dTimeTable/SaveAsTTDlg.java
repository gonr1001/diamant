
/**
 *
 * Title: SaveAsTTDlg $Revision: 1.5 $  $Date: 2004-09-10 13:31:02 $
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
 * Description: SaveAsTTDlg is a class used to
 *
 */
package dInterface.dTimeTable;


import javax.swing.JFileChooser;

import dConstants.DConst;
import dInterface.DApplication;
import dResources.DFileFilter;
import eLib.exit.dialog.FatalProblemDlg;
import eLib.exit.dialog.InformationDlg;
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