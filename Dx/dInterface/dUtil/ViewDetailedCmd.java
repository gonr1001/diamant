/**
 *
 * Title: ViewDetailedCmd $Revision: 1.1 $  $Date: 2003-09-30 14:57:14 $
 * Description: ViewDetailedCmd
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
package dInterface.dUtil;


import javax.swing.JFrame;
import dResources.DConst;
import dInterface.Command;
import dInterface.DApplication;

/**
 *
 * AboutCmd is class used to call the command
 * which displays the About dialog
 *
 */
public class ViewDetailedCmd implements Command {

    public ViewDetailedCmd() {
    } // end ViewDetailedCmd
    //------------------------------
    public void execute(DApplication dApplic) {
      if ( dApplic.getDMediator().getCurrentDoc()!= null)
        dApplic.getDMediator().getCurrentDoc().displayDetailed();
    } // end execute
} /* end class ViewDetailedCmd */
