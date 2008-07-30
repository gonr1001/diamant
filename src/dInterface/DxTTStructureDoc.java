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
import java.io.File;
import java.io.InputStream;
import java.util.Observable;


import javax.swing.JInternalFrame;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import dConstants.DConst;
import dInterface.dTimeTable.DxDetailedTTPane;
import dInterface.dTimeTable.DxSimpleTTPane;
import dInternal.DModel;
import dInternal.dTimeTable.TTStructure;
import developer.DxFlags;
import eLib.exit.dialog.FatalProblemDlg;
import eLib.exit.dialog.InformationDlg;

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
		System.out.println("UFile Name in addDxTTStructureDoc" + fileName);
		_ttStructure = new TTStructure();
		// try {
		_ttStructure.loadTTStructFromFile(fileName);
		// }
		_documentName = modifiyDocumentName(fileName);
		buidDocument(true, true);
		if(DxFlags.newDxTTPane) {
			_dxTTPane.updateDxTTPane(_ttStructure);
		} 
//		else {
//			_ttPane.updateTTPane(_ttStructure);
//		}
//		_ttPane.updateTTPane(_ttStructure);
	}
	
	public DxTTStructureDoc(DMediator dMed, InputStream fileName) throws Exception {
		super(dMed);
		System.out.println("UFile Name in addDxTTStructureDoc" + fileName);
		_ttStructure = new TTStructure();
		// try {
		_ttStructure.loadTTStructFromFile(fileName);
		// }
//		_documentName = modifiyDocumentName(fileName);
		buidDocument(true, true);
		if(DxFlags.newDxTTPane) {
			_dxTTPane.updateDxTTPane(_ttStructure);
		} 
//		else {
//			_ttPane.updateTTPane(_ttStructure);
//		}
//		_ttPane.updateTTPane(_ttStructure);
	}

	// -------------------------------------------
	public TTStructure getTTStructure() {
		return _ttStructure;
	} // end getTTStructure


	// -------------------------------------------
	private String modifiyDocumentName(String str) {
		String iStr = str;
		if (iStr.endsWith("pref" + File.separator + "StandardTTC.xml")
				|| iStr.endsWith("pref" + File.separator + "StandardTTE.xml")) {
			iStr = iStr.substring(0, iStr.lastIndexOf("pref"));
			iStr += DConst.NO_NAME;
		}
		return iStr;
	}

	@Override
	public void update(Observable ttS, Object component) {		
		if (ttS instanceof TTStructure){
			if (component != null)
			component.toString();
		_dMediator.getDApplication().setCursorWait();
		if(DxFlags.newDxTTPane) {
			_dxTTPane.updateDxTTPane(_ttStructure);
		} 
//		else {
//			_ttPane.updateTTPane(_ttStructure);
//		}
//		_ttPane.updateTTPane(_ttStructure);
		// _stateBar.upDate();
		_dMediator.getDApplication().setCursorDefault();
		} else {
			new InformationDlg("I am in DxTTStructureDoc.update \n" 
					+"This message will never ocurrs in normal conditions");
		}
	}// end update

	// -------------------------------------------
	public void displaySimple() {
		close();
		buidDocument(true, true);
		if(DxFlags.newDxTTPane) {
			_dxTTPane.updateDxTTPane(_ttStructure);
		} 
//		else {
//			_ttPane.updateTTPane(_ttStructure);
//		}
//		_ttPane.updateTTPane(_ttStructure);
		
	}

	// -------------------------------------------
	public void displayHorizontalSplit() {
		close();
		buidDocument(false, false);
		
		if(DxFlags.newDxTTPane) {
			_dxTTPane.updateDxTTPane(_ttStructure);
		} 
//		else {
//			_ttPane.updateTTPane(_ttStructure);
//		}
//		_ttPane.updateTTPane(_ttStructure);
		
	}

	public void displayVericalSplit() {
		close();
		buidDocument(false, true);
		if(DxFlags.newDxTTPane) {
			_dxTTPane.updateDxTTPane(_ttStructure);
		} 
//		else {
//			_ttPane.updateTTPane(_ttStructure);
//		}
//		_ttPane.updateTTPane(_ttStructure);
		
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

//		if(DxFlags.newDxTTPane) {
			if (simple) {
				_dxTTPane = new DxSimpleTTPane(_ttStructure, _dMediator
						.getDApplication().getToolBar());
			} else {
				_dxTTPane = new DxDetailedTTPane(_ttStructure, _dMediator
						.getDApplication().getToolBar(), vertical);
			}
//		} else {
//			if (simple) {
//				_ttPane = new SimpleTTPane(_ttStructure, _dMediator
//						.getDApplication().getToolBar());
//			} else {
//				_ttPane = new DetailedTTPane(_ttStructure, _dMediator
//						.getDApplication().getToolBar(), vertical);
//			}
//		}
		
		

		_jif.getContentPane().add(_dxTTPane.getDxPane(), BorderLayout.CENTER);
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
	 * Retourne le nom du fichier utilisé afin de réaliser l'importation
	 * automatique des données
	 * 
	 * @return le nom du fichier utilisé afin de réaliser l'importation
	 *         automatique des données
	 */
	public String getAutoImportDIMFilePath() {
		return _autoImportDIMFilePath;
	}

	/**
	 * Applique le nom du fichier utilisé lors de l'importation automatique des
	 * données
	 * 
	 * XXXX n'est pas appelé lorsque l'on est en mode debug, car en mode debug,
	 * ImportDlg n'est pas utilisé
	 * 
	 * @param importDIMFileName
	 */
	public void setAutoImportDIMFilePath(String importDIMFilePath) {
		_autoImportDIMFilePath = importDIMFilePath;
	}

	@Override
	public DModel getCurrentDModel() {
		return null;
	}

	@Override
	public void changeInModel(String str) {
		_ttStructure.changeInTTStructure(str);	
	}

	@Override
	public boolean isModified() {
		return _ttStructure.isModified();
	}

	@Override
	public void saveDxDocument(String str) {
		_ttStructure.saveTTStructure(str);	
	}

	@Override
	public void clean() {
		_ttStructure = null;	
	}
}
