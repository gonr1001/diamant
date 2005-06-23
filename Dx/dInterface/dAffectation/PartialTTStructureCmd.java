package dInterface.dAffectation;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import dInterface.Command;
import dInterface.DApplication;
import dInterface.selectiveSchedule.dialog.SelectiveScheduleDlg;

public class PartialTTStructureCmd implements Command{

  public void execute(DApplication dApplic) {
    SelectiveScheduleDlg.getInstance().displayDlg();
  }
}
