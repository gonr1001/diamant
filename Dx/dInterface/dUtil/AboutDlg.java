/**
 *
 * Title: AboutDlg $Revision: 1.5 $  $Date: 2004-06-21 15:38:17 $
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
 * @version $Revision: 1.5 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInterface.dUtil;


import javax.swing.JDialog;

import com.iLib.gDialog.InformationDlg;

import dConstants.DConst;
import dInterface.DApplication;
/**
 *
 * AboutDlg is a class used to display the about dialog.
 *
 */
public class AboutDlg extends JDialog {

    /**
     * the constructor will displays the dialog
     *
     * @param jframe    the parent of the dialog
     * @param str       the title of the window dialog
     * @since           JDK1.3
     */

    public AboutDlg(DApplication dApplic) {
      new InformationDlg(dApplic.getJFrame(), DConst.APP_NAME +  DConst.ABOUT_D,
                                         DConst.ABOUT_TD + DConst.APP_NAME);
    } // end constructor AboutDlg
} /* end class AboutDlg */
