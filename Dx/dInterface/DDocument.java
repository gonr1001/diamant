/**
 *
 * Title: DDocument $Revision: 1.22 $  $Date: 2003-05-27 17:15:11 $
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
 * @version $Revision: 1.22 $
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
import dInternal.dData.Status;
import dInternal.TTParameters;
import dInternal.DModelEvent;
import dInternal.DModelListener;
import dInternal.dTimeTable.TTStructure;
import dResources.DConst;
import java.util.StringTokenizer;
import dInterface.dTimeTable.TTPanel;

import com.iLib.gDialog.FatalProblemDlg;
//debug

public class DDocument implements ActionListener, DModelListener{
  private boolean _modified;
  private DApplication _dApplic;
  private JInternalFrame _jif;
  private DModel _dm;
  private TTPanel _ttPanel;
  private JPanel _statusPanel;
  JLabel _nbModif, _nbBlocs,  _nbCStu, _nbCInstr, _nbCRoom;

  //-------------------------------------------
  public DDocument(DApplication dApplic, String title, TTStructure ttStruct) {
   //     System.out.println("check token method : "+ (new StringTokenizer("    ")).countTokens());// debug
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
    _dm = new DModel(_dApplic.getJFrame(),ttStruct);
    _jif = new JInternalFrame(title,true,true,true,true);
    //_bottomLablel = new JLabel("hello");
    _statusPanel = initStatusPanel();
    _jif.getContentPane().add(_statusPanel, BorderLayout.SOUTH);
    _ttPanel = new TTPanel(_dm);
    //_ttPanel.setPreferredSize( new Dimension(20 , 20) );//_jif.getContentPane().getWidth()+ 500,
         // _jif.getContentPane().getHeight() + 500) );
    _dm.addDModelListener(this);
    _modified = false;

    _jif.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
    _jif.setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
    _jif.getContentPane().add(_ttPanel, BorderLayout.CENTER);
    _jif.pack();
    _dApplic.getDesktop().add(_jif, new Integer(1));
    _jif.setVisible(true);
    try {
      _jif.setMaximum(true);  //This line allows the scrollbars of the TTPanel to be present when the _jif is resized
    }
    catch (java.beans.PropertyVetoException pve) {
      new FatalProblemDlg("I was in DDocument trying to make steMaximum!!!" );
      System.exit(52);
      pve.printStackTrace();
    }
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

    public TTStructure getTTStructure() {
      return _dm.getTTStructure();
    } // end getJIF

    public TTPanel getTTPanel() {
      return _ttPanel;
    } // end getJIF
    //-------------------------------------------

    public JPanel initStatusPanel(){
      JPanel panel = new JPanel();
      _nbModif = new JLabel( "Modifications " + _dm.getStatus().getModif() );
      _nbBlocs = new JLabel(DConst.BLOCS + _dm.getStatus().getModif() +" / " + _dm.getStatus().getModif() );
      _nbCInstr = new JLabel("    +CON02+ nbCftIns");
      _nbCInstr.setForeground(Color.red);
      _nbCRoom = new JLabel("    +CON03+ nbCftRoom");
      _nbCRoom.setForeground(Color.blue);
      _nbCStu = new JLabel("    +CON01+nbCftStud");
      _nbCStu.setForeground(Color.magenta);
      panel.add(_nbModif);
      panel.add(_nbBlocs);
      panel.add(_nbCInstr);
      panel.add(_nbCRoom);
      panel.add(_nbCStu);
      return panel;
    } // initBottomPanel



    public void updateStatusPanel() {
      _nbModif.setText( "Modifications " + _dm.getStatus().getModif() );
      _nbBlocs.setText(DConst.BLOCS + _dm.getStatus().getModif() +" / " + _dm.getStatus().getModif() );
     _nbCInstr.setText("    +CON02+ nbCftIns");
     _nbCInstr.setForeground(Color.red);
     _nbCRoom.setText("    +CON03+ nbCftRoom");
     _nbCRoom.setForeground(Color.blue);
     _nbCStu.setText("    +CON01+nbCftStud");
      _nbCStu.setForeground(Color.magenta);
     }



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
      this.updateStatusPanel();
      _ttPanel.updateTTPanel(_dm.getTTStructure());
    }// end actionPerformed
} /* end DDocument class */