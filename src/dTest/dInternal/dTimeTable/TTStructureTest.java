/**
 *
 * Title: TTStructureTest 
 * Description: 
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.StringTokenizer;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXParseException;

import dConstants.DConst;
import dInternal.DResource;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Day;
//import dInternal.dTimeTable.DxTTStructure;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.Sequence;
import dInternal.dTimeTable.TTStructure;
import eLib.exit.xml.input.XMLReader;
import eLib.exit.xml.input.XMLInputFile;
import eLib.exit.xml.output.XMLWriter;
import eLib.exit.xml.output.XMLOutputFile;

public class TTStructureTest extends TestCase {

	private final String _pathForFiles = "." + File.separator + "dataTest"
			+ File.separator + "TTxmlFiles" + File.separator;

	private final String _pathForOutputFiles = "." + File.separator
			+ "forOutputTests" + File.separator;

	private final String _pathForSchemas = "dInternal" + File.separator
			+ "dTimeTable" + File.separator + "schemas" + File.separator;

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(TTStructureTest.class);
	} // end suite

	public void test_fileEmpty() {
		TTStructure dxTTS = new TTStructure();
		try {
			InputStream in = new FileInputStream(_pathForFiles + "noFile");
			dxTTS.loadTTStructureFromInpuStream(in);
			fail("Should raise a FileNotFoundException");
		} catch (FileNotFoundException expected) {
			assertEquals("test_fileEmpty: assertEquals", true, true);
		} catch (Exception e) {
			System.out.println("Exception " + e.toString());
			assertFalse(true);
		}
	}

	public void test_getSchemaFileName() {
		TTStructure dxTTS = new TTStructure();

		assertEquals("test_getSchemaFileName: equals", _pathForSchemas
				+ "DxTimetable.xsd", dxTTS.getSchemaFileName());
	}

	public void test_fileBadCloseTag() {
		TTStructure dxTTS = new TTStructure();

		try {
			InputStream in = new FileInputStream(_pathForFiles
					+ "badClosedTag.xml");
			dxTTS.loadTTStructureFromInpuStream(in);
			fail("Should raise a SAXParseException");
		} catch (SAXParseException xmlSE) { // XMLStreamException xmlSE) {
			StringTokenizer st = new StringTokenizer(xmlSE.getMessage(),
					DConst.CR_LF);
			assertEquals(
					"test_fileBadCloseTag: assertEquals",
					st.nextToken(),
					"Element type \"TTperiod\" must be followed by either attribute specifications, \">\" or \"/>\".");
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_fileNoOpenTag: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_fileBadCloseTag");
			e.printStackTrace();
		}
	}

	public void test_fileNoOpenTag() {
		TTStructure dxTTS = new TTStructure();
		try {
			InputStream in = new FileInputStream(_pathForFiles
					+  "noOpenTag.xml");
			dxTTS.loadTTStructureFromInpuStream(in);
			fail("Should raise a SAXParseException");
		} catch (SAXParseException xmlSE) { // XMLStreamException xmlSE) 
			StringTokenizer st = new StringTokenizer(xmlSE.getMessage(),
					DConst.CR_LF);

			assertEquals(
					"test_fileBadCloseTag: assertEquals",
					st.nextToken(),
					"The element type \"TTperiods\" must be terminated by the matching end-tag \"</TTperiods>\".");
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_fileNoOpenTag: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_fileNoOpenTag");
			e.printStackTrace();
		}
	}
	
	
	public void test_fileNoCloseTag() {
		TTStructure dxTTS = new TTStructure();
		try {
			InputStream in = new FileInputStream(_pathForFiles
					+ "noCloseTag.xml");
			dxTTS.loadTTStructureFromInpuStream(in);
			//dxTTS.loadTTSFromFile(_pathForFiles + "noCloseTag.xml");
			fail("Should raise a XMLStreamException");
		} catch (SAXParseException xmlSE) { // XMLStreamException xmlSE) 
			StringTokenizer st = new StringTokenizer(xmlSE.getMessage(),
					DConst.CR_LF);
//			assertEquals("test_fileNoCloseTags: assertEquals", st.nextToken(),
//					"ParseError at [row,col]:[37,17]");
			assertEquals(
					"test_fileNoCloseTag: assertEquals",
					"The end-tag for element type \"TTperiod\" must end with a '>' delimiter.",
					st.nextToken());

		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_fileNoCloseTags: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_fileNoCloseTags");
			e.printStackTrace();
		}
	}

	/**
	 * test that load the timetable from an xml file
	 */
	public void test_loadTTStructure() {
		TTStructure tts = new TTStructure();
		InputStream is;
		try {
			is = new FileInputStream(_pathForFiles + "StandardTTC.xml");

			tts.loadTTStructureFromInpuStream(is);// loadTTSFromFile(_pathForFiles
			// +
			// "DXToolsMethodsTest_resizeAvailability.xml")
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(
				"test_CreateStandardTT : assertEquals 1 (number of cycles):",
				3, tts.getSetOfCycles().size());

		Period period = tts.getCurrentCycle().getFirstPeriod();
		assertEquals("test_getFirstPeriod : assertEquals 1 (begin hour):", 8,
				period.getBeginHour()[0]);
		assertEquals("test_getFirstPeriod : assertEquals 2 (begin minute):",
				15, period.getBeginHour()[1]);
		assertEquals("test_getFirstPeriod : assertEquals 3 (priority):", 0,
				period.getPriority());

		period = tts.getCurrentCycle().getLastPeriod();
		assertEquals("test_getLastPeriod : assertEquals 1 (begin hour):", 21,
				period.getBeginHour()[0]);
		assertEquals("test_getLastPeriod : assertEquals 2 (begin minute):", 0,
				period.getBeginHour()[1]);
		assertEquals("test_getLastPeriod : assertEquals 3 (priority):", 1,
				period.getPriority());

		period = tts.getCurrentCycle().getPeriodByIndex(4, 2, 1);
		assertEquals("test_getPeriod : assertEquals 1 (begin hour):", 20,
				period.getBeginHour()[0]);
		assertEquals("test_getPeriod : assertEquals 2 (begin minute):", 0,
				period.getBeginHour()[1]);
		assertEquals("test_getPeriod : assertEquals 3 (priority):", 1, period
				.getPriority());

	}

	/**
	 * test that creates the standard xml timetable file
	 */
	public void test_CreateDefaultTT() {
		TTStructure tts = new TTStructure();
		tts.createDefaultTT(_pathForOutputFiles + "newStandardTT.xml", 5, 5);
		InputStream is;
		try {
			is = new FileInputStream(_pathForOutputFiles + "newStandardTT.xml");

			tts.loadTTStructureFromInpuStream(is);// loadTTSFromFile(_pathForFiles
			// +
			// "DXToolsMethodsTest_resizeAvailability.xml")
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(
				"test1_CreateDefaultTT : assertEquals 1 (number of cycles):",
				5, tts.getSetOfCycles().size());
		assertEquals("test2_CreateDefaultTT : assertEquals 2 (PeriodLenght):",
				60, tts.getPeriodLenght());
	}

	/**
	 * test that gives the maximal number of periods by day in a cycle
	 */

	public void test_getMaxNumberOfPeriodsADay() {
		TTStructure tts = new TTStructure();
		InputStream is;
		try {
			is = new FileInputStream(_pathForFiles + "nonUniformTT.xml");
			tts.loadTTStructureFromInpuStream(is);// loadTTSFromFile(_pathForFiles
			// +
			// "DXToolsMethodsTest_resizeAvailability.xml")
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int maxPer = tts.getCurrentCycle().getMaxNumberOfPeriodsADay();
		assertEquals("test_getMaxNumberOfPeriodsADay : assertEquals 1 :", 12,
				maxPer);

		int maxSeq = tts.getCurrentCycle().getMaxNumberOfSeqs();
		assertEquals("test_getMaxNumberOfSequences : assertEquals 1 :", 3,
				maxSeq);
	}

	/**
	 * test that save the ttstructure in a xml file
	 */
	public void test_saveTTStructure() {
		TTStructure tts = new TTStructure();
		InputStream is;
		try {
			is = new FileInputStream(_pathForFiles + "StandardTTC.xml");

			tts.loadTTStructureFromInpuStream(is);// loadTTSFromFile(_pathForFiles
			// +
			// "DXToolsMethodsTest_resizeAvailability.xml")
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tts.saveTTStructure(_pathForOutputFiles + "SaveStandardTTC.xml");
		TTStructure newtts = new TTStructure();
		InputStream nis;
		try {
			nis = new FileInputStream(_pathForOutputFiles
					+ "SaveStandardTTC.xml");
			newtts.loadTTStructureFromInpuStream(nis);// loadTTSFromFile(_pathForFiles
			// +
			// "DXToolsMethodsTest_resizeAvailability.xml")
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Period lastPer = newtts.getCurrentCycle().getLastPeriod();
		assertEquals(
				"test_saveTTStructure : assertEquals 1 (number of cycles):", 3,
				newtts.getSetOfCycles().size());
		assertEquals("test_saveTTStructure : assertEquals 2 (PeriodLenght):",
				60, newtts.getPeriodLenght());
		assertEquals("test_saveTTStructure : assertEquals 3 (begin hour):", 21,
				lastPer.getBeginHour()[0]);
		assertEquals("test_saveTTStructure : assertEquals 4 (begin minute):",
				0, lastPer.getBeginHour()[1]);
		assertEquals("test_saveTTStructure : assertEquals 5 (priority):", 1,
				lastPer.getPriority());
	}

	/**
	 * test that read the cycle xml tag
	 */
	public void test_readXMLtag() {
		XMLInputFile xmlFile;
		Element item;
		TTStructure tts = new TTStructure();

		try {
			xmlFile = new XMLInputFile();
			InputStream is = new FileInputStream(_pathForFiles
					+ "StandardTTC.xml");
			Document doc = xmlFile.createDocumentFromInputStream(is);
			XMLReader list = new XMLReader();
			item = list.getRootElement(doc);
			tts.readXMLtag(item);

		} catch (Exception e) {
			System.out.println(e);
		}
		assertEquals("test_readXMLtag : assertEquals 1 (number of cycles):",
				tts.getSetOfCycles().size(), 3);
		assertEquals("test_readXMLtag : assertEquals 2 (period length):", tts
				.getPeriodLenght(), 60);
	}

	/**
	 * test that write the cycle xml tag
	 */
	public void test_writeXMLtag() {
		XMLInputFile xmlFile;
		Element item;
		TTStructure tts = new TTStructure();
		TTStructure newtts = new TTStructure();

		Cycle cycle = new Cycle();
		cycle.getSetOfDays().addResource(new DResource("Ma", new Day()), 0);
		cycle.getDayByIndex(0).getSetOfSequences().addResource(
				new DResource("AM", new Sequence()), 0);
		cycle.getDayByIndex(0).getSequence(0).getSetOfPeriods().addResource(
				new DResource("1", new Period()), 0);
		cycle.addDays(3);

		tts.getSetOfCycles().addResource(new DResource("1", cycle), 0);
		tts.getSetOfCycles().addResource(new DResource("2", cycle), 0);
		try {
			xmlFile = new XMLInputFile();

			Document doc;
			XMLWriter wr = new XMLWriter();
			doc = wr.getNewDocument();
			// write xml file
			Element ttCycle = tts.writeXMLtag(doc);
			doc = wr.buildDocument(doc, ttCycle);
			XMLOutputFile xmlOF = new XMLOutputFile();
			xmlOF.write(doc, _pathForOutputFiles + "SaveSetOfCycles.xml");

			// read xml file
			InputStream is = new FileInputStream(_pathForOutputFiles
					+ "SaveSetOfCycles.xml");
			doc = xmlFile.createDocumentFromInputStream(is);
			XMLReader list = new XMLReader();
			item = list.getRootElement(doc);
			newtts.readXMLtag(item);
			assertEquals(
					"test_writeXMLtag : assertEquals 1 (number of cycles):",
					tts.getSetOfCycles().size(), newtts.getSetOfCycles().size());
			assertEquals("test_writeXMLtag : assertEquals 2 (period length):",
					tts.getPeriodLenght(), newtts.getPeriodLenght());
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_writeXMLtag: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_writeXMLtag");
			e.printStackTrace();
		}
	}

	/**
	 * test that creates the standard xml timetable file
	 */
	public void test_cloneCurrentTTS() {
		TTStructure tts = new TTStructure();
		InputStream is;
		try {
			is = new FileInputStream(_pathForFiles + "5j27p.xml");
			tts.loadTTStructureFromInpuStream(is);// loadTTSFromFile(_pathForFiles
			// +
			// "DXToolsMethodsTest_resizeAvailability.xml")
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TTStructure cloneTTS = tts.cloneCurrentTTS();
		assertEquals(
				"test_cloneCurrentTTS1 : assertEquals 1 (number of cycles):",
				1, cloneTTS.getSetOfCycles().size());
		assertEquals(
				"test_cloneCurrentTTS2 : assertEquals 2 (number of days):", 5,
				cloneTTS.getCurrentCycle().getSetOfDays().size());
		assertEquals("test_cloneCurrentTTS3 : assertEquals 3 (PeriodLenght):",
				30, cloneTTS.getPeriodLenght());
		Period p = cloneTTS.getCurrentCycle().getFirstPeriod();
		int[] hour = p.getBeginHour();
		DResource r = cloneTTS.getCurrentCycle().getSetOfDays()
				.getResourceAt(0);
		assertEquals("test_cloneCurrentTTS4 : assertEquals 4 (hour):", 8,
				hour[0]);
		assertEquals("test_cloneCurrentTTS5 : assertEquals 5 (min):", 0,
				hour[1]);
		assertEquals("test_cloneCurrentTTS6 : assertEquals 6 (day):", "Lu", r
				.getID());
		p = cloneTTS.getCurrentCycle().getLastPeriod();
		hour = p.getBeginHour();
		r = cloneTTS.getCurrentCycle().getSetOfDays().getResourceAt(4);
		assertEquals("test_cloneCurrentTTS7 : assertEquals 4 (hour):", 21,
				hour[0]);
		assertEquals("test_cloneCurrentTTS8 : assertEquals 5 (min):", 30,
				hour[1]);
		assertEquals("test_cloneCurrentTTS9 : assertEquals 6 (day):", "Ve", r
				.getID());

	}

	/**
	 * test that clone the standard xml timetable file and test if it is equals
	 * to the original tt file
	 */
	public void test1_cloneCurrentTTS() {
		TTStructure tts = new TTStructure();
		InputStream is;
		try {
			is = new FileInputStream(_pathForFiles + "5j27p.xml");
			tts.loadTTStructureFromInpuStream(is);// loadTTSFromFile(_pathForFiles
			// +
			// "DXToolsMethodsTest_resizeAvailability.xml")
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TTStructure cloneTTS = tts.cloneCurrentTTS();
		boolean isEquals = tts.getCurrentCycle().isEquals(
				cloneTTS.getCurrentCycle());
		assertEquals("test_cloneCurrentTTS : assertEquals 1 :clone:", true,
				isEquals);
		DResource dayR = cloneTTS.getCurrentCycle().getSetOfDays()
				.getResourceAt(0);
		dayR.setID("Lun");
		isEquals = tts.getCurrentCycle().isEquals(cloneTTS.getCurrentCycle());
		assertEquals("test_cloneCurrentTTS : assertEquals 2 :clone:", false,
				isEquals);
	}
}