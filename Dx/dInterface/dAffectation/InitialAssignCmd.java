/**
 *
 * Title: PLAFCmd $Revision: 1.6 $  $Date: 2005-02-01 21:27:15 $
 * Description: PLAFCmd is a class used to
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
 * @version $Revision: 1.6 $
 * @author  $Author: syay1801 $
 * @since JDK1.3
 */
package dInterface.dAffectation;

import dConstants.DConst;
import dInterface.Command;
import dInterface.DApplication;
import eLib.exit.dialog.InformationDlg;


public class InitialAssignCmd implements Command {

  public InitialAssignCmd () {
  } // end InitialAssignCmd
  //------------------------------
    public void execute(DApplication dApplic) {
        dApplic.getDModel().changeInDModel(dApplic.getJFrame());
        new InformationDlg(dApplic.getJFrame(), DConst.INITIAL_ASSIGN_MESSAGE);
        dApplic.getMenuBar().postInitialAssign();
    } // end execute
} /* end class InitialAssignCmd */
