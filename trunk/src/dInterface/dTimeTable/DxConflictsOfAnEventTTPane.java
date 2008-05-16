/**
 * Created on 14-Feb-08
 * 
 * 
 * Title: DxConflictsOfAnEventTTPane.java
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

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import dInternal.DResource;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.TTStructure;

public class DxConflictsOfAnEventTTPane extends DxTTPane {

	TTStructure _totalTTStruct;

	DResource _eventRes;

	/**
	 * 
	 * @param tts
	 * @param vertical
	 * @param eventName
	 */
	public DxConflictsOfAnEventTTPane(TTStructure totaltts,
			TTStructure partialtts, boolean vertical, DResource eventRes) {
		super(partialtts);
		_eventRes = eventRes;
		_totalTTStruct = totaltts;
		initDetailedTTPane(vertical);

	} // end DxConflictsOfAnEventTTPane

	// -------------------------------------------
	public JComponent getDxPane() {
		return _jSplitPane;
	}

	// -------------------------------------------
	@Override
	public void updateDxTTPane(TTStructure ttp) {
		_tts = ttp;
		findRowHeaders();
		initTTPane(_jScrollPaneTwo);
		initTTPane(_jScrollPaneOne);
	}

	// -------------------------------------------
	public int getIpady(int i) {
		if (_rowHeaders[i]._n == -1 || _rowHeaders[i]._n == 0)
			return LINE_HEIGHT * 2 + LINE_HEIGHT;
		return (LINE_HEIGHT + 1) * (_rowHeaders[i]._n + 2) + LINE_HEIGHT;
	}

	// -------------------------------------------
	public DxPeriodPanel createPeriodPanel(int refNo, String str) {
		Period totalPeriod = _totalTTStruct.getCurrentCycle()
				.getPeriodByPeriodKey(str);
		return new DxConflictsOfAnEventPeriodPanel(refNo, str, totalPeriod,
				_eventRes);
	}

	// -------------------------------------------
	public DxPeriodPanel createEmptyPeriodPanel() {
		return new DxConflictsOfAnEventPeriodPanel();
	}

	// -------------------------------------------
	private void initDetailedTTPane(boolean vertical) {
		_jScrollPaneOne = new JScrollPane();
		_jScrollPaneTwo = new JScrollPane();
		findRowHeaders();
		if (_tts != null) {
			initTTPane(_jScrollPaneTwo);
			initTTPane(_jScrollPaneOne);
		}
		if (vertical) {
			_jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
					_jScrollPaneTwo, _jScrollPaneOne);
			_jSplitPane.setDividerLocation(0.0);
		} else {
			_jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					_jScrollPaneOne, _jScrollPaneTwo);
			_jSplitPane.setDividerLocation(0.0);
		}
		_jSplitPane.setOneTouchExpandable(true);
	} // end initDetailedTTPane

} // end DxConflictsOfAnEventTTPane
