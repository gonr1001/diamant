package dInterface.dTimeTable;

/**
 *
 * Title: SimpleTTPane $Revision: 1.1 $  $Date: 2003-10-17 18:11:07 $
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
 * @version $Revision: 1.1 $
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

  private MouseListener _mouseListener;


  public SimpleTTPane(TTStructure tts, DToolBar toolBar, boolean vertical, Dimension d, boolean standAlone) {
    super(tts, toolBar,standAlone);
    initSimpleTTPane(vertical, d);
  } // end  SimpleTTPane

  public SimpleTTPane(TTStructure tts, DToolBar toolBar, boolean vertical, Dimension d) {
    super(tts, toolBar,false);
    initSimpleTTPane(vertical, d);
  } // end  DetailedTTPane


  public JComponent getPane(){
    return _jScrollPaneOne;
  }

  private void initSimpleTTPane(boolean vertical, Dimension d) {
    _jScrollPaneOne = new JScrollPane();
    _jScrollPaneTwo = new JScrollPane();
    findRowHeaders();
    if(_tts!=null || _standAlone){
     initTTPane(_jScrollPaneOne);
   }
   System.out.println("H " + d.height + "  W " + d.width);

    _jSplitPane = new JSplitPane();
    _jSplitPane.setTopComponent(_jScrollPaneOne);
    _jSplitPane.setBottomComponent(null);
  } // end  SimpleTTPanel
  /**
   *
   * */
  public void manageActions(){
    //JPanel ttPanel= (JPanel)this.getViewport().getComponent(0);
    /*
     * Mouse listener for this Panel
     */ /*
     _mouseListener = new MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        System.out.println("Un clic sur la periode: ");
        if ((e.getClickCount() == 1) && (_toolBar!=null)) {
          PeriodPanel perpanel= (PeriodPanel)e.getSource();
          if(_lastActivPanel != null)
            _lastActivPanel.setPanelBackGroundColor(0);
          _toolBar.setComboBoxStatus(false);
          _toolBar.setPeriodSelector(Integer.toString(perpanel.getPanelRefNo()));
          //_dm.getDApplication().getToolBar().selectBar(1);
           perpanel.setPanelBackGroundColor(1);
           _toolBar.setComboBoxStatus(true);
          _lastActivPanel=perpanel;
         System.out.println("Un clic sur la periode: "+ perpanel.getPanelRefNo()+" Contains: "
                            +_tts.getCurrentCycle().getPeriodByIndex(
                            perpanel.getPeriodRef()[0],perpanel.getPeriodRef()[1],perpanel.getPeriodRef()[2]).toString());//debug
        }
      }
    };*/
  }
  //-------------------------------------------
  public void updateTTPane(TTStructure ttp){
    initTTPane(_jScrollPaneOne);
  }

  public int getIpady(int i) {
    return LINE_HEIGHT;
  }
  //-------------------------------------------
/*  public JPanel createViewPort() { //int width, int height) {
    Cycle cycle =_tts.getCurrentCycle();
    GridBagLayout gridbag =new GridBagLayout();
    JPanel panel =  new JPanel( gridbag );
    panel.setBackground(SystemColor.window);
    //panel.setMinimumSize(new Dimension(width/10, height/10));

    _jScrollPaneOne.setPreferredSize(new Dimension(panel.getPreferredSize().width +50,
                                   panel.getPreferredSize().height + 50) );
    int nbDays,nbSeq,nbPerADay;
    nbDays = cycle.getSetOfDays().size();
    //System.out.println("nb of Days: " + nbDays);//Debug
    gridbag.columnWeights = new double [nbDays];
    gridbag.columnWidths = new int [nbDays];
    for (int i = 0; i < nbDays; i++) {
      gridbag.columnWeights[i] = 1;
      gridbag.columnWidths[i] = PERIOD_WIDTH;
    }
    nbPerADay = _lastHour-_tts.getCurrentCycle().getFirstPeriod().getBeginHour()[0];
    gridbag.rowWeights = new double [nbPerADay];
    gridbag.rowHeights = new int [nbPerADay];
    for (int i = 0; i < nbPerADay; i++) {
      gridbag.rowWeights[i] = 1;
      gridbag.rowHeights[i] = SMALL_PERIOD_HEIGHT;
    }
    PeriodPanel periodPanel = null;
    GridBagConstraints c = null;
    nbSeq = _tts.getCurrentCycle().getMaxNumberOfSeqs();
    int count = 1;
    for (int i = 0; i < nbDays ; i++ ) {
      Day day= _tts.getCurrentCycle().getDayByRefNo(i+1);
      for(int j = 0; j < nbSeq; j ++) {
        Sequence sequence= _tts.getCurrentCycle().getSequence(day,j+1);
        for(int k = 0; k < sequence.getSetOfPeriods().size(); k ++) {
          Period period= _tts.getCurrentCycle().getPeriod(sequence,k+1);
          periodPanel = new SimplePeriodPanel(count,i,j,k);
          periodPanel.addMouseListener(_mouseListener);
          periodPanel.createPanel(period, PERIOD_WIDTH, LINE_HEIGHT );
          count++;
          c = new GridBagConstraints();
          c.fill = GridBagConstraints.BOTH;
          c.gridx = i;
          c.gridy = (period.getBeginHour()[0] - _tts.getCurrentCycle().getFirstPeriod().getBeginHour()[0]);//period.getEndHour(_periodLenght)[0];

          if ( period.getEndHour(_periodLenght)[1] == 0 ){
            c.gridheight = period.getEndHour(_periodLenght)[0]- period.getBeginHour()[0];
            c.insets = new Insets( period.getBeginHour()[1], 0, 0, 0 );
          } else {
            c.gridheight = period.getEndHour(_periodLenght)[0] + 1 - period.getBeginHour()[0];
            c.insets = new Insets( period.getBeginHour()[1], 0,(SMALL_PERIOD_HEIGHT- period.getEndHour(_periodLenght)[1]), 0 );
          }
          gridbag.setConstraints(periodPanel, c);
          panel.add(periodPanel, c);
        }//end for k
      } // end for j
    }//end for i
     return panel;
  }*/
  //-------------------------------------------
  /**
   * @param int the period panel reference
   * */
  public PeriodPanel getPeriodPanel(int ppRef){
    JPanel thePanel= (JPanel)_jScrollPaneOne.getViewport().getComponent(0);
    for (int i=0; i< thePanel.getComponentCount(); i++){
      PeriodPanel ppanel= (PeriodPanel)thePanel.getComponent(i);
      if(ppanel.getPanelRefNo()==ppRef)
        return ppanel;
    }
    return null;
  }
/*  //-------------------------------------------
  public JViewport getViewport() {
    return _jScrollPaneOne.getViewport();
  }
  //-------------------------------------------

*/
/*
  public JPanel createViewPort(int width, int height) {
    Cycle cycle =_tts.getCurrentCycle();
    GridBagLayout gridbag =new GridBagLayout();
    JPanel panel =  new JPanel( gridbag );
    panel.setBackground(SystemColor.window);
    panel.setMinimumSize(new Dimension(width/10, height/10));

    _jScrollPaneOne.setPreferredSize(new Dimension(panel.getPreferredSize().width +50,
                                   panel.getPreferredSize().height + 50) );
    int nbDays,nbSeq,nbPerADay;
    nbDays = cycle.getSetOfDays().size();
    //System.out.println("nb of Days: " + nbDays);//Debug
    gridbag.columnWeights = new double [nbDays];
    gridbag.columnWidths = new int [nbDays];
    for (int i = 0; i < nbDays; i++) {
      gridbag.columnWeights[i] = 1;
      gridbag.columnWidths[i] = PERIOD_WIDTH;
    }
    nbPerADay = _lastHour-_tts.getCurrentCycle().getFirstPeriod().getBeginHour()[0];
    gridbag.rowWeights = new double [nbPerADay];
    gridbag.rowHeights = new int [nbPerADay];
    for (int i = 0; i < nbPerADay; i++) {
      gridbag.rowWeights[i] = 1;
      gridbag.rowHeights[i] = SMALL_PERIOD_HEIGHT;
    }
    PeriodPanel periodPanel = null;
    GridBagConstraints c = null;
    nbSeq = _tts.getCurrentCycle().getMaxNumberOfSeqs();
    int count = 1;
    for (int i = 0; i < nbDays ; i++ ) {
      Day day= _tts.getCurrentCycle().getDayByRefNo(i+1);
      for(int j = 0; j < nbSeq; j ++) {
        Sequence sequence= _tts.getCurrentCycle().getSequence(day,j+1);
        for(int k = 0; k < sequence.getSetOfPeriods().size(); k ++) {
          Period period= _tts.getCurrentCycle().getPeriod(sequence,k+1);
          periodPanel = new SimplePeriodPanel(count,i,j,k);
          periodPanel.addMouseListener(_mouseListener);
          periodPanel.createPanel(period, PERIOD_WIDTH, LINE_HEIGHT );
          count++;
          c = new GridBagConstraints();
          c.fill = GridBagConstraints.BOTH;
          c.gridx = i;
          c.gridy = (period.getBeginHour()[0] - _tts.getCurrentCycle().getFirstPeriod().getBeginHour()[0]);//period.getEndHour(_periodLenght)[0];

          if ( period.getEndHour(_periodLenght)[1] == 0 ){
            c.gridheight = period.getEndHour(_periodLenght)[0]- period.getBeginHour()[0];
            c.insets = new Insets( period.getBeginHour()[1], 0, 0, 0 );
          } else {
            c.gridheight = period.getEndHour(_periodLenght)[0] + 1 - period.getBeginHour()[0];
            c.insets = new Insets( period.getBeginHour()[1], 0,(SMALL_PERIOD_HEIGHT- period.getEndHour(_periodLenght)[1]), 0 );
          }
          gridbag.setConstraints(periodPanel, c);
          panel.add(periodPanel, c);
        }//end for k
      } // end for j
    }//end for i
     return panel;
  }

  */
} /* end SimplePeriodPane */



