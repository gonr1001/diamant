package dInterface.dTimeTable;

/**
 *
 * Title: SimplePeriodPanel $Revision: 1.10 $  $Date: 2003-10-21 16:23:47 $
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
 * Description: SimplePeriodPanel is a class used to
 *
 */

import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.BorderFactory;

import dInternal.dTimeTable.Period;
import dResources.DConst;

public class SimplePeriodPanel extends PeriodPanel{

  public SimplePeriodPanel(int refNo, String str) {
    super(refNo, str);
  }

  public SimplePeriodPanel() {
    super();
    this.setForeground(Color.WHITE);
    this.setBackground(Color.WHITE);
  }

  public void createPanel(Period period){
    setLayout(new GridLayout(2,1));
    setBorder(BorderFactory.createEtchedBorder());
    setValue(period);
  }

  public void setValue(Period period){
    JPanel topPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JLabel per = new JLabel (" Période "+ _panelRefNo + " ");
    JLabel nbAct = new JLabel( "("+Integer.toString(period.getNumberOfEvents())+")");
    _cTeach = new JLabel(Integer.toString(period.getNbInstConflict()));
    _cRoom = new JLabel(Integer.toString(period.getNbRoomConflict()));
    _cStu = new JLabel(Integer.toString(period.getNbStudConflict()));
    if(period.getPriority()!=2){
      _cRoom.setForeground(DConst.COLOR_ROOM );// rooms conflicts color
      _cTeach.setForeground(DConst.COLOR_INST );// instructors conflicts color
      _cStu.setForeground(DConst.COLOR_STUD);// students conflicts color
    }
    //
    topPanel.add(per);
    topPanel.add(nbAct);
    bottomPanel.add(_cTeach);
    bottomPanel.add(_cRoom);
    bottomPanel.add(_cStu);
    //
    add(topPanel);
    add(bottomPanel);
    // set period panel color
    setPanelColor(period.getPriority());
  }
}