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
  /**
   * constructor
   */
  public SelectAlgorithm(DModel dm) {
    _dm= dm;
  }

  /**
   * execute a selected algorithm
   */
  public void execute(int choice){
    switch(choice){
      case 1: FirstAffectAlgorithm firstAffect= new FirstAffectAlgorithm();
        firstAffect.build(_dm, buildEventsVector());
        break;
    }// end  switch(choice)
  }

  /**
   *
   * @return
   */
  public Vector buildEventsVector(){
    return _dm.getSetOfEvents().getSetOfResources();
  }

}// end class