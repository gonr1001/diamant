/**
 *
 * Title: DDocument $Revision: 1.4 $  $Date: 2003-02-20 15:13:33 $
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
 * @version $Revision: 1.4 $
 * @author  $Author: rgr $
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
import dResources.DConst;

public class DDocument implements ActionListener{
  private boolean _modified;
  private DView _dView;

  /**
   * @link aggregationByValue
   */
  //private DMediator _mediator;
  private JInternalFrame _jif;
  private DModel _dm;
  private JLabel _bottomLablel;
  //-------------------------------------------
  public DDocument(DView dView) {
  /* MIN_HEIGHT is needed to ajdust the minimum
   * height of the _jif */
  final int MIN_HEIGHT = 512;
  /* MIN_WIDTH is needed to ajdust the minimum
   * width of the _jif */
  final int MIN_WIDTH = 512;
  /* MIN_HEIGHT is needed to ajdust the minimum
   * height of the _jif */
  final int MAX_HEIGHT = 1024;
  /* MIN_WIDTH is needed to ajdust the minimum
   * width of the _jif */
  final int MAX_WIDTH = 1024;
  _dView = dView;
    //_mediator = mediator;
    _dm = new DModel();
    _jif = new JInternalFrame(DConst.UN_TITLED,true,true,true,true);
    _bottomLablel = new JLabel("hello");
    JPanel _bottomPanel = initBottomPanel(_bottomLablel);
    _jif.getContentPane().add(_bottomPanel, BorderLayout.SOUTH);
    TTPanel ttPanel = new TTPanel();
    _modified = false;
    _jif.getContentPane().add(ttPanel, BorderLayout.CENTER);
    _jif.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
    _jif.setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
    _jif.pack();
    _dView.getDesktop().add(_jif, new Integer(1));
    _dView.getDesktop().getDesktopManager().maximizeFrame(_jif);
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

    private JPanel initBottomPanel(JLabel label){
      JPanel panel = new JPanel();
      label.setForeground(Color.red);
      panel.add(label);
      return panel;
    } // initBottomPanel

    public void updateBottomPanel(){
      _bottomLablel.setText("done");
    } // initBottomPanel

    public void actionPerformed(ActionEvent  e) {
    if (e.getSource() instanceof CommandHolder) {
     ((CommandHolder) e.getSource()).getCommand().execute();
    // repaint();
    }
    else {
    System.out.println("I do not know what to do, please help me (Action Performed)");
    }// end if ... else
    }// end actionPerformed
} /* end DDocument class */