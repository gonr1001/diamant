/**
 *
 * Title: DDocument $Revision: 1.67 $  $Date: 2003-09-10 13:54:08 $
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
 * @version $Revision: 1.67 $
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
import dInternal.dData.State;
//import dInternal.TTParameters;
import dInternal.DModelEvent;
import dInternal.DModelListener;
import dInternal.dTimeTable.TTStructure;
import dInternal.dTimeTable.TTStructureListener;
import dInternal.dTimeTable.TTStructureEvent;
import dInternal.dData.SetOfStatesEvent;
import dInternal.dData.SetOfStatesListener;
import dResources.DConst;
import java.util.StringTokenizer;
import dInterface.dTimeTable.TTPanel;

import dInterface.dTimeTable.CloseCmd;

import com.iLib.gDialog.FatalProblemDlg;
//debug

public class DDocument  extends InternalFrameAdapter implements
    ActionListener,
    DModelListener,
    TTStructureListener,
    SetOfStatesListener{
  private DApplication _dApplic;
  private JInternalFrame _jif;
  private String _documentName;
  private TTPanel _ttPanel;
  //private boolean _modified;
  private DModel _dm;
  private DStateBar _stateBar;
  private String _version;
  //JLabel _nbModif, _nbBlocs,  _nbCStu, _nbCInstr, _nbCRoom;


  //for a new timetable and a open timetable
  //for new timetable Structure and open timetable Structure from a file
  public DDocument(DApplication dApplic, String ttName, String fileName, int type) {
    _dApplic = dApplic;
    _dm = new DModel(_dApplic, fileName, type);
    if(_dm.getError().length()==0){
      //addTTListener(_dm.getTTStructure());
      _dm.getTTStructure().addTTStructureListener(this);
      ttName = modifiyDocumentName(ttName); // used only in the case of New TTStructure
      buidDocument(ttName);
      _ttPanel.updateTTPanel(_dm.getTTStructure());
      _jif.addInternalFrameListener(this);
    }

  } // end constructor DDocument()

  public void internalFrameActivated(InternalFrameEvent e) {
    _dApplic.getToolBar().setToolBars(getTTStructure());
    //_dApplic.getToolBar().selectBar(0);
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

  public String getError(){
    return _dm.getError();
  }
    //-------------------------------------------
    public boolean isModified(){
        return _dm.getModified();
    } // end getModified
    //-------------------------------------------


    public DModel getDM(){
        return _dm;
    } //end getDModel

    public TTPanel getTTPanel(){
      return _ttPanel;
    }

    public TTStructure getTTStructure() {
      return _dm.getTTStructure();
    } // end getJIF

 //   public TTPanel getTTPanel() {
  //    return _ttPanel;
 //   } // end getJIF
    //-------------------------------------------




     public void actionPerformed(ActionEvent  e) {
       if (e.getSource() instanceof CommandHolder) {
         ((CommandHolder) e.getSource()).getCommand().execute(_dApplic);
       }
       else {
         System.out.println("I do not know what to do, please help me (Action Performed)");
       }// end if ... else
     }// end actionPerformed

    public void changeInDModel(DModelEvent  e) {
      //System.out.println("Update TTPanel in DDocument changeInDModel");//debug
      //_dm.setStateBarComponent();
      _dm.setModified();
      _ttPanel.updateTTPanel(_dm.getTTStructure());
      //this.updateStateBar(_dm.getState());

    }// end actionPerformed

    /*
    */
    public void changeInStateBar (SetOfStatesEvent e){
      //_dm.buildSetOfEvents();
      _dm.setStateBarComponent();
      _stateBar.upDateDStateBar(_dm.getSetOfStates());
    }

    public void changeInTTStructure(TTStructureEvent  e) {
      System.out.println("I was here");
      _dm.setModified();
        _ttPanel.updateTTPanel(_dm.getTTStructure());
    }// end actionPerformed*/

  private void  buidDocument(String title){
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
    _documentName = title;
    _jif = new JInternalFrame(_documentName, true, true, true, true);
    _jif.setDefaultCloseOperation(_jif.DO_NOTHING_ON_CLOSE);
    _jif.addInternalFrameListener( new InternalFrameAdapter() {
      public void internalFrameClosing( InternalFrameEvent e ) {
        new CloseCmd().execute(_dApplic);
      }
    } );
    //_bottomLablel = new JLabel("hello");

    _ttPanel = new TTPanel(_dm);
    _dm.addDModelListener(this);
    _dm.getSetOfStates().addSetOfStatesListener(this);

    _stateBar = new DStateBar(_dm.getSetOfStates());//initStatusPanel();
    _dm.getSetOfStates().sendEvent();
    _jif.getContentPane().add(_stateBar, BorderLayout.SOUTH);

    _jif.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
    _jif.setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
    _jif.getContentPane().add(_ttPanel, BorderLayout.CENTER);
    _jif.pack();
    _dApplic.getDesktop().add(_jif, new Integer(1));
    _jif.setVisible(true);
    //to comment if work with jifs
    try {
      _jif.setMaximum(true);  //This line allows the scrollbars of the TTPanel
                              // to be present when the _jif is resized
    }
    catch (java.beans.PropertyVetoException pve) {
      new FatalProblemDlg("I was in DDocument trying to make steMaximum!!!" );
      System.exit(52);
      pve.printStackTrace();
    }
    //comment until here
  }

  /***/
  public String getVersion(){
    return _version;
  }

  /**
   * */
  public void setVersion(String version){
    _version=version;
  }
  /*
  * a revoir
  */
  public void close(){
    _jif.dispose();
    _jif = null;
    _documentName = "";
    _dm = null;
    _ttPanel = null;
    _stateBar = null;

  }
  private String modifiyDocumentName(String str) {
    if (str.endsWith("pref"+File.separator+"StandardTTC.xml") ||
        str.endsWith("pref"+File.separator+"StandardTTE.xml") ){
      str = str.substring(0,str.lastIndexOf("pref"));
      str += DConst.NO_NAME;
    }
    return str;
  }
} /* end DDocument class */
