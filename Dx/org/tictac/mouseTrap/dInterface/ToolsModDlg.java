/**
 *
 * Title: AboutDlg $Revision: 1.2 $  $Date: 2004-09-24 14:07:39 $
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
 * @version $Revision: 1.2 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */

package org.tictac.mouseTrap.dInterface;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
/**
 *
 * AboutDlg is a class used to display the about dialog.
 *
 */
public class ToolsModDlg extends JDialog implements ActionListener {
	private MyApplication _dApplic;
  	private String [] _buttonsNames = {"OK","CANCEL"};
  	private JPanel  _buttonsPanel;
  	private JTextField _textField0;
  	private JTextField _textField1;
	  
  /**
   * the constructor will displays the dialog
   *
   * @param jframe    the parent of the dialog
   * @param str       the title of the window dialog
   * @since           JDK1.3
   */

  	public ToolsModDlg(MyApplication dApplic) {
		super(dApplic.getJFrame(),"Options de conflits");
		_dApplic = dApplic;
		Dimension dim = new Dimension(600,300);
		setSize(dim);
		setResizable(false);
		this.getContentPane().setLayout(new BorderLayout());
		JPanel jPanel = new JPanel();
		Object [] a =_dApplic.getParams();
		_textField0 = new JTextField(""+a[0]);
		_textField1 = new JTextField(""+a[1]);
		jPanel.setLayout(new GridLayout(6,2));
		jPanel.add(new JLabel(" i = "));
		jPanel.add(_textField0);
		jPanel.add(new JLabel(" j = "));
		jPanel.add(_textField1);

		this.setLocationRelativeTo(dApplic.getJFrame());
		this.setResizable(false);
	  	_buttonsPanel = buttonsPanel(this, _buttonsNames);
	  	this.getContentPane().add(jPanel, BorderLayout.CENTER);
	  	this.getContentPane().add(_buttonsPanel, BorderLayout.SOUTH);
	  	this.setVisible(true);
	} // end constructor AboutDlg

  	public void actionPerformed( ActionEvent ae ) {
		String command = ae.getActionCommand();
		Object[] v = new Object[2];
		//If buttons CANCEL
		if (command.equals("CANCEL")) {
			dispose();
			return;
		}
		if (command.equals("OK")) {
		  int ligne = validation();
		  if (ligne == 0) {
			v[0]=_textField0.getText();
			v[1]=_textField1.getText();
			//_dApplic.getPreferences().setConflicLimits(v);
			//_dApplic.getPreferences().save();
			_dApplic.setParams(v);
			dispose();
		  }
		  else
			JOptionPane.showMessageDialog(this,"Au moins une erreur en ligne "+ ligne);

		} //end if
	 } // end actionPerformed

	private int validation() {
		if(!testText(_textField0.getText(), 0, 9999))
			return 1;
		if(!testText(_textField1.getText(), 0, 9999))
			return 2;
		return 0;
	}

	private boolean testText(String str, int inf , int sup) {
		boolean res = false;
		int i;
		if (str == null)
		  return res;
		if(str.length()==0)
		  return res;
		try {
		   i = Integer.parseInt(str);
		} catch (Exception e) {
		  return res;
		}
		if ( i >= inf && i <= sup)
		  res = true;

		return res;
	}
	/**
	* Creates a panel of buttons to be placed at the bottom of a Dialog.
	* This method adds the ActionListener for each button
	* @param parentDialog The dialog where this panel is placed
	* @param buttonsNames An array of names of buttons
	* @return panel
	*/

	// to be commented
	public static JPanel buttonsPanel(ActionListener parentDialog, String [] buttonsNames){
		JPanel panel = new JPanel();
		JButton button;
		for(int i = 0; i<buttonsNames.length; i++){
		  button = new JButton(buttonsNames[i]);
		  button.setActionCommand(buttonsNames[i]);
		  button.addActionListener(parentDialog);
		  panel.add(button) ;
		}
		return panel;
	}//end method

} /* end class AboutDlg */
