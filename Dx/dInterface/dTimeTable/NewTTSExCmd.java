package dInterface.dTimeTable;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */



import dConstants.DConst;
import dInterface.Command;
import dInterface.DApplication;

/**
 *
 * NewTTSExCmd is class used to call the command
 * which displays the New Time Table Structure dialog
 *
 */

public class NewTTSExCmd implements Command{

  public NewTTSExCmd() {
  }

  public void execute(DApplication dApplic) {
    dApplic.showToolBar();
    dApplic.getDMediator().addDoc(dApplic.getPreferences()._standardTTE, DConst.NO_TYPE);
    dApplic.getMenuBar().postNewTTSExCmd();
  } // end execute

}/* end class NewTTSExCmd */

