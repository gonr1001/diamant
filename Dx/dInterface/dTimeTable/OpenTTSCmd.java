package dInterface.dTimeTable;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, ajz
 * @version 1.0
 */



import dInterface.Command;
import dInterface.DApplication;

/**
 *
 * OpenTTSCmd is class used to call the command
 * which displays the Open Time Table Structure dialog
 *
 */

public class OpenTTSCmd implements Command{

  public OpenTTSCmd() {
  }


  public void execute(DApplication dApplic) {
    dApplic.showToolBar();
    new OpenTTSDlg(dApplic);
  } // end execute

}/* end class OpenTTSCmd */






