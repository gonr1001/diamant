/**
 *
 * Title: ButtonsPanel $Revision: 1.5 $  $Date: 2005-04-19 20:37:46 $
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
 */


/**
 * Description: ButtonsPanel is a class used to
 *
 */
package dInterface.dUtil;


import javax.swing.JPanel;


public abstract class ButtonsPanel extends JPanel {
  //public ButtonsPanel(){}

  public abstract void setFirstEnable();
  public abstract void setFirstDisable();
  public abstract void setMiddleEnable();
  public abstract void setMiddleDisable();

/**
 * @return 
 */
public boolean isFirstEnable() {
	// TODO Auto-generated method stub
	return false;
}
}

