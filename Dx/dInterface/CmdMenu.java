/**
 * Title: CmdMenu $Revision: 1.6 $  $Date: 2004-06-21 15:38:16 $
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
 * @version $Revision: 1.6 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInterface;


import javax.swing.JMenuItem;

/**
 *
 * CmdMenu is a class suggested by the command pattern.
 * All menu items will be associated with a command.
 *
 *
 */

public class CmdMenu extends JMenuItem implements CommandHolder {
  private Command _cmd;
  /**
    * the constructor associates the menu item with
    * the jframe and the String to be displayed by the menu
    *
    * @param str       The String displayed by the menu
    * @since           JDK1.3
    */
  public CmdMenu(String str) {
    super(str);
  }


/*  public CmdMenu(String str, JFrame jframe) {
    super(str);
  }*/

  //**-----------------------
  public void setCommand(Command cmd) {
    _cmd = cmd;
  }

  //**-----------------------
  public Command getCommand() {
    return _cmd;
   }
} /* end class CmdMenu */