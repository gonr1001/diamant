package dInternal.dConditionsTest;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */

import dInternal.dData.*;
import dInternal.dTimeTable.*;
import dInternal.DModel;
import dConstants.DConst;
import java.util.Vector;
import java.util.StringTokenizer;

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
  * @param soa
  * @param sos
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
   * @param avoidPriority
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
   *
   */
  public void buildAllConditions(TTStructure tts){
    if(_matrixIsBuilded){
      tts.getCurrentCycle().emptyAllEventsInPeriod();
      //_dm.getSetOfEvents()._isEventPlaced=true;
      for (int i=0; i< _dm.getSetOfEvents().size(); i++){
        Resource event = _dm.getSetOfEvents().getResourceAt(i);
        addOrRemEventInTTs(tts, event, 1, false);
      }// end for (int i=0; i< _dm.getSetOfEvents().size(); i++)
      _dm.getSetOfEvents().updateActivities(_dm.getSetOfEvents().getSetOfResources());
    }
  }

  /**
    * add or remove an event in a given tts
    * @param event
    * @param int operation -1= remove event, 0= do nothing, 1= add event
    * @return
    */
  public int[] addOrRemEventInTTs(TTStructure tts, Resource event, int operation, boolean usePriority){
     return standardAddOrRemEventInTTs(tts,event, operation, usePriority);
   }

   /**
    * add or remove an event in DModel tts
    * @param event
    * @param int operation -1= remove event, 0= do nothing, 1= add event
    * @return
    */
   public int[] addOrRemEventInTTs( Resource event, int operation, boolean usePriority){
     return standardAddOrRemEventInTTs(_dm.getTTStructure(),event, operation,usePriority);
   }


  /**
   * standard add or remove an event in tts
   * @param event
   * @param int operation -1= remove event, 0= do nothing, 1= add event
   * @return int [] conflicts range 0= nb of students conflicts,
   * range 1= nb of instructors conflicts
   * range 2= nb of rooms conflicts
   */
  private int[] standardAddOrRemEventInTTs(TTStructure tts, Resource event, int operation, boolean usePriority){
    int[] numberOfConflicts={0,0,0};
    int totalNumberOfConflicts=0;
    //extractPreference();

    if(((EventAttach)event.getAttach()).getAssignState()){//if (_dm.getSetOfActivities().getUnity(evKey[0],evKey[1],evKey[2],evKey[3]).isAssign()){
      StringTokenizer periodKey = new StringTokenizer(((EventAttach)event.getAttach()).getPeriodKey(),DConst.TOKENSEPARATOR);
      int[] perKey={Integer.parseInt(periodKey.nextToken()),Integer.parseInt(periodKey.nextToken()),Integer.parseInt(periodKey.nextToken())};
      int duration = ((EventAttach)event.getAttach()).getDuration()/tts.getPeriodLenght();
      //int[] avoidPriority={};
      if ((tts.getCurrentCycle().isPeriodContiguous(perKey[0],perKey[1],perKey[2],
          duration, _avoidPriority,usePriority)) && (duration>0) ){
        for (int j=0; j< duration; j++){
        	//System.out.println("**Event :"+ event.getID()+"  first: "+perKey[0]+ " " + perKey[1]+  " " +perKey[2]+j+" Event Per Key: "+((EventAttach)event.getAttach()).getPeriodKey());
          Period per = tts.getCurrentCycle().getPeriodByKey(perKey[0],perKey[1],perKey[2]+j);
          int [] newPerKey={perKey[0],perKey[1],perKey[2]+j};
          //periodVariationEvents(newPerKey);//debug
          for (int k=0; k< _testToRun.size(); k++){
            Condition cond = (Condition)_testToRun.get(k);
            numberOfConflicts[k]+=cond.executeTest(newPerKey,per,event.getID(),operation);
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
        //System.out.println("not assign "+event.getID());//debug
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

}// end class