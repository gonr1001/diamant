package dInternal.dData;



/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr
 * @version 1.0
 */

import java.util.*;
import java.awt.Component;

public interface SetOfRoomsListener extends EventListener{
  void changeInSetOfRooms(SetOfRoomsEvent e, Component c);
}