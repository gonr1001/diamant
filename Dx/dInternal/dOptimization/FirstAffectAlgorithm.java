package dInternal.dAlgorithms;

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
import dInternal.dConditionsTest.*;
import dInternal.dData.Resource;
import java.util.Vector;
import java.util.StringTokenizer;
//import java.awt.import java.awt.Component;;

public class FirstAffectAlgorithm implements Algorithm {

  private Vector _noPlaceEvent;
  private Vector _placeEvent;
  private int _lastNumberOfNoPlaceEvents=-1;
  private int _currentDuration=0;
  private DModel _dm;

  /**
   * constructor
   */
  public FirstAffectAlgorithm() {
    _noPlaceEvent= new Vector(1);
    _placeEvent= new Vector(1);
  }

  /**
   *
   * @param tts
   * @param vectorOfEvents
   */
  public void build(DModel dm, Vector vect){
    _dm= dm;
    Resource currentEvent;
    Period currentPeriod;
    Vector vectorOfEvents = (Vector)vect.clone();

    while(start()){
       currentPeriod= _dm.getTTStructure().getCurrentCycle().getNextPeriod(_currentDuration);
       _lastNumberOfNoPlaceEvents= _noPlaceEvent.size();
       _noPlaceEvent.removeAllElements();
       System.out.println("_lastNumberOfNoPlaceEvents: "+_lastNumberOfNoPlaceEvents);//debug
      while(!vectorOfEvents.isEmpty()){
        currentEvent= (Resource)vectorOfEvents.remove(0);
        int numberOfConflicts=0;
        while(((EventAttach)currentEvent.getAttach()).getAssignState())
          currentEvent= (Resource)vectorOfEvents.remove(0);
        int dayIndex= _dm.getTTStructure().getCurrentCycle().getCurrentDayIndex();
        int[] dayTime= {(int)_dm.getTTStructure().getCurrentCycle().getSetOfDays().
          getResourceAt(dayIndex).getKey(), currentPeriod.getBeginHour()[0],currentPeriod.getBeginHour()[1]};
        ((EventAttach)currentEvent.getAttach()).setKey(4,_dm.getTTStructure().getCurrentCycle().getPeriod(dayTime));
        //numberOfConflicts= _dm.getTTStructure().getCurrentCycle().isPeriodContiguous()
        //numberOfConflicts=testConditions(currentPeriod,currentEvent,0);
        numberOfConflicts= _dm.getConditionsTest().addOrRemEventInPeriod(currentPeriod,currentEvent,0);
        if(numberOfConflicts==0){
          _dm.getConditionsTest().addOrRemEventInPeriod(currentPeriod,currentEvent,1);
          _placeEvent.add(currentEvent);
          currentPeriod= _dm.getTTStructure().getCurrentCycle().getNextPeriod(_currentDuration);
        }else{// end if if(numberOfConflicts==0)
          _noPlaceEvent.add(currentEvent);
        }// end else if(numberOfConflicts==0)
      }// end while(!vectorOfEvents.isEmpty())
      vectorOfEvents= (Vector)_noPlaceEvent.clone();
    }// end while(start())
    _dm.getSetOfEvents().updateActivities(_placeEvent);
    _dm.getSetOfActivities().sendEvent(null);
  }

  /**
   * check if algorithm must continue
   * @return
   */
  private boolean start(){
    if(_lastNumberOfNoPlaceEvents== _noPlaceEvent.size())
      return false;
    return true;
  }

}