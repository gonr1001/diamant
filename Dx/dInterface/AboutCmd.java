/**
 *
 * Title: AboutCmd $Revision: 1.1.1.1 $  $Date: 2003-01-23 17:51:40 $
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
 * @version $Revision: 1.1.1.1 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInterface;

import java.awt.*;
import javax.swing.*;

/**
 *
 * AboutCmd is class used to call the command
 * which displays the About dialog
 *
 */
public class AboutCmd implements Command {

    final static String BOXMESS = "Boite a propos de";
    private JFrame _jFrame;

    public AboutCmd (JFrame jFrame) {
        _jFrame = jFrame;
    } // end AboutCmd
    //------------------------------
    public void execute() {
        new AboutDlg(_jFrame, BOXMESS);
    } // end execute
} /* end class AboutCmd */

