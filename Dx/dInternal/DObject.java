/**
 * Created on Mar 25, 2004
 * 
 * 
 * Title: DObject 
 * Description: DObject is a class used to
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
 * 
 * 
 */
package dInternal;

public abstract class  DObject extends Object{

  /**
   * this method is implemented in each resource object (Room, Instructor, Student
   * and Activity)
   * */
  
  public abstract long getSelectedField();

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
    return false; // XXXX Pascal: ???
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
  	value = ""+value; // XXXX Pascal: ????
  }

  /**
   *
   * */
  public String toWrite(){
    return ""; // XXXX Pascal: ?
  }
  /**
   *
   * */
  public String externalKey(String str, String id){
    return str+id; // XXXX Pascal: Hmmmm
  }

  /**
   *
   * */
  public boolean isEquals(DObject obj){
  	obj.toString();
    return true; // XXXX Pascal: ?
  }

  /**
   *
   * @return
   */
  public int[][] getMatrixAvailability(){
    int [][] mat= new int[2][2];
    return mat; // XXXX Pascal: ?
  }



  /**
   *
   * @param mat
   */
  public void setAvailability(int[][] mat){
  	mat[0][0]+=0;// XXXX Pascal: ?
  }


/**
 * @param field
 * @return
 */
public long getSelectedField(int field) {
	field+=0;
	return 0;// XXXX Pascal: ?
}



}