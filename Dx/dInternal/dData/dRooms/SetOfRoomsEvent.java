package dInternal.dData.dRooms;



/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */


import java.util.EventObject;

public class SetOfRoomsEvent extends EventObject{

  public SetOfRoomsEvent(SetOfRooms source) {
    super (source);
  }
}