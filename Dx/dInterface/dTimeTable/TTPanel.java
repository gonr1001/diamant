package dInterface.dTimeTable;

/**
 *
 * Title: TTPanel $Revision: 1.55 $  $Date: 2003-10-08 18:15:22 $
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
 * @version $Revision: 1.55 $
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

import java.awt.Component;
import javax.swing.JViewport;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
//import java.awt.GridBagLayout;
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

import dInterface.DToolBar;



public abstract class TTPanel {

  protected final int PERIOD_WIDTH =  100;  // for the screen
  protected final int MAGIC = 0;
  protected final int PERIOD_HEIGHT = 400;  // for the screen
  protected final int LINE_HEIGHT = 10;
  protected final int SMALL_PERIOD_HEIGHT = 40; //LINE_HEIGHT * 2;  // for the screen
  protected final int HEADER_HEIGHT = SMALL_PERIOD_HEIGHT / 2 ;
  protected final int ROW_WIDTH =  35;    // timeTable.nbDays * MINWIDTH;

  protected int _lastHour;
  protected TTStructure _tts;
  protected DToolBar _toolBar;
  protected JScrollPane _jScrollPaneOne;
  protected JSplitPane _jSplitPane;
  protected PeriodPanel _lastActivPanel = null;
  protected int _periodLenght;

  /**
   * constructor
   * @param tts
   * @param toolBar
   */
  public TTPanel(TTStructure tts, DToolBar toolBar) {
    _tts = tts;
    _toolBar = toolBar;
    _jScrollPaneOne = null;
    _jSplitPane = null;
  }
  //-------------------------------------------
  abstract public void updateTTPanel(TTStructure ttp);

  abstract public JViewport getViewport();

  abstract public JScrollPane getJScrollPane();

  abstract public Component getPanel();

  abstract  public PeriodPanel getPeriodPanel(int i);

  //-------------------------------------------
  protected JPanel XcreateColumnHeader() {
    JPanel panel = new JPanel();
    GridBagLayout gridBL = new GridBagLayout();
    GridBagConstraints gridBC = new GridBagConstraints();
    panel.setLayout(gridBL);
    //gridBC.fill = GridBagConstraints.BOTH;
    Cycle cycle = _tts.getCurrentCycle();
    gridBC.gridy = 0;
    for (int i = 0; i < cycle.getSetOfDays().size() ; i++){
      gridBC.gridx = i;
      gridBC.ipadx = PERIOD_WIDTH;
      Resource day = cycle.getSetOfDays().getResourceAt(i);
      JLabel jLabel = new JLabel("J " + (i + 1) + " : "+ day.getID(), JLabel.CENTER);
      jLabel.setBorder(BorderFactory.createEtchedBorder());
      gridBL.setConstraints(jLabel, gridBC);
      panel.add(jLabel, gridBC);
    }
    panel.setBorder(BorderFactory.createEtchedBorder());
    return panel;
  }

  protected JPanel createColumnHeader() {
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(1,0));
    Cycle cycle = _tts.getCurrentCycle();
    panel.setPreferredSize(new Dimension(PERIOD_WIDTH * cycle.getSetOfDays().size(),
        HEADER_HEIGHT));
    panel.setBorder(BorderFactory.createEtchedBorder());
    for (int i = 0; i < cycle.getSetOfDays().size() ; i++){
      Resource day = cycle.getSetOfDays().getResourceAt(i);
      JLabel l = new JLabel("J " + (i + 1) + " : "+ day.getID(), JLabel.CENTER);
      l.setBorder(BorderFactory.createEtchedBorder());
      panel.add(l);
    }
    panel.setBorder(BorderFactory.createEtchedBorder());
    return panel;
  }
}