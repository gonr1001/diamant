/**
 *
 * Title: DDocument $Revision: 1.2 $  $Date: 2003-06-13 17:02:09 $
 * Description: DDocument is a class used to
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
 * @version $Revision: 1.2 $
 * @author  $Author: ysyam $
 * @since JDK1.3
 */
package dInterface;


import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;
import java.awt.Dimension;

import javax.swing.event.*;
import javax.swing.*;
import javax.swing.JDesktopPane;


import dInternal.DModel;
import dInternal.dData.Status;
//import dInternal.TTParameters;
import dInternal.DModelEvent;
import dInternal.DModelListener;
import dInternal.dTimeTable.TTStructure;
import dInternal.dTimeTable.TTStructureListener;
import dInternal.dTimeTable.TTStructureEvent;
import dResources.DConst;
import java.util.StringTokenizer;
import dInterface.dTimeTable.TTPanel;

import dInterface.dTimeTable.CloseCmd;

import com.iLib.gDialog.FatalProblemDlg;

public abstract class AbstractDocument {
  /**
   * @field DApplication _dApplic
   */
  protected DApplication _dApplic;
  protected JInternalFrame _jif;
  protected String _documentName;
  protected TTPanel _ttPanel;

  public AbstractDocument(DApplication dApplic, String title) {
    _dApplic = dApplic;
    _documentName = title;
  }

  public final JInternalFrame getJIF() {
    return _jif;
  } // end getJIF

  //   public TTPanel getTTPanel() {
 //    return _ttPanel;
 //   } // end getJIF
  //-------------------------------------------
  public final String getDocumentName() {
    return _documentName;
  } // end getDocumentName

  //-------------------------------------------
  public final void setDocumentName(String name) {
    _documentName = name;
    _jif.setTitle(name);
  } // end setDocumentName
    //-------------------------------------------
}