package dInterface.dAffectation;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import dInterface.DApplication;
import dInterface.dUtil.DXTools;
import dResources.DConst;
import java.util.Vector;

public class ActivityModifDlg extends SetOfElementsInterface{

private String[] _buttonsNames = {DConst.BUT_OK, DConst.BUT_APPLY, DConst.BUT_CANCEL};

  /**
   * Constructor
   * @param dApplic
   */
  public ActivityModifDlg(DApplication dApplic) {
    super(dApplic,"Activités",1);
    Vector [] vect= new Vector[1];
    vect[0]= new Vector();
    vect[0].add("Test");
     _buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);
    setVectorsOfElements(vect);
    initDialog();
  }

}