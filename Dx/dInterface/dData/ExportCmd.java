/**
 *
 * Title: ExportCmd $Revision: 1.5 $  $Date: 2004-04-16 17:31:45 $
 * Description: ExportCmd is class used as the command
 *              which executes the exportation
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
 */

package dInterface.dData;


import dInterface.Command;

import javax.swing.JFrame;
import com.iLib.gDialog.InformationDlg;

import dResources.DConst;
import dInterface.DApplication;
import java.io.File;
import java.util.StringTokenizer;
import dInternal.dUtil.DXToolsMethods;
/**
   *
   * ExportCmd is a class used to call a command
   *              export.
   *
   */

  public class ExportCmd implements Command {
    private final String CR_LF = "\r\n";
    public ExportCmd (JFrame jFrame) {
    } // end constructor
//------------------------------
    public void execute(DApplication dApplic) {

      String dir = getTokenDir(dApplic.getDMediator().getCurrentDoc().getDocumentName(),File.separator);
      dApplic.getDMediator().getCurrentDoc().getDM().exportData(dir);
      File fileStu = new File(dir +DConst.TT_STUD_FILE);
      File fileTT = new File(dir +DConst.TT_FILE);
      /*
       if (file.exists()){
         doFileExist(currentFile, data, report);
       } else{ //if (file.exists())
         inNewFile(currentFile, data);
         new InformationDlg(_dApplic.getJFrame(), DConst.DEF_F_D7 + currentFile);
       }
      */
      String mess = dir + DConst.TT_STUD_FILE  + CR_LF + dir + DConst.TT_FILE + CR_LF + DConst.EXPORTED;
      new InformationDlg(dApplic.getJFrame(), mess, DConst.EXPORT_MESSAGE);
    }

    /**
   * return a token in from a stringtokenizer
   * @param str
   * @param delimiter
   * @param position
   * @return
   */
  private  String getTokenDir(String str, String delimiter){
    StringTokenizer strToken= new StringTokenizer(str,delimiter);
    String string = "";
    int nbTokens= strToken.countTokens();
    for (int i=0; i< nbTokens-1; i++){
      string+= strToken.nextToken() + delimiter;
    }
    return string;
   }
} /* end class ImportCmd */