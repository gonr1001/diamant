/**
 *
 * Title: PLAFCmd $Revision: 1.3 $  $Date: 2004-10-14 18:59:32 $
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
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInterface.dUtil;

import dInterface.Command;
import dInterface.DApplication;

public class PLAFCmd implements Command {

  public PLAFCmd () {
  } // end PLAFCmd
  //------------------------------
    public void execute(DApplication dApplic) {
        new PLAFDlg( dApplic );
    } // end execute
} /* end class PLAFCmd */
