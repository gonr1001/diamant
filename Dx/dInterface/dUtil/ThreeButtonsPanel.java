/**
 *
 * Title: ThreeButtonsPanel $Revision: 1.1 $  $Date: 2004-04-07 15:41:01 $
 * Description: ThreeButtonsPanel is a class used to have three buttons in a panel:
 *               X, Apply and Close
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

public class ThreeButtonsPanel extends ButtonsPanel {

  public ThreeButtonsPanel(ActionListener parentDialog, String [] buttonsNames) {
    super();
    JButton button = new JButton(buttonsNames[0]);
    button.setActionCommand(buttonsNames[0]);
    button.addActionListener(parentDialog);
    this.add(button);
    button = new JButton(buttonsNames[1]);
    button.setActionCommand(buttonsNames[1]);
    button.addActionListener(parentDialog);
    this.add(button) ;
    button = new JButton(buttonsNames[2]);
    button.setActionCommand(buttonsNames[2]);
    button.addActionListener(parentDialog);
    this.add(button) ;
  }

  public void setFirstEnable() {
    getComponent(0).setEnabled(true);
  }

  public void setFirstDisable() {
    getComponent(0).setEnabled(false);
  }

  public void setMiddleEnable() {
    getComponent(1).setEnabled(true);
  }

  public void setMiddleDisable() {
    getComponent(1).setEnabled(false);
  }
}