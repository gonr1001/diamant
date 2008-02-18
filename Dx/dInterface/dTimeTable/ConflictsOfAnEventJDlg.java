/**
 *
 * Title: ConflictsOfAnEventJDlg $Revision: 1.11 $  $Date: 2008-02-18 16:37:34 $
 * Description: ConflictsOfAnEventJDlg is a class used to
 *              display the so called Conflicts Of An Event which
 *              gives the conflicts between an event and the others events
 *              that are in the TTStructure
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
 * @version $Revision: 1.11 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */

package dInterface.dTimeTable;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import dConstants.DConst;
import dInterface.DToolBar;

import dInternal.DModel;
import dInternal.DResource;
import dInternal.dTimeTable.TTStructure;
import developer.DxFlags;

@SuppressWarnings("serial")
public class ConflictsOfAnEventJDlg extends JDialog implements ActionListener {
	/* ADJUST_HEIGHT is needed to adjust the screenSize
	 * minus the barSize (the value is a guess) at the bottom */
	private final static int ADJUST_HEIGHT = 88;
	/* ADJUST_WIDTH is needed to adjust the screenSize
	 * minus border pixels (the value is a guess) at each side of the screen */
	private final static int ADJUST_WIDTH = 6;
	private TTPane _ttPane;
	private DxTTPane _dxTTPane;
	private TTStructure _tempTTStruct;
	private DToolBar _toolBar;


	public ConflictsOfAnEventJDlg(JDialog jDialog, DResource event, DModel dm) {
		super(jDialog, DConst.CONFLICTS_OF_AN_EVENT_DLG_TITLE+ " " + event.getID(), true);
		initDlg(event, dm);
	}

	public void initDlg(DResource event, DModel dm) {
//		DResource localEvent = event.clone();
	
		_tempTTStruct = dm.getTTStructure().cloneCurrentTTS();
		dm.getConditionsToTest().buildAllConditions(_tempTTStruct);
		_tempTTStruct.getCurrentCycle().resetAllNumberOfConflicts();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //nop
		this.setSize(new Dimension(screenSize.width - ADJUST_WIDTH,         //nop
				screenSize.height - ADJUST_HEIGHT));                        //nop
		if (DxFlags.newDxTTPane) {
//			_dxTTPane = new DxConflictsOfAnEventTTPane(_tempTTStruct);
			_dxTTPane = new DxConflictsOfAnEventTTPane(_tempTTStruct,
					/*_toolBar,*/ true, event);
			dm.getConditionsToTest().addEventInAllPeriods(_tempTTStruct, event);
			
			_dxTTPane.updateTTPane(_tempTTStruct);                          // nop			
			this.getContentPane().add(_dxTTPane.getPane());                 // nop
			this.setVisible(true);                                          // nop
		} else {
			_ttPane = new ConflictsOfAnEventTTPane(dm.getTTStructure(), _tempTTStruct,
					_toolBar, true, event);
			dm.getConditionsToTest().addEventInAllPeriods(_tempTTStruct, event);
			
			_ttPane.updateTTPane(_tempTTStruct);
			this.getContentPane().add(_ttPane.getPane());                   
			this.setVisible(true);                                          
		}
	}
	public void actionPerformed(ActionEvent e) {
		// if Button CLOSE is pressed
		if (e.getActionCommand().equals(DConst.BUT_CLOSE)) {
			_tempTTStruct = null;
			_ttPane = null;
			dispose();
		}
	}// end actionPerformed
	
}// end ManualImprovementDetailed
