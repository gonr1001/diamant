/**
 * Created on 21-Feb-08
 * 
 * 
 * Title: DxConflictsOfAnEventPanel.java
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

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

import dConstants.DConst;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.dTimeTable.TTStructure;

@SuppressWarnings("serial")
public class DxConflictsOfAnEventPanel extends JDialog {
	/*
	 * ADJUST_HEIGHT is needed to adjust the screenSize minus the barSize (the
	 * value is a guess) at the bottom
	 */
	private final static int ADJUST_HEIGHT = 88;
	/*
	 * ADJUST_WIDTH is needed to adjust the screenSize minus border pixels (the
	 * value is a guess) at each side of the screen
	 */
	private final static int ADJUST_WIDTH = 6;

	private DxTTPane _dxTTPane;
	
	private TTStructure _tempTTStruct;

	public DxConflictsOfAnEventPanel(JDialog jDialog, DResource eventRes,
			DModel dm) {
		super(jDialog, DConst.CONFLICTS_OF_AN_EVENT_DLG_TITLE + " "
				+ eventRes.getID(), true);
		initDlg(eventRes, dm);
	}

	public void initDlg(DResource eventRes, DModel dm) {
		Runtime runtime = Runtime.getRuntime();
		System.out.println("free memory initDlg:   " + runtime.freeMemory()
				/ 1024);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(new Dimension(screenSize.width - ADJUST_WIDTH,
				screenSize.height - ADJUST_HEIGHT));

		_tempTTStruct = dm.getTTStructure().cloneCurrentTTS();
		// in a tt install all events and calculate conflicts
		dm.getConditionsToTest().buildAllConditions(_tempTTStruct);
		// in a tt period by period set conflicts to zero
		_tempTTStruct.getCurrentCycle().resetAllNumberOfConflicts();

		// build the Pane to put in this Panel/JDialog
		_dxTTPane = new DxConflictsOfAnEventTTPane(dm.getTTStructure(),
				_tempTTStruct, true, eventRes);
		// add the Event in each period the changes must be displayed
		dm.getConditionsToTest().addEventInAllPeriods(_tempTTStruct, eventRes);
		_dxTTPane.updateDxTTPane(_tempTTStruct);
		this.getContentPane().add(_dxTTPane.getDxPane());
		this.setVisible(true);
	}

}// end DxConflictsOfAnEventPanel

