/**
 *
 * Title: CommandHolder $Revision: 1.1.1.1 $  $Date: 2003-01-23 17:51:40 $
 * Description: CommandHolder is an interface suggested by the
  *              command pattern. A class implementing the interface
  *              must allows to set a command (this associates a
  *              command to a class) and to get the command (to execute
 *               the command).
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
 * @version $Revision: 1.1.1.1 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInterface;

/**
 *
 * CommandHolder is an interface suggested by the command pattern.
 *
 */
public interface CommandHolder {
    public void setCommand (Command cmd);
    public Command getCommand();

} /* end interface CommandHolder */
