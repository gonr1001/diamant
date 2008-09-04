/**
 *
 * Title: FullReportPref.java
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

import java.util.StringTokenizer;
import java.util.Vector;
import java.util.prefs.Preferences;

/**
 * @author rgr
 * 
 */
public class ReportPref {

	private Preferences _pref;

	public ReportPref() {
		// Retrieve the user preference node for the package com.mycompany
		_pref = Preferences
				.userNodeForPackage(ca.sixs.util.pref.ReportPref.class);
	}

	public String getSelectedPrefInFullReport() {
		// Get the value of the preference;
		// default value is returned if the preference does not exist
		return _pref
				.get("selectedOptionsInFullReport",
						"Acti;Nat;Grp;Blc;Durée;JNum;Jour;Début;Fin;Nomb É;Enseignant;Local;");
	}

	protected void putSelectedOptionsInFullReport(String newValue) {
		// Set the value of the preference
		_pref.put("selectedOptionsInFullReport", newValue);
	}

	public String getSelectedPrefInConflicttReport() {
		// Get the value of the preference;
		// default value is returned if the preference does not exist
		return _pref
				.get("OptionsInConflictReport",
						"Jour;Type Conf;Début;Numb C;Événement 1;Événement 2;Conflits;PérJour;");
	}

	protected void putSelectedOptionsInConflicttReport(String newValue) {
		// Set the value of the preference
		_pref.put("OptionsInConflictReport", newValue);
	}

	public Vector<String> getSelectedOptionsForFullReport() {
		StringTokenizer st = new StringTokenizer(getSelectedPrefInFullReport(),
				";");
		Vector<String> res = new Vector<String>();

		while (st.countTokens() > 0) {
			res.add(st.nextToken());
		}
		return res;
	} // end getSelectedOptionsInFullReport()

	public Vector<String> getSelectedOptionsForConflictReport() {
		StringTokenizer st = new StringTokenizer(
				getSelectedPrefInConflicttReport(), ";");
		Vector<String> res = new Vector<String>();
		while (st.countTokens() > 0) {
			res.add(st.nextToken());
		}
		return res;
	} // end getSelectedOptionsInFullReport()

	/**
	 * @param rigth
	 */
	public void saveSelectedOptionsInConflictReport(Vector<String> vec) {
		String str = convertVectorToString(vec);
		this.putSelectedOptionsInConflicttReport(str);
	}

	/**
	 * @param rigth
	 */
	public void saveSelectedOptionsInFullReport(Vector<String> vec) {
		String str = convertVectorToString(vec);
		this.putSelectedOptionsInFullReport(str);
	}

	/**
	 * @param vec
	 * @return
	 */
	protected String convertVectorToString(Vector<String> vec) {
		String str = "";
		for (int i = 0; i < vec.size(); i++) {
			str += vec.elementAt(i);
			str += ";";
		}
		return str;
	}

	protected Vector<String> convertStringToVector(String str) {
		Vector<String> vec = new Vector<String>();
		StringTokenizer st = new StringTokenizer(str, ";");
		while (st.hasMoreTokens()) {
			vec.add(st.nextToken());
		}
		return vec;
	}
}