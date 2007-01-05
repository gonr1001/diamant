//package dInternal.dUtil;
//
///**
// * <p>Title: Diamant 1.5</p>
// * <p>Description: exam timetable construction with Condition Pattern</p>
// * <p>Copyright: Copyright (c) 2002</p>
// * <p>Company: UdeS</p>
// * @author rgr, ysyam, alexander
// * @version 1.0
// */
//
//public class DXValue extends DXObject{
//
//  /**the reference value of this object*/
//  private int _refNo;
//  /** the boolean value field*/
//  private boolean _booleanValue;
//  /**the integer value field*/
//  private int _intValue;
//  /**the string value field*/
//  private String _stringValue;
//  /**the object value field*/
//  private Object _objectValue;
//
//  /**
//   * Contructor
//   * */
//  public DXValue() {
//    _intValue=-1;
//    _refNo=0;
//    _booleanValue=false;
//    _stringValue="";
//    //_objectValue= new Object();
//  }
//
//  /**
//   * Contructor
//   * */
//  public DXValue(int i, Object obj) {
//    _intValue= i;
//    _refNo=0;
//    _booleanValue=false;
//    _stringValue="";
//    _objectValue= obj;
//  }
//
//  /**
//   *
//   * */
//  public void setBooleanValue(boolean value){
//    _booleanValue= value;
//  }
//
//  /**
//   *
//   * */
//  public void setIntValue(int value){
//    _intValue = value;
//  }
//
//  /**
//   *
//   * */
//  public void setStringValue(String value){
//    _stringValue = value;
//  }
//
//  /**
//   *
//   * */
//  public void setRefNo(int refno){
//    _refNo = refno;
//  }
//
//  /**
//   *
//   * */
//  public void setObjectValue(Object value){
//    _objectValue = value;
//  }
//
//  /**
//   *
//   * */
//  public boolean getBooleanValue(){
//    return _booleanValue;
//  }
//
//  /**
//   *
//   * */
//  public int getIntValue(){
//    return _intValue;
//  }
//
//  /**
//   *
//   * */
//  public String getStringValue(){
//    return _stringValue;
//  }
//
//  /**
//   *
//   * */
//  public int getRefNo(){
//    return _refNo;
//  }
//
//  /**
//   *
//   * */
//  public Object getObjectValue(){
//    return _objectValue;
//  }
//  /**
// * compare this resource with the specified resource
// * @param resource the specified resource
// * @return bolean true if this resource and the specified resource are equals
// * false if they are not equals
// * */
//public boolean isEquals(DXObject val){
//  DXValue value = (DXValue)val;
//  if(_booleanValue!= value._booleanValue)
//    return false;
//  if(_intValue!= value._intValue)
//    return false;
//  if(_refNo!= value._refNo)
//    return false;
//  if(!_stringValue.equals(value._stringValue))
//    return false;
//  /*if(!this._objectValue.equals(value._objectValue))
//    return false;*/
//  return true;
// }
//
///**
// *This object (which is already a string!) is itself returned.
// * @return the string itself
// * */
//public String toWrite(){
//  
//  return ";"+_intValue+";"+_stringValue;
//}
//
//
//}