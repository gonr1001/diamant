package dInternal.dData;

import java.awt.Color;

public class SetOfStates extends SetOfResources{

  String _error="";
  /***
   * constructor
   */

  public SetOfStates() {
    super(5);
  }

  /**
   *
   */
  public void buildSetOfStates(){
    State state= new State(Color.BLACK,10);
    addResource(new Resource("Nb de blocs:",state),0);
    state= new State(Color.BLUE,2);
    addResource(new Resource("Inst:",state),0);
    state= new State(Color.RED,6);
    addResource(new Resource("Stud:",state),0);
  }

  /**
   *
   * @return
   */
  public String getError() {
    return _error;
  }
}