package dInterface.dUtil;
/**
 *
 * Title: ExitCmd $Revision: 1.1 $  $Date: 2003-06-09 10:26:42 $
 * Description: ExitCmd is a class used by menus
 *              to exit the application
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

import dInterface.DApplication;
import dInterface.Command;

/**
 *
 * ExitCmd is a class used to call a command
 *              to exit the application.
 *
 */

public class ExitCmd implements Command {

  public ExitCmd() {
  } // end constructor
//------------------------------
  public void execute(DApplication dApplic) {
    dApplic.closeApplic();
  }

} /* end class ExitCmd */

