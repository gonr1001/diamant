package dInterface.dTimeTable;

/**
 *
 * Title: TTPanel $Revision: 1.46 $  $Date: 2003-09-30 17:35:36 $
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
 * @version $Revision: 1.46 $
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



public abstract class TTPanel {

  //protected int MINHEIGHT = 60;
  protected int HHEIGHT =  24; // timeTable.nbDays * MINWIDTH;
  protected int VWIDTH =  36; // timeTable.nbDays * MINWIDTH;
  protected int UWIDTH =  100; // timeTable.nbDays * MINWIDTH;
  protected int UHEIGHT =  60;// (timeTable.getLatest() - timeTable.getEarliest()) * MINHEIGHT;
  protected int MinWidth=80;
  protected int MinHeight=80;
  protected int LASTHOUR=8;
  protected DModel _dm;

  public TTPanel(DModel dm) {
    _dm = dm;
  }

  abstract public void updateTTPanel(TTStructure ttp);

  public JViewport getViewport() {
    return new JViewport();
  }



  public JScrollPane getJScrollPane() {
      return new JScrollPane();

  }

  abstract public Component getPanel();

  //abstract public JSplitPane getJSplitPane();

   abstract  public PeriodPanel getPeriodPanel(int i);/*{
      return new PeriodPanel(0,0,0,0);
    }*/
}