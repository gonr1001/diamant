package dTest.dInternal.dTimeTable;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author  ysyam
 * @version 1.0
 */

import java.io.File;
import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.dOptimization.ConflictsAttach;
import dInternal.dTimeTable.Period;
import eLib.exit.xml.input.XMLReader;
import eLib.exit.xml.input.XMLInputFile;
import eLib.exit.xml.output.XMLWriter;
import eLib.exit.xml.output.XMLOutputFile;

public class PeriodTest extends TestCase {

	private final String _pathForFiles = "." + File.separator + "dataTest"
			+ File.separator + "TTxmlFiles" + File.separator;
	
	private final String _pathForOutputFiles = "." + File.separator
	+ "forOutputTests" + File.separator;

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(PeriodTest.class);
	} // end suite

	/**
	 * test that read the period xml tag
	 * */
	public void test_readXMLtag() {
		XMLInputFile xmlFile;
		Element item;
		Period period = new Period();
		try {
			xmlFile = new XMLInputFile();
			Document doc = xmlFile.createDocument(_pathForFiles + "period.xml");
			XMLReader list = new XMLReader();
			item = list.getRootElement(doc);
			period.readXMLtag(item);
			assertEquals("test_readXMLtag : assertEquals 1(Hour):", 8, period
					.getBeginHour()[0]);
			assertEquals("test_readXMLtag : assertEquals 2(Minute):", 15,
					period.getBeginHour()[1]);
			assertEquals("test_readXMLtag : assertEquals 3(priotity):", 0,
					period.getPriority());

		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_readXMLtag: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_readXMLtag");
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	public void test_eventsInPeriod() {
		XMLInputFile xmlFile;
		Element item;
		Period period = new Period();
		try {
			xmlFile = new XMLInputFile();
			Document doc = xmlFile.createDocument(_pathForFiles + "period.xml");
			XMLReader list = new XMLReader();
			item = list.getRootElement(doc);
			period.readXMLtag(item);
			ConflictsAttach confAttach = new ConflictsAttach();
			Vector<String> vec = new Vector<String>();
			vec.add("YS,RGR,AJ");
			confAttach.addConflict("AMC640.1.02.1", 3, "student", vec);
			period.getEventsInPeriod().addResource(
					new DResource("GEI200.1.01.1", confAttach), 0);
			vec = new Vector<String>();
			vec.add("Alex");
			confAttach = new ConflictsAttach();
			confAttach.addConflict("AMC645.1.01.1", 1, "Instructor", vec);
			period.getEventsInPeriod().addResource(
					new DResource("ADM111.1.01.1", confAttach), 0);
			DSetOfResources sor = period
					.getConflictsEventsInPeriod("GEI200.1.01.1");
			assertEquals("test_eventsInPeriod : assertEquals :", 2, sor.size());
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_eventsInPeriod: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_eventsInPeriod");
			e.printStackTrace();
		}
	}

	/**
	 * test that gives the end hour
	 * */
	public void test_getEndHour() {
		XMLInputFile xmlFile;
		Element item;
		Period period = new Period();
		try {
			xmlFile = new XMLInputFile();
			Document doc = xmlFile.createDocument(_pathForFiles + "period.xml");
			XMLReader list = new XMLReader();
			item = list.getRootElement(doc);
			period.readXMLtag(item);
			assertEquals("test_getEndHour : assertEquals (endHour):", period
					.getEndHour()[0], 9);
			assertEquals("test_getEndHour : assertEquals (endMinute):", period
					.getEndHour()[1], 15);
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_getEndHour: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_getEndHour");
			e.printStackTrace();
		}

	}

	/**
	 * test that gives the end hour
	 * */
	public void test_writeXMLtag() {
		XMLInputFile xmlFile;
		Element item;
		Period period = new Period();
		Period periodS = new Period();
		try {
			xmlFile = new XMLInputFile();
			Document doc;
			period.setBeginHour(9, 30);
			period.setPriority(2);
			XMLWriter wr = new XMLWriter();
			doc = wr.getNewDocument();
			Element ttPeriod = period.writeXMLtag(doc);
			doc = wr.buildDocument(doc, ttPeriod);
			XMLOutputFile xmlOF = new XMLOutputFile();
			xmlOF.write(doc, _pathForOutputFiles + "SavePeriod.xml");

			// read xml file
			doc = xmlFile.createDocument(_pathForOutputFiles + "SavePeriod.xml");

			XMLReader list = new XMLReader();
			item = list.getRootElement(doc);

			periodS = new Period();
			periodS.readXMLtag(item);
			assertEquals("test_writeXMLtag : assertEquals (beginHour):", periodS
					.getBeginHour()[0], period.getBeginHour()[0]);
			assertEquals("test_writeXMLtag : assertEquals (beginMinute):", periodS
					.getBeginHour()[1], period.getBeginHour()[1]);
			assertEquals("test_writeXMLtag : assertEquals (priority):", periodS
					.getPriority(), period.getPriority());
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_writeXMLtag: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_writeXMLtag");
			e.printStackTrace();
		}

	}

}