/**
 * Created on Jul 13, 2007
 * 
 * 
 * Title: FilterFileTest.java 
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
package eTest;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: FilterFileTest is a class used to:
 * <p>
 * Make tests to FilterFile
 * <p>
 * 
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import eLib.exit.exception.DxException;
import eLib.exit.txt.FilterFile;

public class FilterFileTest extends TestCase {

	private final String _pathForFiles = "." + File.separator + "edataTest"
			+ File.separator;

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(FilterFileTest.class);
	} // end suite

	public void testSimple() throws Exception {
		FilterFile filter = new FilterFile();
		filter.setCharKnown("");

		assertEquals("testSimple assertEquals :", true, filter
				.validFile(_pathForFiles + "Simple.txt"));
	}

	public void testEmptyCollection() {
		Collection<Object> collection = new ArrayList<Object>();
		assertTrue(collection.isEmpty());
	}

	public void testSimple1() {
		FilterFile filter = new FilterFile();
		filter.setCharKnown("");

		try {
			filter.validFile(_pathForFiles + "SimpleCharNotV.txt");
		} catch (DxException e) {
			return;
		}
		fail("DxException");
	}

	public void testReadFile() throws Exception {
		FilterFile filter = new FilterFile();
		filter.setCharKnown("");

		filter.readFile(_pathForFiles + "twoLines.txt");
		byte[] a = { (byte) 65, (byte) 13, (byte) 10, (byte) 66, (byte) 13,
				(byte) 10 };
		assertEquals("testReadFile assertEquals :", a.length, filter
				.getByteArray().length);
		assertEquals("testReadFile assertEquals :", true, compareArrays(a,
				filter.getByteArray()));
	}

	public void testAdjustingLinesLF() throws Exception {
		byte[] a = { (byte) 65, (byte) 10, (byte) 66, (byte) 10 };
		FilterFile filter = new FilterFile(a, "");
		filter.adjustingLines();

		byte[] b = { (byte) 65, (byte) 13, (byte) 10, (byte) 66, (byte) 13,
				(byte) 10 };
		assertEquals("testAdjustingLinesLF assertEquals1 :", b.length, filter
				.getByteArray().length);
		assertEquals("testAdjustingLinesLF assertEquals2 :", true,
				compareArrays(b, filter.getByteArray()));
	}

	public void testAdjustingLinesCR() throws Exception {
		byte[] a = { (byte) 65, (byte) 13, (byte) 66, (byte) 13 };
		FilterFile filter = new FilterFile(a, "");
		filter.adjustingLines();

		byte[] b = { (byte) 65, (byte) 13, (byte) 10, (byte) 66, (byte) 13,
				(byte) 10 };
		assertEquals("testAdjustingLinesCR assertEquals1 :", b.length, filter
				.getByteArray().length);
		assertEquals("testAdjustingLinesCR assertEquals2 :", true,
				compareArrays(b, filter.getByteArray()));
	}

	public void testAdjustingLines1() throws Exception {
		byte[] a = { (byte) 65, (byte) 13, (byte) 66, (byte) 67 };
		FilterFile filter = new FilterFile(a, "");
		filter.adjustingLines();

		byte[] b = { (byte) 65, (byte) 13, (byte) 10, (byte) 66, (byte) 67 };
		assertEquals("testAdjustingLines1 assertEquals1 :", b.length, filter
				.getByteArray().length);
		assertEquals("testAdjustingLines1 assertEquals2 :", true,
				compareArrays(b, filter.getByteArray()));
	}

	public void testAdjustingLines2() throws Exception {
		byte[] a = { (byte) 13, (byte) 65, (byte) 13, (byte) 13, (byte) 66,
				(byte) 67 };
		FilterFile filter = new FilterFile(a, "");
		filter.adjustingLines();

		byte[] b = { (byte) 13, (byte) 10, (byte) 65, (byte) 13, (byte) 10,
				(byte) 13, (byte) 10, (byte) 66, (byte) 67 };
		assertEquals("testAdjustingLines2 assertEquals1 :", b.length, filter
				.getByteArray().length);
		assertEquals("testAdjustingLines2 assertEquals2 :", true,
				compareArrays(b, filter.getByteArray()));
	}

	public void testAdjustingEndFileLF() throws Exception {
		byte[] a = { (byte) 65, (byte) 10, (byte) 66, (byte) 10 };
		FilterFile filter = new FilterFile(a, "");
		filter.adjustingLines();
		filter.adjustingEndFile();

		byte[] b = { (byte) 65, (byte) 13, (byte) 10, (byte) 66 };
		assertEquals("testAdjustingEndFileLF assertEquals1 :", b.length, filter
				.getByteArray().length);
		assertEquals("testAdjustingEndFileLF assertEquals2 :", true,
				compareArrays(b, filter.getByteArray()));
	}

	public void testAdjustingEndFileCR() throws Exception {
		byte[] a = { (byte) 65, (byte) 13, (byte) 66, (byte) 13 };
		FilterFile filter = new FilterFile(a, "");
		filter.adjustingLines();
		filter.adjustingEndFile();
		byte[] b = { (byte) 65, (byte) 13, (byte) 10, (byte) 66 };
		assertEquals("testAdjustingEndFileCR assertEquals1 :", b.length, filter
				.getByteArray().length);
		assertEquals("testAdjustingEndFileCR assertEquals2 :", true,
				compareArrays(b, filter.getByteArray()));
	}

	public void testAdjustingEndFile() throws Exception {
		byte[] a = { (byte) 65, (byte) 13, (byte) 66, (byte) 13, (byte) 10,
				(byte) 13, (byte) 10, (byte) 13, (byte) 10 };
		FilterFile filter = new FilterFile(a, "");
		filter.adjustingLines();
		filter.adjustingEndFile();
		byte[] b = { (byte) 65, (byte) 13, (byte) 10, (byte) 66 };
		assertEquals("testAdjustingEndFile assertEquals1 :", b.length, filter
				.getByteArray().length);
		assertEquals("testAdjustingEndFile assertEquals2 :", true,
				compareArrays(b, filter.getByteArray()));
	}

	public void testAppendToCharKnown() throws Exception {
		byte[] a = { (byte) 65, (byte) 13, (byte) 66, (byte) 13, (byte) 10,
				(byte) 13, (byte) 10, (byte) 13, (byte) 10 };
		FilterFile filter = new FilterFile(a, "aeiou");
		filter.appendToCharKnown("bc");

		assertEquals("testAppendToCharKnownassertEquals :", filter
				.getCharKnown(), "aeioubc");

	}

	private boolean compareArrays(byte[] a, byte[] b) {
		boolean res = false;
		if (a.length == b.length) {
			for (int i = 0; i < a.length; i++) {
				if (a[i] != b[i])
					return res;
			}
			res = true;
		} // end if
		return res;
	}
} // end FilterFileTest
