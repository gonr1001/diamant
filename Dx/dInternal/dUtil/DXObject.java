package dInternal.dUtil;

/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */

public class DXObject extends Object{
  public DXObject() {
  }

  /**
   * this method is implemented in each resource object (Room, Instructor, Student
   * and Activity)
   * */
  public int getSelectedField(int choice){
     return -2;
  }

  public boolean compareByField(int choice, String value){
    return false;
  }

  public void setField(int fieldIndex, String value){

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
    return id;
  }

}