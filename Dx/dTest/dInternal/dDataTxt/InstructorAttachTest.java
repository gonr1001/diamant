//package dTest.dInternal.dDataTxt;
//
///**
// * <p>Title: Diamant 1.5</p>
// * <p>Description: exam timetable construction with Condition Pattern</p>
// * <p>Copyright: Copyright (c) 2002</p>
// * <p>Company: UdeS</p>
// * @author rgr, ysyam, alexander
// * @version 1.0
// */
//import java.util.Vector;
//
//import dInternal.dData.dInstructors.InstructorAttach;
//
//import junit.framework.Test;
//import junit.framework.TestCase;
//import junit.framework.TestSuite;
//
//
//public class InstructorAttachTest extends TestCase {
//  private InstructorAttach _inst;
//
//  public InstructorAttachTest(String name) {
//    super(name);
//    _inst = new InstructorAttach();
//  }
//
//  public static Test suite() {
//   // the type safe way is in SimpleTest
//   // the dynamic way :
//   return new TestSuite(InstructorAttachTest.class);
//  } // end suite
//
//  public void test_addAvailability(){
//    Vector <String> v = new Vector <String>();
//    v.add("1 1 1 1 5");
//    v.add("1 1 1 5 5");
//    _inst.addAvailability("1 1 1 1 5");
//    _inst.addAvailability("1 1 1 5 5");
//    assertEquals("test_addAvailability: assertEquals", _inst.getVectorAvailability(), v);
//  }
//
//  public void test_removeAvailability(){
//	  Vector <String> v = new Vector <String>();
//    v.add("1 1 1 1 5");
//    //v.add("1 1 1 5 5");
//    _inst.addAvailability("1 1 1 1 5");
//    _inst.addAvailability("1 1 1 5 5");
//    //assertEquals("Instructor Dispo equals :", _inst.getVectorAvailability(), v);
//    _inst.removeAvailability(2);
//    assertEquals("test_removeAvailability: assertEquals", _inst.getVectorAvailability(), v);
//  }
//
//  public void test_setAvailability(){
//	  Vector <String> v = new Vector <String>();
//    v.add("1 1 1 1 5");
//    v.add("1 1 1 5 5");
//    _inst.setAvailability(v);
//    assertEquals("test_setAvailability: assertEquals", _inst.getVectorAvailability(), v);
//  }
//
//  public void test1_setAvailability(){
//	  Vector <String> v = new Vector <String>();
//    v.add("1 1 1 1 5");
//    v.add("1 1 1 5 5");
//    int [][] availMatrix={{1,1,1,1,5},{1,1,1,5,5}};
//    _inst.setAvailability(availMatrix);
//    assertEquals("test1_setAvailability: assertEquals", v, _inst.getVectorAvailability());
//  }
//
//  public void test_getAvailability(){
//	  Vector <String> v = new Vector <String>();
//    v.add("1 1 1 1 5");
//    v.add("1 1 1 5 5");
//    int [][] availMatrix={{1,1,1,1,5},{1,1,1,5,5}};
//    _inst.setAvailability(v);
//    assertEquals("test_getAvailability: assertEquals", true, compare(availMatrix, _inst.getMatrixAvailability()));
//  }
//
//  public void test1_getAvailability(){
//	  Vector <String> v = new Vector <String>();
//    v.add("1 1 1 1 5 1");
//    v.add("1 1 1 5 5");
//    int [][] availMatrix={{1,1,1,1,5},{1,1,1,5,5}};
//    _inst.setAvailability(v);
//    assertEquals("test1_getAvailability: assertEquals", false, compare(availMatrix, _inst.getMatrixAvailability()));
//  }
//
//
//  /**
//   * */
//  private boolean compare(int[][] matrix1, int[][] matrix2){
//    if(matrix1.length== matrix2.length){
//      for (int i=0; i< matrix1.length; i++){
//        if (matrix1[i].length== matrix2[i].length){
//          for(int j=0; j< matrix1[i].length; j++)
//            if (matrix1[i][j]!=matrix2[i][j])
//              return false;
//        }else
//          return false;
//      }
//    }
//    else
//      return false;
//    return true;
//  }
//}