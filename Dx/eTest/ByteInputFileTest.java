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

//	public void testByteInputFileNull() {
//		ByteInputFile bif ;
//		@SuppressWarnings("unused")
//		byte[] b = null;
//		try {
//			b = bif.readFileAsBytes();
//		} catch (NullPointerException e) {
//			return; 
//		}
//		catch (Exception e) {
//			System.out.println("hello");
//			System.out.println(e);
//			e.printStackTrace();
//			throw new RuntimeException("Problem in testByteInputFileNull");
//		}
//		fail("Expected NullPointerException");
//	}

} // end ByteInputFileTest

