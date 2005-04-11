package dInterface.dAlgorithms;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import dConstants.DConst;
import dInterface.Command;
import dInterface.DApplication;
import dInternal.dOptimization.SelectAlgorithm;
import eLib.exit.dialog.InformationDlg;

// XXXX Pascal: Balance, Middle, Optimize sont tellement sembables...
//              Pkoi ne pas avoir utilise le Template pattern?!

public class BalanceMixingAlgorithmCmd implements Command{

  int _selectedContext=1;// context for balance mixing algorithm
  boolean _USER_TEST_ACTIV = false;

  /**
   *
   */
  public BalanceMixingAlgorithmCmd(boolean USER_TEST_ACTIV) {
    _USER_TEST_ACTIV = USER_TEST_ACTIV;
  }

  public void execute(DApplication dApplic) {
    DConst.USER_TEST_ACTIV= _USER_TEST_ACTIV;
    (new SelectAlgorithm(dApplic.getDModel(),_selectedContext)).execute();
    new InformationDlg(dApplic.getJFrame(), DConst.STUDENTS_MIXING_MESSAGE);
  }
}