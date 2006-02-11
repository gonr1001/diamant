/**
 *
 * Title: ConflictDlg 
 * Description: 
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
 *
 */
package dInterface.dUtil;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dConstants.DConst;
import dInterface.DApplication;

/**
 *
 * AboutDlg is a class used to display the about dialog.
 *
 */
public class ConflictDlg extends JDialog implements ActionListener {
	DApplication _dApplic;

	private String[] _buttonsNames = { DConst.BUT_OK };

	private JPanel _buttonsPanel;

	private JTextField _textField0;

	private JTextField _textField1;

	private JTextField _textField2;

	private JTextField _textField3;

	private JTextField _textField4;

	private JTextField _textField5;

	private JTextField _textField6;

	/**
	 * the constructor will displays the dialog
	 *
	 * @param jframe    the parent of the dialog
	 * @param str       the title of the window dialog
	 * @since           JDK1.3
	 */

	public ConflictDlg(DApplication dApplic) {
		super(dApplic.getJFrame(), "Options de conflits");
		_dApplic = dApplic;
		Dimension dim = new Dimension(600, 300);
		setSize(dim);
		setResizable(false);
		this.getContentPane().setLayout(new BorderLayout());
		JPanel jPanel = new JPanel();
		int[] a = _dApplic.getPreferences().getConflictLimits();
		/*String [] b = new  String  [a.length];
		 for(int i= 0; i <a.length; i ++) {
		 b [i] = "" + a[i];
		 }*/

		_textField0 = new JTextField("" + a[0]);
		_textField1 = new JTextField("" + a[1]);
		_textField2 = new JTextField("" + a[2]);
		_textField3 = new JTextField("" + a[3]);
		_textField4 = new JTextField("" + a[4]);
		_textField5 = new JTextField("" + a[5]);
		_textField6 = new JTextField("" + a[6]);
		jPanel.setLayout(new GridLayout(7, 2));
		jPanel.add(new JLabel("Max Conflits Étu entre 2 Eve ="));
		jPanel.add(_textField0);

		jPanel.add(new JLabel("Max Conflits Ens entre 2 Eve ="));
		jPanel.add(_textField1);

		jPanel.add(new JLabel("Max Conflits Loc entre 2 Eve ="));
		jPanel.add(_textField2);

		jPanel.add(new JLabel(
				"Placer dans périodes (0=Normale, 1= Norm et Bas)"));
		jPanel.add(_textField3);

		jPanel.add(new JLabel("Nombre Max de cours dans une période ="));
		jPanel.add(_textField4);

		jPanel.add(new JLabel("Périodes d'écart ="));
		jPanel.add(_textField5);

		jPanel.add(new JLabel("Taux de remplissage des locaux (en %) ="));
		jPanel.add(_textField6);

		this.setLocationRelativeTo(dApplic.getJFrame());
		this.setResizable(false);

		_buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);

		this.getContentPane().add(jPanel, BorderLayout.CENTER);
		this.getContentPane().add(_buttonsPanel, BorderLayout.SOUTH);
		this.setVisible(true);
	} // end constructor AboutDlg

	public void actionPerformed(ActionEvent ae) {
		String command = ae.getActionCommand();
		Vector v = new Vector();
		//If buttons CANCEL
		if (command.equals(DConst.BUT_OK)) {
			int ligne = validation();
			if (ligne == 0) {
				v.add(_textField0.getText());
				v.add(_textField1.getText());
				v.add(_textField2.getText());
				v.add(_textField3.getText());
				v.add(_textField4.getText());
				v.add(_textField5.getText());
				v.add(_textField6.getText());
				_dApplic.getPreferences().setConflicLimits(v);
				_dApplic.getPreferences().save();
				dispose();
			} else
				JOptionPane.showMessageDialog(this, "Erreur à la ligne: "
						+ ligne);

		} //end if

	} // end actionPerformed

	private int validation() {
		if (!testText(_textField0.getText(), 0, 9999))
			return 1;
		if (!testText(_textField1.getText(), 0, 9999))
			return 2;
		if (!testText(_textField2.getText(), 0, 9999))
			return 3;
		if (!testText(_textField3.getText(), 0, 2))
			return 4;
		if (!testText(_textField4.getText(), 0, 9999))
			return 5;
		if (!testText(_textField5.getText(), 0, 4))
			return 6;
		if (!testText(_textField6.getText(), 1, 125))
			return 7;
		return 0;
	}

	private boolean testText(String str, int inf, int sup) {
		boolean res = false;
		int i;
		if (str == null)
			return res;
		if (str.length() == 0)
			return res;
		try {
			i = Integer.parseInt(str);
		} catch (Exception e) {
			return res;
		}
		if (i >= inf && i <= sup)
			res = true;

		return res;
	}

} /* end class AboutDlg */
