package dInternal.dConditionsTest;

import dInternal.dData.SetOfResources;
import dInternal.dData.Resource;
import dInternal.dData.SetOfActivities;
import dInternal.dData.Activity;
import dInternal.dData.Type;
import dInternal.dData.Section;
import dInternal.dData.Unity;
import dInternal.dData.Assignment;
import dInternal.dData.SetOfInstructors;
import dInternal.dData.SetOfRooms;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Period;
import dInternal.dUtil.DXToolsMethods;
import dInternal.DModel;
import dResources.DConst;

import java.util.Vector;
import java.util.StringTokenizer;
import java.awt.Component;


public class SetOfEvents extends SetOfResources{

  public Vector _soeListeners = new Vector(1);
  protected boolean _isEventPlaced=false;
  private DModel _dm;
  private String _UNAVAILABLE= "------";

  /***
   * Constructor
   */
  public SetOfEvents(DModel dm) {
    super(6);
    _dm= dm;
  }


  /**
   * Build setOfEvents from activities
   * @param cycle
   */
  public void build(){
    String unityKey;
    for (int i=0; i< _dm.getSetOfActivities().size(); i++){
      Resource activity= _dm.getSetOfActivities().getResourceAt(i);
      long instructorKey=-1, roomKey=-1;
      if(((Activity)activity.getAttach()).getActivityVisibility()){
      for(int j=0; j< ((Activity)activity.getAttach()).getSetOfTypes().size(); j++){
        Resource type = ((Activity)activity.getAttach()).getSetOfTypes().getResourceAt(j);
        for(int k=0; k< ((Type)type.getAttach()).getSetOfSections().size(); k++){
          Resource section = ((Type)type.getAttach()).getSetOfSections().getResourceAt(k);
          for(int l=0; l< ((Section)section.getAttach()).getSetOfUnities().size(); l++){
            Resource unity= ((Section)section.getAttach()).getSetOfUnities().getResourceAt(l);
            Assignment assignment = (Assignment)((Unity)unity.getAttach()).getAssignment(
                _dm.getTTStructure().getCurrentCycleResource().getID()).getAttach();
            if(assignment!=null){
              int instructorIndex = _dm.getSetOfInstructors().getIndexOfResource(assignment.getInstructorName());
              if(instructorIndex!=-1)
                instructorKey = _dm.getSetOfInstructors().getResourceAt(instructorIndex).getKey();
              int roomIndex = _dm.getSetOfRooms().getIndexOfResource(assignment.getRoomName());
              if(roomIndex!=-1)
                roomKey = _dm.getSetOfRooms().getResourceAt(roomIndex).getKey();
              int[] dayTime = assignment.getDateAndTime();
              unityKey = activity.getKey()+"."+ type.getKey()+"."+section.getKey()+"."+unity.getKey()+".";
              String unityID = activity.getID()+"."+ type.getID()+"."+section.getID()+"."+unity.getID()+".";
              EventAttach event = new EventAttach(unityKey,instructorKey,roomKey,
                  ((Unity)unity.getAttach()).getDuration(),
                  ((Cycle)_dm.getTTStructure().getCurrentCycleResource().getAttach()).getPeriod(dayTime));
              event.setAssignState(((Unity)unity.getAttach()).isAssign());
              event.setPermanentState(((Unity)unity.getAttach()).isPermanent());
              //System.out.println("Unity Key: "+unityKey+ " - Period Key: "+((Cycle)cycle.getAttach()).getPeriod(dayTime));//debug
              this.addResource(new Resource(unityID, event),0);
            }// end if(assignement!=null)
          }// end for(int l=0; l< ((Section)section.getAttach()).getSetOfUnities().size(); l++)
        }// end for(int k=0; k< ((Type)type.getAttach()).getSetOfSections().size(); k++)
      }//for(int j=0; j< activity.getSetOfTypes().size(); j++)
      }//end if(((Activity)activity.getAttach()).getActivityVisibility())
    }// end for (int i=0; i< soa.size(); i++)

  }

  /**
   * get the complet activity name of an event
   * @param eventKey
   * @param soa
   * @return
   */
  public String getEventID(String eventID, SetOfActivities soa){
    String id= eventID;
    StringTokenizer event1 = new StringTokenizer(eventID,DConst.TOKENSEPARATOR);
    if(event1.countTokens()>=4){
      Resource activity = soa.getResource(event1.nextToken());
      Resource type = ((Activity)activity.getAttach()).getSetOfTypes().getResource(event1.nextToken());
      Resource section = ((Type)type.getAttach()).getSetOfSections().getResource(event1.nextToken());
      Resource unity = ((Section)section.getAttach()).getSetOfUnities().getResource(event1.nextToken());
      id= activity.getID()+DConst.TOKENSEPARATOR+type.getID()+
          DConst.TOKENSEPARATOR+section.getID()+DConst.TOKENSEPARATOR+unity.getID();
    }
    return id;
  }

  /**
   *
   * @return
   */
  public int getNumberOfEventAssign(){
    int count=0;
    if(_isEventPlaced)
    for (int i=0; i< this.size(); i++){
      if(((EventAttach)getResourceAt(i).getAttach()).getAssignState())
        count++;
    }// end for (int i=0; i< this.size(); i++)
    return count;
  }

  /**
   * update activities from events
   */
  public void updateActivities(Vector eventsToUpdate){
    EventAttach event;//= (EventAttach)((Resource)_unities.get(_currentActivityIndex)).getAttach();
    for (int i=0; i< eventsToUpdate.size(); i++){
      event=(EventAttach)((Resource)eventsToUpdate.get(i)).getAttach();
      long actKey= Long.parseLong(DXToolsMethods.getToken(event.getPrincipalRescKey(),".",0));
      long typeKey= Long.parseLong(DXToolsMethods.getToken(event.getPrincipalRescKey(),".",1));
      long sectKey= Long.parseLong(DXToolsMethods.getToken(event.getPrincipalRescKey(),".",2));
      long unitKey= Long.parseLong(DXToolsMethods.getToken(event.getPrincipalRescKey(),".",3));
      Unity unity= _dm.getSetOfActivities().getUnity(actKey,typeKey,sectKey,unitKey);
      Assignment assignment= (Assignment)unity.getSetOfAssignments().getResourceAt(
          _dm.getTTStructure().getCurrentCycleIndex()).getAttach();
      assignment.setInstructor(getRescName(_dm.getSetOfInstructors(),event.getInstructorKey()));
      assignment.setRoom(getRescName(_dm.getSetOfRooms(),event.getRoomKey()));
      long dayKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
      long seqKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",1));
      long perKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",2));
      Period period= _dm.getTTStructure().getCurrentCycle().getPeriodByKey(dayKey,seqKey,perKey);
      assignment.setDateAndTime((int)dayKey,period.getBeginHour()[0],period.getBeginHour()[1]);
      unity.setAssign(event.getAssignState());
      unity.setPermanent(event.getPermanentState());
    }// end for (int i=0; i< eventsToUpdate.size(); i++)

  }

  /**
   * get a resource key
   * @param soresc
   * @param elt
   * @return the resource key or -1 if key does not found
   */
  private String getRescName(SetOfResources soresc, long eltkey){
    if (eltkey!=-1){
      return soresc.getResource(eltkey).getID();
    }
    return _UNAVAILABLE;
  }


  /**
  *
  * @param component
  */
public void sendEvent(Component component) {
  SetOfEventsEvent event = new SetOfEventsEvent(this);
  for (int i=0; i< _soeListeners.size(); i++) {
    SetOfEventsListener soel = (SetOfEventsListener) _soeListeners.elementAt(i);
    soel.changeInSetOfEvents(event, component);
  }
 }

 /**
  *
  * @param dml
  */
 public synchronized void addSetOfEventsListener(SetOfEventsListener sorl) {
   if (_soeListeners.contains(sorl)){
     return;
   }
   _soeListeners.addElement(sorl);
   System.out.println("addSetOfEvents Listener ...");//debug
  }


}// end class