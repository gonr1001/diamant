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
   * @param typeOfConflict the type of conflict with the event 0= student conflict,
   * 1 = room available 2 = blocs room conflict, 3 = capacity  rooms conflicts,
   * 4 = blocs instructor conflict, 5 = availability instructor conflict
   * @param setOfInformations all additionnal information (exp. list of
   * students in conflicts, name of instructor in conflict, etc)
   */
  public void addConflict(String eventName, int numberOfConflicts,int typeOfConflict, Vector setOfInformations){
    DXValue confValue= new DXValue();
    confValue.setIntValue(numberOfConflicts);
    confValue.setStringValue(Integer.toString(typeOfConflict));
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
   * remove an activity from setfo conflicts
   * @param eventName the event name
   * @param typeOfConflict the type of conflicts
   * @return
   */
  public boolean removeConflict(String eventName, int typeOfConflict){
    for (int i=0; i< _setOfConflicts.size(); i++){
      Resource conf= _setOfConflicts.getResourceAt(i);
      if(conf.getID().equalsIgnoreCase(eventName)){
        if (Integer.parseInt( ((DXValue)conf.getAttach()).getStringValue())==typeOfConflict){
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