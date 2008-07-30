/**
 *
 * Title: LookAndFeelPrefTest.java
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


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author rgr
 * 
 */
public class LookAndFeelPrefTest extends TestCase implements
		ConstantsForLookAndFeel {

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(LookAndFeelPrefTest.class);
	} // end suite

	public void test_FromXToWindows() {
		LookAndFeelPref dp = new LookAndFeelPref();
		// IMPORTANT
		// the test must be done saving the value in str, changing the look and Feel 
		// then putting back str as preference
		String str = dp.getLookAndFeel();
		assertEquals("test_FromWindowToMetal: assertEquals str", str, dp
				.getLookAndFeel());
		dp.putLookAndFeel(WINDOWS);
		assertEquals("test_FromWindowToMetal: assertEquals WINDOWS", WINDOWS, dp
				.getLookAndFeel());
		dp.putLookAndFeel(str);
		assertEquals("test_FromWindowToMetal: assertEquals str", str, dp
				.getLookAndFeel());
	}
	
	public void test_FromXToMetal() {
		LookAndFeelPref dp = new LookAndFeelPref();
		// IMPORTANT
		// the test must be done saving the value in str, changing the look and Feel 
		// then putting back str as preference
		String str = dp.getLookAndFeel();
		assertEquals("test_FromWindowToMetal: assertEquals str", str, dp
				.getLookAndFeel());
		dp.putLookAndFeel(METAL);
		assertEquals("test_FromWindowToMetal: assertEquals METAL", METAL, dp
				.getLookAndFeel());
		dp.putLookAndFeel(str);
		assertEquals("test_FromWindowToMetal: assertEquals str", str, dp
				.getLookAndFeel());
	}
	
	public void test_FromXToMotif() {
		LookAndFeelPref dp = new LookAndFeelPref();
		// IMPORTANT
		// the test must be done saving the value in str, changing the look and Feel 
		// then putting back str as preference
		String str = dp.getLookAndFeel();
		assertEquals("test_FromWindowToMetal: assertEquals str", str, dp
				.getLookAndFeel());
		dp.putLookAndFeel(MOTIF);
		assertEquals("test_FromWindowToMetal: assertEquals MOTIF", MOTIF, dp
				.getLookAndFeel());
		dp.putLookAndFeel(str);
		assertEquals("test_FromWindowToMetal: assertEquals str", str, dp
				.getLookAndFeel());
	}
	
	public void test_convertIntToExt() {
		LookAndFeelPref dp = new LookAndFeelPref();
		assertEquals("test_convertIntToExt: assertEquals METAL_EXT", METAL_EXT, dp.convertIntToExt(METAL));
		assertEquals("test_convertIntToExt: assertEquals METAL_EXT", MOTIF_EXT, dp.convertIntToExt(MOTIF));
		assertEquals("test_convertIntToExt: assertEquals METAL_EXT", WINDOWS_EXT, dp.convertIntToExt(WINDOWS));
		assertEquals("test_convertIntToExt: assertEquals No Value", NO_VALUE, dp.convertIntToExt("X"));
	}
	
	public void test_convertExtToINT() {
		LookAndFeelPref dp = new LookAndFeelPref();
		assertEquals("test_convertIntToExt: assertEquals METAL_EXT", METAL, dp.convertExtToInt(METAL_EXT));
		assertEquals("test_convertIntToExt: assertEquals METAL_EXT", MOTIF, dp.convertExtToInt(MOTIF_EXT));
		assertEquals("test_convertIntToExt: assertEquals METAL_EXT", WINDOWS, dp.convertExtToInt(WINDOWS_EXT));
		assertEquals("test_convertIntToExt: assertEquals No Value", NO_VALUE, dp.convertExtToInt("X"));
	}
}
