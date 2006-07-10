/**
 * Created on Jun 27, 2006
 * 
 * 
 * Title: DxTTCycleDoc.java 
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JInternalFrame;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import dInterface.dTimeTable.DetailedTTPane;
import dInterface.dTimeTable.SimpleTTPane;
import dInternal.DModel;
import dInternal.DxStateBarModel;
import dInternal.dTimeTable.TTStructure;
import eLib.exit.dialog.FatalProblemDlg;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxTTCycleDoc is a class used to:
 * <p>
 * make a link between the DxDocument and the DxModel
 * <p>
 * 
 */
public class DxTTableDoc extends DxDocument {

	private String _type;

	private DxStateBar _stateBar;

	private DModel _dm;

	public DxTTableDoc(DMediator mediator, String fileName) throws Exception {
		super(mediator);
		_type = "type";
		// try {
		_dm = new DModel(this, fileName);
		// }
		_documentName = fileName;
		buidDocument(true, true);
		_ttPane.updateTTPane(_dm.getTTStructure());
	}

	// -------------------------------------------
	private void buidDocument(boolean simple, boolean vertical) {
		/*
		 * MIN_HEIGHT is needed to ajdust the minimum height of the _jif
		 */
		final int MIN_HEIGHT = 512;
		/*
		 * MIN_WIDTH is needed to ajdust the minimum width of the _jif
		 */
		final int MIN_WIDTH = 512;
		/*
		 * MIN_HEIGHT is needed to ajdust the minimum height of the _jif
		 */
		final int MAX_HEIGHT = 2048;
		/*
		 * MIN_WIDTH is needed to ajdust the minimum width of the _jif
		 */
		final int MAX_WIDTH = 2048;

		_jif = new JInternalFrame(_documentName, true, true, true, true);
		_jif.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		_jif.addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameClosing(InternalFrameEvent e) {
				e.toString();
				_dMediator.getDApplication().close();
			}
		});

		_dm.addObserver(this);
		_stateBar = new DxStateBar(new DxStateBarModel(_dm));

		_jif.getContentPane().add(_stateBar, BorderLayout.SOUTH);

		_jif.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		_jif.setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));

		if (simple) {
			_ttPane = new SimpleTTPane(_dm.getTTStructure(), _dMediator
					.getDApplication().getToolBar());
		} else {
			_ttPane = new DetailedTTPane(_dm.getTTStructure(), _dMediator
					.getDApplication().getToolBar(), vertical);
		}
		_jif.getContentPane().add(_ttPane.getPane(), BorderLayout.CENTER);
		_jif.pack();
		// the 1 in Integer(1) could be any integer
		_dMediator.getDApplication().getDesktop().add(_jif, new Integer(1));

		_jif.setVisible(true);

		// to comment if work with jifs
		try {
			_jif.setMaximum(true); // This line allows the scrollbars of the
			// TTPanel
			// to be present when the _jif is resized
		} catch (java.beans.PropertyVetoException pve) {
			new FatalProblemDlg(
					"I was in DDocument trying to make setMaximum!!!");
			System.exit(1); // end of execution abnormal
			pve.printStackTrace();
		}
	} // end buidDocument

	@Override
	public void update(Observable ttS, Object component) {
		if (component != null)
			component.toString();
		_dMediator.getDApplication().setCursorWait();
		_ttPane.updateTTPane(_dm.getTTStructure());
		_stateBar.upDate();
		_stateBar.upDate();
		_dMediator.getDApplication().setCursorDefault();
	}

	@Override
	public TTStructure getTTStructure() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changeInModel() {
		System.out.println("changeInModel+ rgr here");
		_dm.changeInDModel(new Object());
	}// end changeInDModel

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}
	
    // -------------------------------------------
    public DModel getCurrentDModel() {
        return _dm;
    } // end getDModel

	@Override
	public void saveTTStrucure(String str) {
		// TODO Auto-generated method stub

	}

	public String getTTtype() {
		return _type;
	}

	@Override
	public void changeInModel(ActionListener listener) {
		// TODO Auto-generated method stub

	}
}
