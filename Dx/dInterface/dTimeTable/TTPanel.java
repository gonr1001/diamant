package dInterface.dTimeTable;

/**
 *
 * Title: TTPanel $Revision: 1.54 $  $Date: 2003-10-07 13:09:54 $
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
 * @version $Revision: 1.54 $
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
  protected final int PERIOD_HEIGHT = 400;  // for the screen
  protected final int LINE_HEIGHT = 10;
  protected final int SMALL_PERIOD_HEIGHT = 40; //LINE_HEIGHT * 2;  // for the screen
  protected final int HEADER_HEIGHT = SMALL_PERIOD_HEIGHT / 2 ;
  protected final int ROW_WIDTH =  35;    // timeTable.nbDays * MINWIDTH;

  protected int _lastHour;
  //protected DModel _dm;
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

  abstract public JViewport getViewport() ; /*{
    return new JViewport();
  }*/



  abstract public JScrollPane getJScrollPane();/* {
      return  _jScrollPaneOne;
  }*/

  abstract public Component getPanel();

  //abstract public JSplitPane getJSplitPane();

   abstract  public PeriodPanel getPeriodPanel(int i);/*{
      return new PeriodPanel(0,0,0,0);
    }*/
}