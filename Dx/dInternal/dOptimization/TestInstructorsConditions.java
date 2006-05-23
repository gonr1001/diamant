package dInternal.dOptimization;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */
import java.util.Vector;

import dConstants.DConst;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.dData.AvailabilityAttach;
import dInternal.dTimeTable.Period;
//import dInternal.dUtil.DXToolsMethods;

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
    ConflictsAttach confVal2= new ConflictsAttach();
    nbConf1= InstructorAvailibilityConflicts(period, perKey,eventKey, confVal2);
    nbConf2= InstructorEventsConflicts(period,eventKey, confVal);
    number= nbConf1+nbConf2;
    if (nbConf1!=0)
      confVal.mergeConflictsAttach(confVal2);//confVal.addConflict("Disponibilite Enseignant",nbConf1,DConst.R_INSTRUCTOR_NAME_AVAIL,new Vector());

    switch(operation){
      case 0:
        break;
      case 1:
        DResource resc=period.getEventsInPeriod().getResource(eventKey);
        if(resc!=null)
          ((ConflictsAttach)resc.getAttach()).mergeConflictsAttach(confVal);
        else
          period.getEventsInPeriod().addResource(new DResource(eventKey,confVal),1);
        period.addNbInstructorsConflict(number);
        break;
      case -1:period.getEventsInPeriod().removeResource(eventKey);
        period.removeNbInstructorsConflict(number);
        for(int i=0; i< period.getEventsInPeriod().size(); i++){
          ((ConflictsAttach)period.getEventsInPeriod().getResourceAt(i).
           getAttach()).removeConflict(eventKey,DConst.R_INSTRUCTOR_NAME);
        ((ConflictsAttach)period.getEventsInPeriod().getResourceAt(i).
           getAttach()).removeConflict(eventKey,DConst.R_INSTRUCTOR_NAME_AVAIL);
        }
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
  private int InstructorAvailibilityConflicts(Period period, int[] perK,String eventKey, ConflictsAttach confV){
    EventAttach event = (EventAttach)_dm.getSetOfEvents().getResource(eventKey).getAttach();
    long instKey[] = event.getInstructorKey();
     int nbConf=0;
     int [][] matrix;
     period.getBeginHour();// only to disable warning
     Vector description= new Vector();// is a vector of Long containing instructor keys
    //long instKey = event.getInstructorKey();
    for (int i=0; i< instKey.length; i++){
      if(event.getPeriodKey().length()!=0){
    	  if (!DConst.newInstructors) {
    		  AvailabilityAttach inst = (AvailabilityAttach)_dm.getSetOfInstructors().getResource(instKey[i]).getAttach();
        	  matrix= inst.getMatrixAvailability();
    	  } else {
    		  int index =_dm.getDxSetOfInstructors().getIndexByKey(instKey[i]);
    		  matrix = _dm.getDxSetOfInstructors().getInstructorAvaMatrix(index);
    	  }
    		  
    	  
        int dayIndexAvail= _dm.getTTStructure().findIndexInWeekTable(perK[0]);
        int perPosition= _dm.getTTStructure().getCurrentCycle().getPeriodPositionInDay(perK[0],perK[1],perK[2]);
        if(perPosition>0){
          
          if ((dayIndexAvail < matrix.length)){
            if(matrix[dayIndexAvail][perPosition-1]==_NOTAVAIL){
              nbConf++;
              description.add(new Long(instKey[i]));
            }
          }else{// else if ((dayIndexAvail < matrix.length))
            nbConf++;
            description.add(new Long(instKey[i]));
          }// end else if ((dayIndexAvail < matrix.length))
        }// end if(perPosition>0)
      }
    }// end for (int i=0; i< instKey.length; i++)
    if(nbConf>0){
      confV.addConflict("Disponibilite Enseignant",nbConf,DConst.R_INSTRUCTOR_NAME_AVAIL,description);
    }
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
    }
    return nbConf;
  }


}// end class
