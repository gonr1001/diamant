/**
 *
 * Title: DMediator
 *
 *
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
 */

package dInterface;

import java.beans.PropertyVetoException;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import dConstants.DConst;

import eLib.exit.dialog.FatalProblemDlg;

/**
 * Description: DMediator is a class used as in the pattern mediator to make a
 * link between DApplication and the current DDocument. There is only one
 * currect document the one which is at the top of all jInternalFrames
 * 
 * <p>
 * 
 * 
 */

public class DMediator extends Object {

	private DApplication _dApplication;

	private Vector<DDocument> _documents;
	private Vector<DxDocument> _dxDocuments;

	private boolean _cancel;

	// -----------------------------
	public DMediator(DApplication dApplic) {
		_dApplication = dApplic;
		_documents = new Vector<DDocument>();
		_dxDocuments = new Vector<DxDocument>();
		_cancel = false;
	} // end Mediator

	public boolean getCancel() {
		return _cancel;
	}

	/**
	 * for new tt and for open tt
	 * 
	 * @param ttname
	 *            This string will be displayed as the title of the JIF
	 * @param fileName
	 *            is the full path file name containing the TTStructure
	 * @param type
	 *            is the type of timetable to be constructed possible types
	 *            NO_TYPE = 0; CYCLE = 1; EXAM = 2; CYCLEANDEXAM = 3;
	 * @throws Exception
	 * 
	 */
	public String addDoc(String ttName, String fileName, int type)
			throws Exception /* !!!NIC!!! */{
		DDocument currentDoc = new DDocument(this, ttName, fileName, type);
		if (currentDoc.getError().length() == 0) {
			_documents.addElement(currentDoc);
			_dApplication.getToolBar().setToolBars(
					currentDoc.getCurrentDModel().getTTStructure());
			_dApplication.hideToolBar();
		} else {
			new FatalProblemDlg(_dApplication.getJFrame(),
					"In DMediator.addDoc: " + currentDoc.getError());
			System.exit(1);
		}
		return currentDoc.getError();
	} // end addDoc

	/**
	 * for new ttStructure and for open ttStructure
	 * 
	 * @param fileName
	 *            is the full path file name containing the TTStructure
	 * @param type
	 *            is the type of timetable to be constructed
	 * @throws Exception
	 * 
	 */
	public String addDoc(String fileName, int type) throws Exception /* !!!NIC!!! */{
		_dApplication.setCursorWait();
		DDocument currentDoc = new DDocument(this, fileName, fileName, type);
		_documents.addElement(currentDoc);

		if (currentDoc.getError().length() == 0) {
			_dApplication.getToolBar().setToolBars(
					currentDoc.getCurrentDModel().getTTStructure());
		} else {
			new FatalProblemDlg(_dApplication.getJFrame(), currentDoc
					.getError());
			System.exit(1);
		}
		_dApplication.setCursorDefault();
		return currentDoc.getError();
	} // end addDoc

	
	/**
	 * for new tt and for open tt
	 * 
	 * @param ttname
	 *            This string will be displayed as the title of the JIF
	 * @param fileName
	 *            is the full path file name containing the TTStructure
	 * @param type
	 *            is the type of timetable to be constructed possible types
	 *            NO_TYPE = 0; CYCLE = 1; EXAM = 2; CYCLEANDEXAM = 3;
	 * @throws Exception
	 * 
	 */
	public String addDxTTCycleDoc(String ttName, String fileName)
			throws Exception /* !!!NIC!!! */{
		DxDocument currentDoc = new DxTTCycleDoc(this, ttName, fileName);
//		if (currentDoc.getError().length() == 0) {
			_dxDocuments.addElement(currentDoc);
//			_dApplication.getToolBar().setToolBars(
//					currentDoc.getCurrentDModel().getTTStructure());
			_dApplication.hideToolBar();
//		} else {
//			new FatalProblemDlg(_dApplication.getJFrame(),
//					"In DMediator.addDoc: " + currentDoc.getError());
//			System.exit(1);
//		}
//	return currentDoc.getError();
		return "error";
	} // end addDoc

	/**
	 * for new ttStructure and for open ttStructure
	 * 
	 * @param fileName
	 *            is the full path file name containing the TTStructure
	 * @param type
	 *            is the type of timetable to be constructed
	 * @throws Exception
	 * 
	 */
	public String addDxTTExamDoc(String fileName, int type) throws Exception /* !!!NIC!!! */{
		_dApplication.setCursorWait();
		DDocument currentDoc = new DDocument(this, fileName, fileName, type);
		_documents.addElement(currentDoc);

		if (currentDoc.getError().length() == 0) {
			_dApplication.getToolBar().setToolBars(
					currentDoc.getCurrentDModel().getTTStructure());
		} else {
			new FatalProblemDlg(_dApplication.getJFrame(), currentDoc
					.getError());
			System.exit(1);
		}
		_dApplication.setCursorDefault();
		return currentDoc.getError();
	} // end addDoc
	
	
	/**
	 * for new ttStructure and for open ttStructure
	 * 
	 * @param fileName
	 *            is the full path file name containing the TTStructure
	 * @param type
	 *            is the type of timetable to be constructed
	 * @throws Exception
	 * 
	 */
	public String addDxTTStructureDoc(String fileName) throws Exception /* !!!NIC!!! */{
		
		DxDocument currentDoc = new DxTTStructureDoc(this, fileName, fileName);
		_dxDocuments.addElement(currentDoc);

//		if (currentDoc.getError().length() == 0) {
			_dApplication.getToolBar().setToolBars(
					currentDoc.getTTStructure());
////		} else {
//			new FatalProblemDlg(_dApplication.getJFrame(), currentDoc
//					.getError());
//			System.exit(1);
//		}
		
		return "error";
	} // end addDoc
	
	
	
	
	public void removeCurrentDoc() {
		_documents.remove(getCurrentDoc());
		if (_documents.size() != 0) {
			try {
				_documents.get(0).getJIF().setSelected(true);
			} catch (PropertyVetoException e) {
				new FatalProblemDlg(_dApplication.getJFrame(),
						"In DMediator.removeCurrentDoc: " + e.toString());
				System.exit(1);
			}
		} else {// end if (_documents.size()!=0)
			_dApplication.getToolBar().setEnabledToolbar(false);
		}
	} // end addDoc

	// -------------------------------------------

	public String saveCurrentDoc(String str) {
		getCurrentDoc().setDocumentName(str);
		String error = "";
		error = getCurrentDoc().getCurrentDModel().saveTimeTable(str);
		return error;
	}

	public void closeCurrentDoc() {
		if (getCurrentDoc() != null) {
			if (getCurrentDoc().isModified()) {
				_cancel = promptToSave();
			} else {// end if
				DDocument aux = getCurrentDoc();
				_documents.remove(getCurrentDoc());
				aux.close();
			} // end else
			if (_documents.size() == 0) {
				_dApplication.hideToolBar();
			} else {
				_dApplication.getToolBar().setToolBars(
						getCurrentDoc().getCurrentDModel().getTTStructure());
			}
		}// end if

	}

	// -------------------------------------------
	public DDocument getCurrentDoc() {
		DDocument currentDoc = null;
		for (int i = 0; i < _documents.size(); i++) {
			currentDoc = _documents.elementAt(i);
			JInternalFrame currentFrame = currentDoc.getJIF();
			if (currentFrame.isSelected()) {
				return currentDoc;
			} // end if
		} // end for
		if (_documents.size() != 0) {
			currentDoc = _documents.elementAt(0);
			try {
				currentDoc.getJIF().setIcon(false);
			} catch (PropertyVetoException e) {
				new FatalProblemDlg(_dApplication.getJFrame(),
						"In DMediator.getCurrentDoc: " + e.toString());
				System.exit(1);
			}
			return currentDoc;
		}
		return null;
	} // end getCurrentDoc
	public DxDocument getCurrentDxDoc() {
		DxDocument currentDoc = null;
		for (int i = 0; i < _dxDocuments.size(); i++) {
			currentDoc = _dxDocuments.elementAt(i);
			JInternalFrame currentFrame = currentDoc.getJIF();
			if (currentFrame.isSelected()) {
				return currentDoc;
			} // end if
		} // end for
		if (_dxDocuments.size() != 0) {
			currentDoc = _dxDocuments.elementAt(0);
			try {
				currentDoc.getJIF().setIcon(false);
			} catch (PropertyVetoException e) {
				new FatalProblemDlg(_dApplication.getJFrame(),
						"In DMediator.getCurrentDoc: " + e.toString());
				System.exit(1);
			}
			return currentDoc;
		}
		return null;
	}
	// -------------------------------------------
	public JInternalFrame getCurrentFrame() {
		DDocument currentDoc = getCurrentDoc();
		if (currentDoc != null) {
			return currentDoc.getJIF();
		}
		return null;
	} // end getCurrentFrame

	/**
	 * Prompts to save if document has changed. This checks the document to see
	 * if it has changed since it was loaded or last saved. If so, it prompts
	 * the user to save, not save or cancel.
	 * 
	 * @return false if the user cancels (presses the cancel button or the
	 *         dialog's close button). Otherwise, it return true.
	 */
	private boolean promptToSave() {
		int retval = JOptionPane.showConfirmDialog(_dApplication.getJFrame(),
				DConst.SAVE_PROMPT);
		DDocument aux = getCurrentDoc();
		switch (retval) {
		case JOptionPane.YES_OPTION:
			_dApplication.close();
			removeCurrentDoc();
			aux.close();
			return false;

		case JOptionPane.NO_OPTION:
			removeCurrentDoc();
			aux.close();
			return false;

		case JOptionPane.CANCEL_OPTION:
		case JOptionPane.CLOSED_OPTION:
			return true;
		}// end switch
		return true;// it does not matter
	}// end promptToSave

	public DApplication getDApplication() {
		return _dApplication;
	}


} /* end class DMediator */