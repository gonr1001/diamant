/**
 *
 * Title: ConflictsOfAnEventJDlg $Revision: 1.13 $  $Date: 2008-02-22 16:01:59 $
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
 * @version $Revision: 1.13 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */

package dInterface.dTimeTable;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

import dConstants.DConst;
import dInterface.DToolBar;

import dInternal.DModel;
import dInternal.DResource;
import dInternal.dTimeTable.TTStructure;
import developer.DxFlags;

@SuppressWarnings("serial")
public class ConflictsOfAnEventJDlg extends JDialog { //implements ActionListener {
	/* ADJUST_HEIGHT is needed to adjust the screenSize
	 * minus the barSize (the value is a guess) at the bottom */
	private final static int ADJUST_HEIGHT = 88;
	/* ADJUST_WIDTH is needed to adjust the screenSize
	 * minus border pixels (the value is a guess) at each side of the screen */
	private final static int ADJUST_WIDTH = 6;
	private TTPane _ttPane;
	private TTStructure _tempTTStruct;
	private DToolBar _toolBar;

	public ConflictsOfAnEventJDlg(JDialog jDialog, DResource eventRes, DModel dm) {
		super(jDialog, DConst.CONFLICTS_OF_AN_EVENT_DLG_TITLE + " "
				+ eventRes.getID(), true);
		initDlg(eventRes, dm);
	}

	public void initDlg(DResource eventRes, DModel dm) {
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Runtime runtime = Runtime.getRuntime();
		System.out.println("free memory initDlg" + runtime.freeMemory() / 1024);
		_tempTTStruct = dm.getTTStructure().cloneCurrentTTS();
		dm.getConditionsToTest().buildAllConditions(_tempTTStruct);
		_tempTTStruct.getCurrentCycle().resetAllNumberOfConflicts();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(new Dimension(screenSize.width - ADJUST_WIDTH,
				screenSize.height - ADJUST_HEIGHT));
//		if (DxFlags.newDxTTPane) {
//			_dxTTPane = new ConflictsOfAnEventTTPane(_tempTTStruct, true,
//					eventRes);
//			dm.getConditionsToTest().addEventInAllPeriods(_tempTTStruct,
//					eventRes);
//
////			_dxTTPane.updateTTPane(_tempTTStruct);
//			this.getContentPane().add(_dxTTPane.getPane());
//			this.setVisible(true);
//		} else {
			_ttPane = new ConflictsOfAnEventTTPane(dm.getTTStructure(),
					_tempTTStruct, _toolBar, true, eventRes);
			dm.getConditionsToTest().oldAddEventInAllPeriods(_tempTTStruct,
					eventRes);

			_ttPane.updateTTPane(_tempTTStruct);
			this.getContentPane().add(_ttPane.getPane());
			this.setVisible(true);
//		}
	}

}// end ConflictsOfAnEventJDlg
