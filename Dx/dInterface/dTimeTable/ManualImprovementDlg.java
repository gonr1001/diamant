package dInterface.dTimeTable;

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
import dInterface.DToolBar;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.TwoButtonsPanel;

import dInterface.dAffectation.EventsDlgInterface;
import dInterface.dAffectation.EditActivityDlg;


import dInternal.DModel;
import dInternal.dData.Resource;


import dConstants.DConst;

public class ManualImprovementDlg extends EventsDlgInterface{

   private DModel _dm;
   private DToolBar _toolBar;
   private JFrame _jFrame;

  /**
   * Constructor
   * @param dApplic The application
   */
  public ManualImprovementDlg(DApplication dApplic, String title) {
    super(dApplic,title);
    _toolBar= dApplic.getToolBar();
    _dm= dApplic.getDMediator().getCurrentDoc().getDM();
    buildArrowButtons(false);
	initialize();
  }//end method

  public ButtonsPanel setButtons() {
    //_applyPanel
    String [] a = {DConst.BUT_CHANGE, DConst.BUT_CLOSE};;
    _buttonsPanel = new TwoButtonsPanel(this, a);
    return _buttonsPanel;
  }
  /**
   * build buttom to use in the dialog
   */
  public void buildArrowButtons(boolean enableArrows) {

	_leftArrowsPanel = new JPanel();
	_rightArrowsPanel = new JPanel();
  }
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    //if the source is one of the the _leftArrowsPanel buttons
/*
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
	}//end if ( (e.getSource().equals(_rightArrowsPanel.getComponent(0)))*/
    //if Button CLOSE is pressed
    if (command.equals(DConst.BUT_CLOSE))
      dispose();
    if ((command.equals(DConst.BUT_CHANGE)) && (selectedItems!=null)){
      new EditActivityDlg(_jDialog, _dApplic, (String)selectedItems[0], this, false);
    }
  }//end method


 /**
  *
  */
  protected void doubleClicMouseProcess(){
   Resource event= _dm.getSetOfEvents().getResource((String)selectedItems[0]);
    ManualImprovementDetailed frameResult =
        new ManualImprovementDetailed(this,_toolBar, event.getID(), _dm);
  }
}//end class