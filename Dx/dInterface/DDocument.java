/**
 *
 * Title: DDocument $Revision: 1.9 $  $Date: 2003-03-10 17:28:41 $
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
 * @version $Revision: 1.9 $
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
import dInternal.TTParameters;
import dInternal.DModelEvent;
import dInternal.DModelListener;
import dResources.DConst;
import java.util.StringTokenizer;//debug

public class DDocument implements ActionListener, DModelListener{
  private boolean _modified;
  private DApplication _dApplic;

  /**
   */
  //private DMediator _mediator;
  private JInternalFrame _jif;
  private DModel _dm;
  private JLabel _bottomLablel;
  private TTPanel _ttPanel;
  //private TTParameters _ttParameters;

  //-------------------------------------------
  public DDocument(DApplication dApplic) {
        System.out.println("check token method : "+ (new StringTokenizer("    ")).countTokens());// debug
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
  _dApplic = dApplic;
    //_mediator = mediator;
    _dm = new DModel();
    _jif = new JInternalFrame(DConst.UN_TITLED,true,true,true,true);
    _bottomLablel = new JLabel("hello");
    JPanel _bottomPanel = initBottomPanel(_bottomLablel);
    _jif.getContentPane().add(_bottomPanel, BorderLayout.SOUTH);
    _ttPanel = new TTPanel();
    //_ttParameters = new TTParameters();
    _dm.addDModelListener(this);
    _modified = false;
    _jif.getContentPane().add(_ttPanel, BorderLayout.CENTER);
    _jif.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
    _jif.setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
    _jif.pack();
    _dApplic.getDesktop().add(_jif, new Integer(1));
    _dApplic.getDesktop().getDesktopManager().maximizeFrame(_jif);
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

    public TTParameters getTTParameters() {
      return _dm.getTTParameters();
    } // end getJIF

    public TTPanel getTTPanel() {
      return _ttPanel;
    } // end getJIF
    //-------------------------------------------

    private JPanel initBottomPanel(JLabel label){
      JPanel panel = new JPanel();
      label.setForeground(Color.red);
      panel.add(label);
      return panel;
    } // initBottomPanel

    public void updateBottomPanel(){
      _bottomLablel.setText("Change done");
    } // updateBottomPanel

/*    public void updateTTPanel(){
      _ttPanel.setText("Change done");
    } // initBottomPanel*/

    public void actionPerformed(ActionEvent  e) {
    if (e.getSource() instanceof CommandHolder) {
     ((CommandHolder) e.getSource()).getCommand().execute(_dApplic);
    }
    else {
    System.out.println("I do not know what to do, please help me (Action Performed)");
    }// end if ... else
    }// end actionPerformed

    public void changeInDModel(DModelEvent  e) {
       _bottomLablel.setText("Change done");
      _ttPanel.updateTTPanel(_dm.getTTParameters());
      // repaint();
    }// end actionPerformed
} /* end DDocument class */