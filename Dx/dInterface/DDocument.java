/**
 *
 * Title: DDocument $Revision: 1.2 $  $Date: 2003-01-31 16:43:04 $
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
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInterface;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;

import javax.swing.event.*;
import javax.swing.*;
import javax.swing.JDesktopPane;

import dInternal.DModel;
import dResources.DConst;

public class DDocument {
  private boolean _modified;
  private DView _dView;
  private DMediator _mediator;
  private JInternalFrame _jif;
  private DModel _dm;

  //-------------------------------------------
  public DDocument(DView dView,
                            DMediator mediator) {
    _dView = dView;
    _mediator = mediator;
    _dm = new DModel();
    _jif = new JInternalFrame(DConst.UN_TITLED,true,true,true,true);
    JPanel ttPanel = new TTPanel();
    _modified = false;
    JScrollPane jsp = new JScrollPane(ttPanel);
    _jif.getContentPane().add(jsp, BorderLayout.CENTER);
    _jif.setBounds(10, 10, 600, 550);
    _dView.getDesktop().add(_jif, new Integer(1));
    _jif.setVisible(true);
  } // end constructor DDocument()



    //-------------------------------------------
    public void setModified(){
        _modified = true;
    } // end setModified
    //-------------------------------------------
    public boolean getModified(){
        return _modified;
    } // end getModified
    //-------------------------------------------
    public JInternalFrame getJIF() {
        return _jif;
    } // end getJIF
    //-------------------------------------------
    public DModel getDM(){
        return _dm;
    } //end getDModel

} /* end DDocument class */