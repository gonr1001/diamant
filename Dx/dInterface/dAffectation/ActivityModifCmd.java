package dInterface.dAffectation;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

//public class ActivityModifCmd {
import dInterface.Command;
import dInterface.DApplication;




public class ActivityModifCmd implements Command{

  public ActivityModifCmd() {

  }

  public void execute(DApplication dApplic) {
    new ActivityModifDlg(dApplic);
  }
}