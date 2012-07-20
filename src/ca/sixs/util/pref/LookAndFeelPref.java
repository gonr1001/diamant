/**
 *
 * Title: LookAndFeelPref.java
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

import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import dConstants.DConst;
import eLib.exit.dialog.FatalProblemDlg;

/**
 * @author rgr
 * 
 */

// TODO hide magic strings.
public class LookAndFeelPref implements ConstantsForLookAndFeel {

	private Preferences _pref;

	public LookAndFeelPref() {
		// Retrieve the user preference node for the package com.mycompany
		_pref = Preferences
				.userNodeForPackage(ca.sixs.util.pref.LookAndFeelPref.class);
	}

	public String getLookAndFeel() {
		// Get the value of the preference;
		// default value is returned if the preference does not
		// existutLookAndFeel(METAL);
		return _pref.get(LOOK_AND_FEEL, LOOK_AND_FEEL_DEFAULT_VALUE);
	}

	protected void putLookAndFeel(String newValue) {
		// Set the value of the preference
		_pref.put(LOOK_AND_FEEL, newValue);
	}

	public void setLookAndFeel() {
		String str = this.getLookAndFeel();
		try {
			//was UIManager.setLookAndFeel(str);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			System.out.println("pref: " + str);
		} catch (UnsupportedLookAndFeelException ulafe) {
			new FatalProblemDlg("UnsupportedLookAndFeel: " + str);
			System.err.println("Warning: UnsupportedLookAndFeel: " + str);
			ulafe.printStackTrace();
			System.exit(1);
		} catch (ClassNotFoundException cnfe) {
			new FatalProblemDlg("Error ClassNotFound LookAndFeel" + str);
			System.err.println("Error ClassNotFound LookAndFeel" + str);
			cnfe.printStackTrace();
			System.exit(1);
		} catch (IllegalAccessException iace) {
			new FatalProblemDlg("Error IllegalAccess LookAndFeel" + str);
			System.err.println("Error IllegalAccess LookAndFeel" + str);
			iace.printStackTrace();
			System.exit(1);
		} catch (InstantiationException ie) {
			new FatalProblemDlg("Error Instantiation LookAndFeel" + str);
			System.err.println("Error Instantiation LookAndFeel" + str);
			ie.printStackTrace();
			System.exit(1);
		}
	} // end setLookAndFeel

	public void lafChooser(JFrame jFrame) {
		String[] possibilities = { METAL_EXT, MOTIF_EXT, WINDOWS_EXT };
		String current = getLookAndFeel();
		
		String str = (String) JOptionPane.showInputDialog(jFrame,
				DConst.PLAF_D, DConst.PLAF_TD, JOptionPane.PLAIN_MESSAGE, /* icon */
				null, possibilities, convertIntToExt(current));
		
		if ((str != null) && (str.length() > 0)) {
			String choice = convertExtToInt(str);
			this.putLookAndFeel(choice);
			this.setLookAndFeel();
			SwingUtilities.updateComponentTreeUI(jFrame);
		}
	}

	/**
	 * @param str
	 * @return
	 */
	protected String convertIntToExt(String str) {
		if (str.compareTo(METAL) == 0) {
			return METAL_EXT;
		}
		if (str.compareTo(MOTIF) == 0) {
			return MOTIF_EXT;
		}
		if (str.compareTo(WINDOWS) == 0) {
			return WINDOWS_EXT;
		}
		return NO_VALUE;

	}

	/**
	 * @param str
	 * @return
	 */
	protected String convertExtToInt(String str) {
		if (str.compareTo(METAL_EXT) == 0) {
			return METAL;
		}
		if (str.compareTo(MOTIF_EXT) == 0) {
			return MOTIF;
		}
		if (str.compareTo(WINDOWS_EXT) == 0) {
			return WINDOWS;
		}
		return NO_VALUE;
	}
}
