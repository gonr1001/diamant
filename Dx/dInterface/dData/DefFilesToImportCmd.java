package dInterface.dData;
/**
 *
 * Title: DefFileToImportCmd $Revision: 1.3 $  $Date: 2003-10-28 13:50:21 $
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
 * @version $Revision: 1.3 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */



import dInterface.DApplication;

import dInterface.Command;

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

