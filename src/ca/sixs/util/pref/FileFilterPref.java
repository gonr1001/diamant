package ca.sixs.util.pref;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.dUtil.DxTools;

public class FileFilterPref implements ConstantsForFileFilter, ActionListener {

	private JDialog jd;

	private Preferences _pref;

	private String[] _buttonsNames ;

	private JPanel _buttonsPanel;

	private JTextField _fileFiterChars;


	public FileFilterPref() {
		// Retrieve the user preference node for the package com.mycompany
		_pref = Preferences
				.userNodeForPackage(ca.sixs.util.pref.FileFilterPref.class);
	}

	public String getFileFilterChars() {
		// Get the value of the preference;
		// default value is returned if the preference does not exist
		return _pref.get(FILE_FILTER,
				FILE_FILTER_DEFAULT_VALUE);
	}

	protected void putFileFilterChars(String newValue) {
		// Set the value of the preference
		_pref.put(FILE_FILTER, newValue);
	}



	public void paramChooser(DApplication dApplic) {
		jd = new JDialog(dApplic.getJFrame(), DLG_T_FILE_FILTER, true);
		jd.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		Dimension dim = new Dimension(600, 300);
		jd.setSize(dim);
		_buttonsNames [0] = DConst.BUT_OK ;
		_fileFiterChars = new JTextField(""
				+ this.getFileFilterChars());


		JPanel jPanel = new JPanel();
		jPanel.setLayout(new GridLayout(1, 2));
		jPanel.add(new JLabel("Allowed characters"));
		jPanel.add(_fileFiterChars);


		jd.setLocationRelativeTo(dApplic.getJFrame());
		jd.setResizable(false);

		_buttonsPanel = DxTools.buttonsPanel(this, _buttonsNames);

		jd.getContentPane().add(jPanel, BorderLayout.CENTER);
		jd.getContentPane().add(_buttonsPanel, BorderLayout.SOUTH);
		jd.setVisible(true);
	} // end constructor AboutDlg

	public void actionPerformed(ActionEvent ae) {
		String command = ae.getActionCommand();
		if (command.equals(DConst.BUT_OK)) {
			if (!_fileFiterChars.getText().isEmpty()) {
				this.putFileFilterChars(_fileFiterChars.getText());
				jd.dispose();
			} else {
				JOptionPane
						.showMessageDialog(jd, "Erreur à la ligne: " + 0);
			}
		} // end if
	} // end actionPerformed


}