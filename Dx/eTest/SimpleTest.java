///**
//*
//* Title: SimpleTest $Revision: 1.3 $  $Date: 2007-11-30 14:35:33 $
//* Description: SimpleTestis a class used to verify that at least one test
//*              pass. 
//*              It does not test any thing in the package.
//*              It is used to verify that the call to jUnit is done
//*
//*              the main method of the whole program is here.
//*
//* Copyright (c) 2001 by rgr.
//* All rights reserved.
//*
//*
//* This software is the confidential and proprietary information
//* of rgr. ("Confidential Information").  You
//* shall not disclose such Confidential Information and shall use
//* it only in accordance with the terms of the license agreement
//* you entered into with rgr.
//*
//* @version $Revision: 1.3 $
//* @author  $Author: gonzrubi $
//* @since JDK1.3
//*/
//package eTest;
//
//
//
//import junit.framework.*;
//
///**
// * Some very simple tests.
// *
// */
//public class SimpleTest extends TestCase {
//    protected int fValue1;
//    protected int fValue2;
//    public SimpleTest(String name) {
//        super(name);
//    }
//    protected void setUp() {
//        fValue1= 2;
//        fValue2= 3;
//    }
//    public static Test suite() {
//        /*
//         * the dynamic way
//         */
//        return new TestSuite(SimpleTest.class);
//    }
//
//    public void testAdd() {
//        double result;
//        result = fValue1 + fValue2;
//        // forced failure result != 5
//        // rgr assertTrue(result == 6);
//        assertTrue(result == 5);
//    }
//    
//   /* public void testDivideByZero() {
//        // rgr int zero= 0;
//        int zero = 0;
//        int result = 8/zero;
//    }*/
//    
//    public void testEquals() {
//        assertEquals(12, 12);
//        assertEquals(12L, 12L);
//        assertEquals(new Long(12), new Long(12));
//
//        // rgr assertEquals("Size", 12, 13);
//        assertEquals("Size", 12, 12);
//        // rgr assertEquals("Capacity", 12.0, 11.99, 0.0);
//        assertEquals("Capacity", 12.0, 12.00, 0.0);
//    }
//
//
//}