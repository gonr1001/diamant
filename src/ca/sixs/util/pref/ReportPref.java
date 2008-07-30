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
				.userNodeForPackage(ca.sixs.util.pref.ParametersPref.class);
	}

	public String getSelectedPrefInFullReport() {
		// Get the value of the preference;
		// default value is returned if the preference does not exist
		return _pref
				.get("selectedOptionsInFullReport",
						"Acti;Nat;Grp;Blc;Durée;JNum;Jour;Début;Fin;Nomb É;Enseignant;Local;");
	}

	private void putSelectedOptionsInFullReport(String newValue) {
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

	private void putSelectedOptionsInConflicttReport(String newValue) {
		// Set the value of the preference
		_pref.put("OptionsInConflictReport", newValue);
	}

	public Vector<String> getSelectedOptionsForFullReport() {
		StringTokenizer st = new StringTokenizer(getSelectedPrefInFullReport(),
				";");
		// String s = "selectedOptionsInFullReport";
		Vector<String> res = new Vector<String>();
		// if (st.nextToken().equals(s)) {
		while (st.countTokens() > 0) {
			res.add(st.nextToken());
		}
		// } else {
		// System.out
		// .println("Preferences.getSelectedOptionsFullReport  Help");
		// }
		return res;
	} // end getSelectedOptionsInFullReport()

	public Vector<String> getSelectedOptionsForConflictReport() {
		StringTokenizer st = new StringTokenizer(
				getSelectedPrefInConflicttReport(), ";");
		// String s = "selectedOptionsInFullReport";
		Vector<String> res = new Vector<String>();
		// if (st.nextToken().equals(s)) {
		while (st.countTokens() > 0) {
			res.add(st.nextToken());
		}
		// } else {
		// System.out
		// .println("Preferences.getSelectedOptionsFullReport  Help");
		// }
		return res;
	} // end getSelectedOptionsInFullReport()

	/**
	 * @param rigth
	 */
	public void saveSelectedOptionsInConflictReport(Vector<String> rigth) {
		String str = "";
		for (int i = 0; i < rigth.size(); i++) {
			str += rigth.elementAt(i);
			str += ";";
		}
		this.putSelectedOptionsInConflicttReport(str);
	}
	
	/**
	 * @param rigth
	 */
	public void saveSelectedOptionsInFullReport(Vector<String> rigth) {
		String str = "";
		for (int i = 0; i < rigth.size(); i++) {
			str += rigth.elementAt(i);
			str += ";";
		}
		this.putSelectedOptionsInFullReport(str);
	}
}