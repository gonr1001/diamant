package dInterface.dAffectation;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import dInterface.Command;
import dInterface.DApplication;


public class SectionCmd implements Command{

  public void execute(DApplication dApplic) {
      new SectionStudentsDlg(dApplic);
      //new SectionDlg(dApplic);
  }
}