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
import dResources.DConst;
import java.util.Vector;
import java.util.StringTokenizer;

public class TestConditions {

 private StudentsConflictsMatrix _matrix;
 private DModel _dm;
 private Vector _testToRun = new Vector(1);
 private boolean _matrixIsBuilded= false;
 int[] _avoidPriority={};
 /**
  * Constructor
  * @param soa
  * @param sos
  */
 public TestConditions(DModel dm) {
   _dm= dm;
    _matrix = new StudentsConflictsMatrix();
    _testToRun.add(new TestStudentsConditions(_matrix, _dm.getSetOfActivities()));
    _testToRun.add( new TestRoomsConditions(_dm));
    _testToRun.add( new TestInstructorsConditions(_dm));
  }

  public StudentsConflictsMatrix getConflictsMatrix(){
    return _matrix;
  }

  /**
   *
   * @param avoidPriority
   */
  public void setAvoidPriorityTable(int[] avoidPriority){
    _avoidPriority= avoidPriority;
  }

  /**
   *
   * @param avoidPriority
   */
  public void emptyAvoidPriorityTable(){
    int[] avoidPriority= {};
    _avoidPriority= avoidPriority;
  }

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
  private void buildStudentConflictMatrix(){
    if (!_matrixIsBuilded){
     _matrix.buildMatrix(_dm.getSetOfActivities(), _dm.getSetOfStudents());
     _matrixIsBuilded= true;
    }
 }

 /**
  *
  */
  public void initAllConditions(){
    buildStudentConflictMatrix();
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
        addOrRemEventInTTs(tts, event,1);
      }// end for (int i=0; i< _dm.getSetOfEvents().size(); i++)
    }
  }

  /**
    * add or remove an event in a given tts
    * @param event
    * @param operation
    * @return
    */
  public int addOrRemEventInTTs(TTStructure tts, Resource event, int operation){
     return StandardAddOrRemEventInTTs(tts,event, operation);
   }

   /**
    * add or remove an event in DModel tts
    * @param event
    * @param operation
    * @return
    */
   public int addOrRemEventInTTs( Resource event, int operation){
     return StandardAddOrRemEventInTTs(_dm.getTTStructure(),event, operation);
   }


  /**
   * standard add or remove an event in tts
   * @param event
   * @param int operation -1= remove event, 0= do nothing, 1= add event
   * @return
   */
  private int StandardAddOrRemEventInTTs(TTStructure tts, Resource event, int operation){
    int numberOfConflicts=0;
    //StringTokenizer eventKey = new StringTokenizer(event.getID(),DConst.TOKENSEPARATOR);
    /*String[] evKey = {eventKey.nextToken(),eventKey.nextToken(),
      eventKey.nextToken(),eventKey.nextToken()};*/
    if(((EventAttach)event.getAttach()).getAssignState()){//if (_dm.getSetOfActivities().getUnity(evKey[0],evKey[1],evKey[2],evKey[3]).isAssign()){
      StringTokenizer periodKey = new StringTokenizer(((EventAttach)event.getAttach()).getPeriodKey(),DConst.TOKENSEPARATOR);
      long[] perKey={Long.parseLong(periodKey.nextToken()),Long.parseLong(periodKey.nextToken()),Long.parseLong(periodKey.nextToken())};
      int duration = ((EventAttach)event.getAttach()).getDuration()/tts.getPeriodLenght();
      //int[] avoidPriority={};
      if (tts.getCurrentCycle().isPeriodContiguous(perKey[0],perKey[1],perKey[2],
          duration, _avoidPriority)){
        for (int j=0; j< duration; j++){
          Period per = tts.getCurrentCycle().getPeriodByKey(perKey[0],perKey[1],perKey[2]+j);
          for (int k=0; k< _testToRun.size(); k++){
            Condition cond = (Condition)_testToRun.get(k);
            numberOfConflicts+=cond.executeTest(per,event.getID(),operation);
          }// end  for (int j=0; j< _testToRun.size(); j++)
          if (operation!=0){
            ((EventAttach)event.getAttach()).setInAPeriod(getBooleanValue(operation));
            ((EventAttach)event.getAttach()).setAssignState(getBooleanValue(operation));
          }
        }// end for (int j=0; j< ((EventAttach)event.getAttach())
      }
    }// end if (_dm.getSetOfActivities().getUnity(
    return numberOfConflicts;
  }

  /**
   *
   * @param per
   * @param event
   * @param operation
   * @return
   */
  /*public int addOrRemEventInPeriod(Period per, Resource event,int operation){
    int numberC=0;
    StringTokenizer periodKey = new StringTokenizer(((EventAttach)event.getAttach()).getPeriodKey(),".");
    long[] perKey={Long.parseLong(periodKey.nextToken()),Long.parseLong(periodKey.nextToken()),Long.parseLong(periodKey.nextToken())};
    int currentDuration = ((EventAttach)event.getAttach()).getDuration()/_dm.getTTStructure().getPeriodLenght();
    //int[] avoidPriority={};
    if (_dm.getTTStructure().getCurrentCycle().isPeriodContiguous(perKey[0],perKey[1],perKey[2],currentDuration, _avoidPriority)){
      for (int k=0; k< _dm.getConditionsTest().getTestToRun().size(); k++){
        Condition cond = (Condition)_dm.getConditionsTest().getTestToRun().get(k);
        numberC+=cond.executeTest(per,event.getID(),operation);
      }// end  for (int j=0; j< _testToRun.size(); j++)
      if (operation!=0){
        ((EventAttach)event.getAttach()).setInAPeriod(getBooleanValue(operation));
        ((EventAttach)event.getAttach()).setAssignState(getBooleanValue(operation));
      }
    }else{// end if (_dm.getTTStructure().getCurrentCycle().isP
      numberC=-1;
    }// end else if (_dm.getTTStructure().getCurrentCycle().isP
    return numberC;
  }*/


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
   * get all test to run
   * @return
   */
  public Vector getTestToRun(){
    return _testToRun;
  }

}// end class