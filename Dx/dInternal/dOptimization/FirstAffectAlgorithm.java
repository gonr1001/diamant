package dInternal.dOptimization;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import dInternal.DModel;
import dInternal.dTimeTable.*;
import dInternal.dOptimization.*;
import dInternal.dDataTxt.Resource;
import dInternal.dDataTxt.SetOfResources;
import dInternal.dUtil.DXValue;
import java.util.Vector;



public class FirstAffectAlgorithm implements Algorithm {

  private Vector _placeEvent;
  private DModel _dm;
  //private int [] _avoidPriority;
 // private int [] _acceptableConflictsTable;

  /**
   * constructor
   */
  public FirstAffectAlgorithm(DModel dm) {
    //_noPlaceEvent= new Vector(1);
    _placeEvent= new Vector(1);
    //_avoidPriority= avoidPriority;
    //_acceptableConflictsTable= acceptableConflictsTable;
    _dm= dm;
  }

  /**
   *
   * @param tts
   * @param vectorOfEvents
   */
  public void build( ){
    //_dm= dm;
    Resource currentEvent;
    Period currentPeriod;
    Vector periodList;
    Vector vect= buildEventsVector();
    int currentDuration=0;

    //_dm.getConditionsTest().setAvoidPriorityTable(_avoidPriority);
    //_dm.getConditionsTest().setacceptableConflictsTable(_acceptableConflictsTable);
    _dm.getConditionsTest().extractPreference();

    for(int i=0; i< vect.size(); i++){
      currentEvent= (Resource)vect.get(i);
      boolean isNumberOfConflictsAccept=false;
      int[] nbConf;
      /*while(((EventAttach)currentEvent.getAttach()).getAssignState())
        currentEvent= (Resource)vectorOfEvents.remove(0);*/
      if(!((EventAttach)currentEvent.getAttach()).getAssignState()){
        currentDuration = ((EventAttach)currentEvent.getAttach()).getDuration()/_dm.getTTStructure().getPeriodLenght();
        periodList=buildSortContiguousPeriodVector(currentDuration,_dm.getConditionsTest().getAvoidPriorityTable());
        while(!periodList.isEmpty()){
          DXValue value= (DXValue)((Resource)periodList.remove(0)).getAttach();
          currentPeriod= (Period)value.getObjectValue();
          int[] dayTime= {value.getIntValue(), currentPeriod.getBeginHour()[0],currentPeriod.getBeginHour()[1]};
          ((EventAttach)currentEvent.getAttach()).setKey(4,_dm.getTTStructure().getCurrentCycle().getPeriod(dayTime));
          ((EventAttach)currentEvent.getAttach()).setAssignState(true);
          nbConf= _dm.getConditionsTest().addOrRemEventInTTs(currentEvent,0,true);
          isNumberOfConflictsAccept= isConflictsAcceptable(nbConf);
          ((EventAttach)currentEvent.getAttach()).setAssignState(false);
          if((isNumberOfConflictsAccept) && ((EventAttach)currentEvent.getAttach()).getDuration()!=0){
            ((EventAttach)currentEvent.getAttach()).setAssignState(true);
            _dm.getConditionsTest().addOrRemEventInTTs(currentEvent,1,true);
            _placeEvent.add(currentEvent);
            periodList.removeAllElements();
          }//else{// end if if(numberOfConflicts==0)
        }// end while(!periodList.isEmpty())
      }// end if(!((EventAttach)currentEvent.getAttach()).getAssignState())
    }// end for(int i=0; i< vect.size(); i++)
   // _dm.getConditionsTest().emptyAvoidPriorityTable();
    _dm.getSetOfEvents().updateActivities(_placeEvent);
    _dm.getSetOfActivities().sendEvent(null);
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
    SetOfResources soresc= new SetOfResources(4);
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
              DXValue value= new DXValue();
              value.setIntValue((int)day.getKey());
              value.setObjectValue(per);
              soresc.setCurrentKey(number);
              soresc.addResource(new Resource(Integer.toString(counter++), value),1);
            }// end if (_dm.getTTStructure()
          }// end if(per.getEventsInPeriod().size()< _dm.getConditionsTest().
        }// end for(int k=0; k< ((Sequence)seq.getAttach()).getSetOfPeriod
      }// end for(int j=0; j< ((Day)day.getAttach()).getSetO
    }// end for (int i=0; i< _dm.getTTStructure().getCurrentCycle()
    soresc.sortSetOfResourcesByKey();
    return (Vector)soresc.getSetOfResources().clone();
  }


}// end class