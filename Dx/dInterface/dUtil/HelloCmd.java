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

import dInternal.dTimeTable.TTStructure;

public class HelloCmd implements Command {

  public HelloCmd() {
  } //end NewCmd

  public void execute(DApplication dApplic) {
    TTStructure tts= new TTStructure();
    //dApplic.getDMediator().addDoc("Sans titre", 1, tts);
    //dApplic.getToolBar().setToolBars(tts);
  } // end execute
} /* end HelloCmd class */
