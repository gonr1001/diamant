package dTest.dInternal.dData;

/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
import junit.framework.*;
import dInternal.dData.StudentAttach;
import dInternal.dUtil.DXValue;
import java.util.Vector;

public class StudentAttachTest extends TestCase {
  //private StudentAttach _student;

  public StudentAttachTest(String name) {
    super(name);
  }

  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(StudentAttachTest.class);
  } // end suite

  /**
   * test_addCourse, test that the courses are added in the studentAttach
   * */
  public void test_addCourse(){
    String [] courses = {"GEI4521","GEI442101","GIN2001","GEI420101"};
    StudentAttach studentAttach = new StudentAttach();
    for (int i=0; i< courses.length; i++)
      studentAttach.addCourse(courses[i]);
    assertEquals("test_addCourse: assertEquals", true,compare(courses,studentAttach));

  }

  /**
   * test1_addCourse, test that a modified courses aren't in the studentAttach
   * coursesList
   * */
  public void test1_addCourse(){
    String [] courses = {"GEI4521","GEI442101","GIN2001","GEI420101"};
    StudentAttach studentAttach = new StudentAttach();
    for (int i=0; i< courses.length; i++)
      studentAttach.addCourse(courses[i]);
    studentAttach.getCoursesList().getResource("GEI4521").setID("GEI4441");
    assertEquals("test1_addCourse: assertEquals", false,compare(courses,studentAttach));

  }

  /**
   * test2_addCourse, test that the attach courses is set in the true group
   * coursesList
   * */
  public void test2_addCourse(){
    String [] courses = {"GEI4521","GEI400101","GIN2001","GEI420101"};
    StudentAttach studentAttach = new StudentAttach();
    for (int i=0; i< courses.length; i++)
      studentAttach.addCourse(courses[i]);
    assertEquals("test2_addCourse: assertEquals 1", 1,
                 ((DXValue)studentAttach.getCoursesList().getResource("GEI4001"
                 ).getAttach()).getIntValue());
    assertEquals("test2_addCourse: assertEquals 2", -1,
                 ((DXValue)studentAttach.getCoursesList().getResource("GEI4521"
                 ).getAttach()).getIntValue());

  }

  /**
   * test3_addCourse, test that the group is solidify in the attach courses
   * */
  public void test3_addCourse(){
    String [] courses = {"GEI4521","GEI442101","GIN2001","GEI420101"};
    StudentAttach studentAttach = new StudentAttach();
    for (int i=0; i< courses.length; i++)
      studentAttach.addCourse(courses[i]);
    assertEquals("test3_addCourse: assertEquals", true,
                 ((DXValue)studentAttach.getCoursesList().getResource("GEI4421"
                 ).getAttach()).getBooleanValue());

  }

  /**
   * test_externalKey, test the external key generated by StudentAttach
   * */
  public void test_externalKey(){
    String [] courses = {"GEI4521","GEI442101","GIN2001","GEI420101"};
    String auxField = "/06-05-2003";
    StudentAttach studentAttach = new StudentAttach();
    for (int i=0; i< courses.length; i++)
      studentAttach.addCourse(courses[i]);
    studentAttach.setAuxField(auxField);
    String key = "-DX-EXIT"+auxField;
    assertEquals("test_externalKey: assertEquals", key, studentAttach.externalKey("-DX-EXIT"));

  }

  /**
   * */
  private boolean compare(String[] coursesList, StudentAttach attach){
    for(int i=0; i< coursesList.length; i++){
      if (attach.getCoursesList().getIndexOfResource(coursesList[i].substring(0,7))==-1)
        return false;
    }
    return true;
  }

}