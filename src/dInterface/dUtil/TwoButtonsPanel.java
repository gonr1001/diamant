/**
 *
 * Title: TwoButtonsPanel $Revision: 1.6 $  $Date: 2005-07-05 12:04:25 $
 * Description: TwoButtonsPanel is a class used to have two buttons in a panel:
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
 * @version $Revision: 1.6 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInterface.dUtil;


import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class TwoButtonsPanel extends ButtonsPanel {

  public TwoButtonsPanel(ActionListener parentDialog, String [] buttonsNames) {
    super();
    for(int i = 0; i < buttonsNames.length; i++) {
      JButton button = new JButton(buttonsNames[i]);
      button.setActionCommand(buttonsNames[i]);
      button.addActionListener(parentDialog);
      this.add(button);
    }
  }

  public void setFirstEnable() {
    getComponent(0).setEnabled(true);
  }

  public void setFirstDisable() {
    getComponent(0).setEnabled(false);
  }

  public void setMiddleEnable() {
  	// to avoid warning
  }

  public void setMiddleDisable() {
  	//  to avoid warning
  }
  
  public boolean isFirstEnable() {
  	return getComponent(0).isEnabled();
  }
}
