/**
 *
 * Title: AboutCmd $Revision: 1.3 $  $Date: 2003-03-10 17:28:42 $
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
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInterface;

//import java.awt.*;
import javax.swing.JFrame;
import dResources.DConst;

/**
 *
 * AboutCmd is class used to call the command
 * which displays the About dialog
 *
 */
public class TTDefinitionCmd implements Command {
    private DMediator _mediator;
    private JFrame _jFrame;

    public TTDefinitionCmd (DApplication dApplic, DMediator mediator) {

        //_jFrame = jFrame;
        //_mediator = mediator;
    } // end AboutCmd
    //------------------------------
    public void execute(DApplication dApplic) {
        new TTDefinitionDlg(dApplic, DConst.ABOUT_TD + DConst.APP_NAME);
    } // end execute
} /* end class AboutCmd */

