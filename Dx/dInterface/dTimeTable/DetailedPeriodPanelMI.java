package dInterface.dTimeTable;

/**
 *
 * Title: DetailedPeriodPanel $Revision: 1.8 $  $Date: 2004-06-09 19:29:17 $
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
 * @version $Revision: 1.8 $
 * @author  $Author: gonzrubi $
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
import java.util.Vector;

//import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JPanel;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import dInternal.dTimeTable.Period;
import dConstants.DConst;



public class DetailedPeriodPanelMI extends PeriodPanel{
  private JList _jList;
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
    JPanel bottomPanel = new JPanel();
    JLabel per = new JLabel (" Période "+ _panelRefNo + " ");

    //_vec = period.getEventsInPeriod().getNamesVector(1);


    _vec = period.getConflictsEventsInPeriod(_eventName).getNamesVector(1);
    _jList  = new JList(_vec);

    //JLabel vec = new JLabel("moi");
    //_rightList.addMouseListener(mouseListenerLists);

    JLabel nbAct = new JLabel( "("+Integer.toString(period.getNumberOfEvents())+")");
    /*_cTeach = new JLabel(Integer.toString(period.getNbInstConflict()));
    _cRoom = new JLabel(Integer.toString(period.getNbRoomConflict()));
    _cStu = new JLabel(Integer.toString(period.getNbStudConflict()));
    if(period.getPriority()!=2){
      _cRoom.setForeground(DConst.COLOR_ROOM );// rooms conflicts color
      _cTeach.setForeground(DConst.COLOR_INST );// instructors conflicts color
      _cStu.setForeground(DConst.COLOR_STUD);// students conflicts color
    }*/
    //
    topPanel.add(per);
    topPanel.add(nbAct);
    for(int i = 0; i < _vec.size(); i ++) {
      miPanel.add( new JLabel((String)_vec.get(i)));
    }
    //miPanel.add(_jList);
    /*bottomPanel.add(_cTeach);
    bottomPanel.add(_cRoom);
    bottomPanel.add(_cStu);*/
    //
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