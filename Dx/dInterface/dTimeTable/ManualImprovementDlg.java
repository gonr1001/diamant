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

import dInterface.dAffectation.EventsDlgInterface;
import dInterface.dAffectation.EditActivityDlg;


import dInternal.DModel;
import dInternal.dData.Resource;


import dResources.DConst;

public class ManualImprovementDlg extends EventsDlgInterface{

   private String[] _buttonsNames = {DConst.BUT_CHANGE,DConst.BUT_APPLY, DConst.BUT_CLOSE};
   private DModel _dm;
//   private TTStructure _newTTS;
   private DToolBar _toolBar;
   private JFrame _jFrame;

  /**
   * Constructor
   * @param dApplic The application
   */
  public ManualImprovementDlg(DApplication dApplic, String title) {
    super(dApplic,title);
   // TTStructure oldTTS= dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure();
   // _newTTS= new TTStructure();
   // _newTTS.setTTStructureDocument(oldTTS.getTTStructureDocument());
    _toolBar= dApplic.getToolBar();
    _dm= dApplic.getDMediator().getCurrentDoc().getDM();
    buildButtons();
    jbInit();
  }//end method



  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    //if the source is one of the the _leftArrowsPanel buttons
    //if Button CLOSE is pressed
    if (command.equals(_buttonsNames[2]))
      dispose();
    if ((command.equals(_buttonsNames[0])) && (selectedItems!=null)){
      new EditActivityDlg(_jdialog,_dApplic, (String)selectedItems[0], this,false);
    }
  }//end method

  /**
  * build buttom to use in the dialog
  */
 protected void buildButtons(){
   _buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);
   String [] arrowsNames = {DConst.TO_RIGHT, DConst.TO_LEFT};
   _leftArrowsPanel = DXTools.arrowsPanel(this, arrowsNames,false);
   _rightArrowsPanel = DXTools.arrowsPanel(this, arrowsNames,false);
 }

 /**
  *
  */
  protected void doubleClicMouseProcess(){
   Resource event= _dm.getSetOfEvents().getResource((String)selectedItems[0]);
    ManualImprovementDetailed frameResult =
        new ManualImprovementDetailed(this,_toolBar, event.getID(), _dm);
  }
}//end class