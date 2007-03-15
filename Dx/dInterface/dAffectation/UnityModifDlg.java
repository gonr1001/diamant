package dInterface.dAffectation;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.util.Vector;

import dConstants.DConst;
import dInterface.DApplication;
//import dInterface.dAssignementDlgs.DxEditActivityDlg;
import dInterface.dAssignementDlgs.DxEditEventDlg;
import dInterface.dUtil.DxTools;
//import dInternal.dDataTxt.Resource;
import dInternal.DResource;
import dInternal.dData.dActivities.Section;


public class UnityModifDlg extends SetOfElementsInterface{

private String[] _buttonsNames = {DConst.BUT_ADD, DConst.BUT_REMOVE, DConst.BUT_CLOSE};
private Section _section;
private String _title;

  /**
   * Constructor
   * @param dApplic
   */
  public UnityModifDlg(Dialog parent, DApplication dApplic,String title,DResource section) {
    super(parent,dApplic,title+section.getID()+".", DConst.NUMBER_OF_UNITIES, 1);//    "Nombre d'blocs",1);
    _title=title+section.getID()+".";
    _section= (Section)section.getAttach();
    init();
    _buttonsPanel = DxTools.buttonsPanel(this, _buttonsNames);
    initDialog();
  }

  /**
   *
   */
  private void init(){
    Vector [] vect= new Vector[1];
    vect[0]= _section.getSetOfUnities().getNamesVector(1);
    setVectorsOfElements(vect);
  }

  /**
   *
   */
  protected void doubleClicMouseProcess(){
    //System.out.println("Activity modif:"+ _listOfElements[_selectedPanel].getSelectedValue().toString());
    DResource unity= _section.getSetOfUnities().getResource(_listOfElements[_selectedPanel].
       getSelectedValue().toString());
    new DxEditEventDlg(this,this._dApplic, _title+unity.getID()+".",true);
    //new DxEditActivityDlg(this,this._dApplic, _title+unity.getID()+".",true);
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
    if (command.equals(DConst.BUT_ADD)) {  // Ajouter
      DResource unity= _section.getSetOfUnities().getResourceAt(_section.getSetOfUnities().size()-1);
      String ID= Integer.toString(Integer.parseInt(unity.getID())+1);
      int nbCycle= _dApplic.getCurrentDModel().getTTStructure().getSetOfCycles().size();
      _section.addUnity(ID,nbCycle, true);
      init();
     
      _dApplic.getCurrentDModel().changeInModelByUnityModifDlg(this);
    }
    if (command.equals(DConst.BUT_REMOVE)) {  // Supprimer
      if(_section.getSetOfUnities().size()>1){
     _section.getSetOfUnities().removeResourceAt(_section.getSetOfUnities().size()-1);
      init();
      _dApplic.getCurrentDModel().changeInModelByUnityModifDlg(this);
      }
    }

  }


}