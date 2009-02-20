package eLib.exit.dialog;
/**
 *
 * Title: InformationDlg $Revision: 1.5 $  $Date: 2006-12-30 16:23:24 $
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
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */



import java.lang.String;
import java.awt.Component;
import javax.swing.JOptionPane;

/**
  * Description: InformationDlg is a class used to display a message,
  *              when an exception is throw, the dialog displays
  *              the exception message.
  *              Normally after that the dialog is displayed
  *              a statment System.exit(1) is executed.
  *
  */
public class InformationDlg {
  private final String INFORMATION = "Information";

  /**
   * @param message a String  which will be the message that
   * the user see in the dialog display.
   *
   * <p>Clicking in the button will dispose the dialog.
   */
  public InformationDlg(String message) {
	  new InformationDlg(null, message, INFORMATION);
  }

  /**
   * @param message a String which will be the message that
   *        the user see in  in the dialog display.
   * @param title a String which will be displayed as the
   *        title message.
   *
   * <p>Clicking in the button will dispose the dialog.
   */
  public InformationDlg(String message, String title) {
	  new InformationDlg(null, message,title);
  }

 /**
   * @param comp a Component the parent of the JOptionPane
   * @param message a String  which will be the message that
   *        the user see in  in the dialog display.
   *        title message.
   *
   * <p>Clicking in the button will dispose the dialog.
   */
  public InformationDlg(Component comp, String message) {
         new InformationDlg(comp, message,INFORMATION);
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
  public InformationDlg(Component comp, String message, String title) {
    JOptionPane.showMessageDialog(comp,
                                  message,
                                  title,
                                  JOptionPane.INFORMATION_MESSAGE);
  }
} /* InformationDlg */
