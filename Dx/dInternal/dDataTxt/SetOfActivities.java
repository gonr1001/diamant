package dInternal;

/**
 * <p>Title: DX</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author  ysyam, alexander
 * @version 1.0
 */

public class ActivitiesList extends ResourceList{

  /**activities in text format*/
  private byte[] _dataloaded;

  /**
   * Constructor
   * */
  public ActivitiesList(byte[] dataloaded) {
    super(dataloaded,1);
    _dataloaded = dataloaded;
  }

  /**
   * analyse activities datas by a finished states machine
   * INPUT: beginPosition, an integer (start position of the finished states machine)
   * OUTPUT: boolean. "true" the analysis proceeded successfully and false otherwise
   * */
  public boolean analyseTokens(int beginPosition){
    return true;
  }

  /**
   * build activitiesList from activities datas by a finished states machine
   * INPUT: beginPosition, an integer (start position of the finished states machine)
   * OUTPUT: boolean. "true" the analysis proceeded successfully and false otherwise
   * */
  public void buildActivitiesList(int beginPosition){

  }

  /**
   * add an activity object in the list
   * @param String the courseName of the activity
   * @return boolean result of the operation
   * */
  public boolean addActivity(String courseName){
    if(courseName.length()>=6){
      Activity activity = new Activity();
      Resource activ= new Resource(courseName.substring(0,_COURSENAMELENGTH), activity);
      return addResource(activ,1);
    }
    return false;
  }

  /**
   * add an activity object in the list
   * @param Resource the the activity resource
   * @return boolean result of the operation
   * */
  public boolean addActivity(Resource activity){
      return addResource(activity,1);
  }

  /**
   * get an activity object from de the list
   * @param String the courseName of the activity
   * @return Resource the activity Resource
   * */
  public Resource getActivity(String courseName){
    if(courseName.length()>=6){
       return getResource(courseName.substring(0,_COURSENAMELENGTH));
    }
    return null;
  }

  /**
   * get an activity object from de the list
   * @param long the key of the activity
   * @return Resource the activity Resource
   * */
  public Resource getActivity(long courseKey){
      return getResource(courseKey);
  }

  /**
   * set an activity Resource object in the list
   * @param Resource the activity resource
   * @return boolean result of the operation
   * */
  public boolean setActivity(Resource activity){
    return setResource(activity);
  }

  /**
   * remove an activity Resource from de list
   * @param long the key of the activity
   * @return boolean result of the operation
   * */
  public boolean removeActivity(long activityKey){

    return removeResource(activityKey);
  }

  /**
   * remove an activity Resource from de list
   * @param String the courseName of the activity
   * @return boolean result of the operation
   * */
  public boolean removeActivity(String courseName){
    if(courseName.length()>=6){
       return removeResource(courseName.substring(0,_COURSENAMELENGTH));
    }
   return false;
  }

  private int _COURSENAMELENGTH=6;
}