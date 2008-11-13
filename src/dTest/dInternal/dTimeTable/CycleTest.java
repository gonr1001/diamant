/**
 *
 * Title: CycleTest 
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
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */
package dTest.dInternal.dTimeTable;

/**
 * <p>
 * Title: Diamant
 * </p>
 * <p>
 * Description: timetable construction
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: UdeS
 * </p>
 * 
 * @author ysyam
 * @version 1.0
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import dInternal.DResource;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Day;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.Sequence;
import eLib.exit.xml.input.XMLReader;
import eLib.exit.xml.input.XMLInputFile;
import eLib.exit.xml.output.XMLWriter;
import eLib.exit.xml.output.XMLOutputFile;

public class CycleTest extends TestCase {
	private final String _pathForFiles = "." + File.separator + "dataTest"
			+ File.separator + "TTxmlFiles" + File.separator;

	private final String _pathForOutputFiles = "." + File.separator
			+ "forOutputTests" + File.separator;


	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(CycleTest.class);
	} // end suite

	/**
	 * test the added days in a cycle
	 */
	public void test_addDays() {
		XMLInputFile xmlFile;
		Element item;
		Cycle cycle = new Cycle();
		try {
			xmlFile = new XMLInputFile();
			InputStream is = new FileInputStream(_pathForFiles + "cycle.xml");
			Document doc = xmlFile.createDocumentFromInputStream(is);
			XMLReader list = new XMLReader();
			item = list.getRootElement(doc);
			cycle.readXMLtag(item);
			assertEquals("test_readXMLtag : assertEquals 1 (number of days):",
			cycle.getNumberOfDays(), 7);
			cycle.getSetOfDays().addResource(new DResource("Ma", new Day()), 0);
			cycle.addDays(3);
			assertEquals("test_addDays : assertEquals 1 (day 1):", "Ma" ,cycle
					.getSetOfDays().getResourceAt(0).getID());
			assertEquals("test_addDays : assertEquals 2 (day 2):","Me",  cycle
					.getSetOfDays().getResourceAt(1).getID() );
			assertEquals("test_addDays : assertEquals 3 (size setofdays):",  11, cycle
					.getSetOfDays().size());
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_addDays: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_addDays");
			e.printStackTrace();
		}
	}

	/**
	 * test the removed days in a cycle
	 */

	public void test_removeDays() {		
		XMLInputFile xmlFile;
		Element item;
		Cycle cycle = new Cycle();
		try {
			xmlFile = new XMLInputFile();
			InputStream is = new FileInputStream(_pathForFiles + "cycle.xml");
			Document doc = xmlFile.createDocumentFromInputStream(is);
			XMLReader list = new XMLReader();
			item = list.getRootElement(doc);
			cycle.readXMLtag(item);
			cycle.getSetOfDays().addResource(new DResource("Ma", new Day()), 0);
			cycle.addDays(4);
			cycle.removeDays(2);
			assertEquals("test_removeDays : assertEquals 1 (day 1):", "Ma", cycle
					.getSetOfDays().getResourceAt(0).getID());
			assertEquals("test_removeDays : assertEquals 2 (day 3):", "Je", cycle
					.getSetOfDays().getResourceAt(2).getID());
			assertEquals("test_removeDays : assertEquals 3 (size setofdays):", 10,
					cycle.getSetOfDays().size());
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_addDays: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_addDays");
			e.printStackTrace();
		}
	}

	/**
	 * test that gives the previous period
	 */
	public void test_previousAndNext() {
		XMLInputFile xmlFile;
		Element item;
		Cycle cycle = new Cycle();
		try {
			xmlFile = new XMLInputFile();
			InputStream is = new FileInputStream(_pathForFiles + "cycle.xml");
			Document doc = xmlFile.createDocumentFromInputStream(is);
			XMLReader list = new XMLReader();
			item = list.getRootElement(doc);
			cycle.readXMLtag(item);
			
			cycle.setCurrentDaySeqPerIndex(2, 1, 1);
			cycle.getPreviousPeriod();
			Period period = cycle.getPreviousPeriod();
			assertEquals("test_previousPeriod_1 : assertEquals (BeginHour):",
					period.getBeginHour()[0], 13);
			assertEquals("test_previousPeriod_1 : assertEquals (BeginMinute):",
					period.getBeginHour()[1], 30);
			cycle.setCurrentDaySeqPerIndex(2, 1, 0);
			
			cycle.getPreviousPeriod();
			period = cycle.getPreviousPeriod();
			assertEquals("test_previousPeriod_2 : assertEquals (BeginHour):", 11,
					period.getBeginHour()[0]);
			assertEquals("test_previousPeriod_2 : assertEquals (BeginMinute):", 15,
					period.getBeginHour()[1]);
			cycle.setCurrentDaySeqPerIndex(2, 0, 0);
			
			cycle.setCurrentDaySeqPerIndex(0, 0, 1);
			cycle.getNextPeriod(1);
			period = cycle.getNextPeriod(1);
			assertEquals("test_NextPeriod_1 : assertEquals (BeginHour):", 10,
					period.getBeginHour()[0]);
			assertEquals("test_NextPeriod_1 : assertEquals (BeginMinute):", 15,
					period.getBeginHour()[1]);
			
			cycle.setCurrentDaySeqPerIndex(2, 0, 0);			
			for (int i = 0; i < 3; i++)
				cycle.getPreviousPeriod();
			period = cycle.getPreviousPeriod();
			assertEquals("test_previousPeriod_3 : assertEquals (BeginHour):", 19,
					period.getBeginHour()[0]);
			assertEquals("test_previousPeriod_3 : assertEquals (BeginMinute):", 00,
					period.getBeginHour()[1]);
			
			cycle.setCurrentDaySeqPerIndex(0, 0, 1);
			cycle.getNextPeriod(1);
			period = cycle.getNextPeriod(1);
			assertEquals("test_NextPeriod_1 : assertEquals (BeginHour):", 10,
					period.getBeginHour()[0]);
			assertEquals("test_NextPeriod_1 : assertEquals (BeginMinute):", 15,
					period.getBeginHour()[1]);
			
			cycle.setCurrentDaySeqPerIndex(0, 0, 3);
			cycle.getNextPeriod(1);
			period = cycle.getNextPeriod(1);
			assertEquals("test_NextPeriod_2 : assertEquals (BeginHour):", 13,
					period.getBeginHour()[0]);
			assertEquals("test_NextPeriod_2 : assertEquals (BeginMinute):", 30,
					period.getBeginHour()[1]);
			
			cycle.setCurrentDaySeqPerIndex(1, 2, 2);
			for (int i = 0; i < 3; i++)
				cycle.getNextPeriod(1);
			period = cycle.getNextPeriod(1);
			assertEquals("test_NextPeriod_3 : assertEquals (BeginHour):", 9,
					period.getBeginHour()[0]);
			assertEquals("test_NextPeriod_3 : assertEquals (BeginMinute):", 15,
					period.getBeginHour()[1]);
			
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_previousAndNext: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_previousAndNext");
			e.printStackTrace();
		}
	}





	/**
	 * test that write the cycle xml tag
	 */
	public void test_writeXMLtag() {
		XMLInputFile xmlFile;
		Element item;
		Cycle cycle = new Cycle();
		Cycle newCycle = new Cycle();

		cycle.getSetOfDays().addResource(new DResource("Ma", new Day()), 0);
		cycle.getDayByIndex(0).getSetOfSequences().addResource(
				new DResource("AM", new Sequence()), 0);
		cycle.getDayByIndex(0).getSequence(0).getSetOfPeriods().addResource(
				new DResource("1", new Period()), 0);
		cycle.addDays(3);
		
		try {
			xmlFile = new XMLInputFile();
			// System.out.println(path+"cycle.xml");//debug
			Document doc;// = xmlFile.getDocumentFile(path+"cycle.xml");
			XMLWriter wr = new XMLWriter();
			doc = wr.getNewDocument();
			// write xml file
			Element ttCycle = cycle.writeXMLtag(doc);
			doc = wr.buildDocument(doc, ttCycle);
			XMLOutputFile xmlOF = new XMLOutputFile();
			xmlOF.write(doc, _pathForOutputFiles + "SaveCycle.xml");

			// read xml file			
			InputStream is = new FileInputStream(_pathForOutputFiles + "SaveCycle.xml");
			doc = xmlFile.createDocumentFromInputStream(is);
			
			XMLReader list = new XMLReader();
			item = list.getRootElement(doc);
			newCycle.readXMLtag(item);
			
			assertEquals("test_writeXMLtag : assertEquals 1 (number of days):",
					cycle.getNumberOfDays(), newCycle.getNumberOfDays());
			
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_addDays: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_addDays");
			e.printStackTrace();
		}
	}

}