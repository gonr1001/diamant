/**
 * Created on May 4, 2006
 * 
 * 
 * Title: DxRWInstructorsTest.java 
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
 * Description: DxRWInstructorsTest is a class used to:
 * <p>
 * TODO:insert comments
 * <p> 
 * 
 */
public class DxRWInstructorsTest extends TestCase{

	/**
	 * @param args
	 */
	public DxRWInstructorsTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxRWInstructorsTest.class);
	} // end suite

}
