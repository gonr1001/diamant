package dInternal.dOptimization;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import java.awt.Cursor;
import java.util.Vector;

import dInternal.DModel;

public class SelectAlgorithm {

 private DModel _dm;
 private Vector _algorithmToRun;
 private int _currentAlgoToExecute=0;
 //private int [] _avoidPriority={1,2};
 //private int [] _acceptableConflictsTable={0,0,0};
  /**
   * constructor
   */
  public SelectAlgorithm(DModel dm, int contexte) {
    _dm= dm;
    _algorithmToRun= new Vector(1);
    _currentAlgoToExecute= contexte;
    System.out.println("Selected Context:"+contexte);
    buildAlgorithmToRun();
  }


  /**
   * build Algorithm To Run
   */
  private void buildAlgorithmToRun(){
    _algorithmToRun.removeAllElements();
    switch(_currentAlgoToExecute){
      case 0: _algorithmToRun.add(new FirstAffectAlgorithm(_dm));
        break;
      case 1: _algorithmToRun.add(new StudentMixingAlgorithm(_dm,0));
        break;
      case 2: _algorithmToRun.add(new StudentMixingAlgorithm(_dm,1));//intermediaire
        break;
      case 3: _algorithmToRun.add(new StudentMixingAlgorithm(_dm,2));
        break;
    }// end  switch(choice)
  }

  /**
   * execute algorithm
   */
  public void execute(){
    if(_algorithmToRun.size()!=0){
      _dm.getDDocument().setCursor(Cursor.WAIT_CURSOR);
      ((Algorithm)_algorithmToRun.firstElement()).build( );
      _dm.getDDocument().setCursor(Cursor.DEFAULT_CURSOR);
    }
  }



  /**
   *
   * @param avoidPriority
   */
  /*public void setAvoidPriorityTable(int[] avoidPriority){
    _avoidPriority= avoidPriority;
  }*/

  /**
   *
   * @param int[] acceptableConflictsTable range 0= student, range 1= instructor
   * range 2= room
   */
 /* public void setacceptableConflictsTable(int[] acceptableConflictsTable){
    _acceptableConflictsTable= acceptableConflictsTable;
  }*/

}// end class