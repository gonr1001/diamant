package dInterface.dData;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */

//public class ExportCmd {
import dInterface.Command;

import javax.swing.JFrame;
import com.iLib.gDialog.InformationDlg;

import dResources.DConst;
import dInterface.DApplication;
/**
   *
   * ImportCmd is a class used to call a command
   *              import.
   *
   */

  public class ExportCmd implements Command {

    public ExportCmd (JFrame jFrame) {
    } // end constructor
//------------------------------
    public void execute(DApplication dApplic) {
      dApplic.getDMediator().getCurrentDoc().getDM().exportData();
      dApplic.getDMediator().getCurrentDoc().getDM().exportData();
      new InformationDlg(dApplic.getJFrame(), DConst.EXPORT_MESSAGE);
    }
} /* end class ImportCmd */