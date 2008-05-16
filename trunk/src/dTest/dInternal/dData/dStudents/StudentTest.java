/**
 *
 * Title: StudentTest $Revision: 1.2 $  $Date: 2005-03-08 16:00:45 $
 * Description: StudentTest is a class used as a test container.
 *              It test students and their attributes.
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
 * @author  $Author: syay1801 $
 * @since JDK1.3
 */

package dTest.dInternal.dData.dStudents;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInternal.dData.dStudents.Student;

public class StudentTest extends TestCase {
	Student _student;
	
	public StudentTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(StudentTest.class);
	} // end suite
	
	public void setUp(){
		_student = new Student("Décarie-Drolet, Chloe");
	}
	
	public void test_addCourses(){
		_student.addCourses("FII143100");
		_student.addCourses("FII356100");
		_student.addCourses("SOI247100");
		_student.addCourses("SOI362100");
		assertEquals("test_addCourses: assertEquals1","FII1431",(_student.getCoursesList()).getResourceAt(0).getID());
		assertEquals("test_addCourses: assertEquals2","FII3561",(_student.getCoursesList()).getResourceAt(1).getID());
		assertEquals("test_addCourses: assertEquals3","SOI2471",(_student.getCoursesList()).getResourceAt(2).getID());
		assertEquals("test_addCourses: assertEquals4","SOI3621",(_student.getCoursesList()).getResourceAt(3).getID());
	}
	
	public void test_setSex(){
		// 0= Male; 1=Female
		_student.setSex(0);
		assertEquals("test_setSex: assertEquals", 0, _student.getSex());
	}
	
	public void test_setAuxField(){
		_student.setAuxField("test");
		assertEquals("test_setAuxField: assertEquals","test", _student.getAuxField());
	}
	
	public void test_setSession(){
		_student.setSession(2);
		assertEquals("test_setSession: assertEquals", 2 , _student.getSession());
	}
	
	public void test_setStage(){
		_student.setStage(1);
		assertEquals("test_setStage: assertEquals", 1 , _student.getStage());
	}

	public void test_setArea(){
		_student.setArea(2);
		assertEquals("test_setArea: assertEquals", 2 , _student.getArea());
	}

	public void test_externalKey(){
		//don't used
	}
	
	public void test1_isInActivity(){
		assertEquals("test1_isInActivity: assertEquals1", false, _student.isInActivity("FII1431"));
		_student.addCourses("FII143100");
		assertEquals("test1_isInActivity: assertEquals2", true, _student.isInActivity("FII1431"));
	}
	public void test2_isInActivity(){
		assertEquals("test2_isInActivity: assertEquals1", false, _student.isInActivity("FII1431",0));
		_student.addCourses("FII143100");
		_student.setInGroup("FII143100",0,true);
		assertEquals("test2_isInActivity: assertEquals2", false, _student.isInActivity("FII1431",0));
	}
	public void test_setInGroup(){
		assertEquals("test_isInGroup: assertEquals1", false, _student.isInGroup("FII1431",0));
		_student.addCourses("FII143100");
		_student.setInGroup("FII143100",0,true);
		assertEquals("test_isInGroup: assertEquals2", true, _student.isInGroup("FII1431",0));
	}
	
	public void test_getGroup(){
		_student.addCourses("FII143100");
		_student.setInGroup("FII143100",0,true);
		assertEquals("test_getGroup: assertEquals", 0, _student.getGroup("FII1431"));
	}	
}