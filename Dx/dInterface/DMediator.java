/**
 *
 * Title: DMediator $Revision: 1.46 $  $Date: 2004-09-29 19:00:39 $
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
 * @version $Revision: 1.46 $
 * @author  $Author: garr2701 $
 * @since JDK1.3
 */

package dInterface;

import java.beans.PropertyVetoException;
import java.io.File;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.tictac.mouseTrap.dModel.Trace;

import dConstants.DConst;
import dInterface.dTimeTable.SaveCmd;
import eLib.exit.dialog.FatalProblemDlg;

public class DMediator {
  private DApplication _dApplic;
  private Vector _documents;
  private boolean _cancel;

//+++++++++++++++++++++++++++++
  Logger logger = Logger.getLogger(this.getClass().getName());
  Trace trace=new Trace();
  public DMediator() {
    PropertyConfigurator.configureAndWatch("trace"+File.separator+"log4j.conf");
  }
  public DMediator(boolean flag) {
    PropertyConfigurator.configureAndWatch("trace"+File.separator+"log4jreex.conf");
  }
  //-----------------------------

  public DMediator(DApplication dApplic) {
  	//  +++++++++++++++++++++++++++++
   	logger.info(trace.write(this,dApplic));	
     //-----------------------------
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
//  +++++++++++++++++++++++++++++
  	Vector traceParams=new Vector();
  	traceParams.add(ttName);
  	traceParams.add(fileName);
  	traceParams.add(new Integer(type));
  	logger.info(trace.write(this,traceParams));	
    //-----------------------------
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
//  +++++++++++++++++++++++++++++
  	Vector traceParams=new Vector();
  	traceParams.add(fileName);
  	traceParams.add(new Integer(type));
  	logger.info(trace.write(this,traceParams));	
    //-----------------------------
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
  	//  +++++++++++++++++++++++++++++
  	logger.info(trace.write(this));	
    //-----------------------------
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
//  +++++++++++++++++++++++++++++
  	logger.info(trace.write(this,str));	
    //-----------------------------
    getCurrentDoc().setDocumentName(str);
    String error = "";
    error = getCurrentDoc().getDM().saveTimeTable(str);
    return error;
  }

  public void closeCurrentDoc(){
  	//  +++++++++++++++++++++++++++++
  	logger.info(trace.write(this));	
    //-----------------------------

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
		return null;
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
//  +++++++++++++++++++++++++++++
  	logger.info(trace.write(this));	
    //-----------------------------
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