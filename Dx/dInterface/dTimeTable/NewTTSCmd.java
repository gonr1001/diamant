package dInterface.dTimeTable;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */

import javax.swing.JFrame;

import dResources.DConst;
import dInterface.Command;
import dInterface.DApplication;

/**
 *
 * NewTTSCmd is class used to call the command
 * which displays the New Time Table Structure dialog
 *
 */

public class NewTTSCmd implements Command{
  private final boolean PARTIAL = true;
  public NewTTSCmd() {
  }

  public void execute(DApplication dApplic) {
    dApplic.showToolBar();
    dApplic.getDMediator().addDoc(dApplic.getCurrentDir() + DConst.NO_NAME, PARTIAL);
    //loadTTData(dApplic);
  } // end execute

}/* end class NewTTSCmd */






