package dInterface.dTimeTable;

/**
 *
 * Title: SimpleTTPane $Revision: 1.4 $  $Date: 2003-10-20 15:01:10 $
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
 * @version $Revision: 1.4 $
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

  public SimpleTTPane(TTStructure tts, DToolBar toolBar, boolean vertical, Dimension d) {
    super(tts, toolBar);
    initSimpleTTPane(vertical, d);
  } // end  SimpleTTPane

  public JComponent getPane(){
    return _jScrollPaneOne;
  }

  private void initSimpleTTPane(boolean vertical, Dimension d) {
    _jScrollPaneOne = new JScrollPane();
    _jScrollPaneTwo = new JScrollPane();
    findRowHeaders();
    if(_tts!=null){
     initTTPane(_jScrollPaneOne);
   }
    _jSplitPane = new JSplitPane();
    _jSplitPane.setTopComponent(_jScrollPaneOne);
    _jSplitPane.setBottomComponent(null);
  } // end  SimpleTTPanel
  /**
   *
   * */
  public void manageActions(){
    JPanel ttPanel= (JPanel)this.getViewport().getComponent(0);
    /*
     * Mouse listener for this Panel
     */
     _mouseListener = new MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        System.out.println("Un clic sur la periode: ");
        if ((e.getClickCount() == 1) && (_toolBar!=null)) {
          PeriodPanel perpanel= (PeriodPanel)e.getSource();
          if(_lastActivePanel != null)
            _lastActivePanel.setPanelBackGroundColor(0);
          _toolBar.setComboBoxStatus(false);
          _toolBar.setPeriodSelector(Integer.toString(perpanel.getPanelRefNo()));
           perpanel.setPanelBackGroundColor(1);
           _toolBar.setComboBoxStatus(true);
          _lastActivePanel=perpanel;
         System.out.println("Un clic sur la periode: "+ perpanel.getPanelRefNo()+" Ref: " +
                                                    perpanel.getPeriodRef()[0] +"." +
                                                    perpanel.getPeriodRef()[1]+"." +
                                                    perpanel.getPeriodRef()[2]);//debug

         System.out.println("Un clic sur la periode: "+ perpanel.getPanelRefNo()+" Contains: "
                            +_tts.getCurrentCycle().getPeriodByIndex(
                                                    perpanel.getPeriodRef()[0],
                                                    perpanel.getPeriodRef()[1],
                                                    perpanel.getPeriodRef()[2]).toString());//debug
        }
      }
    };
  }
  //-------------------------------------------
  public void updateTTPane(TTStructure ttp){
    initTTPane(_jScrollPaneOne);
  }

  public int getIpady(int i) {
    return LINE_HEIGHT * 2;
  }

  public PeriodPanel createPeriodPanel(int refNo, String str) {
   return new SimplePeriodPanel(refNo, str);
  }

  public PeriodPanel createEmptyPeriodPanel() {
   return new SimplePeriodPanel();
  }
  //-------------------------------------------


} /* end SimplePeriodPane */



