/**
*
* Title: DValue $Revision $  $Date: 2005-02-02 21:37:58 $
* Description: DValue is a class used to
*
*
* Copyright (c) 2001 by rgr.
* All rights reserved.
*
*
* This software is the confidential and proprietary information
* of rgr. ("Confidential Information").  You
* shall not disclose such Confidential Information and shall use
* it only in accordance with the terms of the license agreement
* you entered into with rgr.
*
* @version $ $
* @author  $Author: garr2701 $
* @since JDK1.3
*/

package dInternal;

public class DValue extends DObject{

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
  public DValue() {
  	_refNo=0;
  	_booleanValue=false;
    _intValue=-1;
    _stringValue="";
    _objectValue= new Object();
  }

  /**
   * Contructor
   * */
  public DValue(int i, Object obj) {
    _intValue= i;
    _refNo=0;
    _booleanValue=false;
    _stringValue="";
    _objectValue= obj;
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
  /**
 * compare this resource with the specified resource
 * @param resource the specified resource
 * @return bolean true if this resource and the specified resource are equals
 * false if they are not equals
 * */
public boolean isEquals(DObject val){
  DValue value = (DValue)val;
  if(_booleanValue!= value._booleanValue)
    return false;
  if(_intValue!= value._intValue)
    return false;
  if(_refNo!= value._refNo)
    return false;
  if(!_stringValue.equals(value._stringValue))
    return false;
  /*if(!this._objectValue.equals(value._objectValue))
    return false;*/
  return true;
 }

/**
 *This object (which is already a string!) is itself returned.
 * @return the string itself
 * */
public String toWrite(){
  
  return ";"+_intValue+";"+_stringValue;
}

/* (non-Javadoc)
 * @see dInternal.DObject#getSelectedField()
 */
public long getSelectedField() {
	
	return 0;
}


}