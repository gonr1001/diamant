/**
 *
 * Title: DMediator $Revision: 1.8 $  $Date: 2003-05-23 09:30:58 $
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
 * @version $Revision: 1.8 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */

package dInterface;

import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;


import dInternal.dTimeTable.TTStructure;

public class DMediator {
  private DApplication _dApplic;
  private Vector _documents;

  public DMediator(DApplication dApplic) {
    _dApplic = dApplic;
    _documents = new Vector();
  } // end Mediator

  //-------------------------------------------
  public void addDoc(String title, TTStructure ttStruct) { //MouseApp map, MouseMoveApp mvap){
    DDocument currentDoc = new DDocument(_dApplic, title, ttStruct);//, map, mvap);
    _documents.addElement(currentDoc);
    currentDoc = null;
  } //end addDoc

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

} /* end class DMediator */