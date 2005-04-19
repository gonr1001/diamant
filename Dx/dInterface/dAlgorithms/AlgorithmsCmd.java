package dInterface.dAlgorithms;

import dConstants.DConst;
import dInterface.Command;
import dInterface.DApplication;
import dInternal.dOptimization.SelectAlgorithm;
import eLib.exit.dialog.InformationDlg;

public class AlgorithmsCmd implements Command{

  int _selectedContext=0;// context for first affect algorithm

  public void execute(DApplication dApplic) {
    (new SelectAlgorithm(dApplic.getDModel(),_selectedContext)).execute();
    new InformationDlg(dApplic.getJFrame(), DConst.TT_BUILD_MESSAGE);
  }
}