/**
*
* Title: ManualImprovementDetailed $Revision: 1.15 $  $Date: 2004-11-05 13:53:48 $
* Description: ManualImprovementDetailed is a class used to
*              display the so called ManualImprovement which
*              gives the conflicts between an event and the others events
*              that are in the TTStructure
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
* @version $Revision: 1.15 $
* @author  $Author: syay1801 $
* @since JDK1.3
*/

package dInterface.dTimeTable;



import java.awt.Dimension;
import java.awt.Toolkit;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;

import dInterface.DToolBar;
import dInternal.DModel;
import dInternal.dDataTxt.Resource;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.TTStructure;





public class ManualImprovementDetailed extends JDialog {//{ implements ActionListener{
  /* ADJUST_HEIGHT is needed to ajdust the screenSize
  * minus the barSize (the value is a guess) at the bottom */
  private final static int ADJUST_HEIGHT = 88;
  /* ADJUST_WIDTH is needed to ajdust the screenSize
  * minus border pixels (the value is a guess) at each side of the screen */
  private final static int ADJUST_WIDTH = 6;
  //private JInternalFrame _jif;
  private TTPane _ttPane;
  private TTStructure _manualImproveTTStruct;
  private DToolBar _toolBar;

  /**
   * constructor
   */
  public ManualImprovementDetailed(JDialog jDialog, 
  									DToolBar toolbar,
									String eventName,
									DModel dm) {
    super(jDialog, eventName, true);
    _toolBar = toolbar;   
    initDlg(eventName, dm);
  }

  /**
   *
   * @return
   */
 /* public TTStructure getTTS(){
    return _ttStruct;
  }*/

 /**
  *
  * @return
  */
  /*public JFrame getJFrame(){
    return _jFrame;
  }*/

 /**
  *
  * @param e
  */
  /*
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
  }*/


  public void initDlg(String eventName, DModel dm){
  	_manualImproveTTStruct = dm.getTTStructure().cloneCurrentTTS();//cloneCurrentTTSruct(dm);
  	//dm.getConditionsTest().buildAllConditions(_manualImproveTTStruct);
  	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.setSize(new Dimension(screenSize.width - ADJUST_WIDTH,
                                screenSize.height - ADJUST_HEIGHT));
    
    _ttPane = new DetailedMITTPane(_manualImproveTTStruct,_toolBar, true, eventName);
    
    Resource event = dm.getSetOfEvents().getResource(eventName);   
    dm.getConditionsTest().addEventInAllPeriods(_manualImproveTTStruct, event);
   
    // to set colors
 /*   String eventPeriodKey=((EventAttach)event.getAttach()).getPeriodKey();
    long[] perKey={Long.parseLong(DXToolsMethods.getToken(eventPeriodKey,".",0)),
      Long.parseLong(DXToolsMethods.getToken(eventPeriodKey,".",1)),
      Long.parseLong(DXToolsMethods.getToken(eventPeriodKey,".",2))};
    int dayIndex= _manualImproveTTStruct.getCurrentCycle().getSetOfDays().getIndexOfResource(perKey[0]);
    int seqIndex= ((Day)_manualImproveTTStruct.getCurrentCycle().getSetOfDays().getResourceAt(dayIndex).
                   getAttach()).getSetOfSequences().getIndexOfResource(perKey[1]);
    int perIndex= ((Sequence)((Day)_manualImproveTTStruct.getCurrentCycle().getSetOfDays().getResourceAt(dayIndex).
                              getAttach()).getSetOfSequences().getResourceAt(seqIndex).getAttach()).
                  getSetOfPeriods().getIndexOfResource(perKey[2]);
    
    int duration = ((EventAttach)event.getAttach()).getDuration()/ dm.getTTStructure().getPeriodLenght();
    
    setColorOfPanel(dayIndex,seqIndex,perIndex,duration,((EventAttach)event.getAttach()).isPlaceInAPeriod());
    //end set colors;
     */
    _ttPane.updateTTPane(_manualImproveTTStruct);
    this.getContentPane().add(_ttPane.getPane());

    this.show();
  }

  /**
   * 
   * @param event
   * @param dm
   */	
 /* private void buildNewTTSTestConditions(Resource event, DModel dm){
  		/*dm.getConditionsTest().buildAllConditions(_ttStruct);
  		
		String eventPeriodKey = ((EventAttach)event.getAttach()).getPeriodKey();
		boolean eventAssignState = ((EventAttach)event.getAttach()).getAssignState();
		boolean eventPermanentState = ((EventAttach)event.getAttach()).getPermanentState();
		boolean inAPeriod = ((EventAttach)event.getAttach()).isPlaceInAPeriod();
		if (event!=null) {
			((EventAttach)event.getAttach()).setAssignState(true); //put in
			dm.getConditionsTest().removeEventInTTs(_ttStruct,event,false);
			//((EventAttach)event.getAttach()).setAssignState(false); //take aout inutile car remove put false in setAssign
			for(int i=0; i< _ttStruct.getCurrentCycle().getSetOfDays().size(); i++){
				Resource day= _ttStruct.getCurrentCycle().getSetOfDays().getResourceAt(i);
				for(int j=0; j< ((Day)day.getAttach()).getSetOfSequences().size(); j++){
					Resource seq= ((Day)day.getAttach()).getSetOfSequences().getResourceAt(j);
					for(int k=0; k< ((Sequence)seq.getAttach()).getSetOfPeriods().size();k++){
						Resource per= ((Sequence)seq.getAttach()).getSetOfPeriods().getResourceAt(k);
						int[] daytime={(int)day.getKey(), (int)seq.getKey(), (int)per.getKey()};
						//int origDuration= ((EventAttach)event.getAttach()).getDuration();
						//((EventAttach)event.getAttach()).setDuration(_ttStruct.getPeriodLenght());
						int duration = ((EventAttach)event.getAttach()).getDuration()/_ttStruct.getPeriodLenght();
						if(_ttStruct.getCurrentCycle().isPeriodContiguous(
											daytime[0],daytime[1],daytime[2],
											duration,
											new int[0],
											false)){
							String periodKey=daytime[0]+"."+daytime[1]+"."+daytime[2];
							((EventAttach)event.getAttach()).setKey(4,periodKey);
							((EventAttach)event.getAttach()).setAssignState(true);
							dm.getConditionsTest().addEventInTTS(_ttStruct,event,false);//rgr was true
							((EventAttach)event.getAttach()).setAssignState(false);
							k = k - 1 + duration;
						}// end  if(_ttStruct.getCurrentCycle().isPeriodContiguous(day
						//((EventAttach)event.getAttach()).setDuration(origDuration);
					}// end for(int k=0; k< ((Sequence)seq.getAttach())
				}// end for(int j=0; j< ((Day)day.getAttach()).getSetOfSequences().size(); j++)
			}// end for(int i=0; i< _newTTS.getCurrentCycle()
		}// end if(event!=null){
		((EventAttach)event.getAttach()).setKey(4,eventPeriodKey);
		((EventAttach)event.getAttach()).setAssignState(eventAssignState);
    	((EventAttach)event.getAttach()).setPermanentState(eventPermanentState);
    	((EventAttach)event.getAttach()).setInAPeriod(inAPeriod);*./
  }*/

  /**
   *
   * @param simple
   */
 /* protected JInternalFrame  buildInternalFrame(boolean simple){
    JInternalFrame jif;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameDim = new Dimension(700,650);
    //_documentName = title;
   jif = new JInternalFrame(FRAMENAME, true, true, true, true);
   //jif.addInternalFrameListener(this);
   jif.setDefaultCloseOperation(jif.DO_NOTHING_ON_CLOSE);
   jif.setTitle(FRAMENAME);
   //_simpleView= simpleView;
   //jif.addInternalFrameListener(this);
   jif.setMinimumSize(frameDim);
   jif.setPreferredSize(frameDim);

   if (simple)
     _ttPane = new SimpleTTPane(_ttStruct,_toolBar);
   else
     _ttPane = new DetailedTTPane(_ttStruct,_toolBar,false, jif.getSize());

   jif.getContentPane().add(_ttPane.getPane(), BorderLayout.CENTER);
   jif.pack();
   jif.setVisible(true);
   return jif;
  } // end buidDocument*/



   /**
    *
    */
  protected void setColorOfPanel(int dayIndex, int seqIndex, int perIndex, int duration, boolean isAssign){
    for (int i=0; i< ((JPanel)_ttPane.getViewport().getComponent(0)).getComponentCount(); i++){
      PeriodPanel perPanel= (PeriodPanel)((JPanel)_ttPane.getViewport().getComponent(0)).getComponent(i);
      Period period= _manualImproveTTStruct.getCurrentCycle().getPeriodByIndex( perPanel.getPeriodRef()[0],
          perPanel.getPeriodRef()[1],perPanel.getPeriodRef()[2]);
      //int[] ppKey={};
      if((dayIndex==perPanel.getPeriodRef()[0]) &&
         (seqIndex==perPanel.getPeriodRef()[1]) &&
         (perIndex<=perPanel.getPeriodRef()[2])&&
         (perPanel.getPeriodRef()[2]<= (perIndex+duration-1)) &&
         (isAssign)) {
        perPanel.setPanelColor(4);
      } else{
        if((period.getNbInstConflict()+period.getNbRoomConflict()+period.getNbStudConflict()) != 0){
          perPanel.setPanelColor(3);
        }// end if((period.getNbInstConflict()+period.getNbRoomConfli
      }

    }// end for (int i=0; i< ((JPanel)_ttPanel.getViewport().
  }
  	/**
  	 * cloneCurrentTTSruct 
  	 * @param dm
  	 * @return TTStructure containing the values of the TTStructure in dm
  	 */
	/*private TTStructure cloneCurrentTTSruct(DModel dm) {
		TTStructure oldTTS= dm.getTTStructure();
		TTStructure ttStruct = new TTStructure();
		ttStruct.setTTStructureDocument(oldTTS.getTTStructureDocument());
		return ttStruct;
  }*/
}
