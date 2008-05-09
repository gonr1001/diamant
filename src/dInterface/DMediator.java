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
import java.io.IOException;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import dConstants.DConst;
import dInternal.DxLoadData;
import eLib.exit.dialog.DxExceptionDlg;
import eLib.exit.exception.DxException;

/**
 * Description: DMediator is a class used as in the pattern mediator to make a
 * link between DApplication and the current DDocument. There is only one
 * current document the one which is at the top of all jInternalFrames
 * 
 * <p>
 * 
 * 
 */

public class DMediator extends Object {

	private DApplication _dApplication;

	private Vector<DxDocument> _dxDocuments;

	private boolean _cancel;

	// -----------------------------
	public DMediator(DApplication dApplic) {
		_dApplication = dApplic;
		_dxDocuments = new Vector<DxDocument>();
		_cancel = false;
	} // end DMediator

	public boolean getCancel() {
		return _cancel;
	}

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
//	public String addDoc(String fileName, int type) throws DxException /* !!!NIC!!! */{
//		DApplication.getInstance().setCursorWait();
//		DDocument currentDoc = new DDocument(this, fileName, fileName, type);
//		_documents.addElement(currentDoc);
//
//		if (currentDoc.getError().length() == 0) {
//			_dApplication.getToolBar().setToolBars(
//					currentDoc.getCurrentDModel().getTTStructure());
//		} else {
//			new DxExceptionDlg(_dApplication.getJFrame(), currentDoc.getError());
//			System.exit(1);
//		}
//		DApplication.getInstance().setCursorDefault();
//		return currentDoc.getError();
//	} // end addDoc

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
//	public String addDoc(String ttName, String fileName, int type)
//			throws DxException {
//		DDocument currentDoc = new DDocument(this, ttName, fileName, type);
//		if (currentDoc.getError().length() == 0) {
//			_documents.addElement(currentDoc);
//			_dApplication.getToolBar().setToolBars(
//					currentDoc.getCurrentDModel().getTTStructure());
//			_dApplication.hideToolBar();
//		} else {
//			throw new DxException(currentDoc.getError());
//		}
//		return currentDoc.getError();
//	} // end addDoc

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
	 * @throws IOException 
	 * @throws NullPointerException 
	 * @throws Exception
	 * 
	 */
	public String addDxTTableDoc(String ttName, String fileName)
			throws DxException, NullPointerException, IOException {
		DxDocument currentDoc = new DxTTableDoc(this, fileName);
		//currentDoc.setDocumentName(ttName);
		_dxDocuments.addElement(currentDoc);
		return "";
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
	 * @throws IOException 
	 * @throws NullPointerException 
	 * @throws Exception
	 * 
	 */
	public String addDxTTableDoc(DxLoadData dxLoadData, String fileName){
			//throws DxException, NullPointerException, IOException {
		DxDocument currentDoc = new DxTTableDoc(this, dxLoadData, fileName);
		//currentDoc.setDocumentName(fileName);
		_dxDocuments.addElement(currentDoc);

		return "";
	}

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
	public String addDxTTExamDoc(String ttName, String fileName, int type) throws Exception /* !!!NIC!!! */{
		//TODO review all this method
		DApplication.getInstance().setCursorWait();
		DxDocument currentDoc = new DxTTableDoc(this, fileName, type);
		//currentDoc.setDocumentName(ttName);
		_dxDocuments.addElement(currentDoc);
		_dApplication.hideToolBar();
		DApplication.getInstance().setCursorDefault();
	
		//		_documents.addElement(currentDoc);

//		if (currentDoc.getError().length() == 0) {
//			_dApplication.getToolBar().setToolBars(
//					currentDoc.getCurrentDModel().getTTStructure());
//		} else {
//			new DxExceptionDlg(_dApplication.getJFrame(), currentDoc.getError());
//			System.exit(1);
//		}
//		DApplication.getInstance().setCursorDefault();
//		return currentDoc.getError();
		return "addDxTTExamDoc";
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
	public void addDxTTStructureDoc(String fileName) throws Exception {
		DxDocument currentDxDoc = new DxTTStructureDoc(this, fileName);
		_dxDocuments.addElement(currentDxDoc);
		_dApplication.getToolBar().setToolBars(currentDxDoc.getTTStructure());
	} // end addDxTTStructureDoc

//	public void removeCurrentDoc() {
//		_documents.remove(getCurrentDoc());
//		if (_documents.size() != 0) {
//			try {
//				_documents.get(0).getJIF().setSelected(true);
//			} catch (PropertyVetoException e) {
//				new DxExceptionDlg(_dApplication.getJFrame(), e.toString());
//				e.printStackTrace();
//				System.exit(1);
//			}
//		} else {// end if (_documents.size()!=0)
//			_dApplication.getToolBar().setEnabledToolbar(false);
//		}
//	} // end removeCurrentDoc

	private void removeCurrentDxDoc() {
		_dxDocuments.remove(getCurrentDxDoc());
		if (_dxDocuments.size() != 0) {
			try {
				_dxDocuments.get(0).getJIF().setSelected(true);
			} catch (PropertyVetoException e) {
				new DxExceptionDlg(_dApplication.getJFrame(), e.getMessage(), e);
				System.exit(1);
			}
		} else {// end if (_documents.size()!=0)
			_dApplication.getToolBar().setEnabledToolbar(false);
		}

	}

	// -------------------------------------------

	public String saveCurrentDxDoc(String str) {
		String error = "";
			getCurrentDxDoc().setDocumentName(str);
			getCurrentDxDoc().saveDxDocument(str);
		return error;
	}

//	public void closeCurrentDoc() {
//		if (getCurrentDoc() != null) {
//			if (getCurrentDoc().isModified()) {
//				_cancel = promptToSave();
//			} else {// end if
//				DDocument aux = getCurrentDoc();
//				_documents.remove(getCurrentDoc());
//				aux.close();
//			} // end else
//			if (_documents.size() == 0) {
//				_dApplication.hideToolBar();
//			} else {
//				_dApplication.getToolBar().setToolBars(
//						getCurrentDoc().getCurrentDModel().getTTStructure());
//			}
//		}// end if
//
//	}

	public void closeCurrentDxDoc() {
		if (getCurrentDxDoc() != null) {
			if (getCurrentDxDoc().isModified()) {
				_cancel = promptToSave();
			} else {// end if
				DxDocument aux = getCurrentDxDoc();
				_dxDocuments.remove(getCurrentDxDoc());
				aux.close();
			} // end else
			if (_dxDocuments.size() == 0) {
				_dApplication.hideToolBar();
			} else {
				_dApplication.getToolBar().setToolBars(
						getCurrentDxDoc().getTTStructure());
			}
		}// end if
	}

	// -------------------------------------------
//	public DDocument getCurrentDoc() {
//		DDocument currentDoc = null;
//		for (int i = 0; i < _documents.size(); i++) {
//			currentDoc = _documents.elementAt(i);
//			JInternalFrame currentFrame = currentDoc.getJIF();
//			if (currentFrame.isSelected()) {
//				return currentDoc;
//			} // end if
//		} // end for
//		if (_documents.size() != 0) {
//			currentDoc = _documents.elementAt(0);
//			try {
//				currentDoc.getJIF().setIcon(false);
//			} catch (PropertyVetoException e) {
//				new DxExceptionDlg(_dApplication.getJFrame(),
//						"In DMediator.getCurrentDoc: " + e.getMessage(), e);
//				System.exit(1);
//			}
//			return currentDoc;
//		}
//		return null;
//	} // end getCurrentDoc

	public DxDocument getCurrentDxDoc() {
		DxDocument currentDxDoc = null;
		for (int i = 0; i < _dxDocuments.size(); i++) {
			currentDxDoc = _dxDocuments.elementAt(i);
			JInternalFrame currentFrame = currentDxDoc.getJIF();
			if (currentFrame.isSelected()) {
				return currentDxDoc;
			} // end if
		} // end for
		if (_dxDocuments.size() != 0) {
			currentDxDoc = _dxDocuments.elementAt(0);
			try {
				currentDxDoc.getJIF().setIcon(false);
			} catch (PropertyVetoException e) {
				new DxExceptionDlg(_dApplication.getJFrame(), e.getMessage(), e);
				e.printStackTrace();
				System.exit(1);
			}
			return currentDxDoc;
		}
		return null;
	}

	// -------------------------------------------
//	public JInternalFrame getCurrentFrame() {
//		DDocument currentDoc = getCurrentDoc();
//		if (currentDoc != null) {
//			return currentDoc.getJIF();
//		}
//		return null;
//	} // end getCurrentFrame

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

			DxDocument aux = getCurrentDxDoc();
			switch (retval) {
			case JOptionPane.YES_OPTION:
				_dApplication.save();
				removeCurrentDxDoc();
				aux.close();
				return false;

			case JOptionPane.NO_OPTION:
				removeCurrentDxDoc();
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

	public void clean() {
		DxDocument dxd = this.getCurrentDxDoc();
		dxd.clean();
		dxd = null;
		
	}

} /* end class DMediator */