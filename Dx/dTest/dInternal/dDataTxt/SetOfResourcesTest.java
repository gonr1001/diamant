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
import dInternal.dData.Instructor;
import dInternal.dData.SetOfInstructors;
import java.util.Vector;

public class SetOfResourcesTest extends TestCase {

  public SetOfResourcesTest(String name) {
    super(name);

  }

  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(SetOfResourcesTest.class);
  } // end suite

 /*public void testInstructorID(){
    assertEquals("Instructor ID equals :", _inst.toWrite(), "Alexander");
  }*/

  public void testObject(){
    Resource _resc;
    byte [] data = {1,0,1,1,1};
    Instructor _inst;
    _inst = new Instructor();
    _inst.addAvailability("1 1 1 5 5");
    _inst.addAvailability("5 5 5 1 5");
    _resc = new Resource("Yan",_inst);//
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(_resc,1);
    ilist.addResource(_resc,1);
    assertEquals("Instructor Dispo equals :", _inst.getVectorAvailability(), ((Instructor)_resc.getObject()).getVectorAvailability());
    System.out.println("\r\n"+ilist.getResource(1));//debug
  }

  public void testAddResource(){
    Resource _resc;
    byte [] data = {1,0,1,1,1};
    Instructor _inst;
    _inst = new Instructor();
    _inst.addAvailability("1 1 1 5 5");
    _inst.addAvailability("5 5 5 1 5");
    _resc = new Resource("Yan",_inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(_resc,1);
    assertEquals("Resource name equals :", "Yan", ilist.getResource("Yan").getID());
  }

  public void testDuplicateResource(){
    Resource _resc;
    byte [] data = {1,0,1,1,1};
    Instructor _inst;
    _inst = new Instructor();
    _inst.addAvailability("1 1 1 5 5");
    _inst.addAvailability("5 5 5 1 5");
    _resc = new Resource("Yan",_inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(_resc,1);
    ilist.addResource(_resc,1);
    assertEquals("Resource size equals :", 1, ilist.size());
  }


}