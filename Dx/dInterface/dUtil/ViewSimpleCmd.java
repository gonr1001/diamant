/**
 *
 * Title: AboutCmd $Revision: 1.3 $  $Date: 2005-07-05 12:04:26 $
 * Description: AboutCmd is class used as the command
 *              which displays the About dialog
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

/**
 *
 * AboutCmd is class used to call the command
 * which displays the About dialog
 *
 */
public class ViewSimpleCmd implements Command {

    public ViewSimpleCmd() {
//    	to avoid warning
    } // end ViewSimpleCmd
    //------------------------------
    public void execute(DApplication dApplic) {
      if ( dApplic.getDMediator().getCurrentDoc()!= null)
        dApplic.getDMediator().getCurrentDoc().displaySimple();
        //dApplic.getMenuBar().postAbout();
    } // end execute
} /* end class ViewSimpleCmd */
