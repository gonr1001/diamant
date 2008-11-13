/**
 *
 * Title: SequenceTest 
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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.dData.StandardCollection;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.Sequence;
import eLib.exit.xml.input.XMLReader;
import eLib.exit.xml.input.XMLInputFile;
import eLib.exit.xml.output.XMLWriter;
import eLib.exit.xml.output.XMLOutputFile;

public class SequenceTest extends TestCase {

	private final String _pathForFiles = "." + File.separator + "dataTest"
			+ File.separator + "TTxmlFiles" + File.separator;

	private final String _pathForOutputFiles = "." + File.separator
			+ "forOutputTests" + File.separator;

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(SequenceTest.class);
	} // end suite

	/**
	 * test that read the sequence xml tag
	 */
	public void test_readXMLtag() {
		XMLInputFile xmlFile;
		Element setOfPers;
		Sequence sequence = new Sequence();
		try {
			xmlFile = new XMLInputFile();
			InputStream is = new FileInputStream(_pathForFiles + "sequence.xml");
			Document doc = xmlFile.createDocumentFromInputStream(is);

			XMLReader list = new XMLReader();
			setOfPers = list.getRootElement(doc);
			sequence.readXMLtag(setOfPers);
			assertEquals(
					"test_readXMLtag : assertEquals 1(Number of periods):", 2,
					sequence.getSetOfPeriods().size());

			assertEquals("test_readXMLtag : assertEquals 2(Minute):", sequence
					.getSetOfPeriods().getResourceAt(0).getID(), "1");
			assertEquals("test_readXMLtag : assertEquals 2(Minute):", sequence
					.getSetOfPeriods().getResourceAt(1).getID(), "2");
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_readXMLtag: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_readXMLtag");
			e.printStackTrace();
		}

	}

	/**
	 * test to generate a clone of a sequence
	 */

	public void test_cloneSequence() {
		Sequence firstSequence = new Sequence();
		Sequence clonedSequence = new Sequence();
		DSetOfResources setOfPeriods = new StandardCollection();

		for (int i = 0; i < 3; i++) {
			setOfPeriods.addResource(new DResource(Integer.toString(i),
					new Period()), 0);
		}
		firstSequence.setSetOfPeriods(setOfPeriods);
		clonedSequence = firstSequence.cloneSequence();

		assertEquals(
				"test_cloneSequence : assertEquals 1 (Size of setOfResources):",
				firstSequence.getSetOfPeriods().size(), clonedSequence
						.getSetOfPeriods().size());
		assertEquals("test_cloneSequence : assertEquals 2 (ID of period 1):",
				firstSequence.getSetOfPeriods().getResourceAt(0).getID(),
				clonedSequence.getSetOfPeriods().getResourceAt(0).getID());
		assertEquals("test_cloneSequence : assertEquals 3 (ID of period 2):",
				firstSequence.getSetOfPeriods().getResourceAt(1).getID(),
				clonedSequence.getSetOfPeriods().getResourceAt(1).getID());
	}

	/**
	 * test for writing a sequence XMLTag
	 */
	public void test_writeXMLtag() {
		XMLInputFile xmlFile;
		Element eSetOfPers;
		Sequence firstSequence = new Sequence();
		Sequence savedSequence = new Sequence();
		DSetOfResources setOfPeriods = new StandardCollection();
		try {
			xmlFile = new XMLInputFile();
			for (int i = 1; i < 5; i++) {
				setOfPeriods.addResource(new DResource(Integer.toString(i),
						new Period()), 1);
			}
			firstSequence.setSetOfPeriods(setOfPeriods);
			XMLWriter wr = new XMLWriter();
			Document doc;
			doc = wr.getNewDocument();
			eSetOfPers = firstSequence.writeXMLtag(doc);
			doc = wr.buildDocument(doc, eSetOfPers);
			XMLOutputFile xmlOF = new XMLOutputFile();
			xmlOF.write(doc, _pathForOutputFiles + "SavedSequence.xml");

			// read xml file
			InputStream is = new FileInputStream(_pathForOutputFiles + "SavedSequence.xml");
			doc = xmlFile.createDocumentFromInputStream(is);

			XMLReader list = new XMLReader();
			eSetOfPers = list.getRootElement(doc);
			savedSequence = new Sequence();
			savedSequence.readXMLtag(eSetOfPers);
			assertEquals(
					"test_writeXMLtag : assertEquals 1 (Size of setOfPeriods):",
					firstSequence.getSetOfPeriods().size(), savedSequence
							.getSetOfPeriods().size());
			assertEquals("test_writeXMLtag : assertEquals 2 (ID of period 1):",
					firstSequence.getSetOfPeriods().getResourceAt(0).getID(),
					savedSequence.getSetOfPeriods().getResourceAt(0).getID());
			assertEquals("test_writeXMLtag : assertEquals 2 (ID of period 1):",
					firstSequence.getSetOfPeriods().getResourceAt(1).getID(),
					savedSequence.getSetOfPeriods().getResourceAt(1).getID());

		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_writeXMLtag: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_writeXMLtag");
			e.printStackTrace();
		}
	}

}
