package dInterface.dTimeTable;

/**
 *
 * Title: DetailedPeriodPanel $Revision: 1.10 $  $Date: 2003-10-20 13:51:30 $
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
 * Description: DetailedPeriodPanel is a class used to
 *
 */
import java.util.Vector;
import java.awt.Dimension;
import java.util.Collection;
//import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;

import dInternal.dTimeTable.Period;
import dResources.DConst;
import dInternal.dData.SetOfActivities;
import dInternal.dData.Activity;


public class DetailedPeriodPanel extends PeriodPanel{
  JLabel _nbAct, _cTeach, _cRoom, _cStu;

  private JList _jList;
  private Vector _vec;
  private String [] arr = {"ADM111", "GEI442"};
  //private SetOfActivities _activities;

  public DetailedPeriodPanel(){
    super();
  }
/*  public DetailedPeriodPanel(int refNo, int day, int seq, int per) {
    super( refNo, day,  seq,  per);
    _panelRefNo= refNo;
    _TTSday= day;
    _TTSseq= seq;
    _TTSperiod= per;
    _vec = new Vector();
    _vec.add(arr[0]);
    _vec.add(arr[1]);
    //_activities = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities();
  }*/
  public DetailedPeriodPanel(int refNo, String str) {
    super(refNo, str);
    /*_panelRefNo= refNo;
    _TTSday= day;
    _TTSseq= seq;
    _TTSperiod= per;*/
  }

/*
  public void setBackground(int i){
  setBackground(Color.TRANSLUCENT);
}

public void setForeground(int i){
 setForeground(Color.TRANSLUCENT);
 }*/
  /**
   *
   * */
  public void createPanel(Period period){//, int w, int h){
    setLayout(new GridLayout(3,1));
    //setPreferredSize(new Dimension(w, h));
    setBorder(new BevelBorder(BevelBorder.RAISED));
    setValue(period);
  }

  public void setValue(Period period){
    JPanel topPanel = new JPanel();
    JPanel miPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JLabel per = new JLabel (" Période "+ _panelRefNo + " ");

    _vec = period.getEventsInPeriod().getNamesVector(1);
    //_vec = period.getEventsInPeriod().getNamesVector().size;
  //_vec = getUnitiesNames(_vec);

    _jList  = new JList(_vec);
    //JLabel vec = new JLabel("moi");
    //_rightList.addMouseListener(mouseListenerLists);

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
    miPanel.add(_jList);
    bottomPanel.add(_cTeach);
    bottomPanel.add(_cRoom);
    bottomPanel.add(_cStu);
    //
    add(topPanel);
    add(miPanel);
    add(bottomPanel);
    // set period panel color
    setPanelColor( period.getPriority());
  }

  /**
   * @return int the period reference number
   * */
  public int getPanelRefNo(){

    return _panelRefNo;
  }

  /**
   * @return int[] the period reference of the panel. int[0]= the day,
   * int[1]= the sequence, int[2]= the period reference number
   * */
  public int[] getPeriodRef(){
    int [] per={_TTSday,_TTSseq,_TTSperiod};
    return per;
  }

  /**
  *
  * */
 public void setPanelColor( int priority){
   Color color= Color.GRAY;
   switch(priority){
     case 0: color= new Color(236,233,216);//getBackground()
       break;
     case 1: color= Color.LIGHT_GRAY;
       break;
     case 2: color= Color.DARK_GRAY;
       break;
     case 3: color= Color.PINK;// to show a conflict panel
       break;
     case 4: color= Color.GREEN;// to show a selected panel
       break;
   }
   for (int i=0; i< getComponentCount(); i++){
     getComponent(i).setBackground(color);
   }

  }
  /**
   *
   * */
  public void setPanelBackGroundColor(int init ){
    Color color= Color.BLUE;
  switch(init){
    case 0: color= new Color(236,233,216);//getBackground()
      break;
    case 1: color= Color.BLUE;
      break;
    case 2: ;
      break;
   }
    setBackground(color);
  }

}