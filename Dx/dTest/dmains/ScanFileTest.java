/**
 *
 * Title: ScanFileTest 
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

package dTest.dmains;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import dConstants.DConst;
import dmains.ScanFile;
import eLib.exit.txt.ByteInputFile;

public class ScanFileTest extends TestCase {

	public ScanFileTest(String name) {
		super(name);
		String path = System.getProperty("user.dir") + File.separator
				+ "scanFileDataTest" + File.separator + "testFile.txt";
		String[] args = new String[1];
		args[0] = path;
		ScanFile scan = new ScanFile();
		if (scan.fileNameExists(args)) {
			String inputFileName = scan.getFileName();
			String outFileName = inputFileName.replaceFirst(".txt", "") + "OUT"
					+ ".txt";
			scan.doIt(inputFileName, outFileName);
			if (scan.getError() != "")
				System.out.println("Error in main : " + scan.getError());
		}

	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(ScanFileTest.class);
	} // end suite

	/**
	 * test that the output file is ok
	 */
	public void test_beginOfFile() {
		// path ="." +
		// File.separator+"dataTest"+File.separator+"testFile.txt.out.txt";
		String s = "4D 61 6E 69 66 65 73 74 2D 56 65 72 73 69 6F 6E    Manifest-Version";
		byte[] a = s.getBytes();
		byte[] b = null;
		byte[] c = new byte[a.length];
		String str = System.getProperty("user.dir") + File.separator
				+ "scanFileDataTest" + File.separator + "testFileOUT.txt";
		try {
			ByteInputFile bif = new ByteInputFile(str);
			b = bif.readFileAsBytes();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

		for (int i = 0; i < c.length; i++) {
			c[i] = b[i];

		}

		assertEquals("test_endOfFile : assertEquals: ", true, compareArrays(a,
				c));
	}

	public void test_endOfFile() {
		// path ="." +
		// File.separator+"dataTest"+File.separator+"testFile.txt.out.txt";
		String s = "0D 0A                                              .."
				+ DConst.CR_LF;
		byte[] a = s.getBytes();
		byte[] b = null;
		byte[] c = new byte[a.length];
		String str = System.getProperty("user.dir") + File.separator
				+ "scanFileDataTest" + File.separator + "testFileOUT.txt";
		try {
			ByteInputFile bif = new ByteInputFile(str);
			b = bif.readFileAsBytes();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		int offset = 759;
		for (int i = 0; i < c.length; i++) {
			c[i] = b[offset + i];

		}

		assertEquals("test_endOfFile : assertEquals: ", true, compareArrays(a,
				c));
	}

	private boolean compareArrays(byte[] a, byte[] b) {
		boolean res = false;
		if (a.length == b.length) {
			for (int i = 0; i < a.length; i++) {
				if (a[i] != b[i])
					return res;
			}
			res = true;
		} // en if
		return res;
	}

}