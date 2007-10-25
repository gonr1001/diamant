/**
 *
 * Title: SectionModifDlg $Revision: 1.22 $  $Date: 2007-10-25 17:55:48 $
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
 * @version $Revision: 1.22 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInterface.dAffectation;



import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.util.Vector;

import dConstants.DConst;
import dInterface.dUtil.DxTools;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.dData.dActivities.Type;



public class SectionModifDlg extends SetOfElementsInterface{

private String[] _buttonsNames = {DConst.BUT_ADD, DConst.BUT_REMOVE, DConst.BUT_CLOSE};
private DResource _type;
private String _title;
//private Dialog _parent;
//private DApplication _dApplic;
//  /**
//   * Constructor
//   * @param dApplic
//   */
//  public SectionModifDlg(Dialog parent, DApplication dApplic,String title,DResource type) {
//    super(parent,dApplic,title+type.getID()+".",DConst.NUMBER_OF_SECTIONS,1);//  "Nombre de sections",1);
//    //Vector [] vect= new Vector[1];
//    //_parent = parent;
//    _type= type;
//    _title= title;
//    _dApplic= dApplic;
//     _buttonsPanel = DxTools.buttonsPanel(this, _buttonsNames);
//     init();
//     initDialog();
//  }
  
  public SectionModifDlg(Dialog parent, DModel dModel,String title,DResource type) {
	    super(parent,dModel,title+type.getID()+".",DConst.NUMBER_OF_SECTIONS,1);//  "Nombre de sections",1);
	    //Vector [] vect= new Vector[1];
	    //_parent = parent;
	    _type= type;
	    _title= title;
	    _dModel= dModel;
	     _buttonsPanel = DxTools.buttonsPanel(this, _buttonsNames);
	     init();
	     initDialog();
	  }

  /**
   *
   */
  protected void init(){
    Vector [] vect= new Vector[1];
    vect[0]= ((Type)_type.getAttach()).getSetOfSections().getNamesVector(1);
    setVectorsOfElements(vect);
    _dModel.changeInDModelBySectionModDlg(this);
  }

  /**
   *
   */
  protected void doubleClicMouseProcess(){
    DResource section= ((Type)_type.getAttach()).getSetOfSections().getResource(_listOfElements[_selectedPanel].
       getSelectedValue().toString());
    new UnityModifDlg(this,this._dModel,_title+_type.getID()+".", section);
  }

  /**
   * action performed
   * @param e
   */
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    //System.out.println("Choix: "+command);
    Type type= (Type)_type.getAttach();
    Vector <String> vect= type.getSetOfSections().getNamesVector(1);
    //boolean _change = false, _restore = false;
    if (command.equals(DConst.BUT_CLOSE)) {  // fermer
      dispose();
    }
    if (command.equals(DConst.BUT_ADD)) {  // Ajouter
      new SelectGroupDlg(this, vect, true);
    }
    if (command.equals(DConst.BUT_REMOVE)) {  // Supprimer
      //new SelectGroupDlg(this, vect, false);
      Object str= _listOfElements[_selectedPanel].getSelectedValue();
      if (str!=null && (type.getSetOfSections().size()>1)){
        vect= new Vector<String>();
        vect.add(str.toString());
        new SelectGroupDlg(this, vect, false);
        //init();
      //_dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities().sendEvent(this);
      }

    }

  }

  /**
   *
   * @return
   */
  public Type getType(){
    return (Type)_type.getAttach();
  }

//  /**
//   *
//   * @return
//   */
  public DModel getDModel(){
    return _dModel;
  }

}