/**
 *
 * Title: PLAFCmd $Revision: 1.1 $  $Date: 2004-02-16 19:51:43 $
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
 * @version $Revision: 1.1 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInterface.dAffectation;

import com.iLib.gDialog.InformationDlg;

import dResources.DConst;
import dInterface.Command;
import dInterface.DApplication;


public class InitialAssignCmd implements Command {

  public InitialAssignCmd (DApplication dApplic) {
  } // end PLAFCmd
  //------------------------------
    public void execute(DApplication dApplic) {
        dApplic.getDMediator().getCurrentDoc().getDM().sendEvent(dApplic.getJFrame());
        new InformationDlg(dApplic.getJFrame(), DConst.INITIAL_ASSIGN_MESSAGE);
        dApplic.getMenuBar().postInitialAssign();

    } // end execute
} /* end class PLAFCmd */
