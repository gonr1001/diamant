package dInterface.dAlgorithms;

import dConstants.DConst;
import dInterface.Command;
import dInterface.DApplication;
import dInternal.dOptimization.SelectAlgorithm;
import eLib.exit.dialog.InformationDlg;

public class AlgorithmsCmd implements Command{

  int _selectedContext=0;// context for first affect algorithm

  public AlgorithmsCmd() {
  }

  public void execute(DApplication dApplic) {
    (new SelectAlgorithm(dApplic.getDMediator().getCurrentDoc().getDM(),_selectedContext)).execute();
    new InformationDlg(dApplic.getJFrame(), DConst.TT_BUILD_MESSAGE);
  }
}