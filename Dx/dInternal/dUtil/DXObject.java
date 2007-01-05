//package dInternal.dUtil;
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
//public class  DXObject extends Object{
//
//
//  /**
//   * this method is implemented in each resource object (Room, Instructor, Student
//   * and Activity)
//   * */
//  public long getSelectedField(){
//     return -2;
//  }
//
//
//  /**
//   * Method to be implemented in each resource object. It compares if the argument
//   * "value" has the same value of the field indicated by the fieldIndex
//   * @param fieldIndex The index of the field. The value of the index is assigned in each
//   * resource
//   * @param value The value tibe compared
//   * @return true if the field has the same value of the argument "value"
//   */
//  public boolean compareByField(int fieldIndex, String value) {
//  	fieldIndex = fieldIndex+ 0;
//  	value = ""+value;
//    return false;
//  }
//
//  /**
//   * Method to be implemented in each resource object. It sets the field indicated by the
//   * argument "fieldValue" with the value "value"
//   * @param fieldIndex. The index of the field. The value of the index is assigned in each
//   * resource
//   * @param value The value to be setted
//   */
//  
//  public void setField(int fieldIndex, String value){
//  	fieldIndex = fieldIndex+ 0;
//  	value = ""+value;
//  }
//
//  /**
//   *
//   * */
//  public String toWrite(){
//
//    return "";
//  }
//  /**
//   *
//   * */
//  public String externalKey(String str, String id){
//  	str+="";
//    return id;
//  }
//
//  /**
//   *
//   * */
//  public boolean isEquals(DXObject obj){
//  	obj.toString();
//    return true;
//  }
//
//  /**
//   *
//   * @return
//   */
//  public int[][] getMatrixAvailability(){
//    int [][] mat= new int[2][2];
//    return mat;
//  }
//
//
//
//  /**
//   *
//   * @param mat
//   */
//  public void setAvailability(int[][] mat){
//  	mat[0][0]+=0;
//  }
//
//
//
//}