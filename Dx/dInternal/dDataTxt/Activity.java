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

  private String _departement="";//eg. génie electrique
  private String _activityType=""; // eg. informatique
  private int _activitySession=0;//
  private boolean _activityVisible= true;//
  /**  is in activities file the line between type of rooms and
   activity is fixed or not */
  public String _idemLine;
  private SetOfResources _natures;


  /**
   * Constructor
   * */
  public Activity() {
    //naturesList= new SetOfResources(0);
    //Resource groups = new Resource();
    _natures= new SetOfResources(0);
  }

  /**
   * add a nature object in the list
   * @param String the ID of the nature
   * @return boolean result of the operation
   * */
  public boolean addNature(String id){
    //Resource actNature =
    Nature nature= new Nature();
    Resource actNature = new Resource(id,nature);
    return _natures.addResource(actNature,1);
  }

  /**
   * add a nature object in the list
   * @param String the ID of the nature
   * @param Nature the nature to be added
   * @return boolean result of the operation
   * */
  public boolean addNature(Nature nature, String id){
    Resource actNature = new Resource(id,nature);
    return _natures.addResource(actNature,1);
  }

  /**
   * remove a nature object from de list
   * @param String the ID of the nature
   * @return boolean result of the operation
   * */
  public boolean removeNature(String id){
    return _natures.removeResource(id);
  }

  /**
   * set a nature object in the list
   * @param Resource the nature resource
   * @return boolean result of the operation
   * */
  public boolean setNature(Resource nature){
    return _natures.setResource(nature);
  }

  /**
   * return a nature object from de the list
   * @param String the ID of the nature
   * @return Resource the nature object
   * */
  public Resource getNature(String id){
    return _natures.getResource(id);
  }

  /**
   * return the nature list
   * @return SetOfResources the list of nature object
   * */
  public SetOfResources getNaturesList(){
    return _natures;
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
    return _natures.toWrite();
  }

}