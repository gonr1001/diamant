package dInternal.dConditionsTest;

import dInternal.dUtil.DXObject;
import dInternal.dData.SetOfResources;
import java.util.Vector;

public class EventAttach extends DXObject {

  /** _principalRescKey is the composition of activity, type, section and
   * unity keys of an activity wich is represent in the following format a.b.c.d
   */
  private String _principalRescKey;
  private long _secondaryRescKey1; // the instructor key
  private long _secondaryRescKey2; // the room key
  // the student reference will be found in the conflicts matrix
  private Vector _tabuList; //

  /**
   * Constructor
   * @param princKey
   * @param key1
   * @param key2
   */
  public EventAttach(String princKey, long key1, long key2) {
    _principalRescKey = princKey;
    _secondaryRescKey1 = key1;
    _secondaryRescKey2 = key2;
    _tabuList = new Vector();
  }

  public String getPrincipalRescKey(){
    return _principalRescKey;
  }

  /**
   * Tests if the specified string is a component in the tabulist vector.
   * @param princKey
   * @return
   */
  public boolean isInTabuList(String princKey){
    return _tabuList.contains(princKey);
  }

  /**
   * Removes the first occurrence of the specified element in the tabulist vector If
   * it does not contain the element, it is unchanged.
   * @param princKey
   * @return
   */
  public boolean removeFromTabuList(String princKey){
    return _tabuList.remove(princKey);
  }

  /**
   * Adds the specified element to the end of the tabulist vector or let it unchanged
   * if the element already exist in the vector
   * @param princKey
   * @return
   */
  public boolean addToTabuList(String princKey){
    if (_tabuList.contains(princKey))
      return _tabuList.add(princKey);
    return false;
  }

  /**
   * set events keys by the appropriate field
   * @param field 0= set principal key, 1= set secondary key1, 2= set secondary key2
   * @param value
   */
  public void setKey(int field, String value){
    switch(field){
      case 0: _principalRescKey = value;
        break;
      case 1: _secondaryRescKey1 = Long.parseLong(value);
        break;
      case 2: _secondaryRescKey2 = Long.parseLong(value);
        break;
    }

  }
}