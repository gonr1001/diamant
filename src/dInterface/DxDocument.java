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

import dInterface.dTimeTable.DxTTPane;
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
	
	protected DxTTPane _dxTTPane;

	protected String _autoImportDIMFileName;

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

	public final DxTTPane getDxTTPane() {
		return _dxTTPane;
	}
	public void close() {
		_jif.dispose();
		//_dMediator.getDApplication().close();
	}




	/**
	 *
	 * getAutoImportDIMFilePath
	 * @return le nom du fichier utilisé afin de réaliser l'importation
	 *         automatique des données
	 */
	public String getAutoImportDIMFileName() {
		return _autoImportDIMFileName;
	}

	/**
	 * 
	 * setAutoImportDIMFilePath
	 * @param importDIMFileName
	 */
	public void setAutoImportDIMFileName(String importDIMFileName) {
		_autoImportDIMFileName = importDIMFileName;
	}
	
	public abstract TTStructure getTTStructure();

	public abstract boolean isModified();
	
	public abstract void saveDxDocument(String str);

	public abstract DModel getCurrentDModel();

	public abstract void displaySimple(); 

	public abstract void displayVericalSplit();

	public abstract void displayHorizontalSplit();

	public abstract void clean();
	
} /* end DxDocument class */
