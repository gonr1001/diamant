/**
 * Created on 14-Feb-08
 * 
 * 
 * Title: DxDetailedTTPane.java
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

import dInterface.DToolBar;
import dInternal.dTimeTable.TTStructure;

public class DxDetailedTTPane extends DxTTPane {

	public DxDetailedTTPane(TTStructure tts, DToolBar toolBar, boolean vertical) { //, Dimension d) {
		super(tts, toolBar);
		initDetailedTTPane(vertical);//, d);
	} // end  DetailedTTPane

	//-------------------------------------------
	public JComponent getDxPane() {
		return _jSplitPane;
	}

	//-------------------------------------------
	@Override
	public void updateDxTTPane(TTStructure ttp) {
		_tts = ttp;
		findRowHeaders();
		initTTPane(_jScrollPaneOne);
		initTTPane(_jScrollPaneTwo);
	}

	//-------------------------------------------
	public int getIpady(int i) {
		if (_rowHeaders[i]._n == -1 || _rowHeaders[i]._n == 0)
			return LINE_HEIGHT * 2;
		return (LINE_HEIGHT + 1) * (_rowHeaders[i]._n + 2);
	}

	//-------------------------------------------
	public DxPeriodPanel createPeriodPanel(int refNo, String str) {
		return new DxDetailedPeriodPanel(refNo, str);
	}

	//-------------------------------------------
	public DxPeriodPanel createEmptyPeriodPanel() {
		return new DxDetailedPeriodPanel();
	}

	//-------------------------------------------
	private void initDetailedTTPane(boolean vertical) { //, Dimension d) {
		_jScrollPaneOne = new JScrollPane();
		_jScrollPaneTwo = new JScrollPane();
		findRowHeaders();
		if (_tts != null) {
			initTTPane(_jScrollPaneOne);
			initTTPane(_jScrollPaneTwo);
		}
		if (vertical) {
			_jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
					_jScrollPaneOne, _jScrollPaneTwo);
			_jSplitPane.setDividerLocation(0.0);
		} else {
			_jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					_jScrollPaneOne, _jScrollPaneTwo);
			_jSplitPane.setDividerLocation(0.0);
		}
		_jSplitPane.setOneTouchExpandable(true);

	} // end initDetailedTTPane

} // end DetailedTTPane

