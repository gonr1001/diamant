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
    Resource resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach inst;
    inst = new InstructorAttach();
    inst.addAvailability("1 1 1 5 5");
    inst.addAvailability("5 5 5 1 5");
    resc = new Resource("Yan",inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(resc,1);
    assertEquals("test_addResource: assertEquals",
                 inst.getVectorAvailability(),
                 ((InstructorAttach)resc.getAttach()).getVectorAvailability());
  }

  /**
   * test1_addResource, test that the created ID is the same
   *  that the one which is on the SetOfResources
   */
  public void test1_addResource(){
    Resource resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach inst;
    inst = new InstructorAttach();
    inst.addAvailability("1 1 1 5 5");
    inst.addAvailability("5 5 5 1 5");
    resc = new Resource("Yan",inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(resc,1);
    assertEquals("test1_addResource: assertEquals", "Yan", ilist.getResource("Yan").getID());
  }

  /**
   * test2_addResource, test that the SetOfResources
   *     does not contains duplicates.
   */
  public void test2_addResource(){
    Resource resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach inst;
    inst = new InstructorAttach();
    inst.addAvailability("1 1 1 5 5");
    inst.addAvailability("5 5 5 1 5");
    resc = new Resource("Yan",inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(resc,1);
    ilist.addResource(resc,1);
    assertEquals("test2_addResource: assertEquals", 1, ilist.size());
  }

  /**
   * test4_addResource, test that the created Attach is not the same
   *  that the one which is on the SetOfResources
   */
  public void test4_addResource(){
    Resource resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach inst;
    inst = new InstructorAttach();
    inst.addAvailability("1 1 1 5 5");
    inst.addAvailability("5 5 5 1 5");
    resc = new Resource("Yan",inst);
    inst = new InstructorAttach();
    inst.addAvailability("1 1 2 5 5");
    inst.addAvailability("5 5 5 1 5");
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(resc,1);
    assertEquals("test4_addResource: assertEquals",
                 false,
    ((InstructorAttach)resc.getAttach()).getVectorAvailability().equals(inst.getVectorAvailability()));
  }

  /**
   * test_addResource5, test that the created Attach is the same
   *  that the one which is on the SetOfResources
   */
  public void test5_addResource(){
    Resource resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach inst;
    inst = new InstructorAttach();
    inst.addAvailability("1 1 1 5 5");
    inst.addAvailability("5 5 5 1 5");
    resc = new Resource("Yan",inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(resc,1);
    resc = new Resource("Bernard",inst);
    ilist.addResource(resc,1);
    resc = new Resource("Steph",inst);
    ilist.addResource(resc,1);
    resc = new Resource("Ruben",inst);
    ilist.addResource(resc,1);
    resc = new Resource("Alex",inst);
    ilist.addResource(resc,1);

    assertEquals("test5_addResource: assertEquals 0", "Yan", ilist.getResource("Yan").getID());
    assertEquals("test5_addResource: assertEquals 1", "Ruben", ilist.getResource("Ruben").getID());
    assertEquals("test5_addResource: assertEquals 2", "Alex", ilist.getResource("Alex").getID());
    assertEquals("test5_addResource: assertEquals 3", "Steph", ilist.getResource("Steph").getID());
    assertEquals("test5_addResource: assertEquals 4", "Bernard", ilist.getResource("Bernard").getID());
  }
  /**
   * test_removeResourceAt, test that the SetOfResources
   *     does not contains the remove Resource At (index)
   *     the index is in a valid range.
   */
  public void test_removeResourceAt(){
    Resource resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach inst;
    inst = new InstructorAttach();
    inst.addAvailability("1 1 1 5 5");
    inst.addAvailability("5 5 5 1 5");
    resc = new Resource("Yan",inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(resc,1);
    ilist.removeResourceAt(0);
    assertEquals("test_removeResourceAt: assertEquals", 0, ilist.size());
  }

  /**
   * test1_removeResourceAt, test that the SetOfResources
   *     does not contains the remove Resource At (index)
   *     the index is out of range.
   */
  public void test1_removeResourceAt(){
    Resource resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach inst;
    inst = new InstructorAttach();
    inst.addAvailability("1 1 1 5 5");
    inst.addAvailability("5 5 5 1 5");
    resc = new Resource("Yan",inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(resc,1);
    ilist.removeResourceAt(100);
    assertEquals("test1_removeResourceAt: assertEquals", 1, ilist.size());
  }


  /**
   * test_removeResource, test that the SetOfResources
   *     does not contains the remove Resource  (String).
   */
  public void test_removeResource(){
    Resource resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach inst;
    inst = new InstructorAttach();
    inst.addAvailability("1 1 1 5 5");
    inst.addAvailability("5 5 5 1 5");
    resc = new Resource("Yan",inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(resc,1);
    assertEquals("test_removeResource: assertEquals", true, ilist.removeResource("Yan"));
  }

  /**
   * test1_removeResource, test that the SetOfResources
   *     does not contains the remove Resource  (long).
   */
  public void test1_removeResource(){
    Resource resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach inst;
    inst = new InstructorAttach();
    inst.addAvailability("1 1 1 5 5");
    inst.addAvailability("5 5 5 1 5");
    resc = new Resource("Yan",inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(resc,1);
    assertEquals("test1_removeResource: assertEquals", true, ilist.removeResource(1));
  }

  /**
   * test_getResourceAt, test that the SetOfResources
   *     contains the Resource At (index)
   *     the index is in a valid range.
   */
  public void test_getResourceAt(){
    Resource resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach inst;
    inst = new InstructorAttach();
    inst.addAvailability("1 1 1 5 5");
    inst.addAvailability("5 5 5 1 5");
    resc = new Resource("Yan",inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(resc,1);
    assertEquals("test_getResourceAt: assertEquals", "Yan", ilist.getResourceAt(0).getID());
    assertEquals("test_getResourceAt: assertEquals", 1, ilist.getResourceAt(0).getKey());
    assertEquals("test_getResourceAt: assertEquals", inst, ilist.getResourceAt(0).getAttach());
  }

  /**
   * test1_getResourceAt, test that the SetOfResources
   *     contains the Resource At (index)
   *     the index is not in a valid range.
   */
  public void test1_getResourceAt(){
    Resource resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach inst;
    inst = new InstructorAttach();
    inst.addAvailability("1 1 1 5 5");
    inst.addAvailability("5 5 5 1 5");
    resc = new Resource("Yan",inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(resc,1);
    //ilist.removeResourceAt(0);
    assertEquals("test1_getResourceAt: assertEquals", null, ilist.getResourceAt(10));
  }

  /**
   * test_getResource, test that the SetOfResources
   *     contains the get Resource  (String).
   */
  public void test_getResource(){
    Resource resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach inst;
    inst = new InstructorAttach();
    inst.addAvailability("1 1 1 5 5");
    inst.addAvailability("5 5 5 1 5");
    resc = new Resource("Yan",inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(resc,1);
    assertEquals("test_getResource: assertEquals", "Yan", ilist.getResource("Yan").getID());
    assertEquals("test_getResource: assertEquals", 1, ilist.getResource("Yan").getKey());
    assertEquals("test_getResource: assertEquals", inst, ilist.getResource("Yan").getAttach());
    assertEquals("test_getResource: assertEquals", null, ilist.getResource("Yann"));
  }

  /**
   * test1_getResource, test that the SetOfResources
   *     contains the get Resource  (long).
   */
  public void test1_getResource(){
    Resource resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach inst;
    inst = new InstructorAttach();
    inst.addAvailability("1 1 1 5 5");
    inst.addAvailability("5 5 5 1 5");
    resc = new Resource("Yan",inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(resc,1);
    assertEquals("test1_getResource: assertEquals", "Yan", ilist.getResource(1).getID());
    assertEquals("test1_getResource: assertEquals", 1, ilist.getResource(1).getKey());
    assertEquals("test1_getResource: assertEquals", inst, ilist.getResource(1).getAttach());
    assertEquals("test1_getResource: assertEquals", null, ilist.getResource(10));
  }

  /**
   * test_getIndexOfResource, test that the SetOfResources
   *     contains the Resource  (long).
   */
  public void test_getIndexOfResource(){
    Resource resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach inst;
    inst = new InstructorAttach();
    inst.addAvailability("1 1 1 5 5");
    inst.addAvailability("5 5 5 1 5");
    resc = new Resource("Yan",inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(resc,1);
    resc = new Resource("Ruben",inst);
    ilist.addResource(resc,1);
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
    Resource resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach inst;
    inst = new InstructorAttach();
    inst.addAvailability("1 1 1 5 5");
    inst.addAvailability("5 5 5 1 5");
    resc = new Resource("Yan",inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(resc,1);
    resc = new Resource("Ruben",inst);
    ilist.addResource(resc,1);
    resc = new Resource("Alex",inst);
    ilist.addResource(resc,1);
    assertEquals("test1_getIndexOfResource: assertEquals 0", 2, ilist.getIndexOfResource("Yan"));
    assertEquals("test1_getIndexOfResource: assertEquals 1", 0, ilist.getIndexOfResource("Alex"));
    assertEquals("test1_getIndexOfResource: assertEquals 2", 1, ilist.getIndexOfResource("Ruben"));
    assertEquals("test1_getIndexOfResource: assertEquals 3", -1, ilist.getIndexOfResource("YanRuben"));
    assertEquals("test1_getIndexOfResource: assertEquals 4", true,
                 ilist.getIndexOfResource("Ruben")==ilist.getIndexOfResource("Ruben"));
  }

  /**
   * test_setResource, test that a Resource can be setted,
   *  some values could change. Never the _resourceKey.
   *
   */
  public void test_setResource(){
    Resource resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach inst;
    inst = new InstructorAttach();
    inst.addAvailability("1 1 1 5 5");
    inst.addAvailability("5 5 5 1 5");
    resc = new Resource("Yan",inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(resc,1);
    resc = new Resource("Ruben",inst);
    ilist.addResource(resc,1);
    resc = ilist.getResource("Yan");
    resc.setID("Yannick");
    ilist.setResource(resc);
    assertEquals("test_setResource: assertEquals 0", "Yannick", ilist.getResource(1).getID());

    resc = ilist.getResource("Ruben");
    inst = new InstructorAttach();
    inst.addAvailability("1 1 1 5 5");
    inst.addAvailability("5 5 5 1 5");
    resc.setAttach(inst);
    ilist.setResource(resc);
    assertEquals("test_setResource: assertEquals 1", inst, ilist.getResource("Ruben").getAttach());
    assertEquals("test_setResource: assertEquals 2", true,
                 compare(inst.getMatrixAvailability(),
                         ((InstructorAttach)ilist.getResource("Yannick").getAttach()).getMatrixAvailability()));
    inst = new InstructorAttach();
    inst.addAvailability("1 1 1 5 6");
    inst.addAvailability("5 5 5 1 5");
    resc.setAttach(inst);
    ilist.setResource(resc);
    assertEquals("test_setResource: assertEquals 3", false,
              compare(inst.getMatrixAvailability(),
                         ((InstructorAttach)ilist.getResource("Yannick").getAttach()).getMatrixAvailability()));
  }

  /**
   * test_sortSetOfResourcesByID, test that the set elements\
   * are sorted by ID.
   */
  public void test_sortSetOfResourcesByID(){
    Resource resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach inst;
    inst = new InstructorAttach();
    inst.addAvailability("1 1 1 5 5");
    inst.addAvailability("5 5 5 1 5");
    resc = new Resource("Yan",inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(resc,1);
    resc = new Resource("Bernard",inst);
    ilist.addResource(resc,1);
    resc = new Resource("Steph",inst);
    ilist.addResource(resc,1);
    resc = new Resource("Ruben",inst);
    ilist.addResource(resc,1);
    resc = new Resource("Alex",inst);
    ilist.addResource(resc,1);
    ilist.sortSetOfResourcesByID();

    assertEquals("test_sortSetOfResourcesByID: assertEquals 0", "Alex", ilist.getResourceAt(0).getID());
    assertEquals("test_sortSetOfResourcesByID: assertEquals 1", "Bernard", ilist.getResourceAt(1).getID());
    assertEquals("test_sortSetOfResourcesByID: assertEquals 2", "Ruben", ilist.getResourceAt(2).getID());
    assertEquals("test_sortSetOfResourcesByID: assertEquals 3", "Steph", ilist.getResourceAt(3).getID());
    assertEquals("test_sortSetOfResourcesByID: assertEquals 4", "Yan", ilist.getResourceAt(4).getID());
  }

  /**
   * test_sortSetOfResourcesByKey, test that the set elements
   * are sorted by Key.
   */
  public void test_sortSetOfResourcesByKey(){
    Resource resc;
    byte [] data = {1,0,1,1,1};
    InstructorAttach inst;
    inst = new InstructorAttach();
    inst.addAvailability("1 1 1 5 5");
    inst.addAvailability("5 5 5 1 5");
    resc = new Resource("Yan",inst);
    SetOfInstructors ilist = new SetOfInstructors(data, 4,5);
    ilist.addResource(resc,1);
    resc = new Resource("Bernard",inst);
    ilist.addResource(resc,1);
    resc = new Resource("Steph",inst);
    ilist.addResource(resc,1);
    resc = new Resource("Ruben",inst);
    ilist.addResource(resc,1);
    resc = new Resource("Alex",inst);
    ilist.addResource(resc,1);
    ilist.sortSetOfResourcesByKey();

    assertEquals("test_sortSetOfResourcesByKey: assertEquals 0", "Yan", ilist.getResourceAt(0).getID());
    assertEquals("test_sortSetOfResourcesByKey: assertEquals 1", "Bernard", ilist.getResourceAt(1).getID());
    assertEquals("test_sortSetOfResourcesByKey: assertEquals 2", "Steph", ilist.getResourceAt(2).getID());
    assertEquals("test_sortSetOfResourcesByKey: assertEquals 3", "Ruben", ilist.getResourceAt(3).getID());
    assertEquals("test_sortSetOfResourcesByKey: assertEquals 4", "Alex", ilist.getResourceAt(4).getID());
  }

  /**@todo implement the test
   * test_sortSetOfResourcesBySelectedAttachField, test that the set elements
   * are sorted by SelectedAttachField.
   */
  public void test_sortSetOfResourcesBySelectedAttachField(){
    assertEquals("test_sortSetOfResourcesBySelectedAttachField: assertEquals 0", true, true);
  }
  /**
 * */
  private boolean compare(int[][] matrix1, int[][] matrix2){
    if(matrix1.length== matrix2.length){
      for (int i=0; i< matrix1.length; i++){
        if (matrix1[i].length== matrix2[i].length){
          for(int j=0; j< matrix1[i].length; j++)
            if (matrix1[i][j]!=matrix2[i][j])
              return false;
        }else
          return false;
      }
    }
    else
      return false;
    return true;
  }
}