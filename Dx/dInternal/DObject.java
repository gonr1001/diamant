/**
*
* Title: DObject $Revision $  $Date: 2005-02-02 21:37:58 $
* Description: DObject is a class used to
*
*
* Copyright (c) 2001 by rgr.
* All rights reserved.
*
*
* This software is the confidential and proprietary information
* of rgr. ("Confidential Information").  You
* shall not disclose such Confidential Information and shall use
* it only in accordance with the terms of the license agreement
* you entered into with rgr.
*
* @version $ $
* @author  $Author: garr2701 $
* @since JDK1.3
*/

package dInternal;

public abstract class  DObject extends Object{
  public DObject() {
  }


  /**
   * this method is implemented in each resource object (Room, Instructor, Student
   * and Activity)
   * */
  
  public abstract long getSelectedField();/*{
     return -2;
  }
*/

  /**
   * Method to be implemented in each resource object. It compares if the argument
   * "value" has the same value of the field indicated by the fieldIndex
   * @param fieldIndex The index of the field. The value of the index is assigned in each
   * resource
   * @param value The value tibe compared
   * @return true if the field has the same value of the argument "value"
   */
  public boolean compareByField(int fieldIndex, String value) {
  	fieldIndex = fieldIndex+ 0;
  	value = ""+value;
    return false;
  }

  /**
   * Method to be implemented in each resource object. It sets the field indicated by the
   * argument "fieldValue" with the value "value"
   * @param fieldIndex. The index of the field. The value of the index is assigned in each
   * resource
   * @param value The value to be setted
   */
  
  public void setField(int fieldIndex, String value){
  	fieldIndex = fieldIndex+ 0;
  	value = ""+value;
  }

  /**
   *
   * */
  public String toWrite(){
    return "";
  }
  /**
   *
   * */
  public String externalKey(String str, String id){
  	str+="";
    return id;
  }

  /**
   *
   * */
  public boolean isEquals(DObject obj){
  	obj.toString();
    return true;
  }

  /**
   *
   * @return
   */
  public int[][] getMatrixAvailability(){
    int [][] mat= new int[2][2];
    return mat;
  }



  /**
   *
   * @param mat
   */
  public void setAvailability(int[][] mat){
  	mat[0][0]+=0;
  }



}