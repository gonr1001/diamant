/**
 *
 * Title: AboutDlg $Revision: 1.1.1.1 $  $Date: 2003-01-23 17:51:40 $
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
 * @version $Revision: 1.1.1.1 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInterface;

import javax.swing.*;

/**
 *
 * AboutDlg is a class used to display the about dialog.
 *
 */
public class AboutDlg extends JDialog {
    final static String MESS00 = "rgr, \nCopyRight\n 2000 - 2003";
    /**
     * the constructor will displays the dialog
     *
     * @param jframe    the parent of the dialog
     * @param str       the title of the window dialog
     * @since           JDK1.3
     */

    public AboutDlg(JFrame jFrame, String str) {
        JOptionPane.showMessageDialog( jFrame,
                                       MESS00,
                                       str,
                                       JOptionPane.INFORMATION_MESSAGE );
    } // end constructor AboutDlg
} /* end class AboutDlg */
