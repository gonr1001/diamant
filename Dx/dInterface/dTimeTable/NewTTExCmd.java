/**
 *
 * Title: NewTTExCmd $Revision: 1.7 $  $Date: 2004-02-16 14:57:15 $
 * Description: NewTTExCmd is a class used to have a new
 *  			document window
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
 * @version $Revision: 1.7 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInterface.dTimeTable;


import dInterface.Command;
import dInterface.DApplication;
import dResources.DConst;


public class NewTTExCmd implements Command {

  public NewTTExCmd() {
  } //end NewTTExCmd

  public void execute(DApplication dApplic) {
    new NewTTDlg(dApplic, DConst.EXAM);
    // this is  done in  NewTTDlg if a file is opened
    // dApplic.getMenuBar().postNewTTCyCmd();
    // there is no postNewTTExCmd();
  } // end execute
} /* end NewTTExCmd class */
