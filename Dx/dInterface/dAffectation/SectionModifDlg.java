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
import dInternal.dData.Type;
import dInternal.dData.Resource;
import java.util.Vector;
import java.awt.event.*;
import java.awt.Dialog;
import javax.swing.JFrame;

public class SectionModifDlg extends SetOfElementsInterface{

private String[] _buttonsNames = {DConst.BUT_ADD, DConst.BUT_REMOVE, DConst.BUT_CLOSE};
private Resource _type;
private String _title;
  /**
   * Constructor
   * @param dApplic
   */
  public SectionModifDlg(Dialog parent, DApplication dApplic,String title,Resource type) {
    super(parent,dApplic,title+type.getID()+".","Nombre de sections",1);
    Vector [] vect= new Vector[1];
    _type= type;
    _title= title;
    vect[0]= ((Type)_type.getAttach()).getSetOfSections().getNamesVector(1);
     _buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);
     //_buttonsPanel.getComponent(0).setEnabled(false);
     //_buttonsPanel.getComponent(1).setEnabled(false);
    setVectorsOfElements(vect);
    initDialog();
  }

  /**
   *
   */
  protected void doubleClicMouseProcess(){
    Resource section= ((Type)_type.getAttach()).getSetOfSections().getResource(_listOfElements[_selectedPanel].
       getSelectedValue().toString());
    new UnityModifDlg(this,this._dApplic,_title+_type.getID()+".", section);
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