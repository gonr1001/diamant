/**
 *
 * Title: SectionModifDlg $Revision: 1.6 $  $Date: 2004-02-13 20:15:00 $
 * Description: SectionModifDlg is class used
 *           to display a dialog to modifiy the number of sections
 *
 * Copyright (c) 2001 by rgr.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information
 * of rgr. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with rgr.
 *
 * @version $Revision: 1.6 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInterface.dAffectation;



import dInterface.DApplication;
import dInterface.dUtil.DXTools;
import dResources.DConst;
import dInternal.dData.Type;
import dInternal.dData.Resource;
import java.util.Vector;
import java.awt.event.*;
import java.awt.Dialog;


public class SectionModifDlg extends SetOfElementsInterface{

private String[] _buttonsNames = {DConst.BUT_ADD, DConst.BUT_REMOVE, DConst.BUT_CLOSE};
private Resource _type;
private String _title;
  /**
   * Constructor
   * @param dApplic
   */
  public SectionModifDlg(Dialog parent, DApplication dApplic,String title,Resource type) {
    super(parent,dApplic,title+type.getID()+".",DConst.NUMBER_OF_SECTIONS,1);//  "Nombre de sections",1);
    //Vector [] vect= new Vector[1];
    _type= type;
    _title= title;
     _buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);
     init();
     initDialog();
  }

  /**
   *
   */
  private void init(){
    Vector [] vect= new Vector[1];
    vect[0]= ((Type)_type.getAttach()).getSetOfSections().getNamesVector(1);
    setVectorsOfElements(vect);
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
    Type type= (Type)_type.getAttach();
    //boolean _change = false, _restore = false;
    if (command.equals(DConst.BUT_CLOSE)) {  // fermer
      dispose();
    }
    if (command.equals(DConst.BUT_ADD)) {  // Ajouter
      Resource section= type.getSetOfSections().getResourceAt(
          ((Type)_type.getAttach()).getSetOfSections().size()-1);
      String ID= DXTools.STIConvertGroup(section.getID())+1; // boite de dialog selec groupe
      int nbCycle= _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().getSetOfCycles().size();
      type.addSection(ID,nbCycle,true);
      init();
      _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities().sendEvent(this);
    }
    if (command.equals(DConst.BUT_REMOVE)) {  // Supprimer
      if(((Type)_type.getAttach()).getSetOfSections().size()>1){
      ((Type)_type.getAttach()).getSetOfSections().removeResourceAt(
          ((Type)_type.getAttach()).getSetOfSections().size()-1);
      init();
      _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities().sendEvent(this);
      }
    }

  }

}