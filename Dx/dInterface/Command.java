package dInterface;
/**
 *
 * Title: Command $Revision: 1.4 $  $Date: 2003-06-09 10:23:40 $
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
 * @version $Revision: 1.4 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */


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
