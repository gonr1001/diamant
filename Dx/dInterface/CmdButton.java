/**
 * Title: CmdButton $Revision: 1.1 $  $Date: 2003-02-20 11:07:28 $
 * Description: CmdMenu is a class suggested by the
 *              command pattern. All menu items will be associated
 *              with a command.
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
package dInterface;

import java.awt.*;

import javax.swing.*;

/**
 *
 * CmdMenu is a class suggested by the command pattern.
 * All menu items will be associated with a command.
 *
 *
 */

public class CmdButton extends JButton implements CommandHolder {
  private Command _cmd;
  /**
    * the constructor associates the menu item with
    * the jframe and the String to be displayed by the menu
    *
    * @param str       The String displayed by the menu
    * @since           JDK1.3
    */
  public CmdButton(String str) {
    super(str);
  }

/*  public CmdButton(String str, JFrame jframe) {
    super(str);
  }*/

  //**-----------------------
  public void setCommand (Command cmd) {
    _cmd = cmd;
  }
  //**-----------------------
  public Command getCommand() {
    return _cmd;
   }
} /* end class CmdMenu */