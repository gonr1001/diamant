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

public class Bloc extends DXObject{

  /** duration of the bloc (in minutes)*/
  private int _duration;
  /** prefere sequence in a day
   *-1= no prefer sequence; 0= AM; 1=PM; 2= evening
   */
  private int _preferSequence=-1;
  /**if bloc is fixed in a period*/
  private boolean _fixed;
  /**if bloc is solidify in a period*/
  private boolean _solidify;
  /**prefer function rooms of this bloc*/
  private SetOfResources _preferFunctionSetOfRooms;
  /**activities that must be placed before this bloc*/
  private SetOfResources _activitiesBefore;
  /**activities that must be placed after this bloc*/
  private SetOfResources _activitiesAfter;
  /**all cycles where bloc is assigned*/
  private SetOfResources _cycleAssignmentList;

  /**
   * Constructor
   * */
  public Bloc() {
    _preferFunctionSetOfRooms = new SetOfResources(0);
    _activitiesBefore = new SetOfResources(0);
    _activitiesAfter = new SetOfResources(0);
    _cycleAssignmentList = new SetOfResources(0);
  }

  /**
   * set duration
   * @param int the duration in minutes
   * */
  public void setDuration(int duration){
    _duration = duration;
  }

  /**
   * get duration
   * @return int the duration in hour
   * */
  public int getDuration(){
    return _duration;
  }

  /**
   * set if bloc is fixed
   * @param boolean the bloc state
   * */
  public void setFixed(boolean fixed){
    _fixed = fixed;
  }

  /**
   * get fixed state of the bloc
   * @param boolean the bloc state
   * */
  public boolean getFixed(){
    return _fixed;
  }

  /**
   * set if bloc is solidify
   * @param boolean the bloc state
   * */
  public void setSolidify(boolean solidify){
    _solidify = solidify;
  }

  /**
   * set prefer sequence
   * *-1= no prefer sequence; 0= AM; 1=PM; 2= evening
   * @param int the prefer sequence
   * */
  public void setPreferSequence(int prefer){
    _preferSequence = prefer;
  }

  /**
   * add a cycle for period assignment
   * @param String the cycle ID
   * @return boolean the operation result
   * */
  public boolean addCycleAssignment(String cycle){
    Resource cycleAss = new Resource(cycle, new DXObject());
    return _cycleAssignmentList.addResource(cycleAss,1);
  }

  /**
   * get a cycle for period assignment
   * @param String the cycle ID
   * @return Resource the operation result
   * */
  public Resource getCycleAssignment(String cycle){
    return _cycleAssignmentList.getResource(cycle);
  }

  /**
   * get the cycle assignment list
   * @return SetOfResources the cycle assignment list
   * */
  public SetOfResources getCycleAssignmentList(){
    return _cycleAssignmentList;
  }

  /**
   * add a cycle for period assignment
   * @param Resource the cycleAssignment resource
   * @return boolean the operation result
   * */
  public boolean addCycleAssignment(Resource cycleAss){
    return _cycleAssignmentList.addResource(cycleAss,1);
  }
  /**
   * add activity in activity before list
   * @param String the activity name
   * @return boolean the operation result
   * */
  public boolean addActivityBefore(String activityName){
    Resource activityBefore= new Resource(activityName, new DXObject());
    return _activitiesBefore.addResource(activityBefore,1);
  }

  /**
   * add activity in activity after list
   * @param String the activity name
   * @return boolean the operation result
   * */
  public boolean addActivityAfter(String activityName){
    Resource activityAfter= new Resource(activityName, new DXObject());
    return _activitiesAfter.addResource(activityAfter,1);
  }

  /**
   * add activity prefer function room
   * @param String the room function
   * @return boolean the operation result
   * */
  public boolean addPreferFunctionRoom(String function){
    Resource functionRoom= new Resource(function, new DXObject());
    return _preferFunctionSetOfRooms.addResource(functionRoom,1);
  }

  /**
   * get activity prefer function room
   * @param String the room function
   * @return boolean the operation result
   * */
  public SetOfResources getPreferFunctionRoom(){
    return _preferFunctionSetOfRooms;
  }

  /**
   * set activity prefer function room
   * @param String the room function
   * @return boolean the operation result
   * */
  public void setPreferFunctionRoom(SetOfResources preferFuncList){
    _preferFunctionSetOfRooms=preferFuncList;
  }

  /**
   * remove activity in activity before list
   * @param String the activity name
   * @return boolean the operation result
   * */
  public boolean removeActivityBefore(String activityName){
    return _activitiesBefore.removeResource(activityName);
  }

  /**
   * remove activity in activity after list
   * @param String the activity name
   * @return boolean the operation result
   * */
  public boolean removeActivityAfter(String activityName){
    return _activitiesAfter.removeResource(activityName);
  }

  /**
   * remove activity prefer function room
   * @param String the room function
   * @return boolean the operation result
   * */
  public boolean removePreferFunctionRoom(String activityName){
    return _preferFunctionSetOfRooms.removeResource(activityName);
  }

  /**
   * remove a cycle from a bloc
   * @param String the cycle ID
   * @return boolean the operation result
   * */
  public boolean removeCycleAssignment(String cycle){
    return _cycleAssignmentList.removeResource(cycle);
  }

  /**
   *This object (which is already a string!) is itself returned.
   * @return the string itself
   * */
  public String toWrite(){
   return "";
  }

}