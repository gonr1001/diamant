package eLib.exit.dialog;
/**
 *
 * Title: InformationDlg $Revision: 1.3 $  $Date: 2005-07-05 12:04:35 $
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
 * @version $Revision: 1.3 $
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
  *              when an exception is throwed, the dialog displays
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
    JOptionPane.showMessageDialog(null,
                                  message,
                                  INFORMATION,
                                  JOptionPane.INFORMATION_MESSAGE);
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
    JOptionPane.showMessageDialog(null,
                                 message,
                                 title,
                                 JOptionPane.INFORMATION_MESSAGE);
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
    JOptionPane.showMessageDialog(comp,
                               message,
                               INFORMATION,
                               JOptionPane.INFORMATION_MESSAGE);
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
