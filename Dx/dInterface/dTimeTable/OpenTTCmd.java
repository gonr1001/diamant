/**
 *
 * Title: OpenTTCmd $Revision: 1.2 $  $Date: 2003-09-09 13:09:59 $
 * Description: OpenTTCmd is a class used to open a file then display in
 *  			the document window
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


public class OpenTTCmd implements Command {

  public OpenTTCmd() {
  } //end NewCmd

  public void execute(DApplication dApplic) {
    new OpenTTDlg(dApplic);
    dApplic.getMenuBar().postOpenTTCmd();
  } // end execute
} /* end OpenTTCmd class */
