///**
// * Created on Jun 29, 2006
// * 
// * 
// * Title: ASequenceTest.java 
// *
// * Copyright (c) 2001 by rgr.
// * All rights reserved.
// *
// *
// * This software is the confidential and proprietary information
// * of rgr. ("Confidential Information").  You
// * shall not disclose such Confidential Information and shall use
// * it only in accordance with the terms of the license agreement
// * you entered into with rgr.
// *
// * 
// * 
// */
//package dTest.dInternal.dTimeTable;
//
//import java.util.Vector;
//
//import junit.framework.Test;
//import junit.framework.TestCase;
//import junit.framework.TestSuite;
//import dInternal.dTimeTable.APeriod;
//import dInternal.dTimeTable.ASequence;
//
///**
// * Ruben Gonzalez-Rubio
// * 
// * Description: ASequenceTest is a class used to:
// * <p>
// * TODO:insert comments
// * <p> 
// * 
// */
//public class ASequenceTest extends TestCase {
//
//	public ASequenceTest(String name) {
//		super(name);
//	}
//
//	public static Test suite() {
//		// the type safe way is in SimpleTest
//		// the dynamic way :
//		return new TestSuite(ASequenceTest.class);
//	} // end suite
//
//	public void testSequenceEqual() {
//		System.out.println("-- Begin Test ASequence ----");
//		ASequence sequenceUn = new ASequence();
//		ASequence sequenceDeux = new ASequence();
//		assertTrue(sequenceDeux.isEquals(sequenceUn));
//		System.out.println(sequenceDeux.isEquals(sequenceUn));
//		System.out.println("-- Deux seuqnces equales ----");
//
//	}
//
//	public void testSequenceEcrit() {
//		System.out.println("Test creer deux sequence et les comparer");
//		ASequence leLundiMatin = new ASequence();
//		leLundiMatin.setSequenceId("AM");
//		System.out.println(leLundiMatin.getSequenceId());
//		Vector<APeriod> leLundiMatinPeriods = new Vector<APeriod>();
//		APeriod leLundiMatinPeriodsUn = new APeriod();
//		leLundiMatinPeriodsUn.setBeginTime(8, 30);
//		leLundiMatinPeriodsUn.setEndTime(11, 30);
//		leLundiMatinPeriodsUn.setPeriodId(1);
//		leLundiMatinPeriodsUn.setPriority(1);
//		leLundiMatinPeriods.add(leLundiMatinPeriodsUn);
//		leLundiMatin.setTTperiods(leLundiMatinPeriods);
//		System.out.println(leLundiMatin.getTTperiods().toString());
//
//		ASequence leMardiMatin = new ASequence();
//		leMardiMatin.setSequenceId("AM");
//		System.out.println(leMardiMatin.getSequenceId());
//		Vector<APeriod> leMardiMatinPeriods = new Vector<APeriod>();
//		APeriod leMardiMatinPeriodsUn = new APeriod();
//		leMardiMatinPeriodsUn.setBeginTime(8, 30);
//		leMardiMatinPeriodsUn.setEndTime(11, 30);
//		leMardiMatinPeriodsUn.setPeriodId(1);
//		leMardiMatinPeriodsUn.setPriority(1);
//		leMardiMatinPeriods.add(leMardiMatinPeriodsUn);
//		leMardiMatin.setTTperiods(leMardiMatinPeriods);
//		System.out.println(leMardiMatin.getTTperiods().toString());
//
//		assertTrue(leLundiMatin.isEquals(leMardiMatin));
//		System.out.println(leLundiMatin.isEquals(leMardiMatin));
//		System.out.println("-- End Test ASequence ----");
//
//	}
//
//}
