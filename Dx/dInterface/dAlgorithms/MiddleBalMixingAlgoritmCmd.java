package dInterface.dAlgorithms;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */



import com.iLib.gDialog.InformationDlg;

import dConstants.DConst;
import dInterface.Command;
import dInterface.DApplication;
import dInternal.dOptimization.SelectAlgorithm;

public class MiddleBalMixingAlgoritmCmd implements Command{

  int _selectedContext=2;// context for middle balance mixing algorithm
   boolean _USER_TEST_ACTIV = false;

  /**
   *
   */
   public MiddleBalMixingAlgoritmCmd(boolean USER_TEST_ACTIV) {
    _USER_TEST_ACTIV = USER_TEST_ACTIV;
  }

  public void execute(DApplication dApplic) {
    DConst.USER_TEST_ACTIV= _USER_TEST_ACTIV;
    System.out.println("MiddleBalMixingAlgoritmCmd");//debug
    (new SelectAlgorithm(dApplic.getDMediator().getCurrentDoc().getDM(),_selectedContext)).execute();
    new InformationDlg(dApplic.getJFrame(), DConst.STUDENTS_MIXING_MESSAGE);
  }
}
