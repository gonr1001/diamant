package dInternal.dConditionsTest;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */

import dInternal.dTimeTable.Period;
import dInternal.dData.SetOfActivities;
import dInternal.dData.Activity;
import dInternal.dData.Type;
//import dInternal.dUtil.DXValue;
import dInternal.dData.SetOfResources;
import dInternal.dData.Resource;
import dInternal.dUtil.DXToolsMethods;
import java.util.StringTokenizer;
import java.util.Vector;
import dResources.DConst;

public class TestStudentsConditions implements Condition {
  StudentsConflictsMatrix _matrix;
  SetOfActivities _soa;

  public TestStudentsConditions(StudentsConflictsMatrix matrix, SetOfActivities soa) {
    _matrix = matrix;
    _soa = soa;
  }

  public int executeTest(Period period, String eventKey, int operation){
    String key1= DXToolsMethods.getToken(eventKey,DConst.TOKENSEPARATOR,0)+DConst.TOKENSEPARATOR+
                 DXToolsMethods.getToken(eventKey,DConst.TOKENSEPARATOR,1)+DConst.TOKENSEPARATOR+
                 DXToolsMethods.getToken(eventKey,DConst.TOKENSEPARATOR,2);
    int number=0;
    //if (period.getEventsInPeriod().getIndexOfResource(eventKey)==-1){
      int nbConf=0;
      ConflictsAttach confVal= new ConflictsAttach();
      for (int i=0; i< period.getEventsInPeriod().size(); i++){
        String event2= period.getEventsInPeriod().getResourceAt(i).getID();
        String key2= DXToolsMethods.getToken(event2,DConst.TOKENSEPARATOR,0)+DConst.TOKENSEPARATOR+
                     DXToolsMethods.getToken(event2,DConst.TOKENSEPARATOR,1)+DConst.TOKENSEPARATOR+
                     DXToolsMethods.getToken(event2,DConst.TOKENSEPARATOR,2);
        if(!key1.equalsIgnoreCase(key2)){
          nbConf= _matrix.getNumberOfCOnflicts(key1, key2);
          number+= nbConf;
          if (nbConf!=0)
            confVal.addConflict(period.getEventsInPeriod().getResourceAt(i).getID(),nbConf,0,new Vector());
        }// end if(!key1.equalsIgnoreCase(key2))
      }// end for (int i=0; i< period.getEventsInPeriod().size(); i++)

      switch(operation){
          case 0:
            break;
          case 1:
            Resource resc=period.getEventsInPeriod().getResource(eventKey);
            if(resc!=null)
              ((ConflictsAttach)resc.getAttach()).mergeConflictsAttach(confVal);
            else
              period.getEventsInPeriod().addResource(new Resource(eventKey,confVal),1);
            period.addNbStudConflict(number);
            break;
          case -1:
            period.getEventsInPeriod().removeResource(eventKey);
              period.removeNbStudConflict(number);
              for(int i=0; i< period.getEventsInPeriod().size(); i++)
                ((ConflictsAttach)period.getEventsInPeriod().getResourceAt(i).
                 getAttach()).removeConflict(eventKey,0);

            break;
        }
        //return 0;
    //}// end if (period.getEventsInPeriod().getIndexOfResource(eventKey)==-1)
    return number;
  }
}