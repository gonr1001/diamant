/**
 *
 * Title: ClassName $Revision: 1.4 $  $Date: 2005-04-19 20:37:43 $
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
 * @version $Revision: 1.4 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */

package dInterface.dData;


import dInterface.Command;
import dInterface.DApplication;

/**
 *
 * InstructorAvailabilityCmd is class used to call the command
 * which displays the InstructorAvailabilityDlg dialog
 *
 */
public class InstructorAvailabilityCmd implements Command {

    //------------------------------
    public void execute(DApplication dApplic) {
        new InstructorAvailabiliyDlg(dApplic);//.getJFrame(), DConst.INST_ASSIGN_TD, dApplic.getDMediator().getCurrentDoc().getDM());
    } // end execute
} /* end class InstructorAvailabilityCmd */
