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
import dConstants.DConst;




public class EventsCmd implements Command{

  public EventsCmd() {

  }

  public void execute(DApplication dApplic) {
    new EventsDlg(dApplic,DConst.EVENTS_DLG_TITLE);
  }
}