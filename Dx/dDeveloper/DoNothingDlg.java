/**
 *
 * Title: DoNothingDlg $Revision: 1.9 $  $Date: 2005-11-02 16:46:43 $
 * Description: DoNothingDlg is created by DoNotingCmd
 *              The dialog is displayed nothing is done.
 *              This class is used until
 *              the corresponding command will be written.
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
 * @version $Revision: 1.9 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dDeveloper;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import dInterface.DApplication;

/**
 *
 * DoNothingDlg is a class used to show a dialog which does nothing.
 *
 */

public class DoNothingDlg extends JDialog {

  final static String STR = "rgr, \nNot Implemented yet, \nWorking on that!!";
  /**
    * the constructor will displays the dialog
    *
    * @param jframe    the parent of the dialog
    * @param str       the title of the window dialog
    * @since           JDK1.3
    */	
   public DoNothingDlg(DApplication dApplic, String str) {
   JOptionPane.showMessageDialog( dApplic.getJFrame(),
                                    STR,
                                    str,
                                    JOptionPane.INFORMATION_MESSAGE );
   } // end constructor
} /* end class DoNothingDlg */
