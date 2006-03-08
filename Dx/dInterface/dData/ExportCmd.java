/**
 *
 * Title: ExportCmd $Revision: 1.15 $  $Date: 2006-03-08 16:50:31 $
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
 * @version $Revision: 1.15 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */

package dInterface.dData;


import java.io.File;
import java.util.StringTokenizer;

//import javax.swing.JFrame;

import dConstants.DConst;
import dInterface.Command;
import dInterface.DApplication;
import eLib.exit.dialog.InformationDlg;
/**
   *
   * ExportCmd is a class used to call a command
   *              export.
   *
   */

  public class ExportCmd implements Command {
    
//------------------------------
    public void execute(DApplication dApplic) {

      String dir = getTokenDir(dApplic.getCurrentDoc().getDocumentName(),File.separator);

      File fileStu = new File(dir + DConst.TT_STUD_FILE);
      File fileTT = new File(dir + DConst.TT_FILE);
      String mess = "";
       if (fileStu.exists() || fileTT.exists()) {
         mess += "Un ou les deux fichiers existent dans le répertoire" + DConst.CR_LF;
                mess += "PAS d'exportation";
         new InformationDlg(dApplic.getJFrame(),mess , DConst.EXPORT_MESSAGE);
       } else{ //if (fileStu.exists() || fileTT.exists())
         dApplic.getCurrentDModel().exportData(dir);
         mess += dir + DConst.TT_STUD_FILE  + DConst.CR_LF + dir + DConst.TT_FILE + DConst.CR_LF + DConst.EXPORTED;
         new InformationDlg(dApplic.getJFrame(), mess, DConst.EXPORT_MESSAGE);
       }


    }

    /**
   * return a token in from a stringtokenizer
   * @param str
   * @param delimiter
   * @param position
   * @return
   */
  private String getTokenDir(String str, String delimiter){
    StringTokenizer strToken= new StringTokenizer(str,delimiter);
    String string = "";
    int nbTokens= strToken.countTokens();
    for (int i=0; i< nbTokens-1; i++){
      string+= strToken.nextToken() + delimiter;
    }
    return string;
   }
} /* end class ImportCmd */