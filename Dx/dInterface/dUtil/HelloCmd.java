package dInterface.dUtil;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */

import javax.swing.JFrame;

import dInterface.Command;
import dInterface.DApplication;

import dInternal.dTimetable.TTStructure;

public class HelloCmd implements Command {

  public HelloCmd() {
  } //end NewCmd

  public void execute(DApplication dApplic) {
    dApplic.getDMediator().addDoc("Sans titre", new TTStructure());
  } // end execute
} /* end HelloCmd class */
