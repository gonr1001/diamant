package dInternal.dDataTxt;

import java.util.Vector;
import dConstants.DConst;
//import dInternal.DSetOfResources;
//import dInternal.DResource;



public class SetOfStates extends SetOfResources{

  String _error="";

  /**
   * @associates SetOfStatesListener 
   */
  private Vector _sosListener = new Vector(1);
  /***
   * constructor
   */

  public SetOfStates() {
  	super(5);//super(5);
    initSetOfStates();
  }

  /**
   * The states must be in the order that they are displayed
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

    state= new State(DConst.COLOR_STUD,0);
    addResource(new Resource(DConst.SB_C_STUD,state),0);
    state= new State(DConst.COLOR_INST, 0);
    addResource(new Resource(DConst.SB_C_INST,state),0);
    state= new State(DConst.COLOR_ROOM,0);
    addResource(new Resource(DConst.SB_C_ROOM,state),0);

  }

  /**
   *
   * @return
   */
  public String getError() {
    return _error;
  }



  public void addState(String id, State state){
    this.addResource(new Resource(id, state),0);
  }

  /**
   *
   * @param sosl
   */
  public synchronized void addSetOfStatesListener(SetOfStatesListener sosl) {
    if (_sosListener.contains(sosl)){
      return;
    }
    _sosListener.addElement(sosl);
    //System.out.println("addSetOfStates Listener ...");
   }

   public void sendEvent() {
    SetOfStatesEvent event = new SetOfStatesEvent(this);
    for (int i=0; i< _sosListener.size(); i++) {
      SetOfStatesListener sosl = (SetOfStatesListener) _sosListener.elementAt(i);
      sosl.changeInStateBar(event);
      //System.out.println("sendEvent: "+event.toString()+"   --I:"+i);
      // System.out.println("SetOfStates listener started: "+i);//debug
    }
   }

   public synchronized void removeSetOfStatesListener(SetOfStatesListener sosl) {
     _sosListener.removeElement(sosl);
   }

/* (non-Javadoc)
 * @see dInternal.DSetOfResources#toWrite()
 */
public String toWrite() {
	// TODO Auto-generated method stub
	return null;
}

/* (non-Javadoc)
 * @see dInternal.DObject#getSelectedField()
 */
public long getSelectedField() {
	// TODO Auto-generated method stub
	return 0;
}

}