package dInternal.dXMLData.rooms;



/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */


import java.util.EventObject;

public class SetOfRoomsCategoriesEvent extends EventObject{

  public SetOfRoomsCategoriesEvent(SetOfRoomsCategories source) {
    super (source);
  }
}