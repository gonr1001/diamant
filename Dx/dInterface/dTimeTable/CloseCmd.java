/**
 *
 * Title: CloseCmd $Revision: 1.2 $  $Date: 2003-09-08 14:57:02 $
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
 * @version $Revision: 1.2 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInterface.dTimeTable;

import javax.swing.JFrame;
import dInterface.Command;
import dInterface.DApplication;


public class CloseCmd implements Command {

  public CloseCmd() {
  } //end CloseCmd

  public void execute(DApplication dApplic) {
    dApplic.getDMediator().closeCurrentDoc();
    dApplic.getMenuBar().postShowAll();
  } // end execute
} /* end CloseCmd class */
