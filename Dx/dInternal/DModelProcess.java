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
import dInternal.dTimeTable.*;
import dInternal.dConditionsTest.*;
import dInternal.dUtil.*;

import java.util.StringTokenizer;

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
   //_dm._conditionTest = new ConditionsTest(_dm);
   _dm.getConditionsTest().initAllConditions();
   /*for (int i=0; i< _dm._setOfEvents.size(); i++){
     EventAttach event = (EventAttach)_dm._setOfEvents.getResourceAt(i).getAttach();
     StringTokenizer keys = new StringTokenizer(event.getPeriodKey(),".");
     long [] dayTimeKeys = {Long.parseLong(keys.nextToken()),Long.parseLong(keys.nextToken())
       ,Long.parseLong(keys.nextToken())};
     Period period;
     int duration = event.getDuration()/_dm._ttStruct.getPeriodLenght();
     if (_dm._ttStruct.getCurrentCycle().isPeriodContiguous(dayTimeKeys[0],dayTimeKeys[1],dayTimeKeys[2],duration))
     for (int j=0; j< duration-1; j++){
       period =_dm._ttStruct.getCurrentCycle().getPeriodByKey(dayTimeKeys[0],dayTimeKeys[1],dayTimeKeys[2]+j);
       period.getEventsInPeriod().addResource(new Resource(event.getPrincipalRescKey(),null),1);
     }// end if (_dm._ttStruct.getCurrentCycle().isPeriodContiguous(
   }// end for (int i=0; i< _dm._setOfEvents.size(); i++)
   */
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
      ((State)_dm._setOfStates.getResource(DConst.SB_T_ASSIG).getAttach()).setValue(_dm._setOfEvents.getNumberOfEventAssign());

      int [] nbConf = _dm.getTTStructure().getCurrentCycle().getTotalNumberOfConflicts();

      ((State)_dm._setOfStates.getResource(DConst.SB_CONF).getAttach()).setValue(nbConf[0]+
          nbConf[1]+nbConf[2]);

      ((State)_dm._setOfStates.getResource(DConst.SB_C_INST).getAttach()).setValue(nbConf[0]);
      ((State)_dm._setOfStates.getResource(DConst.SB_C_ROOM).getAttach()).setValue(nbConf[1]);
      ((State)_dm._setOfStates.getResource(DConst.SB_C_STUD).getAttach()).setValue(nbConf[2]);
    }
  }

  /**
  * build set of events using currentcycle, setofactivities, setofinstructors and
  * setofrooms
  */
 public void buildSetOfEvents(){
   _dm._setOfEvents = new SetOfEvents(_dm);
   if (_dm._setOfActivities!=null){
     _dm._setOfEvents.build();
     //updateEventsInTTS();
     if((_dm._setOfActivities!=null) && (_dm._setOfStudents!=null))
       _dm._setOfActivities.buildStudentRegisteredList(_dm._setOfStudents);
     _dm._conditionTest = new TestConditions(_dm);
     //_dm._conditionTest.buildStudentsMatrix(_dm._setOfActivities,_dm._setOfStudents);
     //System.out.println(conditionTest.getConflictsMatrix().toWriteMatrix());
    // _dm._setOfStates.sendEvent();
   }// end if (_setOfActivities!=null)

  }

  /**
   * resize resource availability
   */
  protected void resizeResourceAvailability(SetOfResources soRes){
    int [][] matrix;
    DXObject attach;
    for (int i=0; i< soRes.size(); i++){
      attach = soRes.getResourceAt(i).getAttach();
      //System.out.println(_dm._setOfInstructors.getResourceAt(i).getID());//debug
      matrix=attach.getMatrixAvailability();
      matrix = DXToolsMethods.resizeAvailability(matrix,_dm._ttStruct);
      attach.setAvailability(matrix);
    }
  }


}// end classe