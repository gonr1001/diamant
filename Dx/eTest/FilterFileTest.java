package eTest;
/**
 *
 * Title: ClassName $Revision: 1.2 $  $Date: 2004-09-10 13:31:21 $
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
 * @version $Revision: 1.2 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */


import junit.framework.*;

import java.io.*;

import eLib.exit.txt.FilterFile;






public class FilterFileTest extends TestCase {

  public FilterFileTest(String name) {
    super(name);
  }

  public static Test suite() {
    // the type safe way is in SimpleTest
    // the dynamic way :
    return new TestSuite(FilterFileTest.class);
  } // end suite

  public void testSimple() throws Exception {
    FilterFile filter = new FilterFile();
    filter.setCharKnown("");
    String str = System.getProperty("user.dir")
                                   + File.separator +
                                   "eDataTest"
                                   + File.separator +
                                   "Simple.txt";
    assertEquals("testSimple assertEquals :", true, filter.validFile(str));
  }

  public void testSimple1() throws Exception {
  FilterFile filter = new FilterFile();
  filter.setCharKnown("");
  String str = System.getProperty("user.dir")
                                 + File.separator +
                                 "eDataTest"
                                 + File.separator +
                                 "SimpleCharNotV.txt";
  assertEquals("testSimple1 assertEquals :", false, filter.validFile(str));
  }

  public void testReadFile() throws Exception {
    FilterFile filter = new FilterFile();
    filter.setCharKnown("");
    String str = System.getProperty("user.dir")
                                 + File.separator +
                                 "eDataTest"
                                 + File.separator +
                                   "TwoLines.txt";
    filter.readFile(str);
    byte [] a = {(byte)65, (byte)13,(byte)10, (byte)66, (byte)13,(byte)10};
    assertEquals("testReadFile assertEquals :", a.length, filter.getByteArray().length);
    assertEquals("testReadFile assertEquals :", true, compareArrays(a, filter.getByteArray()));
  }
  public void testAdjustingLinesLF() throws Exception {
    byte [] a = {(byte)65, (byte)10, (byte)66, (byte)10};
    FilterFile filter = new FilterFile(a,"");
    filter.adjustingLines();

    byte [] b = {(byte)65, (byte)13,(byte)10, (byte)66, (byte)13,(byte)10};
    assertEquals("testAdjustingLinesLF assertEquals1 :", b.length, filter.getByteArray().length);
    assertEquals("testAdjustingLinesLF assertEquals2 :", true, compareArrays(b, filter.getByteArray()));
  }
  public void testAdjustingLinesCR() throws Exception {
    byte [] a = {(byte)65, (byte)13, (byte)66, (byte)13};
    FilterFile filter = new FilterFile(a,"");
    filter.adjustingLines();

    byte [] b = {(byte)65, (byte)13,(byte)10, (byte)66, (byte)13,(byte)10};
    assertEquals("testAdjustingLinesCR assertEquals1 :", b.length, filter.getByteArray().length);
    assertEquals("testAdjustingLinesCR assertEquals2 :", true, compareArrays(b, filter.getByteArray()));
  }

  public void testAdjustingLines1() throws Exception {
    byte [] a = {(byte)65, (byte)13, (byte)66, (byte)67};
    FilterFile filter = new FilterFile(a,"");
    filter.adjustingLines();

    byte [] b = {(byte)65, (byte)13,(byte)10, (byte)66, (byte)67};
    assertEquals("testAdjustingLines1 assertEquals1 :", b.length, filter.getByteArray().length);
    assertEquals("testAdjustingLines1 assertEquals2 :", true, compareArrays(b, filter.getByteArray()));
  }


  public void testAdjustingLines2() throws Exception {
    byte [] a = { (byte)13, (byte)65, (byte)13,  (byte)13, (byte)66, (byte)67};
    FilterFile filter = new FilterFile(a,"");
    filter.adjustingLines();

    byte [] b = {(byte)13, (byte)10, (byte)65, (byte)13, (byte)10,  (byte)13,(byte)10, (byte)66, (byte)67};
    assertEquals("testAdjustingLines2 assertEquals1 :", b.length, filter.getByteArray().length);
    assertEquals("testAdjustingLines2 assertEquals2 :", true, compareArrays(b, filter.getByteArray()));
  }

  public void testAdjustingEndFileLF() throws Exception {
    byte [] a = {(byte)65, (byte)10, (byte)66, (byte)10};
    FilterFile filter = new FilterFile(a,"");
    filter.adjustingLines();
    filter.adjustingEndFile();

    byte [] b = {(byte)65, (byte)13,(byte)10, (byte)66};
    assertEquals("testAdjustingEndFileLF assertEquals1 :", b.length, filter.getByteArray().length);
    assertEquals("testAdjustingEndFileLF assertEquals2 :", true, compareArrays(b, filter.getByteArray()));
  }

  public void testAdjustingEndFileCR() throws Exception {
    byte [] a = {(byte)65, (byte)13, (byte)66, (byte)13};
    FilterFile filter = new FilterFile(a,"");
    filter.adjustingLines();
    filter.adjustingEndFile();
    byte [] b = {(byte)65, (byte)13,(byte)10, (byte)66};
    assertEquals("testAdjustingEndFileCR assertEquals1 :", b.length, filter.getByteArray().length);
    assertEquals("testAdjustingEndFileCR assertEquals2 :", true, compareArrays(b, filter.getByteArray()));
  }

  public void testAdjustingEndFile() throws Exception {
    byte [] a = {(byte)65, (byte)13, (byte)66, (byte)13,(byte)10,(byte)13,(byte)10,(byte)13,(byte)10};
    FilterFile filter = new FilterFile(a,"");
    filter.adjustingLines();
    filter.adjustingEndFile();
    byte [] b = {(byte)65, (byte)13,(byte)10, (byte)66};
    assertEquals("testAdjustingEndFile assertEquals1 :", b.length, filter.getByteArray().length);
    assertEquals("testAdjustingEndFile assertEquals2 :", true, compareArrays(b, filter.getByteArray()));
  }
  
  public void testAppendToCharKnown() throws Exception {
    byte [] a = {(byte)65, (byte)13, (byte)66, (byte)13,(byte)10,(byte)13,(byte)10,(byte)13,(byte)10};
    FilterFile filter = new FilterFile(a,"aeiou");
    filter.appendToCharKnown("bc");
   
    //byte [] b = {(byte)65, (byte)13,(byte)10, (byte)66};
    assertEquals("testAppendToCharKnownassertEquals :", filter.getCharKnown(), "aeioubc");
    //assertEquals("testAdjustingEndFile assertEquals2 :", true, compareArrays(b, filter.getByteArray()));
  }
  
  private boolean compareArrays(byte [] a, byte [] b){
    boolean res = false;
    if ( a.length == b.length) {
      for(int i = 0; i < a.length ; i ++) {
        if (a[i] != b[i] )
          return res;
      }
      res = true;
    } // en if
    return res;
  }
} // end FilterFileTest