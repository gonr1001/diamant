package dTest.dInternal;

/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
import junit.framework.*;
import dInternal.dData.Instructor;
import java.util.Vector;

public class InstructorTest extends TestCase {
  private Instructor _inst;
  public InstructorTest(String name) {
    super(name);
    _inst = new Instructor();
  }

  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(InstructorTest.class);
  } // end suite

 /*public void testInstructorID(){
    assertEquals("Instructor ID equals :", _inst.toString(), "Alexander");
  }*/

  public void testInstructorDispo(){
    Vector v = new Vector();
    v.add("1 1 1 1 5");
    v.add("1 1 1 5 5");
    _inst.addDispDay("1 1 1 1 5");
    _inst.addDispDay("1 1 1 5 5");
    assertEquals("Instructor Dispo equals :", _inst.getInstDisp(), v);
  }

  public void testremoveInstDispo(){
    Vector v = new Vector();
    v.add("1 1 1 1 5");
    //v.add("1 1 1 5 5");
    _inst.addDispDay("1 1 1 1 5");
    _inst.addDispDay("1 1 1 5 5");
    //assertEquals("Instructor Dispo equals :", _inst.getInstDisp(), v);
    _inst.removeDispDay(2);
    assertEquals("Instructor Dispo remove equals :", _inst.getInstDisp(), v);
  }

  public void testsetInstDispo(){
    Vector v = new Vector();
    v.add("1 1 1 1 5");
    v.add("1 1 1 5 5");
    _inst.setInstructorDisp(v);
    assertEquals("Instructor Dispo set equals :", _inst.getInstDisp(), v);
  }
}