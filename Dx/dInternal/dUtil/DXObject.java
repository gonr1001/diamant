package dResources;

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
   * this method is implements in each resource object (Room, Instructor, Student
   * and activity)
   * */
  public int getSelectedField(int choice){
     return -2;
  }
}