package dInterface.dTimeTable;

/**
 *
 * Title: SimpleTTPane $Revision: 1.7 $  $Date: 2003-10-22 19:28:36 $
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
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */


/**
 * Description: SimpleTTPanel is a class used to
 *
 */

import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.util.*;
import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.JViewport;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.*;
import java.awt.event.*;

import dInterface.DToolBar;

import dInternal.DModel;
import dInternal.dData.Resource;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Day;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.Sequence;
import dInternal.dTimeTable.TTStructure;



public class SimpleTTPane extends TTPane {

  public SimpleTTPane(TTStructure tts, DToolBar toolBar) {
    super(tts, toolBar);
    initSimpleTTPane();
  } // end  SimpleTTPane

  public JComponent getPane(){
    return _jScrollPaneOne;
  }
  //-------------------------------------------
   public void updateTTPane(TTStructure ttp){
     _tts = ttp;
     findRowHeaders();
     initTTPane(_jScrollPaneOne);
   }
 //-------------------------------------------
   public int getIpady(int i) {
     return LINE_HEIGHT * 2;
   }
   //-------------------------------------------
   public PeriodPanel createPeriodPanel(int refNo, String str) {
    return new SimplePeriodPanel(refNo, str);
   }
  //-------------------------------------------
   public PeriodPanel createEmptyPeriodPanel() {
    return new SimplePeriodPanel();
   }
 //-------------------------------------------
  private void initSimpleTTPane() {
    _jScrollPaneOne = new JScrollPane();
    _jScrollPaneTwo = new JScrollPane();
    findRowHeaders();
    if(_tts!=null){
     initTTPane(_jScrollPaneOne);
   }
    _jSplitPane = new JSplitPane();
    _jSplitPane.setTopComponent(_jScrollPaneOne);
    _jSplitPane.setBottomComponent(null);
  } // end  initSimpleTTPanel
} /* end SimplePeriodPane */



