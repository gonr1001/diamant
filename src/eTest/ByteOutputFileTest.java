/**
 * Created on 30-November-07
 * 
 * 
 * Title: ByteOutputFileTest.java
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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import eLib.exit.txt.ByteInputFile;
import eLib.exit.txt.ByteOutputFile;

public class ByteOutputFileTest extends TestCase {

	public ByteOutputFileTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(ByteOutputFileTest.class);
	} // end suite



	public void testReadWriteRead() {
		ByteInputFile bif;
		ByteOutputFile bof;
		try {
			bif = new ByteInputFile("." + File.separator + "eDataTest"
					+ File.separator + "nonEmpty.txt");
			byte[] b = bif.readFileAsBytes();
			assertEquals("Test File nonEmpty size :", 5, b.length);
			assertEquals("Test File nonEmpty byte :", (byte)'1', b[0]);
			assertEquals("Test File nonEmpty byte :", (byte)'5', b[4]);
			bif.close();
			bof = new ByteOutputFile("."+ File.separator + "forOutputTests"
					+ File.separator + "testReadWriteRead.txt");
			bof.writeFileFromBytes(b);
			bof.close();
			bif = new ByteInputFile("." + File.separator + "eDataTest"
					+ File.separator + "testReadWriteRead.txt");
			b = bif.readFileAsBytes();
			assertEquals("Test File nonEmpty size :", 5, b.length);
			assertEquals("Test File nonEmpty byte :", (byte)'1', b[0]);
			assertEquals("Test File nonEmpty byte :", (byte)'5', b[4]);
			bif.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			throw new RuntimeException("Problem in testReadWriteRead");
		}
	}

} // end ByteOutputFileTest