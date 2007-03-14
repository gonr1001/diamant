///**
//*
//* Title: ManualImprovementDetailed $Revision: 1.18 $  $Date: 2007-03-14 12:41:46 $
//* Description: ManualImprovementDetailed is a class used to
//*              display the so called ManualImprovement which
//*              gives the conflicts between an event and the others events
//*              that are in the TTStructure
//*
//*
//* Copyright (c) 2001 by rgr.
//* All rights reserved.
//*
//*
//* This software is the confidential and proprietary information
//* of rgr. ("Confidential Information").  You
//* shall not disclose such Confidential Information and shall use
//* it only in accordance with the terms of the license agreement
//* you entered into with rgr.
//*
//* @version $Revision: 1.18 $
//* @author  $Author: gonzrubi $
//* @since JDK1.3
//*/
//
//package dInterface.dTimeTable;
//
//
//
//import java.awt.Dimension;
//import java.awt.Toolkit;
//
//import javax.swing.JDialog;
//import javax.swing.JPanel;
//
//import dInterface.DToolBar;
//
//import dInternal.DModel;
//import dInternal.DResource;
//import dInternal.dOptimization.EventAttach;
//import dInternal.dTimeTable.Day;
//import dInternal.dTimeTable.Period;
//import dInternal.dTimeTable.Sequence;
//import dInternal.dTimeTable.TTStructure;
//import dInternal.dUtil.DXToolsMethods;
//
//
//
//
//
//public class ManualImprovementDetailed extends JDialog {
//  /* ADJUST_HEIGHT is needed to ajdust the screenSize
//  * minus the barSize (the value is a guess) at the bottom */
//  private final static int ADJUST_HEIGHT = 88;
//  /* ADJUST_WIDTH is needed to ajdust the screenSize
//  * minus border pixels (the value is a guess) at each side of the screen */
//  private final static int ADJUST_WIDTH = 6;
//  //private JInternalFrame _jif;
//  private TTPane _ttPane;
//  private TTStructure _manualImproveTTStruct;
//  private DToolBar _toolBar;
//
//  /**
//   * constructor
//   */
//  public ManualImprovementDetailed(JDialog jDialog, 
//  									DToolBar toolbar,
//									String eventName,
//									DModel dm) {
//    super(jDialog, eventName, true);
//    _toolBar = toolbar;   
//    initDlg(eventName, dm);
//  }
//
//  public void initDlg(String eventName, DModel dm){
//  	_manualImproveTTStruct = dm.getTTStructure().cloneCurrentTTS();
//  	dm.getConditionsTest().buildAllConditions(_manualImproveTTStruct);
//  	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//    this.setSize(new Dimension(screenSize.width - ADJUST_WIDTH,
//                                screenSize.height - ADJUST_HEIGHT));
//    
//    _ttPane = new DetailedMITTPane(_manualImproveTTStruct,_toolBar, true, eventName);
//    
//    DResource event = dm.getSetOfEvents().getResource(eventName);   
//    dm.getConditionsTest().addEventInAllPeriods(_manualImproveTTStruct, event);
//   
//    // to set colors
//    String eventPeriodKey=((EventAttach)event.getAttach()).getPeriodKey();
//    long[] perKey={Long.parseLong(DXToolsMethods.getToken(eventPeriodKey,".",0)),
//      Long.parseLong(DXToolsMethods.getToken(eventPeriodKey,".",1)),
//      Long.parseLong(DXToolsMethods.getToken(eventPeriodKey,".",2))};
//    int dayIndex= _manualImproveTTStruct.getCurrentCycle().getSetOfDays().getIndexOfResource(perKey[0]);
//    int seqIndex= ((Day)_manualImproveTTStruct.getCurrentCycle().getSetOfDays().getResourceAt(dayIndex).
//                   getAttach()).getSetOfSequences().getIndexOfResource(perKey[1]);
//    int perIndex= ((Sequence)((Day)_manualImproveTTStruct.getCurrentCycle().getSetOfDays().getResourceAt(dayIndex).
//                              getAttach()).getSetOfSequences().getResourceAt(seqIndex).getAttach()).
//                  getSetOfPeriods().getIndexOfResource(perKey[2]);
//    
//    int duration = ((EventAttach)event.getAttach()).getDuration()/ dm.getTTStructure().getPeriodLenght();
//    _ttPane.updateTTPane(_manualImproveTTStruct);
//    setColorOfPanel(dayIndex,seqIndex,perIndex,duration,((EventAttach)event.getAttach()).isPlaceInAPeriod());
//    //end set colors;
//     
//    
//    this.getContentPane().add(_ttPane.getPane());
//
//    this.setVisible(true);
//  }
//
//   /**
//    *
//    */
//  protected void setColorOfPanel(int dayIndex, int seqIndex, int perIndex, int duration, boolean isAssign){
//    for (int i=0; i< ((JPanel)_ttPane.getViewport().getComponent(0)).getComponentCount(); i++){
//      PeriodPanel perPanel= (PeriodPanel)((JPanel)_ttPane.getViewport().getComponent(0)).getComponent(i);
//      Period period= _manualImproveTTStruct.getCurrentCycle().getPeriodByIndex( perPanel.getPeriodRef()[0],
//          perPanel.getPeriodRef()[1],perPanel.getPeriodRef()[2]);
//      //int[] ppKey={};
//      if((dayIndex==perPanel.getPeriodRef()[0]) &&
//         (seqIndex==perPanel.getPeriodRef()[1]) &&
//         (perIndex<=perPanel.getPeriodRef()[2])&&
//         (perPanel.getPeriodRef()[2]<= (perIndex+duration-1)) &&
//         (isAssign)) {
//      	//period.setPriority(4);
//        perPanel.setPanelColor(4);
//      } else{
//        if((period.getNbInstConflict()+period.getNbRoomConflict()+period.getNbStudConflict()) != 0){
//        	//period.setPriority(3);
//        	perPanel.setPanelColor(3);
//        }// end if((period.getNbInstConflict()+period.getNbRoomConfli
//      }
//
//    }// end for (int i=0; i< ((JPanel)_ttPanel.getViewport().
//  }
//  	
//}// end ManualImprovementDetailed
