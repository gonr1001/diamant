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
import dInternal.dData.Section;
import dInternal.dData.Resource;
import dInternal.dData.Unity;
import dInternal.dData.Assignment;
import java.util.Vector;
import java.awt.event.*;
import java.awt.Dialog;
import javax.swing.JFrame;

public class UnityModifDlg extends SetOfElementsInterface{

private String[] _buttonsNames = {DConst.BUT_ADD, DConst.BUT_REMOVE, DConst.BUT_CLOSE};
private Section _section;
private String _title;

  /**
   * Constructor
   * @param dApplic
   */
  public UnityModifDlg(Dialog parent, DApplication dApplic,String title,Resource section) {
    super(parent,dApplic,title+section.getID()+".","Nombre d'unit�s",1);
    _title=title+section.getID()+".";
    _section= (Section)section.getAttach();
    init();
    _buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);
    initDialog();
  }

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
    Resource unity= _section.getSetOfUnities().getResource(_listOfElements[_selectedPanel].
       getSelectedValue().toString());
    //new UnityModifDlg(this,_title+unity.getID()+".", section);
    new EditActivityDlg(this,this._dApplic, _title+unity.getID()+".");
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
      Resource unity= _section.getSetOfUnities().getResourceAt(_section.getSetOfUnities().size()-1);
      String ID= Integer.toString(Integer.parseInt(unity.getID())+1);
      int nbCycle= _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().getSetOfCycles().size();
      _section.addUnity(ID,nbCycle, true);
      init();
    }

  }

}