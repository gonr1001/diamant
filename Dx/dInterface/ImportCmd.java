package dInterface;
/**
 *
 * Title: ImportCmd $Revision: 1.1 $  $Date: 2003-05-12 15:33:28 $
 * Description: ImportCmd is a class used by menus
 *              to execute import
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
/**
 *
 * ImportCmd is a class used to call a command
 *              import.
 *
 */

public class ImportCmd implements Command {

  private final static String TITLEBOX = "Do Nothing Cmd";
  //private JFrame _jFrame;

  public ImportCmd (JFrame jFrame) {
    //_jFrame = jFrame;
  } // end constructor
//------------------------------
  public void execute(DApplication dApplic) {
    new ImportDlg(dApplic, TITLEBOX);
  }
} /* end class ImportCmd */
