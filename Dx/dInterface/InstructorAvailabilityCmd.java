/**
 *
 * Title: ClassName $Revision: 1.1 $  $Date: 2003-03-11 17:44:03 $
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
 * @version $Revision: 1.1 $
 * @author  $Author: rgr $
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */

package dInterface;

import javax.swing.JFrame;
import dResources.DConst;

/**
 *
 * AboutCmd is class used to call the command
 * which displays the About dialog
 *
 */
public class InstructorAvailabilityCmd implements Command {

    private JFrame _jFrame;

    public InstructorAvailabilityCmd (JFrame jFrame) {
        _jFrame = jFrame;
    } // end AboutCmd
    //------------------------------
    public void execute(DApplication dApplic) {
        new InstructorAvailabiliyDlg(dApplic.getJFrame(), "to See");
    } // end execute
} /* end class AboutCmd */
