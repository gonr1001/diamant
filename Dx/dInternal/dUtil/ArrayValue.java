package dInternal.dUtil;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */

public class ArrayValue extends DXObject{

  /**the reference value of this object*/
  private int [] _intArray;
  //private String [] _stringArray;

  /**
   * Contructor
   * */
  public ArrayValue(int arrayLenght) {
    _intArray= new int[arrayLenght];
    for (int i=0; i< arrayLenght; i++)
      _intArray[i]=0;
  }

  /**
   *
   * @param value
   * @param index
   */
  public void setIntArrayValue(int value, int index){
    if(index < _intArray.length)
    _intArray[index]= value;
  }

  /**
   *
   * @param index
   * @return
   */
  public int getIntArrayValue(int index){
     if(index < _intArray.length)
      return _intArray[index];
    return -1;
  }

}