package dDeveloper;

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

// XXXX Pascal: A quoi sert cette classe?
public class StateZeroCmd implements Command {

  public void execute(DApplication dApplic) {
    dApplic.getMenuBar().postStateZero();
  } // end execute
}
