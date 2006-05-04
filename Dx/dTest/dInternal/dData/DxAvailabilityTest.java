/**
 * Created on May 4, 2006
 * 
 * 
 * Title: DxAvailabilityTest.java 
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
package dTest.dInternal.dData;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dTest.dInternal.dData.dInstructors.DSetOfInstructorsTest;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxAvailabilityTest is a class used to:
 * <p>
 * TODO:insert comments
 * <p> 
 * 
 */
public class DxAvailabilityTest extends TestCase{

	/**
	 * @param args
	 */
	public DxAvailabilityTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DSetOfInstructorsTest.class);
	} // end suite


}
