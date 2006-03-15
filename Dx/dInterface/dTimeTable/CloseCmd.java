/**
 *
 * Title: CloseCmd $Revision: 1.8 $  $Date: 2006-03-15 14:25:35 $
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
 * @version $Revision: 1.8 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInterface.dTimeTable;


import dInterface.Command;
import dInterface.DApplication;


public class CloseCmd implements Command {

  public void execute(DApplication dApplic) {
    dApplic.getDMediator().closeCurrentDoc();
    if(!dApplic.getDMediator().getCancel()) {
    	if (dApplic.isInDevelopment()) {
			dApplic.initialState();
		} else {
//			dApplic.getMenuBar().postCloseCmd();
		}
    }
  } // end execute
} /* end CloseCmd class */
