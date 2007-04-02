/**
 * Created on Jun 29, 2006
 * 
 * 
 * Title: APeriodTest.java 
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
package dTest.dInternal.dTimeTable;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInternal.dTimeTable.APeriod;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: APeriodTest is a class used to:
 * <p>
 * TODO:insert comments
 * <p> 
 * 
 */
public class APeriodTest extends TestCase {
	
	public APeriodTest(String name) {
		super(name);
		}
	
	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(APeriodTest.class);
	} // end suite
	
	public void testPeriodEqual() {
		System.out.println("-- Begin Test APeriod ----");
		APeriod periodUn = new APeriod();
		APeriod periodDeux = new APeriod();
		assertTrue(periodDeux.isEquals(periodUn));
		System.out.println(periodDeux.isEquals(periodDeux));
		System.out.println("-- Deux Periods equales ----");
		
	}

	public void testPeriodNotEqual() {
		APeriod periodUn = new APeriod();
		APeriod periodDeux = new APeriod();
		periodDeux.setBeginTime(10, 10);
		assertFalse(periodDeux.isEquals(periodUn));
		System.out.println(periodDeux.isEquals(periodDeux));
		System.out.println("-- Deux Periods non equales verification----");
		System.out.println("-- affichage premiere periode ----");
		System.out.println(periodUn.toString());
		System.out.println("-- affichage deuxieme periode ----");
		System.out.println(periodDeux.toString());
		System.out.println("-- End Test testPeriodNotEqual ----");
	}
	
	public void CreerAfficherPeriod() {
		APeriod unePeriode = new APeriod();
		unePeriode.setPeriodId(1);
		unePeriode.setBeginTime(10, 10);
		unePeriode.setEndTime(20, 20);
		unePeriode.setPriority(3);
		System.out.println(unePeriode.toString());
		System.out.println("-- End Test APeriod ----");
	}
	
}
