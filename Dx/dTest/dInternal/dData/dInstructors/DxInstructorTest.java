/**
 * Created on May 4, 2006
 * 
 * 
 * Title: DxInstructorTest.java 
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
package dTest.dInternal.dData.dInstructors;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxInstructorTest is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
public class DxInstructorTest extends TestCase {

	/**
	 * @param args
	 */
	public DxInstructorTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxInstructorTest.class);
	} // end suite

}