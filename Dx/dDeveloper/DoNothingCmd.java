/**
 *
 * Title: DoNothingCmd $Revision: 1.2 $  $Date: 2003-03-10 16:31:55 $
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
 * @version $Revision: 1.2 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dAux;

import dInterface.Command;

import javax.swing.JFrame;
import java.util.ResourceBundle;
/**
 *
 * DoNothingCmd is a class used to call a command
 *              which does nothing. It is used to wait for the
 *              real command.
 */

public class DoNothingCmd implements Command {

  private final static String TITLEBOX = "Do Nothing Cmd";
  //private JFrame _jFrame;

  public DoNothingCmd (JFrame jFrame) {
    //_jFrame = jFrame;
  } // end constructor
//------------------------------
  public void execute(JFrame jFrame) {
    new DoNothingDlg(jFrame, TITLEBOX);
  }
} /* end class DoNothingCmd */
