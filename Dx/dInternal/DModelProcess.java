
/**
*
* Title: DModel $Revision: 1.32 $  $Date: 2005-04-19 20:37:48 $
* Description: DModel is a class used to
*
*
* Copyright (c) 2001 by rgr.
* All rights reserved.
*
*
* This software is the confidential and proprietary information
* of rgr. ("Confidential Information").  You
* shall not disclose such Confidential Information and shall use
* it only in accordance with the terms of the license agreement
* you entered into with rgr.
*
* @version $Revision: 1.32 $
* @author  $Author: gonzrubi $
* @since JDK1.3
*/

package dInternal;

/*
import dConstants.DConst;

import dInternal.dData.dRooms.SetOfCategories;
import dInternal.dData.dRooms.SetOfRooms;
import dInternal.dData.dRooms.SetOfSites;
import dInternal.DSetOfResources;
import dInternal.DState;

import dInternal.DObject;
import dInternal.dOptimization.TestConditions;
import dInternal.dUtil.DXToolsMethods;



public class DModelProcess {
  private DModel _dm;
  
  public DModelProcess() {

  }

  public void setModel(DModel dm){
    _dm= dm;
  }
*/
  /**
  *
  */
 /*public void updateEventsInTTS(){
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
/*  public void setStateBarComponent(){
    if (_dm._constructionState>0){//_visibleVec = _activities.getIDsByField(3, "true");
      ((DState)_dm.getSetOfStates().getResource(DConst.SB_T_ACT).getAttach()).setValue(_dm.getSetOfActivities().getIDsByField(3, "true").size());
      ((DState)_dm.getSetOfStates().getResource(DConst.SB_T_INST).getAttach()).setValue(_dm.getSetOfInstructors().size());
      ((DState)_dm.getSetOfStates().getResource(DConst.SB_T_ROOM).getAttach()).setValue(_dm.getSetOfRooms().size());
      ((DState)_dm.getSetOfStates().getResource(DConst.SB_T_STUD).getAttach()).setValue(_dm.getSetOfStudents().size());

      ((DState)_dm.getSetOfStates().getResource(DConst.SB_T_EVENT).getAttach()).setValue(_dm.getSetOfEvents().size());
      ((DState)_dm.getSetOfStates().getResource(DConst.SB_T_ASSIG).getAttach()).setValue(_dm.getSetOfEvents().getNumberOfEventAssign());

      int [] nbConf = _dm.getTTStructure().getCurrentCycle().getTotalNumberOfConflicts();

      ((DState)_dm.getSetOfStates().getResource(DConst.SB_CONF).getAttach()).setValue(nbConf[0]+
          nbConf[1]+nbConf[2]);

      ((DState)_dm.getSetOfStates().getResource(DConst.SB_C_INST).getAttach()).setValue(nbConf[0]);
      ((DState)_dm.getSetOfStates().getResource(DConst.SB_C_ROOM).getAttach()).setValue(nbConf[1]);
      ((DState)_dm.getSetOfStates().getResource(DConst.SB_C_STUD).getAttach()).setValue(nbConf[2]);
      _dm.getSetOfStates().sortSetOfResourcesByKey();
    }
  }*/

  /**
  * build set of events using currentcycle, setofactivities, setofinstructors and
  * setofrooms
  */
 /*public void buildSetOfEvents(){
   _dm._setOfEvents.getSetOfResources().removeAllElements();
   _dm._setOfEvents.setCurrentKey(1);
   if (_dm.getSetOfActivities() != null){
     _dm._setOfEvents.build(_dm.getSetOfActivities(), _dm.getSetOfImportErrors() );
     //updateEventsInTTS();
     if((_dm.getSetOfActivities()!=null) && (_dm.getSetOfStudents()!=null))//if((DModel._setOfActivities!=null) && (DModel._setOfStudents!=null))
       _dm.getSetOfActivities().buildStudentRegisteredList(_dm.getSetOfStudents());
     	_dm._conditionTest = new TestConditions(_dm);
   }// end if (_setOfActivities!=null)

  }*/

  /**
   * resize resource availability
   */
 /* public void resizeResourceAvailability(DSetOfResources soRes){
    int [][] matrix;
    DObject attach;
    for (int i=0; i< soRes.size(); i++){
      attach = soRes.getResourceAt(i).getAttach();
      matrix=attach.getMatrixAvailability();
      matrix = DXToolsMethods.resizeAvailability(matrix,_dm.getTTStructure());
      //attach.setAvailability(matrix);
    }
  }*/
  /**
   * resize resource availability TO delete
   */
 /* public void resizeResourceAvailability(DSetOfResources soRes){
    int [][] matrix;
    DObject attach;
    for (int i=0; i< soRes.size(); i++){
      attach = soRes.getResourceAt(i).getAttach();
      matrix=attach.getMatrixAvailability();
      matrix = DXToolsMethods.resizeAvailability(matrix,_dm._ttStruct);
      //attach.setAvailability(matrix);
    }
  }*/
	/**
	 * @param ofSites
	 */
/*	public void resizeSiteAvailability(SetOfSites setOfSites) {
		int [][] matrix;
	    DObject attach;
	    for (int i=0; i< setOfSites.size(); i++){
	    	SetOfCategories soc = (SetOfCategories) setOfSites.getResourceAt(i).getAttach();
	    	for (int j=0; j < soc.size(); j++) {
	    		SetOfRooms sor = (SetOfRooms) soc.getResourceAt(j).getAttach();
	    		for (int k=0; k < sor.size();k++){
	    			attach = sor.getResourceAt(i).getAttach();
	    		      matrix=attach.getMatrixAvailability();
	    		      matrix = DXToolsMethods.resizeAvailability(matrix,_dm.getTTStructure());
	    		}//end for (int k=0; k < sor.size();k++){
	    	} //end for (int j=0; j < soc.size(); j++) {
	    }//end for (int i=0; i< setOfSites.size(); i++){	
	} //resizeSiteAvailability
*/
//}// end classe