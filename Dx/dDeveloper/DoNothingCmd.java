/**
 *
 * Title: DoNothingCmd $Revision: 1.8 $  $Date: 2005-04-11 14:38:12 $
 * Description: DoNothingCmd is a class used by menus
 *              to execute a command
 *              which displays the DoNothingDlg which does nothing.
 *              This class is used until
 *              the corresponding command will be written.
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
 * @version $Revision: 1.8 $
 * @author  $Author: durp1901 $
 * @since JDK1.3
 */
package dDeveloper;

import dInterface.Command;
import dInterface.DApplication;
/**
 *
 * DoNothingCmd is a class used to call a command
 *              which does nothing. It is used to wait for the
 *              real command.
 * 
 * XXXX Pascal: exemple d'utilite?
 */

public class DoNothingCmd implements Command {

  private final static String TITLEBOX = "Do Nothing Cmd";


  public DoNothingCmd () {
  } // end constructor
//------------------------------
  public void execute(DApplication dApplic) {
    new DoNothingDlg(dApplic, TITLEBOX);
  }
} /* end class DoNothingCmd */
