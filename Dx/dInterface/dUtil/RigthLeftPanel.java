/**
 * Created on Jun 21, 2005
 * 
 * TODO To change the class description for this generated file
 * 
 * Project Dx
 * Title: LeftRigthPanel.java 
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

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import dConstants.DConst;
import dInterface.DDialog;

/**
 * @author : Ruben Gonzalez-Rubio
 * 
 * Description: RigthLeftPanel.java is a class used to:
 * <p>
 * Build a JPanel with two buttons Right and Left, it is possible to enable or
 * disable the buttons, and to test if they are enabled
 * 
 * When a button is pressed, the ActionListener will call the methods
 * applyPressed or close Pressed in the parent instance
 * 
 */
public class RigthLeftPanel extends JPanel implements ActionListener {
	DDialog _parent;

	public RigthLeftPanel(DDialog parent) {
		_parent = parent;
		Container verticalBox = Box.createVerticalBox();
		verticalBox.add(Box.createVerticalGlue());
		verticalBox.add(Box.createVerticalStrut(150));
		String[] buttonsNames = { DConst.TO_RIGHT, DConst.TO_LEFT };
		for (int i = 0; i < buttonsNames.length; i++) {
			JButton button = new JButton(buttonsNames[i]);
			button.setActionCommand(buttonsNames[i]);
			button.addActionListener(this);
			//verticalBox.add(Box.createVerticalGlue());
			verticalBox.add(button);
			verticalBox.add(Box.createVerticalStrut(10));
		}

		//verticalBox.add(Box.createVerticalGlue());
		this.add(verticalBox);
	}
	 
	public void setRigthEnable() {
		((Box)this.getComponent(0)).getComponent(2).setEnabled(true);
	}

	public void setRigthDisable() {
		((Box)this.getComponent(0)).getComponent(2).setEnabled(false);
	}

	public boolean isRigthEnable() {
		return ((Box)this.getComponent(0)).getComponent(2).isEnabled();
	}

	public void setLeftEnable() {
		((Box)this.getComponent(0)).getComponent(4).setEnabled(true);
	}

	public void setLeftDisable() {
		((Box)this.getComponent(0)).getComponent(4).setEnabled(false);
	}

	public boolean isLeftEnable() {
		return ((Box)this.getComponent(0)).getComponent(2).isEnabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals(DConst.TO_RIGHT))
			_parent.rightPressed();

		if (command.equals(DConst.TO_LEFT)) {
			_parent.leftPressed();
		}

	}
} // end RigthLeftPanel
