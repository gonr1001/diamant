/**
 * Created on 21-Feb-08
 * 
 * 
 * Title: DxDetailedPeriodPanel.java
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.selectiveSchedule.SelectiveScheduleManager;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.dData.StandardCollection;
import dInternal.dTimeTable.Period;

@SuppressWarnings("serial")
public class DxDetailedPeriodPanel extends DxPeriodPanel{
	  private JList _jList;
	  private Vector<String> _vec;

	  public DxDetailedPeriodPanel(){
	    super();
	    this.setForeground(Color.WHITE);
	    this.setBackground(Color.WHITE);
	  }

	  public DxDetailedPeriodPanel(int refNo, String str) {
	    super(refNo, str);
	  }
	  /**
	   *
	   * */
	  public void createPanel(Period period){
	    setLayout(new BorderLayout());//new GridLayout(3,1));
	    setBorder(BorderFactory.createEtchedBorder());
	    setValue(period);
	  }

	    public void setValue(Period period) {
	        JPanel topPanel = new JPanel();
	        JPanel miPanel = new JPanel();
	        //miPanel.setLayout(new GridLayout(0,1));
	        //JPanel bottomPanel = new JPanel();
	        JLabel per = new JLabel(" P�riode " + _panelRefNo + " ");

	        if (SelectiveScheduleManager.getInstance().isEnabled()) {
	            DSetOfResources sor = new StandardCollection(); // 6 = events
	            // On inclus seulement les evenements qui sont filtres par
	            // SelectiveScheduleManager

	            DSetOfResources eip = period.getEventsInPeriod();
	            Vector<DResource>setOfResources = eip.getSetOfResources();
	            Iterator<DResource> itrSetOfResources = setOfResources.iterator();
	            DResource res;
	            DResource eventRes;

	            while (itrSetOfResources.hasNext()) {
	                res = itrSetOfResources.next();
	                eventRes = DApplication.getInstance().getCurrentDModel()
	                        .getSetOfEvents().getResource(res.getID());
	                if (SelectiveScheduleManager.getInstance().validateElement(
	                        eventRes)) {
	                    sor.addResource(res, 1);
	                }
	            }

	            _vec = sor.getNamesVector(1);
	        } else {
	            //            _vec = period.getEventsInPeriod().getNamesVector(1);
	            _vec = period.getConflictsEventsInPeriod(_str).getNamesVector(1);
	        }
	        _jList = new JList(_vec);
	        //JLabel vec = new JLabel("moi");
	        //_rightList.addMouseListener(mouseListenerLists);

	        JLabel nbAct = new JLabel("("
	                + Integer.toString(period.getNumberOfEvents()) + ")");
	        _cTeach = new JLabel(Integer.toString(period.getNbInstConflict()));
	        _cRoom = new JLabel(Integer.toString(period.getNbRoomConflict()));
	        _cStu = new JLabel(Integer.toString(period.getNbStudConflict()));
	        if (period.getPriority() != 2) {
	            _cRoom.setForeground(DConst.COLOR_ROOM);// rooms conflicts color
	            _cTeach.setForeground(DConst.COLOR_INST);// instructors conflicts
	            // color
	            _cStu.setForeground(DConst.COLOR_STUD);// students conflicts color
	        }
	        //
	        topPanel.add(per);
	        topPanel.add(nbAct);
	        /*
	         * for(int i = 0; i < _vec.size(); i ++) { miPanel.add( new
	         * JLabel((String)_vec.get(i))); }
	         */
	        miPanel.add(_jList);
	        /*
	         * bottomPanel.add(_cTeach); bottomPanel.add(_cRoom);
	         * bottomPanel.add(_cStu);
	         */
	        //
	        add(topPanel, BorderLayout.NORTH); //add(topPanel);
	        add(miPanel, BorderLayout.CENTER);
	        add(buildConflictPanel(period), BorderLayout.SOUTH);
	        // set period panel color
	        setPanelColor(period.getPriority());
	    }
	} /* end DetailedPeriodPanel */