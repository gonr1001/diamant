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


public class ManualImprovementCmd implements Command {

  public ManualImprovementCmd() {
  } //end OpenTTCmd

  public void execute(DApplication dApplic) {
    new ManualImprovementDlg(true);
  } // end execute
} /* end ManualImprovementCmd class */
