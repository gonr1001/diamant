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

import java.util.Vector;
import java.awt.Component;


public class SetOfEvents extends SetOfResources{

  public Vector _soeListeners = new Vector(1);

  /**
   * Constructor
   */
  public SetOfEvents() {
    super(6);
  }

  /**
   * Build setOfEvents
   * @param cycle
   */
  public void build(Resource cycle, SetOfActivities soa, SetOfInstructors soi,
                    SetOfRooms sor){
    long instructorKey=-1, roomKey=-1;
    String unityKey;
    for (int i=0; i< soa.size(); i++){
      Resource activity= soa.getResourceAt(i);
      if(((Activity)activity.getAttach()).getActivityVisibility()){
      for(int j=0; j< ((Activity)activity.getAttach()).getSetOfTypes().size(); j++){
        Resource type = ((Activity)activity.getAttach()).getSetOfTypes().getResourceAt(j);
        for(int k=0; k< ((Type)type.getAttach()).getSetOfSections().size(); k++){
          Resource section = ((Type)type.getAttach()).getSetOfSections().getResourceAt(k);
          for(int l=0; l< ((Section)section.getAttach()).getSetOfUnities().size(); l++){
            Resource unity= ((Section)section.getAttach()).getSetOfUnities().getResourceAt(l);
            Assignment assignment = (Assignment)((Unity)unity.getAttach()).getAssignment(cycle.getID()).getAttach();
            if(assignment!=null){
              int instructorIndex = soi.getIndexOfResource(assignment.getInstructorName());
              if(instructorIndex!=-1)
                instructorKey = soi.getResourceAt(instructorIndex).getKey();
              int roomIndex = sor.getIndexOfResource(assignment.getRoomName());
              if(roomIndex!=-1)
                roomKey = sor.getResourceAt(roomIndex).getKey();
              int[] dayTime = assignment.getDateAndTime();
              unityKey = activity.getKey()+"."+ type.getKey()+"."+section.getKey()+"."+unity.getKey()+".";
              EventAttach event = new EventAttach(unityKey,instructorKey,roomKey,
                  ((Unity)unity.getAttach()).getDuration(),
                  ((Cycle)cycle.getAttach()).getPeriod(dayTime));
              event.setEventState(((Unity)unity.getAttach()).isAssign());
              //System.out.println("Unity Key: "+unityKey+ " - Period Key: "+((Cycle)cycle.getAttach()).getPeriod(dayTime));//debug
              this.addResource(new Resource(unityKey, event),0);
            }// end if(assignement!=null)
          }// end for(int l=0; l< ((Section)section.getAttach()).getSetOfUnities().size(); l++)
        }// end for(int k=0; k< ((Type)type.getAttach()).getSetOfSections().size(); k++)
      }//for(int j=0; j< activity.getSetOfTypes().size(); j++)
      }//end if(((Activity)activity.getAttach()).getActivityVisibility())
    }// end for (int i=0; i< soa.size(); i++)

  }

  /**
   *
   * @return
   */
  public int getNumberOfEventAssign(){
    int count=0;
    for (int i=0; i< this.size(); i++){
      if(((EventAttach)getResourceAt(i).getAttach()).getEventState())
        count++;
    }// end for (int i=0; i< this.size(); i++)
    return count;
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