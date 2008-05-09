/**
 *
 * Title: DoNothingDlg 
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
 *
 *
 */
package developer;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

/**
 * 
 * DoNothingDlg is a class used to show a dialog which does nothing.
 * 
 */
@SuppressWarnings("serial")
public class DoNothingDlg extends JDialog {

	final static String STR = "rgr, \nNot Implemented yet, \nWorking on that!!";

	/**
	 * the constructor will displays the dialog
	 *
	 * @param jframe    the parent of the dialog
	 * @param str       the title of the window dialog
	 * @since           JDK1.3
	 */
	public DoNothingDlg(JFrame jFrame, String str) {
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		JOptionPane.showMessageDialog(jFrame, STR, str,
				JOptionPane.INFORMATION_MESSAGE);
	} // end constructor
} /* end class DoNothingDlg */
