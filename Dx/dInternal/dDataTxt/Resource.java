package dInternal;

/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
import dResources.DXObject;

public class Resource {
  private static final String CR_LF = "\r\n";
  private long _resourcekey=0;
  private String _resourceID;// can be a name, an ID, a CIP code
  private String _message="";// message to be write before _resourceID in toString method
  private DXObject _resourceObjet;

  public Resource( String id, DXObject obj) {
    _resourceID = id;
    _resourceObjet = new DXObject();
    _resourceObjet = obj;
  }

  public long getKey(){
    return _resourcekey;
  }

  protected void setKey(long k){
    _resourcekey=k;
  }

  /**
   *
   * */
  public String getID(){
    return _resourceID;
  }

  public DXObject getObject(){
    return _resourceObjet;
  }

  public void setObject(DXObject obj){
    _resourceObjet = new DXObject();
    _resourceObjet = obj;
  }

  public void setID(String id){
    _resourceID = id;
  }

  public void setMessage(String message){
    _message = message;
  }

  public String toString(String separator){
    String instInfo;
    if (_message.length()!=0){
      String temp="0000000"+ Long.toString(_resourcekey);
      temp= temp.substring(temp.length()-_SIZE1,temp.length());
      _resourceID = temp+ _message +_resourceID;
    }
    instInfo= _resourceID + separator;
    instInfo += _resourceObjet.toString();
    return instInfo;
  }

  private static final int _SIZE1=8;
}