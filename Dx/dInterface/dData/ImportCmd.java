package dInterface.dData;
/**
 *
 * Title: ImportCmd $Revision: 1.5 $  $Date: 2003-09-09 11:37:10 $
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
 * @version $Revision: 1.5 $
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
  int _t;
    public ImportCmd (JFrame jFrame, int t) {
      _t = t;
    } // end constructor

  public ImportCmd (JFrame jFrame) {
  } // end constructor
//------------------------------
  public void execute(DApplication dApplic) {
    new ImportDlg(dApplic);
    dApplic.getMenuBar().postImportCmd();
  }
} /* end class ImportCmd */
