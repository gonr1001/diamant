package dAux;

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

public class StateZeroCmd implements Command {

  public StateZeroCmd() {
  }

  public void execute(DApplication dApplic) {
    dApplic.getMenuBar().postStateZero();
  } // end execute
}
