package dInterface.dAlgorithms;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */



import dInterface.Command;
import dInterface.DApplication;
import dInternal.dAlgorithms.SelectAlgorithm;
import dResources.DConst;

import com.iLib.gDialog.InformationDlg;

public class MiddleBalMixingAlgoritmCmd implements Command{

  int _selectedContext=2;// context for middle balance mixing algorithm

  /**
   *
   */
  public MiddleBalMixingAlgoritmCmd() {

  }

  public void execute(DApplication dApplic) {
    System.out.println("MiddleBalMixingAlgoritmCmd");//debug
    (new SelectAlgorithm(dApplic.getDMediator().getCurrentDoc().getDM(),_selectedContext)).execute();
    new InformationDlg(dApplic.getJFrame(), DConst.STUDENTS_MIXING_MESSAGE);
  }
}