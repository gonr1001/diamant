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
import dInterface.dUtil.DXTools;
import javax.swing.JFrame;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.TwoButtonsPanel;

import dInterface.dAffectation.EventsDlgInterface;
import dInterface.dAffectation.EditActivityDlg;


import dInternal.DModel;
import dInternal.dData.Resource;


import dResources.DConst;

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
    buildArrowButtons();
    jbInit();
  }//end method

  public ButtonsPanel setButtons() {
    //_applyPanel
    String [] a = {DConst.BUT_CHANGE, DConst.BUT_CLOSE};;
    _buttonsPanel = new TwoButtonsPanel(this, a);
    return _buttonsPanel;
  }

  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    //if the source is one of the the _leftArrowsPanel buttons
    //if Button CLOSE is pressed
    if (command.equals(DConst.BUT_CLOSE))
      dispose();
    if ((command.equals(DConst.BUT_CHANGE)) && (selectedItems!=null)){
      new EditActivityDlg(_jdialog, _dApplic, (String)selectedItems[0], false);
    }
  }//end method

  /**
  * build buttom to use in the dialog
  */
/* protected void buildArrowButtons(){
   String [] arrowsNames = {DConst.TO_RIGHT, DConst.TO_LEFT};
   _leftArrowsPanel = DXTools.arrowsPanel(this, arrowsNames,false);
   _rightArrowsPanel = DXTools.arrowsPanel(this, arrowsNames,false);
 } // end buildArrowButtons*/

 /**
  *
  */
  protected void doubleClicMouseProcess(){
   Resource event= _dm.getSetOfEvents().getResource((String)selectedItems[0]);
    ManualImprovementDetailed frameResult =
        new ManualImprovementDetailed(this,_toolBar, event.getID(), _dm);
  }
}//end class