package dInternal.dOptimization;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */
import java.util.StringTokenizer;
import java.util.Vector;

import dConstants.DConst;
import dInterface.dUtil.DXTools;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.dData.dActivities.Activity;
import dInternal.dData.dRooms.RoomAttach;
//import dInternal.dDataTxt.RoomAttach;
import dInternal.dData.dActivities.Type;
import dInternal.dTimeTable.Period;
import dInternal.dUtil.DXToolsMethods;


public class TestRoomsConditions implements Condition{

  private DModel _dm;
  private int _NOTAVAIL=5;
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
  public int executeTest(int[] perKey, Period period, String eventKey, int operation){
    int number=0;
    int nbConf1, nbConf2,nbConf3=0;
    perKey[0]=perKey[0];
    ConflictsAttach confVal= new ConflictsAttach();
    nbConf1= roomAvailibilityConflicts(period,eventKey);
    nbConf2= roomCapacityConflicts(/*period,*/eventKey);
    nbConf3= roomEventsConflicts(period,eventKey, confVal);
    number= nbConf1+nbConf2+nbConf3;
    if (nbConf1!=0)
      confVal.addConflict("Disponibilite Local",nbConf1,DConst.R_ROOM_NAME,new Vector());
    if (nbConf2!=0)
      confVal.addConflict("Capacité Local",nbConf2,DConst.R_ROOM_NAME,new Vector());

    switch(operation){
      case 0:
        break;
      case 1:
        DResource resc = period.getEventsInPeriod().getResource(eventKey);
        if(resc!=null)
          ((ConflictsAttach)resc.getAttach()).mergeConflictsAttach(confVal);
        else
          period.getEventsInPeriod().addResource(new DResource(eventKey,confVal),1);
        period.addNbRoomsConflict(number);
         break;
       case -1:period.getEventsInPeriod().removeResource(eventKey);
         period.removeNbRoomsConflict(number);
         for(int i=0; i< period.getEventsInPeriod().size(); i++)
           ((ConflictsAttach)period.getEventsInPeriod().getResourceAt(i).
            getAttach()).removeConflict(eventKey,DConst.R_ROOM_NAME);
         break;
     }
     //return 0;
   //}// end if (period.getEventsInPeriod().getIndexOfResource(eventKey)==-1)
    return number;
  }

  /**
   *check room availability conflicts
   * @param period
   * @param eventKey
   * @return
   */
  private int roomAvailibilityConflicts(Period period, String eventKey){
    EventAttach event = (EventAttach)_dm.getSetOfEvents().getResource(eventKey).getAttach();
    long roomKey = event.getRoomKey();
    if((roomKey!=-1) && (event.getPeriodKey().length()!=0)){
      RoomAttach room = (RoomAttach)_dm.getSetOfRooms().getResource(roomKey).getAttach();
      long dayKey = Integer.parseInt(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
      int[] dayTime={(int)dayKey, period.getBeginHour()[0],period.getBeginHour()[1]};
      String thePeriod= _dm.getTTStructure().getCurrentCycle().getPeriod(dayTime);
      long seqKey = Integer.parseInt(DXToolsMethods.getToken(thePeriod,".",1));
      long perKey = Integer.parseInt(DXToolsMethods.getToken(thePeriod,".",2));
      int dayIndexAvail= _dm.getTTStructure().findIndexInWeekTable(dayKey);
      int perPosition= _dm.getTTStructure().getCurrentCycle().getPeriodPositionInDay(dayKey,seqKey,perKey);
      if(perPosition>0){
        int [][] matrix= room.getMatrixAvailability();
        if ((dayIndexAvail < matrix.length)){
        if(room.getMatrixAvailability()[dayIndexAvail][perPosition-1]==_NOTAVAIL)
          return 1;
        }else{// else if ((dayIndexAvail < matrix.length))
            return 1;
          }// end else if ((dayIndexAvail < matrix.length))
      }// end if(perPosition>0)
    }
    return 0;
  }

  /**
   * Check room capicity
   * @param period
   * @param eventKey
   * @return
   */
   private int roomCapacityConflicts(/*Period period,*/ String eventKey){
   EventAttach event = (EventAttach)_dm.getSetOfEvents().getResource(eventKey).getAttach();
    StringTokenizer event1 = new StringTokenizer(eventKey,DConst.TOKENSEPARATOR);
    DResource activity = _dm.getSetOfActivities().getResource(event1.nextToken());
    DResource type = ((Activity)activity.getAttach()).getSetOfTypes().getResource(event1.nextToken());
    DResource section = ((Type)type.getAttach()).getSetOfSections().getResource(event1.nextToken());
    int nbOfStudents= _dm.getSetOfStudents().getStudentsByGroup(activity.getID(),
        type.getID(), DXTools.STIConvertGroupToInt(section.getID())).size();
    long roomKey = event.getRoomKey();
   if(roomKey!=-1){
      RoomAttach room = (RoomAttach)_dm.getSetOfRooms().getResource(roomKey).getAttach();
      if(room.getCapacity()< nbOfStudents)
        return 1;
   }
    return 0;
  }

  /**
   * Check rooms conflicts between 2 events
   * @param period
   * @param eventKey
   * @return
   */
  private int roomEventsConflicts(Period period, String eventKey, ConflictsAttach confV){
    EventAttach event1 = (EventAttach)_dm.getSetOfEvents().getResource(eventKey).getAttach();
    EventAttach event2;
    int nbConf=0;
    for(int i=0; i< period.getEventsInPeriod().size(); i++){
      String event2ID = period.getEventsInPeriod().getResourceAt(i).getID();
      event2= (EventAttach)_dm.getSetOfEvents().getResource(event2ID).getAttach();
      if(!event1.getPrincipalRescKey().equalsIgnoreCase(event2.getPrincipalRescKey())){
        if((event1.getRoomKey()==event2.getRoomKey()) && (event1.getRoomKey()!=-1)){
          confV.addConflict(period.getEventsInPeriod().getResourceAt(i).getID(),1,DConst.R_ROOM_NAME,new Vector());
          nbConf++;
        }
      }
    }
    return nbConf;
  }




}