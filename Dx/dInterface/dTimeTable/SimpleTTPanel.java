package dInterface.dTimeTable;

/**
 *
 * Title: SimpleTTPanel $Revision: 1.4 $  $Date: 2003-09-29 20:36:32 $
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



public class SimpleTTPanel extends TTPanel {

  //private int MINHEIGHT = 60;
  private int HHEIGHT =  24; // timeTable.nbDays * MINWIDTH;
  private int VWIDTH =  36; // timeTable.nbDays * MINWIDTH;
  private int UWIDTH =  100; // timeTable.nbDays * MINWIDTH;
  private int UHEIGHT =  60;// (timeTable.getLatest() - timeTable.getEarliest()) * MINHEIGHT;
  private int MinWidth=80;
  private int MinHeight=80;
  private int LASTHOUR=8;

  private int _periodLenght;
  private MouseListener _mouseListener;
  private PeriodPanel _lastActivPpanel=null;
  private JPanel _jSplitPane;

  private JScrollPane _jScrollPaneOne;
   private JScrollPane _jScrollPaneTwo;

  public SimpleTTPanel(DModel dm) {
    super();
    _dm = dm;
    _jScrollPaneOne = new JScrollPane();
    _jScrollPaneTwo = new JScrollPane();
    //_jScrollPaneTwo= _jScrollPaneOne;//debug
    if(_dm.getTTStructure()!=null){
      initTTPaneOne();
      initTTPaneTwo();
    }
    //_jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,_jScrollPane,_jScrollPane);

    _jSplitPane = new JPanel();//JSplitPane.VERTICAL_SPLIT,true);//,_jScrollPane,new JLabel("B"));

    _jSplitPane.add(_jScrollPaneOne);

    //_jSplitPane.setTopComponent(_jScrollPaneOne);
    //_jSplitPane.setBottomComponent(null);//_jScrollPaneTwo);//new JLabel("B")); //(_jScrollPane);
   //  _jSplitPane.setOneTouchExpandable(true);
    //   _jSplitPane.setDividerLocation(0);
    //Dimension minSize= new Dimension (100, 100);
   // _jScrollPaneOne.setMinimumSize(minSize);
  }

  private void initTTPaneOne() {
    _periodLenght= _dm.getTTStructure().getPeriodLenght();
    Cycle cycle =_dm.getTTStructure().getCurrentCycle();
    MinWidth = cycle.getNumberOfDays() * UWIDTH;
    Period lastPeriod = _dm.getTTStructure().getCurrentCycle().getLastPeriod();
    LASTHOUR=lastPeriod.getEndHour(_periodLenght)[0];
    if(lastPeriod.getEndHour(_periodLenght)[1]!=0)
      LASTHOUR++;
    //System.out.println("last Hour: "+ LASTHOUR+":"+lastPeriod.getEndHour(_periodLenght)[1]);//debug
    MinHeight = (LASTHOUR-_dm.getTTStructure().getCurrentCycle().getFirstPeriod().getBeginHour()[0]) * UHEIGHT;

    Point point= new Point(0,0);
    point = _jScrollPaneOne.getViewport().getViewPosition();
    _jScrollPaneOne.setColumnHeaderView(createColumnHeader());
    _jScrollPaneOne.setRowHeaderView(createRowHeader());
    _jScrollPaneOne.setViewportView(createViewPort());
    _jScrollPaneOne.getViewport().setViewPosition(point);
    manageActions();

  }
  private void initTTPaneTwo() {
    _periodLenght= _dm.getTTStructure().getPeriodLenght();
    Cycle cycle =_dm.getTTStructure().getCurrentCycle();
    MinWidth = cycle.getNumberOfDays() * UWIDTH;
    Period lastPeriod = _dm.getTTStructure().getCurrentCycle().getLastPeriod();
    LASTHOUR=lastPeriod.getEndHour(_periodLenght)[0];
    if(lastPeriod.getEndHour(_periodLenght)[1]!=0)
      LASTHOUR++;
    //System.out.println("last Hour: "+ LASTHOUR+":"+lastPeriod.getEndHour(_periodLenght)[1]);//debug
    MinHeight = (LASTHOUR-_dm.getTTStructure().getCurrentCycle().getFirstPeriod().getBeginHour()[0]) * UHEIGHT;

    Point point= new Point(0,0);
    point = _jScrollPaneTwo.getViewport().getViewPosition();
    _jScrollPaneTwo.setColumnHeaderView(createColumnHeader());
    _jScrollPaneTwo.setRowHeaderView(createRowHeader());
    _jScrollPaneTwo.setViewportView(createViewPort());
    _jScrollPaneTwo.getViewport().setViewPosition(point);
    manageActions();

  }
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
        if (e.getClickCount() == 1) {
          PeriodPanel perpanel= (PeriodPanel)e.getSource();
          if(_lastActivPpanel!=null)
            _lastActivPpanel.setPanelBackGroundColor(0);
          _dm.getDDocument().getDMediator().getDApplication().getToolBar().setComboBoxStatus(false);
          _dm.getDDocument().getDMediator().getDApplication().getToolBar().setPeriodSelector(Integer.toString(perpanel.getPanelRefNo()));
          //_dm.getDApplication().getToolBar().selectBar(1);
           perpanel.setPanelBackGroundColor(1);
           _dm.getDDocument().getDMediator().getDApplication().getToolBar().setComboBoxStatus(true);
          _lastActivPpanel=perpanel;
         System.out.println("Un clic sur la periode: "+ perpanel.getPanelRefNo()+" Contains: "
                            +_dm.getTTStructure().getCurrentCycle().getPeriodByIndex(
                            perpanel.getPeriodRef()[0],perpanel.getPeriodRef()[1],perpanel.getPeriodRef()[2]).toString());//debug
        }
      }
    };
  }

  public void updateTTPanel(TTStructure ttp){
    initTTPaneOne();
    initTTPaneTwo();
  }

  private JPanel createColumnHeader() {
    JPanel panel = new JPanel(new GridLayout(1, 0));
    Cycle cycle =_dm.getTTStructure().getCurrentCycle();
    for (int i = 0; i < cycle.getSetOfDays().size() ; i++){
      Resource day = cycle.getSetOfDays().getResourceAt(i);
      panel.add(new JLabel("J " + (i + 1) + " : "+ day.getID(), JLabel.CENTER));
    }
    panel.setPreferredSize(new Dimension(UWIDTH *cycle.getSetOfDays().size(), HHEIGHT));
    panel.setBorder(BorderFactory.createEtchedBorder());
    return panel;
  }

  private JPanel createRowHeader() {
    JPanel panel = new JPanel(new GridLayout(0,1));
    Cycle cycle =_dm.getTTStructure().getCurrentCycle();
    Day day = _dm.getTTStructure().getCurrentCycle().getCurrentDay();
    int numbOfSequences = day.getSetOfSequences().size();
    JLabel label, label1;
    Period firstPer= _dm.getTTStructure().getCurrentCycle().getFirstPeriod();
    for (int i = firstPer.getBeginHour()[0]; i < LASTHOUR; i++) {
      label = new JLabel(Integer.toString(i) + ":00");
      //System.out.println("TTPanel Row header viewlabel.getText()" + label.getText());//Debug
      label.setVerticalAlignment(JLabel.TOP);
      panel.setBorder(BorderFactory.createEtchedBorder());
      panel.add(label);
     }
     panel.setPreferredSize(new Dimension(35, MinHeight));
     return panel;
  }

  private JPanel createViewPort() {
    Cycle cycle =_dm.getTTStructure().getCurrentCycle();//.getCycle(_dm.getCurrentCycle());
    GridBagLayout gridbag =new GridBagLayout();
    JPanel panel =  new JPanel( gridbag );
    panel.setBackground(SystemColor.window);
    panel.setPreferredSize(new Dimension(MinWidth, MinHeight));
    _jScrollPaneOne.setPreferredSize(new Dimension(panel.getPreferredSize().width + 40,
                                   panel.getPreferredSize().height + 50));
    int nbDays,nbSeq,nbPerADay;
    nbDays = cycle.getSetOfDays().size();
    //System.out.println("nb of Days: " + nbDays);//Debug
    gridbag.columnWeights = new double [nbDays];
    gridbag.columnWidths = new int [nbDays];
    for (int i = 0; i < nbDays; i++) {
      gridbag.columnWeights[i] = 1;
      gridbag.columnWidths[i] = UWIDTH;
    }
    nbPerADay = LASTHOUR-_dm.getTTStructure().getCurrentCycle().getFirstPeriod().getBeginHour()[0];
    gridbag.rowWeights = new double [nbPerADay];
    gridbag.rowHeights = new int [nbPerADay];
    for (int i = 0; i < nbPerADay; i++) {
      gridbag.rowWeights[i] = 1;
      gridbag.rowHeights[i] = UHEIGHT;
    }
    PeriodPanel periodPanel = null;
    GridBagConstraints c = null;
    nbSeq = _dm.getTTStructure().getCurrentCycle().getMaxNumberOfSeqs();
    int count = 1;
    for (int i = 0; i < nbDays ; i++ ) {
      Day day= _dm.getTTStructure().getCurrentCycle().getDayByRefNo(i+1);
      for(int j = 0; j < nbSeq; j ++) {
        Sequence sequence= _dm.getTTStructure().getCurrentCycle().getSequence(day,j+1);
        for(int k = 0; k < sequence.getSetOfPeriods().size(); k ++) {
          Period period= _dm.getTTStructure().getCurrentCycle().getPeriod(sequence,k+1);
          periodPanel = new SimplePeriodPanel(count,i,j,k);
          periodPanel.addMouseListener(_mouseListener);
          periodPanel.createPanel(period,UWIDTH, UHEIGHT);
          count++;
          c = new GridBagConstraints();
          c.fill = GridBagConstraints.BOTH;
          c.gridx = i;
          c.gridy = (period.getBeginHour()[0] - _dm.getTTStructure().getCurrentCycle().getFirstPeriod().getBeginHour()[0]);//period.getEndHour(_periodLenght)[0];

          if ( period.getEndHour(_periodLenght)[1] == 0 ){
            c.gridheight = period.getEndHour(_periodLenght)[0]- period.getBeginHour()[0];
            c.insets = new Insets( period.getBeginHour()[1], 0, 0, 0 );
          } else {
            c.gridheight = period.getEndHour(_periodLenght)[0] + 1 - period.getBeginHour()[0];
            c.insets = new Insets( period.getBeginHour()[1], 0,(UHEIGHT- period.getEndHour(_periodLenght)[1]), 0 );
          }
          gridbag.setConstraints(periodPanel, c);
          panel.add(periodPanel, c);
        }//end for k
      } // end for j
    }//end for i
     return panel;
  }

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


public JViewport getViewport() {
  return _jScrollPaneOne.getViewport();
}

public JScrollPane getJScrollPane() {
  return _jScrollPaneOne;
}

public Component getPanel() {
  return _jScrollPaneOne;
}

}


