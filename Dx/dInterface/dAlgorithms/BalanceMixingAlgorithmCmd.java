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
import dInternal.dAlgorithms.SelectAlgorithm;
import dConstants.DConst;

import com.iLib.gDialog.InformationDlg;

public class BalanceMixingAlgorithmCmd implements Command{

  int _selectedContext=1;// context for balance mixing algorithm

  /**
   *
   */
  public BalanceMixingAlgorithmCmd() {

  }

  public void execute(DApplication dApplic) {
    (new SelectAlgorithm(dApplic.getDMediator().getCurrentDoc().getDM(),_selectedContext)).execute();
    new InformationDlg(dApplic.getJFrame(), DConst.STUDENTS_MIXING_MESSAGE);
  }
}