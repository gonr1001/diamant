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
import dInterface.dUtil.DxTools;
import dInternal.dData.dActivities.Activity;
//import dInternal.dDataTxt.Resource;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.dData.dActivities.Type;


@SuppressWarnings("serial")
public class TypeModifDlg extends SetOfElementsInterface{

private String[] _buttonsNames = {DConst.BUT_ADD, DConst.BUT_REMOVE, DConst.BUT_CLOSE};
private DResource _activity;
//private DApplication _dApplic;
  /**
   * Constructor
   * @param dApplic
   */
//  public TypeModifDlg(Dialog parent, DApplication dApplic,DResource activity) {
//    super(parent,dApplic,activity.getID(), DConst.NUMBER_OF_TYPES, 1);//  )"Nombre de types",1);
//    _dApplic= dApplic;
//    Vector [] vect= new Vector[1];
//    _activity= activity;
//    vect[0]= ((Activity)_activity.getAttach()).getSetOfTypes().getNamesVector(1);
//     _buttonsPanel = DxTools.buttonsPanel(this, _buttonsNames);
//     _buttonsPanel.getComponent(0).setEnabled(true);
//     _buttonsPanel.getComponent(1).setEnabled(true);
//    setVectorsOfElements(vect);
//    initDialog();
//  }
  
  public TypeModifDlg(Dialog parent, DModel dModel,DResource activity) {
	    super(parent,dModel,activity.getID(), DConst.NUMBER_OF_TYPES, 1);//  )"Nombre de types",1);
	    _dModel= dModel;
	    Vector [] vect= new Vector[1];
	    _activity= activity;
	    vect[0]= ((Activity)_activity.getAttach()).getSetOfTypes().getNamesVector(1);
	     _buttonsPanel = DxTools.buttonsPanel(this, _buttonsNames);
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
    DResource type= ((Activity)_activity.getAttach()).getSetOfTypes().getResource(_listOfElements[_selectedPanel].
       getSelectedValue().toString());
    new SectionModifDlg(this,this._dModel,_activity.getID()+".", type);
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
        int nbCycle= _dModel.getTTStructure().getSetOfCycles().size();
        type.addSection("01",nbCycle,true);
        init();
         Vector students= activity.getStudentRegistered();
         _dModel.changeInDModelByModifyAdd(this, students, _activity.getID()+"201;0");
         /*_dApplic.getDModel().getSetOfStudents().addActivityToStudents(students,_activity.getID()+"201;0");
         _dApplic.getDModel().getConditionsTest().setMatrixBuilded(false,false);
        _dApplic.getDModel().getSetOfActivities().sendEvent(this);*/
      }
    }
    if (command.equals(DConst.BUT_REMOVE)) {  // Supprimer
      Activity activity= ((Activity)_activity.getAttach());
      if(activity.getSetOfTypes().size()>1){
        activity.getSetOfTypes().removeResource("2");
        init();
        _dModel.changeInDModelByModifyRemove(this);
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