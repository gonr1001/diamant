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




public class RoomsAvailabilityCmd implements Command{

  public void execute(DApplication dApplic) {
  	 new AvailabiltyDialog(dApplic,dApplic.getDModel().getSetOfRooms());
  	// new RoomsAvailabilityDlg(dApplic);
  }
}