package dInterface.dAffectation;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */

import dInterface.DApplication;
import dInterface.dUtil.DXTools;
import dResources.DConst;
import dInternal.dData.SetOfActivities;

import dInternal.dData.Resource;
import java.util.Vector;
import java.awt.event.*;
import java.awt.Dialog;

public class ActivityModifDlg extends SetOfElementsInterface{

  private String[] _buttonsNames = {DConst.BUT_ADD, DConst.BUT_REMOVE, DConst.BUT_CLOSE};
  private SetOfActivities _soa;

  /**
   * Constructor
   * @param dApplic
   */
  public ActivityModifDlg(DApplication dApplic) {
    super(new Dialog(dApplic.getJFrame()),dApplic,"Activités","Nombre d'activités",1);
    _soa= dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities();
    Vector [] vect= new Vector[1];
    vect[0]= dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities().getNamesVector(1);
    _buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);
    _buttonsPanel.getComponent(0).setEnabled(false);
    _buttonsPanel.getComponent(1).setEnabled(false);
    setVectorsOfElements(vect);
    initDialog();
  }

  /**
   *
   */
  protected void doubleClicMouseProcess(){
    //System.out.println("Activity modif:"+ _listOfElements[_selectedPanel].getSelectedValue().toString());
    Resource activity= _soa.getResource(_listOfElements[_selectedPanel].
        getSelectedValue().toString());
    new TypeModifDlg(this,this._dApplic, activity);
  }

  /**
   * action performed
   * @param e
   */
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    //boolean _change = false, _restore = false;
    if (command.equals(DConst.BUT_CLOSE)) {  // fermer
      dispose();
    }

  }

}