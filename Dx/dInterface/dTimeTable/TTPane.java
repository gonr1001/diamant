package dInterface.dTimeTable;

/**
 *
 * Title: TTPane $Revision: 1.10 $  $Date: 2003-10-28 14:24:53 $
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
 * @version $Revision: 1.10 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */


/**
 * Description: TTPanel is a class used to
 *
 */
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;


import javax.swing.JComponent;
import javax.swing.JViewport;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.BorderFactory;

import dInterface.DToolBar;
import dInternal.dData.Resource;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Day;

import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.TTStructure;
import dInternal.dUtil.DisplayAttributs;
import dInternal.dUtil.DXToolsMethods;

public abstract class TTPane {

  protected MouseListener _mouseListener;
  protected final int PERIOD_WIDTH =  94;  // for the screen
  protected final int LINE_HEIGHT = 12;
  protected final int HEADER_HEIGHT = 20 ;
  protected final int ROW_HEADER_WIDTH =  35;

  protected TTStructure _tts;
  protected DToolBar _toolBar;
  protected JScrollPane _jScrollPaneOne;
  protected JScrollPane _jScrollPaneTwo;
  protected JSplitPane _jSplitPane;
  PeriodPanel _lastActivePanel = null;

  protected class RowRecord {
     int _n;
     String _str;
     RowRecord(int n, String str){
       _n = n; _str = str;
     }
  }
  protected RowRecord [] _rowHeaders;
  protected DisplayAttributs [][] _toDisplay;

  /**
   * constructor
   * @param tts
   * @param toolBar
   * @param standAlone if true it start a independent application
   */
  protected TTPane(TTStructure tts, DToolBar toolBar) {
    _tts = tts;
    _toolBar = toolBar;
    _jScrollPaneOne = null;
    _jScrollPaneTwo = null;
    _jSplitPane = null;
  }
  //-------------------------------------------
  public abstract JComponent getPane(); //?
  //-------------------------------------------
  public abstract void updateTTPane(TTStructure ttp);
  //-------------------------------------------
  protected abstract int getIpady(int i);
  //-------------------------------------------
  protected abstract PeriodPanel createPeriodPanel(int refNo, String str);
  //-------------------------------------------
  protected abstract PeriodPanel createEmptyPeriodPanel();
  //-------------------------------------------
  public JViewport getViewport() {
    return _jScrollPaneOne.getViewport();
  }
  //-------------------------------------------
  public PeriodPanel getPeriodPanel(int ppRef){
   JPanel thePanel= (JPanel)_jScrollPaneOne.getViewport().getComponent(0);
   for (int i=0; i< thePanel.getComponentCount(); i++){
     PeriodPanel ppanel= (PeriodPanel)thePanel.getComponent(i);
     if(ppanel.getPanelRefNo()==ppRef)
       return ppanel;
   }
   return null;
  }
  //-------------------------------------------
  protected void initTTPane(JScrollPane jScrollPane) {
    jScrollPane.setColumnHeaderView(createColumnHeader());
    jScrollPane.setRowHeaderView(createRowHeader());
    jScrollPane.setViewportView(createViewPort());
    // Point point = jScrollPaneOne.getViewport().getViewPosition();
    jScrollPane.getViewport().setViewPosition(jScrollPane.getViewport().getViewPosition());
    manageActions();
  }
  //-------------------------------------------
  protected void findRowHeaders() {
    _toDisplay = _tts.getCurrentCycle().getAttributsToDisplay(_tts.getPeriodLenght());
    _rowHeaders = new RowRecord [_toDisplay[0].length];
    for(int i = 0; i < _toDisplay[0].length; i++) {
      if (_toDisplay[0][i].getEventsInPeriods()!=null){
        _rowHeaders[i] = new RowRecord(_toDisplay[0][i].getEventsInPeriods().size(),
                                        _toDisplay[0][i].getHourToDisplay());

      } else {
        _rowHeaders[i] = new RowRecord(-1,
                                       _toDisplay[0][i].getHourToDisplay());
      } // end if (_toDisplay[0][i].getEventsInPeriods()!=null) else
    }// end for(int i = 0; i < _toDisplay[0].length; i++)

    for(int j = 0; j < _toDisplay[0].length; j++){
      for(int i = 0; i < _toDisplay.length; i++) {
        if (_toDisplay[i][j].getEventsInPeriods()!=null){
          if (_rowHeaders[j]._n < _toDisplay[i][j].getEventsInPeriods().size())
            _rowHeaders[j]._n = _toDisplay[i][j].getEventsInPeriods().size();
        } // end if (_toDisplay[i][j].getEventsInPeriods()!=null){
      } //end for(int i = 0; i < _toDisplay.length; i++) {
    } //end for(int j = 0; j < _toDisplay[0].length; j++){

  } //end findRowHeaders

  protected JPanel createColumnHeader() {
    JPanel jPanel = new JPanel();
    GridBagLayout gridBL = new GridBagLayout();
    GridBagConstraints gridBC = new GridBagConstraints();
    jPanel.setLayout(gridBL);
    JLabel jLabel;
    Cycle cycle;
    gridBC.gridy = 0;
    gridBC.ipadx = PERIOD_WIDTH;
    for (int i = 0; i < _toDisplay.length ; i++){
      gridBC.gridx = i;

      cycle = _tts.getCurrentCycle();
      Resource day = cycle.getSetOfDays().getResourceAt(i);
      jLabel = new JLabel("J " + (i + 1) + " : " + day.getID(), JLabel.CENTER);
      jLabel.setMinimumSize(new Dimension(PERIOD_WIDTH, HEADER_HEIGHT));
      jLabel.setPreferredSize(new Dimension(PERIOD_WIDTH, HEADER_HEIGHT));
      jLabel.setBorder(BorderFactory.createEtchedBorder());
      gridBL.setConstraints(jLabel, gridBC);
      jPanel.add(jLabel, gridBC);
    }
    jPanel.setBorder(BorderFactory.createEtchedBorder());
    return jPanel;
  } // end createColumnHeader


  protected JPanel createRowHeader() {
    JPanel jPanel = new JPanel();
    GridBagLayout gridBL = new GridBagLayout();
    GridBagConstraints gridBC = new GridBagConstraints();
    jPanel.setLayout(gridBL);

    JLabel jLabel;
    for (int i = 0; i < _rowHeaders.length; i++) {
      gridBC.gridx = 0;
      gridBC.gridy = i;
        //if (_rowHeaders[i]._n != -1) {
      gridBC.ipady = getIpady(i);
        //} else {
         // gridBC.ipady = getIpady(i);
        //}
      jLabel = new JLabel(_rowHeaders[i]._str);

      jLabel.setVerticalAlignment(JLabel.TOP);
      jLabel.setMinimumSize(new Dimension(ROW_HEADER_WIDTH, getIpady(i)));
      jLabel.setPreferredSize(new Dimension(ROW_HEADER_WIDTH, getIpady(i)));
      jLabel.setBorder(BorderFactory.createEtchedBorder());
      gridBL.setConstraints(jLabel, gridBC);
      jPanel.add(jLabel);
    }
    jPanel.setBorder(BorderFactory.createEtchedBorder());
    return jPanel;
  }

//-------------------------------------------
  protected JPanel createViewPort(){
    GridBagLayout gridBL = new GridBagLayout();
    GridBagConstraints gridBC = new GridBagConstraints();
    JPanel panel =  new JPanel(gridBL);
    panel.setBackground(SystemColor.window);
    gridBC.fill = GridBagConstraints.BOTH;

    PeriodPanel  periodPanel = null;
    JLabel jLabel;
    //int count = 1;
    for (int i = 0; i < _toDisplay.length; i++ ) {
      for (int j= 0; j < _toDisplay[0].length ; j++) {
        gridBC.gridx = i;
        gridBC.gridy = j;
        gridBC.ipadx = PERIOD_WIDTH ;
        //gridBC.ipady =  getIpady(i,j);
        if ( _toDisplay[i][j].getPeriodKey()!= "" &&  _toDisplay[i][j].getPeriodType()) {
          Period period = _tts.getCurrentCycle().getPeriodByPeriodKey(_toDisplay[i][j].getPeriodKey());
          periodPanel = createPeriodPanel(getPeriodNumber(_toDisplay[i][j].getPeriodKey()), _toDisplay[i][j].getPeriodKey());
          periodPanel.addMouseListener(_mouseListener);
          periodPanel.createPanel(period);
          //count++;
          gridBC.ipady =  getIpady(j);
        }
        else {
          periodPanel = createEmptyPeriodPanel();
          gridBC.ipady =  getIpady(j);
        }
        //gridBC.ipadx = PERIOD_WIDTH ;

        periodPanel.setMinimumSize(new Dimension(PERIOD_WIDTH, getIpady(j)));
        periodPanel.setPreferredSize(new Dimension(PERIOD_WIDTH,  getIpady(j)));
        periodPanel.setBorder(BorderFactory.createEtchedBorder());

        gridBL.setConstraints(periodPanel, gridBC);
        panel.add(periodPanel, gridBC);

      } // end for j
    }//end for i
    return panel;
  }
  public void manageActions(){
    //JPanel ttPanel= (JPanel)this.getViewport().getComponent(0);
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
         /*System.out.println("Un clic sur la periode: "+ perpanel.getPanelRefNo()+" Ref: " +
                                                    perpanel.getPeriodRef()[0] +"." +
                                                    perpanel.getPeriodRef()[1]+"." +
                                                    perpanel.getPeriodRef()[2]);//debug

         System.out.println("Un clic sur la periode: "+ perpanel.getPanelRefNo()+" Contains: "
                            +_tts.getCurrentCycle().getPeriodByIndex(
                                                    perpanel.getPeriodRef()[0],
                                                    perpanel.getPeriodRef()[1],
                                                    perpanel.getPeriodRef()[2]).toString());//debug*/
        }
      }
    };
  }

  private int getPeriodNumber(String str){
    long dKey = Long.parseLong(DXToolsMethods.getToken(str,".",0));
    long sKey = Long.parseLong(DXToolsMethods.getToken(str,".",1));
    long pKey = Long.parseLong(DXToolsMethods.getToken(str,".",2));
    int count = 0;
    Day day = _tts.getCurrentCycle().getDayByRefNo((int)dKey);
    for(int i = 0; i < sKey-1; i++) {
      count += day.getSequence(i).getSetOfPeriods().size();
    }
    return _tts.getCurrentCycle().getMaxNumberOfPeriodsADay() * ((int)dKey- 1)+ count + (int)pKey;

  }
} /*  end TTPane */