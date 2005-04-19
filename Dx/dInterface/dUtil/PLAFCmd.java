/**
 *
 * Title: PLAFCmd $Revision: 1.4 $  $Date: 2005-04-19 20:37:48 $
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
 * @version $Revision: 1.4 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInterface.dUtil;

import dInterface.Command;
import dInterface.DApplication;

public class PLAFCmd implements Command {

  //------------------------------
    public void execute(DApplication dApplic) {
        new PLAFDlg( dApplic );
    } // end execute
} /* end class PLAFCmd */
