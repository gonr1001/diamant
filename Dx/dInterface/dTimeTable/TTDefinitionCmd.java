/**
 *
 * Title: AboutCmd $Revision: 1.1 $  $Date: 2003-05-20 16:23:15 $
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
 * @version $Revision: 1.1 $
 * @author  $Author: alexj $
 * @since JDK1.3
 */
package dInterface.dTimeTable;

//import java.awt.*;
import javax.swing.JFrame;
import dResources.DConst;
import dInterface.Command;
import dInterface.DApplication;

/**
 *
 * TTDefinitionCmd is class used to call the command
 * which displays the TTDefinitionDlg dialog
 *
 */
public class TTDefinitionCmd implements Command {

    public TTDefinitionCmd () {
    } // end TTDefinitionCmd
    //------------------------------
    public void execute(DApplication dApplic) {
        new TTDefinitionDlg(dApplic, DConst.ABOUT_TD + DConst.APP_NAME);
    } // end execute
} /* end class TTDefinitionCmd */

