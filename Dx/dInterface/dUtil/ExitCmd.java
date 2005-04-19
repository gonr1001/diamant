package dInterface.dUtil;
/**
 *
 * Title: ExitCmd $Revision: 1.3 $  $Date: 2005-04-19 20:37:47 $
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
 * @version $Revision: 1.3 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */

import dInterface.Command;
import dInterface.DApplication;

/**
 *
 * ExitCmd is a class used to call a command
 *              to exit the application.
 *
 */

public class ExitCmd implements Command {

//------------------------------
  public void execute(DApplication dApplic) {
    dApplic.closeApplic();
  }

} /* end class ExitCmd */

