/**
 *
 * Title: DMediator $Revision: 1.43 $  $Date: 2004-06-09 19:29:16 $
 * Description: DMediator is a class used to
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
 * @version $Revision: 1.43 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */

package dInterface;

import java.util.Vector;
import java.beans.PropertyVetoException;


import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import com.iLib.gDialog.FatalProblemDlg;
import dInterface.dTimeTable.SaveCmd;
import dConstants.DConst;

public class DMediator {
  private DApplication _dApplic;
  private Vector _documents;
  private boolean _cancel;

  public DMediator(DApplication dApplic) {
    _dApplic = dApplic;
    _documents = new Vector();
    _cancel = false;
  } // end Mediator

  public boolean getCancel() {
    return _cancel;
  }
/**
 * for new tt and for open tt
 * @param ttname This string will be displaye as the title of the JIF
 * @param fileName is the full path file name containing the TTStructure
 * @param type is the type of timetable to be constructed
 *
 */
  public String addDoc(String ttName, String fileName, int type) {
    DDocument currentDoc;
    currentDoc = new DDocument(this, ttName, fileName, type);
    if(currentDoc.getError().length()==0){
      _documents.addElement(currentDoc);
      currentDoc.getDM().addAllListeners();
      _dApplic.getToolBar().setToolBars(currentDoc.getDM().getTTStructure());
      _dApplic.hideToolBar();
    }else{
      new FatalProblemDlg(_dApplic.getJFrame(), currentDoc.getError());
      System.exit(1);
    }
    return currentDoc.getError();
  } //end addDoc



/**
 * for new ttStructure and for open
 * @param fileName is the full path file name containing the TTStructure
 * @param type is the type of timetable to be constructed
 *
 */
  public String addDoc(String fileName, int type) {
    DDocument currentDoc = new DDocument(this, fileName, fileName, type);
    _documents.addElement(currentDoc);
    if (currentDoc.getError().length() == 0){
      currentDoc.getDM().addAllListeners();
      _dApplic.getToolBar().setToolBars(currentDoc.getDM().getTTStructure());
    }else{
      new FatalProblemDlg(_dApplic.getJFrame(), currentDoc.getError());
      System.exit(1);
    }
    return currentDoc.getError();
  } //end addDoc

  public void removeCurrentDoc(){
      _documents.remove(getCurrentDoc());
      if (_documents.size()!=0) {
        try{
          ((DDocument)_documents.get(0)).getJIF().setSelected(true);
        } catch (PropertyVetoException e){
          new FatalProblemDlg(_dApplic.getJFrame(), e.toString());
          System.exit(1);
        }
      }else{//end if (_documents.size()!=0)
        _dApplic.getToolBar().setEnabledToolbar(false);
      }
    } //end addDoc
  //-------------------------------------------

  public String saveCurrentDoc(String str){
    getCurrentDoc().setDocumentName(str);
    String error = "";
    error = getCurrentDoc().getDM().saveTimeTable(str);
    return error;
  }

  public void closeCurrentDoc(){
    if (getCurrentDoc()!= null) {
      if (getCurrentDoc().isModified()) {
        _cancel = promptToSave();
      } else {//end if
        DDocument aux = getCurrentDoc();
        _documents.remove(getCurrentDoc());
        aux.close();
      } //end else
      if(_documents.size()==0){
        _dApplic.hideToolBar();
      }else{
        _dApplic.getToolBar().setToolBars(getCurrentDoc().getDM().getTTStructure());
      }
    }//end if

  }
  //-------------------------------------------
  public DDocument getCurrentDoc() {
    DDocument currentDoc = null;
    for (int i =0; i < _documents.size(); i++) {
      currentDoc = (DDocument) _documents.elementAt(i);
      JInternalFrame currentFrame = currentDoc.getJIF();
      if (currentFrame.isSelected()) {
        return currentDoc;
      } // end if
    } // end for
    if (_documents.size()!=0){
      currentDoc = (DDocument) _documents.elementAt(0);
      try{
         currentDoc.getJIF().setIcon(false);
       } catch (PropertyVetoException e){
         new FatalProblemDlg(_dApplic.getJFrame(), e.toString());
         System.exit(1);
        }
        return currentDoc;
    }
        return null;
  } //end getCurrentDoc

  //-------------------------------------------
  public JInternalFrame getCurrentFrame() {
    DDocument currentDoc = getCurrentDoc();
    if (currentDoc != null) {
      return currentDoc.getJIF();
    } // end if
    else {
      return null;
    } // end else
  } //end getCurrentFrame
  /**
* Prompts to save if document has changed.
* This checks the document to see if it has changed since it was
* loaded or last saved. If so, it prompts the user to save, not
* save or cancel.
*
* @return false if the user cancels (presses the cancel button
* or the dialog's close button).  Otherwise, it return true.
*/
  private boolean promptToSave() {
   int retval = JOptionPane.showConfirmDialog(_dApplic.getJFrame(), DConst.SAVE_PROMPT );
   DDocument aux = getCurrentDoc();
   switch ( retval ) {
     case JOptionPane.YES_OPTION:
       new SaveCmd().execute(_dApplic);
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
   }//end switch
   return true;// it does not matter
  }//end promptToSave


  public DApplication getDApplication() {
    return _dApplic;
  }
} /* end class DMediator */