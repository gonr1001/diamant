package dInternal;

import java.util.Vector;
import dConstants.DConst;
//import dInternal.DSetOfResources;
//import dInternal.DResource;



public class DSetOfStates extends DSetOfResources{

  String _error="";
  private Vector _sosListener = new Vector(1);
  /***
   * constructor
   */

  public DSetOfStates() {
  	super();//super(5);
    initSetOfStates();
  }

  /**
   * The states must be in the order that they are displayed
   * on the StateBar
   */
  private void initSetOfStates(){
    DState state= new DState(DConst.COLOR_BLACK,-1);
    addResource(new DResource(DConst.SB_TOTAL,state),0);

    state= new DState(DConst.COLOR_AUX, 0);
    addResource(new DResource(DConst.SB_T_ACT,state),0);
    state= new DState(DConst.COLOR_AUX,0);
    addResource(new DResource(DConst.SB_T_INST,state),0);
    state= new DState(DConst.COLOR_AUX,0);
    addResource(new DResource(DConst.SB_T_ROOM,state),0);
    state= new DState(DConst.COLOR_AUX,0);
    addResource(new DResource(DConst.SB_T_STUD,state),0);
    state= new DState(DConst.COLOR_AUX,0);
    addResource(new DResource(DConst.SB_T_EVENT,state),0);
    state= new DState(DConst.COLOR_AUX,0);
    addResource(new DResource(DConst.SB_T_ASSIG,state),0);


    state= new DState(DConst.COLOR_BLACK,0);
    addResource(new DResource(DConst.SB_CONF,state),0);

    state= new DState(DConst.COLOR_STUD,0);
    addResource(new DResource(DConst.SB_C_STUD,state),0);
    state= new DState(DConst.COLOR_INST, 0);
    addResource(new DResource(DConst.SB_C_INST,state),0);
    state= new DState(DConst.COLOR_ROOM,0);
    addResource(new DResource(DConst.SB_C_ROOM,state),0);

  }

  /**
   *
   * @return
   */
  public String getError() {
    return _error;
  }



  public void addState(String id, DState state){
    this.addResource(new DResource(id, state),0);
  }

  /**
   *
   * @param sosl
   */
  public synchronized void addSetOfStatesListener(DSetOfStatesListener sosl) {
    if (_sosListener.contains(sosl)){
      return;
    }
    _sosListener.addElement(sosl);
    //System.out.println("addSetOfStates Listener ...");
   }

   public void sendEvent() {
    DSetOfStatesEvent event = new DSetOfStatesEvent(this);
    for (int i=0; i< _sosListener.size(); i++) {
      DSetOfStatesListener sosl = (DSetOfStatesListener) _sosListener.elementAt(i);
      sosl.changeInStateBar(event);
      //System.out.println("sendEvent: "+event.toString()+"   --I:"+i);
      // System.out.println("SetOfStates listener started: "+i);//debug
    }
   }

   public synchronized void removeSetOfStatesListener(DSetOfStatesListener sosl) {
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