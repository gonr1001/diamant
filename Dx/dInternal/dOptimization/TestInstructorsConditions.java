package dInternal.dConditionsTest;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */
import dInternal.DModel;

import dInternal.dData.InstructorAttach;

import dInternal.dData.Resource;
import dInternal.dTimeTable.Period;
import dInternal.dUtil.DXToolsMethods;

import java.util.Vector;

import dResources.DConst;

public class TestInstructorsConditions  implements Condition{

  private DModel _dm;
  private int _NOTAVAIL=5;
  /**
   * Constructor
   * @param dmodel
   */
  public TestInstructorsConditions(DModel dm) {
    _dm= dm;
  }

  /**
   *
   * @param period
   * @param eventKey
   * @param operation
   * @return
   */
  public int executeTest(int[] perKey, Period period, String eventKey, int operation){
    int number=0;
    int nbConf1, nbConf2;
    ConflictsAttach confVal= new ConflictsAttach();
    nbConf1= InstructorAvailibilityConflicts(period,eventKey);
    nbConf2= InstructorEventsConflicts(period,eventKey, confVal);
    number= nbConf1+nbConf2;
    if (nbConf1!=0)
      confVal.addConflict("Disponibilite Enseignant",nbConf1,DConst.R_INSTRUCTOR_NAME_AVAIL,new Vector());

    switch(operation){
      case 0:
        break;
      case 1:
        Resource resc=period.getEventsInPeriod().getResource(eventKey);
        if(resc!=null)
          ((ConflictsAttach)resc.getAttach()).mergeConflictsAttach(confVal);
        else
          period.getEventsInPeriod().addResource(new Resource(eventKey,confVal),1);
        period.addNbInstructorsConflict(number);
        break;
      case -1:period.getEventsInPeriod().removeResource(eventKey);
        period.removeNbInstructorsConflict(number);
        for(int i=0; i< period.getEventsInPeriod().size(); i++)
          ((ConflictsAttach)period.getEventsInPeriod().getResourceAt(i).
           getAttach()).removeConflict(eventKey,DConst.R_INSTRUCTOR_NAME);
        break;
    }
    //return 0;
    //}// end if (period.getEventsInPeriod().getIndexOfResource(eventKey)==-1)
    return number;
  }

  /**
   *check Instructor availability conflicts
   * @param period
   * @param eventKey
   * @return
   */
  private int InstructorAvailibilityConflicts(Period period, String eventKey){
    EventAttach event = (EventAttach)_dm.getSetOfEvents().getResource(eventKey).getAttach();
    long instKey[] = event.getInstructorKey();
     int nbConf=0;
    //long instKey = event.getInstructorKey();
    for (int i=0; i< instKey.length; i++){
      if(event.getPeriodKey().length()!=0){
        InstructorAttach inst = (InstructorAttach)_dm.getSetOfInstructors().getResource(instKey[i]).getAttach();
        long dayKey = Integer.parseInt(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
        int[] dayTime={(int)dayKey, period.getBeginHour()[0],period.getBeginHour()[1]};
        String thePeriod= _dm.getTTStructure().getCurrentCycle().getPeriod(dayTime);
        long seqKey = Integer.parseInt(DXToolsMethods.getToken(thePeriod,".",1));
        long perKey = Integer.parseInt(DXToolsMethods.getToken(thePeriod,".",2));
        int dayIndexAvail= _dm.getTTStructure().findIndexInWeekTable(dayKey);
        int perPosition= _dm.getTTStructure().getCurrentCycle().getPeriodPositionInDay(dayKey,seqKey,perKey);
        if(perPosition>0){
          int [][] matrix= inst.getMatrixAvailability();
          if ((dayIndexAvail < matrix.length)){
            if(matrix[dayIndexAvail][perPosition-1]==_NOTAVAIL)
              nbConf++;
          }else{// else if ((dayIndexAvail < matrix.length))
            nbConf++;
          }// end else if ((dayIndexAvail < matrix.length))
        }// end if(perPosition>0)
      }
    }// end for (int i=0; i< instKey.length; i++)
    return nbConf;
  }

  /**
   * Check Instructor conflicts between 2 events
   * @param period
   * @param eventKey
   * @return
   */
  private int InstructorEventsConflicts(Period period, String eventKey, ConflictsAttach confV){
    EventAttach event1 = (EventAttach)_dm.getSetOfEvents().getResource(eventKey).getAttach();
    long keys1[] = event1.getInstructorKey();
    EventAttach event2;
    int nbConf=0;
    for(int i=0; i< period.getEventsInPeriod().size(); i++){
      String event2ID = period.getEventsInPeriod().getResourceAt(i).getID();
      event2= (EventAttach)_dm.getSetOfEvents().getResource(event2ID).getAttach();
      long keys2[] = event2.getInstructorKey();
      for(int j=0; j< keys1.length; j++){
        for(int k=0; k< keys2.length; k++){
          if(!event1.getPrincipalRescKey().equalsIgnoreCase(event2.getPrincipalRescKey())){
            if((keys1[j]==keys2[k])){
              confV.addConflict(event2ID,1, DConst.R_INSTRUCTOR_NAME, new Vector());
              nbConf++;
            }
          }
        }// end for(int k=0; k< keys2.length; k++)
      }// end for(int j=0; j< keys1.length; j++)
      /*if(!event1.getPrincipalRescKey().equalsIgnoreCase(event2.getPrincipalRescKey())){
        if((event1.getInstructorKey()==event2.getInstructorKey()) && (event1.getInstructorKey()!=-1)){
          confV.addConflict(period.getEventsInPeriod().getResourceAt(i).getID(),1, DConst.R_INSTRUCTOR_NAME, new Vector());
          nbConf++;
        }
      }*/
    }
    return nbConf;
  }


}// end class
