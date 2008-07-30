////package ca.sixs.util.pref;
//
//import java.util.prefs.BackingStoreException;
//import java.util.prefs.Preferences;
//
//
//
//public class DPreferences implements ConstantsForLookAndFeel {
//
//	Preferences _prefs;
//	
//	// Preference key name and default value
//	
//	final String LOOK_AND_FEEL = "lookAndFeel";
//
//	final String LOOK_AND_FEEL_DEFAULT_VALUE = METAL;
//
//	final String LANGUAGE = "language";
//
//	final String LANGUAGE_DEFAULT_VALUE = "English";
//
//
//	public String _defaultDir;
//
//	public String _originalFullFileName;
//
//	public String _acceptedChars;
//
//	public String _selectedOptionsInFullReport;
//
//	public String _selectedOptionsInConflictReport;
//
//	public String _conflictLimits;
//
//
//	// singleton: it has only one instance
//	private static int instanceNumber = 0;
//
//	private static DPreferences _instance = null;
//
//	// DApplication is a singleton
//	public static DPreferences getInstance() {
//		if (instanceNumber == 0) {
//			instanceNumber++;
//			_instance = new DPreferences();
//		}
//		return _instance;
//	}
//
//	public DPreferences() {
//		// Retrieve the user preference node for the package com.mycompany
//		_prefs = Preferences
//				.userNodeForPackage(ca.sixs.util.pref.DPreferences.class);
//	}
//
//	public String getLookAndFeel() {
//		// Get the value of the preference;
//		// default value is returned if the preference does not exist
//		return _prefs.get(LOOK_AND_FEEL, LOOK_AND_FEEL_DEFAULT_VALUE);
//	}
//
//	public void putLookAndFeel(String newValue) {
//		// Set the value of the preference
//		_prefs.put(LOOK_AND_FEEL, newValue);
//	}
//
//	public String getLanguage() {
//		// Get the value of the preference;
//		// default value is returned if the preference does not exist
//		return _prefs.get(LANGUAGE, LANGUAGE_DEFAULT_VALUE);
//	}
//
//	public void putLanguage(String newValue) {
//		// Set the value of the preference
//		_prefs.put(LANGUAGE, newValue);
//	}
//
//	public void clear()  {
//		try {
//			_prefs.clear();
//		} catch (BackingStoreException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//}
