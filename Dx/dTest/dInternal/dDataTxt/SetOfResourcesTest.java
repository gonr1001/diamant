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
import dInternal.dData.InstructorAttach;
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

  /**
   * test_addResource, test that the created Attach is the same
   *  that the one which is on the SetOfResources
   */
  public void test_addResource(){
    Resource _resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach _inst;
    _inst = new InstructorAttach();
    _inst.addAvailability("1 1 1 5 5");
    _inst.addAvailability("5 5 5 1 5");
    _resc = new Resource("Yan",_inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(_resc,1);
    assertEquals("test_addResource: assertEquals",
                 _inst.getVectorAvailability(),
                 ((InstructorAttach)_resc.getAttach()).getVectorAvailability());
  }

  /**
   * test1_addResource, test that the created ID is the same
   *  that the one which is on the SetOfResources
   */
  public void test1_addResource(){
    Resource _resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach _inst;
    _inst = new InstructorAttach();
    _inst.addAvailability("1 1 1 5 5");
    _inst.addAvailability("5 5 5 1 5");
    _resc = new Resource("Yan",_inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(_resc,1);
    assertEquals("test1_addResource: assertEquals", "Yan", ilist.getResource("Yan").getID());
  }

  /**
   * test2_addResource, test that the SetOfResources
   *     does not contains duplicates.
   */
  public void test2_addResource(){
    Resource _resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach _inst;
    _inst = new InstructorAttach();
    _inst.addAvailability("1 1 1 5 5");
    _inst.addAvailability("5 5 5 1 5");
    _resc = new Resource("Yan",_inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(_resc,1);
    ilist.addResource(_resc,1);
    assertEquals("test2_addResource: assertEquals", 1, ilist.size());
  }

  /**
   * test4_addResource, test that the created Attach is not the same
   *  that the one which is on the SetOfResources
   */
  public void test4_addResource(){
    Resource _resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach _inst;
    _inst = new InstructorAttach();
    _inst.addAvailability("1 1 1 5 5");
    _inst.addAvailability("5 5 5 1 5");
    _resc = new Resource("Yan",_inst);
    _inst = new InstructorAttach();
    _inst.addAvailability("1 1 2 5 5");
    _inst.addAvailability("5 5 5 1 5");
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(_resc,1);
    assertEquals("test4_addResource: assertEquals",
                 false,
                 ((InstructorAttach)_resc.getAttach()).getVectorAvailability().equals(_inst.getVectorAvailability()));
  }


  /**
   * test_removeResourceAt, test that the SetOfResources
   *     does not contains the remove Resource At (index)
   *     the index is in a valid range.
   */
  public void test_removeResourceAt(){
    Resource _resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach _inst;
    _inst = new InstructorAttach();
    _inst.addAvailability("1 1 1 5 5");
    _inst.addAvailability("5 5 5 1 5");
    _resc = new Resource("Yan",_inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(_resc,1);
    ilist.removeResourceAt(0);
    assertEquals("test_removeResourceAt: assertEquals", 0, ilist.size());
  }

  /**
   * test1_removeResourceAt, test that the SetOfResources
   *     does not contains the remove Resource At (index)
   *     the index is out of range.
   */
  public void test1_removeResourceAt(){
    Resource _resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach _inst;
    _inst = new InstructorAttach();
    _inst.addAvailability("1 1 1 5 5");
    _inst.addAvailability("5 5 5 1 5");
    _resc = new Resource("Yan",_inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(_resc,1);
    ilist.removeResourceAt(100);
    assertEquals("test1_removeResourceAt: assertEquals", 1, ilist.size());
  }


  /**
   * test_removeResource, test that the SetOfResources
   *     does not contains the remove Resource  (String).
   */
  public void test_removeResource(){
    Resource _resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach _inst;
    _inst = new InstructorAttach();
    _inst.addAvailability("1 1 1 5 5");
    _inst.addAvailability("5 5 5 1 5");
    _resc = new Resource("Yan",_inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(_resc,1);
    assertEquals("test_removeResource: assertEquals", true, ilist.removeResource("Yan"));
  }

  /**
   * test1_removeResource, test that the SetOfResources
   *     does not contains the remove Resource  (long).
   */
  public void test1_removeResource(){
    Resource _resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach _inst;
    _inst = new InstructorAttach();
    _inst.addAvailability("1 1 1 5 5");
    _inst.addAvailability("5 5 5 1 5");
    _resc = new Resource("Yan",_inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(_resc,1);
    assertEquals("test1_removeResource: assertEquals", true, ilist.removeResource(1));
  }

  /**
   * test_getResourceAt, test that the SetOfResources
   *     contains the Resource At (index)
   *     the index is in a valid range.
   */
  public void test_getResourceAt(){
    Resource _resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach _inst;
    _inst = new InstructorAttach();
    _inst.addAvailability("1 1 1 5 5");
    _inst.addAvailability("5 5 5 1 5");
    _resc = new Resource("Yan",_inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(_resc,1);
    //ilist.removeResourceAt(0);
    assertEquals("test_getResourceAt: assertEquals", "Yan", ilist.getResourceAt(0).getID());
    assertEquals("test_getResourceAt: assertEquals", 1, ilist.getResourceAt(0).getKey());
    assertEquals("test_getResourceAt: assertEquals", _inst, ilist.getResourceAt(0).getAttach());
  }

  /**
   * test1_getResourceAt, test that the SetOfResources
   *     contains the Resource At (index)
   *     the index is not in a valid range.
   */
  public void test1_getResourceAt(){
    Resource _resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach _inst;
    _inst = new InstructorAttach();
    _inst.addAvailability("1 1 1 5 5");
    _inst.addAvailability("5 5 5 1 5");
    _resc = new Resource("Yan",_inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(_resc,1);
    //ilist.removeResourceAt(0);
    assertEquals("test1_getResourceAt: assertEquals", null, ilist.getResourceAt(10));
  }

  /**
   * test_getResource, test that the SetOfResources
   *     contains the get Resource  (String).
   */
  public void test_getResource(){
    Resource _resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach _inst;
    _inst = new InstructorAttach();
    _inst.addAvailability("1 1 1 5 5");
    _inst.addAvailability("5 5 5 1 5");
    _resc = new Resource("Yan",_inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(_resc,1);
    assertEquals("test_getResource: assertEquals", "Yan", ilist.getResource("Yan").getID());
    assertEquals("test_getResource: assertEquals", 1, ilist.getResource("Yan").getKey());
    assertEquals("test_getResource: assertEquals", _inst, ilist.getResource("Yan").getAttach());
    assertEquals("test_getResource: assertEquals", null, ilist.getResource("Yann"));
  }

  /**
   * test1_getResource, test that the SetOfResources
   *     contains the get Resource  (long).
   */
  public void test1_getResource(){
    Resource _resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach _inst;
    _inst = new InstructorAttach();
    _inst.addAvailability("1 1 1 5 5");
    _inst.addAvailability("5 5 5 1 5");
    _resc = new Resource("Yan",_inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(_resc,1);
    assertEquals("test1_getResource: assertEquals", "Yan", ilist.getResource(1).getID());
    assertEquals("test1_getResource: assertEquals", 1, ilist.getResource(1).getKey());
    assertEquals("test1_getResource: assertEquals", _inst, ilist.getResource(1).getAttach());
    assertEquals("test1_getResource: assertEquals", null, ilist.getResource(10));
  }

  /**
   * test_getIndexOfResource, test that the SetOfResources
   *     contains the Resource  (long).
   */
  public void test_getIndexOfResource(){
    Resource _resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach _inst;
    _inst = new InstructorAttach();
    _inst.addAvailability("1 1 1 5 5");
    _inst.addAvailability("5 5 5 1 5");
    _resc = new Resource("Yan",_inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(_resc,1);
    _resc = new Resource("Ruben",_inst);
    ilist.addResource(_resc,1);
    assertEquals("test_getIndexOfResource: assertEquals", 0, ilist.getIndexOfResource(1));
    assertEquals("test_getIndexOfResource: assertEquals", 1, ilist.getIndexOfResource(2));
    assertEquals("test_getIndexOfResource: assertEquals", -1, ilist.getIndexOfResource(10));
    assertEquals("test_getIndexOfResource: assertEquals", false,
                 ilist.getIndexOfResource(0)==ilist.getIndexOfResource(1));
  }

  /**
   * test1_getIndexOfResource, test that the SetOfResources
   *     contains the Resource  (String).
   */
  public void test1_getIndexOfResource(){
    Resource _resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach _inst;
    _inst = new InstructorAttach();
    _inst.addAvailability("1 1 1 5 5");
    _inst.addAvailability("5 5 5 1 5");
    _resc = new Resource("Yan",_inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(_resc,1);
    _resc = new Resource("Ruben",_inst);
    ilist.addResource(_resc,1);
    assertEquals("test1_getIndexOfResource: assertEquals 0", 0, ilist.getIndexOfResource("Yan"));
    assertEquals("test1_getIndexOfResource: assertEquals 1", 1, ilist.getIndexOfResource("Ruben"));
    assertEquals("test1_getIndexOfResource: assertEquals 2", -1, ilist.getIndexOfResource("YanRuben"));
    assertEquals("test1_getIndexOfResource: assertEquals 3", false,
                 ilist.getIndexOfResource("Ruben")==ilist.getIndexOfResource("Ruben"));
  }

}