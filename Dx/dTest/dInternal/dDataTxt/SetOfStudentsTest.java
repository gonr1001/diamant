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
import dInternal.dData.Resource;
import dInternal.dData.StudentAttach;
import dInternal.dData.SetOfStudents;
import dResources.DConst;
import java.util.Vector;
import java.util.StringTokenizer;

public class SetOfStudentsTest  extends TestCase{

  public SetOfStudentsTest(String name) {
    super(name);
  }

  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(SetOfStudentsTest.class);
  } // end suite

  /**
   * test_analyseTokens, test that analyse the first line (the number of students)
   * of instructors file
   * */
  public void test_analyseTokens(){


  }

  /**
   * test1_analyseTokens, test that analyse the empty instructor name
   * in the instructors file
   * */
  public void test1_analyseTokens(){


  }

  /**
   * test2_analyseTokens, test that analyse the wrong number of instructors
   * in the instructors file
   * */
  public void test2_analyseTokens(){

  }

  /**
   * test3_analyseTokens, test the number of instructor availabilities
   * periods per day in the instructors file
   * */
  public void test3_analyseTokens(){

  }

  /**
   * test4_analyseTokens, test that analyse the wrong description of instructors
   * availability in the instructors file
   * */
  public void test4_analyseTokens(){

  }

}