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
		return new TestSuite(ReportPrefTest.class);
	} // end suite

	
	public void test_SelectedOptionsForFullReport() {
		ReportPref rp = new ReportPref();
		Vector <String> aux = rp.getSelectedOptionsForFullReport();
		assertEquals("test_Size: aux.size()",
				aux.size(), 
				(rp.getSelectedOptionsForFullReport()).size());
		rp.putSelectedOptionsInFullReport("Acti;Nat;Grp;Blc;Durée;JNum;Jour;Début;Fin;Nomb É;Enseignant;Local;");
		Vector <String> test1 = rp.getSelectedOptionsForFullReport();
		assertEquals("testupdate", "Acti;Nat;Grp;Blc;Durée;JNum;Jour;Début;Fin;Nomb É;Enseignant;Local;", rp.convertVectorToString(test1) ); 
		Vector<String> vec = rp.convertStringToVector("Acti;Nat;Grp;Blc;Durée;JNum;Jour;Début;Fin;Nomb É;Enseignant;Local;");
		rp.saveSelectedOptionsInFullReport(vec);
		Vector <String> test2 = rp.getSelectedOptionsForFullReport();
		assertEquals("testupdate", "Acti;Nat;Grp;Blc;Durée;JNum;Jour;Début;Fin;Nomb É;Enseignant;Local;", rp.convertVectorToString(test2) ); 	
	}
	
	
	public void test_SelectedOptionsForConflictReport() {
		ReportPref rp = new ReportPref();
		Vector <String> aux = rp.getSelectedOptionsForConflictReport();
		assertEquals("test_Size: aux.size()",
				aux.size(), 
				(rp.getSelectedOptionsForConflictReport()).size());
		rp.putSelectedOptionsInConflicttReport("Jour;Type Conf;Début;Numb C;Événement 1;Événement 2;Conflits;PérJour;");
		Vector <String> test1 = rp.getSelectedOptionsForConflictReport();
		assertEquals("testupdate","Jour;Type Conf;Début;Numb C;Événement 1;Événement 2;Conflits;PérJour;", rp.convertVectorToString(test1) ); 
		Vector<String> vec = rp.convertStringToVector("Jour;Type Conf;Début;Numb C;Événement 1;Événement 2;Conflits;PérJour;");
		rp.saveSelectedOptionsInConflictReport(vec);
		Vector <String> test2 = rp.getSelectedOptionsForConflictReport();		
		assertEquals("test", "Jour;Type Conf;Début;Numb C;Événement 1;Événement 2;Conflits;PérJour;", rp.convertVectorToString(test2) ); 		
	}
	
}
