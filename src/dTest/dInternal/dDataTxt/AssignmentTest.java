package dTest.dInternal.dDataTxt;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class AssignmentTest extends TestCase {

  public AssignmentTest(String name) {
    super(name);

  }

  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(AssignmentTest.class);
  } // end suite



  public void test_Assignment(){
    assertEquals("No test is required: assertEquals", true, true);
  }


}