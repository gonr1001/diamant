package dInterface.dTimeTable;

/**
 *
 * Title: DetailedTTPane $Revision: 1.11 $  $Date: 2004-10-26 17:27:08 $
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
 * @version $Revision: 1.11 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */


/**
 * Description: DetailedTTPanel is a class used to
 *
 */

//import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import dInterface.DToolBar;
import dInternal.dTimeTable.TTStructure;


public class DetailedTTPane extends TTPane {

  public DetailedTTPane(TTStructure tts, DToolBar toolBar, boolean vertical){ //, Dimension d) {
    super(tts, toolBar);
    initDetailedTTPane(vertical);//, d);
  } // end  DetailedTTPane
 //-------------------------------------------
  public JComponent getPane(){
    return _jSplitPane;
  }
 //-------------------------------------------
  public void updateTTPane(TTStructure ttp){
    _tts = ttp;
    findRowHeaders();
    initTTPane(_jScrollPaneOne);
    initTTPane(_jScrollPaneTwo);
  }
 //-------------------------------------------
  public int getIpady(int i) {
    if (_rowHeaders[i]._n == -1 || _rowHeaders[i]._n == 0)
      return LINE_HEIGHT * 2;
    return (LINE_HEIGHT + 1) * (_rowHeaders[i]._n + 2);
  }
  //-------------------------------------------
   public PeriodPanel createPeriodPanel(int refNo, String str) {
   return new DetailedPeriodPanel(refNo, str);
  }
 //-------------------------------------------
  public PeriodPanel createEmptyPeriodPanel() {
    return new DetailedPeriodPanel();
  }
   //-------------------------------------------
  private void initDetailedTTPane(boolean vertical) { //, Dimension d) {
    _jScrollPaneOne = new JScrollPane();
    _jScrollPaneTwo = new JScrollPane();
   findRowHeaders();
   if(_tts!=null){
     initTTPane(_jScrollPaneOne);
     initTTPane(_jScrollPaneTwo);
   }
   if (vertical) {
     _jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,_jScrollPaneOne,_jScrollPaneTwo);
     _jSplitPane.setDividerLocation(0.0);
   } else {
      _jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,_jScrollPaneOne,_jScrollPaneTwo);
      _jSplitPane.setDividerLocation(0.0);
   }
    _jSplitPane.setOneTouchExpandable(true);

  }  // end initDetailedTTPane
} // end DetailedTTPane


