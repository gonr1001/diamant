package dInternal.dOptimization;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */


import java.util.Vector;

import dConstants.DConst;
import dInternal.DResource;
import dInternal.DSetOfResources;
//import dInternal.dTimeTable.SetOfConflicts;
import dInternal.dData.StandardCollection;
import dInternal.dUtil.ArrayValue;
import dInternal.DObject;
import dInternal.DValue;


public class ConflictsAttach extends DObject{


  /**
   * set of conflicts contains a resource in which the ID is the name of event and the
   * object attach is a DXValue where
   * intValue is the number of conflicts
   * stringValue is the type of conflict
   * objectValue is the setOfInformations associated in the conflict
   */
  private DSetOfResources _setOfConflicts;


  /**
   * Constructor
   * */
  public ConflictsAttach() {
    _setOfConflicts = new StandardCollection();
  }// end DConflicts constructor
  //--------------------------------------------------------

  /**
   *add a conflict in setofconflicts
   * @param eventName name of event in conflict eg' ADM111.1.A.1
   * @param numberOfConflicts the number of conflicts with the event
   * @param typeOfConflict the type of conflict with the event "student" conflict,
   * "room" conflicts,
   * "instructor" conflict
   * @param setOfInformations all additionnal information (exp. list of
   * students in conflicts, name of instructor in conflict, etc)
   */
  public void addConflict(String eventName, int numberOfConflicts,String typeOfConflict, Vector setOfInformations){
    DValue confValue= new DValue();
    confValue.setIntValue(numberOfConflicts);
    confValue.setStringValue(typeOfConflict);
    confValue.setObjectValue(setOfInformations);
    _setOfConflicts.addResource(new DResource(eventName,confValue),0);
  }

  /**
   * merge two conflicts attach list
   * @param cAttach
   */
  public void mergeConflictsAttach(ConflictsAttach cAttach){
    for (int i=0; i< cAttach.getConflictsAttach().size(); i++){
      DResource resc= cAttach.getConflictsAttach().getResourceAt(i);
      _setOfConflicts.addResource(new DResource(resc.getID(),resc.getAttach()),0);
    }// end for (int i=0; i< cAttach.getConflictsAttach().size(); i++)
  }


  /**
   *
   * @param event
   * @return
   */
  public DSetOfResources getAllConflictsOfAnEvent(DSetOfResources soc){
      //Vector vec= new Vector();
      //ArrayValue arrV= new ArrayValue(0);
       for (int i=0; i< _setOfConflicts.size(); i++){
         int [] nbconf={0,0,0};
        DResource conf= _setOfConflicts.getResourceAt(i);
        String str= conf.getID();
          if ( ((DValue)conf.getAttach()).getStringValue().equalsIgnoreCase(DConst.R_STUDENT_NAME)){
            nbconf[0]= ((DValue)conf.getAttach()).getIntValue();
          }
          if ( ((DValue)conf.getAttach()).getStringValue().equalsIgnoreCase(DConst.R_INSTRUCTOR_NAME)){
            nbconf[1]= ((DValue)conf.getAttach()).getIntValue();
          }
          if ( ((DValue)conf.getAttach()).getStringValue().equalsIgnoreCase(DConst.R_INSTRUCTOR_NAME_AVAIL)){
            nbconf[1]+= ((DValue)conf.getAttach()).getIntValue();
          }
          if ( ((DValue)conf.getAttach()).getStringValue().equalsIgnoreCase(DConst.R_ROOM_NAME)){
            nbconf[2]= ((DValue)conf.getAttach()).getIntValue();
          }
          DResource resc= soc.getResource(str);
          if(resc!=null){
             ArrayValue arrV= (ArrayValue)resc.getAttach();
            arrV.setIntArrayValue(arrV.getIntArrayValue(0)+nbconf[0],0);
            arrV.setIntArrayValue(arrV.getIntArrayValue(1)+nbconf[1],1);
            arrV.setIntArrayValue(arrV.getIntArrayValue(2)+nbconf[2],2);
          }else{// else if(resc!=null)
            ArrayValue arrV= new ArrayValue(3);
            arrV.setIntArrayValue(nbconf[0],0);
            arrV.setIntArrayValue(nbconf[1],1);
            arrV.setIntArrayValue(nbconf[2],2);
            soc.addResource(new DResource(str,arrV),1);
          }// end else if(resc!=null)
        //vec.add(str+"   "+nbconf[0]+" "+nbconf[1]+" "+nbconf[2]);
      }// end for (int i=0; i< _setOfConflicts.size(); i++)
      return soc;
  }

  /**
   *
   * @param event
   * @return
   */
  public DSetOfResources getAllConflictsOfAnEvent(DSetOfResources soc, /*String eventSource,*/ String eventName){
      //Vector vec= new Vector();
      //ArrayValue arrV= new ArrayValue(0);
       for (int i=0; i< _setOfConflicts.size(); i++){
         int [] nbconf={0,0,0};
        DResource conf= _setOfConflicts.getResourceAt(i);
        String str= conf.getID();
        if(str.equalsIgnoreCase(eventName)){
          if ( ((DValue)conf.getAttach()).getStringValue().equalsIgnoreCase(DConst.R_STUDENT_NAME)){
            nbconf[0]= ((DValue)conf.getAttach()).getIntValue();
          }
          if ( ((DValue)conf.getAttach()).getStringValue().equalsIgnoreCase(DConst.R_INSTRUCTOR_NAME)){
            nbconf[1]= ((DValue)conf.getAttach()).getIntValue();
          }
          if ( ((DValue)conf.getAttach()).getStringValue().equalsIgnoreCase(DConst.R_INSTRUCTOR_NAME_AVAIL)){
            nbconf[1]+= ((DValue)conf.getAttach()).getIntValue();
          }
          if ( ((DValue)conf.getAttach()).getStringValue().equalsIgnoreCase(DConst.R_ROOM_NAME)){
            nbconf[2]= ((DValue)conf.getAttach()).getIntValue();
          }
          DResource resc= soc.getResource(str);
          if(resc!=null){
             ArrayValue arrV= (ArrayValue)resc.getAttach();
            arrV.setIntArrayValue(arrV.getIntArrayValue(0)+nbconf[0],0);
            arrV.setIntArrayValue(arrV.getIntArrayValue(1)+nbconf[1],1);
            arrV.setIntArrayValue(arrV.getIntArrayValue(2)+nbconf[2],2);
          }else{// else if(resc!=null)
            ArrayValue arrV= new ArrayValue(3);
            arrV.setIntArrayValue(nbconf[0],0);
            arrV.setIntArrayValue(nbconf[1],1);
            arrV.setIntArrayValue(nbconf[2],2);
            soc.addResource(new DResource(str,arrV),1);
          }// end else if(resc!=null)
        //vec.add(str+"   "+nbconf[0]+" "+nbconf[1]+" "+nbconf[2]);
        }// end if(str.equalsIgnoreCase(eventName))
      }// end for (int i=0; i< _setOfConflicts.size(); i++)
      return soc;
  }


  /**
   * remove an activity from setfo conflicts
   * @param eventName the event name
   * @param typeOfConflict the type of conflicts
   * @return
   */
  public boolean removeConflict(String eventName, String typeOfConflict){
    for (int i=0; i< _setOfConflicts.size(); i++){
      DResource conf= _setOfConflicts.getResourceAt(i);
      if(conf.getID().equalsIgnoreCase(eventName)){
        if ( ((DValue)conf.getAttach()).getStringValue().equalsIgnoreCase(typeOfConflict)){
          return _setOfConflicts.removeResourceAt(i);
        }
      }// end if(conf.getID().equalsIgnoreCase(eventName))
    }// end for (int i=0; i< _setOfConflicts.size(); i++)
    return false;
  }

//  /**
//   * get an event conflicts from setof conflicts
//   * @param eventName the event name
//   * @param typeOfConflict the type of conflicts
//   * @return Vector of dxvalue containing conflict information
//   */
//  public Vector<DObject> getConflicts(String eventName, String typeOfConflict){
//    Vector <DObject>conflict = new Vector<DObject>();
//    for (int i=0; i< _setOfConflicts.size(); i++){
//      DResource conf= _setOfConflicts.getResourceAt(i);
//      if(conf.getID().equalsIgnoreCase(eventName)){
//        if ( ((DValue)conf.getAttach()).getStringValue().equalsIgnoreCase(typeOfConflict)){
//          conflict.add(conf.getAttach());
//        }
//      }// end if(conf.getID().equalsIgnoreCase(eventName))
//    }// end for (int i=0; i< _setOfConflicts.size(); i++)
//    return conflict;
//  }


  /**
   * get names all elements in conflict. return a vector
   * */
  public DSetOfResources getConflictsAttach(){
    return _setOfConflicts;
  }// end of Vector getList() method
  //--------------------------------------------------------
/* (non-Javadoc)
 * @see dInternal.DObject#getSelectedField()
 */
public long getSelectedField() {
	// TODO Auto-generated method stub
	return 0;
}

}// class end