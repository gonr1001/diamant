package dInterface.dTimeTable;

/**
 *
 * Title: DetailedTTPane $Revision: 1.3 $  $Date: 2003-10-20 14:09:47 $
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
 * @version $Revision: 1.3 $
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
import javax.swing.JComponent;
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


public class DetailedTTPane extends TTPane {
  //int []_lines;
  //private MouseListener _mouseListener;


  public DetailedTTPane(TTStructure tts, DToolBar toolBar, boolean vertical, Dimension d) {
    super(tts, toolBar);
    initDetailedTTPane(vertical, d);
  } // end  DetailedTTPane

  public JComponent getPane(){
    return _jSplitPane;
  }

  private void initDetailedTTPane(boolean vertical, Dimension d) {
  _jScrollPaneOne = new JScrollPane();
   _jScrollPaneTwo = new JScrollPane();
   findRowHeaders();
   if(_tts!=null){
     initTTPane(_jScrollPaneOne);
     initTTPane(_jScrollPaneTwo);
   }
   System.out.println("H " + d.height + "  W " + d.width);

   if (vertical) {
     _jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,_jScrollPaneOne,_jScrollPaneTwo);
     _jSplitPane.setDividerLocation(d.height/2);
   } else {
      _jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,_jScrollPaneOne,_jScrollPaneTwo);
      _jSplitPane.setDividerLocation(d.width /2);
   }
    _jSplitPane.setOneTouchExpandable(true);
  }  // end initDetailedTTPane





  //-------------------------------------------
  public int getIpady(int i) {
    return LINE_HEIGHT * _rowHeaders[i]._n;
  }
  //-------------------------------------------
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
    initTTPane(_jScrollPaneTwo);
  }

  public PeriodPanel createPeriodPanel(int refNo, String str) {
   return new DetailedPeriodPanel(refNo, str);
  }
/*
//-------------------------------------------
  public JPanel createViewPort(){//int x, int y){ //int width, int height) {
    GridBagLayout gridBL = new GridBagLayout();
    GridBagConstraints gridBC = new GridBagConstraints();
    JPanel panel =  new JPanel(gridBL);
    panel.setBackground(SystemColor.window);
    gridBC.fill = GridBagConstraints.NONE;
    //panel.setMinimumSize(new Dimension(width/10, height/10));

    _jScrollPaneOne.setPreferredSize(new Dimension(panel.getPreferredSize().width +50,
        panel.getPreferredSize().height + 50) );

    Cycle cycle =_tts.getCurrentCycle();
    int nbDays,nbSeq,nbPerADay;
    nbDays = cycle.getSetOfDays().size();
    //System.out.println("nb of Days: " + nbDays);//Debug
    ///gridbag.columnWeights = new double [nbDays];
    //gridbag.columnWidths = new int [nbDays];
    //for (int i = 0; i < nbDays; i++) {
   //   gridbag.columnWeights[i] = 1;
    //  gridbag.columnWidths[i] = PERIOD_WIDTH;
    //}
    nbPerADay = _lastHour - _tts.getCurrentCycle().getFirstPeriod().getBeginHour()[0];
    //gridbag.rowWeights = new double [nbPerADay];
    //gridbag.rowHeights = new int [nbPerADay];
    /*for (int i = 0; i < nbPerADay; i++) {
      gridbag.rowWeights[i] = 1;
      if (_lines[i] != -1 )
        gridbag.rowHeights[i] = _lines[i] *LINE_HEIGHT;
      else
        gridbag.rowHeights[i] = LINE_HEIGHT;
    }
    PeriodPanel periodPanel = null;
    //GridBagConstraints c = null;
    nbSeq = _tts.getCurrentCycle().getMaxNumberOfSeqs();
    //int count = 1;
    //gridBC.gridheight =_lines.length ;
  //gridBC.gridwidth = nbDays;
    for (int i = 0; i < nbDays ; i++ ) {
      Day day= _tts.getCurrentCycle().getDayByRefNo(i+1);
      for (int m= 0; m < _lines.length; m++) {
      //for(int j = 0; j < nbSeq; j ++) {
       // Sequence sequence= _tts.getCurrentCycle().getSequence(day,j+1);
        //for(int k = 0; k < sequence.getSetOfPeriods().size(); k ++) {
          //Period period= _tts.getCurrentCycle().getPeriod(sequence,k+1);
          JLabel jLabel;// = new JLabel( i + " " + m);
          /*periodPanel = new DetailedPeriodPanel(count,i,j,k);
          periodPanel.addMouseListener(_mouseListener);
          int pos = _tts.getCurrentCycle().getPeriodPositionInDay(i+1,j+1,k+1);
          periodPanel.createPanel(period); //, PERIOD_WIDTH, _lines[pos-1] * LINE_HEIGHT);

          //c = new GridBagConstraints();
          //c.fill = GridBagConstraints.BOTH;
          gridBC.gridx = i;
          gridBC.gridy = m;
          gridBC.ipadx = PERIOD_WIDTH ;
          if (_lines[m] != -1) {
            gridBC.ipady =  (_lines[m]  + 2) * LINE_HEIGHT;
            jLabel = new JLabel( i + " " + m);
           // jLabel.setMinimumSize(new Dimension(PERIOD_WIDTH-40, (_lines[m]  + 2) * LINE_HEIGHT));
          jLabel.setPreferredSize(new Dimension(PERIOD_WIDTH-40, (_lines[m]  + 2) * LINE_HEIGHT));
                      jLabel.setBorder(BorderFactory.createEtchedBorder());
  //label = new JLabel(hour[j] ) ; // + ":00");
  //label.setVerticalAlignment(JLabel.TOP);

          } else {
            gridBC.ipady = LINE_HEIGHT;
            jLabel = new JLabel(" ");
  //label = new JLabel(" ") ; // + ":00");
          }
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
/*
          gridBL.setConstraints(jLabel, gridBC);
          panel.add(jLabel, gridBC);
          //gridBL.setConstraints(periodPanel, gridBC);
          //panel.add(periodPanel, gridBC);
        //}//end for k
      } // end for j
    }//end for i
    return panel;
  }*/
//-------------------------------------------
/**
 * @param int the period panel reference
 * */
 public PeriodPanel getPeriodPanel(int ppRef){
    /*JPanel thePanel= (JPanel)_jScrollPaneOne.getViewport().getComponent(0);
    for (int i=0; i< thePanel.getComponentCount(); i++){
     JPanel ppanel= (JPanel)thePanel.getComponent(i);
      if(ppanel.getPanelRefNo()==ppRef)
       return ppanel;
    }*/
    return null;
  }
/*//-------------------------------------------
  public JViewport getViewport() {
    return _jScrollPaneOne.getViewport();
  }


*/

} // end DetailedTTPane


