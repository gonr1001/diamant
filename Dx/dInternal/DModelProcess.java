package dInternal;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */

import dConstants.DConst;
import dInternal.dDataTxt.SetOfResources;
import dInternal.dDataTxt.State;
import dInternal.dUtil.DXObject;
import dInternal.dUtil.DXToolsMethods;



public class DModelProcess {
  private DModel _dm;
  //private boolean modelSet=false;

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
 }

 /**
  *
  * @param dayIndex
  * @param seqIndex
  * @param periodIndex
  */
/* public void updateEventsInPeriod(int dayIndex, int seqIndex, int periodIndex){

  }*/

  /**
   *
   */
  public void setStateBarComponent(){
    if (_dm._constructionState>0){//_visibleVec = _activities.getIDsByField(3, "true");
      ((State)_dm.getSetOfStates().getResource(DConst.SB_T_ACT).getAttach()).setValue(DModel._setOfActivities.getIDsByField(3, "true").size());
      ((State)_dm.getSetOfStates().getResource(DConst.SB_T_INST).getAttach()).setValue(DModel._setOfInstructors.size());
      ((State)_dm.getSetOfStates().getResource(DConst.SB_T_ROOM).getAttach()).setValue(DModel._setOfRooms.size());
      ((State)_dm.getSetOfStates().getResource(DConst.SB_T_STUD).getAttach()).setValue(DModel._setOfStudents.size());

      ((State)_dm.getSetOfStates().getResource(DConst.SB_T_EVENT).getAttach()).setValue(_dm._setOfEvents.size());
      ((State)_dm.getSetOfStates().getResource(DConst.SB_T_ASSIG).getAttach()).setValue(_dm._setOfEvents.getNumberOfEventAssign());

      int [] nbConf = _dm.getTTStructure().getCurrentCycle().getTotalNumberOfConflicts();

      ((State)_dm.getSetOfStates().getResource(DConst.SB_CONF).getAttach()).setValue(nbConf[0]+
          nbConf[1]+nbConf[2]);

      ((State)_dm.getSetOfStates().getResource(DConst.SB_C_INST).getAttach()).setValue(nbConf[0]);
      ((State)_dm.getSetOfStates().getResource(DConst.SB_C_ROOM).getAttach()).setValue(nbConf[1]);
      ((State)_dm.getSetOfStates().getResource(DConst.SB_C_STUD).getAttach()).setValue(nbConf[2]);
      _dm.getSetOfStates().sortSetOfResourcesByKey();
    }
  }

  /**
  * build set of events using currentcycle, setofactivities, setofinstructors and
  * setofrooms
  */
 public void buildSetOfEvents(){
   _dm._setOfEvents.getSetOfResources().removeAllElements();
   _dm._setOfEvents.setCurrentKey(1);
   if (_dm.getSetOfActivities() != null){
     _dm._setOfEvents.build(_dm.getSetOfActivities(), _dm.getSetOfImportErrors() );
     //updateEventsInTTS();
     if((_dm.getSetOfActivities()!=null) && (_dm.getSetOfStudents()!=null))//if((DModel._setOfActivities!=null) && (DModel._setOfStudents!=null))
       _dm.getSetOfActivities().buildStudentRegisteredList(_dm.getSetOfStudents());
     //_dm._conditionTest = new TestConditions(_dm);
   }// end if (_setOfActivities!=null)

  }

  /**
   * resize resource availability
   */
  public void resizeResourceAvailability(SetOfResources soRes){
    int [][] matrix;
    DXObject attach;
    for (int i=0; i< soRes.size(); i++){
      attach = soRes.getResourceAt(i).getAttach();
      matrix=attach.getMatrixAvailability();
      matrix = DXToolsMethods.resizeAvailability(matrix,_dm._ttStruct);
      //attach.setAvailability(matrix);
    }
  }


}// end classe