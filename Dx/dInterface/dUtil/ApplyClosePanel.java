/**
 * Created on Jun 21, 2005
 *  
 * Project Dx
 * Title: ApplyClosePanel.java 
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
 * 
 * @since JDK1.3
 */

package dInterface.dUtil;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import dConstants.DConst;
import dInterface.DDialog;

/**
 * @author : Ruben Gonzalez-Rubio
 *
 * Description: ApplyClosePanel.java is a class used to: 
 * <p>
 * Build a JPanel with two buttons Apply and Close,
 * it is possible to enable or disable the buttons, and
 * to test if they are enabled
 * 
 * When a button is pressed, the ActionListener will call
 * the methods applyPressed or close Pressed in the parent
 * instance
 * 
 */
abstract public class ApplyClosePanel extends JPanel implements ApplyCloseInterface {
	DDialog _parent;
	JButton _button;

	public ApplyClosePanel(DDialog parent) {
		_parent = parent;
		String[] buttonsNames = { DConst.BUT_APPLY, DConst.BUT_CLOSE };
		for (int i = 0; i < buttonsNames.length; i++) {
			_button = new JButton(buttonsNames[i]);
			_button.setActionCommand(buttonsNames[i]);
			this.add(_button);
		}
	}
	
	public void setActionListener(ActionListener actionListener) {
		_button.addActionListener(actionListener);
	}

	public void setApplyEnable() {
		getComponent(0).setEnabled(true);
	}

	public void setApplyDisable() {
		getComponent(0).setEnabled(false);
	}

	public boolean isApplyEnable() {
		return getComponent(0).isEnabled();
	}

	public void setCloseEnable() {
		getComponent(1).setEnabled(true);
	}

	public void setCloseDisable() {
		getComponent(1).setEnabled(false);
	}

	public boolean isCloseEnable() {
		return getComponent(1).isEnabled();
	}

//	/* (non-Javadoc)
//	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
//	 */
//	public void actionPerformed(ActionEvent e) {
//		String command = e.getActionCommand();
//		if (command.equals(DConst.BUT_CLOSE))
//			_parent.closePresed();
//		
//		if (command.equals(DConst.BUT_APPLY)) {
//			_parent.applyPressed();
//		}
//		
//	}

//	/* (non-Javadoc)
//	 * @see dInterface.dUtil.ApplyCloseInterface#applyPressed()
//	 */
//	public void applyPressed() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/* (non-Javadoc)
//	 * @see dInterface.dUtil.ApplyCloseInterface#closePresed()
//	 */
//	public void closePresed() {
//		// TODO Auto-generated method stub
//		
//	}	
} //end ApplyClosePanel
