package dDeveloper;

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

 // XXXX Pascal: Arrrggggg!!!! Des paths absolus dans du code... !!

  public void execute(DApplication dApplic) {
    dApplic.setCurrentDir(".\\devData\\");
    dApplic.getDMediator().addDoc(".\\devData\\fichier1.dia",0);
    dApplic.getDModel().changeInDModel(dApplic.getJFrame());
    dApplic.getMenuBar().postInitialAssign();
  }
} // end MyFileCmd
