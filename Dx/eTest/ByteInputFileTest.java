package eTest;
/**
 *
 * Title: ClassName $Revision: 1.6 $  $Date: 2005-08-01 18:00:45 $
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
 * @version $Revision: 1.6 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */


import junit.framework.*;

import java.lang.RuntimeException;
import java.io.*;

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


 
  public void testFileNotExist() throws Exception {
    try {
      _bif = new ByteInputFile("hello.txt");
      fail("Should raise an IOFileException");
    } catch (Exception e) {
    	//System.out.println("Exception :" + e);
    	//assertEquals("test_0 : assertEquals: ", "java.io.FileNotFound", e.toString().substring(0,"java.io.FileNotFound".length()));
    }
  }

  public void testFileEmpty() throws Exception {
    try {
      _bif = new ByteInputFile("."+File.separator+"eDataTest"+File.separator+"empty.txt");
    } catch (Exception e) {
      System.out.println(e);
      e.printStackTrace();
      throw new RuntimeException("Problem in testFileEmpty");
    }
    byte [] b = _bif.readFileAsBytes();
    assertEquals("Test FileEmpty :", null, b);
    _bif.close();
  }

  public void testNameFileEmpty() throws Exception {
    String str = null;
    try {
      _bif = new ByteInputFile(str);
    } catch (Exception e) {
    	 System.out.println(e);
         //e.printStackTrace();
    }
    assertEquals("testNameFileEmpty :", null, str);
  }

  public void testFileNotOpen() throws Exception {
    byte [] b = null;
    try {
      b = _bif.readFileAsBytes();
    } catch (Exception e) {
    	System.out.println("Exception :" + e);
    }
    assertEquals("Test testFileNotOpen :", null, b);
  }
} // end ByteInputFileTest

