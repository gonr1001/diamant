/**
 *
 * Title: NewTTCyCmd $Revision: 1.2 $  $Date: 2003-06-02 15:05:36 $
 * Description: NewCmd is a class used to have a new
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
 * @version $Revision: 1.2 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInterface.dTimeTable;

import javax.swing.JFrame;
import dInterface.Command;
import dInterface.DApplication;
import dResources.DConst;


public class NewTTCyCmd implements Command {

  public NewTTCyCmd() {
  } //end NewCmd

  public void execute(DApplication dApplic) {
    new NewTTDlg(dApplic, DConst.CYCLE);
  } // end execute
} /* end NewCmd class */
