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
import dInternal.Resource;
import dInternal.Instructor;
import dInternal.InstructorsList;
import java.util.Vector;

public class ResourceTest extends TestCase {

  public ResourceTest(String name) {
    super(name);

  }

  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(ResourceTest.class);
  } // end suite

 /*public void testInstructorID(){
    assertEquals("Instructor ID equals :", _inst.toString(), "Alexander");
  }*/

  public void testObject(){
    Resource _resc;
    byte [] data = {1,0,1,1,1};
    Instructor _inst;
    _inst = new Instructor();
    _inst.addDispDay("1 1 1 5 5");
    _inst.addDispDay("5 5 5 1 5");
    _resc = new Resource(1,"Yan",_inst);
    InstructorsList ilist = new InstructorsList(data, 4,5);
    ilist.addResource(_resc);
    ilist.addResource(_resc);
    assertEquals("Instructor Dispo equals :", _inst.getInstDisp(), ((Instructor)_resc.getObject()).getInstDisp());
    System.out.println("\r\n"+ilist.getResource(1));//debug
  }


}