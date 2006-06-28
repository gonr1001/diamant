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
import java.util.Observer;

import javax.swing.JInternalFrame;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import dConstants.DConst;
import dInterface.dTimeTable.DetailedTTPane;
import dInterface.dTimeTable.SimpleTTPane;
import dInterface.dTimeTable.TTPane;
import dInternal.DModel;
import dInternal.dTimeTable.TTStructure;
import eLib.exit.dialog.FatalProblemDlg;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxTTStructureDoc is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
public class DxTTStructureDoc extends DxDocument implements Observer {

	// private DMediator _dMediator;

	// private JInternalFrame _jif;

	// private String _documentName;

	// private TTPane _ttPane;

	private TTStructure _ttStructure;

	// private DxStateBar _stateBar;

	// private String _version;

	// private String _autoImportDIMFilePath = null; // XXXX GS

	// -----------------------------

	// for a new timetable and a open timetable
	// for new timetable Structure and open timetable Structure from a file

	/**
	 * 
	 * @param dMediator
	 *            (pattern Mediator)
	 * @param ttname
	 *            This string will be displayed as the title of the JIF
	 * @param fileName
	 *            is the full path file name containing the TTStructure
	 * @param type
	 *            is the type of timetable to be constructed see DConst.
	 *            possible types NO_TYPE = 0; CYCLE = 1; EXAM = 2; CYCLEANDEXAM =
	 *            3;
	 * @throws Exception
	 * 
	 */
	public DxTTStructureDoc(DMediator mediator, String fileName,
			String fileName2) throws Exception {
		_dMediator = mediator;
		_ttStructure = new TTStructure();
		// try {
		_ttStructure.loadTTStructFromFile(fileName);
		// }
		_documentName = modifiyDocumentName(fileName);
		buidDocument(true, true);
		_ttPane.updateTTPane(_ttStructure);
		_jif.addInternalFrameListener(this);

	} // end constructor DDocument()

	public void internalFrameActivated(InternalFrameEvent e) {
		e.toString(); // to avoid warning
		_dMediator.getDApplication().getToolBar().setToolBars(_ttStructure);
	} // end internalFrameActivated

//	// -------------------------------------------
//	public JInternalFrame getJIF() {
//		return _jif;
//	} // end getJIF

	// -------------------------------------------
	public final String getDocumentName() {
		return _documentName;
	} // end getDocumentName

	// -------------------------------------------
	public final void setDocumentName(String name) {
		_documentName = name;
		_jif.setTitle(name);
	} // end setDocumentName

	// -------------------------------------------
	public String getError() {
		return _dm.getError();
	}

	// -------------------------------------------
	public boolean isModified() {
		return _dm.getModified();
	} // end getModified

	// -------------------------------------------
	public DModel getCurrentDModel() {
		return _dm;
	} // end getDModel

	// -------------------------------------------
	public DMediator getDMediator() {
		return _dMediator;
	} // end getDModel

	// -------------------------------------------
	public TTPane getTTPane() {
		return _ttPane;
	}

	// -------------------------------------------
	public TTStructure getTTStructure() {
		return _ttStructure;
	} // end getJIF

	// -------------------------------------------
	public String getVersion() {
		return this._version;
	}

	// -------------------------------------------
	/**
	 * */
	public void setVersion(String version) {
		_version = version;
	}

	/*
	 * a revoir
	 */
	public void close() {
		_jif.dispose();
		_jif = null;
		_ttPane = null;
		_stateBar = null;
	}

	// -------------------------------------------
	private String modifiyDocumentName(String str) {
		if (str.endsWith("pref" + File.separator + "StandardTTC.xml")
				|| str.endsWith("pref" + File.separator + "StandardTTE.xml")) {
			str = str.substring(0, str.lastIndexOf("pref"));
			str += DConst.NO_NAME;
		}
		return str;
	}

	// public void update(Observable dm, Object component) {
	// if (component != null)
	// component.toString();
	// _dMediator.getDApplication().setCursorWait();
	// _ttPane.updateTTPane(((DModel) dm).getTTStructure());
	// _stateBar.upDate();
	// _dMediator.getDApplication().setCursorDefault();
	// }// end update

	// -------------------------------------------
	public void displaySimple() {
		close();
		buidDocument(true, true);
		_ttPane.updateTTPane(_dm.getTTStructure());
	}

	// -------------------------------------------
	public void displayHorizontalSplit() {
		close();
		buidDocument(false, false);
		_ttPane.updateTTPane(_dm.getTTStructure());
	}

	public void displayVericalSplit() {
		close();
		buidDocument(false, true);
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

		// _ttStructure.addObserver(this);

		// _stateBar = new DxStateBar(new DxStateBarModel(_dm));

		// _jif.getContentPane().add(_stateBar, BorderLayout.SOUTH);

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
		_dMediator.getDApplication().getDesktop().add(_jif, new Integer(1)); // XXXX
		// Pascal:
		// Magic
		// number
		_jif.setVisible(true);

		// to comment if work with jifs
		try {
			_jif.setMaximum(true); // This line allows the scrollbars of the
			// TTPanel
			// to be present when the _jif is resized
		} catch (java.beans.PropertyVetoException pve) {
			new FatalProblemDlg(
					"I was in DDocument trying to make setMaximum!!!");
			System.exit(52); // XXXX Pascal: 52 ?
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
}
