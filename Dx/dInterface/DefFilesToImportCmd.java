package dInterface;
/**
 *
 * Title: DefFileToImportCmd $Revision: 1.1 $  $Date: 2003-05-12 15:33:28 $
 * Description: DefFileToImportCmd is a class used by menus
 *              to define the files to import
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
 * @version $Revision: 1.1 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */

import dInterface.Command;

import javax.swing.JFrame;
import java.util.ResourceBundle;

import dAux.DoNothingDlg;
import dInterface.DApplication;

import dResources.DConst;
/**
 *
 * ImportCmd is a class used to call a command
 *              import.
 *
 */

public class DefFilesToImportCmd implements Command {

//  private final static String TITLEBOX = "Do Nothing Cmd";

  public DefFilesToImportCmd() {
  } // end constructor
//------------------------------
  public void execute(DApplication dApplic) {
    DefFilesToImportDlg dfti = new DefFilesToImportDlg(dApplic,DConst.DEF_F_TD);
    String str = dfti.showDlg();
    saveInFile(str);
  }
  private void saveInFile(String str) {

  }
} /* end class DefFileToImportCmd */

