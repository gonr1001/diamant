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
	String _path;

	Cycle _cycle;

	public CycleTest(String name) {
		super(name);
		_path = "." + File.separator + "dataTest" + File.separator
				+ "TTxmlFiles" + File.separator;

		XMLInputFile xmlFile;
		Element item;
		_cycle = new Cycle();
		try {

			xmlFile = new XMLInputFile();
			// System.out.println(path+"cycle.xml");//debug
			Document doc = xmlFile.createDocument(_path + "cycle.xml");
			XMLReader list = new XMLReader();
			item = list.getRootElement(doc);
			_cycle.readXMLtag(item);
			// _setOfCycles.readXMLtag(root);
		} catch (Exception e) {
			System.out.println(e);
		}
		//
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(CycleTest.class);
	} // end suite

	/**
	 * test the added days in a cycle
	 */
	public void test_addDays() {
		Cycle cycle = new Cycle();
		cycle.getSetOfDays().addResource(new DResource("Ma", new Day()), 0);
		cycle.addDays(3);
		assertEquals("test_addDays : assertEquals 1 (day 1):", cycle
				.getSetOfDays().getResourceAt(0).getID(), "Ma");
		assertEquals("test_addDays : assertEquals 2 (day 2):", cycle
				.getSetOfDays().getResourceAt(1).getID(), "Me");
		assertEquals("test_addDays : assertEquals 3 (size setofdays):", cycle
				.getSetOfDays().size(), 4);
	}

	/**
	 * test the removed days in a cycle
	 */

	public void test_removeDays() {
		Cycle cycle = new Cycle();
		cycle.getSetOfDays().addResource(new DResource("Ma", new Day()), 0);
		cycle.addDays(4);
		cycle.removeDays(2);
		assertEquals("test_removeDays : assertEquals 1 (day 1):", cycle
				.getSetOfDays().getResourceAt(0).getID(), "Ma");
		assertEquals("test_removeDays : assertEquals 2 (day 3):", cycle
				.getSetOfDays().getResourceAt(2).getID(), "Je");
		assertEquals("test_removeDays : assertEquals 3 (size setofdays):",
				cycle.getSetOfDays().size(), 3);
	}

	/**
	 * test that gives the previous period
	 */
	public void test_previousPeriod_1() {
		_cycle.setCurrentDaySeqPerIndex(2, 1, 1);
		_cycle.getPreviousPeriod();
		Period period = _cycle.getPreviousPeriod();
		assertEquals("test_previousPeriod_1 : assertEquals (BeginHour):",
				period.getBeginHour()[0], 13);
		assertEquals("test_previousPeriod_1 : assertEquals (BeginMinute):",
				period.getBeginHour()[1], 30);
	}

	/**
	 * test that gives the previous period
	 */
	public void test_previousPeriod_2() {
		_cycle.setCurrentDaySeqPerIndex(2, 1, 0);
		_cycle.getPreviousPeriod();
		Period period = _cycle.getPreviousPeriod();
		assertEquals("test_previousPeriod_2 : assertEquals (BeginHour):", 11,
				period.getBeginHour()[0]);
		assertEquals("test_previousPeriod_2 : assertEquals (BeginMinute):", 15,
				period.getBeginHour()[1]);
	}

	/**
	 * test that gives the previous period
	 */
	public void test_previousPeriod_3() {
		_cycle.setCurrentDaySeqPerIndex(2, 0, 0);
		for (int i = 0; i < 3; i++)
			_cycle.getPreviousPeriod();
		Period period = _cycle.getPreviousPeriod();
		assertEquals("test_previousPeriod_3 : assertEquals (BeginHour):", 19,
				period.getBeginHour()[0]);
		assertEquals("test_previousPeriod_3 : assertEquals (BeginMinute):", 00,
				period.getBeginHour()[1]);
	}

	/**
	 * test that gives the next period
	 */
	public void test_NextPeriod_1() {
		_cycle.setCurrentDaySeqPerIndex(0, 0, 1);
		_cycle.getNextPeriod(1);
		Period period = _cycle.getNextPeriod(1);
		assertEquals("test_NextPeriod_1 : assertEquals (BeginHour):", 10,
				period.getBeginHour()[0]);
		assertEquals("test_NextPeriod_1 : assertEquals (BeginMinute):", 15,
				period.getBeginHour()[1]);
	}

	/**
	 * test that gives the next period
	 */
	public void test_NextPeriod_2() {
		_cycle.setCurrentDaySeqPerIndex(0, 0, 3);
		_cycle.getNextPeriod(1);
		Period period = _cycle.getNextPeriod(1);
		assertEquals("test_NextPeriod_2 : assertEquals (BeginHour):", 13,
				period.getBeginHour()[0]);
		assertEquals("test_NextPeriod_2 : assertEquals (BeginMinute):", 30,
				period.getBeginHour()[1]);
	}

	/**
	 * test that gives the next period
	 */
	public void test_NextPeriod_3() {
		_cycle.setCurrentDaySeqPerIndex(1, 2, 2);
		for (int i = 0; i < 3; i++)
			_cycle.getNextPeriod(1);
		Period period = _cycle.getNextPeriod(1);
		assertEquals("test_NextPeriod_3 : assertEquals (BeginHour):", 10,
				period.getBeginHour()[0]);
		assertEquals("test_NextPeriod_3 : assertEquals (BeginMinute):", 15,
				period.getBeginHour()[1]);
	}

	/**
	 * test that read the cycle xml tag
	 */
	public void test_readXMLtag() {
		/*
		 * readFile xmlFile; Element item; Cycle cycle= new Cycle(); try{
		 * xmlFile = new readFile();
		 * //System.out.println(path+"cycle.xml");//debug Document doc =
		 * xmlFile.getDocumentFile(_path+"cycle.xml"); ReadXMLElement list= new
		 * ReadXMLElement(); item= list.getRootElement(doc);
		 * cycle.readXMLtag(item); //_setOfCycles.readXMLtag(root);
		 * }catch(Exception e){ System.out.println(e); }
		 */
		assertEquals("test_readXMLtag : assertEquals 1 (number of days):",
				_cycle.getNumberOfDays(), 7);
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
			xmlOF.write(doc, _path + "SaveCycle.xml");

			// read xml file
			doc = xmlFile.createDocument(_path + "SaveCycle.xml");
			XMLReader list = new XMLReader();
			item = list.getRootElement(doc);
			newCycle.readXMLtag(item);
			// item= list.getRootElement(doc);
			// newCycle.readXMLtag(item);
			// _setOfCycles.readXMLtag(root);
		} catch (Exception e) {
			System.out.println(e);
		}
		assertEquals("test_writeXMLtag : assertEquals 1 (number of days):",
				cycle.getNumberOfDays(), newCycle.getNumberOfDays());
	}

}