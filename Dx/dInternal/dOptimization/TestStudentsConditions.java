package dInternal.dOptimization;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */

import java.util.Vector;

import dConstants.DConst;
import dInternal.dDataTxt.Resource;
import dInternal.dDataTxt.SetOfActivities;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Period;
import dInternal.dUtil.DXToolsMethods;

public class TestStudentsConditions implements Condition {
  StudentsConflictsMatrix _matrix;
  SetOfActivities _soa;
  Cycle _cycle;
  int _periodVariationEvents=0;

  /**
   *
   * @param matrix
   * @param soa
   * @param cycle
   */
  public TestStudentsConditions(StudentsConflictsMatrix matrix, SetOfActivities soa, Cycle cycle) {
    _matrix = matrix;
    _soa = soa;
    _cycle= cycle;
  }

  /**
   *
   * @param periodVariationEvents
   */
  public void setPeriodVariationEvents(int periodVariationEvents){
    _periodVariationEvents = periodVariationEvents;
  }

  /**
   *
   * @param perKey
   * @param period
   * @param eventKey
   * @param operation
   * @return
   */
  public int executeTest(int[] perKey, Period per, String eventKey, int operation){
    String key1= DXToolsMethods.getToken(eventKey,DConst.TOKENSEPARATOR,0)+DConst.TOKENSEPARATOR+
                 DXToolsMethods.getToken(eventKey,DConst.TOKENSEPARATOR,1)+DConst.TOKENSEPARATOR+
                 DXToolsMethods.getToken(eventKey,DConst.TOKENSEPARATOR,2);
    int number=0;
    //if (period.getEventsInPeriod().getIndexOfResource(eventKey)==-1){
      int nbConf=0;
      ConflictsAttach confVal= new ConflictsAttach();
      Vector allPeriods= new Vector(1);
      Period period;
      if (operation==0)
        allPeriods= periodVariationEventsPeriods(perKey);
      else
        allPeriods.add(per);

      for(int a=0; a< allPeriods.size(); a++){
        period= (Period)allPeriods.get(a);

        for (int i=0; i< period.getEventsInPeriod().size(); i++){
          String event2= period.getEventsInPeriod().getResourceAt(i).getID();
          String key2= DXToolsMethods.getToken(event2,DConst.TOKENSEPARATOR,0)+DConst.TOKENSEPARATOR+
                       DXToolsMethods.getToken(event2,DConst.TOKENSEPARATOR,1)+DConst.TOKENSEPARATOR+
                       DXToolsMethods.getToken(event2,DConst.TOKENSEPARATOR,2);
          if(!key1.equalsIgnoreCase(key2)){
            nbConf= _matrix.getNumberOfCOnflicts(key1, key2);
            number+= nbConf;
            if (nbConf!=0)
              confVal.addConflict(period.getEventsInPeriod().getResourceAt(i).getID(),nbConf,DConst.R_STUDENT_NAME,new Vector());
          }// end if(!key1.equalsIgnoreCase(key2))
        }// end for (int i=0; i< period.getEventsInPeriod().size(); i++)

      }//for(int a=0; a< allPeriods.size(); a++)

      switch(operation){
          case 0:
            break;
          case 1:
            Resource resc=per.getEventsInPeriod().getResource(eventKey);
            if(resc!=null)
              ((ConflictsAttach)resc.getAttach()).mergeConflictsAttach(confVal);
            else
              per.getEventsInPeriod().addResource(new Resource(eventKey,confVal),1);
            per.addNbStudConflict(number);
            break;
          case -1:
            per.getEventsInPeriod().removeResource(eventKey);
              per.removeNbStudConflict(number);
              for(int i=0; i< per.getEventsInPeriod().size(); i++)
                ((ConflictsAttach)per.getEventsInPeriod().getResourceAt(i).
                 getAttach()).removeConflict(eventKey,DConst.R_STUDENT_NAME);

            break;
        }
        //return 0;
    //}// end if (period.getEventsInPeriod().getIndexOfResource(eventKey)==-1)
    return number;
  }

 /**
  * check a students conflicts of an event in a definite periods
  * @param perKey int[] (0=day, 1=seq, 2=per)
  * @param eventDuration (in number of periods)
  * @return
  */
 public Vector periodVariationEventsPeriods(int[] perKey){
   Vector periodsVector= new Vector(1);
   _cycle.setCurrentDaySeqPerIndex(perKey[0]-1,perKey[1]-1,perKey[2]-1);
   _cycle.getPreviousPeriod(1);
   // get previous periods
   for (int i=0; i< _periodVariationEvents; i++){
     Period per=_cycle.getPreviousPeriod(1);
     periodsVector.insertElementAt (per,0);
   }
   // get next periods
   _cycle.setCurrentDaySeqPerIndex(perKey[0]-1,perKey[1]-1,perKey[2]-1);
   for (int i=0; i< _periodVariationEvents+1; i++){
     Period per=_cycle.getNextPeriod(1);
     periodsVector.add(per);
   }
   return periodsVector;
  }


}