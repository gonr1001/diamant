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
  private int _resourcekey;
  private String _resourceID;// can be a name, an ID, a CIP code
  private Object _resourseObjet;

  public Resource( int key, String id, Object obj) {
    _resourcekey = key;
    _resourceID = id;
    _resourseObjet = new Object();
    _resourseObjet = obj;
  }

  public int getKey(){
    return _resourcekey;
  }

  /**
   *
   * */
  public String getID(){
    return _resourceID;
  }

  public Object getObject(){
    return _resourseObjet;
  }

  public String toString(){
    String instInfo;
    instInfo= _resourceID + CR_LF;
    instInfo += _resourseObjet.toString();
    return instInfo;
  }

}