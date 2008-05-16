/**
 * Created on 10-Jan-08
 * 
 * 
 * Title: DxTTStructureTest.java
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

package dInternal.dTimeTable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.StringTokenizer;

import javax.xml.stream.XMLStreamException;
import dConstants.DConst;

import dInternal.dTimeTable.DxTTStructure;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DxTTStructureTest extends TestCase {

	private final String _pathForFiles = "." + File.separator + "dataTest"
			+ File.separator + "TTxmlFiles" + File.separator;

	private final String _pathForSchemas = "dInternal" + File.separator
			+ "dTimeTable" + File.separator + "schemas" + File.separator;

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxTTStructureTest.class);
	} // end suite

	public void test_fileEmpty() {
		DxTTStructure dxTTS = new DxTTStructure();

		try {
			dxTTS.loadTTSFromFile(_pathForFiles + "noFile");
			fail("Should raise a FileNotFoundException");
		} catch (FileNotFoundException expected) {
			assertEquals("test_fileEmpty: assertEquals", true, true);
		} catch (Exception e) {
			System.out.println("Exception " + e.toString());
			assertFalse(true);
		}
	}

	public void test_getSchemaFileName() {
		DxTTStructure dxTTS = new DxTTStructure();

		assertEquals("test_getSchemaFileName: equals", _pathForSchemas
				+ "DxTimetable.xsd", dxTTS.getSchemaFileName());
	}

	public void test_fileBadCloseTag() {
		DxTTStructure dxTTS = new DxTTStructure();

		try {
			dxTTS.loadTTSFromFile(_pathForFiles + "badClosedTag.xml");
			fail("Should raise a XMLStreamException");
		} catch (XMLStreamException xmlSE) {
			StringTokenizer st = new StringTokenizer(xmlSE.getMessage(),
					DConst.CR_LF);
			assertEquals("test_fileBadCloseTag: assertEquals", st.nextToken(),
					"ParseError at [row,col]:[14,9]");
			assertEquals(
					"test_fileBadCloseTag: assertEquals",
					st.nextToken(),
					"Message: Element type \"TTperiod\" must be followed by either attribute specifications, \">\" or \"/>\".");
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_fileBadCloseTag: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_fileBadCloseTag");
			e.printStackTrace();
		}
	}

	public void test_fileNoOpenTag() {
		DxTTStructure dxTTS = new DxTTStructure();
		try {
			dxTTS.loadTTSFromFile(_pathForFiles + "noOpenTag.xml");
			fail("Should raise a XMLStreamException");
		} catch (XMLStreamException xmlSE) {
			StringTokenizer st = new StringTokenizer(xmlSE.getMessage(),
					DConst.CR_LF);
			assertEquals("test_fileNoOpenTag: assertEquals", st.nextToken(),
					"ParseError at [row,col]:[18,10]");
			assertEquals(
					"test_fileNoOpenTag: assertEquals",
					st.nextToken(),
					"Message: The element type \"TTperiods\" must be terminated by the matching end-tag \"</TTperiods>\".");
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_fileNoOpenTag: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_fileNoOpenTag");
			e.printStackTrace();
		}
	}

	public void test_fileNoCloseTags() {
		DxTTStructure dxTTS = new DxTTStructure();
		try {
			dxTTS.loadTTSFromFile(_pathForFiles + "noCloseTag.xml");
			fail("Should raise a XMLStreamException");
		} catch (XMLStreamException xmlSE) {
			StringTokenizer st = new StringTokenizer(xmlSE.getMessage(),
					DConst.CR_LF);
			assertEquals("test_fileNoCloseTags: assertEquals", st.nextToken(),
					"ParseError at [row,col]:[37,17]");
			assertEquals(
					"test_fileNoCloseTags: assertEquals",
					"Message: The end-tag for element type \"TTperiod\" must end with a '>' delimiter.",
					st.nextToken());

		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_fileNoCloseTags: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_fileNoCloseTags");
			e.printStackTrace();
		}
	}
}
