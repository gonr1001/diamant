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
    assertEquals("Instructor ID equals :", _inst.toWrite(), "Alexander");
  }*/

  public void test_addAvailability(){
    Vector v = new Vector();
    v.add("1 1 1 1 5");
    v.add("1 1 1 5 5");
    _inst.addAvailability("1 1 1 1 5");
    _inst.addAvailability("1 1 1 5 5");
    assertEquals("test_addAvailability: assertEquals", _inst.getVectorAvailability(), v);
  }

  public void test_removeAvailability(){
    Vector v = new Vector();
    v.add("1 1 1 1 5");
    //v.add("1 1 1 5 5");
    _inst.addAvailability("1 1 1 1 5");
    _inst.addAvailability("1 1 1 5 5");
    //assertEquals("Instructor Dispo equals :", _inst.getVectorAvailability(), v);
    _inst.removeAvailability(2);
    assertEquals("test_removeAvailability: assertEquals", _inst.getVectorAvailability(), v);
  }

  public void test_setAvailability(){
    Vector v = new Vector();
    v.add("1 1 1 1 5");
    v.add("1 1 1 5 5");
    _inst.setAvailability(v);
    assertEquals("test_setAvailability: assertEquals", _inst.getVectorAvailability(), v);
  }

  public void test1_setAvailability(){
    Vector v = new Vector();
    v.add("1 1 1 1 5");
    v.add("1 1 1 5 5");
    int [][] availMatrix={{1,1,1,1,5},{1,1,1,5,5}};
    _inst.setAvailability(availMatrix);
    assertEquals("test1_getAvailability: assertEquals", v , _inst.getVectorAvailability());
  }

  public void test_getAvailability(){
    Vector v = new Vector();
    v.add("1 1 1 1 5");
    v.add("1 1 1 5 5");
    int [][] availMatrix={{1,1,1,1,5},{1,1,1,5,5}};
    _inst.setAvailability(v);
    assertEquals("test_getAvailability: assertEquals", true , compare(availMatrix, _inst.getMatrixAvailability()));
  }

  public void test1_getAvailability(){
    Vector v = new Vector();
    v.add("1 1 1 1 5 1");
    v.add("1 1 1 5 5");
    int [][] availMatrix={{1,1,1,1,5},{1,1,1,5,5}};
    _inst.setAvailability(v);
    assertEquals("test1_getAvailability: assertEquals", false , compare(availMatrix, _inst.getMatrixAvailability()));
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