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

import org.xml.sax.SAXException;

import dConstants.DConst;

import dInternal.dTimeTable.DxTTStructure;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DxTTStructureTest extends TestCase {

	/**
	 * 
	 */
	public DxTTStructureTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxTTStructureTest.class);
	} // end suite

	public void test_fileEmpty() {
		DxTTStructure dxTTS = new DxTTStructure();
		String path = buildPathName();
		try {
			dxTTS.loadTTSFromFile(path + "noFile");
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
		assertEquals("test_getSchemaFileName: equals", dxTTS
				.getSchemaFileName(), "dInternal" + File.separator
				+ "dTimeTable" + File.separator + "schemas" + File.separator
				+ "DxTimetable.xsd");
	}

	public void test_fileBadCloseTag() {
		DxTTStructure dxTTS = new DxTTStructure();
		String path = buildPathName();
		try {
			dxTTS.loadTTSFromFile(path + "badClosedTag.xml");
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
			System.out.println(e);
			e.printStackTrace();
			throw new RuntimeException("Problem in test_fileNBadCloseTag");
		}
	}

	public void test_fileNoOpenTag() {
		DxTTStructure dxTTS = new DxTTStructure();
		String path = buildPathName();
		try {
			dxTTS.loadTTSFromFile(path + "noOpenTag.xml");
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
			System.out.println(e);
			e.printStackTrace();
			throw new RuntimeException("Problem in test_fileNoOpenTag");
		}
	}
	
	
//	public void test_emptyElement() {
//		DxTTStructure dxTTS = new DxTTStructure();
//		String path = buildPathName();
//		try {
//			dxTTS.loadTTSFromFile(path + "badElement.xml");
//			fail("Should raise a XMLStreamException");
//		} catch (XMLStreamException xmlSE) {
//			StringTokenizer st = new StringTokenizer(xmlSE.getMessage(),
//					DConst.CR_LF);
//			assertEquals("test_emptyElement: assertEquals", st.nextToken(),
//					"ParseError at [row,col]:[18,10]");
//			assertEquals(
//					"test_emptyElement: assertEquals",
//					st.nextToken(),
//					"Message: The element type \"TTperiods\" must be terminated by the matching end-tag \"</TTperiods>\".");
//		} catch (Exception e) {
//			System.out.println(e);
//			e.printStackTrace();
//			throw new RuntimeException("Problem in test_emptyElement");
//		}
//	}
	
	
	private String buildPathName() {
		StringBuffer path = new StringBuffer(".");
		path.append(File.separator);
		path.append("dataTest");
		path.append(File.separator);
		path.append("TTxmlFiles");
		path.append(File.separator);
		return path.toString();

	}

}
