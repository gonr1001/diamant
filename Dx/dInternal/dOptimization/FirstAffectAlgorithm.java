/**
*
* Title: FirstAffectAlgorithm $Revision: 1.19 $  $Date: 2005-04-11 14:42:25 $
* Description: FirstAffectAlgorithm is a class used to
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
* @version $Revision: 1.19 $
* @author  $Author: durp1901 $
* @since JDK1.3
*/

package dInternal.dOptimization;


import java.util.Vector;

import dInternal.DModel;
//import dInternal.dDataTxt.Resource;
import dInternal.DResource;
//import dInternal.dDataTxt.SetOfResources;
import dInternal.DSetOfResources;
import dInternal.dData.StandardCollection;
import dInternal.dDataTxt.Resource;
import dInternal.dTimeTable.Day;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.Sequence;
import dInternal.DValue;



public class FirstAffectAlgorithm implements Algorithm {
  /**
   * @associates DResource 
   */
  private Vector _placeEvent; // XXXX Pascal: Commentaire?
  private DModel _dm;

  /**
   * constructor
   */
  public FirstAffectAlgorithm(DModel dm) {
    _placeEvent= new Vector(1);
    _dm= dm;
  }

  /**
   *
   * @param tts
   * @param vectorOfEvents
   */
  public void build( ){
    DResource currentEvent;
    Period currentPeriod;
    Vector periodList;
    Vector vect= buildEventsVector();
    int currentDuration=0;

    //_dm.getConditionsTest().setAvoidPriorityTable(_avoidPriority);
    //_dm.getConditionsTest().setacceptableConflictsTable(_acceptableConflictsTable);
    _dm.getConditionsTest().extractPreference();

    for(int i=0; i< vect.size(); i++){
      currentEvent= (DResource)vect.get(i);
      boolean isNumberOfConflictsAccept=false;
      int[] nbConf;
      /*while(((EventAttach)currentEvent.getAttach()).getAssignState())
        currentEvent= (Resource)vectorOfEvents.remove(0);*/
      if(!((EventAttach)currentEvent.getAttach()).getAssignState()){
        currentDuration = ((EventAttach)currentEvent.getAttach()).getDuration()/_dm.getTTStructure().getPeriodLenght();
        periodList=buildSortContiguousPeriodVector(currentDuration,_dm.getConditionsTest().getAvoidPriorityTable());
        while(!periodList.isEmpty()){
          DValue value= (DValue)((DResource)periodList.remove(0)).getAttach();
          currentPeriod= (Period)value.getObjectValue();
          int[] dayTime= {value.getIntValue(), currentPeriod.getBeginHour()[0],currentPeriod.getBeginHour()[1]};
          ((EventAttach)currentEvent.getAttach()).setKey(4,_dm.getTTStructure().getCurrentCycle().getPeriod(dayTime));
          ((EventAttach)currentEvent.getAttach()).setAssignState(true);
          nbConf= _dm.getConditionsTest().getEventConflictsInTTs(_dm.getTTStructure(),currentEvent,true);
          isNumberOfConflictsAccept= isConflictsAcceptable(nbConf);
          ((EventAttach)currentEvent.getAttach()).setAssignState(false);
          if((isNumberOfConflictsAccept) && ((EventAttach)currentEvent.getAttach()).getDuration()!=0){
            ((EventAttach)currentEvent.getAttach()).setAssignState(true);
            _dm.getConditionsTest().addEventInTTs(_dm.getTTStructure(),currentEvent,true);
            _placeEvent.add(currentEvent);
            periodList.removeAllElements();
          }//else{// end if if(numberOfConflicts==0)
        }// end while(!periodList.isEmpty())
      }// end if(!((EventAttach)currentEvent.getAttach()).getAssignState())
    }// end for(int i=0; i< vect.size(); i++)
   // _dm.getConditionsTest().emptyAvoidPriorityTable();
    _dm.getSetOfEvents().updateActivities(_dm.getSetOfActivities(),_placeEvent);
    _dm.changeInDModel(null);
  }

  /**
   * return events list to use by the algorithm
   * @return
   */
  private Vector buildEventsVector(){
    return _dm.getSetOfEvents().getSetOfResources();
  }

  /**
   * check if conflicts are acceptable
   * @param conflicts
   * @return
   */
  private boolean isConflictsAcceptable(int [] conflicts){
    for (int i=0; i< conflicts.length; i++){
      if( _dm.getConditionsTest().getAcceptableConflictsTable()[i] < conflicts[i])
        return false;
    }
    return true;
  }

  /**
   * build Sort of Contiguous Period Vector
   * @return
   */
  private Vector buildSortContiguousPeriodVector(int duration, int[] avoidPriority){
    DSetOfResources soresc= new StandardCollection();
    int counter=1;
    for (int i=0; i< _dm.getTTStructure().getCurrentCycle().getSetOfDays().size(); i++){
      Resource day = _dm.getTTStructure().getCurrentCycle().getSetOfDays().getResourceAt(i);
      for(int j=0; j< ((Day)day.getAttach()).getSetOfSequences().size(); j++){
        Resource seq = ((Day)day.getAttach()).getSetOfSequences().getResourceAt(j);
        for(int k=0; k< ((Sequence)seq.getAttach()).getSetOfPeriods().size(); k++){
          Resource period= ((Sequence)seq.getAttach()).getSetOfPeriods().getResourceAt(k);
          Period per= (Period)period.getAttach();
          if(per.getEventsInPeriod().size()< _dm.getConditionsTest().getPeriodAcceptableSize()){
            if (_dm.getTTStructure().getCurrentCycle().isPeriodContiguous(day.getKey(),seq.getKey(),period.getKey(),duration, avoidPriority,true)){
              int number=0;
              for (int l=0; l< duration; l++){
                number+= ((Period)((Sequence)seq.getAttach()).getSetOfPeriods().getResourceAt(k+l).getAttach()
                ).getEventsInPeriod().size();
              }
              DValue value= new DValue();
              value.setIntValue((int)day.getKey());
              value.setObjectValue(per);
              soresc.setCurrentKey(number);
              soresc.addResource(new DResource(Integer.toString(counter++), value),1);
            }// end if (_dm.getTTStructure()
          }// end if(per.getEventsInPeriod().size()< _dm.getConditionsTest().
        }// end for(int k=0; k< ((Sequence)seq.getAttach()).getSetOfPeriod
      }// end for(int j=0; j< ((Day)day.getAttach()).getSetO
    }// end for (int i=0; i< _dm.getTTStructure().getCurrentCycle()
    soresc.sortSetOfResourcesByKey();
    return (Vector)soresc.getSetOfResources().clone();
  }


}// end class