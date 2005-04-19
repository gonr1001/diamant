package dInterface.dData;
/**
 *
 * Title: ImportCmd $Revision: 1.10 $  $Date: 2005-04-19 20:37:43 $
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
 * @version $Revision: 1.10 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */


import dInterface.Command;
import dInterface.DApplication;
/**
 *
 * ImportCmd is a class used to call a command
 *              import.
 *
 */

public class ImportCmd implements Command {

//------------------------------
  public void execute(DApplication dApplic) {
    new ImportDlg(dApplic);
    //dApplic.getMenuBar().postImportCmd();
  }
} /* end class ImportCmd */
