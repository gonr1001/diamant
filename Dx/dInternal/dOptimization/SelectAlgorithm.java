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
  /**
   * constructor
   */
  public SelectAlgorithm(DModel dm, int contexte) {
    _dm= dm;
    _algorithmToRun= new Vector(1);
    buildAlgorithmToRun(contexte);
  }

  /**
   * build Algorithm To Run
   */
  private void buildAlgorithmToRun(int contexte){
    switch(contexte){
      case 0: _algorithmToRun.add(new FirstAffectAlgorithm());
        break;
    }// end  switch(choice)
  }

  /**
   * execute algorithm
   */
  public void execute(){
    if(_algorithmToRun.size()!=0){
      ((Algorithm)_algorithmToRun.get(_currentAlgoToExecute)).build(_dm, buildEventsVector());
    }
  }

  /**
   *
   * @return
   */
  public Vector buildEventsVector(){
    return _dm.getSetOfEvents().getSetOfResources();
  }

}// end class