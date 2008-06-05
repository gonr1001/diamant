/**
 * Created on 4-Dec-07
 * 
 * 
 * Title: SemiExtendedAsciiFileTest.java
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import eLib.exit.exception.DxException;
import eLib.exit.txt.SemiExtendedAsciiFile;

public class SemiExtendedAsciiFileTest extends TestCase {

	private final String _pathForFiles = "." + File.separator + "dataTest" 
	+ File.separator + "edataTest" + File.separator;
	
	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(SemiExtendedAsciiFileTest.class);
	} // end suite

	public void testReadValidFile() {
		try {
			SemiExtendedAsciiFile seaf = new SemiExtendedAsciiFile();
			seaf.setCharKnown("");
			assertEquals("testSimple assertEquals :", true, seaf.validFile(_pathForFiles + "Simple.txt"));
		} catch (Exception e) {
			// Should not fail in tests
			System.out.println("Exception in: testReadValidFile");
			e.printStackTrace();
		}
	}

	public void testCharNotValid() {
		try {
			SemiExtendedAsciiFile seaf = new SemiExtendedAsciiFile();
			seaf.setCharKnown("");
			seaf.validFile(_pathForFiles + "SimpleCharNotV.txt");
		} catch (NullPointerException e) {
			// Should not fail in tests
			System.out.println(e);
			e.printStackTrace();
			throw new RuntimeException("Problem in testCharNotValid");
		} catch (FileNotFoundException e) {
			// Should not fail in tests
			System.out.println(e);
			e.printStackTrace();
			throw new RuntimeException("Problem in testCharNotValid");
		} catch (IOException e) {
			// Should not fail in tests
			System.out.println(e);
			e.printStackTrace();
			throw new RuntimeException("Problem in testCharNotValid");
		} catch (DxException e) {
			// Should fail in tests
			return;
		} catch (Exception e) {
			// Should not fail in tests
			System.out.println(e);
			e.printStackTrace();
			throw new RuntimeException("Problem in testCharNotValid");
		}
		fail("DxException");
	}

	public void testReadFile() {
		try {
			SemiExtendedAsciiFile seaf = new SemiExtendedAsciiFile();
			seaf.setCharKnown("");

			seaf.readFile(_pathForFiles +  "TwoLines.txt");
			byte[] a = { (byte) 65, (byte) 13, (byte) 10, (byte) 66, (byte) 13,
					(byte) 10 };
			assertEquals("testReadFile assertEquals :", a.length, seaf
					.getByteArray().length);
			assertEquals("testReadFile assertEquals :", true, compareArrays(a,
					seaf.getByteArray()));
		} catch (NullPointerException e) {
			// Should not fail in tests
			System.out.println(e);
			e.printStackTrace();
			throw new RuntimeException("Problem in testReadFile");
		} catch (FileNotFoundException e) {
			// Should not fail in tests
			System.out.println(e);
			e.printStackTrace();
			throw new RuntimeException("Problem in testReadFile");
		} catch (IOException e) {
			// Should not fail in tests
			System.out.println(e);
			e.printStackTrace();
			throw new RuntimeException("Problem in testReadFile");
		} catch (Exception e) {
			// Should not fail in tests
			System.out.println(e);
			e.printStackTrace();
			throw new RuntimeException("Problem in testReadFile");
		}
	}

	public void testAdjustingLinesLF() {
		byte[] a = { (byte) 65, (byte) 10, (byte) 66, (byte) 10 };
		byte[] b = { (byte) 65, (byte) 13, (byte) 10, (byte) 66, (byte) 13,
				(byte) 10 };

		SemiExtendedAsciiFile seaf = new SemiExtendedAsciiFile(a, "");
		seaf.adjustingLines();
		assertEquals("testAdjustingLinesLF length:", b.length, seaf
				.getByteArray().length);
		assertEquals("testAdjustingLinesLF compare:", true, compareArrays(b,
				seaf.getByteArray()));
	}

	public void testAdjustingLinesCR() {
		byte[] a = { (byte) 65, (byte) 13, (byte) 66, (byte) 13 };
		byte[] b = { (byte) 65, (byte) 13, (byte) 10, (byte) 66, (byte) 13,
				(byte) 10 };

		SemiExtendedAsciiFile seaf = new SemiExtendedAsciiFile(a, "");
		seaf.adjustingLines();
		assertEquals("testAdjustingLinesCR length: :", b.length, seaf
				.getByteArray().length);
		assertEquals("testAdjustingLinesCR compare: ", true, compareArrays(b,
				seaf.getByteArray()));
	}

	public void testAdjustingLinesCRLF() {

		byte[] a = { (byte) 65, (byte) 13, (byte) 66, (byte) 67 };
		byte[] b = { (byte) 65, (byte) 13, (byte) 10, (byte) 66, (byte) 67 };

		SemiExtendedAsciiFile seaf = new SemiExtendedAsciiFile(a, "");
		seaf.adjustingLines();
		assertEquals("testAdjustingLinesCRLF length: ", b.length, seaf
				.getByteArray().length);
		assertEquals("testAdjustingLinesCRLF compare: ", true, compareArrays(b,
				seaf.getByteArray()));
	}

	public void testAdjustingLinesManyCRLF() {
		byte[] a = { (byte) 13, (byte) 65, (byte) 13, (byte) 13, (byte) 66,
				(byte) 67 };
		byte[] b = { (byte) 13, (byte) 10, (byte) 65, (byte) 13, (byte) 10,
				(byte) 13, (byte) 10, (byte) 66, (byte) 67 };
		SemiExtendedAsciiFile seaf = new SemiExtendedAsciiFile(a, "");
		seaf.adjustingLines();
		
		assertEquals("testAdjustingLinesManyCRLF length: ", b.length, seaf
				.getByteArray().length);
		assertEquals("testAdjustingLinesManyCRLF compare: ", true,
				compareArrays(b, seaf.getByteArray()));
	}

	public void testAdjustingEndFileLF() {
		byte[] a = { (byte) 65, (byte) 10, (byte) 66, (byte) 10 };
		byte[] b = { (byte) 65, (byte) 13, (byte) 10, (byte) 66 };
		
		SemiExtendedAsciiFile seaf = new SemiExtendedAsciiFile(a, "");
		seaf.adjustingLines();
		seaf.adjustingEndFile();
		
		assertEquals("testAdjustingEndFileLF  length: ", b.length, seaf
				.getByteArray().length);
		assertEquals("testAdjustingEndFileLF compare: ", true,
				compareArrays(b, seaf.getByteArray()));
	}

	public void testAdjustingEndFileCR() {
		byte[] a = { (byte) 65, (byte) 13, (byte) 66, (byte) 13 };
		byte[] b = { (byte) 65, (byte) 13, (byte) 10, (byte) 66 };
		SemiExtendedAsciiFile seaf = new SemiExtendedAsciiFile(a, "");
		seaf.adjustingLines();
		seaf.adjustingEndFile();
		
		assertEquals("testAdjustingEndFileCR  length: ", b.length, seaf
				.getByteArray().length);
		assertEquals("testAdjustingEndFileCR compare: ", true,
				compareArrays(b, seaf.getByteArray()));
	}

	public void testAdjustingEndFile() {
		byte[] a = { (byte) 65, (byte) 13, (byte) 66, (byte) 13, (byte) 10,
				(byte) 13, (byte) 10, (byte) 13, (byte) 10 };
		byte[] b = { (byte) 65, (byte) 13, (byte) 10, (byte) 66 };
		
		SemiExtendedAsciiFile seaf = new SemiExtendedAsciiFile(a, "");
		seaf.adjustingLines();
		seaf.adjustingEndFile();
		
		assertEquals("testAdjustingEndFile  length: ", b.length, seaf
				.getByteArray().length);
		assertEquals("testAdjustingEndFile  compare: ", true,
				compareArrays(b, seaf.getByteArray()));
	}

	public void testAppendToCharKnown() {
		byte[] a = { (byte) 65, (byte) 13, (byte) 66, (byte) 13, (byte) 10,
				(byte) 13, (byte) 10, (byte) 13, (byte) 10 };
		SemiExtendedAsciiFile seaf = new SemiExtendedAsciiFile(a, "aeiou");
		seaf.appendToCharKnown("bc");

		assertEquals("testAppendToCharKnownassertEquals :", seaf
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
