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
import dInternal.dData.Activity;
import dInternal.dData.Type;
import dInternal.dData.Resource;

import dInternal.dData.Resource;
import java.util.Vector;
import java.awt.event.*;
import java.awt.Dialog;


public class TypeModifDlg extends SetOfElementsInterface{

private String[] _buttonsNames = {DConst.BUT_ADD, DConst.BUT_REMOVE, DConst.BUT_CLOSE};
private Resource _activity;
private DApplication _dApplic;
  /**
   * Constructor
   * @param dApplic
   */
  public TypeModifDlg(Dialog parent, DApplication dApplic,Resource activity) {
    super(parent,dApplic,activity.getID(),"Nombre de types",1);
    _dApplic= dApplic;
    Vector [] vect= new Vector[1];
    _activity= activity;
    vect[0]= ((Activity)_activity.getAttach()).getSetOfTypes().getNamesVector(1);
     _buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);
     _buttonsPanel.getComponent(0).setEnabled(true);
     _buttonsPanel.getComponent(1).setEnabled(true);
    setVectorsOfElements(vect);
    initDialog();
  }

  /**
   *
   */
  protected void doubleClicMouseProcess(){
    //System.out.println("Activity modif:"+ _listOfElements[_selectedPanel].getSelectedValue().toString());
    //SectionModifDlg
    Resource type= ((Activity)_activity.getAttach()).getSetOfTypes().getResource(_listOfElements[_selectedPanel].
       getSelectedValue().toString());
    new SectionModifDlg(this,this._dApplic,_activity.getID()+".", type);
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
    if (command.equals(DConst.BUT_ADD)) { // Ajouter
      Activity activity= ((Activity)_activity.getAttach());
      if(activity.getSetOfTypes().size()<2){
        activity.addType("2");
        Type type = (Type) activity.getSetOfTypes().getResource("2").getAttach();
        int nbCycle= _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().getSetOfCycles().size();
        type.addSection("01",nbCycle,true);
        init();
         Vector students= activity.getStudentRegistered();
         _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfStudents().addActivityToStudents(students,_activity.getID()+"201;0");
        _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities().sendEvent(this);
      }
    }
    if (command.equals(DConst.BUT_REMOVE)) {  // Supprimer
      Activity activity= ((Activity)_activity.getAttach());
      if(activity.getSetOfTypes().size()>1){
        activity.getSetOfTypes().removeResource("2");
        init();
        _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities().sendEvent(this);
      }
    }

  }

  /**
   *
   */
  private void init(){
    Vector [] vect= new Vector[1];
    vect[0]= ((Activity) _activity.getAttach()).getSetOfTypes().getNamesVector(1);
    setVectorsOfElements(vect);
  }


}