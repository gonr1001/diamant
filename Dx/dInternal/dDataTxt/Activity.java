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
import java.util.Vector;
import dResources.DConst;


public class Activity extends DXObject{

  /* Each field is linked to an index who serves for filtering and for selecting
  * an object.
  * Indexes :
  * _departement -> 0
  * _activityType -> 1
  * _activitySession -> 2
  * _activityVisible -> 3
  */
  private String _departement="";//eg. génie electrique
  private String _activityType=""; // eg. informatique
  private int _activitySession=0;//
  private boolean _activityVisible= true;//
  /**  is in activities file the line between type of rooms and
   activity is fixed or not */
  private String _idemLine;
  private SetOfResources _setOfTypes; // contents Resources of class Type
  private Vector _studentRegistered; // it contains key of students


  /**
   * Constructor
   * */
  public Activity() {
    //naturesList= new SetOfResources(0);
    //Resource groups = new Resource();
    _setOfTypes= new SetOfResources(0);
    _studentRegistered= new Vector(1);
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
   * return the _studentRegistered vector
   * @return
   */
  public Vector getStudentRegistered(){
    return _studentRegistered;
  }


  /**
   * add a studentKey in the _studentRegistered vector if it doesn't already exist
   * and return true. It return false otherwise
   * @return
   */
  public boolean addStudentRegistered(long studentKey){
    if(!_studentRegistered.contains(Long.toString(studentKey))){
      _studentRegistered.add(Long.toString(studentKey));
      return true;
    }
    return false;
  }

  /**
   * test if _studentRegistered contains a specified element
   * @param studentKey
   * @return
   */
  public boolean isStudentRegisteredContains(long studentKey){
    return _studentRegistered.contains(Long.toString(studentKey));
  }

  /**
   * It compares a field with the value defined by the argument "value"
   * @param fieldIndex the index of the field
   * @param value the value to be compared
   * @return true if the attribute value is equal to the argument "value"
   */
  public boolean compareByField(int fieldIndex, String value){
   switch(fieldIndex){
      case 0:
        if (_departement.equals(value))
          return true;
      case 1:
        if (_activityType.equals(value))
          return true;
      case 2:
        int intValue = Integer.parseInt(value);
        if (_activitySession == intValue)
          return true;
      case 3:
        boolean boolValue = Boolean.valueOf(value).booleanValue();

        if (_activityVisible == boolValue)
          return true;
    }
    return false;
 }

 /**
  * Set a field according to the argument fieldIndex
  * @param fieldIndex The index of a field
  * @param value The set value to the field
  */
 public void setField(int fieldIndex, String value){
   switch(fieldIndex){
      case 0:
        setActivityDepartment(value);
      case 1:
        setActivityType(value);
      case 2:
        int intValue = Integer.parseInt(value);
        setActivitySession(intValue);
      case 3:
        boolean boolValue = Boolean.valueOf(value).booleanValue();
        setActivityVisibility(boolValue);
    }//end switch
 }//end method


  /**
   *This object (which is already a string!) is itself returned.
   * @return the string itself
   * */
  public String toWrite(){
    return _setOfTypes.toWrite();
  }

  /**
  * compare this resource with the specified resource
  * @param resource the specified resource
  * @return bolean true if this resource and the specified resource are equals
  * false if they are not equals
  * */
 public boolean isEquals(DXObject act){
   Activity activity = (Activity)act;
   if(!this._setOfTypes.isEquals( activity._setOfTypes))
     return false;
   if(this._activitySession!= activity._activitySession)
      return false;
   if(this._activityVisible!= activity._activityVisible)
      return false;
   return true;
  }

  /**
   *
   * @param idL
   */
  public void setIdemLine(String idL){
    _idemLine= idL;
  }

  /**
   *
   * @return
   */
  public String getIdemLine(){
    return _idemLine;
  }



}