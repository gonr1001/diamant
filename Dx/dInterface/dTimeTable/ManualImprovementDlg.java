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
import dInterface.dUtil.DXTools;
import javax.swing.JFrame;
import dInterface.dAffectation.EventsDlgInterface;
import dInternal.dConditionsTest.TestConditions;
import dInternal.dConditionsTest.SetOfEvents;
import dInternal.dConditionsTest.EventAttach;
import dInternal.dTimeTable.*;
import dInternal.DModel;
import dInternal.dData.Resource;
import dInternal.dUtil.DXToolsMethods;

import dResources.DConst;

public class ManualImprovementDlg extends EventsDlgInterface{

   private String[] _buttonsNames = {DConst.BUT_CLOSE};
   private ManualImprovementResultFrame _frameResult;
   private DModel _dm;
   TTStructure _newTTS;
   JFrame _jInternalF;

  /**
   * Constructor
   * @param dApplic The application
   */
  public ManualImprovementDlg(DApplication dApplic, String title) {
    super(dApplic,title);
    TTStructure oldTTS= dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure();
    _newTTS= new TTStructure();
    _newTTS.setTTStructureDocument(oldTTS.getTTStructureDocument());
    _frameResult= new ManualImprovementResultFrame(_newTTS,dApplic.getToolBar());
    _dm= dApplic.getDMediator().getCurrentDoc().getDM();
    buildButtons();
    jbInit();
  }//end method



  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    //if the source is one of the the _leftArrowsPanel buttons
    //if Button CLOSE is pressed
    if (command.equals(_buttonsNames[0]))
      dispose();

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
    buildNewTTSTestConditions(event);
    _jInternalF=_frameResult.createFrame(true, event.getID());
    String eventPeriodKey=((EventAttach)event.getAttach()).getPeriodKey();
    long[] perKey={Long.parseLong(DXToolsMethods.getToken(eventPeriodKey,".",0)),
      Long.parseLong(DXToolsMethods.getToken(eventPeriodKey,".",1)),
      Long.parseLong(DXToolsMethods.getToken(eventPeriodKey,".",2))};
    int dayIndex= _newTTS.getCurrentCycle().getSetOfDays().getIndexOfResource(perKey[0]);
    int seqIndex= ((Day)_newTTS.getCurrentCycle().getSetOfDays().getResourceAt(dayIndex).
                   getAttach()).getSetOfSequences().getIndexOfResource(perKey[1]);
    int perIndex= ((Sequence)((Day)_newTTS.getCurrentCycle().getSetOfDays().getResourceAt(dayIndex).
                   getAttach()).getSetOfSequences().getResourceAt(seqIndex).getAttach()).
                  getSetOfPeriods().getIndexOfResource(perKey[2]);
    //int[] perKeyIndex={};
    int duration = ((EventAttach)event.getAttach()).getDuration()/_dApplic.getDMediator()
                 .getCurrentDoc().getDM().getTTStructure().getPeriodLenght();
    _frameResult.setColorOfPanel(dayIndex,seqIndex,perIndex,duration);
    //_frameResult.setColorOfPanel(event.getID());
  }

  /**
   *
   */
  private void buildNewTTSTestConditions(Resource event){
    _dm.getConditionsTest().buildAllConditions(_newTTS);
    //Resource event= _dm.getSetOfEvents().getResource((String)selectedItems[0]);
    String eventPeriodKey= ((EventAttach)event.getAttach()).getPeriodKey();
    boolean eventAssignState= ((EventAttach)event.getAttach()).getAssignState();
    if(event!=null){
      _dm.getConditionsTest().addOrRemEventInTTs(_newTTS,event,-1);
      for(int i=0; i< _newTTS.getCurrentCycle().getSetOfDays().size(); i++){
        Resource day= _newTTS.getCurrentCycle().getSetOfDays().getResourceAt(i);
        for(int j=0; j< ((Day)day.getAttach()).getSetOfSequences().size(); j++){
          Resource seq= ((Day)day.getAttach()).getSetOfSequences().getResourceAt(j);
          for(int k=0; k< ((Sequence)seq.getAttach()).getSetOfPeriods().size();k++){
            Resource per= ((Sequence)seq.getAttach()).getSetOfPeriods().getResourceAt(k);
            int[] daytime={(int)day.getKey(), (int)seq.getKey(), (int)per.getKey()};
            String periodKey=daytime[0]+"."+daytime[1]+"."+daytime[2];
            ((EventAttach)event.getAttach()).setKey(4,periodKey);
            _dm.getConditionsTest().addOrRemEventInTTs(_newTTS,event,1);
          }// end for(int k=0; k< ((Sequence)seq.getAttach())
        }// end for(int j=0; j< ((Day)day.getAttach()).getSetOfSequences().size(); j++)
      }// end for(int i=0; i< _newTTS.getCurrentCycle()
    }// end if(event!=null){
    ((EventAttach)event.getAttach()).setKey(4,eventPeriodKey);
    ((EventAttach)event.getAttach()).setAssignState(eventAssignState);
  }


}//end class