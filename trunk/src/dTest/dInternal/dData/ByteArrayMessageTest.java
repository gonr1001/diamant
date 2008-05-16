package dTest.dInternal.dData;

/**
 *
 * Title: DataExchangeTest $Revision $  $Date: 2006-01-27 23:23:47 $
 * Description: DataExchangeTest is a class used to test the class DValue
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
 * @version $ $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */

import dInternal.dData.ByteArrayMsg;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ByteArrayMessageTest extends TestCase {
	ByteArrayMsg a;

	public ByteArrayMessageTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(ByteArrayMessageTest.class);
	} // end suite

	public void setUp() {
		a = new ByteArrayMsg("new header", "new contents");
	}

	public void test_getContents() {
		assertEquals("test_getContents: assertEquals", "new contents", a
				.getContents());
	}

	public void test_getHeader() {
		assertEquals("test_getHeader: assertEquals", "new header", a
				.getHeader());
	}

	public void test_setContents() {
		a.setContents("renew contenants");
		assertEquals("test_setContents: assertEquals", "renew contenants", a
				.getContents());
	}

}