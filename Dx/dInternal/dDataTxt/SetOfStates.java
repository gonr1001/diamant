package dInternal.dData;

import dResources.DConst;

public class SetOfStates extends SetOfResources{

  String _error="";
  /***
   * constructor
   */

  public SetOfStates() {
    super(5);
    initSetOfStates();
  }

  /**
   * The states must be in the order that they are sisplaye
   * on the StateBar
   */
  private void initSetOfStates(){
    State state= new State(DConst.COLOR_BLACK,-1);
    addResource(new Resource(DConst.SB_TOTAL,state),0);

    state= new State(DConst.COLOR_AUX, 0);
    addResource(new Resource(DConst.SB_T_ACT,state),0);
    state= new State(DConst.COLOR_AUX,0);
    addResource(new Resource(DConst.SB_T_INST,state),0);
    state= new State(DConst.COLOR_AUX,0);
    addResource(new Resource(DConst.SB_T_ROOM,state),0);
    state= new State(DConst.COLOR_AUX,0);
    addResource(new Resource(DConst.SB_T_STUD,state),0);
    state= new State(DConst.COLOR_AUX,0);
    addResource(new Resource(DConst.SB_T_EVENT,state),0);
    state= new State(DConst.COLOR_AUX,0);
    addResource(new Resource(DConst.SB_T_ASSIG,state),0);


    state= new State(DConst.COLOR_BLACK,0);
    addResource(new Resource(DConst.SB_CONF,state),0);

    state= new State(DConst.COLOR_INST, 0);
    addResource(new Resource(DConst.SB_C_INST,state),0);
    state= new State(DConst.COLOR_ROOM,0);
    addResource(new Resource(DConst.SB_C_ROOM,state),0);
    state= new State(DConst.COLOR_STUD,0);
    addResource(new Resource(DConst.SB_C_STUD,state),0);
  }

  /**
   *
   * @return
   */
  public String getError() {
    return _error;
  }

  public void incrementModification() {}
}