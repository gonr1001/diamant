package dInternal.dConditionsTest;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */


import java.util.Vector;
import dInternal.dData.SetOfResources;
import dInternal.dData.Resource;
import dInternal.dUtil.DXObject;
import dInternal.dUtil.DXValue;
import dInternal.dUtil.ArrayValue;
import dResources.DConst;


public class ConflictsAttach extends DXObject{


  /**
   * set of conflicts contains a resource in wich the ID is the name of event and the
   * object attach is a DXValue where
   * intValue is the number of conflicts
   * stringValue is the type of conflict
   * objectValue is the setOfInformations associated in the conflict
   */
  private SetOfResources _setOfConflicts;


  /**
   * Constructor
   * */
  public ConflictsAttach() {
    _setOfConflicts = new SetOfResources(7);
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
    DXValue confValue= new DXValue();
    confValue.setIntValue(numberOfConflicts);
    confValue.setStringValue(typeOfConflict);
    confValue.setObjectValue(setOfInformations);
    _setOfConflicts.addResource(new Resource(eventName,confValue),0);
  }

  /**
   * merge two conflicts attach list
   * @param cAttach
   */
  public void mergeConflictsAttach(ConflictsAttach cAttach){
    for (int i=0; i< cAttach.getConflictsAttach().size(); i++){
      Resource resc= cAttach.getConflictsAttach().getResourceAt(i);
      _setOfConflicts.addResource(new Resource(resc.getID(),resc.getAttach()),0);
    }// end for (int i=0; i< cAttach.getConflictsAttach().size(); i++)

  }


  /**
   *
   * @param event
   * @return
   */
  public SetOfResources getAllConflictsOfAnEvent(SetOfResources soc){
      Vector vec= new Vector();
      //ArrayValue arrV= new ArrayValue(0);
       for (int i=0; i< _setOfConflicts.size(); i++){
         int [] nbconf={0,0,0};
        Resource conf= _setOfConflicts.getResourceAt(i);
        String str= conf.getID();
          if ( ((DXValue)conf.getAttach()).getStringValue().equalsIgnoreCase(DConst.R_STUDENT_NAME)){
            nbconf[0]= ((DXValue)conf.getAttach()).getIntValue();
          }
          if ( ((DXValue)conf.getAttach()).getStringValue().equalsIgnoreCase(DConst.R_INSTRUCTOR_NAME)){
            nbconf[1]= ((DXValue)conf.getAttach()).getIntValue();
          }
          if ( ((DXValue)conf.getAttach()).getStringValue().equalsIgnoreCase(DConst.R_ROOM_NAME)){
            nbconf[2]= ((DXValue)conf.getAttach()).getIntValue();
          }
          Resource resc= soc.getResource(str);
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
            soc.addResource(new Resource(str,arrV),1);
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
  public SetOfResources getAllConflictsOfAnEvent(SetOfResources soc, String eventName){
      Vector vec= new Vector();
      //ArrayValue arrV= new ArrayValue(0);
       for (int i=0; i< _setOfConflicts.size(); i++){
         int [] nbconf={0,0,0};
        Resource conf= _setOfConflicts.getResourceAt(i);
        String str= conf.getID();
        if(str.equalsIgnoreCase(eventName)){
          if ( ((DXValue)conf.getAttach()).getStringValue().equalsIgnoreCase(DConst.R_STUDENT_NAME)){
            nbconf[0]= ((DXValue)conf.getAttach()).getIntValue();
          }
          if ( ((DXValue)conf.getAttach()).getStringValue().equalsIgnoreCase(DConst.R_INSTRUCTOR_NAME)){
            nbconf[1]= ((DXValue)conf.getAttach()).getIntValue();
          }
          if ( ((DXValue)conf.getAttach()).getStringValue().equalsIgnoreCase(DConst.R_ROOM_NAME)){
            nbconf[2]= ((DXValue)conf.getAttach()).getIntValue();
          }
          Resource resc= soc.getResource(str);
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
            soc.addResource(new Resource(str,arrV),1);
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
      Resource conf= _setOfConflicts.getResourceAt(i);
      if(conf.getID().equalsIgnoreCase(eventName)){
        if ( ((DXValue)conf.getAttach()).getStringValue().equalsIgnoreCase(typeOfConflict)){
          return _setOfConflicts.removeResourceAt(i);
        }
      }// end if(conf.getID().equalsIgnoreCase(eventName))
    }// end for (int i=0; i< _setOfConflicts.size(); i++)
    return false;
  }


  /**
   * get names all elements in conflict. return a vector
   * */
  public SetOfResources getConflictsAttach(){
    return _setOfConflicts;
  }// end of Vector getList() method
  //--------------------------------------------------------

}// class end