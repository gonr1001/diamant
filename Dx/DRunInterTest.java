

/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
import junit.framework.*;
import junit.extensions.jfcunit.JFCTestCase;
import dTest.dInterface.InstructorDlgTest;

public class DRunInterTest  {
  public static void main (String[] args) {
      junit.textui.TestRunner.run (suite());
  }
  public DRunInterTest() {

  }
  // The tests are very poor at the moment
  public static Test suite ( ) {
      TestSuite suite= new TestSuite("Proto Interface Tests");
          suite.addTest(InstructorDlgTest.suite());
          //suite.addTest(ResourceTest.suite());
          //suite.addTest(FilterTest.suite());
          //suite.addTest(DPeriodTest.suite());

      return suite;
    }

}