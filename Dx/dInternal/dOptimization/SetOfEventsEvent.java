package dInternal.dConditionsTest;



/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr
 * @version 1.0
 */


import java.util.EventObject;

public class SetOfEventsEvent extends EventObject{

  public SetOfEventsEvent(SetOfEvents source) {
    super (source);
  }
}