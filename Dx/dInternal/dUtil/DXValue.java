package dInternal.dUtil;

/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
import dInternal.dUtil.DXObject;

public class DXValue extends DXObject{

  /**the reference value of this object*/
  private int _refNo;
  /** the boolean value field*/
  private boolean _booleanValue;
  /**the integer value field*/
  private int _intValue;
  /**the string value field*/
  private String _stringValue;
  /**the object value field*/
  private Object _objectValue;

  /**
   * Contructor
   * */
  public DXValue() {
    _intValue=-1;
    _refNo=0;
    _booleanValue=false;
    _stringValue="";
  }

  /**
   *
   * */
  public void setBooleanValue(boolean value){
    _booleanValue= value;
  }

  /**
   *
   * */
  public void setIntValue(int value){
    _intValue = value;
  }

  /**
   *
   * */
  public void setStringValue(String value){
    _stringValue = value;
  }

  /**
   *
   * */
  public void setRefNo(int refno){
    _refNo = refno;
  }

  /**
   *
   * */
  public void setObjectValue(Object value){
    _objectValue = value;
  }

  /**
   *
   * */
  public boolean getBooleanValue(){
    return _booleanValue;
  }

  /**
   *
   * */
  public int getIntValue(){
    return _intValue;
  }

  /**
   *
   * */
  public String getStringValue(){
    return _stringValue;
  }

  /**
   *
   * */
  public int getRefNo(){
    return _refNo;
  }

  /**
   *
   * */
  public Object getObjectValue(){
    return _objectValue;
  }
}