/**
 *
 * Title: ParametersPref.java
 *
 * Description: DApplication is a class used to display the application GUI,
 *              The class creates the main window, and menuBar, and toolBar,
 *              and the logger
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
package ca.sixs.util.pref;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.prefs.Preferences;


import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.dUtil.DxTools;


/**
 * @author rgr
 *
 */
public class ParametersPref implements ConstantsForParameters,  ActionListener {
	
	private JFrame _jFrame;
	
	private JDialog jd;

	private Preferences _pref;
	
	private String[] _buttonsNames = { DConst.BUT_OK };

	private JPanel _buttonsPanel;

	private JTextField _textField0;

	private JTextField _textField1;

	private JTextField _textField2;

	private JTextField _textField3;

	private JTextField _textField4;

	private JTextField _textField5;

	private JTextField _textField6;
	
		
	public ParametersPref() {
		// Retrieve the user preference node for the package com.mycompany
		_pref = Preferences
				.userNodeForPackage(ca.sixs.util.pref.ParametersPref.class);
	}

	
	public int getMaxStuConfictsBetweenTwoEvents() {
		// Get the value of the preference;
		// default value is returned if the preference does not exist
		return _pref.getInt(MAX_STUDENT_CONFLICTS, MAX_STUDENT_CONFLICTS_DEFAULT);
	}

	private void putMaxStuConfictsBetweenTwoEvents(int newValue) {
		// Set the value of the preference
		_pref.putInt(MAX_STUDENT_CONFLICTS, newValue);
	}
	
	public int getMaxInsConfictsBetweenTwoEvents() {
		// Get the value of the preference;
		// default value is returned if the preference does not exist
		return _pref.getInt(MAX_INSTRUCTOR_CONFLICTS, MAX_INSTRUCTOR_CONFLICTS_DEFAULT);
	}

	private void putMaxInsConfictsBetweenTwoEvents(int newValue) {
		// Set the value of the preference
		_pref.putInt(MAX_INSTRUCTOR_CONFLICTS, newValue);
	}
	
	public int getMaxRooConfictsBetweenTwoEvents() {
		// Get the value of the preference;
		// default value is returned if the preference does not exist
		return _pref.getInt(MAX_ROOM_CONFLICTS, MAX_ROOM_CONFLICTS_DEFAULT);
	}

	private void putMaxRooConfictsBetweenTwoEvents(int newValue) {
		// Set the value of the preference
		_pref.putInt(MAX_ROOM_CONFLICTS, newValue);
	}
	

	public int getAllowedPriorityLevel() {
		// Get the value of the preference;
		// default value is returned if the preference does not exist
		return _pref.getInt(PRIORITY_LEVEL, PRIORITY_LEVEL_DEFAULT );
	}

	private void putAllowedPriorityLevel(int newValue) {
		// Set the value of the preference
		_pref.putInt(PRIORITY_LEVEL, newValue);
	}

	public int getMaxEventsInPeriod() {
		// Get the value of the preference;
		// default value is returned if the preference does not exist
		return _pref.getInt(MAX_EVENTS_IN_PERIOD, MAX_EVENTS_IN_PERIOD_DEFAULT);
	}

	private void putMaxEventsInPeriod(int newValue) {
		// Set the value of the preference
		_pref.putInt(MAX_EVENTS_IN_PERIOD, newValue);
	}
	
	public int getMinGapBetweenPeriods() {
		// Get the value of the preference;
		// default value is returned if the preference does not exist
		return _pref.getInt(MIN_GAP_BETWEEN_PERIODS, MIN_GAP_BETWEEN_PERIODS_DEFAULT);
	}

	private void putMinGapBetweenPeriods(int newValue) {
		// Set the value of the preference
		_pref.putInt(MIN_GAP_BETWEEN_PERIODS, newValue);
	}
		
	public int getAllowedRoomBookingRate() {
		// Get the value of the preference;
		// default value is returned if the preference does not exist
		return _pref.getInt(ROOM_BOOK_RATE, ROOM_BOOK_RATE_DEFAULT);
	}

	private void putAllowedRoomBookingRate(int newValue) {
		// Set the value of the preference
		_pref.putInt(ROOM_BOOK_RATE, newValue);
	}



	// private void initialize() {
	// // _dApplic.addPrefsListener(_prefs);
	// _lafList = createsLAFList();
	// _lafList.addActionListener(this);
	// JPanel jp = new JPanel();
	// jp.setLayout(new BorderLayout());
	// jp.add(new JLabel(DConst.PLAF_D), BorderLayout.NORTH);
	// jp.add(new JLabel(" "), BorderLayout.CENTER);
	// jp.add(_lafList, BorderLayout.SOUTH);
	// this.getContentPane().add(jp, BorderLayout.CENTER);
	// } // end initialize()

//	public void actionPerformed(ActionEvent ae) {
//		// String lnfName = "";
//		if (ae.getSource() == _lafList) {
//			int flag = _lafList.getSelectedIndex();
//			if (flag == 0) {
//				// lnfName = METAL;
//				this.putLookAndFeel("a");
//			} else if (flag == 1) {
//				// lnfName = MOTIF;
//				this.putLookAndFeel("b");
//			} else {
//				// lnfName = WINDOWS;
//				this.putLookAndFeel("c");
//			}
//		} // end if
//		SwingUtilities.updateComponentTreeUI(_jFrame);
//		// _dApplic.updateLAF(_dPreferences.get("lookAndFeel", METAL));
//		// try {
//		// rgr System.out.println("prefs: "+ _dPreferences.absolutePath());
//		// rgr _dPreferences.flush();
//		// } catch (BackingStoreException e) {
//		// System.out.println("hayhay "+ "I'm on the catch");
//		// e.printStackTrace();
//		// }
//		// _dApplic.getDxPreferences().setLAFName(lnfName);
//		// _dApplic.getDxPreferences().save();
//		jd.dispose();
//	} // end actionPerformed

//	private JComboBox createsLAFList() {
//		final int numItems = 3;
//		String[] lafTable = new String[numItems];
//		lafTable[0] = "a";
//		lafTable[1] = "b";
//		lafTable[2] = "c";
//		JComboBox lafList = new JComboBox(lafTable);
//
//		lafList.setBorder(BorderFactory.createEtchedBorder());
//
////		String lf = UIManager.getLookAndFeel().getName();
////		// System.out.println("l&A " + lf);
////		if (lf.equals(NAME_METAL)) {
////			lafList.setSelectedIndex(0);
////		} else if (lf.equals(NAME_MOTIF)) {
////			lafList.setSelectedIndex(1);
////		} else if (lf.equals(NAME_WINDOWS)) {
////			lafList.setSelectedIndex(2);
////		}
//		return lafList;
//	} // end createsLAFList



	public void paramChooser(DApplication dApplic) {
		jd = new JDialog(dApplic.getJFrame(), DLG_T_PARAMETERS, true);	
		jd.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//		Dimension dim = new Dimension(600, 300);
//		jd.setSize(dim);
//		jd.setResizable(false);
		_jFrame = dApplic.getJFrame();
		Dimension dim = new Dimension(600, 300);
		jd.setSize(dim);
		jd.setResizable(false);
		_textField0 = new JTextField("" + this.getMaxStuConfictsBetweenTwoEvents());
		_textField1 = new JTextField("" + this.getMaxInsConfictsBetweenTwoEvents());
		_textField2 = new JTextField("" + this.getMaxRooConfictsBetweenTwoEvents());
		_textField3 = new JTextField("" + this.getAllowedPriorityLevel());
		_textField4 = new JTextField("" + this.getMaxEventsInPeriod());
		_textField5 = new JTextField("" + this.getMinGapBetweenPeriods());
		_textField6 = new JTextField("" + this.getAllowedRoomBookingRate());

		JPanel jPanel = new JPanel();
////		initialize();

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

		jd.setLocationRelativeTo(_jFrame);
		jd.setResizable(false);

		_buttonsPanel = DxTools.buttonsPanel(this, _buttonsNames);
		
		jd.getContentPane().add(jPanel, BorderLayout.CENTER);
		jd.getContentPane().add(_buttonsPanel, BorderLayout.SOUTH);
		jd.setVisible(true);
	} // end constructor AboutDlg






	public void actionPerformed(ActionEvent ae) {
		String command = ae.getActionCommand();
		//If buttons CANCEL
		if (command.equals(DConst.BUT_OK)) {
			int ligne = validation();
			if (ligne == 0) {

//					//DxConflictLimits  cl = new DxConflictLimits();
//					StringBuffer strB = new StringBuffer("conflictLimits" + ";");
//					strB.append(_textField0.getText()+ ";");
//					strB.append(_textField1.getText()+ ";");
//					strB.append(_textField2.getText()+ ";");
//					strB.append(_textField3.getText()+ ";");
//					strB.append(_textField4.getText()+ ";");
//					strB.append(_textField5.getText()+ ";");
//					strB.append(_textField6.getText()+ ";");
//					_dApplic.getDxPreferences().setDxConflictLimits(strB.toString());
//					_dApplic.getDxPreferences().save();
				
				this.putMaxStuConfictsBetweenTwoEvents(Integer.parseInt(_textField0.getText()));
				this.putMaxInsConfictsBetweenTwoEvents(Integer.parseInt(_textField1.getText()));
				this.putMaxRooConfictsBetweenTwoEvents(Integer.parseInt(_textField2.getText()));
				this.putAllowedPriorityLevel(Integer.parseInt(_textField3.getText()));
				this.putMaxEventsInPeriod(Integer.parseInt(_textField4.getText()));
				this.putMinGapBetweenPeriods(Integer.parseInt(_textField5.getText()));				
				this.putAllowedRoomBookingRate(Integer.parseInt(_textField6.getText()));
				
					jd.dispose();

			} else
				JOptionPane.showMessageDialog(jd, "Erreur à la ligne: "
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
	
}
