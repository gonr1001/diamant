/**
 *
 * Title: PLAFCmd $Revision: 1.2 $  $Date: 2004-06-09 19:29:18 $
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
 * @version $Revision: 1.2 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInterface.dUtil;

import dConstants.DConst;
import dInterface.Command;
import dInterface.DApplication;

public class PLAFCmd implements Command {

  public PLAFCmd (DApplication dApplic) {
  } // end PLAFCmd
  //------------------------------
    public void execute(DApplication dApplic) {
        new PLAFDlg(dApplic, DConst.PLAF_TD);
    } // end execute
} /* end class PLAFCmd */
