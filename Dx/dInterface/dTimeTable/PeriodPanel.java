package dInterface.dTimeTable;

/**
 *
 * Title: PeriodPanel $Revision: 1.6 $  $Date: 2003-09-29 20:36:32 $
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
 * @version $Revision: 1.6 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */


/**
 * Description: PeriodPanel is a class used to
 *
 */

import java.awt.Dimension;
//import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;

import dInternal.dTimeTable.Period;
import dResources.DConst;

public abstract class PeriodPanel extends JPanel{
  JLabel _nbAct, _cTeach, _cRoom, _cStu;
  protected int _TTSday;
  protected int _TTSseq;
  protected int _TTSperiod;
  protected int _panelRefNo;

  public PeriodPanel(int refNo, int day, int seq, int per) {
    _panelRefNo= refNo;
    _TTSday= day;
    _TTSseq= seq;
    _TTSperiod= per;
  }

  /**
   *
   * */
  public void createPanel( Period period, int w, int h){
    setLayout(new GridLayout(2,1));
    setPreferredSize(new Dimension(w, h));
    setBorder(new BevelBorder(BevelBorder.RAISED));
    setValue(period);
  }

  public void setValue(Period period){
    JPanel topPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JLabel per = new JLabel (" P�riode "+ _panelRefNo + " ");
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
 private void setPanelColor( int priority){
   Color color= Color.GRAY;
   switch(priority){
     case 0: color= new Color(236,233,216);//getBackground()
       break;
     case 1: color= Color.LIGHT_GRAY;
       break;
     case 2: color= Color.DARK_GRAY;
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