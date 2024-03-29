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
public class ParametersPref implements ConstantsForParameters, ActionListener {

	private JDialog jd;

	private Preferences _pref;

	private String[] _buttonsNames;

	private JPanel _buttonsPanel;

	private JTextField _maxStuConflictsBetweenTwoEventsJTF;

	private int _maxStuConflictsBetweenTwoEvents;

	private JTextField _maxInsConflictsBetweenTwoEventsJTF;

	private int _maxInsConflictsBetweenTwoEvents;

	private JTextField _maxRooConflictsBetweenTwoEventsJTF;
	
	private int _maxRooConflictsBetweenTwoEvents;

	private JTextField _allowedPriorityLevelJTF;
	
	private int _allowedPriorityLevel;

	private JTextField _maxEventsInPeriodJTF;
	
	private int _maxEventsInPeriod;

	private JTextField _minGapBetweenPeriodsJTF;
	
	private int _minGapBetweenPeriods;

	private JTextField _allowedRoomBookingRateJTF;
	
	private int _allowedRoomBookingRate;
	

	public ParametersPref() {
		// Retrieve the user preference node for the package com.mycompany
		_pref = Preferences
				.userNodeForPackage(ca.sixs.util.pref.ParametersPref.class);
	}

	public int getMaxStuConfictsBetweenTwoEvents() {
		// Get the value of the preference;
		// default value is returned if the preference does not exist
		return _pref.getInt(MAX_STUDENT_CONFLICTS,
				MAX_STUDENT_CONFLICTS_DEFAULT);
	}

	protected void putMaxStuConfictsBetweenTwoEvents(int newValue) {
		// Set the value of the preference
		_pref.putInt(MAX_STUDENT_CONFLICTS, newValue);
	}

	public int getMaxInsConfictsBetweenTwoEvents() {
		// Get the value of the preference;
		// default value is returned if the preference does not exist
		return _pref.getInt(MAX_INSTRUCTOR_CONFLICTS,
				MAX_INSTRUCTOR_CONFLICTS_DEFAULT);
	}

	protected void putMaxInsConfictsBetweenTwoEvents(int newValue) {
		// Set the value of the preference
		_pref.putInt(MAX_INSTRUCTOR_CONFLICTS, newValue);
	}

	public int getMaxRooConfictsBetweenTwoEvents() {
		// Get the value of the preference;
		// default value is returned if the preference does not exist
		return _pref.getInt(MAX_ROOM_CONFLICTS, MAX_ROOM_CONFLICTS_DEFAULT);
	}

	protected void putMaxRooConfictsBetweenTwoEvents(int newValue) {
		// Set the value of the preference
		_pref.putInt(MAX_ROOM_CONFLICTS, newValue);
	}

	public int getAllowedPriorityLevel() {
		// Get the value of the preference;
		// default value is returned if the preference does not exist
		return _pref.getInt(PRIORITY_LEVEL, PRIORITY_LEVEL_DEFAULT);
	}

	protected void putAllowedPriorityLevel(int newValue) {
		// Set the value of the preference
		_pref.putInt(PRIORITY_LEVEL, newValue);
	}

	public int getMaxEventsInPeriod() {
		// Get the value of the preference;
		// default value is returned if the preference does not exist
		return _pref.getInt(MAX_EVENTS_IN_PERIOD, MAX_EVENTS_IN_PERIOD_DEFAULT);
	}

	protected void putMaxEventsInPeriod(int newValue) {
		// Set the value of the preference
		_pref.putInt(MAX_EVENTS_IN_PERIOD, newValue);
	}

	public int getMinGapBetweenPeriods() {
		// Get the value of the preference;
		// default value is returned if the preference does not exist
		return _pref.getInt(MIN_GAP_BETWEEN_PERIODS,
				MIN_GAP_BETWEEN_PERIODS_DEFAULT);
	}

	protected void putMinGapBetweenPeriods(int newValue) {
		// Set the value of the preference
		_pref.putInt(MIN_GAP_BETWEEN_PERIODS, newValue);
	}

	public int getAllowedRoomBookingRate() {
		// Get the value of the preference;
		// default value is returned if the preference does not exist
		return _pref.getInt(ROOM_BOOK_RATE, ROOM_BOOK_RATE_DEFAULT);
	}

	protected void putAllowedRoomBookingRate(int newValue) {
		// Set the value of the preference
		_pref.putInt(ROOM_BOOK_RATE, newValue);
	}

	public void paramChooser(DApplication dApplic) {
		jd = new JDialog(dApplic.getJFrame(), DLG_T_PARAMETERS, true);
		jd.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		Dimension dim = new Dimension(600, 300);
		jd.setSize(dim);
		_buttonsNames = new String[1];
		_buttonsNames[0] = DConst.BUT_OK;
		_maxStuConflictsBetweenTwoEventsJTF = new JTextField(""
				+ this.getMaxStuConfictsBetweenTwoEvents());
		_maxInsConflictsBetweenTwoEventsJTF = new JTextField(""
				+ this.getMaxInsConfictsBetweenTwoEvents());
		_maxRooConflictsBetweenTwoEventsJTF = new JTextField(""
				+ this.getMaxRooConfictsBetweenTwoEvents());
		_allowedPriorityLevelJTF = new JTextField(""
				+ this.getAllowedPriorityLevel());
		_maxEventsInPeriodJTF = new JTextField("" + this.getMaxEventsInPeriod());
		_minGapBetweenPeriodsJTF = new JTextField(""
				+ this.getMinGapBetweenPeriods());
		_allowedRoomBookingRateJTF = new JTextField(""
				+ this.getAllowedRoomBookingRate());

		JPanel jPanel = new JPanel();
		jPanel.setLayout(new GridLayout(7, 2));
		jPanel.add(new JLabel("Max Conflits �tu entre 2 Eve ="));
		jPanel.add(_maxStuConflictsBetweenTwoEventsJTF);

		jPanel.add(new JLabel("Max Conflits Ens entre 2 Eve ="));
		jPanel.add(_maxInsConflictsBetweenTwoEventsJTF);

		jPanel.add(new JLabel("Max Conflits Loc entre 2 Eve ="));
		jPanel.add(_maxRooConflictsBetweenTwoEventsJTF);

		jPanel.add(new JLabel(
				"Placer dans p�riodes (0=Normale, 1= Norm et Bas)"));
		jPanel.add(_allowedPriorityLevelJTF);

		jPanel.add(new JLabel("Nombre Max de cours dans une p�riode ="));
		jPanel.add(_maxEventsInPeriodJTF);

		jPanel.add(new JLabel("P�riodes d'�cart ="));
		jPanel.add(_minGapBetweenPeriodsJTF);

		jPanel.add(new JLabel("Taux de remplissage des locaux (en %) ="));
		jPanel.add(_allowedRoomBookingRateJTF);

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
			int ligne = validation();
			if (ligne == 0) {
				this
						.putMaxStuConfictsBetweenTwoEvents(Integer
								.parseInt(_maxStuConflictsBetweenTwoEventsJTF
										.getText()));
				this
						.putMaxInsConfictsBetweenTwoEvents(Integer
								.parseInt(_maxInsConflictsBetweenTwoEventsJTF
										.getText()));
				this
						.putMaxRooConfictsBetweenTwoEvents(Integer
								.parseInt(_maxRooConflictsBetweenTwoEventsJTF
										.getText()));
				this.putAllowedPriorityLevel(Integer
						.parseInt(_allowedPriorityLevelJTF.getText()));
				this.putMaxEventsInPeriod(Integer
						.parseInt(_maxEventsInPeriodJTF.getText()));
				this.putMinGapBetweenPeriods(Integer
						.parseInt(_minGapBetweenPeriodsJTF.getText()));
				this.putAllowedRoomBookingRate(Integer
						.parseInt(_allowedRoomBookingRateJTF.getText()));
				jd.dispose();
			} else {
				JOptionPane
						.showMessageDialog(jd, "Erreur � la ligne: " + ligne);
			}
		} // end if
	} // end actionPerformed

	private int validation() {
		if (!testText(_maxStuConflictsBetweenTwoEventsJTF.getText(), LOW_LIMIT,
				HIGH_LIMIT))
			return 1;
		if (!testText(_maxInsConflictsBetweenTwoEventsJTF.getText(), LOW_LIMIT,
				HIGH_LIMIT))
			return 2;
		if (!testText(_maxRooConflictsBetweenTwoEventsJTF.getText(), LOW_LIMIT,
				HIGH_LIMIT))
			return 3;
		if (!testText(_allowedPriorityLevelJTF.getText(), LOW_LIMIT,
				HIGH_PRIORITY_LIMIT))
			return 4;
		if (!testText(_maxEventsInPeriodJTF.getText(), LOW_LIMIT, HIGH_LIMIT))
			return 5;
		if (!testText(_minGapBetweenPeriodsJTF.getText(), LOW_LIMIT,
				HIGH_GAP_LIMIT))
			return 6;
		if (!testText(_allowedRoomBookingRateJTF.getText(),
				LOW_BOOK_RATE_LIMIT, HIGH_BOOK_RATE_LIMIT))
			return 7;
		return 0;
	}

	protected boolean testText(String str, int inf, int sup) {
		int i;
		if (str == null || str.length() == 0) {
			return false;
		}
		try {
			i = Integer.parseInt(str);
		} catch (Exception e) {
			return false;
		}
		if (i >= inf && i <= sup) {
			return true;
		}
		return false;
	}

	public void savePrefBeforeTest() {
		_maxStuConflictsBetweenTwoEvents = getMaxStuConfictsBetweenTwoEvents();
		putMaxStuConfictsBetweenTwoEvents(0);
		_maxInsConflictsBetweenTwoEvents = getMaxInsConfictsBetweenTwoEvents();
		putMaxInsConfictsBetweenTwoEvents(0);
		_maxRooConflictsBetweenTwoEvents = getMaxRooConfictsBetweenTwoEvents();
		putMaxRooConfictsBetweenTwoEvents(0);
		_allowedPriorityLevel = getAllowedPriorityLevel();
		putAllowedPriorityLevel(0);
		_maxEventsInPeriod = getMaxEventsInPeriod();
		putMaxEventsInPeriod(30);
		_minGapBetweenPeriods = getMinGapBetweenPeriods();
		putMinGapBetweenPeriods(0);		
		_allowedRoomBookingRate = getAllowedRoomBookingRate();
		putAllowedRoomBookingRate(100);

		System.out.println(_maxStuConflictsBetweenTwoEvents + "," +
		_maxInsConflictsBetweenTwoEvents + "," +
		_maxRooConflictsBetweenTwoEvents + "," +
		_allowedPriorityLevel + "," +
		_maxEventsInPeriod + "," +
		_minGapBetweenPeriods + "," +		
		_allowedRoomBookingRate  + "," );
	}

	public void restorePrefAfterTest() {
		putMaxStuConfictsBetweenTwoEvents(_maxStuConflictsBetweenTwoEvents);
		putMaxInsConfictsBetweenTwoEvents(_maxInsConflictsBetweenTwoEvents);
		putMaxRooConfictsBetweenTwoEvents(_maxRooConflictsBetweenTwoEvents);
		putAllowedPriorityLevel(_allowedPriorityLevel);
		putMaxEventsInPeriod(_maxEventsInPeriod);
		putMinGapBetweenPeriods(_minGapBetweenPeriods);		
		putAllowedRoomBookingRate(_allowedRoomBookingRate);			
	}

}
