package dInterface.dTimeTable;

/**
 *
 * Title: DetailedTTPanel $Revision: 1.11 $  $Date: 2003-10-07 14:33:22 $
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

import java.awt.Dimension;
import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
//import java.awt.GridBagLayout;
//import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.util.*;
import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.JViewport;
import javax.swing.JSplitPane;
import javax.swing.border.*;
import java.awt.event.*;
import javax.swing.BoxLayout;

import dInterface.DToolBar;

import dInternal.DModel;
import dInternal.dData.Resource;
import dInternal.dTimeTable.*;


public class DetailedTTPanel extends TTPanel {
  final int VERTICAL_ADJUST = 115;
  final int HORIZONTAL_ADJUST = 8;
  int []_lines; //={10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130,140};
  int _totalHeight;
  private MouseListener _mouseListener;

  //private JScrollPane _jScrollPaneOne;
  private JScrollPane _jScrollPaneTwo;

  public DetailedTTPanel(TTStructure tts, DToolBar toolBar, boolean vertical, Dimension d) {
    super(tts, toolBar);
    //_jSplitPane = new JSplitPane();
    _jScrollPaneOne = new JScrollPane();
    _jScrollPaneTwo = new JScrollPane();
    calculateSizes();
    if(_tts!=null){
      initTTPaneOne();
      initTTPaneTwo();
    }
    System.out.println("H " + d.height + "  W " + d.width);

    if (vertical) {
      _jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,_jScrollPaneOne,_jScrollPaneTwo);
      _jSplitPane.setDividerLocation((d.height - VERTICAL_ADJUST)/2);
    } else {
       _jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,_jScrollPaneOne,_jScrollPaneTwo);
       _jSplitPane.setDividerLocation((d.width - HORIZONTAL_ADJUST)/2);
    }
    _jSplitPane.setOneTouchExpandable(true);
  } // end  SimpleTTPanel

  private void calculateSizes() {
    //Taille avec trous
    _lines = new int [_tts.getCurrentCycle().getMaxNumberOfPeriodsADay()+
           _tts.getCurrentCycle().getCurrentDay().getSetOfSequences().size()-1];
    for(int i = 0; i < _lines.length; i++) {
      _lines[i] = -1;
    }

    int [] a = {};
    int pos =0;
    for (int i = 0; i < _tts.getCurrentCycle().getSetOfDays().size() ; i++ ) {
      Day day= _tts.getCurrentCycle().getDayByRefNo(i+1);
      for(int j = 0; j < day.getSetOfSequences().size(); j ++) {
        Sequence sequence= _tts.getCurrentCycle().getSequence(day,j+1);
        for(int k = 0; k < sequence.getSetOfPeriods().size(); k ++) {
          Period period = _tts.getCurrentCycle().getPeriod(sequence,k+1);
         /* if (_tts.getCurrentCycle().isPeriodContiguous(i+1,j+1,k+1,2,a))
            _lines[i] =;*/
          pos=_tts.getCurrentCycle().getPeriodPositionInDay(i+1,j+1,k+1);
          if (_lines[pos + j -1] < (period.getEventsInPeriod().size() + 2))
            _lines[pos + j -1] = period.getEventsInPeriod().size() + 2;

        } //for k
        //_lines[pos] = 3;
      } //for j
    } //for i
    _totalHeight = 0;
    for(int i = 0; i < _lines.length; i++) {
      _totalHeight += _lines[i];
    }
  }

  //-------------------------------------------
  private void initTTPaneOne() {
    // calculating totalWidth;
    _periodLenght = _tts.getPeriodLenght();
    Cycle cycle =_tts.getCurrentCycle();
    int totalWidth = cycle.getNumberOfDays() * PERIOD_WIDTH;

    // calculating totalHeight;
    Period lastPeriod = _tts.getCurrentCycle().getLastPeriod();
    _lastHour = lastPeriod.getEndHour(_periodLenght)[0];
    if(lastPeriod.getEndHour(_periodLenght)[1] != 0)
      _lastHour++;
    //System.out.println("last Hour: "+ LASTHOUR+":"+lastPeriod.getEndHour(_periodLenght)[1]);//debug
    int totalHeight = _totalHeight * LINE_HEIGHT;

    //_jScrollPaneOne.setMinimumSize(new Dimension(totalWidth, totalHeight));
    //putting panels in the screen
    _jScrollPaneOne.setColumnHeaderView(createColumnHeader());
    _jScrollPaneOne.setRowHeaderView(createRowHeader(totalHeight));
    _jScrollPaneOne.setViewportView(createViewPort(totalWidth, totalHeight));
    //  Point point = _jScrollPaneOne.getViewport().getViewPosition();
    _jScrollPaneOne.getViewport().setViewPosition(_jScrollPaneOne.getViewport().getViewPosition());
    manageActions();
  }
  //-------------------------------------------
  private void initTTPaneTwo() {
    // calculating totalWidth;
    _periodLenght = _tts.getPeriodLenght();
    Cycle cycle =_tts.getCurrentCycle();
    int totalWidth = cycle.getNumberOfDays() * PERIOD_WIDTH;

    // calculating totalHeight;
    Period lastPeriod = _tts.getCurrentCycle().getLastPeriod();
    _lastHour = lastPeriod.getEndHour(_periodLenght)[0];
    if(lastPeriod.getEndHour(_periodLenght)[1] != 0)
      _lastHour++;
    //System.out.println("last Hour: "+ LASTHOUR+":"+lastPeriod.getEndHour(_periodLenght)[1]);//debug
     int totalHeight = _totalHeight * LINE_HEIGHT;

    //_jScrollPaneTwo.setMinimumSize(new Dimension(totalWidth, totalHeight));
    //putting panels in the screen
    _jScrollPaneTwo.setColumnHeaderView(createColumnHeader());
    _jScrollPaneTwo.setRowHeaderView(createRowHeader(totalHeight));
    _jScrollPaneTwo.setViewportView(createViewPort(totalWidth, totalHeight));
    //  Point point = _jScrollPaneTwo.getViewport().getViewPosition();
    _jScrollPaneTwo.getViewport().setViewPosition(_jScrollPaneTwo.getViewport().getViewPosition());
    manageActions();
  }
  //-------------------------------------------
  /**
   *
   * */
  private void manageActions(){
    //JPanel ttPanel= (JPanel)this.getViewport().getComponent(0);
  /*
    * Mouse listener for this Panel
   */
    _mouseListener = new MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
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
    };
  }
//-------------------------------------------
  public void updateTTPanel(TTStructure ttp){
    initTTPaneOne();
    initTTPaneTwo();
  }
//-------------------------------------------
  private JPanel createColumnHeader() {
    JPanel panel = new JPanel();

    panel.setLayout(new GridLayout(1,0));//BoxLayout(panel, BoxLayout.X_AXIS));//1, 0));
    Cycle cycle = _tts.getCurrentCycle();
    //panel.setMinimumSize(new Dimension(PERIOD_WIDTH * cycle.getSetOfDays().size(),
                                         //HEADER_HEIGHT));
    panel.setPreferredSize(new Dimension(PERIOD_WIDTH * cycle.getSetOfDays().size(),
                                         HEADER_HEIGHT));
    //panel.setMaximumSize(new Dimension(PERIOD_WIDTH * cycle.getSetOfDays().size(),
                                         //HEADER_HEIGHT));
    panel.setBorder(BorderFactory.createEtchedBorder());
    for (int i = 0; i < cycle.getSetOfDays().size() ; i++){
      Resource day = cycle.getSetOfDays().getResourceAt(i);
       JLabel l = new JLabel("J " + (i + 1) + " : "+ day.getID(), JLabel.CENTER);
       //l.setMinimumSize(new Dimension(PERIOD_WIDTH,
                                         //HEADER_HEIGHT));
       //l.setPreferredSize(new Dimension(PERIOD_WIDTH,
                                        // HEADER_HEIGHT));
       //l.setMaximumSize(new Dimension(PERIOD_WIDTH,
                                        // HEADER_HEIGHT));
      panel.add(l);//new JLabel("J " + (i + 1) + " : "+ day.getID(), JLabel.CENTER));
    }

    return panel;
  }
//-------------------------------------------
  private JPanel createRowHeader(int height) {
    JPanel panel = new JPanel();
    GridBagLayout gridBL = new GridBagLayout();
    GridBagConstraints gridBC = new GridBagConstraints();
    panel.setLayout(gridBL);//(new GridLayout(0,1));
    //panel.setPreferredSize(new Dimension(ROW_WIDTH,  _totalHeight * LINE_HEIGHT));
    System.out.println("W & H" + ROW_WIDTH + " " + _totalHeight * LINE_HEIGHT);
    //c.gridwidth = 1;
    //c.gridheight = _totalHeight * LINE_HEIGHT;
    gridBC.fill = GridBagConstraints.HORIZONTAL;
    //c.fill = GridBagConstraints.NONE;//.VERTICAL;
    //c.fill = GridBagConstraints.VERTICAL;

    Cycle cycle =_tts.getCurrentCycle();
    //Day day = _tts.getCurrentCycle().getCurrentDay();
    //int numbOfSequences = day.getSetOfSequences().size();
    JLabel label;
    Period firstPer = _tts.getCurrentCycle().getFirstPeriod();
    //int j = 0;
    String [] hour = _tts.getCurrentCycle().getHourOfPeriodsADay();
    //int offset = 0;
    int j = 0;
    for (int i = 0; i < _lines.length ; i++) {

      //label.setSize(new Dimension(ROW_WIDTH, _lines[i] * LINE_HEIGHT));
      gridBC.gridx = 0;
      gridBC.gridy = i;
      System.out.println("gridx & gridy" + gridBC.gridx + " " + gridBC.gridy);
      //System.out.println("TTPanel Row header viewlabel.getText()" + label.getText());//Debug
      //
      //c.gridwidth = 1;
      if (_lines[i] != -1) {
        gridBC.ipady =  _lines[i] * LINE_HEIGHT;
        //offset += _lines[i] * LINE_HEIGHT;
        label = new JLabel(hour[j] ) ; // + ":00");
        label.setVerticalAlignment(JLabel.TOP);
        //label.setSize(new Dimension(ROW_WIDTH, _lines[i] * LINE_HEIGHT));
        //c.ipady = offset;
        j++;
      } else {
        gridBC.ipady = LINE_HEIGHT;
        //offset += LINE_HEIGHT;
        label = new JLabel(" ") ; // + ":00");
        //label.setSize(new Dimension(ROW_WIDTH, LINE_HEIGHT));
      }
      //System.out.println("gridwidth & gridheight" + c.gridwidth + " " + c.gridheight);

      //c.ipady = _lines[i] * LINE_HEIGHT;
      //System.out.println("ipadx & ipady" + c.ipadx + " " + c.ipady);

      //_lines[i] * LINE_HEIGHT; //(period.getBeginHour()[0] -
           //_tts.getCurrentCycle().getFirstPeriod().getBeginHour()[0]);//period.getEndHour(_periodLenght)[0];
/*
if ( period.getEndHour(_periodLenght)[1] == 0 ){
  c.gridheight =_lines[pos-1] * LINE_HEIGHT;// period.getEndHour(_periodLenght)[0]- period.getBeginHour()[0];
  c.insets = new Insets( period.getBeginHour()[1], 0, 0, 0 );
} else {
  c.gridheight =_lines[pos-1] * LINE_HEIGHT;// period.getEndHour(_periodLenght)[0] + 1 - period.getBeginHour()[0];
  c.insets = new Insets( period.getBeginHour()[1], 0, _lines[pos-1] * LINE_HEIGHT - period.getEndHour(_periodLenght)[1]), 0 );
}*/
      label.setBorder(BorderFactory.createEtchedBorder());
      gridBL.setConstraints(label, gridBC);

      panel.add(label);



    }
    return panel;
  }
//-------------------------------------------
  private JPanel createViewPort(int width, int height) {
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
    nbPerADay = _lastHour - _tts.getCurrentCycle().getFirstPeriod().getBeginHour()[0];
    gridbag.rowWeights = new double [nbPerADay];
    gridbag.rowHeights = new int [nbPerADay];
    for (int i = 0; i < nbPerADay; i++) {
      gridbag.rowWeights[i] = 1;
      if (_lines[i] != -1 )
        gridbag.rowHeights[i] = _lines[i] *LINE_HEIGHT;
      else
        gridbag.rowHeights[i] = LINE_HEIGHT;
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
          periodPanel = new DetailedPeriodPanel(count,i,j,k);
          periodPanel.addMouseListener(_mouseListener);
          int pos=_tts.getCurrentCycle().getPeriodPositionInDay(i+1,j+1,k+1);
          periodPanel.createPanel(period, PERIOD_WIDTH, _lines[pos-1] * LINE_HEIGHT);
          count++;
          c = new GridBagConstraints();
          c.fill = GridBagConstraints.BOTH;
          c.gridx = i;
          c.gridy = _lines[pos-1] * LINE_HEIGHT; //(period.getBeginHour()[0] -
                     //_tts.getCurrentCycle().getFirstPeriod().getBeginHour()[0]);//period.getEndHour(_periodLenght)[0];

          if ( period.getEndHour(_periodLenght)[1] == 0 ){
            c.gridheight =_lines[pos-1] * LINE_HEIGHT;// period.getEndHour(_periodLenght)[0]- period.getBeginHour()[0];
            c.insets = new Insets( period.getBeginHour()[1], 0, 0, 0 );
          } else {
            c.gridheight =_lines[pos-1] * LINE_HEIGHT;// period.getEndHour(_periodLenght)[0] + 1 - period.getBeginHour()[0];
            c.insets = new Insets( period.getBeginHour()[1], 0, _lines[pos-1] * LINE_HEIGHT /*- period.getEndHour(_periodLenght)[1])*/, 0 );
          }
          gridbag.setConstraints(periodPanel, c);
          panel.add(periodPanel, c);
        }//end for k
      } // end for j
    }//end for i
    return panel;
  }
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
//-------------------------------------------
  public JViewport getViewport() {
    return _jScrollPaneOne.getViewport();
  }
//-------------------------------------------
  public JScrollPane getJScrollPane() {
    return _jScrollPaneOne;
  }
  //-------------------------------------------
  public Component getPanel() {
    return _jSplitPane;
  }
  //-------------------------------------------

  public JSplitPane getJSplitPane() {
    return _jSplitPane;
  }


}


