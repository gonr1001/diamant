/**
 * Created on 21-Feb-08
 * 
 * 
 * Title: DxSimplePeriodPanel.java
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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dConstants.DConst;
import dInternal.dTimeTable.Period;

@SuppressWarnings("serial")
public class DxSimplePeriodPanel extends DxPeriodPanel{

	  public DxSimplePeriodPanel(int refNo, String str) {
	    super(refNo, str);
	  }

	  public DxSimplePeriodPanel() {
	    super();
	    this.setForeground(Color.WHITE);
	    this.setBackground(Color.WHITE);
	  }

	  public void createPanel(Period period){
	    setLayout(new GridBagLayout());
	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.weightx = 1.0;
	    constraints.weighty = 1.0;
	    constraints.fill = GridBagConstraints.BOTH;
	    setBorder(BorderFactory.createEtchedBorder());
	    setValue(period, constraints);
	  }

	  public void setValue(Period period, GridBagConstraints constraints){
	    //JPanel perPanel = new JPanel();
	    JLabel per = new JLabel (" P "+ _panelRefNo + " ");
	    constraints.weightx = .150;
	    constraints.weighty = 1.0;
	    constraints.gridheight = 2;
	    constraints.gridx = 0;
	    constraints.gridy = 0;
	    add(per, constraints);

	   /* buildConflictPanel(period);
	    JPanel conflictPanel = new JPanel();
	    _cStu = new JLabel(Integer.toString(period.getNbStudConflict()));
	    _cTeach = new JLabel(Integer.toString(period.getNbInstConflict()));
	    _cRoom = new JLabel(Integer.toString(period.getNbRoomConflict()));
	    conflictPanel.add(_cStu);
	    conflictPanel.add(_cTeach);
	    conflictPanel.add(_cRoom);
	    conflictPanel.setBorder(BorderFactory.createEtchedBorder());*/

	 /*   if(period.getPriority()!=2){
	      _cRoom.setForeground(DConst.COLOR_ROOM );// rooms conflicts color
	      _cTeach.setForeground(DConst.COLOR_INST );// instructors conflicts color
	      _cStu.setForeground(DConst.COLOR_STUD);// students conflicts color
	    }*/
	    //
	    constraints.weightx = .850;
	    constraints.weighty = 1.0;
	    constraints.gridheight = 1;
	    constraints.gridwidth = 1;
	    constraints.gridx = 1;
	    constraints.gridy = 0;
	    JPanel conflict= buildConflictPanel(period);
	    conflict.setBorder(BorderFactory.createEtchedBorder()) ;
	    add(conflict,constraints);    //conflictPanel, constraints);

	    JLabel nbAct = new JLabel(DConst.SB_T_EVENT +" "+Integer.toString(period.getNumberOfEvents())+"");
	    constraints.gridheight = 1;
	    constraints.gridwidth = 2;
	    constraints.gridx = 1;
	    constraints.gridy = 1;
	    add(nbAct, constraints);

	    // set period panel color
	    setPanelColor(period.getPriority());
	  }


	}