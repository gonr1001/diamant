/**
 *
 * Title: Command $Revision: 1.3 $  $Date: 2003-03-10 17:28:41 $
 * Description: Command is an interface suggested by the
 *              command pattern. All real commands must implement
 *              the method execute which contains the code which
 *              the command must execute, it is called by
 *              actionPerformed
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
 * @version $Revision: 1.3 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInterface;

import javax.swing.JFrame;
/**
 *
 * Command is an interface suggested by the command pattern.
 *
 */
public interface Command {
    // public void execute();
    public void execute(DApplication dApplic);
} /* end interface Command */
