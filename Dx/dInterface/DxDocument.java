/**
 * Created on Jun 27, 2006
 * 
 * 
 * Title: DxDocument.java 
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

// import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JInternalFrame;
import dInterface.dTimeTable.TTPane;
import dInternal.DModel;
import dInternal.dTimeTable.TTStructure;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxDocument is a class used to:
 * <p>
 * treat the two types of DxDocument, DxTTStructure
 * <p>
 * and
 * <p>
 * DxTTCycleDoc or DxTTExamDoc
 * <p>
 * 
 */
public abstract class DxDocument implements Observer {

	protected DMediator _dMediator;

	protected JInternalFrame _jif;

	protected String _documentName;

	protected TTPane _ttPane;

	protected String _autoImportDIMFilePath;

	public DxDocument() {
		// for tests
	}

	public DxDocument(DMediator dMed) {
		_dMediator = dMed;
	}

	public abstract void update(Observable dm, Object component);

	public abstract void changeInModel(String str);

	// -------------------------------------------
	public final JInternalFrame getJIF() {
		return _jif;
	} // end getJIF

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
	public DMediator getDMediator() {
		return _dMediator;
	} // end getDMediator

	public final TTPane getTTPane() {
		return _ttPane;
	}

	public void close() {
		_jif.dispose();
	}

	public abstract TTStructure getTTStructure();

	// // -----------------------------
	//
	// // for a new timetable and a open timetable
	// // for new timetable Structure and open timetable Structure from a file
	//
	// /**
	// *
	// * @param dMediator
	// * (pattern Mediator)
	// * @param ttname
	// * This string will be displayed as the title of the JIF
	// * @param fileName
	// * is the full path file name containing the TTStructure
	// * @param type
	// * is the type of timetable to be constructed see DConst.
	// * possible types NO_TYPE = 0; CYCLE = 1; EXAM = 2; CYCLEANDEXAM =
	// * 3;
	// * @throws Exception
	// *
	// */
	// // XXXX Pascal: 'type' devrait etre un objet, pas un 'int' !
	// public DxDocument(DMediator dMediator, String ttName, String fileName,
	// int type) throws Exception /* !!!NIC!!! */{
	// _dMediator = dMediator;
	// // _dm = new DModel(this, fileName, type);
	// // ####RGR S'il y a une erreur, on ne fait rien et on ajoute le frame
	// // listener quand même. L'erreur survient à l'appel du FramListener.
	// // Après trace de la construction du DModel, les instructeurs sont
	// // valide mais les activities créent une erreur.
	// System.out.println("My error is " + _dm.getError());
	// if (_dm.getError().length() == 0) {
	// _documentName = modifiyDocumentName(ttName);
	// buidDocument(true, true);
	// _ttPane.updateTTPane(_dm.getTTStructure());
	//
	// if (_dm.isOnlyATimeTable()) {
	// _stateBar.paint();
	// } else if (_dm.isATimeTable()) {
	// _stateBar.upDate();
	// }
	// }
	// _jif.addInternalFrameListener(this);
	//
	// } // end constructor DDocument()
	//

	//
	// public void internalFrameActivated(InternalFrameEvent e) {
	// e.toString(); // XXXX Pascal: Pkoi appelle toString? Devrait etre e=e
	// _dMediator.getDApplication().getToolBar().setToolBars(_ttStructure);
	// } // end internalFrameActivated

	//
	// // -------------------------------------------
	public abstract boolean isModified();


	//
	// // -------------------------------------------
	// public DModel getCurrentDModel() {
	// return _dm;
	// } // end getDModel
	//

	// // -------------------------------------------

	// /*
	// * a revoir
	// */
	// public abstract void close();

	//
	// // -------------------------------------------
	// private String modifiyDocumentName(String str) {
	// if (str.endsWith("pref" + File.separator + "StandardTTC.xml")
	// || str.endsWith("pref" + File.separator + "StandardTTE.xml")) {
	// str = str.substring(0, str.lastIndexOf("pref"));
	// str += DConst.NO_NAME;
	// }
	// return str;
	// }
	//

	//
	// // -------------------------------------------
	// public void displaySimple() {
	// close();
	// buidDocument(true, true);
	// _ttPane.updateTTPane(_dm.getTTStructure());
	// }
	//
	// // -------------------------------------------
	// public void displayHorizontalSplit() {
	// close();
	// buidDocument(false, false);
	// _ttPane.updateTTPane(_dm.getTTStructure());
	// }
	//
	// public void displayVericalSplit() {
	// close();
	// buidDocument(false, true);
	// _ttPane.updateTTPane(_dm.getTTStructure());
	// }
	//
	// // -------------------------------------------
	// private void buidDocument(boolean simple, boolean vertical) {
	// /*
	// * MIN_HEIGHT is needed to ajdust the minimum height of the _jif
	// */
	// final int MIN_HEIGHT = 512;
	// /*
	// * MIN_WIDTH is needed to ajdust the minimum width of the _jif
	// */
	// final int MIN_WIDTH = 512;
	// /*
	// * MIN_HEIGHT is needed to ajdust the minimum height of the _jif
	// */
	// final int MAX_HEIGHT = 2048;
	// /*
	// * MIN_WIDTH is needed to ajdust the minimum width of the _jif
	// */
	// final int MAX_WIDTH = 2048;
	//
	// _jif = new JInternalFrame(_documentName, true, true, true, true);
	// _jif.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	// _jif.addInternalFrameListener(new InternalFrameAdapter() {
	// public void internalFrameClosing(InternalFrameEvent e) {
	// e.toString();
	// _dMediator.getDApplication().close();
	// }
	// });
	//
	// _dm.addObserver(this);
	//
	// _stateBar = new DxStateBar(new DxStateBarModel(_dm));
	//
	// _jif.getContentPane().add(_stateBar, BorderLayout.SOUTH);
	//
	// _jif.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
	// _jif.setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
	//
	// if (simple) {
	// _ttPane = new SimpleTTPane(_dm.getTTStructure(), _dMediator
	// .getDApplication().getToolBar());
	// } else {
	// _ttPane = new DetailedTTPane(_dm.getTTStructure(), _dMediator
	// .getDApplication().getToolBar(), vertical);
	// }
	// _jif.getContentPane().add(_ttPane.getPane(), BorderLayout.CENTER);
	// _jif.pack();
	// _dMediator.getDApplication().getDesktop().add(_jif, new Integer(1)); //
	// XXXX
	// // Pascal:
	// // Magic
	// // number
	// _jif.setVisible(true);
	//
	// // to comment if work with jifs
	// try {
	// _jif.setMaximum(true); // This line allows the scrollbars of the
	// // TTPanel
	// // to be present when the _jif is resized
	// } catch (java.beans.PropertyVetoException pve) {
	// new FatalProblemDlg(
	// "I was in DDocument trying to make setMaximum!!!");
	// System.exit(52); // XXXX Pascal: 52 ?
	// pve.printStackTrace();
	// }
	// } // end buidDocument
	//
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

	public abstract void saveTTStrucure(String str);

	public abstract DModel getCurrentDModel();

	public abstract void displaySimple(); 

	public abstract void displayVericalSplit();

	public abstract void displayHorizontalSplit();
	// // -------------------------------------------
	// public void displaySimple() {
	// close();
	// buidDocument(true, true);
	// _ttPane.updateTTPane(_dm.getTTStructure());
	// }
	//
	// // -------------------------------------------
	// public void displayHorizontalSplit() {
	// close();
	// buidDocument(false, false);
	// _ttPane.updateTTPane(_dm.getTTStructure());
	// }
	//
	// public void displayVericalSplit() {
	// close();
	// buidDocument(false, true);
	// _ttPane.updateTTPane(_dm.getTTStructure());
	// }
	//

} /* end DxDocument class */
