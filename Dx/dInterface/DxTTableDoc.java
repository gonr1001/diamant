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
import java.io.IOException;
import java.util.Observable;

import javax.swing.JInternalFrame;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import dInterface.dTimeTable.DetailedTTPane;
import dInterface.dTimeTable.SimpleTTPane;
import dInternal.DModel;
import dInternal.DxLoadData;
import dInternal.DxStateBarModel;
import dInternal.dTimeTable.TTStructure;
import eLib.exit.dialog.DxExceptionDlg;
import eLib.exit.dialog.InformationDlg;
import eLib.exit.exception.DxException;

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

	private int _type;

	private DxStateBar _stateBar;

	private DModel _dm;

	public DxTTableDoc() {
		// for tests
	}

	public DxTTableDoc(DMediator mediator, String fileName) throws DxException, NullPointerException, IOException {
		super(mediator);
		_type = 0;
		initDxTTableDoc(fileName);
	}

	private void initDxTTableDoc(String fileName) throws DxException, NullPointerException, IOException {
		_dm = new DModel(this, fileName, _type);
		_documentName = fileName;
		buidDocument(true, true);
		_ttPane.updateTTPane(_dm.getTTStructure());
	}
	
	public DxTTableDoc(DMediator mediator, String fileName, int type) throws DxException, NullPointerException, IOException {
		super(mediator);
		_type = type;
		initDxTTableDoc(fileName);
	}

	/**
	 * @param mediator
	 * @param dxLoadData
	 * @param fileName
	 */
	public DxTTableDoc(DMediator mediator, DxLoadData dxLoadData,
			String fileName) {
		super(mediator);
		_type = 0;
		_documentName = fileName;
		initDxTTableDoc(dxLoadData);
	}

	/**
	 * @param dxLoadData
	 */
	private void initDxTTableDoc(DxLoadData dxLoadData) {
		_dm = new DModel(this, dxLoadData, _type);
		buidDocument(true, true);
		_ttPane.updateTTPane(_dm.getTTStructure());
		
	}

	// -------------------------------------------
	private void buidDocument(boolean simple, boolean vertical) {
		/*
		 * MIN_HEIGHT is needed to adjust the minimum height of the _jif
		 */
		final int MIN_HEIGHT = 512;
		/*
		 * MIN_WIDTH is needed to adjust the minimum width of the _jif
		 */
		final int MIN_WIDTH = 512;
		/*
		 * MIN_HEIGHT is needed to adjust the minimum height of the _jif
		 */
		final int MAX_HEIGHT = 2048;
		/*
		 * MIN_WIDTH is needed to adjust the minimum width of the _jif
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
			new DxExceptionDlg(
					"I was in DDocument trying to make setMaximum!!!\n"+pve.getMessage());
			pve.printStackTrace();
			System.exit(1); // end of execution abnormal
		}
	} // end buidDocument

	@Override
	public void update(Observable md, Object component) {
		if (md instanceof DModel){
		if (component != null)
			component.toString();
		_dMediator.getDApplication().setCursorWait();
		_ttPane.updateTTPane(_dm.getTTStructure());
		_stateBar.upDate();
		_stateBar.upDate();
		_dMediator.getDApplication().setCursorDefault();
		} else {
		new InformationDlg("I am in DxTTTableDoc.update \n" 
				+"This message will never ocurrs in normal conditions");
		}	
	}

	@Override
	public TTStructure getTTStructure() {		
		return _dm.getTTStructure();
	}

	public void changeInModel(String str) {
		_dm.changeInDModel(str);
	}// end changeInDModel


	// -------------------------------------------
	public DModel getCurrentDModel() {
		return _dm;
	} // end getDModel
	
	
	// -------------------------------------------
	@Override
	public boolean isModified() {
		return _dm.getModified();
	} // end getDModel


	@Override
	public void displaySimple() {
		close();
		buidDocument(true, true);
		_ttPane.updateTTPane(_dm.getTTStructure());
		_stateBar.upDate();
	}

	@Override
	public void displayVericalSplit() {
		 close();
		 buidDocument(false, false);
		 _ttPane.updateTTPane(_dm.getTTStructure());
		 _stateBar.upDate();
	}

	@Override
	public void displayHorizontalSplit() {
		 close();
		 buidDocument(false, true);
		 _ttPane.updateTTPane(_dm.getTTStructure());	
		 _stateBar.upDate();
	}

	@Override
	public void saveDxDocument(String str) {
		_dm.saveTimeTable(str);
		
	}

	@Override
	public void clean() {
		_stateBar = null;
		_dm = null;
	}
}
