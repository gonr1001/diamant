/**
 *
 * Title: ConflictCmd $Revision: 1.1 $  $Date: 2003-12-04 18:09:56 $
 * Description: ConflictCmd is class used as the command
 *              which displays the Conflict dialog
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



import dInterface.Command;
import dInterface.DApplication;

/**
 *
 * ConflictCmd is class used to call the command
 * which displays the Conflict dialog
 *
 */
public class ConflictCmd implements Command {

    public ConflictCmd() {
    } // end AboutCmd
    //------------------------------
    public void execute(DApplication dApplic) {
        new ConflictDlg(dApplic);
        dApplic.getMenuBar().postConflict();
    } // end execute
} /* end class AboutCmd */

