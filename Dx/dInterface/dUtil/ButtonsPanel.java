/**
 *
 * Title: AboutDlg $Revision: 1.1 $  $Date: 2004-04-07 15:41:01 $
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

public abstract class ButtonsPanel extends JPanel {
  public ButtonsPanel(){}

  public abstract void setFirstEnable();
  public abstract void setFirstDisable();
  //public abstract void setMiddleEnable();
  //public abstract void setMiddleDisable();
}

