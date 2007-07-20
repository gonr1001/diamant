package dInterface.dTimeTable;

/**
 *
 * Title: ConflictsOfAnEventPeriodPanel $Revision: 1.4 $  $Date: 2007-07-20 12:52:37 $
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
 * @version $Revision: 1.4 $
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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dInternal.DResource;
import dInternal.dTimeTable.Period;




public class ConflictsOfAnEventPeriodPanel extends PeriodPanel{
  
  private Vector<String> _vec;
  private DResource _event;
  private String _shortEventName;
  private Period _totalPeriod;
  
  public ConflictsOfAnEventPeriodPanel(){
    super();
    this.setForeground(Color.WHITE);
    this.setBackground(Color.WHITE);
  }

  public ConflictsOfAnEventPeriodPanel(int refNo, String str, Period totalPeriod, DResource event) {
    super(refNo, str);
    _event = event;
    StringTokenizer st = new StringTokenizer(event.getID(), ".");
    if (st.countTokens() > 2)
        _shortEventName =  st.nextToken();
    _totalPeriod = totalPeriod;
  }
  /**
   *
   * */
  public void createPanel(Period period){
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createEtchedBorder());
    setPanel(period);
  }

  private void setPanel(Period period){
    JPanel topPanel = new JPanel();
    JPanel miPanel = new JPanel();
    JPanel southPanel = new JPanel();
    miPanel.setLayout(new GridLayout(0,1));
    southPanel.setLayout(new GridLayout(0,1));
    JLabel per = new JLabel (" Période "+ _panelRefNo + " ");
    _vec = period.getConflictsEventsInPeriod(_event.getID()).getNamesVector(1);
    JLabel nbAct = new JLabel( "("+Integer.toString(period.getNumberOfEvents())+")");
    topPanel.add(per);
    topPanel.add(nbAct);
    // the events are displayed
    for(int i = 0; i < _vec.size(); i ++) {
//        if ( ((String)_vec.get(i)).contains(_shortEventName) ){ // XXXX Pascal: lien inutile avec JDK 1.5
		if ( (_vec.get(i)).matches(".*" + _shortEventName + ".*") ){
            JLabel jl = new JLabel(_vec.get(i));
            jl.setForeground(Color.BLUE);
            miPanel.add(jl);
        }  else miPanel.add( new JLabel(_vec.get(i)));
    }
    
    add(topPanel, BorderLayout.NORTH); //add(topPanel);
    add(miPanel, BorderLayout.CENTER);
    int cStu = period.getNbStudConflict();
	int cInst = period.getNbInstConflict();
    int cRoom = period.getNbRoomConflict();
    Color color;
    if (cStu + cInst + cRoom == 0) 
        color = Color.GREEN;
    else
        color = Color.PINK;
    if (eventIsHere())
        color = Color.YELLOW;
    southPanel.add(buildConflictPanel(period, "Ajoute", color));
    southPanel.add(buildConflictPanel(_totalPeriod, "Était"));
    add(southPanel, BorderLayout.SOUTH);
    // set period panel color
    setPanelColor(period.getPriority());
    
    
  }
  
 
  public boolean eventIsHere() {
      return _totalPeriod.getEventsInPeriod().getResource(_event.getID())!= null;
  }
  
} /* end ConflictsOfAnEventPeriodPanel */

