package dInterface.dAlgorithms;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import dInterface.Command;
import dInterface.DApplication;
import dInternal.dOptimization.SelectAlgorithm;
import dConstants.DConst;

import com.iLib.gDialog.InformationDlg;

public class OptimizeMixingAlgorithmCmd implements Command{

  int _selectedContext=3;// context for optimize mixing algorithm
  boolean _USER_TEST_ACTIV = false;

  /**
   *
   */
  public OptimizeMixingAlgorithmCmd(boolean USER_TEST_ACTIV) {
    _USER_TEST_ACTIV = USER_TEST_ACTIV;
  }

  public void execute(DApplication dApplic) {
    DConst.USER_TEST_ACTIV= _USER_TEST_ACTIV;
    (new SelectAlgorithm(dApplic.getDMediator().getCurrentDoc().getDM(),_selectedContext)).execute();
    new InformationDlg(dApplic.getJFrame(), DConst.STUDENTS_MIXING_MESSAGE);
  }
}