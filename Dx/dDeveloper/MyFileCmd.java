package dAux;

import dInterface.Command;
import dInterface.DApplication;
/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */


public class MyFileCmd implements Command{

  public MyFileCmd() {
  }

  public void execute(DApplication dApplic) {
    dApplic.getDMediator().addDoc("D:\\Developpements\\DiamantExtreme\\Dx\\devData\\fichier1.dia",0);
    dApplic.getDMediator().getCurrentDoc().getDM().sendEvent(dApplic.getJFrame());
    dApplic.getMenuBar().postInitialAssign();
    //dApplic.getMenuBar().postMyFile();
  }
} // end MyFileCmd
