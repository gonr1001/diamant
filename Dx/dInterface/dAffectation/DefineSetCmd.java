package dInterface.dAffectation;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import dAux.DoNothingDlg;
import dInterface.Command;
import dInterface.DApplication;


public class DefineSetCmd implements Command{

  public DefineSetCmd() {
  }

  public void execute(DApplication dApplic) {
    new DoNothingDlg(dApplic, "DefineSetDlg");
  }
}