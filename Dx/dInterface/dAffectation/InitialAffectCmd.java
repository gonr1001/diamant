/**
 *
 * Title: PLAFCmd $Revision: 1.1 $  $Date: 2003-09-11 19:25:39 $
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
 * @author  $Author: ysyam $
 * @since JDK1.3
 */
package dInterface.dAffectation;

import dResources.DConst;
import dInterface.Command;
import dInterface.DApplication;

public class InitialAffectCmd implements Command {

  public InitialAffectCmd (DApplication dApplic) {
  } // end PLAFCmd
  //------------------------------
    public void execute(DApplication dApplic) {
        dApplic.getDMediator().getCurrentDoc().getDM().sendEvent(dApplic.getJFrame());
    } // end execute
} /* end class PLAFCmd */
