//package dInterface.dTimeTable;
//
///**
// *
// * Title: ConflictsOfAnEventTTPane $Revision: 1.3 $  $Date: 2008-02-22 16:54:57 $
// *
// *
// * Copyright (c) 2001 by rgr.
// * All rights reserved.
// *
// *
// * This software is the confidential and proprietary information
// * of rgr. ("Confidential Information").  You
// * shall not disclose such Confidential Information and shall use
// * it only in accordance with the terms of the license agreement
// * you entered into with rgr.
// *
// * @version $Revision: 1.3 $
// * @author  $Author: gonzrubi $
// * @since JDK1.3
// *
// * Our convention is that: It's necessary to indicate explicitly
// * all Exceptions that a method can throw.
// * All Exceptions must be handled explicitly.
// */
//
//
///**
// * Description: ConflictsOfAnEventTTPane is a class used to
// *
// */
//
//
//import javax.swing.JComponent;
//import javax.swing.JScrollPane;
//import javax.swing.JSplitPane;
//
//import dInterface.DToolBar;
//import dInternal.DResource;
//import dInternal.dTimeTable.Period;
//import dInternal.dTimeTable.TTStructure;
//
//
//public class ConflictsOfAnEventTTPane extends TTPane {
//  TTStructure _totalTTStruct;
//  DResource _event;
//  
//  /**
//   * 
//   * @param tts
//   * @param toolBar
//   * @param vertical
//   * @param eventName
//   */
//  public ConflictsOfAnEventTTPane(TTStructure totaltts, TTStructure partialtts, 
//  							DToolBar toolBar, 
//							boolean vertical,
//							DResource event) {
//    super(partialtts, toolBar);
//    _event = event;
//    _totalTTStruct = totaltts;
//    initDetailedTTPane(vertical); 
//  } // end  ConflictsOfAnEventTTPane
// //-------------------------------------------
//  public JComponent getPane(){
//    return _jSplitPane;
//  }
// //-------------------------------------------
//  public void updateTTPane(TTStructure ttp){
//    _tts = ttp;
//    findRowHeaders();
//    //int[] indexes= getEventIndexes()
//    initTTPane(_jScrollPaneTwo);
//    initTTPane(_jScrollPaneOne);
//
//  }
// //-------------------------------------------
//  public int getIpady(int i) {
//    if (_rowHeaders[i]._n == -1 || _rowHeaders[i]._n == 0)
//      return LINE_HEIGHT * 2+ LINE_HEIGHT;
//    return (LINE_HEIGHT + 1) * (_rowHeaders[i]._n + 2) +LINE_HEIGHT;
//  }
//  //-------------------------------------------
//   public PeriodPanel createPeriodPanel(int refNo, String str) {
//   	Period totalPeriod= _totalTTStruct.getCurrentCycle().getPeriodByPeriodKey(str);
//   return new ConflictsOfAnEventPeriodPanel(refNo, str,totalPeriod, _event);
//  }
// //-------------------------------------------
//  public PeriodPanel createEmptyPeriodPanel() {
//    return new ConflictsOfAnEventPeriodPanel(); 
//  }
//   //-------------------------------------------
//  private void initDetailedTTPane(boolean vertical) { //, Dimension d) {
//    _jScrollPaneOne = new JScrollPane();
//    _jScrollPaneTwo = new JScrollPane();
//   findRowHeaders();
//   if(_tts!=null){
//
//     initTTPane(_jScrollPaneTwo);
//     initTTPane(_jScrollPaneOne);
//   }
//   if (vertical) {
//     _jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,_jScrollPaneTwo, _jScrollPaneOne);
//     _jSplitPane.setDividerLocation(0.0);
//   } else {
//      _jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,_jScrollPaneOne,_jScrollPaneTwo);
//      _jSplitPane.setDividerLocation(0.0);
//   }
//    _jSplitPane.setOneTouchExpandable(true);
//
//  }  // end initDetailedTTPane
//  
//  /**
//   * 
//   * @param event
//   * @return int[4] where index 0= day index, index 1= seq index, 
//   * index 2= period index, index 3= duration
//   */
//  /*
//  private int[] getEventIndexes(DResource event){
////  to set colors
//  	int[] indexes={0,0,0,0};
//    String eventPeriodKey=((EventAttach)event.getAttach()).getPeriodKey();
//    long[] perKey={Long.parseLong(DXToolsMethods.getToken(eventPeriodKey,".",0)),
//      Long.parseLong(DXToolsMethods.getToken(eventPeriodKey,".",1)),
//      Long.parseLong(DXToolsMethods.getToken(eventPeriodKey,".",2))};
//    indexes[0]= _totalTTStruct.getCurrentCycle().getSetOfDays().getIndexOfResource(perKey[0]);// day Index
//    indexes[1]= ((Day)_totalTTStruct.getCurrentCycle().getSetOfDays().getResourceAt(indexes[0]).// seq Index
//                   getAttach()).getSetOfSequences().getIndexOfResource(perKey[1]);
//    indexes[2]= ((Sequence)((Day)_totalTTStruct.getCurrentCycle().getSetOfDays().getResourceAt(indexes[0]).
//                              getAttach()).getSetOfSequences().getResourceAt(indexes[1]).getAttach()).
//                  getSetOfPeriods().getIndexOfResource(perKey[2]);// period Index
//    
//    indexes[3] = ((EventAttach)event.getAttach()).getDuration()/ _totalTTStruct.getPeriodLenght();// duration
//    //_ttPane.updateTTPane(_partialTTStruct);
//    //setColorOfPanel(dayIndex,seqIndex,perIndex,duration,((EventAttach)event.getAttach()).isPlaceInAPeriod());
//    //end set colors;
//    return indexes;
//  }
//  */
//  
//  /**
//  *
//  */
///*protected void setColorOfPanel(int dayIndex, int seqIndex, int perIndex, int duration, boolean isAssign){
//  for (int i=0; i< ((JPanel)this.getViewport().getComponent(0)).getComponentCount(); i++){
//    PeriodPanel perPanel= (PeriodPanel)((JPanel)this.getViewport().getComponent(0)).getComponent(i);
//    Period period= _tts.getCurrentCycle().getPeriodByIndex( perPanel.getPeriodRef()[0],
//        perPanel.getPeriodRef()[1],perPanel.getPeriodRef()[2]);
//    
//    if((dayIndex==perPanel.getPeriodRef()[0]) &&
//       (seqIndex==perPanel.getPeriodRef()[1]) &&
//       (perIndex<=perPanel.getPeriodRef()[2])&&
//       (perPanel.getPeriodRef()[2]<= (perIndex+duration-1)) &&
//       (isAssign)) {
//    	
//      perPanel.setPanelColor(4);
//    } else{
//      if((period.getNbInstConflict()+period.getNbRoomConflict()+period.getNbStudConflict()) != 0){
//      	
//      	perPanel.setPanelColor(3);
//      }// end if((period.getNbInstConflict()+period.getNbRoomConfli
//    }
//
//  }// end for (int i=0; i< ((JPanel)_ttPanel.getViewport().
//  
//}*/
//
//} // end DetailedTTPane
//
//
//
//
