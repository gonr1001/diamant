package dInternal.dAlgorithms;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import dInternal.DModel;
import java.util.Vector;

public class SelectAlgorithm {

 private DModel _dm;
 private Vector _algorithmToRun;
 private int _currentAlgoToExecute=0;
  int [] _avoidPriority={1,2};
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
      case 0: _algorithmToRun.add(new FirstAffectAlgorithm(_dm,_avoidPriority));
        break;
      case 1: _algorithmToRun.add(new StudentMixingAlgorithm(_dm,0));
        break;
      case 2: _algorithmToRun.add(new StudentMixingAlgorithm(_dm,1));
        break;
    }// end  switch(choice)
  }

  /**
   * execute algorithm
   */
  public void execute(){
    if(_algorithmToRun.size()!=0){
      ((Algorithm)_algorithmToRun.firstElement()).build( );
    }
  }

  /**
   *
   * @param avoidPriority
   */
  public void setAvoidPriorityTable(int[] avoidPriority){
    _avoidPriority= avoidPriority;
  }

}// end class