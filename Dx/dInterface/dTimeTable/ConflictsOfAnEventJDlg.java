/**
 *
 * Title: ConflictsOfAnEventJDlg $Revision: 1.5 $  $Date: 2008-02-01 14:31:00 $
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
 * @version $Revision: 1.5 $
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

public class ConflictsOfAnEventJDlg extends JDialog implements ActionListener {
	/* ADJUST_HEIGHT is needed to ajdust the screenSize
	 * minus the barSize (the value is a guess) at the bottom */
	private final static int ADJUST_HEIGHT = 88;
	/* ADJUST_WIDTH is needed to ajdust the screenSize
	 * minus border pixels (the value is a guess) at each side of the screen */
	private final static int ADJUST_WIDTH = 6;
	private TTPane _ttPane;
	private TTStructure _partialTTStruct;
	private DToolBar _toolBar;


	public ConflictsOfAnEventJDlg(JDialog jDialog, DResource event, DModel dm) {
		super(jDialog, DConst.CONFLICTS_OF_AN_EVENT_DLG_TITLE+ " " + event.getID(), true);
		initDlg(event, dm);
	}

	public void initDlg(DResource event, DModel dm) {
		TTStructure totalTTStruct = dm.getTTStructure();
		_partialTTStruct = totalTTStruct.cloneCurrentTTS();
		dm.getConditionsToTest().buildAllConditions(_partialTTStruct);
		_partialTTStruct.getCurrentCycle().resetAllNumberOfConflicts();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(new Dimension(screenSize.width - ADJUST_WIDTH,
				screenSize.height - ADJUST_HEIGHT));

		_ttPane = new ConflictsOfAnEventTTPane(totalTTStruct, _partialTTStruct,
				_toolBar, true, event);
		dm.getConditionsToTest().addEventInAllPeriods(_partialTTStruct, event);

		_ttPane.updateTTPane(_partialTTStruct);


		this.getContentPane().add(_ttPane.getPane());

		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		// if Button CLOSE is pressed
		if (e.getActionCommand().equals(DConst.BUT_CLOSE)) {
			dispose();
		}

	}// end actionPerformed
	
}// end ManualImprovementDetailed
