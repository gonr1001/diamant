package dInterface.dTimeTable;

/**
 *
 * Title: DetailedTTPane $Revision: 1.7 $  $Date: 2004-11-05 13:53:48 $
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
 * @version $Revision: 1.7 $
 * @author  $Author: syay1801 $
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


public class DetailedMITTPane extends TTPane {
  String _eventName;
  public DetailedMITTPane(TTStructure tts, DToolBar toolBar, boolean vertical) {
    super(tts, toolBar);
    initDetailedTTPane(vertical); //, d);
  } // end  DetailedTTPane

  public DetailedMITTPane(TTStructure tts, 
  							DToolBar toolBar, 
							boolean vertical,
							String eventName) {
    super(tts, toolBar);
    _eventName = eventName;
    initDetailedTTPane(vertical); //, d);
  } // end  DetailedTTPane
 //-------------------------------------------
  public JComponent getPane(){
    return _jSplitPane;
  }
 //-------------------------------------------
  public void updateTTPane(TTStructure ttp){
    _tts = ttp;
    findRowHeaders();

    initTTPane(_jScrollPaneTwo);
    initTTPane(_jScrollPaneOne);

  }
 //-------------------------------------------
  public int getIpady(int i) {
    if (_rowHeaders[i]._n == -1 || _rowHeaders[i]._n == 0)
      return LINE_HEIGHT * 2;
    return (LINE_HEIGHT + 1) * (_rowHeaders[i]._n + 2);
  }
  //-------------------------------------------
   public PeriodPanel createPeriodPanel(int refNo, String str) {
   return new DetailedPeriodPanelMI(refNo, str, _eventName);
  }
 //-------------------------------------------
  public PeriodPanel createEmptyPeriodPanel() {
    return new DetailedPeriodPanelMI(); 
  }
   //-------------------------------------------
  private void initDetailedTTPane(boolean vertical) { //, Dimension d) {
    _jScrollPaneOne = new JScrollPane();
    _jScrollPaneTwo = new JScrollPane();
   findRowHeaders();
   if(_tts!=null){

     initTTPane(_jScrollPaneTwo);
     initTTPane(_jScrollPaneOne);
   }
   if (vertical) {
     _jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,_jScrollPaneTwo, _jScrollPaneOne);
     _jSplitPane.setDividerLocation(0.0);
   } else {
      _jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,_jScrollPaneOne,_jScrollPaneTwo);
      _jSplitPane.setDividerLocation(0.0);
   }
    _jSplitPane.setOneTouchExpandable(true);

  }  // end initDetailedTTPane
} // end DetailedTTPane




