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

import eLib.exit.exception.IOFileException;
import eLib.exit.txt.ByteInputFile;

public class ByteInputFileTest extends TestCase {
	ByteInputFile _bif;

	public ByteInputFileTest(String name) {
		super(name);
		_bif = null;
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(ByteInputFileTest.class);
	} // end suite

	public void testFileNotExist()  {		
		 try {
			 _bif = new ByteInputFile("hello.txt");
			 }
			 catch (IOFileException e) {
			   return;
			 }
			 fail("Expected IOFileException");
	}

	
	public void testFileEmpty()throws Exception  {
		try {
			_bif = new ByteInputFile("." + File.separator + "eDataTest"
					+ File.separator + "empty.txt");
		} catch (IOFileException e) {
			System.out.println(e);
			e.printStackTrace();
			throw new RuntimeException("Problem in testFileEmpty");
		}
		byte[] b = _bif.readFileAsBytes();
		assertEquals("Test FileEmpty :", null, b);
		_bif.close();
	}

	public void testNameFileEmpty() throws Exception {
		String str = null;
		try {
			_bif = new ByteInputFile(str);
		} catch (IOFileException e) {
			e.toString();
		}
		assertEquals("testNameFileEmpty :", null, str);
	}

	public void testFileNotOpen() {
		@SuppressWarnings("unused")
		byte[] b = null;
		try {
			b = _bif.readFileAsBytes();
		} catch (IOFileException e){
			System.out.println("hello");
		} catch (NullPointerException e) {
			return; //System.out.println("Exception :" + e);
		}
		fail("Expected NullPointerException");
	}

} // end ByteInputFileTest

