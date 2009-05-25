/**
 *
 * Title: DValueTest $Revision $  $Date: 2005-03-08 16:00:45 $
 * Description: 	DValueTest is a class used to test the class 
 * 				DValue
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
 * @author  $Author: syay1801 $
 * @since JDK1.3
 */

package dTest.dInternal;

import dInternal.DValue;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DValueTest extends TestCase {
	DValue a;
	DValue b;

	public DValueTest(String name) {
		super(name);

	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DValueTest.class);
	} // end suite

	@Override
	public void setUp() {
		a = new DValue();
		b = new DValue(5, new String("30"));
	}

	public void testA_setRefNo() {
		a.setRefNo(1);
		assertEquals("testA_setRefNo: assertEquals", 1, a.getRefNo());
	}

	public void testA_setBooleanValue() {
		a.setBooleanValue(true);
		assertEquals("testA_setBooleanValue: assertEquals", true, a
				.getBooleanValue());
	}

	public void testA_setIntValue() {
		a.setIntValue(2);
		assertEquals("testA_setIntValue: assertEquals", 2, a.getIntValue());
	}

	public void testA_setObjectValue() {
		a.setObjectValue(new String("3"));
		assertEquals("testA_setObjectValue: assertEquals", "3", a
				.getObjectValue());
	}

	public void testB_setRefNo() {
		b.setRefNo(1);
		assertEquals("testB_setRefNo: assertEquals", 1, b.getRefNo());
	}

	public void testB_setBooleanValue() {
		b.setBooleanValue(true);
		assertEquals("testB_setBooleanValue: assertEquals", true, b
				.getBooleanValue());
	}

	public void testB_setIntValue() {
		b.setIntValue(2);
		assertEquals("testB_setIntValue: assertEquals", 2, b.getIntValue());
	}

	public void testB_setObjectValue() {
		b.setObjectValue(new String("3"));
		assertEquals("testB_setObjectValue: assertEquals", "3", b
				.getObjectValue());
	}
}