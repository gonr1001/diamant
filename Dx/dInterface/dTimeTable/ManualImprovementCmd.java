package dInterface.dTimeTable;

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
import dResources.DConst;



public class ManualImprovementCmd implements Command {


  public ManualImprovementCmd() {
  } //end OpenTTCmd

  public void execute(DApplication dApplic) {
    new ManualImprovementDlg(dApplic,DConst.MANUALIMPROVEMENT_DLG_TITLE);
  } // end execute
} /* end ManualImprovementCmd class */
