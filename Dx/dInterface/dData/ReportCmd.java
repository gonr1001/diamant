package dInterface.dData;

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

public class ReportCmd implements Command{

  public ReportCmd() {
  }

  public void execute(DApplication dApplic) {
    new ReportDlg(dApplic);
  }

}

