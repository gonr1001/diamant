/**
 *
 * Title: ButtonsPanel $Revision: 1.6 $  $Date: 2005-07-05 12:04:25 $
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

/**
 * Description: ButtonsPanel is a class used to
 *
 */
package dInterface.dUtil;

import javax.swing.JPanel;

public abstract class ButtonsPanel extends JPanel {
	
	public abstract void setFirstEnable();

	public abstract void setFirstDisable();

	public abstract void setMiddleEnable();

	public abstract void setMiddleDisable();

	/**
	 * @return 
	 */
	public boolean isFirstEnable() {
		return false;
	}
}
