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
	
	public abstract TTStructure getTTStructure();

	public abstract boolean isModified();
	
	public abstract void saveDxDocument(String str);

	public abstract DModel getCurrentDModel();

	public abstract void displaySimple(); 

	public abstract void displayVericalSplit();

	public abstract void displayHorizontalSplit();


} /* end DxDocument class */
