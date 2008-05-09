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
//
////public class RoomAttachTest {
//import java.util.Vector;
//
//import dInternal.dData.dRooms.RoomAttach;
//
//import junit.framework.Test;
//import junit.framework.TestCase;
//import junit.framework.TestSuite;
////import dInternal.dDataTxt.RoomAttach;
//
//public class RoomAttachTest extends TestCase {
//  //private StudentAttach _student;
//
//  public RoomAttachTest(String name) {
//    super(name);
//  }
//
//  public static Test suite() {
//   // the type safe way is in SimpleTest
//   // the dynamic way :
//   return new TestSuite(RoomAttachTest.class);
//  } // end suite
//
//
//  public void test_addAvailability(){
//   Vector <String>v = new Vector<String>();
//   v.add("1 1 1 1 5");
//   v.add("1 1 1 5 5");
//   RoomAttach roomAttach = new RoomAttach();
//   roomAttach.addAvailability("1 1 1 1 5");
//   roomAttach.addAvailability("1 1 1 5 5");
//   assertEquals("test_addAvailability: assertEquals", roomAttach.getVectorAvailability(), v);
// }
//
// public void test_removeAvailability(){
//	 Vector <String>v = new Vector<String>();
//   v.add("1 1 1 1 5");
//   //v.add("1 1 1 5 5");
//   RoomAttach roomAttach = new RoomAttach();
//   roomAttach.addAvailability("1 1 1 1 5");
//   roomAttach.addAvailability("1 1 1 5 5");
//   //assertEquals("Instructor Dispo equals :", _inst.getVectorAvailability(), v);
//   roomAttach.removeAvailDay(2);
//   assertEquals("test_removeAvailability: assertEquals", roomAttach.getVectorAvailability(), v);
// }
//
// public void test_setAvailability(){
//	 Vector <String>v = new Vector<String>();
//   v.add("1 1 1 1 5");
//   v.add("1 1 1 5 5");
//   RoomAttach roomAttach = new RoomAttach();
//   roomAttach.setAvailability(v);
//   assertEquals("test_setAvailability: assertEquals", roomAttach.getVectorAvailability(), v);
//  }
//
//  public void test1_setAvailability(){
//	  Vector <String>v = new Vector<String>();
//    v.add("1 1 1 1 5");
//    v.add("1 1 1 5 5");
//    RoomAttach roomAttach = new RoomAttach();
//    int [][] availMatrix={{1,1,1,1,5},{1,1,1,5,5}};
//    roomAttach.setAvailability(availMatrix);
//    assertEquals("test1_getAvailability: assertEquals", v , roomAttach.getVectorAvailability());
//  }
//
//  public void test_getAvailability(){
//	  Vector <String>v = new Vector<String>();
//    v.add("1 1 1 1 5");
//    v.add("1 1 1 5 5");
//    RoomAttach roomAttach = new RoomAttach();
//    int [][] availMatrix={{1,1,1,1,5},{1,1,1,5,5}};
//    roomAttach.setAvailability(v);
//    assertEquals("test_getAvailability: assertEquals", true, compare(availMatrix, roomAttach.getMatrixAvailability()));
//  }
//
//  public void test1_getAvailability(){
//	  Vector <String>v = new Vector<String>();
//    v.add("1 1 1 1 5 1");
//    v.add("1 1 1 5 5");
//    RoomAttach roomAttach = new RoomAttach();
//    int [][] availMatrix={{1,1,1,1,5},{1,1,1,5,5}};
//    roomAttach.setAvailability(v);
//    assertEquals("test1_getAvailability: assertEquals", false, compare(availMatrix, roomAttach.getMatrixAvailability()));
//  }
//
//
//  /**
//   * compare matrix1 and matrix2
//   * @param int[][] the matrix1
//   * @param int[][] the matrix2
//   * @return boolean the result
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
//
//}