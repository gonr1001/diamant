/**
 *
 * Title: AboutDlg $Revision: 1.7 $  $Date: 2004-10-14 18:59:31 $
 * Description: AboutDlg is a class representing the Dialog About
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
 * @version $Revision: 1.7 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInterface.dUtil;


//import javax.swing.JDialog;

import dConstants.DConst;
import dInterface.DApplication;
import eLib.exit.dialog.InformationDlg;
/**
 *
 * AboutDlg is a class used to display the about dialog.
 *
 */
public class AboutDlg { 

    /**
     * the constructor will displays the dialog
     *
     * @param dApplic   where the parent of the dialog is
     *                  stored
     * @since           JDK1.3
     */

    public AboutDlg(DApplication dApplic) {
      new InformationDlg(dApplic.getJFrame(), DConst.APP_NAME +  DConst.ABOUT_D,
                                         DConst.ABOUT_TD + DConst.APP_NAME);
    } // end constructor AboutDlg
} /* end class AboutDlg */
