package dInternal.dConditionsTest;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */
import dInternal.DModel;
import dInternal.dTimeTable.Period;
import dInternal.dData.SetOfRooms;
import dInternal.dData.RoomAttach;
import dInternal.dData.Activity;
import dInternal.dData.Type;
import dInternal.dUtil.DXToolsMethods;
import dInternal.dData.Resource;
import dResources.DConst;
import java.util.StringTokenizer;
import java.util.Vector;


public class TestRoomsConditions implements Condition{

  DModel _dm;
  /**
   * Constructor
   * @param sor
   */
  public TestRoomsConditions(DModel dm) {
    _dm= dm;
  }

  /**
   *
   * @param period
   * @param eventKey
   * @param operation
   * @return
   */
  public int executeTest(Period period, String eventKey, int operation){
    /*StringTokenizer event1 = new StringTokenizer(eventKey,DConst.TOKENSEPARATOR);
   Resource activity = _dm.getSetOfActivities() .getResource(Long.parseLong(event1.nextToken()));
   Resource type = ((Activity)activity.getAttach()).getSetOfTypes().getResource(Long.parseLong(event1.nextToken()));
   Resource section = ((Type)type.getAttach()).getSetOfSections().getResource(Long.parseLong(event1.nextToken()));
   String key1= activity.getID()+DConst.TOKENSEPARATOR+type.getID()+
                DConst.TOKENSEPARATOR+section.getID();*/
   int number=0;
   //if (period.getEventsInPeriod().getIndexOfResource(eventKey)==-1){
     int nbConf;
     ConflictsAttach confVal= new ConflictsAttach();
     //for (int i=0; i< period.getEventsInPeriod().size(); i++){
        //String key2 = period.getEventsInPeriod().getResourceAt(i).getID();
        nbConf= roomAvailibilityConflicts(period,eventKey);
        number+= nbConf;
        if (nbConf!=0)
          confVal.addConflict("Disponibilite",nbConf,1,new Vector());
      //}// end for (int i=0; i< period.getEventsInPeriod().size(); i++)


     switch(operation){
       case 0:
         break;
       case 1:
         period.getEventsInPeriod().addResource(new Resource(eventKey,confVal),1);
         period.addNbRoomsConflict(number);
         break;
       case -1:period.getEventsInPeriod().removeResource(eventKey);
         period.removeNbRoomsConflict(number);
         for(int i=0; i< period.getEventsInPeriod().size(); i++)
           ((ConflictsAttach)period.getEventsInPeriod().getResourceAt(i).
            getAttach()).removeConflict(eventKey,1);
         break;
     }
     //return 0;
   //}// end if (period.getEventsInPeriod().getIndexOfResource(eventKey)==-1)
    return -1;
  }

  /**
   *
   * @param period
   * @param eventKey
   * @return
   */
  private int roomAvailibilityConflicts(Period period, String eventKey){
    EventAttach event = (EventAttach)_dm.getSetOfEvents().getResource(eventKey).getAttach();
    long roomKey = event.getRoomKey();
    if(roomKey!=-1){
      RoomAttach room = (RoomAttach)_dm.getSetOfRooms().getResource(roomKey).getAttach();
      long dayKey = Integer.parseInt(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
      int dayIndexAvail= _dm.getTTStructure().findIndexInWeekTable(dayKey);
      if(room.getMatrixAvailability()[dayIndexAvail][0]==_NOTAVAIL)
        return 1;
    }
    return 0;
  }

  private int _NOTAVAIL=5;
}