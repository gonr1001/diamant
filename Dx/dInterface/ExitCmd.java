package dInterface;
/**
 *
 * Title: DefFileToImportCmd $Revision: 1.1 $  $Date: 2003-05-15 14:35:46 $
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

public class ExitCmd implements Command {

//  private final static String TITLEBOX = "Do Nothing Cmd";

  public ExitCmd() {
  } // end constructor
//------------------------------
  public void execute(DApplication dApplic) {

            dApplic.getJFrame().dispose();
            System.exit(0);

  }

} /* end class DefFileToImportCmd */

