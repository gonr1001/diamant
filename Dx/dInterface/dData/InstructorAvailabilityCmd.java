/**
 *
 * Title: ClassName $Revision: 1.7 $  $Date: 2006-03-03 16:03:35 $
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
 * @version $Revision: 1.7 $
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
import dInterface.dAffectation.AvailabiltyDialog;

/**
 *
 * InstructorAvailabilityCmd is class used to call the command
 * which displays the InstructorAvailabilityDlg dialog
 *
 */
public class InstructorAvailabilityCmd implements Command {

    //------------------------------
    public void execute(DApplication dApplic) {
    	 new AvailabiltyDialog(dApplic,dApplic.getCurrentDModel().getSetOfInstructors());
    	// new InstructorAvailabiliyDlg(dApplic);
    } // end execute
} /* end class InstructorAvailabilityCmd */
