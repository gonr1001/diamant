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
  private Calendar _dateAndTime;
  /**instructor name*/
  private String _instructor;
  /** room name*/
  private String _room;

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
    _dateAndTime.set(0,0,day,hour,minute);
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
   * get date and time of the bloc in this week
   * @return Calendar the day, begin hour and begin minute
   * */
  public Calendar getDateAndTime(){
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

}