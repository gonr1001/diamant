package dInterface.dTimeTable;

/**
 *
 * Title: TTPane $Revision: 1.2 $  $Date: 2003-10-17 19:04:46 $
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
 * @version $Revision: 1.2 $
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
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JViewport;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
//import java.awt.GridLayout;
import java.awt.GridBagLayout;
//import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.util.*;
import java.awt.*;
import javax.swing.BorderFactory;

import dInternal.DModel;
import dInternal.dData.Resource;

import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Day;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.Sequence;
import dInternal.dTimeTable.TTStructure;
import dInternal.dUtil.DisplayAttributs;

import dInterface.DToolBar;



public abstract class TTPane {
  private final static boolean SIMPLE = true;

  protected final int PERIOD_WIDTH =  100;  // for the screen
  //protected final int PERIOD_HEIGHT = 400;  // for the screen
  protected final int LINE_HEIGHT = 50;
  //protected final int SMALL_PERIOD_HEIGHT = 40; //LINE_HEIGHT * 2;  // for the screen
  protected final int HEADER_HEIGHT = 20 ;
  protected final int ROW_HEADER =  35;

  protected class RowRecord {
    int _n;
    String _str;
    RowRecord(int n, String str){
      _n = n; _str = str;
    }
  }

  protected TTStructure _tts;
  protected DToolBar _toolBar;
  protected JScrollPane _jScrollPaneOne;
  protected JScrollPane _jScrollPaneTwo;
  protected JSplitPane _jSplitPane;

  protected int _periodLenght;
  protected int _lastHour;

  protected RowRecord [] _rowHeaders;
  protected DisplayAttributs [][] _toDisplay;

  protected boolean _standAlone;
  /**
   * constructor
   * @param tts
   * @param toolBar
   * @param standAlone if true it start a independent application
   */
  protected TTPane(TTStructure tts, DToolBar toolBar, boolean standAlone) {
    _tts = tts;
    _toolBar = toolBar;
    _standAlone = standAlone;
    _jScrollPaneOne = null;
    _jScrollPaneTwo = null;
    _jSplitPane = null;
  }
  //-------------------------------------------
  public abstract JComponent getPane();
  //-------------------------------------------
  public abstract void updateTTPane(TTStructure ttp);
  //-------------------------------------------
  public abstract  PeriodPanel getPeriodPanel(int i);

  public abstract int  getIpady(int i);

  //-------------------------------------------
  public abstract void manageActions();
  //-------------------------------------------
  public JViewport getViewport() {
    return _jScrollPaneOne.getViewport();
  }
  //-------------------------------------------
  protected void initTTPane(JScrollPane jScrollPane) {
    jScrollPane.setColumnHeaderView(createColumnHeader());
    jScrollPane.setRowHeaderView(createRowHeader());
    jScrollPane.setViewportView(createViewPort());
    //  Point point = _jScrollPaneOne.getViewport().getViewPosition();
    jScrollPane.getViewport().setViewPosition(jScrollPane.getViewport().getViewPosition());
    //manageActions();
  }
  //-------------------------------------------
  protected void findRowHeaders() {
    if (_standAlone) {
      _toDisplay = new DisplayAttributs[5][3];
      _rowHeaders = new RowRecord [_toDisplay[0].length];
      for(int i = 0; i < _rowHeaders.length; i++) {
        _rowHeaders[i] = new RowRecord(2/*_toDisplay[0][j].n*/, " ");
      }
    } else {
      _toDisplay = _tts.getCurrentCycle().getAttributsToDisplay(60);
      //showMe();
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
    } // end if (_standAlone) else
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
      if (_standAlone) {
        jLabel = new JLabel("J " + (i + 1) + " < ", JLabel.CENTER);
      } else {
        cycle = _tts.getCurrentCycle();
        Resource day = cycle.getSetOfDays().getResourceAt(i);
        jLabel = new JLabel("J " + (i + 1) + " : " + day.getID(), JLabel.CENTER);
      }
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

      if (_standAlone) {
        jLabel = new JLabel("10" + ":00");
        gridBC.ipady = LINE_HEIGHT;
      } else {
        if (_rowHeaders[i]._n != -1) {
          gridBC.ipady = getIpady(i);
        } else {
          gridBC.ipady = LINE_HEIGHT;
        }
        jLabel = new JLabel(_rowHeaders[i]._str);
      }
      jLabel.setVerticalAlignment(JLabel.TOP);
      jLabel.setMinimumSize(new Dimension(ROW_HEADER, LINE_HEIGHT));
      jLabel.setPreferredSize(new Dimension(ROW_HEADER, LINE_HEIGHT));
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
      //GridBagConstraints c = null;
      //nbSeq = _tts.getCurrentCycle().getMaxNumberOfSeqs();
      //int count = 1;
      //gridBC.gridheight =_lines.length ;
      //gridBC.gridwidth = nbDays;
      for (int i = 0; i < _toDisplay.length; i++ ) {
        for (int j= 0; j < _toDisplay[0].length ; j++) {
          //for(int j = 0; j < nbSeq; j ++) {
          // Sequence sequence= _tts.getCurrentCycle().getSequence(day,j+1);
          //for(int k = 0; k < sequence.getSetOfPeriods().size(); k ++) {
          //Period period= _tts.getCurrentCycle().getPeriod(sequence,k+1);
          JLabel jLabel;// = new JLabel( i + " " + m);
          /*periodPanel = new DetailedPeriodPanel(count,i,j,k);
          periodPanel.addMouseListener(_mouseListener);
          int pos = _tts.getCurrentCycle().getPeriodPositionInDay(i+1,j+1,k+1);
          periodPanel.createPanel(period); //, PERIOD_WIDTH, _lines[pos-1] * LINE_HEIGHT);*/

          //c = new GridBagConstraints();
          //c.fill = GridBagConstraints.BOTH;
          gridBC.gridx = i;
          gridBC.gridy = j;
          gridBC.ipadx = PERIOD_WIDTH ;
          //if (_lines[m] != -1) {
          gridBC.ipady =  getIpady(j);
          if(_standAlone) {
            gridBC.ipady =  LINE_HEIGHT;
            //jLabel = new JLabel( i + " " + j);
          } else {
            //jLabel = new JLabel( i + " " + j);
            if ( _toDisplay[i][j].getPeriodKey()!= "" &&  _toDisplay[i][j].getPeriodType()) {
              Period period = _tts.getCurrentCycle().getPeriodByPeriodKey(_toDisplay[i][j].getPeriodKey());

          /*periodPanel = new DetailedPeriodPanel(count,i,j,k);
          periodPanel.addMouseListener(_mouseListener);
          int pos = _tts.getCurrentCycle().getPeriodPositionInDay(i+1,j+1,k+1);
          periodPanel.createPanel(period); //, */
              periodPanel = new SimplePeriodPanel(_toDisplay[i][j].getPeriodKey());
             // periodPanel.addMouseListener(_mouseListener);
              periodPanel.createPanel(period);
            }
            else
             periodPanel = new SimplePeriodPanel();
            //periodPanel.addMouseListener(_mouseListener);

             //periodPanel.createPanel(period); //, PERIOD_WIDTH, _lines[pos-1] * LINE_HEIGHT);
          }
          periodPanel.setMinimumSize(new Dimension(PERIOD_WIDTH, getIpady(j)));
          periodPanel.setPreferredSize(new Dimension(PERIOD_WIDTH,  getIpady(j)));
          periodPanel.setBorder(BorderFactory.createEtchedBorder());
          //label = new JLabel(hour[j] ) ; // + ":00");
          //label.setVerticalAlignment(JLabel.TOP);

         /* } else {
            gridBC.ipady = LINE_HEIGHT;
            jLabel = new JLabel(" ");
          //label = new JLabel(" ") ; // + ":00");
          }*/
        /*  c.gridy = _lines[pos-1] * LINE_HEIGHT; //(period.getBeginHour()[0] -
          //_tts.getCurrentCycle().getFirstPeriod().getBeginHour()[0]);//period.getEndHour(_periodLenght)[0];

          if ( period.getEndHour(_periodLenght)[1] == 0 ){
            c.gridheight =_lines[pos-1] * LINE_HEIGHT;// period.getEndHour(_periodLenght)[0]- period.getBeginHour()[0];
            c.insets = new Insets( period.getBeginHour()[1], 0, 0, 0 );
          } else {
            c.gridheight =_lines[pos-1] * LINE_HEIGHT;// period.getEndHour(_periodLenght)[0] + 1 - period.getBeginHour()[0];*/
          // c.insets = new Insets( period.getBeginHour()[1], 0, _lines[pos-1] * LINE_HEIGHT /*- period.getEndHour(_periodLenght)[1])*/, 0 );
          //}
          //gridBC.insets = new Insets(0,8,0,8);

          gridBL.setConstraints(periodPanel, gridBC);
          panel.add(periodPanel, gridBC);
          //gridBL.setConstraints(periodPanel, gridBC);
          //panel.add(periodPanel, gridBC);
          //}//end for k
          } // end for j
        }//end for i
        return panel;
      }
      private int [][] getAttributesToDisplay() {
        //int[][] a = new int [1][1];
        return  new int [5][14];
      }
      //-------------------------------------------
      public static  void main(String [] args) {
        JFrame jFrame = new JFrame("Test Panels");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(400,300);
        jFrame.setLocation(200,200);
        TTPane dTTp;
        if (SIMPLE)
          dTTp = new SimpleTTPane (null, null, true, jFrame.getSize(), true);
        else
          dTTp = new DetailedTTPane (null, null, true, jFrame.getSize(), true);

        jFrame.setContentPane(dTTp.getPane());
        jFrame.setVisible(true);
      }// end main
} /*  end TTPane */
