package dInterface.dAffectation;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */


import java.awt.event.ActionEvent;
import dInterface.DApplication;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.TwoButtonsPanel;
import dInterface.dUtil.DXTools;

import dInternal.dConditionsTest.EventAttach;

import dResources.DConst;

public class EventsDlg extends EventsDlgInterface{


  /**
   * Constructor
   * @param dApplic The application
   */
  public EventsDlg(DApplication dApplic, String title) {
    super(dApplic,title);
    buildButtons();
    jbInit();
  }//end method

  public ButtonsPanel setButtons() {
    //_applyPanel
    String [] a = {DConst.BUT_APPLY, DConst.BUT_CLOSE};
    _buttonsPanel = new TwoButtonsPanel(this, a);
    //Setting the button APPLY disable
    _buttonsPanel.setFirstDisable();
    return _buttonsPanel;

  }

  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    //if the source is one of the the _leftArrowsPanel buttons
    if ( (e.getSource().equals(_leftArrowsPanel.getComponent(0))) ||
         (e.getSource().equals(_leftArrowsPanel.getComponent(1))) ){
      //if "toRight" button
      if (e.getSource().equals(_leftArrowsPanel.getComponent(0)))
        DXTools.listTransfers(_leftList, _centerList, _leftVector, _centerVector, 1);
      else
        DXTools.listTransfers(_centerList, _leftList, _centerVector, _leftVector, 1);
      _leftLabel.setText(String.valueOf(_leftVector.size()));
      _centerLabel.setText(String.valueOf(_centerVector.size()));
      _buttonsPanel.setFirstEnable();
    }//end if ( (e.getSource().equals(_leftArrowsPanel.getComponent(0)))) || (e.getSource().equals(_leftArrowsPanel.getComponent(1)))) )
    //if the source is one of the the _rightArrowsPanel buttons
    if ( (e.getSource().equals(_rightArrowsPanel.getComponent(0))) ||
         (e.getSource().equals(_rightArrowsPanel.getComponent(1))) ){
      //if "toRight" button
      if (e.getSource().equals(_rightArrowsPanel.getComponent(0)))
        DXTools.listTransfers(_centerList, _rightList, _centerVector, _rightVector, 1);
      else
        DXTools.listTransfers(_rightList, _centerList, _rightVector, _centerVector, 1);
      _rightLabel.setText(String.valueOf(_rightVector.size()));
      _centerLabel.setText(String.valueOf(_centerVector.size()));
      _buttonsPanel.setFirstEnable();
    }//end if ( (e.getSource().equals(_rightArrowsPanel.getComponent(0))) || (e.getSource().equals(_rightArrowsPanel.getComponent(1))) )
    //if Button CLOSE is pressed
    if (command.equals(DConst.BUT_CLOSE))
      dispose();
    //if Button OK is pressed
/*    if (command.equals(_buttonsNames[0])){
      setUnities();
      _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfEvents().sendEvent(this);
      dispose();
    }*/
    //if Button APPLY is pressed
    if (command.equals(DConst.BUT_APPLY)){
      setUnities();
      _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfEvents().sendEvent(this);
      _buttonsPanel.setFirstDisable();
    }// end if Button APPLY
  }//end method

  /**
  * build buttom to use in the dialog
  */
 protected void buildButtons(){
   //_buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);
   String [] arrowsNames = {DConst.TO_RIGHT, DConst.TO_LEFT};
   _leftArrowsPanel = DXTools.arrowsPanel(this, arrowsNames,true);
   _rightArrowsPanel = DXTools.arrowsPanel(this, arrowsNames,true);
 }

 /**
 *
 */
 protected void doubleClicMouseProcess(){
   //new EditActivityDlg(_jdialog,_dApplic, (String)selectedItems[0], this,false);
   new EditActivityDlg(_jdialog,_dApplic, (String)selectedItems[0], false);
  /* EventAttach event=(EventAttach)_dApplic.getDMediator().getCurrentDoc().getDM().
    getSetOfEvents().getResource((String)selectedItems[0]).getAttach();
   if(event.getAssignState() || event.getPermanentState())
     jbInit();*/
  }


}//end class