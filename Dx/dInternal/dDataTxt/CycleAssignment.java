package dInternal;

/**
 * <p>Title: DX</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
import dResources.DXObject;
import java.util.Calendar;

public class CycleAssignment extends DXObject{

  /**contains the day, the begin hour and the begin minute*/
  private int[] _dateAndTime={0,0,0};
  /**instructor name*/
  private String _instructor;
  /** room name*/
  private String _room;
  /** room is fixed*/
  private boolean _roomFixed= false;
  /**
   *Constructor
   */
  public CycleAssignment() {

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
    _instructor = instructor;
  }

  /**
   * set the room name
   * @param String the room name
   * */
  public void setRoom(String room){
    _room = room;
  }

  /**
   * set the room state
   * @param boolean the room state (true if room is fixed and false otherwise)
   * */
  public void setRoomState(boolean fixed){
    this._roomFixed = fixed;
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
  public String getInstructor(){
    return _instructor;
  }

  /**
   * get room name of the bloc in this week
   * @return String the room name
   * */
  public String getRoom(){
    return _room;
  }

  /**
   * get room state of the bloc in this week
   * @return boolean the room state (true if room is fixed and false otherwise)
   * */
  public boolean getRoomState(){
    return _roomFixed;
  }

}