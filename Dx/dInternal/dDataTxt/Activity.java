package dInternal.dData;

/**
 * <p>Title: DX</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam, alexander
 * @version 1.0
 */
import dInternal.dUtil.DXObject;


public class Activity extends DXObject{

  private String _departement="";//eg. g�nie electrique
  private String _activityType=""; // eg. informatique
  private int _activitySession=0;//
  private boolean _activityVisible= true;//
  /**  is in activities file the line between type of rooms and
   activity is fixed or not */
  public String _idemLine;
  private SetOfResources _setOfTypes; // contents Resources of class Type


  /**
   * Constructor
   * */
  public Activity() {
    //naturesList= new SetOfResources(0);
    //Resource groups = new Resource();
    _setOfTypes= new SetOfResources(0);
  }

  /**
   * add a nature object in the list
   * @param String the ID of the nature
   * @return boolean result of the operation
   * */
  public boolean addType(String id){
    //Resource actNature =
    Type nature= new Type();
    Resource actType = new Resource(id,nature);
    return _setOfTypes.addResource(actType,1);
  }

  /**
   * add a nature object in the list
   * @param String the ID of the nature
   * @param Nature the nature to be added
   * @return boolean result of the operation
   * */
  public boolean addType(Type type, String id){
    Resource actType = new Resource(id,type);
    return _setOfTypes.addResource(actType,1);
  }

  /**
   * remove a nature object from de list
   * @param String the ID of the nature
   * @return boolean result of the operation
   * */
  public boolean removeType(String id){
    return _setOfTypes.removeResource(id);
  }

  /**
   * set a nature object in the list
   * @param Resource the nature resource
   * @return boolean result of the operation
   * */
  public boolean setType(Resource type){
    return _setOfTypes.setResource(type);
  }

  /**
   * return a nature object from de the list
   * @param String the ID of the nature
   * @return Resource the nature object
   * */
  public Resource getType(String id){
    return _setOfTypes.getResource(id);
  }

  /**
   * return the nature list
   * @return SetOfResources the list of nature object
   * */
  public SetOfResources getSetOfTypes(){
    return _setOfTypes;
  }

  /**
   * set activity session
   * @param int the activity session
   * */
  public void setActivitySession(int activitySession){
    _activitySession= activitySession;
  }

  /**
   * get the activity session
   * @return int the activity session
   * */
  public int getActivitySession(){
    return _activitySession;
  }

  /**
   * set activity visibility
   * @param boolean the activity visibility
   * */
  public void setActivityVisibility(boolean visibility){
    _activityVisible= visibility;
  }

  /**
   * get the activity visibility
   * @return boolean the activity visibility
   * */
  public boolean getActivityVisibility(){
    return _activityVisible;
  }

  /**
   * set activity Department
   * @param String the activity Department
   * */
  public void setActivityDepartment(String department){
    _departement= department;
  }

  /**
   * get the activity Department
   * @return String the activity Department
   * */
  public String getActivityDepartment(){
    return _departement;
  }

  /**
   * set activity Type
   * @param String the activity Type
   * */
  public void setActivityType(String activityType){
    _activityType= activityType;
  }

  /**
   * get the activity Type
   * @return String the activity Type
   * */
  public String getActivityType(){
    return _activityType;
  }

  /**
   *This object (which is already a string!) is itself returned.
   * @return the string itself
   * */
  public String toWrite(){
    return _setOfTypes.toWrite();
  }

}