package dInterface.dData;
/**
 *
 * Title: DefFileToImportCmd $Revision: 1.2 $  $Date: 2003-06-04 16:25:17 $
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
 * @version $Revision: 1.2 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */


import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JFileChooser;


import dAux.DoNothingDlg;
import dInterface.DApplication;

import dInterface.Command;
import dResources.DConst;
import dResources.DFileFilter;
/**
 *
 * ImportCmd is a class used to call a command
 *              import.
 *
 */

public class DefFilesToImportCmd implements Command {

  public DefFilesToImportCmd() {
  } // end constructor
//------------------------------
  public void execute(DApplication dApplic) {
    new DefFilesToImportDlg(dApplic);
  }

} /* end class DefFileToImportCmd */

