package dTest.dInternal.dDataTxt;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */

import junit.framework.*;


public class ActivityTest extends TestCase {

  public ActivityTest(String name) {
    super(name);

  }

  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(ActivityTest.class);
  } // end suite



  public void test_Activity(){
    assertEquals("No test is required: : assertEquals", true, true);
  }


}