/**
 * Created on Mar 25, 2006
 * 
 * 
 * Title: DxStateBar.java 
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
package dInterface;

import javax.swing.JLabel;
import javax.swing.JPanel;

import dInternal.DxState;
import dInternal.DxStateBarModel;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxStateBar is a class used to:
 * <p>
 * TODO:insert comments
 * <p> 
 * 
 */
public class DxStateBar extends JPanel {

	private JLabel _theLabels[];

	private DxStateBarModel _stateBarModel;

	public DxStateBar(DxStateBarModel stateBarModel) {
		_stateBarModel = stateBarModel;
		_theLabels = new JLabel[_stateBarModel.size()];
		for (int i = 0; i < _stateBarModel.size(); i++) {
			_theLabels[i] = new JLabel();
			this.add(_theLabels[i]);
		}
	}

	public void upDate() {
		_stateBarModel.update();
		DxState s;
		for (int i = 0; i < _stateBarModel.size(); i++) {
			s = _stateBarModel.elementAt(i);
			if (s.getColor() != null) {
				_theLabels[i].setForeground(s.getColor());
			}
			if (s.getValue() >= 0) {
				_theLabels[i].setText(s.getHeader() + " : " + " "
						+ s.getValue() + " ");
			} else {
				_theLabels[i].setText(s.getHeader() + " : " + "  ");
			}
		} // end for
	} // end upDate

} // end class DxStateBar
