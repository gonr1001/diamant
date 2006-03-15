package dInternal;
/**
*
* Title: DSetOfStates $Revision: 1.7 $  $Date: 2006-03-15 13:35:53 $
* Description: DSetOfStates is a class used to
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
* @version $Revision: 1.7 $
* @author  $Author: gonzrubi $
* @since JDK1.3
*/


import dConstants.DConst;

public class DSetOfStates extends DSetOfResources{
  String _error = "";

  /**
   * @associates DSetOfStatesListener 
   */
  //private Vector _sosListener = new Vector(1);
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
  // XXXX Pascal: Kosse ca ?!
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
