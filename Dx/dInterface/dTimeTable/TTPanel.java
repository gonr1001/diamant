package dInterface.dTimeTable;

/**
 *
 * Title: TTPanel $Revision: 1.44 $  $Date: 2003-09-29 20:21:47 $
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
 * @version $Revision: 1.44 $
 * @author  $Author: syay1801 $
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
  protected DModel _dm;

  public TTPanel() {
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

    public PeriodPanel getPeriodPanel(int i){
      return new PeriodPanel(0,0,0,0);
    }
}