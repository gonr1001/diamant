/**
 *
 * Title: ByteInputFileTest
 * Description: ClassName is a class used to
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
 */

package eTest;

import junit.framework.*;

import java.lang.RuntimeException;
import java.util.StringTokenizer;
import java.io.*;

import eLib.exit.txt.ByteInputFile;

public class ByteInputFileTest extends TestCase {

	public ByteInputFileTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(ByteInputFileTest.class);
	} // end suite

	public void testFileNotExist() {
		@SuppressWarnings("unused")
		ByteInputFile bif;
		try {
			bif = new ByteInputFile("hello.txt");
		} catch (FileNotFoundException e) {
			return;
		}
		fail("Expected FileNotFoundException");
	}

	public void testFileNameNull() {
		@SuppressWarnings("unused")
		ByteInputFile bif;
		try {
			bif = new ByteInputFile(null);
		} catch (NullPointerException e) {
			return;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			throw new RuntimeException("Problem in testFileNameNull");
		}
		fail("Expected NullPointerException");
	}

	public void testFileEmpty() {
		ByteInputFile bif;
		try {
			bif = new ByteInputFile("." + File.separator + "eDataTest"
					+ File.separator + "empty.txt");
			byte[] b = bif.readFileAsBytes();
			assertEquals("Test File Empty :", null, b);
			bif.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			throw new RuntimeException("Problem in testFileEmpty");
		}
	}

	public void testFileNonEmpty() {
		ByteInputFile bif;
		try {
			bif = new ByteInputFile("." + File.separator + "eDataTest"
					+ File.separator + "nonEmpty.txt");
			byte[] b = bif.readFileAsBytes();
			assertEquals("Test File nonEmpty size :", 5, b.length);
			assertEquals("Test File nonEmpty byte :", (byte)'1', b[0]);
			assertEquals("Test File nonEmpty byte :", (byte)'5', b[4]);
			bif.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			throw new RuntimeException("Problem in testFileNonEmpty");
		}
	}

	public void testFileCountLines() {
		ByteInputFile bif;
		try {
			bif = new ByteInputFile("." + File.separator + "eDataTest"
					+ File.separator + "count3Lines.txt");
			byte[] b = bif.readFileAsBytes();
			StringTokenizer dataTokens = new StringTokenizer(new String(b),
					"=="); // the token separator is not in the file
			String str = dataTokens.nextToken().trim();
			assertEquals("Test File CountLines size :", 3, ByteInputFile
					.countLines(str));
			bif.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			throw new RuntimeException("Problem in testFileCountLines");
		}
	}

} // end ByteInputFileTest

