package dInternal;

/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */

public class Resource {
  private static final String CR_LF = "\r\n";
  private int _resourcekey=0;
  private String _resourceID;// can be a name, an ID, a CIP code
  private Object _resourceObjet;

  public Resource( String id, Object obj) {
    _resourceID = id;
    _resourceObjet = new Object();
    _resourceObjet = obj;
  }

  public int getKey(){
    return _resourcekey;
  }

  protected void setKey(int k){
    _resourcekey=k;
  }

  /**
   *
   * */
  public String getID(){
    return _resourceID;
  }

  public Object getObject(){
    return _resourceObjet;
  }

  public void setObject(Object obj){
    _resourceObjet = new Object();
    _resourceObjet = obj;
  }

  public void setID(String id){
    _resourceID = id;
  }

  public String toString(){
    String instInfo;
    instInfo= _resourceID + CR_LF;
    instInfo += _resourceObjet.toString();
    return instInfo;
  }

}