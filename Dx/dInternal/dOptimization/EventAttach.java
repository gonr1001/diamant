package dInternal.dConditionsTest;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */

import dInternal.dUtil.DXObject;
import dInternal.dData.SetOfResources;
import java.util.Vector;

public class EventAttach extends DXObject {

  /** _principalRescKey is the composition of activity, type, section and
   * unity keys of an activity wich is represent in the following format a.b.c.d
   */
  private String _principalRescKey;
  private int _eventDuration=0;
  private long _instructorRescKey; // the instructor key
  private long _roomRescKey; // the room key
  // the student reference will be found in the conflicts matrix
  private Vector _tabuList; //
  private boolean isAssign=false;// tell if this event is place in the timetable
   private boolean isPermanent=false;// tell if this event is permanent in the timetable
  private String _ttsKey="";// give the key of the period where event is place
  //is in a.b.c format where a = day, b= sequence, c = period

  /**
   * Constructor
   * @param princKey
   * @param key1
   * @param key2
   */
  public EventAttach(String princKey, long key1, long key2, int eventDuration, String eventPeriod) {
    _principalRescKey = princKey;
    _instructorRescKey = key1;
    _roomRescKey = key2;
    _eventDuration = eventDuration;
    _ttsKey = eventPeriod;
    _tabuList = new Vector();
  }

  public String getPrincipalRescKey(){
    return _principalRescKey;
  }

  public long getInstructorKey(){
    return _instructorRescKey;
  }

 /* public void setInstructorKey(long key){
    _instructorRescKey= key;
  }*/

  public long getRoomKey(){
   return _roomRescKey;
  }

 /* public void setRoomKey(long key){
   _roomRescKey= key;
  }*/

  /**
   *
   * @return
   */
  public String getPeriodKey(){
    return _ttsKey;
  }

  /**
   *
   * @return
   */
  public int getDuration(){
    return _eventDuration;
  }


  /**
   * Tests if the specified string is a component in the tabulist vector.
   * @param princKey
   * @return
   */
  public boolean isInTabuList(String princKey){
    return _tabuList.contains(princKey);
  }

  /**
   * Removes the first occurrence of the specified element in the tabulist vector If
   * it does not contain the element, it is unchanged.
   * @param princKey
   * @return
   */
  public boolean removeFromTabuList(String princKey){
    return _tabuList.remove(princKey);
  }

  /**
   * Adds the specified element to the end of the tabulist vector or let it unchanged
   * if the element already exist in the vector
   * @param princKey
   * @return
   */
  public boolean addToTabuList(String princKey){
    if (_tabuList.contains(princKey))
      return _tabuList.add(princKey);
    return false;
  }

  public void setAssignState(boolean state){
    isAssign= state;
  }

  public boolean getAssignState(){
   return isAssign;
 }

 public void setPermanentState(boolean state){
    isPermanent= state;
  }

  public boolean getPermanentState(){
   return isPermanent;
 }


  /**
   * set events keys by the appropriate field
   * @param field 0= set principal key, 1= set secondary key1, 2= set secondary key2
   * @param value
   */
  public void setKey(int field, String value){
    switch(field){
      case 0: _principalRescKey = value;
        break;
      case 1: _instructorRescKey = Long.parseLong(value);
        break;
      case 2: _roomRescKey = Long.parseLong(value);
        break;
      case 3: _eventDuration = Integer.parseInt(value);
        break;
      case 4: _ttsKey = value;
        break;
    }
  }
}