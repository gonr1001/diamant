/**
*
* Title: TestConditions $Revision: 1.37 $  $Date: 2004-12-16 19:21:02 $
* Description: TestConditions is a class used to
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
* @version $Revision: 1.37 $
* @author  $Author: gonzrubi $
* @since JDK1.3
*/

package dInternal.dOptimization;

import java.util.StringTokenizer;
import java.util.Vector;

import dConstants.DConst;
import dInternal.DModel;
//import dInternal.dDataTxt.Resource;
import dInternal.DResource;
import dInternal.dDataTxt.Resource;
import dInternal.dTimeTable.Day;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.Sequence;
import dInternal.dTimeTable.TTStructure;

public class TestConditions {

 private StudentsConflictsMatrix _matrix;
 private DModel _dm;
 private Vector _testToRun = new Vector(1);
 private boolean _matrixIsBuilded= false;
 private int[] _avoidPriority={1,2};
 private int [] _acceptableConflictsTable={0,0,0};
 private int _periodAcceptableSize=20;
 private int _periodVariationEvents=0;
 
 
 /**
  * Constructor
  * @param dm
  * 
  */
 public TestConditions(DModel dm) {
   _dm= dm;
    _matrix = new StudentsConflictsMatrix();
    _testToRun.add(new TestStudentsConditions(_matrix, _dm.getSetOfActivities(), _dm.getTTStructure().getCurrentCycle()));
    _testToRun.add( new TestInstructorsConditions(_dm));
    _testToRun.add( new TestRoomsConditions(_dm));
    //dm.getDDocument().getDMediator().getDApplication().getPreferences();
  }

  public StudentsConflictsMatrix getConflictsMatrix(){
    return _matrix;
  }

  /**
   * 
   * @return
   */
  public int[] getAvoidPriorityTable(){
    return _avoidPriority;
  }

  /**
   *
   * @return
   */
  public int getPeriodAcceptableSize(){
    return _periodAcceptableSize;
  }

  /**
   *
   * @return int[] acceptableConflictsTable range 0= student, range 1= instructor
   * range 2= room
   */
  public int[] getAcceptableConflictsTable(){
    return _acceptableConflictsTable;
  }

  /**
   *
   * @param avoidPriority
   */
  /*public void emptyAvoidPriorityTable(){
    int[] avoidPriority= {};
    _avoidPriority= avoidPriority;
  }*/

  /**
   * build student conflict matrix
   * @param soa
   * @param sos
   */
  /*private void buildStudentsMatrix(SetOfActivities soa, SetOfStudents sos) {
    _matrix.buildMatrix(soa, sos);
  }*/

 /**
  *
  */
  public void buildStudentConflictMatrix(){
    if (!_matrixIsBuilded){
     _matrix.buildMatrix(_dm);
     _matrixIsBuilded= true;
    }
 }

 /**
  * set the _matrixIsBuilded value to true
  */
 public void setMatrixBuilded(boolean value, boolean doFirstGroupAssignement){
   _matrixIsBuilded=value;
   if(doFirstGroupAssignement)
     _matrix.doFirstGroupAssignement();
 }

 /**
  *
  */
  public void initAllConditions(){
    buildStudentConflictMatrix();
    extractPreference();
    buildAllConditions(_dm.getTTStructure());
  }


  /**
   * buildAllConditions all assigned events in SetOfEvents are placed
   *                    in the corresponding period in the ttStructure
   * 					further more, if an event has an invalid property
   * 					according with the ttStructure, the SetOfActivities
   * 					will be updated.
   * @param tts <p>a TTStructure where periods are updated. </p>
   *            <p> TTStructure could be modified </p>
   * 			<p> an Event in the SetOfEvents could be modified  (_dm.getSetOfEvents) </p>
   * 			<p> an Activity in the SetOfActivities could be modified  (_dm.getSetOfActivities) </p>
   */
	public void buildAllConditions(TTStructure tts){
    if(_matrixIsBuilded){
      tts.getCurrentCycle().emptyAllEventsInPeriod();
     
      for (int i=0; i< _dm.getSetOfEvents().size(); i++){
        DResource event = _dm.getSetOfEvents().getResourceAt(i);
        addEventInTTs(tts, event, false);
      }// end for (int i=0; i< _dm.getSetOfEvents().size(); i++)    
      _dm.getSetOfEvents().updateActivities(_dm.getSetOfActivities(),
      										_dm.getSetOfEvents().getSetOfResources());
    }
  }
	


  public int[] addEventInTTs(TTStructure tts, DResource event,  boolean usePriority) {
  	return addOrRemoveOrGetConflictsEventInTTs(tts,event, 1, usePriority);
  }
  
  public int[] removeEventInTTs(TTStructure tts, DResource event,  boolean usePriority) {
  	return addOrRemoveOrGetConflictsEventInTTs(tts,event, -1, usePriority);
  }
  
  public int[] getEventConflictsInTTs(TTStructure tts, DResource event,  boolean usePriority) {
  	return addOrRemoveOrGetConflictsEventInTTs(tts,event, 0, usePriority);
  }
  

  /**
   * addOrRemoveOrGetConflictsEventInTTs
   * <p> if add an event in tts the event is placed in the corresponding Periods </p>
   * <p> if remove an event in tts the event is removed from the corresponding Periods </p>
   * <p> if getConflicts of an event in tts the Conflicts are calculated for the corresponding periods 
   *     (where the event is plan to be placed ) </p>
   * 
   * @param tts the TTStructure to be used (it can be modified)
   * @param event the Event to work with
   * @param int operation -1= remove event, 0= getConflicts, 1= add event
   * @return int [] conflicts after the operation
   * <p>range 0= nb of students conflicts </p>
   * <p>range 1= nb of instructors conflicts  </p>
   * <p> range 2= nb of rooms conflicts  </p>
   */
  private int[] addOrRemoveOrGetConflictsEventInTTs(TTStructure tts, DResource event, int operation, boolean usePriority ){
    int[] numberOfConflicts={0,0,0};

    if(((EventAttach)event.getAttach()).getAssignState()){
      StringTokenizer periodKey = new StringTokenizer(((EventAttach)event.getAttach()).getPeriodKey(),DConst.TOKENSEPARATOR);
      int[] perKey={Integer.parseInt(periodKey.nextToken()),Integer.parseInt(periodKey.nextToken()),Integer.parseInt(periodKey.nextToken())};
      int duration = ((EventAttach)event.getAttach()).getDuration()/tts.getPeriodLenght();
     
      if ((tts.getCurrentCycle().isPeriodContiguous(perKey[0],perKey[1],perKey[2],
          duration, _avoidPriority, usePriority)) && (duration>0) ){
      	
        for (int j=0; j< duration; j++){
          Period per = tts.getCurrentCycle().getPeriodByKey(perKey[0],perKey[1],perKey[2]+j);
          int [] newPerKey={perKey[0],perKey[1],perKey[2]+j};
          
          for (int k=0; k < _testToRun.size(); k++){
            Condition cond = (Condition)_testToRun.get(k);
            numberOfConflicts[k] += cond.executeTest(newPerKey,per,event.getID(),operation);
          }// end  for (int j=0; j< _testToRun.size(); j++)
          
          if (operation!=0){
            ((EventAttach)event.getAttach()).setInAPeriod(getBooleanValue(operation));
            ((EventAttach)event.getAttach()).setAssignState(getBooleanValue(operation));
          }
        }// end for (int j=0; j< ((EventAttach)event.getAttach())
      }else{// end if (tts.getCurrentCycle().isPeriodContiguous(
        ((EventAttach)event.getAttach()).setInAPeriod(false);
        ((EventAttach)event.getAttach()).setAssignState(false);
        ((EventAttach)event.getAttach()).setPermanentState(false);
      }// end else if (tts.getCurrentCycle().isPeriodContiguous(
    }// end if (_dm.getSetOfActivities().getUnity(
    return numberOfConflicts;
  }

  /**
   *
   * @param oper
   * @return
   */
  private boolean getBooleanValue(int oper){
   switch(oper){
     case 1: return true;
     case -1: return false;
   }
    return false;
  }

  /**
   * extract preference tables
   */
  public void extractPreference(){
    if(_dm.getDDocument().getDMediator()!=null){
      int [] conflictsPreference= _dm.getDDocument().getDMediator().getDApplication().getPreferences().getConflictLimits();
      for (int i=0; i< _acceptableConflictsTable.length; i++)
        _acceptableConflictsTable[i]= conflictsPreference[i];
      _avoidPriority= new int [2-conflictsPreference[3]];
      int inc=0;
      for (int i=conflictsPreference[3]+1; i< 3; i++)
        _avoidPriority[inc++]=i;
      _periodAcceptableSize= conflictsPreference[4];
      _periodVariationEvents = conflictsPreference[5];
      ((TestStudentsConditions)_testToRun.get(0)).setPeriodVariationEvents(_periodVariationEvents);
    }
  }

  /**
   * get all test to run
   * @return
   */
  public Vector getTestToRun(){
    return _testToRun;
  }

  /**
   *
   * @param perKey
   * @return
   */
  public Vector periodVariationEvents(int[] perKey){
    extractPreference();
   TestStudentsConditions studTest= new TestStudentsConditions(_matrix, _dm.getSetOfActivities(), _dm.getTTStructure().getCurrentCycle());
   studTest.setPeriodVariationEvents(_periodVariationEvents);
   return studTest.periodVariationEventsPeriods(perKey);
  }

  /**
   * @param improveTTStruct
   * @param event
   */
  public void addEventInAllPeriods(TTStructure improveTTStruct, DResource event) {
  	EventAttach eventAttach = ((EventAttach) event.getAttach()).cloneEvent();
  	eventAttach.setAssignState(true);
  	DResource res = new DResource(event.getID(),eventAttach);
  	eventAttach.setDuration(improveTTStruct.getPeriodLenght());
  	for(int i=0; i< improveTTStruct.getCurrentCycle().getSetOfDays().size(); i++){
  		Resource day= improveTTStruct.getCurrentCycle().getSetOfDays().getResourceAt(i);
  		for(int j=0; j< ((Day)day.getAttach()).getSetOfSequences().size(); j++){
  			Resource seq= ((Day)day.getAttach()).getSetOfSequences().getResourceAt(j);
  			for(int k=0; k< ((Sequence)seq.getAttach()).getSetOfPeriods().size();k++){
  				Resource per= ((Sequence)seq.getAttach()).getSetOfPeriods().getResourceAt(k);
  				int[] daytime={(int)day.getKey(), (int)seq.getKey(), (int)per.getKey()};
  				
  				String periodKey=daytime[0]+"."+daytime[1]+"."+daytime[2];
  				eventAttach.setKey(4,periodKey);
  				//((EventAttach)event.getAttach()).setAssignState(true);
  				System.out.println(i+ " "+ j + " " + k);
  				addEventInTTs(improveTTStruct,res,false);
  				
  			}// end for(int k=0; k< ((Sequence)seq.getAttach())
  		}// end for(int j=0; j< ((Day)day.getAttach()).getSetOfSequences().size(); j++)
  	}// end for(int i=0; i< _newTTS.getCurrentCycle()
  }
  
}// end class