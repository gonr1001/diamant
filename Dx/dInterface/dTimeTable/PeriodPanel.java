package dInterface.dTimeTable;

/**
 *
 * Title: PeriodPanel $Revision: 1.26 $  $Date: 2005-03-08 16:00:43 $
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
 * @version $Revision: 1.26 $
 * @author  $Author: syay1801 $
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


//import javax.swing.JScrollPane;
import java.awt.Color;
import java.util.StringTokenizer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import dConstants.DConst;
import dInternal.dTimeTable.Period;
import eLib.exit.dialog.FatalProblemDlg;

public  abstract class PeriodPanel extends JPanel{
  JLabel _nbAct, _cTeach, _cRoom, _cStu;
  protected int _TTSday;
  protected int _TTSseq;
  protected int _TTSperiod;
  protected int _panelRefNo;
  protected String _str;

  protected PeriodPanel(){
  }

  protected PeriodPanel(int refNo, String str) {
    _str = str;
    _panelRefNo= refNo;
    StringTokenizer st = new StringTokenizer(str,".");
    _TTSday= Integer.parseInt(st.nextToken()) -1;
    _TTSseq= Integer.parseInt(st.nextToken()) -1;
    _TTSperiod= Integer.parseInt(st.nextToken())-1;

  }

  protected abstract void createPanel(Period period);


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
    case 3: color= Color.PINK;// to show a period where a conflict arises.
      break;
    case 4: color= Color.GREEN;// to show where an event is affected.
      break;
    case 5: color= Color.ORANGE;// to show where an event is affected.
    break;
    default:
      new FatalProblemDlg("I was in PeriodPanel class and the switch failed!!!" );
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
    case 2: 
      break;
   }
    setBackground(color);
  }

  public JPanel buildConflictPanel(Period period, String str, Color color){
      JPanel conflictPanel = new JPanel();
      JLabel title = new JLabel(str);
      _cStu = new JLabel(Integer.toString(period.getNbStudConflict()));
      _cTeach = new JLabel(Integer.toString(period.getNbInstConflict()));
      _cRoom = new JLabel(Integer.toString(period.getNbRoomConflict()));
      conflictPanel.add(title);
      conflictPanel.add(_cStu);
      conflictPanel.add(_cTeach);
      conflictPanel.add(_cRoom);
      
      if (color != null) {
          conflictPanel.setBackground(color);
      }
        if(period.getPriority()!=2){
          _cRoom.setForeground(DConst.COLOR_ROOM );// rooms conflicts color
          _cTeach.setForeground(DConst.COLOR_INST );// instructors conflicts color
          _cStu.setForeground(DConst.COLOR_STUD);// students conflicts color
        }
        return conflictPanel;
  }
  public JPanel buildConflictPanel(Period period,String str){
      JPanel conflictPanel = new JPanel();
      JLabel title = new JLabel(str);
      _cStu = new JLabel(Integer.toString(period.getNbStudConflict()));
      _cTeach = new JLabel(Integer.toString(period.getNbInstConflict()));
      _cRoom = new JLabel(Integer.toString(period.getNbRoomConflict()));
      conflictPanel.add(title);
      conflictPanel.add(_cStu);
      conflictPanel.add(_cTeach);
      conflictPanel.add(_cRoom);
      //conflictPanel.setBorder(BorderFactory.createEtchedBorder());

        if(period.getPriority()!=2){
          _cRoom.setForeground(DConst.COLOR_ROOM );// rooms conflicts color
          _cTeach.setForeground(DConst.COLOR_INST );// instructors conflicts color
          _cStu.setForeground(DConst.COLOR_STUD);// students conflicts color
        }
        return conflictPanel;
  }
	
  public JPanel buildConflictPanel(Period period){
      return buildConflictPanel(period,"");
  }
}