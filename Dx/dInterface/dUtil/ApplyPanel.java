/**
 *
 * Title: ApplyPanel $Revision: 1.2 $  $Date: 2004-04-07 14:35:30 $
 * Description: ApplyPanel is a class used to have two buttons in a panel:
 *              Apply and Close
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
 * @version $Revision: 1.2 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInterface.dUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JButton;

import dResources.DConst;

public class ApplyPanel extends ButtonsPanel {

  public ApplyPanel(ActionListener parentDialog, String [] buttonsNames) {
    super();
    JButton button = new JButton(buttonsNames[0]);
    button.setActionCommand(buttonsNames[0]);
    button.addActionListener(parentDialog);
    this.add(button);
    button = new JButton(buttonsNames[1]);
    button.setActionCommand(buttonsNames[1]);
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