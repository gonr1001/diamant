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

import org.xml.sax.SAXException;

import dInternal.dTimeTable.DxTTStructure;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DxTTStructureTest extends TestCase {
	private String _path;

	/**
	 * 
	 */
	public DxTTStructureTest(String name) {
		super(name);
		_path = buildPathName();
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxTTStructureTest.class);
	} // end suite

	public void test_fileEmpty() {
		DxTTStructure dxTTS = new DxTTStructure();
		try {
			dxTTS.loadTTSFromFile(_path + "noFile");
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

	public void test_fileNoCloseTag() {
		DxTTStructure dxTTS = new DxTTStructure();
		try {
			dxTTS.loadTTSFromFile(_path + "noCloseTag.xml");
			fail("Should raise a SAXException");
		} catch (SAXException expected) {
			assertEquals("test_fileNoCloseTag: assertEquals", true, true);
		} catch (Exception e) {
			System.out.println("Exception " + e.toString());
			assertFalse(true);
		}
	}

	public void test_fileNoOpenTag() {
		DxTTStructure dxTTS = new DxTTStructure();
		try {
			dxTTS.loadTTSFromFile(_path + "noOpenTag.xml");
			fail("Should raise a SAXException");
		} catch (SAXException expected) {
			assertEquals("test_fileNoOpenTag: assertEquals", true, true);
		} catch (Exception e) {
			System.out.println("Exception " + e.toString());
			assertFalse(true);
		}
	}

	private String buildPathName() {
		// StringBuffer path = new StringBuffer(OConstant.ROOT_DIR);
		// path.append(File.separator);
		// path.append(OConstant.FILE_TESTS_DATA);
		// path.append(File.separator);
		// return path.toString();
		return "";
	}

}
