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
 private Vector _testToRun = new Vector();
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
   * build student conflict matrix
   * @param soa
   * @param sos
   */
  private void buildStudentsMatrix(SetOfActivities soa, SetOfStudents sos) {
    _matrix.buildMatrix(soa, sos);
  }

  /**
   *
   */
  public void buildAllConditions(){
    _matrix.buildMatrix(_dm.getSetOfActivities(), _dm.getSetOfStudents());
    _dm.getTTStructure().getCurrentCycle().emptyAllEventsInPeriod();
    _dm.getSetOfEvents()._isEventPlaced=true;
    for (int i=0; i< _dm.getSetOfEvents().size(); i++){
      Resource event = _dm.getSetOfEvents().getResourceAt(i);
      StringTokenizer eventKey = new StringTokenizer(event.getID(),DConst.TOKENSEPARATOR);
      long[] evKey = {Long.parseLong(eventKey.nextToken()),Long.parseLong(eventKey.nextToken()),
      Long.parseLong(eventKey.nextToken()),Long.parseLong(eventKey.nextToken())};
      if (_dm.getSetOfActivities().getUnity(evKey[0],evKey[1],evKey[2],evKey[3]).isAssign()){
        StringTokenizer periodKey = new StringTokenizer(((EventAttach)event.getAttach()).getPeriodKey(),DConst.TOKENSEPARATOR);
        long[] perKey={Long.parseLong(periodKey.nextToken()),Long.parseLong(periodKey.nextToken()),Long.parseLong(periodKey.nextToken())};
        int duration = ((EventAttach)event.getAttach()).getDuration()/_dm.getTTStructure().getPeriodLenght();
        int[] avoidPriority={};
        if (_dm.getTTStructure().getCurrentCycle().isPeriodContiguous(perKey[0],perKey[1],perKey[2],duration, avoidPriority))
        for (int j=0; j< duration; j++){
          Period per = _dm.getTTStructure().getCurrentCycle().getPeriodByKey(perKey[0],perKey[1],perKey[2]+j);
          for (int k=0; k< _testToRun.size(); k++){
            Condition cond = (Condition)_testToRun.get(k);
            cond.executeTest(per,event.getID(),1);
          }// end  for (int j=0; j< _testToRun.size(); j++)
        }// end for (int j=0; j< ((EventAttach)event.getAttach())
      }// end if (_dm.getSetOfActivities().getUnity(


    }// end for (int i=0; i< _dm.getSetOfEvents().size(); i++)

  }
}