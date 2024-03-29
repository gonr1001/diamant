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

	private final String _pathForFiles = "." + File.separator + "dataTest" 
	+ File.separator + "edataTest" + File.separator;
	
	private final String _pathForOutputFiles = "." + File.separator + "forOutputTests"
	+ File.separator;

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(ByteOutputFileTest.class);
	} // end suite



	public void testReadWriteRead() {
		ByteInputFile bif;
		ByteOutputFile bof;
		try {
			bif = new ByteInputFile(_pathForFiles + "nonEmpty.txt");
			byte[] b = bif.readFileAsBytes();
			assertEquals("Test File nonEmpty size :", 5, b.length);
			assertEquals("Test File nonEmpty byte :", (byte)'1', b[0]);
			assertEquals("Test File nonEmpty byte :", (byte)'5', b[4]);
			bif.close();
			bof = new ByteOutputFile(_pathForOutputFiles + "testReadWriteRead.txt");
			bof.writeFileFromBytes(b);
			bof.close();
			bif = new ByteInputFile(_pathForOutputFiles + "testReadWriteRead.txt");
			b = bif.readFileAsBytes();
			assertEquals("Test File nonEmpty size :", 5, b.length);
			assertEquals("Test File nonEmpty byte :", (byte)'1', b[0]);
			assertEquals("Test File nonEmpty byte :", (byte)'5', b[4]);
			bif.close();
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("testReadWriteRead: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: testReadWriteRead");
			e.printStackTrace();
		}
	}

} // end ByteOutputFileTest