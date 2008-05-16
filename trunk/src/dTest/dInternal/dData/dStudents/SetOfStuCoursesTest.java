/**
 * 
 * Title: SetOfStuSitesTest $Revision: 1.2 $ $Date: 2005-03-08 16:00:45 $
 * Description: SetOfStuSitesTest is a class used as a test container. It test
 * students and their attributes.
 * 
 * 
 * Copyright (c) 2001 by rgr. All rights reserved.
 * 
 * 
 * This software is the confidential and proprietary information of rgr.
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with rgr.
 * 
 * @version $Revision: 1.2 $
 * @author $Author: syay1801 $
 * @since JDK1.3
 */

package dTest.dInternal.dData.dStudents;

import dInternal.dData.dStudents.SetOfStuCourses;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SetOfStuCoursesTest extends TestCase {
	SetOfStuCourses _sosc;

	public SetOfStuCoursesTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(SetOfStuCoursesTest.class);
	} // end suite

	public void setUp() {
		_sosc = new SetOfStuCourses();
	}

	public void test_getSelectedField() {
		assertEquals("test_getSelectedField: assertEquals", 0, 
				_sosc.getSelectedField(5));
	}

	public void test_toWrite() {
		assertEquals("test_toWrite: assertEquals", "", _sosc.toWrite());
	}

	public void test_setAuxField() {
		_sosc.setAuxField("test");
		assertEquals("test_setAuxField: assertEquals", "test", 
				_sosc.getAuxField());
	}
}