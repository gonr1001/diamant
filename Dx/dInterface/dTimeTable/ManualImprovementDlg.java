/**
*
* Title: ManualImprovementDlg $Revision: 1.26 $  $Date: 2004-12-16 19:20:52 $
* Description: DConst is a class used to
*
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
* @version $Revision: 1.26 $
* @author  $Author: gonzrubi $
* @since JDK1.3
*/
package dInterface.dTimeTable;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.DToolBar;
import dInterface.dAffectation.EditActivityDlg;
import dInterface.dAffectation.EventsDlgInterface;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.TwoButtonsPanel;

import dInternal.DModel;
import dInternal.DResource;


public class ManualImprovementDlg extends EventsDlgInterface {
   private DModel _dm;
   private DToolBar _toolBar;
   
  /**
   * Constructor
   * @param dApplic The application
   */
  public ManualImprovementDlg(DApplication dApplic, String title) {
    super(dApplic,title);
    _toolBar= dApplic.getToolBar();
    _dm = dApplic.getDModel();
    buildArrowButtons(false);
	initialize();
  }//end method

  public ButtonsPanel setButtons() {
    //_applyPanel
    String [] a = {DConst.BUT_CHANGE, DConst.BUT_CLOSE};
    _buttonsPanel = new TwoButtonsPanel(this, a);
    return _buttonsPanel;
  }
  
  /**
   * build buttom to use in the dialog
   */
  public void buildArrowButtons(boolean enableArrows) {
  	enableArrows = enableArrows && true;
	_leftArrowsPanel = new JPanel();
	_rightArrowsPanel = new JPanel();
  }
  
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    //if Button CLOSE is pressed
    if (command.equals(DConst.BUT_CLOSE))
      dispose();
    if ((command.equals(DConst.BUT_CHANGE)) && (selectedItems!=null)){
      new EditActivityDlg(_jDialog, _dApplic, (String)selectedItems[0], this, false);
    }
  }//end actionPerformed

  /**
   * When double click in an event display ManualImprovmentDetailed
   */
  protected void doubleClicMouseProcess(){
  	DResource event= _dm.getSetOfEvents().getResource((String)selectedItems[0]); 	
  	new ManualImprovementDetailed(this,_toolBar, event.getID(), _dm);
  }
}//end class ManualImprovementDlg