package dInternal.dData;

import java.util.EventObject;

public class SetOfStatesEvent extends EventObject{

  public SetOfStatesEvent(SetOfStates source) {
    super (source);
  }
}