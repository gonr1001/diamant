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

public class SetOfEvents extends SetOfResources{

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
  public void build(String cycle, SetOfActivities soa, SetOfInstructors soi,
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
            Assignment assignement = (Assignment)((Unity)unity.getAttach()).getAssignment(cycle).getAttach();
            if(assignement!=null){
              int instructorIndex = soi.getIndexOfResource(assignement.getInstructorName());
              if(instructorIndex!=-1)
                instructorKey = soi.getResourceAt(instructorIndex).getKey();
              int roomIndex = sor.getIndexOfResource(assignement.getRoomName());
              if(roomIndex!=-1)
                roomKey = sor.getResourceAt(roomIndex).getKey();
            }// end if(assignement!=null)
            unityKey = activity.getKey()+"."+ type.getKey()+"."+section.getKey()+"."+unity.getKey()+".";
            EventAttach event = new EventAttach(unityKey,instructorKey,roomKey);
            this.addResource(new Resource(activity.getID(), event),0);
          }// end for(int l=0; l< ((Section)section.getAttach()).getSetOfUnities().size(); l++)
        }// end for(int k=0; k< ((Type)type.getAttach()).getSetOfSections().size(); k++)
      }//for(int j=0; j< activity.getSetOfTypes().size(); j++)
      }//end if(((Activity)activity.getAttach()).getActivityVisibility())
    }// end for (int i=0; i< soa.size(); i++)

  }
}