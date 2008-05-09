package eLib.exit.dialog;
/**
 *
 * Title: FatalProblemDlg $Revision: 1.6 $  $Date: 2007-10-25 17:55:51 $
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
 * @version $Revision: 1.6 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */



import java.lang.String;
import java.awt.Component;
import javax.swing.JOptionPane;

/**
  * Description: FatalProblemDlg is a class used to display a message,
  *              when an exception is throw, the dialog displays
  *              the exception message.
  *              Normally after the dialog is displayed
  *              a statement System.exit(1) is executed.
  *
  */
public class FatalProblemDlg {
  private final String FATAL_PROBLEM = "Fatal Problem";

 /**
   * @param message a String  which will be the message that
   * the user see in the dialog display.
   *
   * <p>Clicking in the button will dispose the dialog.
   */
  public FatalProblemDlg(String message) {
    JOptionPane.showMessageDialog(null,
                                  message,
                                  FATAL_PROBLEM,
                                  JOptionPane.ERROR_MESSAGE);
  }

 /**
   * @param message a String which will be the message that
   * the user see in  in the dialog display.
   * @param title a String which will be displayed as the
   * title message.
   *
   * <p>Clicking in the button will dispose the dialog.
   */
//  public FatalProblemDlg(String message) {
//	  new FatalProblemDlg(null,message,FATAL_PROBLEM);
//  }

 /**
  * @param comp a Component the parent of the JOptionPane
  * @param message a String  which will be the message that
  * the user see in  in the dialog display.
  * title message.
  *
  * <p>Clicking in the button will dispose the dialog.
  */
  public FatalProblemDlg(Component comp, String message) {
	 new FatalProblemDlg(comp,message,FATAL_PROBLEM);
  }
  
  
  /**
   * @param comp a Component the parent of the JOptionPane
   * @param message a String  which will be the message that
   * the user see in  in the dialog display.
   * @param title a String which will be displayed as the
   * title message.
   *
   * <p>Clicking in the button will dispose the dialog.
   */
  public FatalProblemDlg(Component comp, String message, String title) {
  	JOptionPane.showMessageDialog(comp,message,
			title,JOptionPane.ERROR_MESSAGE);
  }
  
} /* FatalProblemDlg */




