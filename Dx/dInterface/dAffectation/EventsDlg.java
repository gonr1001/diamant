package dInterface.dAffectation;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import dInternal.dConditionsTest.EventAttach;
import dInternal.dConditionsTest.SetOfEvents;
import dInternal.dData.SetOfActivities;
import dInternal.dData.Unity;
import dInternal.dUtil.DXToolsMethods;

import dInterface.DApplication;
import dInterface.dUtil.DXTools;

import dResources.DConst;

public class EventsDlg extends EventsDlgInterface{

   private String[] _buttonsNames = {DConst.BUT_OK, DConst.BUT_APPLY, DConst.BUT_CANCEL};

  /**
   * Constructor
   * @param dApplic The application
   */
  public EventsDlg(DApplication dApplic) {
    super(dApplic,DConst.EVENTS_DLG_TITLE);
    buildButtons();
    jbInit();
  }//end method



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
      _buttonsPanel.getComponent(1).setEnabled(true);
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
      _buttonsPanel.getComponent(1).setEnabled(true);
    }//end if ( (e.getSource().equals(_rightArrowsPanel.getComponent(0))) || (e.getSource().equals(_rightArrowsPanel.getComponent(1))) )
    //if Button CANCEL is pressed
    if (command.equals(_buttonsNames[2]))
      dispose();
    //if Button OK is pressed
    if (command.equals(_buttonsNames[0])){
      setUnities();
      _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfEvents().sendEvent(this);
      dispose();
    }
    //if Button APPLY is pressed
    if (command.equals(_buttonsNames[1])){
      setUnities();
      _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfEvents().sendEvent(this);
      _buttonsPanel.getComponent(1).setEnabled(false);
    }// end if Button APPLY
  }//end method

  /**
  * build buttom to use in the dialog
  */
 public void buildButtons(){
   _buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);
   String [] arrowsNames = {DConst.TO_RIGHT, DConst.TO_LEFT};
   _leftArrowsPanel = DXTools.arrowsPanel(this, arrowsNames,true);
   _rightArrowsPanel = DXTools.arrowsPanel(this, arrowsNames,true);
 }


}//end class