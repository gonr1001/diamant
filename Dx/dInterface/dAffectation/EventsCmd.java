package dInterface.dAffectation;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import dConstants.DConst;
import dInterface.Command;
import dInterface.DApplication;




public class EventsCmd implements Command{

  public void execute(DApplication dApplic) {
    new EventsDlg(dApplic,DConst.EVENTS_DLG_TITLE);
  }
}