/**
 *
 * Title: DMediator $Revision: 1.1 $  $Date: 2003-01-24 18:27:59 $
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
 * @version $Revision: 1.1 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */

package dInterface;

import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

public class DMediator {
  private DView _dView;
  private Vector _documents;

  public DMediator(DView dView) {
    _dView= dView;
    _documents = new Vector();
  } // end Mediator

  //-------------------------------------------
  public void addDoc() { //MouseApp map, MouseMoveApp mvap){
    DDocument currentDoc = new DDocument(_dView, this);//, map, mvap);
    _documents.addElement(currentDoc);
    currentDoc = null;
  } //end addDoc

  //-------------------------------------------
  public DDocument getCurrentDoc() {
    System.out.println("getCurrentDoc" + _documents.size() +"*****");
    DDocument currentDoc = null;
    for (int i =0; i < _documents.size(); i++) {
      System.out.println("getCurrentDoc " + i +"++++");
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