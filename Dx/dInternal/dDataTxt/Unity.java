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

public class Unity extends DXObject{

  /** duration of the bloc (in minutes)*/
  private int _duration;
  /** prefere sequence in a day
   *-1= no prefer sequence; 0= AM; 1=PM; 2= evening
   */
  private int _preferSequence=-1;
  /**if bloc is assigned in a period*/
  private boolean _assign;
  /**if bloc is permanent assigned in a period*/
  private boolean _permanent;
  /**prefer function rooms of this bloc*/
  private SetOfResources _preferFunctionSetOfRooms;
  /**activities that must be placed before this bloc*/
  private SetOfResources _activitiesBefore;
  /**activities that must be placed after this bloc*/
  private SetOfResources _activitiesAfter;
  /**all cycles where bloc is assigned*/
  private SetOfResources _setOfAssignments;

  private boolean _isCyclic= true;

  /**
   * Constructor
   * */
  public Unity() {
    _preferFunctionSetOfRooms = new SetOfResources(0);
    _activitiesBefore = new SetOfResources(0);
    _activitiesAfter = new SetOfResources(0);
    _setOfAssignments = new SetOfResources(0);
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
  public void setAssign(boolean assign){
    _assign = assign;
  }

  /**
   * get fixed state of the bloc
   * @param boolean the bloc state
   * */
  public boolean isAssign(){
    return _assign;
  }

  /**
   * is permanent state of the bloc
   * @param boolean the bloc state
   * */
  public boolean isPermanent(){
    return _permanent;
  }

  /**
   * set if bloc is solidify
   * @param boolean the bloc state
   * */
  public void setPermanent(boolean permanent){
    _permanent = permanent;
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
  public boolean addAssignment(String cycle){
    return _setOfAssignments.addResource(new Resource(cycle, new DXObject()),1);
  }

  /**
   * get a cycle for period assignment
   * @param String the cycle ID
   * @return Resource the operation result
   * */
  public Resource getAssignment(String cycle){
    return _setOfAssignments.getResource(cycle);
  }

  /**
   * get the cycle assignment list
   * @return SetOfResources the cycle assignment list
   * */
  public SetOfResources getSetOfAssignments(){
    return _setOfAssignments;
  }

  /**
   * add a cycle for period assignment
   * @param Resource the cycleAssignment resource
   * @return boolean the operation result
   * */
  public boolean addAssignment(Resource cycleAss){
    return _setOfAssignments.addResource(cycleAss,1);
  }
  /**
   * add activity in activity before list
   * @param String the activity name
   * @return boolean the operation result
   * */
  public boolean addActivityBefore(String activityName){
    return _activitiesBefore.addResource(new Resource(activityName, new DXObject()),1);
  }

  /**
   * add activity in activity after list
   * @param String the activity name
   * @return boolean the operation result
   * */
  public boolean addActivityAfter(String activityName){
    return _activitiesAfter.addResource(new Resource(activityName, new DXObject()),1);
  }

  /**
   * add activity prefer function room
   * @param String the room function
   * @return boolean the operation result
   * */
  public boolean addPreferFunctionRoom(String function){
    return _preferFunctionSetOfRooms.addResource(new Resource(function, new DXObject()),1);
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
    return _setOfAssignments.removeResource(cycle);
  }

  /**
   *This object (which is already a string!) is itself returned.
   * @return the string itself
   * */
  public String toWrite(){
   return "";
  }
  /**
* compare this resource with the specified resource
* @param resource the specified resource
* @return bolean true if this resource and the specified resource are equals
* false if they are not equals
* */
  public boolean isEquals(DXObject unit){
    Unity unity = (Unity)unit;
    if(this._assign!= unity._assign)
      return false;
    if(this._duration!= unity._duration)
      return false;
    if(this._isCyclic!= unity._isCyclic)
      return false;
    if(this._permanent!= unity._permanent)
      return false;
    if(this._preferSequence!= unity._preferSequence)
      return false;
    if(!this._setOfAssignments.isEquals( unity._setOfAssignments))
      return false;
    return true;
  }

}