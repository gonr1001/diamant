package dInternal;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */

import dResources.DConst;
import dInternal.dData.*;
import dInternal.dConditionsTest.*;
import dInternal.dUtil.*;

public class DModelProcess {
  private DModel _dm;
  private boolean modelSet=false;

  public DModelProcess() {

  }

  public void setModel(DModel dm){
    _dm= dm;
  }

  /**
  *
  */
 public void updateEventsInTTS(){

 }

 /**
  *
  * @param dayIndex
  * @param seqIndex
  * @param periodIndex
  */
 public void updateEventsInPeriod(int dayIndex, int seqIndex, int periodIndex){

  }

  /**
   *
   */
  public void setStateBarComponent(){
    if (_dm._constructionState>0){//_visibleVec = _activities.getIDsByField(3, "true");
      ((State)_dm._setOfStates.getResource(DConst.SB_T_ACT).getAttach()).setValue(_dm._setOfActivities.getIDsByField(3, "true").size());
      ((State)_dm._setOfStates.getResource(DConst.SB_T_INST).getAttach()).setValue(_dm._setOfInstructors.size());
      ((State)_dm._setOfStates.getResource(DConst.SB_T_ROOM).getAttach()).setValue(_dm._setOfRooms.size());
      ((State)_dm._setOfStates.getResource(DConst.SB_T_STUD).getAttach()).setValue(_dm._setOfStudents.size());

      ((State)_dm._setOfStates.getResource(DConst.SB_T_EVENT).getAttach()).setValue(_dm._setOfEvents.size());

      ((State)_dm._setOfStates.getResource(DConst.SB_CONF).getAttach()).setValue(10);

      ((State)_dm._setOfStates.getResource(DConst.SB_C_INST).getAttach()).setValue(1);
      ((State)_dm._setOfStates.getResource(DConst.SB_C_ROOM).getAttach()).setValue(7);
      ((State)_dm._setOfStates.getResource(DConst.SB_C_STUD).getAttach()).setValue(2);
    }
  }

  /**
  * build set of events using currentcycle, setofactivities, setofinstructors and
  * setofrooms
  */
 public void buildSetOfEvents(){
   _dm._setOfEvents = new SetOfEvents();
   if (_dm._setOfActivities!=null){
     String cycle = _dm._ttStruct.getSetOfCycles().getSetOfCycles().getResourceAt(
         _dm._ttStruct.getCurrentCycleIndex()).getID();
     _dm._setOfEvents.build(cycle, _dm._setOfActivities, _dm._setOfInstructors, _dm._setOfRooms);
   }// end if (_setOfActivities!=null)
  }

  /**
   * resize instructor availability
   */
  protected void resizeInstructorsAvailability(){
    int [][] matrix;
    InstructorAttach attach;
    for (int i=0; i< _dm._setOfInstructors.size(); i++){
      attach = (InstructorAttach)_dm._setOfInstructors.getResourceAt(i).getAttach();
      matrix=attach.getMatrixAvailability();
      matrix = DXToolsMethods.resizeAvailability(matrix,_dm._ttStruct);
      attach.setAvailability(matrix);
    }
  }


}// end classe