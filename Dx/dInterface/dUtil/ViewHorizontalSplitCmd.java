/**
 *
 * Title: ViewHorizontalSplitCmd $Revision: 1.2 $  $Date: 2003-10-28 14:24:53 $
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
 * @version $Revision: 1.2 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInterface.dUtil;



import dInterface.Command;
import dInterface.DApplication;

/**
 *
 * AboutCmd is class used to call the command
 * which displays the About dialog
 *
 */
public class ViewHorizontalSplitCmd implements Command {

    public ViewHorizontalSplitCmd() {
    } // end ViewDetailedCmd
    //------------------------------
    public void execute(DApplication dApplic) {
      if ( dApplic.getDMediator().getCurrentDoc()!= null)
        dApplic.getDMediator().getCurrentDoc().displayHorizontalSplit();
    } // end execute
} /* end class ViewDetailedCmd */
