/**
 *
 * Title: PLAFCmd $Revision: 1.1 $  $Date: 2003-02-20 11:07:28 $
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
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInterface;

import javax.swing.JFrame;
import dInternal.Preferences;
import dResources.DConst;

public class PLAFCmd implements Command {
  private DView _dView;

  public PLAFCmd (DView dView) {
       _dView = dView;
    } // end PLAFCmd
    //------------------------------
    public void execute() {
        new PLAFDlg(_dView, DConst.PLAF_TD);
    } // end execute
} /* end class PLAFCmd */
