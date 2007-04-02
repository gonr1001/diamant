/**
 * Created on Jun 29, 2006
 * 
 * 
 * Title: ADayTest.java 
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

import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInternal.dTimeTable.ACycle;
import dInternal.dTimeTable.ADay;
import dInternal.dTimeTable.APeriod;
import dInternal.dTimeTable.ASequence;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: ADayTest is a class used to:
 * <p>
 * TODO:insert comments
 * <p> 
 * 
 */
public class ADayTest extends TestCase{
	
	public ADayTest(String name) {
		super(name);
		}
	
	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(ADayTest.class);
	} // end suite
	public void testCycleEqual() {
		System.out.println("-- Begin Test ADay ----");
		ACycle dayUn = new ACycle();
		ACycle dayDeux = new ACycle();
		assertTrue(dayDeux.isEquals(dayUn));
		System.out.println(dayDeux.isEquals(dayUn));
		System.out.println(dayDeux.toString());
		System.out.println("-- Creation et comparaison de deux jours vides ----");
		
	}
	
	public void testCycleNotEqual() {
		System.out.println("-- Begin Test ADay creer et comparer ----");
		Vector<ASequence> lesSequencesLu;
		Vector<ASequence> lesSequencesMa;
		Vector<APeriod> leLundiMatinPeriods;
		Vector<APeriod> leLundiSoirPeriods;
		Vector<APeriod> leMardiMatinPeriods;
		Vector<APeriod> leMardiSoirPeriods;
		ADay leLundi;
		ADay leMardi;
		ASequence leLundiMatin = new ASequence();
		ASequence leLundiSoir = new ASequence();
		ASequence leMardiMatin = new ASequence();
		ASequence leMardiSoir = new ASequence();
		APeriod leLundiMatinPeriodsUn;
		APeriod leLundiSoirPeriodsDeux;
		APeriod leLundiSoirPeriodsTrois;
		APeriod leMardiMatinPeriodsUn;
		APeriod leMardiSoirPeriodsDeux;
		
		leLundi = new ADay();
		leMardi = new ADay();
		leLundi.setDayId("LU");
		leMardi.setDayId("MA");
		leLundi.setDayRef(1);
		leMardi.setDayRef(2);
		lesSequencesLu = new Vector<ASequence>();
		lesSequencesMa = new Vector<ASequence>();
		leLundiMatin.setSequenceId("AM");
		leLundiSoir.setSequenceId("PM");
		leMardiMatin.setSequenceId("AM");
		leMardiSoir.setSequenceId("PM");
		leLundiMatinPeriods = new Vector<APeriod>();
		leLundiSoirPeriods= new Vector<APeriod>();
		leMardiMatinPeriods= new Vector<APeriod>();
		leMardiSoirPeriods= new Vector<APeriod>();
		leLundiMatinPeriodsUn = new APeriod();
		leLundiSoirPeriodsDeux = new APeriod();
		leLundiSoirPeriodsTrois = new APeriod();
		leMardiMatinPeriodsUn = new APeriod();
		leMardiSoirPeriodsDeux = new APeriod();

		leLundiMatinPeriodsUn.setBeginTime(8, 30);
		leLundiSoirPeriodsDeux.setBeginTime(13, 30);
		leLundiSoirPeriodsTrois.setBeginTime(17, 20);
		leMardiMatinPeriodsUn.setBeginTime(8, 30);
		leMardiSoirPeriodsDeux.setBeginTime(13, 30);
		
		leLundiMatinPeriodsUn.setEndTime(11, 30);
		leLundiSoirPeriodsDeux.setEndTime(15, 30);
		leLundiSoirPeriodsTrois.setEndTime(20, 30);
		leMardiMatinPeriodsUn.setEndTime(11, 30);
		leMardiSoirPeriodsDeux.setEndTime(15, 30);
		
		leLundiMatinPeriodsUn.setPeriodId(1);
		leLundiSoirPeriodsDeux.setPeriodId(2);
		leLundiSoirPeriodsTrois.setPeriodId(3);
		leMardiMatinPeriodsUn.setPeriodId(1);
		leMardiSoirPeriodsDeux.setPeriodId(2);
		
		leLundiMatinPeriodsUn.setPriority(1);
		leLundiSoirPeriodsDeux.setPriority(1);
		leLundiSoirPeriodsTrois.setPriority(1);
		leMardiMatinPeriodsUn.setPriority(1);
		leMardiSoirPeriodsDeux.setPriority(1);
		
		leLundiMatinPeriods.add(leLundiMatinPeriodsUn);
		leLundiSoirPeriods.add(leLundiSoirPeriodsDeux);
		leLundiSoirPeriods.add(leLundiSoirPeriodsTrois);
		leMardiMatinPeriods.add(leMardiMatinPeriodsUn);
		leMardiSoirPeriods.add(leMardiSoirPeriodsDeux);
			
		leLundiMatin.setTTperiods(leLundiMatinPeriods);
		leLundiSoir.setTTperiods(leLundiSoirPeriods);
		leMardiMatin.setTTperiods(leMardiMatinPeriods);
		leMardiSoir.setTTperiods(leMardiSoirPeriods);
		lesSequencesLu.add(leLundiMatin);
		lesSequencesLu.add(leLundiSoir);
		lesSequencesMa.add(leMardiMatin);
		lesSequencesMa.add(leMardiSoir);
		leLundi.setTTsequences(lesSequencesLu);
		leMardi.setTTsequences(lesSequencesMa);
				
		assertFalse(leLundi.isEquals(leMardi));
		System.out.println(leLundi.isEquals(leMardi));
		System.out.println(leLundi.getTTsequences().toString());
		System.out.println("-- Creation et comparaison de deux jours non  identiques ----");
		System.out.println("-- End Test ADay ----");
		
	}
}
