package dInterface.dTimeTable;

/**
 *
 * Title: DetailedPeriodPanel $Revision: 1.13 $  $Date: 2004-10-27 14:36:58 $
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
 * @version $Revision: 1.13 $
 * @author  $Author: syay1801 $
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */


/**
 * Description: DetailedPeriodPanel is a class used to
 *
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
//import javax.swing.JList;
import javax.swing.JPanel;

import dInternal.dTimeTable.Period;




public class DetailedPeriodPanelMI extends PeriodPanel{
  //private JList _jList;
  private Vector _vec;
  private String _eventName;
  
  public DetailedPeriodPanelMI(){
    super();
    this.setForeground(Color.WHITE);
    this.setBackground(Color.WHITE);
  }

  public DetailedPeriodPanelMI(int refNo, String str, String eventName) {
    super(refNo, str);
    _eventName = eventName;
  }
  /**
   *
   * */
  public void createPanel(Period period){
    setLayout(new BorderLayout());//new GridLayout(3,1));
    setBorder(BorderFactory.createEtchedBorder());
    setValue(period);
  }

  public void setValue(Period period){
    JPanel topPanel = new JPanel();
    JPanel miPanel = new JPanel();
    miPanel.setLayout(new GridLayout(0,1));
    JLabel per = new JLabel (" Période "+ _panelRefNo + " ");

    _vec = period.getConflictsEventsInPeriod(_eventName).getNamesVector(1);
    JLabel nbAct = new JLabel( "("+Integer.toString(period.getNumberOfEvents())+")");
    topPanel.add(per);
    topPanel.add(nbAct);
    // the events are displayed
    //System.out.println("RGR");
    for(int i = 0; i < _vec.size(); i ++) {
      miPanel.add( new JLabel((String)_vec.get(i)));
      //System.out.println("R" +(String)_vec.get(i));
    }
    add(topPanel, BorderLayout.NORTH); //add(topPanel);
    add(miPanel, BorderLayout.CENTER);
    add(buildConflictPanel(period), BorderLayout.SOUTH);
    // set period panel color
    setPanelColor( period.getPriority());
  }

} /* end DetailedPeriodPanel *

public class DetailedPeriodPanelMI {

  public DetailedPeriodPanelMI() {
  }
}*/