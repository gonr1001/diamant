/**
 *
 * Title: FullReportPrefTest.java
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

import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author rgr
 *
 */
public class ReportPrefTest extends TestCase implements
		ConstantsForReports {

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(LookAndFeelPrefTest.class);
	} // end suite

	public void test_Values() {
		ReportPref rp = new ReportPref();
		Vector <String> aux = rp.getSelectedOptionsForFullReport();
		assertEquals("test_Values: aux.size()",
				aux.size(), 
				(rp.getSelectedOptionsForFullReport()).size());
//		dp.putLookAndFeel(WINDOWS);
//		assertEquals("test_lookAndFeel: assertEquals WINDOWS", WINDOWS, dp
//				.getLookAndFeel());
//		dp.putLookAndFeel(str);
//		assertEquals("test_lookAndFeel: assertEquals METAL", str, dp
//				.getLookAndFeel());
	}	

	
}
