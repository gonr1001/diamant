package dInterface.dAffectation;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JLabel;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.dUtil.DXTools;
//import dInternal.dDataTxt.Resource;
import dInternal.DResource;
import dInternal.dData.dActivities.SetOfActivities;

public class ActivityModifDlg extends SetOfElementsInterface{

  private String[] _buttonsNames = {DConst.BUT_CLOSE};
  private SetOfActivities _soa;

  /**
   * Constructor
   * @param dApplic
   */
  
  // XXXX Pascal: On internationalise ou pas ?!
  public ActivityModifDlg(DApplication dApplic) {
    super(new Dialog(dApplic.getJFrame()),dApplic,"Activités","Nombre d'activités",1);
    getContentPane().add(new JLabel("hello"), BorderLayout.NORTH);
    _soa= dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities();
    Vector [] vect= new Vector[1];
    vect[0]= dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities().getIDsByField(3, "true");
  /*  JButton button = new JButton( DConst.BUT_CLOSE);
       button.setActionCommand( DConst.BUT_CLOSE);
      button.addActionListener(this);
      vect[0]= dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities().getIDsByField(3, "true");;*/
     _buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);
    // _buttonsPanel.getComponent(0).setEnabled(false);
   // _buttonsPanel.getComponent(1).setEnabled(false);
    setVectorsOfElements(vect);
    initDialog();
  }

  /**
   *
   */
  protected void doubleClicMouseProcess(){
    //System.out.println("Activity modif:"+ _listOfElements[_selectedPanel].getSelectedValue().toString());
    DResource activity= _soa.getResource(_listOfElements[_selectedPanel].
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