/**
 * Created on 21-Feb-08
 * 
 * 
 * Title: DxPeriodPanel.java
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
 * 
 * 
 */

package dInterface.dTimeTable;

import java.awt.Color;
import java.util.StringTokenizer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import dConstants.DConst;
import dInternal.dTimeTable.Period;
import eLib.exit.dialog.FatalProblemDlg;

@SuppressWarnings("serial")
public abstract class DxPeriodPanel extends JPanel{
	  JLabel _nbAct, _cTeach, _cRoom, _cStu;
	  protected int _TTSday;
	  protected int _TTSseq;
	  protected int _TTSperiod;
	  protected int _panelRefNo;
	  protected String _str;
	  
	  
	  protected DxPeriodPanel(){
	  	//
	  }

	  protected DxPeriodPanel(int refNo, String str) {
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
	  * rgd: 28/04/2005 I changed the color case 2
	  * */

	  public void setPanelColor(int priority){
	  Color color= Color.GRAY;
	  switch(priority){
	    case 0: color= new Color(236,233,216);//getBackground()
	      break;
	    case 1: color= Color.LIGHT_GRAY;
	      break;
	    case 2: color= new Color(133,133,119); // gray medium to show low period
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