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
  /**show if the resource has been manually added, or automatic (during extract data)*/
  private boolean _manuallyCreated;
  /**Resource key*/
  private long _resourceKey=0;
  /**Resource ID it can be a name, an ID, a CIP code*/
  private String _resourceID;
  /**Resource Object*/
  private DXObject _resourceObjet;

  /**
   * Constructor
   * @param String the id of the Resource
   * @param DXObject the object to set in the _resourceObjet field
   * */
  public Resource(String id, DXObject obj) {
    _resourceID = id;
    _resourceObjet = obj;
  }

  /**
   * Return the resource key
   * @return long the resource key
   * */
  public long getKey(){
    return _resourceKey;
  }

  /**
   *set the resource key
   * @param long the resource key
   * */
  protected void setKey(long k){
    _resourceKey=k;
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
   *set if the resource has been manualy added, or automatic (during extract data)
   *@param boolean the resource state
   * */
  public void setManuallyCreated(boolean manual){
    _manuallyCreated = manual;
  }

  /**
   * Return the resource state showing if the resource has been manualy added,
   * or automatic (during extract data)
   * @return boolean the resource state
   * */
  public boolean isManuallyCreated(){
    return _manuallyCreated;
  }

  /**
   *This object (which is already a string!) is itself returned.
   * @return the string itself
   * */
  public String toWrite(String separator){
    String instInfo;
    String id= _resourceID;
    id=_resourceObjet.externalKey( Long.toString(_resourceKey))+id;
    instInfo= id + separator;
    instInfo += _resourceObjet.toWrite();
    return instInfo;
  }

}