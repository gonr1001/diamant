package dInternal.dData;

/**
 * <p>Title: DX</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
import dInternal.dUtil.DXObject;
import java.util.Calendar;

public class Assignment extends DXObject{

  /**contains the day, the begin hour and the begin minute*/
  private int[] _dateAndTime={0,0,0};
  /**instructor name valid only for initialization*/
  private String _instructorName;
  /** instructor key */
  long  _instructorKey = -1;
  /** room valid only for initialization*/
  private String _roomName;
  /** room key */
  long  _roomKey = -1;
  /** room is fixed*/
  private boolean _roomFixed= false;
  /**
   *Constructor
   */
  public Assignment() {

  }

  /**
   * set date and time of the bloc in this week
   * @param int the day
   * @param int the begin hour
   * @param int the begin minute
   * */
  public void setDateAndTime(int day, int hour, int minute){
    _dateAndTime[0]=day;
    _dateAndTime[1]=hour;
    _dateAndTime[2]=minute;
  }

  /**
   * set the instructor name
   * @param String the instructor name
   * */
  public void setInstructor(String instructor){
    _instructorName = instructor;
  }

  /**
   * set the instructor key
   * @param long the instructor key
   * */
  public void setInstructor(long instructor){
    _instructorKey = instructor;
    _instructorName = null;
  }

  /**
   * set the room name
   * @param String the room name
   * */
  public void setRoom(String room){
    _roomName = room;
  }

  /**
   * set the room key
   * @param long the room key
   * */
  public void setRoom(long room){
    _roomKey = room;
    _roomName = null;
  }
  /**
   * set the room state
   * @param boolean the room state (true if room is fixed and false otherwise)
   * */
  public void setRoomState(boolean fixed){
    _roomFixed = fixed;
  }

  /**
   * get date and time of the bloc in this week
   * @return Calendar the day, begin hour and begin minute
   * */
  public int[] getDateAndTime(){
    return _dateAndTime;
  }

  /**
   * get instructor name of the bloc in this week
   * @return String the instructor name
   * */
  public String getInstructorName(){
    return _instructorName;
  }

  /**
   * get instructor key of the unit
   * @return String the instructor key
   * */
  public long getInstructorKey(){
    return _instructorKey;
  }

  /**
   * get room name of the bloc in this week
   * @return String the room name
   * */
  public String getRoomName(){
    return _roomName;
  }

  /**
   * get room key of the unit
   * @return long the room key
   * */
  public long getRoomKey(){
    return _roomKey;
  }
  /**
   * get room state of the bloc in this week
   * @return boolean the room state (true if room is fixed and false otherwise)
   * */
  public boolean getRoomState(){
    return _roomFixed;
  }

  /**
* compare this resource with the specified resource
* @param resource the specified resource
* @return bolean true if this resource and the specified resource are equals
* false if they are not equals
* */
  public boolean isEquals(DXObject ass){
    Assignment assmt = (Assignment)ass;
    if(this._instructorKey!= assmt._instructorKey)
      return false;

    return true;
  }

}