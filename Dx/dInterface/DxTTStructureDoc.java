/**
 * Created on Jun 27, 2006
 * 
 * 
 * Title: DxTTStructureDoc.java 
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
import java.io.File;
import java.util.Observable;
// import java.util.Observer;

import javax.swing.JInternalFrame;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import dConstants.DConst;
import dInterface.dTimeTable.DetailedTTPane;
import dInterface.dTimeTable.SimpleTTPane;
import dInternal.DModel;
import dInternal.dTimeTable.TTStructure;
import eLib.exit.dialog.FatalProblemDlg;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxTTStructureDoc is a class used to:
 * <p>
 * make a link between the DxDocument and the DxTTStructure
 * <p>
 * 
 */
public class DxTTStructureDoc extends DxDocument {

	private TTStructure _ttStructure;

	/**
	 * 
	 * @param dMediator
	 *            (pattern Mediator)
	 * @param fileName
	 *            is the full path file name containing the TTStructure
	 * 
	 * @throws Exception
	 * 
	 */

	public DxTTStructureDoc(DMediator dMed, String fileName) throws Exception {
		super(dMed);
		_ttStructure = new TTStructure();
		// try {
		_ttStructure.loadTTStructFromFile(fileName);
		// }
		_documentName = modifiyDocumentName(fileName);
		buidDocument(true, true);
		_ttPane.updateTTPane(_ttStructure);
	}

	// -------------------------------------------
	public TTStructure getTTStructure() {
		return _ttStructure;
	} // end getTTStructure


	// -------------------------------------------
	private String modifiyDocumentName(String str) {
		if (str.endsWith("pref" + File.separator + "StandardTTC.xml")
				|| str.endsWith("pref" + File.separator + "StandardTTE.xml")) {
			str = str.substring(0, str.lastIndexOf("pref"));
			str += DConst.NO_NAME;
		}
		return str;
	}

	public void update(Observable dm, Object component) {
		if (component != null)
			component.toString();
		_dMediator.getDApplication().setCursorWait();
		_ttPane.updateTTPane(_ttStructure);
		// _stateBar.upDate();
		_dMediator.getDApplication().setCursorDefault();
	}// end update

	// -------------------------------------------
	public void displaySimple() {
		close();
		buidDocument(true, true);
		_ttPane.updateTTPane(_ttStructure);
	}

	// -------------------------------------------
	public void displayHorizontalSplit() {
		close();
		buidDocument(false, false);
		_ttPane.updateTTPane(_ttStructure);
	}

	public void displayVericalSplit() {
		close();
		buidDocument(false, true);
		_ttPane.updateTTPane(_ttStructure);
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

		_ttStructure.addObserver(this);

		_jif.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		_jif.setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));

		if (simple) {
			_ttPane = new SimpleTTPane(_ttStructure, _dMediator
					.getDApplication().getToolBar());
		} else {
			_ttPane = new DetailedTTPane(_ttStructure, _dMediator
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

	/**
	 * Retourne le nom du fichier utilis� afin de r�aliser l'importation
	 * automatique des donn�es
	 * 
	 * @return le nom du fichier utilis� afin de r�aliser l'importation
	 *         automatique des donn�es
	 */
	public String getAutoImportDIMFilePath() {
		return _autoImportDIMFilePath;
	}

	/**
	 * Applique le nom du fichier utilis� lors de l'importation automatique des
	 * donn�es
	 * 
	 * XXXX n'est pas appel� lorsque l'on est en mode debug, car en mode debug,
	 * ImportDlg n'est pas utilis�
	 * 
	 * @param importDIMFileName
	 */
	public void setAutoImportDIMFilePath(String importDIMFilePath) {
		_autoImportDIMFilePath = importDIMFilePath;
	}

	// public void changeInModelByToolBar(ActionListener listener) {
	// System.out.println("changeInModelByToolBar+ rgr here");
	//
	// }

	@Override
	public void changeInModel(ActionListener listener) {
		System.out.println("changeInModel+ rgr here");
		_ttStructure.changeInTTStructure(listener);
	}// end changeInDModel

	@Override
	public void saveTTStrucure(String str) {
		_ttStructure.saveTTStructure(str);
	}

	@Override
	public void changeInModel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DModel getCurrentDModel() {
		// TODO Auto-generated method stub
		return null;
	}
}
