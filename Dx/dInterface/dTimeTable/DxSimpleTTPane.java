/**
 * Created on 14-Feb-08
 * 
 * 
 * Title: DxSimpleTTPane.java
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

public class DxSimpleTTPane extends DxTTPane {

	public DxSimpleTTPane(TTStructure tts, DToolBar toolBar) {
		super(tts, toolBar);
		initSimpleTTPane();
	} // end  SimpleTTPane

	public JComponent getDxPane() {
		return _jScrollPaneOne;
	}

	//-------------------------------------------
	public void updateTTPane(TTStructure ttp) {
		_tts = ttp;
		findRowHeaders();
		initTTPane(_jScrollPaneOne);
	}

	//-------------------------------------------
	public int getIpady(int i) {
		if (i != 0)
			return LINE_HEIGHT * 2 * (i / i);
		return LINE_HEIGHT * 2;
	}

	//-------------------------------------------
	public DxPeriodPanel createPeriodPanel(int refNo, String str) {
		return new DxSimplePeriodPanel(refNo, str);
	}

	//-------------------------------------------
	public DxPeriodPanel createEmptyPeriodPanel() {
		return new DxSimplePeriodPanel();
	}

	//-------------------------------------------
	private void initSimpleTTPane() {
		_jScrollPaneOne = new JScrollPane();
		_jScrollPaneTwo = new JScrollPane();
		findRowHeaders();
		if (_tts != null) {
			initTTPane(_jScrollPaneOne);
		}
		_jSplitPane = new JSplitPane();
		_jSplitPane.setTopComponent(_jScrollPaneOne);
		_jSplitPane.setBottomComponent(null);
	} // end  initSimpleTTPanel

	@Override
	public void updateDxTTPane(TTStructure ttp) {
			_tts = ttp;
			findRowHeaders();
			initTTPane(_jScrollPaneOne);
	}
	
} /* end SimplePeriodPane */
