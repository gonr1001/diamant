/**
 *
 * Title: AboutCmd $Revision: 1.4 $  $Date: 2003-09-08 11:05:01 $
 * Description: AboutCmd is class used as the command
 *              which displays the About dialog
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
package dInterface.dUtil;


import javax.swing.JFrame;
import dResources.DConst;
import dInterface.Command;
import dInterface.DApplication;

/**
 *
 * AboutCmd is class used to call the command
 * which displays the About dialog
 *
 */
public class AboutCmd implements Command {

    public AboutCmd() {
    } // end AboutCmd
    //------------------------------
    public void execute(DApplication dApplic) {
        new AboutDlg(dApplic);
        dApplic.getMenuBar().postAbout();
    } // end execute
} /* end class AboutCmd */

