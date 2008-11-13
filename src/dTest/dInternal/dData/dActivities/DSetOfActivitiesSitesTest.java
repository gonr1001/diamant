/**
 * Created on 29 nov. 2004
 * 
 * 
 * Title: DSetOfActivitiesSitesTest 
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
package dTest.dInternal.dData.dActivities;

import dInternal.DResource;
import dInternal.DxLoadData;
import dInternal.dData.dActivities.Activity;
import dInternal.dData.dActivities.SetOfActivities;
import dInternal.dData.dActivities.SetOfActivitiesSites;
import dInternal.dData.dActivities.Type;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DSetOfActivitiesSitesTest extends TestCase {

	public DSetOfActivitiesSitesTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DSetOfActivitiesSitesTest.class);
	} // end suite

	/**
	 * test_buildSetOfResources1_5, test that all elements of the activity are added
	 * in the activities file
	 * */
	public void test_buildSetOfResources1_5() {
		String tokens = "ADM1111  01" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "LUC LAJOIE" + "\r\n" + "1" + "\r\n" + "3" + "\r\n" + "1 12"
				+ "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n" + "0" + "\r\n"
				+ "0" + "\r\n" + "0" + "\r\n" + "ADM1111  02" + "\r\n" + "1"
				+ "\r\n" + "1" + "\r\n" + "RÉAL CAOUETTE" + "\r\n" + "1"
				+ "\r\n" + "3" + "\r\n" + "1 12" + "\r\n" + "1" + "\r\n"
				+ "C1-387" + "\r\n" + "0" + "\r\n" + "0" + "\r\n" + "0"
				+ "\r\n" + "ADM1112  01" + "\r\n" + "1" + "\r\n" + "1" + "\r\n"
				+ "Yannick" + "\r\n" + "1" + "\r\n" + "3" + "\r\n" + "1 12"
				+ "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n" + "0" + "\r\n"
				+ "0" + "\r\n" + "0" + "\r\n" + "GEI4411  01" + "\r\n" + "1"
				+ "\r\n" + "1" + "\r\n" + "Ruben" + "\r\n" + "2" + "\r\n"
				+ "3 2" + "\r\n" + "1 12 2 2" + "\r\n" + "1 1" + "\r\n"
				+ "C1-387 C1-330" + "\r\n" + "0 0" + "\r\n" + "0 0" + "\r\n"
				+ "0 0" + "\r\n";

		SetOfActivitiesSites setOfsites = new SetOfActivitiesSites(false, 60);
		DxLoadData ld = new DxLoadData();
		setOfsites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 1);
		if (setOfsites.getError().length() == 0) {
			setOfsites.buildSetOfResources(ld.buildDataExchange(tokens
					.getBytes()), 1);
		}
		assertEquals("test0_buildSetOfResources1_5: assertEquals 0", 1,
				setOfsites.size());
		DResource siteResc = setOfsites.getResource("SHE");
		assertEquals("test1_buildSetOfResources1_5: assertEquals 1", 1,
				siteResc.getKey());
		DResource activityResc = ((SetOfActivities) siteResc.getAttach())
				.getResource("ADM111");
		assertEquals("test2_buildSetOfResources1_5: assertEquals 1", 1,
				activityResc.getKey());
		assertEquals("test3_buildSetOfResources1_5: assertEquals 2", 2,
				((Activity) activityResc.getAttach()).getSetOfTypes().size());
		DResource typeResc = ((Activity) activityResc.getAttach())
				.getSetOfTypes().getResource("1");
		assertEquals("test4_buildSetOfResources1_5: assertEquals 3", 2,
				((Type) typeResc.getAttach()).getSetOfSections().size());
	}

	/**
	 * test_buildSetOfResources1_6, test that all elements of the activity are added
	 * in the activities file
	 * */
	public void test_buildSetOfResources1_6() {
		String tokens = "Diamant1.6" + "\r\n" + "ADM1111  01 SHE 50" + "\r\n"
				+ "1" + "\r\n" + "1" + "\r\n" + "LUC LAJOIE" + "\r\n" + "1"
				+ "\r\n" + "3" + "\r\n" + "1 12" + "\r\n" + "1" + "\r\n"
				+ "C1-387" + "\r\n" + "0" + "\r\n" + "0" + "\r\n" + "0"
				+ "\r\n" + "ADM1111  02 SHE 99999" + "\r\n" + "1" + "\r\n"
				+ "1" + "\r\n" + "RÉAL CAOUETTE" + "\r\n" + "1" + "\r\n" + "3"
				+ "\r\n" + "1 12" + "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n"
				+ "0" + "\r\n" + "0" + "\r\n" + "0" + "\r\n"
				+ "ADM1112  01 SHE 99999" + "\r\n" + "1" + "\r\n" + "1"
				+ "\r\n" + "Yannick" + "\r\n" + "1" + "\r\n" + "3" + "\r\n"
				+ "1 12" + "\r\n" + "1" + "\r\n" + "C1-387" + "\r\n" + "0"
				+ "\r\n" + "0" + "\r\n" + "0" + "\r\n"
				+ "GEI4411  01 SHE 99999" + "\r\n" + "1" + "\r\n" + "1"
				+ "\r\n" + "Ruben" + "\r\n" + "2" + "\r\n" + "3 2" + "\r\n"
				+ "1 12 2 2" + "\r\n" + "1 1" + "\r\n" + "C1-387 C1-330"
				+ "\r\n" + "0 0" + "\r\n" + "0 0" + "\r\n" + "0 0" + "\r\n";

		SetOfActivitiesSites setOfsites = new SetOfActivitiesSites(false, 60);
		DxLoadData ld = new DxLoadData();
		setOfsites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 1);
		if (setOfsites.getError().length() == 0) {
			setOfsites.buildSetOfResources(ld.buildDataExchange(tokens
					.getBytes()), 1);
		}
		assertEquals("test0_buildSetOfResources1_6: assertEquals 0", 1,
				setOfsites.size());
		DResource siteResc = setOfsites.getResource("SHE");
		assertEquals("test1_buildSetOfResources1_6: assertEquals 1", 1,
				siteResc.getKey());
		DResource activityResc = ((SetOfActivities) siteResc.getAttach())
				.getResource("ADM111");
		assertEquals("test2_buildSetOfResources1_6: assertEquals 1", 1,
				activityResc.getKey());
		assertEquals("test3_buildSetOfResources1_6: assertEquals 2", 2,
				((Activity) activityResc.getAttach()).getSetOfTypes().size());
		DResource typeResc = ((Activity) activityResc.getAttach())
				.getSetOfTypes().getResource("1");
		assertEquals("test4_buildSetOfResources1_6: assertEquals 3", 2,
				((Type) typeResc.getAttach()).getSetOfSections().size());
		
	}

}
