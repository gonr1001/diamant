package dInternal.dConditionsTest;



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
import dInternal.DModelEvent;

public interface SetOfEventsListener extends EventListener{
  void changeInSetOfEvents(SetOfEventsEvent e, Component c);
}