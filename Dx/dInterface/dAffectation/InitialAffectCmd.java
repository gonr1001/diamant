/**
 *
 * Title: PLAFCmd $Revision: 1.3 $  $Date: 2003-09-19 16:10:54 $
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
 * @version $Revision: 1.3 $
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
        System.out.println(dApplic.getDMediator().getCurrentDoc().getDM().getStandardReport()[0]);//debug
        System.out.println(dApplic.getDMediator().getCurrentDoc().getDM().getStandardReport()[1]);//debug

    } // end execute
} /* end class PLAFCmd */
