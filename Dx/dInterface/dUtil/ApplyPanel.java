/**
 *
 * Title: AboutDlg $Revision: 1.1 $  $Date: 2004-04-05 12:50:54 $
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
 * @version $Revision: 1.1 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInterface.dUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JButton;

import dResources.DConst;

public class ApplyPanel extends JPanel {
  private String [] _buttonsNames = {DConst.BUT_APPLY, DConst.BUT_CLOSE};


  public ApplyPanel(ActionListener parentDialog) {
    super();
    JButton button = new JButton(DConst.BUT_APPLY);
    button.setActionCommand(DConst.BUT_APPLY);
    button.addActionListener(parentDialog);
    this.add(button);
    button = new JButton(DConst.BUT_CLOSE);
    button.setActionCommand(DConst.BUT_CLOSE);
    button.addActionListener(parentDialog);
    this.add(button) ;
  }

  public void setApplyEnable() {
    getComponent(0).setEnabled(true);
  }

  public void setApplyDisable() {
    getComponent(0).setEnabled(false);
  }
}