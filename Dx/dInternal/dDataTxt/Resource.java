package dInternal.dData;

/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
import dInternal.dUtil.DXObject;

public class Resource {
  private static final String CR_LF = "\r\n";
  /**show if the resource has been manualy added, or automatic (during extract data)*/
  private boolean _manualCreated;
  /**Resource key*/
  private long _resourcekey=0;
  /**Resource ID it can be a name, an ID, a CIP code*/
  private String _resourceID;
  /**the resource temporary message. the message to be write before _resourceID
 *    in toString method*/
  private String _tempMessage="";
  /**Resource Object*/
  private DXObject _resourceObjet;

  /**
   * Constructor
   * @param String the id of the Resource
   * @param DXObject the object to set in the _resourceObjet field
   * */
  public Resource(String id, DXObject obj) {
    _resourceID = id;
    //_resourceObjet = new DXObject();
    _resourceObjet = obj;
  }

  /**
   * Return the resource key
   * @return long the resource key
   * */
  public long getKey(){
    return _resourcekey;
  }

  /**
   *set the resource key
   * @param long the resource key
   * */
  protected void setKey(long k){
    _resourcekey=k;
  }

  /**
   * Return the resource ID
   * @return String the resource ID
   * */
  public String getID(){
    return _resourceID;
  }

  /**
   * Return the resource Object
   * @return DXObject the resource Object
   * */
  public DXObject getObject(){
    return _resourceObjet;
  }

  /**
   *set the resource Object
   * @param DXObject the resource Object
   * */
  public void setObject(DXObject obj){
    //_resourceObjet = new DXObject();
    _resourceObjet = obj;
  }

  /**
   *set the resource ID
   * @param String the resource ID
   * */
  public void setID(String id){
    _resourceID = id;
  }

  /**
   *set the resource temporary message
   * @param String the resource temporary message
   * */
  public void setMessage(String message){
    _tempMessage = message;
  }

  /**
   *set if the resource has been manualy added, or automatic (during extract data)
   *@param boolean the resource state
   * */
  public void setIfIsManualCreated(boolean manual){
    _manualCreated = manual;
  }

  /**
   * Return the resource state showing if the resource has been manualy added,
   * or automatic (during extract data)
   * @return boolean the resource state
   * */
  public boolean getIfIsManualCreated(){
    return _manualCreated;
  }

  /**
   *This object (which is already a string!) is itself returned.
   * @return the string itself
   * */
  public String toString(String separator){
    String instInfo;
    String id= _resourceID;
    if (_tempMessage.length()!=0){
      String temp="0000000"+ Long.toString(_resourcekey);
      temp= temp.substring(temp.length()-_SIZE1,temp.length());
      id = temp+ _tempMessage +_resourceID;
    }
    instInfo= id + separator;
    instInfo += _resourceObjet.toString();
    return instInfo;
  }

  private static final int _SIZE1=8;
}