package dInterface.dTimeTable;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import javax.swing.JFrame;
import dInterface.Command;
import dInterface.DApplication;
import dResources.DConst;
import dInterface.dTimeTable.ManualImprovementResultFrame;
import dInternal.dTimeTable.TTStructure;


public class ManualImprovementCmd implements Command {

  //ManualImprovementResultFrame _manImpResF;
  public ManualImprovementCmd() {
  } //end OpenTTCmd

  public void execute(DApplication dApplic) {
   //_manImpResF= new ManualImprovementResultFrame(dApplic);
    new ManualImprovementDlg(dApplic,DConst.MANUALIMPROVEMENT_DLG_TITLE);
    //(new ManualImprovementResultFrame(dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure(),
      //                               dApplic.getToolBar())).createFrame("ADM111",true);
  } // end execute
} /* end ManualImprovementCmd class */
