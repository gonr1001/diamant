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
import dInternal.dData.SetOfResources;
import dInternal.dData.Resource;
import java.util.StringTokenizer;
import dResources.DConst;

public class TestStudentsConditions implements Condition {
  StudentsConflictsMatrix _matrix;
  SetOfActivities _soa;

  public TestStudentsConditions(StudentsConflictsMatrix matrix, SetOfActivities soa) {
    _matrix = matrix;
    _soa = soa;
  }

  public int executeTest(Period period, String eventKey, int operation){
    StringTokenizer event1 = new StringTokenizer(eventKey,DConst.TOKENSEPARATOR);
    Resource activity = _soa.getResource(Long.parseLong(event1.nextToken()));
    Resource type = ((Activity)activity.getAttach()).getSetOfTypes().getResource(Long.parseLong(event1.nextToken()));
    Resource section = ((Type)type.getAttach()).getSetOfSections().getResource(Long.parseLong(event1.nextToken()));
    String key1= activity.getID()+DConst.TOKENSEPARATOR+type.getID()+
                 DConst.TOKENSEPARATOR+section.getID();
    int number=0;
    if (period.getEventsInPeriod().getIndexOfResource(eventKey)==-1){
      for (int i=0; i< period.getEventsInPeriod().size(); i++){
        StringTokenizer event2 = new StringTokenizer(period.getEventsInPeriod()
        .getResourceAt(i).getID(),DConst.TOKENSEPARATOR);
        activity = _soa.getResource(Long.parseLong(event2.nextToken()));
        type = ((Activity)activity.getAttach()).getSetOfTypes().getResource(Long.parseLong(event2.nextToken()));
        section = ((Type)type.getAttach()).getSetOfSections().getResource(Long.parseLong(event2.nextToken()));
        String key2=  activity.getID()+DConst.TOKENSEPARATOR+type.getID()+
                 DConst.TOKENSEPARATOR+section.getID();
        number+= _matrix.getNumberOfCOnflicts(key1, key2);

      }// end for (int i=0; i< period.getEventsInPeriod().size(); i++)
      switch(operation){
          case 0:
            break;
          case 1:period.getEventsInPeriod().addResource(new Resource(eventKey,null),1);
            period.addNbStudConflict(number);
            break;
          case -1:period.getEventsInPeriod().removeResource(eventKey);
            period.removeNbStudConflict(number);
            break;
        }

    }// end if (period.getEventsInPeriod().getIndexOfResource(eventKey)==-1)
    return -1;
  }
}