/**
 *
 * Title: CloseCmd $Revision: 1.5 $  $Date: 2004-03-02 20:29:55 $
 * Description: CloseCmd is a class used to close a
 *  			document
 *
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
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInterface.dTimeTable;


import dInterface.Command;
import dInterface.DApplication;


public class CloseCmd implements Command {

  public CloseCmd() {
  } //end CloseCmd

  public void execute(DApplication dApplic) {
    dApplic.getDMediator().closeCurrentDoc();
    if(!dApplic.getDMediator().getCancel()) {
      dApplic.getMenuBar().postCloseCmd();
    }
  } // end execute
} /* end CloseCmd class */
